
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.penjualan;

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
import mizanposapp.view.innerpanel.penjualan.Daftardatakaryawan_inner_panel;
import mizanposapp.view.innerpanel.penjualan.Daftardatakaryawan_input_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class DaftardatakaryawaninnerController {

    CrudHelper ch = new CrudHelper();
    public static String id;
    ArrayList<String> idlist = new ArrayList<>();
    ArrayList<String> lsdata = new ArrayList();
    ArrayList<Integer> lssize = new ArrayList();
    DefaultTableModel dtm = new DefaultTableModel();

    public DaftardatakaryawaninnerController(Daftardatakaryawan_inner_panel pane) {
        loadheader(pane);
        loaddata(pane);
        loaddatadetail(pane);
        inputdata(pane);
        editdata(pane);
        deletedata(pane);
        updateloaddata(pane);
        selectdata(pane);
        oncarienter(pane);
        onbuttoncari(pane);
    }

    private void loadheader(Daftardatakaryawan_inner_panel pane) {
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
            JSONArray jaheader = (JSONArray) joheader.get("nama");
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
            Logger.getLogger(DaftardatakaryawaninnerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loaddata(Daftardatakaryawan_inner_panel pane) {
        cleardata();
        disablebutton(pane);
        dtm.getDataVector().removeAllElements();
        dtm.fireTableDataChanged();
        SwingWorker worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                pane.indi.setVisible(true);
                JSONParser jpdata = new JSONParser();
                String param = "tipe=2";
                Object objdata = jpdata.parse(ch.getdatadetails("dm/daftarnama", param));
                System.out.println(objdata);
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
                disablebutton(pane);

            }

        };
        worker.execute();

    }

    private void loaddatadetailraw(Daftardatakaryawan_inner_panel pane) {
        cleardata();
        disablebutton(pane);
        dtm.getDataVector().removeAllElements();
        dtm.fireTableDataChanged();
        SwingWorker worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                pane.indi.setVisible(true);
                JSONParser jpdata = new JSONParser();
                String param = String.format("tipe=2&cari=%s", pane.tcari.getText());
                System.out.println(ch.getdatadetails("dm/carinama", param));
                Object objdata = jpdata.parse(ch.getdatadetails("dm/carinama", param));
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
                disablebutton(pane);

            }

        };
        worker.execute();

    }

    private void loaddatadetail(Daftardatakaryawan_inner_panel pane) {
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

    private void selectdata(Daftardatakaryawan_inner_panel pane) {
        pane.tabledata.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    enablebutton(pane);
                    //System.out.println(id);
                }

            }
        });
    }

    private void cleardata() {
        idlist.clear();
        id = "";
    }

    private void editdata(Daftardatakaryawan_inner_panel pane) {
        pane.bedit.addActionListener((ActionEvent e) -> {
            int row = pane.tabledata.getSelectedRow();
            id = idlist.get(row);
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Daftardatakaryawan_input_panel());
            jd.pack();
            jd.setLocationRelativeTo(null);
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setTitle("Edit Data Karyawan");
            jd.setVisible(true);
            if (pane.tcari.getText().equals("Cari Data") || pane.tcari.getText().equals("")) {
                loaddata(pane);
            } else {
                loaddatadetailraw(pane);
            }
        });
    }

    private void inputdata(Daftardatakaryawan_inner_panel pane) {
        pane.btambah.addActionListener((ActionEvent e) -> {
            cleardata();
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Daftardatakaryawan_input_panel());
            jd.pack();
            jd.setLocationRelativeTo(null);
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setTitle("Input Data Karyawan");
            jd.setVisible(true);
            loaddata(pane);
        });
    }

    private void deletedata(Daftardatakaryawan_inner_panel pane) {
        pane.bhapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = pane.tabledata.getSelectedRow();
                System.out.println(idlist.get(row));
                if (JOptionPane.showConfirmDialog(null, "Yakin akan menghapus data ini?",
                        "Konfirmasi", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE) == 0) {
                    String data = String.format("id=%s", idlist.get(row));
                    ch.deletedata("dm/deletedatakaryawan", data);
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
                        if (pane.tcari.getText().equals("Cari Data") || pane.tcari.getText().equals("")) {
                            loaddata(pane);
                        } else {
                            loaddatadetailraw(pane);
                        }
                    }
                }

            }
        });

    }

    private void updateloaddata(Daftardatakaryawan_inner_panel pane) {
        pane.bupdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loaddata(pane);
                pane.tcari.setText("Cari Data");
            }
        });
    }

    private void oncarienter(Daftardatakaryawan_inner_panel pane) {
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

    private void onbuttoncari(Daftardatakaryawan_inner_panel pane) {
        pane.bcari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!pane.tcari.getText().equals("Cari Data")) {
                    loaddatadetailraw(pane);
                }
            }
        });
    }

    private void disablebutton(Daftardatakaryawan_inner_panel pane) {
        pane.bedit.setEnabled(false);
        pane.bhapus.setEnabled(false);
    }

    private void enablebutton(Daftardatakaryawan_inner_panel pane) {
        pane.bedit.setEnabled(true);
        pane.bhapus.setEnabled(true);
    }

}
