/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.pembelian;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.innerpanel.pembelian.Daftarreturpembelian_inner_panel;
import mizanposapp.view.innerpanel.pembelian.Daftarreturpembelian_input_panel;

/**
 *
 * @author Minami
 */
public class DaftarreturpembelianinnerController {

    public DaftarreturpembelianinnerController(Daftarreturpembelian_inner_panel dpip) {
        inputdata(dpip);
        editdata(dpip);

    }

    private void inputdata(Daftarreturpembelian_inner_panel dpip) {
        dpip.btambah.addActionListener((ActionEvent e) -> {
            Daftarreturpembelian_input_panel pane = new Daftarreturpembelian_input_panel();
            Staticvar.pmp.container.removeAll();
            Staticvar.pmp.container.setLayout(new BorderLayout());
            Staticvar.pmp.container.add(pane, BorderLayout.CENTER);
            Staticvar.pmp.container.revalidate();
            Staticvar.pmp.container.repaint();
        });
    }

    private void editdata(Daftarreturpembelian_inner_panel dpip) {
        dpip.bedit.addActionListener((ActionEvent e) -> {
            Daftarreturpembelian_input_panel pane = new Daftarreturpembelian_input_panel();
            Staticvar.pmp.container.removeAll();
            Staticvar.pmp.container.setLayout(new BorderLayout());
            Staticvar.pmp.container.add(pane, BorderLayout.CENTER);
            Staticvar.pmp.container.revalidate();
            Staticvar.pmp.container.repaint();
        });
    }

}
