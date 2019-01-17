/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.penjualan;

import mizanposapp.controller.innerpanel.penjualan.*;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import mizanposapp.helper.FuncHelper;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Errorpanel;
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.penjualan.Daftarpos_kas_masuk_input_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class DaftarposkasmasukinputController {

    String id;
    String val_pengguna, val_kelompok_akun;
    CrudHelper ch = new CrudHelper();
    Daftarpos_kas_masuk_input_panel pane;
    String tagid_akun;

    public DaftarposkasmasukinputController(Daftarpos_kas_masuk_input_panel pane) {
        this.pane = pane;
        carikelompok();
        loaddata();
        tutup();
        simpandata();
    }

    private void loaddata() {
        try {
            id = Staticvar.ids;
            if (id.equals("")) {
                pane.edkasir.setText("");
                pane.edkelompok_akun.setText("");
                pane.edjumlah.setText("0");
                pane.edketerangan.setText("");

            } else {
                JSONParser jpdata = new JSONParser();
                String param = String.format("id=%s", id);
                Object objdata = jpdata.parse(ch.getdatadetails("dm/dataposkasmasuk", param));
                JSONArray jadata = (JSONArray) objdata;
                for (int i = 0; i < jadata.size(); i++) {
                    JSONObject joindata = (JSONObject) jadata.get(i);
                    pane.edkasir.setText(String.valueOf(joindata.get("nama_pengguna")));
                    val_pengguna = String.valueOf(joindata.get("id_pengguna"));
                    val_kelompok_akun = String.valueOf(joindata.get("id_kel_kas_masuk"));
                    pane.edkelompok_akun.setText(String.valueOf(joindata.get("nama_kel_kas_masuk")));
                    pane.edjumlah.setText(String.valueOf(joindata.get("jumlah")));
                    pane.edketerangan.setText(String.valueOf(joindata.get("keterangan")));

                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(DaftarposkasmasukinputController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void simpandata() {
        pane.bsimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Staticvar.isupdate = true;
                if (id.equals("")) {
                    String data = "data=id_pengguna='" + val_pengguna + "'::"
                         + "tanggal='" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "'::"
                         + "id_kel_kas_masuk='" + val_kelompok_akun + "'::"
                         + "jumlah='" + pane.edjumlah.getText() + "'::"
                         + "keterangan='" + pane.edketerangan.getText() + "'";
                    ch.insertdata("dm/insertposkasmasuk", data);
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
                    String data = "data=id_pengguna='" + val_pengguna + "'::"
                         + "tanggal='" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "'::"
                         + "id_kel_kas_masuk='" + val_kelompok_akun + "'::"
                         + "jumlah='" + pane.edjumlah.getText() + "'::"
                         + "keterangan='" + pane.edketerangan.getText() + "'";
                    ch.updatedata("dm/updateposkasmasuk", data, id);
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
        });
    }

    private void tutup() {
        pane.bbatal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Staticvar.isupdate = false;
                JDialog jd = (JDialog) pane.getRootPane().getParent();
                jd.dispose();
            }
        });
    }

    private void carikelompok() {
        pane.bcari_kelompok.addActionListener((ActionEvent e) -> {
            Staticvar.preid = val_kelompok_akun;
            Staticvar.prelabel = pane.edkelompok_akun.getText();
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("poskelkasmasuk", "popupdaftarposkelkasmasuk", "Daftar POS Kelompok Kas Masuk"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            val_kelompok_akun = Staticvar.resid;
            pane.edkelompok_akun.setText(Staticvar.reslabel);

        });

    }

}
