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
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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
import mizanposapp.helper.ConvertFunc;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Globalsession;
import mizanposapp.helper.Staticvar;
import mizanposapp.helper.Tablestyle;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Errorpanel;
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.pembelian.Daftarorderpembelian_inner_panel;
import mizanposapp.view.innerpanel.pembelian.Daftarorderpembelian_input_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class DaftarorderpembelianinputController {

    String id;
    CrudHelper ch = new CrudHelper();
    Daftarorderpembelian_input_panel pane;
    String valsupplier, valgudang, valdept, valsalesman, valshipvia, valtop,
            valakun_pembelian, valakun_ongkir, valakun_diskon, valakun_uang_muka;
    int valcheck;
    int tipe_bayar, tipe_beli, status_selesai;
    DefaultTableModel dtmtabeldata = new DefaultTableModel();
    Object[] rowtabledata = new Object[12];
    ArrayList<Entitytabledata> tabeldatalist = new ArrayList<>();
    int col = 0;
    //int istunai = 0;
    //int isjasa = 0;
    //int row = 0;
    NumberFormat nf = NumberFormat.getInstance();
    double total_pembelian_all = 0;
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
    String test;

    private boolean ischangevalue = false;
    static String oldvalue = "";

    ArrayList<Integer> lsvisiblecolom = new ArrayList<>();
    static boolean sudahterpanggil = false;

    public DaftarorderpembelianinputController(Daftarorderpembelian_input_panel pane) {
        this.pane = pane;
        skinning();
        loadsession();
        loaddata();
        simpandata();
        checkandcombocontrol();
        carisuplier();
        carigudang();
        caridepartment();
        carisalesman();
        carishipvia();
        caritop();
        cariakunpembelian();
        cariakundiskon();
        cariakunongkir();
        cariakunuangmuka();
        addtotable();
        kalkulasi();
        hapusbaris();
        tambahbaris();
        batal();

    }

    private void loadsession() {
        pane.edakun_pembelian.setText(Globalsession.AKUNPEMBELIANTUNAI + "-" + Globalsession.NAMAAKUNPEMBELIANTUNAI);
        pane.edakun_uang_muka.setText(Globalsession.AKUNUANGMUKAPEMBELIAN + "-" + Globalsession.NAMAAKUNUANGMUKAPEMBELIAN);
        pane.edakun_diskon_pembelian.setText(Globalsession.AKUNDISKONPEMBELIAN + "-" + Globalsession.NAMAAKUNDISKONPEMBELIAN);
        pane.edakun_ongkir.setText(Globalsession.AKUNONGKOSKIRIMPEMBELIAN + "-" + Globalsession.NAMAAKUNONGKOSKIRIMPEMBELIAN);
        valakun_pembelian = Globalsession.AKUNPEMBELIANTUNAI;
        valakun_uang_muka = Globalsession.AKUNUANGMUKAPEMBELIAN;
        valakun_diskon = Globalsession.AKUNDISKONPEMBELIAN;
        valakun_ongkir = Globalsession.AKUNONGKOSKIRIMPEMBELIAN;
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
                try {
                    JSONParser jpdata = new JSONParser();
                    String param = String.format("id_keltrans=%s", "32");
                    Object ob = jpdata.parse(ch.getdatadetails("getnomortransaksi", param));
                    JSONArray ja = (JSONArray) ob;
                    for (int i = 0; i < ja.size(); i++) {
                        JSONObject jo = (JSONObject) ja.get(i);
                        pane.edno_transaksi.setText(String.valueOf(jo.get("no_transaksi")));
                        no_urut = ConvertFunc.ToInt(String.valueOf(jo.get("no_urut")));
                    }

                } catch (ParseException ex) {
                    Logger.getLogger(DaftarorderpembelianinputController.class.getName()).log(Level.SEVERE, null, ex);
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
                    hidetable(7);
                    showtable(6);
                    valcheck = 0;

                } else {
                    hidetable(6);
                    showtable(7);
                    valcheck = 1;
                }
            }
        });

        pane.cmb_tipe_bayar.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (pane.cmb_tipe_bayar.getSelectedIndex() == 0) {
                    pane.eduang_muka.setText("0");
                    pane.eduang_muka.setEnabled(false);
                    pane.edtop.setVisible(false);
                    pane.bcaritop.setVisible(false);
                    pane.ltop.setVisible(false);
                    tipe_bayar = 0;
                    valtop = "";
                    pane.edakun_pembelian.setText(Globalsession.AKUNPEMBELIANTUNAI + "-" + Globalsession.NAMAAKUNPEMBELIANTUNAI);
                    valakun_pembelian = Globalsession.AKUNPEMBELIANTUNAI;
                } else {
                    pane.eduang_muka.setEnabled(true);
                    pane.edtop.setVisible(true);
                    pane.bcaritop.setVisible(true);
                    pane.ltop.setVisible(true);
                    tipe_bayar = 1;
                    valtop = "";
                    pane.edakun_pembelian.setText(Globalsession.AKUNHUTANGUSAHA + "-" + Globalsession.NAMAAKUNHUTANGUSAHA);
                    valakun_pembelian = Globalsession.AKUNHUTANGUSAHA;
                }
            }
        });

        pane.cmb_tipe_pembelian.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                JComboBox jc = (JComboBox) e.getSource();
                if (jc.getSelectedIndex() == 0) {
                    tipe_beli = 0;
                    lshide.set(3, lsoldhide.get(3));
                    lshide.set(5, lsoldhide.get(5));
                    lshide.set(9, lsoldhide.get(9));
                    lsresize.set(3, lsoldsize.get(3));
                    lsresize.set(5, lsoldsize.get(5));
                    lsresize.set(9, lsoldsize.get(9));
                    setheader();
                    if (pane.ckdiskon.isSelected()) {
                        hidetable(7);
                        showtable(6);
                        valcheck = 0;

                    } else {
                        hidetable(6);
                        showtable(7);
                        valcheck = 1;
                    }
                } else {
                    tipe_beli = 1;
                    lshide.set(3, 0);
                    lshide.set(5, 0);
                    lshide.set(9, 0);
                    lsresize.set(3, 0);
                    lsresize.set(5, 0);
                    lsresize.set(9, 0);
                    setheader();
                    if (pane.ckdiskon.isSelected()) {
                        hidetable(7);
                        showtable(6);
                        valcheck = 0;

                    } else {
                        hidetable(6);
                        showtable(7);
                        valcheck = 1;
                    }
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
            String dataheader = ch.getheaders();
            JSONParser jpheader = new JSONParser();
            Object objheader = jpheader.parse(dataheader);
            JSONObject joheader = (JSONObject) objheader;
            JSONArray jaheader = (JSONArray) joheader.get("inputorderpembelian");
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
            Logger.getLogger(DaftarorderpembelianinnerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        new Tablestyle(pane.tabledata).applystyleheader();

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
                pane.ckselesai.setSelected(false);
                status_selesai = 0;
                pane.cmb_tipe_pembelian.setSelectedIndex(0);
                pane.edtop.setVisible(false);
                pane.bcaritop.setVisible(false);
                pane.ltop.setVisible(false);
                tipe_beli = 0;
                pane.cmb_tipe_bayar.setSelectedIndex(0);
                pane.eduang_muka.setEnabled(false);
                pane.dtanggal.setDate(new Date());
                pane.dtanggal_pengantaran.setDate(new Date());
                pane.edsupplier.setText("");
                valsupplier = "";
                pane.edno_transaksi.setText("");
                pane.eddept.setText(Globalsession.DEFAULT_DEPT_NAME);
                valdept = Globalsession.DEFAULT_DEPT_ID;
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

                tabeldatalist.add(new Entitytabledata("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
                dtmtabeldata.addRow(rowtabledata);
                pane.tabledata.setModel(dtmtabeldata);
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
                    hidetable(7);
                    showtable(6);
                } else {
                    showtable(7);
                    hidetable(6);
                }

            } else {

                JSONParser jpdata = new JSONParser();
                String param = String.format("id=%s", id);
                Object rawobjdata = jpdata.parse(ch.getdatadetails("dataorderpembelian", param));
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
                        Logger.getLogger(DaftarorderpembelianinputController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

                Object jopembelian = jsonobjdata.get("pembelian");
                JSONArray japembelian = (JSONArray) jopembelian;

                for (int i = 0; i < japembelian.size(); i++) {
                    JSONObject joinpembelian = (JSONObject) japembelian.get(i);
                    valsupplier = String.valueOf(joinpembelian.get("id_supplier"));
                    pane.edsupplier.setText(String.valueOf(joinpembelian.get("nama_supplier")));

                    status_selesai = ConvertFunc.ToInt(joinpembelian.get("isorderselesai"));
                    if (status_selesai == 0) {
                        pane.ckselesai.setSelected(false);
                    } else {
                        pane.ckselesai.setSelected(true);
                    }

                    tipe_bayar = ConvertFunc.ToInt(joinpembelian.get("tipe_pembayaran"));
                    if (tipe_bayar == 0) {
                        pane.cmb_tipe_bayar.setSelectedIndex(0);
                    } else {
                        pane.cmb_tipe_bayar.setSelectedIndex(1);
                    }

                    if (pane.cmb_tipe_bayar.getSelectedIndex() == 0) {
                        pane.eduang_muka.setText("0");
                        pane.eduang_muka.setEnabled(false);
                        pane.edtop.setVisible(false);
                        pane.bcaritop.setVisible(false);
                        pane.ltop.setVisible(false);
                        valtop = "";
                        pane.edakun_pembelian.setText(Globalsession.AKUNPEMBELIANTUNAI + "-" + Globalsession.NAMAAKUNPEMBELIANTUNAI);
                    } else {
                        pane.eduang_muka.setEnabled(true);
                        pane.edtop.setVisible(true);
                        pane.bcaritop.setVisible(true);
                        pane.ltop.setVisible(true);
                        valtop = "";
                        pane.edakun_pembelian.setText(Globalsession.AKUNHUTANGUSAHA + "-" + Globalsession.NAMAAKUNHUTANGUSAHA);
                    }
                    try {
                        pane.dtanggal_pengantaran.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(String.valueOf(joinpembelian.get("tanggal_pengantaran")))));
                    } catch (java.text.ParseException ex) {
                        Logger.getLogger(DaftarorderpembelianinputController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    valshipvia = String.valueOf(joinpembelian.get("id_pengantaran"));
                    pane.edshipvia.setText(String.valueOf(joinpembelian.get("nama_pengantaran")));

                    valgudang = String.valueOf(joinpembelian.get("id_gudang"));
                    pane.edgudang.setText(String.valueOf(joinpembelian.get("nama_gudang")));

                    valtop = String.valueOf(joinpembelian.get("id_termofpayment"));
                    pane.edtop.setText(String.valueOf(joinpembelian.get("top")));

                    pane.ltotal_pembelian.setText(String.valueOf(joinpembelian.get("total_pembelian")));

                    pane.edbiayalain.setText(String.valueOf(joinpembelian.get("total_biaya")));
                    pane.eddiskon1.setText(String.valueOf(joinpembelian.get("diskon_persen")));
                    pane.eddiskon2.setText(String.valueOf(joinpembelian.get("diskon_nominal")));

                    pane.eduang_muka.setText(String.valueOf(joinpembelian.get("total_uang_muka")));

                    pane.ltotal_pajak.setText(String.valueOf(joinpembelian.get("total_pajak")));

                    pane.edakun_pembelian.setText(String.valueOf(joinpembelian.get("akun_pembelian")) + "-"
                            + String.valueOf(joinpembelian.get("nama_akun_pembelian")));

                    valakun_pembelian = String.valueOf(joinpembelian.get("akun_pembelian"));

                    pane.edakun_ongkir.setText(String.valueOf(joinpembelian.get("akun_biaya")) + "-"
                            + String.valueOf(joinpembelian.get("nama_akun_biaya")));

                    valakun_ongkir = String.valueOf(joinpembelian.get("akun_biaya"));

                    pane.edakun_diskon_pembelian.setText(String.valueOf(joinpembelian.get("akun_diskon")) + "-"
                            + String.valueOf(joinpembelian.get("nama_akun_diskon")));

                    valakun_diskon = String.valueOf(joinpembelian.get("akun_diskon"));

                    pane.edakun_uang_muka.setText(String.valueOf(joinpembelian.get("akun_uang_muka")) + "-"
                            + String.valueOf(joinpembelian.get("nama_akun_uang_muka")));

                    valakun_uang_muka = String.valueOf(joinpembelian.get("akun_uang_muka"));

                    valcheck = Integer.parseInt(String.valueOf(joinpembelian.get("diskon_dalam")));
                    if (valcheck == 0) {
                        pane.ckdiskon.setSelected(true);
                    } else {
                        pane.ckdiskon.setSelected(false);
                    }

                    if (pane.ckdiskon.isSelected()) {
                        hidetable(7);
                        showtable(6);
                    } else {
                        hidetable(6);
                        showtable(7);
                    }

                    valsalesman = String.valueOf(joinpembelian.get("id_bagian_pembelian"));
                    pane.edsalesman.setText(String.valueOf(joinpembelian.get("nama_bagian_pembelian")));

                    tipe_beli = ConvertFunc.ToInt(joinpembelian.get("tipe_pembelian"));
                    pane.cmb_tipe_pembelian.setSelectedIndex(tipe_beli);

                    if (pane.cmb_tipe_pembelian.getSelectedIndex() == 0) {
                        lshide.set(3, lsoldhide.get(3));
                        lshide.set(9, lsoldhide.get(9));
                        lsresize.set(3, lsoldsize.get(3));
                        lsresize.set(9, lsoldsize.get(9));
                        setheader();
                        if (pane.ckdiskon.isSelected()) {
                            hidetable(7);
                            showtable(6);
                            valcheck = 0;

                        } else {
                            hidetable(6);
                            showtable(7);
                            valcheck = 1;
                        }
                    } else {
                        lshide.set(3, 0);
                        lshide.set(9, 0);
                        lsresize.set(3, 0);
                        lsresize.set(9, 0);
                        setheader();
                        if (pane.ckdiskon.isSelected()) {
                            hidetable(7);
                            showtable(6);
                            valcheck = 0;

                        } else {
                            hidetable(6);
                            showtable(7);
                            valcheck = 1;
                        }
                    }
                }

                Object objtabeldata = jsonobjdata.get("pembelian_detail");
                JSONArray jatabledata = (JSONArray) objtabeldata;
                for (int i = 0; i < jatabledata.size(); i++) {
                    JSONObject jointabeldata = (JSONObject) jatabledata.get(i);
                    String id_barang = "";
                    String kode_barang = "";
                    String nama_barang = "";
                    if (pane.cmb_tipe_pembelian.getSelectedIndex() == 0) {
                        id_barang = String.valueOf(jointabeldata.get("id_inv"));
                        kode_barang = String.valueOf(jointabeldata.get("kode_inv"));
                        nama_barang = String.valueOf(jointabeldata.get("nama_inv"));
                    } else {
                        id_barang = String.valueOf(jointabeldata.get("akun"));
                        kode_barang = String.valueOf(jointabeldata.get("akun"));
                        nama_barang = String.valueOf(jointabeldata.get("nama_akun"));
                    }
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
                    String total = nf.format(kalkulasitotalperindex(diskon_persen, diskon_nominal, jumlah, harga_beli, isi_satuan));
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
            Logger.getLogger(DaftarorderpembelianinputController.class.getName()).log(Level.SEVERE, null, ex);
        }
        int[] columnright = {2, 4, 5, 6, 7, 11};
        new Tablestyle(pane.tabledata).applystylerow(columnright);
    }

    private void rawsimpan() {
        if (id.equals("")) {
            String data = "genjur="
                    + "id_keltrans='32'::"
                    + "id_dept='" + valdept + "'::"
                    + "tanggal='" + new SimpleDateFormat("yyyy-MM-dd").format(pane.dtanggal.getDate()) + "'::"
                    + "noref='" + ConvertFunc.EncodeString(pane.edno_transaksi.getText()) + "'::"
                    + "keterangan='" + ConvertFunc.EncodeString(pane.edketerangan.getText()) + "'"
                    + "&pembelian="
                    + "id_supplier='" + valsupplier + "'::"
                    + "tipe_pembayaran='" + String.valueOf(tipe_bayar) + "'::"
                    + "id_gudang='" + valgudang + "'::"
                    + "total_pembelian='" + total_pembelian_all + "'::"
                    + "total_biaya='" + ConvertFunc.ToDouble(pane.edbiayalain.getText()) + "'::"
                    + "diskon_persen='" + ConvertFunc.ToDouble(pane.eddiskon1.getText()) + "'::"
                    + "diskon_nominal='" + ConvertFunc.ToDouble(pane.eddiskon2.getText()) + "'::"
                    + "total_uang_muka='" + ConvertFunc.ToDouble(pane.eduang_muka.getText()) + "'::"
                    + "total_pajak='" + total_pajak + "'::"
                    + "id_currency='" + Globalsession.DEFAULT_CURRENCY_ID + "'::"
                    + "nilai_kurs='1'::"
                    + "akun_pembelian='" + valakun_pembelian + "'::"
                    + "akun_biaya='" + valakun_ongkir + "'::"
                    + "akun_diskon='" + valakun_diskon + "'::"
                    + "akun_uang_muka='" + valakun_uang_muka + "'::"
                    + "diskon_dalam='" + valcheck + "'::"
                    + "tanggal_pengantaran='" + new SimpleDateFormat("yyyy-MM-dd").format(pane.dtanggal_pengantaran.getDate()) + "'::"
                    + "id_pengantaran='" + valshipvia + "'::"
                    + "id_bagian_pembelian='" + valsalesman + "'::"
                    + "id_termofpayment='" + valtop + "'::"
                    + "tipe_pembelian='" + tipe_beli + "'::"
                    + "isorderselesai='" + status_selesai + "'"
                    + "&" + kirimtexpembelian();

            ch.insertdata("insertorderpembelian", data);
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
                        pane.cmb_tipe_bayar.setSelectedIndex(0);
                        pane.eduang_muka.setText("0");
                        pane.edbiayalain.setText("0");
                        pane.lsubtotal.setText("0");
                        pane.ltotal_pajak.setText("0");
                        pane.ltotal_pembelian.setText("0");
                    } else {
                        Daftarorderpembelian_inner_panel inpane = new Daftarorderpembelian_inner_panel();
                        Staticvar.pmp.container.removeAll();
                        Staticvar.pmp.container.setLayout(new BorderLayout());
                        Staticvar.pmp.container.add(inpane, BorderLayout.CENTER);
                        Staticvar.pmp.container.revalidate();
                        Staticvar.pmp.container.repaint();
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
                    + "id_keltrans='32'::"
                    + "id_dept='" + valdept + "'::"
                    + "tanggal='" + new SimpleDateFormat("yyyy-MM-dd").format(pane.dtanggal.getDate()) + "'::"
                    + "noref='" + ConvertFunc.EncodeString(pane.edno_transaksi.getText()) + "'::"
                    + "keterangan='" + ConvertFunc.EncodeString(pane.edketerangan.getText()) + "'"
                    + "&pembelian="
                    + "id_supplier='" + valsupplier + "'::"
                    + "tipe_pembayaran='" + String.valueOf(tipe_bayar) + "'::"
                    + "id_gudang='" + valgudang + "'::"
                    + "total_pembelian='" + total_pembelian_all + "'::"
                    + "total_biaya='" + ConvertFunc.ToDouble(pane.edbiayalain.getText()) + "'::"
                    + "diskon_persen='" + ConvertFunc.ToDouble(pane.eddiskon1.getText()) + "'::"
                    + "diskon_nominal='" + ConvertFunc.ToDouble(pane.eddiskon2.getText()) + "'::"
                    + "total_uang_muka='" + ConvertFunc.ToDouble(pane.eduang_muka.getText()) + "'::"
                    + "total_pajak='" + total_pajak + "'::"
                    + "id_currency='" + Globalsession.DEFAULT_CURRENCY_ID + "'::"
                    + "nilai_kurs='1'::"
                    + "akun_pembelian='" + valakun_pembelian + "'::"
                    + "akun_biaya='" + valakun_ongkir + "'::"
                    + "akun_diskon='" + valakun_diskon + "'::"
                    + "akun_uang_muka='" + valakun_uang_muka + "'::"
                    + "diskon_dalam='" + valcheck + "'::"
                    + "tanggal_pengantaran='" + new SimpleDateFormat("yyyy-MM-dd").format(pane.dtanggal_pengantaran.getDate()) + "'::"
                    + "id_pengantaran='" + valshipvia + "'::"
                    + "id_bagian_pembelian='" + valsalesman + "'::"
                    + "id_termofpayment='" + valtop + "'::"
                    + "tipe_pembelian='" + tipe_beli + "'::"
                    + "isorderselesai='" + status_selesai + "'"
                    + "&" + kirimtexpembelian();
            ch.updatedata("updateorderpembelian", data, id);
            if (Staticvar.getresult.equals("berhasil")) {
                Daftarorderpembelian_inner_panel inpane = new Daftarorderpembelian_inner_panel();
                Staticvar.pmp.container.removeAll();
                Staticvar.pmp.container.setLayout(new BorderLayout());
                Staticvar.pmp.container.add(inpane, BorderLayout.CENTER);
                Staticvar.pmp.container.revalidate();
                Staticvar.pmp.container.repaint();
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
                if (pane.edsupplier.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Supplier tidak boleh kosong", "Informasi", JOptionPane.INFORMATION_MESSAGE);

                } else if (tabeldatalist.size() == 0) {
                    JOptionPane.showMessageDialog(null, "Table Tidak Boleh Kosong", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    int tahunbulan = Integer.parseInt(new SimpleDateFormat("yyyyMM").format(pane.dtanggal.getDate()));
                    int periodetahunnulan = Integer.parseInt(Globalsession.PERIODE_TAHUN + Globalsession.PERIODE_BULAN);
                    if (tahunbulan > periodetahunnulan) {
                        int dialog = JOptionPane.showConfirmDialog(null, "Tanggal transaksi setelah periode akuntansi.\n"
                                + "Apakah anda ingin melanjutkan transaksi ?", "Konfirmasi", JOptionPane.YES_NO_OPTION, 1);
                        if (dialog == 0) {
                            double inuangmuka = ConvertFunc.ToDouble(pane.eduang_muka.getText());
                            if (inuangmuka >= total_pembelian_all) {
                                JOptionPane.showMessageDialog(null, "Uang Muka tidak boleh lebih besar dari Grand Total", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                rawsimpan();
                            }

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
                        double inuangmuka = ConvertFunc.ToDouble(pane.eduang_muka.getText());
                        if (inuangmuka >= total_pembelian_all) {
                            JOptionPane.showMessageDialog(null, "Uang Muka tidak boleh lebih besar dari Grand Total", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            rawsimpan();
                        }
                    }
                }
            }
        });
    }

    private String kirimtexpembelian() {
        StringBuilder sb = new StringBuilder();
        sb.append("pembelian_detail=");
        int listcount = 0;
        if (tabeldatalist.get(tabeldatalist.size() - 1).getId_barang().equals("")) {
            listcount = tabeldatalist.size() - 1;
        } else {
            listcount = tabeldatalist.size();
        }
        for (int i = 0; i < listcount; i++) {
            sb.append("id_inv=" + "'" + tabeldatalist.get(i).getId_barang() + "'" + "::"
                    + "qty=" + "'" + ConvertFunc.ToDouble(tabeldatalist.get(i).getJumlah()) + "'" + "::"
                    + "harga=" + "'" + ConvertFunc.ToDouble(tabeldatalist.get(i).getHarga_beli()) + "'" + "::"
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
        if (tipe_beli == 1) {
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
                Runnable rn = new Runnable() {
                    @Override
                    public void run() {
                        tabeldatalist.remove(row);
                        dtmtabeldata.removeRow(row);
                        pane.tabledata.repaint();
                        kalkulasitotal();
                    }
                };
                SwingUtilities.invokeLater(rn);

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
                        String data = String.format("id_keltrans=%s&no_urut=%s", "3", String.valueOf(no_urut));
                        ch.insertdata("insertnomorgagal", data);
                        Daftarorderpembelian_inner_panel inpane = new Daftarorderpembelian_inner_panel();
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
            Staticvar.sfilter = "";
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

    private void cariakunpembelian() {
        pane.bcariakun_pembelian.addActionListener((ActionEvent e) -> {
            rawgetidakun(pane.edakun_pembelian.getText(), valakun_pembelian);
            if (!Staticvar.resid.equals(valakun_pembelian)) {
                valakun_pembelian = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.edakun_pembelian.setText(val);
            }
        });

    }

    private void cariakundiskon() {
        pane.bcariakun_diskon_pembelian.addActionListener((ActionEvent e) -> {
            rawgetidakun(pane.edakun_diskon_pembelian.getText(), valakun_diskon);
            if (!Staticvar.resid.equals(valakun_diskon)) {
                valakun_diskon = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.edakun_diskon_pembelian.setText(val);
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
                        Staticvar.prelabel = defnilai;
                        try {
                            if (tipe_beli == 1) {
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
                                    tm.setValueAt(String.valueOf(Staticvar.resid), row, 0);
                                    if (!Staticvar.preid.equals(Staticvar.resid)) {
                                        tm.setValueAt(String.valueOf(Staticvar.reslabel), row, 1);
                                        tabeldatalist.get(row).setDiskon_persen("0");
                                        tabeldatalist.get(row).setDiskon_nominal("0");
                                        tabeldatalist.get(row).setTotal("0");
                                        tabeldatalist.get(row).setJumlah("0");
                                        tabeldatalist.get(row).setIsi_satuan("1");
                                        tabeldatalist.get(row).setHarga_beli("0");
                                        tabeldatalist.get(row).setHarga_jual("0");
                                        tm.setValueAt("0", row, 4);
                                        tm.setValueAt("0", row, 6);
                                        tm.setValueAt("0", row, 7);
                                        tm.setValueAt("0", row, 11);
                                        kalkulasitotalperrow(row);
                                        pane.tabledata.requestFocus();
                                        pane.tabledata.changeSelection(row, 2, false, false);
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
                                        tm.setValueAt(String.valueOf(joindata.get("nama")), row, 1);
                                        tabeldatalist.get(row).setJumlah("1");
                                        tm.setValueAt("0", row, 2);
                                        tabeldatalist.get(row).setId_satuan(String.valueOf(joindata.get("id_satuan")));
                                        tm.setValueAt(String.valueOf(joindata.get("nama_satuan")), row, 3);
                                        tabeldatalist.get(row).setId_satuan_pengali(String.valueOf(joindata.get("id_satuan")));
                                        tabeldatalist.get(row).setIsi_satuan("1");
                                        tabeldatalist.get(row).setHarga_beli(String.valueOf(joindata.get("harga_beli")));
                                        tm.setValueAt(nf.format(ConvertFunc.ToDouble(joindata.get("harga_beli"))), row, 4);
                                        tabeldatalist.get(row).setHarga_jual(String.valueOf(joindata.get("harga_jual")));
                                        tm.setValueAt(nf.format(ConvertFunc.ToDouble(joindata.get("harga_jual"))), row, 5);
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
                                            tabeldatalist.get(row).setId_satuan_pengali(String.valueOf(joindata2.get("id_satuan")));
                                            tabeldatalist.get(row).setIsi_satuan("1");
                                            tabeldatalist.get(row).setHarga_beli(String.valueOf(joindata2.get("harga_beli")));
                                            tm.setValueAt(nf.format(ConvertFunc.ToDouble(joindata2.get("harga_beli"))), row, 4);
                                            tabeldatalist.get(row).setHarga_jual(String.valueOf(joindata2.get("harga_jual")));
                                            tm.setValueAt(nf.format(ConvertFunc.ToDouble(joindata2.get("harga_jual"))), row, 5);
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
                            Logger.getLogger(DaftarorderpembelianinputController.class.getName()).log(Level.SEVERE, null, ex);

                        } finally {
                            ischangevalue = false;
                        }
                    } else if (col == 2) {
                        try {
                            ischangevalue = true;
                            tabeldatalist.get(row).setJumlah(String.valueOf(tm.getValueAt(row, 2)));
                            columnfunction(row, 2, false);
                            //nextcolom(2, row);
                        } catch (Exception ex) {
                        } finally {
                            ischangevalue = false;
                        }
                    } else if (col == 4) {
                        try {
                            ischangevalue = true;
                            tabeldatalist.get(row).setHarga_beli(String.valueOf(tm.getValueAt(row, 4)));
                            columnfunction(row, 4, false);
                            //nextcolom(4, row);
                        } catch (Exception ex) {
                        } finally {
                            ischangevalue = false;
                        }
                    } else if (col == 6) {
                        try {
                            ischangevalue = true;
                            String valcol = String.valueOf(pane.tabledata.getValueAt(row, 6));
                            if (checkalphabeth(valcol) == false) {
                                tabeldatalist.get(row).setDiskon_persen(String.valueOf(tm.getValueAt(row, 6)));
                                columnfunction(row, 6, false);
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
                                pane.tabledata.setValueAt(valcol, row, 6);
                                columnfunction(row, 6, false);
                                pane.tabledata.requestFocus();
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
                                tabeldatalist.get(row).setDiskon_nominal(String.valueOf(ConvertFunc.ToDouble(tm.getValueAt(row, 7))));
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
                                pane.tabledata.setValueAt(valcol, row, 7);
                                columnfunction(row, 7, false);
                                //nextcolom(7, row);
                            }
                        } catch (Exception ex) {
                        } finally {
                            ischangevalue = false;
                        }
                    } else if (col == 10) {
                        tabeldatalist.get(row).setKeterangan(String.valueOf(tm.getValueAt(row, 10)));
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
                if (e.getClickCount() == 2) {
                    if (col == 0) {
                        if (tipe_beli == 1) {
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
                                pane.tabledata.setValueAt(String.valueOf(Staticvar.resid), row, 0);
                                pane.tabledata.setValueAt(String.valueOf(Staticvar.reslabel), row, 1);
                                tabeldatalist.get(row).setDiskon_persen("0");
                                tabeldatalist.get(row).setDiskon_nominal("0");
                                tabeldatalist.get(row).setTotal("0");
                                tabeldatalist.get(row).setJumlah("0");
                                tabeldatalist.get(row).setIsi_satuan("1");
                                tabeldatalist.get(row).setHarga_beli("0");
                                tabeldatalist.get(row).setHarga_jual("0");
                                pane.tabledata.setValueAt("0", row, 4);
                                pane.tabledata.setValueAt("0", row, 6);
                                pane.tabledata.setValueAt("0", row, 7);
                                pane.tabledata.setValueAt("0", row, 11);
                                kalkulasitotalperrow(row);
                                pane.tabledata.requestFocus();
                                pane.tabledata.changeSelection(row, 2, false, false);
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
                                defnilai = String.valueOf(pane.tabledata.getValueAt(row, 0));
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
                                            pane.tabledata.setValueAt(String.valueOf(joindata2.get("kode")), row, 0);
                                            pane.tabledata.setValueAt(String.valueOf(joindata2.get("nama")), row, 1);
                                            tabeldatalist.get(row).setJumlah("0");
                                            tabeldatalist.get(row).setId_satuan(String.valueOf(joindata2.get("id_satuan")));
                                            pane.tabledata.setValueAt(String.valueOf(joindata2.get("nama_satuan")), row, 3);
                                            tabeldatalist.get(row).setId_satuan_pengali(String.valueOf(joindata2.get("id_satuan")));
                                            tabeldatalist.get(row).setIsi_satuan("1");
                                            tabeldatalist.get(row).setHarga_beli(String.valueOf(joindata2.get("harga_beli")));
                                            pane.tabledata.setValueAt(nf.format(ConvertFunc.ToDouble(joindata2.get("harga_beli"))), row, 4);
                                            tabeldatalist.get(row).setHarga_jual(String.valueOf(joindata2.get("harga_jual")));
                                            pane.tabledata.setValueAt(nf.format(ConvertFunc.ToDouble(joindata2.get("harga_jual"))), row, 5);
                                            pane.tabledata.setValueAt("0", row, 6);
                                            pane.tabledata.setValueAt("0", row, 7);
                                            tabeldatalist.get(row).setDiskon_persen("0");
                                            tabeldatalist.get(row).setDiskon_nominal("0");
                                            pane.tabledata.setValueAt(String.valueOf(joindata2.get("nama_pajak_beli")), row, 8);
                                            tabeldatalist.get(row).setId_pajak(String.valueOf(joindata2.get("id_pajak_beli")));
                                            tabeldatalist.get(row).setNilai_pajak(String.valueOf(joindata2.get("persen_pajak_beli")));
                                            tabeldatalist.get(row).setId_gudang(valgudang);
                                            pane.tabledata.setValueAt(pane.edgudang.getText(), row, 9);
                                            tabeldatalist.get(row).setKeterangan("");
                                            pane.tabledata.setValueAt("", row, 10);
                                            tabeldatalist.get(row).setTotal("0");
                                            pane.tabledata.setValueAt("0", row, 11);
                                        }
                                        kalkulasitotalperrow(row);
                                        pane.tabledata.requestFocus();
                                        pane.tabledata.changeSelection(row, 2, false, false);
                                    } else {
                                        pane.tabledata.requestFocus();
                                    }
                                } catch (ParseException ex) {
                                    Logger.getLogger(DaftarorderpembelianinputController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    } else if (col == 3) {
                        Staticvar.sfilter = "";
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
                        Staticvar.sfilter = "";
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
                        Staticvar.sfilter = "";
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
                if (col == 3) {
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
                    tabeldatalist.get(row).setId_satuan(Staticvar.resid);
                    pane.tabledata.setValueAt(Staticvar.reslabel, row, 3);
                    tabeldatalist.get(row).setIsi_satuan(Staticvar.resvalueextended);
                    dtmtabeldata.fireTableCellUpdated(row, 3);
                    kalkulasitotalperrow(row);
                } else if (col == 8) {
                    Staticvar.sfilter = "";
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
                    Staticvar.sfilter = "";
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
        if (!pane.tabledata.getValueAt(row, 0).equals("")
                || !pane.tabledata.getValueAt(row, 2).equals("")
                || !pane.tabledata.getValueAt(row, 3).equals("")
                || !pane.tabledata.getValueAt(row, 6).equals("")
                || !pane.tabledata.getValueAt(row, 7).equals("")) {
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
        if ((col == 2) || (col == 4) || (col == 7)) {
            String value = nf.format(ConvertFunc.ToDouble(pane.tabledata.getValueAt(row, col)));
            pane.tabledata.setValueAt(value, row, col);
            kalkulasitotalperrow(row);
            if (addrow == true) {
                addautorow(row);
            }
        } else if (col == 6) {
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
                    double subtotal = ConvertFunc.ToDouble(pane.lsubtotal.getText());
                    double biayalain = ConvertFunc.ToDouble(pane.edbiayalain.getText());
                    double diskon = ConvertFunc.ToDouble(pane.eddiskon2.getText());
                    double pajak = ConvertFunc.ToDouble(pane.ltotal_pajak.getText());
                    total_pembelian_all = subtotal + biayalain - diskon + pajak;
                    pane.ltotal_pembelian.setText(nf.format(total_pembelian_all));
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
                    double subtotal = ConvertFunc.ToDouble(pane.lsubtotal.getText());
                    double biayalain = ConvertFunc.ToDouble(pane.edbiayalain.getText());
                    double diskon_persen = ConvertFunc.ToDouble(pane.eddiskon1.getText());
                    double diskon_nominal = (subtotal + biayalain) * (diskon_persen / 100);
                    double pajak = ConvertFunc.ToDouble(pane.ltotal_pajak.getText());
                    total_pembelian_all = subtotal + biayalain - diskon_nominal + pajak;

                    pane.eddiskon2.setText(nf.format(diskon_nominal));
                    pane.ltotal_pembelian.setText(nf.format(total_pembelian_all));
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
                    double subtotal = ConvertFunc.ToDouble(pane.lsubtotal.getText());
                    double biayalain = ConvertFunc.ToDouble(pane.edbiayalain.getText());
                    double pajak = ConvertFunc.ToDouble(pane.ltotal_pajak.getText());
                    double diskon_nominal = ConvertFunc.ToDouble(pane.eddiskon2.getText());
                    double diskon_persen = (diskon_nominal / (subtotal + biayalain)) * 100;
                    total_pembelian_all = subtotal + biayalain - diskon_nominal + pajak;

                    pane.eddiskon1.setText(String.valueOf(diskon_persen));
                    pane.ltotal_pembelian.setText(nf.format(total_pembelian_all));
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
        double subtotal = 0;
        total_pembelian_all = 0;
        total_pajak = 0;

        for (int i = 0; i < jumlah_row; i++) {
            double total_beli_masing = ConvertFunc.ToDouble(emptycellcheck(i, 11));
            subtotal = subtotal + total_beli_masing;

            double total_pajak_masing = ConvertFunc.ToDouble(emptycellcheck(i, 11)) * (ConvertFunc.ToDouble(tabeldatalist.get(i).getNilai_pajak()) / 100);
            total_pajak = total_pajak + total_pajak_masing;
        }

        pane.ltotal_pajak.setText(nf.format(total_pajak));
        pane.lsubtotal.setText(nf.format(subtotal));

        double biayalain = ConvertFunc.ToDouble(pane.edbiayalain.getText());
        double pajak = ConvertFunc.ToDouble(pane.ltotal_pajak.getText());
        double diskon_persen = ConvertFunc.ToDouble(pane.eddiskon1.getText());
        double diskon_nominal = (subtotal + biayalain) * (diskon_persen / 100);
        total_pembelian_all = subtotal + biayalain - diskon_nominal + pajak;

        pane.eddiskon2.setText(nf.format(diskon_nominal));
        pane.ltotal_pembelian.setText(nf.format(total_pembelian_all));
    }

    private void kalkulasitotalperrow(int row) {
        if (pane.ckdiskon.isSelected() == true) {
            String isifielddiskon = String.valueOf(pane.tabledata.getValueAt(row, 6));
            if (isifielddiskon.contains("+")) {
                double qty = ConvertFunc.ToDouble(String.valueOf(pane.tabledata.getValueAt(row, 2))) * ConvertFunc.ToInt(tabeldatalist.get(row).getIsi_satuan());
                double harga = ConvertFunc.ToDouble(pane.tabledata.getValueAt(row, 4));
                double total = harga;
                String s = "";
                String[] multidiskon = isifielddiskon.split("\\+");
                for (int i = 0; i < multidiskon.length; i++) {
                    double diskonper = ConvertFunc.ToDouble(multidiskon[i]);
                    total = ((total - (diskonper / 100 * total)));
                }
                total = qty * total;
                tabeldatalist.get(row).setTotal(String.valueOf(total));
                pane.tabledata.setValueAt(nf.format(total), row, 11);
            } else {
                double qty = ConvertFunc.ToDouble(String.valueOf(pane.tabledata.getValueAt(row, 2))) * ConvertFunc.ToInt(tabeldatalist.get(row).getIsi_satuan());
                double harga = ConvertFunc.ToDouble(String.valueOf(pane.tabledata.getValueAt(row, 4)));
                double diskon = ConvertFunc.ToDouble(emptycellcheck(row, 6));
                double total = qty * (harga - (diskon / 100 * harga));
                tabeldatalist.get(row).setTotal(String.valueOf(total));
                pane.tabledata.setValueAt(nf.format(total), row, 11);
            }
        } else {

            double qty = ConvertFunc.ToDouble(String.valueOf(pane.tabledata.getValueAt(row, 2))) * ConvertFunc.ToInt(tabeldatalist.get(row).getIsi_satuan());
            double harga = ConvertFunc.ToDouble(pane.tabledata.getValueAt(row, 4));
            double diskon = ConvertFunc.ToDouble(emptycellcheck(row, 7));
            double total = qty * (harga - diskon);
            tabeldatalist.get(row).setTotal(String.valueOf(total));
            pane.tabledata.setValueAt(nf.format(total), row, 11);
        }
        kalkulasitotal();
    }

    private double kalkulasitotalperindex(String rawdiskonpersen, String rawdiskonnominal, String rawqty, String rawharga, String isisatuan) {
        double total = 0;
        if (pane.ckdiskon.isSelected() == true) {
            String isifielddiskon = rawdiskonpersen;
            if (isifielddiskon.contains("+")) {
                double qty = ConvertFunc.ToDouble(rawqty) * ConvertFunc.ToInt(isisatuan);
                double harga = ConvertFunc.ToDouble(rawharga);
                total = harga;
                String[] multidiskon = isifielddiskon.split("\\+");
                for (int i = 0; i < multidiskon.length; i++) {
                    double diskonper = ConvertFunc.ToDouble(multidiskon[i]);
                    total = ((total - (diskonper / 100 * total)));
                }
                total = qty * total;
            } else {
                double qty = ConvertFunc.ToDouble(rawqty) * ConvertFunc.ToInt(isisatuan);
                double harga = ConvertFunc.ToDouble(rawharga);
                double diskon = ConvertFunc.ToDouble(rawdiskonpersen);
                total = qty * (harga - (diskon / 100 * harga));
            }
        } else {

            double qty = ConvertFunc.ToDouble(rawqty) * ConvertFunc.ToInt(isisatuan);
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

    private boolean checknumerik(String val) {
        boolean hasil = false;
        try {
            ConvertFunc.ToDouble(val);
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
                pane.tabledata.changeSelection(row + 1, 0, false, false);
            } else if (String.valueOf(pane.tabledata.getValueAt(row, 0)).equals("")
                    || String.valueOf(pane.tabledata.getValueAt(row, 0)).equals("null")) {
                pane.tabledata.requestFocus();
                pane.tabledata.changeSelection(row, 0, false, false);
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
                        if (((j + 1) == 6) || ((j + 1) == 7)) {
                            if (pane.ckdiskon.isSelected()) {
                                if ((j + 1) == 7) {
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
                                    pane.tabledata.changeSelection(currentrow, 6, false, false);
                                }
                            } else {
                                pane.tabledata.requestFocus();
                                pane.tabledata.changeSelection(currentrow, 7, false, false);
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
                        if (((j - 1) == 6) || ((j - 1) == 7)) {
                            if (pane.ckdiskon.isSelected()) {
                                if ((j - 1) == 7) {
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
                                    pane.tabledata.changeSelection(currentrow, 6, false, false);
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
                                    pane.tabledata.changeSelection(currentrow, 7, false, false);
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
