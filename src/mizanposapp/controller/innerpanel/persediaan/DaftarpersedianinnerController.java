/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.persediaan;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JDialog;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.innerpanel.persediaan.Daftarpersediaan_input_panel;
import mizanposapp.view.innerpanel.persediaan.Daftarpersedian_inner_panel;
import mizanposapp.view.innerpanel.persediaan.Persedian_koreksistock_input_panel;

/**
 *
 * @author Minami
 */
public class DaftarpersedianinnerController {

    public DaftarpersedianinnerController(Daftarpersedian_inner_panel pane) {
        inputdata(pane);
        editdata(pane);
        pilihdata(pane);
        koreksistock(pane);
    }

    private void inputdata(Daftarpersedian_inner_panel pane) {
        pane.btambah.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Daftarpersediaan_input_panel());
            jd.pack();
            jd.setLocationRelativeTo(null);
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setTitle("Input Data Persediaan");
            jd.setVisible(true);
        });
    }

    private void editdata(Daftarpersedian_inner_panel pane) {
        pane.bedit.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Daftarpersediaan_input_panel());
            jd.pack();
            jd.setLocationRelativeTo(null);
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setTitle("Input Data Persediaan");
            jd.setVisible(true);
        });
    }

    private void pilihdata(Daftarpersedian_inner_panel pane) {
        pane.tabledata.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (pane.persediaanpopup.isPopupTrigger(e)) {
                    pane.persediaanpopup.show(pane.tabledata, e.getX(), e.getY());
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    private void koreksistock(Daftarpersedian_inner_panel pane) {
        pane.mkoreksi_stock.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Persedian_koreksistock_input_panel());
            jd.pack();
            jd.setLocationRelativeTo(null);
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setTitle("Koreksi Stock");
            jd.setVisible(true);
        });
    }

}
