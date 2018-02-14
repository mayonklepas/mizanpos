/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.penjualan;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import javax.swing.JDialog;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.innerpanel.persediaan.Daftardatadept_input_panel;
import mizanposapp.view.innerpanel.penjualan.Daftarpiutangrincian_inner_panel;

/**
 *
 * @author Minami
 */
public class DaftarpiutangrincianinnerController {

    public DaftarpiutangrincianinnerController(Daftarpiutangrincian_inner_panel dpip) {
        inputdata(dpip);
        editdata(dpip);

    }

    private void inputdata(Daftarpiutangrincian_inner_panel dpip) {
        dpip.btambah.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Daftardatadept_input_panel());
            jd.pack();
            jd.setLocationRelativeTo(null);
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setTitle("Input Data Departemen");
            jd.setVisible(true);
        });
    }

    private void editdata(Daftarpiutangrincian_inner_panel dpip) {
        dpip.bedit.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Daftardatadept_input_panel());
            jd.pack();
            jd.setLocationRelativeTo(null);
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setTitle("Edit Data Departemen");
            jd.setVisible(true);
        });
    }

}
