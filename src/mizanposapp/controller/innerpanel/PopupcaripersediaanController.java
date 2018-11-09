/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel;

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
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.innerpanel.Popupcaripersediaan;

/**
 *
 * @author Minami
 */
public class PopupcaripersediaanController {

    Popupcaripersediaan pane;
    DefaultTableModel dtm = new DefaultTableModel();
    ArrayList<SatuanEntity> ls = new ArrayList();
    Object[] obj = new Object[2];

    public PopupcaripersediaanController(Popupcaripersediaan pane, ArrayList<SatuanEntity> ls) {
        this.pane = pane;
        this.ls = ls;
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
        loaddata();
        oke();
        tutup();
        onfocusbykey();
        callokebyenter();
    }

    private void loaddata() {
        dtm.setRowCount(0);
        dtm.addColumn("ID");
        dtm.addColumn("Nama");
        for (int i = 0; i < ls.size(); i++) {
            obj[0] = ls.get(i).getId();
            obj[1] = ls.get(i).getNama();
            dtm.addRow(obj);
        }
        pane.tabledata.setModel(dtm);
    }

    private void oke() {
        pane.bok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Staticvar.isupdate = true;
                int i = pane.tabledata.getSelectedRow();
                Staticvar.resid = ls.get(i).getId();
                Staticvar.resvalue = String.valueOf(pane.tabledata.getValueAt(i, 0));
                Staticvar.reslabel = String.valueOf(pane.tabledata.getValueAt(i, 1));
                Staticvar.sfilter = "";
                JDialog jd = (JDialog) pane.getRootPane().getParent();
                jd.dispose();
            }
        });

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
