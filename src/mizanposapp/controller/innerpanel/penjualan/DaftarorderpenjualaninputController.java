/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.penjualan;

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
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
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
import mizanposapp.controller.innerpanel.penjualan.DaftarorderpenjualaninnerController;
import mizanposapp.helper.FuncHelper;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Globalsession;
import mizanposapp.helper.Staticvar;
import mizanposapp.helper.Tablestyle;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Errorpanel;
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.penjualan.Daftarorderpenjualan_inner_panel;
import mizanposapp.view.innerpanel.penjualan.Daftarorderpenjualan_input_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class DaftarorderpenjualaninputController {

    String id;
    CrudHelper ch = new CrudHelper();
    Daftarorderpenjualan_input_panel pane;
    String valpelanggan = "", valgudang = "", valdept = "", valsalesman = "", valshipvia = "", valtop = "",
         valakun_penjualan = "", valakun_ongkir = "", valakun_diskon = "", valakun_uang_muka = "", valgolongan = "",
         valuser_input = "";
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
    String stok = "stok";
    String jumlah = "jumlah";
    String satuan = "satuan";
    String harga_jual = "harga_jual";
    String diskon_persen = "disc_persen";
    String diskon_nominal = "disc_rp";
    String pajak = "pajak";
    String gudang = "gudang";
    String keterangan = "keterangan";
    String total = "total";

    public DaftarorderpenjualaninputController(Daftarorderpenjualan_input_panel pane) {
        this.pane = pane;
        Staticvar.inputmode = true;
        skinning();
        loadsession();
        loaddata();
        simpandata();
        checkandcombocontrol();
        caripelanggan();
        carigudang();
        caridepartment();
        carisalesman();
        carishipvia();
        caritop();
        cariakunpenjualan();
        cariakundiskon();
        cariakunongkir();
        cariakunuangmuka();
        addtotable();
        kalkulasi();
        hapusbaris();
        tambahbaris();
        batal();
        gantitanggal();
        pane.tabdata.setEnabledAt(1, false);

    }

    private void loadsession() {
        pane.edakun_penjualan.setText(Globalsession.AKUNPENJUALANTUNAI + "-" + Globalsession.NAMAAKUNPENJUALANTUNAI);
        pane.edakun_uang_muka.setText(Globalsession.AKUNUANGMUKAPENJUALAN + "-" + Globalsession.NAMAAKUNUANGMUKAPENJUALAN);
        pane.edakun_diskon_penjualan.setText(Globalsession.AKUNDISKONPENJUALAN + "-" + Globalsession.NAMAAKUNDISKONPENJUALAN);
        pane.edakun_ongkir.setText(Globalsession.AKUNONGKOSKIRIMPENJUALAN + "-" + Globalsession.NAMAAKUNONGKOSKIRIMPENJUALAN);
        valakun_penjualan = Globalsession.AKUNPENJUALANTUNAI;
        valakun_uang_muka = Globalsession.AKUNUANGMUKAPENJUALAN;
        valakun_diskon = Globalsession.AKUNDISKONPENJUALAN;
        valakun_ongkir = Globalsession.AKUNONGKOSKIRIMPENJUALAN;
        pane.edpelanggan.setText(Globalsession.Penjualan_PelangganUmumnama);
        valpelanggan = Globalsession.Penjualan_PelangganUmum;
        pane.eduser_input.setText(Globalsession.nama_user);
        valuser_input = Globalsession.id_user;
    }

    private void skinning() {
        pane.dtanggal.setDateFormatString("dd MMMM yyyy");
        pane.dtanggal_pengantaran.setDateFormatString("dd MMMM yyyy");
        pane.dtanggal.setDate(new Date());
        pane.dtanggal_pengantaran.setDate(new Date());
        pane.dtanggal.getDateEditor().setEnabled(false);
        pane.cmb_tipe_bayar.setVisible(false);

    }

    private void getkodetransaksi() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                HashMap hm = new FuncHelper().getkodetransaksi("22", new Date(), valdept);
                pane.edno_transaksi.setText(String.valueOf(hm.get("no_transaksi")));
                no_urut = FuncHelper.ToInt(String.valueOf(hm.get("no_urut")));
            }
        });

    }

    private void gantitanggal() {
        pane.dtanggal.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    int tahunbulan = Integer.parseInt(new SimpleDateFormat("yyyyM").format(pane.dtanggal.getDate()));
                    int periodetahunbulan = Integer.parseInt(Globalsession.periode_year + Globalsession.periode_month);
                    if (tahunbulan >= periodetahunbulan) {
                        Date zaman_old = (Date) evt.getOldValue();
                        Date zaman_now = (Date) evt.getNewValue();
                        int tahunbulanold = Integer.parseInt(new SimpleDateFormat("yyyyM").format(zaman_old));
                        int tahunbulannow = Integer.parseInt(new SimpleDateFormat("yyyyM").format(zaman_now));
                        if (tahunbulannow != tahunbulanold) {
                            new FuncHelper().insertnogagal("22", zaman_old, valdept, String.valueOf(no_urut));
                            HashMap hm = new FuncHelper().getkodetransaksi("22", zaman_now, valdept);
                            pane.edno_transaksi.setText(String.valueOf(hm.get("no_transaksi")));
                            no_urut = FuncHelper.ToInt(String.valueOf(hm.get("no_urut")));
                        }

                    } else {
                        FuncHelper.info("Proses Ditolak", "Tanggal input tidak boleh lebih kecil dari tanggal periode");
                        pane.dtanggal.setDate(new Date());
                    }

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
                return column == gx(nama) || column == gx(satuan) || column == gx(stok) || column == gx(pajak) || column == gx(gudang) || column == gx(total) ? false : true;
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
            "0", "NUMPAD1", "NUMPAD2", "NUMPAD3", "NUMPAD4", "NUMPAD5", "NUMPAD6", "NUMPAD7", "NUMPAD8", "NUMPAD9",
            "NUMPAD0", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
            "q", "r", "s", "t", "u", "p", "w", "x", "y", "z", "A", "B", "C", "D",
            "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "P", "W", "X", "Y", "Z", "BACK_SPACE", ",", ".",};
        for (int i = 0; i < keyholdnumeric.length; i++) {
            pane.tabledata.getInputMap().put(KeyStroke.getKeyStroke(keyholdnumeric[i]), "startEditing");
        }

    }

    private void checkandcombocontrol() {

        pane.ckselesai.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox cb = (JCheckBox) e.getSource();
                if (cb.isSelected()) {
                    status_selesai = 1;
                } else {
                    status_selesai = 0;
                }
            }
        });

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

        pane.cmb_tipe_bayar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pane.cmb_tipe_bayar.getSelectedIndex() == 0) {
                    pane.edtop.setVisible(false);
                    pane.bcaritop.setVisible(false);
                    pane.ltop.setVisible(false);
                    tipe_bayar = 0;
                    valtop = "";
                    pane.edakun_penjualan.setText(Globalsession.AKUNPENJUALANTUNAI + "-" + Globalsession.NAMAAKUNPENJUALANTUNAI);
                    valakun_penjualan = Globalsession.AKUNPENJUALANTUNAI;
                } else {
                    pane.edtop.setVisible(true);
                    pane.bcaritop.setVisible(true);
                    pane.ltop.setVisible(true);
                    tipe_bayar = 1;
                    valtop = "";
                    pane.edakun_penjualan.setText(Globalsession.AKUNPIUTANGUSAHA + "-" + Globalsession.NAMAAKUNPIUTANGUSAHA);
                    valakun_penjualan = Globalsession.AKUNPIUTANGUSAHA;
                }
            }
        });

        pane.cmb_tipe_penjualan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox jc = (JComboBox) e.getSource();
                if (jc.getSelectedIndex() == 0) {
                    pane.lgudang.setVisible(true);
                    pane.ltitik2gudang.setVisible(true);
                    pane.edgudang.setVisible(true);
                    pane.bcari_gudang.setVisible(true);
                    valgudang = "";
                    pane.edgudang.setText("");
                    tipe_jual = 0;
                    lshide.set(gx(satuan), lsoldhide.get(gx(satuan)));
                    lshide.set(gx(stok), lsoldhide.get(gx(stok)));
                    lshide.set(gx(gudang), lsoldhide.get(gx(gudang)));
                    lsresize.set(gx(satuan), lsoldsize.get(gx(satuan)));
                    lsresize.set(gx(gudang), lsoldsize.get(gx(gudang)));
                    lsresize.set(gx(stok), lsoldsize.get(gx(stok)));
                    setheader();
                    setheader();
                    if (pane.ckdiskon.isSelected()) {
                        hidetable(gx(diskon_nominal));
                        showtable(gx(diskon_persen));
                        valcheck = 0;

                    } else {
                        hidetable(gx(diskon_persen));
                        showtable(gx(diskon_nominal));
                        valcheck = 1;
                    }
                } else {
                    pane.lgudang.setVisible(false);
                    pane.ltitik2gudang.setVisible(false);
                    pane.edgudang.setVisible(false);
                    pane.bcari_gudang.setVisible(false);
                    pane.edgudang.setText(Globalsession.Setting_GudangDefaultnama);
                    valgudang = Globalsession.Setting_GudangDefault;
                    tipe_jual = 1;
                    lshide.set(gx(satuan), 0);
                    lshide.set(gx(gudang), 0);
                    lshide.set(gx(stok), 0);
                    lsresize.set(gx(satuan), 0);
                    lsresize.set(gx(gudang), 0);
                    lsresize.set(gx(stok), 0);
                    setheader();
                    setheader();
                    if (pane.ckdiskon.isSelected()) {
                        hidetable(gx(diskon_nominal));
                        showtable(gx(diskon_persen));
                        valcheck = 0;

                    } else {
                        hidetable(gx(diskon_persen));
                        showtable(gx(diskon_nominal));
                        valcheck = 1;
                    }
                }

                if (sudah_jangan_set_lagi_kau_membuat_semua_kacau == true) {
                    sudah_jangan_set_lagi_kau_membuat_semua_kacau = false;
                    return;
                }

                Runnable rn = new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < pane.tabledata.getRowCount(); i++) {
                            dtmtabeldata.removeRow(0);
                        }
                        tabeldatalist.clear();
                        dtmtabeldata.setRowCount(0);
                        tabeldatalist.add(new Entitytabledata("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
                        dtmtabeldata.addRow(rowtabledata);
                    }
                };
                SwingUtilities.invokeLater(rn);

            }
        }
        );

    }

    private void loadheader() {
        try {
            pane.tabledata.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            pane.tabledata.setModel(dtmtabeldata);
            String dataheader = ch.getheaderinputs();
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
                lsoldsize.add(Integer.parseInt(String.valueOf(jaaray.get(3))));
                lsresize.add(Integer.parseInt(String.valueOf(jaaray.get(3))));
                lsoldhide.add(Integer.parseInt(String.valueOf(jaaray.get(2))));
                lshide.add(Integer.parseInt(String.valueOf(jaaray.get(2))));
            }

            setheader();
        } catch (ParseException ex) {
            Logger.getLogger(DaftarorderpenjualaninnerController.class.getName()).log(Level.SEVERE, null, ex);
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
                int wi = FuncHelper.ToInt(pembagi * setsize);
                tcm.getColumn(i).setMinWidth(wi);
                tcm.getColumn(i).setWidth(wi);
                tcm.getColumn(i).setMaxWidth(wi);

                lebarAllNew = lebarAllNew + wi;
            } else {
                int wi = FuncHelper.ToInt(lebar - lebarAllNew);
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
                pane.ckselesai.setSelected(false);
                status_selesai = 0;
                pane.cmb_tipe_penjualan.setSelectedIndex(0);
                pane.edtop.setVisible(false);
                pane.bcaritop.setVisible(false);
                pane.ltop.setVisible(false);
                tipe_jual = 0;
                pane.cmb_tipe_bayar.setSelectedIndex(0);
                pane.dtanggal.setDate(new Date());
                pane.dtanggal_pengantaran.setDate(new Date());
                pane.edpelanggan.setText(Globalsession.Penjualan_PelangganUmumnama);
                valpelanggan = Globalsession.Penjualan_PelangganUmum;
                pane.edno_transaksi.setText("");
                pane.eddept.setText(Globalsession.Setting_DeptDefaultnama);
                valdept = Globalsession.Setting_DeptDefault;
                pane.edgudang.setText(Globalsession.Setting_GudangDefaultnama);
                valgudang = Globalsession.Setting_GudangDefault;
                pane.edketerangan.setText("");
                pane.edsalesman.setText("");
                valsalesman = "";
                pane.edshipvia.setText("");
                valshipvia = "";
                pane.edtop.setText("");
                valtop = "";
                pane.lsubtotal.setText("0");
                pane.edbiayalain.setText("0");
                pane.eddiskon1.setText("0");
                pane.eddiskon2.setText("0");
                pane.ltotal_pajak.setText("0");
                pane.ltotal_penjualan.setText("0");

                tabeldatalist.add(new Entitytabledata("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
                dtmtabeldata.addRow(rowtabledata);
                //pane.tabledata.setModel(dtmtabeldata);
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
                    valuser_input = String.valueOf(joingenjur.get("id_user"));
                    pane.eduser_input.setText(String.valueOf(joingenjur.get("nama_user")));
                    try {
                        pane.dtanggal.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(joingenjur.get("tanggal"))));
                    } catch (java.text.ParseException ex) {
                        Logger.getLogger(DaftarorderpenjualaninputController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

                Object jopenjualan = jsonobjdata.get("penjualan");
                JSONArray japenjualan = (JSONArray) jopenjualan;

                for (int i = 0; i < japenjualan.size(); i++) {
                    JSONObject joinpenjualan = (JSONObject) japenjualan.get(i);
                    valpelanggan = String.valueOf(joinpenjualan.get("id_pelanggan"));
                    valgolongan = String.valueOf(joinpenjualan.get("id_golongan"));
                    pane.edpelanggan.setText(String.valueOf(joinpenjualan.get("nama_pelanggan")));

                    status_selesai = FuncHelper.ToInt(joinpenjualan.get("isorderselesai"));
                    if (status_selesai == 0) {
                        pane.ckselesai.setSelected(false);
                    } else {
                        pane.ckselesai.setSelected(true);
                    }

                    tipe_bayar = FuncHelper.ToInt(joinpenjualan.get("tipe_pembayaran"));
                    if (tipe_bayar == 0) {
                        pane.cmb_tipe_bayar.setSelectedIndex(0);
                    } else {
                        pane.cmb_tipe_bayar.setSelectedIndex(1);
                    }

                    if (pane.cmb_tipe_bayar.getSelectedIndex() == 0) {
                        pane.edtop.setVisible(false);
                        pane.bcaritop.setVisible(false);
                        pane.ltop.setVisible(false);
                        valtop = "";
                        pane.edakun_penjualan.setText(Globalsession.AKUNPENJUALANTUNAI + "-" + Globalsession.NAMAAKUNPENJUALANTUNAI);
                    } else {
                        pane.edtop.setVisible(true);
                        pane.bcaritop.setVisible(true);
                        pane.ltop.setVisible(true);
                        valtop = "";
                        pane.edakun_penjualan.setText(Globalsession.AKUNPIUTANGUSAHA + "-" + Globalsession.NAMAAKUNPIUTANGUSAHA);
                    }

                    tipe_jual = FuncHelper.ToInt(joinpenjualan.get("tipe_penjualan"));
                    if (tipe_jual == 0) {
                        pane.cmb_tipe_penjualan.setSelectedIndex(0);
                    } else {
                        pane.cmb_tipe_penjualan.setSelectedIndex(1);
                    }

                    if (pane.cmb_tipe_penjualan.getSelectedIndex() == 0) {
                        pane.lgudang.setVisible(true);
                        pane.ltitik2gudang.setVisible(true);
                        pane.edgudang.setVisible(true);
                        pane.bcari_gudang.setVisible(true);
                        valgudang = "";
                        pane.edgudang.setText("");
                        tipe_jual = 0;
                        lshide.set(gx(satuan), lsoldhide.get(gx(satuan)));
                        lsresize.set(gx(satuan), lsoldsize.get(gx(satuan)));

                        lshide.set(gx(gudang), lsoldhide.get(gx(gudang)));
                        lsresize.set(gx(gudang), lsoldsize.get(gx(gudang)));

                        lshide.set(gx(stok), lsoldhide.get(gx(stok)));
                        lsresize.set(gx(stok), lsoldsize.get(gx(stok)));

                        setheader();
                        setheader();
                        if (pane.ckdiskon.isSelected()) {
                            hidetable(gx(diskon_nominal));
                            showtable(gx(diskon_persen));
                            valcheck = 0;

                        } else {
                            hidetable(gx(diskon_persen));
                            showtable(gx(diskon_nominal));
                            valcheck = 1;
                        }
                    } else {
                        pane.lgudang.setVisible(false);
                        pane.ltitik2gudang.setVisible(false);
                        pane.edgudang.setVisible(false);
                        pane.bcari_gudang.setVisible(false);
                        pane.edgudang.setText(Globalsession.Setting_GudangDefaultnama);
                        valgudang = Globalsession.Setting_GudangDefault;
                        tipe_jual = 1;
                        lshide.set(gx(satuan), 0);
                        lshide.set(gx(gudang), 0);
                        lshide.set(gx(stok), 0);
                        lsresize.set(gx(satuan), 0);
                        lsresize.set(gx(gudang), 0);
                        lsresize.set(gx(stok), 0);
                        setheader();
                        setheader();
                        if (pane.ckdiskon.isSelected()) {
                            hidetable(gx(diskon_nominal));
                            showtable(gx(diskon_persen));
                            valcheck = 0;

                        } else {
                            hidetable(gx(diskon_persen));
                            showtable(gx(diskon_nominal));
                            valcheck = 1;
                        }
                    }

                    try {
                        pane.dtanggal_pengantaran.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(String.valueOf(joinpenjualan.get("tanggal_pengantaran")))));
                    } catch (java.text.ParseException ex) {
                        Logger.getLogger(DaftarorderpenjualaninputController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    valshipvia = String.valueOf(joinpenjualan.get("id_pengantaran"));
                    pane.edshipvia.setText(String.valueOf(joinpenjualan.get("nama_pengantaran")));

                    valgudang = String.valueOf(joinpenjualan.get("id_gudang"));
                    pane.edgudang.setText(String.valueOf(joinpenjualan.get("nama_gudang")));

                    valtop = String.valueOf(joinpenjualan.get("id_termofpayment"));
                    pane.edtop.setText(String.valueOf(joinpenjualan.get("top")));

                    pane.ltotal_penjualan.setText(String.valueOf(joinpenjualan.get("total_penjualan")));

                    pane.edbiayalain.setText(String.valueOf(joinpenjualan.get("total_biaya")));
                    pane.eddiskon1.setText(String.valueOf(joinpenjualan.get("diskon_persen")));
                    pane.eddiskon2.setText(String.valueOf(joinpenjualan.get("diskon_nominal")));

                    pane.ltotal_pajak.setText(String.valueOf(joinpenjualan.get("total_pajak")));

                    pane.edakun_penjualan.setText(String.valueOf(joinpenjualan.get("akun_penjualan")) + "-"
                         + String.valueOf(joinpenjualan.get("nama_akun_penjualan")));

                    valakun_penjualan = String.valueOf(joinpenjualan.get("akun_penjualan"));

                    pane.edakun_ongkir.setText(String.valueOf(joinpenjualan.get("akun_biaya")) + "-"
                         + String.valueOf(joinpenjualan.get("nama_akun_biaya")));

                    valakun_ongkir = String.valueOf(joinpenjualan.get("akun_biaya"));

                    pane.edakun_diskon_penjualan.setText(String.valueOf(joinpenjualan.get("akun_diskon")) + "-"
                         + String.valueOf(joinpenjualan.get("nama_akun_diskon")));

                    valakun_diskon = String.valueOf(joinpenjualan.get("akun_diskon"));

                    pane.edakun_uang_muka.setText(String.valueOf(joinpenjualan.get("akun_uang_muka")) + "-"
                         + String.valueOf(joinpenjualan.get("nama_akun_uang_muka")));

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

                    tipe_jual = FuncHelper.ToInt(joinpenjualan.get("tipe_penjualan"));
                    pane.cmb_tipe_penjualan.setSelectedIndex(tipe_jual);

                }

                Object objtabeldata = jsonobjdata.get("penjualan_detail");
                JSONArray jatabledata = (JSONArray) objtabeldata;
                for (int i = 0; i < jatabledata.size(); i++) {
                    JSONObject jointabeldata = (JSONObject) jatabledata.get(i);
                    String id_barang = "";
                    String kode_barang = "";
                    String nama_barang = "";
                    if (pane.cmb_tipe_penjualan.getSelectedIndex() == 0) {
                        id_barang = String.valueOf(jointabeldata.get("id_inv"));
                        kode_barang = String.valueOf(jointabeldata.get("kode_inv"));
                        nama_barang = String.valueOf(jointabeldata.get("nama_inv"));
                    } else {
                        id_barang = String.valueOf(jointabeldata.get("akun"));
                        kode_barang = String.valueOf(jointabeldata.get("akun"));
                        nama_barang = String.valueOf(jointabeldata.get("nama_akun"));
                    }
                    String stok = String.valueOf(jointabeldata.get("stok"));
                    String jumlah = String.valueOf(jointabeldata.get("qty"));
                    String id_satuan = String.valueOf(jointabeldata.get("id_satuan"));
                    String nama_satuan = String.valueOf(jointabeldata.get("nama_satuan"));
                    String isi_satuan = String.valueOf(jointabeldata.get("qty_satuan_pengali"));
                    String id_satuan_pengali = String.valueOf(jointabeldata.get("id_satuan_pengali"));
                    String harga_jual = nf.format(FuncHelper.ToDouble(jointabeldata.get("harga_jual")));
                    String diskon_persen = String.valueOf(jointabeldata.get("diskon_persen"));
                    String diskon_nominal = String.valueOf(jointabeldata.get("diskon_nominal"));
                    String id_pajak = String.valueOf(jointabeldata.get("id_pajak"));
                    String nama_pajak = String.valueOf(jointabeldata.get("nama_pajak"));
                    String nilai_pajak = String.valueOf(jointabeldata.get("nilai_pajak"));
                    String id_gudang = String.valueOf(jointabeldata.get("id_gudang"));
                    String nama_gudang = String.valueOf(jointabeldata.get("nama_gudang"));
                    String keterangan = String.valueOf(jointabeldata.get("keterangan"));
                    String total = nf.format(kalkulasitotalperindex(diskon_persen, diskon_nominal, jumlah, harga_jual, isi_satuan));
                    tabeldatalist.add(new Entitytabledata(id_barang, kode_barang, stok, nama_barang, jumlah, id_satuan,
                         nama_satuan, isi_satuan, id_satuan_pengali, harga_jual, diskon_persen, diskon_nominal, id_pajak, nama_pajak,
                         nilai_pajak, id_gudang, nama_gudang, keterangan, total));

                }
                for (int i = 0; i < tabeldatalist.size(); i++) {
                    rowtabledata[gx(kode)] = tabeldatalist.get(i).getKode_barang();
                    rowtabledata[gx(nama)] = tabeldatalist.get(i).getNama_barang();
                    rowtabledata[gx(stok)] = tabeldatalist.get(i).getStok();
                    rowtabledata[gx(jumlah)] = tabeldatalist.get(i).getJumlah();
                    rowtabledata[gx(satuan)] = tabeldatalist.get(i).getNama_satuan();
                    rowtabledata[gx(harga_jual)] = tabeldatalist.get(i).getHarga_jual();
                    rowtabledata[gx(diskon_persen)] = tabeldatalist.get(i).getDiskon_persen();
                    rowtabledata[gx(diskon_nominal)] = tabeldatalist.get(i).getDiskon_nominal();
                    rowtabledata[gx(pajak)] = tabeldatalist.get(i).getNama_pajak();
                    rowtabledata[gx(gudang)] = tabeldatalist.get(i).getNama_gudang();
                    rowtabledata[gx(keterangan)] = tabeldatalist.get(i).getKeterangan();
                    rowtabledata[gx(total)] = tabeldatalist.get(i).getTotal();
                    dtmtabeldata.addRow(rowtabledata);
                }
                kalkulasitotal();
                for (int i = 0; i < rowtabledata.length; i++) {
                    rowtabledata[i] = "";
                }
                pane.cmb_tipe_bayar.setEnabled(false);
                pane.cmb_tipe_penjualan.setEnabled(false);

            }

        } catch (ParseException ex) {
            Logger.getLogger(DaftarorderpenjualaninputController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void rawsimpan() {
        if (id.equals("")) {
            String data = "genjur="
                 + "id_keltrans='22'::"
                 + "id_dept='" + valdept + "'::"
                 + "tanggal='" + new SimpleDateFormat("yyyy-MM-dd").format(pane.dtanggal.getDate()) + "'::"
                 + "noref='" + FuncHelper.EncodeString(pane.edno_transaksi.getText()) + "'::"
                 + "user_input='" + FuncHelper.EncodeString(valuser_input) + "'::"
                 + "keterangan='" + FuncHelper.EncodeString(pane.edketerangan.getText()) + "'"
                 + "&penjualan="
                 + "id_pelanggan='" + valpelanggan + "'::"
                 + "tipe_pembayaran='" + String.valueOf(tipe_bayar) + "'::"
                 + "id_gudang='" + valgudang + "'::"
                 + "total_penjualan='" + total_penjualan_all + "'::"
                 + "total_biaya='" + FuncHelper.ToDouble(pane.edbiayalain.getText()) + "'::"
                 + "diskon_persen='" + FuncHelper.ToDouble(pane.eddiskon1.getText()) + "'::"
                 + "diskon_nominal='" + FuncHelper.ToDouble(pane.eddiskon2.getText()) + "'::"
                 + "total_uang_muka='0'::"
                 + "total_pajak='" + total_pajak + "'::"
                 + "id_currency='" + Globalsession.id_currency_company + "'::"
                 + "nilai_kurs='1'::"
                 + "akun_penjualan='" + valakun_penjualan + "'::"
                 + "akun_biaya='" + valakun_ongkir + "'::"
                 + "akun_diskon='" + valakun_diskon + "'::"
                 + "akun_uang_muka='" + valakun_uang_muka + "'::"
                 + "diskon_dalam='" + valcheck + "'::"
                 + "tanggal_pengantaran='" + new SimpleDateFormat("yyyy-MM-dd").format(pane.dtanggal_pengantaran.getDate()) + "'::"
                 + "id_pengantaran='" + valshipvia + "'::"
                 + "id_bagian_penjualan='" + valsalesman + "'::"
                 + "id_termofpayment='" + valtop + "'::"
                 + "tipe_penjualan='" + tipe_jual + "'::"
                 + "isorderselesai='" + status_selesai + "'"
                 + "&" + kirimtexpenjualan();

            ch.insertdata("insertorderpenjualan", data);
            if (Staticvar.getresult.equals("berhasil")) {
                try {
                    FuncHelper.konfir("Ingin Melanjutkan transaksi ? ",
                         "Data berhasil disimpan, jika ingin melakukan transaksi baru, tekan ya", "Ya");
                    if (Staticvar.isupdate == true) {
                        Staticvar.isupdate = false;
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
                        pane.cmb_tipe_bayar.setSelectedIndex(0);
                        pane.edbiayalain.setText("0");
                        pane.lsubtotal.setText("0");
                        pane.ltotal_pajak.setText("0");
                        pane.ltotal_penjualan.setText("0");
                    } else {
                        Daftarorderpenjualan_inner_panel inpane = new Daftarorderpenjualan_inner_panel();
                        Staticvar.pp.container.removeAll();
                        Staticvar.pp.container.setLayout(new BorderLayout());
                        Staticvar.pp.container.add(inpane, BorderLayout.CENTER);
                        Staticvar.pp.container.revalidate();
                        Staticvar.pp.container.repaint();
                        Staticvar.inputmode = false;
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
                 + "noref='" + FuncHelper.EncodeString(pane.edno_transaksi.getText()) + "'::"
                 + "user_input='" + FuncHelper.EncodeString(valuser_input) + "'::"
                 + "user_edit='" + FuncHelper.EncodeString(Globalsession.id_user) + "'::"
                 + "keterangan='" + FuncHelper.EncodeString(pane.edketerangan.getText()) + "'"
                 + "&penjualan="
                 + "id_pelanggan='" + valpelanggan + "'::"
                 + "tipe_pembayaran='" + String.valueOf(tipe_bayar) + "'::"
                 + "id_gudang='" + valgudang + "'::"
                 + "total_penjualan='" + total_penjualan_all + "'::"
                 + "total_biaya='" + FuncHelper.ToDouble(pane.edbiayalain.getText()) + "'::"
                 + "diskon_persen='" + FuncHelper.ToDouble(pane.eddiskon1.getText()) + "'::"
                 + "diskon_nominal='" + FuncHelper.ToDouble(pane.eddiskon2.getText()) + "'::"
                 + "total_uang_muka='0'::"
                 + "total_pajak='" + total_pajak + "'::"
                 + "id_currency='" + Globalsession.id_currency_company + "'::"
                 + "nilai_kurs='1'::"
                 + "akun_penjualan='" + valakun_penjualan + "'::"
                 + "akun_biaya='" + valakun_ongkir + "'::"
                 + "akun_diskon='" + valakun_diskon + "'::"
                 + "akun_uang_muka='" + valakun_uang_muka + "'::"
                 + "diskon_dalam='" + valcheck + "'::"
                 + "tanggal_pengantaran='" + new SimpleDateFormat("yyyy-MM-dd").format(pane.dtanggal_pengantaran.getDate()) + "'::"
                 + "id_pengantaran='" + valshipvia + "'::"
                 + "id_bagian_penjualan='" + valsalesman + "'::"
                 + "id_termofpayment='" + valtop + "'::"
                 + "tipe_penjualan='" + tipe_jual + "'::"
                 + "isorderselesai='" + status_selesai + "'"
                 + "&" + kirimtexpenjualan();
            ch.updatedata("updateorderpenjualan", data, id);
            if (Staticvar.getresult.equals("berhasil")) {
                Daftarorderpenjualan_inner_panel inpane = new Daftarorderpenjualan_inner_panel();
                Staticvar.pp.container.removeAll();
                Staticvar.pp.container.setLayout(new BorderLayout());
                Staticvar.pp.container.add(inpane, BorderLayout.CENTER);
                Staticvar.pp.container.revalidate();
                Staticvar.pp.container.repaint();
                Staticvar.inputmode = false;
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
                if (pane.edpelanggan.getText().equals("")) {
                    FuncHelper.info("Proses Ditolak", "Supplier tidak boleh kosong");

                } else if (tabeldatalist.size() == 0) {
                    FuncHelper.info("Proses Ditolak", "Table tidak boleh kosong");
                } else {
                    int tahunbulan = Integer.parseInt(new SimpleDateFormat("yyyyM").format(pane.dtanggal.getDate()));
                    int periodetahunnulan = Integer.parseInt(Globalsession.periode_year + Globalsession.periode_month);
                    if (tahunbulan > periodetahunnulan) {
                        FuncHelper.konfir("Apakah anda ingin melanjutkan transaksi ?",
                             "Tanggal transaksi anda setelah periode akuntansi, jika ingin tetap melanjuktan tekan Ya, jika tidak tekan batal", "Ya");
                        if (Staticvar.isupdate == true) {
                            Staticvar.isupdate = false;
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
                 + "qty=" + "'" + FuncHelper.ToDouble(tabeldatalist.get(i).getJumlah()) + "'" + "::"
                 + "harga=" + "'" + FuncHelper.ToDouble(tabeldatalist.get(i).getHarga_jual()) + "'" + "::"
                 + "id_satuan=" + "'" + tabeldatalist.get(i).getId_satuan() + "'" + "::"
                 + "diskon_persen=" + "'" + FuncHelper.EncodeString(tabeldatalist.get(i).getDiskon_persen()) + "'" + "::"
                 + "diskon_nominal=" + "'" + FuncHelper.ToDouble(tabeldatalist.get(i).getDiskon_nominal()) + "'" + "::"
                 + "id_pajak=" + "'" + tabeldatalist.get(i).getId_pajak() + "'" + "::"
                 + "id_gudang=" + "'" + tabeldatalist.get(i).getId_gudang() + "'" + "::"
                 + "id_satuan_pengali=" + "'" + tabeldatalist.get(i).getId_satuan_pengali() + "'" + "::"
                 + "qty_satuan_pengali=" + "'" + tabeldatalist.get(i).getIsi_satuan() + "'" + "::"
                 + "keterangan=" + "'" + FuncHelper.EncodeString(tabeldatalist.get(i).getKeterangan()) + "'");
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
        pane.bhapus_baris.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = pane.tabledata.getSelectedRow();
                FuncHelper.konfir("Yakin akan menghapus data ini?",
                     "Data yang akan anda hapus adalah" + pane.tabledata.getValueAt(row, gx(kode)) + " - "
                     + pane.tabledata.getValueAt(row, gx(nama)) + " Tekan Ya jika ingin menghapus", "Ya");

                if (Staticvar.isupdate == true) {
                    Staticvar.isupdate = false;
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
    }

    private void tambahbaris() {
        pane.btambah_baris.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

        });
    }

    private void batal() {
        pane.bbatal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new FuncHelper().insertnogagal("22", pane.dtanggal.getDate(), valdept, String.valueOf(no_urut));
                        Staticvar.inputmode = false;
                        Daftarorderpenjualan_inner_panel inpane = new Daftarorderpenjualan_inner_panel();
                        Staticvar.pp.container.removeAll();
                        Staticvar.pp.container.setLayout(new BorderLayout());
                        Staticvar.pp.container.add(inpane, BorderLayout.CENTER);
                        Staticvar.pp.container.revalidate();
                        Staticvar.pp.container.repaint();
                    }
                });

            }
        });
    }

    private void caripelanggan() {
        pane.bcari_pelanggan.addActionListener((ActionEvent e) -> {
            Staticvar.sfilter = "";
            Staticvar.preid = valpelanggan;
            Staticvar.prelabel = pane.edpelanggan.getText();
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("nama", "popupdaftarnama?tipe=0", "Daftar Pelanggan"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valpelanggan = Staticvar.resid;
            valgolongan = Staticvar.resvalueextended;
            pane.edpelanggan.setText(Staticvar.reslabel);
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

    private void carigudang() {
        pane.bcari_gudang.addActionListener((ActionEvent e) -> {
            Staticvar.sfilter = "";
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
            if (!Staticvar.preid.equals(valdept)) {
                new FuncHelper().insertnogagal("22", pane.dtanggal.getDate(), Staticvar.preid, String.valueOf(no_urut));
                HashMap hm = new FuncHelper().getkodetransaksi("22", pane.dtanggal.getDate(), valdept);
                pane.edno_transaksi.setText(String.valueOf(hm.get("no_transaksi")));
                no_urut = FuncHelper.ToInt(String.valueOf(hm.get("no_urut")));
            }

        });

    }

    private void carishipvia() {
        pane.bcarishipvia.addActionListener((ActionEvent e) -> {
            Staticvar.sfilter = "";
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
            Staticvar.sfilter = "";
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

    private void cariakunpenjualan() {
        pane.bcariakun_penjualan.addActionListener((ActionEvent e) -> {
            rawgetidakun(pane.edakun_penjualan.getText(), valakun_penjualan);
            if (!Staticvar.resid.equals(valakun_penjualan)) {
                valakun_penjualan = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.edakun_penjualan.setText(val);
            }
        });

    }

    private void cariakundiskon() {
        pane.bcariakun_diskon_penjualan.addActionListener((ActionEvent e) -> {
            rawgetidakun(pane.edakun_diskon_penjualan.getText(), valakun_diskon);
            if (!Staticvar.resid.equals(valakun_diskon)) {
                valakun_diskon = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.edakun_diskon_penjualan.setText(val);
            }

        });

    }

    private void cariakunongkir() {
        pane.bcariakun_ongkir.addActionListener((ActionEvent e) -> {
            rawgetidakun(pane.edakun_ongkir.getText(), valakun_ongkir);
            if (!Staticvar.resid.equals(valakun_ongkir)) {
                valakun_ongkir = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.edakun_ongkir.setText(val);
            }
        });

    }

    private void cariakunuangmuka() {
        pane.bcari_uang_muka.addActionListener((ActionEvent e) -> {
            rawgetidakun(pane.edakun_uang_muka.getText(), valakun_uang_muka);
            if (!Staticvar.resid.equals(valakun_uang_muka)) {
                valakun_uang_muka = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.edakun_uang_muka.setText(val);
            }
        });

    }

    private void addtotable() {

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
                    if (col == gx(kode)) {
                        ischangevalue = true;
                        Staticvar.preid = tabeldatalist.get(row).getId_barang();
                        String defnilai = "";
                        String cekval = String.valueOf(tm.getValueAt(row, col));
                        if (cekval.equals("null") || cekval.equals("")) {
                            defnilai = "";
                        } else {
                            defnilai = String.valueOf(tm.getValueAt(row, gx(kode)));
                        }
                        Staticvar.prelabel = defnilai;
                        Staticvar.sfilter = defnilai;
                        Staticvar.prelabel = defnilai;
                        try {
                            if (tipe_jual == 1) {
                                if (sudahterpanggil == true) {
                                    sudahterpanggil = false;
                                } else {
                                    JDialog jd = new JDialog(new Mainmenu());
                                    jd.add(new Popupcari("akun", "popupdaftarakun", "Daftar Akun"));
                                    jd.pack();
                                    jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                                    jd.setLocationRelativeTo(null);
                                    jd.setVisible(true);
                                    jd.toFront();
                                    tabeldatalist.get(row).setId_barang(Staticvar.resid);
                                    tm.setValueAt(String.valueOf(Staticvar.resid), row, gx(kode));
                                    if (!Staticvar.preid.equals(Staticvar.resid)) {
                                        tm.setValueAt(String.valueOf(Staticvar.reslabel), row, gx(nama));
                                        tabeldatalist.get(row).setDiskon_persen("0");
                                        tabeldatalist.get(row).setDiskon_nominal("0");
                                        tabeldatalist.get(row).setTotal("0");
                                        tabeldatalist.get(row).setJumlah("0");
                                        tabeldatalist.get(row).setIsi_satuan("1");
                                        tabeldatalist.get(row).setHarga_jual("0");
                                        tm.setValueAt("0", row, gx(diskon_persen));
                                        tm.setValueAt("0", row, gx(diskon_nominal));
                                        tm.setValueAt("0", row, gx(total));
                                        kalkulasitotalperrow(row);
                                        pane.tabledata.requestFocus();
                                        pane.tabledata.changeSelection(row, gx(jumlah), false, false);
                                    } else {
                                        pane.tabledata.requestFocus();
                                    }
                                }

                            } else {
                                JSONParser jpdata = new JSONParser();
                                String param = String.format("kode=%s", String.valueOf(tm.getValueAt(row, col)));
                                Object objdataraw = jpdata.parse(ch.getdatadetails("dm/datapersediaanbykode", param));
                                JSONObject jodataraw = (JSONObject) objdataraw;
                                Object objdata = jodataraw.get("data");
                                JSONArray jadata = (JSONArray) objdata;
                                if (jadata.size() == 1) {
                                    for (int i = 0; i < jadata.size(); i++) {
                                        JSONObject joindata = (JSONObject) jadata.get(i);
                                        tabeldatalist.get(row).setId_barang(String.valueOf(joindata.get("id")));
                                        tm.setValueAt(String.valueOf(joindata.get("nama")), row, gx(nama));
                                        pane.tabledata.setValueAt(String.valueOf(joindata.get("stok")), row, gx(stok));
                                        tabeldatalist.get(row).setStok(String.valueOf(joindata.get("stok")));
                                        tabeldatalist.get(row).setJumlah("0");
                                        pane.tabledata.setValueAt("0", row, gx(jumlah));
                                        tabeldatalist.get(row).setId_satuan(String.valueOf(joindata.get("id_satuan")));
                                        tm.setValueAt(String.valueOf(joindata.get("nama_satuan")), row, gx(satuan));
                                        tabeldatalist.get(row).setId_satuan_pengali(String.valueOf(joindata.get("id_satuan")));
                                        tabeldatalist.get(row).setIsi_satuan("1");
                                        tabeldatalist.get(row).setHarga_jual(String.valueOf(joindata.get("harga_jual")));
                                        tm.setValueAt(nf.format(FuncHelper.ToDouble(joindata.get("harga_jual"))), row, gx(harga_jual));
                                        tm.setValueAt("0", row, gx(diskon_persen));
                                        tm.setValueAt("0", row, gx(diskon_nominal));
                                        tabeldatalist.get(row).setDiskon_persen("0");
                                        tabeldatalist.get(row).setDiskon_nominal("0");
                                        tm.setValueAt(String.valueOf(joindata.get("nama_pajak_beli")), row, gx(pajak));
                                        tabeldatalist.get(row).setId_pajak(String.valueOf(joindata.get("id_pajak_jual")));
                                        tabeldatalist.get(row).setNilai_pajak(String.valueOf(joindata.get("nilai_pajak_jual")));
                                        tabeldatalist.get(row).setId_gudang(valgudang);
                                        tm.setValueAt(pane.edgudang.getText(), row, gx(gudang));
                                        tabeldatalist.get(row).setKeterangan("");
                                        tm.setValueAt("", row, gx(keterangan));
                                        tabeldatalist.get(row).setTotal("0");
                                        tm.setValueAt("0", row, gx(total));
                                    }
                                    kalkulasitotalperrow(row);
                                    pane.tabledata.requestFocus();
                                    pane.tabledata.changeSelection(row, gx(jumlah), false, false);
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
                                            tm.setValueAt(String.valueOf(joindata2.get("kode")), row, gx(kode));
                                            tm.setValueAt(String.valueOf(joindata2.get("nama")), row, gx(nama));
                                            pane.tabledata.setValueAt(String.valueOf(joindata2.get("stok")), row, gx(stok));
                                            tabeldatalist.get(row).setStok(String.valueOf(joindata2.get("stok")));
                                            tabeldatalist.get(row).setJumlah("0");
                                            pane.tabledata.setValueAt("0", row, gx(jumlah));
                                            tabeldatalist.get(row).setId_satuan(String.valueOf(joindata2.get("id_satuan")));
                                            tm.setValueAt(String.valueOf(joindata2.get("nama_satuan")), row, gx(satuan));
                                            tabeldatalist.get(row).setId_satuan_pengali(String.valueOf(joindata2.get("id_satuan")));
                                            tabeldatalist.get(row).setIsi_satuan("1");
                                            tabeldatalist.get(row).setHarga_jual(String.valueOf(joindata2.get("harga_jual")));
                                            tm.setValueAt(nf.format(FuncHelper.ToDouble(joindata2.get("harga_jual"))), row, gx(harga_jual));
                                            tm.setValueAt("0", row, gx(diskon_persen));
                                            tm.setValueAt("0", row, gx(diskon_nominal));
                                            tabeldatalist.get(row).setDiskon_persen("0");
                                            tabeldatalist.get(row).setDiskon_nominal("0");
                                            tm.setValueAt(String.valueOf(joindata2.get("nama_pajak_jual")), row, gx(pajak));
                                            tabeldatalist.get(row).setId_pajak(String.valueOf(joindata2.get("id_pajak_jual")));
                                            tabeldatalist.get(row).setNilai_pajak(String.valueOf(joindata2.get("nilai_pajak_jual")));
                                            tabeldatalist.get(row).setId_gudang(valgudang);
                                            tm.setValueAt(pane.edgudang.getText(), row, gx(gudang));
                                            tabeldatalist.get(row).setKeterangan("");
                                            tm.setValueAt("", row, gx(keterangan));
                                            tabeldatalist.get(row).setTotal("0");
                                            tm.setValueAt("0", row, gx(total));
                                        }
                                        kalkulasitotalperrow(row);
                                        pane.tabledata.requestFocus();
                                        pane.tabledata.changeSelection(row, gx(jumlah), false, false);
                                    }
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
                            Logger.getLogger(DaftarorderpenjualaninputController.class.getName()).log(Level.SEVERE, null, ex);
                        } finally {
                            ischangevalue = false;
                        }
                    } else if (col == gx(jumlah)) {
                        try {
                            ischangevalue = true;
                            if (!tabeldatalist.get(row).getJumlah().equals(tm.getValueAt(row, gx(jumlah)))) {
                                tabeldatalist.get(row).setJumlah(String.valueOf(tm.getValueAt(row, gx(jumlah))));
                                double callhargajual = gethargajual(
                                     tabeldatalist.get(row).getId_barang(),
                                     tabeldatalist.get(row).getId_satuan(),
                                     tabeldatalist.get(row).getJumlah());
                                tm.setValueAt(nf.format(callhargajual), row, gx(harga_jual));
                                tabeldatalist.get(row).setHarga_jual(String.valueOf(callhargajual));
                                columnfunction(row, gx(jumlah), false);
                            }

                            //nextcolom(2, row);
                        } catch (Exception ex) {
                        } finally {
                            ischangevalue = false;
                        }
                    } else if (col == gx(harga_jual)) {
                        try {
                            ischangevalue = true;
                            tabeldatalist.get(row).setHarga_jual(String.valueOf(tm.getValueAt(row, gx(harga_jual))));
                            columnfunction(row, gx(harga_jual), false);
                            //nextcolom(4, row);
                        } catch (Exception ex) {
                        } finally {
                            ischangevalue = false;
                        }
                    } else if (col == gx(diskon_persen)) {
                        try {
                            ischangevalue = true;
                            String valcol = String.valueOf(pane.tabledata.getValueAt(row, gx(diskon_persen)));
                            if (checkalphabeth(valcol) == false) {
                                tabeldatalist.get(row).setDiskon_persen(String.valueOf(tm.getValueAt(row, gx(diskon_persen))));
                                columnfunction(row, gx(diskon_persen), false);
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
                                valcol = "0";
                                pane.tabledata.setValueAt(valcol, row, gx(diskon_persen));
                                columnfunction(row, gx(diskon_persen), false);
                                pane.tabledata.requestFocus();
                            }

                        } catch (Exception ex) {
                        } finally {
                            ischangevalue = false;
                        }
                    } else if (col == gx(diskon_nominal)) {
                        try {
                            ischangevalue = true;
                            String valcol = String.valueOf(pane.tabledata.getValueAt(row, gx(diskon_nominal)));
                            if (checknumerik(valcol) == true) {
                                columnfunction(row, gx(diskon_nominal), false);
                                tabeldatalist.get(row).setDiskon_nominal(String.valueOf(FuncHelper.ToDouble(tm.getValueAt(row, 7))));
                                //nextcolom(col, row);
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
                                pane.tabledata.setValueAt(valcol, row, gx(diskon_nominal));
                                columnfunction(row, gx(diskon_nominal), false);
                                //nextcolom(7, row);
                            }
                        } catch (Exception ex) {
                        } finally {
                            ischangevalue = false;
                        }
                    } else if (col == gx(keterangan)) {
                        tabeldatalist.get(row).setKeterangan(String.valueOf(tm.getValueAt(row, gx(keterangan))));
                    }
                    ischangevalue = true;
                    String curval = String.valueOf(tm.getValueAt(row, col));
                    if (curval.equals("") || curval.equals("null")) {
                        if (oldvalue.equals("null")) {
                            tm.setValueAt("", row, col);
                        } else {
                            tm.setValueAt(oldvalue, row, col);
                        }
                        kalkulasitotalperrow(row);
                        oldvalue = "";
                        if (col == gx(kode)) {
                            pane.tabledata.requestFocus();
                            pane.tabledata.changeSelection(row, gx(kode), false, false);
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
                if (e.getClickCount() == 2) {
                    if (col == gx(kode)) {
                        if (tipe_jual == 1) {
                            Staticvar.sfilter = "";
                            sudahterpanggil = true;
                            JDialog jd = new JDialog(new Mainmenu());
                            jd.add(new Popupcari("akun", "popupdaftarakun", "Daftar Akun"));
                            jd.pack();
                            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                            jd.setLocationRelativeTo(null);
                            jd.setVisible(true);
                            jd.toFront();
                            if (!Staticvar.preid.equals(Staticvar.resid)) {
                                tabeldatalist.get(row).setId_barang(Staticvar.resid);
                                pane.tabledata.setValueAt(String.valueOf(Staticvar.resid), row, gx(kode));
                                pane.tabledata.setValueAt(String.valueOf(Staticvar.reslabel), row, gx(nama));
                                tabeldatalist.get(row).setDiskon_persen("0");
                                tabeldatalist.get(row).setDiskon_nominal("0");
                                tabeldatalist.get(row).setTotal("0");
                                tabeldatalist.get(row).setJumlah("0");
                                tabeldatalist.get(row).setIsi_satuan("1");
                                tabeldatalist.get(row).setHarga_jual("0");
                                pane.tabledata.setValueAt("0", row, gx(diskon_persen));
                                pane.tabledata.setValueAt("0", row, gx(diskon_nominal));
                                pane.tabledata.setValueAt("0", row, gx(total));
                                kalkulasitotalperrow(row);
                                pane.tabledata.requestFocus();
                                pane.tabledata.changeSelection(row, gx(jumlah), false, false);
                            } else {
                                pane.tabledata.requestFocus();
                            }

                        } else {
                            Staticvar.preid = tabeldatalist.get(row).getId_barang();
                            String defnilai = "";
                            String cekval = String.valueOf(pane.tabledata.getValueAt(row, col));
                            if (cekval.equals("null") || cekval.equals("")) {
                                defnilai = "";
                            } else {
                                defnilai = String.valueOf(pane.tabledata.getValueAt(row, gx(kode)));
                            }
                            Staticvar.prelabel = defnilai;
                            Staticvar.sfilter = "";
                            Staticvar.prelabel = defnilai;
                            JDialog jd = new JDialog(new Mainmenu());
                            jd.add(new Popupcari("persediaan", "popupdaftarpersediaan", "Daftar Persediaan"));
                            jd.pack();
                            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                            jd.setLocationRelativeTo(null);
                            jd.setVisible(true);
                            jd.toFront();
                            if (!Staticvar.reslabel.equals("")) {
                                try {
                                    if (!Staticvar.preid.equals(Staticvar.resid)) {
                                        JSONParser jpdata2 = new JSONParser();
                                        String param2 = String.format("id=%s", Staticvar.resid);
                                        Object objdataraw2 = jpdata2.parse(ch.getdatadetails("dm/datapersediaan", param2));
                                        JSONObject jodataraw2 = (JSONObject) objdataraw2;
                                        Object objdata2 = jodataraw2.get("data");
                                        JSONArray jadata2 = (JSONArray) objdata2;
                                        for (int i = 0; i < jadata2.size(); i++) {
                                            JSONObject joindata2 = (JSONObject) jadata2.get(i);
                                            tabeldatalist.get(row).setId_barang(Staticvar.resid);
                                            pane.tabledata.setValueAt(String.valueOf(joindata2.get("kode")), row, gx(kode));
                                            pane.tabledata.setValueAt(String.valueOf(joindata2.get("nama")), row, gx(nama));
                                            pane.tabledata.setValueAt(String.valueOf(joindata2.get("stok")), row, gx(stok));
                                            tabeldatalist.get(row).setStok(String.valueOf(joindata2.get("stok")));
                                            tabeldatalist.get(row).setJumlah("0");
                                            pane.tabledata.setValueAt("0", row, gx(jumlah));
                                            tabeldatalist.get(row).setId_satuan(String.valueOf(joindata2.get("id_satuan")));
                                            pane.tabledata.setValueAt(String.valueOf(joindata2.get("nama_satuan")), row, gx(satuan));
                                            tabeldatalist.get(row).setId_satuan_pengali(String.valueOf(joindata2.get("id_satuan")));
                                            tabeldatalist.get(row).setIsi_satuan("1");
                                            tabeldatalist.get(row).setHarga_jual(String.valueOf(joindata2.get("harga_jual")));
                                            pane.tabledata.setValueAt(nf.format(FuncHelper.ToDouble(joindata2.get("harga_jual"))), row, gx(harga_jual));
                                            pane.tabledata.setValueAt("0", row, gx(diskon_persen));
                                            pane.tabledata.setValueAt("0", row, gx(diskon_nominal));
                                            tabeldatalist.get(row).setDiskon_persen("0");
                                            tabeldatalist.get(row).setDiskon_nominal("0");
                                            pane.tabledata.setValueAt(String.valueOf(joindata2.get("nama_pajak_jual")), row, gx(pajak));
                                            tabeldatalist.get(row).setId_pajak(String.valueOf(joindata2.get("id_pajak_jual")));
                                            tabeldatalist.get(row).setNilai_pajak(String.valueOf(joindata2.get("nilai_pajak_jual")));
                                            tabeldatalist.get(row).setId_gudang(valgudang);
                                            pane.tabledata.setValueAt(pane.edgudang.getText(), row, gx(gudang));
                                            tabeldatalist.get(row).setKeterangan("");
                                            pane.tabledata.setValueAt("", row, gx(keterangan));
                                            tabeldatalist.get(row).setTotal("0");
                                            pane.tabledata.setValueAt("0", row, gx(total));
                                        }
                                        kalkulasitotalperrow(row);
                                        pane.tabledata.requestFocus();
                                        pane.tabledata.changeSelection(row, gx(jumlah), false, false);
                                    } else {
                                        pane.tabledata.requestFocus();
                                    }
                                } catch (ParseException ex) {
                                    Logger.getLogger(DaftarorderpenjualaninputController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    } else if (col == gx(satuan)) {
                        Staticvar.sfilter = "";
                        Staticvar.preid = tabeldatalist.get(row).getId_satuan();
                        Staticvar.prelabel = String.valueOf(tb.getValueAt(row, gx(satuan)));
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
                        if (!tabeldatalist.get(row).getId_satuan().equals(Staticvar.resid)) {
                            tabeldatalist.get(row).setId_satuan(Staticvar.resid);
                            tb.setValueAt(Staticvar.reslabel, row, gx(satuan));
                            tabeldatalist.get(row).setIsi_satuan(Staticvar.resvalueextended);
                            dtmtabeldata.fireTableCellUpdated(row, gx(satuan));
                            double callhargajual = gethargajual(
                                 tabeldatalist.get(row).getId_barang(),
                                 tabeldatalist.get(row).getId_satuan(),
                                 tabeldatalist.get(row).getJumlah());
                            tb.setValueAt(nf.format(callhargajual), row, gx(harga_jual));
                            tabeldatalist.get(row).setHarga_jual(String.valueOf(callhargajual));
                            kalkulasitotalperrow(row);
                        }
                    } else if (col == gx(pajak)) {
                        Staticvar.sfilter = "";
                        Staticvar.preid = tabeldatalist.get(row).getId_pajak();
                        Staticvar.prelabel = String.valueOf(tb.getValueAt(row, gx(pajak)));
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
                        tb.setValueAt(Staticvar.reslabel, row, gx(pajak));
                        dtmtabeldata.fireTableCellUpdated(row, gx(pajak));
                        kalkulasitotalperrow(row);
                    } else if (col == gx(gudang)) {
                        Staticvar.sfilter = "";
                        Staticvar.preid = tabeldatalist.get(row).getId_gudang();
                        Staticvar.prelabel = String.valueOf(tb.getValueAt(row, gx(gudang)));
                        JDialog jd = new JDialog(new Mainmenu());
                        jd.add(new Popupcari("gudang", "popupdaftargudang", "Daftar Gudang"));
                        jd.pack();
                        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        jd.setLocationRelativeTo(null);
                        jd.setVisible(true);
                        jd.toFront();
                        tabeldatalist.get(row).setId_gudang(Staticvar.resid);
                        tb.setValueAt(Staticvar.reslabel, row, gx(gudang));
                        dtmtabeldata.fireTableCellUpdated(row, gx(gudang));
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

            @Override
            public void mouseReleased(MouseEvent e) {
                JTable tb = (JTable) e.getSource();
                if (tb.isEditing()) {
                    tb.getCellEditor().cancelCellEditing();
                }
            }

        };

        pane.tabledata.addMouseListener(madap);

        pane.tabledata.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println(e.getKeyChar());
            }
        });

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
                if (col == gx(satuan)) {
                    Staticvar.sfilter = "";
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
                    if (!tabeldatalist.get(row).getId_satuan().equals(Staticvar.resid)) {
                        tabeldatalist.get(row).setId_satuan(Staticvar.resid);
                        pane.tabledata.setValueAt(Staticvar.reslabel, row, gx(satuan));
                        tabeldatalist.get(row).setIsi_satuan(Staticvar.resvalueextended);
                        dtmtabeldata.fireTableCellUpdated(row, gx(satuan));
                        double callhargajual = gethargajual(
                             tabeldatalist.get(row).getId_barang(),
                             tabeldatalist.get(row).getId_satuan(),
                             tabeldatalist.get(row).getJumlah());
                        pane.tabledata.setValueAt(nf.format(callhargajual), row, gx(harga_jual));
                        tabeldatalist.get(row).setHarga_jual(String.valueOf(callhargajual));
                        kalkulasitotalperrow(row);
                    }
                } else if (col == gx(pajak)) {
                    Staticvar.sfilter = "";
                    Staticvar.preid = tabeldatalist.get(row).getId_pajak();
                    Staticvar.prelabel = String.valueOf(pane.tabledata.getValueAt(row, gx(pajak)));
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
                    pane.tabledata.setValueAt(Staticvar.reslabel, row, gx(pajak));
                    dtmtabeldata.fireTableCellUpdated(row, gx(pajak));
                    kalkulasitotalperrow(row);
                } else if (col == gx(gudang)) {
                    Staticvar.sfilter = "";
                    Staticvar.preid = tabeldatalist.get(row).getId_gudang();
                    Staticvar.prelabel = String.valueOf(pane.tabledata.getValueAt(row, gx(gudang)));
                    JDialog jd = new JDialog(new Mainmenu());
                    jd.add(new Popupcari("gudang", "popupdaftargudang", "Daftar Gudang"));
                    jd.pack();
                    jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                    jd.setLocationRelativeTo(null);
                    jd.setVisible(true);
                    jd.toFront();
                    tabeldatalist.get(row).setId_gudang(Staticvar.resid);
                    pane.tabledata.setValueAt(Staticvar.reslabel, row, gx(gudang));
                    dtmtabeldata.fireTableCellUpdated(row, gx(gudang));
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

                if (row == gx(kode)) {
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
                addautorow(row);
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
            String value = nf.format(FuncHelper.ToDouble(pane.tabledata.getValueAt(row, col)));
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

    private void kalkulasi() {
        KeyAdapter keadbiaya = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                char cr = e.getKeyChar();
                if (!Character.isLetter(cr)) {
                    double subtotal = FuncHelper.ToDouble(pane.lsubtotal.getText());
                    double biayalain = FuncHelper.ToDouble(pane.edbiayalain.getText());
                    double diskon = FuncHelper.ToDouble(pane.eddiskon2.getText());
                    double pajak = FuncHelper.ToDouble(pane.ltotal_pajak.getText());
                    total_penjualan_all = subtotal + biayalain - diskon + pajak;
                    pane.ltotal_penjualan.setText(nf.format(total_penjualan_all));
                } else {
                    FuncHelper.info("Proses Ditolak", "Hanya memperbolehkan angka");
                    pane.edbiayalain.setText("");
                }
            }

        };

        KeyAdapter keaddiskonpersen = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                char cr = e.getKeyChar();
                if (!Character.isLetter(cr)) {
                    double subtotal = FuncHelper.ToDouble(pane.lsubtotal.getText());
                    double biayalain = FuncHelper.ToDouble(pane.edbiayalain.getText());
                    double indiskon_persen = FuncHelper.ToDouble(pane.eddiskon1.getText());
                    double indiskon_nominal = (subtotal + biayalain) * (indiskon_persen / 100);
                    double pajak = FuncHelper.ToDouble(pane.ltotal_pajak.getText());
                    total_penjualan_all = subtotal + biayalain - indiskon_nominal + pajak;

                    pane.eddiskon2.setText(nf.format(indiskon_nominal));
                    pane.ltotal_penjualan.setText(nf.format(total_penjualan_all));
                } else {
                    FuncHelper.info("Proses Ditolak", "Kolom Hanya memperbolehkan angka");
                    pane.edbiayalain.setText("");
                }
            }

        };

        KeyAdapter keaddiskonnominal = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                char cr = e.getKeyChar();
                if (!Character.isLetter(cr)) {
                    double subtotal = FuncHelper.ToDouble(pane.lsubtotal.getText());
                    double biayalain = FuncHelper.ToDouble(pane.edbiayalain.getText());
                    double pajak = FuncHelper.ToDouble(pane.ltotal_pajak.getText());
                    double indiskon_nominal = FuncHelper.ToDouble(pane.eddiskon2.getText());
                    double indiskon_persen = (indiskon_nominal / (subtotal + biayalain)) * 100;
                    total_penjualan_all = subtotal + biayalain - indiskon_nominal + pajak;
                    pane.eddiskon1.setText(FuncHelper.rounding(indiskon_persen));
                    pane.ltotal_penjualan.setText(nf.format(total_penjualan_all));
                } else {
                    FuncHelper.info("Proses Ditolak", "Kolom Hanya memperbolehkan angka");
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
        double subtotal = 0;
        total_penjualan_all = 0;
        total_pajak = 0;

        for (int i = 0; i < jumlah_row; i++) {
            double total_beli_masing = FuncHelper.ToDouble(emptycellcheck(i, gx(total)));
            subtotal = subtotal + total_beli_masing;

            double total_pajak_masing = FuncHelper.ToDouble(emptycellcheck(i, gx(total))) * (FuncHelper.ToDouble(tabeldatalist.get(i).getNilai_pajak()) / 100);
            total_pajak = total_pajak + total_pajak_masing;
        }

        pane.ltotal_pajak.setText(nf.format(total_pajak));
        pane.lsubtotal.setText(nf.format(subtotal));

        double biayalain = FuncHelper.ToDouble(pane.edbiayalain.getText());
        double pajak = FuncHelper.ToDouble(pane.ltotal_pajak.getText());
        double indiskon_nominal = FuncHelper.ToDouble(pane.eddiskon2.getText());
        //double diskon_nominal = (subtotal + biayalain) * (diskon_persen / 100);
        total_penjualan_all = subtotal + biayalain - indiskon_nominal + pajak;

        pane.eddiskon2.setText(nf.format(indiskon_nominal));
        pane.ltotal_penjualan.setText(nf.format(total_penjualan_all));
    }

    private void kalkulasitotalperrow(int row) {
        if (pane.ckdiskon.isSelected() == true) {
            String isifielddiskon = String.valueOf(pane.tabledata.getValueAt(row, gx(diskon_persen)));
            if (isifielddiskon.contains("+")) {
                double qty = FuncHelper.ToDouble(String.valueOf(pane.tabledata.getValueAt(row, gx(jumlah))));
                double harga = FuncHelper.ToDouble(pane.tabledata.getValueAt(row, gx(harga_jual)));
                double intotal = harga;
                String s = "";
                String[] multidiskon = isifielddiskon.split("\\+");
                for (int i = 0; i < multidiskon.length; i++) {
                    double diskonper = FuncHelper.ToDouble(multidiskon[i]);
                    intotal = ((intotal - (diskonper / 100 * intotal)));
                }
                intotal = qty * intotal;
                tabeldatalist.get(row).setTotal(String.valueOf(intotal));
                pane.tabledata.setValueAt(nf.format(intotal), row, gx(total));
            } else {
                double qty = FuncHelper.ToDouble(String.valueOf(pane.tabledata.getValueAt(row, gx(jumlah))));
                double harga = FuncHelper.ToDouble(String.valueOf(pane.tabledata.getValueAt(row, gx(harga_jual))));
                double diskon = FuncHelper.ToDouble(emptycellcheck(row, gx(diskon_persen)));
                double intotal = qty * (harga - (diskon / 100 * harga));
                tabeldatalist.get(row).setTotal(String.valueOf(total));
                pane.tabledata.setValueAt(nf.format(intotal), row, gx(total));
            }
        } else {

            double qty = FuncHelper.ToDouble(String.valueOf(pane.tabledata.getValueAt(row, gx(jumlah))));
            double harga = FuncHelper.ToDouble(pane.tabledata.getValueAt(row, gx(harga_jual)));
            double diskon = FuncHelper.ToDouble(emptycellcheck(row, gx(diskon_nominal)));
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
                double qty = FuncHelper.ToDouble(rawqty);
                double harga = FuncHelper.ToDouble(rawharga);
                total = harga;
                String[] multidiskon = isifielddiskon.split("\\+");
                for (int i = 0; i < multidiskon.length; i++) {
                    double diskonper = FuncHelper.ToDouble(multidiskon[i]);
                    total = ((total - (diskonper / 100 * total)));
                }
                total = qty * total;
            } else {
                double qty = FuncHelper.ToDouble(rawqty);
                double harga = FuncHelper.ToDouble(rawharga);
                double diskon = FuncHelper.ToDouble(rawdiskonpersen);
                total = qty * (harga - (diskon / 100 * harga));
            }
        } else {

            double qty = FuncHelper.ToDouble(rawqty);
            double harga = FuncHelper.ToDouble(rawharga);
            double diskon = FuncHelper.ToDouble(rawdiskonnominal);
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

    private boolean checknumerik(String val) {
        boolean hasil = false;
        try {
            FuncHelper.ToDouble(val);
            hasil = true;
        } catch (Exception e) {
            hasil = false;
        }
        return hasil;
    }

    private boolean checkalphabeth(String val) {
        Pattern p = Pattern.compile("[^0-9+]");
        Matcher m = p.matcher(val);
        boolean b = m.find();
        return b;
    }

    private void nextcolom(int col, int row) {
        int colcount = pane.tabledata.getColumnCount() - 1;
        if (col == colcount) {
            if (pane.tabledata.getRowCount() - 1 > row) {
                pane.tabledata.requestFocus();
                pane.tabledata.changeSelection(row + 1, gx(kode), false, false);
            } else if (String.valueOf(pane.tabledata.getValueAt(row, gx(kode))).equals("")
                 || String.valueOf(pane.tabledata.getValueAt(row, gx(kode))).equals("null")) {
                pane.tabledata.requestFocus();
                pane.tabledata.changeSelection(row, gx(kode), false, false);
            } else {
                addautorow(row);
            }
            return;
        }

        xnextcolom(col, row, pane.tabledata.getColumnCount() - 1);
    }

    private void backcolom(int col, int row) {
        xbackcolom(col, row, pane.tabledata.getColumnCount() - 1);
    }

    private void xnextcolom(int currentcoll, int currentrow, int colcount) {
        for (int i = 0; i <= colcount; i++) {
            if (currentcoll == i) {
                for (int j = currentcoll; j <= colcount; j++) {
                    while (!cekcolomnol(j + 1)) {
                        if (((j + 1) == gx(diskon_persen)) || ((j + 1) == gx(diskon_nominal))) {
                            if (pane.ckdiskon.isSelected()) {
                                if ((j + 1) == gx(diskon_nominal)) {
                                    int x = j + 1;
                                    for (int k = x; k <= colcount; k++) {
                                        while (!cekcolomnol(k + 1)) {
                                            pane.tabledata.requestFocus();
                                            pane.tabledata.changeSelection(currentrow, k + 1, false, false);
                                            return;
                                        }
                                    }
                                } else {
                                    pane.tabledata.requestFocus();
                                    pane.tabledata.changeSelection(currentrow, gx(diskon_persen), false, false);
                                }
                            } else {
                                pane.tabledata.requestFocus();
                                pane.tabledata.changeSelection(currentrow, gx(diskon_nominal), false, false);
                            }
                        } else {
                            pane.tabledata.requestFocus();
                            pane.tabledata.changeSelection(currentrow, j + 1, false, false);
                        }
                        return;
                    }
                }
            }
        }
    }

    private void xbackcolom(int currentcoll, int currentrow, int colcount) {
        for (int i = colcount; i >= 0; i--) {
            if (currentcoll == i) {
                for (int j = currentcoll; j >= 0; j--) {
                    while (!cekcolomnol(j - 1)) {
                        if (((j - 1) == gx(diskon_persen)) || ((j - 1) == gx(diskon_nominal))) {
                            if (pane.ckdiskon.isSelected()) {
                                if ((j - 1) == gx(diskon_nominal)) {
                                    int x = j - 1;
                                    for (int k = x; k >= 0; k--) {
                                        while (!cekcolomnol(k - 1)) {
                                            pane.tabledata.requestFocus();
                                            pane.tabledata.changeSelection(currentrow, k - 1, false, false);
                                            return;
                                        }
                                    }
                                } else {
                                    pane.tabledata.requestFocus();
                                    pane.tabledata.changeSelection(currentrow, gx(diskon_persen), false, false);
                                }
                            } else {
                                if ((j - 1) == 6) {
                                    int x = j - 1;
                                    for (int k = x; k >= 0; k--) {
                                        while (!cekcolomnol(k - 1)) {
                                            pane.tabledata.requestFocus();
                                            pane.tabledata.changeSelection(currentrow, k - 1, false, false);
                                            return;
                                        }
                                    }
                                } else {
                                    pane.tabledata.requestFocus();
                                    pane.tabledata.changeSelection(currentrow, gx(diskon_persen), false, false);
                                }
                            }
                        } else {
                            pane.tabledata.requestFocus();
                            pane.tabledata.changeSelection(currentrow, j - 1, false, false);
                        }
                        return;
                    }
                }
            }
        }
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

        String id_barang, kode_barang, stok, nama_barang, jumlah,
             id_satuan, nama_satuan, isi_satuan, id_satuan_pengali, harga_jual, diskon_persen, diskon_nominal,
             id_pajak, nama_pajak, nilai_pajak, id_gudang, nama_gudang, keterangan, total;

        public Entitytabledata(String id_barang, String kode_barang, String stok, String nama_barang, String jumlah, String id_satuan, String nama_satuan, String isi_satuan, String id_satuan_pengali, String harga_jual, String diskon_persen, String diskon_nominal, String id_pajak, String nama_pajak, String nilai_pajak, String id_gudang, String nama_gudang, String keterangan, String total) {
            this.id_barang = id_barang;
            this.kode_barang = kode_barang;
            this.stok = stok;
            this.nama_barang = nama_barang;
            this.jumlah = jumlah;
            this.id_satuan = id_satuan;
            this.nama_satuan = nama_satuan;
            this.isi_satuan = isi_satuan;
            this.id_satuan_pengali = id_satuan_pengali;
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

        public String getStok() {
            return stok;
        }

        public void setStok(String stok) {
            this.stok = stok;
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
