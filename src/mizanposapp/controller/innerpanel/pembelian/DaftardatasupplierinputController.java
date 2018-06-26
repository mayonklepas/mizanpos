/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.pembelian;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import mizanposapp.controller.innerpanel.persediaan.DaftarserviceinnerinputController;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Errorpanel;
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.pembelian.Daftardatasupplier_input_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class DaftardatasupplierinputController {

    String id;
    CrudHelper ch = new CrudHelper();
    Daftardatasupplier_input_panel pane;
    String valklasifikasi;

    public DaftardatasupplierinputController(Daftardatasupplier_input_panel pane) {
        this.pane = pane;
        loaddata();
        tutup();
        simpandata();
        cariklasifikasisupplier();
    }

    private void cariklasifikasisupplier() {
        pane.bcari_supplier.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("supplierklasifikasi", "popupdaftarklasifikasinama", "Daftar Klasifikasi"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valklasifikasi = Staticvar.resid;
            pane.edklasifikasi_supplier.setText(Staticvar.reslabel);
        });

    }

    private void loaddata() {
        try {
            id = Staticvar.ids;
            if (id.equals("")) {
                pane.edkode_supplier.setText("");
                pane.ednama_supplier.setText("");
                pane.edklasifikasi_supplier.setText("");
                valklasifikasi = "";
                pane.taketeranga_supplier.setText("");
                pane.edalamat_supplier.setText("");
                pane.edkota_supplier.setText("");
                pane.edprovinsi_supplier.setText("");
                pane.ednegara_supplier.setText("");
                pane.edtelepon_supplier.setText("");
                pane.edhp_supplier.setText("");
                pane.edcp_supplier.setText("");
                pane.edpos_supplier.setText("");
                pane.edfax_supplier.setText("");
                pane.edemail_supplier.setText("");
                pane.edweb_supplier.setText("");
                pane.ckaktif.setSelected(true);
            } else {
                JSONParser jpdata = new JSONParser();
                String param = String.format("id=%s&tipe=1", id);
                Object objdata = jpdata.parse(ch.getdatadetails("dm/datanama", param));
                JSONArray jadata = (JSONArray) objdata;
                for (int i = 0; i < jadata.size(); i++) {
                    JSONObject joindata = (JSONObject) jadata.get(i);
                    pane.edkode_supplier.setText(String.valueOf(joindata.get("kode")));
                    pane.ednama_supplier.setText(String.valueOf(joindata.get("nama")));
                    pane.edklasifikasi_supplier.setText(String.valueOf(joindata.get("nama_cards_klas")));
                    valklasifikasi = String.valueOf(joindata.get("id_cards_klas"));
                    pane.taketeranga_supplier.setText(String.valueOf(joindata.get("keterangan")));
                    pane.edalamat_supplier.setText(String.valueOf(joindata.get("alamat")));
                    pane.edkota_supplier.setText(String.valueOf(joindata.get("kota")));
                    pane.edprovinsi_supplier.setText(String.valueOf(joindata.get("provinsi")));
                    pane.ednegara_supplier.setText(String.valueOf(joindata.get("negara")));
                    pane.edtelepon_supplier.setText(String.valueOf(joindata.get("telp")));
                    pane.edhp_supplier.setText(String.valueOf(joindata.get("hp")));
                    pane.edcp_supplier.setText(String.valueOf(joindata.get("kontak_person")));
                    pane.edpos_supplier.setText(String.valueOf(joindata.get("kode_pos")));
                    pane.edfax_supplier.setText(String.valueOf(joindata.get("fax")));
                    pane.edemail_supplier.setText(String.valueOf(joindata.get("email")));
                    pane.edweb_supplier.setText(String.valueOf(joindata.get("website")));
                    int iscek = Integer.parseInt(String.valueOf(joindata.get("isaktif")));
                    if (iscek == 1) {
                        pane.ckaktif.setSelected(true);
                    } else {
                        pane.ckaktif.setSelected(false);
                    }
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
                String ckval = "";
                if (pane.ckaktif.isSelected() == true) {
                    ckval = "1";
                } else {
                    ckval = "0";
                }
                if (id.equals("")) {
                    String data = String.format("data=kode='%s'::nama='%s'::tipe=1::isaktif='%s'::id_cards_klas='%s'::keterangan='%s'&"
                            + "lokasi=alamat='%s'::kota='%s'::kode_pos='%s'::provinsi='%s'::negara='%s'::telp='%s'::fax='%s'::"
                            + "email='%s'::website='%s'::kontak_person='%s'::hp='%s'",
                            pane.edkode_supplier.getText(),
                            pane.ednama_supplier.getText(),
                            ckval,
                            valklasifikasi,
                            pane.taketeranga_supplier.getText(),
                            pane.edalamat_supplier.getText(),
                            pane.edkota_supplier.getText(),
                            pane.edpos_supplier.getText(),
                            pane.edprovinsi_supplier.getText(),
                            pane.ednegara_supplier.getText(),
                            pane.edtelepon_supplier.getText(),
                            pane.edfax_supplier.getText(),
                            pane.edemail_supplier.getText(),
                            pane.edweb_supplier.getText(),
                            pane.edcp_supplier.getText(),
                            pane.edhp_supplier.getText());
                    ch.insertdata("dm/insertnama", data);
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
                    String data = String.format("data=kode='%s'::nama='%s'::tipe=1::isaktif='%s'::id_cards_klas='%s'::keterangan='%s'&"
                            + "lokasi=alamat='%s'::kota='%s'::kode_pos='%s'::provinsi='%s'::negara='%s'::telp='%s'::fax='%s'::"
                            + "email='%s'::website='%s'::kontak_person='%s'::hp='%s'",
                            pane.edkode_supplier.getText(),
                            pane.ednama_supplier.getText(),
                            ckval,
                            valklasifikasi,
                            pane.taketeranga_supplier.getText(),
                            pane.edalamat_supplier.getText(),
                            pane.edkota_supplier.getText(),
                            pane.edpos_supplier.getText(),
                            pane.edprovinsi_supplier.getText(),
                            pane.ednegara_supplier.getText(),
                            pane.edtelepon_supplier.getText(),
                            pane.edfax_supplier.getText(),
                            pane.edemail_supplier.getText(),
                            pane.edweb_supplier.getText(),
                            pane.edcp_supplier.getText(),
                            pane.edhp_supplier.getText());
                    ch.updatedata("dm/updatenama", data, id);
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
