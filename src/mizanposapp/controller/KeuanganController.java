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
import mizanposapp.view.Keuangan_panel;
import mizanposapp.view.innerpanel.keuangan.Daftarkasmasuk_inner_panel;
import mizanposapp.view.innerpanel.pembelian.Cekhargabeli_inner_panel;
import mizanposapp.view.innerpanel.pembelian.Daftardatasupplier_inner_panel;
import mizanposapp.view.innerpanel.pembelian.Daftarfakturpembelian_inner_panel;
import mizanposapp.view.innerpanel.pembelian.Daftarhutang_inner_panel;
import mizanposapp.view.innerpanel.pembelian.Daftarorderpembelian_inner_panel;
import mizanposapp.view.innerpanel.pembelian.Daftarreturpembelian_inner_panel;

/**
 *
 * @author Minami
 */
public class KeuanganController {

    Keuangan_panel pp;

    public KeuanganController() {
    }

    public KeuanganController(Mainmenu mm) {

    }

    public KeuanganController(Keuangan_panel pp) {
        kasmasukview(pp);
        kaskeluarview(pp);
        transferkasview(pp);
        giromasukview(pp);
        girokeluarview(pp);
    }

    private void kasmasukviewauto(Keuangan_panel pp) {
        System.gc();
        Daftardatasupplier_inner_panel pane = new Daftardatasupplier_inner_panel();
        Staticvar.kp = pp;
        pp.container.removeAll();
        pp.container.setLayout(new BorderLayout());
        pp.container.add(pane, BorderLayout.CENTER);
        pp.container.revalidate();
        pp.container.repaint();
    }

    private void kasmasukview(Keuangan_panel pp) {
        pp.bkasmasuk.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.gc();
                Daftarkasmasuk_inner_panel pane = new Daftarkasmasuk_inner_panel();
                Staticvar.kp = pp;
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

    private void kaskeluarview(Keuangan_panel pp) {
        pp.bkaskeluar.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.gc();
                Daftarorderpembelian_inner_panel pane = new Daftarorderpembelian_inner_panel();
                Staticvar.kp = pp;
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

    private void transferkasview(Keuangan_panel pp) {
        pp.btransferkas.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.gc();
                Daftarreturpembelian_inner_panel pane = new Daftarreturpembelian_inner_panel();
                Staticvar.kp = pp;
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

    private void giromasukview(Keuangan_panel pp) {
        pp.lgiromasuk.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.gc();
                Daftarfakturpembelian_inner_panel pane = new Daftarfakturpembelian_inner_panel();
                Staticvar.kp = pp;
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

    private void girokeluarview(Keuangan_panel pp) {
        pp.lgirokeluar.addMouseListener(new MouseListener() {
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
                System.gc();
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
