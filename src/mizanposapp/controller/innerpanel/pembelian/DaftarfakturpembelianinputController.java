/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.pembelian;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Globalsession;
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
    DefaultTableModel dtmtabeldata = new DefaultTableModel();
    Object[] rowtabledata = new Object[11];
    ArrayList<Entitytabledata> tabeldatalist = new ArrayList<>();
    int col = 0;
    int row = 0;
    NumberFormat nf = NumberFormat.getInstance();

    public DaftarfakturpembelianinputController(Daftarfakturpembelian_input_panel pane) {
        this.pane = pane;
        carisuplier();
        carigudang();
        caridepartment();
        carisalesman();
        carishipvia();
        caritop();
        loaddata();
        addtotable();
    }

    private void loaddata() {
        try {
            id = Staticvar.ids;
            if (id.equals("")) {

                pane.ckdiskon.setSelected(true);
                pane.cmb_tipe_pembelian.setSelectedIndex(0);
                pane.cmb_tipe_bayar.setSelectedIndex(0);
                pane.dtanggal.setDate(new Date());
                pane.dtanggal_info.setDate(new Date());
                pane.edsupplier.setText("");
                valsupplier = "";
                pane.edno_tarnsaksi.setText("");
                pane.eddept.setText("");
                valdept = "";
                pane.edbarcode.setText("");
                pane.edqty.setText("");
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
                tabeldatalist.add(new Entitytabledata("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
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

                    pane.cmb_tipe_pembelian.setSelectedIndex(0);
                    pane.cmb_tipe_bayar.setSelectedIndex(0);
                    pane.dtanggal.setDate(new Date());
                    pane.dtanggal_info.setDate(new Date());
                    pane.edsupplier.setText(String.valueOf(joindata.get("")));
                    valsupplier = String.valueOf(joindata.get(""));
                    pane.edno_tarnsaksi.setText(String.valueOf(joindata.get("")));
                    pane.eddept.setText(String.valueOf(joindata.get("")));
                    valdept = String.valueOf(joindata.get(""));
                    pane.edbarcode.setText(String.valueOf(joindata.get("")));
                    pane.edqty.setText(String.valueOf(joindata.get("")));
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
                    pane.eddiskon1.setText(String.valueOf(joindata.get("")));
                    pane.eddiskon2.setText(String.valueOf(joindata.get("")));
                    pane.ltotal_pajak.setText(String.valueOf(joindata.get("")));
                    pane.eduang_muka.setText(String.valueOf(joindata.get("")));
                    pane.ltotal_pembelian.setText(String.valueOf(joindata.get("")));
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
                    String harga_beli = String.valueOf(jointabeldata.get("harga_beli"));
                    String harga_jual = String.valueOf(jointabeldata.get("harga_jual"));
                    String diskon_persen = String.valueOf(jointabeldata.get("diskon_persen"));
                    String diskon_nominal = String.valueOf(jointabeldata.get("diskon_nominal"));
                    String id_pajak = String.valueOf(jointabeldata.get("id_pajak"));
                    String nama_pajak = String.valueOf(jointabeldata.get("nama_pajak"));
                    String id_gudang = String.valueOf(jointabeldata.get("id_gudang"));
                    String nama_gudang = String.valueOf(jointabeldata.get("nama_gudang"));
                    String keterangan = String.valueOf(jointabeldata.get("keterangan"));
                    String total = String.valueOf(jointabeldata.get("total"));
                    tabeldatalist.add(new Entitytabledata(id_barang, kode_barang, nama_barang, jumlah, id_satuan,
                            nama_satuan, harga_beli, harga_jual, diskon_persen, diskon_nominal, id_pajak, nama_pajak,
                            id_gudang, nama_gudang, keterangan, total));

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

            pane.ckdiskon.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (pane.ckdiskon.isSelected()) {
                        hidetable(7);
                        showtable(6);
                    } else {
                        hidetable(6);
                        showtable(7);
                    }
                }
            });
        } catch (ParseException ex) {
            Logger.getLogger(DaftarfakturpembelianinputController.class.getName()).log(Level.SEVERE, null, ex);
        }

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
            jd.add(new Popupcari("pengantaran", "popupdaftarpengantaran", "Daftar Kurir Pengantaran"));
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
            public void tableChanged(TableModelEvent e
            ) {
                col = e.getColumn();
            }

        });

        KeyAdapter keyatable = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int row = pane.tabledata.getSelectedRow();
                if (e.getKeyCode() == KeyEvent.VK_F3) {
                    tabeldatalist.add(new Entitytabledata("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
                    dtmtabeldata.addRow(rowtabledata);
                } else if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    tabeldatalist.remove(row);
                    dtmtabeldata.removeRow(row);
                    for (int i = 0; i < 4; i++) {
                        dtmtabeldata.fireTableCellUpdated(row, i);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_F4) {
                    for (int i = 0; i < tabeldatalist.size(); i++) {
                        System.out.println("----------------------------------------");
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
                                pane.tabledata.setValueAt("1", row, 2);
                                tabeldatalist.get(row).setId_satuan(String.valueOf(joindata.get("id_satuan")));
                                pane.tabledata.setValueAt(String.valueOf(joindata.get("nama_satuan")), row, 3);
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
                                tabeldatalist.get(row).setId_gudang(valgudang);
                                pane.tabledata.setValueAt(pane.edgudang.getText(), row, 9);
                                tabeldatalist.get(row).setKeterangan("");
                                pane.tabledata.setValueAt("", row, 10);
                                tabeldatalist.get(row).setTotal(String.valueOf(joindata.get("harga_beli")));
                                pane.tabledata.setValueAt(String.valueOf(joindata.get("harga_beli")), row, 11);

                            }
                        } catch (ParseException ex) {
                            Logger.getLogger(DaftarreturpembelianinputController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (col == 2) {
                        if (pane.ckdiskon.isSelected() == true) {
                            int qty = Integer.parseInt(String.valueOf(pane.tabledata.getValueAt(row, 2)));
                            double harga = Double.parseDouble(String.valueOf(pane.tabledata.getValueAt(row, 4)));
                            double diskon = Double.parseDouble(String.valueOf(pane.tabledata.getValueAt(row, 6)));
                            double total = qty * (harga - (diskon / 100 * harga));
                            pane.tabledata.setValueAt(total, row, 11);
                        } else {
                            int qty = Integer.parseInt(String.valueOf(pane.tabledata.getValueAt(row, 2)));
                            double harga = Double.parseDouble(String.valueOf(pane.tabledata.getValueAt(row, 4)));
                            double diskon = Double.parseDouble(String.valueOf(pane.tabledata.getValueAt(row, 7)));
                            double total = qty * (harga - diskon);
                            pane.tabledata.setValueAt(total, row, 11);
                        }

                    } else if (col == 3) {
                        JDialog jd = new JDialog(new Mainmenu());
                        jd.add(new Popupcari("satuan", "popupdaftarsatuan", "Daftar Satuan"));
                        jd.pack();
                        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        jd.setLocationRelativeTo(null);
                        jd.setVisible(true);
                        jd.toFront();
                        tabeldatalist.get(row).setId_satuan(Staticvar.resid);
                        pane.tabledata.setValueAt(Staticvar.reslabel, row, 3);
                        dtmtabeldata.fireTableCellUpdated(row, 3);
                    } else if (col == 4) {
                        tabeldatalist.get(row).setHarga_beli(String.valueOf(pane.tabledata.getValueAt(row, 4)));
                    } else if (col == 5) {
                        tabeldatalist.get(row).setHarga_jual(String.valueOf(pane.tabledata.getValueAt(row, 5)));
                    } else if (col == 6) {
                        if (pane.ckdiskon.isSelected() == true) {
                            int qty = Integer.parseInt(String.valueOf(pane.tabledata.getValueAt(row, 2)));
                            double harga = Double.parseDouble(String.valueOf(pane.tabledata.getValueAt(row, 4)));
                            double diskon = Double.parseDouble(String.valueOf(pane.tabledata.getValueAt(row, 6)));
                            double total = qty * (harga - (diskon / 100 * harga));
                            pane.tabledata.setValueAt(total, row, 11);
                        } else {
                            int qty = Integer.parseInt(String.valueOf(pane.tabledata.getValueAt(row, 2)));
                            double harga = Double.parseDouble(String.valueOf(pane.tabledata.getValueAt(row, 4)));
                            double diskon = Double.parseDouble(String.valueOf(pane.tabledata.getValueAt(row, 7)));
                            double total = qty * (harga - diskon);
                            pane.tabledata.setValueAt(total, row, 11);
                        }
                    } else if (col == 7) {
                        if (pane.ckdiskon.isSelected() == true) {
                            int qty = Integer.parseInt(String.valueOf(pane.tabledata.getValueAt(row, 2)));
                            double harga = Double.parseDouble(String.valueOf(pane.tabledata.getValueAt(row, 4)));
                            double diskon = Double.parseDouble(String.valueOf(pane.tabledata.getValueAt(row, 6)));
                            double total = qty * (harga - (diskon / 100 * harga));
                            pane.tabledata.setValueAt(total, row, 11);
                        } else {
                            int qty = Integer.parseInt(String.valueOf(pane.tabledata.getValueAt(row, 2)));
                            double harga = Double.parseDouble(String.valueOf(pane.tabledata.getValueAt(row, 4)));
                            double diskon = Double.parseDouble(String.valueOf(pane.tabledata.getValueAt(row, 7)));
                            double total = qty * (harga - diskon);
                            pane.tabledata.setValueAt(total, row, 11);
                        }
                    } else if (col == 8) {

                    } else if (col == 9) {
                        JDialog jd = new JDialog(new Mainmenu());
                        jd.add(new Popupcari("gudang", "popupdaftargudang", "Daftar Gudang"));
                        jd.pack();
                        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        jd.setLocationRelativeTo(null);
                        jd.setVisible(true);
                        jd.toFront();
                        tabeldatalist.get(row).setId_satuan(Staticvar.resid);
                        pane.tabledata.setValueAt(Staticvar.reslabel, row, 9);
                        dtmtabeldata.fireTableCellUpdated(row, 9);
                    }

                    /*String id_satuan_check = multisatuanlist.get(row).getId_satuan();
                    String barcode_check = multisatuanlist.get(row).getBarcode();
                    String qty_check = multisatuanlist.get(row).getQty_satuan_pengali();
                    int jumlah_row = pane.tablemulti_satuan.getRowCount() - 1;
                    String last_id_satuan_check = multisatuanlist.get(jumlah_row).getId_satuan();
                    String last_barcode_check = multisatuanlist.get(jumlah_row).getBarcode();
                    String last_qty_check = multisatuanlist.get(jumlah_row).getQty_satuan_pengali();
                    if (!id_satuan_check.equals("") && !barcode_check.equals("") && !qty_check.equals("")) {
                        if (!last_id_satuan_check.equals("") && !last_barcode_check.equals("") && !last_qty_check.equals("")) {
                            multisatuanlist.add(new DaftarpersediaaninputController.multisatuan("", "", "", "", "", "", "", ""));
                            dtmmultisatuan.addRow(rowmultisatuan);
                        }
                    }*/
                }
            }

        };
        pane.tabledata.addKeyListener(keyatable);
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

    public class Entitytabledata {

        String id_barang, kode_barang, nama_barang, jumlah,
                id_satuan, nama_satuan, harga_beli, harga_jual, diskon_persen, diskon_nominal,
                id_pajak, nama_pajak, id_gudang, nama_gudang, keterangan, total;

        public Entitytabledata(String id_barang, String kode_barang, String nama_barang, String jumlah, String id_satuan, String nama_satuan, String harga_beli, String harga_jual, String diskon_persen, String diskon_nominal, String id_pajak, String nama_pajak, String id_gudang, String nama_gudang, String keterangan, String total) {
            this.id_barang = id_barang;
            this.kode_barang = kode_barang;
            this.nama_barang = nama_barang;
            this.jumlah = jumlah;
            this.id_satuan = id_satuan;
            this.nama_satuan = nama_satuan;
            this.harga_beli = harga_beli;
            this.harga_jual = harga_jual;
            this.diskon_persen = diskon_persen;
            this.diskon_nominal = diskon_nominal;
            this.id_pajak = id_pajak;
            this.nama_pajak = nama_pajak;
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
