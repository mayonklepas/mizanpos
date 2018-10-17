/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.Akunting_panel;
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.akuntansi.Daftarakun_inner_panel;
import mizanposapp.view.innerpanel.akuntansi.Daftarbukubesar_inner_panel;
import mizanposapp.view.innerpanel.akuntansi.Daftarjurnalumum_inner_panel;

/**
 *
 * @author Minami
 */
public class AkuntansiController {

    Akunting_panel pp;

    public AkuntansiController() {
    }

    public AkuntansiController(Mainmenu mm) {

    }

    public AkuntansiController(Akunting_panel pp) {
        akunview(pp);
        jurnalumumview(pp);
        bukubesarview(pp);
    }

    private void akunview(Akunting_panel pp) {
        System.gc();
        Daftarakun_inner_panel pane = new Daftarakun_inner_panel();
        Staticvar.ap = pp;
        pp.container.removeAll();
        pp.container.setLayout(new BorderLayout());
        pp.container.add(pane, BorderLayout.CENTER);
        pp.container.revalidate();
        pp.container.repaint();
    }

    private void jurnalumumview(Akunting_panel pp) {
        pp.bjurnalumum.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.gc();
                Daftarjurnalumum_inner_panel pane = new Daftarjurnalumum_inner_panel();
                Staticvar.ap = pp;
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

    private void bukubesarview(Akunting_panel pp) {
        pp.bbukubesar.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.gc();
                JDialog jd = new JDialog(new Mainmenu());
                jd.add(new Popupcari("akun", "popupdaftarakun", "Daftar Akun"));
                jd.pack();
                jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                jd.setLocationRelativeTo(null);
                jd.setVisible(true);
                jd.toFront();
                if (Staticvar.resid.equals("")) {
                    JOptionPane.showMessageDialog(null, "Anda Belum Memilih Akun", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    Staticvar.ids = Staticvar.resid;
                    Staticvar.resid = "";
                    Daftarbukubesar_inner_panel pane = new Daftarbukubesar_inner_panel();
                    Staticvar.ap = pp;
                    pp.container.removeAll();
                    pp.container.setLayout(new BorderLayout());
                    pp.container.add(pane, BorderLayout.CENTER);
                    pp.container.revalidate();
                    pp.container.repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e
            ) {
                //pp.bpenyesuaian.setBackground(new Color(3, 3, 3));
            }

            @Override
            public void mouseEntered(MouseEvent e
            ) {
            }

            @Override
            public void mouseExited(MouseEvent e
            ) {
            }
        });
    }

    private void daftarakunpentingview(Akunting_panel pp) {
        pp.ldaftarakunpenting.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {

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
