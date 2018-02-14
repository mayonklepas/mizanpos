/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.Pembelian_panel;
import mizanposapp.view.innerpanel.pembelian.Cekhargabeli_inner_panel;
import mizanposapp.view.innerpanel.persediaan.Daftardatasupplier_inner_panel;
import mizanposapp.view.innerpanel.pembelian.Daftarfakturpembelian_inner_panel;
import mizanposapp.view.innerpanel.pembelian.Daftarhutang_inner_panel;
import mizanposapp.view.innerpanel.pembelian.Daftarreturpembelian_inner_panel;

/**
 *
 * @author Minami
 */
public class PembelianController {

    //Pembelian_panel pp;
    public PembelianController() {
    }

    public PembelianController(Mainmenu mm) {

    }

    public PembelianController(Pembelian_panel pp) {
        returview(pp);
        fakturpembelianview(pp);
        hutangview(pp);
        cekhargabarangview(pp);
        supplierview(pp);
    }

    private void supplierview(Pembelian_panel pp) {
        Daftardatasupplier_inner_panel pane = new Daftardatasupplier_inner_panel();
        Staticvar.pmp = pp;
        pp.container.removeAll();
        pp.container.setLayout(new BorderLayout());
        pp.container.add(pane, BorderLayout.CENTER);
        pp.container.revalidate();
        pp.container.repaint();
    }

    private void returview(Pembelian_panel pp) {
        pp.breturpembelian.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Daftarreturpembelian_inner_panel pane = new Daftarreturpembelian_inner_panel();
                Staticvar.pmp = pp;
                pp.container.removeAll();
                pp.container.setLayout(new BorderLayout());
                pp.container.add(pane, BorderLayout.CENTER);
                pp.container.revalidate();
                pp.container.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //pp.bpenyesuaian.setBackground(new Color(3, 3, 3));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    private void fakturpembelianview(Pembelian_panel pp) {
        pp.bfakturpembelian.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Daftarfakturpembelian_inner_panel pane = new Daftarfakturpembelian_inner_panel();
                Staticvar.pmp = pp;
                pp.container.removeAll();
                pp.container.setLayout(new BorderLayout());
                pp.container.add(pane, BorderLayout.CENTER);
                pp.container.revalidate();
                pp.container.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //pp.bpenyesuaian.setBackground(new Color(3, 3, 3));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    private void hutangview(Pembelian_panel pp) {
        pp.butangusaha.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Daftarhutang_inner_panel pane = new Daftarhutang_inner_panel();
                Staticvar.pmp = pp;
                pp.container.removeAll();
                pp.container.setLayout(new BorderLayout());
                pp.container.add(pane, BorderLayout.CENTER);
                pp.container.revalidate();
                pp.container.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //pp.bpenyesuaian.setBackground(new Color(3, 3, 3));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    private void cekhargabarangview(Pembelian_panel pp) {
        pp.lcekhargabarang.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                /*Mainmenu mm = (Mainmenu) pp.getRootPane().getParent();
                Settingdiskonkelompok_inner_panel pane = new Settingdiskonkelompok_inner_panel();
                mm.panel_tengah.removeAll();
                mm.panel_tengah.setLayout(new BorderLayout());
                mm.panel_tengah.add(pane, BorderLayout.CENTER);
                mm.panel_tengah.revalidate();
                mm.panel_tengah.repaint();*/
                Cekhargabeli_inner_panel pane = new Cekhargabeli_inner_panel();
                pp.container.removeAll();
                pp.container.setLayout(new BorderLayout());
                pp.container.add(pane, BorderLayout.CENTER);
                pp.container.revalidate();
                pp.container.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //pp.bpenyesuaian.setBackground(new Color(3, 3, 3));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

}
