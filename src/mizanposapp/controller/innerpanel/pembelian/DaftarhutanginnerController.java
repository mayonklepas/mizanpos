/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.pembelian;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.innerpanel.pembelian.Daftarpembayaranhutangperinvoice_input_panel;
import mizanposapp.view.innerpanel.pembelian.Daftarhutang_inner_panel;
import mizanposapp.view.innerpanel.pembelian.Daftarhutangrincian_inner_panel;

/**
 *
 * @author Minami
 */
public class DaftarhutanginnerController {

    public DaftarhutanginnerController(Daftarhutang_inner_panel dpip) {
        inputdata(dpip);
        editdata(dpip);

    }

    private void inputdata(Daftarhutang_inner_panel dpip) {
        dpip.bbayar.addActionListener((ActionEvent e) -> {
            Daftarpembayaranhutangperinvoice_input_panel pane = new Daftarpembayaranhutangperinvoice_input_panel();
            Staticvar.pmp.container.removeAll();
            Staticvar.pmp.container.setLayout(new BorderLayout());
            Staticvar.pmp.container.add(pane, BorderLayout.CENTER);
            Staticvar.pmp.container.revalidate();
            Staticvar.pmp.container.repaint();
        });
    }

    private void editdata(Daftarhutang_inner_panel dpip) {
        dpip.bdetailbayar.addActionListener((ActionEvent e) -> {
            Daftarpembayaranhutangperinvoice_input_panel pane = new Daftarpembayaranhutangperinvoice_input_panel();
            Staticvar.pmp.container.removeAll();
            Staticvar.pmp.container.setLayout(new BorderLayout());
            Staticvar.pmp.container.add(pane, BorderLayout.CENTER);
            Staticvar.pmp.container.revalidate();
            Staticvar.pmp.container.repaint();
        });
    }

}
