/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller;

import java.awt.BorderLayout;
import mizanchart.Mizanchart;
import mizanposapp.view.Beranda_panel;

/**
 *
 * @author Minami
 */
public class BerandaController {

    Beranda_panel pane;

    public BerandaController(Beranda_panel pane) {
        this.pane = pane;
        showchart();
    }

    private void showchart() {
        Mizanchart mc = new Mizanchart();
        pane.mainpane.add(mc.showchart());
    }

}
