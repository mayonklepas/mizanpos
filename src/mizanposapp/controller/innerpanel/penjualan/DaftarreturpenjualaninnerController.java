/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.penjualan;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.innerpanel.penjualan.Daftarreturpenjualan_inner_panel;
import mizanposapp.view.innerpanel.penjualan.Daftarreturpenjualan_input_panel;

/**
 *
 * @author Minami
 */
public class DaftarreturpenjualaninnerController {

    public DaftarreturpenjualaninnerController(Daftarreturpenjualan_inner_panel dpip) {
        inputdata(dpip);
        editdata(dpip);

    }

    private void inputdata(Daftarreturpenjualan_inner_panel dpip) {
        dpip.btambah.addActionListener((ActionEvent e) -> {
            Daftarreturpenjualan_input_panel pane = new Daftarreturpenjualan_input_panel();
            Staticvar.pp.container.removeAll();
            Staticvar.pp.container.setLayout(new BorderLayout());
            Staticvar.pp.container.add(pane, BorderLayout.CENTER);
            Staticvar.pp.container.revalidate();
            Staticvar.pp.container.repaint();
        });
    }

    private void editdata(Daftarreturpenjualan_inner_panel dpip) {
        dpip.bedit.addActionListener((ActionEvent e) -> {
            Daftarreturpenjualan_input_panel pane = new Daftarreturpenjualan_input_panel();
            Staticvar.pp.container.removeAll();
            Staticvar.pp.container.setLayout(new BorderLayout());
            Staticvar.pp.container.add(pane, BorderLayout.CENTER);
            Staticvar.pp.container.revalidate();
            Staticvar.pp.container.repaint();
        });
    }

}
