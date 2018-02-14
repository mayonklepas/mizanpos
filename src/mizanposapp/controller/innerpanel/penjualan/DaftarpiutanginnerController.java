/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.penjualan;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.innerpanel.pembelian.Daftarpembayaranpiutangperinvoice_input_panel;
import mizanposapp.view.innerpanel.penjualan.Daftarpiutang_inner_panel;
import mizanposapp.view.innerpanel.penjualan.Daftarpiutangrincian_inner_panel;

/**
 *
 * @author Minami
 */
public class DaftarpiutanginnerController {

    public DaftarpiutanginnerController(Daftarpiutang_inner_panel dpip) {
        inputdata(dpip);
        editdata(dpip);

    }

    private void inputdata(Daftarpiutang_inner_panel dpip) {
        dpip.bbayar.addActionListener((ActionEvent e) -> {
            Daftarpembayaranpiutangperinvoice_input_panel pane = new Daftarpembayaranpiutangperinvoice_input_panel();
            Staticvar.pp.container.removeAll();
            Staticvar.pp.container.setLayout(new BorderLayout());
            Staticvar.pp.container.add(pane, BorderLayout.CENTER);
            Staticvar.pp.container.revalidate();
            Staticvar.pp.container.repaint();
        });
    }

    private void editdata(Daftarpiutang_inner_panel dpip) {
        dpip.bdetailbayar.addActionListener((ActionEvent e) -> {
            Daftarpiutangrincian_inner_panel pane = new Daftarpiutangrincian_inner_panel();
            Staticvar.pp.container.removeAll();
            Staticvar.pp.container.setLayout(new BorderLayout());
            Staticvar.pp.container.add(pane, BorderLayout.CENTER);
            Staticvar.pp.container.revalidate();
            Staticvar.pp.container.repaint();
        });
    }

}
