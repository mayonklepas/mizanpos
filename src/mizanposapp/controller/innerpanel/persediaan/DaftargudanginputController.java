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
import mizanposapp.helper.FuncHelper;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Globalsession;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Errorpanel;
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.persediaan.Daftargudang_input_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class DaftargudanginputController {

    String id;
    CrudHelper ch = new CrudHelper();
    Daftargudang_input_panel pane;
    String idpenanggungjawab;
    String iddepartment;

    public DaftargudanginputController(Daftargudang_input_panel pane) {
        this.pane = pane;
        loaddata();
        tutup();
        simpandata();
        caripenanggungjawab();
        caridepartment();
    }

    private void caripenanggungjawab() {
        pane.bcari_penanggungjawab.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("nama", "popupdaftarnama?tipe=2", "Daftar Karyawan"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            pane.edpenanggung_jawab.setText(Staticvar.reslabel);
            idpenanggungjawab = Staticvar.resid;
        });

    }

    private void caridepartment() {
        pane.bcari_department.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("department", "popupdaftardept", "Daftar Department"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            pane.eddepartment.setText(Staticvar.reslabel);
            iddepartment = Staticvar.resid;
        });

    }

    private void loaddata() {
        try {
            id = Staticvar.ids;
            if (id.equals("")) {
                pane.edkode_gudang.setText("");
                pane.ednama_gudang.setText("");
                pane.edpenanggung_jawab.setText("");
                idpenanggungjawab = String.valueOf("");
                pane.eddepartment.setText(Globalsession.Setting_DeptDefaultnama);
                iddepartment = Globalsession.Setting_DeptDefault;
            } else {
                JSONParser jpdata = new JSONParser();
                String param = String.format("id=%s", id);
                Object objdata = jpdata.parse(ch.getdatadetails("dm/datagudang", param));
                JSONArray jadata = (JSONArray) objdata;
                for (int i = 0; i < jadata.size(); i++) {
                    JSONObject joindata = (JSONObject) jadata.get(i);
                    pane.edkode_gudang.setText(String.valueOf(joindata.get("kode")));
                    pane.ednama_gudang.setText(String.valueOf(joindata.get("nama")));
                    pane.edpenanggung_jawab.setText(String.valueOf(joindata.get("nama_penanggung_jawab")));
                    idpenanggungjawab = String.valueOf(joindata.get("id_penanggung_jawab"));
                    pane.eddepartment.setText(String.valueOf(joindata.get("nama_dept")));
                    iddepartment = String.valueOf(joindata.get("id_dept"));
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(DaftargudanginputController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void simpandata() {
        pane.bsimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Staticvar.isupdate = true;
                if (id.equals("")) {
                    String data = "data=kode='" + FuncHelper.EncodeString(pane.edkode_gudang.getText()) + "'::"
                            + "nama='" + FuncHelper.EncodeString(pane.ednama_gudang.getText()) + "'::"
                            + "id_penanggung_jawab='" + FuncHelper.EncodeString(idpenanggungjawab) + "'::"
                            + "id_dept='" + FuncHelper.EncodeString(iddepartment) + "'";
                    ch.insertdata("dm/insertgudang", data);
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
                    String data = "data=kode='" + FuncHelper.EncodeString(pane.edkode_gudang.getText()) + "'::"
                            + "nama='" + FuncHelper.EncodeString(pane.ednama_gudang.getText()) + "'::"
                            + "id_penanggung_jawab='" + FuncHelper.EncodeString(idpenanggungjawab) + "'::"
                            + "id_dept='" + FuncHelper.EncodeString(iddepartment) + "'";
                    ch.updatedata("dm/updategudang", data, id);
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
