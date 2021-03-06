/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.penjualan;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowStateListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.AbstractAction;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import static mizanposapp.controller.innerpanel.penjualan.BayarposController.istunai;
import mizanposapp.helper.FuncHelper;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Globalsession;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Errorpanel;
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.Popupcaripending;
import mizanposapp.view.innerpanel.penjualan.Insertpos_pane;
import mizanposapp.view.innerpanel.penjualan.Bayarpos_pane;
import mizanposapp.view.innerpanel.penjualan.Pos_hutang_pane;
import mizanposapp.view.innerpanel.penjualan.Posframe;
import mizanposapp.view.innerpanel.persediaan.Daftarpersediaan_input_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class PosframeController {

    String DefaultCari = "Barcode Scanner Atau Kode Barang [F2]";
    String id;
    CrudHelper ch = new CrudHelper();
    Posframe pane;
    String valpelanggan = "", valgudang = "", valdept = "", valsalesman = "", valshipvia = "", valtop = "",
         valakun_penjualan = "", valakun_ongkir = "", valakun_diskon = "",
         valakun_uang_muka = "", valgolongan = "", validtransaksi = "";
    int valcheck = 0;
    int tipe_bayar = 0, tipe_jual = 0, status_selesai = 0;
    DefaultTableModel dtmtabeldata = new DefaultTableModel();
    Object[] rowtabledata = new Object[12];
    ArrayList<Entitytabledata> tabeldatalist = new ArrayList<>();
    int col = 0;
    //int istunai = 0;
    //int isjasa = 0;
    //int row = 0;
    NumberFormat nf = NumberFormat.getInstance();
    double total_penjualan_all = 0;
    double total_pajak = 0;
    double total_service = 0;
    double subtotal = 0;
    String no_urut = "0";
    JTextField jt2;
    JTextField jt4;
    JTextField jt6;
    JTextField jt7;
    ArrayList<Integer> lshide = new ArrayList<>();
    ArrayList<Integer> lsoldhide = new ArrayList<>();
    ArrayList<Integer> lsresize = new ArrayList<>();
    ArrayList<Integer> lsoldsize = new ArrayList<>();
    private boolean ischangevalue = false;
    static String oldvalue = "";
    ArrayList<Integer> lsvisiblecolom = new ArrayList<>();
    ArrayList<String> listheadername = new ArrayList<>();
    static boolean sudahterpanggil = false;
    static boolean sudah_jangan_set_lagi_kau_membuat_semua_kacau = false;
    String kode = "kode";
    String nama = "nama";
    String jumlah = "jumlah";
    String satuan = "satuan";
    String harga_beli = "harga_beli";
    String harga_jual = "harga_jual";
    String diskon_persen = "disc_persen";
    String diskon_nominal = "disc_rp";
    String pajak = "pajak";
    String gudang = "gudang";
    String keterangan = "keterangan";
    String total = "total";
    boolean panggil = true;
    double jumlah_piutang = 0;
    JPopupMenu pop;

    KeyEventDispatcher keydis = new KeyEventDispatcher() {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == KeyEvent.KEY_RELEASED) {
                if (pane.edbarcode.isFocusOwner()) {
                    if ((e.getKeyCode() == KeyEvent.VK_DELETE) || (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
                        if (pane.edbarcode.getText().equals("")) {
                            pane.edbarcode.setText(DefaultCari);
                            pane.edbarcode.select(0, 0);
                            pane.edbarcode.setForeground(Color.GRAY);
                        }
                    }
                }
            }
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                if (pane.edbarcode.isFocusOwner()) {
                    if (pane.edbarcode.getText().equals(DefaultCari)) {
                        pane.edbarcode.setText("");
                        pane.edbarcode.setForeground(Color.BLACK);
                    } else if (pane.edbarcode.getText().equals("")) {
                        pane.edbarcode.setText(DefaultCari);
                        pane.edbarcode.setForeground(Color.GRAY);
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_F2) {
                    if (!pane.edbarcode.isFocusOwner()) {
                        pane.tabledata.clearSelection();
                        pane.edbarcode.requestFocus();
                    } else {
                        if (Globalsession.POS_HarusMenggunakanSalesman.equals("1")) {

                            if (pane.edsalesman.getText().equals("")) {
                                FuncHelper.info("Proses Ditolak", "Anda Harus Mengisi Salesman !!");
                                pane.bcari_salesman.doClick();
                            } else {
                                additemtotable();
                            }
                        } else {
                            additemtotable();
                        }

                    }

                } else if (e.getKeyCode() == KeyEvent.VK_F8) {
                    carisatuan();
                } else if (e.getKeyCode() == KeyEvent.VK_F9) {
                    pane.bsimpan.doClick();
                } else if (e.getKeyCode() == KeyEvent.VK_F10) {
                    pane.bpending.doClick();
                } else if (e.getKeyCode() == KeyEvent.VK_F11) {
                    pane.brecall.doClick();
                } else if (e.getKeyCode() == KeyEvent.VK_F12) {
                    pane.bbatal.doClick();
                } else if (e.getKeyCode() == KeyEvent.VK_F5) {
                    pane.bupdate.doClick();
                } else if (e.getKeyCode() == KeyEvent.VK_F4) {
                    pane.bcari_pelanggan.doClick();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    pane.btutup.doClick();
                } else if (e.getKeyCode() == KeyEvent.VK_INSERT) {
                    KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(this);
                    int crow = pane.tabledata.getSelectedRow();
                    if (pane.tabledata.getSelectionModel().isSelectionEmpty()) {
                        crow = pane.tabledata.getRowCount() - 1;
                    }
                    InsertposController.id_barang = tabeldatalist.get(crow).getId_barang();
                    InsertposController.jumlah = tabeldatalist.get(crow).getJumlah();
                    InsertposController.satuan = tabeldatalist.get(crow).getNama_satuan();
                    InsertposController.id_satuan_pengali = tabeldatalist.get(crow).getId_satuan_pengali();
                    InsertposController.qty_satuan_pengali = tabeldatalist.get(crow).getIsi_satuan();
                    InsertposController.id_satuan = tabeldatalist.get(crow).getId_satuan();
                    InsertposController.keterangan = tabeldatalist.get(crow).getKeterangan();
                    InsertposController.diskon_persen = tabeldatalist.get(crow).getDiskon_persen();
                    InsertposController.diskon_nominal = tabeldatalist.get(crow).getDiskon_nominal();
                    InsertposController.harga_persatuan = tabeldatalist.get(crow).getHarga_jual();
                    InsertposController.golongan = valgolongan;
                    if (pane.ckdiskon.isSelected()) {
                        InsertposController.status_diskon_persen = true;
                    } else {
                        InsertposController.status_diskon_persen = false;
                    }

                    JDialog jd = new JDialog(new Mainmenu());
                    jd.add(new Insertpos_pane());
                    jd.pack();
                    jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                    jd.setLocationRelativeTo(null);
                    jd.setVisible(true);
                    jd.toFront();
                    if (InsertposController.status_update == true) {
                        tabeldatalist.get(crow).setJumlah(InsertposController.jumlah);
                        tabeldatalist.get(crow).setNama_satuan(InsertposController.satuan);
                        tabeldatalist.get(crow).setId_satuan(InsertposController.id_satuan);
                        tabeldatalist.get(crow).setId_satuan_pengali(InsertposController.id_satuan_pengali);
                        tabeldatalist.get(crow).setIsi_satuan(InsertposController.qty_satuan_pengali);
                        tabeldatalist.get(crow).setKeterangan(InsertposController.keterangan);
                        tabeldatalist.get(crow).setDiskon_persen(InsertposController.diskon_persen);
                        tabeldatalist.get(crow).setDiskon_nominal(InsertposController.diskon_nominal);
                        tabeldatalist.get(crow).setHarga_jual(InsertposController.harga_persatuan);
                        pane.tabledata.setValueAt(String.valueOf(InsertposController.jumlah), crow, gx(jumlah));
                        pane.tabledata.setValueAt(String.valueOf(InsertposController.satuan), crow, gx(satuan));
                        pane.tabledata.setValueAt(String.valueOf(InsertposController.diskon_persen), crow, gx(diskon_persen));
                        pane.tabledata.setValueAt(String.valueOf(InsertposController.diskon_nominal), crow, gx(diskon_nominal));
                        pane.tabledata.setValueAt(String.valueOf(InsertposController.harga_persatuan), crow, gx(harga_jual));
                        pane.tabledata.setValueAt(String.valueOf(InsertposController.keterangan), crow, gx(keterangan));
                        kalkulasitotalperrow(crow);
                    }

                    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
                } else if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    int row = pane.tabledata.getSelectedRow();
                    if (pane.tabledata.getSelectionModel().isSelectionEmpty()) {
                        row = pane.tabledata.getRowCount() - 1;
                    }
                    FuncHelper.konfir("Yakin akan menghapus data ini?",
                         "Data yang akan anda hapus adalah " + pane.tabledata.getValueAt(row, gx(kode)) + " - "
                         + pane.tabledata.getValueAt(row, gx(nama)) + " Tekan Ya untuk menghapus", "Ya");
                    if (Staticvar.isupdate == true) {
                        Staticvar.isupdate = false;
                        Runnable rn = new Runnable() {
                            @Override
                            public void run() {
                                int row = pane.tabledata.getSelectedRow();
                                if (pane.tabledata.getSelectionModel().isSelectionEmpty()) {
                                    row = pane.tabledata.getRowCount() - 1;
                                }
                                tabeldatalist.remove(row);
                                dtmtabeldata.removeRow(row);
                                pane.edbarcode.requestFocus();
                                //pane.tabledata.requestFocus();
                                //pane.tabledata.changeSelection(0, 0, false, false);
                                kalkulasitotal();
                            }
                        };
                        SwingUtilities.invokeLater(rn);
                    }
                }
            }
            return false;
        }
    };

    public PosframeController() {
    }

    public PosframeController(Posframe pane) {
        this.pane = pane;
        skinning();
        loadsession();
        loaddata();
        simpandata();
        checkandcombocontrol();
        gantitanggal();
        setPlaceHolder();
        caripelanggan();
        caridepartment();
        carisalesman();
        setpopup();
        keyfunction();
        carigudang();
        tutup();
        carikasmasuklain();
        carikaskeluarlain();
        showhutang();
        recallpending();
        bayar();
        batal();

    }

    private void setpopup() {
        pop = new JPopupMenu();
        JMenuItem editpersediaan = new JMenuItem("Edit Data Barang");
        pop.add(editpersediaan);

        editpersediaan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keydis);
                int row = pane.tabledata.getSelectedRow();
                Staticvar.ids = tabeldatalist.get(row).getId_barang();
                JDialog jd = new JDialog(new Mainmenu());
                jd.add(new Daftarpersediaan_input_panel());
                jd.pack();
                jd.setLocationRelativeTo(null);
                jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                jd.setTitle("Edit Data Barang");
                jd.setVisible(true);
                if (Staticvar.isupdate == true) {
                    double callhargajual = gethargajual(
                         tabeldatalist.get(row).getId_barang(),
                         tabeldatalist.get(row).getId_satuan(),
                         tabeldatalist.get(row).getJumlah());
                    pane.tabledata.setValueAt(nf.format(callhargajual), row, 5);
                    tabeldatalist.get(row).setHarga_jual(nf.format(callhargajual));
                    kalkulasitotalperrow(row);
                }

                Staticvar.isupdate = false;

                KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keydis);
            }
        });

        pane.tabledata.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!pane.tabledata.getSelectionModel().isSelectionEmpty()) {
                    if (e.isPopupTrigger()) {
                        pop.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (!pane.tabledata.getSelectionModel().isSelectionEmpty()) {
                    if (e.isPopupTrigger()) {
                        pop.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }

        });

    }

    private void loadsession() {
        valakun_penjualan = Globalsession.AKUNPENJUALANTUNAI;
        valakun_uang_muka = Globalsession.AKUNUANGMUKAPENJUALAN;
        valakun_diskon = Globalsession.AKUNDISKONPENJUALAN;
        valakun_ongkir = Globalsession.AKUNONGKOSKIRIMPENJUALAN;

    }

    private void changekreditortunai() {

    }

    private void gantitanggal() {
        pane.dtanggal.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    Date zaman_old = (Date) evt.getOldValue();
                    Date zaman_now = (Date) evt.getNewValue();
                    new FuncHelper().insertnogagal("2", zaman_old, valdept, no_urut);
                    HashMap hm = new FuncHelper().getkodetransaksi("2", zaman_now, valdept);
                    pane.edno_transaksi.setText(String.valueOf(hm.get("no_transaksi")));
                    no_urut = String.valueOf(hm.get("no_urut"));
                }
            }
        });
    }

    private void skinning() {
        pane.dtanggal.setDateFormatString("dd MMMM yyyy");
        pane.dtanggal.setDate(new Date());
        pane.dtanggal.getDateEditor().setEnabled(false);
    }

    private void customtable() {
        pane.tabledata.setDefaultEditor(Object.class, null);
        pane.tabledata.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

    }

    private void setPlaceHolder() {
        pane.edbarcode.setForeground(Color.GRAY);
        pane.edbarcode.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (pane.edbarcode.getText().equals(DefaultCari)) {
                    //pane.edbarcode.setSelectionStart(0);
                    pane.edbarcode.select(0, 0);
                    pane.edbarcode.setForeground(Color.GRAY);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (pane.edbarcode.getText().isEmpty()) {
                    pane.edbarcode.setForeground(Color.GRAY);
                    pane.edbarcode.setText("Barcode Scanner Atau Kode Barang [F2]");
                }
            }
        });
    }

    private void checkandcombocontrol() {
        pane.ckdiskon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox cb = (JCheckBox) e.getSource();
                for (int i = 0; i < pane.tabledata.getRowCount(); i++) {
                    kalkulasitotalperrow(i);
                }
                if (cb.isSelected()) {
                    hidetable(gx(diskon_nominal));
                    showtable(gx(diskon_persen));
                    valcheck = 0;
                    valakun_penjualan = Globalsession.AKUNPENJUALANTUNAI;
                } else {
                    hidetable(gx(diskon_persen));
                    showtable(gx(diskon_nominal));
                    valcheck = 1;
                    valakun_penjualan = Globalsession.AKUNPIUTANGUSAHA;
                }

            }
        });

    }

    private void loadheader() {
        try {
            pane.tabledata.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            pane.tabledata.setModel(dtmtabeldata);
            String dataheader = ch.getheaders();
            JSONParser jpheader = new JSONParser();
            Object objheader = jpheader.parse(dataheader);
            JSONObject joheader = (JSONObject) objheader;
            JSONArray jaheader = (JSONArray) joheader.get("inputorderpenjualan");
            //perulangan mengambil header
            for (int i = 0; i < jaheader.size(); i++) {
                JSONObject jodata = (JSONObject) jaheader.get(i);
                JSONArray jaaray = (JSONArray) jodata.get("key");
                dtmtabeldata.addColumn(jaaray.get(1));
                listheadername.add(String.valueOf(jaaray.get(0)));
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
            Logger.getLogger(PosframeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void setheader() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        TableColumnModel tcm = pane.tabledata.getColumnModel();

        double lebar = d.getWidth() - Staticvar.lebarPanelMenu;;
        double lebarAll = 0;

        for (int i = 0; i < lshide.size(); i++) {
            if (lshide.get(i) == 0) {
                hidetable(i);
            }
        }

        for (int i = 0; i < lsresize.size(); i++) {

            if (cekcolomnol(i)) {
                continue;
            }

            int setsize = lsresize.get(i);
            lebarAll = lebarAll + setsize;
        }

        double pembagi = lebar / lebarAll;
        double lebarAllNew = 0;
        for (int i = 0; i < lsresize.size() - 1; i++) {
            int setsize = lsresize.get(i);
            if (i != lsresize.size() - 1) {
                int wi = FuncHelper.ToInt(pembagi * setsize);
                tcm.getColumn(i).setMinWidth(wi);
                tcm.getColumn(i).setWidth(wi);
                tcm.getColumn(i).setMaxWidth(wi);

                lebarAllNew = lebarAllNew + wi;
            } else {
                int wi = FuncHelper.ToInt(lebar - lebarAllNew);
                tcm.getColumn(i).setMinWidth(wi);
                tcm.getColumn(i).setMaxWidth(wi);
            }
        }
    }

    private void loaddata() {
        customtable();
        loadheader();
        try {

            status_selesai = 0;
            tipe_jual = 0;
            pane.dtanggal.setDate(new Date());
            valpelanggan = "";
            pane.edno_transaksi.setText("");
            pane.eddept.setText(Globalsession.Setting_DeptDefaultnama);
            pane.edketerangan.setText("Penjualan POS");
            pane.ckjenisbayar.setSelected(true);
            valdept = Globalsession.Setting_DeptDefault;
            valgudang = Globalsession.Setting_GudangDefault;
            pane.edgudang.setText(Globalsession.Setting_GudangDefaultnama);
            pane.edsalesman.setText("");
            valsalesman = "";
            valshipvia = "";
            valtop = "";
            pane.edpelanggan.setText(Globalsession.Penjualan_PelangganUmumnama);
            valpelanggan = Globalsession.Penjualan_PelangganUmum;
            pane.lsubtotal.setText("0");
            pane.ltotal_pajak.setText("0");
            pane.ltotal_service.setText("0");
            pane.ltotal_penjualan.setText("0");
            if (Globalsession.DEFAULT_DISKON_DALAM.equals("0")
                 || Globalsession.DEFAULT_DISKON_DALAM.equals("")
                 || Globalsession.DEFAULT_DISKON_DALAM.equals("NULL")
                 || Globalsession.DEFAULT_DISKON_DALAM.equals("null")) {
                pane.ckdiskon.setSelected(true);
                valcheck = 0;
            } else {
                pane.ckdiskon.setSelected(false);
                valcheck = 1;
            }
            if (pane.ckdiskon.isSelected()) {
                hidetable(gx(diskon_nominal));
                showtable(gx(diskon_persen));
            } else {
                showtable(gx(diskon_persen));
                hidetable(gx(diskon_nominal));
            }

            HashMap hm = new FuncHelper().getkodetransaksi("2", pane.dtanggal.getDate(), valdept);
            pane.edno_transaksi.setText(String.valueOf(hm.get("no_transaksi")));
            no_urut = String.valueOf(hm.get("no_urut"));

        } catch (Exception ex) {
            Logger.getLogger(PosframeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void simpandata() {
        pane.bpending.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Staticvar.isupdate = true;

                FuncHelper.konfir("Apakah anda ingin melakukan pending transaksi ? ",
                     "Data Akan disimpan sebagai transaksi pending ", "Ya");

                if (Staticvar.isupdate == true) {
                    Staticvar.isupdate = false;
                    if (tabeldatalist.size() == 0) {
                        JOptionPane.showMessageDialog(null, "Table Tidak Boleh Kosong", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                        FuncHelper.info("Proses Ditolak", "Table Tidak Boleh Kosong");
                    } else {
                        int tahunbulan = Integer.parseInt(new SimpleDateFormat("yyyyM").format(pane.dtanggal.getDate()));
                        int periodetahunnulan = Integer.parseInt(Globalsession.periode_year + Globalsession.periode_month);
                        if (tahunbulan > periodetahunnulan) {
                            FuncHelper.konfir("Apakah anda ingin melanjutkan transaksi ?",
                                 "Tanggal transaksi anda berada setelah periode akuntansi", "Ya");

                            if (Staticvar.isupdate == true) {
                                Staticvar.isupdate = false;
                                rawsimpan();
                            }
                        } else if (tahunbulan < periodetahunnulan) {
                            JDialog jd = new JDialog(new Mainmenu());
                            Errorpanel ep = new Errorpanel();
                            ep.ederror.setText("Tanggal transaksi sebelum periode akuntansi. \n"
                                 + "Anda tidak dapat memasukan, mengedit,menghapus transaksi sebelum periode. \n"
                                 + "Untuk dapat memasukan atau mengedit transaksi, silahkan merubah periode akuntansi");
                            jd.add(ep);
                            jd.pack();
                            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                            jd.setLocationRelativeTo(null);
                            jd.setVisible(true);
                            jd.toFront();
                        } else {
                            rawsimpan();
                        }
                    }
                }

            }
        });
    }

    private void bayar() {
        pane.bsimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pane.edbarcode.getText().equals("") || pane.edbarcode.equals("Barcode Scanner Atau Kode Barang [F2]")) {
                    if (pane.tabledata.getRowCount() == 0) {
                        FuncHelper.info("Proses Ditolak", "Tabel tidak boleh kosong");
                    } else {
                        KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keydis);
                        Staticvar.isupdate = false;
                        BayarposController.validtransaksi = validtransaksi;
                        BayarposController.sub_total = subtotal;
                        BayarposController.total_pajak = total_pajak;
                        BayarposController.total_service = total_service;
                        BayarposController.valpelanggan = valpelanggan;
                        BayarposController.valgudang = valgudang;
                        BayarposController.valdept = valdept;
                        BayarposController.valsalesman = valsalesman;
                        BayarposController.valshipvia = valshipvia;
                        BayarposController.valtop = valtop;
                        BayarposController.valakun_ongkir = valakun_ongkir;
                        BayarposController.valakun_diskon = valakun_diskon;
                        BayarposController.valakun_uang_muka = valakun_uang_muka;
                        BayarposController.valgolongan = valgolongan;
                        BayarposController.tabeldatalist = tabeldatalist;
                        BayarposController.tanggal = pane.dtanggal.getDate();
                        BayarposController.no_transaksi = pane.edno_transaksi.getText();
                        BayarposController.keterangan = pane.edketerangan.getText();
                        BayarposController.kirimtextpenjualan = kirimtexpenjualan();
                        BayarposController.jumlah_piutang = jumlah_piutang;
                        if (pane.ckdiskon.isSelected()) {
                            BayarposController.ispersen = true;

                        } else {
                            BayarposController.ispersen = false;

                        }

                        if (pane.ckjenisbayar.isSelected()) {
                            BayarposController.istunai = true;
                            BayarposController.valakun_penjualan = valakun_penjualan;
                        } else {
                            BayarposController.istunai = false;
                            BayarposController.valakun_penjualan = Globalsession.AKUNPIUTANGUSAHA;
                        }
                        JDialog jd = new JDialog(new Mainmenu());
                        jd.add(new Bayarpos_pane());
                        jd.pack();
                        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        jd.setLocationRelativeTo(null);
                        jd.setVisible(true);
                        jd.toFront();
                        int rowcount = pane.tabledata.getRowCount();
                        if (Staticvar.isupdate == true) {
                            for (int i = 0; i < rowcount; i++) {
                                tabeldatalist.remove(0);
                                dtmtabeldata.removeRow(0);
                            }
                            kalkulasitotal();
                            HashMap hm = new FuncHelper().getkodetransaksi("2", pane.dtanggal.getDate(), valdept);
                            pane.edno_transaksi.setText(String.valueOf(hm.get("no_transaksi")));
                            no_urut = String.valueOf(hm.get("no_urut"));
                        }
                        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keydis);
                        String param = String.format("id=%s", valpelanggan);
                        jumlah_piutang = Double.parseDouble(ch.getdatadetails("getsaldopiutang", param));
                        pane.ltotalpiutang.setText(nf.format(jumlah_piutang));
                    }
                } else {
                    if (Globalsession.POS_HarusMenggunakanSalesman.equals("1")) {
                        if (pane.edsalesman.getText().equals("")) {
                            FuncHelper.info("Proses Ditolak", "Anda Harus Mengisi Salesman !!");
                            pane.bcari_salesman.doClick();

                        } else {
                            additemtotable();
                        }
                    } else {
                        additemtotable();
                    }
                }
            }
        });
    }

    private void recallpending() {
        pane.brecall.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keydis);
                JDialog jd = new JDialog(new Mainmenu());
                jd.add(new Popupcaripending());
                jd.pack();
                jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                jd.setLocationRelativeTo(null);
                jd.setVisible(true);
                jd.toFront();
                validtransaksi = Staticvar.resid;

                new FuncHelper().insertnogagal("2", pane.dtanggal.getDate(), valdept, no_urut);

                if (Staticvar.preid != validtransaksi) {
                    if (!validtransaksi.equals("") || !validtransaksi.equals("null")) {
                        try {
                            tabeldatalist.clear();
                            int rowcount = pane.tabledata.getRowCount();
                            for (int i = 0; i < rowcount; i++) {
                                dtmtabeldata.removeRow(0);
                            }
                            dtmtabeldata.setRowCount(0);
                            JSONParser jpdata = new JSONParser();
                            String param = String.format("id=%s", validtransaksi);
                            Object rawobjdata = jpdata.parse(ch.getdatadetails("datapendingpenjualan", param));
                            JSONObject jsonobjdata = (JSONObject) rawobjdata;

                            Object objgenjur = jsonobjdata.get("genjur");
                            JSONArray jagenjur = (JSONArray) objgenjur;
                            for (int i = 0; i < jagenjur.size(); i++) {
                                JSONObject joingenjur = (JSONObject) jagenjur.get(i);
                                valdept = String.valueOf(joingenjur.get("id_dept"));
                                pane.eddept.setText(String.valueOf(joingenjur.get("nama_dept")));
                                pane.edno_transaksi.setText(String.valueOf(joingenjur.get("noref")));
                                pane.edketerangan.setText(String.valueOf(joingenjur.get("keterangan")));

                            }

                            Object objpenjualan = jsonobjdata.get("penjualan");
                            JSONArray japenjualan = (JSONArray) objpenjualan;
                            for (int i = 0; i < japenjualan.size(); i++) {
                                JSONObject joinpenjualan = (JSONObject) japenjualan.get(i);
                                valgudang = String.valueOf(joinpenjualan.get("id_gudang"));
                                pane.edgudang.setText(String.valueOf(joinpenjualan.get("nama_gudang")));
                                valpelanggan = String.valueOf(joinpenjualan.get("id_pelanggan"));
                                pane.edpelanggan.setText(String.valueOf(joinpenjualan.get("nama_pelanggan")));

                                valcheck = FuncHelper.ToInt(joinpenjualan.get("diskon_dalam"));
                                if (valcheck == 0) {
                                    pane.ckdiskon.setSelected(true);
                                } else {
                                    pane.ckdiskon.setSelected(false);
                                }
                                tipe_jual = FuncHelper.ToInt(joinpenjualan.get("tipe_penjualan"));
                                if (tipe_jual == 0) {
                                    pane.ckjenisbayar.setSelected(true);
                                    valakun_penjualan = Globalsession.AKUNPENJUALANTUNAI;
                                } else {
                                    pane.ckjenisbayar.setSelected(false);
                                    valakun_penjualan = Globalsession.AKUNPIUTANGUSAHA;
                                }

                            }

                            Object objtabeldata = jsonobjdata.get("penjualan_detail");
                            JSONArray jatabledata = (JSONArray) objtabeldata;
                            for (int i = 0; i < jatabledata.size(); i++) {
                                JSONObject jointabeldata = (JSONObject) jatabledata.get(i);
                                String id_barang = String.valueOf(jointabeldata.get("id_inv"));
                                String kode_barang = String.valueOf(jointabeldata.get("kode_inv"));
                                String nama_barang = String.valueOf(jointabeldata.get("nama_inv"));
                                String jumlah = String.valueOf(jointabeldata.get("qty"));
                                String id_satuan = String.valueOf(jointabeldata.get("id_satuan"));
                                String nama_satuan = String.valueOf(jointabeldata.get("nama_satuan"));
                                String isi_satuan = String.valueOf(jointabeldata.get("qty_satuan_pengali"));
                                String id_satuan_pengali = String.valueOf(jointabeldata.get("id_satuan_pengali"));
                                String harga_beli = nf.format(FuncHelper.ToDouble(jointabeldata.get("harga_beli")));
                                String harga_jual = nf.format(FuncHelper.ToDouble(jointabeldata.get("harga_jual")));
                                String diskon_persen = String.valueOf(jointabeldata.get("diskon_persen"));
                                String diskon_nominal = String.valueOf(jointabeldata.get("diskon_nominal"));
                                String id_pajak = String.valueOf(jointabeldata.get("id_pajak"));
                                String nama_pajak = String.valueOf(jointabeldata.get("kode_pajak"));
                                String nilai_pajak = String.valueOf(jointabeldata.get("nilai_pajak"));
                                String id_service = String.valueOf(jointabeldata.get("id_service"));
                                String nilai_service = String.valueOf(jointabeldata.get("nilai_service"));
                                String id_gudang = String.valueOf(jointabeldata.get("id_gudang"));
                                String nama_gudang = String.valueOf(jointabeldata.get("nama_gudang"));
                                String keterangan = String.valueOf(jointabeldata.get("keterangan"));
                                String total = nf.format(kalkulasitotalperindex(diskon_persen, diskon_nominal, jumlah, harga_jual, isi_satuan));
                                tabeldatalist.add(new Entitytabledata(id_barang, kode_barang, nama_barang, jumlah, id_satuan,
                                     nama_satuan, isi_satuan, id_satuan_pengali, harga_beli, harga_jual, diskon_persen, diskon_nominal, id_pajak, nama_pajak,
                                     nilai_pajak, id_service, nilai_service, id_gudang, nama_gudang, keterangan, total));

                            }
                            for (int i = 0; i < tabeldatalist.size(); i++) {
                                rowtabledata[0] = tabeldatalist.get(i).getKode_barang();
                                rowtabledata[1] = tabeldatalist.get(i).getNama_barang();
                                rowtabledata[2] = tabeldatalist.get(i).getJumlah();
                                rowtabledata[3] = tabeldatalist.get(i).getNama_satuan();
                                rowtabledata[4] = tabeldatalist.get(i).getHarga_beli();
                                rowtabledata[5] = tabeldatalist.get(i).getHarga_jual();
                                rowtabledata[6] = tabeldatalist.get(i).getDiskon_persen();
                                rowtabledata[7] = tabeldatalist.get(i).getDiskon_nominal();
                                rowtabledata[8] = tabeldatalist.get(i).getNama_pajak();
                                rowtabledata[9] = tabeldatalist.get(i).getNama_gudang();
                                rowtabledata[10] = tabeldatalist.get(i).getKeterangan();
                                rowtabledata[11] = tabeldatalist.get(i).getTotal();
                                dtmtabeldata.addRow(rowtabledata);
                            }
                            kalkulasitotal();
                            for (int i = 0; i < rowtabledata.length; i++) {
                                rowtabledata[i] = "";
                            }
                        } catch (ParseException ex) {
                            Logger.getLogger(DaftarfakturpenjualaninputController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }

                KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keydis);
            }
        });
    }

    private void tutup() {
        pane.btutup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        FuncHelper.konfir("Yakin ingin keluar dari POS", "Cek kembali transaksi anda sebelum keluar dari POS", "Ya");
                        if (Staticvar.isupdate == true) {
                            Staticvar.isupdate = false;
                            KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keydis);
                            new FuncHelper().insertnogagal("2", pane.dtanggal.getDate(), valdept, no_urut);
                            pane.dispose();
                        }

                    }
                });

            }
        });
    }

    private void batal() {
        pane.bbatal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FuncHelper.konfir("Yakin ingin membatalkan trnasaksi ini", "Cek kembali sebelum anda yakin ingn membatalkan transaksi ini", "Ya");
                if (Staticvar.isupdate == true) {
                    Staticvar.isupdate = false;
                    Runnable rn = new Runnable() {
                        @Override
                        public void run() {
                            int jumlahrow = pane.tabledata.getRowCount();
                            for (int i = 0; i < jumlahrow; i++) {
                                tabeldatalist.remove(0);
                                dtmtabeldata.removeRow(0);
                                pane.edbarcode.requestFocus();
                            }
                            kalkulasitotal();
                        }
                    };
                    SwingUtilities.invokeLater(rn);
                }
            }
        });
    }

    private void carikasmasuklain() {
        pane.bkas_masuk_lain.addActionListener((ActionEvent e) -> {
            KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keydis);
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("poskasmasuk", "popupdaftarposkasmasuk?id_kasir=" + Globalsession.DEFAULT_ID_PENGGUNA + "&"
                 + "tgl=" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "", "Daftar POS Kas Masuk"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keydis);
        });

    }

    private void carikaskeluarlain() {
        pane.bkas_keluar_lain.addActionListener((ActionEvent e) -> {
            KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keydis);
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("poskaskeluar", "popupdaftarposkaskeluar?id_kasir=" + Globalsession.DEFAULT_ID_PENGGUNA + "&"
                 + "tgl=" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "", "Daftar POS Kas Keluar"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keydis);
        });

    }

    private void caripelanggan() {
        pane.bcari_pelanggan.addActionListener((ActionEvent e) -> {
            KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keydis);
            Staticvar.sfilter = "";
            Staticvar.preid = valpelanggan;
            Staticvar.prevalueextended = valgolongan;
            Staticvar.prelabel = String.valueOf(pane.edpelanggan.getText());
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("nama", "popupdaftarnama?tipe=0", "Daftar Pelanggan"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valpelanggan = Staticvar.resid;
            valgolongan = Staticvar.resvalueextended;
            pane.edpelanggan.setText(Staticvar.reslabel);
            pane.edbarcode.requestFocus();
            String param = String.format("id=%s", valpelanggan);
            jumlah_piutang = Double.parseDouble(ch.getdatadetails("getsaldopiutang", param));
            pane.ltotalpiutang.setText(nf.format(jumlah_piutang));
            KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keydis);
        });

    }

    private void carisalesman() {
        pane.bcari_salesman.addActionListener((ActionEvent e) -> {
            KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keydis);
            Staticvar.sfilter = "";
            Staticvar.preid = valsalesman;
            Staticvar.prelabel = pane.edsalesman.getText();
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("nama", "popupdaftarnama?tipe=2", "Daftar Karyawan"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valsalesman = Staticvar.resid;
            pane.edsalesman.setText(Staticvar.reslabel);
            KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keydis);
        });

    }

    private void caridepartment() {
        pane.bcari_dept.addActionListener((ActionEvent e) -> {
            KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keydis);
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
            new FuncHelper().insertnogagal("2", pane.dtanggal.getDate(), Staticvar.preid, no_urut);
            HashMap hm = new FuncHelper().getkodetransaksi("2", pane.dtanggal.getDate(), valdept);
            pane.edno_transaksi.setText(String.valueOf(hm.get("no_transaksi")));
            no_urut = String.valueOf(hm.get("no_urut"));
            KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keydis);
        });

    }

    private void carigudang() {
        pane.bcari_gudang.addActionListener((ActionEvent e) -> {
            KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keydis);
            Staticvar.sfilter = "";
            Staticvar.preid = valgudang;
            Staticvar.prelabel = pane.edgudang.getText();
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("gudang", "popupdaftargudang", "Daftar Gudang"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valgudang = Staticvar.resid;
            pane.edgudang.setText(Staticvar.reslabel);
            KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keydis);
        });

    }

    private void carisatuan() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keydis);
        int row = pane.tabledata.getSelectedRow();
        if (pane.tabledata.getSelectionModel().isSelectionEmpty()) {
            row = pane.tabledata.getRowCount() - 1;
        }
        Staticvar.sfilter = "";
        Staticvar.preid = tabeldatalist.get(row).getId_satuan();
        Staticvar.prelabel = tabeldatalist.get(row).getNama_satuan();
        Staticvar.prevalueextended = tabeldatalist.get(row).getIsi_satuan();
        JDialog jd = new JDialog();
        jd.add(new Popupcari("satuanperbarang",
             String.format("popupdaftarsatuanperbarang?id_inv=%s",
                  tabeldatalist.get(row).getId_barang()),
             "Daftar Satuan Perbarang"));
        jd.pack();
        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        jd.setLocationRelativeTo(null);
        jd.setVisible(true);
        jd.toFront();
        if (!tabeldatalist.get(row).getId_satuan().equals(Staticvar.resid)) {
            tabeldatalist.get(row).setId_satuan(Staticvar.resid);
            tabeldatalist.get(row).setNama_satuan(Staticvar.reslabel);
            tabeldatalist.get(row).setIsi_satuan(Staticvar.resvalueextended);

            double callhargajual = gethargajual(
                 tabeldatalist.get(row).getId_barang(),
                 tabeldatalist.get(row).getId_satuan(),
                 tabeldatalist.get(row).getJumlah());
            pane.tabledata.setValueAt(Staticvar.reslabel, row, 3);
            pane.tabledata.setValueAt(nf.format(callhargajual), row, 5);
            tabeldatalist.get(row).setHarga_jual(nf.format(callhargajual));
            kalkulasitotalperrow(row);

        }
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keydis);
    }

    private void showhutang() {
        pane.bpembayaran.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keydis);
                PoshutangController.id_pelanggan = valpelanggan;
                PoshutangController.nama_pelanggan = pane.edpelanggan.getText();
                PoshutangController.jumlah_piutang = jumlah_piutang;
                JDialog jd = new JDialog(new Mainmenu());
                jd.add(new Pos_hutang_pane());
                jd.pack();
                jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                jd.setLocationRelativeTo(null);
                jd.setVisible(true);
                jd.toFront();
                String param = String.format("id=%s", valpelanggan);
                jumlah_piutang = Double.parseDouble(ch.getdatadetails("getsaldopiutang", param));
                pane.ltotalpiutang.setText(nf.format(jumlah_piutang));
                KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keydis);

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

    private void keyfunction() {
        pane.edbarcode.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    pane.bsimpan.doClick();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    pane.tabledata.requestFocus();
                    pane.tabledata.changeSelection(0, 0, false, false);
                }

            }
        });

        pane.tabledata.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (pane.tabledata.getSelectedRow() == 0) {
                        pane.edbarcode.requestFocus();
                        pane.tabledata.clearSelection();
                    }
                }
            }

        });

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keydis);

    }

    private void additemtotable() {
        try {
            KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keydis);
            JSONParser jpdata = new JSONParser();
            String kode_barcode = pane.edbarcode.getText().toLowerCase();
            if (pane.edbarcode.getText().toLowerCase().contains("x")) {
                try {

                    String splitcode[] = pane.edbarcode.getText().toLowerCase().split("x");
                    if (splitcode.length == 2) {
                        String pre = splitcode[0];
                        String post = splitcode[1];
                        String rawkode_barcode = "";
                        if (pre.matches("[0-9]+")) {
                            rawkode_barcode = post;
                        } else {
                            rawkode_barcode = pre + "x" + post;
                        }

                        if (kode_barcode.substring(kode_barcode.length() - 1, kode_barcode.length()).toLowerCase().equals("x")) {
                            kode_barcode = rawkode_barcode + "x";
                        } else {
                            kode_barcode = rawkode_barcode;
                        }

                    } else if (splitcode.length > 2) {
                        String pre = splitcode[0];
                        String post = "";
                        StringBuilder sbpost = new StringBuilder();
                        for (int i = 0; i < splitcode.length; i++) {
                            if (i > 0) {
                                sbpost.append(splitcode[i]).append("x");
                            }
                        }
                        post = sbpost.toString().substring(0, sbpost.toString().length() - 1);
                        if (kode_barcode.substring(kode_barcode.length() - 1, kode_barcode.length()).toLowerCase().equals("x")) {
                            post = post + "x";
                        }
                        if (pre.matches("[0-9]+")) {
                            kode_barcode = post;
                        } else {
                            kode_barcode = pre + "x" + post;
                        }
                    } else {
                        String pre = splitcode[0];
                        String post = "";
                        if (pre.matches("[0-9]+")) {
                            kode_barcode = post;
                        } else {
                            kode_barcode = pre + post;
                        }
                    }
                } catch (Exception es) {
                    kode_barcode = "";
                }

            }

            Staticvar.preid = kode_barcode;
            String defnilai = "";
            String cekval = kode_barcode;
            if (cekval.equals("null") || cekval.equals("")) {
                defnilai = "";
            } else {
                defnilai = kode_barcode;
            }
            Staticvar.prelabel = defnilai;
            Staticvar.sfilter = defnilai;
            Staticvar.prelabel = defnilai;

            String param = String.format("kode=%s", kode_barcode);
            Object objdataraw = jpdata.parse(ch.getdatadetails("dm/datapersediaanpos", param));
            JSONObject jodataraw = (JSONObject) objdataraw;
            Object objdata = jodataraw.get("data");
            JSONArray jadata = (JSONArray) objdata;
            if (jadata.size() == 1) {
                for (int i = 0; i < jadata.size(); i++) {
                    JSONObject jointabeldata = (JSONObject) jadata.get(i);
                    if (tabeldatalist.isEmpty()) {
                        String id_barang = String.valueOf(jointabeldata.get("id"));
                        String kode_barang = String.valueOf(jointabeldata.get("kode"));
                        String nama_barang = String.valueOf(jointabeldata.get("nama"));
                        String jumlah_qty = "1";
                        if (pane.edbarcode.getText().toLowerCase().contains("x")) {
                            String pre = pane.edbarcode.getText().toLowerCase().split("x")[0];
                            if (pre.matches("[0-9]+")) {
                                jumlah_qty = pre;
                            }
                        }
                        String jumlah = String.valueOf(FuncHelper.ToInt(jumlah_qty));
                        String id_satuan = String.valueOf(jointabeldata.get("id_satuan"));
                        String nama_satuan = String.valueOf(jointabeldata.get("kode_satuan"));
                        String isi_satuan = String.valueOf(jointabeldata.get("qty_satuan_pengali"));
                        String id_satuan_pengali = String.valueOf(jointabeldata.get("id_satuan_pengali"));
                        String harga_beli = nf.format(FuncHelper.ToDouble(jointabeldata.get("harga_beli")));
                        String harga_jual = nf.format(FuncHelper.ToDouble(jointabeldata.get("harga_jual")));
                        String diskon_persen = "0";
                        String diskon_nominal = "0";
                        String id_pajak = String.valueOf(jointabeldata.get("id_pajak_jual"));
                        String nama_pajak = String.valueOf(jointabeldata.get("nama_pajak_jual"));
                        String nilai_pajak = String.valueOf(jointabeldata.get("nilai_pajak_jual"));
                        String id_service = String.valueOf(jointabeldata.get("id_service"));
                        String nilai_service = String.valueOf(jointabeldata.get("nilai_service"));
                        String id_gudang = valgudang;
                        String nama_gudang = pane.edgudang.getText();
                        String keterangan = "";
                        String total = nf.format(kalkulasitotalperindex(diskon_persen, diskon_nominal, jumlah, harga_jual, isi_satuan));
                        tabeldatalist.add(new Entitytabledata(id_barang, kode_barang, nama_barang, jumlah, id_satuan,
                             nama_satuan, isi_satuan, id_satuan_pengali, harga_beli, harga_jual, diskon_persen, diskon_nominal, id_pajak, nama_pajak,
                             nilai_pajak, id_service, nilai_service, id_gudang, nama_gudang, keterangan, total));
                        int rowcount = pane.tabledata.getRowCount();
                        rowtabledata[0] = tabeldatalist.get(rowcount).getKode_barang();
                        rowtabledata[1] = tabeldatalist.get(rowcount).getNama_barang();
                        rowtabledata[2] = tabeldatalist.get(rowcount).getJumlah();
                        rowtabledata[3] = tabeldatalist.get(rowcount).getNama_satuan();
                        rowtabledata[4] = tabeldatalist.get(rowcount).getHarga_beli();
                        rowtabledata[5] = tabeldatalist.get(rowcount).getHarga_jual();
                        rowtabledata[6] = tabeldatalist.get(rowcount).getDiskon_persen();
                        rowtabledata[7] = tabeldatalist.get(rowcount).getDiskon_nominal();
                        rowtabledata[8] = tabeldatalist.get(rowcount).getNama_pajak();
                        rowtabledata[9] = tabeldatalist.get(rowcount).getNama_gudang();
                        rowtabledata[10] = tabeldatalist.get(rowcount).getKeterangan();
                        rowtabledata[11] = tabeldatalist.get(rowcount).getTotal();
                        dtmtabeldata.addRow(rowtabledata);
                        double callhargajual = gethargajual(
                             tabeldatalist.get(rowcount).getId_barang(),
                             tabeldatalist.get(rowcount).getId_satuan(),
                             tabeldatalist.get(rowcount).getJumlah());
                        pane.tabledata.setValueAt(nf.format(callhargajual), rowcount, 5);
                        tabeldatalist.get(rowcount).setHarga_jual(nf.format(callhargajual));
                        kalkulasitotalperrow(rowcount);
                        //kalkulasitotal();
                    } else {
                        boolean status_ada = true;

                        if (Globalsession.POS_ScanBarcodeBuatBaru.equals("1")) {
                            status_ada = false;
                        } else {

                            for (int j = 0; j < tabeldatalist.size(); j++) {
                                if (tabeldatalist.get(j).getId_barang().matches(String.valueOf(jointabeldata.get("id")))
                                     && tabeldatalist.get(j).getId_satuan().matches(String.valueOf(jointabeldata.get("id_satuan")))) {
                                    String jumlah_qty = "1";
                                    if (pane.edbarcode.getText().toLowerCase().contains("x")) {
                                        String pre = pane.edbarcode.getText().toLowerCase().split("x")[0];
                                        if (pre.matches("[0-9]+")) {
                                            jumlah_qty = pre;
                                        }
                                    }
                                    int curjumlah = FuncHelper.ToInt(tabeldatalist.get(j).getJumlah()) + FuncHelper.ToInt(jumlah_qty);
                                    tabeldatalist.get(j).setJumlah(String.valueOf(curjumlah));
                                    pane.tabledata.setValueAt(String.valueOf(curjumlah), j, gx(jumlah));
                                    double callhargajual = gethargajual(
                                         tabeldatalist.get(j).getId_barang(),
                                         tabeldatalist.get(j).getId_satuan(),
                                         tabeldatalist.get(j).getJumlah());
                                    pane.tabledata.setValueAt(nf.format(callhargajual), j, 5);
                                    tabeldatalist.get(j).setHarga_jual(nf.format(callhargajual));
                                    kalkulasitotalperrow(j);
                                    status_ada = true;
                                    break;
                                } else {
                                    status_ada = false;
                                }
                            }

                        }

                        if (status_ada == false) {
                            String id_barang = String.valueOf(jointabeldata.get("id"));
                            String kode_barang = String.valueOf(jointabeldata.get("kode"));
                            String nama_barang = String.valueOf(jointabeldata.get("nama"));
                            String jumlah_qty = "1";
                            if (pane.edbarcode.getText().toLowerCase().contains("x")) {
                                String pre = pane.edbarcode.getText().toLowerCase().split("x")[0];
                                if (pre.matches("[0-9]+")) {
                                    jumlah_qty = pre;
                                }
                            }
                            String jumlah = String.valueOf(FuncHelper.ToInt(jumlah_qty));
                            String id_satuan = String.valueOf(jointabeldata.get("id_satuan"));
                            String nama_satuan = String.valueOf(jointabeldata.get("kode_satuan"));
                            String isi_satuan = String.valueOf(jointabeldata.get("qty_satuan_pengali"));
                            String id_satuan_pengali = String.valueOf(jointabeldata.get("id_satuan_pengali"));
                            String harga_beli = nf.format(FuncHelper.ToDouble(jointabeldata.get("harga_beli")));
                            String harga_jual = nf.format(FuncHelper.ToDouble(jointabeldata.get("harga_jual")));
                            String diskon_persen = "0";
                            String diskon_nominal = "0";
                            String id_pajak = String.valueOf(jointabeldata.get("id_pajak_jual"));
                            String nama_pajak = String.valueOf(jointabeldata.get("nama_pajak_jual"));
                            String nilai_pajak = String.valueOf(jointabeldata.get("nilai_pajak_jual"));
                            String id_service = String.valueOf(jointabeldata.get("id_service"));
                            String nilai_service = String.valueOf(jointabeldata.get("nilai_service"));
                            String id_gudang = valgudang;
                            String nama_gudang = pane.edgudang.getText();
                            String keterangan = "";
                            String total = nf.format(kalkulasitotalperindex(diskon_persen, diskon_nominal, jumlah, harga_jual, isi_satuan));
                            tabeldatalist.add(new Entitytabledata(id_barang, kode_barang, nama_barang, jumlah, id_satuan,
                                 nama_satuan, isi_satuan, id_satuan_pengali, harga_beli, harga_jual, diskon_persen, diskon_nominal, id_pajak, nama_pajak,
                                 nilai_pajak, id_service, nilai_service, id_gudang, nama_gudang, keterangan, total));
                            int rowcount = pane.tabledata.getRowCount();
                            rowtabledata[0] = tabeldatalist.get(rowcount).getKode_barang();
                            rowtabledata[1] = tabeldatalist.get(rowcount).getNama_barang();
                            rowtabledata[2] = tabeldatalist.get(rowcount).getJumlah();
                            rowtabledata[3] = tabeldatalist.get(rowcount).getNama_satuan();
                            rowtabledata[4] = tabeldatalist.get(rowcount).getHarga_beli();
                            rowtabledata[5] = tabeldatalist.get(rowcount).getHarga_jual();
                            rowtabledata[6] = tabeldatalist.get(rowcount).getDiskon_persen();
                            rowtabledata[7] = tabeldatalist.get(rowcount).getDiskon_nominal();
                            rowtabledata[8] = tabeldatalist.get(rowcount).getNama_pajak();
                            rowtabledata[9] = tabeldatalist.get(rowcount).getNama_gudang();
                            rowtabledata[10] = tabeldatalist.get(rowcount).getKeterangan();
                            rowtabledata[11] = tabeldatalist.get(rowcount).getTotal();
                            dtmtabeldata.addRow(rowtabledata);
                            double callhargajual = gethargajual(
                                 tabeldatalist.get(rowcount).getId_barang(),
                                 tabeldatalist.get(rowcount).getId_satuan(),
                                 tabeldatalist.get(rowcount).getJumlah());
                            pane.tabledata.setValueAt(nf.format(callhargajual), rowcount, 5);
                            tabeldatalist.get(rowcount).setHarga_jual(nf.format(callhargajual));
                            kalkulasitotalperrow(rowcount);
                            //kalkulasitotal();

                        }
                    }
                }

            } else {
                JDialog jd = new JDialog(new Mainmenu());
                jd.add(new Popupcari("persediaan", "popupdaftarpersediaan", "Daftar Persediaan"));
                jd.pack();
                jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                jd.setLocationRelativeTo(null);
                jd.setVisible(true);
                jd.toFront();
                if (!Staticvar.reslabel.equals("")) {
                    JSONParser jpdata2 = new JSONParser();
                    String param2 = String.format("id=%s", Staticvar.resid);
                    Object objdataraw2 = jpdata2.parse(ch.getdatadetails("dm/datapersediaan", param2));
                    JSONObject jodataraw2 = (JSONObject) objdataraw2;
                    Object objdata2 = jodataraw2.get("data");
                    JSONArray jadata2 = (JSONArray) objdata2;
                    for (int i = 0; i < jadata2.size(); i++) {
                        JSONObject jointabeldata = (JSONObject) jadata2.get(i);
                        if (tabeldatalist.isEmpty()) {
                            String id_barang = String.valueOf(jointabeldata.get("id"));
                            String kode_barang = String.valueOf(jointabeldata.get("kode"));

                            String nama_barang = String.valueOf(jointabeldata.get("nama"));
                            String jumlah_qty = "1";

                            if (pane.edbarcode.getText().toLowerCase().contains("x")) {
                                String pre = pane.edbarcode.getText().toLowerCase().split("x")[0];
                                if (pre.matches("[0-9]+")) {
                                    jumlah_qty = pre;
                                }
                            }
                            String jumlah = String.valueOf(FuncHelper.ToInt(jumlah_qty));
                            String id_satuan = String.valueOf(jointabeldata.get("id_satuan"));
                            String nama_satuan = String.valueOf(jointabeldata.get("kode_satuan"));
                            String isi_satuan = String.valueOf(jointabeldata.get("qty_satuan_pengali"));
                            String id_satuan_pengali = String.valueOf(jointabeldata.get("id_satuan_pengali"));
                            String harga_beli = nf.format(FuncHelper.ToDouble(jointabeldata.get("harga_beli")));
                            String harga_jual = nf.format(FuncHelper.ToDouble(jointabeldata.get("harga_jual")));
                            String diskon_persen = "0";
                            String diskon_nominal = "0";
                            String id_pajak = String.valueOf(jointabeldata.get("id_pajak_jual"));
                            String nama_pajak = String.valueOf(jointabeldata.get("nama_pajak_jual"));
                            String nilai_pajak = String.valueOf(jointabeldata.get("nilai_pajak_jual"));
                            String id_service = String.valueOf(jointabeldata.get("id_service"));
                            String nilai_service = String.valueOf(jointabeldata.get("nilai_service"));
                            String id_gudang = valgudang;
                            String nama_gudang = pane.edgudang.getText();
                            String keterangan = "";
                            String total = nf.format(kalkulasitotalperindex(diskon_persen, diskon_nominal, jumlah, harga_jual, isi_satuan));
                            tabeldatalist.add(new Entitytabledata(id_barang, kode_barang, nama_barang, jumlah, id_satuan,
                                 nama_satuan, isi_satuan, id_satuan_pengali, harga_beli, harga_jual, diskon_persen, diskon_nominal, id_pajak, nama_pajak,
                                 nilai_pajak, id_service, nilai_service, id_gudang, nama_gudang, keterangan, total));
                            int rowcount = pane.tabledata.getRowCount();
                            rowtabledata[0] = tabeldatalist.get(rowcount).getKode_barang();
                            rowtabledata[1] = tabeldatalist.get(rowcount).getNama_barang();
                            rowtabledata[2] = tabeldatalist.get(rowcount).getJumlah();
                            rowtabledata[3] = tabeldatalist.get(rowcount).getNama_satuan();
                            rowtabledata[4] = tabeldatalist.get(rowcount).getHarga_beli();
                            rowtabledata[5] = tabeldatalist.get(rowcount).getHarga_jual();
                            rowtabledata[6] = tabeldatalist.get(rowcount).getDiskon_persen();
                            rowtabledata[7] = tabeldatalist.get(rowcount).getDiskon_nominal();
                            rowtabledata[8] = tabeldatalist.get(rowcount).getNama_pajak();
                            rowtabledata[9] = tabeldatalist.get(rowcount).getNama_gudang();
                            rowtabledata[10] = tabeldatalist.get(rowcount).getKeterangan();
                            rowtabledata[11] = tabeldatalist.get(rowcount).getTotal();
                            dtmtabeldata.addRow(rowtabledata);
                            double callhargajual = gethargajual(
                                 tabeldatalist.get(rowcount).getId_barang(),
                                 tabeldatalist.get(rowcount).getId_satuan(),
                                 tabeldatalist.get(rowcount).getJumlah());
                            pane.tabledata.setValueAt(nf.format(callhargajual), rowcount, 5);
                            tabeldatalist.get(rowcount).setHarga_jual(nf.format(callhargajual));
                            kalkulasitotalperrow(rowcount);
                            //kalkulasitotal();
                            pane.edbarcode.setText(kode_barang);
                        } else {
                            boolean status_ada = true;

                            for (int j = 0; j < tabeldatalist.size(); j++) {
                                if (tabeldatalist.get(j).getId_barang().matches(String.valueOf(jointabeldata.get("id")))
                                     && tabeldatalist.get(j).getId_satuan().matches(String.valueOf(jointabeldata.get("id_satuan")))) {
                                    String jumlah_qty = "1";
                                    if (pane.edbarcode.getText().toLowerCase().contains("x")) {
                                        String pre = pane.edbarcode.getText().toLowerCase().split("x")[0];
                                        if (pre.matches("[0-9]+")) {
                                            jumlah_qty = pre;
                                        }
                                    }
                                    int curjumlah = FuncHelper.ToInt(tabeldatalist.get(j).getJumlah())
                                         + FuncHelper.ToInt(jumlah_qty);
                                    tabeldatalist.get(j).setJumlah(String.valueOf(curjumlah));
                                    pane.tabledata.setValueAt(String.valueOf(curjumlah), j, gx(jumlah));
                                    double callhargajual = gethargajual(
                                         tabeldatalist.get(j).getId_barang(),
                                         tabeldatalist.get(j).getId_satuan(),
                                         tabeldatalist.get(j).getJumlah());
                                    pane.tabledata.setValueAt(nf.format(callhargajual), j, 5);
                                    tabeldatalist.get(j).setHarga_jual(nf.format(callhargajual));
                                    kalkulasitotalperrow(j);
                                    status_ada = true;
                                    break;
                                } else {
                                    status_ada = false;
                                }
                            }

                            if (status_ada == false) {
                                String id_barang = String.valueOf(jointabeldata.get("id"));
                                String kode_barang = String.valueOf(jointabeldata.get("kode"));

                                String nama_barang = String.valueOf(jointabeldata.get("nama"));
                                String jumlah_qty = "1";
                                if (pane.edbarcode.getText().toLowerCase().contains("x")) {
                                    String pre = pane.edbarcode.getText().toLowerCase().split("x")[0];
                                    if (pre.matches("[0-9]+")) {
                                        jumlah_qty = pre;
                                    }
                                }
                                String jumlah = String.valueOf(FuncHelper.ToInt(jumlah_qty));
                                String id_satuan = String.valueOf(jointabeldata.get("id_satuan"));
                                String nama_satuan = String.valueOf(jointabeldata.get("kode_satuan"));
                                String isi_satuan = String.valueOf(jointabeldata.get("qty_satuan_pengali"));
                                String id_satuan_pengali = String.valueOf(jointabeldata.get("id_satuan_pengali"));
                                String harga_beli = nf.format(FuncHelper.ToDouble(jointabeldata.get("harga_beli")));
                                String harga_jual = nf.format(FuncHelper.ToDouble(jointabeldata.get("harga_jual")));
                                String diskon_persen = "0";
                                String diskon_nominal = "0";
                                String id_pajak = String.valueOf(jointabeldata.get("id_pajak_jual"));
                                String nama_pajak = String.valueOf(jointabeldata.get("nama_pajak_jual"));
                                String nilai_pajak = String.valueOf(jointabeldata.get("nilai_pajak_jual"));
                                String id_service = String.valueOf(jointabeldata.get("id_service"));
                                String nilai_service = String.valueOf(jointabeldata.get("nilai_service"));
                                String id_gudang = valgudang;
                                String nama_gudang = pane.edgudang.getText();
                                String keterangan = "";
                                String total = nf.format(kalkulasitotalperindex(diskon_persen, diskon_nominal, jumlah, harga_jual, isi_satuan));
                                tabeldatalist.add(new Entitytabledata(id_barang, kode_barang, nama_barang, jumlah, id_satuan,
                                     nama_satuan, isi_satuan, id_satuan_pengali, harga_beli, harga_jual, diskon_persen, diskon_nominal, id_pajak, nama_pajak,
                                     nilai_pajak, id_service, nilai_service, id_gudang, nama_gudang, keterangan, total
                                ));
                                int rowcount = pane.tabledata.getRowCount();
                                rowtabledata[0] = tabeldatalist.get(rowcount).getKode_barang();
                                rowtabledata[1] = tabeldatalist.get(rowcount).getNama_barang();
                                rowtabledata[2] = tabeldatalist.get(rowcount).getJumlah();
                                rowtabledata[3] = tabeldatalist.get(rowcount).getNama_satuan();
                                rowtabledata[4] = tabeldatalist.get(rowcount).getHarga_beli();
                                rowtabledata[5] = tabeldatalist.get(rowcount).getHarga_jual();
                                rowtabledata[6] = tabeldatalist.get(rowcount).getDiskon_persen();
                                rowtabledata[7] = tabeldatalist.get(rowcount).getDiskon_nominal();
                                rowtabledata[8] = tabeldatalist.get(rowcount).getNama_pajak();
                                rowtabledata[9] = tabeldatalist.get(rowcount).getNama_gudang();
                                rowtabledata[10] = tabeldatalist.get(rowcount).getKeterangan();
                                rowtabledata[11] = tabeldatalist.get(rowcount).getTotal();
                                dtmtabeldata.addRow(rowtabledata);
                                double callhargajual = gethargajual(
                                     tabeldatalist.get(rowcount).getId_barang(),
                                     tabeldatalist.get(rowcount).getId_satuan(),
                                     tabeldatalist.get(rowcount).getJumlah());
                                pane.tabledata.setValueAt(nf.format(callhargajual), rowcount, 5);
                                tabeldatalist.get(rowcount).setHarga_jual(nf.format(callhargajual));
                                kalkulasitotalperrow(rowcount);
                                //kalkulasitotal();
                                pane.edbarcode.setText(kode_barang);
                            }
                        }
                    }
                }
            }
            KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keydis);
        } catch (ParseException ex) {
            Logger.getLogger(PosframeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        pane.edbarcode.setText(DefaultCari);
        pane.edbarcode.setForeground(Color.GRAY);
        pane.edbarcode.select(0, 0);

        pane.edbarcode.requestFocus();
    }

    private void inserttorow() {

    }

    private void addautorow(int row) {
        int lastrow = pane.tabledata.getRowCount() - 1;
        if (!pane.tabledata.getValueAt(row, gx(kode)).equals("")
             || !pane.tabledata.getValueAt(row, gx(jumlah)).equals("")
             || !pane.tabledata.getValueAt(row, gx(satuan)).equals("")
             || !pane.tabledata.getValueAt(row, gx(diskon_persen)).equals("")
             || !pane.tabledata.getValueAt(row, gx(diskon_nominal)).equals("")) {
            if (row == lastrow) {
                tabeldatalist.add(new Entitytabledata("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
                dtmtabeldata.addRow(rowtabledata);
                pane.tabledata.requestFocus();
                pane.tabledata.changeSelection(row + 1, 0, false, false);
            }
        }
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

    private void columnfunction(int row, int col, boolean addrow) {
        if (pane.tabledata.getValueAt(row, col).equals("")) {
            return;
        }
        if ((col == gx(jumlah)) || (col == gx(harga_jual)) || (col == gx(diskon_nominal))) {
            String value = nf.format(FuncHelper.ToDouble(pane.tabledata.getValueAt(row, col)));
            pane.tabledata.setValueAt(value, row, col);
            kalkulasitotalperrow(row);
            if (addrow == true) {
                addautorow(row);
            }
        } else if (col == gx(diskon_persen)) {
            String value = String.valueOf(pane.tabledata.getValueAt(row, col));
            pane.tabledata.setValueAt(value, row, col);
            kalkulasitotalperrow(row);
            if (addrow == true) {
                addautorow(row);
            }
        }
    }

    private String kirimtexpenjualan() {
        StringBuilder sb = new StringBuilder();
        sb.append("penjualan_detail=");
        int listcount = 0;
        if (tabeldatalist.get(tabeldatalist.size() - 1).getId_barang().equals("")) {
            listcount = tabeldatalist.size() - 1;
        } else {
            listcount = tabeldatalist.size();
        }
        for (int i = 0; i < listcount; i++) {
            sb.append("id_inv=" + "'" + tabeldatalist.get(i).getId_barang() + "'" + "::"
                 + "qty=" + "'" + FuncHelper.ToDouble(tabeldatalist.get(i).getJumlah()) + "'" + "::"
                 + "harga=" + "'" + FuncHelper.ToDouble(tabeldatalist.get(i).getHarga_jual()) + "'" + "::"
                 + "id_satuan=" + "'" + tabeldatalist.get(i).getId_satuan() + "'" + "::"
                 + "diskon_persen=" + "'" + FuncHelper.EncodeString(tabeldatalist.get(i).getDiskon_persen()) + "'" + "::"
                 + "diskon_nominal=" + "'" + FuncHelper.ToDouble(tabeldatalist.get(i).getDiskon_nominal()) + "'" + "::"
                 + "id_pajak=" + "'" + tabeldatalist.get(i).getId_pajak() + "'" + "::"
                 + "id_service=" + "'" + tabeldatalist.get(i).getId_service() + "'" + "::"
                 + "id_gudang=" + "'" + tabeldatalist.get(i).getId_gudang() + "'" + "::"
                 + "id_satuan_pengali=" + "'" + tabeldatalist.get(i).getId_satuan_pengali() + "'" + "::"
                 + "qty_satuan_pengali=" + "'" + tabeldatalist.get(i).getIsi_satuan() + "'" + "::"
                 + "keterangan=" + "'" + FuncHelper.EncodeString(tabeldatalist.get(i).getKeterangan()) + "'");
            sb.append("--");

        }
        String hasil = sb.toString().substring(0, sb.toString().length() - 2);
        return hasil;
    }

    public void rawsimpan() {
        String tipe_beli = "0";
        if (pane.ckjenisbayar.isSelected()) {
            tipe_beli = "0";
            valakun_penjualan = Globalsession.AKUNPENJUALANTUNAI;
        } else {
            tipe_beli = "1";
            valakun_penjualan = Globalsession.AKUNPIUTANGUSAHA;
        }

        String diskon_dalam = "0";
        if (pane.ckdiskon.isSelected()) {
            diskon_dalam = "0";
        } else {
            diskon_dalam = "1";
        }
        String data = "id=" + validtransaksi + "&genjur="
             + "id_keltrans='2'::"
             + "id_dept='" + valdept + "'::"
             + "tanggal='" + new SimpleDateFormat("yyyy-MM-dd").format(pane.dtanggal.getDate()) + "'::"
             + "noref='" + FuncHelper.EncodeString(pane.edno_transaksi.getText()) + "'::"
             + "keterangan='" + FuncHelper.EncodeString(keterangan) + "'"
             + "&penjualan="
             + "id_pelanggan='" + valpelanggan + "'::"
             + "tipe_pembayaran='" + tipe_beli + "'::"
             + "id_gudang='" + valgudang + "'::"
             + "total_penjualan='" + FuncHelper.ToDouble(total_penjualan_all) + "'::"
             + "total_biaya='" + FuncHelper.ToDouble("0") + "'::"
             + "diskon_persen='" + FuncHelper.ToDouble("0") + "'::"
             + "diskon_nominal='" + FuncHelper.ToDouble("0") + "'::"
             + "total_uang_muka='" + FuncHelper.ToDouble("0") + "'::"
             + "total_pajak='" + total_pajak + "'::"
             + "total_service='" + total_service + "'::"
             + "id_currency='" + Globalsession.id_currency_company + "'::"
             + "nilai_kurs='1'::"
             + "akun_penjualan='" + valakun_penjualan + "'::"
             + "akun_biaya='" + valakun_ongkir + "'::"
             + "akun_diskon='" + valakun_diskon + "'::"
             + "akun_uang_muka='" + valakun_uang_muka + "'::"
             + "diskon_dalam='" + diskon_dalam + "'::"
             + "tanggal_pengantaran='" + new SimpleDateFormat("yyyy-MM-dd").format(pane.dtanggal.getDate()) + "'::"
             + "id_pengantaran='" + valshipvia + "'::"
             + "id_bagian_penjualan='" + valsalesman + "'::"
             + "id_termofpayment='" + valtop + "'::"
             + "tipe_penjualan='0'"
             + "&" + kirimtexpenjualan();

        ch.insertdata("insertpendingpenjualan", data);
        if (Staticvar.getresult.equals("berhasil")) {
            int rowcount = pane.tabledata.getRowCount();
            if (Staticvar.isupdate == true) {
                for (int i = 0; i < rowcount; i++) {
                    tabeldatalist.remove(0);
                    dtmtabeldata.removeRow(0);
                }
                kalkulasitotal();
                HashMap hm = new FuncHelper().getkodetransaksi("2", pane.dtanggal.getDate(), valdept);
                pane.edno_transaksi.setText(String.valueOf(hm.get("no_transaksi")));
                no_urut = String.valueOf(hm.get("no_urut"));
            }
            String param = String.format("id=%s", valpelanggan);
            jumlah_piutang = Double.parseDouble(ch.getdatadetails("getsaldopiutang", param));
            pane.ltotalpiutang.setText(nf.format(jumlah_piutang));
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

    private void kalkulasitotal() {
        int jumlah_row = pane.tabledata.getRowCount();
        subtotal = 0;
        total_penjualan_all = 0;
        total_pajak = 0;
        total_service = 0;

        for (int i = 0; i < jumlah_row; i++) {
            double total_beli_masing = FuncHelper.ToDouble(emptycellcheck(i, 11));
            subtotal = subtotal + total_beli_masing;

            double total_pajak_masing = FuncHelper.ToDouble(emptycellcheck(i, 11)) * (FuncHelper.ToDouble(tabeldatalist.get(i).getNilai_pajak()) / 100);
            total_pajak = total_pajak + total_pajak_masing;

            double total_service_masing = FuncHelper.ToDouble(emptycellcheck(i, 11)) * (FuncHelper.ToDouble(tabeldatalist.get(i).getNilai_service()) / 100);
            total_service = total_service + total_service_masing;
        }

        pane.ltotal_pajak.setText(nf.format(total_pajak));
        pane.ltotal_service.setText(nf.format(total_service));
        pane.lsubtotal.setText(nf.format(subtotal));
        total_penjualan_all = subtotal + FuncHelper.ToDouble(total_pajak) + FuncHelper.ToDouble(total_service);
        pane.ltotal_penjualan.setText(nf.format(total_penjualan_all));
        pane.ltotal_belanja.setText(nf.format(total_penjualan_all));
    }

    private void kalkulasitotalperrow(int row) {
        if (pane.ckdiskon.isSelected() == true) {
            String isifielddiskon = String.valueOf(pane.tabledata.getValueAt(row, gx(diskon_persen)));
            if (isifielddiskon.contains("+")) {
                double qty = FuncHelper.ToDouble(String.valueOf(pane.tabledata.getValueAt(row, gx(jumlah))));
                double harga = FuncHelper.ToDouble(pane.tabledata.getValueAt(row, gx(harga_jual)));
                double intotal = harga;
                String s = "";
                String[] multidiskon = isifielddiskon.split("\\+");
                for (int i = 0; i < multidiskon.length; i++) {
                    double diskonper = FuncHelper.ToDouble(multidiskon[i]);
                    intotal = ((intotal - (diskonper / 100 * intotal)));
                }
                intotal = qty * intotal;
                tabeldatalist.get(row).setTotal(String.valueOf(intotal));
                pane.tabledata.setValueAt(nf.format(intotal), row, gx(total));
            } else {
                double qty = FuncHelper.ToDouble(String.valueOf(pane.tabledata.getValueAt(row, gx(jumlah))));
                double harga = FuncHelper.ToDouble(String.valueOf(pane.tabledata.getValueAt(row, gx(harga_jual))));
                double diskon = FuncHelper.ToDouble(emptycellcheck(row, gx(diskon_persen)));
                double intotal = qty * (harga - (diskon / 100 * harga));
                tabeldatalist.get(row).setTotal(String.valueOf(total));
                pane.tabledata.setValueAt(nf.format(intotal), row, gx(total));
            }
        } else {

            double qty = FuncHelper.ToDouble(String.valueOf(pane.tabledata.getValueAt(row, gx(jumlah))));
            double harga = FuncHelper.ToDouble(pane.tabledata.getValueAt(row, gx(harga_jual)));
            double diskon = FuncHelper.ToDouble(emptycellcheck(row, gx(diskon_nominal)));
            double intotal = qty * (harga - diskon);
            tabeldatalist.get(row).setTotal(String.valueOf(intotal));
            pane.tabledata.setValueAt(nf.format(intotal), row, gx(total));
        }
        kalkulasitotal();
    }

    private double kalkulasitotalperindex(String rawdiskonpersen, String rawdiskonnominal, String rawqty, String rawharga, String isisatuan) {
        double total = 0;
        if (pane.ckdiskon.isSelected() == true) {
            String isifielddiskon = rawdiskonpersen;
            if (isifielddiskon.contains("+")) {
                double qty = FuncHelper.ToDouble(rawqty);
                double harga = FuncHelper.ToDouble(rawharga);
                total = harga;
                String[] multidiskon = isifielddiskon.split("\\+");
                for (int i = 0; i < multidiskon.length; i++) {
                    double diskonper = FuncHelper.ToDouble(multidiskon[i]);
                    total = ((total - (diskonper / 100 * total)));
                }
                total = qty * total;
            } else {
                double qty = FuncHelper.ToDouble(rawqty);
                double harga = FuncHelper.ToDouble(rawharga);
                double diskon = FuncHelper.ToDouble(rawdiskonpersen);
                total = qty * (harga - (diskon / 100 * harga));
            }
        } else {

            double qty = FuncHelper.ToDouble(rawqty);
            double harga = FuncHelper.ToDouble(rawharga);
            double diskon = FuncHelper.ToDouble(rawdiskonnominal);
            total = qty * (harga - diskon);
        }
        return total;
    }

    private String emptycellcheck(int row, int col) {
        String ret = "";
        String value = String.valueOf(pane.tabledata.getValueAt(row, col));

        try {
            if (value.equals("") || value.equals("null")) {
                ret = "0";
            } else {
                ret = value;
            }
        } catch (NullPointerException e) {
            ret = "0";
        }

        return ret;
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

    private double gethargajual(String id_inv, String id_satuan, String qty) {
        String param = String.format("id_golongan=%s&id_inv=%s&id_satuan=%s&qty=%s", valgolongan, id_inv, id_satuan, qty);
        String hargajual = ch.getdatadetails("gethargajual", param);
        return Double.parseDouble(hargajual);
    }

    private int gx(String columname) {
        return listheadername.indexOf(columname);

    }

    public class Entitytabledata {

        String id_barang, kode_barang, nama_barang, jumlah,
             id_satuan, nama_satuan, isi_satuan, id_satuan_pengali, harga_beli, harga_jual, diskon_persen, diskon_nominal,
             id_pajak, nama_pajak, nilai_pajak, id_service, nilai_service, id_gudang, nama_gudang, keterangan, total;

        public Entitytabledata(String id_barang, String kode_barang, String nama_barang, String jumlah, String id_satuan, String nama_satuan, String isi_satuan, String id_satuan_pengali, String harga_beli, String harga_jual, String diskon_persen, String diskon_nominal, String id_pajak, String nama_pajak, String nilai_pajak, String id_service, String nilai_service, String id_gudang, String nama_gudang, String keterangan, String total) {
            this.id_barang = id_barang;
            this.kode_barang = kode_barang;
            this.nama_barang = nama_barang;
            this.jumlah = jumlah;
            this.id_satuan = id_satuan;
            this.nama_satuan = nama_satuan;
            this.isi_satuan = isi_satuan;
            this.id_satuan_pengali = id_satuan_pengali;
            this.harga_beli = harga_beli;
            this.harga_jual = harga_jual;
            this.diskon_persen = diskon_persen;
            this.diskon_nominal = diskon_nominal;
            this.id_pajak = id_pajak;
            this.nama_pajak = nama_pajak;
            this.nilai_pajak = nilai_pajak;
            this.id_service = id_service;
            this.nilai_service = nilai_service;
            this.id_gudang = id_gudang;
            this.nama_gudang = nama_gudang;
            this.keterangan = keterangan;
            this.total = total;
        }

        public String getId_barang() {
            return id_barang;
        }

        public void setId_barang(String id_barang) {
            this.id_barang = id_barang;
        }

        public String getKode_barang() {
            return kode_barang;
        }

        public void setKode_barang(String kode_barang) {
            this.kode_barang = kode_barang;
        }

        public String getNama_barang() {
            return nama_barang;
        }

        public void setNama_barang(String nama_barang) {
            this.nama_barang = nama_barang;
        }

        public String getJumlah() {
            return jumlah;
        }

        public void setJumlah(String jumlah) {
            this.jumlah = jumlah;
        }

        public String getId_satuan() {
            return id_satuan;
        }

        public void setId_satuan(String id_satuan) {
            this.id_satuan = id_satuan;
        }

        public String getNama_satuan() {
            return nama_satuan;
        }

        public void setNama_satuan(String nama_satuan) {
            this.nama_satuan = nama_satuan;
        }

        public String getIsi_satuan() {
            return isi_satuan;
        }

        public void setIsi_satuan(String isi_satuan) {
            this.isi_satuan = isi_satuan;
        }

        public String getId_satuan_pengali() {
            return id_satuan_pengali;
        }

        public void setId_satuan_pengali(String id_satuan_pengali) {
            this.id_satuan_pengali = id_satuan_pengali;
        }

        public String getHarga_beli() {
            return harga_beli;
        }

        public void setHarga_beli(String harga_beli) {
            this.harga_beli = harga_beli;
        }

        public String getHarga_jual() {
            return harga_jual;
        }

        public void setHarga_jual(String harga_jual) {
            this.harga_jual = harga_jual;
        }

        public String getDiskon_persen() {
            return diskon_persen;
        }

        public void setDiskon_persen(String diskon_persen) {
            this.diskon_persen = diskon_persen;
        }

        public String getDiskon_nominal() {
            return diskon_nominal;
        }

        public void setDiskon_nominal(String diskon_nominal) {
            this.diskon_nominal = diskon_nominal;
        }

        public String getId_pajak() {
            return id_pajak;
        }

        public void setId_pajak(String id_pajak) {
            this.id_pajak = id_pajak;
        }

        public String getNama_pajak() {
            return nama_pajak;
        }

        public void setNama_pajak(String nama_pajak) {
            this.nama_pajak = nama_pajak;
        }

        public String getNilai_pajak() {
            return nilai_pajak;
        }

        public void setNilai_pajak(String nilai_pajak) {
            this.nilai_pajak = nilai_pajak;
        }

        public String getId_service() {
            return id_service;
        }

        public void setId_service(String id_service) {
            this.id_service = id_service;
        }

        public String getNilai_service() {
            return nilai_service;
        }

        public void setNilai_service(String nilai_service) {
            this.nilai_service = nilai_service;
        }

        public String getId_gudang() {
            return id_gudang;
        }

        public void setId_gudang(String id_gudang) {
            this.id_gudang = id_gudang;
        }

        public String getNama_gudang() {
            return nama_gudang;
        }

        public void setNama_gudang(String nama_gudang) {
            this.nama_gudang = nama_gudang;
        }

        public String getKeterangan() {
            return keterangan;
        }

        public void setKeterangan(String keterangan) {
            this.keterangan = keterangan;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

    }
}
