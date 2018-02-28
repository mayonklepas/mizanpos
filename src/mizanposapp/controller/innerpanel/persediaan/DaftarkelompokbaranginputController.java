/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.persediaan;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import javax.swing.JDialog;
import mizanposapp.helper.CrudHelper;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.persediaan.Daftarkelompokbarang_input_panel;

/**
 *
 * @author Minami
 */
public class DaftarkelompokbaranginputController {

    String id;
    CrudHelper ch = new CrudHelper();
    Daftarkelompokbarang_input_panel pane;

    public DaftarkelompokbaranginputController(Daftarkelompokbarang_input_panel pane) {
        this.pane = pane;
        carisatuan();
        carigudang();
        carilokasi();
    }

    private void carisatuan() {
        pane.bcari_satuan.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("satuan", "popupdaftarsatuan", "Daftar Satuan"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
        });

    }

    private void carigudang() {
        pane.bcari_gudang.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("gudang", "popupdaftargudang", "Daftar Gudang"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
        });

    }

    private void carilokasi() {
        pane.bcari_lokasi.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("lokasi", "popupdaftarlokasipersediaan", "Daftar Lokasi Persediaan"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
        });

    }

    /* private void loaddata() {
        try {
            id = DaftarlokasibaranginnerController.id;
            JSONParser jpdata = new JSONParser();
            String param = String.format("id=%s", id);
            Object objdata = jpdata.parse(ch.getdatadetails("dm/datalokasi", param));
            JSONArray jadata = (JSONArray) objdata;
            for (int i = 0; i < jadata.size(); i++) {
                JSONObject joindata = (JSONObject) jadata.get(i);
                pane.edkode_lokasi.setText(String.valueOf(joindata.get("KODELOKASI")));
                pane.ednama_lokasi.setText(String.valueOf(joindata.get("NAMALOKASI")));
                pane.edpenanggungjawab.setText(String.valueOf(joindata.get("IDPENANGGUNGJAWAB")));
            }
        } catch (ParseException ex) {
            Logger.getLogger(DaftarkelompokbaranginputController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void simpandata() {
        pane.bsimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (id.equals("")) {
                    String data = String.format("data=kodelokasi='%s'::namalokasi='%s'::idpenanggungjawab='%s'",
                            pane.edkode_lokasi.getText(),
                            pane.ednama_lokasi.getText(),
                            pane.edpenanggungjawab.getText());
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
                    String data = String.format("data=kodelokasi='%s'::namalokasi='%s'::idpenanggungjawab='%s'",
                            pane.edkode_lokasi.getText(),
                            pane.ednama_lokasi.getText(),
                            pane.edpenanggungjawab.getText());
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
                JDialog jd = (JDialog) pane.getRootPane().getParent();
                jd.dispose();
            }
        });
    }
    
     */
}
