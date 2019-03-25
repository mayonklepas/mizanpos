/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller;

import com.sun.xml.internal.ws.api.message.saaj.SAAJFactory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import mizanposapp.controller.innerpanel.pengaturan.LoginController;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.FuncHelper;
import mizanposapp.helper.Globalsession;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.Akunting_panel;
import mizanposapp.view.Beranda_panel;
import mizanposapp.view.Keuangan_panel;
import mizanposapp.view.Laporan_panel;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.Pembelian_panel;
import mizanposapp.view.Penjualan_panel;
import mizanposapp.view.Persedian_panel;
import mizanposapp.view.frameform.Bantuan;
import mizanposapp.view.innerpanel.pengaturan.Data_pengguna_inner_panel;
import mizanposapp.view.innerpanel.pengaturan.Gantipassword_panel;
import mizanposapp.view.innerpanel.pengaturan.Login_panel;
import mizanposapp.view.innerpanel.pengaturan.Pengaturan_inner_panel;
import mizanposapp.view.innerpanel.penjualan.Daftardatapelanggan_inner_panel;
import mizanposapp.view.innerpanel.persediaan.Daftarpersediaan_inner_panel;
import org.panda_lang.pandomium.Pandomium;
import org.panda_lang.pandomium.settings.PandomiumSettings;
import org.panda_lang.pandomium.wrapper.PandomiumBrowser;
import org.panda_lang.pandomium.wrapper.PandomiumClient;

/**
 *
 * @author Minami
 */
public class MainmenuController {

    Mainmenu mm = new Mainmenu();
    boolean isclick = false;

