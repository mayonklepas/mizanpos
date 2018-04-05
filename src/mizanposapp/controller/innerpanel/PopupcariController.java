/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel;

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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.penjualan.Daftardatakaryawan_input_panel;
import mizanposapp.view.innerpanel.penjualan.Daftardatapelanggan_input_panel;
import mizanposapp.view.innerpanel.persediaan.Daftardatadept_input_panel;
import mizanposapp.view.innerpanel.persediaan.Daftardatasupplier_input_panel;
import mizanposapp.view.innerpanel.persediaan.Daftardatasupplierklasifikasi_input_panel;
import mizanposapp.view.innerpanel.persediaan.Daftargudang_input_panel;
import mizanposapp.view.innerpanel.persediaan.Daftarkelompokbarang_input_panel;
import mizanposapp.view.innerpanel.persediaan.Daftarlokasibarang_input_panel;
import mizanposapp.view.innerpanel.persediaan.Daftarmerekbarang_input_panel;
import mizanposapp.view.innerpanel.persediaan.Daftarsatuanbarang_input_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class PopupcariController {

    CrudHelper ch = new CrudHelper();
    ArrayList<String> idlist = new ArrayList<>();
    ArrayList<String> lsdata = new ArrayList();
    ArrayList<Integer> lssize = new ArrayList();
    DefaultTableModel dtm = new DefaultTableModel();
    Popupcari pane;

    public PopupcariController(Popupcari pane, String tipe, String page, String header) {
        Staticvar.resid = "";
        Staticvar.isupdate = false;
        this.pane = pane;
        pane.lheader.setText(header);
        loadheader(tipe);
        loaddata(page);
        loaddatadetail(page);
        inputdata(tipe, page);
        editdata(tipe, page);
        deletedata(page);
        selectdata();
        oncarienter();
        //selectid();
        oke();
        tutup();

    }

    private void loadheader(String tipe) {
        try {
            pane.tabledata.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
            pane.tabledata.setModel(dtm);
            TableColumnModel tcm = pane.tabledata.getColumnModel();
            JTableHeader thead = pane.tabledata.getTableHeader();
            thead.setFont(new Font("Century Gothic", Font.BOLD, 13));
            pane.tabledata.setRowHeight(25);
            pane.tabledata.setDefaultEditor(Object.class, null);
            String dataheader = ch.getheaderpopups();
            JSONParser jpheader = new JSONParser();
            Object objheader = jpheader.parse(dataheader);
            JSONObject joheader = (JSONObject) objheader;
            JSONArray jaheader = (JSONArray) joheader.get(tipe);
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
            Logger.getLogger(PopupcariController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loaddata(String page) {
        cleardata();
        disablebutton();
        dtm.getDataVector().removeAllElements();
        dtm.fireTableDataChanged();
        SwingWorker worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                //pane.indi.setVisible(true);
                JSONParser jpdata = new JSONParser();
                Object objdata = jpdata.parse(ch.getdatas(page));
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

            }

        };
        worker.execute();

    }

    private void loaddatadetailraw(String page) {
        cleardata();
        disablebutton();
        dtm.getDataVector().removeAllElements();
        dtm.fireTableDataChanged();
        SwingWorker worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                //pane.indi.setVisible(true);
                JSONParser jpdata = new JSONParser();
                String param = String.format("cari=%s", pane.tcari.getText());
                Object objdata = jpdata.parse(ch.getdatadetails(page.replace("daftar", "cari"), param));
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

            }

        };
        worker.execute();

    }

    private void loaddatadetail(String page) {
        pane.tcari.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loaddatadetailraw(page);
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

    private void deletedata(String page) {
        pane.bhapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = pane.tabledata.getSelectedRow();
                System.out.println(idlist.get(row));
                if (JOptionPane.showConfirmDialog(null, "Yakin akan menghapus data ini?",
                        "Konfirmasi", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE) == 0) {
                    String data = String.format("id=%s", idlist.get(row));
                    ch.deletedata(page.replace("daftar", "delete"), data);
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
                            loaddata(page);
                        } else {
                            loaddatadetailraw(page);
                        }
                    }
                }

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

    private void disablebutton() {
        pane.bedit.setEnabled(false);
        pane.bhapus.setEnabled(false);
    }

    private void enablebutton() {
        pane.bedit.setEnabled(true);
        pane.bhapus.setEnabled(true);
    }

    private void inputdata(String tipe, String page) {
        pane.bbaru.addActionListener((ActionEvent e) -> {
            JPanel inpane = new JPanel();
            Staticvar.ids = "";
            switch (tipe) {
                case "nama":
                    if (page.contains("2")) {
                        inpane = new Daftardatakaryawan_input_panel();
                    } else if (page.contains("1")) {
                        inpane = new Daftardatasupplier_input_panel();
                    } else {
                        inpane = new Daftardatapelanggan_input_panel();
                    }
                    break;
                case "department":
                    inpane = new Daftardatadept_input_panel();
                    break;
                case "gudang":
                    inpane = new Daftargudang_input_panel();
                    break;
                case "supplierklasifikasi":
                    inpane = new Daftardatasupplierklasifikasi_input_panel();
                    break;
                case "kelompokbarang":
                    inpane = new Daftarkelompokbarang_input_panel();
                    break;
                case "merek":
                    inpane = new Daftarmerekbarang_input_panel();
                    break;
                case "lokasi":
                    inpane = new Daftarlokasibarang_input_panel();
                    break;
                case "satuan":
                    inpane = new Daftarsatuanbarang_input_panel();
                    break;

            }
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(inpane);
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            loaddata(page);

        });
    }

    private void editdata(String tipe, String page) {
        pane.bedit.addActionListener((ActionEvent e) -> {
            int row = pane.tabledata.getSelectedRow();
            Staticvar.ids = idlist.get(row);
            JPanel inpane = new JPanel();
            switch (tipe) {
                case "nama":
                    if (page.contains("2")) {
                        inpane = new Daftardatakaryawan_input_panel();
                    } else if (page.contains("1")) {
                        inpane = new Daftardatasupplier_input_panel();
                    } else {
                        inpane = new Daftardatapelanggan_input_panel();
                    }
                    break;
                case "department":
                    inpane = new Daftardatadept_input_panel();
                    break;
                case "gudang":
                    inpane = new Daftargudang_input_panel();
                    break;
                case "supplierklasifikasi":
                    inpane = new Daftardatasupplierklasifikasi_input_panel();
                    break;
                case "kelompokbarang":
                    inpane = new Daftarkelompokbarang_input_panel();
                    break;
                case "merek":
                    inpane = new Daftarmerekbarang_input_panel();
                    break;
                case "lokasi":
                    inpane = new Daftarlokasibarang_input_panel();
                    break;
                case "satuan":
                    inpane = new Daftarsatuanbarang_input_panel();
                    break;

            }
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(inpane);
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            loaddata(page);
        });
    }

    private void selectid() {
        pane.tabledata.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = pane.tabledata.getSelectedRow();
                if (i >= 0) {
                    Staticvar.resid = idlist.get(i);
                    Staticvar.reslabel = String.valueOf(pane.tabledata.getValueAt(i, 1));
                }
            }

        });
    }

    private void oke() {
        pane.bok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Staticvar.isupdate = true;
                int i = pane.tabledata.getSelectedRow();
                Staticvar.resid = idlist.get(i);
                Staticvar.reslabel = String.valueOf(pane.tabledata.getValueAt(i, 1));
                JDialog jd = (JDialog) pane.getRootPane().getParent();
                jd.dispose();
            }
        });
    }

    private void tutup() {
        pane.btutup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Staticvar.isupdate = false;
                JDialog jd = (JDialog) pane.getRootPane().getParent();
                jd.dispose();
            }
        });
    }

}