/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.pembelian;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.Pane;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Errorpanel;
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.pembelian.Sethargajual;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class SethargajualController {

    CrudHelper ch = new CrudHelper();
    Sethargajual pane;
    int val_harga_jual_tipe, val_harga_berdasar_tipe;
    String val_id_satuan, val_kode_satuan;
    DefaultTableModel dtmtabeldata = new DefaultTableModel();
    Object[] rowtabledata = new Object[6];
    ArrayList<Integer> lshide = new ArrayList<>();
    ArrayList<Integer> lsoldhide = new ArrayList<>();
    ArrayList<Integer> lsresize = new ArrayList<>();
    ArrayList<Integer> lsoldsize = new ArrayList<>();
    ArrayList<Entitytabledata> tabeldatalist = new ArrayList<>();
    NumberFormat nf = NumberFormat.getInstance();

    private boolean ischangevalue = false;
    static String oldvalue = "";

    ArrayList<Integer> lsvisiblecolom = new ArrayList<>();
    static boolean sudahterpanggil = false;

    public SethargajualController(Sethargajual pane) {
        this.pane = pane;
        String id_barang = Staticvar.ids;
        String harga_jual = String.valueOf(Staticvar.map_var.get("harga_jual"));
        String harga_beli = String.valueOf(Staticvar.map_var.get("harga_beli"));
        Staticvar.ids = "";
        Staticvar.map_var.clear();
        checkandcombocontrol();
        loaddata(id_barang, harga_beli);
        addtotable(id_barang);
        hapusbaris();
        simpandata(id_barang);
        batal(harga_beli, harga_jual);

    }

    private void customtable() {
        pane.tabledata.setRowSelectionAllowed(false);
        pane.tabledata.setCellSelectionEnabled(true);
        pane.tabledata.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        dtmtabeldata = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 3 ? false : true;
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

        for (int i = 0; i < Staticvar.keyholdnumeric.length; i++) {
            pane.tabledata.getInputMap().put(KeyStroke.getKeyStroke(Staticvar.keyholdnumeric[i]), "startEditing");
        }

    }

    private void checkandcombocontrol() {
        pane.ckharga_jual_persen.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JCheckBox c = (JCheckBox) e.getSource();
                if (c.isSelected()) {
                    val_harga_jual_tipe = 1;
                    showtable(5);
                    hidetable(4);
                    pane.cmbharga_berdasar.setVisible(true);
                    pane.lharga_berdasar.setVisible(true);
                } else {
                    val_harga_jual_tipe = 0;
                    showtable(4);
                    hidetable(5);
                    pane.cmbharga_berdasar.setVisible(false);
                    pane.lharga_berdasar.setVisible(false);
                }
            }
        });

        pane.cmbharga_berdasar.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                if (cb.getSelectedIndex() == 0) {
                    val_harga_berdasar_tipe = 0;
                } else if (cb.getSelectedIndex() == 1) {
                    val_harga_berdasar_tipe = 1;
                } else {
                    val_harga_berdasar_tipe = 2;
                }
            }
        });
    }

    private void loadheader() {
        try {
            pane.tabledata.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            pane.tabledata.setModel(dtmtabeldata);
            String dataheader = ch.getheaderpopups();
            JSONParser jpheader = new JSONParser();
            Object objheader = jpheader.parse(dataheader);
            JSONObject joheader = (JSONObject) objheader;
            JSONArray jaheader = (JSONArray) joheader.get("sethargajual");
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
            Logger.getLogger(DaftarfakturpembelianinnerController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void setheader() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        TableColumnModel tcm = pane.tabledata.getColumnModel();

        double lebar = d.getWidth() - 950;
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

    public void loaddata(String id_barang, String harga_barang) {
        try {
            customtable();
            loadheader();
            JSONParser jpdata = new JSONParser();
            String param = String.format("id=%s", id_barang);
            Object rawobjdata = jpdata.parse(ch.getdatadetails("datasettinghargajualpersediaan", param));
            JSONObject jsonobjdata = (JSONObject) rawobjdata;

            Object jodatapembelian = jsonobjdata.get("datapersediaan");
            JSONArray jadatapembelian = (JSONArray) jodatapembelian;
            pane.edharga_beli.setText(harga_barang);
            for (int i = 0; i < jadatapembelian.size(); i++) {
                JSONObject joindatapembelian = (JSONObject) jadatapembelian.get(i);
                pane.lkode_barang.setText(String.valueOf(joindatapembelian.get("kode")));
                pane.lnama_barang.setText(String.valueOf(joindatapembelian.get("nama")));
                pane.edharga_jual.setText(String.valueOf(joindatapembelian.get("harga_jual")));
                pane.edharga_master.setText(String.valueOf(joindatapembelian.get("harga_master")));
                pane.edmark_up_harga_beli.setText(String.valueOf(joindatapembelian.get("harga_jual_persen")));
                val_id_satuan = String.valueOf(joindatapembelian.get("id_satuan"));
                val_kode_satuan = String.valueOf(joindatapembelian.get("kode_satuan"));
                val_harga_jual_tipe = Integer.parseInt(String.valueOf(joindatapembelian.get("ishargajualpersen")));
                if (val_harga_jual_tipe == 1) {
                    pane.ckharga_jual_persen.setSelected(true);
                    pane.cmbharga_berdasar.setVisible(true);
                    pane.lharga_berdasar.setVisible(true);
                } else {
                    pane.ckharga_jual_persen.setSelected(false);
                    pane.cmbharga_berdasar.setVisible(false);
                    pane.lharga_berdasar.setVisible(false);
                }
                val_harga_berdasar_tipe = Integer.parseInt(String.valueOf(joindatapembelian.get("harga_jual_berdasar")));
                if (val_harga_berdasar_tipe == 0) {
                    pane.cmbharga_berdasar.setSelectedIndex(0);
                } else if (val_harga_berdasar_tipe == 1) {
                    pane.cmbharga_berdasar.setSelectedIndex(1);
                } else {
                    pane.cmbharga_berdasar.setSelectedIndex(2);
                }
            }

            Object objtabeldata = jsonobjdata.get("datamultiharga");
            JSONArray jatabledata = (JSONArray) objtabeldata;

            if (jatabledata.isEmpty()) {
                tabeldatalist.add(new Entitytabledata("", "", "", "", "", "", "", "", "", ""));
                dtmtabeldata.addRow(rowtabledata);
                pane.tabledata.requestFocus();
                pane.tabledata.changeSelection(0, 0, false, false);
            } else {
                for (int i = 0; i < jatabledata.size(); i++) {
                    JSONObject jointabeldata = (JSONObject) jatabledata.get(i);
                    String id_golongan = String.valueOf(jointabeldata.get("id_golongan"));
                    String kode_golongan = String.valueOf(jointabeldata.get("kode_golongan"));
                    String dari = String.valueOf(jointabeldata.get("dari"));
                    String hingga = String.valueOf(jointabeldata.get("hingga"));
                    String id_satuan = String.valueOf(jointabeldata.get("id_satuan"));
                    String kode_satuan = String.valueOf(jointabeldata.get("kode_satuan"));
                    String harga_jual = nf.format(ConvertFunc.ToDouble(jointabeldata.get("harga_jual")));
                    String harga_jual_persen = String.valueOf(jointabeldata.get("harga_jual_persen"));
                    String id_satuan_pengali = String.valueOf(jointabeldata.get("id_satuan_pengali"));
                    String qty_satuan_pengali = String.valueOf(jointabeldata.get("qty_satuan_pengali"));
                    tabeldatalist.add(new Entitytabledata(id_golongan, kode_golongan, dari, hingga, id_satuan,
                            kode_satuan, harga_jual, harga_jual_persen, id_satuan_pengali, qty_satuan_pengali));

                }
                for (int i = 0; i < tabeldatalist.size(); i++) {
                    rowtabledata[0] = tabeldatalist.get(i).getKode_golongan();
                    rowtabledata[1] = tabeldatalist.get(i).getDari();
                    rowtabledata[2] = tabeldatalist.get(i).getHingga();
                    rowtabledata[3] = tabeldatalist.get(i).getKode_satuan();
                    rowtabledata[4] = tabeldatalist.get(i).getHarga_jual();
                    rowtabledata[5] = tabeldatalist.get(i).getHarga_jual_persen();
                    dtmtabeldata.addRow(rowtabledata);
                }
                //kalkulasitotal();
                for (int i = 0; i < rowtabledata.length; i++) {
                    rowtabledata[i] = "";
                }
            }

        } catch (ParseException ex) {
            Logger.getLogger(SethargajualController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void addtotable(String idb_barang) {
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
                    if (col == 1) {
                        try {
                            ischangevalue = true;
                            tabeldatalist.get(row).setDari(String.valueOf(tm.getValueAt(row, 1)));
                        } catch (Exception ex) {
                        } finally {
                            ischangevalue = false;
                        }
                    } else if (col == 2) {
                        try {
                            ischangevalue = true;
                            tabeldatalist.get(row).setHingga(String.valueOf(tm.getValueAt(row, 2)));
                        } catch (Exception ex) {
                        } finally {
                            ischangevalue = false;
                        }
                    } else if (col == 4) {
                        try {
                            ischangevalue = true;
                            tabeldatalist.get(row).setHarga_jual(String.valueOf(tm.getValueAt(row, 4)));
                        } catch (Exception ex) {
                        } finally {
                            ischangevalue = false;
                        }
                    } else if (col == 5) {
                        ischangevalue = true;
                        tabeldatalist.get(row).setHarga_jual_persen(String.valueOf(tm.getValueAt(row, 5)));
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
                    if (col == 0) {
                        try {
                            Staticvar.preid = tabeldatalist.get(row).getId_golongan();
                            Staticvar.prelabel = tabeldatalist.get(row).getKode_golongan();
                            Staticvar.prevalueextended = tabeldatalist.get(row).getQty_satuan_pengali();
                            JDialog jd = new JDialog(new Mainmenu());
                            jd.add(new Popupcari("golongan", "popupdaftargolongan?tipe=0", "Daftar Golongan"));
                            jd.pack();
                            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                            jd.setLocationRelativeTo(null);
                            jd.setVisible(true);
                            jd.toFront();
                            tabeldatalist.get(row).setId_golongan(Staticvar.resid);
                            tabeldatalist.get(row).setKode_golongan(Staticvar.reslabel);
                            pane.tabledata.setValueAt(Staticvar.reslabel, row, 0);
                            tabeldatalist.get(row).setDari("1");
                            pane.tabledata.setValueAt("1", row, 1);
                            tabeldatalist.get(row).setHingga("99");
                            pane.tabledata.setValueAt("99", row, 2);
                            tabeldatalist.get(row).setId_satuan(val_id_satuan);
                            tabeldatalist.get(row).setKode_satuan(val_kode_satuan);
                            pane.tabledata.setValueAt(val_kode_satuan, row, 3);
                            tabeldatalist.get(row).setHarga_jual("0");
                            pane.tabledata.setValueAt("0", row, 4);
                            tabeldatalist.get(row).setId_satuan_pengali(val_id_satuan);
                            tabeldatalist.get(row).setQty_satuan_pengali("1");

                        } catch (Exception ex) {
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
                            Logger.getLogger(DaftarfakturpembelianinputController.class.getName()).log(Level.SEVERE, null, ex);

                        }
                    } else if (col == 3) {
                        Staticvar.sfilter = "";
                        Staticvar.preid = tabeldatalist.get(row).getId_satuan();
                        Staticvar.prelabel = String.valueOf(pane.tabledata.getValueAt(row, 3));
                        Staticvar.prevalueextended = tabeldatalist.get(row).getQty_satuan_pengali();
                        JDialog jd = new JDialog(new Mainmenu());
                        jd.add(new Popupcari("satuanperbarang",
                                String.format("popupdaftarsatuanperbarang?id_inv=%s", idb_barang),
                                "Daftar Satuan Perbarang"));
                        jd.pack();
                        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        jd.setLocationRelativeTo(null);
                        jd.setVisible(true);
                        jd.toFront();
                        tabeldatalist.get(row).setId_satuan(Staticvar.resid);
                        pane.tabledata.setValueAt(Staticvar.reslabel, row, 3);
                        tabeldatalist.get(row).setQty_satuan_pengali(Staticvar.resvalueextended);
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
                pane.bhapus.doClick();
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
                    Staticvar.preid = tabeldatalist.get(row).getId_golongan();
                    Staticvar.prelabel = tabeldatalist.get(row).getKode_golongan();
                    Staticvar.prevalueextended = tabeldatalist.get(row).getQty_satuan_pengali();
                    JDialog jd = new JDialog(new Mainmenu());
                    jd.add(new Popupcari("golongan", "popupdaftargolongan?tipe=0", "Daftar Golongan"));
                    jd.pack();
                    jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                    jd.setLocationRelativeTo(null);
                    jd.setVisible(true);
                    jd.toFront();
                    tabeldatalist.get(row).setId_golongan(Staticvar.resid);
                    tabeldatalist.get(row).setKode_golongan(Staticvar.reslabel);
                    pane.tabledata.setValueAt(Staticvar.reslabel, row, 0);
                    tabeldatalist.get(row).setDari("1");
                    pane.tabledata.setValueAt("1", row, 1);
                    tabeldatalist.get(row).setHingga("99");
                    pane.tabledata.setValueAt("99", row, 2);
                    tabeldatalist.get(row).setKode_satuan(val_kode_satuan);
                    pane.tabledata.setValueAt(val_kode_satuan, row, 3);
                    tabeldatalist.get(row).setId_satuan(val_id_satuan);
                    tabeldatalist.get(row).setHarga_jual("0");
                    pane.tabledata.setValueAt("0", row, 4);
                    tabeldatalist.get(row).setId_satuan_pengali(val_id_satuan);
                    tabeldatalist.get(row).setQty_satuan_pengali("1");
                } else if (col == 3) {
                    Staticvar.sfilter = "";
                    Staticvar.preid = tabeldatalist.get(row).getId_satuan();
                    Staticvar.prelabel = String.valueOf(pane.tabledata.getValueAt(row, 3));
                    Staticvar.prevalueextended = tabeldatalist.get(row).getQty_satuan_pengali();
                    JDialog jd = new JDialog(new Mainmenu());
                    jd.add(new Popupcari("satuanperbarang",
                            String.format("popupdaftarsatuanperbarang?id_inv=%s", idb_barang),
                            "Daftar Satuan Perbarang"));
                    jd.pack();
                    jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                    jd.setLocationRelativeTo(null);
                    jd.setVisible(true);
                    jd.toFront();
                    tabeldatalist.get(row).setId_satuan(Staticvar.resid);
                    pane.tabledata.setValueAt(Staticvar.reslabel, row, 3);
                    tabeldatalist.get(row).setQty_satuan_pengali(Staticvar.resvalueextended);
                    dtmtabeldata.fireTableCellUpdated(row, 3);
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

    private void nextcolom(int col, int row) {
        int colcount = pane.tabledata.getColumnCount() - 1;

        if (col == 4 || col == 5) {
            if (pane.tabledata.getRowCount() - 1 > row) {
                pane.tabledata.requestFocus();
                pane.tabledata.changeSelection(row + 1, 0, false, false);
            } else if (String.valueOf(pane.tabledata.getValueAt(row, 0)).equals("")
                    || String.valueOf(pane.tabledata.getValueAt(row, 0)).equals("null")) {
                pane.tabledata.requestFocus();
                pane.tabledata.changeSelection(row, 0, false, false);
            } else {
                addautorow(row);
                return;
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
                        if (((j + 1) == 4) || ((j + 1) == 5)) {
                            if (pane.ckharga_jual_persen.isSelected()) {
                                if ((j + 1) == 5) {
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
                                    pane.tabledata.changeSelection(currentrow, 4, false, false);
                                }
                            } else {
                                pane.tabledata.requestFocus();
                                pane.tabledata.changeSelection(currentrow, 4, false, false);
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
                        if (((j - 1) == 4) || ((j - 1) == 5)) {
                            if (pane.ckharga_jual_persen.isSelected()) {
                                if ((j - 1) == 5) {
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
                                    pane.tabledata.changeSelection(currentrow, 4, false, false);
                                }
                            } else {
                                if ((j - 1) == 4) {
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
                                    pane.tabledata.changeSelection(currentrow, 5, false, false);
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

    private void addautorow(int row) {
        int lastrow = pane.tabledata.getRowCount() - 1;
        if (!pane.tabledata.getValueAt(row, 0).equals("")
                || !pane.tabledata.getValueAt(row, 3).equals("")) {
            if (row == lastrow) {
                tabeldatalist.add(new Entitytabledata("", "", "", "", "", "", "", "", "", ""));
                dtmtabeldata.addRow(rowtabledata);
                pane.tabledata.requestFocus();
                pane.tabledata.changeSelection(row + 1, 0, false, false);
            }
        }
    }

    private void hapusbaris() {
        pane.bhapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = pane.tabledata.getSelectedRow();
                int dialog = JOptionPane.showConfirmDialog(null, "Yakin akan menghapus data ini?",
                        "Konfirmasi", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (dialog == 0) {
                    Runnable rn = new Runnable() {
                        @Override
                        public void run() {
                            int rowcount = pane.tabledata.getRowCount();
                            if (rowcount == 1) {
                                tabeldatalist.remove(row);
                                dtmtabeldata.removeRow(row);
                                pane.tabledata.repaint();
                                tabeldatalist.add(new Entitytabledata("", "", "", "", "", "", "", "", "", ""));
                                dtmtabeldata.addRow(rowtabledata);
                                pane.tabledata.requestFocus();
                                pane.tabledata.changeSelection(1, 0, false, false);
                            } else {
                                tabeldatalist.remove(row);
                                dtmtabeldata.removeRow(row);
                                pane.tabledata.repaint();
                            }

                        }
                    };
                    SwingUtilities.invokeLater(rn);
                }

            }
        });
    }

    private void rawsimpan(String id_barang) {
        String data = "datapersediaan="
                + "harga_beli='" + ConvertFunc.ToDouble(pane.edharga_beli.getText()) + "'::"
                + "harga_jual='" + ConvertFunc.ToDouble(pane.edharga_jual.getText()) + "'::"
                + "harga_master='" + ConvertFunc.ToDouble(pane.edharga_master.getText()) + "'::"
                + "harga_jual_persen='" + ConvertFunc.ToDouble(pane.edmark_up_harga_beli.getText()) + "'::"
                + "ishargajualpersen='" + val_harga_jual_tipe + "'::"
                + "harga_jual_berdasar='" + val_harga_berdasar_tipe + "'"
                + "&" + kirimtexharga();
        System.out.println(data);
        ch.updatedata("updatesettinghargajualpersediaan", data, id_barang);
        if (Staticvar.getresult.equals("berhasil")) {
            Staticvar.map_var.clear();
            Staticvar.map_var.put("harga_beli", pane.edharga_beli.getText());
            Staticvar.map_var.put("harga_jual", pane.edharga_jual.getText());
            JDialog jd = (JDialog) pane.getRootPane().getParent();
            jd.dispose();
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

    private String kirimtexharga() {
        StringBuilder sb = new StringBuilder();
        sb.append("datamultiharga=");
        int listcount = 0;
        if (tabeldatalist.get(tabeldatalist.size() - 1).getId_golongan().equals("")) {
            listcount = tabeldatalist.size() - 1;
        } else {
            listcount = tabeldatalist.size();
        }
        for (int i = 0; i < listcount; i++) {
            sb.append("id_golongan=" + "'" + tabeldatalist.get(i).getId_golongan() + "'" + "::"
                    + "dari=" + "'" + ConvertFunc.ToDouble(tabeldatalist.get(i).getDari()) + "'" + "::"
                    + "hingga=" + "'" + ConvertFunc.ToDouble(tabeldatalist.get(i).getHingga()) + "'" + "::"
                    + "id_satuan=" + "'" + tabeldatalist.get(i).getId_satuan() + "'" + "::"
                    + "harga_jual=" + "'" + ConvertFunc.ToDouble(tabeldatalist.get(i).getHarga_jual()) + "'" + "::"
                    + "harga_jual_persen=" + "'" + ConvertFunc.ToDouble(tabeldatalist.get(i).getHarga_jual_persen()) + "'" + "::"
                    + "id_satuan_pengali=" + "'" + tabeldatalist.get(i).getId_satuan_pengali() + "'" + "::"
                    + "qty_satuan_pengali=" + "'" + ConvertFunc.ToDouble(tabeldatalist.get(i).getQty_satuan_pengali()) + "'");
            sb.append("--");
        }
        String rawhasil = sb.toString().substring(0, sb.toString().length() - 2);
        return rawhasil;
    }

    private void simpandata(String id_barang) {
        pane.bsimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rawsimpan(id_barang);
            }
        });
    }

    private void batal(String harga_beli, String harga_jual) {
        pane.bbatal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Staticvar.map_var.put("harga_beli", harga_beli);
                Staticvar.map_var.put("harga_jual", harga_jual);
                JDialog jd = (JDialog) pane.getRootPane().getParent();
                jd.dispose();
            }
        });

    }

    public class Entitytabledata {

        String id_golongan, kode_golongan, dari, hingga, id_satuan, kode_satuan,
                harga_jual, harga_jual_persen, id_satuan_pengali, qty_satuan_pengali;

        public Entitytabledata(String id_golongan, String kode_golongan, String dari, String hingga, String id_satuan, String kode_satuan, String harga_jual, String harga_jual_persen, String id_satuan_pengali, String qty_satuan_pengali) {
            this.id_golongan = id_golongan;
            this.kode_golongan = kode_golongan;
            this.dari = dari;
            this.hingga = hingga;
            this.id_satuan = id_satuan;
            this.kode_satuan = kode_satuan;
            this.harga_jual = harga_jual;
            this.harga_jual_persen = harga_jual_persen;
            this.id_satuan_pengali = id_satuan_pengali;
            this.qty_satuan_pengali = qty_satuan_pengali;
        }

        public String getId_golongan() {
            return id_golongan;
        }

        public void setId_golongan(String id_golongan) {
            this.id_golongan = id_golongan;
        }

        public String getKode_golongan() {
            return kode_golongan;
        }

        public void setKode_golongan(String kode_golongan) {
            this.kode_golongan = kode_golongan;
        }

        public String getDari() {
            return dari;
        }

        public void setDari(String dari) {
            this.dari = dari;
        }

        public String getHingga() {
            return hingga;
        }

        public void setHingga(String hingga) {
            this.hingga = hingga;
        }

        public String getId_satuan() {
            return id_satuan;
        }

        public void setId_satuan(String id_satuan) {
            this.id_satuan = id_satuan;
        }

        public String getKode_satuan() {
            return kode_satuan;
        }

        public void setKode_satuan(String kode_satuan) {
            this.kode_satuan = kode_satuan;
        }

        public String getHarga_jual() {
            return harga_jual;
        }

        public void setHarga_jual(String harga_jual) {
            this.harga_jual = harga_jual;
        }

        public String getHarga_jual_persen() {
            return harga_jual_persen;
        }

        public void setHarga_jual_persen(String harga_jual_persen) {
            this.harga_jual_persen = harga_jual_persen;
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
