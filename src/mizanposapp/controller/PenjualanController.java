/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller;

import java.awt.BorderLayout;
import java.awt.Frame;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import mizanposapp.helper.Globalsession;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.Penjualan_panel;
import mizanposapp.view.innerpanel.penjualan.Daftarfakturpenjualan_inner_panel;
import mizanposapp.view.innerpanel.penjualan.Daftarorderpenjualan_inner_panel;
import mizanposapp.view.innerpanel.penjualan.Daftarpiutang_inner_panel;
import mizanposapp.view.innerpanel.penjualan.Daftarreturpenjualan_inner_panel;
import mizanposapp.view.innerpanel.penjualan.POS_panel;
import mizanposapp.view.innerpanel.penjualan.Posframe;
import mizanposapp.view.innerpanel.penjualan.Settingbonuspenjualan_inner_panel;
import mizanposapp.view.innerpanel.penjualan.Settingdiskonharian_inner_panel;
import mizanposapp.view.innerpanel.penjualan.Settingdiskonkelompok_inner_panel;

/**
 *
 * @author Minami
 */
public class PenjualanController {

    Penjualan_panel pp;

    public PenjualanController() {
    }

    public PenjualanController(Mainmenu mm) {

    }

    public PenjualanController(Penjualan_panel pp) {
        this.pp = pp;
        orderpenjualanview();
        returview();
        fakturpenjualanview();
        piutangviewview();
        posview();
        karyawanview();
        settingharidiskonview();
        settingbonuspenjualanview();
        settingdiskonperkelompokview();
    }

    private void orderpenjualanview() {
        MouseAdapter amadap = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (Staticvar.inputmode == true) {
                    JOptionPane.showMessageDialog(null, "Anda Dalam Mode Input, Selesaikan Transaksi Untuk Berpindah Menu", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    if (Globalsession.penjualan_order.equals("1")) {
                        System.gc();
                        Staticvar.pp = pp;
                        Daftarorderpenjualan_inner_panel pane = new Daftarorderpenjualan_inner_panel();
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

        };
        pp.borderpenjualan.addMouseListener(amadap);
    }

    private void returview() {
        MouseAdapter amadap = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (Staticvar.inputmode == true) {
                    JOptionPane.showMessageDialog(null, "Anda Dalam Mode Input, Selesaikan Transaksi Untuk Berpindah Menu", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else {

                    if (Globalsession.penjualan_retur.equals("1")) {
                        System.gc();
                        Staticvar.pp = pp;
                        Daftarreturpenjualan_inner_panel pane = new Daftarreturpenjualan_inner_panel();
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

        };
        pp.breturpenjualan.addMouseListener(amadap);
    }

    private void fakturpenjualanview() {
        MouseAdapter amadap = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (Staticvar.inputmode == true) {
                    JOptionPane.showMessageDialog(null, "Anda Dalam Mode Input, Selesaikan Transaksi Untuk Berpindah Menu", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else {

                    if (Globalsession.penjualan_faktur.equals("1")) {
                        System.gc();
                        Staticvar.pp = pp;
                        Daftarfakturpenjualan_inner_panel pane = new Daftarfakturpenjualan_inner_panel();
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

        };
        pp.bfakturpenjualan.addMouseListener(amadap);
    }

    private void piutangviewview() {
        MouseAdapter amadap = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (Staticvar.inputmode == true) {
                    JOptionPane.showMessageDialog(null, "Anda Dalam Mode Input, Selesaikan Transaksi Untuk Berpindah Menu", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else {

                    if (Globalsession.penjualan_piutang.equals("1")) {
                        System.gc();
                        Staticvar.pp = pp;
                        Daftarpiutang_inner_panel pane = new Daftarpiutang_inner_panel();
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

        };
        pp.bpiutang.addMouseListener(amadap);
    }

    private void posview() {
        MouseAdapter amadap = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (Staticvar.inputmode == true) {
                    JOptionPane.showMessageDialog(null, "Anda Dalam Mode Input, Selesaikan Transaksi Untuk Berpindah Menu", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    Staticvar.inputmode = false;
                    System.gc();
                    Posframe pf = new Posframe();
                    pf.setExtendedState(MAXIMIZED_BOTH);
                    pf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    pf.setVisible(true);
                }
            }

        };
        pp.bpos.addMouseListener(amadap);
    }

    private void karyawanview() {
        MouseAdapter amadap = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (Staticvar.inputmode == true) {
                    JOptionPane.showMessageDialog(null, "Anda Dalam Mode Input, Selesaikan Transaksi Untuk Berpindah Menu", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    System.gc();
                    Staticvar.pp = pp;
                    Daftarorderpenjualan_inner_panel pane = new Daftarorderpenjualan_inner_panel();
                    pp.container.removeAll();
                    pp.container.setLayout(new BorderLayout());
                    pp.container.add(pane, BorderLayout.CENTER);
                    pp.container.revalidate();
                    pp.container.repaint();
                    Staticvar.inputmode = false;
                }
            }

        };
        //pp.breturpenjualan.addMouseListener(amadap);
    }

    private void settingharidiskonview() {
        MouseAdapter amadap = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (Staticvar.inputmode == true) {
                    JOptionPane.showMessageDialog(null, "Anda Dalam Mode Input, Selesaikan Transaksi Untuk Berpindah Menu", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    System.gc();
                    Staticvar.pp = pp;
                    Daftarorderpenjualan_inner_panel pane = new Daftarorderpenjualan_inner_panel();
                    pp.container.removeAll();
                    pp.container.setLayout(new BorderLayout());
                    pp.container.add(pane, BorderLayout.CENTER);
                    pp.container.revalidate();
                    pp.container.repaint();
                    Staticvar.inputmode = false;
                }
            }

        };
        //pp.breturpenjualan.addMouseListener(amadap);
    }

    private void settingbonuspenjualanview() {
        MouseAdapter amadap = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (Staticvar.inputmode == true) {
                    JOptionPane.showMessageDialog(null, "Anda Dalam Mode Input, Selesaikan Transaksi Untuk Berpindah Menu", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    System.gc();
                    Staticvar.pp = pp;
                    Daftarorderpenjualan_inner_panel pane = new Daftarorderpenjualan_inner_panel();
                    pp.container.removeAll();
                    pp.container.setLayout(new BorderLayout());
                    pp.container.add(pane, BorderLayout.CENTER);
                    pp.container.revalidate();
                    pp.container.repaint();
                    Staticvar.inputmode = false;
                }
            }

        };
        //pp.breturpenjualan.addMouseListener(amadap);
    }

    private void settingdiskonperkelompokview() {
        MouseAdapter amadap = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (Staticvar.inputmode == true) {
                    JOptionPane.showMessageDialog(null, "Anda Dalam Mode Input, Selesaikan Transaksi Untuk Berpindah Menu", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    System.gc();
                    Staticvar.pp = pp;
                    Daftarorderpenjualan_inner_panel pane = new Daftarorderpenjualan_inner_panel();
                    pp.container.removeAll();
                    pp.container.setLayout(new BorderLayout());
                    pp.container.add(pane, BorderLayout.CENTER);
                    pp.container.revalidate();
                    pp.container.repaint();
                    Staticvar.inputmode = false;
                }
            }

        };
        //pp.breturpenjualan.addMouseListener(amadap);
    }

}
