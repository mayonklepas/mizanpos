/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.penjualan;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.AbstractAction;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import mizanposapp.helper.ConvertFunc;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Globalsession;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Errorpanel;
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.penjualan.Insertpos_pane;
import mizanposapp.view.innerpanel.penjualan.Posframe;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class PosframeController {

    String id;
    CrudHelper ch = new CrudHelper();
    Posframe pane;
    String valpelanggan = "", valgudang = "", valdept = "", valsalesman = "", valshipvia = "", valtop = "",
         valakun_penjualan = "", valakun_ongkir = "", valakun_diskon = "", valakun_uang_muka = "", valgolongan = "";
    int valcheck = 0;
    int tipe_bayar = 0, tipe_jual = 0, status_selesai = 0;
    DefaultTableModel dtmtabeldata = new DefaultTableModel();
    Object[] rowtabledata = new Object[12];
    ArrayList<Entitytabledata> tabeldatalist = new ArrayList<>();
    int col = 0;
    //int istunai = 0;
    //int isjasa = 0;
    //int row = 0;
    NumberFormat nf = NumberFormat.getInstance();
    double total_penjualan_all = 0;
    double total_pajak = 0;
    int no_urut = 0;
    JTextField jt2;
    JTextField jt4;
    JTextField jt6;
    JTextField jt7;
    ArrayList<Integer> lshide = new ArrayList<>();
    ArrayList<Integer> lsoldhide = new ArrayList<>();
    ArrayList<Integer> lsresize = new ArrayList<>();
    ArrayList<Integer> lsoldsize = new ArrayList<>();
    private boolean ischangevalue = false;
    static String oldvalue = "";
    ArrayList<Integer> lsvisiblecolom = new ArrayList<>();
    ArrayList<String> listheadername = new ArrayList<>();
    static boolean sudahterpanggil = false;
    static boolean sudah_jangan_set_lagi_kau_membuat_semua_kacau = false;
    String kode = "kode";
    String nama = "nama";
    String jumlah = "jumlah";
    String satuan = "satuan";
    String harga_beli = "harga_beli";
    String harga_jual = "harga_jual";
    String diskon_persen = "disc_persen";
    String diskon_nominal = "disc_rp";
    String pajak = "pajak";
    String gudang = "gudang";
    String keterangan = "keterangan";
    String total = "total";
    boolean panggil = true;

    public PosframeController(Posframe pane) {
        this.pane = pane;
        skinning();
        loadsession();
        loaddata();
        simpandata();
        checkandcombocontrol();
        caripelanggan();
        caridepartment();
        carisalesman();
        //hapusbaris();
        //tambahbaris();
        //batal();
        keyfunction();

    }

    private void loadsession() {
        valakun_penjualan = Globalsession.AKUNPENJUALANTUNAI;
        valakun_uang_muka = Globalsession.AKUNUANGMUKAPENJUALAN;
        valakun_diskon = Globalsession.AKUNDISKONPENJUALAN;
        valakun_ongkir = Globalsession.AKUNONGKOSKIRIMPENJUALAN;
    }

    private void skinning() {
        pane.dtanggal.setDateFormatString("dd MMMM yyyy");
        pane.dtanggal.setDate(new Date());
        pane.dtanggal.getDateEditor().setEnabled(false);
    }

    private void getkodetransaksi() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONParser jpdata = new JSONParser();
                    String param = String.format("id_keltrans=%s", "2");
                    Object ob = jpdata.parse(ch.getdatadetails("getnomortransaksi", param));
                    JSONArray ja = (JSONArray) ob;
                    for (int i = 0; i < ja.size(); i++) {
                        JSONObject jo = (JSONObject) ja.get(i);
                        pane.edno_transaksi.setText(String.valueOf(jo.get("no_transaksi")));
                        no_urut = ConvertFunc.ToInt(String.valueOf(jo.get("no_urut")));
                    }

                } catch (ParseException ex) {
                    Logger.getLogger(PosframeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    private void customtable() {
        pane.tabledata.setDefaultEditor(Object.class, null);
        pane.tabledata.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

    }

    private void checkandcombocontrol() {
        pane.ckdiskon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox cb = (JCheckBox) e.getSource();
                for (int i = 0; i < pane.tabledata.getRowCount(); i++) {
                    kalkulasitotalperrow(i);
                }
                if (cb.isSelected()) {
                    hidetable(gx(diskon_nominal));
                    showtable(gx(diskon_persen));
                    valcheck = 0;

                } else {
                    hidetable(gx(diskon_persen));
                    showtable(gx(diskon_nominal));
                    valcheck = 1;
                }
            }
        });

    }

    private void loadheader() {
        try {
            pane.tabledata.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            pane.tabledata.setModel(dtmtabeldata);
            String dataheader = ch.getheaders();
            JSONParser jpheader = new JSONParser();
            Object objheader = jpheader.parse(dataheader);
            JSONObject joheader = (JSONObject) objheader;
            JSONArray jaheader = (JSONArray) joheader.get("inputorderpenjualan");
            //perulangan mengambil header
            for (int i = 0; i < jaheader.size(); i++) {
                JSONObject jodata = (JSONObject) jaheader.get(i);
                JSONArray jaaray = (JSONArray) jodata.get("key");
                dtmtabeldata.addColumn(jaaray.get(1));
                listheadername.add(String.valueOf(jaaray.get(0)));
            }

            // resize colum
            for (int i = 0; i < jaheader.size(); i++) {
                JSONObject jodata = (JSONObject) jaheader.get(i);
                JSONArray jaaray = (JSONArray) jodata.get("key");
            }

            // hidden column
            for (int i = 0; i < jaheader.size(); i++) {
                JSONObject jodata = (JSONObject) jaheader.get(i);
                JSONArray jaaray = (JSONArray) jodata.get("key");
                lsresize.add(Integer.parseInt(String.valueOf(jaaray.get(3))));
                lsoldhide.add(Integer.parseInt(String.valueOf(jaaray.get(3))));
                lsoldsize.add(Integer.parseInt(String.valueOf(jaaray.get(3))));
                lshide.add(Integer.parseInt(String.valueOf(jaaray.get(2))));
            }

            setheader();
        } catch (ParseException ex) {
            Logger.getLogger(PosframeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void setheader() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        TableColumnModel tcm = pane.tabledata.getColumnModel();

        double lebar = d.getWidth() - Staticvar.lebarPanelMenu;;
        double lebarAll = 0;

        for (int i = 0; i < lshide.size(); i++) {
            if (lshide.get(i) == 0) {
                hidetable(i);
            }
        }

        for (int i = 0; i < lsresize.size(); i++) {

            if (cekcolomnol(i)) {
                continue;
            }

            int setsize = lsresize.get(i);
            lebarAll = lebarAll + setsize;
        }

        double pembagi = lebar / lebarAll;
        double lebarAllNew = 0;
        for (int i = 0; i < lsresize.size() - 1; i++) {
            int setsize = lsresize.get(i);
            if (i != lsresize.size() - 1) {
                int wi = ConvertFunc.ToInt(pembagi * setsize);
                tcm.getColumn(i).setMinWidth(wi);
                tcm.getColumn(i).setWidth(wi);
                tcm.getColumn(i).setMaxWidth(wi);

                lebarAllNew = lebarAllNew + wi;
            } else {
                int wi = ConvertFunc.ToInt(lebar - lebarAllNew);
                tcm.getColumn(i).setMinWidth(wi);
                tcm.getColumn(i).setMaxWidth(wi);
            }
        }
    }

    private void loaddata() {
        customtable();
        loadheader();
        try {
            id = Staticvar.ids;
            if (id.equals("")) {
                getkodetransaksi();
                status_selesai = 0;
                tipe_jual = 0;
                pane.dtanggal.setDate(new Date());
                valpelanggan = "";
                pane.edno_transaksi.setText("");
                pane.eddept.setText(Globalsession.DEFAULT_DEPT_NAME);
                valdept = Globalsession.DEFAULT_DEPT_ID;
                valgudang = Globalsession.DEFAULT_ID_GUDANG;
                pane.edketerangan.setText("");
                pane.edsalesman.setText("");
                valsalesman = "";
                valshipvia = "";
                valtop = "";
                pane.lsubtotal.setText("0");
                pane.ltotal_pajak.setText("0");
                pane.ltotal_penjualan.setText("0");
                if (Globalsession.DEFAULT_DISKON_DALAM.equals("0")
                     || Globalsession.DEFAULT_DISKON_DALAM.equals("")
                     || Globalsession.DEFAULT_DISKON_DALAM.equals("NULL")
                     || Globalsession.DEFAULT_DISKON_DALAM.equals("null")) {
                    pane.ckdiskon.setSelected(true);
                    valcheck = 0;
                } else {
                    pane.ckdiskon.setSelected(false);
                    valcheck = 1;
                }
                if (pane.ckdiskon.isSelected()) {
                    hidetable(gx(diskon_nominal));
                    showtable(gx(diskon_persen));
                } else {
                    showtable(gx(diskon_persen));
                    hidetable(gx(diskon_nominal));
                }

            } else {

                JSONParser jpdata = new JSONParser();
                String param = String.format("id=%s", id);
                Object rawobjdata = jpdata.parse(ch.getdatadetails("dataorderpenjualan", param));
                JSONObject jsonobjdata = (JSONObject) rawobjdata;

                Object jogenjur = jsonobjdata.get("genjur");
                JSONArray jagenjur = (JSONArray) jogenjur;

                for (int i = 0; i < jagenjur.size(); i++) {
                    JSONObject joingenjur = (JSONObject) jagenjur.get(i);
                    pane.edno_transaksi.setText(String.valueOf(joingenjur.get("noref")));
                    pane.edketerangan.setText(String.valueOf(joingenjur.get("keterangan")));
                    pane.eddept.setText(String.valueOf(joingenjur.get("nama_dept")));
                    valdept = String.valueOf(joingenjur.get("id_dept"));
                    try {
                        pane.dtanggal.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(joingenjur.get("tanggal"))));
                    } catch (java.text.ParseException ex) {
                        Logger.getLogger(PosframeController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

                Object jopenjualan = jsonobjdata.get("penjualan");
                JSONArray japenjualan = (JSONArray) jopenjualan;

                for (int i = 0; i < japenjualan.size(); i++) {
                    JSONObject joinpenjualan = (JSONObject) japenjualan.get(i);
                    valpelanggan = String.valueOf(joinpenjualan.get("id_pelanggan"));
                    valgolongan = String.valueOf(joinpenjualan.get("id_golongan"));

                    pane.ltotal_penjualan.setText(String.valueOf(joinpenjualan.get("total_penjualan")));

                    pane.ltotal_pajak.setText(String.valueOf(joinpenjualan.get("total_pajak")));

                    valakun_penjualan = String.valueOf(joinpenjualan.get("akun_penjualan"));

                    valakun_uang_muka = String.valueOf(joinpenjualan.get("akun_uang_muka"));

                    valcheck = Integer.parseInt(String.valueOf(joinpenjualan.get("diskon_dalam")));
                    if (valcheck == 0) {
                        pane.ckdiskon.setSelected(true);
                    } else {
                        pane.ckdiskon.setSelected(false);
                    }

                    if (pane.ckdiskon.isSelected()) {
                        hidetable(gx(diskon_nominal));
                        showtable(gx(diskon_persen));
                    } else {
                        hidetable(gx(diskon_persen));
                        showtable(gx(diskon_nominal));
                    }

                    valsalesman = String.valueOf(joinpenjualan.get("id_bagian_penjualan"));
                    pane.edsalesman.setText(String.valueOf(joinpenjualan.get("nama_bagian_penjualan")));

                }

                Object objtabeldata = jsonobjdata.get("penjualan_detail");
                JSONArray jatabledata = (JSONArray) objtabeldata;
                for (int i = 0; i < jatabledata.size(); i++) {
                    JSONObject jointabeldata = (JSONObject) jatabledata.get(i);
                    String id_barang = String.valueOf(jointabeldata.get("id_inv"));
                    String kode_barang = String.valueOf(jointabeldata.get("kode_inv"));
                    String nama_barang = String.valueOf(jointabeldata.get("nama_inv"));
                    String jumlah = String.valueOf(jointabeldata.get("qty"));
                    String id_satuan = String.valueOf(jointabeldata.get("id_satuan"));
                    String nama_satuan = String.valueOf(jointabeldata.get("nama_satuan"));
                    String isi_satuan = String.valueOf(jointabeldata.get("qty_satuan_pengali"));
                    String id_satuan_pengali = String.valueOf(jointabeldata.get("id_satuan_pengali"));
                    String harga_beli = nf.format(ConvertFunc.ToDouble(jointabeldata.get("harga_beli")));
                    String harga_jual = nf.format(ConvertFunc.ToDouble(jointabeldata.get("harga_jual")));
                    String diskon_persen = String.valueOf(jointabeldata.get("diskon_persen"));
                    String diskon_nominal = String.valueOf(jointabeldata.get("diskon_nominal"));
                    String id_pajak = String.valueOf(jointabeldata.get("id_pajak"));
                    String nama_pajak = String.valueOf(jointabeldata.get("nama_pajak"));
                    String nilai_pajak = String.valueOf(jointabeldata.get("nilai_pajak"));
                    String id_gudang = String.valueOf(jointabeldata.get("id_gudang"));
                    String nama_gudang = String.valueOf(jointabeldata.get("nama_gudang"));
                    String keterangan = String.valueOf(jointabeldata.get("keterangan"));
                    String total = nf.format(kalkulasitotalperindex(diskon_persen, diskon_nominal, jumlah, harga_jual, isi_satuan));
                    tabeldatalist.add(new Entitytabledata(id_barang, kode_barang, nama_barang, jumlah, id_satuan,
                         nama_satuan, isi_satuan, id_satuan_pengali, harga_beli, harga_jual, diskon_persen, diskon_nominal, id_pajak, nama_pajak,
                         nilai_pajak, id_gudang, nama_gudang, keterangan, total));

                }
                for (int i = 0; i < tabeldatalist.size(); i++) {
                    rowtabledata[0] = tabeldatalist.get(i).getKode_barang();
                    rowtabledata[1] = tabeldatalist.get(i).getNama_barang();
                    rowtabledata[2] = tabeldatalist.get(i).getJumlah();
                    rowtabledata[3] = tabeldatalist.get(i).getNama_satuan();
                    rowtabledata[4] = tabeldatalist.get(i).getHarga_beli();
                    rowtabledata[5] = tabeldatalist.get(i).getHarga_jual();
                    rowtabledata[6] = tabeldatalist.get(i).getDiskon_persen();
                    rowtabledata[7] = tabeldatalist.get(i).getDiskon_nominal();
                    rowtabledata[8] = tabeldatalist.get(i).getNama_pajak();
                    rowtabledata[9] = tabeldatalist.get(i).getNama_gudang();
                    rowtabledata[10] = tabeldatalist.get(i).getKeterangan();
                    rowtabledata[11] = tabeldatalist.get(i).getTotal();
                    dtmtabeldata.addRow(rowtabledata);
                }
                kalkulasitotal();
                for (int i = 0; i < rowtabledata.length; i++) {
                    rowtabledata[i] = "";
                }

            }

        } catch (ParseException ex) {
            Logger.getLogger(PosframeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void rawsimpan() {
        if (id.equals("")) {
            String data = "genjur="
                 + "id_keltrans='22'::"
                 + "id_dept='" + valdept + "'::"
                 + "tanggal='" + new SimpleDateFormat("yyyy-MM-dd").format(pane.dtanggal.getDate()) + "'::"
                 + "noref='" + ConvertFunc.EncodeString(pane.edno_transaksi.getText()) + "'::"
                 + "keterangan='" + ConvertFunc.EncodeString(pane.edketerangan.getText()) + "'"
                 + "&penjualan="
                 + "id_pelanggan='" + valpelanggan + "'::"
                 + "tipe_pembayaran='" + String.valueOf(tipe_bayar) + "'::"
                 + "id_gudang='" + valgudang + "'::"
                 + "total_penjualan='" + total_penjualan_all + "'::"
                 + "total_uang_muka='0'::"
                 + "total_pajak='" + total_pajak + "'::"
                 + "id_currency='" + Globalsession.DEFAULT_CURRENCY_ID + "'::"
                 + "nilai_kurs='1'::"
                 + "akun_penjualan='" + valakun_penjualan + "'::"
                 + "akun_diskon='" + valakun_diskon + "'::"
                 + "akun_uang_muka='" + valakun_uang_muka + "'::"
                 + "diskon_dalam='" + valcheck + "'::"
                 + "id_bagian_penjualan='" + valsalesman + "'::"
                 + "id_termofpayment='" + valtop + "'::"
                 + "tipe_penjualan='" + tipe_jual + "'::"
                 + "isorderselesai='" + status_selesai + "'"
                 + "&" + kirimtexpenjualan();

            ch.insertdata("insertorderpenjualan", data);
            if (Staticvar.getresult.equals("berhasil")) {
                try {
                    int dialog = JOptionPane.showConfirmDialog(null, "Data berhasil disimpan. \n "
                         + "Ingin Melanjutkan transaksi", "Konfirmasi", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    if (dialog == 0) {
                        Runnable run = new Runnable() {
                            @Override
                            public void run() {
                                int rowcount = pane.tabledata.getRowCount();
                                for (int i = 0; i < rowcount; i++) {
                                    dtmtabeldata.removeRow(0);
                                }
                                tabeldatalist.clear();
                                dtmtabeldata.setRowCount(0);
                                tabeldatalist.add(new Entitytabledata("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
                                dtmtabeldata.addRow(rowtabledata);
                            }
                        };
                        SwingUtilities.invokeLater(run);
                        getkodetransaksi();
                        pane.tabledata.requestFocus();
                        pane.lsubtotal.setText("0");
                        pane.ltotal_pajak.setText("0");
                        pane.ltotal_penjualan.setText("0");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                JDialog jd = new JDialog(new Mainmenu());
                Errorpanel ep = new Errorpanel();
                ep.ederror.setText(Staticvar.getresult);
                jd.add(ep);
                jd.pack();
                jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                jd.setLocationRelativeTo(null);
                jd.setVisible(true);
                jd.toFront();
            }
        } else {
            String data = "genjur="
                 + "id_keltrans='22'::"
                 + "id_dept='" + valdept + "'::"
                 + "tanggal='" + new SimpleDateFormat("yyyy-MM-dd").format(pane.dtanggal.getDate()) + "'::"
                 + "noref='" + ConvertFunc.EncodeString(pane.edno_transaksi.getText()) + "'::"
                 + "keterangan='" + ConvertFunc.EncodeString(pane.edketerangan.getText()) + "'"
                 + "&penjualan="
                 + "id_pelanggan='" + valpelanggan + "'::"
                 + "tipe_pembayaran='" + String.valueOf(tipe_bayar) + "'::"
                 + "id_gudang='" + valgudang + "'::"
                 + "total_penjualan='" + total_penjualan_all + "'::"
                 + "total_uang_muka='0'::"
                 + "total_pajak='" + total_pajak + "'::"
                 + "id_currency='" + Globalsession.DEFAULT_CURRENCY_ID + "'::"
                 + "nilai_kurs='1'::"
                 + "akun_penjualan='" + valakun_penjualan + "'::"
                 + "akun_biaya='" + valakun_ongkir + "'::"
                 + "akun_diskon='" + valakun_diskon + "'::"
                 + "akun_uang_muka='" + valakun_uang_muka + "'::"
                 + "diskon_dalam='" + valcheck + "'::"
                 + "id_pengantaran='" + valshipvia + "'::"
                 + "id_bagian_penjualan='" + valsalesman + "'::"
                 + "id_termofpayment='" + valtop + "'::"
                 + "tipe_penjualan='" + tipe_jual + "'::"
                 + "isorderselesai='" + status_selesai + "'"
                 + "&" + kirimtexpenjualan();
            ch.updatedata("updateorderpenjualan", data, id);
            if (Staticvar.getresult.equals("berhasil")) {

            } else {
                JDialog jd = new JDialog(new Mainmenu());
                Errorpanel ep = new Errorpanel();
                ep.ederror.setText(Staticvar.getresult);
                jd.add(ep);
                jd.pack();
                jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                jd.setLocationRelativeTo(null);
                jd.setVisible(true);
                jd.toFront();
            }
        }
    }

    private void simpandata() {
        pane.bsimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Staticvar.isupdate = true;
                if (tabeldatalist.size() == 0) {
                    JOptionPane.showMessageDialog(null, "Table Tidak Boleh Kosong", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    int tahunbulan = Integer.parseInt(new SimpleDateFormat("yyyyMM").format(pane.dtanggal.getDate()));
                    int periodetahunnulan = Integer.parseInt(Globalsession.PERIODE_TAHUN + Globalsession.PERIODE_BULAN);
                    if (tahunbulan > periodetahunnulan) {
                        int dialog = JOptionPane.showConfirmDialog(null, "Tanggal transaksi setelah periode akuntansi.\n"
                             + "Apakah anda ingin melanjutkan transaksi ?", "Konfirmasi", JOptionPane.YES_NO_OPTION, 1);
                        if (dialog == 0) {

                            rawsimpan();

                        }
                    } else if (tahunbulan < periodetahunnulan) {
                        JDialog jd = new JDialog(new Mainmenu());
                        Errorpanel ep = new Errorpanel();
                        ep.ederror.setText("Tanggal transaksi sebelum periode akuntansi. \n"
                             + "Anda tidak dapat memasukan, mengedit,menghapus transaksi sebelum periode. \n"
                             + "Untuk dapat memasukan atau mengedit transaksi, silahkan merubah periode akuntansi");
                        jd.add(ep);
                        jd.pack();
                        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        jd.setLocationRelativeTo(null);
                        jd.setVisible(true);
                        jd.toFront();
                    } else {

                        rawsimpan();

                    }
                }
            }
        });
    }

    private String kirimtexpenjualan() {
        StringBuilder sb = new StringBuilder();
        sb.append("penjualan_detail=");
        int listcount = 0;
        if (tabeldatalist.get(tabeldatalist.size() - 1).getId_barang().equals("")) {
            listcount = tabeldatalist.size() - 1;
        } else {
            listcount = tabeldatalist.size();
        }
        for (int i = 0; i < listcount; i++) {
            sb.append("id_inv=" + "'" + tabeldatalist.get(i).getId_barang() + "'" + "::"
                 + "qty=" + "'" + ConvertFunc.ToDouble(tabeldatalist.get(i).getJumlah()) + "'" + "::"
                 + "harga=" + "'" + ConvertFunc.ToDouble(tabeldatalist.get(i).getHarga_jual()) + "'" + "::"
                 + "id_satuan=" + "'" + tabeldatalist.get(i).getId_satuan() + "'" + "::"
                 + "diskon_persen=" + "'" + ConvertFunc.EncodeString(tabeldatalist.get(i).getDiskon_persen()) + "'" + "::"
                 + "diskon_nominal=" + "'" + ConvertFunc.ToDouble(tabeldatalist.get(i).getDiskon_nominal()) + "'" + "::"
                 + "id_pajak=" + "'" + tabeldatalist.get(i).getId_pajak() + "'" + "::"
                 + "id_gudang=" + "'" + tabeldatalist.get(i).getId_gudang() + "'" + "::"
                 + "id_satuan_pengali=" + "'" + tabeldatalist.get(i).getId_satuan_pengali() + "'" + "::"
                 + "qty_satuan_pengali=" + "'" + tabeldatalist.get(i).getIsi_satuan() + "'" + "::"
                 + "keterangan=" + "'" + ConvertFunc.EncodeString(tabeldatalist.get(i).getKeterangan()) + "'");
            sb.append("--");

        }
        String rawhasil = sb.toString().substring(0, sb.toString().length() - 2);
        String finalhasil = "";
        if (tipe_jual == 1) {
            finalhasil = rawhasil.replace("id_inv", "akun");
        } else {
            finalhasil = rawhasil;
        }

        return finalhasil;
    }

    private void hapusbaris() {
        int row = pane.tabledata.getSelectedRow();
        int dialog = JOptionPane.showConfirmDialog(null,
             "Yakin akan menghapus " + pane.tabledata.getValueAt(row, gx(kode)) + " - "
             + pane.tabledata.getValueAt(row, gx(nama)),
             "Konfirmasi", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (dialog == 0) {
            Runnable rn = new Runnable() {
                @Override
                public void run() {
                    tabeldatalist.remove(row);
                    dtmtabeldata.removeRow(row);
                    kalkulasitotal();
                    if (row >= 0) {
                        pane.tabledata.changeSelection(0, 0, false, false);
                    }
                }
            };
            SwingUtilities.invokeLater(rn);
        }

    }

    private void tambahbaris() {
        int lastrow = pane.tabledata.getRowCount() - 1;
        if (lastrow < 0) {
            tabeldatalist.add(new Entitytabledata("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
            dtmtabeldata.addRow(rowtabledata);
            pane.tabledata.requestFocus();
            pane.tabledata.changeSelection(1, 0, false, false);
        } else {
            addautorow(lastrow);
        }
    }

    private void batal() {
        pane.bbatal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        String data = String.format("id_keltrans=%s&no_urut=%s", "22", String.valueOf(no_urut));
                        ch.insertdata("insertnomorgagal", data);
                    }
                });

            }
        });
    }

    private void caripelanggan() {
        pane.bcari_pelanggan.addActionListener((ActionEvent e) -> {
            Staticvar.sfilter = "";
            Staticvar.preid = valpelanggan;
            Staticvar.prelabel = String.valueOf(pane.cmbpelanggan.getEditor().getItem());
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("nama", "popupdaftarnama?tipe=0", "Daftar Pelanggan"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valpelanggan = Staticvar.resid;
            valgolongan = Staticvar.resvalueextended;
            pane.cmbpelanggan.getEditor().setItem(Staticvar.reslabel);
        });

    }

    private void carisalesman() {
        pane.bcari_salesman.addActionListener((ActionEvent e) -> {
            Staticvar.sfilter = "";
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

    private void caridepartment() {
        pane.bcari_dept.addActionListener((ActionEvent e) -> {
            Staticvar.sfilter = "";
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

    private void rawgetidakun(String prevlabel, String previd) {
        Staticvar.sfilter = "";
        Staticvar.preid = previd;
        Staticvar.prelabel = prevlabel;
        JDialog jd = new JDialog(new Mainmenu());
        jd.add(new Popupcari("akun", "popupdaftarakun", "Daftar Akun"));
        jd.pack();
        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        jd.setLocationRelativeTo(null);
        jd.setVisible(true);
        jd.toFront();
    }

    private void keyfunction() {
        pane.edbarcode.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    additemtotable();
                }

            }
        });

        pane.tabledata.getInputMap().put(KeyStroke.getKeyStroke("DELETE"), "hapus");
        pane.tabledata.getActionMap().put("hapus", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = pane.tabledata.getSelectedRow();
                int dialog = JOptionPane.showConfirmDialog(null,
                     "Yakin akan menghapus " + pane.tabledata.getValueAt(row, gx(kode)) + " - "
                     + pane.tabledata.getValueAt(row, gx(nama)),
                     "Konfirmasi", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (dialog == 0) {
                    Runnable rn = new Runnable() {
                        @Override
                        public void run() {
                            tabeldatalist.remove(row);
                            dtmtabeldata.removeRow(row);
                            kalkulasitotal();
                            if (row >= 0) {
                                pane.tabledata.changeSelection(0, 0, false, false);
                            }
                        }
                    };
                    SwingUtilities.invokeLater(rn);
                }
            }
        });

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_RELEASED) {
                    if (e.getKeyCode() == KeyEvent.VK_F2) {
                        if (pane.edbarcode.isFocusable() == false) {
                            pane.tabledata.clearSelection();
                            pane.edbarcode.setText("");
                            pane.edbarcode.requestFocus();
                        } else {
                            additemtotable();
                        }

                    } else if (e.getKeyCode() == KeyEvent.VK_F9) {
                        pane.bsimpan.doClick();
                    } else if (e.getKeyCode() == KeyEvent.VK_F10) {
                        pane.bpending.doClick();
                    } else if (e.getKeyCode() == KeyEvent.VK_F11) {
                        pane.brecall.doClick();
                    } else if (e.getKeyCode() == KeyEvent.VK_F12) {
                        pane.bbatal.doClick();
                    } else if (e.getKeyCode() == KeyEvent.VK_F5) {
                        pane.bupdate.doClick();
                    } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        pane.btutup.doClick();
                    } else if (e.getKeyCode() == KeyEvent.VK_INSERT) {
                        Staticvar.sfilter = "";
                        Staticvar.preid = valpelanggan;
                        Staticvar.prelabel = String.valueOf(pane.cmbpelanggan.getEditor().getItem());
                        JDialog jd = new JDialog(new Mainmenu());
                        jd.add(new Insertpos_pane());
                        jd.pack();
                        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        jd.setLocationRelativeTo(null);
                        jd.setVisible(true);
                        jd.toFront();
                        valpelanggan = Staticvar.resid;
                        valgolongan = Staticvar.resvalueextended;
                    }
                }
                return false;
            }
        });

    }

    private void additemtotable() {
        try {
            JSONParser jpdata = new JSONParser();
            String kode_barcode = pane.edbarcode.getText().toLowerCase();
            if (pane.edbarcode.getText().toLowerCase().contains("x")) {
                try {

                    String splitcode[] = pane.edbarcode.getText().toLowerCase().split("x");
                    if (splitcode.length == 2) {
                        String pre = splitcode[0];
                        String post = splitcode[1];
                        String rawkode_barcode = "";
                        if (pre.matches("[0-9]+")) {
                            rawkode_barcode = post;
                        } else {
                            rawkode_barcode = pre + "x" + post;
                        }

                        if (kode_barcode.substring(kode_barcode.length() - 1, kode_barcode.length()).toLowerCase().equals("x")) {
                            kode_barcode = rawkode_barcode + "x";
                        } else {
                            kode_barcode = rawkode_barcode;
                        }

                    } else if (splitcode.length > 2) {
                        String pre = splitcode[0];
                        String post = "";
                        StringBuilder sbpost = new StringBuilder();
                        for (int i = 0; i < splitcode.length; i++) {
                            if (i > 0) {
                                sbpost.append(splitcode[i]).append("x");
                            }
                        }
                        post = sbpost.toString().substring(0, sbpost.toString().length() - 1);
                        if (kode_barcode.substring(kode_barcode.length() - 1, kode_barcode.length()).toLowerCase().equals("x")) {
                            post = post + "x";
                        }
                        if (pre.matches("[0-9]+")) {
                            kode_barcode = post;
                        } else {
                            kode_barcode = pre + "x" + post;
                        }
                    } else {
                        String pre = splitcode[0];
                        String post = "";
                        if (pre.matches("[0-9]+")) {
                            kode_barcode = post;
                        } else {
                            kode_barcode = pre + post;
                        }
                    }
                } catch (Exception es) {
                    kode_barcode = "";
                }

            }

            Staticvar.preid = kode_barcode;
            String defnilai = "";
            String cekval = kode_barcode;
            if (cekval.equals("null") || cekval.equals("")) {
                defnilai = "";
            } else {
                defnilai = kode_barcode;
            }
            Staticvar.prelabel = defnilai;
            Staticvar.sfilter = defnilai;
            Staticvar.prelabel = defnilai;

            String param = String.format("kode=%s", kode_barcode);
            Object objdataraw = jpdata.parse(ch.getdatadetails("dm/datapersediaanbykode", param));
            JSONObject jodataraw = (JSONObject) objdataraw;
            Object objdata = jodataraw.get("data");
            JSONArray jadata = (JSONArray) objdata;
            if (jadata.size() == 1) {
                for (int i = 0; i < jadata.size(); i++) {
                    JSONObject jointabeldata = (JSONObject) jadata.get(i);
                    if (tabeldatalist.isEmpty()) {
                        String id_barang = String.valueOf(jointabeldata.get("id"));
                        String kode_barang = String.valueOf(jointabeldata.get("kode"));
                        String nama_barang = String.valueOf(jointabeldata.get("nama"));
                        String jumlah_qty = "1";
                        if (pane.edbarcode.getText().toLowerCase().contains("x")) {
                            String pre = pane.edbarcode.getText().toLowerCase().split("x")[0];
                            if (pre.matches("[0-9]+")) {
                                jumlah_qty = pre;
                            }
                        }
                        String jumlah = String.valueOf(ConvertFunc.ToInt(jumlah_qty));
                        String id_satuan = String.valueOf(jointabeldata.get("id_satuan"));
                        String nama_satuan = String.valueOf(jointabeldata.get("nama_satuan"));
                        String isi_satuan = String.valueOf(jointabeldata.get("qty_satuan_pengali"));
                        String id_satuan_pengali = String.valueOf(jointabeldata.get("id_satuan_pengali"));
                        String harga_beli = nf.format(ConvertFunc.ToDouble(jointabeldata.get("harga_beli")));
                        String harga_jual = nf.format(ConvertFunc.ToDouble(jointabeldata.get("harga_jual")));
                        String diskon_persen = "0";
                        String diskon_nominal = "0";
                        String id_pajak = String.valueOf(jointabeldata.get("id_pajak_jual"));
                        String nama_pajak = String.valueOf(jointabeldata.get("nama_pajak_jual"));
                        String nilai_pajak = String.valueOf(jointabeldata.get("nilai_pajak_jual"));
                        String id_gudang = String.valueOf(jointabeldata.get("id_gudang"));
                        String nama_gudang = String.valueOf(jointabeldata.get("nama_gudang"));
                        String keterangan = String.valueOf(jointabeldata.get("keterangan"));
                        String total = nf.format(kalkulasitotalperindex(diskon_persen, diskon_nominal, jumlah, harga_jual, isi_satuan));
                        tabeldatalist.add(new Entitytabledata(id_barang, kode_barang, nama_barang, jumlah, id_satuan,
                             nama_satuan, isi_satuan, id_satuan_pengali, harga_beli, harga_jual, diskon_persen, diskon_nominal, id_pajak, nama_pajak,
                             nilai_pajak, id_gudang, nama_gudang, keterangan, total));
                        int rowcount = pane.tabledata.getRowCount();
                        rowtabledata[0] = tabeldatalist.get(rowcount).getKode_barang();
                        rowtabledata[1] = tabeldatalist.get(rowcount).getNama_barang();
                        rowtabledata[2] = tabeldatalist.get(rowcount).getJumlah();
                        rowtabledata[3] = tabeldatalist.get(rowcount).getNama_satuan();
                        rowtabledata[4] = tabeldatalist.get(rowcount).getHarga_beli();
                        rowtabledata[5] = tabeldatalist.get(rowcount).getHarga_jual();
                        rowtabledata[6] = tabeldatalist.get(rowcount).getDiskon_persen();
                        rowtabledata[7] = tabeldatalist.get(rowcount).getDiskon_nominal();
                        rowtabledata[8] = tabeldatalist.get(rowcount).getNama_pajak();
                        rowtabledata[9] = tabeldatalist.get(rowcount).getNama_gudang();
                        rowtabledata[10] = tabeldatalist.get(rowcount).getKeterangan();
                        rowtabledata[11] = tabeldatalist.get(rowcount).getTotal();
                        dtmtabeldata.addRow(rowtabledata);
                        kalkulasitotal();
                    } else {
                        boolean status_ada = true;

                        for (int j = 0; j < tabeldatalist.size(); j++) {
                            if (tabeldatalist.get(j).getId_barang().matches(String.valueOf(jointabeldata.get("id")))) {
                                String jumlah_qty = "1";
                                if (pane.edbarcode.getText().toLowerCase().contains("x")) {
                                    String pre = pane.edbarcode.getText().toLowerCase().split("x")[0];
                                    if (pre.matches("[0-9]+")) {
                                        jumlah_qty = pre;
                                    }
                                }
                                int curjumlah = ConvertFunc.ToInt(tabeldatalist.get(j).getJumlah()) + ConvertFunc.ToInt(jumlah_qty);
                                tabeldatalist.get(j).setJumlah(String.valueOf(curjumlah));
                                pane.tabledata.setValueAt(String.valueOf(curjumlah), j, gx(jumlah));
                                kalkulasitotalperrow(j);
                                status_ada = true;
                                break;
                            } else {
                                status_ada = false;
                            }
                        }

                        if (status_ada == false) {
                            String id_barang = String.valueOf(jointabeldata.get("id"));
                            String kode_barang = String.valueOf(jointabeldata.get("kode"));
                            String nama_barang = String.valueOf(jointabeldata.get("nama"));
                            String jumlah_qty = "1";
                            if (pane.edbarcode.getText().toLowerCase().contains("x")) {
                                String pre = pane.edbarcode.getText().toLowerCase().split("x")[0];
                                if (pre.matches("[0-9]+")) {
                                    jumlah_qty = pre;
                                }
                            }
                            String jumlah = String.valueOf(ConvertFunc.ToInt(jumlah_qty));
                            String id_satuan = String.valueOf(jointabeldata.get("id_satuan"));
                            String nama_satuan = String.valueOf(jointabeldata.get("nama_satuan"));
                            String isi_satuan = String.valueOf(jointabeldata.get("qty_satuan_pengali"));
                            String id_satuan_pengali = String.valueOf(jointabeldata.get("id_satuan_pengali"));
                            String harga_beli = nf.format(ConvertFunc.ToDouble(jointabeldata.get("harga_beli")));
                            String harga_jual = nf.format(ConvertFunc.ToDouble(jointabeldata.get("harga_jual")));
                            String diskon_persen = "0";
                            String diskon_nominal = "0";
                            String id_pajak = String.valueOf(jointabeldata.get("id_pajak_jual"));
                            String nama_pajak = String.valueOf(jointabeldata.get("nama_pajak_jual"));
                            String nilai_pajak = String.valueOf(jointabeldata.get("nilai_pajak_jual"));
                            String id_gudang = String.valueOf(jointabeldata.get("id_gudang"));
                            String nama_gudang = String.valueOf(jointabeldata.get("nama_gudang"));
                            String keterangan = String.valueOf(jointabeldata.get("keterangan"));
                            String total = nf.format(kalkulasitotalperindex(diskon_persen, diskon_nominal, jumlah, harga_jual, isi_satuan));
                            tabeldatalist.add(new Entitytabledata(id_barang, kode_barang, nama_barang, jumlah, id_satuan,
                                 nama_satuan, isi_satuan, id_satuan_pengali, harga_beli, harga_jual, diskon_persen, diskon_nominal, id_pajak, nama_pajak,
                                 nilai_pajak, id_gudang, nama_gudang, keterangan, total));
                            int rowcount = pane.tabledata.getRowCount();
                            rowtabledata[0] = tabeldatalist.get(rowcount).getKode_barang();
                            rowtabledata[1] = tabeldatalist.get(rowcount).getNama_barang();
                            rowtabledata[2] = tabeldatalist.get(rowcount).getJumlah();
                            rowtabledata[3] = tabeldatalist.get(rowcount).getNama_satuan();
                            rowtabledata[4] = tabeldatalist.get(rowcount).getHarga_beli();
                            rowtabledata[5] = tabeldatalist.get(rowcount).getHarga_jual();
                            rowtabledata[6] = tabeldatalist.get(rowcount).getDiskon_persen();
                            rowtabledata[7] = tabeldatalist.get(rowcount).getDiskon_nominal();
                            rowtabledata[8] = tabeldatalist.get(rowcount).getNama_pajak();
                            rowtabledata[9] = tabeldatalist.get(rowcount).getNama_gudang();
                            rowtabledata[10] = tabeldatalist.get(rowcount).getKeterangan();
                            rowtabledata[11] = tabeldatalist.get(rowcount).getTotal();
                            dtmtabeldata.addRow(rowtabledata);
                            kalkulasitotal();

                        }
                    }
                }

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
                        JSONObject jointabeldata = (JSONObject) jadata2.get(i);
                        if (tabeldatalist.isEmpty()) {
                            String id_barang = String.valueOf(jointabeldata.get("id"));
                            String kode_barang = String.valueOf(jointabeldata.get("kode"));

                            String nama_barang = String.valueOf(jointabeldata.get("nama"));
                            String jumlah_qty = "1";

                            if (pane.edbarcode.getText().toLowerCase().contains("x")) {
                                String pre = pane.edbarcode.getText().toLowerCase().split("x")[0];
                                if (pre.matches("[0-9]+")) {
                                    jumlah_qty = pre;
                                }
                            }
                            String jumlah = String.valueOf(ConvertFunc.ToInt(jumlah_qty));
                            String id_satuan = String.valueOf(jointabeldata.get("id_satuan"));
                            String nama_satuan = String.valueOf(jointabeldata.get("nama_satuan"));
                            String isi_satuan = String.valueOf(jointabeldata.get("qty_satuan_pengali"));
                            String id_satuan_pengali = String.valueOf(jointabeldata.get("id_satuan_pengali"));
                            String harga_beli = nf.format(ConvertFunc.ToDouble(jointabeldata.get("harga_beli")));
                            String harga_jual = nf.format(ConvertFunc.ToDouble(jointabeldata.get("harga_jual")));
                            String diskon_persen = "0";
                            String diskon_nominal = "0";
                            String id_pajak = String.valueOf(jointabeldata.get("id_pajak_jual"));
                            String nama_pajak = String.valueOf(jointabeldata.get("nama_pajak_jual"));
                            String nilai_pajak = String.valueOf(jointabeldata.get("nilai_pajak_jual"));
                            String id_gudang = String.valueOf(jointabeldata.get("id_gudang"));
                            String nama_gudang = String.valueOf(jointabeldata.get("nama_gudang"));
                            String keterangan = String.valueOf(jointabeldata.get("keterangan"));
                            String total = nf.format(kalkulasitotalperindex(diskon_persen, diskon_nominal, jumlah, harga_jual, isi_satuan));
                            tabeldatalist.add(new Entitytabledata(id_barang, kode_barang, nama_barang, jumlah, id_satuan,
                                 nama_satuan, isi_satuan, id_satuan_pengali, harga_beli, harga_jual, diskon_persen, diskon_nominal, id_pajak, nama_pajak,
                                 nilai_pajak, id_gudang, nama_gudang, keterangan, total));
                            int rowcount = pane.tabledata.getRowCount();
                            rowtabledata[0] = tabeldatalist.get(rowcount).getKode_barang();
                            rowtabledata[1] = tabeldatalist.get(rowcount).getNama_barang();
                            rowtabledata[2] = tabeldatalist.get(rowcount).getJumlah();
                            rowtabledata[3] = tabeldatalist.get(rowcount).getNama_satuan();
                            rowtabledata[4] = tabeldatalist.get(rowcount).getHarga_beli();
                            rowtabledata[5] = tabeldatalist.get(rowcount).getHarga_jual();
                            rowtabledata[6] = tabeldatalist.get(rowcount).getDiskon_persen();
                            rowtabledata[7] = tabeldatalist.get(rowcount).getDiskon_nominal();
                            rowtabledata[8] = tabeldatalist.get(rowcount).getNama_pajak();
                            rowtabledata[9] = tabeldatalist.get(rowcount).getNama_gudang();
                            rowtabledata[10] = tabeldatalist.get(rowcount).getKeterangan();
                            rowtabledata[11] = tabeldatalist.get(rowcount).getTotal();
                            dtmtabeldata.addRow(rowtabledata);
                            kalkulasitotal();
                            pane.edbarcode.setText(kode_barang);
                        } else {
                            boolean status_ada = true;

                            for (int j = 0; j < tabeldatalist.size(); j++) {
                                if (tabeldatalist.get(j).getId_barang().matches(String.valueOf(jointabeldata.get("id")))) {
                                    String jumlah_qty = "1";
                                    if (pane.edbarcode.getText().toLowerCase().contains("x")) {
                                        String pre = pane.edbarcode.getText().toLowerCase().split("x")[0];
                                        if (pre.matches("[0-9]+")) {
                                            jumlah_qty = pre;
                                        }
                                    }
                                    int curjumlah = ConvertFunc.ToInt(tabeldatalist.get(j).getJumlah()) + ConvertFunc.ToInt(jumlah_qty);
                                    tabeldatalist.get(j).setJumlah(String.valueOf(curjumlah));
                                    pane.tabledata.setValueAt(String.valueOf(curjumlah), j, gx(jumlah));
                                    kalkulasitotalperrow(j);
                                    status_ada = true;
                                    break;
                                } else {
                                    status_ada = false;
                                }
                            }

                            if (status_ada == false) {
                                String id_barang = String.valueOf(jointabeldata.get("id"));
                                String kode_barang = String.valueOf(jointabeldata.get("kode"));

                                String nama_barang = String.valueOf(jointabeldata.get("nama"));
                                String jumlah_qty = "1";
                                if (pane.edbarcode.getText().toLowerCase().contains("x")) {
                                    String pre = pane.edbarcode.getText().toLowerCase().split("x")[0];
                                    if (pre.matches("[0-9]+")) {
                                        jumlah_qty = pre;
                                    }
                                }
                                String jumlah = String.valueOf(ConvertFunc.ToInt(jumlah_qty));
                                String id_satuan = String.valueOf(jointabeldata.get("id_satuan"));
                                String nama_satuan = String.valueOf(jointabeldata.get("nama_satuan"));
                                String isi_satuan = String.valueOf(jointabeldata.get("qty_satuan_pengali"));
                                String id_satuan_pengali = String.valueOf(jointabeldata.get("id_satuan_pengali"));
                                String harga_beli = nf.format(ConvertFunc.ToDouble(jointabeldata.get("harga_beli")));
                                String harga_jual = nf.format(ConvertFunc.ToDouble(jointabeldata.get("harga_jual")));
                                String diskon_persen = "0";
                                String diskon_nominal = "0";
                                String id_pajak = String.valueOf(jointabeldata.get("id_pajak_jual"));
                                String nama_pajak = String.valueOf(jointabeldata.get("nama_pajak_jual"));
                                String nilai_pajak = String.valueOf(jointabeldata.get("nilai_pajak_jual"));
                                String id_gudang = String.valueOf(jointabeldata.get("id_gudang"));
                                String nama_gudang = String.valueOf(jointabeldata.get("nama_gudang"));
                                String keterangan = String.valueOf(jointabeldata.get("keterangan"));
                                String total = nf.format(kalkulasitotalperindex(diskon_persen, diskon_nominal, jumlah, harga_jual, isi_satuan));
                                tabeldatalist.add(new Entitytabledata(id_barang, kode_barang, nama_barang, jumlah, id_satuan,
                                     nama_satuan, isi_satuan, id_satuan_pengali, harga_beli, harga_jual, diskon_persen, diskon_nominal, id_pajak, nama_pajak,
                                     nilai_pajak, id_gudang, nama_gudang, keterangan, total));
                                int rowcount = pane.tabledata.getRowCount();
                                rowtabledata[0] = tabeldatalist.get(rowcount).getKode_barang();
                                rowtabledata[1] = tabeldatalist.get(rowcount).getNama_barang();
                                rowtabledata[2] = tabeldatalist.get(rowcount).getJumlah();
                                rowtabledata[3] = tabeldatalist.get(rowcount).getNama_satuan();
                                rowtabledata[4] = tabeldatalist.get(rowcount).getHarga_beli();
                                rowtabledata[5] = tabeldatalist.get(rowcount).getHarga_jual();
                                rowtabledata[6] = tabeldatalist.get(rowcount).getDiskon_persen();
                                rowtabledata[7] = tabeldatalist.get(rowcount).getDiskon_nominal();
                                rowtabledata[8] = tabeldatalist.get(rowcount).getNama_pajak();
                                rowtabledata[9] = tabeldatalist.get(rowcount).getNama_gudang();
                                rowtabledata[10] = tabeldatalist.get(rowcount).getKeterangan();
                                rowtabledata[11] = tabeldatalist.get(rowcount).getTotal();
                                dtmtabeldata.addRow(rowtabledata);
                                kalkulasitotal();
                                pane.edbarcode.setText(kode_barang);
                            }
                        }
                    }
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(PosframeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        pane.edbarcode.setText("");
        pane.edbarcode.requestFocus();
    }

    private void inserttorow() {

    }

    private void addautorow(int row) {
        int lastrow = pane.tabledata.getRowCount() - 1;
        if (!pane.tabledata.getValueAt(row, gx(kode)).equals("")
             || !pane.tabledata.getValueAt(row, gx(jumlah)).equals("")
             || !pane.tabledata.getValueAt(row, gx(satuan)).equals("")
             || !pane.tabledata.getValueAt(row, gx(diskon_persen)).equals("")
             || !pane.tabledata.getValueAt(row, gx(diskon_nominal)).equals("")) {
            if (row == lastrow) {
                tabeldatalist.add(new Entitytabledata("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
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
        if (pane.tabledata.getValueAt(row, col).equals("")) {
            return;
        }
        if ((col == gx(jumlah)) || (col == gx(harga_jual)) || (col == gx(diskon_nominal))) {
            String value = nf.format(ConvertFunc.ToDouble(pane.tabledata.getValueAt(row, col)));
            pane.tabledata.setValueAt(value, row, col);
            kalkulasitotalperrow(row);
            if (addrow == true) {
                addautorow(row);
            }
        } else if (col == gx(diskon_persen)) {
            String value = String.valueOf(pane.tabledata.getValueAt(row, col));
            pane.tabledata.setValueAt(value, row, col);
            kalkulasitotalperrow(row);
            if (addrow == true) {
                addautorow(row);
            }
        }
    }

    private void kalkulasitotal() {
        int jumlah_row = pane.tabledata.getRowCount();
        double subtotal = 0;
        total_penjualan_all = 0;
        total_pajak = 0;

        for (int i = 0; i < jumlah_row; i++) {
            double total_beli_masing = ConvertFunc.ToDouble(emptycellcheck(i, 11));
            subtotal = subtotal + total_beli_masing;

            double total_pajak_masing = ConvertFunc.ToDouble(emptycellcheck(i, 11)) * (ConvertFunc.ToDouble(tabeldatalist.get(i).getNilai_pajak()) / 100);
            total_pajak = total_pajak + total_pajak_masing;
        }

        pane.ltotal_pajak.setText(nf.format(total_pajak));
        pane.lsubtotal.setText(nf.format(subtotal));
        total_penjualan_all = subtotal + ConvertFunc.ToDouble(pajak);

        pane.ltotal_penjualan.setText(nf.format(total_penjualan_all));
        pane.ltota_lbelanja.setText(nf.format(total_penjualan_all));
    }

    private void kalkulasitotalperrow(int row) {
        if (pane.ckdiskon.isSelected() == true) {
            String isifielddiskon = String.valueOf(pane.tabledata.getValueAt(row, gx(diskon_persen)));
            if (isifielddiskon.contains("+")) {
                double qty = ConvertFunc.ToDouble(String.valueOf(pane.tabledata.getValueAt(row, gx(jumlah))));
                double harga = ConvertFunc.ToDouble(pane.tabledata.getValueAt(row, gx(harga_jual)));
                double intotal = harga;
                String s = "";
                String[] multidiskon = isifielddiskon.split("\\+");
                for (int i = 0; i < multidiskon.length; i++) {
                    double diskonper = ConvertFunc.ToDouble(multidiskon[i]);
                    intotal = ((intotal - (diskonper / 100 * intotal)));
                }
                intotal = qty * intotal;
                tabeldatalist.get(row).setTotal(String.valueOf(intotal));
                pane.tabledata.setValueAt(nf.format(intotal), row, gx(total));
            } else {
                double qty = ConvertFunc.ToDouble(String.valueOf(pane.tabledata.getValueAt(row, gx(jumlah))));
                double harga = ConvertFunc.ToDouble(String.valueOf(pane.tabledata.getValueAt(row, gx(harga_jual))));
                double diskon = ConvertFunc.ToDouble(emptycellcheck(row, gx(diskon_persen)));
                double intotal = qty * (harga - (diskon / 100 * harga));
                tabeldatalist.get(row).setTotal(String.valueOf(total));
                pane.tabledata.setValueAt(nf.format(intotal), row, gx(total));
            }
        } else {

            double qty = ConvertFunc.ToDouble(String.valueOf(pane.tabledata.getValueAt(row, gx(jumlah))));
            double harga = ConvertFunc.ToDouble(pane.tabledata.getValueAt(row, gx(harga_jual)));
            double diskon = ConvertFunc.ToDouble(emptycellcheck(row, gx(diskon_nominal)));
            double intotal = qty * (harga - diskon);
            tabeldatalist.get(row).setTotal(String.valueOf(intotal));
            pane.tabledata.setValueAt(nf.format(intotal), row, gx(total));
        }
        kalkulasitotal();
    }

    private double kalkulasitotalperindex(String rawdiskonpersen, String rawdiskonnominal, String rawqty, String rawharga, String isisatuan) {
        double total = 0;
        if (pane.ckdiskon.isSelected() == true) {
            String isifielddiskon = rawdiskonpersen;
            if (isifielddiskon.contains("+")) {
                double qty = ConvertFunc.ToDouble(rawqty);
                double harga = ConvertFunc.ToDouble(rawharga);
                total = harga;
                String[] multidiskon = isifielddiskon.split("\\+");
                for (int i = 0; i < multidiskon.length; i++) {
                    double diskonper = ConvertFunc.ToDouble(multidiskon[i]);
                    total = ((total - (diskonper / 100 * total)));
                }
                total = qty * total;
            } else {
                double qty = ConvertFunc.ToDouble(rawqty);
                double harga = ConvertFunc.ToDouble(rawharga);
                double diskon = ConvertFunc.ToDouble(rawdiskonpersen);
                total = qty * (harga - (diskon / 100 * harga));
            }
        } else {

            double qty = ConvertFunc.ToDouble(rawqty);
            double harga = ConvertFunc.ToDouble(rawharga);
            double diskon = ConvertFunc.ToDouble(rawdiskonnominal);
            total = qty * (harga - diskon);
        }
        return total;
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

    private boolean cekcolomnol(int colom) {
        boolean result = false;
        if (lshide.get(colom) == 0) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    private double gethargajual(String id_inv, String id_satuan, String qty) {
        String param = String.format("id_golongan=%s&id_inv=%s&id_satuan=%s&qty=%s", valgolongan, id_inv, id_satuan, qty);
        String hargajual = ch.getdatadetails("gethargajual", param);
        return Double.parseDouble(hargajual);
    }

    private int gx(String columname) {
        return listheadername.indexOf(columname);
    }

    public class Entitytabledata {

        String id_barang, kode_barang, nama_barang, jumlah,
             id_satuan, nama_satuan, isi_satuan, id_satuan_pengali, harga_beli, harga_jual, diskon_persen, diskon_nominal,
             id_pajak, nama_pajak, nilai_pajak, id_gudang, nama_gudang, keterangan, total;

        public Entitytabledata(String id_barang, String kode_barang, String nama_barang, String jumlah, String id_satuan, String nama_satuan, String isi_satuan, String id_satuan_pengali, String harga_beli, String harga_jual, String diskon_persen, String diskon_nominal, String id_pajak, String nama_pajak, String nilai_pajak, String id_gudang, String nama_gudang, String keterangan, String total) {
            this.id_barang = id_barang;
            this.kode_barang = kode_barang;
            this.nama_barang = nama_barang;
            this.jumlah = jumlah;
            this.id_satuan = id_satuan;
            this.nama_satuan = nama_satuan;
            this.isi_satuan = isi_satuan;
            this.id_satuan_pengali = id_satuan_pengali;
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

        public String getId_satuan_pengali() {
            return id_satuan_pengali;
        }

        public void setId_satuan_pengali(String id_satuan_pengali) {
            this.id_satuan_pengali = id_satuan_pengali;
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