/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.penjualan;

import mizanposapp.controller.innerpanel.penjualan.*;
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
import mizanposapp.view.innerpanel.penjualan.Daftarpiutang_inner_panel;
import mizanposapp.view.innerpanel.penjualan.Daftarpiutangrincian_inner_panel;
import mizanposapp.view.innerpanel.penjualan.Daftarpembayaranpiutangperinvoice_input_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class DaftarpembayaranpiutangperinvoiceinputController {

    String id;
    String valpelanggan = "", valakun_pengengeluaran = "", valakun_diskon = "", valdept = "";
    int no_urut = 0, valgiro = 0;
    CrudHelper ch = new CrudHelper();
    Daftarpembayaranpiutangperinvoice_input_panel pane;
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
    String noref = "noref";
    String tanggal = "tanggal";
    String total = "total";
    String sisa = "sisa";
    String diskon = "diskon";
    String jumlah_bayar = "jumlah_bayar";

    public DaftarpembayaranpiutangperinvoiceinputController(Daftarpembayaranpiutangperinvoice_input_panel pane) {
        this.pane = pane;
        loadsession();
        skinning();
        loaddata();
        addtotable();
        simpandata();
        caripelanggan();
        cariakunpenjualan();
        caridepartment();
        cariakundiskon();
        tambahbaris();
        hapusbaris();
        cekandcombocontrol();
        batal();
    }

    private void loadsession() {
        pane.edakun_penerimaan.setText(Globalsession.AKUNPENJUALANTUNAI + "-" + Globalsession.NAMAAKUNPENJUALANTUNAI);
        pane.edakun_diskon.setText(Globalsession.AKUNDISKONPENJUALAN + "-" + Globalsession.NAMAAKUNDISKONPENJUALAN);
        valakun_pengengeluaran = Globalsession.AKUNPENJUALANTUNAI;
        valakun_diskon = Globalsession.AKUNDISKONPENJUALAN;
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
                    String param = String.format("id_keltrans=%s", "43");
                    Object ob = jpdata.parse(ch.getdatadetails("getnomortransaksi", param));
                    JSONArray ja = (JSONArray) ob;
                    for (int i = 0; i < ja.size(); i++) {
                        JSONObject jo = (JSONObject) ja.get(i);
                        pane.ednoref.setText(String.valueOf(jo.get("no_transaksi")));
                        no_urut = ConvertFunc.ToInt(String.valueOf(jo.get("no_urut")));
                    }

                } catch (ParseException ex) {
                    Logger.getLogger(DaftarorderpenjualaninputController.class.getName()).log(Level.SEVERE, null, ex);
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
            JSONArray jaheader = (JSONArray) joheader.get("inputpembayaranpiutang");
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
                pane.edpelanggan.setText(String.valueOf(Staticvar.map_var.get("nama_pelanggan")));
                valpelanggan = String.valueOf(Staticvar.map_var.get("id_pelanggan"));
                pane.eddept.setText(Globalsession.DEFAULT_DEPT_NAME);
                valdept = Globalsession.DEFAULT_DEPT_ID;
                pane.edketerangan.setText("");
                tabeldatalist.add(new Entitytabledata("", "", "", "", "", "", "", ""));
                dtmtabeldata.addRow(rowtabledata);
            } else {
                JSONParser jpdata = new JSONParser();
                String param = String.format("id=%s", id);
                Object rawobjdata = jpdata.parse(ch.getdatadetails("datapembayaranpiutang", param));
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
                        Logger.getLogger(DaftarorderpenjualaninputController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

                Object jopenjualan = jsonobjdata.get("kasmasuk");
                JSONArray japenjualan = (JSONArray) jopenjualan;

                for (int i = 0; i < japenjualan.size(); i++) {
                    JSONObject joinpenjualan = (JSONObject) japenjualan.get(i);
                    valpelanggan = String.valueOf(joinpenjualan.get("id_cards"));
                    pane.edpelanggan.setText(String.valueOf(joinpenjualan.get("nama_cards")));

                    pane.edtotal_nilai.setText(String.valueOf(joinpenjualan.get("jumlah")));

                    valgiro = ConvertFunc.ToInt(joinpenjualan.get("isgiro"));
                    if (valgiro == 0) {
                        pane.ckgiro.setSelected(false);
                    } else {
                        pane.ckgiro.setSelected(true);
                    }

                    if (pane.ckgiro.isSelected()) {
                        pane.ednocek.setVisible(true);
                        pane.lnocek.setVisible(true);
                        pane.ltitik2nocek.setVisible(true);
                        pane.ltempo.setVisible(true);
                        pane.ltitik2tempo.setVisible(true);
                        pane.dtempo.setVisible(true);
                    } else {
                        pane.ednocek.setVisible(false);
                        pane.lnocek.setVisible(false);
                        pane.ltitik2nocek.setVisible(false);
                        pane.ltempo.setVisible(false);
                        pane.ltitik2tempo.setVisible(false);
                        pane.dtempo.setVisible(false);
                    }

                    pane.ednocek.setText(String.valueOf(joinpenjualan.get("no_giro")));

                    try {
                        pane.dtempo.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(String.valueOf(joinpenjualan.get("tanggal_giro_jatuh_tempo")))));
                    } catch (java.text.ParseException ex) {
                        Logger.getLogger(DaftarorderpenjualaninputController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    pane.edakun_penerimaan.setText(String.valueOf(joinpenjualan.get("akun_masuk_dari")) + "-"
                            + String.valueOf(joinpenjualan.get("nama_akun_masuk_dari")));

                    valakun_pengengeluaran = String.valueOf(joinpenjualan.get("akun_masuk_dari"));
                }

                Object jotabledata = jsonobjdata.get("kasmasuk_detail");
                JSONArray jatabledata = (JSONArray) jotabledata;
                for (int i = 0; i < jatabledata.size(); i++) {
                    JSONObject jointabledata = (JSONObject) jatabledata.get(i);
                    String id = String.valueOf(jointabledata.get("id"));
                    String akun = String.valueOf(jointabledata.get("akun"));
                    String noref = String.valueOf(jointabledata.get("noref"));
                    String tanggal = String.valueOf(jointabledata.get("tanggal"));
                    String totalpiutang = String.valueOf(jointabledata.get("total"));
                    double sebenarnyasisa = ConvertFunc.ToDouble(jointabledata.get("sisa"))
                            + ConvertFunc.ToDouble(jointabledata.get("diskon_nominal"))
                            + ConvertFunc.ToDouble(jointabledata.get("jumlah"));
                    String sisapiutang = String.valueOf(sebenarnyasisa);
                    String diskon = String.valueOf(jointabledata.get("diskon_nominal"));
                    String jumlah_bayar = String.valueOf(jointabledata.get("jumlah"));
                    tabeldatalist.add(new Entitytabledata(id, akun, noref, tanggal, totalpiutang, sisapiutang, diskon, jumlah_bayar));
                }

                for (int i = 0; i < tabeldatalist.size(); i++) {
                    rowtabledata[0] = tabeldatalist.get(i).getNoref();
                    rowtabledata[1] = tabeldatalist.get(i).getTanggal();
                    rowtabledata[2] = tabeldatalist.get(i).getTotalpiutang();
                    rowtabledata[3] = tabeldatalist.get(i).getSisapiutang();
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
                    if (col == gx(diskon)) {
                        try {
                            ischangevalue = true;
                            String noref = tabeldatalist.get(row).getNoref();
                            tabeldatalist.get(row).setDiskon(String.valueOf(tm.getValueAt(row, gx(diskon))));
                            double indiskon = ConvertFunc.ToDouble(tabeldatalist.get(row).getDiskon());
                            double insisa = ConvertFunc.ToDouble(tabeldatalist.get(row).getSisapiutang());
                            if (indiskon > insisa) {
                                JOptionPane.showMessageDialog(null,
                                        "Jumlah Diskon " + noref + " Tidak boleh Nol atau lebih besar dari Sisa Piutang", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                                pane.tabledata.requestFocus();
                                pane.tabledata.changeSelection(row, gx(diskon), false, false);
                                tabeldatalist.get(row).setDiskon(String.valueOf("0"));
                                tm.setValueAt("0", row, gx(diskon));
                            } else {
                                double total_bayar = insisa - indiskon;
                                tabeldatalist.get(row).setJumlah_bayar(String.valueOf(total_bayar));
                                tm.setValueAt(total_bayar, row, gx(jumlah_bayar));
                                kalkulasi();
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        } finally {
                            ischangevalue = false;
                        }
                    } else if (col == gx(jumlah_bayar)) {
                        try {
                            ischangevalue = true;
                            String noref = tabeldatalist.get(row).getNoref();
                            tabeldatalist.get(row).setJumlah_bayar(String.valueOf(pane.tabledata.getValueAt(row, gx(jumlah_bayar))));
                            tabeldatalist.get(row).setSisapiutang(String.valueOf(pane.tabledata.getValueAt(row, gx(sisa))));
                            tabeldatalist.get(row).setDiskon(String.valueOf(pane.tabledata.getValueAt(row, gx(diskon))));
                            double injumlah_bayar = ConvertFunc.ToDouble(tabeldatalist.get(row).getJumlah_bayar());
                            double insisa = ConvertFunc.ToDouble(tabeldatalist.get(row).getSisapiutang());
                            double indiskon = ConvertFunc.ToDouble(tabeldatalist.get(row).getDiskon());
                            double totabayartambahdiskon = injumlah_bayar + indiskon;

                            if (totabayartambahdiskon > insisa || injumlah_bayar <= 0) {
                                JOptionPane.showMessageDialog(null,
                                        "Jumlah Bayar " + noref + " Tidak boleh lebih besar dari Sisa Piutang + Diskon", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                                pane.tabledata.requestFocus();
                                pane.tabledata.changeSelection(row, gx(jumlah_bayar), false, false);
                                tabeldatalist.get(row).setJumlah_bayar(pane.edtotal_nilai.getText());
                                tm.setValueAt(ConvertFunc.ToDouble(pane.edtotal_nilai.getText()), row, gx(jumlah_bayar));
                            } else {
                                tabeldatalist.get(row).setJumlah_bayar(String.valueOf(tm.getValueAt(row, gx(jumlah_bayar))));
                                kalkulasi();
                            }

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
                    if (col == gx(noref)) {
                        Staticvar.preid = tabeldatalist.get(row).getId();
                        String defnilai = "";
                        String cekval = String.valueOf(tb.getValueAt(row, col));
                        if (cekval.equals("null") || cekval.equals("")) {
                            defnilai = "";
                        } else {
                            defnilai = String.valueOf(tb.getValueAt(row, gx(noref)));
                        }
                        Staticvar.prelabel = defnilai;
                        Staticvar.sfilter = "";
                        Staticvar.prelabel = defnilai;
                        JDialog jd = new JDialog(new Mainmenu());
                        jd.add(new Popupcari("returpenjualan", "popupdaftarpiutangperpelanggan?ispiutang=1&id=" + valpelanggan, "Daftar Hutang Usaha " + pane.edpelanggan.getText()));
                        jd.pack();
                        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        jd.setLocationRelativeTo(null);
                        jd.setVisible(true);
                        jd.toFront();
                        if (!Staticvar.reslabel.equals("")) {
                            try {
                                if (tb.getRowCount() <= 1) {
                                    if (!Staticvar.preid.equals(Staticvar.resid)) {
                                        JSONParser jpdata = new JSONParser();
                                        String param = String.format("id=%s", Staticvar.resid);
                                        Object objdataraw = jpdata.parse(ch.getdatadetails("datapiutangperpelanggan", param));
                                        JSONArray jadata = (JSONArray) objdataraw;
                                        for (int i = 0; i < jadata.size(); i++) {
                                            JSONObject joindata = (JSONObject) jadata.get(i);
                                            tabeldatalist.get(row).setAkun(String.valueOf(joindata.get("akun")));
                                            tabeldatalist.get(row).setId(String.valueOf(joindata.get("id")));
                                            tabeldatalist.get(row).setNoref(String.valueOf(joindata.get("noref")));
                                            tb.setValueAt(String.valueOf(joindata.get("noref")), row, gx(noref));
                                            tabeldatalist.get(row).setTanggal(String.valueOf(joindata.get("tanggal")));
                                            tb.setValueAt(String.valueOf(joindata.get("tanggal")), row, gx(tanggal));
                                            tabeldatalist.get(row).setTotalpiutang(String.valueOf(joindata.get("total")));
                                            tb.setValueAt(String.valueOf(joindata.get("total")), row, gx(total));
                                            tabeldatalist.get(row).setSisapiutang(String.valueOf(joindata.get("sisa")));
                                            tb.setValueAt(String.valueOf(joindata.get("sisa")), row, gx(sisa));
                                            tabeldatalist.get(row).setDiskon("0");
                                            tb.setValueAt("0", row, gx(diskon));
                                            tabeldatalist.get(row).setJumlah_bayar(String.valueOf(joindata.get("sisa")));
                                            tb.setValueAt(String.valueOf(joindata.get("sisa")), row, gx(jumlah_bayar));
                                        }
                                        kalkulasi();
                                        addautorow(row);
                                    } else {
                                        tb.requestFocus();
                                    }
                                } else {
                                    boolean status = true;
                                    for (int i = 0; i < tb.getRowCount() - 1; i++) {
                                        String kodesekarang = Staticvar.resid;
                                        String kodesebelumnya = tabeldatalist.get(i).getId();
                                        if (kodesekarang.equals(kodesebelumnya)) {
                                            JOptionPane.showMessageDialog(null, tb.getValueAt(i, 0) + " Sudah Ada");
                                            status = false;
                                        }
                                    }

                                    if (status == true) {
                                        if (!Staticvar.preid.equals(Staticvar.resid)) {
                                            JSONParser jpdata = new JSONParser();
                                            String param = String.format("id=%s", Staticvar.resid);
                                            Object objdataraw = jpdata.parse(ch.getdatadetails("datapiutangperpelanggan", param));
                                            JSONArray jadata = (JSONArray) objdataraw;
                                            for (int i = 0; i < jadata.size(); i++) {
                                                JSONObject joindata = (JSONObject) jadata.get(i);
                                                tabeldatalist.get(row).setAkun(String.valueOf(joindata.get("akun")));
                                                tabeldatalist.get(row).setId(String.valueOf(joindata.get("id")));
                                                tabeldatalist.get(row).setNoref(String.valueOf(joindata.get("noref")));
                                                tb.setValueAt(String.valueOf(joindata.get("noref")), row, gx(noref));
                                                tabeldatalist.get(row).setTanggal(String.valueOf(joindata.get("tanggal")));
                                                tb.setValueAt(String.valueOf(joindata.get("tanggal")), row, gx(tanggal));
                                                tabeldatalist.get(row).setTotalpiutang(String.valueOf(joindata.get("total")));
                                                tb.setValueAt(String.valueOf(joindata.get("total")), row, gx(total));
                                                tabeldatalist.get(row).setSisapiutang(String.valueOf(joindata.get("sisa")));
                                                tb.setValueAt(String.valueOf(joindata.get("sisa")), row, gx(sisa));
                                                tabeldatalist.get(row).setDiskon("0");
                                                tb.setValueAt("0", row, gx(sisa));
                                                tabeldatalist.get(row).setJumlah_bayar(String.valueOf(joindata.get("sisa")));
                                                tb.setValueAt(String.valueOf(joindata.get("sisa")), row, gx(jumlah_bayar));
                                            }
                                            kalkulasi();
                                            addautorow(row);
                                        } else {
                                            tb.requestFocus();
                                        }
                                    }

                                }
                            } catch (ParseException ex) {
                                Logger.getLogger(DaftarorderpenjualaninputController.class.getName()).log(Level.SEVERE, null, ex);
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
                if (col == gx(noref)) {
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
                    jd.add(new Popupcari("returpenjualan", "popupdaftarpiutangperpelanggan?ispiutang=1&id=" + valpelanggan, "Daftar Hutang Usaha " + pane.edpelanggan.getText()));
                    jd.pack();
                    jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                    jd.setLocationRelativeTo(null);
                    jd.setVisible(true);
                    jd.toFront();
                    if (!Staticvar.reslabel.equals("")) {
                        try {
                            if (pane.tabledata.getRowCount() <= 1) {
                                if (!Staticvar.preid.equals(Staticvar.resid)) {
                                    JSONParser jpdata = new JSONParser();
                                    String param = String.format("id=%s", Staticvar.resid);
                                    Object objdataraw = jpdata.parse(ch.getdatadetails("datapiutangperpelanggan", param));
                                    JSONArray jadata = (JSONArray) objdataraw;
                                    for (int i = 0; i < jadata.size(); i++) {
                                        JSONObject joindata = (JSONObject) jadata.get(i);
                                        tabeldatalist.get(row).setAkun(String.valueOf(joindata.get("akun")));
                                        tabeldatalist.get(row).setId(String.valueOf(joindata.get("id")));
                                        tabeldatalist.get(row).setNoref(String.valueOf(joindata.get("noref")));
                                        pane.tabledata.setValueAt(String.valueOf(joindata.get("noref")), row, 0);
                                        tabeldatalist.get(row).setTanggal(String.valueOf(joindata.get("tanggal")));
                                        pane.tabledata.setValueAt(String.valueOf(joindata.get("tanggal")), row, 1);
                                        tabeldatalist.get(row).setTotalpiutang(String.valueOf(joindata.get("total")));
                                        pane.tabledata.setValueAt(String.valueOf(joindata.get("total")), row, 2);
                                        tabeldatalist.get(row).setSisapiutang(String.valueOf(joindata.get("sisa")));
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
                            } else {
                                boolean status = true;
                                for (int i = 0; i < pane.tabledata.getRowCount() - 1; i++) {
                                    String kodesekarang = Staticvar.resid;
                                    String kodesebelumnya = tabeldatalist.get(i).getId();
                                    if (kodesekarang.equals(kodesebelumnya)) {
                                        JOptionPane.showMessageDialog(null, pane.tabledata.getValueAt(i, 0) + " Sudah Ada");
                                        status = false;
                                    }
                                }

                                if (status == true) {
                                    if (!Staticvar.preid.equals(Staticvar.resid)) {
                                        JSONParser jpdata = new JSONParser();
                                        String param = String.format("id=%s", Staticvar.resid);
                                        Object objdataraw = jpdata.parse(ch.getdatadetails("datapiutangperpelanggan", param));
                                        JSONArray jadata = (JSONArray) objdataraw;
                                        for (int i = 0; i < jadata.size(); i++) {
                                            JSONObject joindata = (JSONObject) jadata.get(i);
                                            tabeldatalist.get(row).setAkun(String.valueOf(joindata.get("akun")));
                                            tabeldatalist.get(row).setId(String.valueOf(joindata.get("id")));
                                            tabeldatalist.get(row).setNoref(String.valueOf(joindata.get("noref")));
                                            pane.tabledata.setValueAt(String.valueOf(joindata.get("noref")), row, 0);
                                            tabeldatalist.get(row).setTanggal(String.valueOf(joindata.get("tanggal")));
                                            pane.tabledata.setValueAt(String.valueOf(joindata.get("tanggal")), row, 1);
                                            tabeldatalist.get(row).setTotalpiutang(String.valueOf(joindata.get("total")));
                                            pane.tabledata.setValueAt(String.valueOf(joindata.get("total")), row, 2);
                                            tabeldatalist.get(row).setSisapiutang(String.valueOf(joindata.get("sisa")));
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
                                }

                            }
                        } catch (ParseException ex) {
                            Logger.getLogger(DaftarorderpenjualaninputController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });

        pane.tabledata.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
        pane.tabledata.getActionMap().put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pane.tabledata.requestFocus();
                int row = pane.tabledata.getSelectedRow();
                int col = pane.tabledata.getSelectedColumn();
                pane.tabledata.changeSelection(row, col + 1, false, false);
                if (col == 5) {
                    addautorow(row);
                }
            }
        });

    }

    private void addautorow(int row) {
        int lastrow = pane.tabledata.getRowCount() - 1;
        if (!pane.tabledata.getValueAt(row, 0).equals("")
                || !pane.tabledata.getValueAt(row, gx(tanggal)).equals("")
                || !pane.tabledata.getValueAt(row, gx(sisa)).equals("")
                || !pane.tabledata.getValueAt(row, gx(diskon)).equals("")) {
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
                    + "id_keltrans='43'::"
                    + "id_dept='" + valdept + "'::"
                    + "tanggal='" + new SimpleDateFormat("yyyy-MM-dd").format(pane.dtanggal.getDate()) + "'::"
                    + "noref='" + ConvertFunc.EncodeString(pane.ednoref.getText()) + "'::"
                    + "keterangan='" + ConvertFunc.EncodeString(pane.edketerangan.getText()) + "'"
                    + "&kasmasuk="
                    + "id_cards='" + valpelanggan + "'::"
                    + "akun_masuk_dari='" + valakun_pengengeluaran + "'::"
                    + "jumlah='" + ConvertFunc.ToDouble(pane.edtotal_nilai.getText()) + "'::"
                    + "isgiro='" + valgiro + "'::"
                    + "no_giro='" + pane.ednocek.getText() + "'::"
                    + "tanggal_giro_jatuh_tempo='" + new SimpleDateFormat("yyyy-MM-dd").format(pane.dtempo.getDate()) + "'"
                    + "&" + kirimtextdata();

            ch.insertdata("insertpembayaranpiutang", data);
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
                                tabeldatalist.add(new Entitytabledata("", "", "", "", "", "", "", ""));
                                dtmtabeldata.addRow(rowtabledata);
                            }
                        };
                        SwingUtilities.invokeLater(run);
                        getkodetransaksi();
                        pane.tabledata.requestFocus();
                    } else {
                        JPanel inpane = new JPanel();
                        if (Staticvar.frame.equals("piutang")) {
                            inpane = new Daftarpiutang_inner_panel();
                        } else if (Staticvar.frame.equals("rincian_piutang")) {
                            inpane = new Daftarpiutangrincian_inner_panel();
                        }
                        Staticvar.pp.container.removeAll();
                        Staticvar.pp.container.setLayout(new BorderLayout());
                        Staticvar.pp.container.add(inpane, BorderLayout.CENTER);
                        Staticvar.pp.container.revalidate();
                        Staticvar.pp.container.repaint();
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
                    + "id_keltrans='43'::"
                    + "id_dept='" + valdept + "'::"
                    + "tanggal='" + new SimpleDateFormat("yyyy-MM-dd").format(pane.dtanggal.getDate()) + "'::"
                    + "noref='" + ConvertFunc.EncodeString(pane.ednoref.getText()) + "'::"
                    + "keterangan='" + ConvertFunc.EncodeString(pane.edketerangan.getText()) + "'"
                    + "&kasmasuk="
                    + "id_cards='" + valpelanggan + "'::"
                    + "akun_masuk_dari='" + valakun_pengengeluaran + "'::"
                    + "jumlah='" + ConvertFunc.ToDouble(pane.edtotal_nilai.getText()) + "'::"
                    + "isgiro='" + valgiro + "'::"
                    + "no_giro='" + pane.ednocek.getText() + "'::"
                    + "tanggal_giro_jatuh_tempo='" + new SimpleDateFormat("yyyy-MM-dd").format(pane.dtempo.getDate()) + "'"
                    + "&" + kirimtextdata();
            ch.updatedata("updatepembayaranpiutang", data, id);
            if (Staticvar.getresult.equals("berhasil")) {
                JPanel inpane = new JPanel();
                if (Staticvar.frame.equals("piutang")) {
                    inpane = new Daftarpiutang_inner_panel();
                } else if (Staticvar.frame.equals("rincian_piutang")) {
                    inpane = new Daftarpiutangrincian_inner_panel();
                }
                Staticvar.pp.container.removeAll();
                Staticvar.pp.container.setLayout(new BorderLayout());
                Staticvar.pp.container.add(inpane, BorderLayout.CENTER);
                Staticvar.pp.container.revalidate();
                Staticvar.pp.container.repaint();
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
                if (pane.edpelanggan.getText().equals("")) {
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
                                    double sisa = ConvertFunc.ToDouble(tabeldatalist.get(i).getSisapiutang());
                                    double diskon = ConvertFunc.ToDouble(tabeldatalist.get(i).getDiskon());
                                    double totabayartambahdiskon = jumlahbayar + diskon;
                                    if (jumlahbayar <= 0) {
                                        JOptionPane.showMessageDialog(null, "Jumlah Bayar " + noref + " Tidak boleh Nol", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                                        status = false;
                                    } else if (totabayartambahdiskon > sisa) {
                                        JOptionPane.showMessageDialog(null, "Jumlah Bayar " + noref + " Tidak boleh lebih besar dari Piutang + Diskon", "Informasi", JOptionPane.INFORMATION_MESSAGE);
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
                                double sisa = ConvertFunc.ToDouble(tabeldatalist.get(i).getSisapiutang());
                                double diskon = ConvertFunc.ToDouble(tabeldatalist.get(i).getDiskon());
                                double totabayartambahdiskon = jumlahbayar + diskon;
                                if (jumlahbayar <= 0) {
                                    JOptionPane.showMessageDialog(null, "Jumlah Bayar " + noref + " Tidak boleh Nol", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                                    status = false;
                                } else if (totabayartambahdiskon > sisa) {
                                    JOptionPane.showMessageDialog(null, "Jumlah Bayar " + noref + " Tidak boleh lebih besar dari Piutang + Diskon", "Informasi", JOptionPane.INFORMATION_MESSAGE);
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

    private String kirimtextdata() {
        StringBuilder sb = new StringBuilder();
        sb.append("kasmasuk_detail=");
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
                    + "diskon_nominal=" + "'" + ConvertFunc.ToDouble(tabeldatalist.get(i).getDiskon()) + "'" + "::"
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
            pane.edpelanggan.setText(Staticvar.reslabel);
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
        jd.add(new Popupcari("akun", "popupdaftarkasbank", "Daftar Akun"));
        jd.pack();
        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        jd.setLocationRelativeTo(null);
        jd.setVisible(true);
        jd.toFront();
    }

    private void cariakunpenjualan() {
        pane.bcariakun_penerimaan.addActionListener((ActionEvent e) -> {
            rawgetidakun(pane.edakun_penerimaan.getText(), valakun_pengengeluaran);
            if (!Staticvar.resid.equals(valakun_pengengeluaran)) {
                valakun_pengengeluaran = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.edakun_penerimaan.setText(val);
            }
        });

    }

    private void cariakundiskon() {
        pane.bcariakun_diskon.addActionListener((ActionEvent e) -> {
            rawgetidakundiskon(pane.edakun_diskon.getText(), valakun_diskon);
            if (!Staticvar.resid.equals(valakun_diskon)) {
                valakun_diskon = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.edakun_diskon.setText(val);
            }

        });

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
                        String data = String.format("id_keltrans=%s&no_urut=%s", "43", String.valueOf(no_urut));
                        ch.insertdata("insertnomorgagal", data);
                        JPanel inpane = new JPanel();
                        if (Staticvar.frame.equals("piutang")) {
                            inpane = new Daftarpiutang_inner_panel();
                        } else if (Staticvar.frame.equals("rincian_piutang")) {
                            inpane = new Daftarpiutangrincian_inner_panel();
                        }
                        Staticvar.pp.container.removeAll();
                        Staticvar.pp.container.setLayout(new BorderLayout());
                        Staticvar.pp.container.add(inpane, BorderLayout.CENTER);
                        Staticvar.pp.container.revalidate();
                        Staticvar.pp.container.repaint();
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
                        "Yakin akan menghapus " + pane.tabledata.getValueAt(row, gx(noref)) + " - "
                        + pane.tabledata.getValueAt(row, gx(total)),
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
                    tabeldatalist.add(new Entitytabledata("", "", "", "", "", "", "", ""));
                    dtmtabeldata.addRow(rowtabledata);
                    pane.tabledata.requestFocus();
                    pane.tabledata.changeSelection(1, gx(noref), false, false);
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

    private int gx(String columname) {
        return listheadername.indexOf(columname);
    }

    public class Entitytabledata {

        String id, akun, noref, tanggal, totalpiutang, sisapiutang, diskon, jumlah_bayar;

        public Entitytabledata(String id, String akun, String noref, String tanggal, String totalpiutang, String sisapiutang, String diskon, String jumlah_bayar) {
            this.id = id;
            this.akun = akun;
            this.noref = noref;
            this.tanggal = tanggal;
            this.totalpiutang = totalpiutang;
            this.sisapiutang = sisapiutang;
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

        public String getTotalpiutang() {
            return totalpiutang;
        }

        public void setTotalpiutang(String totalpiutang) {
            this.totalpiutang = totalpiutang;
        }

        public String getSisapiutang() {
            return sisapiutang;
        }

        public void setSisapiutang(String sisapiutang) {
            this.sisapiutang = sisapiutang;
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