    public MainmenuController() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                mm.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                mm.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        if (Staticvar.inputmode == true) {
                            FuncHelper.info("Anda Dalam Mode Input", " Selesaikan Transaksi Untuk Berpindah Menu");
                        } else {
                            FuncHelper.konfir("Yakin anda ingin keluar?", "Saat aplikasi ditutup maka komputer akan otomatis melakukan shutdown", "Ya");
                            if (Staticvar.isupdate == true) {
                                Staticvar.isupdate = false;
                                System.exit(0);
                            }
                        }

                    }

                });
            }
        });
        mm.setExtendedState(MAXIMIZED_BOTH);
        mm.setLocationRelativeTo(null);
        mm.setVisible(true);
        //loadbrowser();
        panelusermouseevent();
        panel1mouseevent();
        panel2mouseevent();
        panel12mouseevent();
        panel6mouseevent();
        panel7mouseevent();
        panel8mouseevent();
        panel9mouseevent();
        panel10mouseevent();
        LoginController.username = "";
        LoginController.jaga_pane = true;
        JDialog jd = new JDialog(new Mainmenu());
        jd.add(new Login_panel());
        jd.pack();
        jd.setLocationRelativeTo(null);
        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        jd.setTitle("Login");
        jd.setVisible(true);
        if (Staticvar.isupdate == true) {
            Staticvar.isupdate = false;
            LoginController.username = "";
            new CrudHelper();
            new Globalsession(Staticvar.id_user_aktif);

        }
    }

    private void loadbrowser() {
        PandomiumSettings panset = PandomiumSettings.getDefaultSettings();
        Pandomium pan = new Pandomium(panset);
        pan.initialize();
        PandomiumClient panclient = pan.createClient();
        PandomiumBrowser panbro = panclient.loadURL("https://google.com");
        mm.panel_tengah.removeAll();
        mm.panel_tengah.add(panbro.toAWTComponent(), BorderLayout.CENTER);
        mm.panel_tengah.revalidate();
        mm.panel_tengah.repaint();
    }

    private void panel1mouseevent() {
        mm.pclose.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isclick = true;
                mm.pclose.setBackground(new Color(3, 3, 3));

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (Staticvar.inputmode == true) {
                    FuncHelper.info("Anda Dalam Mode Input", " Selesaikan Transaksi Untuk Berpindah Menu");
                } else {
                    isclick = true;
                    mm.pberanda.setBackground(new Color(3, 3, 3));
                    Beranda_panel bp = new Beranda_panel();
                    mm.panel_tengah.removeAll();
                    mm.panel_tengah.setLayout(new BorderLayout());
                    mm.panel_tengah.add(bp, BorderLayout.CENTER);
                    mm.panel_tengah.revalidate();
                    mm.panel_tengah.repaint();
                    Staticvar.inputmode = false;
                    FuncHelper.konfir("Yakin anda ingin keluar?", "Saat aplikasi ditutup maka komputer akan otomatis melakukan shutdown", "Shutdown");
                    if (Staticvar.isupdate == true) {
                        Staticvar.isupdate = false;
                        System.exit(0);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mm.pclose.setBackground(new Color(3, 3, 3));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mm.pclose.setBackground(new Color(41, 39, 40));
            }
        });
    }

    private void panel2mouseevent() {
        mm.psetting.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (Staticvar.inputmode == true) {
                    JOptionPane.showMessageDialog(mm, "", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                    FuncHelper.info("Anda Dalam Mode Input", " Selesaikan Transaksi Untuk Berpindah Menu");
                } else {
                    if (Globalsession.sistem_setup_program.equals("1")) {
                        System.gc();
                        isclick = true;
                        mm.ppembelian.setBackground(new Color(3, 3, 3));
                        isclick = true;
                        Pengaturan_inner_panel pp = new Pengaturan_inner_panel();
                        mm.panel_tengah.removeAll();
                        mm.panel_tengah.setLayout(new BorderLayout());
                        mm.panel_tengah.add(pp, BorderLayout.CENTER);
                        mm.panel_tengah.revalidate();
                        mm.panel_tengah.repaint();
                        Staticvar.inputmode = false;
                    } else {
                        FuncHelper.info("Akses Ditolak", "Maaf akun anda tidak diizinkan mengakses menu Pengaturan Aplikasi");
                    }

                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mm.psetting.setBackground(new Color(3, 3, 3));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mm.psetting.setBackground(new Color(41, 39, 40));
            }
        });
    }

    private void panelusermouseevent() {
        mm.pakun.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                System.gc();
                isclick = true;
                mm.ppembelian.setBackground(new Color(3, 3, 3));
                isclick = true;
                if (Globalsession.sistem_setting_pengguna.equals("1")) {
                    JDialog jd = new JDialog(new Mainmenu());
                    jd.add(new Data_pengguna_inner_panel());
                    jd.pack();
                    jd.setLocationRelativeTo(null);
                    jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                    jd.setTitle("Daftar Data Pengguna");
                    jd.setVisible(true);
                } else {
                    FuncHelper.konfir("Akses Ditolak", "Maaf akun anda tidak diizinkan mengakses menu Pengaturan Akun,"
                         + " tapi anda masih bisa mengganti password", "Ganti Password");
                    if (Staticvar.isupdate == true) {
                        Staticvar.isupdate = false;
                        LoginController.username = Globalsession.nama_user;
                        JDialog jd = new JDialog(new Mainmenu());
                        jd.add(new Login_panel());
                        jd.pack();
                        jd.setLocationRelativeTo(null);
                        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        jd.setTitle("Login");
                        jd.setVisible(true);
                        if (Staticvar.isupdate == true) {
                            Staticvar.isupdate = false;
                            JDialog jdin = new JDialog(new Mainmenu());
                            jdin.add(new Gantipassword_panel());
                            jdin.pack();
                            jdin.setLocationRelativeTo(null);
                            jdin.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                            jdin.setTitle("Ganti Password");
                            jdin.setVisible(true);
                            if (Staticvar.isupdate == true) {
                                LoginController.username = "";
                                Staticvar.isupdate = false;
                                FuncHelper.info("Proses Berhasil", "Password anda sudah berhasil diubah, anda bisa melakukan login menggunakan pasword baru anda");
                            }
                        }

                    }
                }

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mm.pakun.setBackground(new Color(3, 3, 3));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mm.pakun.setBackground(new Color(41, 39, 40));
            }
        });
    }

    private void panel12mouseevent() {
        mm.pkeuangan.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (Staticvar.inputmode == true) {
                    FuncHelper.info("Anda Dalam Mode Input", " Selesaikan Transaksi Untuk Berpindah Menu");
                } else {
                    System.gc();
                    isclick = true;
                    mm.pberanda.setBackground(new Color(3, 3, 3));
                    Keuangan_panel kp = new Keuangan_panel();
                    mm.panel_tengah.removeAll();
                    mm.panel_tengah.setLayout(new BorderLayout());
                    mm.panel_tengah.add(kp, BorderLayout.CENTER);
                    mm.panel_tengah.revalidate();
                    mm.panel_tengah.repaint();
                    Staticvar.inputmode = false;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mm.pkeuangan.setBackground(new Color(3, 3, 3));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mm.pkeuangan.setBackground(new Color(41, 39, 40));
            }
        });
    }

    private void panel6mouseevent() {
        mm.pberanda.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (Staticvar.inputmode == true) {
                    FuncHelper.info("Anda Dalam Mode Input", " Selesaikan Transaksi Untuk Berpindah Menu");
                } else {
                    System.gc();
                    isclick = true;
                    mm.pberanda.setBackground(new Color(3, 3, 3));
                    Beranda_panel bp = new Beranda_panel();
                    mm.panel_tengah.removeAll();
                    mm.panel_tengah.setLayout(new BorderLayout());
                    mm.panel_tengah.add(bp, BorderLayout.CENTER);
                    mm.panel_tengah.revalidate();
                    mm.panel_tengah.repaint();
                    Staticvar.inputmode = false;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mm.pberanda.setBackground(new Color(3, 3, 3));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mm.pberanda.setBackground(new Color(41, 39, 40));
            }
        });
    }

    private void panel7mouseevent() {
        mm.ppersediaan.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (Staticvar.inputmode == true) {
                    FuncHelper.info("Anda Dalam Mode Input", " Selesaikan Transaksi Untuk Berpindah Menu");
                } else {
                    System.gc();
                    isclick = true;
                    mm.ppersediaan.setBackground(new Color(3, 3, 3));
                    Persedian_panel pp = new Persedian_panel();
                    mm.panel_tengah.removeAll();
                    mm.panel_tengah.setLayout(new BorderLayout());
                    mm.panel_tengah.add(pp, BorderLayout.CENTER);
                    mm.panel_tengah.revalidate();
                    mm.panel_tengah.repaint();
                    Staticvar.inputmode = false;
                    Daftarpersediaan_inner_panel pin = new Daftarpersediaan_inner_panel();
                    pp.container.removeAll();
                    pp.container.setLayout(new BorderLayout());
                    pp.container.add(pin, BorderLayout.CENTER);
                    pp.container.revalidate();
                    pp.container.repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mm.ppersediaan.setBackground(new Color(3, 3, 3));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mm.ppersediaan.setBackground(new Color(41, 39, 40));
            }
        });
    }

    private void panel8mouseevent() {
        mm.ppenjualan.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (Staticvar.inputmode == true) {
                    FuncHelper.info("Anda Dalam Mode Input", " Selesaikan Transaksi Untuk Berpindah Menu");
                } else {
                    System.gc();
                    isclick = true;
                    mm.ppenjualan.setBackground(new Color(3, 3, 3));
                    Penjualan_panel pp = new Penjualan_panel();
                    mm.panel_tengah.removeAll();
                    mm.panel_tengah.setLayout(new BorderLayout());
                    mm.panel_tengah.add(pp, BorderLayout.CENTER);
                    mm.panel_tengah.revalidate();
                    mm.panel_tengah.repaint();
                    Staticvar.inputmode = false;
                    Daftardatapelanggan_inner_panel pin = new Daftardatapelanggan_inner_panel();
                    pp.container.removeAll();
                    pp.container.setLayout(new BorderLayout());
                    pp.container.add(pin, BorderLayout.CENTER);
                    pp.container.revalidate();
                    pp.container.repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mm.ppenjualan.setBackground(new Color(3, 3, 3));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mm.ppenjualan.setBackground(new Color(41, 39, 40));
            }
        });
    }

    private void panel9mouseevent() {
        mm.ppembelian.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (Staticvar.inputmode == true) {
                    FuncHelper.info("Anda Dalam Mode Input", " Selesaikan Transaksi Untuk Berpindah Menu");
                } else {
                    System.gc();
                    isclick = true;
                    mm.ppembelian.setBackground(new Color(3, 3, 3));
                    Pembelian_panel pp = new Pembelian_panel();
                    mm.panel_tengah.removeAll();
                    mm.panel_tengah.setLayout(new BorderLayout());
                    mm.panel_tengah.add(pp, BorderLayout.CENTER);
                    mm.panel_tengah.revalidate();
                    mm.panel_tengah.repaint();
                    Staticvar.inputmode = false;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mm.ppembelian.setBackground(new Color(3, 3, 3));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mm.ppembelian.setBackground(new Color(41, 39, 40));
            }
        });
    }

    private void panel10mouseevent() {
        mm.pakuntansi.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (Staticvar.inputmode == true) {
                    FuncHelper.info("Anda Dalam Mode Input", " Selesaikan Transaksi Untuk Berpindah Menu");
                } else {
                    System.gc();
                    isclick = true;
                    mm.pakuntansi.setBackground(new Color(3, 3, 3));
                    Akunting_panel lp = new Akunting_panel();
                    mm.panel_tengah.removeAll();
                    mm.panel_tengah.setLayout(new BorderLayout());
                    mm.panel_tengah.add(lp, BorderLayout.CENTER);
                    mm.panel_tengah.revalidate();
                    mm.panel_tengah.repaint();
                    Staticvar.inputmode = false;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mm.pakuntansi.setBackground(new Color(3, 3, 3));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mm.pakuntansi.setBackground(new Color(41, 39, 40));
            }
        });
    }
}
