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
import mizanposapp.view.Keuangan_panel;
import mizanposapp.view.innerpanel.keuangan.Daftargirokeluar_inner_panel;
import mizanposapp.view.innerpanel.keuangan.Daftargiromasuk_inner_panel;
import mizanposapp.view.innerpanel.keuangan.Daftarkas_inner_panel;
import mizanposapp.view.innerpanel.keuangan.Daftarkaskeluar_inner_panel;
import mizanposapp.view.innerpanel.keuangan.Daftarkasmasuk_inner_panel;
import mizanposapp.view.innerpanel.keuangan.Daftartransferkas_inner_panel;
import mizanposapp.view.innerpanel.pembelian.Cekhargabeli_inner_panel;
import mizanposapp.view.innerpanel.pembelian.Daftardatasupplier_inner_panel;
import mizanposapp.view.innerpanel.pembelian.Daftarfakturpembelian_inner_panel;
import mizanposapp.view.innerpanel.pembelian.Daftarhutang_inner_panel;
import mizanposapp.view.innerpanel.pembelian.Daftarorderpembelian_inner_panel;
import mizanposapp.view.innerpanel.pembelian.Daftarreturpembelian_inner_panel;
import mizanposapp.view.innerpanel.penjualan.Daftarorderpenjualan_inner_panel;

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
        kasdanbank(pp);
        kasmasukview(pp);
        kaskeluarview(pp);
        transferkasview(pp);
        giromasukview(pp);
        girokeluarview(pp);
        //girokeluarview(pp);
    }

    private void kasdanbank(Keuangan_panel pp) {
        if (Staticvar.inputmode == true) {
            JOptionPane.showMessageDialog(null, "Anda Dalam Mode Input, Selesaikan Transaksi Untuk Berpindah Menu", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.gc();
            Daftarkas_inner_panel pane = new Daftarkas_inner_panel();
            Staticvar.kp = pp;
            pp.container.removeAll();
            pp.container.setLayout(new BorderLayout());
            pp.container.add(pane, BorderLayout.CENTER);
            pp.container.revalidate();
            pp.container.repaint();
            Staticvar.inputmode = false;
        }
    }

    private void kasmasukview(Keuangan_panel pp) {
        pp.bkasmasuk.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (Staticvar.inputmode == true) {
                    JOptionPane.showMessageDialog(null, "Anda Dalam Mode Input, Selesaikan Transaksi Untuk Berpindah Menu", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else {

                    if (Globalsession.keuangan_kas_masuk.equals("1")) {
                        System.gc();
                        Daftarkasmasuk_inner_panel pane = new Daftarkasmasuk_inner_panel();
                        Staticvar.kp = pp;
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

    private void kaskeluarview(Keuangan_panel pp) {
        pp.bkaskeluar.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (Staticvar.inputmode == true) {
                    JOptionPane.showMessageDialog(null, "Anda Dalam Mode Input, Selesaikan Transaksi Untuk Berpindah Menu", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else {

                    if (Globalsession.keuangan_kas_keluar.equals("1")) {
                        System.gc();
                        Daftarkaskeluar_inner_panel pane = new Daftarkaskeluar_inner_panel();
                        Staticvar.kp = pp;
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

    private void transferkasview(Keuangan_panel pp) {
        pp.btransferkas.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (Staticvar.inputmode == true) {
                    JOptionPane.showMessageDialog(null, "Anda Dalam Mode Input, Selesaikan Transaksi Untuk Berpindah Menu", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else {

                    if (Globalsession.keuangan_transfer_kas.equals("1")) {
                        System.gc();
                        Daftartransferkas_inner_panel pane = new Daftartransferkas_inner_panel();
                        Staticvar.kp = pp;
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

    private void giromasukview(Keuangan_panel pp) {
        pp.lgiromasuk.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (Staticvar.inputmode == true) {
                    JOptionPane.showMessageDialog(null, "Anda Dalam Mode Input, Selesaikan Transaksi Untuk Berpindah Menu", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    System.gc();
                    Daftargiromasuk_inner_panel pane = new Daftargiromasuk_inner_panel();
                    Staticvar.kp = pp;
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

    private void girokeluarview(Keuangan_panel pp) {
        pp.lgirokeluar.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (Staticvar.inputmode == true) {
                    JOptionPane.showMessageDialog(null, "Anda Dalam Mode Input, Selesaikan Transaksi Untuk Berpindah Menu", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    System.gc();
                    Daftargirokeluar_inner_panel pane = new Daftargirokeluar_inner_panel();
                    Staticvar.kp = pp;
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
