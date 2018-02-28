/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.persediaan;

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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.innerpanel.persediaan.Daftarstokminimum_inner_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class DaftarstokminimuminnerController {

    CrudHelper ch = new CrudHelper();
    ArrayList<String> idlist = new ArrayList<>();
    ArrayList<String> lsdata = new ArrayList();
    ArrayList<Integer> lssize = new ArrayList();
    DefaultTableModel dtm = new DefaultTableModel();

    public DaftarstokminimuminnerController(Daftarstokminimum_inner_panel pane) {
        loadheader(pane);
        loaddata(pane);
        loaddatadetail(pane);
        oncarienter(pane);
        onbuttoncari(pane);
    }

    private void loadheader(Daftarstokminimum_inner_panel pane) {
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
            JSONArray jaheader = (JSONArray) joheader.get("stokminimum");
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
            Logger.getLogger(DaftarstokminimuminnerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loaddata(Daftarstokminimum_inner_panel pane) {
        cleardata();
        dtm.getDataVector().removeAllElements();
        dtm.fireTableDataChanged();
        SwingWorker worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                pane.indi.setVisible(true);
                JSONParser jpdata = new JSONParser();
                Object objdata = jpdata.parse(ch.getdatas("dm/daftarstokminimum"));
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

            }

        };
        worker.execute();

    }

    private void loaddatadetailraw(Daftarstokminimum_inner_panel pane) {
        cleardata();
        dtm.getDataVector().removeAllElements();
        dtm.fireTableDataChanged();
        SwingWorker worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                pane.indi.setVisible(true);
                JSONParser jpdata = new JSONParser();
                String param = String.format("cari=%s", pane.tcari.getText());
                Object objdata = jpdata.parse(ch.getdatadetails("dm/caristokminimum", param));
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

            }

        };
        worker.execute();

    }

    private void loaddatadetail(Daftarstokminimum_inner_panel pane) {
        pane.tcari.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loaddatadetailraw(pane);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    private void cleardata() {
        idlist.clear();
        Staticvar.ids = "";
    }

    private void oncarienter(Daftarstokminimum_inner_panel pane) {
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

    private void onbuttoncari(Daftarstokminimum_inner_panel pane) {
        pane.bcari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!pane.tcari.getText().equals("Cari Data")) {
                    loaddatadetailraw(pane);
                }
            }
        });
    }

}
