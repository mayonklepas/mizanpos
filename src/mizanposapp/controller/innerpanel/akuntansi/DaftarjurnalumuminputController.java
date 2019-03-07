/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.akuntansi;

import mizanposapp.controller.innerpanel.akuntansi.*;
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
import java.util.HashMap;
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
import mizanposapp.helper.FuncHelper;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Globalsession;
import mizanposapp.helper.Staticvar;
import mizanposapp.helper.Tablestyle;
import mizanposapp.helper.numtoword;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Errorpanel;
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.akuntansi.Daftarjurnalumum_inner_panel;
import mizanposapp.view.innerpanel.akuntansi.Daftarjurnalumum_input_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class DaftarjurnalumuminputController {

    String id;
    String valproyek = "", valdept = "";
    int no_urut = 0, valgiro = 0;
    CrudHelper ch = new CrudHelper();
    Daftarjurnalumum_input_panel pane;
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
    ArrayList<String> listheadername = new ArrayList<>();
    String akun = "akun";
    String nama_akun = "nama_akun";
    String debit = "debit";
    String kredit = "kredit";

    public DaftarjurnalumuminputController(Daftarjurnalumum_input_panel pane) {
        this.pane = pane;
        skinning();
        loaddata();
        addtotable();
        simpandata();
        caridepartment();
        tambahbaris();
        hapusbaris();
        batal();
    }

    private void loadsession() {
        pane.eddept.setText(Globalsession.DEFAULT_DEPT_NAME);
        valdept = Globalsession.DEFAULT_DEPT_ID;
        pane.edproyek.setText("");
        valproyek = "";
    }

    private void skinning() {
        pane.dtanggal.setDateFormatString("dd MMMM yyyy");
        pane.dtanggal.setDate(new Date());
        pane.dtanggal.getDateEditor().setEnabled(false);
    }

    private void customtable() {
        pane.tabledata.setRowSelectionAllowed(false);
        pane.tabledata.setCellSelectionEnabled(true);
        pane.tabledata.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        dtmtabeldata = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 1 ? false : true;
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
                HashMap hm = new FuncHelper().getkodetransaksi("1", new Date(), valdept);
                pane.edno_transaksi.setText(String.valueOf(hm.get("no_transaksi")));
                no_urut = FuncHelper.ToInt(String.valueOf(hm.get("no_urut")));
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
            JSONArray jaheader = (JSONArray) joheader.get("inputjurnalumum");
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
            Logger.getLogger(DaftarjurnalumuminnerController.class.getName()).log(Level.SEVERE, null, ex);
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
                loadsession();
                pane.edno_transaksi.setText("");
                pane.edketerangan.setText("");
                tabeldatalist.add(new Entitytabledata("", "", "", ""));
                dtmtabeldata.addRow(rowtabledata);
                pane.tabledata.requestFocus();

            } else {
                JSONParser jpdata = new JSONParser();
                String param = String.format("id=%s", id);
                Object rawobjdata = jpdata.parse(ch.getdatadetails("datajurnalumum", param));
                JSONObject jsonobjdata = (JSONObject) rawobjdata;

                Object jogenjur = jsonobjdata.get("genjur");
                JSONArray jagenjur = (JSONArray) jogenjur;

                for (int i = 0; i < jagenjur.size(); i++) {
                    JSONObject joingenjur = (JSONObject) jagenjur.get(i);
                    pane.edno_transaksi.setText(String.valueOf(joingenjur.get("noref")));
                    pane.edketerangan.setText(String.valueOf(joingenjur.get("keterangan")));
                    pane.eddept.setText(String.valueOf(joingenjur.get("nama_dept")));
                    valdept = String.valueOf(joingenjur.get("id_dept"));
                    pane.edproyek.setText(String.valueOf(joingenjur.get("nama_job")));
                    valproyek = String.valueOf(joingenjur.get("id_job"));
                    try {
                        pane.dtanggal.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(joingenjur.get("tanggal"))));
                    } catch (java.text.ParseException ex) {
                        Logger.getLogger(DaftarjurnalumuminputController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

                Object jotabledata = jsonobjdata.get("jurnal");
                JSONArray jatabledata = (JSONArray) jotabledata;
                for (int i = 0; i < jatabledata.size(); i++) {
                    JSONObject jointabledata = (JSONObject) jatabledata.get(i);
                    String akunin = String.valueOf(jointabledata.get("akun"));
                    String nama_akunin = String.valueOf(jointabledata.get("nama_akun"));
                    String debitin = String.valueOf(jointabledata.get("debit"));
                    String kreditin = String.valueOf(jointabledata.get("kredit"));
                    tabeldatalist.add(new Entitytabledata(akunin, nama_akunin, debitin, kreditin));
                }

                for (int i = 0; i < tabeldatalist.size(); i++) {
                    rowtabledata[0] = tabeldatalist.get(i).getAkun();
                    rowtabledata[1] = tabeldatalist.get(i).getNama_akun();
                    rowtabledata[2] = tabeldatalist.get(i).getDebit();
                    rowtabledata[3] = tabeldatalist.get(i).getKredit();
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
                    if (col == gx(akun)) {
                        try {

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        } finally {
                            ischangevalue = false;
                        }
                    } else if (col == gx(debit)) {
                        try {
                            if (FuncHelper.ToDouble(tm.getValueAt(row, gx(debit))) != 0) {
                                tabeldatalist.get(row).setKredit("0");
                                tm.setValueAt(FuncHelper.ToDouble("0"), row, gx(kredit));
                            }
                            tabeldatalist.get(row).setDebit(String.valueOf(tm.getValueAt(row, gx(debit))));
                            kalkulasi();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        } finally {
                            ischangevalue = false;
                        }
                    } else if (col == gx(kredit)) {
                        try {
                            if (FuncHelper.ToDouble(tm.getValueAt(row, gx(kredit))) != 0) {
                                tabeldatalist.get(row).setKredit("0");
                                tm.setValueAt(FuncHelper.ToDouble("0"), row, gx(debit));
                            }
                            tabeldatalist.get(row).setKredit(String.valueOf(tm.getValueAt(row, gx(kredit))));
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
            }
        }
        );
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
                    if (col == gx(akun)) {
                        Staticvar.preid = tabeldatalist.get(row).getAkun();
                        String defnilai = "";
                        String cekval = String.valueOf(tb.getValueAt(row, col));
                        if (cekval.equals("null") || cekval.equals("")) {
                            defnilai = "";
                        } else {
                            defnilai = String.valueOf(tb.getValueAt(row, gx(akun)));
                        }
                        Staticvar.prelabel = defnilai;
                        Staticvar.sfilter = "";
                        JDialog jd = new JDialog(new Mainmenu());
                        jd.add(new Popupcari("akun", "popupdaftarakun", "Daftar Akun"));
                        jd.pack();
                        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        jd.setLocationRelativeTo(null);
                        jd.setVisible(true);
                        jd.toFront();
                        tabeldatalist.get(row).setAkun(Staticvar.resid);
                        tb.setValueAt(Staticvar.resid, row, gx(akun));
                        tabeldatalist.get(row).setNama_akun(Staticvar.reslabel);
                        tb.setValueAt(Staticvar.reslabel, row, gx(nama_akun));
                        if (Staticvar.resid.equals("") || Staticvar.resid.equals(Staticvar.preid)) {

                        } else {
                            tb.setValueAt(FuncHelper.ToDouble("0"), row, gx(debit));
                            tb.setValueAt(FuncHelper.ToDouble("0"), row, gx(kredit));
                            pane.tabledata.requestFocus();
                            pane.tabledata.changeSelection(row, 2, false, false);
                        }
                        double hasilkredit = 0;
                        double hasildebit = 0;
                        for (int i = 0; i < pane.tabledata.getRowCount() - 1; i++) {
                            double debitin = FuncHelper.ToDouble(pane.tabledata.getValueAt(i, gx(debit)));
                            double kreditin = FuncHelper.ToDouble(pane.tabledata.getValueAt(i, gx(kredit)));
                            hasildebit = hasildebit + debitin;
                            hasilkredit = hasilkredit + kreditin;
                        }
                        double selisih = hasildebit - hasilkredit;
                        pane.tabledata.setValueAt(FuncHelper.ToDouble(selisih), row, gx(debit));

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
            public void actionPerformed(ActionEvent e
            ) {
                pane.bhapus_baris.doClick();
            }
        }
        );
        pane.tabledata.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");
        pane.tabledata.getActionMap().put("enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                int row = pane.tabledata.getSelectedRow();
                int col = pane.tabledata.getSelectedColumn();
                oldvalue = String.valueOf(pane.tabledata.getValueAt(row, col));
                if (pane.tabledata.editCellAt(row, col)) {
                    Component editor = pane.tabledata.getEditorComponent();
                    editor.requestFocusInWindow();
                }
                if (col == gx(akun)) {
                    Staticvar.preid = tabeldatalist.get(row).getAkun();
                    String defnilai = "";
                    String cekval = String.valueOf(pane.tabledata.getValueAt(row, col));
                    if (cekval.equals("null") || cekval.equals("")) {
                        defnilai = "";
                    } else {
                        defnilai = String.valueOf(pane.tabledata.getValueAt(row, gx(akun)));
                    }
                    Staticvar.prelabel = defnilai;
                    Staticvar.sfilter = "";
                    Staticvar.prelabel = defnilai;
                    JDialog jd = new JDialog(new Mainmenu());
                    jd.add(new Popupcari("akun", "popupdaftarakun", "Daftar Akun"));
                    jd.pack();
                    jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                    jd.setLocationRelativeTo(null);
                    jd.setVisible(true);
                    jd.toFront();
                    tabeldatalist.get(row).setAkun(Staticvar.resid);
                    pane.tabledata.setValueAt(Staticvar.resid, row, gx(akun));
                    tabeldatalist.get(row).setNama_akun(Staticvar.reslabel);
                    pane.tabledata.setValueAt(Staticvar.reslabel, row, gx(nama_akun));
                    if (Staticvar.resid.equals("") || Staticvar.resid.equals(Staticvar.preid)) {
                    } else {
                        pane.tabledata.setValueAt(FuncHelper.ToDouble("0"), row, gx(debit));
                        pane.tabledata.setValueAt(FuncHelper.ToDouble("0"), row, gx(kredit));
                        pane.tabledata.requestFocus();
                        pane.tabledata.changeSelection(row, 2, false, false);
                    }
                    double hasilkredit = 0;
                    double hasildebit = 0;
                    for (int i = 0; i < pane.tabledata.getRowCount() - 1; i++) {
                        double debitin = FuncHelper.ToDouble(pane.tabledata.getValueAt(i, gx(debit)));
                        double kreditin = FuncHelper.ToDouble(pane.tabledata.getValueAt(i, gx(kredit)));
                        hasildebit = hasildebit + debitin;
                        hasilkredit = hasilkredit + kreditin;
                    }
                    if (hasilkredit > hasildebit) {
                        double selisih = hasilkredit - hasildebit;
                        pane.tabledata.setValueAt(FuncHelper.ToDouble(selisih), row, gx(debit));
                    }
                }
            }
        }
        );

        pane.tabledata.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
        pane.tabledata.getActionMap().put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                pane.tabledata.requestFocus();
                int row = pane.tabledata.getSelectedRow();
                int col = pane.tabledata.getSelectedColumn();
                if (col == gx(kredit)) {
                    if (row == pane.tabledata.getRowCount() - 1) {
                        if (pane.tabledata.getValueAt(row, gx(akun)).equals("null")
                             || pane.tabledata.getValueAt(row, gx(akun)).equals("")
                             || pane.tabledata.getValueAt(row, gx(kredit)).equals("")) {
                        } else {
                            addautorow(row);
                        }
                    } else {
                        pane.tabledata.requestFocus();
                        pane.tabledata.changeSelection(row + 1, 0, false, false);
                    }
                } else if (col == gx(debit)) {
                    if (row == pane.tabledata.getRowCount() - 1) {
                        if (String.valueOf(pane.tabledata.getValueAt(row, gx(akun))).equals("null")
                             || String.valueOf(pane.tabledata.getValueAt(row, gx(akun))).equals("")) {
                        } else {
                            double hasilkredit = 0;
                            double hasildebit = 0;
                            for (int i = 0; i < pane.tabledata.getRowCount() - 1; i++) {
                                double debitin = FuncHelper.ToDouble(pane.tabledata.getValueAt(i, gx(debit)));
                                double kreditin = FuncHelper.ToDouble(pane.tabledata.getValueAt(i, gx(kredit)));
                                hasildebit = hasildebit + debitin;
                                hasilkredit = hasilkredit + kreditin;
                            }
                            if (hasildebit > hasilkredit) {
                                double hasil = hasildebit - hasilkredit;
                                if (FuncHelper.ToDouble(pane.tabledata.getValueAt(row, col)) == 0) {
                                    pane.tabledata.setValueAt(String.valueOf(hasil), row, gx(kredit));
                                }
                            } else if (hasildebit < hasilkredit) {
                                double hasil = hasilkredit - hasildebit;
                                if (FuncHelper.ToDouble(pane.tabledata.getValueAt(row, col)) == 0) {
                                    pane.tabledata.setValueAt(String.valueOf(hasil), row, gx(debit));
                                }
                            }

                        }
                    }
                    pane.tabledata.changeSelection(row, col + 1, false, false);
                } else {
                    pane.tabledata.changeSelection(row, col + 1, false, false);
                }
            }
        }
        );

        pane.tabledata.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "tab");
        pane.tabledata.getActionMap().put("tab", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                pane.tabledata.requestFocus();
                int row = pane.tabledata.getSelectedRow();
                int col = pane.tabledata.getSelectedColumn();
                if (col == gx(kredit)) {
                    if (row == pane.tabledata.getRowCount() - 1) {
                        if (pane.tabledata.getValueAt(row, gx(akun)).equals("null")
                             || pane.tabledata.getValueAt(row, gx(akun)).equals("")
                             || pane.tabledata.getValueAt(row, gx(kredit)).equals("")) {
                        } else {
                            addautorow(row);
                        }
                    } else {
                        pane.tabledata.requestFocus();
                        pane.tabledata.changeSelection(row + 1, 0, false, false);
                    }

                } else if (col == gx(debit)) {
                    if (row == pane.tabledata.getRowCount() - 1) {
                        if (String.valueOf(pane.tabledata.getValueAt(row, gx(akun))).equals("null")
                             || String.valueOf(pane.tabledata.getValueAt(row, gx(akun))).equals("")) {
                        } else {
                            double hasilkredit = 0;
                            double hasildebit = 0;
                            for (int i = 0; i < pane.tabledata.getRowCount() - 1; i++) {
                                double debitin = FuncHelper.ToDouble(pane.tabledata.getValueAt(i, gx(debit)));
                                double kreditin = FuncHelper.ToDouble(pane.tabledata.getValueAt(i, gx(kredit)));
                                hasilkredit = hasilkredit + kreditin;
                                hasildebit = hasildebit + debitin;
                            }
                            double hasil = hasildebit - hasilkredit;
                            if (FuncHelper.ToDouble(pane.tabledata.getValueAt(row, col)) == 0) {
                                pane.tabledata.setValueAt(String.valueOf(hasil), row, gx(kredit));
                            }
                        }
                    }
                    pane.tabledata.changeSelection(row, col + 1, false, false);
                } else {
                    pane.tabledata.changeSelection(row, col + 1, false, false);

                }
            }
        }
        );

        pane.tabledata.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
        pane.tabledata.getActionMap().put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
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
                if (col == gx(debit)) {
                    if (row == pane.tabledata.getRowCount() - 1) {
                        if (String.valueOf(pane.tabledata.getValueAt(row, gx(akun))).equals("null")
                             || String.valueOf(pane.tabledata.getValueAt(row, gx(akun))).equals("")) {
                        } else {
                            double hasilkredit = 0;
                            double hasildebit = 0;
                            for (int i = 0; i < pane.tabledata.getRowCount() - 1; i++) {
                                double debitin = FuncHelper.ToDouble(pane.tabledata.getValueAt(i, gx(debit)));
                                double kreditin = FuncHelper.ToDouble(pane.tabledata.getValueAt(i, gx(kredit)));
                                hasilkredit = hasilkredit + kreditin;
                                hasildebit = hasildebit + debitin;
                            }
                            double hasil = hasildebit - hasilkredit;
                            if (FuncHelper.ToDouble(pane.tabledata.getValueAt(row, col)) == 0) {
                                pane.tabledata.setValueAt(String.valueOf(hasil), row, gx(kredit));
                            }
                        }
                        pane.tabledata.changeSelection(row + 1, col, false, false);
                    }
                }
                addautorow(row);
            }
        }
        );

    }

    private void addautorow(int row) {
        int lastrow = pane.tabledata.getRowCount() - 1;
        if (!pane.tabledata.getValueAt(row, gx(akun)).equals("")
             || !pane.tabledata.getValueAt(row, gx(kredit)).equals("")
             || !pane.tabledata.getValueAt(row, gx(debit)).equals("")) {
            if (row == lastrow) {
                tabeldatalist.add(new Entitytabledata("", "", "", ""));
                dtmtabeldata.addRow(rowtabledata);
                pane.tabledata.requestFocus();
                pane.tabledata.changeSelection(row + 1, 0, false, false);
            }
        }
    }

    private void rawsimpan() {
        if (id.equals("")) {
            String data = "genjur="
                 + "id_keltrans='1'::"
                 + "id_dept='" + valdept + "'::"
                 + "tanggal='" + new SimpleDateFormat("yyyy-MM-dd").format(pane.dtanggal.getDate()) + "'::"
                 + "noref='" + pane.edno_transaksi.getText() + "'::"
                 + "keterangan='" + pane.edketerangan.getText() + "'"
                 + "&" + kirimtextdata();

            ch.insertdata("insertjurnalumum", data);
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
                                    System.out.println(i);
                                    dtmtabeldata.removeRow(0);
                                }
                                dtmtabeldata.setRowCount(0);
                                tabeldatalist.clear();
                                tabeldatalist.add(new Entitytabledata("", "", "", ""));
                                dtmtabeldata.addRow(rowtabledata);
                            }
                        };
                        SwingUtilities.invokeLater(run);
                        getkodetransaksi();
                        pane.tabledata.requestFocus();
                    } else {
                        JPanel inpane = new JPanel();
                        inpane = new Daftarjurnalumum_inner_panel();
                        Staticvar.ap.container.removeAll();
                        Staticvar.ap.container.setLayout(new BorderLayout());
                        Staticvar.ap.container.add(inpane, BorderLayout.CENTER);
                        Staticvar.ap.container.revalidate();
                        Staticvar.ap.container.repaint();
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
                 + "id_keltrans='1'::"
                 + "id_dept='" + valdept + "'::"
                 + "tanggal='" + new SimpleDateFormat("yyyy-MM-dd").format(pane.dtanggal.getDate()) + "'::"
                 + "noref='" + pane.edno_transaksi.getText() + "'::"
                 + "keterangan='" + pane.edketerangan.getText() + "'"
                 + "&" + kirimtextdata();
            ch.updatedata("updatejurnalumum", data, id);
            if (Staticvar.getresult.equals("berhasil")) {
                JPanel inpane = new JPanel();
                inpane = new Daftarjurnalumum_inner_panel();
                Staticvar.ap.container.removeAll();
                Staticvar.ap.container.setLayout(new BorderLayout());
                Staticvar.ap.container.add(inpane, BorderLayout.CENTER);
                Staticvar.ap.container.revalidate();
                Staticvar.ap.container.repaint();
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
                if (pane.edno_transaksi.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, " Field Kode tidak boleh kosong", "Informasi", JOptionPane.INFORMATION_MESSAGE);

                } else if (tabeldatalist.isEmpty() || pane.tabledata.getRowCount() == 1
                     && (String.valueOf(pane.tabledata.getValueAt(0, 0)).equals("null")
                     || String.valueOf(pane.tabledata.getValueAt(0, 0)).equals(""))) {
                    JOptionPane.showMessageDialog(null, "Table Tidak Boleh Kosong", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else if (FuncHelper.ToDouble(pane.lselisih.getText()) != 0) {
                    JOptionPane.showMessageDialog(null, "Tidak Boleh ada selisih", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    int tahunbulan = Integer.parseInt(new SimpleDateFormat("yyyyMM").format(pane.dtanggal.getDate()));
                    int periodetahunnulan = Integer.parseInt(Globalsession.PERIODE_TAHUN + Globalsession.PERIODE_BULAN);
                    if (tahunbulan > periodetahunnulan) {
                        int dialog = JOptionPane.showConfirmDialog(null, "Tanggal transaksi setelah periode akuntansi.\n"
                             + "Apakah anda ingin melanjutkan transaksi ?", "Konfirmasi", JOptionPane.YES_NO_OPTION, 1);
                        if (dialog == 0) {
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
                        if (status == true) {
                            rawsimpan();
                        }
                    }
                }
            }
        });
    }

    private String kirimtextdata() {
        StringBuilder sb = new StringBuilder();
        sb.append("jurnal=");
        int listcount = 0;
        if (tabeldatalist.get(tabeldatalist.size() - 1).getAkun().equals("")) {
            listcount = tabeldatalist.size() - 1;
        } else {
            listcount = tabeldatalist.size();
        }
        for (int i = 0; i < listcount; i++) {
            sb.append("akun=" + "'" + tabeldatalist.get(i).getAkun() + "'" + "::"
                 + "debit=" + "'" + FuncHelper.ToDouble(tabeldatalist.get(i).getDebit()) + "'" + "::"
                 + "kredit=" + "'" + FuncHelper.ToDouble(tabeldatalist.get(i).getKredit()) + "'");
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
        if ((col == gx(kredit))) {
            String value = nf.format(FuncHelper.ToDouble(pane.tabledata.getValueAt(row, col)));
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

    /* private void cariterimadari() {
        pane.bcariproyek.addActionListener((ActionEvent e) -> {
            Staticvar.sfilter = "";
            Staticvar.preid = valterima_dari;
            Staticvar.prelabel = pane.edterima_dari.getText();
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("nama", "popupdaftarnama?tipe=-1", "Daftar Terima Dari"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valterima_dari = Staticvar.resid;
            pane.edterima_dari.setText(Staticvar.reslabel);
        });

    }*/
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

    private void batal() {
        pane.bbatal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new FuncHelper().insertnogagal("1", new Date(), valdept, String.valueOf(no_urut));
                        JPanel inpane = new JPanel();
                        inpane = new Daftarjurnalumum_inner_panel();
                        Staticvar.ap.container.removeAll();
                        Staticvar.ap.container.setLayout(new BorderLayout());
                        Staticvar.ap.container.add(inpane, BorderLayout.CENTER);
                        Staticvar.ap.container.revalidate();
                        Staticvar.ap.container.repaint();
                        Staticvar.frame = "";
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
                int dialog = JOptionPane.showConfirmDialog(null,
                     "Yakin akan menghapus " + pane.tabledata.getValueAt(row, gx(akun)) + " - "
                     + pane.tabledata.getValueAt(row, gx(nama_akun)),
                     "Konfirmasi", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (dialog == 0) {
                    Runnable rn = new Runnable() {
                        @Override
                        public void run() {
                            tabeldatalist.remove(row);
                            dtmtabeldata.removeRow(row);
                            if (row >= 0) {
                                pane.tabledata.changeSelection(0, 0, false, false);
                            }
                            kalkulasi();
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
                    tabeldatalist.add(new Entitytabledata("", "", "", ""));
                    dtmtabeldata.addRow(rowtabledata);
                    pane.tabledata.requestFocus();
                    pane.tabledata.changeSelection(1, gx(akun), false, false);
                } else {
                    addautorow(lastrow);
                }
            }

        });
    }

    private void kalkulasi() {
        int rowcount = pane.tabledata.getRowCount();
        double hasildebit = 0;
        double hasilkredit = 0;
        for (int i = 0; i < rowcount; i++) {
            double debitin = FuncHelper.ToDouble(tabeldatalist.get(i).getDebit());
            hasildebit = hasildebit + debitin;

            double kreditin = FuncHelper.ToDouble(tabeldatalist.get(i).getKredit());
            hasilkredit = hasilkredit + kreditin;
        }
        pane.ltotaldebit.setText(nf.format(hasildebit));
        pane.ltotalkredit.setText(nf.format(hasilkredit));
        double selisih = hasildebit - hasilkredit;
        pane.lselisih.setText(nf.format(selisih));
    }

    private int gx(String columname) {
        return listheadername.indexOf(columname);

    }

    public class Entitytabledata {

        String akun, nama_akun, debit, kredit;

        public Entitytabledata(String akun, String nama_akun, String debit, String kredit) {
            this.akun = akun;
            this.nama_akun = nama_akun;
            this.debit = debit;
            this.kredit = kredit;
        }

        public String getAkun() {
            return akun;
        }

        public void setAkun(String akun) {
            this.akun = akun;
        }

        public String getNama_akun() {
            return nama_akun;
        }

        public void setNama_akun(String nama_akun) {
            this.nama_akun = nama_akun;
        }

        public String getDebit() {
            return debit;
        }

        public void setDebit(String debit) {
            this.debit = debit;
        }

        public String getKredit() {
            return kredit;
        }

        public void setKredit(String kredit) {
            this.kredit = kredit;
        }

    }

}
