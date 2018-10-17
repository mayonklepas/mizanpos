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
import mizanposapp.view.Akunting_panel;
import mizanposapp.view.innerpanel.akuntansi.Daftarakun_inner_panel;
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

    /*
    private void bukubesarview(Akunting_panel pp) {
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

    private void daftarakunpentingview(Akunting_panel pp) {
        pp.lgiromasuk.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.gc();
                Daftargiromasuk_inner_panel pane = new Daftargiromasuk_inner_panel();
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

    private void rubahkodeakunview(Akunting_panel pp) {
        pp.lgirokeluar.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.gc();
                Daftargirokeluar_inner_panel pane = new Daftargirokeluar_inner_panel();
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
     */
}
