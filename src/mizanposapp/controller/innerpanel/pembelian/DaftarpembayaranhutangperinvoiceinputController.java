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
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
import mizanposapp.helper.numtoword;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Errorpanel;
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.pembelian.Daftarhutang_inner_panel;
import mizanposapp.view.innerpanel.pembelian.Daftarhutangrincian_inner_panel;
import mizanposapp.view.innerpanel.pembelian.Daftarpembayaranhutangperinvoice_input_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class DaftarpembayaranhutangperinvoiceinputController {

    String id;
    String valsupplier = "", valakun_pengengeluaran = "", valakun_diskon = "", valdept = "";
    int no_urut = 0, valgiro = 0;
    CrudHelper ch = new CrudHelper();
    Daftarpembayaranhutangperinvoice_input_panel pane;
    DefaultTableModel dtmtabeldata = new DefaultTableModel();
    Object[] rowtabledata = new Object[6];
    ArrayList<Entitytabledata> tabeldatalist = new ArrayList<>();
    ArrayList<Integer> lshide = new ArrayList<>();
    ArrayList<Integer> lsoldhide = new ArrayList<>();
    ArrayList<Integer> lsresize = new ArrayList<>();
    ArrayList<Integer> lsoldsize = new ArrayList<>();
    private boolean ischangevalue = false;
    static String oldvalue = "";
    NumberFormat nf = NumberFormat.getInstance();

    public DaftarpembayaranhutangperinvoiceinputController(Daftarpembayaranhutangperinvoice_input_panel pane) {
        this.pane = pane;
        loadsession();
        skinning();
        loaddata();
        addtotable();
        simpandata();
        carisupplier();
        cariakunpembelian();
        caridepartment();
        cariakundiskon();
        tambahbaris();
        hapusbaris();
        cekandcombocontrol();
        batal();
    }

    private void loadsession() {
        pane.edakun_pengeluaran.setText(Globalsession.AKUNPEMBELIANTUNAI + "-" + Globalsession.NAMAAKUNPEMBELIANTUNAI);
        pane.edakun_diskon.setText(Globalsession.AKUNDISKONPEMBELIAN + "-" + Globalsession.NAMAAKUNDISKONPEMBELIAN);
        valakun_pengengeluaran = Globalsession.AKUNPEMBELIANTUNAI;
        valakun_diskon = Globalsession.AKUNDISKONPEMBELIAN;
    }

    private void skinning() {
        pane.dtanggal.setDateFormatString("dd MMMM yyyy");
        pane.dtanggal.setDate(new Date());
        pane.dtempo.setDateFormatString("dd MMMM yyyy");
        pane.dtempo.setDate(new Date());
        pane.dtanggal.getDateEditor().setEnabled(false);
        pane.dtempo.getDateEditor().setEnabled(false);
        pane.edtotal_nilai.setEnabled(false);

    }

    private void cekandcombocontrol() {
        pane.ckgiro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox jc = (JCheckBox) e.getSource();
                if (jc.isSelected() == true) {
                    pane.lnocek.setVisible(true);
                    pane.ltitik2nocek.setVisible(true);
                    pane.ednocek.setVisible(true);
                    pane.ltempo.setVisible(true);
                    pane.ltitik2tempo.setVisible(true);
                    pane.dtempo.setVisible(true);
                    valgiro = 1;
                } else {
                    pane.lnocek.setVisible(false);
                    pane.ltitik2nocek.setVisible(false);
                    pane.ednocek.setVisible(false);
                    pane.ltempo.setVisible(false);
                    pane.ltitik2tempo.setVisible(false);
                    pane.dtempo.setVisible(false);
                    valgiro = 0;
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
                return column == 0 || column == 2 || column == 3 ? false : true;
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

    private void getkodetransaksi() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONParser jpdata = new JSONParser();
                    String param = String.format("id_keltrans=%s", "42");
                    Object ob = jpdata.parse(ch.getdatadetails("getnomortransaksi", param));
                    JSONArray ja = (JSONArray) ob;
                    for (int i = 0; i < ja.size(); i++) {
                        JSONObject jo = (JSONObject) ja.get(i);
                        pane.ednoref.setText(String.valueOf(jo.get("no_transaksi")));
                        no_urut = ConvertFunc.ToInt(String.valueOf(jo.get("no_urut")));
                    }

                } catch (ParseException ex) {
                    Logger.getLogger(DaftarorderpembelianinputController.class.getName()).log(Level.SEVERE, null, ex);
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
            JSONArray jaheader = (JSONArray) joheader.get("inputpembayaranhutang");
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
                pane.ckgiro.setSelected(false);
                pane.edtotal_nilai.setEnabled(false);
                pane.lnocek.setVisible(false);
                pane.ltitik2nocek.setVisible(false);
                pane.ednocek.setVisible(false);
                pane.ltempo.setVisible(false);
                pane.ltitik2tempo.setVisible(false);
                pane.dtempo.setVisible(false);
                valgiro = 0;
                pane.dtanggal.setDate(new Date());
                pane.dtempo.setDate(new Date());
                pane.edsupplier.setText(String.valueOf(Staticvar.map_var.get("nama_supplier")));
                valsupplier = String.valueOf(Staticvar.map_var.get("id_supplier"));
                pane.eddept.setText(Globalsession.DEFAULT_DEPT_NAME);
                valdept = Globalsession.DEFAULT_DEPT_ID;
                pane.edketerangan.setText("");
                tabeldatalist.add(new Entitytabledata("", "", "", "", "", "", "", ""));
                dtmtabeldata.addRow(rowtabledata);
            } else {
                JSONParser jpdata = new JSONParser();
                String param = String.format("id=%s", id);
                Object rawobjdata = jpdata.parse(ch.getdatadetails("datapembayaranhutang", param));
                JSONObject jsonobjdata = (JSONObject) rawobjdata;

                Object jogenjur = jsonobjdata.get("genjur");
                JSONArray jagenjur = (JSONArray) jogenjur;

                for (int i = 0; i < jagenjur.size(); i++) {
                    JSONObject joingenjur = (JSONObject) jagenjur.get(i);
                    pane.ednoref.setText(String.valueOf(joingenjur.get("noref")));
                    pane.edketerangan.setText(String.valueOf(joingenjur.get("keterangan")));
                    pane.eddept.setText(String.valueOf(joingenjur.get("nama_dept")));
                    valdept = String.valueOf(joingenjur.get("id_dept"));
                    try {
                        pane.dtanggal.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(joingenjur.get("tanggal"))));
                    } catch (java.text.ParseException ex) {
                        Logger.getLogger(DaftarorderpembelianinputController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

                Object jopembelian = jsonobjdata.get("kaskeluar");
                JSONArray japembelian = (JSONArray) jopembelian;

                for (int i = 0; i < japembelian.size(); i++) {
                    JSONObject joinpembelian = (JSONObject) japembelian.get(i);
                    valsupplier = String.valueOf(joinpembelian.get("id_cards"));
                    pane.edsupplier.setText(String.valueOf(joinpembelian.get("nama_cards")));

                    pane.edtotal_nilai.setText(String.valueOf(joinpembelian.get("jumlah")));

                    valgiro = ConvertFunc.ToInt(joinpembelian.get("isgiro"));
                    if (valgiro == 0) {
                        pane.ckgiro.setSelected(false);
                    } else {
                        pane.ckgiro.setSelected(true);
                    }

                    if (pane.ckgiro.isSelected()) {
                        pane.ednocek.setVisible(true);
                    } else {
                        pane.ednocek.setVisible(false);
                    }

                    pane.ednocek.setText(String.valueOf(joinpembelian.get("no_giro")));

                    try {
                        pane.dtempo.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(String.valueOf(joinpembelian.get("tanggal_giro_jatuh_tempo")))));
                    } catch (java.text.ParseException ex) {
                        Logger.getLogger(DaftarorderpembelianinputController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    pane.edakun_pengeluaran.setText(String.valueOf(joinpembelian.get("akun_keluar_dari")) + "-"
                            + String.valueOf(joinpembelian.get("nama_akun_keluar_dari")));

                    valakun_pengengeluaran = String.valueOf(joinpembelian.get("akun_keluar_dari"));
                }

                Object jotabledata = jsonobjdata.get("kaskeluar_detail");
                JSONArray jatabledata = (JSONArray) jotabledata;
                for (int i = 0; i < jatabledata.size(); i++) {
                    JSONObject jointabledata = (JSONObject) jatabledata.get(i);
                    String id = String.valueOf(jointabledata.get("id"));
                    String akun = String.valueOf(jointabledata.get("akun"));
                    String noref = String.valueOf(jointabledata.get("noref"));
                    String tanggal = String.valueOf(jointabledata.get("tanggal"));
                    String totalhutang = String.valueOf(jointabledata.get("total"));
                    String sisahutang = String.valueOf(jointabledata.get("sisa"));
                    String diskon = String.valueOf(jointabledata.get("diskon"));
                    String jumlah_bayar = String.valueOf(jointabledata.get("jumlah"));
                    tabeldatalist.add(new Entitytabledata(id, akun, noref, tanggal, totalhutang, sisahutang, diskon, jumlah_bayar));
                }

                for (int i = 0; i < tabeldatalist.size(); i++) {
                    rowtabledata[0] = tabeldatalist.get(i).getNoref();
                    rowtabledata[1] = tabeldatalist.get(i).getTanggal();
                    rowtabledata[2] = tabeldatalist.get(i).getTotalhutang();
                    rowtabledata[3] = tabeldatalist.get(i).getSisahutang();
                    rowtabledata[4] = tabeldatalist.get(i).getDiskon();
                    rowtabledata[5] = tabeldatalist.get(i).getJumlah_bayar();
                    dtmtabeldata.addRow(rowtabledata);
                }
                pane.tabledata.setModel(dtmtabeldata);
                kalkulasi();
                for (int i = 0; i < rowtabledata.length; i++) {
                    rowtabledata[i] = "";
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int[] columnright = {2, 3, 4, 5};
        new Tablestyle(pane.tabledata).applystylerow(columnright);
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
                    if (col == 4) {
                        try {
                            ischangevalue = true;
                            tabeldatalist.get(row).setDiskon(String.valueOf(tm.getValueAt(row, 4)));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        } finally {
                            ischangevalue = false;
                        }
                    } else if (col == 5) {
                        try {
                            ischangevalue = true;
                            tabeldatalist.get(row).setJumlah_bayar(String.valueOf(tm.getValueAt(row, 5)));
                            kalkulasi();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        } finally {
                            ischangevalue = false;
                        }
                    }
                    ischangevalue = true;
                    String curval = String.valueOf(tm.getValueAt(row, col));
                    if (curval.equals("") || curval.equals("null")) {
                        if (oldvalue.equals("null")) {
                            tm.setValueAt("", row, col);
                        } else {
                            tm.setValueAt(oldvalue, row, col);
                        }
                        oldvalue = "";
                        if (col == 0) {
                            pane.tabledata.requestFocus();
                            pane.tabledata.changeSelection(row, 0, false, false);
                        }
                    }
                    ischangevalue = false;
                }
                kalkulasi();

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
                        Staticvar.preid = tabeldatalist.get(row).getId();
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
                        jd.add(new Popupcari("returpembelian", "popupdaftarhutangpersupplier?ishutang=1&id=" + valsupplier, "Daftar Hutang Usaha " + pane.edsupplier.getText()));
                        jd.pack();
                        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        jd.setLocationRelativeTo(null);
                        jd.setVisible(true);
                        jd.toFront();
                        if (!Staticvar.reslabel.equals("")) {
                            try {
                                if (!Staticvar.preid.equals(Staticvar.resid)) {
                                    JSONParser jpdata = new JSONParser();
                                    String param = String.format("id=%s", Staticvar.resid);
                                    Object objdataraw = jpdata.parse(ch.getdatadetails("datahutangpersupplier", param));
                                    JSONArray jadata = (JSONArray) objdataraw;
                                    for (int i = 0; i < jadata.size(); i++) {
                                        JSONObject joindata = (JSONObject) jadata.get(i);
                                        tabeldatalist.get(row).setAkun(String.valueOf(joindata.get("akun")));
                                        tabeldatalist.get(row).setId(String.valueOf(joindata.get("id")));
                                        tabeldatalist.get(row).setNoref(String.valueOf(joindata.get("noref")));
                                        pane.tabledata.setValueAt(String.valueOf(joindata.get("noref")), row, 0);
                                        tabeldatalist.get(row).setTanggal(String.valueOf(joindata.get("tanggal")));
                                        pane.tabledata.setValueAt(String.valueOf(joindata.get("tanggal")), row, 1);
                                        tabeldatalist.get(row).setTotalhutang(String.valueOf(joindata.get("total")));
                                        pane.tabledata.setValueAt(String.valueOf(joindata.get("total")), row, 2);
                                        tabeldatalist.get(row).setSisahutang(String.valueOf(joindata.get("sisa")));
                                        pane.tabledata.setValueAt(String.valueOf(joindata.get("sisa")), row, 3);
                                        tabeldatalist.get(row).setDiskon("0");
                                        pane.tabledata.setValueAt("0", row, 4);
                                        tabeldatalist.get(row).setJumlah_bayar(String.valueOf(joindata.get("sisa")));
                                        pane.tabledata.setValueAt(String.valueOf(joindata.get("sisa")), row, 5);
                                    }
                                    kalkulasi();
                                    addautorow(row);
                                } else {
                                    pane.tabledata.requestFocus();
                                }
                            } catch (ParseException ex) {
                                Logger.getLogger(DaftarorderpembelianinputController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

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
                if (col == 0) {
                    Staticvar.preid = tabeldatalist.get(row).getId();
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
                    jd.add(new Popupcari("returpembelian", "popupdaftarhutangpersupplier?ishutang=1&id=" + valsupplier, "Daftar Hutang Usaha " + pane.edsupplier.getText()));
                    jd.pack();
                    jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                    jd.setLocationRelativeTo(null);
                    jd.setVisible(true);
                    jd.toFront();
                    if (!Staticvar.reslabel.equals("")) {
                        try {
                            if (!Staticvar.preid.equals(Staticvar.resid)) {
                                JSONParser jpdata = new JSONParser();
                                String param = String.format("id=%s", Staticvar.resid);
                                Object objdataraw = jpdata.parse(ch.getdatadetails("datahutangpersupplier", param));
                                JSONArray jadata = (JSONArray) objdataraw;
                                for (int i = 0; i < jadata.size(); i++) {
                                    JSONObject joindata = (JSONObject) jadata.get(i);
                                    tabeldatalist.get(row).setAkun(String.valueOf(joindata.get("akun")));
                                    tabeldatalist.get(row).setId(String.valueOf(joindata.get("id")));
                                    tabeldatalist.get(row).setNoref(String.valueOf(joindata.get("noref")));
                                    pane.tabledata.setValueAt(String.valueOf(joindata.get("noref")), row, 0);
                                    tabeldatalist.get(row).setTanggal(String.valueOf(joindata.get("tanggal")));
                                    pane.tabledata.setValueAt(String.valueOf(joindata.get("tanggal")), row, 1);
                                    tabeldatalist.get(row).setTotalhutang(String.valueOf(joindata.get("total")));
                                    pane.tabledata.setValueAt(String.valueOf(joindata.get("total")), row, 2);
                                    tabeldatalist.get(row).setSisahutang(String.valueOf(joindata.get("sisa")));
                                    pane.tabledata.setValueAt(String.valueOf(joindata.get("sisa")), row, 3);
                                    tabeldatalist.get(row).setDiskon("0");
                                    pane.tabledata.setValueAt("0", row, 4);
                                    tabeldatalist.get(row).setJumlah_bayar(String.valueOf(joindata.get("sisa")));
                                    pane.tabledata.setValueAt(String.valueOf(joindata.get("sisa")), row, 5);
                                }
                                kalkulasi();
                                addautorow(row);
                            } else {
                                pane.tabledata.requestFocus();
                            }
                        } catch (ParseException ex) {
                            Logger.getLogger(DaftarpembayaranhutangperinvoiceinputController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });

    }

    private void addautorow(int row) {
        int lastrow = pane.tabledata.getRowCount() - 1;
        if (!pane.tabledata.getValueAt(row, 0).equals("")
                || !pane.tabledata.getValueAt(row, 0).equals("")
                || !pane.tabledata.getValueAt(row, 3).equals("")
                || !pane.tabledata.getValueAt(row, 4).equals("")
                || !pane.tabledata.getValueAt(row, 5).equals("")) {
            if (row == lastrow) {
                tabeldatalist.add(new Entitytabledata("", "", "", "", "", "", "", ""));
                dtmtabeldata.addRow(rowtabledata);
                pane.tabledata.requestFocus();
                pane.tabledata.changeSelection(row + 1, 0, false, false);
            }
        }
    }

    private void rawsimpan() {
        if (id.equals("")) {
            String data = "genjur="
                    + "id_keltrans='42'::"
                    + "id_dept='" + valdept + "'::"
                    + "tanggal='" + new SimpleDateFormat("yyyy-MM-dd").format(pane.dtanggal.getDate()) + "'::"
                    + "noref='" + ConvertFunc.EncodeString(pane.ednoref.getText()) + "'::"
                    + "keterangan='" + ConvertFunc.EncodeString(pane.edketerangan.getText()) + "'"
                    + "&kaskeluar="
                    + "id_cards='" + valsupplier + "'::"
                    + "akun_keluar_dari='" + valakun_pengengeluaran + "'::"
                    + "jumlah='" + ConvertFunc.ToDouble(pane.edtotal_nilai.getText()) + "'::"
                    + "isgiro='" + valgiro + "'::"
                    + "no_giro='" + pane.ednocek.getText() + "'::"
                    + "tanggal_giro_jatuh_tempo='" + new SimpleDateFormat("yyyy-MM-dd").format(pane.dtempo.getDate()) + "'"
                    + "&" + kirimtexpembelian();

            ch.insertdata("insertpembayaranhutang", data);
            if (Staticvar.getresult.equals("berhasil")) {
                try {
                    int dialog = JOptionPane.showConfirmDialog(null, "Data berhasil disimpan. \n "
                            + "Ingin Melanjutkan transaksi", "Konfirmasi", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    if (dialog == 0) {
                        Runnable run = new Runnable() {
                            @Override
                            public void run() {
                                int rowcount = pane.tabledata.getRowCount();
                                tabeldatalist.clear();
                                for (int i = 0; i < rowcount; i++) {
                                    dtmtabeldata.removeRow(0);
                                }
                                dtmtabeldata.setRowCount(0);
                                tabeldatalist.add(new Entitytabledata("", "", "", "", "", "", "", ""));
                                dtmtabeldata.addRow(rowtabledata);
                            }
                        };
                        SwingUtilities.invokeLater(run);
                        getkodetransaksi();
                        pane.tabledata.requestFocus();
                    } else {
                        Daftarhutang_inner_panel inpane = new Daftarhutang_inner_panel();
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
                    + "id_keltrans='42'::"
                    + "id_dept='" + valdept + "'::"
                    + "tanggal='" + new SimpleDateFormat("yyyy-MM-dd").format(pane.dtanggal.getDate()) + "'::"
                    + "noref='" + ConvertFunc.EncodeString(pane.ednoref.getText()) + "'::"
                    + "keterangan='" + ConvertFunc.EncodeString(pane.edketerangan.getText()) + "'"
                    + "&kaskeluar="
                    + "id_cards='" + valsupplier + "'::"
                    + "akun_keluar_dari='" + valakun_pengengeluaran + "'::"
                    + "jumlah='" + ConvertFunc.ToDouble(pane.edtotal_nilai.getText()) + "'::"
                    + "isgiro='" + valgiro + "'::"
                    + "no_giro='" + pane.ednocek.getText() + "'::"
                    + "tanggal_giro_jatuh_tempo='" + new SimpleDateFormat("yyyy-MM-dd").format(pane.dtempo.getDate()) + "'"
                    + "&" + kirimtexpembelian();
            ch.updatedata("updatepembayaranhutang", data, id);
            if (Staticvar.getresult.equals("berhasil")) {
                JPanel inpane = new JPanel();
                if (Staticvar.frame.equals("hutang")) {
                    inpane = new Daftarhutang_inner_panel();
                } else if (Staticvar.frame.equals("rincian_hutang")) {
                    inpane = new Daftarhutangrincian_inner_panel();
                }
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
                boolean status = true;
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
                            int jumlahrow = pane.tabledata.getRowCount();
                            for (int i = 0; i < jumlahrow; i++) {
                                if (!tabeldatalist.get(i).getNoref().equals("")) {
                                    String noref = tabeldatalist.get(i).getNoref();
                                    double jumlahbayar = ConvertFunc.ToDouble(tabeldatalist.get(i).getJumlah_bayar());
                                    double sisa = ConvertFunc.ToDouble(tabeldatalist.get(i).getSisahutang());
                                    double diskon = ConvertFunc.ToDouble(tabeldatalist.get(i).getDiskon());
                                    double sisasetelahdiskon = sisa - (sisa * (diskon / 100));
                                    if (jumlahbayar > sisasetelahdiskon) {
                                        JOptionPane.showMessageDialog(null, "Jumlah Bayar " + noref + " Tidak boleh lebih besar dari Total", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                                        status = false;
                                    }
                                }
                            }
                            if (status == true) {
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
                        int jumlahrow = pane.tabledata.getRowCount();
                        for (int i = 0; i < jumlahrow; i++) {
                            if (!tabeldatalist.get(i).getNoref().equals("")) {
                                String noref = tabeldatalist.get(i).getNoref();
                                double jumlahbayar = ConvertFunc.ToDouble(tabeldatalist.get(i).getJumlah_bayar());
                                double sisa = ConvertFunc.ToDouble(tabeldatalist.get(i).getSisahutang());
                                double diskon = ConvertFunc.ToDouble(tabeldatalist.get(i).getDiskon());
                                double sisasetelahdiskon = sisa - (sisa * (diskon / 100));
                                if (jumlahbayar > sisasetelahdiskon) {
                                    JOptionPane.showMessageDialog(null, "Jumlah Bayar " + noref + " Tidak boleh lebih besar dari Total", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                                    status = false;
                                }
                            }
                        }
                        if (status == true) {
                            rawsimpan();
                        }
                    }
                }
            }
        });
    }

    private String kirimtexpembelian() {
        StringBuilder sb = new StringBuilder();
        sb.append("kaskeluar_detail=");
        int listcount = 0;
        if (tabeldatalist.get(tabeldatalist.size() - 1).getId().equals("")) {
            listcount = tabeldatalist.size() - 1;
        } else {
            listcount = tabeldatalist.size();
        }
        for (int i = 0; i < listcount; i++) {
            sb.append("id_no_genjur=" + "'" + tabeldatalist.get(i).getId() + "'" + "::"
                    + "akun=" + "'" + tabeldatalist.get(i).getAkun() + "'" + "::"
                    + "jumlah=" + "'" + ConvertFunc.ToDouble(tabeldatalist.get(i).getJumlah_bayar()) + "'" + "::"
                    + "diskon=" + "'" + ConvertFunc.ToDouble(tabeldatalist.get(i).getDiskon()) + "'" + "::"
                    + "akun_diskon=" + "'" + valakun_diskon + "'");
            sb.append("--");

        }
        String hasil = sb.toString().substring(0, sb.toString().length() - 2);
        return hasil;
    }

    private void hidetable(int index) {
        TableColumn col = pane.tabledata.getColumnModel().getColumn(index);
        col.setMinWidth(0);
        col.setMaxWidth(0);
        col.setWidth(0);
        col.setPreferredWidth(0);
    }

    private void columnfunction(int row, int col, boolean addrow) {
        if (pane.tabledata.getValueAt(row, col).equals("")) {
            return;
        }
        if ((col == 4) || (col == 5)) {
            String value = nf.format(ConvertFunc.ToDouble(pane.tabledata.getValueAt(row, col)));
            pane.tabledata.setValueAt(value, row, col);
            if (addrow == true) {
                addautorow(row);
            }
        }
    }

    private void showtable(int index) {
        TableColumn col = pane.tabledata.getColumnModel().getColumn(index);
        col.setMinWidth(100);
        col.setMaxWidth(100);
        col.setWidth(100);
        col.setPreferredWidth(100);
    }

    private void carisupplier() {
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

    private void cariakunpembelian() {
        pane.bcariakun_pengeluaran.addActionListener((ActionEvent e) -> {
            rawgetidakun(pane.edakun_pengeluaran.getText(), valakun_pengengeluaran);
            if (!Staticvar.resid.equals("")) {
                valakun_pengengeluaran = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.edakun_pengeluaran.setText(val);
            }
        });

    }

    private void cariakundiskon() {
        pane.bcariakun_diskon.addActionListener((ActionEvent e) -> {
            rawgetidakundiskon(pane.edakun_diskon.getText(), valakun_diskon);
            if (!Staticvar.resid.equals("")) {
                valakun_diskon = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.edakun_diskon.setText(val);
            }

        });

    }

    private void rawgetidakun(String prevlabel, String previd) {
        Staticvar.sfilter = "";
        Staticvar.preid = previd;
        Staticvar.prelabel = prevlabel;
        JDialog jd = new JDialog(new Mainmenu());
        jd.add(new Popupcari("akun", "popupdaftarkasbank", "Daftar Akun"));
        jd.pack();
        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        jd.setLocationRelativeTo(null);
        jd.setVisible(true);
        jd.toFront();
    }

    private void rawgetidakundiskon(String prevlabel, String previd) {
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

    private void batal() {
        pane.bbatal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        String data = String.format("id_keltrans=%s&no_urut=%s", "42", String.valueOf(no_urut));
                        ch.insertdata("insertnomorgagal", data);
                        JPanel inpane = new JPanel();
                        if (Staticvar.frame.equals("hutang")) {
                            inpane = new Daftarhutang_inner_panel();
                        } else if (Staticvar.frame.equals("rincian_hutang")) {
                            inpane = new Daftarhutangrincian_inner_panel();
                        }
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
                    tabeldatalist.add(new Entitytabledata("", "", "", "", "", "", "", ""));
                    dtmtabeldata.addRow(rowtabledata);
                    pane.tabledata.requestFocus();
                    pane.tabledata.changeSelection(1, 0, false, false);
                } else {
                    addautorow(lastrow);
                }
            }

        });
    }

    private void kalkulasi() {
        int rowcount = pane.tabledata.getRowCount();
        double hasil = 0;
        for (int i = 0; i < rowcount; i++) {
            double jumlah = ConvertFunc.ToDouble(tabeldatalist.get(i).getJumlah_bayar());
            hasil = hasil + jumlah;
        }
        pane.edtotal_nilai.setText(nf.format(hasil));
        pane.lterbilang.setText(numtoword.TerbilangIndonesia(hasil));
    }

    public class Entitytabledata {

        String id, akun, noref, tanggal, totalhutang, sisahutang, diskon, jumlah_bayar;

        public Entitytabledata(String id, String akun, String noref, String tanggal, String totalhutang, String sisahutang, String diskon, String jumlah_bayar) {
            this.id = id;
            this.akun = akun;
            this.noref = noref;
            this.tanggal = tanggal;
            this.totalhutang = totalhutang;
            this.sisahutang = sisahutang;
            this.diskon = diskon;
            this.jumlah_bayar = jumlah_bayar;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAkun() {
            return akun;
        }

        public void setAkun(String akun) {
            this.akun = akun;
        }

        public String getNoref() {
            return noref;
        }

        public void setNoref(String noref) {
            this.noref = noref;
        }

        public String getTanggal() {
            return tanggal;
        }

        public void setTanggal(String tanggal) {
            this.tanggal = tanggal;
        }

        public String getTotalhutang() {
            return totalhutang;
        }

        public void setTotalhutang(String totalhutang) {
            this.totalhutang = totalhutang;
        }

        public String getSisahutang() {
            return sisahutang;
        }

        public void setSisahutang(String sisahutang) {
            this.sisahutang = sisahutang;
        }

        public String getDiskon() {
            return diskon;
        }

        public void setDiskon(String diskon) {
            this.diskon = diskon;
        }

        public String getJumlah_bayar() {
            return jumlah_bayar;
        }

        public void setJumlah_bayar(String jumlah_bayar) {
            this.jumlah_bayar = jumlah_bayar;
        }

    }

}
