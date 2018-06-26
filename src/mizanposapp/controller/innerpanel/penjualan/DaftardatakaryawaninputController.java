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
import mizanposapp.view.innerpanel.penjualan.Daftardatakaryawan_input_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class DaftardatakaryawaninputController {

    String id;
    CrudHelper ch = new CrudHelper();
    Daftardatakaryawan_input_panel pane;
    String valklasifikasi, valgolongan;

    public DaftardatakaryawaninputController(Daftardatakaryawan_input_panel pane) {
        this.pane = pane;
        loaddata();
        tutup();
        simpandata();
        cariklasifikasikaryawan();
        carigolongankaryawan();
    }

    private void cariklasifikasikaryawan() {
        pane.bcari_klasifikasi.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("supplierklasifikasi", "popupdaftarklasifikasinama", "Daftar Klasifikasi"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valklasifikasi = Staticvar.resid;
            pane.edklasifikasi_karyawan.setText(Staticvar.reslabel);
        });

    }

    private void carigolongankaryawan() {
        pane.bcari_golongan.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("golongan", "popupdaftargolongan?tipe=2", "Daftar Golongan"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valgolongan = Staticvar.resid;
            pane.edgolongan_karyawan.setText(Staticvar.reslabel);
        });

    }

    private void loaddata() {
        try {
            id = Staticvar.ids;
            if (id.equals("")) {
                pane.edkode_karyawan.setText("");
                pane.ednama_karyawan.setText("");
                pane.edklasifikasi_karyawan.setText("");
                valklasifikasi = "";
                valgolongan = "";
                pane.edjabatan_karyawan.setText("");
                pane.taketerangan_karyawan.setText("");
                pane.edalamat_karyawan.setText("");
                pane.edkota_karyawan.setText("");
                pane.edprovinsi_karyawan.setText("");
                pane.ednegara_karyawan.setText("");
                pane.edtelepon_karyawan.setText("");
                pane.edhp_karyawan.setText("");
                pane.edcp_karyawan.setText("");
                pane.edpos_karyawan.setText("");
                pane.edfax_karyawan.setText("");
                pane.edemail_karyawan.setText("");
                pane.edweb_karyawan.setText("");
                pane.ckaktif.setSelected(true);
            } else {
                JSONParser jpdata = new JSONParser();
                String param = String.format("id=%s&tipe=2", id);
                Object objdata = jpdata.parse(ch.getdatadetails("dm/datanama", param));
                JSONArray jadata = (JSONArray) objdata;
                for (int i = 0; i < jadata.size(); i++) {
                    JSONObject joindata = (JSONObject) jadata.get(i);
                    pane.edkode_karyawan.setText(String.valueOf(joindata.get("kode")));
                    pane.ednama_karyawan.setText(String.valueOf(joindata.get("nama")));
                    pane.edklasifikasi_karyawan.setText(String.valueOf(joindata.get("nama_cards_klas")));
                    valklasifikasi = String.valueOf(joindata.get("id_cards_klas"));
                    pane.taketerangan_karyawan.setText(String.valueOf(joindata.get("keterangan")));
                    pane.edalamat_karyawan.setText(String.valueOf(joindata.get("alamat")));
                    pane.edkota_karyawan.setText(String.valueOf(joindata.get("kota")));
                    pane.edprovinsi_karyawan.setText(String.valueOf(joindata.get("provinsi")));
                    pane.ednegara_karyawan.setText(String.valueOf(joindata.get("negara")));
                    pane.edtelepon_karyawan.setText(String.valueOf(joindata.get("telp")));
                    pane.edhp_karyawan.setText(String.valueOf(joindata.get("hp")));
                    pane.edcp_karyawan.setText(String.valueOf(joindata.get("kontak_person")));
                    pane.edpos_karyawan.setText(String.valueOf(joindata.get("kode_pos")));
                    pane.edfax_karyawan.setText(String.valueOf(joindata.get("fax")));
                    pane.edemail_karyawan.setText(String.valueOf(joindata.get("email")));
                    pane.edweb_karyawan.setText(String.valueOf(joindata.get("website")));
                    pane.edgolongan_karyawan.setText(String.valueOf(joindata.get("nama_golongan")));
                    pane.edjabatan_karyawan.setText(String.valueOf(joindata.get("jabatan")));
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
                    String data = String.format("data=kode='%s'::nama='%s'::tipe=2::isaktif='%s'::id_cards_klas='%s'::keterangan='%s'::id_golongan='%s'::jabatan='%s'&"
                            + "lokasi=alamat='%s'::kota='%s'::kode_pos='%s'::provinsi='%s'::negara='%s'::telp='%s'::fax='%s'::"
                            + "email='%s'::website='%s'::kontak_person='%s'::hp='%s'",
                            pane.edkode_karyawan.getText(),
                            pane.ednama_karyawan.getText(),
                            ckval,
                            valklasifikasi,
                            pane.taketerangan_karyawan.getText(),
                            valgolongan,
                            pane.edjabatan_karyawan.getText(),
                            pane.edalamat_karyawan.getText(),
                            pane.edkota_karyawan.getText(),
                            pane.edpos_karyawan.getText(),
                            pane.edprovinsi_karyawan.getText(),
                            pane.ednegara_karyawan.getText(),
                            pane.edtelepon_karyawan.getText(),
                            pane.edfax_karyawan.getText(),
                            pane.edemail_karyawan.getText(),
                            pane.edweb_karyawan.getText(),
                            pane.edcp_karyawan.getText(),
                            pane.edhp_karyawan.getText());
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
                    String data = String.format("data=kode='%s'::nama='%s'::tipe=2::isaktif='%s'::id_cards_klas='%s'::keterangan='%s'::id_golongan='%s'::jabatan='%s'&"
                            + "lokasi=alamat='%s'::kota='%s'::kode_pos='%s'::provinsi='%s'::negara='%s'::telp='%s'::fax='%s'::"
                            + "email='%s'::website='%s'::kontak_person='%s'::hp='%s'",
                            pane.edkode_karyawan.getText(),
                            pane.ednama_karyawan.getText(),
                            ckval,
                            valklasifikasi,
                            pane.taketerangan_karyawan.getText(),
                            valgolongan,
                            pane.edjabatan_karyawan.getText(),
                            pane.edalamat_karyawan.getText(),
                            pane.edkota_karyawan.getText(),
                            pane.edpos_karyawan.getText(),
                            pane.edprovinsi_karyawan.getText(),
                            pane.ednegara_karyawan.getText(),
                            pane.edtelepon_karyawan.getText(),
                            pane.edfax_karyawan.getText(),
                            pane.edemail_karyawan.getText(),
                            pane.edweb_karyawan.getText(),
                            pane.edcp_karyawan.getText(),
                            pane.edhp_karyawan.getText(),
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
