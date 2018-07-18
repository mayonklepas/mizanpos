/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.persediaan;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import mizanposapp.helper.ConvertFunc;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Errorpanel;
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.persediaan.Daftarservice_input_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class DaftarserviceinnerinputController {

    String id;
    CrudHelper ch = new CrudHelper();
    Daftarservice_input_panel pane;
    String tagid_akun;

    public DaftarserviceinnerinputController(Daftarservice_input_panel pane) {
        this.pane = pane;
        cariakun();
        loaddata();
        tutup();
        simpandata();
    }

    private void loaddata() {
        try {
            id = Staticvar.ids;
            if (id.equals("")) {
                pane.edkode_service.setText("");
                pane.ednama_service.setText("");
                pane.edpersen_service.setText("0");
                pane.edid_akun.setText("");

            } else {
                JSONParser jpdata = new JSONParser();
                String param = String.format("id=%s", id);
                Object objdata = jpdata.parse(ch.getdatadetails("dm/dataservice", param));
                JSONArray jadata = (JSONArray) objdata;
                for (int i = 0; i < jadata.size(); i++) {
                    JSONObject joindata = (JSONObject) jadata.get(i);
                    pane.edkode_service.setText(String.valueOf(joindata.get("kode")));
                    pane.ednama_service.setText(String.valueOf(joindata.get("nama")));
                    pane.edpersen_service.setText(String.valueOf(joindata.get("persen_service")));
                    pane.edid_akun.setText(String.valueOf(joindata.get("akun_service")) + " | " + String.valueOf(joindata.get("nama_akun_service")));
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(DaftarserviceinnerinputController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void simpandata() {
        pane.bsimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Staticvar.isupdate = true;
                if (id.equals("")) {
                    String data = "data=kode='" + ConvertFunc.EncodeString(pane.edkode_service.getText()) + "'::"
                            + "nama='" + ConvertFunc.EncodeString(pane.ednama_service.getText()) + "'::"
                            + "persen_service='" + ConvertFunc.EncodeString(pane.edpersen_service.getText()) + "'::"
                            + "akun_service='" + ConvertFunc.EncodeString(pane.edid_akun.getText().split(" | ")[0]) + "'";
                    ch.insertdata("dm/insertservice", data);
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
                    String data = "data=kode='" + ConvertFunc.EncodeString(pane.edkode_service.getText()) + "'::"
                            + "nama='" + ConvertFunc.EncodeString(pane.ednama_service.getText()) + "'::"
                            + "persen_service='" + ConvertFunc.EncodeString(pane.edpersen_service.getText()) + "'::"
                            + "akun_service='" + ConvertFunc.EncodeString(pane.edid_akun.getText().split(" | ")[0]) + "'";
                    ch.updatedata("dm/updateservice", data, id);
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

    private void cariakun() {
        pane.bcari_akun.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("akun", "popupdaftarakun", "Daftar Akun"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            pane.edid_akun.setText(Staticvar.resid + " | " + Staticvar.reslabel);

        });

    }

}
