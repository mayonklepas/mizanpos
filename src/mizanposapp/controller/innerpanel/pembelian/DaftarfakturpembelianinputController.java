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
import mizanposapp.view.innerpanel.pembelian.Daftarfakturpembelian_input_panel;

/**
 *
 * @author Minami
 */
public class DaftarfakturpembelianinputController {

    String id;
    CrudHelper ch = new CrudHelper();
    Daftarfakturpembelian_input_panel pane;

    public DaftarfakturpembelianinputController(Daftarfakturpembelian_input_panel pane) {
        this.pane = pane;
        carisuplier();
        carigudang();
        caridepartment();
    }

    private void carisuplier() {
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

    private void carigudang() {
        pane.bcari_gudang.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("gudang", "popupdaftargudang", "Daftar Gudang"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
        });

    }

    private void caridepartment() {
        pane.bcari_department.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("department", "popupdaftardepartment", "Daftar Department"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
        });

    }

}
