/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.pembelian;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Globalsession;
import mizanposapp.helper.Oneforallfunc;
import mizanposapp.helper.Staticvar;
import mizanposapp.helper.Tablestyle;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Errorpanel;
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.pembelian.Daftarfakturpembelian_inner_panel;
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
    DefaultTableModel dtmtabeldata = new DefaultTableModel();
    Object[] rowtabledata = new Object[11];
    ArrayList<Entitytabledata> tabeldatalist = new ArrayList<>();
    int col = 0;
    //int row = 0;
    NumberFormat nf = NumberFormat.getInstance();
    double total_pembelian_all = 0;
    double total_pajak = 0;
    int no_urut = 0;
    JTextField jt2;
    JTextField jt4;
    JTextField jt6;
    JTextField jt7;
    ArrayList<Integer> lsstatus = new ArrayList<>();

    private boolean ischangevalue = false;
    static String oldvalue = "";

    public DaftarfakturpembelianinputController(Daftarfakturpembelian_input_panel pane) {
        this.pane = pane;
        skinning();
        //getkodetransaksi();
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
        batal();
    }

    private void skinning() {
        new Tablestyle(pane.tabledata).applystyleheader();
        DateFormat dtf = DateFormat.getDateInstance(DateFormat.LONG);
        pane.dtanggal.setDateFormat(dtf);
    }

    private void getkodetransaksi() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONParser jpdata = new JSONParser();
                    String param = String.format("id_keltrans=%s", "3");
                    Object ob = jpdata.parse(ch.getdatadetails("getnomortransaksi", param));
                    JSONArray ja = (JSONArray) ob;
                    for (int i = 0; i < ja.size(); i++) {
                        JSONObject jo = (JSONObject) ja.get(i);
                        pane.edno_transaksi.setText(String.valueOf(jo.get("no_transaksi")));
                        no_urut = Oneforallfunc.intparsing(String.valueOf(jo.get("no_urut")));
                    }

                } catch (ParseException ex) {
                    Logger.getLogger(DaftarreturpembelianinputController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    private void customtable() {
        pane.tabledata.setRowSelectionAllowed(false);
        pane.tabledata.setCellSelectionEnabled(true);
        pane.tabledata.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        dtmtabeldata = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 3 || column == 5 || column == 8 || column == 9 || column == 11 ? false : true;
            }

        };

        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (pane.tabledata.isEditing()) {
                    pane.tabledata.getCellEditor().cancelCellEditing();
                }
            }

        };

        pane.addMouseListener(ma);

        TableCellEditor tce = new DefaultCellEditor(new JTextField()) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                delegate.setValue((editorComponent instanceof JTextField) ? null : value);
                return editorComponent;
            }

        };

        pane.tabledata.setDefaultEditor(Object.class, tce);
        String keyholdnumeric[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9",
            "0", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
            "q", "r", "s", "t", "u", "p", "w", "x", "y", "z", "A", "B", "C", "D",
            "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "P", "W", "X", "Y", "Z", "BACK_SPACE", ",", "."};
        for (int i = 0; i < keyholdnumeric.length; i++) {
            pane.tabledata.getInputMap().put(KeyStroke.getKeyStroke(keyholdnumeric[i]), "startEditing");
        }

    }

    private void checkandcombocontrol() {
        pane.ckdiskon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox cb = (JCheckBox) e.getSource();
                int row = pane.tabledata.getSelectedRow();
                /*for (int i = 0; i < pane.tabledata.getRowCount(); i++) {
                    pane.tabledata.setValueAt("0", i, 6);
                    dtmtabeldata.fireTableCellUpdated(i, 6);
                    pane.tabledata.setValueAt("0", i, 7);
                    dtmtabeldata.fireTableCellUpdated(i, 7);
                    kalkulasitotalperrow(row);
                }*/
                kalkulasitotalperrow(row);
                if (cb.isSelected()) {
                    hidetable(7);
                    showtable(6);

                } else {
                    hidetable(6);
                    showtable(7);
                }
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

    private void loadheader() {
        try {
            pane.tabledata.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
            pane.tabledata.setModel(dtmtabeldata);
            TableColumnModel tcm = pane.tabledata.getColumnModel();
            String dataheader = ch.getheaders();
            JSONParser jpheader = new JSONParser();
            Object objheader = jpheader.parse(dataheader);
            JSONObject joheader = (JSONObject) objheader;
            JSONArray jaheader = (JSONArray) joheader.get("inputfakturpembelian");
            //perulangan mengambil header
            for (int i = 0; i < jaheader.size(); i++) {
                JSONObject jodata = (JSONObject) jaheader.get(i);
                JSONArray jaaray = (JSONArray) jodata.get("key");
                dtmtabeldata.addColumn(jaaray.get(1));
            }

            // resize colum
            for (int i = 0; i < jaheader.size(); i++) {
                JSONObject jodata = (JSONObject) jaheader.get(i);
                JSONArray jaaray = (JSONArray) jodata.get("key");
                Double wd = d.getWidth() - 344;
                int setsize = Integer.parseInt(String.valueOf(jaaray.get(3)));
                int wi = (setsize * wd.intValue()) / 100;
                tcm.getColumn(i).setMinWidth(wi);
                tcm.getColumn(i).setMaxWidth(wi);
            }

            // hidden column
            for (int i = 0; i < jaheader.size(); i++) {
                JSONObject jodata = (JSONObject) jaheader.get(i);
                JSONArray jaaray = (JSONArray) jodata.get("key");
                lsstatus.add(Integer.parseInt(String.valueOf(jaaray.get(3))));
                if (jaaray.get(2).equals("0")) {
                    hidetable(i);
                }
            }

        } catch (ParseException ex) {
            Logger.getLogger(DaftarfakturpembelianinnerController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                pane.edno_transaksi.setText("");
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
                loadheader();
                tabeldatalist.add(new Entitytabledata("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
                dtmtabeldata.addRow(rowtabledata);
                pane.tabledata.setModel(dtmtabeldata);
                if (pane.ckdiskon.isSelected()) {
                    hidetable(7);
                    showtable(6);
                } else {
                    showtable(7);
                    hidetable(6);
                }

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
                    pane.edno_transaksi.setText(String.valueOf(joindata.get("")));
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
        int[] columnrender = {2, 4, 6, 7, 11};
        new Tablestyle(pane.tabledata).applystylerow(columnrender);
    }

    private void simpandata() {
        /*pane.bsimpan.addActionListener(new ActionListener() {
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
        });*/
    }

    private String kirimtextsatuan(int tipe, String id_inv) {
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
        return sb.toString().substring(0, sb.toString().length() - 2);*/
        return "test";
    }

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

    private void batal() {
        pane.bbatal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        String data = String.format("id_keltrans=%s&no_urut=%s", "3", String.valueOf(no_urut));
                        ch.insertdata("insertnomorgagal", data);
                        Daftarfakturpembelian_inner_panel inpane = new Daftarfakturpembelian_inner_panel();
                        Staticvar.pmp.container.removeAll();
                        Staticvar.pmp.container.setLayout(new BorderLayout());
                        Staticvar.pmp.container.add(inpane, BorderLayout.CENTER);
                        Staticvar.pmp.container.revalidate();
                        Staticvar.pmp.container.repaint();
                    }
                });

            }
        });
    }

    private void carisuplier() {
        pane.bcari_supplier.addActionListener((ActionEvent e) -> {
            Staticvar.preid = valsupplier;
            Staticvar.prelabel = pane.edsupplier.getText();
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
            Staticvar.preid = valsalesman;
            Staticvar.prelabel = pane.edsalesman.getText();
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
            Staticvar.preid = valgudang;
            Staticvar.prelabel = pane.edgudang.getText();
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
            Staticvar.preid = valdept;
            Staticvar.prelabel = pane.eddept.getText();
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
            Staticvar.preid = valshipvia;
            Staticvar.prelabel = pane.edshipvia.getText();
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
            Staticvar.preid = valtop;
            Staticvar.prelabel = pane.edtop.getText();
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

        pane.tabledata.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {

                TableModel tm = (TableModel) e.getSource();
                if (e.getType() == TableModelEvent.UPDATE) {
                    if (ischangevalue) {
                        return;
                    }
                    int col = e.getColumn();
                    int row = e.getFirstRow();

                    /*String val = String.valueOf(tm.getValueAt(row, col));
                    if ((val == oldvalue) && (!val.equals("null") || !oldvalue.equals("null"))) {
                        return;
                    }
                    
                    if (val.equals("")) {
                        tm.setValueAt(oldvalue, row, col);
                        oldvalue = "";
                        return;
                    }*/
                    if (col == 0) {
                        ischangevalue = true;
                        Staticvar.preid = tabeldatalist.get(row).getId_barang();
                        String defnilai = "";
                        String cekval = String.valueOf(tm.getValueAt(row, col));
                        if (cekval.equals("null") || cekval.equals("")) {
                            defnilai = "";
                        } else {
                            defnilai = String.valueOf(tm.getValueAt(row, 0));
                        }
                        Staticvar.prelabel = defnilai;
                        Staticvar.sfilter = defnilai;
                        Staticvar.reslabel = defnilai;
                        try {
                            JSONParser jpdata = new JSONParser();
                            String param = String.format("kode=%s", String.valueOf(tm.getValueAt(row, col)));
                            Object objdataraw = jpdata.parse(ch.getdatadetails("dm/datapersediaanbykode", param));
                            JSONObject jodataraw = (JSONObject) objdataraw;
                            Object objdata = jodataraw.get("data");
                            JSONArray jadata = (JSONArray) objdata;
                            if (jadata.size() == 1) {
                                for (int i = 0; i < jadata.size(); i++) {
                                    JSONObject joindata = (JSONObject) jadata.get(i);
                                    tabeldatalist.get(row).setId_barang(Staticvar.resid);
                                    tm.setValueAt(String.valueOf(joindata.get("nama")), row, 1);
                                    tabeldatalist.get(row).setJumlah("1");
                                    tm.setValueAt("0", row, 2);
                                    tabeldatalist.get(row).setId_satuan(String.valueOf(joindata.get("id_satuan")));
                                    tm.setValueAt(String.valueOf(joindata.get("nama_satuan")), row, 3);
                                    tabeldatalist.get(row).setIsi_satuan("1");
                                    tabeldatalist.get(row).setHarga_beli(String.valueOf(joindata.get("harga_beli")));
                                    tm.setValueAt(nf.format(Oneforallfunc.ToDouble(joindata.get("harga_beli"))), row, 4);
                                    tabeldatalist.get(row).setHarga_jual(String.valueOf(joindata.get("harga_jual")));
                                    tm.setValueAt(nf.format(Oneforallfunc.ToDouble(joindata.get("harga_jual"))), row, 5);
                                    tm.setValueAt("0", row, 6);
                                    tm.setValueAt("0", row, 7);
                                    tabeldatalist.get(row).setDiskon_persen("0");
                                    tabeldatalist.get(row).setDiskon_nominal("0");
                                    tm.setValueAt(String.valueOf(joindata.get("nama_pajak_beli")), row, 8);
                                    tabeldatalist.get(row).setId_pajak(String.valueOf(joindata.get("id_pajak_beli")));
                                    tabeldatalist.get(row).setNilai_pajak(String.valueOf(joindata.get("persen_pajak_beli")));
                                    tabeldatalist.get(row).setId_gudang(valgudang);
                                    tm.setValueAt(pane.edgudang.getText(), row, 9);
                                    tabeldatalist.get(row).setKeterangan("");
                                    tm.setValueAt("", row, 10);
                                    tabeldatalist.get(row).setTotal("0");
                                    tm.setValueAt("0", row, 11);
                                }
                                kalkulasitotalperrow(row);
                                pane.tabledata.requestFocus();
                                pane.tabledata.changeSelection(row, 2, false, false);
                            } else {
                                JDialog jd = new JDialog(new Mainmenu());
                                jd.add(new Popupcari("persediaan", "popupdaftarpersediaan", "Daftar Persediaan"));
                                jd.pack();
                                jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                                jd.setLocationRelativeTo(null);
                                jd.setVisible(true);
                                jd.toFront();
                                if (!Staticvar.reslabel.equals("")) {
                                    JSONParser jpdata2 = new JSONParser();
                                    String param2 = String.format("id=%s", Staticvar.resid);
                                    Object objdataraw2 = jpdata2.parse(ch.getdatadetails("dm/datapersediaan", param2));
                                    JSONObject jodataraw2 = (JSONObject) objdataraw2;
                                    Object objdata2 = jodataraw2.get("data");
                                    JSONArray jadata2 = (JSONArray) objdata2;
                                    for (int i = 0; i < jadata2.size(); i++) {
                                        JSONObject joindata2 = (JSONObject) jadata2.get(i);
                                        tabeldatalist.get(row).setId_barang(Staticvar.resid);
                                        tm.setValueAt(String.valueOf(joindata2.get("kode")), row, 0);
                                        tm.setValueAt(String.valueOf(joindata2.get("nama")), row, 1);
                                        tabeldatalist.get(row).setJumlah("0");
                                        tabeldatalist.get(row).setId_satuan(String.valueOf(joindata2.get("id_satuan")));
                                        tm.setValueAt(String.valueOf(joindata2.get("nama_satuan")), row, 3);
                                        tabeldatalist.get(row).setIsi_satuan("1");
                                        tabeldatalist.get(row).setHarga_beli(String.valueOf(joindata2.get("harga_beli")));
                                        tm.setValueAt(nf.format(Oneforallfunc.ToDouble(joindata2.get("harga_beli"))), row, 4);
                                        tabeldatalist.get(row).setHarga_jual(String.valueOf(joindata2.get("harga_jual")));
                                        tm.setValueAt(nf.format(Oneforallfunc.ToDouble(joindata2.get("harga_jual"))), row, 5);
                                        tm.setValueAt("0", row, 6);
                                        tm.setValueAt("0", row, 7);
                                        tabeldatalist.get(row).setDiskon_persen("0");
                                        tabeldatalist.get(row).setDiskon_nominal("0");
                                        tm.setValueAt(String.valueOf(joindata2.get("nama_pajak_beli")), row, 8);
                                        tabeldatalist.get(row).setId_pajak(String.valueOf(joindata2.get("id_pajak_beli")));
                                        tabeldatalist.get(row).setNilai_pajak(String.valueOf(joindata2.get("persen_pajak_beli")));
                                        tabeldatalist.get(row).setId_gudang(valgudang);
                                        tm.setValueAt(pane.edgudang.getText(), row, 9);
                                        tabeldatalist.get(row).setKeterangan("");
                                        tm.setValueAt("", row, 10);
                                        tabeldatalist.get(row).setTotal("0");
                                        tm.setValueAt("0", row, 11);
                                    }
                                    kalkulasitotalperrow(row);
                                    pane.tabledata.requestFocus();
                                    pane.tabledata.changeSelection(row, 2, false, false);
                                }
                            }

                        } catch (ParseException ex) {
                            JDialog jd = new JDialog(new Mainmenu());
                            Errorpanel ep = new Errorpanel();
                            ep.ederror.setText(ex.toString());
                            jd.add(ep);
                            jd.pack();
                            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                            jd.setLocationRelativeTo(null);
                            jd.setVisible(true);
                            jd.toFront();
                            Staticvar.resid = "";
                            Logger.getLogger(DaftarreturpembelianinputController.class.getName()).log(Level.SEVERE, null, ex);

                        } finally {
                            ischangevalue = false;
                        }
                    } else if (col == 2) {
                        try {
                            ischangevalue = true;
                            columnfunction(row, 2, false);
                            nextcolom(2, row);
                        } catch (Exception ex) {
                        } finally {
                            ischangevalue = false;
                        }
                    } else if (col == 4) {
                        try {
                            ischangevalue = true;
                            columnfunction(row, 4, false);
                            nextcolom(4, row);
                        } catch (Exception ex) {
                        } finally {
                            ischangevalue = false;
                        }
                    } else if (col == 6) {
                        try {
                            ischangevalue = true;
                            String valcol = String.valueOf(pane.tabledata.getValueAt(row, 6));
                            if (checkalphabeth(valcol) == false) {
                                columnfunction(row, 6, false);
                                pane.tabledata.requestFocus();
                                pane.tabledata.changeSelection(row, 8, false, false);
                            } else {
                                JDialog jd = new JDialog(new Mainmenu());
                                Errorpanel ep = new Errorpanel();
                                ep.ederror.setText("Isi Harus Angka dan Tidak Boleh Kosong");
                                jd.add(ep);
                                jd.pack();
                                jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                                jd.setLocationRelativeTo(null);
                                jd.setVisible(true);
                                jd.toFront();
                                valcol = valcol.replaceAll("[^0-9.+]", "");
                                pane.tabledata.setValueAt(valcol, row, 6);
                                columnfunction(row, 6, false);
                            }
                        } catch (Exception ex) {
                        } finally {
                            ischangevalue = false;
                        }
                    } else if (col == 7) {
                        try {
                            ischangevalue = true;
                            String valcol = String.valueOf(pane.tabledata.getValueAt(row, 7));
                            if (checknumerik(valcol) == true) {
                                columnfunction(row, 7, false);
                            } else {
                                JDialog jd = new JDialog(new Mainmenu());
                                Errorpanel ep = new Errorpanel();
                                ep.ederror.setText("Isi Harus Angka dan Tidak Boleh Kosong");
                                jd.add(ep);
                                jd.pack();
                                jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                                jd.setLocationRelativeTo(null);
                                jd.setVisible(true);
                                jd.toFront();
                                valcol = valcol.replaceAll("[^0-9]", "");
                                pane.tabledata.setValueAt(valcol, row, 7);
                                columnfunction(row, 7, false);
                            }
                        } catch (Exception ex) {
                        } finally {
                            ischangevalue = false;
                        }
                    }
                    ischangevalue = true;
                    String curval = String.valueOf(tm.getValueAt(row, col));
                    if (curval.equals("0") || curval.equals("") || curval.equals("null")) {
                        tm.setValueAt(oldvalue, row, col);
                        oldvalue = "";
                        if (col == 0) {
                            pane.tabledata.requestFocus();
                            pane.tabledata.changeSelection(row, 0, false, false);
                        }
                    }
                    ischangevalue = false;
                }
            }
        });

        MouseAdapter madap = new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                JTable tb = (JTable) e.getSource();
                int row = tb.rowAtPoint(e.getPoint());
                int col = tb.columnAtPoint(e.getPoint());
                if (tb.isEditing()) {
                    tb.getCellEditor().cancelCellEditing();
                }
                oldvalue = String.valueOf(tb.getValueAt(row, col));
                if (e.getClickCount() == 2) {
                    if (col == 3) {
                        Staticvar.preid = tabeldatalist.get(row).getId_satuan();
                        Staticvar.prelabel = String.valueOf(tb.getValueAt(row, 3));
                        Staticvar.prevalueextended = tabeldatalist.get(row).getIsi_satuan();
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
                        tb.setValueAt(Staticvar.reslabel, row, 3);
                        tabeldatalist.get(row).setIsi_satuan(Staticvar.resvalueextended);
                        dtmtabeldata.fireTableCellUpdated(row, 3);
                        kalkulasitotalperrow(row);
                    } else if (col == 8) {
                        Staticvar.preid = tabeldatalist.get(row).getId_pajak();
                        Staticvar.prelabel = String.valueOf(tb.getValueAt(row, 8));
                        Staticvar.prevalueextended = tabeldatalist.get(row).getNilai_pajak();
                        JDialog jd = new JDialog(new Mainmenu());
                        jd.add(new Popupcari("pajak", "popupdaftarpajak", "Daftar Pajak"));
                        jd.pack();
                        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        jd.setLocationRelativeTo(null);
                        jd.setVisible(true);
                        jd.toFront();
                        tabeldatalist.get(row).setId_pajak(Staticvar.resid);
                        tabeldatalist.get(row).setNilai_pajak(Staticvar.resvalueextended);
                        tb.setValueAt(Staticvar.reslabel, row, 8);
                        dtmtabeldata.fireTableCellUpdated(row, 8);
                        kalkulasitotalperrow(row);
                    } else if (col == 9) {
                        Staticvar.preid = tabeldatalist.get(row).getId_gudang();
                        Staticvar.prelabel = String.valueOf(tb.getValueAt(row, 9));
                        JDialog jd = new JDialog(new Mainmenu());
                        jd.add(new Popupcari("gudang", "popupdaftargudang", "Daftar Gudang"));
                        jd.pack();
                        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        jd.setLocationRelativeTo(null);
                        jd.setVisible(true);
                        jd.toFront();
                        tabeldatalist.get(row).setId_gudang(Staticvar.resid);
                        tb.setValueAt(Staticvar.reslabel, row, 9);
                        dtmtabeldata.fireTableCellUpdated(row, 9);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                JTable tb = (JTable) e.getSource();
                if (tb.isEditing()) {
                    tb.getCellEditor().cancelCellEditing();
                }
            }

        };
        pane.tabledata.addMouseListener(madap);

        pane.tabledata.getInputMap().put(KeyStroke.getKeyStroke("DELETE"), "hapus");
        pane.tabledata.getActionMap().put("hapus", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pane.bhapus_baris.doClick();
            }
        });
        pane.tabledata.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");
        pane.tabledata.getActionMap().put("enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = pane.tabledata.getSelectedRow();
                int col = pane.tabledata.getSelectedColumn();

                oldvalue = String.valueOf(pane.tabledata.getValueAt(row, col));

                if (pane.tabledata.editCellAt(row, col)) {
                    Component editor = pane.tabledata.getEditorComponent();
                    editor.requestFocusInWindow();
                }
                if (col == 3) {
                    Staticvar.preid = tabeldatalist.get(row).getId_satuan();
                    Staticvar.prelabel = String.valueOf(pane.tabledata.getValueAt(row, 3));
                    Staticvar.prevalueextended = tabeldatalist.get(row).getIsi_satuan();
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
                } else if (col == 8) {
                    Staticvar.preid = tabeldatalist.get(row).getId_pajak();
                    Staticvar.prelabel = String.valueOf(pane.tabledata.getValueAt(row, 8));
                    Staticvar.prevalueextended = tabeldatalist.get(row).getNilai_pajak();
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
                    kalkulasitotalperrow(row);
                } else if (col == 9) {
                    Staticvar.preid = tabeldatalist.get(row).getId_gudang();
                    Staticvar.prelabel = String.valueOf(pane.tabledata.getValueAt(row, 9));
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

        pane.tabledata.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
        pane.tabledata.getActionMap().put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = pane.tabledata.getSelectedRow();
                int col = pane.tabledata.getSelectedColumn();
                if (pane.tabledata.isEditing()) {
                    pane.tabledata.getCellEditor().stopCellEditing();
                }

                nextcolom(col, row);

            }
        });

        pane.tabledata.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
        pane.tabledata.getActionMap().put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = pane.tabledata.getSelectedRow();
                int col = pane.tabledata.getSelectedColumn();
                if (pane.tabledata.isEditing()) {
                    pane.tabledata.getCellEditor().stopCellEditing();
                }
                backcolom(col, row);
            }
        });

        pane.tabledata.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");
        pane.tabledata.getActionMap().put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = pane.tabledata.getSelectedRow();
                int col = pane.tabledata.getSelectedColumn();
                if (pane.tabledata.isEditing()) {
                    pane.tabledata.getCellEditor().stopCellEditing();
                }

                if (row == 0) {
                    pane.tabledata.requestFocus();
                    pane.tabledata.changeSelection(row, col, false, false);
                } else {
                    pane.tabledata.requestFocus();
                    pane.tabledata.changeSelection(row - 1, col, false, false);
                }

            }
        });

        pane.tabledata.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
        pane.tabledata.getActionMap().put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = pane.tabledata.getSelectedRow();
                int col = pane.tabledata.getSelectedColumn();
                if (pane.tabledata.isEditing()) {
                    pane.tabledata.getCellEditor().stopCellEditing();
                }
                if (row == pane.tabledata.getRowCount() - 1) {
                    pane.tabledata.requestFocus();
                    pane.tabledata.changeSelection(row, col, false, false);
                } else {
                    pane.tabledata.requestFocus();
                    pane.tabledata.changeSelection(row + 1, col, false, false);
                }

            }
        });

        pane.tabledata.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "tab");
        pane.tabledata.getActionMap().put("tab", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = pane.tabledata.getSelectedRow();
                int col = pane.tabledata.getSelectedColumn();
                nextcolom(col, row);

            }
        });

        pane.tabledata.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.SHIFT_DOWN_MASK), "shift_tab");
        pane.tabledata.getActionMap().put("shift_tab", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = pane.tabledata.getSelectedRow();
                int col = pane.tabledata.getSelectedColumn();
                if (pane.tabledata.isEditing()) {
                    pane.tabledata.getCellEditor().stopCellEditing();
                }
                backcolom(col, row);
            }
        }
        );

    }

    private void addautorow(int row) {
        int lastrow = pane.tabledata.getRowCount() - 1;
        if (!pane.tabledata.getValueAt(row, 0).equals("")) {
            if (row == lastrow) {
                tabeldatalist.add(new Entitytabledata("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
                dtmtabeldata.addRow(rowtabledata);
                pane.tabledata.requestFocus();
                pane.tabledata.changeSelection(row + 1, 0, false, false);
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

    private void columnfunction(int row, int col, boolean addrow) {
        if (col == 2) {
            String value = nf.format(Oneforallfunc.ToDouble(pane.tabledata.getValueAt(row, col)));
            pane.tabledata.setValueAt(value, row, col);

            kalkulasitotalperrow(row);
            if (addrow == true) {
                addautorow(row);
            }
        } else if (col == 4) {
            String value = nf.format(Oneforallfunc.ToDouble(pane.tabledata.getValueAt(row, col)));
            pane.tabledata.setValueAt(value, row, col);

            kalkulasitotalperrow(row);
            if (addrow == true) {
                addautorow(row);
            }
        } else if (col == 6) {
            String valcol6 = String.valueOf(pane.tabledata.getValueAt(row, 6));
            if (valcol6.equals("")) {
                tabeldatalist.get(row).setDiskon_persen("0");
                pane.tabledata.setValueAt("0", row, 6);
            } else {
                tabeldatalist.get(row).setDiskon_persen(valcol6);
            }
            kalkulasitotalperrow(row);
            if (addrow == true) {
                addautorow(row);
            }
        } else if (col == 7) {
            String valcol7 = String.valueOf(pane.tabledata.getValueAt(row, 7));
            if (valcol7.equals("")) {
                tabeldatalist.get(row).setDiskon_nominal("0");
                pane.tabledata.setValueAt("0", row, 7);
            } else {
                tabeldatalist.get(row).setDiskon_nominal(valcol7);
            }
            kalkulasitotalperrow(row);
            if (addrow == true) {
                addautorow(row);
            }
        }
    }

    private void kalkulasi() {
        KeyAdapter keadbiaya = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                char cr = e.getKeyChar();
                if (!Character.isLetter(cr)) {
                    double biayalain = Oneforallfunc.ToDouble(pane.edbiayalain.getText());
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
                    double diskon_persen = Oneforallfunc.ToDouble(pane.eddiskon1.getText());
                    double biayalain = Oneforallfunc.ToDouble(pane.edbiayalain.getText());

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
                    double diskon_nominal = Oneforallfunc.ToDouble(pane.eddiskon2.getText());
                    double biayalain = Oneforallfunc.ToDouble(pane.edbiayalain.getText());

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
            double total_beli_masing = Oneforallfunc.ToDouble(emptycellcheck(i, 11));
            total_pembelian_all = total_pembelian_all + total_beli_masing;
        }

        total_pajak = 0;
        for (int i = 0; i < jumlah_row; i++) {
            double total_pajak_masing = Oneforallfunc.ToDouble(emptycellcheck(i, 11)) * (Oneforallfunc.doubleparsing(tabeldatalist.get(i).getNilai_pajak()) / 100);
            total_pajak = total_pajak + total_pajak_masing;
        }
        pane.ltotal_pajak.setText(nf.format(total_pajak));

        pane.lsubtotal.setText(nf.format(total_pembelian_all));
        double diskon_persen = Oneforallfunc.ToDouble(pane.eddiskon1.getText());

        double biayalain = Oneforallfunc.ToDouble(pane.edbiayalain.getText());

        double totaldiskon = (total_pembelian_all + biayalain) * (diskon_persen / 100);
        pane.eddiskon2.setText(nf.format(totaldiskon));
        double totaldenganbiayalaintambahdiskon = (total_pembelian_all + biayalain) - totaldiskon;
        pane.ltotal_pembelian.setText(nf.format(totaldenganbiayalaintambahdiskon + total_pajak));
    }

    private void kalkulasitotalperrow(int row) {
        if (pane.ckdiskon.isSelected() == true) {
            String isifielddiskon = String.valueOf(pane.tabledata.getValueAt(row, 6));
            if (isifielddiskon.contains("+")) {
                double qty = Oneforallfunc.ToDouble(String.valueOf(pane.tabledata.getValueAt(row, 2))) * Oneforallfunc.intparsing(tabeldatalist.get(row).getIsi_satuan());
                double harga = Oneforallfunc.ToDouble(pane.tabledata.getValueAt(row, 4));
                double total = harga;
                String[] multidiskon = isifielddiskon.split("\\+");
                for (int i = 0; i < multidiskon.length; i++) {
                    double diskonper = Oneforallfunc.ToDouble(multidiskon[i]);
                    total = (qty * (total - (diskonper / 100 * total)));
                }
                tabeldatalist.get(row).setTotal(String.valueOf(total));
                pane.tabledata.setValueAt(nf.format(total), row, 11);
            } else {
                double qty = Oneforallfunc.ToDouble(String.valueOf(pane.tabledata.getValueAt(row, 2))) * Oneforallfunc.intparsing(tabeldatalist.get(row).getIsi_satuan());
                double harga = Oneforallfunc.ToDouble(String.valueOf(pane.tabledata.getValueAt(row, 4)));
                double diskon = Oneforallfunc.ToDouble(emptycellcheck(row, 6));
                double total = qty * (harga - (diskon / 100 * harga));
                tabeldatalist.get(row).setTotal(String.valueOf(total));
                pane.tabledata.setValueAt(nf.format(total), row, 11);
            }
        } else {

            double qty = Oneforallfunc.ToDouble(String.valueOf(pane.tabledata.getValueAt(row, 2))) * Oneforallfunc.intparsing(tabeldatalist.get(row).getIsi_satuan());
            double harga = Oneforallfunc.ToDouble(pane.tabledata.getValueAt(row, 4));
            double diskon = Oneforallfunc.ToDouble(emptycellcheck(row, 7));
            double total = qty * (harga - diskon);
            tabeldatalist.get(row).setTotal(String.valueOf(total));
            pane.tabledata.setValueAt(nf.format(total), row, 11);
        }
        kalkulasitotal();
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

    private boolean checknumerik(String val) {
        boolean hasil = false;
        try {
            Oneforallfunc.ToDouble(val);
            hasil = true;
        } catch (Exception e) {
            hasil = false;
        }
        return hasil;
    }

    private boolean checkalphabeth(String val) {
        boolean hasil = false;
        if (val.contains("^[0-9].+-") == true) {
            hasil = true;
        } else {
            hasil = false;
        }
        return hasil;
    }

    private void nextcolom(int col, int row) {
        if (col == 4) {
            if (pane.ckdiskon.isSelected()) {
                pane.tabledata.requestFocus();
                pane.tabledata.changeSelection(row, 6, false, false);
            } else {
                pane.tabledata.requestFocus();
                pane.tabledata.changeSelection(row, 7, false, false);
            }
        } else if (col == 5) {
            pane.tabledata.requestFocus();
            pane.tabledata.changeSelection(row, 7, false, false);

        } else if (col == 6) {
            pane.tabledata.requestFocus();
            pane.tabledata.changeSelection(row, 8, false, false);
        } else if (col == pane.tabledata.getColumnCount() - 1) {
            if ((pane.tabledata.getRowCount() - 1) > row) {
                pane.tabledata.requestFocus();
                pane.tabledata.changeSelection(row + 1, 0, false, false);
            } else {
                if (String.valueOf(pane.tabledata.getValueAt(row, 0)).equals("") || String.valueOf(pane.tabledata.getValueAt(row, 0)).equals("null")) {
                    pane.tabledata.requestFocus();
                    pane.tabledata.changeSelection(row, 0, false, false);
                } else {
                    addautorow(row);
                }
            }
        } else {
            xnextcolom(col, row, pane.tabledata.getColumnCount() - 1);
        }
    }

    private void backcolom(int col, int row) {
        if (col == 8) {
            if (pane.ckdiskon.isSelected()) {
                pane.tabledata.requestFocus();
                pane.tabledata.changeSelection(row, 6, false, false);
            } else {
                pane.tabledata.requestFocus();
                pane.tabledata.changeSelection(row, 7, false, false);
            }
        } else if (col == 7 || col == 6) {
            pane.tabledata.requestFocus();
            pane.tabledata.changeSelection(row, 4, false, false);

        } else {
            xbackcolom(col, row, 11);
        }
    }

    private void xnextcolom(int currentcoll, int currentrow, int colcount) {
        for (int i = 0; i < colcount; i++) {
            if (currentcoll == i) {
                for (int j = currentcoll; j < colcount; j++) {
                    while (!cekcolomnol(j + 1)) {
                        pane.tabledata.requestFocus();
                        pane.tabledata.changeSelection(currentrow, j + 1, false, false);
                        return;
                    }
                }
            }
        }
    }

    private void xbackcolom(int currentcoll, int currentrow, int colcount) {
        for (int i = colcount; i > 0; i--) {
            if (currentcoll == i) {
                for (int j = currentcoll; j > 0; j--) {
                    while (!cekcolomnol(j - 1)) {
                        pane.tabledata.requestFocus();
                        pane.tabledata.changeSelection(currentrow, j - 1, false, false);
                        return;
                    }
                }
            }
        }
    }

    private boolean cekcolomnol(int colom) {
        boolean result = false;
        if (lsstatus.get(colom) == 0) {
            result = true;
        } else {
            result = false;
        }
        return result;
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
