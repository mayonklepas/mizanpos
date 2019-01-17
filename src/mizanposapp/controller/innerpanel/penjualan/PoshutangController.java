/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.penjualan;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import mizanposapp.helper.FuncHelper;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Globalsession;
import mizanposapp.helper.Staticvar;
import mizanposapp.helper.numtoword;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Errorpanel;
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.penjualan.Pos_hutang_pane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class PoshutangController {

    Pos_hutang_pane pane;
    NumberFormat nf = NumberFormat.getInstance();
    CrudHelper ch = new CrudHelper();
    ArrayList<String> idlist = new ArrayList<>();
    ArrayList<String> lsdata = new ArrayList();
    ArrayList<Integer> lssize = new ArrayList();
    DefaultTableModel dtmtabeldata = new DefaultTableModel();
    Object[] rowtabledata = new Object[5];
    String valakun_penerimaan = "";
    String valpelanggan = "";
    SwingWorker worker;
    public static String id_pelanggan = "";
    public static String nama_pelanggan = "";
    public static double jumlah_piutang = 0;
    String no_urut = "0";
    String valdept = "";

    public PoshutangController(Pos_hutang_pane pane) {
        this.pane = pane;
        loadsession();
        customtable();
        loadheader();
        loaddata();
        simpandata();
        tutup();
        cariakunpemesanan();
        caripelanggan();
        gantitanggal();
        caridepartment();
        setterbilang();
    }

    private void gantitanggal() {
        pane.dtanggal.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    new FuncHelper().insertnogagal("2", pane.dtanggal.getDate(), valdept, no_urut);
                    HashMap hm = new FuncHelper().getkodetransaksi("2", pane.dtanggal.getDate(), valdept);
                    pane.ednoref.setText(String.valueOf(hm.get("kode_transaksi")));
                    no_urut = String.valueOf(hm.get("no_urut"));
                }
            }
        });
    }

    private void customtable() {
        pane.tabledata.setDefaultEditor(Object.class, null);
        pane.tabledata.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
    }

    private void loadsession() {
        valdept = Globalsession.DEFAULT_DEPT_ID;
        pane.eddept.setText(Globalsession.DEFAULT_DEPT_NAME);
        pane.dtanggal.setDate(new Date());
        valakun_penerimaan = Globalsession.AKUNPIUTANGUSAHA;
        pane.edakun_pemesanan.setText(Globalsession.NAMAAKUNPIUTANGUSAHA);
        pane.edterima_dari.setText(nama_pelanggan);
        valpelanggan = id_pelanggan;
        pane.ltotal_piutang.setText(nf.format(jumlah_piutang));
        HashMap hm = new FuncHelper().getkodetransaksi("43", pane.dtanggal.getDate(), valdept);
        pane.ednoref.setText(String.valueOf(hm.get("kode_transaksi")));
        no_urut = String.valueOf(hm.get("no_urut"));

    }

    private void loadheader() {
        try {
            pane.tabledata.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
            pane.tabledata.setModel(dtmtabeldata);
            TableColumnModel tcm = pane.tabledata.getColumnModel();
            pane.tabledata.setDefaultEditor(Object.class, null);
            String dataheader = ch.getheaderpopups();
            JSONParser jpheader = new JSONParser();
            Object objheader = jpheader.parse(dataheader);
            JSONObject joheader = (JSONObject) objheader;
            JSONArray jaheader = (JSONArray) joheader.get("poshutang");
            for (int i = 0; i < jaheader.size(); i++) {
                JSONObject jodata = (JSONObject) jaheader.get(i);
                JSONArray jaaray = (JSONArray) jodata.get("key");
                if (jaaray.get(2).equals("1")) {
                    dtmtabeldata.addColumn(jaaray.get(1));
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
            Logger.getLogger(DaftarpiutangrincianinnerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loaddata() {
        dtmtabeldata.getDataVector().removeAllElements();
        dtmtabeldata.fireTableDataChanged();
        worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                JSONParser jpdata = new JSONParser();
                String param = String.format("id=%s", valpelanggan);
                Object objdata = jpdata.parse(ch.getdatadetails("daftarpiutangperpelanggan", param));
                JSONArray jadata = (JSONArray) objdata;
                dtmtabeldata.setRowCount(0);
                for (int i = 0; i < jadata.size(); i++) {
                    JSONObject joindata = (JSONObject) jadata.get(i);
                    Object[] objindata = new Object[lsdata.size()];
                    idlist.add(String.valueOf(joindata.get("id")));
                    for (int j = 0; j < objindata.length; j++) {
                        objindata[j] = joindata.get(lsdata.get(j));
                    }
                    dtmtabeldata.addRow(objindata);
                }
                return null;
            }

            @Override
            protected void done() {
                pane.tabledata.setModel(dtmtabeldata);
                pane.tabledata.requestFocus();
                pane.tabledata.changeSelection(0, 0, false, false);
            }

        };
        worker.execute();
    }

    private void simpandata() {
        pane.bsimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String data = "no_ref=" + pane.ednoref.getText() + "&"
                     + "tanggal=" + new SimpleDateFormat("yyyy-MM-dd").format(pane.dtanggal.getDate()) + "&"
                     + "id_dept=" + valdept + "&"
                     + "id_cards=" + valpelanggan + "&"
                     + "akun_penerimaan=" + valakun_penerimaan + "&"
                     + "jumlah=" + pane.edjumlah_bayar.getText() + "&";
                ch.insertdata("insertpembayaranpiutangtotal", data);
                if (Staticvar.getresult.equals("berhasil")) {
                    JOptionPane.showMessageDialog(pane, "Berhasil Disimpan");
                    JDialog jd = (JDialog) pane.getRootPane().getParent();
                    jd.dispose();
                }

            }
        });
    }

    private void setterbilang() {
        pane.edjumlah_bayar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                double jumlah_bayar = FuncHelper.ToDouble(pane.edjumlah_bayar.getText());
                pane.lterbilang.setText(numtoword.TerbilangIndonesia(jumlah_bayar));
            }

        });
    }

    private void tutup() {
        pane.bbatal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FuncHelper().insertnogagal("43", pane.dtanggal.getDate(), valdept, no_urut);
                JDialog jd = (JDialog) pane.getRootPane().getParent();
                jd.dispose();
            }
        });
    }

    private void rawgetidakun(String prevlabel, String previd) {
        Staticvar.sfilter = "";
        Staticvar.preid = previd;
        Staticvar.prelabel = prevlabel;
        JDialog jd = new JDialog(new Mainmenu());
        jd.add(new Popupcari("akun", "popupdaftarakun", "Daftar Akun"));
        jd.pack();
        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        jd.setLocationRelativeTo(null);
        jd.setVisible(true);
        jd.toFront();
    }

    private void cariakunpemesanan() {
        pane.bcari_akun_pemesanan.addActionListener((ActionEvent e) -> {
            rawgetidakun(pane.edakun_pemesanan.getText(), valakun_penerimaan);
            if (!Staticvar.resid.equals(valakun_penerimaan)) {
                valakun_penerimaan = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.edakun_pemesanan.setText(val);
            }
        });

    }

    private void caripelanggan() {
        pane.bcari_terima_dari.addActionListener((ActionEvent e) -> {
            Staticvar.sfilter = "";
            Staticvar.preid = valpelanggan;
            Staticvar.prelabel = pane.edterima_dari.getText();
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("nama", "popupdaftarnama?tipe=0", "Daftar Pelanggan"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valpelanggan = Staticvar.resid;
            pane.edterima_dari.setText(Staticvar.reslabel);
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
            HashMap hm = new FuncHelper().getkodetransaksi("43", pane.dtanggal.getDate(), valdept);
            pane.ednoref.setText(String.valueOf(hm.get("kode_transaksi")));
            no_urut = String.valueOf(hm.get("no_urut"));
        });

    }

}
