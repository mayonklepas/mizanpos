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
import javax.swing.JOptionPane;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.FuncHelper;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Errorpanel;
import mizanposapp.view.innerpanel.pengaturan.Login_panel;

/**
 *
 * @author Minami
 */
public class LoginController {

    Login_panel pane;
    CrudHelper ch = new CrudHelper();
    public static String username = "";

    public LoginController(Login_panel pane) {
        this.pane = pane;
        loaddata();
        login();
        batal();
    }

    private void loaddata() {
        if (username.equals("")) {
            pane.edusername.setEnabled(true);
        } else {
            pane.edusername.setText(username);
            pane.edusername.setEnabled(false);
            username = "";
        }

    }

    private void login() {
        pane.blogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rawlogin();
            }
        });

        pane.edpassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    rawlogin();
                }
            }

        });

        pane.edusername.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    pane.edpassword.requestFocus();
                }
            }

        });
    }

    private void rawlogin() {
        String data = "nama=" + pane.edusername.getText().trim() + "&pwd=" + pane.edpassword.getText() + "";
        ch.insertdata("dm/cekpengguna", data);
        if (Staticvar.getresult.split("#")[0].equals("berhasil")) {
            Staticvar.isupdate = true;
            if (pane.edusername.isEnabled() == true) {
                Staticvar.id_user_aktif = Staticvar.getresult.split("#")[1];
            }
            JDialog jd = (JDialog) pane.getRootPane().getParent();
            jd.dispose();
            username = pane.edusername.getText();
        } else if (Staticvar.getresult.equals("gagal")) {
            FuncHelper.showmessage("Gagal Mengautentifikasi", "Username atau Password yang anda masukan salah, cek kemudian coba ulang kembali");
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
