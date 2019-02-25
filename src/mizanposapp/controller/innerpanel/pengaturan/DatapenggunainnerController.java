/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.pengaturan;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.innerpanel.pengaturan.Data_pengguna_inner_panel;
import mizanposapp.view.innerpanel.pengaturan.Data_pengguna_input_panel;

/**
 *
 * @author Minami
 */
public class DatapenggunainnerController {

    Data_pengguna_inner_panel pane;

    public DatapenggunainnerController(Data_pengguna_inner_panel pane) {
        this.pane = pane;
        databaru();
    }

    private void databaru() {
        pane.bbaru.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog jd = new JDialog(new Mainmenu());
                jd.add(new Data_pengguna_input_panel());
                jd.pack();
                jd.setLocationRelativeTo(null);
                jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                jd.setTitle("Input Data Pengguna");
                jd.setVisible(true);
            }
        });
    }

}
