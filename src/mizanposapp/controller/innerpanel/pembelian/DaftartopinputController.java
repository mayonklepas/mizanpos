/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.pembelian;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Errorpanel;
import mizanposapp.view.innerpanel.pembelian.Daftartop_input_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class DaftartopinputController {

    String id;
    CrudHelper ch = new CrudHelper();
    Daftartop_input_panel pane;
    String valcod;
    StringBuilder sb = new StringBuilder();

    public DaftartopinputController(Daftartop_input_panel pane) {
        this.pane = pane;
        loaddata();
        tutup();
        simpandata();
        iscodcheck();
        generatecod();
    }

    private void iscodcheck() {
        pane.ckcod.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if (pane.ckcod.isSelected()) {
                    pane.edbayar_antara.setEnabled(false);
                    pane.eddapat_diskon.setEnabled(false);
                    pane.edjatuh_tempo.setEnabled(false);
                    pane.edtop.setText("COD");
                    pane.edbayar_antara.setText("0");
                    pane.eddapat_diskon.setText("0");
                    pane.edjatuh_tempo.setText("0");
                } else {
                    pane.edbayar_antara.setEnabled(true);
                    pane.eddapat_diskon.setEnabled(true);
                    pane.edjatuh_tempo.setEnabled(true);
                    pane.edtop.setText("");
                    pane.edbayar_antara.setText("");
                    pane.eddapat_diskon.setText("");
                    pane.edjatuh_tempo.setText("");
                }
            }
        });
    }

    private void loaddata() {
        try {
            id = Staticvar.ids;
            if (id.equals("")) {
                pane.edbayar_antara.setText("0");
                pane.eddapat_diskon.setText("0");
                pane.edjatuh_tempo.setText("0");
                pane.edaketerangan.setText("");
                pane.edtop.setText("Net");
                pane.edtop.setEnabled(false);
            } else {
                JSONParser jpdata = new JSONParser();
                String param = String.format("id=%s", id);
                Object objdata = jpdata.parse(ch.getdatadetails("dm/datatop", param));
                JSONArray jadata = (JSONArray) objdata;
                for (int i = 0; i < jadata.size(); i++) {
                    JSONObject joindata = (JSONObject) jadata.get(i);
                    int cod = Integer.parseInt(String.valueOf(joindata.get("iscod")));
                    if (cod == 0) {
                        pane.ckcod.setSelected(false);
                    } else {
                        pane.ckcod.setSelected(true);
                        pane.edbayar_antara.setEnabled(false);
                        pane.eddapat_diskon.setEnabled(false);
                        pane.edjatuh_tempo.setEnabled(false);
                    }
                    pane.edbayar_antara.setText(String.valueOf(joindata.get("diskon_hari")));
                    pane.eddapat_diskon.setText(String.valueOf(joindata.get("diskon_hari_persen")));
                    pane.edjatuh_tempo.setText(String.valueOf(joindata.get("tempo")));
                    pane.edaketerangan.setText(String.valueOf(joindata.get("keterangan")));
                    pane.edtop.setText(String.valueOf(joindata.get("top")));
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(DaftartopinputController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void simpandata() {
        pane.bsimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Staticvar.isupdate = true;
                if (pane.ckcod.isSelected()) {
                    valcod = "1";
                } else {
                    valcod = "0";
                }
                if (id.equals("")) {
                    String data = String.format("data=diskon_hari='%s'::"
                            + "diskon_hari_persen='%s'::"
                            + "tempo='%s'::"
                            + "keterangan='%s'::"
                            + "top='%s'::"
                            + "iscod='%s'",
                            pane.edbayar_antara.getText(),
                            pane.eddapat_diskon.getText(),
                            pane.edjatuh_tempo.getText(),
                            pane.edaketerangan.getText(),
                            pane.edtop.getText(),
                            valcod);
                    ch.insertdata("dm/inserttop", data);
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
                    String data = String.format("data=diskon_hari='%s'::"
                            + "diskon_hari_persen='%s'::"
                            + "tempo='%s'::"
                            + "keterangan='%s'::"
                            + "top='%s'::"
                            + "iscod='%s'",
                            pane.edbayar_antara.getText(),
                            pane.eddapat_diskon.getText(),
                            pane.edjatuh_tempo.getText(),
                            pane.edaketerangan.getText(),
                            pane.edtop.getText(),
                            valcod);
                    ch.updatedata("dm/updatetop", data, id);
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

    private void generatecod() {
        KeyAdapter keydiskon_hari = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                pane.edtop.setText(pane.edbayar_antara.getText());
            }

        };
        KeyAdapter keydapatdiskon = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                pane.edtop.setText(pane.eddapat_diskon.getText());
            }

        };
        KeyAdapter keytempo = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                pane.edtop.setText("Net" + pane.edjatuh_tempo.getText());
            }

        };
        pane.edbayar_antara.addKeyListener(keydiskon_hari);
        pane.eddapat_diskon.addKeyListener(keydapatdiskon);
        pane.edjatuh_tempo.addKeyListener(keytempo);

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
