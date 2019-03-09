/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.keuangan;

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
import java.util.HashMap;
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
import mizanposapp.helper.numtoword;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Errorpanel;
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.keuangan.Formtransferkas;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class FormtransferkasController {

    String id;
    String valakundari = "", valakuntujuan = "", valakun_charge = "", valdept = "", valakun_transaksi = "", valid_transaksi = "";
    int no_urut = 0, valgiro = 0;
    CrudHelper ch = new CrudHelper();
    Formtransferkas pane;
    NumberFormat nf = NumberFormat.getInstance();

    public FormtransferkasController(Formtransferkas pane) {
        this.pane = pane;
        id = Staticvar.ids;
        loadsession();
        skinning();
        loaddata();
        simpandata();
        cariakuncharge();
        cariakundari();
        cariakuntujuan();
        caridepartment();
        batal();
    }

    private void loadsession() {
        valdept = Globalsession.Setting_DeptDefault;
        pane.eddept.setText(Globalsession.Setting_DeptDefaultnama);
    }

    private void skinning() {
        pane.dtanggal.setDateFormatString("dd MMMM yyyy");
        pane.dtanggal.setDate(new Date());

    }

    private void getkodetransaksi() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                HashMap hm = new FuncHelper().getkodetransaksi("44", new Date(), valdept);
                pane.ednotransaksi.setText(String.valueOf(hm.get("no_transaksi")));
                no_urut = FuncHelper.ToInt(String.valueOf(hm.get("no_urut")));
            }
        });

    }

    private void loaddata() {
        try {
            if (id.equals("")) {
                getkodetransaksi();
                pane.edcharge.setText("0");
                pane.edjumlah.setText("0");
            } else {
                JSONParser jpdata = new JSONParser();
                String param = String.format("id=%s", id);
                Object rawobjdata = jpdata.parse(ch.getdatadetails("datatransferkas", param));
                JSONObject jsonobjdata = (JSONObject) rawobjdata;

                Object jogenjur = jsonobjdata.get("genjur");
                JSONArray jagenjur = (JSONArray) jogenjur;
                for (int i = 0; i < jagenjur.size(); i++) {
                    JSONObject joingenjur = (JSONObject) jagenjur.get(i);
                    pane.ednotransaksi.setText(String.valueOf(joingenjur.get("noref")));
                    pane.edketerangan.setText(String.valueOf(joingenjur.get("keterangan")));
                    pane.eddept.setText(String.valueOf(joingenjur.get("nama_dept")));
                    valdept = String.valueOf(joingenjur.get("id_dept"));
                    try {
                        pane.dtanggal.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(joingenjur.get("tanggal"))));
                    } catch (java.text.ParseException ex) {
                        Logger.getLogger(DaftarorderpenjualaninputController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

                Object jokasmasuk = jsonobjdata.get("transfer_kas");
                JSONArray jakasmasuk = (JSONArray) jokasmasuk;
                for (int i = 0; i < jakasmasuk.size(); i++) {
                    JSONObject joinkasmasuk = (JSONObject) jakasmasuk.get(i);
                    valakundari = String.valueOf(joinkasmasuk.get("akun_dari"));
                    pane.edakun_penerimaan.setText(valakundari + "-" + String.valueOf(joinkasmasuk.get("nama_akun_dari")));
                    valakuntujuan = String.valueOf(joinkasmasuk.get("akun_tujuan"));
                    pane.edtujuan.setText(valakuntujuan + "-" + String.valueOf(joinkasmasuk.get("nama_akun_tujuan")));
                    pane.edcharge.setText(String.valueOf(joinkasmasuk.get("bank_charge_nominal")));
                    pane.edjumlah.setText(String.valueOf(joinkasmasuk.get("jumlah")));
                    valakun_charge = String.valueOf(joinkasmasuk.get("akun_bank_charge"));
                    pane.edakun_charge.setText(valakun_charge + "-" + String.valueOf(joinkasmasuk.get("nama_akun_bank_charge")));

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void rawsimpan() {
        if (id.equals("")) {
            String data = "genjur="
                 + "id_keltrans='44'::"
                 + "id_dept='" + valdept + "'::"
                 + "tanggal='" + new SimpleDateFormat("yyyy-MM-dd").format(pane.dtanggal.getDate()) + "'::"
                 + "noref='" + FuncHelper.EncodeString(pane.ednotransaksi.getText()) + "'::"
                 + "keterangan='" + FuncHelper.EncodeString(pane.edketerangan.getText()) + "'"
                 + "&transfer_kas="
                 + "akun_dari='" + valakundari + "'::"
                 + "akun_tujuan='" + valakuntujuan + "'::"
                 + "jumlah='" + FuncHelper.ToDouble(pane.edjumlah.getText()) + "'::"
                 + "bank_charge_nominal='" + FuncHelper.ToDouble(pane.edcharge.getText()) + "'::"
                 + "akun_bank_charge='" + valakun_charge + "'";
            ch.insertdata("inserttransferkas", data);
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
        } else {
            String data = "genjur="
                 + "id_keltrans='44'::"
                 + "id_dept='" + valdept + "'::"
                 + "tanggal='" + new SimpleDateFormat("yyyy-MM-dd").format(pane.dtanggal.getDate()) + "'::"
                 + "noref='" + FuncHelper.EncodeString(pane.ednotransaksi.getText()) + "'::"
                 + "keterangan='" + FuncHelper.EncodeString(pane.edketerangan.getText()) + "'"
                 + "&transfer_kas="
                 + "akun_dari='" + valakundari + "'::"
                 + "akun_tujuan='" + valakuntujuan + "'::"
                 + "jumlah='" + FuncHelper.ToDouble(pane.edjumlah.getText()) + "'::"
                 + "bank_charge_nominal='" + FuncHelper.ToDouble(pane.edcharge.getText()) + "'::"
                 + "akun_bank_charge='" + valakun_charge + "'";
            ch.updatedata("updatetransferkas", data, id);
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
    }

    private void simpandata() {
        pane.bsimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Staticvar.isupdate = true;
                double jumlahbayar = FuncHelper.ToDouble(pane.edjumlah.getText());
                if (pane.edakun_penerimaan.getText().equals("") || pane.edtujuan.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Akun penerima dan tujuan tidak boleh kosong", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else if (pane.eddept.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Dept  tidak boleh kosong", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else if (jumlahbayar <= 0) {
                    JOptionPane.showMessageDialog(null, "Jumlah bayar tidak boleh 0", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    int tahunbulan = Integer.parseInt(new SimpleDateFormat("yyyyMM").format(pane.dtanggal.getDate()));
                    int periodetahunnulan = Integer.parseInt(Globalsession.periode_year + Globalsession.periode_month);
                    if (tahunbulan > periodetahunnulan) {
                        int dialog = JOptionPane.showConfirmDialog(null, "Tanggal transaksi setelah periode akuntansi.\n"
                             + "Apakah anda ingin melanjutkan transaksi ?", "Konfirmasi", JOptionPane.YES_NO_OPTION, 1);
                        if (dialog == 0) {

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

    private void rawgetidakuncharge(String prevlabel, String previd) {
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

    private void cariakuncharge() {
        pane.bcari_charge.addActionListener((ActionEvent e) -> {
            rawgetidakuncharge(pane.edakun_charge.getText(), valakun_charge);
            if (!Staticvar.resid.equals(valakun_charge)) {
                valakun_charge = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.edakun_charge.setText(val);
            }
        });

    }

    private void rawgetidakunkasbank(String prevlabel, String previd) {
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

    private void cariakundari() {
        pane.bcari_dari.addActionListener((ActionEvent e) -> {
            rawgetidakunkasbank(pane.edakun_penerimaan.getText(), valakundari);
            if (!Staticvar.resid.equals(valakundari)) {
                valakundari = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.edakun_penerimaan.setText(val);
            }
        });

    }

    private void cariakuntujuan() {
        pane.bcari_tujuan.addActionListener((ActionEvent e) -> {
            rawgetidakunkasbank(pane.edtujuan.getText(), valakuntujuan);
            if (!Staticvar.resid.equals(valakuntujuan)) {
                valakuntujuan = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.edtujuan.setText(val);
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
                        new FuncHelper().insertnogagal("44", new Date(), valdept, String.valueOf(no_urut));
                        Staticvar.inputmode = false;
                        JDialog jd = (JDialog) pane.getRootPane().getParent();
                        jd.dispose();
                    }
                });
            }
        });
    }

}
