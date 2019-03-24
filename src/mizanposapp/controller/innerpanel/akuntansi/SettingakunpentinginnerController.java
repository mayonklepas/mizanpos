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
import mizanposapp.helper.FuncHelper;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Globalsession;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Errorpanel;
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.akuntansi.Settingakunpenting_inner_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
        batal();
        carihutangusaha();
        carihutanggiro();
        caripiutangusaha();
        caripiutanggiro();
        caripembeliantunai();
        caripejualantunai();
        cariakunkas();
        caridiskonpembelian();
        caridiskonpenjualan();
        cariuangmukapembelian();
        cariuangmukapenjualan();
        caribiayalainpembelian();
        caribiayalainpenjualan();
        carilabarugitahunberjalan();
        carilabarugiditahan();
    }

    private void loadsession() {
        valdept = Globalsession.Setting_DeptDefault;
        pane.eddept.setText(Globalsession.Setting_DeptDefaultnama);

        valcurr = Globalsession.id_currency_company;
        pane.edmata_uang.setText(Globalsession.CURRENCYCODE);

        loaddata(valdept, valcurr);

    }

    private void loaddata(String iddept, String id_curr) {
        cleartext();
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
                        //pane.lhutang_usaha.setText(String.valueOf(jo.get("keterangan")));
                        break;
                    case "hutang giro":
                        pane.edhutang_giro.setText(String.valueOf(jo.get("akun")) + "-" + String.valueOf(jo.get("nama_akun")));
                        valhutang_giro = String.valueOf(jo.get("akun"));
                        //pane.lhutang_giro.setText(String.valueOf(jo.get("keterangan")));
                        break;
                    case "piutang usaha":
                        pane.edpiutang_usaha.setText(String.valueOf(jo.get("akun")) + "-" + String.valueOf(jo.get("nama_akun")));
                        valpiutang_usaha = String.valueOf(jo.get("akun"));
                        //pane.lpiutang_usaha.setText(String.valueOf(jo.get("keterangan")));
                        break;
                    case "piutang giro":
                        pane.edpiutang_giro.setText(String.valueOf(jo.get("akun")) + "-" + String.valueOf(jo.get("nama_akun")));
                        valpiutang_giro = String.valueOf(jo.get("akun"));
                        //pane.lpiutang_giro.setText(String.valueOf(jo.get("keterangan")));
                        break;
                    case "pembelian tunai":
                        pane.ed_pembelian_tunai.setText(String.valueOf(jo.get("akun")) + "-" + String.valueOf(jo.get("nama_akun")));
                        valpembelian_tunai = String.valueOf(jo.get("akun"));
                        //pane.lpembelian_tunai.setText(String.valueOf(jo.get("keterangan")));
                        break;
                    case "penjualan tunai":
                        pane.edpenjualan_tunai.setText(String.valueOf(jo.get("akun")) + "-" + String.valueOf(jo.get("nama_akun")));
                        valpenjualan_tunai = String.valueOf(jo.get("akun"));
                        //pane.lpenjualan_tunai.setText(String.valueOf(jo.get("keterangan")));
                        break;
                    case "akun kas":
                        pane.edakun_kas.setText(String.valueOf(jo.get("akun")) + "-" + String.valueOf(jo.get("nama_akun")));
                        valakun_kas = String.valueOf(jo.get("akun"));
                        //pane.lakun_kas.setText(String.valueOf(jo.get("keterangan")));
                        break;
                    case "diskon pembelian":
                        pane.eddiskon_pembelian.setText(String.valueOf(jo.get("akun")) + "-" + String.valueOf(jo.get("nama_akun")));
                        valdiskon_pembelian = String.valueOf(jo.get("akun"));
                        //pane.ldiskon_pembelian.setText(String.valueOf(jo.get("keterangan")));
                        break;
                    case "diskon penjualan":
                        pane.eddiskon_penjualan.setText(String.valueOf(jo.get("akun")) + "-" + String.valueOf(jo.get("nama_akun")));
                        valdiskon_penjualan = String.valueOf(jo.get("akun"));
                        //pane.ldiskon_penjualan.setText(String.valueOf(jo.get("keterangan")));
                        break;
                    case "uang muka pembelian":
                        pane.eduang_muka_pembelian.setText(String.valueOf(jo.get("akun")) + "-" + String.valueOf(jo.get("nama_akun")));
                        valuang_muka_pembelian = String.valueOf(jo.get("akun"));
                        //pane.luang_muka_pembelian.setText(String.valueOf(jo.get("keterangan")));
                        break;
                    case "uang muka penjualan":
                        pane.eduang_muka_penjualan.setText(String.valueOf(jo.get("akun")) + "-" + String.valueOf(jo.get("nama_akun")));
                        valuang_muka_penjualan = String.valueOf(jo.get("akun"));
                        //pane.luang_muka_penjualan.setText(String.valueOf(jo.get("keterangan")));
                        break;
                    case "ongkos kirim pembelian":
                        pane.edbiaya_lain_pembelian.setText(String.valueOf(jo.get("akun")) + "-" + String.valueOf(jo.get("nama_akun")));
                        valbiaya_lain_pembelian = String.valueOf(jo.get("akun"));
                        //pane.lbiaya_lain_pembelian.setText(String.valueOf(jo.get("keterangan")));
                        break;
                    case "ongkos kirim penjualan":
                        pane.edbiaya_lain_penjualan.setText(String.valueOf(jo.get("akun")) + "-" + String.valueOf(jo.get("nama_akun")));
                        valbiaya_lain_penjualan = String.valueOf(jo.get("akun"));
                        //pane.lbiaya_lain_penjualan.setText(String.valueOf(jo.get("keterangan")));
                        break;
                    case "laba rugi tahun berjalan":
                        pane.edlabarugi_tahun_berjalan.setText(String.valueOf(jo.get("akun")) + "-" + String.valueOf(jo.get("nama_akun")));
                        vallabarugi_tahun_berjalan = String.valueOf(jo.get("akun"));
                        //pane.llabarugi_tahun_berjalan.setText(String.valueOf(jo.get("keterangan")));
                        break;
                    case "laba rugi ditahan":
                        pane.edlabarugi_ditahan.setText(String.valueOf(jo.get("akun")) + "-" + String.valueOf(jo.get("nama_akun")));
                        vallabarugi_ditahan = String.valueOf(jo.get("akun"));
                        //pane.llabarugi_ditahan.setText(String.valueOf(jo.get("keterangan")));
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
            new Globalsession(Staticvar.id_user_aktif);
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
                } else if (checkakunkosong() == true) {
                    rawsimpan();
                } else {
                    JOptionPane.showMessageDialog(null, "Tidak boleh ada field kosong", "Informasi", JOptionPane.INFORMATION_MESSAGE);
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
            pane.eddept.setText(Staticvar.resvalue);
            loaddata(valdept, valcurr);
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

    private void rawgetkasbank(String prevlabel, String previd) {
        Staticvar.sfilter = "";
        Staticvar.preid = previd;
        Staticvar.prelabel = prevlabel;
        JDialog jd = new JDialog(new Mainmenu());
        jd.add(new Popupcari("akun", "popupdaftarkasbank", "Daftar Akun"));
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

    private void carihutanggiro() {
        pane.bcari_hutang_giro.addActionListener((ActionEvent e) -> {
            rawgetidakun(pane.edhutang_giro.getText(), valhutang_giro);
            if (!Staticvar.resid.equals(valhutang_giro)) {
                valhutang_giro = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.edhutang_giro.setText(val);
            }
        });

    }

    private void caripiutangusaha() {
        pane.bcari_piutang_usaha.addActionListener((ActionEvent e) -> {
            rawgetidakun(pane.edpiutang_usaha.getText(), valpiutang_usaha);
            if (!Staticvar.resid.equals(valpiutang_usaha)) {
                valpiutang_usaha = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.edpiutang_usaha.setText(val);
            }
        });

    }

    private void caripiutanggiro() {
        pane.bcari_piutang_giro.addActionListener((ActionEvent e) -> {
            rawgetidakun(pane.edpiutang_giro.getText(), valpiutang_giro);
            if (!Staticvar.resid.equals(valpiutang_giro)) {
                valpiutang_giro = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.edpiutang_giro.setText(val);
            }
        });

    }

    private void caripembeliantunai() {
        pane.bcari_pembelian_tunai.addActionListener((ActionEvent e) -> {
            rawgetidakun(pane.ed_pembelian_tunai.getText(), valpembelian_tunai);
            if (!Staticvar.resid.equals(valpembelian_tunai)) {
                valpembelian_tunai = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.ed_pembelian_tunai.setText(val);
            }
        });

    }

    private void caripejualantunai() {
        pane.bcari_penjualan_tunai.addActionListener((ActionEvent e) -> {
            rawgetidakun(pane.edpenjualan_tunai.getText(), valpenjualan_tunai);
            if (!Staticvar.resid.equals(valpenjualan_tunai)) {
                valpenjualan_tunai = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.edpenjualan_tunai.setText(val);
            }
        });

    }

    private void cariakunkas() {
        pane.bcari_akun_kas.addActionListener((ActionEvent e) -> {
            rawgetkasbank(pane.edakun_kas.getText(), valakun_kas);
            if (!Staticvar.resid.equals(valakun_kas)) {
                valakun_kas = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.edakun_kas.setText(val);
            }
        });

    }

    private void caridiskonpembelian() {
        pane.bcari_diskon_pembelian.addActionListener((ActionEvent e) -> {
            rawgetidakun(pane.eddiskon_pembelian.getText(), valdiskon_pembelian);
            if (!Staticvar.resid.equals(valdiskon_pembelian)) {
                valdiskon_pembelian = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.eddiskon_pembelian.setText(val);
            }
        });

    }

    private void caridiskonpenjualan() {
        pane.bcari_diskon_penjualan.addActionListener((ActionEvent e) -> {
            rawgetidakun(pane.eddiskon_penjualan.getText(), valdiskon_penjualan);
            if (!Staticvar.resid.equals(valdiskon_penjualan)) {
                valdiskon_penjualan = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.eddiskon_penjualan.setText(val);
            }
        });

    }

    private void cariuangmukapembelian() {
        pane.bcari_uang_muka_pembelian.addActionListener((ActionEvent e) -> {
            rawgetidakun(pane.eduang_muka_pembelian.getText(), valuang_muka_pembelian);
            if (!Staticvar.resid.equals(valuang_muka_pembelian)) {
                valuang_muka_pembelian = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.eduang_muka_pembelian.setText(val);
            }
        });

    }

    private void cariuangmukapenjualan() {
        pane.bcari_uang_muka_penjualan.addActionListener((ActionEvent e) -> {
            rawgetidakun(pane.eduang_muka_penjualan.getText(), valuang_muka_penjualan);
            if (!Staticvar.resid.equals(valuang_muka_penjualan)) {
                valuang_muka_penjualan = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.eduang_muka_penjualan.setText(val);
            }
        });

    }

    private void caribiayalainpembelian() {
        pane.bcari_biaya_lain_pembelian.addActionListener((ActionEvent e) -> {
            rawgetidakun(pane.edbiaya_lain_pembelian.getText(), valbiaya_lain_pembelian);
            if (!Staticvar.resid.equals(valbiaya_lain_pembelian)) {
                valbiaya_lain_pembelian = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.edbiaya_lain_pembelian.setText(val);
            }
        });

    }

    private void caribiayalainpenjualan() {
        pane.bcari_biaya_lain_penjualan.addActionListener((ActionEvent e) -> {
            rawgetidakun(pane.edbiaya_lain_penjualan.getText(), valbiaya_lain_penjualan);
            if (!Staticvar.resid.equals(valbiaya_lain_penjualan)) {
                valbiaya_lain_penjualan = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.edbiaya_lain_penjualan.setText(val);
            }
        });

    }

    private void carilabarugitahunberjalan() {
        pane.bcari_labarugi_tahun_berjalan.addActionListener((ActionEvent e) -> {
            rawgetidakun(pane.edlabarugi_tahun_berjalan.getText(), vallabarugi_tahun_berjalan);
            if (!Staticvar.resid.equals(vallabarugi_tahun_berjalan)) {
                vallabarugi_tahun_berjalan = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.edlabarugi_tahun_berjalan.setText(val);
            }
        });

    }

    private void carilabarugiditahan() {
        pane.bcari_labarugi_ditahan.addActionListener((ActionEvent e) -> {
            rawgetidakun(pane.edlabarugi_ditahan.getText(), vallabarugi_ditahan);
            if (!Staticvar.resid.equals(vallabarugi_ditahan)) {
                vallabarugi_ditahan = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.edlabarugi_ditahan.setText(val);
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

    private void cleartext() {
        pane.edhutang_usaha.setText("");
        //pane.lhutang_usaha.setText("");
        valhutang_usaha = "";

        pane.edhutang_giro.setText("");
        //pane.lhutang_giro.setText("");
        valhutang_giro = "";

        pane.edpiutang_usaha.setText("");
        //pane.lpiutang_usaha.setText("");
        valpiutang_usaha = "";

        pane.edpiutang_giro.setText("");
        //pane.lpiutang_giro.setText("");
        valpiutang_giro = "";

        pane.ed_pembelian_tunai.setText("");
        //pane.lpembelian_tunai.setText("");
        valpembelian_tunai = "";

        pane.edpenjualan_tunai.setText("");
        //pane.lpenjualan_tunai.setText("");
        valpenjualan_tunai = "";

        pane.edakun_kas.setText("");
        //pane.lakun_kas.setText("");
        valakun_kas = "";

        pane.eddiskon_pembelian.setText("");
        //pane.ldiskon_pembelian.setText("");
        valdiskon_pembelian = "";

        pane.eddiskon_penjualan.setText("");
        //pane.ldiskon_penjualan.setText("");
        valdiskon_penjualan = "";

        pane.eduang_muka_pembelian.setText("");
        //pane.luang_muka_pembelian.setText("");
        valuang_muka_pembelian = "";

        pane.eduang_muka_penjualan.setText("");
        //pane.luang_muka_penjualan.setText("");
        valuang_muka_penjualan = "";

        pane.edbiaya_lain_pembelian.setText("");
        //pane.lbiaya_lain_pembelian.setText("");
        valbiaya_lain_pembelian = "";

        pane.edbiaya_lain_penjualan.setText("");
        //pane.lbiaya_lain_penjualan.setText("");
        valbiaya_lain_penjualan = "";

        pane.edlabarugi_tahun_berjalan.setText("");
        //pane.llabarugi_tahun_berjalan.setText("");
        vallabarugi_tahun_berjalan = "";

        pane.edlabarugi_ditahan.setText("");
        //pane.llabarugi_ditahan.setText("");
        vallabarugi_ditahan = "";
    }

    private boolean checkakunkosong() {
        boolean status = true;
        if (valhutang_usaha.equals("")
             || valhutang_giro.equals("")
             || valpiutang_giro.equals("")
             || valpiutang_usaha.equals("")
             || valpembelian_tunai.equals("")
             || valpenjualan_tunai.equals("")
             || valakun_kas.equals("")
             || valdiskon_pembelian.equals("")
             || valdiskon_penjualan.equals("")
             || valuang_muka_pembelian.equals("")
             || valuang_muka_penjualan.equals("")
             || valbiaya_lain_pembelian.equals("")
             || valbiaya_lain_penjualan.equals("")
             || vallabarugi_tahun_berjalan.equals("")
             || vallabarugi_ditahan.equals("")) {
            status = false;
        } else {
            status = true;
        }
        return status;
    }

}
