/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.pembelian;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Errorpanel;
import mizanposapp.view.innerpanel.pembelian.Daftarfakturpembelian_input_panel;
import mizanposapp.view.innerpanel.pembelian.Daftarhutangrincian_inner_panel;
import mizanposapp.view.innerpanel.pembelian.Daftarpembayaranhutangperinvoice_input_panel;
import mizanposapp.view.innerpanel.pembelian.Daftarreturpembelian_input_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class DaftarhutangrincianinnerController {

    CrudHelper ch = new CrudHelper();
    ArrayList<String> idlist = new ArrayList<>();
    ArrayList<String> lsdata = new ArrayList();
    ArrayList<Integer> lssize = new ArrayList();
    ArrayList<String> idlistrincian = new ArrayList<>();
    ArrayList<String> idlistrinciankeltrans = new ArrayList<>();
    ArrayList<String> lsdatarincian = new ArrayList();
    ArrayList<Integer> lssizerincian = new ArrayList();
    DefaultTableModel dtm = new DefaultTableModel();
    DefaultTableModel dtmrincian = new DefaultTableModel();
    Daftarhutangrincian_inner_panel pane;
    String id = "";

    public DaftarhutangrincianinnerController(Daftarhutangrincian_inner_panel pane) {
        this.pane = pane;
        id = Staticvar.ids;
        pane.lheader.setText("Daftar Hutang Usaha - " + String.valueOf(Staticvar.map_var.get("nama_supplier")));
        loadheader();
        loadheaderrincian();
        loaddata(0);
        loaddatadetail();
        inputpembayaran();
        editpembayaran();
        editpembayarandetail();
        updateloaddata();
        selectdata();
        selectdata2();
        oncarienter();
        onbuttoncari();
        deleterincian();

    }

    private void loadheader() {
        try {
            pane.tabledata.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
            pane.tabledata.setModel(dtm);
            TableColumnModel tcm = pane.tabledata.getColumnModel();
            JTableHeader thead = pane.tabledata.getTableHeader();
            thead.setFont(new Font("Century Gothic", Font.BOLD, 13));
            pane.tabledata.setRowHeight(25);
            pane.tabledata.setDefaultEditor(Object.class, null);
            String dataheader = ch.getheaders();
            JSONParser jpheader = new JSONParser();
            Object objheader = jpheader.parse(dataheader);
            JSONObject joheader = (JSONObject) objheader;
            JSONArray jaheader = (JSONArray) joheader.get("hutangrincianheader");
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

            for (int i = 0; i < lssize.size(); i++) {
                Double wd = d.getWidth() - 344;
                int wi = (lssize.get(i) * wd.intValue()) / 100;
                tcm.getColumn(i).setMinWidth(wi);
                tcm.getColumn(i).setMaxWidth(wi);
            }
        } catch (ParseException ex) {
            Logger.getLogger(DaftarhutangrincianinnerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadheaderrincian() {
        try {
            pane.tabledatarincian.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
            pane.tabledatarincian.setModel(dtmrincian);
            TableColumnModel tcm = pane.tabledatarincian.getColumnModel();
            JTableHeader thead = pane.tabledatarincian.getTableHeader();
            thead.setFont(new Font("Century Gothic", Font.BOLD, 13));
            pane.tabledatarincian.setRowHeight(25);
            pane.tabledatarincian.setDefaultEditor(Object.class, null);
            String dataheader = ch.getheaders();
            JSONParser jpheader = new JSONParser();
            Object objheader = jpheader.parse(dataheader);
            JSONObject joheader = (JSONObject) objheader;
            JSONArray jaheader = (JSONArray) joheader.get("hutangrinciandetail");
            //perulangan mengambil header
            for (int i = 0; i < jaheader.size(); i++) {
                JSONObject jodata = (JSONObject) jaheader.get(i);
                JSONArray jaaray = (JSONArray) jodata.get("key");
                if (jaaray.get(2).equals("1")) {
                    dtmrincian.addColumn(jaaray.get(1));
                    lsdatarincian.add(String.valueOf(jaaray.get(0)));
                    lssizerincian.add(Integer.parseInt(String.valueOf(jaaray.get(3))));
                }
            }

            for (int i = 0; i < lssizerincian.size(); i++) {
                Double wd = d.getWidth() - 344;
                int wi = (lssizerincian.get(i) * wd.intValue()) / 100;
                tcm.getColumn(i).setMinWidth(wi);
                tcm.getColumn(i).setMaxWidth(wi);
            }
        } catch (ParseException ex) {
            Logger.getLogger(DaftarhutangrincianinnerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loaddata(int row) {
        cleardata();
        disablebutton();
        dtm.getDataVector().removeAllElements();
        dtm.fireTableDataChanged();
        SwingWorker worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                pane.indi.setVisible(true);
                JSONParser jpdata = new JSONParser();
                String param = String.format("id=%s", String.valueOf(Staticvar.map_var.get("id_supplier")));
                Object objdata = jpdata.parse(ch.getdatadetails("daftarhutangpersupplier", param));
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
                pane.tabledata.requestFocus();
                pane.tabledata.changeSelection(row, 0, false, false);
            }

        };
        worker.execute();

    }

    private void loaddatadetailraw() {
        cleardata2();
        disablebutton();
        dtm.getDataVector().removeAllElements();
        dtm.fireTableDataChanged();
        SwingWorker worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                pane.indi.setVisible(true);
                JSONParser jpdata = new JSONParser();
                String param = String.format("id=%s&cari=%s", id, pane.tcari.getText());
                Object objdata = jpdata.parse(ch.getdatadetails("carihutangpersupplier", param));
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

    private void loaddatadetailrincian(String id) {
        cleardata2();
        dtmrincian.getDataVector().removeAllElements();
        dtmrincian.fireTableDataChanged();
        SwingWorker worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                pane.indi.setVisible(true);
                JSONParser jpdata = new JSONParser();
                String param = String.format("id=%s", id);
                Object objdata = jpdata.parse(ch.getdatadetails("daftarcicilanhutangpersupplier", param));
                JSONArray jadata = (JSONArray) objdata;
                dtmrincian.setRowCount(0);
                for (int i = 0; i < jadata.size(); i++) {
                    JSONObject joindata = (JSONObject) jadata.get(i);
                    Object[] objindata = new Object[lsdatarincian.size()];
                    idlistrincian.add(String.valueOf(joindata.get("id")));
                    idlistrinciankeltrans.add(String.valueOf(joindata.get("id_keltrans")));
                    for (int j = 0; j < objindata.length; j++) {
                        objindata[j] = joindata.get(lsdatarincian.get(j));
                    }
                    dtmrincian.addRow(objindata);
                }

                return null;
            }

            @Override
            protected void done() {
                pane.indi.setVisible(false);
                pane.tabledatarincian.setModel(dtmrincian);
                disablebutton2();
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
                    loaddatadetailraw();
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
                if (pane.tabledata.getSelectionModel().isSelectionEmpty()) {

                } else {
                    int row = pane.tabledata.getSelectedRow();
                    String ids = idlist.get(row);
                    loaddatadetailrincian(ids);
                    enablebutton();
                }
            }
        });
    }

    private void selectdata2() {
        pane.tabledatarincian.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (pane.tabledata.getSelectionModel().isSelectionEmpty()) {
                } else {
                    enablebutton2();
                }
            }
        });
    }

    private void cleardata() {
        idlist.clear();
        Staticvar.ids = "";
    }

    private void cleardata2() {
        idlistrincian.clear();
        Staticvar.ids = "";
    }

    private void updateloaddata() {
        pane.bupdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loaddata(0);
                loaddatadetailrincian("");
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
                    loaddatadetailraw();
                }
            }
        });
    }

    private void disablebutton() {
        pane.bedit.setEnabled(false);
        pane.bhapus.setEnabled(false);
    }

    private void enablebutton() {
        pane.bedit.setEnabled(true);
        pane.bhapus.setEnabled(true);
    }

    private void disablebutton2() {
        pane.bedit2.setEnabled(false);
        pane.bhapus2.setEnabled(false);
    }

    private void enablebutton2() {
        pane.bedit2.setEnabled(true);
        pane.bhapus2.setEnabled(true);
    }

    private void inputpembayaran() {
        pane.btambah.addActionListener((ActionEvent e) -> {
            Staticvar.ids = "";
            Staticvar.frame = "rincian_hutang";
            Daftarpembayaranhutangperinvoice_input_panel inpane = new Daftarpembayaranhutangperinvoice_input_panel();
            Staticvar.pmp.container.removeAll();
            Staticvar.pmp.container.setLayout(new BorderLayout());
            Staticvar.pmp.container.add(inpane, BorderLayout.CENTER);
            Staticvar.pmp.container.revalidate();
            Staticvar.pmp.container.repaint();
        });
    }

    private void editpembayaran() {
        pane.bedit.addActionListener((ActionEvent e) -> {
            Staticvar.frame = "rincian_hutang";
            int row = pane.tabledata.getSelectedRow();
            Staticvar.ids = idlist.get(row);
            Daftarfakturpembelian_input_panel inpane = new Daftarfakturpembelian_input_panel();
            Staticvar.pmp.container.removeAll();
            Staticvar.pmp.container.setLayout(new BorderLayout());
            Staticvar.pmp.container.add(inpane, BorderLayout.CENTER);
            Staticvar.pmp.container.revalidate();
            Staticvar.pmp.container.repaint();
        });
    }

    private void editpembayarandetail() {
        pane.bedit2.addActionListener((ActionEvent e) -> {
            Staticvar.frame = "rincian_hutang";
            int row = pane.tabledatarincian.getSelectedRow();
            Staticvar.ids = idlistrincian.get(row);
            String id_keltrans = idlistrinciankeltrans.get(row);
            JPanel inpane = new JPanel();
            if (id_keltrans.equals("42")) {
                inpane = new Daftarpembayaranhutangperinvoice_input_panel();
            } else if (id_keltrans.equals("31")) {
                inpane = new Daftarreturpembelian_input_panel();
            }
            Staticvar.pmp.container.removeAll();
            Staticvar.pmp.container.setLayout(new BorderLayout());
            Staticvar.pmp.container.add(inpane, BorderLayout.CENTER);
            Staticvar.pmp.container.revalidate();
            Staticvar.pmp.container.repaint();
        });
    }

    private void deleterincian() {
        pane.bhapus2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = pane.tabledata.getSelectedRow();
                int rowdetail = pane.tabledatarincian.getSelectedRow();
                int dialog = JOptionPane.showConfirmDialog(null, "Yakin akan menghapus data ini?",
                        "Konfirmasi", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (dialog == 0) {
                    String data = String.format("id=%s", idlistrincian.get(rowdetail));
                    ch.deletedata("deletepembayaranhutang", data);
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
                                loaddata(row);
                                //loaddatadetailrincian(idlist.get(row));
                            }
                        } else {
                            if (Staticvar.isupdate == true) {
                                loaddata(row);
                                //loaddatadetailrincian(idlist.get(row));
                            }
                        }
                        Staticvar.isupdate = false;
                    }
                }

            }
        });

    }

}
