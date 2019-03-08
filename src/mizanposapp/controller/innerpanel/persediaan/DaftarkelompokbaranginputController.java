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
import mizanposapp.view.innerpanel.persediaan.Daftarkelompokbarang_input_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class DaftarkelompokbaranginputController {

    String id;
    CrudHelper ch = new CrudHelper();
    Daftarkelompokbarang_input_panel pane;
    String valsatuan, valgudang, vallokasi, valdept;

    public DaftarkelompokbaranginputController(Daftarkelompokbarang_input_panel pane) {
        this.pane = pane;
        carisatuan();
        carigudang();
        carilokasi();
        caridept();
        loaddata();
        simpandata();
        tutup();
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
            pane.edsatuan.setText(Staticvar.reslabel);
            valsatuan = Staticvar.resid;
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
            pane.edgudang.setText(Staticvar.reslabel);
            valgudang = Staticvar.resid;
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
            pane.edlokasi.setText(Staticvar.reslabel);
            vallokasi = Staticvar.resid;
        });

    }

    private void caridept() {
        pane.bcari_dept.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("department", "popupdaftardept", "Daftar Department"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            pane.eddept.setText(Staticvar.reslabel);
            valdept = Staticvar.resid;
        });

    }

    private void loaddata() {
        try {
            id = Staticvar.ids;
            if (id.equals("")) {
                pane.edkode_kelompok.setText("");
                pane.ednama_kelompok.setText("");
                pane.edsatuan.setText(Globalsession.DEFAULT_NAMA_SATUAN);
                pane.edgudang.setText(Globalsession.Setting_GudangDefaultnama);
                pane.edlokasi.setText(Globalsession.DEFAULT_NAMA_LOKASI);
                pane.eddept.setText(Globalsession.Setting_DeptDefaultnama);
                valsatuan = Globalsession.DEFAULT_ID_SATUAN;
                valgudang = Globalsession.Setting_GudangDefault;
                vallokasi = Globalsession.DEFAULT_ID_LOKASI;
                valdept = Globalsession.Setting_DeptDefault;
                pane.cknon_poin.setSelected(false);
                pane.rbaverage.setSelected(true);

            } else {
                JSONParser jpdata = new JSONParser();
                String param = String.format("id=%s", id);
                Object objdata = jpdata.parse(ch.getdatadetails("dm/datakelompokbarang", param));
                JSONArray jadata = (JSONArray) objdata;
                for (int i = 0; i < jadata.size(); i++) {
                    JSONObject joindata = (JSONObject) jadata.get(i);
                    pane.edkode_kelompok.setText(String.valueOf(joindata.get("kode")));
                    pane.ednama_kelompok.setText(String.valueOf(joindata.get("nama")));
                    pane.edsatuan.setText(String.valueOf(joindata.get("nama_satuan")));
                    pane.edgudang.setText(String.valueOf(joindata.get("nama_gudang")));
                    pane.edlokasi.setText(String.valueOf(joindata.get("nama_lokasi")));
                    pane.eddept.setText(String.valueOf(joindata.get("nama_dept")));
                    valsatuan = String.valueOf(joindata.get("id_satuan"));
                    valgudang = String.valueOf(joindata.get("id_gudang"));
                    vallokasi = String.valueOf(joindata.get("id_lokasi"));
                    valdept = String.valueOf(joindata.get("id_dept"));
                    int ceknon = Integer.parseInt(String.valueOf(joindata.get("isnonpoin")));
                    int metodehpp = Integer.parseInt(String.valueOf(joindata.get("metode_hpp")));
                    if (ceknon == 1) {
                        pane.cknon_poin.setSelected(true);
                    } else {
                        pane.cknon_poin.setSelected(false);
                    }

                    switch (metodehpp) {
                        case 1:
                            pane.rbfifo.setSelected(true);
                            break;
                        case 2:
                            pane.rblifo.setSelected(true);
                            break;
                        default:
                            pane.rbaverage.setSelected(true);
                            break;
                    }
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(DaftarmerekinnerinputController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void simpandata() {
        pane.bsimpan.addActionListener((ActionEvent e) -> {
            Staticvar.isupdate = true;
            String nonpoin = "";
            String metodehpp = "";
            if (pane.cknon_poin.isSelected() == true) {
                nonpoin = "1";
            } else {
                nonpoin = "0";
            }
            if (pane.rbfifo.isSelected() == true) {
                metodehpp = "1";
            } else if (pane.rblifo.isSelected() == true) {
                metodehpp = "2";
            } else {
                metodehpp = "3";
            }
            if (id.equals("")) {
                String data = "data=kode='" + FuncHelper.EncodeString(pane.edkode_kelompok.getText()) + "'::"
                        + "nama='" + FuncHelper.EncodeString(pane.ednama_kelompok.getText()) + "'::"
                        + "id_satuan='" + FuncHelper.EncodeString(valsatuan) + "'::"
                        + "id_gudang='" + FuncHelper.EncodeString(valgudang) + "'::"
                        + "id_lokasi='" + FuncHelper.EncodeString(vallokasi) + "'::"
                        + "id_dept='" + FuncHelper.EncodeString(valdept) + "'::"
                        + "isnonpoin='" + nonpoin + "'::"
                        + "metode_hpp='" + metodehpp + "'";
                ch.insertdata("dm/insertkelompokbarang", data);
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
                String data = "data=kode='" + FuncHelper.EncodeString(pane.edkode_kelompok.getText()) + "'::"
                        + "nama='" + FuncHelper.EncodeString(pane.ednama_kelompok.getText()) + "'::"
                        + "id_satuan='" + FuncHelper.EncodeString(valsatuan) + "'::"
                        + "id_gudang='" + FuncHelper.EncodeString(valgudang) + "'::"
                        + "id_lokasi='" + FuncHelper.EncodeString(vallokasi) + "'::"
                        + "id_dept='" + FuncHelper.EncodeString(valdept) + "'::"
                        + "isnonpoin='" + nonpoin + "'::"
                        + "metode_hpp='" + metodehpp + "'";
                ch.updatedata("dm/updatekelompokbarang", data, id);
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
