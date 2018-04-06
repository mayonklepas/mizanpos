/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.persediaan;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Errorpanel;
import mizanposapp.view.innerpanel.persediaan.Daftardatadept_input_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class DaftardatadeptinputController {

    String id;
    CrudHelper ch = new CrudHelper();
    Daftardatadept_input_panel pane;
    ArrayList<entity> lssubdept = new ArrayList<>();
    String subvalue = "";

    public DaftardatadeptinputController(Daftardatadept_input_panel pane) {
        this.pane = pane;
        loaddata();
        tutup();
        simpandata();
        ganticheked();
        ganticombo();
    }

    private void loadcombo(String key, String value) {
        pane.cmbsub_dept.removeAllItems();
        if (!key.equals("") && !value.equals("")) {
            lssubdept.add(new entity(key, value));
        }
        try {
            JSONParser jpdata = new JSONParser();
            Object objdata = jpdata.parse(ch.getdatas("dm/daftarsubdept"));
            JSONArray jadata = (JSONArray) objdata;
            for (int i = 0; i < jadata.size(); i++) {
                JSONObject joindata = (JSONObject) jadata.get(i);
                if (!joindata.get("nama").equals(key) && !joindata.get("id").equals(value)) {
                    lssubdept.add(new entity(String.valueOf(joindata.get("nama")), String.valueOf(joindata.get("id"))));
                }

            }
            for (int i = 0; i < lssubdept.size(); i++) {
                pane.cmbsub_dept.addItem(lssubdept.get(i).getKey());
            }
        } catch (ParseException ex) {
            Logger.getLogger(DaftardatadeptinputController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loaddata() {
        try {
            id = Staticvar.ids;
            if (id.equals("")) {
                pane.edid_dept.setText("");
                pane.ednama_dept.setText("");
                pane.ckis_subdept.setSelected(false);
                pane.cmbsub_dept.setEnabled(false);
                loadcombo("", "");
            } else {
                JSONParser jpdata = new JSONParser();
                String param = String.format("id=%s", id);
                Object objdata = jpdata.parse(ch.getdatadetails("dm/datadept", param));
                System.out.println(ch.getdatadetails("dm/datadept", param));
                JSONArray jadata = (JSONArray) objdata;
                for (int i = 0; i < jadata.size(); i++) {
                    JSONObject joindata = (JSONObject) jadata.get(i);
                    pane.edid_dept.setText(String.valueOf(joindata.get("id")));
                    pane.ednama_dept.setText(String.valueOf(joindata.get("nama")));
                    loadcombo(String.valueOf(joindata.get("nama_sub_dept")), String.valueOf(joindata.get("id_sub_dept")));
                    subvalue = String.valueOf(joindata.get("id_sub_dept"));
                    int isub = Integer.parseInt(String.valueOf(joindata.get("issubdept")));
                    if (isub == 1) {
                        pane.ckis_subdept.setSelected(true);
                        pane.cmbsub_dept.setEnabled(true);

                    } else {
                        pane.ckis_subdept.setSelected(false);
                        pane.cmbsub_dept.setEnabled(false);
                    }
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(DaftardatadeptinputController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void simpandata() {
        pane.bsimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Staticvar.isupdate = true;
                String isub = "0";
                if (pane.ckis_subdept.isSelected() == true) {
                    isub = "1";
                } else {
                    isub = "0";
                }
                if (id.equals("")) {

                    String data = String.format("data=id='%s'::nama='%s'::issubdept='%s'::id_sub_dept='%s'",
                            pane.edid_dept.getText(),
                            pane.ednama_dept.getText(),
                            isub,
                            subvalue);
                    ch.insertdata("dm/insertdept", data);
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
                    String data = String.format("data=id='%s'::nama='%s'::issubdept='%s'::id_sub_dept='%s'",
                            pane.edid_dept.getText(),
                            pane.ednama_dept.getText(),
                            isub,
                            subvalue);
                    ch.updatedata("dm/updatedept", data, id);
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
        }
        );
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

    private void ganticheked() {
        pane.ckis_subdept.addItemListener((ItemEvent e) -> {
            if (pane.ckis_subdept.isSelected() == true) {
                pane.cmbsub_dept.setEnabled(true);
            } else {
                pane.cmbsub_dept.setEnabled(false);
            }
        });
    }

    private void ganticombo() {
        pane.cmbsub_dept.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == e.SELECTED) {
                    subvalue = lssubdept.get(pane.cmbsub_dept.getSelectedIndex()).getValue();
                }
            }
        });
    }

    public class entity {

        String key, value;

        public entity() {
        }

        public entity(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }
}
