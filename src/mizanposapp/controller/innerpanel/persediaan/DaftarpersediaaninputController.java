/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.persediaan;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import mizanposapp.controller.innerpanel.SatuanEntity;
import mizanposapp.helper.FuncHelper;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Globalsession;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Errorpanel;
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.Popupcaripersediaan;
import mizanposapp.view.innerpanel.persediaan.Daftarpersediaan_input_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class DaftarpersediaaninputController {

    ArrayList<multisatuan> multisatuanlist = new ArrayList<>();
    ArrayList<multiharga> multihargalist = new ArrayList<>();
    ArrayList<multilokasi> multilokasilist = new ArrayList<>();
    String id = "";
    CrudHelper ch = new CrudHelper();
    Daftarpersediaan_input_panel pane;
    String valkelompok, valsupplier, valmerek, valsatuan, valgudang, vallokasi, valdepartment,
         valpajakpenjualan, valpajakpembelian, valservice, metodehpp;
    String valakun_persediaan, valakun_pendapatan, valakun_hpp, valakun_retur_pembelian, valakun_retur_penjualan, valakun_pembelian;
    DefaultTableModel dtmmultisatuan = new DefaultTableModel();
    DefaultTableModel dtmmultihargajual = new DefaultTableModel();
    DefaultTableModel dtmmultilokasi = new DefaultTableModel();
    int col = 0;
    int row = 0;
    Object[] rowmultisatuan = new Object[4];
    Object[] rowmultiharga = new Object[6];
    Object[] rowmultilokasi = new Object[2];
    private boolean ischangevalue = false;
    static boolean sudahterpanggil = false;
    static boolean sudah_jangan_set_lagi_kau_membuat_semua_kacau = false;
    int no_urut;
    JComboBox<String> comboBox = new JComboBox<>();
    ArrayList<SatuanEntity> lssatuanentity = new ArrayList<>();

    public DaftarpersediaaninputController(Daftarpersediaan_input_panel pane) {
        this.pane = pane;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JDialog jdin = (JDialog) pane.getRootPane().getParent();
                jdin.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        pane.bbatal.doClick();
                    }

                });
            }
        });
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                pane.ednama_persediaan.requestFocus();
            }
        });
        userakses();
        customtablesatuan();
        customtableharga();
        customtablelokasi();
        loaddata();
        carikelompokpersediaan();
        carisupplier();
        carimerek();
        carisatuan();
        carilokasi();
        caridepartment();
        caripajakpembelian();
        caripajakpenjualan();
        carigudang();
        cariservice();
        simpandata();
        tutup();
        multisatuanedit();
        multihargaedit();
        multilokasiedit();
        checkcontrol();
        changepane();
        cariakunpersediaan();
        cariakunpendapatan();
        cariakunhpp();
        cariakunpembelian();
        cariakunreturpembelian();
        cariakunreturpenjualan();

    }

    private void userakses() {
        if (Globalsession.persediaan_rubah_harga_jual.equals("1")) {
            pane.edharga_jual.setEnabled(true);
        } else {
            pane.edharga_jual.setEnabled(false);
        }

        if (Globalsession.persediaan_tampilkan_modal.equals("1")) {
            pane.edharga_beli_akhir.setVisible(true);
            pane.lharga_pokok.setVisible(true);
        } else {
            pane.edharga_beli_akhir.setVisible(false);
            pane.lharga_pokok.setVisible(false);
        }
    }

    private void getkode() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                HashMap hm = new FuncHelper().getkodetransaksi("5001", new Date(), Globalsession.Setting_DeptDefault);
                pane.edkode_persediaan.setText(String.valueOf(hm.get("no_transaksi")));
                no_urut = FuncHelper.ToInt(String.valueOf(hm.get("no_urut")));
            }
        });

    }

    private void checkcontrol() {
        pane.ckharga_jual_persen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pane.ckharga_jual_persen.isSelected()) {
                    TableColumn col = pane.tablemulti_harga_jual.getColumnModel().getColumn(4);
                    col.setMinWidth(0);
                    col.setMaxWidth(0);
                    col.setWidth(0);
                    col.setPreferredWidth(0);
                    TableColumn col2 = pane.tablemulti_harga_jual.getColumnModel().getColumn(5);
                    col2.setMinWidth(100);
                    col2.setMaxWidth(100);
                    col2.setWidth(100);
                    col2.setPreferredWidth(100);
                    pane.lhargaberdasar.setVisible(true);
                    pane.cmbharga_berdasarkan.setVisible(true);
                } else {
                    TableColumn col = pane.tablemulti_harga_jual.getColumnModel().getColumn(4);
                    col.setMinWidth(100);
                    col.setMaxWidth(100);
                    col.setWidth(100);
                    col.setPreferredWidth(100);
                    TableColumn col2 = pane.tablemulti_harga_jual.getColumnModel().getColumn(5);
                    col2.setMinWidth(0);
                    col2.setMaxWidth(0);
                    col2.setWidth(0);
                    col2.setPreferredWidth(0);
                    pane.lhargaberdasar.setVisible(false);
                    pane.cmbharga_berdasarkan.setVisible(false);
                }
            }
        });
    }

    private void customtablesatuan() {
        pane.tablemulti_satuan.setRowSelectionAllowed(false);
        pane.tablemulti_satuan.setCellSelectionEnabled(true);
        pane.tablemulti_satuan.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        dtmmultisatuan = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 3 ? false : true;
            }

        };

        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (pane.tablemulti_satuan.isEditing()) {
                    pane.tablemulti_satuan.getCellEditor().cancelCellEditing();
                }
            }

        };

        pane.addMouseListener(ma);

        TableCellEditor tce = new DefaultCellEditor(new JTextField()) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                delegate.setValue((editorComponent instanceof JTextField) ? null : value);
                return editorComponent;
            }

        };

        pane.tablemulti_satuan.setDefaultEditor(Object.class, tce);
        String keyholdnumeric[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9",
            "0", "NUMPAD1", "NUMPAD2", "NUMPAD3", "NUMPAD4", "NUMPAD5", "NUMPAD6", "NUMPAD7", "NUMPAD8", "NUMPAD9",
            "NUMPAD0", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
            "q", "r", "s", "t", "u", "p", "w", "x", "y", "z", "A", "B", "C", "D",
            "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "P", "W", "X", "Y", "Z", "BACK_SPACE", ",", ".",};
        for (int i = 0; i < keyholdnumeric.length; i++) {
            pane.tablemulti_satuan.getInputMap().put(KeyStroke.getKeyStroke(keyholdnumeric[i]), "startEditing");
        }

    }

    private void customtableharga() {
        pane.tablemulti_harga_jual.setRowSelectionAllowed(false);
        pane.tablemulti_harga_jual.setCellSelectionEnabled(true);
        pane.tablemulti_harga_jual.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        dtmmultihargajual = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 3 ? false : true;
            }

        };

        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (pane.tablemulti_harga_jual.isEditing()) {
                    pane.tablemulti_harga_jual.getCellEditor().cancelCellEditing();
                }
            }

        };

        pane.addMouseListener(ma);

        TableCellEditor tce = new DefaultCellEditor(new JTextField()) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                delegate.setValue((editorComponent instanceof JTextField) ? null : value);
                return editorComponent;
            }

        };

        pane.tablemulti_harga_jual.setDefaultEditor(Object.class, tce);
        String keyholdnumeric[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9",
            "0", "NUMPAD1", "NUMPAD2", "NUMPAD3", "NUMPAD4", "NUMPAD5", "NUMPAD6", "NUMPAD7", "NUMPAD8", "NUMPAD9",
            "NUMPAD0", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
            "q", "r", "s", "t", "u", "p", "w", "x", "y", "z", "A", "B", "C", "D",
            "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "P", "W", "X", "Y", "Z", "BACK_SPACE", ",", ".",};
        for (int i = 0; i < keyholdnumeric.length; i++) {
            pane.tablemulti_harga_jual.getInputMap().put(KeyStroke.getKeyStroke(keyholdnumeric[i]), "startEditing");
        }

    }

    private void customtablelokasi() {
        pane.tablemulti_lokasi.setRowSelectionAllowed(false);
        pane.tablemulti_lokasi.setCellSelectionEnabled(true);
        pane.tablemulti_lokasi.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        dtmmultilokasi = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 1 ? false : true;
            }

        };

        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (pane.tablemulti_harga_jual.isEditing()) {
                    pane.tablemulti_harga_jual.getCellEditor().cancelCellEditing();
                }
            }

        };

        pane.addMouseListener(ma);

        TableCellEditor tce = new DefaultCellEditor(new JTextField()) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                delegate.setValue((editorComponent instanceof JTextField) ? null : value);
                return editorComponent;
            }

        };

        pane.tablemulti_lokasi.setDefaultEditor(Object.class, tce);
        String keyholdnumeric[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9",
            "0", "NUMPAD1", "NUMPAD2", "NUMPAD3", "NUMPAD4", "NUMPAD5", "NUMPAD6", "NUMPAD7", "NUMPAD8", "NUMPAD9",
            "NUMPAD0", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
            "q", "r", "s", "t", "u", "p", "w", "x", "y", "z", "A", "B", "C", "D",
            "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "P", "W", "X", "Y", "Z", "BACK_SPACE", ",", ".",};
        for (int i = 0; i < keyholdnumeric.length; i++) {
            pane.tablemulti_lokasi.getInputMap().put(KeyStroke.getKeyStroke(keyholdnumeric[i]), "startEditing");
        }

    }

    private void carikelompokpersediaan() {
        pane.bcari_kelompok.addActionListener((ActionEvent e) -> {
            Staticvar.sfilter = "";
            Staticvar.preid = valkelompok;
            Staticvar.prelabel = pane.edkelompok_persediaan.getText();
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("kelompokbarang", "popupdaftarkelompokpersediaan", "Daftar Kelompok Persediaan"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valkelompok = Staticvar.resid;
            pane.edkelompok_persediaan.setText(Staticvar.reslabel);

            try {
                JSONParser jpdata = new JSONParser();
                String param = String.format("id=%s", valkelompok);
                Object objdata = jpdata.parse(ch.getdatadetails("dm/datakelompokbarang", param));
                JSONArray jadata = (JSONArray) objdata;
                for (int i = 0; i < jadata.size(); i++) {
                    JSONObject joindata = (JSONObject) jadata.get(i);
                    int mhppcek = FuncHelper.ToInt(joindata.get("metode_hpp"));
                    if (mhppcek == 1) {
                        pane.rbfifo.setSelected(true);
                    } else if (mhppcek == 2) {
                        pane.rblifo.setSelected(true);
                    } else {
                        pane.rbaverage.setSelected(true);
                    }
                    pane.edsatuan_persediaan.setText(String.valueOf(joindata.get("nama_satuan")));
                    valsatuan = String.valueOf(joindata.get("id_satuan"));
                    pane.edlokasi_persediaan.setText(String.valueOf(joindata.get("nama_lokasi")));
                    vallokasi = String.valueOf(joindata.get("id_lokasi"));
                    pane.eddepartment_persediaan.setText(String.valueOf(joindata.get("nama_dept")));
                    valdepartment = String.valueOf(joindata.get("id_dept"));
                    pane.edgudang_persediaan.setText(String.valueOf(joindata.get("nama_gudang")));
                    valgudang = String.valueOf(joindata.get("id_gudang"));

                    valakun_persediaan = String.valueOf(joindata.get("akun_persediaan"));
                    valakun_pendapatan = String.valueOf(joindata.get("akun_pendapatan"));
                    valakun_hpp = String.valueOf(joindata.get("akun_hpp"));
                    valakun_retur_pembelian = String.valueOf(joindata.get("akun_retur_pembelian"));
                    valakun_retur_penjualan = String.valueOf(joindata.get("akun_retur_penjualan"));
                    valakun_pembelian = String.valueOf(joindata.get("akun_pembelian"));

                    pane.edakun_persediaan.setText(valakun_persediaan + "-" + String.valueOf(joindata.get("nama_akun_persediaan")));
                    pane.edakun_pendapatan.setText(valakun_pendapatan + "-" + String.valueOf(joindata.get("nama_akun_pendapatan")));
                    pane.edakun_harga_pokok.setText(valakun_hpp + "-" + String.valueOf(joindata.get("nama_akun_hpp")));
                    pane.edakun_pembelian.setText(valakun_pembelian + "-" + String.valueOf(joindata.get("nama_akun_pembelian")));
                    pane.edakun_retur_pembelian.setText(valakun_retur_pembelian + "-" + String.valueOf(joindata.get("nama_akun_retur_pembelian")));
                    pane.edakun_retur_penjualan.setText(valakun_retur_penjualan + "-" + String.valueOf(joindata.get("nama_akun_retur_penjualan")));
                }
            } catch (ParseException ex) {
                Logger.getLogger(DaftarpersediaaninputController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

    }

    private void carisupplier() {
        pane.bcari_supplier.addActionListener((ActionEvent e) -> {
            Staticvar.sfilter = "";
            Staticvar.preid = valsupplier;
            Staticvar.prelabel = pane.edsupplier_persediaan.getText();
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("nama", "popupdaftarnama?tipe=1", "Daftar Supplier"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valsupplier = Staticvar.resid;
            pane.edsupplier_persediaan.setText(Staticvar.reslabel);

        });

    }

    private void carimerek() {
        pane.bcari_merek.addActionListener((ActionEvent e) -> {
            Staticvar.sfilter = "";
            Staticvar.preid = valmerek;
            Staticvar.prelabel = pane.edmerek_persedian.getText();
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("merek", "popupdaftarmerekpersediaan", "Daftar Merek"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valmerek = Staticvar.resid;
            pane.edmerek_persedian.setText(Staticvar.reslabel);

        });

    }

    private void carisatuan() {
        pane.bcari_satuan.addActionListener((ActionEvent e) -> {
            Staticvar.sfilter = "";
            Staticvar.preid = valsatuan;
            Staticvar.prelabel = pane.edsatuan_persediaan.getText();
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("satuan", "popupdaftarsatuan", "Daftar Satuan"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            for (int i = 0; i < multisatuanlist.size(); i++) {
                if (!multisatuanlist.get(i).getId_satuan().equals("")) {
                    multisatuanlist.get(i).setId_satuan_pengali(Staticvar.resid);
                    pane.tablemulti_satuan.setValueAt(Staticvar.reslabel, i, 3);
                }
            }
            for (int i = 0; i < multihargalist.size(); i++) {
                if (!multihargalist.get(i).getId_satuan().equals("")) {
                    if (multihargalist.get(i).getId_satuan().equals(valsatuan)) {
                        multihargalist.get(i).setId_satuan(Staticvar.resid);
                        pane.tablemulti_harga_jual.setValueAt(Staticvar.reslabel, i, 3);
                    }
                }
            }
            valsatuan = Staticvar.resid;
            pane.edsatuan_persediaan.setText(Staticvar.reslabel);

        });

    }

    private void carilokasi() {
        pane.bcari_lokasi.addActionListener((ActionEvent e) -> {
            Staticvar.sfilter = "";
            Staticvar.preid = vallokasi;
            Staticvar.prelabel = pane.edlokasi_persediaan.getText();
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("lokasi", "popupdaftarlokasipersediaan", "Daftar Lokasi Persediaan"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            vallokasi = Staticvar.resid;
            pane.edlokasi_persediaan.setText(Staticvar.reslabel);

        });

    }

    private void caridepartment() {
        pane.bcari_department.addActionListener((ActionEvent e) -> {
            Staticvar.sfilter = "";
            Staticvar.preid = valdepartment;
            Staticvar.prelabel = pane.eddepartment_persediaan.getText();
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("department", "popupdaftardept", "Daftar Department"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valdepartment = Staticvar.resid;
            pane.eddepartment_persediaan.setText(Staticvar.reslabel);

        });

    }

    private void caripajakpenjualan() {
        pane.bcari_pajak_penjualan.addActionListener((ActionEvent e) -> {
            Staticvar.sfilter = "";
            Staticvar.preid = valpajakpenjualan;
            Staticvar.prelabel = pane.edpajak_penjualan.getText();
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("pajak", "popupdaftarpajak", "Daftar Pajak Penjualan"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valpajakpenjualan = Staticvar.resid;
            pane.edpajak_penjualan.setText(Staticvar.reslabel);

        });

    }

    private void caripajakpembelian() {
        pane.bcari_pajak_pembelian.addActionListener((ActionEvent e) -> {
            Staticvar.sfilter = "";
            Staticvar.preid = valpajakpembelian;
            Staticvar.prelabel = pane.edpajak_pembelian.getText();
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("pajak", "popupdaftarpajak", "Daftar Pajak Pembelian"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valpajakpembelian = Staticvar.resid;
            pane.edpajak_pembelian.setText(Staticvar.reslabel);

        });

    }

    private void cariservice() {
        pane.bcari_service.addActionListener((ActionEvent e) -> {
            Staticvar.sfilter = "";
            Staticvar.preid = valservice;
            Staticvar.prelabel = pane.edservice.getText();
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("service", "popupdaftarservice", "Daftar Service"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valservice = Staticvar.resid;
            pane.edservice.setText(Staticvar.reslabel);

        });

    }

    private void carigudang() {
        pane.bcari_gudang.addActionListener((ActionEvent e) -> {
            Staticvar.sfilter = "";
            Staticvar.preid = valgudang;
            Staticvar.prelabel = pane.edgudang_persediaan.getText();
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("gudang", "popupdaftargudang", "Daftar Gudang"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valgudang = Staticvar.resid;
            pane.edgudang_persediaan.setText(Staticvar.reslabel);

        });

    }

    private void loaddata() {
        try {
            id = Staticvar.ids;
            if (id.equals("")) {
                getkode();
                pane.ckaktif.setSelected(true);
                pane.edkode_persediaan.setText("");
                pane.ednama_persediaan.setText("");
                //pane.edkelompok_persediaan.setText(Globalsession.DEFAULT_NAMA_KELOMPOK);
                //valkelompok = Globalsession.DEFAULT_ID_KELOMPOK;
                pane.edsupplier_persediaan.setText("");
                valsupplier = "";
                pane.edketerangan_persediaan.setText("");
                pane.edmerek_persedian.setText("");
                pane.edsatuan_persediaan.setText("");
                pane.edlokasi_persediaan.setText("");
                pane.eddepartment_persediaan.setText("");
                pane.edgudang_persediaan.setText("");
                pane.rbaverage.setSelected(true);

                valpajakpenjualan = Globalsession.DEFAULT_ID_PAJAK;
                pane.edpajak_penjualan.setText(Globalsession.DEFAULT_KODE_PAJAK);
                valpajakpembelian = Globalsession.DEFAULT_ID_PAJAK;
                pane.edpajak_pembelian.setText(Globalsession.DEFAULT_KODE_PAJAK);

                pane.edservice.setText(Globalsession.DEFAULT_KODE_SERVICE);
                valservice = Globalsession.DEFAULT_ID_SERVICE;
                pane.edstock_minimal.setText("");
                pane.edharga_beli_akhir.setText("");
                pane.edharga_jual.setText("");
                pane.edharga_master.setText("");
                pane.edupharga_beli.setText("0");

                dtmmultisatuan.addColumn("Satuan");
                dtmmultisatuan.addColumn("Kode Barcode");
                dtmmultisatuan.addColumn("Isi Persatuan");
                dtmmultisatuan.addColumn("Satuan Pengali");
                multisatuanlist.add(new multisatuan("", "", "", "", "", "", ""));
                dtmmultisatuan.addRow(rowmultisatuan);
                pane.tablemulti_satuan.setModel(dtmmultisatuan);

                dtmmultihargajual.addColumn("Golongan");
                dtmmultihargajual.addColumn("Dari");
                dtmmultihargajual.addColumn("Hingga");
                dtmmultihargajual.addColumn("Satuan");
                dtmmultihargajual.addColumn("Harga Jual");
                dtmmultihargajual.addColumn("Harga Jual %");
                multihargalist.add(new multiharga("", "", "", "0", "0", "", "", "0", "0", "", "0"));
                dtmmultihargajual.addRow(rowmultiharga);
                pane.tablemulti_harga_jual.setModel(dtmmultihargajual);

                dtmmultilokasi.addColumn("Nama Lokasi");
                dtmmultilokasi.addColumn("Nama Gudang");
                multilokasilist.add(new multilokasi("", "", "", "", ""));
                dtmmultilokasi.addRow(rowmultilokasi);
                pane.tablemulti_lokasi.setModel(dtmmultilokasi);

                pane.ckharga_jual_persen.setSelected(false);
                TableColumn col = pane.tablemulti_harga_jual.getColumnModel().getColumn(4);
                col.setMinWidth(100);
                col.setMaxWidth(100);
                col.setWidth(100);
                col.setPreferredWidth(100);
                TableColumn col2 = pane.tablemulti_harga_jual.getColumnModel().getColumn(5);
                col2.setMinWidth(0);
                col2.setMaxWidth(0);
                col2.setWidth(0);
                col2.setPreferredWidth(0);
                pane.lhargaberdasar.setVisible(false);
                pane.cmbharga_berdasarkan.setVisible(false);

            } else {
                JSONParser jpdata = new JSONParser();
                String param = String.format("id=%s", id);
                Object rawobjdata = jpdata.parse(ch.getdatadetails("dm/datapersediaan", param));
                JSONObject jsonobjdata = (JSONObject) rawobjdata;
                Object objdata = jsonobjdata.get("data");
                JSONArray jadata = (JSONArray) objdata;
                for (int i = 0; i < jadata.size(); i++) {
                    JSONObject joindata = (JSONObject) jadata.get(i);
                    metodehpp = String.valueOf(joindata.get("metode_hpp"));
                    if (metodehpp.equals("1")) {
                        pane.rbfifo.setSelected(true);
                    } else if (metodehpp.equals("2")) {
                        pane.rblifo.setSelected(true);
                    } else {
                        pane.rbaverage.setSelected(true);
                    }
                    pane.ckaktif.setSelected(true);
                    pane.edkode_persediaan.setText(String.valueOf(joindata.get("kode")));
                    pane.ednama_persediaan.setText(String.valueOf(joindata.get("nama")));
                    pane.edkelompok_persediaan.setText(String.valueOf(joindata.get("nama_kelompok")));
                    valkelompok = String.valueOf(joindata.get("id_kelompok"));
                    pane.edsupplier_persediaan.setText(String.valueOf(joindata.get("nama_supplier")));
                    valsupplier = String.valueOf(joindata.get("id_supplier"));
                    pane.edketerangan_persediaan.setText(String.valueOf(joindata.get("keterangan")));
                    pane.edmerek_persedian.setText(String.valueOf(joindata.get("nama_merek")));
                    valmerek = String.valueOf(joindata.get("id_merek"));
                    pane.edsatuan_persediaan.setText(String.valueOf(joindata.get("nama_satuan")));
                    valsatuan = String.valueOf(joindata.get("id_satuan"));
                    pane.edlokasi_persediaan.setText(String.valueOf(joindata.get("nama_lokasi")));
                    vallokasi = String.valueOf(joindata.get("id_lokasi"));
                    pane.eddepartment_persediaan.setText(String.valueOf(joindata.get("nama_dept")));
                    valdepartment = String.valueOf(joindata.get("id_dept"));
                    pane.edgudang_persediaan.setText(String.valueOf(joindata.get("nama_gudang")));
                    valgudang = String.valueOf(joindata.get("id_gudang"));
                    pane.edpajak_penjualan.setText(String.valueOf(joindata.get("kode_pajak_jual")));
                    valpajakpenjualan = String.valueOf(joindata.get("id_pajak_jual"));
                    pane.edpajak_pembelian.setText(String.valueOf(joindata.get("kode_pajak_beli")));
                    valpajakpembelian = String.valueOf(joindata.get("id_pajak_beli"));
                    valservice = String.valueOf(joindata.get("id_service"));
                    pane.edservice.setText(String.valueOf(joindata.get("kode_service")));
                    pane.edstock_minimal.setText(String.valueOf(joindata.get("stok_minimum")));
                    pane.edharga_beli_akhir.setText(String.valueOf(joindata.get("harga_beli")));
                    pane.edharga_jual.setText(String.valueOf(joindata.get("harga_jual")));
                    pane.edharga_master.setText(String.valueOf(joindata.get("harga_master")));
                    pane.edupharga_beli.setText("0");

                    valakun_persediaan = String.valueOf(joindata.get("akun_persediaan"));
                    valakun_pendapatan = String.valueOf(joindata.get("akun_pendapatan"));
                    valakun_hpp = String.valueOf(joindata.get("akun_hpp"));
                    valakun_retur_pembelian = String.valueOf(joindata.get("akun_retur_pembelian"));
                    valakun_retur_penjualan = String.valueOf(joindata.get("akun_retur_penjualan"));
                    valakun_pembelian = String.valueOf(joindata.get("akun_pembelian"));

                    pane.edakun_persediaan.setText(valakun_persediaan + "-" + String.valueOf(joindata.get("nama_akun_persediaan")));
                    pane.edakun_pendapatan.setText(valakun_pendapatan + "-" + String.valueOf(joindata.get("nama_akun_pendapatan")));
                    pane.edakun_harga_pokok.setText(valakun_hpp + "-" + String.valueOf(joindata.get("nama_akun_hpp")));
                    pane.edakun_pembelian.setText(valakun_pembelian + "-" + String.valueOf(joindata.get("nama_akun_pembelian")));
                    pane.edakun_retur_pembelian.setText(valakun_retur_pembelian + "-" + String.valueOf(joindata.get("nama_akun_retur_pembelian")));
                    pane.edakun_retur_penjualan.setText(valakun_retur_penjualan + "-" + String.valueOf(joindata.get("nama_akun_retur_penjualan")));

                    int iscek = Integer.parseInt(String.valueOf(joindata.get("isaktif")));
                    if (iscek == 1) {
                        pane.ckaktif.setSelected(true);
                    } else {
                        pane.ckaktif.setSelected(false);
                    }
                    int iscekhppsamadenganjual = Integer.parseInt(String.valueOf(joindata.get("ishppsamadenganhargajual")));
                    if (iscekhppsamadenganjual == 1) {
                        pane.ckhpp.setSelected(true);
                    } else {
                        pane.ckhpp.setSelected(false);
                    }
                    int ishargapersen = Integer.parseInt(String.valueOf(joindata.get("ishargajualpersen")));
                    if (ishargapersen == 1) {
                        pane.ckharga_jual_persen.setSelected(true);
                    } else {
                        pane.ckharga_jual_persen.setSelected(false);
                    }

                    int valhargajualberdasar = Integer.parseInt(String.valueOf(joindata.get("harga_jual_berdasar")));

                    if (valhargajualberdasar == 1) {
                        pane.cmbharga_berdasarkan.setSelectedIndex(0);
                    } else if (valhargajualberdasar == 2) {
                        pane.cmbharga_berdasarkan.setSelectedIndex(1);
                    } else {
                        pane.cmbharga_berdasarkan.setSelectedIndex(2);
                    }

                }

                //multisatuan
                dtmmultisatuan.addColumn("Satuan");
                dtmmultisatuan.addColumn("Kode Barcode");
                dtmmultisatuan.addColumn("Isi Persatuan");
                dtmmultisatuan.addColumn("Satuan Pengali");
                Object objmultisatuan = jsonobjdata.get("datamultisatuan");
                //System.out.println(objmultisatuan);
                JSONArray jamultisatuan = (JSONArray) objmultisatuan;
                if (jamultisatuan.isEmpty()) {
                    multisatuanlist.add(new multisatuan("", "", "", "", "", "", ""));
                    dtmmultisatuan.addRow(rowmultisatuan);
                } else {
                    for (int i = 0; i < jamultisatuan.size(); i++) {
                        JSONObject joinmultisatuan = (JSONObject) jamultisatuan.get(i);
                        String id = String.valueOf(joinmultisatuan.get("id"));
                        String id_satuan = String.valueOf(joinmultisatuan.get("id_satuan"));
                        String kode_satuan = String.valueOf(joinmultisatuan.get("kode_satuan"));
                        String barcode = String.valueOf(joinmultisatuan.get("barcode"));
                        String id_satuan_pengali = String.valueOf(joinmultisatuan.get("id_satuan_pengali"));
                        String kode_satuan_pengali = String.valueOf(joinmultisatuan.get("kode_satuan_pengali"));
                        String qty_satuan_pengali = String.valueOf(joinmultisatuan.get("qty_satuan_pengali"));
                        multisatuanlist.add(new multisatuan(id, id_satuan, kode_satuan, barcode, id_satuan_pengali, kode_satuan_pengali, qty_satuan_pengali));
                    }
                    for (int i = 0; i < multisatuanlist.size(); i++) {
                        rowmultisatuan[0] = multisatuanlist.get(i).getKode_satuan();
                        rowmultisatuan[1] = multisatuanlist.get(i).getBarcode();
                        rowmultisatuan[2] = multisatuanlist.get(i).getQty_satuan_pengali();
                        rowmultisatuan[3] = multisatuanlist.get(i).getKode_satuan_pengali();
                        dtmmultisatuan.addRow(rowmultisatuan);
                    }
                }
                pane.tablemulti_satuan.setModel(dtmmultisatuan);

                //multiharga
                dtmmultihargajual.addColumn("Golongan");
                dtmmultihargajual.addColumn("Dari");
                dtmmultihargajual.addColumn("Hingga");
                dtmmultihargajual.addColumn("Satuan");
                dtmmultihargajual.addColumn("Harga Jual");
                dtmmultihargajual.addColumn("Harga Jual Persen");
                Object objmultiharga = jsonobjdata.get("datamultiharga");
                System.out.println(objmultiharga);
                JSONArray jamultiharga = (JSONArray) objmultiharga;
                if (jamultiharga.isEmpty()) {
                    multihargalist.add(new multiharga("", "", "", "0", "0", "", "", "", "", "", "0"));
                    dtmmultihargajual.addRow(rowmultiharga);
                } else {
                    for (int i = 0; i < jamultiharga.size(); i++) {
                        JSONObject joinmultiharga = (JSONObject) jamultiharga.get(i);
                        String id = String.valueOf(joinmultiharga.get("id"));
                        String id_golongan = String.valueOf(joinmultiharga.get("id_golongan"));
                        String kode_golongan = String.valueOf(joinmultiharga.get("kode_golongan"));
                        String dari = String.valueOf(joinmultiharga.get("dari"));
                        String hingga = String.valueOf(joinmultiharga.get("hingga"));
                        String id_satuan = String.valueOf(joinmultiharga.get("id_satuan"));
                        String kode_satuan = String.valueOf(joinmultiharga.get("kode_satuan"));
                        String harga_jual = String.valueOf(joinmultiharga.get("harga_jual"));
                        String harga_jual_persen = String.valueOf(joinmultiharga.get("harga_jual_persen"));
                        String id_satuan_pengali = String.valueOf(joinmultiharga.get("id_satuan_pengali"));
                        String qty_satuan_pengali = String.valueOf(joinmultiharga.get("qty_satuan_pengali"));
                        multihargalist.add(new multiharga(id, id_golongan, kode_golongan, dari, hingga,
                             id_satuan, kode_satuan, harga_jual, harga_jual_persen, id_satuan_pengali,
                             qty_satuan_pengali));

                    }
                    for (int i = 0; i < multihargalist.size(); i++) {
                        rowmultiharga[0] = multihargalist.get(i).getKode_golongan();
                        rowmultiharga[1] = multihargalist.get(i).getDari();
                        rowmultiharga[2] = multihargalist.get(i).getHingga();
                        rowmultiharga[3] = multihargalist.get(i).getKode_satuan();
                        rowmultiharga[4] = multihargalist.get(i).getHarga_jual();
                        rowmultiharga[5] = multihargalist.get(i).getHarga_jual_persen();
                        dtmmultihargajual.addRow(rowmultiharga);
                    }
                }
                pane.tablemulti_harga_jual.setModel(dtmmultihargajual);

                //multilokasi
                dtmmultilokasi.addColumn("Nama Lokasi");
                dtmmultilokasi.addColumn("Nama Gudang");
                Object objmultilokasi = jsonobjdata.get("datamultilokasi");
                JSONArray jamultilokasi = (JSONArray) objmultilokasi;
                if (jamultilokasi.isEmpty()) {
                    multilokasilist.add(new multilokasi("", "", "", "", ""));
                    dtmmultilokasi.addRow(rowmultilokasi);
                } else {
                    for (int i = 0; i < jamultilokasi.size(); i++) {
                        JSONObject joinmultilokasi = (JSONObject) jamultilokasi.get(i);
                        String id = String.valueOf(joinmultilokasi.get("id"));
                        String id_lokasi = String.valueOf(joinmultilokasi.get("id_lokasi"));
                        String nama_lokasi = String.valueOf(joinmultilokasi.get("nama_lokasi"));
                        String id_gudang = String.valueOf(joinmultilokasi.get("id_gudang"));
                        String nama_gudang = String.valueOf(joinmultilokasi.get("nama_gudang"));
                        multilokasilist.add(new multilokasi(id, id_lokasi, nama_lokasi, id_gudang, nama_gudang));
                    }

                    for (int i = 0; i < multilokasilist.size(); i++) {
                        rowmultilokasi[0] = multilokasilist.get(i).getNama_gudang();
                        rowmultilokasi[1] = multilokasilist.get(i).getNama_lokaksi();
                        dtmmultilokasi.addRow(rowmultilokasi);
                    }
                }

                pane.tablemulti_lokasi.setModel(dtmmultilokasi);

                for (int i = 0; i < rowmultilokasi.length; i++) {
                    rowmultilokasi[i] = "";
                }

                for (int i = 0; i < rowmultiharga.length; i++) {
                    rowmultiharga[i] = "";
                }

                for (int i = 0; i < rowmultisatuan.length; i++) {
                    rowmultisatuan[i] = "";
                }

                if (pane.ckharga_jual_persen.isSelected()) {
                    TableColumn col = pane.tablemulti_harga_jual.getColumnModel().getColumn(4);
                    col.setMinWidth(0);
                    col.setMaxWidth(0);
                    col.setWidth(0);
                    col.setPreferredWidth(0);
                    TableColumn col2 = pane.tablemulti_harga_jual.getColumnModel().getColumn(5);
                    col2.setMinWidth(100);
                    col2.setMaxWidth(100);
                    col2.setWidth(100);
                    col2.setPreferredWidth(100);
                    pane.lhargaberdasar.setVisible(true);
                    pane.cmbharga_berdasarkan.setVisible(true);
                } else {
                    TableColumn col = pane.tablemulti_harga_jual.getColumnModel().getColumn(4);
                    col.setMinWidth(100);
                    col.setMaxWidth(100);
                    col.setWidth(100);
                    col.setPreferredWidth(100);
                    TableColumn col2 = pane.tablemulti_harga_jual.getColumnModel().getColumn(5);
                    col2.setMinWidth(0);
                    col2.setMaxWidth(0);
                    col2.setWidth(0);
                    col2.setPreferredWidth(0);
                    pane.lhargaberdasar.setVisible(false);
                    pane.cmbharga_berdasarkan.setVisible(false);
                }

            }
        } catch (ParseException ex) {
            Logger.getLogger(DaftarserviceinnerinputController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void rawsimpan(String ckval) {
        if (pane.ckaktif.isSelected() == true) {
            ckval = "1";
        } else {
            ckval = "0";
        }
        if (pane.rbfifo.isSelected() == true) {
            metodehpp = "1";
        } else if (pane.rblifo.isSelected() == true) {
            metodehpp = "2";
        } else {
            metodehpp = "3";
        }
        int harga_berdasar = 0;
        int tipe_harga_jual = 0;
        if (pane.ckharga_jual_persen.isSelected()) {
            tipe_harga_jual = 1;
        } else {
            tipe_harga_jual = 0;
        }

        switch (pane.cmbharga_berdasarkan.getSelectedIndex()) {
            case 0:
                harga_berdasar = 1;
                break;
            case 1:
                harga_berdasar = 2;
                break;
            case 2:
                harga_berdasar = 3;
                break;
            default:
                harga_berdasar = 0;
                break;
        }

        if (id.equals(
             "")) {
            String data = String.format("data=kode='%s'::"
                 + "nama='%s'::"
                 + "id_kelompok='%s'::"
                 + "id_satuan='%s'::"
                 + "id_gudang='%s'::"
                 + "id_dept='%s'::"
                 + "metode_hpp='%s'::"
                 + "stok_minimum='%s'::"
                 + "harga_beli='%s'::"
                 + "harga_jual='%s'::"
                 + "harga_master='%s'::"
                 + "id_pajak_beli='%s'::"
                 + "id_pajak_jual='%s'::"
                 + "id_currency='%s'::"
                 + "gambar='%s'::"
                 + "isaktif='%s'::"
                 + "id_lokasi='%s'::"
                 + "id_merek='%s'::"
                 + "id_supplier='%s'::"
                 + "keterangan='%s'::"
                 + "ishargajualdarigol='%s'::"
                 + "ishargajualpersen='%s'::"
                 + "ishppsamadenganhargajual='%s'::"
                 + "id_service='%s'::"
                 + "akun_persediaan='%s'::"
                 + "akun_pendapatan='%s'::"
                 + "akun_hpp='%s'::"
                 + "akun_pembelian='%s'::"
                 + "akun_retur_pembelian='%s'::"
                 + "akun_retur_penjualan='%s'::"
                 + "harga_jual_berdasar='%s'&" + kirimtextsatuan(0) + "&" + kirimtextharga(0) + "&" + kirimtextlokasi(0),
                 FuncHelper.EncodeString(pane.edkode_persediaan.getText()),
                 FuncHelper.EncodeString(pane.ednama_persediaan.getText()),
                 valkelompok,
                 valsatuan,
                 valgudang,
                 valdepartment,
                 metodehpp,
                 pane.edstock_minimal.getText(),
                 pane.edharga_beli_akhir.getText(),
                 pane.edharga_jual.getText(),
                 pane.edharga_master.getText(),
                 valpajakpembelian,
                 valpajakpenjualan,
                 "1-1",
                 "",
                 ckval,
                 vallokasi,
                 valmerek,
                 valsupplier,
                 FuncHelper.EncodeString(pane.edketerangan_persediaan.getText()),
                 "0",
                 tipe_harga_jual,
                 "0",
                 valservice,
                 valakun_persediaan,
                 valakun_pendapatan,
                 valakun_hpp,
                 valakun_pembelian,
                 valakun_retur_pembelian,
                 valakun_retur_penjualan,
                 String.valueOf(harga_berdasar));

            ch.insertdata("dm/insertpersediaan", data);
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
                JDialog jd = (JDialog) pane.getRootPane().getParent();
                jd.dispose();
            }
        } else {
            String data = String.format("data=kode='%s'::"
                 + "nama='%s'::"
                 + "id_kelompok='%s'::"
                 + "id_satuan='%s'::"
                 + "id_gudang='%s'::"
                 + "id_dept='%s'::"
                 + "metode_hpp='%s'::"
                 + "stok_minimum='%s'::"
                 + "harga_beli='%s'::"
                 + "harga_jual='%s'::"
                 + "harga_master='%s'::"
                 + "id_pajak_beli='%s'::"
                 + "id_pajak_jual='%s'::"
                 + "id_currency='%s'::"
                 + "gambar='%s'::"
                 + "isaktif='%s'::"
                 + "id_lokasi='%s'::"
                 + "id_merek='%s'::"
                 + "id_supplier='%s'::"
                 + "keterangan='%s'::"
                 + "ishargajualdarigol='%s'::"
                 + "ishargajualpersen='%s'::"
                 + "ishppsamadenganhargajual='%s'::"
                 + "id_service='%s'::"
                 + "akun_persediaan='%s'::"
                 + "akun_pendapatan='%s'::"
                 + "akun_hpp='%s'::"
                 + "akun_pembelian='%s'::"
                 + "akun_retur_pembelian='%s'::"
                 + "akun_retur_penjualan='%s'::"
                 + "harga_jual_berdasar='%s'&" + kirimtextsatuan(0) + "&" + kirimtextharga(0) + "&" + kirimtextlokasi(0),
                 FuncHelper.EncodeString(pane.edkode_persediaan.getText()),
                 FuncHelper.EncodeString(pane.ednama_persediaan.getText()),
                 valkelompok,
                 valsatuan,
                 valgudang,
                 valdepartment,
                 metodehpp,
                 pane.edstock_minimal.getText(),
                 pane.edharga_beli_akhir.getText(),
                 pane.edharga_jual.getText(),
                 pane.edharga_master.getText(),
                 valpajakpembelian,
                 valpajakpenjualan,
                 "1-1",
                 "",
                 ckval,
                 vallokasi,
                 valmerek,
                 valsupplier,
                 FuncHelper.EncodeString(pane.edketerangan_persediaan.getText()),
                 "0",
                 tipe_harga_jual,
                 "0",
                 valservice,
                 valakun_persediaan,
                 valakun_pendapatan,
                 valakun_hpp,
                 valakun_pembelian,
                 valakun_retur_pembelian,
                 valakun_retur_penjualan,
                 String.valueOf(harga_berdasar));
            ch.updatedata("dm/updatepersediaan", data, id);
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
                JDialog jd = (JDialog) pane.getRootPane().getParent();
                jd.dispose();
            }
        }
    }

    private void simpandata() {
        pane.bsimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Staticvar.isupdate = true;
                String ckval = "";
                rawsimpan(ckval);
            }
        });
    }

    private void tutup() {
        pane.bbatal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        if (id.equals("")) {
                            new FuncHelper().insertnogagal("5001", new Date(), Globalsession.Setting_DeptDefault, String.valueOf(no_urut));
                        }
                        Staticvar.isupdate = false;
                        JDialog jd = (JDialog) pane.getRootPane().getParent();
                        jd.dispose();
                    }
                });

            }
        });
    }

    private void allaction(JTable tabledata, ArrayList ls, String tipe) {
        tabledata.getInputMap().put(KeyStroke.getKeyStroke("DELETE"), "hapus");
        tabledata.getActionMap().put("hapus", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tabledata.getSelectedRow();
                int dialog = JOptionPane.showConfirmDialog(null,
                     "Yakin akan menghapus " + tabledata.getValueAt(row, 0) + " - "
                     + tabledata.getValueAt(row, 1),
                     "Konfirmasi", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (dialog == 0) {
                    Runnable rn = new Runnable() {
                        @Override
                        public void run() {

                            if (tipe.equals("satuan")) {
                                String idsatuanterhapus = multisatuanlist.get(row).getId_satuan();
                                int counthargaindex = 0;
                                if (multihargalist.get(multihargalist.size() - 1).getId_satuan().equals("")) {
                                    counthargaindex = multihargalist.size() - 2;
                                } else {
                                    counthargaindex = multihargalist.size() - 1;
                                }

                                while (counthargaindex >= 0) {
                                    if (multihargalist.get(counthargaindex).getId_satuan().equals(idsatuanterhapus)) {
                                        multihargalist.remove(counthargaindex);
                                        dtmmultihargajual.removeRow(counthargaindex);
                                    }

                                    if ((pane.tablemulti_harga_jual.getRowCount()) == 0) {
                                        multihargalist.add(new multiharga("", "", "", "", "", "", "", "0", "0", "", "0"));
                                        dtmmultihargajual.addRow(rowmultiharga);
                                    }
                                    counthargaindex--;
                                }
                            }

                            if ((tabledata.getRowCount()) == 1) {
                                if (tipe.equals("satuan")) {

                                    ls.remove(row);
                                    ((DefaultTableModel) tabledata.getModel()).removeRow(row);

                                    ls.add(new multisatuan("", "", "", "", "", "", ""));
                                    ((DefaultTableModel) tabledata.getModel()).addRow(rowmultisatuan);
                                    tabledata.changeSelection(0, 0, false, false);

                                } else if (tipe.equals("harga")) {

                                    ls.remove(row);
                                    ((DefaultTableModel) tabledata.getModel()).removeRow(row);

                                    ls.add(new multiharga("", "", "", "", "", "", "", "0", "0", "", "0"));
                                    ((DefaultTableModel) tabledata.getModel()).addRow(rowmultiharga);
                                    tabledata.changeSelection(0, 0, false, false);
                                } else {

                                    ls.remove(row);
                                    ((DefaultTableModel) tabledata.getModel()).removeRow(row);

                                    ls.add(new multilokasi("", "", "", "", ""));
                                    ((DefaultTableModel) tabledata.getModel()).addRow(rowmultilokasi);
                                    tabledata.changeSelection(0, 0, false, false);
                                }
                            } else {
                                ls.remove(row);
                                ((DefaultTableModel) tabledata.getModel()).removeRow(row);
                                tabledata.changeSelection(row - 1, 0, false, false);

                            }
                        }
                    };
                    SwingUtilities.invokeLater(rn);
                }
            }
        });

        tabledata.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
        tabledata.getActionMap().put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tabledata.getSelectedRow();
                int col = tabledata.getSelectedColumn();
                if (tabledata.isEditing()) {
                    tabledata.getCellEditor().stopCellEditing();
                }
                tabledata.requestFocus();

                if (tipe.equals("satuan")) {
                    if (col == 3) {
                        if (tabledata.getValueAt(row, 3) == null || tabledata.getValueAt(row, 3).equals("")) {

                        } else {
                            addautorowmultisatuan(row);
                        }
                    } else {
                        tabledata.changeSelection(row, col + 1, false, false);
                    }
                }

                if (tipe.equals("harga")) {
                    if (pane.ckharga_jual_persen.isSelected()) {
                        if (col == 3) {
                            tabledata.changeSelection(row, 5, false, false);
                        } else if (col == 5) {
                            addautorowmultiharga(row);
                        } else {
                            tabledata.changeSelection(row, col + 1, false, false);
                        }
                    } else {
                        if (col == 4) {
                            addautorowmultiharga(row);
                        } else {
                            tabledata.changeSelection(row, col + 1, false, false);
                        }
                    }

                }

                if (tipe.equals("lokasi")) {
                    if (col == 1) {
                        if (tabledata.getValueAt(row, 1) == null || tabledata.getValueAt(row, 1).equals("")) {
                        } else {
                            addautorowmultilokasi(row);
                        }
                    } else {
                        tabledata.changeSelection(row, col + 1, false, false);
                    }
                }
            }

        });

        tabledata.getInputMap()
             .put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
        tabledata.getActionMap()
             .put("left", new AbstractAction() {
                 @Override
                 public void actionPerformed(ActionEvent e
                 ) {
                     int row = tabledata.getSelectedRow();
                     int col = tabledata.getSelectedColumn();
                     if (tabledata.isEditing()) {
                         tabledata.getCellEditor().stopCellEditing();
                     }
                     tabledata.requestFocus();
                     if (tipe.equals("harga")) {
                         if (row > 0) {
                             if (col > 0) {
                                 if (pane.ckharga_jual_persen.isSelected()) {
                                     if (col == 5) {
                                         tabledata.changeSelection(row, 3, false, false);
                                     } else {
                                         tabledata.changeSelection(row, col - 1, false, false);
                                     }
                                 } else {
                                     tabledata.changeSelection(row, col - 1, false, false);
                                 }
                             } else {
                                 if (pane.ckharga_jual_persen.isSelected()) {
                                     tabledata.changeSelection(row - 1, tabledata.getColumnCount() - 1, false, false);
                                 } else {
                                     tabledata.changeSelection(row - 1, tabledata.getColumnCount() - 2, false, false);
                                 }

                             }
                         } else {
                             if (col > 0) {
                                 tabledata.changeSelection(row, col - 1, false, false);
                             }
                         }

                     } else {
                         if (row > 0) {
                             if (col > 0) {
                                 tabledata.changeSelection(row, col - 1, false, false);
                             } else {
                                 tabledata.changeSelection(row - 1, tabledata.getColumnCount() - 1, false, false);
                             }
                         } else {
                             if (col > 0) {
                                 tabledata.changeSelection(row, col - 1, false, false);
                             }
                         }

                     }

                 }
             }
             );

        tabledata.getInputMap()
             .put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");
        tabledata.getActionMap()
             .put("up", new AbstractAction() {
                 @Override
                 public void actionPerformed(ActionEvent e
                 ) {
                     int row = tabledata.getSelectedRow();
                     int col = tabledata.getSelectedColumn();
                     if (tabledata.isEditing()) {
                         tabledata.getCellEditor().stopCellEditing();
                     }

                     if (row == 0) {
                         tabledata.requestFocus();
                         tabledata.changeSelection(row, col, false, false);
                     } else {
                         tabledata.requestFocus();
                         tabledata.changeSelection(row - 1, col, false, false);
                     }

                 }
             }
             );

        tabledata.getInputMap()
             .put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
        tabledata.getActionMap()
             .put("down", new AbstractAction() {
                 @Override
                 public void actionPerformed(ActionEvent e
                 ) {
                     int row = tabledata.getSelectedRow();
                     int col = tabledata.getSelectedColumn();
                     if (tabledata.isEditing()) {
                         tabledata.getCellEditor().stopCellEditing();
                     }
                     if (row == tabledata.getRowCount() - 1) {
                         if (tipe.equals("satuan")) {
                             addautorowmultisatuan(row);
                         } else if (tipe.equals("harga")) {
                             addautorowmultiharga(row);
                         } else {
                             addautorowmultilokasi(row);
                         }
                     } else {
                         tabledata.requestFocus();
                         tabledata.changeSelection(row + 1, col, false, false);
                     }

                 }
             }
             );

        tabledata.getInputMap()
             .put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "tab");
        tabledata.getActionMap()
             .put("tab", new AbstractAction() {
                 @Override
                 public void actionPerformed(ActionEvent e
                 ) {
                     int row = tabledata.getSelectedRow();
                     int col = tabledata.getSelectedColumn();
                     if (tabledata.isEditing()) {
                         tabledata.getCellEditor().stopCellEditing();
                     }
                     tabledata.requestFocus();
                     if (col == 3) {
                         if (tabledata.getValueAt(row, 3).equals("")
                              || tabledata.getValueAt(row, 3).equals("")) {
                         } else {
                             if (tipe.equals("satuan")) {
                                 addautorowmultisatuan(row);
                             } else if (tipe.equals("harga")) {
                                 addautorowmultiharga(row);
                             } else {
                                 addautorowmultilokasi(row);
                             }
                         }
                     } else {
                         tabledata.changeSelection(row, col + 1, false, false);
                     }

                 }
             }
             );

        tabledata.getInputMap()
             .put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.SHIFT_DOWN_MASK), "shift_tab");
        tabledata.getActionMap()
             .put("shift_tab", new AbstractAction() {
                 @Override
                 public void actionPerformed(ActionEvent e
                 ) {
                     int row = tabledata.getSelectedRow();
                     int col = tabledata.getSelectedColumn();
                     if (tabledata.isEditing()) {
                         tabledata.getCellEditor().stopCellEditing();
                     }
                     tabledata.requestFocus();
                     if (col > 0) {
                         tabledata.changeSelection(row, col - 1, false, false);
                     }

                 }
             }
             );
    }

    private void changepane() {

        pane.tabpersediaan.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                switch (pane.tabpersediaan.getSelectedIndex()) {
                    case 0:
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                pane.ednama_persediaan.requestFocus();
                            }
                        });
                        break;
                    case 1:
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                pane.tablemulti_satuan.requestFocus();
                                if (pane.tablemulti_satuan.getRowCount() >= 0) {
                                    pane.tablemulti_satuan.changeSelection(0, 0, false, false);
                                }
                            }
                        });
                        break;
                    case 2:
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                pane.tablemulti_satuan.requestFocus();
                                if (pane.tablemulti_satuan.getRowCount() >= 0) {
                                    pane.tablemulti_satuan.changeSelection(0, 0, false, false);
                                }
                            }
                        });
                        break;
                    case 3:
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                pane.tablemulti_harga_jual.requestFocus();
                                if (pane.tablemulti_harga_jual.getRowCount() >= 0) {
                                    pane.tablemulti_harga_jual.changeSelection(0, 0, false, false);
                                }
                                lssatuanentity.clear();
                                lssatuanentity.add(new SatuanEntity(valsatuan, pane.edsatuan_persediaan.getText(), valsatuan, "1"));
                                for (int i = 0; i < multisatuanlist.size(); i++) {
                                    if (!multisatuanlist.get(i).getId_satuan().equals(valsatuan)) {
                                        if (!multisatuanlist.get(i).getId_satuan().equals("")) {
                                            lssatuanentity.add(new SatuanEntity(
                                                 multisatuanlist.get(i).getId_satuan(),
                                                 multisatuanlist.get(i).getKode_satuan(),
                                                 valsatuan,
                                                 multisatuanlist.get(i).getQty_satuan_pengali()));
                                        }

                                    }
                                }

                            }
                        });
                        break;
                    case 4:
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                pane.tablemulti_lokasi.requestFocus();
                                if (pane.tablemulti_lokasi.getRowCount() >= 0) {
                                    pane.tablemulti_lokasi.changeSelection(0, 0, false, false);
                                }
                            }
                        });
                        break;
                    case 5:
                        break;
                    default:
                        break;
                }
            }
        });

    }

    private void multisatuanedit() {
        pane.tablemulti_satuan.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                TableModel tm = (TableModel) e.getSource();
                if (e.getType() == TableModelEvent.UPDATE) {
                    if (ischangevalue) {
                        return;
                    }
                    int col = e.getColumn();
                    int row = e.getFirstRow();
                    if (col == 0) {

                    } else if (col == 1) {
                        try {
                            ischangevalue = true;
                            multisatuanlist.get(row).setBarcode(String.valueOf(pane.tablemulti_satuan.getValueAt(row, 1)));
                        } catch (Exception ex) {

                        } finally {
                            ischangevalue = false;
                        }

                    } else if (col == 2) {
                        try {
                            ischangevalue = true;
                            multisatuanlist.get(row).setQty_satuan_pengali(String.valueOf(pane.tablemulti_satuan.getValueAt(row, 2)));
                        } catch (Exception ex) {

                        } finally {
                            ischangevalue = false;
                        }
                    } else if (col == 3) {
                        try {
                            ischangevalue = true;
                            //addautorowmultisatuan(row);
                        } catch (Exception ex) {

                        } finally {
                            ischangevalue = false;
                        }
                    }

                    //addautorowmultisatuan(row);
                    ischangevalue = false;

                }
            }

        });
        pane.tablemulti_satuan.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");
        pane.tablemulti_satuan.getActionMap().put("enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = pane.tablemulti_satuan.getSelectedRow();
                int col = pane.tablemulti_satuan.getSelectedColumn();

                if (col == 0) {
                    if (pane.edsatuan_persediaan.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Satuan Belum Ditetukan");
                    } else {
                        Staticvar.preid = multisatuanlist.get(row).getId_satuan();
                        String defnilai = "";
                        String cekval = String.valueOf(pane.tablemulti_satuan.getValueAt(row, col));
                        if (cekval.equals("null") || cekval.equals("")) {
                            defnilai = "";
                        } else {
                            defnilai = String.valueOf(pane.tablemulti_satuan.getValueAt(row, 0));
                        }
                        Staticvar.sfilter = "";
                        Staticvar.prelabel = defnilai;
                        JDialog jd = new JDialog(new Mainmenu());
                        jd.add(new Popupcari("satuan", "popupdaftarsatuan", "Daftar Satuan"));
                        jd.pack();
                        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        jd.setLocationRelativeTo(null);
                        jd.setVisible(true);
                        jd.toFront();
                        boolean status_ada = false;
                        for (int i = 0; i < multisatuanlist.size(); i++) {
                            if (multisatuanlist.get(i).getId_satuan().equals(Staticvar.resid)) {
                                status_ada = true;
                                JOptionPane.showMessageDialog(null, Staticvar.reslabel + " Sudah Ada");
                                break;
                            } else {
                                status_ada = false;
                            }
                        }

                        if (status_ada == false) {
                            multisatuanlist.get(row).setId_satuan(Staticvar.resid);
                            multisatuanlist.get(row).setKode_satuan(Staticvar.reslabel);
                            pane.tablemulti_satuan.setValueAt(Staticvar.reslabel, row, 0);
                            multisatuanlist.get(row).setId_satuan_pengali(valsatuan);
                            pane.tablemulti_satuan.setValueAt(pane.edsatuan_persediaan.getText(), row, 3);
                            int counthargaindex = 0;
                            if (multihargalist.get(multihargalist.size() - 1).getId_satuan().equals("")) {
                                counthargaindex = multihargalist.size() - 1;
                            } else {
                                counthargaindex = multihargalist.size();
                            }

                            for (int i = 0; i < counthargaindex; i++) {
                                if (multihargalist.get(i).getId_satuan().equals(Staticvar.preid)) {
                                    multihargalist.get(i).setId_satuan(Staticvar.resid);
                                    pane.tablemulti_harga_jual.setValueAt(Staticvar.reslabel, i, 3);
                                }
                            }

                        }

                    }
                }

            }
        });
        pane.tablemulti_satuan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = pane.tablemulti_satuan.getSelectedRow();
                int col = pane.tablemulti_satuan.getSelectedColumn();
                if (e.getClickCount() == 2) {
                    if (col == 0) {
                        if (pane.edsatuan_persediaan.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Satuan Belum Ditetukan");
                        } else {
                            Staticvar.preid = multisatuanlist.get(row).getId_satuan();
                            String defnilai = "";
                            String cekval = String.valueOf(pane.tablemulti_satuan.getValueAt(row, col));
                            if (cekval.equals("null") || cekval.equals("")) {
                                defnilai = "";
                            } else {
                                defnilai = String.valueOf(pane.tablemulti_satuan.getValueAt(row, 0));
                            }
                            Staticvar.prelabel = defnilai;
                            Staticvar.sfilter = "";
                            Staticvar.prelabel = defnilai;
                            JDialog jd = new JDialog(new Mainmenu());
                            jd.add(new Popupcari("satuan", "popupdaftarsatuan", "Daftar Satuan"));
                            jd.pack();
                            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                            jd.setLocationRelativeTo(null);
                            jd.setVisible(true);
                            jd.toFront();
                            boolean status_ada = false;
                            for (int i = 0; i < multisatuanlist.size(); i++) {
                                if (multisatuanlist.get(i).getId_satuan().equals(Staticvar.resid)) {
                                    status_ada = true;
                                    JOptionPane.showMessageDialog(null, Staticvar.reslabel + " Sudah Ada");
                                    break;
                                } else {
                                    status_ada = false;
                                }
                            }

                            if (status_ada == false) {
                                multisatuanlist.get(row).setId_satuan(Staticvar.resid);
                                multisatuanlist.get(row).setKode_satuan(Staticvar.reslabel);
                                pane.tablemulti_satuan.setValueAt(Staticvar.reslabel, row, 0);
                                multisatuanlist.get(row).setId_satuan_pengali(valsatuan);
                                pane.tablemulti_satuan.setValueAt(pane.edsatuan_persediaan.getText(), row, 3);
                                int counthargaindex = 0;
                                if (multihargalist.get(multihargalist.size() - 1).getId().equals("")) {
                                    counthargaindex = multihargalist.size() - 1;
                                } else {
                                    counthargaindex = multihargalist.size();
                                }

                                for (int i = 0; i < counthargaindex; i++) {
                                    if (multihargalist.get(i).getId_satuan().equals(Staticvar.preid)) {
                                        multihargalist.get(i).setId_satuan(Staticvar.resid);
                                        pane.tablemulti_harga_jual.setValueAt(Staticvar.reslabel, i, 3);
                                    }
                                }
                            }

                        }
                    }
                }
            }

        });
        allaction(pane.tablemulti_satuan, multisatuanlist, "satuan");

    }

    private void multihargaedit() {
        pane.tablemulti_harga_jual.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                TableModel tm = (TableModel) e.getSource();
                if (e.getType() == TableModelEvent.UPDATE) {
                    if (ischangevalue) {
                        return;
                    }
                    int col = e.getColumn();
                    int row = e.getFirstRow();
                    if (col == 0) {

                    } else if (col == 1) {
                        try {
                            ischangevalue = true;
                            multihargalist.get(row).setDari(String.valueOf(pane.tablemulti_harga_jual.getValueAt(row, 1)));
                        } catch (Exception ex) {

                        } finally {
                            ischangevalue = false;
                        }

                    } else if (col == 2) {
                        try {
                            ischangevalue = true;
                            multihargalist.get(row).setHingga(String.valueOf(pane.tablemulti_harga_jual.getValueAt(row, 2)));
                        } catch (Exception ex) {

                        } finally {
                            ischangevalue = false;
                        }
                    } else if (col == 3) {

                    } else if (col == 4) {
                        try {
                            ischangevalue = true;
                            multihargalist.get(row).setHarga_jual(String.valueOf(pane.tablemulti_harga_jual.getValueAt(row, 4)));
                        } catch (Exception ex) {
                        } finally {
                            ischangevalue = false;
                        }
                    } else if (col == 5) {
                        try {
                            ischangevalue = true;
                            multihargalist.get(row).setHarga_jual_persen(String.valueOf(pane.tablemulti_harga_jual.getValueAt(row, 5)));
                            addautorowmultiharga(row);
                        } catch (Exception ex) {
                        } finally {
                            ischangevalue = false;
                        }
                    }

                    ischangevalue = false;

                }

            }

        });

        pane.tablemulti_harga_jual.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");
        pane.tablemulti_harga_jual.getActionMap().put("enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = pane.tablemulti_harga_jual.getSelectedRow();
                int col = pane.tablemulti_harga_jual.getSelectedColumn();

                if (col == 0) {
                    Staticvar.preid = multihargalist.get(row).getId_golongan();
                    String defnilai = "";
                    String cekval = String.valueOf(pane.tablemulti_harga_jual.getValueAt(row, col));
                    if (cekval.equals("null") || cekval.equals("")) {
                        defnilai = "";
                    } else {
                        defnilai = String.valueOf(pane.tablemulti_harga_jual.getValueAt(row, 0));
                    }
                    Staticvar.prelabel = defnilai;
                    Staticvar.sfilter = "";
                    JDialog jd = new JDialog(new Mainmenu());
                    jd.add(new Popupcari("nama", "popupdaftargolongan?tipe=0", "Daftar Pelanggan"));
                    jd.pack();
                    jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                    jd.setLocationRelativeTo(null);
                    jd.setVisible(true);
                    jd.toFront();
                    multihargalist.get(row).setId_golongan(Staticvar.resid);
                    pane.tablemulti_harga_jual.setValueAt(Staticvar.reslabel, row, 0);
                    if (!Staticvar.resid.equals(Staticvar.preid)) {
                        multihargalist.get(row).setDari("999");
                        pane.tablemulti_harga_jual.setValueAt("0", row, 1);
                        multihargalist.get(row).setHingga("999");
                        pane.tablemulti_harga_jual.setValueAt("999", row, 2);
                        multihargalist.get(row).setHarga_jual("0");
                        pane.tablemulti_harga_jual.setValueAt("0", row, 4);
                        multihargalist.get(row).setHarga_jual_persen("0");
                        pane.tablemulti_harga_jual.setValueAt("0", row, 5);
                        multihargalist.get(row).setId_satuan(valsatuan);
                        pane.tablemulti_harga_jual.setValueAt(pane.edsatuan_persediaan.getText(), row, 3);
                        multihargalist.get(row).setId_satuan_pengali(valsatuan);
                        multihargalist.get(row).setQty_satuan_pengali("1");
                    }

                } else if (col == 3) {
                    String defnilai = "";
                    String cekval = String.valueOf(pane.tablemulti_harga_jual.getValueAt(row, 3));
                    if (cekval.equals("null") || cekval.equals("")) {
                        defnilai = "";
                    } else {
                        defnilai = String.valueOf(pane.tablemulti_harga_jual.getValueAt(row, 3));
                    }
                    Staticvar.sfilter = "";
                    Staticvar.prelabel = defnilai;
                    Staticvar.preid = multihargalist.get(row).getId_satuan();
                    JDialog jd = new JDialog(new Mainmenu());
                    jd.add(new Popupcaripersediaan(lssatuanentity));
                    jd.pack();
                    jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                    jd.setLocationRelativeTo(null);
                    jd.setVisible(true);
                    jd.toFront();
                    multihargalist.get(row).setId_satuan(Staticvar.resid);
                    pane.tablemulti_harga_jual.setValueAt(Staticvar.reslabel, row, 3);
                    multihargalist.get(row).setId_satuan_pengali(valsatuan);
                    multihargalist.get(row).setQty_satuan_pengali(Staticvar.resvalueextended);

                }

            }
        });

        pane.tablemulti_harga_jual.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = pane.tablemulti_harga_jual.getSelectedRow();
                int col = pane.tablemulti_harga_jual.getSelectedColumn();
                if (e.getClickCount() == 2) {
                    if (col == 0) {
                        Staticvar.preid = multihargalist.get(row).getId_golongan();
                        String defnilai = "";
                        String cekval = String.valueOf(pane.tablemulti_harga_jual.getValueAt(row, col));
                        if (cekval.equals("null") || cekval.equals("")) {
                            defnilai = "";
                        } else {
                            defnilai = String.valueOf(pane.tablemulti_harga_jual.getValueAt(row, 0));
                        }
                        Staticvar.prelabel = defnilai;
                        Staticvar.sfilter = "";
                        JDialog jd = new JDialog(new Mainmenu());
                        jd.add(new Popupcari("nama", "popupdaftargolongan?tipe=0", "Daftar Pelanggan"));
                        jd.pack();
                        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        jd.setLocationRelativeTo(null);
                        jd.setVisible(true);
                        jd.toFront();
                        multihargalist.get(row).setId_golongan(Staticvar.resid);
                        pane.tablemulti_harga_jual.setValueAt(Staticvar.reslabel, row, 0);
                        if (!Staticvar.resid.equals(Staticvar.preid)) {
                            multihargalist.get(row).setDari("999");
                            pane.tablemulti_harga_jual.setValueAt("0", row, 1);
                            multihargalist.get(row).setHingga("999");
                            pane.tablemulti_harga_jual.setValueAt("999", row, 2);
                            multihargalist.get(row).setHarga_jual("0");
                            pane.tablemulti_harga_jual.setValueAt("0", row, 4);
                            multihargalist.get(row).setHarga_jual_persen("0");
                            pane.tablemulti_harga_jual.setValueAt("0", row, 5);
                            multihargalist.get(row).setId_satuan(valsatuan);
                            pane.tablemulti_harga_jual.setValueAt(pane.edsatuan_persediaan.getText(), row, 3);
                            multihargalist.get(row).setId_satuan_pengali(valsatuan);
                            multihargalist.get(row).setQty_satuan_pengali("1");
                        }

                    } else if (col == 3) {
                        String defnilai = "";
                        String cekval = String.valueOf(pane.tablemulti_harga_jual.getValueAt(row, 3));
                        if (cekval.equals("null") || cekval.equals("")) {
                            defnilai = "";
                        } else {
                            defnilai = String.valueOf(pane.tablemulti_harga_jual.getValueAt(row, 3));
                        }
                        Staticvar.sfilter = "";
                        Staticvar.prelabel = defnilai;
                        Staticvar.preid = multihargalist.get(row).getId_satuan();
                        JDialog jd = new JDialog(new Mainmenu());
                        jd.add(new Popupcaripersediaan(lssatuanentity));
                        jd.pack();
                        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        jd.setLocationRelativeTo(null);
                        jd.setVisible(true);
                        jd.toFront();
                        multihargalist.get(row).setId_satuan(Staticvar.resid);
                        pane.tablemulti_harga_jual.setValueAt(Staticvar.reslabel, row, 3);
                        multihargalist.get(row).setId_satuan_pengali(valsatuan);
                        multihargalist.get(row).setQty_satuan_pengali(Staticvar.resvalueextended);

                    }
                }
            }

        });

        allaction(pane.tablemulti_harga_jual, multihargalist, "harga");
    }

    private void multilokasiedit() {
        pane.tablemulti_lokasi.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                TableModel tm = (TableModel) e.getSource();
                if (e.getType() == TableModelEvent.UPDATE) {
                    if (ischangevalue) {
                        return;
                    }
                    int col = e.getColumn();
                    int row = e.getFirstRow();
                    if (col == 0) {

                    } else if (col == 1) {

                    }

                    ischangevalue = false;

                }

            }

        });

        pane.tablemulti_lokasi.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");
        pane.tablemulti_lokasi.getActionMap().put("enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = pane.tablemulti_lokasi.getSelectedRow();
                int col = pane.tablemulti_lokasi.getSelectedColumn();

                if (col == 0) {
                    Staticvar.preid = multilokasilist.get(row).getId_lokasi();
                    String defnilai = "";
                    String cekval = String.valueOf(pane.tablemulti_lokasi.getValueAt(row, col));
                    if (cekval.equals("null") || cekval.equals("")) {
                        defnilai = "";
                    } else {
                        defnilai = String.valueOf(pane.tablemulti_lokasi.getValueAt(row, 0));
                    }
                    Staticvar.prelabel = defnilai;
                    Staticvar.sfilter = "";
                    Staticvar.prelabel = defnilai;
                    JDialog jd = new JDialog(new Mainmenu());
                    jd.add(new Popupcari("lokasi", "popupdaftarlokasipersediaan", "Daftar Lokasi"));
                    jd.pack();
                    jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                    jd.setLocationRelativeTo(null);
                    jd.setVisible(true);
                    jd.toFront();
                    multilokasilist.get(row).setId_lokasi(Staticvar.resid);
                    pane.tablemulti_lokasi.setValueAt(Staticvar.reslabel, row, 0);
                } else if (col == 1) {
                    Staticvar.preid = multilokasilist.get(row).getId_gudang();
                    String defnilai = "";
                    String cekval = String.valueOf(pane.tablemulti_lokasi.getValueAt(row, col));
                    if (cekval.equals("null") || cekval.equals("")) {
                        defnilai = "";
                    } else {
                        defnilai = String.valueOf(pane.tablemulti_lokasi.getValueAt(row, 0));
                    }
                    Staticvar.prelabel = defnilai;
                    Staticvar.sfilter = "";
                    Staticvar.prelabel = defnilai;
                    JDialog jd = new JDialog(new Mainmenu());
                    jd.add(new Popupcari("gudang", "popupdaftargudang", "Daftar Gudang"));
                    jd.pack();
                    jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                    jd.setLocationRelativeTo(null);
                    jd.setVisible(true);
                    jd.toFront();
                    multilokasilist.get(row).setId_gudang(Staticvar.resid);
                    pane.tablemulti_lokasi.setValueAt(Staticvar.reslabel, row, 1);
                }

            }
        });

        pane.tablemulti_lokasi.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = pane.tablemulti_lokasi.getSelectedRow();
                int col = pane.tablemulti_lokasi.getSelectedColumn();

                if (e.getClickCount() == 2) {
                    if (col == 0) {
                        Staticvar.preid = multilokasilist.get(row).getId_lokasi();
                        String defnilai = "";
                        String cekval = String.valueOf(pane.tablemulti_lokasi.getValueAt(row, col));
                        if (cekval.equals("null") || cekval.equals("")) {
                            defnilai = "";
                        } else {
                            defnilai = String.valueOf(pane.tablemulti_lokasi.getValueAt(row, 0));
                        }
                        Staticvar.prelabel = defnilai;
                        Staticvar.sfilter = "";
                        Staticvar.prelabel = defnilai;
                        JDialog jd = new JDialog(new Mainmenu());
                        jd.add(new Popupcari("lokasi", "popupdaftarlokasipersediaan", "Daftar Lokasi"));
                        jd.pack();
                        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        jd.setLocationRelativeTo(null);
                        jd.setVisible(true);
                        jd.toFront();
                        multilokasilist.get(row).setId_lokasi(Staticvar.resid);
                        pane.tablemulti_lokasi.setValueAt(Staticvar.reslabel, row, 0);
                    } else if (col == 1) {
                        Staticvar.preid = multilokasilist.get(row).getId_gudang();
                        String defnilai = "";
                        String cekval = String.valueOf(pane.tablemulti_lokasi.getValueAt(row, col));
                        if (cekval.equals("null") || cekval.equals("")) {
                            defnilai = "";
                        } else {
                            defnilai = String.valueOf(pane.tablemulti_lokasi.getValueAt(row, 1));
                        }
                        Staticvar.prelabel = defnilai;
                        Staticvar.sfilter = "";
                        Staticvar.prelabel = defnilai;
                        JDialog jd = new JDialog(new Mainmenu());
                        jd.add(new Popupcari("gudang", "popupdaftargudang", "Daftar Gudang"));
                        jd.pack();
                        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        jd.setLocationRelativeTo(null);
                        jd.setVisible(true);
                        jd.toFront();
                        multilokasilist.get(row).setId_gudang(Staticvar.resid);
                        pane.tablemulti_lokasi.setValueAt(Staticvar.reslabel, row, 1);
                    }

                }
            }

        });

        allaction(pane.tablemulti_lokasi, multilokasilist, "lokasi");

    }

    private void addautorowmultisatuan(int row) {
        int jumlah_row = pane.tablemulti_satuan.getRowCount() - 1;
        if (row == jumlah_row) {
            String id_satuan_check = multisatuanlist.get(row).getId_satuan();
            String barcode_check = multisatuanlist.get(row).getBarcode();
            String qty_check = multisatuanlist.get(row).getQty_satuan_pengali();
            //String satuan_pengali_check = multisatuanlist.get(row).getId_satuan_pengali();
            String last_id_satuan_check = multisatuanlist.get(jumlah_row).getId_satuan();
            String last_barcode_check = multisatuanlist.get(jumlah_row).getBarcode();
            String last_qty_check = multisatuanlist.get(jumlah_row).getQty_satuan_pengali();
            //String last_id_satuan_pengali = multisatuanlist.get(jumlah_row).getId_satuan_pengali();
            if (!id_satuan_check.equals("") && !qty_check.equals("")) {
                if (!last_id_satuan_check.equals("") && !last_qty_check.equals("")) {
                    multisatuanlist.add(new multisatuan("", "", "", "", "", "", ""));
                    dtmmultisatuan.addRow(rowmultisatuan);
                    pane.tablemulti_satuan.requestFocus();
                    pane.tablemulti_satuan.changeSelection(row + 1, 0, false, false);
                } else {
                    pane.tablemulti_satuan.requestFocus();
                    pane.tablemulti_satuan.changeSelection(row, 0, false, false);
                }
            }
        } else {
            pane.tablemulti_satuan.requestFocus();
            pane.tablemulti_satuan.changeSelection(row + 1, 0, false, false);
        }

    }

    private void addautorowmultiharga(int row) {
        int jumlah_row = pane.tablemulti_harga_jual.getRowCount() - 1;
        if (row == jumlah_row) {
            String golongan_check = multihargalist.get(row).getId_golongan();
            String dari_check = multihargalist.get(row).getDari();
            String hingga_check = multihargalist.get(row).getHingga();
            String satuan_check = multihargalist.get(row).getId_satuan();
            String harga_jual_check = multihargalist.get(row).getHarga_jual();
            String harga_jual_persen_check = multihargalist.get(row).getHarga_jual_persen();
            String last_golongan_check = multihargalist.get(jumlah_row).getId_golongan();
            String last_dari_check = multihargalist.get(jumlah_row).getDari();
            String last_hingga_check = multihargalist.get(jumlah_row).getHingga();
            String last_satuan_check = multihargalist.get(jumlah_row).getId_satuan();
            String last_harga_jual_check = multihargalist.get(jumlah_row).getHarga_jual();
            String last_harga_jual_persen_check = multihargalist.get(jumlah_row).getHarga_jual_persen();
            if (!golongan_check.equals("") && !dari_check.equals("") && !hingga_check.equals("") && !satuan_check.equals("") && (!harga_jual_check.equals("") || !harga_jual_persen_check.equals(""))) {
                if (!last_golongan_check.equals("") && !last_dari_check.equals("") && !last_hingga_check.equals("") && !last_satuan_check.equals("") && (!last_harga_jual_check.equals("") || !last_harga_jual_persen_check.equals(""))) {
                    multihargalist.add(new multiharga("", "", "", "0", "0", "", "", "0", "0", "", "0"));
                    dtmmultihargajual.addRow(rowmultiharga);
                    pane.tablemulti_harga_jual.requestFocus();
                    pane.tablemulti_harga_jual.changeSelection(row + 1, 0, false, false);
                } else {
                    pane.tablemulti_harga_jual.requestFocus();
                    pane.tablemulti_harga_jual.changeSelection(row, 0, false, false);
                }
            }
        } else {
            pane.tablemulti_harga_jual.requestFocus();
            pane.tablemulti_harga_jual.changeSelection(row + 1, 0, false, false);
        }

    }

    private void addautorowmultilokasi(int row) {
        int jumlah_row = pane.tablemulti_lokasi.getRowCount() - 1;
        if (row == jumlah_row) {
            String id_lokasi_check = multilokasilist.get(row).getId_lokasi();
            String id_gudang_check = multilokasilist.get(row).getId_gudang();
            String last_id_lokasi_check = multilokasilist.get(jumlah_row).getId_lokasi();
            String last_id_gudang_check = multilokasilist.get(jumlah_row).getId_gudang();
            if (!id_lokasi_check.equals("") && !id_gudang_check.equals("")) {
                if (!last_id_lokasi_check.equals("") && !last_id_gudang_check.equals("")) {
                    multilokasilist.add(new multilokasi("", "", "", "", ""));
                    dtmmultilokasi.addRow(rowmultilokasi);
                    pane.tablemulti_lokasi.requestFocus();
                    pane.tablemulti_lokasi.changeSelection(row + 1, 0, false, false);
                } else {
                    pane.tablemulti_lokasi.requestFocus();
                    pane.tablemulti_lokasi.changeSelection(row, 0, false, false);
                }
            }
        } else {
            pane.tablemulti_lokasi.requestFocus();
            pane.tablemulti_lokasi.changeSelection(row + 1, 0, false, false);
        }
    }

    private String kirimtextsatuan(int tipe) {
        StringBuilder sb = new StringBuilder();
        sb.append("datamultisatuan=");
        int listcount = 0;
        if (multisatuanlist.get(multisatuanlist.size() - 1).getId_satuan().equals("")) {
            listcount = multisatuanlist.size() - 1;
        } else {
            listcount = multisatuanlist.size();
        }

        if (tipe == 0) {
            for (int i = 0; i < listcount; i++) {
                sb.append("id_satuan=" + "'" + multisatuanlist.get(i).getId_satuan() + "'" + "::"
                     + "barcode=" + "'" + multisatuanlist.get(i).getBarcode() + "'" + "::"
                     + "id_satuan_pengali=" + "'" + multisatuanlist.get(i).getId_satuan_pengali() + "'" + "::"
                     + "qty_satuan_pengali=" + "'" + multisatuanlist.get(i).getQty_satuan_pengali() + "'");
                sb.append("--");
            }
        } else {
            for (int i = 0; i < listcount; i++) {
                sb.append("id_satuan=" + "'" + multisatuanlist.get(i).getId_satuan() + "'" + "::"
                     + "barcode=" + "'" + multisatuanlist.get(i).getBarcode() + "'" + "::"
                     + "id_satuan_pengali=" + "'" + multisatuanlist.get(i).getId_satuan_pengali() + "'" + "::"
                     + "qty_satuan_pengali=" + "'" + multisatuanlist.get(i).getQty_satuan_pengali() + "'");
                sb.append("--");
            }
        }

        return sb.toString().substring(0, sb.toString().length() - 2);
    }

    private String kirimtextharga(int tipe) {
        StringBuilder sb = new StringBuilder();
        sb.append("datamultiharga=");
        int listcount = 0;
        if (multihargalist.get(multihargalist.size() - 1).getId_golongan().equals("")) {
            listcount = multihargalist.size() - 1;
        } else {
            listcount = multihargalist.size();
        }
        if (tipe == 0) {
            for (int i = 0; i < listcount; i++) {
                sb.append("id_golongan=" + "'" + multihargalist.get(i).getId_golongan() + "'" + "::"
                     + "dari=" + "'" + multihargalist.get(i).getDari() + "'" + "::"
                     + "hingga=" + "'" + multihargalist.get(i).getHingga() + "'" + "::"
                     + "id_satuan=" + "'" + multihargalist.get(i).getId_satuan() + "'" + "::"
                     + "harga_jual=" + "'" + multihargalist.get(i).getHarga_jual() + "'" + "::"
                     + "harga_jual_persen=" + "'" + multihargalist.get(i).getHarga_jual_persen() + "'" + "::"
                     + "id_satuan_pengali=" + "'" + valsatuan + "'" + "::"
                     + "qty_satuan_pengali=" + "'" + multihargalist.get(i).getQty_satuan_pengali() + "'");
                sb.append("--");
            }
        } else {
            for (int i = 0; i < listcount; i++) {
                sb.append("id_golongan=" + "'" + multihargalist.get(i).getId_golongan() + "'" + "::"
                     + "dari=" + "'" + multihargalist.get(i).getDari() + "'" + "::"
                     + "hingga=" + "'" + multihargalist.get(i).getHingga() + "'" + "::"
                     + "id_satuan=" + "'" + multihargalist.get(i).getId_satuan() + "'" + "::"
                     + "harga_jual=" + "'" + multihargalist.get(i).getHarga_jual() + "'" + "::"
                     + "harga_jual_persen=" + "'" + multihargalist.get(i).getHarga_jual_persen() + "'" + "::"
                     + "id_satuan_pengali=" + "'" + valsatuan + "'" + "::"
                     + "qty_satuan_pengali=" + "'" + multihargalist.get(i).getQty_satuan_pengali() + "'");
                sb.append("--");
            }
        }
        return sb.toString().substring(0, sb.toString().length() - 2);
    }

    private String kirimtextlokasi(int tipe) {
        StringBuilder sb = new StringBuilder();
        sb.append("datamultilokasi=");
        int listcount = 0;
        if (multilokasilist.get(multilokasilist.size() - 1).getId_gudang().equals("")) {
            listcount = multilokasilist.size() - 1;
        } else {
            listcount = multilokasilist.size();
        }
        if (tipe == 0) {
            for (int i = 0; i < listcount; i++) {
                sb.append("id_lokasi=" + "'" + multilokasilist.get(i).getId_lokasi() + "'" + "::"
                     + "id_gudang=" + "'" + multilokasilist.get(i).getId_gudang() + "'");
                sb.append("--");
            }
        } else {
            for (int i = 0; i < listcount; i++) {
                sb.append("id_lokasi=" + "'" + multilokasilist.get(i).getId_lokasi() + "'" + "::"
                     + "id_gudang=" + "'" + multilokasilist.get(i).getId_gudang() + "'");
                sb.append("--");
            }
        }
        return sb.toString().substring(0, sb.toString().length() - 2);

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

    private void cariakunpersediaan() {
        pane.bcari_akun_persediaan.addActionListener((ActionEvent e) -> {
            rawgetidakun(pane.edakun_persediaan.getText(), valakun_persediaan);
            if (!Staticvar.resid.equals(valakun_persediaan)) {
                valakun_persediaan = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.edakun_persediaan.setText(val);
            }
        });

    }

    private void cariakunpendapatan() {
        pane.bcari_akun_pendapatan.addActionListener((ActionEvent e) -> {
            rawgetidakun(pane.edakun_pendapatan.getText(), valakun_pendapatan);
            if (!Staticvar.resid.equals(valakun_pendapatan)) {
                valakun_pendapatan = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.edakun_pendapatan.setText(val);
            }
        });

    }

    private void cariakunhpp() {
        pane.bcari_akun_harga_pokok.addActionListener((ActionEvent e) -> {
            rawgetidakun(pane.edakun_harga_pokok.getText(), valakun_hpp);
            if (!Staticvar.resid.equals(valakun_hpp)) {
                valakun_hpp = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.edakun_harga_pokok.setText(val);
            }
        });

    }

    private void cariakunpembelian() {
        pane.bcari_akun_pembelian.addActionListener((ActionEvent e) -> {
            rawgetidakun(pane.edakun_pembelian.getText(), valakun_pembelian);
            if (!Staticvar.resid.equals(valakun_pembelian)) {
                valakun_pembelian = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.edakun_pembelian.setText(val);
            }
        });

    }

    private void cariakunreturpembelian() {
        pane.bcari_akun_retur_pembelian.addActionListener((ActionEvent e) -> {
            rawgetidakun(pane.edakun_retur_pembelian.getText(), valakun_retur_pembelian);
            if (!Staticvar.resid.equals(valakun_retur_pembelian)) {
                valakun_retur_pembelian = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.edakun_retur_pembelian.setText(val);
            }
        });

    }

    private void cariakunreturpenjualan() {
        pane.bcari_akun_retur_penjualan.addActionListener((ActionEvent e) -> {
            rawgetidakun(pane.edakun_retur_penjualan.getText(), valakun_retur_penjualan);
            if (!Staticvar.resid.equals(valakun_retur_penjualan)) {
                valakun_retur_penjualan = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.edakun_retur_penjualan.setText(val);
            }
        });

    }

    public class multisatuan {

        String id, id_satuan, kode_satuan, barcode, id_satuan_pengali, kode_satuan_pengali, qty_satuan_pengali;

        public multisatuan() {

        }

        public multisatuan(String id, String id_satuan, String kode_satuan, String barcode, String id_satuan_pengali, String kode_satuan_pengali, String qty_satuan_pengali) {
            this.id = id;
            this.id_satuan = id_satuan;
            this.kode_satuan = kode_satuan;
            this.barcode = barcode;
            this.id_satuan_pengali = id_satuan_pengali;
            this.kode_satuan_pengali = kode_satuan_pengali;
            this.qty_satuan_pengali = qty_satuan_pengali;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId_satuan() {
            return id_satuan;
        }

        public void setId_satuan(String id_satuan) {
            this.id_satuan = id_satuan;
        }

        public String getKode_satuan() {
            return kode_satuan;
        }

        public void setKode_satuan(String kode_satuan) {
            this.kode_satuan = kode_satuan;
        }

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }

        public String getId_satuan_pengali() {
            return id_satuan_pengali;
        }

        public void setId_satuan_pengali(String id_satuan_pengali) {
            this.id_satuan_pengali = id_satuan_pengali;
        }

        public String getKode_satuan_pengali() {
            return kode_satuan_pengali;
        }

        public void setKode_satuan_pengali(String kode_satuan_pengali) {
            this.kode_satuan_pengali = kode_satuan_pengali;
        }

        public String getQty_satuan_pengali() {
            return qty_satuan_pengali;
        }

        public void setQty_satuan_pengali(String qty_satuan_pengali) {
            this.qty_satuan_pengali = qty_satuan_pengali;
        }

    }

    public class multiharga {

        String id, id_golongan, kode_golongan, dari, hingga, id_satuan, kode_satuan, harga_jual, harga_jual_persen, id_satuan_pengali, qty_satuan_pengali;

        public multiharga(String id, String id_golongan, String kode_golongan, String dari, String hingga, String id_satuan, String kode_satuan, String harga_jual, String harga_jual_persen, String id_satuan_pengali, String qty_satuan_pengali) {
            this.id = id;
            this.id_golongan = id_golongan;
            this.kode_golongan = kode_golongan;
            this.dari = dari;
            this.hingga = hingga;
            this.id_satuan = id_satuan;
            this.kode_satuan = kode_satuan;
            this.harga_jual = harga_jual;
            this.harga_jual_persen = harga_jual_persen;
            this.id_satuan_pengali = id_satuan_pengali;
            this.qty_satuan_pengali = qty_satuan_pengali;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId_golongan() {
            return id_golongan;
        }

        public void setId_golongan(String id_golongan) {
            this.id_golongan = id_golongan;
        }

        public String getKode_golongan() {
            return kode_golongan;
        }

        public void setKode_golongan(String kode_golongan) {
            this.kode_golongan = kode_golongan;
        }

        public String getDari() {
            return dari;
        }

        public void setDari(String dari) {
            this.dari = dari;
        }

        public String getHingga() {
            return hingga;
        }

        public void setHingga(String hingga) {
            this.hingga = hingga;
        }

        public String getId_satuan() {
            return id_satuan;
        }

        public void setId_satuan(String id_satuan) {
            this.id_satuan = id_satuan;
        }

        public String getKode_satuan() {
            return kode_satuan;
        }

        public void setKode_satuan(String kode_satuan) {
            this.kode_satuan = kode_satuan;
        }

        public String getHarga_jual() {
            return harga_jual;
        }

        public void setHarga_jual(String harga_jual) {
            this.harga_jual = harga_jual;
        }

        public String getHarga_jual_persen() {
            return harga_jual_persen;
        }

        public void setHarga_jual_persen(String harga_jual_persen) {
            this.harga_jual_persen = harga_jual_persen;
        }

        public String getId_satuan_pengali() {
            return id_satuan_pengali;
        }

        public void setId_satuan_pengali(String id_satuan_pengali) {
            this.id_satuan_pengali = id_satuan_pengali;
        }

        public String getQty_satuan_pengali() {
            return qty_satuan_pengali;
        }

        public void setQty_satuan_pengali(String qty_satuan_pengali) {
            this.qty_satuan_pengali = qty_satuan_pengali;
        }

    }

    public class multilokasi {

        String id, id_lokasi, nama_lokaksi, id_gudang, nama_gudang;

        public multilokasi(String id, String id_lokasi, String nama_lokaksi, String id_gudang, String nama_gudang) {
            this.id = id;
            this.id_lokasi = id_lokasi;
            this.nama_lokaksi = nama_lokaksi;
            this.id_gudang = id_gudang;
            this.nama_gudang = nama_gudang;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId_lokasi() {
            return id_lokasi;
        }

        public void setId_lokasi(String id_lokasi) {
            this.id_lokasi = id_lokasi;
        }

        public String getNama_lokaksi() {
            return nama_lokaksi;
        }

        public void setNama_lokaksi(String nama_lokaksi) {
            this.nama_lokaksi = nama_lokaksi;
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

    }

}
