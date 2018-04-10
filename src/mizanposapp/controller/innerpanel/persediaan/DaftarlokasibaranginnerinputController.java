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
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Errorpanel;
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.persediaan.Daftarlokasibarang_input_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class DaftarlokasibaranginnerinputController {

    String id;
    CrudHelper ch = new CrudHelper();
    Daftarlokasibarang_input_panel pane;
    String id_penangggung_jawab;

    public DaftarlokasibaranginnerinputController(Daftarlokasibarang_input_panel pane) {
        this.pane = pane;
        loaddata();
        tutup();
        simpandata();
        caripenanggungjawab();
    }

    private void caripenanggungjawab() {
        pane.bcari_penanggung_jawab.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("nama", "popupdaftarnama?tipe=2", "Daftar Karyawan"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            id_penangggung_jawab = Staticvar.resid;
            pane.edpenanggungjawab.setText(Staticvar.reslabel);
        });

    }

    private void loaddata() {
        try {
            id = Staticvar.ids;
            if (id.equals("")) {
                pane.edkode_lokasi.setText("");
                pane.ednama_lokasi.setText("");
                pane.edpenanggungjawab.setText("");
                id_penangggung_jawab = String.valueOf("");
            } else {
                JSONParser jpdata = new JSONParser();
                String param = String.format("id=%s", id);
                Object objdata = jpdata.parse(ch.getdatadetails("dm/datalokasi", param));
                JSONArray jadata = (JSONArray) objdata;
                for (int i = 0; i < jadata.size(); i++) {
                    JSONObject joindata = (JSONObject) jadata.get(i);
                    pane.edkode_lokasi.setText(String.valueOf(joindata.get("kode")));
                    pane.ednama_lokasi.setText(String.valueOf(joindata.get("nama")));
                    pane.edpenanggungjawab.setText(String.valueOf(joindata.get("nama_penanggung_jawab")));
                    id_penangggung_jawab = String.valueOf(joindata.get("id_penanggung_jawab"));
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(DaftarlokasibaranginnerinputController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void simpandata() {
        pane.bsimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Staticvar.isupdate = true;
                if (id.equals("")) {
                    String data = String.format("data=kode='%s'::nama='%s'::id_penanggung_jawab='%s'",
                            pane.edkode_lokasi.getText(),
                            pane.ednama_lokasi.getText(),
                            id_penangggung_jawab);
                    ch.insertdata("dm/insertlokasi", data);
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
                    String data = String.format("data=kode='%s'::nama='%s'::id_penanggung_jawab='%s'",
                            pane.edkode_lokasi.getText(),
                            pane.ednama_lokasi.getText(),
                            id_penangggung_jawab);
                    ch.updatedata("dm/updatelokasi", data, id);
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

}
