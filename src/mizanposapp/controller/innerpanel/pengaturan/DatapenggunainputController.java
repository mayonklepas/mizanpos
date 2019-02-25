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
import mizanposapp.helper.Staticvar;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.pengaturan.Data_pengguna_input_panel;

/**
 *
 * @author Minami
 */
public class DatapenggunainputController {

    Data_pengguna_input_panel pane;
    String valhakakses = "";

    public DatapenggunainputController(Data_pengguna_input_panel pane) {
        this.pane = pane;
        carihakakses();
    }

    private void carihakakses() {
        pane.bcarihakakses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Staticvar.sfilter = "";
                Staticvar.preid = valhakakses;
                Staticvar.prelabel = pane.edhakakses.getText();
                JDialog jd = new JDialog(new Mainmenu());
                jd.add(new Popupcari("hakakses", "popupdaftarpenggunalevel", "Daftar Hak Akses"));
                jd.pack();
                jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                jd.setLocationRelativeTo(null);
                jd.setVisible(true);
                jd.toFront();
                valhakakses = Staticvar.resid;
                pane.edhakakses.setText(Staticvar.reslabel);
            }
        });
    }

}
