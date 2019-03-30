/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.penjualan;

import mizanposapp.controller.innerpanel.penjualan.*;
import java.awt.BorderLayout;
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
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Globalsession;
import mizanposapp.helper.Staticvar;
import mizanposapp.helper.Tablestyle;
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
public class DaftarpiutanginnerController {

    CrudHelper ch = new CrudHelper();
    ArrayList<String> idlist = new ArrayList<>();
    ArrayList<String> lsdata = new ArrayList();
    ArrayList<Integer> lssize = new ArrayList();
    DefaultTableModel dtm = new DefaultTableModel();
    Daftarpiutang_inner_panel pane;
    String id = "";

    public DaftarpiutanginnerController(Daftarpiutang_inner_panel pane) {
        this.pane = pane;
        loadheader();
        filterdata();
        pane.cmbfilter.setSelectedIndex(0);
        loaddata("0");
        loaddatadetail();
        pembayaran();
        detailpembayaran();
        updateloaddata();
        selectdata();
        oncarienter();
        onbuttoncari();
        userakses();

    }

    private void userakses() {
        if (Globalsession.penjualan_piutang_input.equals("1")) {
            pane.bbayar.setEnabled(true);
        } else {
            pane.bbayar.setEnabled(false);
        }

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
            JSONArray jaheader = (JSONArray) joheader.get("piutang");
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
            Logger.getLogger(DaftarpiutanginnerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loaddata(String tipe) {
        cleardata();
        disablebutton();
        dtm.getDataVector().removeAllElements();
        dtm.fireTableDataChanged();
        SwingWorker worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                pane.indi.setVisible(true);
                JSONParser jpdata = new JSONParser();
                String param = "tipe=" + tipe;
                Object objdata = jpdata.parse(ch.getdatadetails("daftarpiutang", param));
                System.out.println(objdata);
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

    private void filterdata() {
        pane.cmbfilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pane.cmbfilter.getSelectedIndex() == 0) {
                    loaddata("0");
                } else if (pane.cmbfilter.getSelectedIndex() == 1) {
                    loaddata("1");
                } else if (pane.cmbfilter.getSelectedIndex() == 2) {
                    loaddata("2");
                }
            }
        });
    }

    private void loaddatadetailraw(String tipe) {
        cleardata();
        disablebutton();
        dtm.getDataVector().removeAllElements();
        dtm.fireTableDataChanged();
        SwingWorker worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                pane.indi.setVisible(true);
                JSONParser jpdata = new JSONParser();
                String param = String.format("tipe=%s&cari=%s", tipe, pane.tcari.getText());
                Object objdata = jpdata.parse(ch.getdatadetails("caripiutang", param));
                JSONArray jadata = (JSONArray) objdata;
                dtm.setRowCount(0);
                for (int i = 0; i < jadata.size(); i++) {
                    JSONObject joindata = (JSONObject) jadata.get(i);
                    Object[] objindata = new Object[lsdata.size()];
                    idlist.add(String.valueOf(joindata.get("ID")));
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
                    loaddatadetailraw(String.valueOf(pane.cmbfilter.getSelectedIndex()));
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

    private void updateloaddata() {
        pane.bupdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pane.cmbfilter.setSelectedIndex(0);
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
                    loaddatadetailraw(String.valueOf(pane.cmbfilter.getSelectedIndex()));
                }
            }
        });
    }

    private void disablebutton() {
        pane.bbayar.setEnabled(false);
        pane.bdetailbayar.setEnabled(false);
    }

    private void enablebutton() {
        if (Globalsession.penjualan_piutang_input.equals("1")) {
            pane.bbayar.setEnabled(true);
        } else {
            pane.bbayar.setEnabled(false);
        }
        pane.bdetailbayar.setEnabled(true);
    }

    private void pembayaran() {
        pane.bbayar.addActionListener((ActionEvent e) -> {
            int row = pane.tabledata.getSelectedRow();
            Staticvar.frame = "piutang";
            Staticvar.map_var.put("id_pelanggan", idlist.get(row));
            Staticvar.map_var.put("nama_pelanggan", pane.tabledata.getValueAt(row, 1));
            Daftarpembayaranpiutangperinvoice_input_panel inpane = new Daftarpembayaranpiutangperinvoice_input_panel();
            Staticvar.pp.container.removeAll();
            Staticvar.pp.container.setLayout(new BorderLayout());
            Staticvar.pp.container.add(inpane, BorderLayout.CENTER);
            Staticvar.pp.container.revalidate();
            Staticvar.pp.container.repaint();
        });
    }

    private void detailpembayaran() {
        pane.bdetailbayar.addActionListener((ActionEvent e) -> {
            int row = pane.tabledata.getSelectedRow();
            Staticvar.map_var.put("id_pelanggan", idlist.get(row));
            Staticvar.map_var.put("nama_pelanggan", pane.tabledata.getValueAt(row, 1));
            Daftarpiutangrincian_inner_panel inpane = new Daftarpiutangrincian_inner_panel();
            Staticvar.pp.container.removeAll();
            Staticvar.pp.container.setLayout(new BorderLayout());
            Staticvar.pp.container.add(inpane, BorderLayout.CENTER);
            Staticvar.pp.container.revalidate();
            Staticvar.pp.container.repaint();
        });
    }

}
