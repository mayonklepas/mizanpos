/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.pengaturan;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JDialog;
import static mizanposapp.controller.innerpanel.pengaturan.LoginController.username;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.FuncHelper;
import mizanposapp.helper.Globalsession;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Errorpanel;
import mizanposapp.view.innerpanel.pengaturan.Gantipassword_panel;

/**
 *
 * @author Minami
 */
public class GantipasswordController {

    Gantipassword_panel pane;
    CrudHelper ch = new CrudHelper();

    public GantipasswordController(Gantipassword_panel pane) {
        this.pane = pane;
        loaddata();
        simpan();
        batal();
    }

    private void loaddata() {
        pane.edusername.setText(Globalsession.nama_user);
        pane.edusername.setEnabled(false);
        username = "";

    }

    private void simpan() {
        pane.bsimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pane.edpass.getText().equals(pane.edrepass.getText())) {
                    rawsimpan();
                } else {
                    FuncHelper.showmessage("Konfirmasi Password Salah", "Password dan Retype Password tidak cocok, Cek kembali kemudian ulangi");
                }

            }
        });

        pane.edrepass.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (pane.edpass.getText().equals(pane.edrepass.getText())) {
                        rawsimpan();
                    } else {
                        FuncHelper.showmessage("Konfirmasi Password Salah", "Password dan Retype Password tidak cocok, Cek kembali kemudian ulangi");
                    }
                }
            }

        });

        pane.edpass.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    pane.edrepass.requestFocus();
                }
            }

        });
    }

    private void rawsimpan() {
        String data = "nama=" + pane.edusername.getText().trim() + "&pwd=" + pane.edpass.getText() + "";
        ch.insertdata("dm/updatepassword", data);
        if (Staticvar.getresult.equals("berhasil")) {
            Staticvar.isupdate = true;
            JDialog jd = (JDialog) pane.getRootPane().getParent();
            jd.dispose();
        } else {
            Staticvar.isupdate = false;
            JDialog jd = new JDialog(new Mainmenu());
            Errorpanel ep = new Errorpanel();
            ep.ederror.setText(Staticvar.getresult);
            jd.add(ep);
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
        }
    }

    private void batal() {
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
