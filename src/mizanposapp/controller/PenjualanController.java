/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.Penjualan_panel;
import mizanposapp.view.innerpanel.penjualan.Daftarfakturpenjualan_inner_panel;
import mizanposapp.view.innerpanel.penjualan.Daftardatakaryawan_inner_panel;
import mizanposapp.view.innerpanel.penjualan.Daftarpiutang_inner_panel;
import mizanposapp.view.innerpanel.penjualan.Daftarreturpenjualan_inner_panel;
import mizanposapp.view.innerpanel.penjualan.POS_panel;
import mizanposapp.view.innerpanel.penjualan.Settingbonuspenjualan_inner_panel;
import mizanposapp.view.innerpanel.penjualan.Settingdiskonharian_inner_panel;
import mizanposapp.view.innerpanel.penjualan.Settingdiskonkelompok_inner_panel;

/**
 *
 * @author Minami
 */
public class PenjualanController {

    //Penjualan_panel pp;
    public PenjualanController() {
    }

    public PenjualanController(Mainmenu mm) {

    }

    public PenjualanController(Penjualan_panel pp) {
        returview(pp);
        fakturpenjualanview(pp);
        piutangviewview(pp);
        posview(pp);
        karyawanview(pp);
        settingharidiskonview(pp);
        settingbonuspenjualanview(pp);
        settingdiskonperkelompokview(pp);
    }

    private void returview(Penjualan_panel pp) {
        pp.breturpenjualan.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.gc();
                Daftarreturpenjualan_inner_panel pane = new Daftarreturpenjualan_inner_panel();
                Staticvar.pp = pp;
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

    private void fakturpenjualanview(Penjualan_panel pp) {
        pp.bfakturpenjualan.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.gc();
                Daftarfakturpenjualan_inner_panel pane = new Daftarfakturpenjualan_inner_panel();
                Staticvar.pp = pp;
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

    private void piutangviewview(Penjualan_panel pp) {
        pp.bpiutang.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.gc();
                Daftarpiutang_inner_panel pane = new Daftarpiutang_inner_panel();
                Staticvar.pp = pp;
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

    private void posview(Penjualan_panel pp) {
        pp.bpos.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.gc();
                POS_panel pane = new POS_panel();
                JFrame jf = new JFrame();
                jf.add(pane);
                jf.setAlwaysOnTop(true);
                jf.setLocationRelativeTo(null);
                jf.setExtendedState(Frame.MAXIMIZED_BOTH);
                jf.setUndecorated(true);
                jf.setVisible(true);
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

    private void karyawanview(Penjualan_panel pp) {
        pp.ldaftarkaryawan.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.gc();
                Daftardatakaryawan_inner_panel pane = new Daftardatakaryawan_inner_panel();
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

    private void settingharidiskonview(Penjualan_panel pp) {
        pp.lsettingharidiskon.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                /*Mainmenu mm = (Mainmenu) pp.getRootPane().getParent();
                Settingdiskonharian_inner_panel pane = new Settingdiskonharian_inner_panel();
                mm.panel_tengah.removeAll();
                mm.panel_tengah.setLayout(new BorderLayout());
                mm.panel_tengah.add(pane, BorderLayout.CENTER);
                mm.panel_tengah.revalidate();
                mm.panel_tengah.repaint();*/
                System.gc();
                Settingdiskonharian_inner_panel pane = new Settingdiskonharian_inner_panel();
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

    private void settingbonuspenjualanview(Penjualan_panel pp) {
        pp.lsettingbonuspenjualan.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                /*Mainmenu mm = (Mainmenu) pp.getRootPane().getParent();
                Settingbonuspenjualan_inner_panel pane = new Settingbonuspenjualan_inner_panel();
                mm.panel_tengah.removeAll();
                mm.panel_tengah.setLayout(new BorderLayout());
                mm.panel_tengah.add(pane, BorderLayout.CENTER);
                mm.panel_tengah.revalidate();
                mm.panel_tengah.repaint();*/
                System.gc();
                Settingbonuspenjualan_inner_panel pane = new Settingbonuspenjualan_inner_panel();
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

    private void settingdiskonperkelompokview(Penjualan_panel pp) {
        pp.lsettingdiskonkelompok.addMouseListener(new MouseListener() {
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
                Settingdiskonkelompok_inner_panel pane = new Settingdiskonkelompok_inner_panel();
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
