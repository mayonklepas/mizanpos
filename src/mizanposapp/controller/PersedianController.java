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
import mizanposapp.view.Persedian_panel;
import mizanposapp.view.innerpanel.persediaan.Daftarkelompokbarang_inner_panel;
import mizanposapp.view.innerpanel.persediaan.Daftarlokasibarang_inner_panel;
import mizanposapp.view.innerpanel.persediaan.Daftarsatuanbarang_inner_panel;
import mizanposapp.view.innerpanel.persediaan.Daftardatadept_inner_panel;
import mizanposapp.view.innerpanel.pembelian.Daftardatapajak_inner_panel;
import mizanposapp.view.innerpanel.persediaan.Daftardatasupplier_inner_panel;
import mizanposapp.view.innerpanel.persediaan.Daftardatasupplierklasifikasi_inner_panel;
import mizanposapp.view.innerpanel.persediaan.Daftargudang_inner_panel;
import mizanposapp.view.innerpanel.persediaan.Daftarmerekbarang_inner_panel;
import mizanposapp.view.innerpanel.persediaan.Daftarservice_inner_panel;
import mizanposapp.view.innerpanel.persediaan.Daftarstokminimum_inner_panel;
import mizanposapp.view.innerpanel.persediaan.Daftarkoreksistok_inner_panel;
import mizanposapp.view.innerpanel.persediaan.Daftarpenyesuaian_inner_panel;

/**
 *
 * @author Minami
 */
public class PersedianController {

    //Persedian_panel pp;
    public PersedianController() {
    }

    public PersedianController(Mainmenu mm) {

    }

    public PersedianController(Persedian_panel pp) {
        penyesuaianview(pp);
        koreksistokview(pp);
        daftarkelompokbarangview(pp);
        daftarsatuanbarangview(pp);
        daftarlokasibarangview(pp);
        daftarmerekbarangview(pp);
        daftarserviceview(pp);
        daftardatapajakview(pp);
        daftargudangview(pp);
        daftardatadeptview(pp);
        daftardatalokasiview(pp);
        daftardatasupplierview(pp);
        daftardatastockminimumview(pp);
        daftardatasupplierklasifikasiview(pp);
    }

    private void penyesuaianview(Persedian_panel pp) {
        pp.bpenyesuaian.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.gc();
                Staticvar.psp = pp;
                Daftarpenyesuaian_inner_panel pane = new Daftarpenyesuaian_inner_panel();
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

    private void koreksistokview(Persedian_panel pp) {
        pp.pkoreksistok.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.gc();
                Staticvar.psp = pp;
                Daftarkoreksistok_inner_panel pane = new Daftarkoreksistok_inner_panel();
                pp.container.removeAll();
                pp.container.setLayout(new BorderLayout());
                pp.container.add(pane, BorderLayout.CENTER);
                pp.container.revalidate();
                pp.container.repaint();
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

    private void daftarkelompokbarangview(Persedian_panel pp) {
        pp.ldaftar_kelompok_barang.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.gc();
                Daftarkelompokbarang_inner_panel pane = new Daftarkelompokbarang_inner_panel();
                pp.container.removeAll();
                pp.container.setLayout(new BorderLayout());
                pp.container.add(pane, BorderLayout.CENTER);
                pp.container.revalidate();
                pp.container.repaint();
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

    private void daftarsatuanbarangview(Persedian_panel pp) {
        pp.ldaftar_satuan_barang.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.gc();
                Daftarsatuanbarang_inner_panel pane = new Daftarsatuanbarang_inner_panel();
                pp.container.removeAll();
                pp.container.setLayout(new BorderLayout());
                pp.container.add(pane, BorderLayout.CENTER);
                pp.container.revalidate();
                pp.container.repaint();
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

    private void daftarlokasibarangview(Persedian_panel pp) {
        pp.ldaftar_lokasi_barang.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.gc();
                Daftarlokasibarang_inner_panel pane = new Daftarlokasibarang_inner_panel();
                pp.container.removeAll();
                pp.container.setLayout(new BorderLayout());
                pp.container.add(pane, BorderLayout.CENTER);
                pp.container.revalidate();
                pp.container.repaint();
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

    private void daftarmerekbarangview(Persedian_panel pp) {
        pp.ldaftar_merek.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.gc();
                Daftarmerekbarang_inner_panel pane = new Daftarmerekbarang_inner_panel();
                pp.container.removeAll();
                pp.container.setLayout(new BorderLayout());
                pp.container.add(pane, BorderLayout.CENTER);
                pp.container.revalidate();
                pp.container.repaint();
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

    private void daftarserviceview(Persedian_panel pp) {
        pp.ldaftar_service.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.gc();
                Daftarservice_inner_panel pane = new Daftarservice_inner_panel();
                pp.container.removeAll();
                pp.container.setLayout(new BorderLayout());
                pp.container.add(pane, BorderLayout.CENTER);
                pp.container.revalidate();
                pp.container.repaint();
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

    private void daftardatapajakview(Persedian_panel pp) {
        pp.ldaftar_data_pajak.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.gc();
                Daftardatapajak_inner_panel pane = new Daftardatapajak_inner_panel();
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

    private void daftargudangview(Persedian_panel pp) {
        pp.ldaftar_gudang.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.gc();
                Daftargudang_inner_panel pane = new Daftargudang_inner_panel();
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

    private void daftardatadeptview(Persedian_panel pp) {
        pp.ldaftar_data_dept.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.gc();
                Daftardatadept_inner_panel pane = new Daftardatadept_inner_panel();
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

    private void daftardatalokasiview(Persedian_panel pp) {
        pp.ldaftar_lokasi_barang.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.gc();
                Daftarlokasibarang_inner_panel pane = new Daftarlokasibarang_inner_panel();
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

    private void daftardatasupplierview(Persedian_panel pp) {
        pp.ldaftar_supplier.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.gc();
                Daftardatasupplier_inner_panel pane = new Daftardatasupplier_inner_panel();
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

    private void daftardatasupplierklasifikasiview(Persedian_panel pp) {
        pp.ldaftar_klasifikasi.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.gc();
                Daftardatasupplierklasifikasi_inner_panel pane = new Daftardatasupplierklasifikasi_inner_panel();
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

    private void daftardatastockminimumview(Persedian_panel pp) {
        pp.ldaftar_stock_minimum.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.gc();
                Daftarstokminimum_inner_panel pane = new Daftarstokminimum_inner_panel();
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
