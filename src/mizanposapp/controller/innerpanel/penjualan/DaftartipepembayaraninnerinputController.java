/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.penjualan;

import mizanposapp.controller.innerpanel.persediaan.*;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import jdk.nashorn.internal.parser.TokenType;
import mizanposapp.helper.ConvertFunc;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Errorpanel;
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.penjualan.Daftartipe_pembayaran_input_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class DaftartipepembayaraninnerinputController {

    String id;
    CrudHelper ch = new CrudHelper();
    Daftartipe_pembayaran_input_panel pane;
    String valakun_pembayaran, valakun_charge;
    ButtonGroup btpembayaran = new ButtonGroup();
    ButtonGroup btkartu = new ButtonGroup();

    public DaftartipepembayaraninnerinputController(Daftartipe_pembayaran_input_panel pane) {
        this.pane = pane;
        loadradiogroup();
        cariakunpembayaran();
        cariakuncharge();
        loaddata();
        tutup();
        simpandata();
    }

    private void loadradiogroup() {
        btpembayaran.add(pane.rbvoucher);
        btpembayaran.add(pane.rbkartu);
        btkartu.add(pane.rbkartu_kredit);
        btkartu.add(pane.rbkartu_debit);
    }

    private void loaddata() {
        try {
            id = Staticvar.ids;
            if (id.equals("")) {
                pane.ednama.setText("");
                pane.edcharge.setText("");
                pane.edakun_pembayaran.setText("0");
                pane.edakun_charge.setText("");

            } else {
                JSONParser jpdata = new JSONParser();
                String param = String.format("id=%s", id);
                Object objdata = jpdata.parse(ch.getdatadetails("dm/datapostbayardengan", param));
                JSONArray jadata = (JSONArray) objdata;
                for (int i = 0; i < jadata.size(); i++) {
                    JSONObject joindata = (JSONObject) jadata.get(i);
                    pane.ednama.setText(String.valueOf(joindata.get("nama")));
                    pane.edcharge.setText(String.valueOf(joindata.get("charge")));
                    pane.edakun_pembayaran.setText(joindata.get("akun_pembayaran") + " : " + String.valueOf(joindata.get("nama_akun_")));
                    pane.edakun_charge.setText(String.valueOf(joindata.get("akun_charge")) + " : " + String.valueOf(joindata.get("nama_akun_charge")));
                    int isvoucer = ConvertFunc.ToInt(String.valueOf(joindata.get("isvoucher")));
                    int iscard = ConvertFunc.ToInt(String.valueOf(joindata.get("iscard")));
                    int isdebitcard = ConvertFunc.ToInt(String.valueOf(joindata.get("isdebitcard")));
                    int iskreditcard = ConvertFunc.ToInt(String.valueOf(joindata.get("iskreditcard")));
                    if (iscard == 1) {
                        pane.rbkartu.setSelected(true);
                        pane.pkartu.setVisible(true);
                    } else if (isvoucer == 1) {
                        pane.rbvoucher.setSelected(true);
                        pane.pkartu.setVisible(false);
                    }

                    if (isdebitcard == 1) {
                        pane.rbkartu_debit.setSelected(true);
                    } else if (iskreditcard == 1) {
                        pane.rbkartu_kredit.setSelected(true);
                    }

                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(DaftartipepembayaraninnerinputController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void simpandata() {
        pane.bsimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Staticvar.isupdate = true;
                String card = "0";
                String voucher = "0";
                String kreditcard = "0";
                String debitcard = "0";
                if (pane.rbkartu.isSelected()) {
                    card = "1";
                    voucher = "0";
                } else {
                    voucher = "0";
                    card = "1";
                }

                if (pane.rbkartu_debit.isSelected()) {
                    debitcard = "0";
                    kreditcard = "1";
                } else {
                    debitcard = "1";
                    kreditcard = "0";
                }
                if (id.equals("")) {
                    String data = "data=nama='" + ConvertFunc.EncodeString(pane.ednama.getText()) + "'::"
                         + "charge='" + ConvertFunc.EncodeString(pane.edcharge.getText()) + "'::"
                         + "akun_pembayaran='" + ConvertFunc.EncodeString(valakun_pembayaran) + "'::"
                         + "akun_charge='" + ConvertFunc.EncodeString(valakun_charge) + "'::"
                         + "isvoucher='" + voucher + "'::"
                         + "iscard='" + card + "'::"
                         + "isdebitcard='" + debitcard + "'::"
                         + "iskreditcard='" + kreditcard + "'";
                    ch.insertdata("dm/insertposbayardengan", data);
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
                    String data = "data=nama='" + ConvertFunc.EncodeString(pane.ednama.getText()) + "'::"
                         + "charge='" + ConvertFunc.EncodeString(pane.edcharge.getText()) + "'::"
                         + "akun_pembayaran='" + ConvertFunc.EncodeString(valakun_pembayaran) + "'::"
                         + "akun_charge='" + ConvertFunc.EncodeString(valakun_charge) + "'::"
                         + "isvoucher='" + voucher + "'::"
                         + "iscard='" + card + "'::"
                         + "isdebitcard='" + debitcard + "'::"
                         + "iskreditcard='" + kreditcard + "'";
                    ch.updatedata("dm/updateposbayardengan", data, id);
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

    private void rawgetidakun(String prevlabel, String previd) {
        Staticvar.sfilter = "";
        Staticvar.preid = previd;
        Staticvar.prelabel = prevlabel;
        JDialog jd = new JDialog(new Mainmenu());
        jd.add(new Popupcari("akun", "popupdaftarakun", "Daftar Akun"));
        jd.pack();
        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        jd.setLocationRelativeTo(null);
        jd.setVisible(true);
        jd.toFront();
    }

    private void cariakunpembayaran() {
        pane.bcari_akun_pembayaran.addActionListener((ActionEvent e) -> {
            rawgetidakun(pane.edakun_pembayaran.getText(), valakun_pembayaran);
            if (!Staticvar.resid.equals(valakun_pembayaran)) {
                valakun_pembayaran = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.edakun_pembayaran.setText(val);
            }
        });

    }

    private void cariakuncharge() {
        pane.bcari_akun_charge.addActionListener((ActionEvent e) -> {
            rawgetidakun(pane.edakun_charge.getText(), valakun_charge);
            if (!Staticvar.resid.equals(valakun_charge)) {
                valakun_charge = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.edakun_charge.setText(val);
            }
        });

    }

}
