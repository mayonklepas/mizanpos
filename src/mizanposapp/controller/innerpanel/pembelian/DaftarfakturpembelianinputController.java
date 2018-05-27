/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.pembelian;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Globalsession;
import mizanposapp.helper.Oneforallfunc;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.pembelian.Daftarfakturpembelian_input_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class DaftarfakturpembelianinputController {

    String id;
    CrudHelper ch = new CrudHelper();
    Daftarfakturpembelian_input_panel pane;
    String valsupplier, valgudang, valdept, valsalesman, valshipvia, valtop;
    int valcheck;
    int tipe_bayar, tipe_beli;
    DefaultTableModel dtmtabeldata;
    Object[] rowtabledata = new Object[11];
    ArrayList<Entitytabledata> tabeldatalist = new ArrayList<>();
    int col = 0;
    //int row = 0;
    NumberFormat nf = NumberFormat.getInstance();
    double total_pembelian_all = 0;
    double total_pajak = 0;

    public DaftarfakturpembelianinputController(Daftarfakturpembelian_input_panel pane) {
        this.pane = pane;
        loaddata();
        checkandcombocontrol();
        carisuplier();
        carigudang();
        caridepartment();
        carisalesman();
        carishipvia();
        caritop();
        addtotable();
        kalkulasi();
        hapusbaris();
    }

    private void customtable() {
        pane.tabledata.setRowSelectionAllowed(false);
        pane.tabledata.setCellSelectionEnabled(true);
        dtmtabeldata = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 3 || column == 5 || column == 8 || column == 9 || column == 11 ? false : true;
            }

        };
    }

    private void checkandcombocontrol() {
        pane.ckdiskon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = pane.tabledata.getSelectedRow();
                for (int i = 0; i < pane.tabledata.getRowCount(); i++) {
                    pane.tabledata.setValueAt("0", i, 6);
                    dtmtabeldata.fireTableCellUpdated(i, 6);
                    pane.tabledata.setValueAt("0", i, 7);
                    dtmtabeldata.fireTableCellUpdated(i, 7);
                    kalkulasitotalperrow(row);
                }
                if (pane.ckdiskon.isSelected()) {
                    hidetable(7);
                    showtable(6);

                } else {
                    hidetable(6);
                    showtable(7);
                }
                kalkulasitotal();
            }
        });

        pane.cmb_tipe_bayar.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (pane.cmb_tipe_bayar.getSelectedIndex() == 0) {
                    pane.eduang_muka.setText("0");
                    pane.eduang_muka.setEnabled(false);
                } else {
                    pane.eduang_muka.setEnabled(true);
                }
            }
        });
    }

    private void loaddata() {
        customtable();
        try {
            id = Staticvar.ids;
            if (id.equals("")) {
                pane.ckdiskon.setSelected(true);
                pane.cmb_tipe_pembelian.setSelectedIndex(0);
                pane.cmb_tipe_bayar.setSelectedIndex(0);
                pane.eduang_muka.setEnabled(false);
                pane.dtanggal.setDate(new Date());
                pane.dtanggal_info.setDate(new Date());
                pane.edsupplier.setText("");
                valsupplier = "";
                pane.edno_tarnsaksi.setText("");
                pane.eddept.setText("");
                valdept = "";
                pane.edgudang.setText(Globalsession.DEFAULT_NAMA_GUDANG);
                valgudang = Globalsession.DEFAULT_ID_GUDANG;
                pane.edketerangan.setText("");
                pane.edsalesman.setText("");
                valsalesman = "";
                pane.edshipvia.setText("");
                valshipvia = "";
                pane.edtop.setText("");
                valtop = "";
                pane.eduser_input.setText("Sementara Admin");
                pane.lsubtotal.setText("0");
                pane.edbiayalain.setText("0");
                pane.eddiskon1.setText("0");
                pane.eddiskon2.setText("0");
                pane.ltotal_pajak.setText("0");
                pane.eduang_muka.setText("0");
                pane.ltotal_pembelian.setText("0");

                dtmtabeldata.addColumn("Kode Barang");
                dtmtabeldata.addColumn("Nama Barang");
                dtmtabeldata.addColumn("Jumlah");
                dtmtabeldata.addColumn("Satuan");
                dtmtabeldata.addColumn("Harga Beli");
                dtmtabeldata.addColumn("Harga Jual");
                dtmtabeldata.addColumn("Diskon Persen");
                dtmtabeldata.addColumn("Diskon Nominal");
                dtmtabeldata.addColumn("Pajak");
                dtmtabeldata.addColumn("Gudang");
                dtmtabeldata.addColumn("Keterangan");
                dtmtabeldata.addColumn("Total");
                tabeldatalist.add(new Entitytabledata("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
                dtmtabeldata.addRow(rowtabledata);
                pane.tabledata.setModel(dtmtabeldata);
                pane.tabledata.getColumnModel().getColumn(7).setMinWidth(0);
                pane.tabledata.getColumnModel().getColumn(7).setMaxWidth(0);
                pane.tabledata.getColumnModel().getColumn(7).setWidth(0);

            } else {

                JSONParser jpdata = new JSONParser();
                String param = String.format("id=%s", id);
                Object rawobjdata = jpdata.parse(ch.getdatadetails("dm/datafakturpembelian", param));
                JSONObject jsonobjdata = (JSONObject) rawobjdata;
                Object objdata = jsonobjdata.get("data");
                JSONArray jadata = (JSONArray) objdata;
                for (int i = 0; i < jadata.size(); i++) {
                    JSONObject joindata = (JSONObject) jadata.get(i);
                    valcheck = Integer.parseInt(String.valueOf(joindata.get("diskon_dalam")));
                    if (valcheck == 1) {
                        pane.ckdiskon.setSelected(true);
                    } else {
                        pane.ckdiskon.setSelected(false);
                    }

                    tipe_bayar = Integer.parseInt(String.valueOf(joindata.get("istunai")));
                    if (tipe_bayar == 0) {
                        pane.eduang_muka.setText("0");
                        pane.eduang_muka.setEnabled(false);
                    } else {
                        pane.eduang_muka.setEnabled(true);
                        pane.eduang_muka.setText(String.valueOf(joindata.get("total_uang_muka")));
                    }
                    tipe_beli = Integer.parseInt(String.valueOf(joindata.get("isjasa")));
                    if (tipe_beli == 0) {

                    } else {

                    }

                    /*datapembelian : id_supplier, istunai, tanggal_pengantaran, id_pengantaran, id_gudang, 
                    tipe_pembelian, id_termofpayment, total_pembelian, total_biaya, diskon_persen, 
                    diskon_nominal, total_uang_ muka, total_pajak, id_currency, nilai_kurs, akun_pembelian, 
                            akun_biaya, akun_diskon, akun_uang_muka,
                            akun_pajak, id_nomor_po, id_cards_bagian_pembelian, diskon_dalam, diskon_per, isjasa*/
                    pane.dtanggal.setDate(new Date());
                    pane.dtanggal_info.setDate(new Date());
                    pane.edsupplier.setText(String.valueOf(joindata.get("nama_supplier")));
                    valsupplier = String.valueOf(joindata.get("id_supplier"));
                    pane.edno_tarnsaksi.setText(String.valueOf(joindata.get("")));
                    pane.eddept.setText(String.valueOf(joindata.get("")));
                    valdept = String.valueOf(joindata.get(""));
                    pane.edgudang.setText(String.valueOf(joindata.get("")));
                    valgudang = String.valueOf(joindata.get(""));
                    pane.edketerangan.setText(String.valueOf(joindata.get("")));
                    pane.edsalesman.setText(String.valueOf(joindata.get("")));
                    valsalesman = String.valueOf(joindata.get(""));
                    pane.edshipvia.setText(String.valueOf(joindata.get("")));
                    valshipvia = String.valueOf(joindata.get(""));
                    pane.edtop.setText(String.valueOf(joindata.get("")));
                    valtop = String.valueOf(joindata.get(""));
                    pane.eduser_input.setText(String.valueOf(joindata.get("")));
                    pane.lsubtotal.setText(String.valueOf(joindata.get("")));
                    pane.edbiayalain.setText(String.valueOf(joindata.get("")));
                    pane.eddiskon1.setText(String.valueOf(joindata.get("diskon_persen")));
                    pane.eddiskon2.setText(String.valueOf(joindata.get("diskon_nominal")));
                    pane.ltotal_pajak.setText(String.valueOf(joindata.get("total_pajak")));
                    pane.eduang_muka.setText(String.valueOf(joindata.get("total_uang_muka")));
                    pane.ltotal_pembelian.setText(String.valueOf(joindata.get("total_pembelian")));
                }
                dtmtabeldata.addColumn("Kode Barang");
                dtmtabeldata.addColumn("Nama Barang");
                dtmtabeldata.addColumn("Jumlah");
                dtmtabeldata.addColumn("Satuan");
                dtmtabeldata.addColumn("Harga Beli");
                dtmtabeldata.addColumn("Harga Jual");
                dtmtabeldata.addColumn("Diskon Persen");
                dtmtabeldata.addColumn("Diskon Nominal");
                dtmtabeldata.addColumn("Pajak");
                dtmtabeldata.addColumn("Gudang");
                dtmtabeldata.addColumn("Keterangan");
                dtmtabeldata.addColumn("Total");
                Object objtabeldata = jsonobjdata.get("datamultiharga");
                System.out.println(objtabeldata);
                JSONArray jatabledata = (JSONArray) objtabeldata;
                for (int i = 0; i < jatabledata.size(); i++) {
                    JSONObject jointabeldata = (JSONObject) jatabledata.get(i);
                    String id_barang = String.valueOf(jointabeldata.get("id_barang"));
                    String kode_barang = String.valueOf(jointabeldata.get("kode_barang"));
                    String nama_barang = String.valueOf(jointabeldata.get("nama_barang"));
                    String jumlah = String.valueOf(jointabeldata.get("jumlah"));
                    String id_satuan = String.valueOf(jointabeldata.get("id_satuan"));
                    String nama_satuan = String.valueOf(jointabeldata.get("nama_satuan"));
                    String isi_satuan = String.valueOf(jointabeldata.get("isi_satuan"));
                    String harga_beli = String.valueOf(jointabeldata.get("harga_beli"));
                    String harga_jual = String.valueOf(jointabeldata.get("harga_jual"));
                    String diskon_persen = String.valueOf(jointabeldata.get("diskon_persen"));
                    String diskon_nominal = String.valueOf(jointabeldata.get("diskon_nominal"));
                    String id_pajak = String.valueOf(jointabeldata.get("id_pajak"));
                    String nama_pajak = String.valueOf(jointabeldata.get("nama_pajak"));
                    String nilai_pajak = String.valueOf(jointabeldata.get("nilai_pajak"));
                    String id_gudang = String.valueOf(jointabeldata.get("id_gudang"));
                    String nama_gudang = String.valueOf(jointabeldata.get("nama_gudang"));
                    String keterangan = String.valueOf(jointabeldata.get("keterangan"));
                    String total = String.valueOf(jointabeldata.get("total"));
                    tabeldatalist.add(new Entitytabledata(id_barang, kode_barang, nama_barang, jumlah, id_satuan,
                            nama_satuan, isi_satuan, harga_beli, harga_jual, diskon_persen, diskon_nominal, id_pajak, nama_pajak,
                            nilai_pajak, id_gudang, nama_gudang, keterangan, total));

                }
                for (int i = 0; i < tabeldatalist.size(); i++) {
                    rowtabledata[0] = tabeldatalist.get(i).getKode_barang();
                    rowtabledata[1] = tabeldatalist.get(i).getNama_barang();
                    rowtabledata[2] = tabeldatalist.get(i).getJumlah();
                    rowtabledata[3] = tabeldatalist.get(i).getNama_satuan();
                    rowtabledata[4] = tabeldatalist.get(i).getDiskon_persen();
                    rowtabledata[5] = tabeldatalist.get(i).getDiskon_nominal();
                    rowtabledata[6] = tabeldatalist.get(i).getNama_pajak();
                    rowtabledata[7] = tabeldatalist.get(i).getNama_gudang();
                    rowtabledata[8] = tabeldatalist.get(i).getKeterangan();
                    rowtabledata[9] = tabeldatalist.get(i).getTotal();
                    dtmtabeldata.addRow(rowtabledata);
                }

                if (valcheck == 0) {
                    hidetable(7);
                    showtable(6);
                } else {
                    hidetable(6);
                    showtable(7);
                }

                pane.tabledata.setModel(dtmtabeldata);

            }

        } catch (ParseException ex) {
            Logger.getLogger(DaftarfakturpembelianinputController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /*private void simpandata() {
        pane.bsimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Staticvar.isupdate = true;
                if (id.equals("")) {
                    String data = String.format("data=kode='%s'::"
                            + "nama='%s'::"
                            + "id_kelompok='%s'::"
                            + "id_satuan='%s'::"
                            + "id_gudang='%s'::"
                            + "id_dept='%s'::"
                            + "metode_hpp='%s'::"
                            + "stok_minimum='%s'::"
                            + "harga_beli='%s'::"
                            + "harga_jual='%s'::"
                            + "harga_master='%s'::"
                            + "id_pajak_beli='%s'::"
                            + "id_pajak_jual='%s'::"
                            + "id_currency='%s'::"
                            + "gambar='%s'::"
                            + "isaktif='%s'::"
                            + "id_lokasi='%s'::"
                            + "id_merek='%s'::"
                            + "id_supplier='%s'::"
                            + "keterangan='%s'::"
                            + "ishargajualdarigol='%s'::"
                            + "ishargajualpersen='%s'::"
                            + "ishppsamadenganhargajual='%s'::"
                            + "id_service='%s'::"
                            + "hargajual_berdasar='%s'&" + kirimtextsatuan(0),
                            pane.edkode_persediaan.getText(),
                            pane.ednama_persediaan.getText(),
                            valkelompok,
                            valsatuan,
                            valgudang,
                            valdepartment,
                            metodehpp,
                            pane.edstock_minimal.getText(),
                            pane.edharga_beli_akhir.getText(),
                            pane.edharga_jual.getText(),
                            pane.edharga_master.getText(),
                            valpajakpembelian,
                            valpajakpenjualan,
                            "1-1",
                            "",
                            ckval,
                            vallokasi,
                            valmerek,
                            valsupplier,
                            pane.edketerangan_persediaan.getText(),
                            "0",
                            "0",
                            "0",
                            valservice,
                            "1");
                    ch.insertdata("dm/insertpersediaan", data);
                    if (!Staticvar.getresult.equals("berhasil")) {
                        JDialog jd = new JDialog(new Mainmenu());
                        Errorpanel ep = new Errorpanel();
                        ep.ederror.setText(Staticvar.getresult);
                        jd.add(ep);
                        jd.pack();
                        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        jd.setLocationRelativeTo(null);
                        jd.setVisible(true);
                        jd.toFront();
                    } else {
                        JDialog jd = (JDialog) pane.getRootPane().getParent();
                        jd.dispose();
                    }
                } else {
                    String data = String.format("data=kode='%s'::"
                            + "nama='%s'::"
                            + "id_kelompok='%s'::"
                            + "id_satuan='%s'::"
                            + "id_gudang='%s'::"
                            + "id_dept='%s'::"
                            + "metode_hpp='%s'::"
                            + "stok_minimum='%s'::"
                            + "harga_beli='%s'::"
                            + "harga_jual='%s'::"
                            + "harga_master='%s'::"
                            + "id_pajak_beli='%s'::"
                            + "id_pajak_jual='%s'::"
                            + "gambar='%s'::"
                            + "isaktif='%s'::"
                            + "id_lokasi='%s'::"
                            + "id_merek='%s'::"
                            + "id_supplier='%s'::"
                            + "keterangan='%s'::"
                            + "ishargajualdarigol='%s'::"
                            + "ishargajualpersen='%s'::"
                            + "ishppsamadenganhargajual='%s'::"
                            + "id_service='%s'::"
                            + "hargajual_berdasar='%s'&" + kirimtextsatuan(1) + "&" + kirimtextharga(1) + "&" + kirimtextlokasi(1),
                            pane.edkode_persediaan.getText(),
                            pane.ednama_persediaan.getText(),
                            valkelompok,
                            valsatuan,
                            valgudang,
                            valdepartment,
                            metodehpp,
                            pane.edstock_minimal.getText(),
                            pane.edharga_beli_akhir.getText(),
                            pane.edharga_jual.getText(),
                            pane.edharga_master.getText(),
                            valpajakpembelian,
                            valpajakpenjualan,
                            "",
                            ckval,
                            vallokasi,
                            valmerek,
                            valsupplier,
                            pane.edketerangan_persediaan.getText(),
                            "0", "0", "0",
                            valservice,
                            "1");
                    System.out.println(data);
                    ch.updatedata("dm/updatepersediaan", data, id);
                    if (!Staticvar.getresult.equals("berhasil")) {
                        JDialog jd = new JDialog(new Mainmenu());
                        Errorpanel ep = new Errorpanel();
                        ep.ederror.setText(Staticvar.getresult);
                        jd.add(ep);
                        jd.pack();
                        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        jd.setLocationRelativeTo(null);
                        jd.setVisible(true);
                        jd.toFront();
                    } else {
                        JDialog jd = (JDialog) pane.getRootPane().getParent();
                        jd.dispose();
                    }
                }
            }
        });
    }*/
 /*private String kirimtextsatuan(int tipe,String id_inv) {
     /*datapembeliandetail : id_inv, qty, harga, id_satuan, diskon_persen, diskon_nominal, id_pajak, id_gudang,
     id_satuan_pengali, qty_satuan_pengali, keterangan, akun
        StringBuilder sb = new StringBuilder();
        sb.append("datamultisatuan=");
        for (int i = 0; i < tabeldatalist.size(); i++) {
            if (tipe == 0) {
                sb.append("id_inv=" + "'" + id_inv+ "'" + "::"
                        + "id_barang=" + "'" + tabeldatalist.get(i).getId_barang() + "'" + "::"
                        + "qty=" + "'" + tabeldatalist.get(i).getJumlah() + "'" + "::"
                        + "harga=" + "'" + tabeldatalist.get(i).getH+ "'");
                sb.append("--");
            } else {
                sb.append("id_inv=" + "'" + id + "'" + "::"
                        + "id_satuan=" + "'" + multisatuanlist.get(i).getId_satuan() + "'" + "::"
                        + "barcode=" + "'" + multisatuanlist.get(i).getBarcode() + "'" + "::"
                        + "id_satuan_pengali=" + "'" + multisatuanlist.get(i).getId_satuan_pengali() + "'" + "::"
                        + "qty_satuan_pengali=" + "'" + multisatuanlist.get(i).getQty_satuan_pengali() + "'");
                sb.append("--");
            }
        }
        return sb.toString().substring(0, sb.toString().length() - 2);
    }*/
    private void hapusbaris() {
        pane.bhapus_baris.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = pane.tabledata.getSelectedRow();
                Runnable rn = new Runnable() {
                    @Override
                    public void run() {
                        tabeldatalist.remove(row);
                        dtmtabeldata.removeRow(row);
                        pane.tabledata.repaint();
                    }
                };
                SwingUtilities.invokeLater(rn);
            }
        });
    }

    private void carisuplier() {
        pane.bcari_supplier.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("nama", "popupdaftarnama?tipe=1", "Daftar Supplier"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valsupplier = Staticvar.resid;
            pane.edsupplier.setText(Staticvar.reslabel);
        });

    }

    private void carisalesman() {
        pane.bcari_salesman.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("nama", "popupdaftarnama?tipe=2", "Daftar Karyawan"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valsalesman = Staticvar.resid;
            pane.edsalesman.setText(Staticvar.reslabel);
        });

    }

    private void carigudang() {
        pane.bcari_gudang.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("gudang", "popupdaftargudang", "Daftar Gudang"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valgudang = Staticvar.resid;
            pane.edgudang.setText(Staticvar.reslabel);
        });

    }

    private void caridepartment() {
        pane.bcari_dept.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("department", "popupdaftardept", "Daftar Department"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valdept = Staticvar.resid;
            pane.eddept.setText(Staticvar.reslabel);
        });

    }

    private void carishipvia() {
        pane.bcarishipvia.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("pengantaran", "popupdaftarpengantaran", "Daftar Kurir"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valshipvia = Staticvar.resid;
            pane.edshipvia.setText(Staticvar.reslabel);
        });

    }

    private void caritop() {
        pane.bcaritop.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("top", "popupdaftartop", "Daftar Term Of Payment"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valtop = Staticvar.resid;
            pane.edtop.setText(Staticvar.reslabel);
        });

    }

    private void addtotable() {

        pane.tabledata.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                col = e.getColumn();
            }

        });

        KeyAdapter keyatable = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int row = pane.tabledata.getSelectedRow();
                if (e.getKeyCode() == KeyEvent.VK_F3) {
                    tabeldatalist.add(new Entitytabledata("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
                    dtmtabeldata.addRow(rowtabledata);
                    pane.tabledata.requestFocus();
                    pane.tabledata.changeSelection(row + 1, 0, false, false);
                    if (pane.tabledata.editCellAt(row + 1, 0)) {
                        Component editor = pane.tabledata.getEditorComponent();
                        editor.requestFocusInWindow();
                    }
                } else {
                    if (col == 0) {
                        JDialog jd = new JDialog(new Mainmenu());
                        jd.add(new Popupcari("persediaan", "popupdaftarpersediaan", "Daftar Persediaan"));
                        jd.pack();
                        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        jd.setLocationRelativeTo(null);
                        jd.setVisible(true);
                        jd.toFront();
                        try {
                            JSONParser jpdata = new JSONParser();
                            String param = String.format("id=%s", Staticvar.resid);
                            Object objdataraw = jpdata.parse(ch.getdatadetails("dm/datapersediaan", param));
                            JSONObject jodataraw = (JSONObject) objdataraw;
                            Object objdata = jodataraw.get("data");
                            JSONArray jadata = (JSONArray) objdata;
                            for (int i = 0; i < jadata.size(); i++) {
                                JSONObject joindata = (JSONObject) jadata.get(i);
                                tabeldatalist.get(row).setId_barang(Staticvar.resid);
                                pane.tabledata.setValueAt(String.valueOf(joindata.get("kode")), row, 0);
                                pane.tabledata.setValueAt(String.valueOf(joindata.get("nama")), row, 1);
                                tabeldatalist.get(row).setJumlah("1");
                                pane.tabledata.setValueAt("0", row, 2);
                                tabeldatalist.get(row).setId_satuan(String.valueOf(joindata.get("id_satuan")));
                                pane.tabledata.setValueAt(String.valueOf(joindata.get("nama_satuan")), row, 3);
                                tabeldatalist.get(row).setIsi_satuan("1");
                                tabeldatalist.get(row).setHarga_beli(String.valueOf(joindata.get("harga_beli")));
                                pane.tabledata.setValueAt(String.valueOf(joindata.get("harga_beli")), row, 4);
                                tabeldatalist.get(row).setHarga_jual(String.valueOf(joindata.get("harga_jual")));
                                pane.tabledata.setValueAt(String.valueOf(joindata.get("harga_jual")), row, 5);
                                pane.tabledata.setValueAt("0", row, 6);
                                pane.tabledata.setValueAt("0", row, 7);
                                tabeldatalist.get(row).setDiskon_persen("0");
                                tabeldatalist.get(row).setDiskon_nominal("0");
                                pane.tabledata.setValueAt(String.valueOf(joindata.get("nama_pajak_beli")), row, 8);
                                tabeldatalist.get(row).setId_pajak(String.valueOf(joindata.get("id_pajak_beli")));
                                tabeldatalist.get(row).setNilai_pajak(String.valueOf(joindata.get("persen_pajak_beli")));
                                tabeldatalist.get(row).setId_gudang(valgudang);
                                pane.tabledata.setValueAt(pane.edgudang.getText(), row, 9);
                                tabeldatalist.get(row).setKeterangan("");
                                pane.tabledata.setValueAt("", row, 10);
                                tabeldatalist.get(row).setTotal("0");
                                pane.tabledata.setValueAt("0", row, 11);

                            }
                            kalkulasitotal();
                            pane.tabledata.requestFocus();
                            pane.tabledata.changeSelection(row, 2, false, false);

                        } catch (ParseException ex) {
                            Logger.getLogger(DaftarreturpembelianinputController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (col == 2) {
                        if (String.valueOf(pane.tabledata.getValueAt(row, 2)).equals("")) {
                            tabeldatalist.get(row).setJumlah("0");
                            pane.tabledata.setValueAt("0", row, 2);
                        } else {
                            tabeldatalist.get(row).setJumlah(String.valueOf(pane.tabledata.getValueAt(row, 2)));
                        }
                        kalkulasitotalperrow(row);
                        kalkulasitotal();
                        addautorow(row);

                    } else if (col == 4) {
                        if (String.valueOf(pane.tabledata.getValueAt(row, 4)).equals("")) {
                            tabeldatalist.get(row).setHarga_beli("0");
                            pane.tabledata.setValueAt("0", row, 4);
                        } else {
                            tabeldatalist.get(row).setHarga_beli(String.valueOf(pane.tabledata.getValueAt(row, 4)));
                        }
                        kalkulasitotalperrow(row);
                        kalkulasitotal();
                        addautorow(row);
                    } else if (col == 6) {
                        if (String.valueOf(pane.tabledata.getValueAt(row, 6)).equals("")) {
                            tabeldatalist.get(row).setDiskon_persen("0");
                            pane.tabledata.setValueAt("0", row, 6);
                        } else {
                            tabeldatalist.get(row).setDiskon_persen(String.valueOf(pane.tabledata.getValueAt(row, 6)));
                        }
                        kalkulasitotalperrowdiskon(row);
                        kalkulasitotal();
                        addautorow(row);

                    } else if (col == 7) {
                        if (String.valueOf(pane.tabledata.getValueAt(row, 7)).equals("")) {
                            tabeldatalist.get(row).setDiskon_nominal("0");
                            pane.tabledata.setValueAt("0", row, 7);
                        } else {
                            tabeldatalist.get(row).setDiskon_nominal(String.valueOf(pane.tabledata.getValueAt(row, 7)));
                        }
                        kalkulasitotalperrow(row);
                        kalkulasitotal();
                        addautorow(row);
                    }

                }

            }

        };
        pane.tabledata.addKeyListener(keyatable);

        pane.tabledata.getInputMap().put(KeyStroke.getKeyStroke("DELETE"), "hapus");
        pane.tabledata.getActionMap().put("hapus", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pane.bhapus_baris.doClick();
            }
        });

        pane.tabledata.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enter");
        pane.tabledata.getActionMap().put("enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = pane.tabledata.getSelectedRow();
                int col = pane.tabledata.getSelectedColumn();
                if (pane.tabledata.editCellAt(row, col)) {
                    Component editor = pane.tabledata.getEditorComponent();
                    editor.requestFocusInWindow();
                }
                if (col == 3) {
                    JDialog jd = new JDialog(new Mainmenu());
                    jd.add(new Popupcari("satuanperbarang",
                            String.format("popupdaftarsatuanperbarang?id_inv=%s",
                                    tabeldatalist.get(row).getId_barang()),
                            "Daftar Satuan Perbarang"));
                    jd.pack();
                    jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                    jd.setLocationRelativeTo(null);
                    jd.setVisible(true);
                    jd.toFront();
                    tabeldatalist.get(row).setId_satuan(Staticvar.resid);
                    pane.tabledata.setValueAt(Staticvar.reslabel, row, 3);
                    tabeldatalist.get(row).setIsi_satuan(Staticvar.resvalueextended);
                    dtmtabeldata.fireTableCellUpdated(row, 3);
                    kalkulasitotalperrow(row);
                    kalkulasitotal();
                } else if (col == 8) {
                    JDialog jd = new JDialog(new Mainmenu());
                    jd.add(new Popupcari("pajak", "popupdaftarpajak", "Daftar Pajak"));
                    jd.pack();
                    jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                    jd.setLocationRelativeTo(null);
                    jd.setVisible(true);
                    jd.toFront();
                    tabeldatalist.get(row).setId_pajak(Staticvar.resid);
                    tabeldatalist.get(row).setNilai_pajak(Staticvar.resvalueextended);
                    pane.tabledata.setValueAt(Staticvar.reslabel, row, 8);
                    dtmtabeldata.fireTableCellUpdated(row, 8);
                    kalkulasitotal();
                } else if (col == 9) {
                    JDialog jd = new JDialog(new Mainmenu());
                    jd.add(new Popupcari("gudang", "popupdaftargudang", "Daftar Gudang"));
                    jd.pack();
                    jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                    jd.setLocationRelativeTo(null);
                    jd.setVisible(true);
                    jd.toFront();
                    tabeldatalist.get(row).setId_gudang(Staticvar.resid);
                    pane.tabledata.setValueAt(Staticvar.reslabel, row, 9);
                    dtmtabeldata.fireTableCellUpdated(row, 9);
                }
            }
        });

        pane.tabledata.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "right");
        pane.tabledata.getActionMap().put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = pane.tabledata.getSelectedRow();
                int col = pane.tabledata.getSelectedColumn();
                if (col == 11) {
                    if (row < pane.tabledata.getRowCount() - 1) {
                        pane.tabledata.requestFocus();
                        pane.tabledata.changeSelection(row + 1, 0, false, false);
                    } else {
                        pane.tabledata.requestFocus();
                        pane.tabledata.changeSelection(row, 0, false, false);
                    }
                } else {
                    if (pane.ckdiskon.isSelected()) {
                        if (col == 6) {
                            pane.tabledata.requestFocus();
                            pane.tabledata.changeSelection(row, 8, false, false);
                        } else {
                            pane.tabledata.requestFocus();
                            pane.tabledata.changeSelection(row, col + 1, false, false);
                        }
                    } else {
                        if (col == 5) {
                            pane.tabledata.requestFocus();
                            pane.tabledata.changeSelection(row, 7, false, false);
                        } else {
                            pane.tabledata.requestFocus();
                            pane.tabledata.changeSelection(row, col + 1, false, false);
                        }
                    }
                }
            }
        });

        pane.tabledata.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "left");
        pane.tabledata.getActionMap().put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = pane.tabledata.getSelectedRow();
                int col = pane.tabledata.getSelectedColumn();
                if (col == 0) {
                    if (row <= 0) {
                        pane.tabledata.requestFocus();
                        pane.tabledata.changeSelection(row, 0, false, false);
                    } else {
                        pane.tabledata.requestFocus();
                        pane.tabledata.changeSelection(row - 1, 11, false, false);
                    }
                } else {
                    if (pane.ckdiskon.isSelected()) {
                        if (col == 8) {
                            pane.tabledata.requestFocus();
                            pane.tabledata.changeSelection(row, 6, false, false);
                        } else {
                            pane.tabledata.requestFocus();
                            pane.tabledata.changeSelection(row, col - 1, false, false);
                        }
                    } else {
                        if (col == 7) {
                            pane.tabledata.requestFocus();
                            pane.tabledata.changeSelection(row, 5, false, false);
                        } else {
                            pane.tabledata.requestFocus();
                            pane.tabledata.changeSelection(row, col - 1, false, false);
                        }
                    }

                }
            }
        });

        pane.tabledata.getInputMap().put(KeyStroke.getKeyStroke("UP"), "up");
        pane.tabledata.getActionMap().put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = pane.tabledata.getSelectedRow();
                int col = pane.tabledata.getSelectedColumn();
                if (row == 0) {
                    pane.tabledata.requestFocus();
                    pane.tabledata.changeSelection(row, col, false, false);
                } else {
                    pane.tabledata.requestFocus();
                    pane.tabledata.changeSelection(row - 1, col, false, false);
                }
            }
        });

    }

    private void addautorow(int row) {
        int lastrow = pane.tabledata.getRowCount() - 1;
        if (row == lastrow) {
            tabeldatalist.add(new Entitytabledata("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
            dtmtabeldata.addRow(rowtabledata);
            pane.tabledata.requestFocus();
            pane.tabledata.changeSelection(row + 1, 0, false, false);
            if (pane.tabledata.editCellAt(row + 1, 0)) {
                Component editor = pane.tabledata.getEditorComponent();
                editor.requestFocusInWindow();
            }

        }
    }

    private void hidetable(int index) {
        TableColumn col = pane.tabledata.getColumnModel().getColumn(index);
        col.setMinWidth(0);
        col.setMaxWidth(0);
        col.setWidth(0);
        col.setPreferredWidth(0);
    }

    private void showtable(int index) {
        TableColumn col = pane.tabledata.getColumnModel().getColumn(index);
        col.setMinWidth(100);
        col.setMaxWidth(100);
        col.setWidth(100);
        col.setPreferredWidth(100);
    }

    private void kalkulasi() {
        KeyAdapter keadbiaya = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                char cr = e.getKeyChar();
                if (!Character.isLetter(cr)) {
                    double biayalain = 0;
                    if (!pane.edbiayalain.getText().equals("")) {
                        biayalain = Double.parseDouble(pane.edbiayalain.getText());
                    } else {
                        biayalain = 0;
                    }
                    double totaldenganbiayalain = total_pembelian_all + biayalain;
                    pane.ltotal_pembelian.setText(nf.format(totaldenganbiayalain));
                } else {
                    JOptionPane.showMessageDialog(null, "Hanya memperbolehkan angka");
                    pane.edbiayalain.setText("");
                }
            }

        };

        KeyAdapter keaddiskonpersen = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                char cr = e.getKeyChar();
                if (!Character.isLetter(cr)) {
                    double diskon_persen = 0;
                    if (!pane.eddiskon1.getText().equals("")) {
                        diskon_persen = Double.parseDouble(pane.eddiskon1.getText());
                    } else {
                        diskon_persen = 0;
                    }

                    double biayalain = 0;
                    if (!pane.edbiayalain.getText().equals("")) {
                        biayalain = Double.parseDouble(pane.edbiayalain.getText());
                    } else {
                        biayalain = 0;
                    }

                    double totaldiskon = (total_pembelian_all + biayalain) * (diskon_persen / 100);
                    pane.eddiskon2.setText(nf.format(totaldiskon));
                    double totaldenganbiayalaintambahdiskon = (total_pembelian_all + biayalain) - totaldiskon;
                    pane.ltotal_pembelian.setText(nf.format(totaldenganbiayalaintambahdiskon));
                } else {
                    JOptionPane.showMessageDialog(null, "Hanya memperbolehkan angka");
                    pane.edbiayalain.setText("");
                }
            }

        };

        KeyAdapter keaddiskonnominal = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                char cr = e.getKeyChar();
                if (!Character.isLetter(cr)) {
                    double diskon_nominal = 0;
                    if (!pane.eddiskon2.getText().equals("")) {
                        diskon_nominal = Double.parseDouble(pane.eddiskon2.getText());
                    } else {
                        diskon_nominal = 0;
                    }

                    double biayalain = 0;
                    if (!pane.edbiayalain.getText().equals("")) {
                        biayalain = Double.parseDouble(pane.edbiayalain.getText());
                    } else {
                        biayalain = 0;
                    }

                    double totaldiskonpersen = (diskon_nominal / (total_pembelian_all + biayalain)) * 100;
                    pane.eddiskon1.setText(nf.format(totaldiskonpersen));
                    double totaldenganbiayalaintambahdiskon = (total_pembelian_all + biayalain) - diskon_nominal;
                    pane.ltotal_pembelian.setText(nf.format(totaldenganbiayalaintambahdiskon));
                } else {
                    JOptionPane.showMessageDialog(null, "Hanya memperbolehkan angka");
                    pane.edbiayalain.setText("");
                }
            }

        };

        pane.eddiskon1.addKeyListener(keaddiskonpersen);
        pane.edbiayalain.addKeyListener(keadbiaya);
        pane.eddiskon2.addKeyListener(keaddiskonnominal);
    }

    private void kalkulasitotal() {
        int jumlah_row = pane.tabledata.getRowCount();
        total_pembelian_all = 0;
        for (int i = 0; i < jumlah_row; i++) {
            double total_beli_masing = Double.parseDouble(emptycellcheck(i, 11));
            total_pembelian_all = total_pembelian_all + total_beli_masing;
        }

        total_pajak = 0;
        for (int i = 0; i < jumlah_row; i++) {
            double total_pajak_masing = Double.parseDouble(emptycellcheck(i, 11)) * (Oneforallfunc.doubleparsing(tabeldatalist.get(i).getNilai_pajak()) / 100);
            total_pajak = total_pajak + total_pajak_masing;
        }
        pane.ltotal_pajak.setText(nf.format(total_pajak));

        pane.lsubtotal.setText(nf.format(total_pembelian_all));
        double diskon_persen = 0;
        if (!pane.eddiskon1.getText().equals("")) {
            diskon_persen = Double.parseDouble(pane.eddiskon1.getText());
        } else {
            diskon_persen = 0;
        }

        double biayalain = 0;
        if (!pane.edbiayalain.getText().equals("")) {
            biayalain = Double.parseDouble(pane.edbiayalain.getText());
        } else {
            biayalain = 0;
        }

        double totaldiskon = (total_pembelian_all + biayalain) * (diskon_persen / 100);
        pane.eddiskon2.setText(nf.format(totaldiskon));
        double totaldenganbiayalaintambahdiskon = (total_pembelian_all + biayalain) - totaldiskon;
        pane.ltotal_pembelian.setText(nf.format(totaldenganbiayalaintambahdiskon + total_pajak));
    }

    private void kalkulasitotalperrow(int row) {
        if (pane.ckdiskon.isSelected() == true) {
            int qty = Integer.parseInt(emptycellcheck(row, 2)) * Integer.parseInt(tabeldatalist.get(row).getIsi_satuan());
            double harga = Double.parseDouble(emptycellcheck(row, 4));
            double diskon = Double.parseDouble(emptycellcheck(row, 6));
            double total = qty * (harga - (diskon / 100 * harga));
            tabeldatalist.get(row).setTotal(String.valueOf(total));
            pane.tabledata.setValueAt(total, row, 11);
        } else {
            int qty = Integer.parseInt(emptycellcheck(row, 2)) * Integer.parseInt(tabeldatalist.get(row).getIsi_satuan());
            double harga = Double.parseDouble(emptycellcheck(row, 4));
            double diskon = Double.parseDouble(emptycellcheck(row, 7));
            double total = qty * (harga - diskon);
            tabeldatalist.get(row).setTotal(String.valueOf(total));
            pane.tabledata.setValueAt(total, row, 11);
        }
    }

    private void kalkulasitotalperrowdiskon(int row) {
        String isifielddiskon = String.valueOf(pane.tabledata.getValueAt(row, 6));
        if (isifielddiskon.contains("+")) {
            int qty = Integer.parseInt(emptycellcheck(row, 2)) * Integer.parseInt(tabeldatalist.get(row).getIsi_satuan());
            double harga = Double.parseDouble(emptycellcheck(row, 4));
            double total = harga;
            String[] multidiskon = isifielddiskon.split("\\+");
            for (int i = 0; i < multidiskon.length; i++) {
                double diskonper = Double.parseDouble(multidiskon[i]);
                total = (qty * (total - (diskonper / 100 * total)));
            }
            tabeldatalist.get(row).setTotal(String.valueOf(total));
            pane.tabledata.setValueAt(total, row, 11);
        } else {
            int qty = Integer.parseInt(emptycellcheck(row, 2)) * Integer.parseInt(tabeldatalist.get(row).getIsi_satuan());
            double harga = Double.parseDouble(emptycellcheck(row, 4));
            double diskon = Double.parseDouble(emptycellcheck(row, 6));
            double total = qty * (harga - (diskon / 100 * harga));
            tabeldatalist.get(row).setTotal(String.valueOf(total));
            pane.tabledata.setValueAt(total, row, 11);
        }
    }

    private String emptycellcheck(int row, int col) {
        String ret = "";
        String value = String.valueOf(pane.tabledata.getValueAt(row, col));
        try {
            if (value.equals("") || value.equals("null")) {
                ret = "0";
            } else {
                ret = value;
            }
        } catch (NullPointerException e) {
            ret = "0";
        }

        return ret;
    }

    public class Entitytabledata {

        String id_barang, kode_barang, nama_barang, jumlah,
                id_satuan, nama_satuan, isi_satuan, harga_beli, harga_jual, diskon_persen, diskon_nominal,
                id_pajak, nama_pajak, nilai_pajak, id_gudang, nama_gudang, keterangan, total;

        public Entitytabledata(String id_barang, String kode_barang, String nama_barang, String jumlah, String id_satuan, String nama_satuan, String isi_satuan, String harga_beli, String harga_jual, String diskon_persen, String diskon_nominal, String id_pajak, String nama_pajak, String nilai_pajak, String id_gudang, String nama_gudang, String keterangan, String total) {
            this.id_barang = id_barang;
            this.kode_barang = kode_barang;
            this.nama_barang = nama_barang;
            this.jumlah = jumlah;
            this.id_satuan = id_satuan;
            this.nama_satuan = nama_satuan;
            this.isi_satuan = isi_satuan;
            this.harga_beli = harga_beli;
            this.harga_jual = harga_jual;
            this.diskon_persen = diskon_persen;
            this.diskon_nominal = diskon_nominal;
            this.id_pajak = id_pajak;
            this.nama_pajak = nama_pajak;
            this.nilai_pajak = nilai_pajak;
            this.id_gudang = id_gudang;
            this.nama_gudang = nama_gudang;
            this.keterangan = keterangan;
            this.total = total;
        }

        public String getId_barang() {
            return id_barang;
        }

        public void setId_barang(String id_barang) {
            this.id_barang = id_barang;
        }

        public String getKode_barang() {
            return kode_barang;
        }

        public void setKode_barang(String kode_barang) {
            this.kode_barang = kode_barang;
        }

        public String getNama_barang() {
            return nama_barang;
        }

        public void setNama_barang(String nama_barang) {
            this.nama_barang = nama_barang;
        }

        public String getJumlah() {
            return jumlah;
        }

        public void setJumlah(String jumlah) {
            this.jumlah = jumlah;
        }

        public String getId_satuan() {
            return id_satuan;
        }

        public void setId_satuan(String id_satuan) {
            this.id_satuan = id_satuan;
        }

        public String getNama_satuan() {
            return nama_satuan;
        }

        public void setNama_satuan(String nama_satuan) {
            this.nama_satuan = nama_satuan;
        }

        public String getIsi_satuan() {
            return isi_satuan;
        }

        public void setIsi_satuan(String isi_satuan) {
            this.isi_satuan = isi_satuan;
        }

        public String getHarga_beli() {
            return harga_beli;
        }

        public void setHarga_beli(String harga_beli) {
            this.harga_beli = harga_beli;
        }

        public String getHarga_jual() {
            return harga_jual;
        }

        public void setHarga_jual(String harga_jual) {
            this.harga_jual = harga_jual;
        }

        public String getDiskon_persen() {
            return diskon_persen;
        }

        public void setDiskon_persen(String diskon_persen) {
            this.diskon_persen = diskon_persen;
        }

        public String getDiskon_nominal() {
            return diskon_nominal;
        }

        public void setDiskon_nominal(String diskon_nominal) {
            this.diskon_nominal = diskon_nominal;
        }

        public String getId_pajak() {
            return id_pajak;
        }

        public void setId_pajak(String id_pajak) {
            this.id_pajak = id_pajak;
        }

        public String getNama_pajak() {
            return nama_pajak;
        }

        public void setNama_pajak(String nama_pajak) {
            this.nama_pajak = nama_pajak;
        }

        public String getNilai_pajak() {
            return nilai_pajak;
        }

        public void setNilai_pajak(String nilai_pajak) {
            this.nilai_pajak = nilai_pajak;
        }

        public String getId_gudang() {
            return id_gudang;
        }

        public void setId_gudang(String id_gudang) {
            this.id_gudang = id_gudang;
        }

        public String getNama_gudang() {
            return nama_gudang;
        }

        public void setNama_gudang(String nama_gudang) {
            this.nama_gudang = nama_gudang;
        }

        public String getKeterangan() {
            return keterangan;
        }

        public void setKeterangan(String keterangan) {
            this.keterangan = keterangan;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

    }
}
