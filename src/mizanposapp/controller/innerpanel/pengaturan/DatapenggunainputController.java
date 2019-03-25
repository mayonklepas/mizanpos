/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.pengaturan;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.FuncHelper;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Errorpanel;
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.pengaturan.Data_pengguna_input_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class DatapenggunainputController {

    Data_pengguna_input_panel pane;
    CrudHelper ch = new CrudHelper();
    String valhakakses = "", valparent, valnama;
    String ids = "";

    public DatapenggunainputController(Data_pengguna_input_panel pane) {
        ids = Staticvar.ids;
        this.pane = pane;
        carihakakses();
        loaddata();
        simpan();
        caridataparent();
        tutup();
    }

    private void loaddata() {
        try {
            if (ids.equals("")) {
                pane.edpassword.setText("");
                pane.edrepassword.setText("");
                pane.edusername.setText("");
                pane.edhakakses.setText("");
                pane.edparent.setText("");
            } else {

                JSONParser jpdata = new JSONParser();
                String param = String.format("id=%s", ids);
                Object objdata = jpdata.parse(ch.getdatadetails("dm/datapengguna", param));
                JSONArray jadata = (JSONArray) objdata;
                for (int i = 0; i < jadata.size(); i++) {
                    JSONObject joindata = (JSONObject) jadata.get(i);
                    pane.edusername.setText(String.valueOf(joindata.get("nama")));
                    pane.edpassword.setText(String.valueOf(joindata.get("pwd")));
                    pane.edhakakses.setText(String.valueOf(joindata.get("nama_pengguna_level")));
                    valhakakses = String.valueOf(joindata.get("id_pengguna_level"));
                    pane.edparent.setText(String.valueOf(joindata.get("nama_parent")));
                    valparent = String.valueOf(joindata.get("id_parent"));
                    pane.edrepassword.setText("");
                }
                pane.edpassword.setEnabled(false);
            }
        } catch (ParseException ex) {
            Logger.getLogger(DatapenggunainputController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void rawsimpan() {
        String data = "data="
             + "nama='" + pane.edusername.getText() + "'::"
             + "pwd='" + pane.edpassword.getText() + "'::"
             + "id_pengguna_level='" + valhakakses + "'::"
             + "id_parent='" + valparent + "'";
        if (ids.equals("")) {
            ch.insertdata("dm/insertpengguna", data);
        } else {
            ch.updatedata("dm/updatepengguna", data, ids);
        }

        if (Staticvar.getresult.equals("berhasil")) {
            FuncHelper.info("Proses Berhasil", "Data telah berhasil disimpan");
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

    private void simpan() {
        pane.bsimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pane.edusername.getText().equals("")
                     || pane.edpassword.getText().equals("")
                     || pane.edrepassword.getText().equals("")
                     || pane.edhakakses.getText().equals("")) {
                    FuncHelper.info("Data tidak lengkap", "Field Hak Akses,Username,Password dan Retype Password, Tidak Boleh kosong");
                } else if (pane.edpassword.getText().equals(pane.edrepassword.getText())) {
                    rawsimpan();
                } else {
                    FuncHelper.info("Gagal Menyimpan Data", "Retype password tidak sama dengan password awal");
                }
            }
        });
    }

    private void carihakakses() {
        pane.bcarihakakses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Staticvar.sfilter = "";
                Staticvar.preid = valhakakses;
                Staticvar.prelabel = pane.edhakakses.getText();
                JDialog jd = new JDialog(new Mainmenu());
                jd.add(new Popupcari("hakakses", "popupdaftarpenggunalevel", "Daftar Hak Akses"));
                jd.pack();
                jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                jd.setLocationRelativeTo(null);
                jd.setVisible(true);
                jd.toFront();
                valhakakses = Staticvar.resid;
                pane.edhakakses.setText(Staticvar.reslabel);
            }
        });
    }

    private void caridataparent() {
        pane.bcariparent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Staticvar.sfilter = "";
                Staticvar.preid = valparent;
                Staticvar.prelabel = pane.edparent.getText();
                JDialog jd = new JDialog(new Mainmenu());
                jd.add(new Popupcari("datapengguna", "popupdaftarpenggunaparent", "Daftar Pengguna"));
                jd.pack();
                jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                jd.setLocationRelativeTo(null);
                jd.setVisible(true);
                jd.toFront();
                valparent = Staticvar.resid;
                pane.edparent.setText(Staticvar.reslabel);
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

}
