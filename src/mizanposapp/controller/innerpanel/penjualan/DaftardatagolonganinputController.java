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
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Errorpanel;
import mizanposapp.view.innerpanel.penjualan.Daftardatagolongan_input_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class DaftardatagolonganinputController {

    String id;
    CrudHelper ch = new CrudHelper();
    Daftardatagolongan_input_panel pane;
    int tipe;
    int hargadasar;

    public DaftardatagolonganinputController(Daftardatagolongan_input_panel pane) {
        this.pane = pane;
        loaddata();
        tutup();
        simpandata();
    }

    private void loaddata() {
        try {
            id = Staticvar.ids;
            if (id.equals("")) {
                pane.edkode_golongan.setText("");
                pane.ednama_golongan.setText("");
                pane.eddiskon_penjualan.setText("0");
                pane.edketerangan_golongan.setText("");
                pane.edharga_jual_golongan.setText("0");
                pane.cmbharga_berdasarkan.setSelectedIndex(0);
                pane.cmbtipe_golongan.setSelectedIndex(0);
            } else {
                JSONParser jpdata = new JSONParser();
                String param = String.format("id=%s", id);
                Object objdata = jpdata.parse(ch.getdatadetails("dm/datagolongan", param));
                JSONArray jadata = (JSONArray) objdata;
                for (int i = 0; i < jadata.size(); i++) {
                    JSONObject joindata = (JSONObject) jadata.get(i);
                    pane.edkode_golongan.setText(String.valueOf(joindata.get("kode")));
                    pane.ednama_golongan.setText(String.valueOf(joindata.get("nama")));
                    pane.eddiskon_penjualan.setText(String.valueOf(joindata.get("diskon")));
                    pane.edketerangan_golongan.setText(String.valueOf(joindata.get("keterangan")));
                    pane.edharga_jual_golongan.setText(String.valueOf(joindata.get("hargajual")));
                    tipe = Integer.parseInt(String.valueOf(joindata.get("tipe")));
                    switch (tipe) {
                        case 0:
                            pane.cmbtipe_golongan.setSelectedIndex(1);
                            break;
                        case 2:
                            pane.cmbtipe_golongan.setSelectedIndex(2);
                            break;
                        case -1:
                            pane.cmbtipe_golongan.setSelectedIndex(3);
                            break;
                        default:
                            pane.cmbtipe_golongan.setSelectedIndex(0);
                            break;
                    }
                    hargadasar = Integer.parseInt(String.valueOf(joindata.get("hargajual_berdasar")));
                    switch (hargadasar) {
                        case 1:
                            pane.cmbharga_berdasarkan.setSelectedIndex(1);
                            break;
                        case 2:
                            pane.cmbharga_berdasarkan.setSelectedIndex(2);
                            break;
                        default:
                            pane.cmbharga_berdasarkan.setSelectedIndex(0);
                            break;
                    }

                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(DaftardatagolonganinputController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void simpandata() {
        pane.bsimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Staticvar.isupdate = true;
                int indexhargadasar = pane.cmbharga_berdasarkan.getSelectedIndex();
                switch (indexhargadasar) {
                    case 1:
                        hargadasar = 1;
                        break;
                    case 2:
                        hargadasar = 2;
                        break;
                    default:
                        hargadasar = 0;
                        break;
                }
                int indextipe = pane.cmbtipe_golongan.getSelectedIndex();
                switch (indextipe) {
                    case 1:
                        tipe = 0;
                        break;
                    case 2:
                        tipe = 2;
                        break;
                    case 3:
                        tipe = -1;
                        break;
                    default:
                        tipe = -1;
                        break;
                }
                if (id.equals("")) {
                    String data = String.format("data=kode='%s'::nama='%s'::tipe='%s'::diskon='%s'::keterangan='%s'::hargajual='%s'::hargajual_berdasar='%s'",
                            pane.edkode_golongan.getText(),
                            pane.ednama_golongan.getText(),
                            tipe,
                            pane.eddiskon_penjualan.getText(),
                            pane.edketerangan_golongan.getText(),
                            pane.edharga_jual_golongan.getText(),
                            hargadasar);
                    ch.insertdata("dm/insertgolongan", data);
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
                    String data = String.format("data=kode='%s'::nama='%s'::tipe='%s'::diskon='%s'::keterangan='%s'::hargajual='%s'::hargajual_berdasar='%s'",
                            pane.edkode_golongan.getText(),
                            pane.ednama_golongan.getText(),
                            tipe,
                            pane.eddiskon_penjualan.getText(),
                            pane.edketerangan_golongan.getText(),
                            pane.edharga_jual_golongan.getText(),
                            hargadasar);
                    ch.updatedata("dm/updategolongan", data, id);
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
