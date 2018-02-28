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
import mizanposapp.view.innerpanel.persediaan.Daftarpenyesuaian_input_panel;

/**
 *
 * @author Minami
 */
public class DaftarpenyesuaianinputController {

    String id;
    CrudHelper ch = new CrudHelper();
    Daftarpenyesuaian_input_panel pane;

    public DaftarpenyesuaianinputController(Daftarpenyesuaian_input_panel pane) {
        this.pane = pane;
        //loaddata(pane);
        //tutup(pane);
        //simpandata(pane);
        caridepartment();
    }

    private void caridepartment() {
        pane.bcari_dept.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("department", "popupdaftardepartment", "Daftar Department"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
        });

    }

    /*private void loaddata(Daftarpenyesuaian_input_panel pane) {
        try {
            id = DaftarmerekinnerController.id;
            JSONParser jpdata = new JSONParser();
            String param = String.format("id=%s", id);
            Object objdata = jpdata.parse(ch.getdatadetails("dm/datamerek", param));
            JSONArray jadata = (JSONArray) objdata;
            for (int i = 0; i < jadata.size(); i++) {
                JSONObject joindata = (JSONObject) jadata.get(i);
                pane.edkode_merek.setText(String.valueOf(joindata.get("KODE")));
                pane.ednama_merek.setText(String.valueOf(joindata.get("NAMA")));
                pane.edketerangan.setText(String.valueOf(joindata.get("KETERANGAN")));
            }
        } catch (ParseException ex) {
            Logger.getLogger(DaftarpenyesuaianinputController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }*/
 /*private void simpandata(Daftarpenyesuaian_input_panel pane) {
        pane.bsimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (id.equals("")) {
                    String data = String.format("data=kode='%s'::nama='%s'::keterangan='%s'",
                            pane.edkode_merek.getText(),
                            pane.ednama_merek.getText(),
                            pane.edketerangan.getText());
                    ch.insertdata("dm/insertmerek", data);
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
                    String data = String.format("data=kode='%s'::nama='%s'::keterangan='%s'",
                            pane.edkode_merek.getText(),
                            pane.ednama_merek.getText(),
                            pane.edketerangan.getText());
                    ch.updatedata("dm/updatemerek", data, id);
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
    }*/

 /*private void tutup(Daftarpenyesuaian_input_panel pane) {
        pane.bbatal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog jd = (JDialog) pane.getRootPane().getParent();
                jd.dispose();
            }
        });
    }*/
}
