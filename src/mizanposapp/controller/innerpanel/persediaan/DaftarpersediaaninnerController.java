/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.persediaan;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Globalsession;
import mizanposapp.helper.Staticvar;
import mizanposapp.helper.Tablestyle;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Errorpanel;
import mizanposapp.view.innerpanel.persediaan.Daftarpersediaan_inner_panel;
import mizanposapp.view.innerpanel.persediaan.Daftarpersediaan_input_panel;
import mizanposapp.view.innerpanel.persediaan.Persedian_koreksistock_input_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class DaftarpersediaaninnerController {

    CrudHelper ch = new CrudHelper();
    ArrayList<String> idlist = new ArrayList<>();
    ArrayList<String> lsdata = new ArrayList();
    ArrayList<Integer> lssize = new ArrayList();
    DefaultTableModel dtm;
    Daftarpersediaan_inner_panel pane;

    public DaftarpersediaaninnerController(Daftarpersediaan_inner_panel pane) {
        this.pane = pane;
        userakses();
        loadheader();
        loaddata("0");
        loaddatadetail();
        inputdata();
        editdata();
        deletedata();
        updateloaddata();
        selectdata();
        oncarienter();
        onbuttoncari();
        koreksistock();
        popupstok();
    }

    private void loadheader() {
        try {
            dtm = new DefaultTableModel();
            pane.tabledata.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
            pane.tabledata.setModel(dtm);
            pane.tabledata.setRowHeight(25);
            pane.tabledata.setDefaultEditor(Object.class, null);
            String dataheader = ch.getheaders();
            JSONParser jpheader = new JSONParser();
            Object objheader = jpheader.parse(dataheader);
            JSONObject joheader = (JSONObject) objheader;
            JSONArray jaheader = (JSONArray) joheader.get("persediaan");
            //perulangan mengambil header
            for (int i = 0; i < jaheader.size(); i++) {
                JSONObject jodata = (JSONObject) jaheader.get(i);
                JSONArray jaaray = (JSONArray) jodata.get("key");
                if (jaaray.get(2).equals("1")) {
                    dtm.addColumn(jaaray.get(1));
                    lsdata.add(String.valueOf(jaaray.get(0)));
                    lssize.add(Integer.parseInt(String.valueOf(jaaray.get(3))));
                }
            }

            TableColumnModel tcm = pane.tabledata.getColumnModel();
            for (int i = 0; i < lssize.size(); i++) {
                Double wd = d.getWidth() - Staticvar.defmenupanel;
                int wi = (lssize.get(i) * wd.intValue()) / 100;
                tcm.getColumn(i).setMinWidth(wi);
                tcm.getColumn(i).setMaxWidth(wi);
            }
        } catch (ParseException ex) {
            Logger.getLogger(DaftarpersediaaninnerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void userakses() {
        if (Globalsession.dm_baru_edit_hapus.equals("0")) {
            pane.btambah.setEnabled(false);
            pane.bedit.setEnabled(false);
            pane.bhapus.setEnabled(false);
        }
    }

    private void loaddata(String halaman) {
        cleardata();
        disablebutton();
        dtm.getDataVector().removeAllElements();
        dtm.fireTableDataChanged();
        SwingWorker worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                pane.indi.setVisible(true);
                JSONParser jpdata = new JSONParser();
                String param = String.format("halaman=%s", halaman);
                Object objdata = jpdata.parse(ch.getdatadetails("dm/daftarpersediaan", param));
                JSONArray jadata = (JSONArray) objdata;
                dtm.setRowCount(0);
                for (int i = 0; i < jadata.size(); i++) {
                    JSONObject joindata = (JSONObject) jadata.get(i);
                    Object[] objindata = new Object[lsdata.size()];
                    idlist.add(String.valueOf(joindata.get("id")));
                    for (int j = 0; j < objindata.length; j++) {
                        objindata[j] = joindata.get(lsdata.get(j));
                    }
                    dtm.addRow(objindata);
                }
                return null;
            }

            @Override
            protected void done() {
                pane.indi.setVisible(false);
                pane.tabledata.setModel(dtm);
                disablebutton();
            }

        };
        worker.execute();

    }

    private void loaddatadetailraw(String halaman) {
        cleardata();
        disablebutton();
        dtm.getDataVector().removeAllElements();
        dtm.fireTableDataChanged();
        SwingWorker worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                pane.indi.setVisible(true);
                JSONParser jpdata = new JSONParser();
                String param = String.format("cari=%s&halaman=%s", pane.tcari.getText(), halaman);
                Object objdata = jpdata.parse(ch.getdatadetails("dm/caripersediaan", param));
                JSONArray jadata = (JSONArray) objdata;
                dtm.setRowCount(0);
                for (int i = 0; i < jadata.size(); i++) {
                    JSONObject joindata = (JSONObject) jadata.get(i);
                    Object[] objindata = new Object[lsdata.size()];
                    idlist.add(String.valueOf(joindata.get("id")));
                    for (int j = 0; j < objindata.length; j++) {
                        objindata[j] = joindata.get(lsdata.get(j));
                    }
                    dtm.addRow(objindata);
                }

                return null;
            }

            @Override
            protected void done() {
                pane.indi.setVisible(false);
                pane.tabledata.setModel(dtm);
                disablebutton();

            }

        };
        worker.execute();

    }

    private void loaddatadetail() {
        pane.tcari.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loaddatadetailraw("0");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    private void selectdata() {
        pane.tabledata.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    enablebutton();
                    //System.out.println(id);
                }

            }
        });
    }

    private void cleardata() {
        idlist.clear();
        Staticvar.ids = "";
    }

    private void editdata() {
        pane.bedit.addActionListener((ActionEvent e) -> {
            int row = pane.tabledata.getSelectedRow();
            Staticvar.ids = idlist.get(row);
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Daftarpersediaan_input_panel());
            jd.pack();
            jd.setLocationRelativeTo(null);
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setTitle("Edit Data Persediaan");
            jd.setVisible(true);
            if (pane.tcari.getText().equals("Cari Data") || pane.tcari.getText().equals("")) {
                if (Staticvar.isupdate == true) {
                    loaddata("0");
                }
            } else {
                if (Staticvar.isupdate == true) {
                    loaddatadetailraw("0");
                }
            }
            Staticvar.isupdate = false;
        });
    }

    private void inputdata() {
        pane.btambah.addActionListener((ActionEvent e) -> {
            Staticvar.ids = "";
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Daftarpersediaan_input_panel());
            jd.pack();
            jd.setLocationRelativeTo(null);
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setTitle("Input Data Service");
            jd.setVisible(true);
            if (pane.tcari.getText().equals("Cari Data") || pane.tcari.getText().equals("")) {
                if (Staticvar.isupdate == true) {
                    loaddata("0");
                }
            } else {
                if (Staticvar.isupdate == true) {
                    loaddatadetailraw("0");
                }
            }
            Staticvar.isupdate = false;
        });
    }

    private void deletedata() {
        pane.bhapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = pane.tabledata.getSelectedRow();
                System.out.println(idlist.get(row));
                if (JOptionPane.showConfirmDialog(null, "Yakin akan menghapus data ini?",
                     "Konfirmasi", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE) == 0) {
                    String data = String.format("id=%s", idlist.get(row));
                    ch.deletedata("dm/deletepersediaan", data);
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
                        Staticvar.isupdate = true;
                        if (pane.tcari.getText().equals("Cari Data") || pane.tcari.getText().equals("")) {
                            if (Staticvar.isupdate == true) {
                                loaddata("0");
                            }
                        } else {
                            if (Staticvar.isupdate == true) {
                                loaddatadetailraw("0");
                            }
                        }
                        Staticvar.isupdate = false;
                    }
                }

            }
        });

    }

    private void updateloaddata() {
        pane.bupdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loaddata("0");
                pane.tcari.setText("Cari Data");
            }
        });
    }

    private void oncarienter() {
        pane.tcari.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                pane.tcari.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                //pane.tcari.setText("Cari Data");
            }
        });
    }

    private void onbuttoncari() {
        pane.bcari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!pane.tcari.getText().equals("Cari Data")) {
                    loaddatadetailraw("0");
                }
            }
        });
    }

    private void disablebutton() {
        pane.bedit.setEnabled(false);
        pane.bhapus.setEnabled(false);
    }

    private void enablebutton() {
        if (Globalsession.dm_baru_edit_hapus.equals("1")) {
            pane.bedit.setEnabled(true);
            pane.bhapus.setEnabled(true);
        } else {
            pane.bedit.setEnabled(false);
            pane.bhapus.setEnabled(false);
        }

    }

    private void koreksistock() {
        pane.mkoreksi_stock.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Persedian_koreksistock_input_panel());
            jd.pack();
            jd.setLocationRelativeTo(null);
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setTitle("Koreksi Stock");
            jd.setVisible(true);
        });
    }

    private void popupstok() {
        pane.tabledata.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    pane.persediaanpopup.show(e.getComponent(), e.getX(), e.getY());
                }
            }

        });

    }

}
