/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.persediaan;

import mizanposapp.controller.innerpanel.persediaan.*;
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
import javax.swing.ButtonGroup;
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
import mizanposapp.controller.innerpanel.persediaan.DaftartransferpersediaaninnerController;
import mizanposapp.helper.ConvertFunc;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Globalsession;
import mizanposapp.helper.Staticvar;
import mizanposapp.helper.Tablestyle;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Errorpanel;
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.persediaan.Daftartransferpersediaan_inner_panel;
import mizanposapp.view.innerpanel.persediaan.Daftartransferpersediaan_input_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class DaftartransferpersediaaninputController {

    String id;
    CrudHelper ch = new CrudHelper();
    Daftartransferpersediaan_input_panel pane;
    String valdept = "", valakun_transferpersediaan = "", valgudangdari = "", valgudangke = "";
    DefaultTableModel dtmtabeldata = new DefaultTableModel();
    Object[] rowtabledata = new Object[12];
    ArrayList<Entitytabledata> tabeldatalist = new ArrayList<>();
    int col = 0;
    NumberFormat nf = NumberFormat.getInstance();
    double total_persediaan_all = 0;
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
    ButtonGroup bg = new ButtonGroup();

    String kode = "kode";
    String nama = "nama";
    String jumlah = "jumlah";
    String satuan = "satuan";

    public DaftartransferpersediaaninputController(Daftartransferpersediaan_input_panel pane) {
        this.pane = pane;
        skinning();
        loadsession();
        loaddata();
        simpandata();
        caridepartment();
        carigudangdari();
        carigudangke();
        addtotable();
        hapusbaris();
        tambahbaris();
        batal();

    }

    private void loadsession() {
        pane.eddept.setText(Globalsession.DEFAULT_DEPT_NAME);
        valdept = Globalsession.DEFAULT_DEPT_ID;
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
                    String param = String.format("id_keltrans=%s", "53");
                    Object ob = jpdata.parse(ch.getdatadetails("getnomortransaksi", param));
                    JSONArray ja = (JSONArray) ob;
                    for (int i = 0; i < ja.size(); i++) {
                        JSONObject jo = (JSONObject) ja.get(i);
                        pane.edno_transaksi.setText(String.valueOf(jo.get("no_transaksi")));
                        no_urut = ConvertFunc.ToInt(String.valueOf(jo.get("no_urut")));
                    }

                } catch (ParseException ex) {
                    Logger.getLogger(DaftartransferpersediaaninputController.class.getName()).log(Level.SEVERE, null, ex);
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
                return column == gx(nama) || column == gx(satuan) ? false : true;
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

    private void loadheader() {
        try {
            pane.tabledata.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            pane.tabledata.setModel(dtmtabeldata);
            String dataheader = ch.getheaders();
            JSONParser jpheader = new JSONParser();
            Object objheader = jpheader.parse(dataheader);
            JSONObject joheader = (JSONObject) objheader;
            JSONArray jaheader = (JSONArray) joheader.get("inputtransferpersediaan");
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
            Logger.getLogger(DaftartransferpersediaaninnerController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void setheader() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        TableColumnModel tcm = pane.tabledata.getColumnModel();

        double lebar = d.getWidth() - Staticvar.lebarPanelMenu;;
        double lebarAll = 0;

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
                pane.dtanggal.setDate(new Date());
                pane.edno_transaksi.setText("");
                pane.eddept.setText(Globalsession.DEFAULT_DEPT_NAME);
                valdept = Globalsession.DEFAULT_DEPT_ID;
                pane.edketerangan.setText("");
                tabeldatalist.add(new Entitytabledata("", "", "", "0", "", "", "", "0"));
                dtmtabeldata.addRow(rowtabledata);

            } else {

                JSONParser jpdata = new JSONParser();
                String param = String.format("id=%s", id);
                Object rawobjdata = jpdata.parse(ch.getdatadetails("datatransferpersediaan", param));
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
                        Logger.getLogger(DaftartransferpersediaaninputController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

                Object objtabeldata = jsonobjdata.get("jurnal_persediaan");
                JSONArray jatabledata = (JSONArray) objtabeldata;
                for (int i = 0; i < jatabledata.size(); i++) {
                    JSONObject jointabeldata = (JSONObject) jatabledata.get(i);
                    String id_inv = String.valueOf(jointabeldata.get("id_inv"));
                    String kode_inv = String.valueOf(jointabeldata.get("kode_inv"));
                    String nama_inv = String.valueOf(jointabeldata.get("nama_inv"));
                    String jumlah = String.valueOf(jointabeldata.get("jumlah"));
                    String id_satuan = String.valueOf(jointabeldata.get("id_satuan"));
                    String nama_satuan = String.valueOf(jointabeldata.get("nama_satuan"));
                    String id_satuan_pengali = String.valueOf(jointabeldata.get("id_satuan_pengali"));
                    String qty_satuan_pengali = String.valueOf(jointabeldata.get("qty_satuan_pengali"));
                    valgudangdari = String.valueOf(jointabeldata.get("id_gudang"));
                    valgudangke = String.valueOf(jointabeldata.get("id_gudang2"));
                    pane.edgudang_dari.setText(String.valueOf(jointabeldata.get("nama_gudang")));
                    pane.edgudang_ke.setText(String.valueOf(jointabeldata.get("nama_gudang2")));
                    tabeldatalist.add(new Entitytabledata(id_inv, kode_inv, nama_inv, jumlah, id_satuan, nama_satuan, id_satuan_pengali, qty_satuan_pengali));

                }
                for (int i = 0; i < tabeldatalist.size(); i++) {
                    rowtabledata[0] = tabeldatalist.get(i).getKode();
                    rowtabledata[1] = tabeldatalist.get(i).getNama();
                    rowtabledata[2] = tabeldatalist.get(i).getJumlah();
                    rowtabledata[3] = tabeldatalist.get(i).getSatuan();
                    dtmtabeldata.addRow(rowtabledata);
                }

                for (int i = 0; i < rowtabledata.length; i++) {
                    rowtabledata[i] = "";
                }

            }

        } catch (ParseException ex) {
            Logger.getLogger(DaftartransferpersediaaninputController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void rawsimpan() {
        if (id.equals("")) {
            String data = "genjur="
                 + "id_keltrans='53'::"
                 + "id_dept='" + valdept + "'::"
                 + "tanggal='" + new SimpleDateFormat("yyyy-MM-dd").format(pane.dtanggal.getDate()) + "'::"
                 + "noref='" + ConvertFunc.EncodeString(pane.edno_transaksi.getText()) + "'::"
                 + "keterangan='" + ConvertFunc.EncodeString(pane.edketerangan.getText()) + "'"
                 + "&" + kirimtexpersediaan();

            ch.insertdata("inserttransferpersediaan", data);
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
                                tabeldatalist.add(new Entitytabledata("", "", "", "0", "", "", "", "0"));
                                dtmtabeldata.addRow(rowtabledata);
                            }
                        };
                        SwingUtilities.invokeLater(run);
                        getkodetransaksi();;
                    } else {
                        Daftartransferpersediaan_inner_panel inpane = new Daftartransferpersediaan_inner_panel();
                        Staticvar.psp.container.removeAll();
                        Staticvar.psp.container.setLayout(new BorderLayout());
                        Staticvar.psp.container.add(inpane, BorderLayout.CENTER);
                        Staticvar.psp.container.revalidate();
                        Staticvar.psp.container.repaint();
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
                 + "id_keltrans='53'::"
                 + "id_dept='" + valdept + "'::"
                 + "tanggal='" + new SimpleDateFormat("yyyy-MM-dd").format(pane.dtanggal.getDate()) + "'::"
                 + "noref='" + ConvertFunc.EncodeString(pane.edno_transaksi.getText()) + "'::"
                 + "keterangan='" + ConvertFunc.EncodeString(pane.edketerangan.getText()) + "'"
                 + "&" + kirimtexpersediaan();
            ch.updatedata("updatetransferpersediaan", data, id);
            if (Staticvar.getresult.equals("berhasil")) {
                Daftartransferpersediaan_inner_panel inpane = new Daftartransferpersediaan_inner_panel();
                Staticvar.psp.container.removeAll();
                Staticvar.psp.container.setLayout(new BorderLayout());
                Staticvar.psp.container.add(inpane, BorderLayout.CENTER);
                Staticvar.psp.container.revalidate();
                Staticvar.psp.container.repaint();
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

                if (pane.edgudang_dari.getText().equals("") || pane.edgudang_ke.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Gudang Dari atau Gudang ke, tidak boleh kosong", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else if (tabeldatalist.size() == 0) {
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

    private String kirimtexpersediaan() {

        StringBuilder sb = new StringBuilder();
        sb.append("jurnal_persediaan=");
        int listcount = 0;
        if (tabeldatalist.get(tabeldatalist.size() - 1).getId_inv().equals("")) {
            listcount = tabeldatalist.size() - 1;
        } else {
            listcount = tabeldatalist.size();
        }
        for (int i = 0; i < listcount; i++) {
            sb.append("id_inv=" + "'" + tabeldatalist.get(i).getId_inv() + "'" + "::"
                 + "jumlah=" + "'" + ConvertFunc.ToDouble(tabeldatalist.get(i).getJumlah()) + "'" + "::"
                 + "id_satuan=" + "'" + tabeldatalist.get(i).getId_satuan() + "'" + "::"
                 + "id_gudang=" + "'" + valgudangdari + "'" + "::"
                 + "id_gudang2=" + "'" + valgudangke + "'" + "::"
                 + "id_satuan_pengali=" + "'" + tabeldatalist.get(i).getId_satuan_pengali() + "'" + "::"
                 + "qty_satuan_pengali=" + "'" + tabeldatalist.get(i).getQty_satuan_pengali() + "'");
            sb.append("--");

        }
        String finale = sb.toString().substring(0, sb.toString().length() - 2);
        return finale;
    }

    private void hapusbaris() {
        pane.bhapus_baris.addActionListener(new ActionListener() {
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
                            if (pane.tabledata.getRowCount() <= 0) {
                                tabeldatalist.add(new Entitytabledata("", "", "", "0", "", "", "", "0"));
                                dtmtabeldata.addRow(rowtabledata);
                                pane.tabledata.requestFocus();
                                pane.tabledata.changeSelection(0, 0, false, false);
                            } else {
                                if (row > 0) {
                                    pane.tabledata.changeSelection(row - 1, 0, false, false);
                                } else {
                                    pane.tabledata.changeSelection(0, 0, false, false);
                                }
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
                    tabeldatalist.add(new Entitytabledata("", "", "", "0", "", "", "", "0"));
                    dtmtabeldata.addRow(rowtabledata);
                    pane.tabledata.requestFocus();
                    pane.tabledata.changeSelection(0, 0, false, false);
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
                        if (id.equals("")) {
                            String data = String.format("id_keltrans=%s&no_urut=%s", "53", String.valueOf(no_urut));
                            ch.insertdata("insertnomorgagal", data);
                        }
                        Daftartransferpersediaan_inner_panel inpane = new Daftartransferpersediaan_inner_panel();
                        Staticvar.psp.container.removeAll();
                        Staticvar.psp.container.setLayout(new BorderLayout());
                        Staticvar.psp.container.add(inpane, BorderLayout.CENTER);
                        Staticvar.psp.container.revalidate();
                        Staticvar.psp.container.repaint();
                    }
                });

            }
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

    private void carigudangdari() {
        pane.bcari_gudang_dari.addActionListener((ActionEvent e) -> {
            Staticvar.sfilter = "";
            Staticvar.preid = valgudangdari;
            Staticvar.prelabel = pane.edgudang_dari.getText();
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("gudang", "popupdaftargudang", "Daftar Gudang"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valgudangdari = Staticvar.resid;
            pane.edgudang_dari.setText(Staticvar.reslabel);
        });

    }

    private void carigudangke() {
        pane.bcari_gudang_ke.addActionListener((ActionEvent e) -> {
            Staticvar.sfilter = "";
            Staticvar.preid = valgudangke;
            Staticvar.prelabel = pane.edgudang_ke.getText();
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("gudang", "popupdaftargudang", "Daftar Gudang"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valgudangke = Staticvar.resid;
            pane.edgudang_ke.setText(Staticvar.reslabel);
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
                        Staticvar.preid = tabeldatalist.get(row).getId_inv();
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
                            JSONParser jpdata = new JSONParser();
                            String param = String.format("kode=%s", String.valueOf(tm.getValueAt(row, col)));
                            Object objdataraw = jpdata.parse(ch.getdatadetails("dm/datapersediaanbykode", param));
                            JSONObject jodataraw = (JSONObject) objdataraw;
                            Object objdata = jodataraw.get("data");
                            JSONArray jadata = (JSONArray) objdata;
                            if (jadata.size() == 1) {
                                for (int i = 0; i < jadata.size(); i++) {
                                    JSONObject joindata = (JSONObject) jadata.get(i);
                                    tabeldatalist.get(row).setId_inv(String.valueOf(joindata.get("id")));
                                    tm.setValueAt(String.valueOf(joindata.get("kode")), row, gx(kode));
                                    tm.setValueAt(String.valueOf(joindata.get("nama")), row, gx(nama));
                                    tabeldatalist.get(row).setJumlah("0");
                                    tm.setValueAt("0", row, gx(jumlah));
                                    tabeldatalist.get(row).setId_satuan(String.valueOf(joindata.get("id_satuan")));
                                    tm.setValueAt(String.valueOf(joindata.get("nama_satuan")), row, gx(satuan));
                                    tabeldatalist.get(row).setId_satuan_pengali(String.valueOf(joindata.get("id_satuan")));
                                    tabeldatalist.get(row).setQty_satuan_pengali("1");
                                }
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
                                        JSONObject joindata = (JSONObject) jadata2.get(i);
                                        tabeldatalist.get(row).setId_inv(String.valueOf(joindata.get("id")));
                                        tm.setValueAt(String.valueOf(joindata.get("kode")), row, gx(kode));
                                        tm.setValueAt(String.valueOf(joindata.get("nama")), row, gx(nama));
                                        tabeldatalist.get(row).setJumlah("0");
                                        tm.setValueAt("0", row, gx(jumlah));
                                        tabeldatalist.get(row).setId_satuan(String.valueOf(joindata.get("id_satuan")));
                                        tm.setValueAt(String.valueOf(joindata.get("nama_satuan")), row, gx(satuan));
                                        tabeldatalist.get(row).setId_satuan_pengali(String.valueOf(joindata.get("id_satuan")));
                                        tabeldatalist.get(row).setQty_satuan_pengali("1");

                                    }
                                    pane.tabledata.requestFocus();
                                    pane.tabledata.changeSelection(row, gx(jumlah), false, false);
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
                            Logger.getLogger(DaftartransferpersediaaninputController.class.getName()).log(Level.SEVERE, null, ex);
                        } finally {
                            ischangevalue = false;
                        }

                    } else if (col == gx(jumlah)) {
                        try {
                            ischangevalue = true;
                            tabeldatalist.get(row).setJumlah(String.valueOf(tm.getValueAt(row, gx(jumlah))));
                            columnfunction(row, gx(jumlah), false);
                        } catch (Exception ex) {
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
                        Staticvar.preid = tabeldatalist.get(row).getId_inv();
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

                                        JSONObject joindata = (JSONObject) jadata2.get(i);

                                        tabeldatalist.get(row).setId_inv(String.valueOf(joindata.get("id")));
                                        pane.tabledata.setValueAt(String.valueOf(joindata.get("kode")), row, gx(kode));
                                        pane.tabledata.setValueAt(String.valueOf(joindata.get("nama")), row, gx(nama));
                                        tabeldatalist.get(row).setJumlah("0");
                                        pane.tabledata.setValueAt("0", row, gx(jumlah));
                                        tabeldatalist.get(row).setId_satuan(String.valueOf(joindata.get("id_satuan")));
                                        pane.tabledata.setValueAt(String.valueOf(joindata.get("nama_satuan")), row, gx(satuan));
                                        tabeldatalist.get(row).setId_satuan_pengali(String.valueOf(joindata.get("id_satuan")));
                                        tabeldatalist.get(row).setQty_satuan_pengali("1");

                                    }
                                    pane.tabledata.requestFocus();
                                    pane.tabledata.changeSelection(row, gx(jumlah), false, false);
                                } else {
                                    pane.tabledata.requestFocus();
                                }
                            } catch (ParseException ex) {
                                Logger.getLogger(DaftartransferpersediaaninputController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    } else if (col == gx(satuan)) {
                        Staticvar.sfilter = "";
                        Staticvar.preid = tabeldatalist.get(row).getId_satuan();
                        Staticvar.prelabel = String.valueOf(tb.getValueAt(row, gx(satuan)));
                        Staticvar.prevalueextended = tabeldatalist.get(row).getQty_satuan_pengali();
                        JDialog jd = new JDialog(new Mainmenu());
                        jd.add(new Popupcari("satuanperbarang",
                             String.format("popupdaftarsatuanperbarang?id_inv=%s",
                                  tabeldatalist.get(row).getId_inv()),
                             "Daftar Satuan Perbarang"));
                        jd.pack();
                        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        jd.setLocationRelativeTo(null);
                        jd.setVisible(true);
                        jd.toFront();
                        if (!tabeldatalist.get(row).getId_satuan().equals(Staticvar.resid)) {
                            tabeldatalist.get(row).setId_satuan(Staticvar.resid);
                            tb.setValueAt(Staticvar.reslabel, row, gx(satuan));
                            tabeldatalist.get(row).setQty_satuan_pengali(Staticvar.resvalueextended);
                            dtmtabeldata.fireTableCellUpdated(row, gx(satuan));
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

            @Override
            public void mouseReleased(MouseEvent e) {
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
                if (col == gx(satuan)) {
                    Staticvar.sfilter = "";
                    Staticvar.preid = tabeldatalist.get(row).getId_satuan();
                    Staticvar.prelabel = String.valueOf(pane.tabledata.getValueAt(row, 3));
                    Staticvar.prevalueextended = tabeldatalist.get(row).getQty_satuan_pengali();
                    JDialog jd = new JDialog(new Mainmenu());
                    jd.add(new Popupcari("satuanperbarang",
                         String.format("popupdaftarsatuanperbarang?id_inv=%s",
                              tabeldatalist.get(row).getId_inv()),
                         "Daftar Satuan Perbarang"));
                    jd.pack();
                    jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                    jd.setLocationRelativeTo(null);
                    jd.setVisible(true);
                    jd.toFront();
                    if (!tabeldatalist.get(row).getId_satuan().equals(Staticvar.resid)) {
                        tabeldatalist.get(row).setId_satuan(Staticvar.resid);
                        pane.tabledata.setValueAt(Staticvar.reslabel, row, gx(satuan));
                        tabeldatalist.get(row).setQty_satuan_pengali(Staticvar.resvalueextended);
                        dtmtabeldata.fireTableCellUpdated(row, gx(satuan));
                    }
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
                if (col == gx(satuan)) {
                    addautorow(row);
                } else {
                    pane.tabledata.requestFocus();
                    pane.tabledata.changeSelection(row, col + 1, false, false);
                }

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
                if (col == gx(kode)) {
                    if (pane.tabledata.getRowCount() > 1) {
                        pane.tabledata.requestFocus();
                        pane.tabledata.changeSelection(row - 1, gx(satuan), false, false);

                    } else {
                        pane.tabledata.requestFocus();
                        pane.tabledata.changeSelection(row, col, false, false);
                    }
                } else {
                    pane.tabledata.requestFocus();
                    pane.tabledata.changeSelection(row, col - 1, false, false);
                }

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
                pane.tabledata.requestFocus();
                pane.tabledata.changeSelection(row, col + 1, false, false);
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
                pane.tabledata.requestFocus();
                pane.tabledata.changeSelection(row, col - 1, false, false);
            }
        }
        );

    }

    private void addautorow(int row) {
        int lastrow = pane.tabledata.getRowCount() - 1;
        if (!pane.tabledata.getValueAt(row, gx(kode)).equals("")
             || !pane.tabledata.getValueAt(row, gx(jumlah)).equals("")
             || !pane.tabledata.getValueAt(row, gx(satuan)).equals("")) {
            if (row == lastrow) {
                tabeldatalist.add(new Entitytabledata("", "", "", "0", "", "", "", "0"));
                dtmtabeldata.addRow(rowtabledata);
                pane.tabledata.requestFocus();
                pane.tabledata.changeSelection(row + 1, 0, false, false);
            } else {
                pane.tabledata.requestFocus();
                pane.tabledata.changeSelection(row + 1, 0, false, false);
            }
        }
    }

    private void columnfunction(int row, int col, boolean addrow) {
        if (pane.tabledata.getValueAt(row, col).equals("")) {
            return;
        }
        if ((col == gx(satuan))) {
            String value = nf.format(ConvertFunc.ToDouble(pane.tabledata.getValueAt(row, col)));
            pane.tabledata.setValueAt(value, row, col);
            if (addrow == true) {
                addautorow(row);
            }
        }
    }

    private double kalkulasiload(String qty, String harga) {
        double inqty = ConvertFunc.ToDouble(qty);
        double inharga = ConvertFunc.ToDouble(harga);
        double intotal = inqty * inharga;
        if (intotal == -0) {
            intotal = 0;
        }

        return intotal;
    }

    private int gx(String columname) {
        return listheadername.indexOf(columname);
    }

    public class Entitytabledata {

        String id_inv, kode, nama, jumlah, id_satuan, satuan, id_satuan_pengali, qty_satuan_pengali;

        public Entitytabledata(String id_inv, String kode, String nama, String jumlah, String id_satuan, String satuan, String id_satuan_pengali, String qty_satuan_pengali) {
            this.id_inv = id_inv;
            this.kode = kode;
            this.nama = nama;
            this.jumlah = jumlah;
            this.id_satuan = id_satuan;
            this.satuan = satuan;
            this.id_satuan_pengali = id_satuan_pengali;
            this.qty_satuan_pengali = qty_satuan_pengali;
        }

        public String getId_inv() {
            return id_inv;
        }

        public void setId_inv(String id_inv) {
            this.id_inv = id_inv;
        }

        public String getKode() {
            return kode;
        }

        public void setKode(String kode) {
            this.kode = kode;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
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

        public String getSatuan() {
            return satuan;
        }

        public void setSatuan(String satuan) {
            this.satuan = satuan;
        }

        public String getId_satuan_pengali() {
            return id_satuan_pengali;
        }

        public void setId_satuan_pengali(String id_satuan_pengali) {
            this.id_satuan_pengali = id_satuan_pengali;
        }

        public String getQty_satuan_pengali() {
            return qty_satuan_pengali;
        }

        public void setQty_satuan_pengali(String qty_satuan_pengali) {
            this.qty_satuan_pengali = qty_satuan_pengali;
        }

    }
}
