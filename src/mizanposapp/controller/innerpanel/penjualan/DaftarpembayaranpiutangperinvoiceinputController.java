/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.penjualan;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import javax.swing.JDialog;
import mizanposapp.helper.CrudHelper;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.penjualan.Daftarpembayaranpiutangperinvoice_input_panel;

/**
 *
 * @author Minami
 */
public class DaftarpembayaranpiutangperinvoiceinputController {

    String id;
    CrudHelper ch = new CrudHelper();
    Daftarpembayaranpiutangperinvoice_input_panel pane;

    public DaftarpembayaranpiutangperinvoiceinputController(Daftarpembayaranpiutangperinvoice_input_panel pane) {
        this.pane = pane;
        caripelanggan();
    }

    private void caripelanggan() {
        pane.bcari_pelanggan.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("nama", "popupdaftarnama?tipe=1", "Daftar Pelanggan"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
        });

    }

}
