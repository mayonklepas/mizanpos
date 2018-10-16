/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import mizanposapp.view.Akunting_panel;
import mizanposapp.view.Beranda_panel;
import mizanposapp.view.Keuangan_panel;
import mizanposapp.view.Laporan_panel;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.Pembelian_panel;
import mizanposapp.view.Penjualan_panel;
import mizanposapp.view.Persedian_panel;
import mizanposapp.view.frameform.Bantuan;
import mizanposapp.view.innerpanel.penjualan.Daftardatapelanggan_inner_panel;
import mizanposapp.view.innerpanel.persediaan.Daftarpersediaan_inner_panel;

/**
 *
 * @author Minami
 */
public class MainmenuController {

    Mainmenu mm = new Mainmenu();
    boolean isclick = false;

    public MainmenuController() {
        mm.setExtendedState(MAXIMIZED_BOTH);
        mm.setLocationRelativeTo(null);
        mm.setVisible(true);
        panel1mouseevent();
        panel2mouseevent();
        panel3mouseevent();
        panel12mouseevent();
        panel6mouseevent();
        panel7mouseevent();
        panel8mouseevent();
        panel9mouseevent();
        panel10mouseevent();
    }

    private void panel1mouseevent() {
        mm.jPanel1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isclick = true;
                mm.jPanel1.setBackground(new Color(3, 3, 3));

            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mm.jPanel1.setBackground(new Color(3, 3, 3));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mm.jPanel1.setBackground(new Color(41, 39, 40));
            }
        });
    }

    private void panel2mouseevent() {
        mm.jPanel2.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isclick = true;
                mm.jPanel9.setBackground(new Color(3, 3, 3));

            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mm.jPanel2.setBackground(new Color(3, 3, 3));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mm.jPanel2.setBackground(new Color(41, 39, 40));
            }
        });
    }

    private void panel3mouseevent() {
        mm.jPanel3.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isclick = true;
                mm.jPanel10.setBackground(new Color(3, 3, 3));

            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mm.jPanel3.setBackground(new Color(3, 3, 3));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mm.jPanel3.setBackground(new Color(41, 39, 40));
            }
        });
    }

    private void panel12mouseevent() {
        mm.jPanel12.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.gc();
                isclick = true;
                mm.jPanel6.setBackground(new Color(3, 3, 3));
                Keuangan_panel kp = new Keuangan_panel();
                mm.panel_tengah.removeAll();
                mm.panel_tengah.setLayout(new BorderLayout());
                mm.panel_tengah.add(kp, BorderLayout.CENTER);
                mm.panel_tengah.revalidate();
                mm.panel_tengah.repaint();

            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mm.jPanel12.setBackground(new Color(3, 3, 3));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mm.jPanel12.setBackground(new Color(41, 39, 40));
            }
        });
    }

    private void panel6mouseevent() {
        mm.jPanel6.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.gc();
                isclick = true;
                mm.jPanel6.setBackground(new Color(3, 3, 3));
                Beranda_panel bp = new Beranda_panel();
                mm.panel_tengah.removeAll();
                mm.panel_tengah.setLayout(new BorderLayout());
                mm.panel_tengah.add(bp, BorderLayout.CENTER);
                mm.panel_tengah.revalidate();
                mm.panel_tengah.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mm.jPanel6.setBackground(new Color(3, 3, 3));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mm.jPanel6.setBackground(new Color(41, 39, 40));
            }
        });
    }

    private void panel7mouseevent() {
        mm.jPanel7.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.gc();
                isclick = true;
                mm.jPanel7.setBackground(new Color(3, 3, 3));
                Persedian_panel pp = new Persedian_panel();
                mm.panel_tengah.removeAll();
                mm.panel_tengah.setLayout(new BorderLayout());
                mm.panel_tengah.add(pp, BorderLayout.CENTER);
                mm.panel_tengah.revalidate();
                mm.panel_tengah.repaint();

                Daftarpersediaan_inner_panel pin = new Daftarpersediaan_inner_panel();
                pp.container.removeAll();
                pp.container.setLayout(new BorderLayout());
                pp.container.add(pin, BorderLayout.CENTER);
                pp.container.revalidate();
                pp.container.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mm.jPanel7.setBackground(new Color(3, 3, 3));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mm.jPanel7.setBackground(new Color(41, 39, 40));
            }
        });
    }

    private void panel8mouseevent() {
        mm.jPanel8.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.gc();
                isclick = true;
                mm.jPanel8.setBackground(new Color(3, 3, 3));
                Penjualan_panel pp = new Penjualan_panel();
                mm.panel_tengah.removeAll();
                mm.panel_tengah.setLayout(new BorderLayout());
                mm.panel_tengah.add(pp, BorderLayout.CENTER);
                mm.panel_tengah.revalidate();
                mm.panel_tengah.repaint();

                Daftardatapelanggan_inner_panel pin = new Daftardatapelanggan_inner_panel();
                pp.container.removeAll();
                pp.container.setLayout(new BorderLayout());
                pp.container.add(pin, BorderLayout.CENTER);
                pp.container.revalidate();
                pp.container.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mm.jPanel8.setBackground(new Color(3, 3, 3));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mm.jPanel8.setBackground(new Color(41, 39, 40));
            }
        });
    }

    private void panel9mouseevent() {
        mm.jPanel9.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.gc();
                isclick = true;
                mm.jPanel9.setBackground(new Color(3, 3, 3));
                Pembelian_panel pp = new Pembelian_panel();
                mm.panel_tengah.removeAll();
                mm.panel_tengah.setLayout(new BorderLayout());
                mm.panel_tengah.add(pp, BorderLayout.CENTER);
                mm.panel_tengah.revalidate();
                mm.panel_tengah.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mm.jPanel9.setBackground(new Color(3, 3, 3));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mm.jPanel9.setBackground(new Color(41, 39, 40));
            }
        });
    }

    private void panel10mouseevent() {
        mm.jPanel10.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.gc();
                isclick = true;
                mm.jPanel10.setBackground(new Color(3, 3, 3));
                Akunting_panel lp = new Akunting_panel();
                mm.panel_tengah.removeAll();
                mm.panel_tengah.setLayout(new BorderLayout());
                mm.panel_tengah.add(lp, BorderLayout.CENTER);
                mm.panel_tengah.revalidate();
                mm.panel_tengah.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mm.jPanel10.setBackground(new Color(3, 3, 3));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mm.jPanel10.setBackground(new Color(41, 39, 40));
            }
        });
    }
}
