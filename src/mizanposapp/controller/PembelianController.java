/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import mizanposapp.helper.Globalsession;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.Pembelian_panel;
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
public class PembelianController {

    //Pembelian_panel pp;
    public PembelianController() {
    }

    public PembelianController(Mainmenu mm) {

    }

    public PembelianController(Pembelian_panel pp) {
        orderview(pp);
        returview(pp);
        fakturpembelianview(pp);
        hutangview(pp);
        cekhargabarangview(pp);
        supplierview(pp);
    }

    private void supplierview(Pembelian_panel pp) {
        if (Staticvar.inputmode == true) {
            JOptionPane.showMessageDialog(null, "Anda Dalam Mode Input, Selesaikan Transaksi Untuk Berpindah Menu", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.gc();
            Daftardatasupplier_inner_panel pane = new Daftardatasupplier_inner_panel();
            Staticvar.pmp = pp;
            pp.container.removeAll();
            pp.container.setLayout(new BorderLayout());
            pp.container.add(pane, BorderLayout.CENTER);
            pp.container.revalidate();
            pp.container.repaint();
            Staticvar.inputmode = false;
        }
    }

    private void orderview(Pembelian_panel pp) {
        pp.bpenerimaanbarang.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (Staticvar.inputmode == true) {
                    JOptionPane.showMessageDialog(null, "Anda Dalam Mode Input, Selesaikan Transaksi Untuk Berpindah Menu", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else {

                    if (Globalsession.pembelian_order.equals("1")) {
                        System.gc();
                        Daftarorderpembelian_inner_panel pane = new Daftarorderpembelian_inner_panel();
                        Staticvar.pmp = pp;
                        pp.container.removeAll();
                        pp.container.setLayout(new BorderLayout());
                        pp.container.add(pane, BorderLayout.CENTER);
                        pp.container.revalidate();
                        pp.container.repaint();
                        Staticvar.inputmode = false;
                    } else {
                        JOptionPane.showMessageDialog(null, "Akses Ditolak !!", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                    }

                }
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

    private void returview(Pembelian_panel pp) {
        pp.breturpembelian.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (Staticvar.inputmode == true) {
                    JOptionPane.showMessageDialog(null, "Anda Dalam Mode Input, Selesaikan Transaksi Untuk Berpindah Menu", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else {

                    if (Globalsession.pembelian_retur.equals("1")) {
                        System.gc();
                        Daftarreturpembelian_inner_panel pane = new Daftarreturpembelian_inner_panel();
                        Staticvar.pmp = pp;
                        pp.container.removeAll();
                        pp.container.setLayout(new BorderLayout());
                        pp.container.add(pane, BorderLayout.CENTER);
                        pp.container.revalidate();
                        pp.container.repaint();
                        Staticvar.inputmode = false;
                    } else {
                        JOptionPane.showMessageDialog(null, "Akses Ditolak !!", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                    }

                }
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
                if (Staticvar.inputmode == true) {
                    JOptionPane.showMessageDialog(null, "Anda Dalam Mode Input, Selesaikan Transaksi Untuk Berpindah Menu", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else {

                    if (Globalsession.pembelian_faktur.equals("1")) {
                        System.gc();
                        Daftarfakturpembelian_inner_panel pane = new Daftarfakturpembelian_inner_panel();
                        Staticvar.pmp = pp;
                        pp.container.removeAll();
                        pp.container.setLayout(new BorderLayout());
                        pp.container.add(pane, BorderLayout.CENTER);
                        pp.container.revalidate();
                        pp.container.repaint();
                        Staticvar.inputmode = false;
                    } else {
                        JOptionPane.showMessageDialog(null, "Akses Ditolak !!", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                    }

                }
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
                if (Staticvar.inputmode == true) {
                    JOptionPane.showMessageDialog(null, "Anda Dalam Mode Input, Selesaikan Transaksi Untuk Berpindah Menu", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else {

                    if (Globalsession.pembelian_hutang.equals("1")) {
                        System.gc();
                        Daftarhutang_inner_panel pane = new Daftarhutang_inner_panel();
                        Staticvar.pmp = pp;
                        pp.container.removeAll();
                        pp.container.setLayout(new BorderLayout());
                        pp.container.add(pane, BorderLayout.CENTER);
                        pp.container.revalidate();
                        pp.container.repaint();
                        Staticvar.inputmode = false;
                    } else {
                        JOptionPane.showMessageDialog(null, "Akses Ditolak !!", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                    }

                }
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
                if (Staticvar.inputmode == true) {
                    JOptionPane.showMessageDialog(null, "Anda Dalam Mode Input, Selesaikan Transaksi Untuk Berpindah Menu", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else {
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
                    Staticvar.inputmode = false;
                }
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
