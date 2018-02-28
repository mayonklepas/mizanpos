/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.pembelian;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import javax.swing.JDialog;
import mizanposapp.helper.CrudHelper;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.pembelian.Daftarpembayaranhutangperinvoice_input_panel;

/**
 *
 * @author Minami
 */
public class DaftarpembayaranhutangperinvoiceinputController {

    String id;
    CrudHelper ch = new CrudHelper();
    Daftarpembayaranhutangperinvoice_input_panel pane;

    public DaftarpembayaranhutangperinvoiceinputController(Daftarpembayaranhutangperinvoice_input_panel pane) {
        this.pane = pane;
        caripelanggan();
    }

    private void caripelanggan() {
        pane.bcari_supplier.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("nama", "popupdaftarnama?tipe=0", "Daftar Supplier"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
        });

    }

}
