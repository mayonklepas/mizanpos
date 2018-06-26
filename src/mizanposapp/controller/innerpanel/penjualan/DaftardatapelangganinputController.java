/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.penjualan;

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
import mizanposapp.view.innerpanel.penjualan.Daftardatapelanggan_input_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class DaftardatapelangganinputController {

    String id;
    CrudHelper ch = new CrudHelper();
    Daftardatapelanggan_input_panel pane;
    String valklasifikasi, valgolongan;

    public DaftardatapelangganinputController(Daftardatapelanggan_input_panel pane) {
        this.pane = pane;
        loaddata();
        tutup();
        simpandata();
        cariklasifikasipelanggan();
        carigolonganpelanggan();
    }

    private void cariklasifikasipelanggan() {
        pane.bcari_klasifikasi.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("supplierklasifikasi", "popupdaftarklasifikasinama", "Daftar Klasifikasi"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valklasifikasi = Staticvar.resid;
            pane.edklasifikasi_pelanggan.setText(Staticvar.reslabel);
        });

    }

    private void carigolonganpelanggan() {
        pane.bcari_golongan.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("golongan", "popupdaftargolongan?tipe=0", "Daftar Golongan"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valgolongan = Staticvar.resid;
            pane.edgolongan_pelanggan.setText(Staticvar.reslabel);
        });

    }

    private void loaddata() {
        try {
            id = Staticvar.ids;
            if (id.equals("")) {
                pane.edkode_pelanggan.setText("");
                pane.ednama_pelanggan.setText("");
                pane.edklasifikasi_pelanggan.setText("");
                valklasifikasi = "";
                valgolongan = "";
                pane.taketerangan_pelanggan.setText("");
                pane.edalamat_pelanggan.setText("");
                pane.edkota_pelanggan.setText("");
                pane.edprovinsi_pelanggan.setText("");
                pane.ednegara_pelanggan.setText("");
                pane.edtelepon_pelanggan.setText("");
                pane.edhp_pelanggan.setText("");
                pane.edcp_pelanggan.setText("");
                pane.edpos_pelanggan.setText("");
                pane.edfax_pelanggan.setText("");
                pane.edemail_pelanggan.setText("");
                pane.edweb_pelanggan.setText("");
                pane.ckaktif.setSelected(true);
            } else {
                JSONParser jpdata = new JSONParser();
                String param = String.format("id=%s&tipe=1", id);
                Object objdata = jpdata.parse(ch.getdatadetails("dm/datanama", param));
                JSONArray jadata = (JSONArray) objdata;
                for (int i = 0; i < jadata.size(); i++) {
                    JSONObject joindata = (JSONObject) jadata.get(i);
                    pane.edkode_pelanggan.setText(String.valueOf(joindata.get("kode")));
                    pane.ednama_pelanggan.setText(String.valueOf(joindata.get("nama")));
                    pane.edklasifikasi_pelanggan.setText(String.valueOf(joindata.get("nama_cards_klas")));
                    valklasifikasi = String.valueOf(joindata.get("id_cards_klas"));
                    pane.taketerangan_pelanggan.setText(String.valueOf(joindata.get("keterangan")));
                    pane.edalamat_pelanggan.setText(String.valueOf(joindata.get("alamat")));
                    pane.edkota_pelanggan.setText(String.valueOf(joindata.get("kota")));
                    pane.edprovinsi_pelanggan.setText(String.valueOf(joindata.get("provinsi")));
                    pane.ednegara_pelanggan.setText(String.valueOf(joindata.get("negara")));
                    pane.edtelepon_pelanggan.setText(String.valueOf(joindata.get("telp")));
                    pane.edhp_pelanggan.setText(String.valueOf(joindata.get("hp")));
                    pane.edcp_pelanggan.setText(String.valueOf(joindata.get("kontak_person")));
                    pane.edpos_pelanggan.setText(String.valueOf(joindata.get("kode_pos")));
                    pane.edfax_pelanggan.setText(String.valueOf(joindata.get("fax")));
                    pane.edemail_pelanggan.setText(String.valueOf(joindata.get("email")));
                    pane.edweb_pelanggan.setText(String.valueOf(joindata.get("website")));
                    pane.edgolongan_pelanggan.setText(String.valueOf(joindata.get("nama_golongan")));
                    valgolongan = String.valueOf(joindata.get("id_golongan"));
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
                    String data = String.format("data=kode='%s'::nama='%s'::tipe=0::isaktif='%s'::id_cards_klas='%s'::keterangan='%s'::id_golongan='%s'&"
                            + "lokasi=alamat='%s'::kota='%s'::kode_pos='%s'::provinsi='%s'::negara='%s'::telp='%s'::fax='%s'::"
                            + "email='%s'::website='%s'::kontak_person='%s'::hp='%s'",
                            pane.edkode_pelanggan.getText(),
                            pane.ednama_pelanggan.getText(),
                            ckval,
                            valklasifikasi,
                            pane.taketerangan_pelanggan.getText(),
                            valgolongan,
                            pane.edalamat_pelanggan.getText(),
                            pane.edkota_pelanggan.getText(),
                            pane.edpos_pelanggan.getText(),
                            pane.edprovinsi_pelanggan.getText(),
                            pane.ednegara_pelanggan.getText(),
                            pane.edtelepon_pelanggan.getText(),
                            pane.edfax_pelanggan.getText(),
                            pane.edemail_pelanggan.getText(),
                            pane.edweb_pelanggan.getText(),
                            pane.edcp_pelanggan.getText(),
                            pane.edhp_pelanggan.getText());
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
                    String data = String.format("data=kode='%s'::nama='%s'::tipe=0::isaktif='%s'::id_cards_klas='%s'::keterangan='%s'::id_golongan='%s'&"
                            + "lokasi=alamat='%s'::kota='%s'::kode_pos='%s'::provinsi='%s'::negara='%s'::telp='%s'::fax='%s'::"
                            + "email='%s'::website='%s'::kontak_person='%s'::hp='%s'",
                            pane.edkode_pelanggan.getText(),
                            pane.ednama_pelanggan.getText(),
                            ckval,
                            valklasifikasi,
                            pane.taketerangan_pelanggan.getText(),
                            valgolongan,
                            pane.edalamat_pelanggan.getText(),
                            pane.edkota_pelanggan.getText(),
                            pane.edpos_pelanggan.getText(),
                            pane.edprovinsi_pelanggan.getText(),
                            pane.ednegara_pelanggan.getText(),
                            pane.edtelepon_pelanggan.getText(),
                            pane.edfax_pelanggan.getText(),
                            pane.edemail_pelanggan.getText(),
                            pane.edweb_pelanggan.getText(),
                            pane.edcp_pelanggan.getText(),
                            pane.edhp_pelanggan.getText(),
                            valgolongan);
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
                JDialog jd = (JDialog) pane.getRootPane().getParent();
                jd.dispose();
            }
        });
    }

}
