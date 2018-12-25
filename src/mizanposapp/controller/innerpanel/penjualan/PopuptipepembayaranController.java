/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.penjualan;

import mizanposapp.controller.innerpanel.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.innerpanel.persediaan.Popuptipepembayaran;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Minami
 */
public class PopuptipepembayaranController {

    Popuptipepembayaran pane;
    CrudHelper ch = new CrudHelper();
    DefaultTableModel dtm = new DefaultTableModel();
    ArrayList<String> idlist = new ArrayList<>();
    ArrayList<String> lsdata = new ArrayList();
    ArrayList<Integer> lssize = new ArrayList();

    public PopuptipepembayaranController(Popuptipepembayaran pane) {
        this.pane = pane;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JDialog jdin = (JDialog) pane.getRootPane().getParent();
                jdin.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        pane.btutup.doClick();
                    }

                });
            }
        });
        //loaddata();
        oke();
        tutup();
        onfocusbykey();
        callokebyenter();
    }

    /*private void loaddata() {
        cleardata();
        disablebutton();
        dtm.getDataVector().removeAllElements();
        dtm.fireTableDataChanged();
        SwingWorker worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                //pane.indi.setVisible(true);
                JSONParser jpdata = new JSONParser();
                Object objdata = jpdata.parse(ch.getdatas());
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
                //pane.indi.setVisible(false);
                pane.tabledata.setModel(dtm);
                disablebutton();
                pane.tcari.setText("");
                pane.tcari.requestFocus();
            }

        };
        worker.execute();
    }*/
    private void cleardata() {
        idlist.clear();
        Staticvar.ids = "";
    }

    private void disablebutton() {
        pane.bedit.setEnabled(false);
        pane.bhapus.setEnabled(false);
    }

    private void oke() {
        /*pane.bok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Staticvar.isupdate = true;
                int i = pane.tabledata.getSelectedRow();
                Staticvar.resid = ls.get(i).getId();
                Staticvar.resvalue = ls.get(i).getId_pengali();
                Staticvar.resvalueextended = ls.get(i).getQty_pengali();
                Staticvar.reslabel = String.valueOf(pane.tabledata.getValueAt(i, 0));
                Staticvar.sfilter = "";
                JDialog jd = (JDialog) pane.getRootPane().getParent();
                jd.dispose();
            }
        });*/

        MouseAdapter madap = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    pane.bok.doClick();
                }
            }

        };
        pane.tabledata.addMouseListener(madap);
    }

    private void callokebyenter() {
        pane.tabledata.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enter");
        pane.tabledata.getActionMap().put("enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pane.bok.doClick();
            }
        });
    }

    private void tutup() {

        pane.btutup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Staticvar.resid = Staticvar.preid;
                Staticvar.reslabel = Staticvar.prelabel;
                Staticvar.resvalue = Staticvar.prevalue;
                Staticvar.sfilter = "";
                JDialog jd = (JDialog) pane.getRootPane().getParent();
                jd.dispose();
            }
        });

        pane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        pane.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "esc");
        pane.getActionMap().put("esc", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pane.btutup.doClick();
            }
        });

        pane.tabledata.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        pane.tabledata.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "esc");
        pane.tabledata.getActionMap().put("esc", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pane.btutup.doClick();
            }
        });

    }

    private void onfocusbykey() {
        pane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        pane.getInputMap().put(KeyStroke.getKeyStroke("UP"), "up");
        pane.getActionMap().put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pane.tabledata.requestFocus();
                pane.tabledata.changeSelection(0, 0, false, false);
            }
        });

        pane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        pane.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "down");
        pane.getActionMap().put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pane.tabledata.requestFocus();
                pane.tabledata.changeSelection(0, 0, false, false);
            }
        });

        //pane.tabledata.addKeyListener(ka);
    }
}
