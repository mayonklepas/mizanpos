/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.persediaan;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import javax.swing.JDialog;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.persediaan.Daftarpersediaan_input_panel;

/**
 *
 * @author Minami
 */
public class DaftarpersediaaninputController {

    String id;
    CrudHelper ch = new CrudHelper();
    Daftarpersediaan_input_panel pane;
    String kelompok, supplier, merek, satuan, lokasi, department;

    public DaftarpersediaaninputController(Daftarpersediaan_input_panel pane) {
        this.pane = pane;
        carikelompokpersediaan();
        carisupplier();
        carimerek();
        carisatuan();
        carilokasi();
        caridepartment();
    }

    private void carikelompokpersediaan() {
        pane.bcari_kelompok.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("kelompokbarang", "popupdaftarkelompokpersediaan", "Daftar Kelompok Persediaan"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            if (Staticvar.isupdate == true) {
                kelompok = Staticvar.resid;
                pane.tkelompok.setText(Staticvar.reslabel);
            }

        });

    }

    private void carisupplier() {
        pane.bcari_supplier.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("nama", "popupdaftarnama?tipe=1", "Daftar Supplier"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            if (Staticvar.isupdate == true) {
                supplier = Staticvar.resid;
                pane.tsupplier.setText(Staticvar.reslabel);
            }
        });

    }

    private void carimerek() {
        pane.bcari_merek.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("merek", "popupdaftarmerekpersediaan", "Daftar Merek"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            if (Staticvar.isupdate == true) {
                merek = Staticvar.resid;
                pane.tmerk.setText(Staticvar.reslabel);
            }
        });

    }

    private void carisatuan() {
        pane.bcari_satuan.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("satuan", "popupdaftarsatuan", "Daftar Satuan"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            if (Staticvar.isupdate == true) {
                satuan = Staticvar.resid;
                pane.tsatuan.setText(Staticvar.reslabel);
            }
        });

    }

    private void carilokasi() {
        pane.bcari_lokasi.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("lokasi", "popupdaftarlokasipersediaan", "Daftar Lokasi Persediaan"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            if (Staticvar.isupdate == true) {
                lokasi = Staticvar.resid;
                pane.tlokasi.setText(Staticvar.reslabel);
            }
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
            if (Staticvar.isupdate == true) {
                department = Staticvar.resid;
                pane.tdepartment.setText(Staticvar.reslabel);
            }
        });

    }

}
