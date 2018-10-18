/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.akuntansi;

import mizanposapp.controller.innerpanel.akuntansi.*;
import mizanposapp.controller.innerpanel.penjualan.*;
import mizanposapp.controller.innerpanel.penjualan.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import mizanposapp.helper.ConvertFunc;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Globalsession;
import mizanposapp.helper.Staticvar;
import mizanposapp.helper.numtoword;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Errorpanel;
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.akuntansi.Settingakunpenting_inner_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class SettingakunpentinginnerController {

    String id;
    String valdept = "";
    String valcurr = "";
    String valhutang_usaha = "";
    String valhutang_giro = "";
    String valpiutang_usaha = "";
    String valpiutang_giro = "";
    String valpembelian_tunai = "";
    String valpenjualan_tunai = "";
    String valakun_kas = "";
    String valdiskon_pembelian = "";
    String valdiskon_penjualan = "";
    String valuang_muka_pembelian = "";
    String valuang_muka_penjualan = "";
    String valbiaya_lain_pembelian = "";
    String valbiaya_lain_penjualan = "";
    String vallabarugi_periode_akuntansi = "";
    String vallabarugi_tahun_berjalan = "";
    String vallabarugi_ditahan = "";

    int no_urut = 0, valgiro = 0;
    CrudHelper ch = new CrudHelper();
    Settingakunpenting_inner_panel pane;
    NumberFormat nf = NumberFormat.getInstance();

    public SettingakunpentinginnerController(Settingakunpenting_inner_panel pane) {
        this.pane = pane;
        loadsession();
        simpandata();
        caridepartment();
        carihutangusaha();
        batal();
    }

    private void loadsession() {
        valdept = Globalsession.DEFAULT_DEPT_ID;
        pane.eddept.setText(Globalsession.DEFAULT_DEPT_NAME);

        valcurr = Globalsession.DEFAULT_CURRENCY_ID;
        pane.edmata_uang.setText(Globalsession.DEFAULT_CURRENCY_ID);

        loaddata(valdept, valcurr);

    }

    private void loaddata(String iddept, String id_curr) {
        try {
            JSONParser jpdata = new JSONParser();
            String param = String.format("id_dept=%s&id_currency=%s", iddept, id_curr);
            Object rawobjdata = jpdata.parse(ch.getdatadetails("daftarakunpenting", param));
            JSONArray ja = (JSONArray) rawobjdata;
            for (int i = 0; i < ja.size(); i++) {
                JSONObject jo = (JSONObject) ja.get(i);
                String kasus = String.valueOf(jo.get("keterangan")).toLowerCase();
                switch (kasus) {
                    case "hutang usaha":
                        pane.edhutang_usaha.setText(String.valueOf(jo.get("akun")) + "-" + String.valueOf(jo.get("nama_akun")));
                        valhutang_usaha = String.valueOf(jo.get("akun"));
                        pane.lhutang_usaha.setText(String.valueOf(jo.get("keterangan")));
                        break;
                    case "hutang giro":
                        pane.edhutang_giro.setText(String.valueOf(jo.get("akun")) + "-" + String.valueOf(jo.get("nama_akun")));
                        valhutang_giro = String.valueOf(jo.get("akun"));
                        pane.lhutang_giro.setText(String.valueOf(jo.get("keterangan")));
                        break;
                    case "piutang usaha":
                        pane.edpiutang_usaha.setText(String.valueOf(jo.get("akun")) + "-" + String.valueOf(jo.get("nama_akun")));
                        valpiutang_usaha = String.valueOf(jo.get("akun"));
                        pane.lpiutang_usaha.setText(String.valueOf(jo.get("keterangan")));
                        break;
                    case "piutang giro":
                        pane.edpiutang_giro.setText(String.valueOf(jo.get("akun")) + "-" + String.valueOf(jo.get("nama_akun")));
                        valpiutang_giro = String.valueOf(jo.get("akun"));
                        pane.lpiutang_giro.setText(String.valueOf(jo.get("keterangan")));
                        break;
                    case "pembelian tunai":
                        pane.ed_pembelian_tunai.setText(String.valueOf(jo.get("akun")) + "-" + String.valueOf(jo.get("nama_akun")));
                        valpembelian_tunai = String.valueOf(jo.get("akun"));
                        pane.lpembelian_tunai.setText(String.valueOf(jo.get("keterangan")));
                        break;
                    case "penjualan tunai":
                        pane.edpenjualan_tunai.setText(String.valueOf(jo.get("akun")) + "-" + String.valueOf(jo.get("nama_akun")));
                        valpenjualan_tunai = String.valueOf(jo.get("akun"));
                        pane.lpenjualan_tunai.setText(String.valueOf(jo.get("keterangan")));
                        break;
                    case "akun kas":
                        pane.edakun_kas.setText(String.valueOf(jo.get("akun")) + "-" + String.valueOf(jo.get("nama_akun")));
                        valakun_kas = String.valueOf(jo.get("akun"));
                        pane.lakun_kas.setText(String.valueOf(jo.get("keterangan")));
                        break;
                    case "diskon pembelian":
                        pane.eddiskon_pembelian.setText(String.valueOf(jo.get("akun")) + "-" + String.valueOf(jo.get("nama_akun")));
                        valdiskon_pembelian = String.valueOf(jo.get("akun"));
                        pane.ldiskon_pembelian.setText(String.valueOf(jo.get("keterangan")));
                        break;
                    case "diskon penjualan":
                        pane.eddiskon_penjualan.setText(String.valueOf(jo.get("akun")) + "-" + String.valueOf(jo.get("nama_akun")));
                        valdiskon_penjualan = String.valueOf(jo.get("akun"));
                        pane.ldiskon_penjualan.setText(String.valueOf(jo.get("keterangan")));
                        break;
                    case "uang muka pembelian":
                        pane.eduang_muka_pembelian.setText(String.valueOf(jo.get("akun")) + "-" + String.valueOf(jo.get("nama_akun")));
                        valuang_muka_pembelian = String.valueOf(jo.get("akun"));
                        pane.luang_muka_pembelian.setText(String.valueOf(jo.get("keterangan")));
                        break;
                    case "uang muka penjualan":
                        pane.eduang_muka_penjualan.setText(String.valueOf(jo.get("akun")) + "-" + String.valueOf(jo.get("nama_akun")));
                        valuang_muka_penjualan = String.valueOf(jo.get("akun"));
                        pane.luang_muka_penjualan.setText(String.valueOf(jo.get("keterangan")));
                        break;
                    case "ongkos kirim pembelian":
                        pane.edbiaya_lain_pembelian.setText(String.valueOf(jo.get("akun")) + "-" + String.valueOf(jo.get("nama_akun")));
                        valbiaya_lain_pembelian = String.valueOf(jo.get("akun"));
                        pane.lbiaya_lain_pembelian.setText(String.valueOf(jo.get("keterangan")));
                        break;
                    case "ongkos kirim penjualan":
                        pane.edbiaya_lain_penjualan.setText(String.valueOf(jo.get("akun")) + "-" + String.valueOf(jo.get("nama_akun")));
                        valbiaya_lain_penjualan = String.valueOf(jo.get("akun"));
                        pane.lbiaya_lain_penjualan.setText(String.valueOf(jo.get("keterangan")));
                        break;
                    case "laba rugi tahun berjalan":
                        pane.edlabarugi_tahun_berjalan.setText(String.valueOf(jo.get("akun")) + "-" + String.valueOf(jo.get("nama_akun")));
                        vallabarugi_tahun_berjalan = String.valueOf(jo.get("akun"));
                        pane.llabarugi_tahun_berjalan.setText(String.valueOf(jo.get("keterangan")));
                        break;
                    case "laba rugi ditahan":
                        pane.edlabarugi_ditahan.setText(String.valueOf(jo.get("akun")) + "-" + String.valueOf(jo.get("nama_akun")));
                        vallabarugi_ditahan = String.valueOf(jo.get("akun"));
                        pane.llabarugi_ditahan.setText(String.valueOf(jo.get("keterangan")));
                        break;

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void rawsimpan() {
        String data = "id_dept=" + valdept + "&id_currency=" + valcurr + ""
                + "&acc_config="
                + "akun='" + valhutang_usaha + "'::keterangan='" + pane.lhutang_usaha.getText() + "'--"
                + "akun='" + valhutang_giro + "'::keterangan='" + pane.lhutang_giro.getText() + "'--"
                + "akun='" + valpiutang_usaha + "'::keterangan='" + pane.lpiutang_usaha.getText() + "'--"
                + "akun='" + valpiutang_giro + "'::keterangan='" + pane.lpiutang_giro.getText() + "'--"
                + "akun='" + valpembelian_tunai + "'::keterangan='" + pane.lpembelian_tunai.getText() + "'--"
                + "akun='" + valpenjualan_tunai + "'::keterangan='" + pane.lpenjualan_tunai.getText() + "'--"
                + "akun='" + valakun_kas + "'::keterangan='" + pane.lakun_kas.getText() + "'--"
                + "akun='" + valdiskon_pembelian + "'::keterangan='" + pane.ldiskon_pembelian.getText() + "'--"
                + "akun='" + valdiskon_penjualan + "'::keterangan='" + pane.ldiskon_penjualan.getText() + "'--"
                + "akun='" + valuang_muka_pembelian + "'::keterangan='" + pane.luang_muka_pembelian.getText() + "'--"
                + "akun='" + valuang_muka_penjualan + "'::keterangan='" + pane.luang_muka_penjualan.getText() + "'--"
                + "akun='" + valbiaya_lain_pembelian + "'::keterangan='" + pane.lbiaya_lain_pembelian.getText() + "'--"
                + "akun='" + valbiaya_lain_penjualan + "'::keterangan='" + pane.lbiaya_lain_penjualan.getText() + "'--"
                + "akun='" + vallabarugi_tahun_berjalan + "'::keterangan='" + pane.llabarugi_tahun_berjalan.getText() + "'--"
                + "akun='" + vallabarugi_ditahan + "'::keterangan='" + pane.llabarugi_ditahan.getText() + "'";
        ch.insertdata("updateakunpenting", data);
        if (Staticvar.getresult.equals("berhasil")) {
            Staticvar.isupdate = true;
            JDialog jd = (JDialog) pane.getRootPane().getParent();
            jd.dispose();
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

    private void simpandata() {
        pane.bsimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Staticvar.isupdate = true;
                if (pane.eddept.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Dept tidak boleh kosong", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    rawsimpan();
                }
            }
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

    private void carihutangusaha() {
        pane.bcari_hutang_usaha.addActionListener((ActionEvent e) -> {
            rawgetidakun(pane.edhutang_usaha.getText(), valhutang_usaha);
            if (!Staticvar.resid.equals(valhutang_usaha)) {
                valhutang_usaha = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.edhutang_usaha.setText(val);
            }
        });

    }

    private void batal() {
        pane.bbatal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        Staticvar.isupdate = false;
                        JDialog jd = (JDialog) pane.getRootPane().getParent();
                        jd.dispose();
                    }
                });
            }
        });
    }

}