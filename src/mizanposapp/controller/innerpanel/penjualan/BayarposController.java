/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.penjualan;

import java.awt.Dialog;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import mizanposapp.helper.FuncHelper;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Globalsession;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Errorpanel;
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.penjualan.Bayarpos_pane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Minami
 */
public class BayarposController {

    NumberFormat nf = NumberFormat.getInstance();
    CrudHelper ch = new CrudHelper();
    Bayarpos_pane pane;
    public static double totalbayar = 0, total_pajak = 0, total_service = 0, sub_total = 0, charge_nominal = 0, pos_bayar_cash, jumlah_piutang;
    public static String valpelanggan = "", valgudang = "", valdept = "", valsalesman = "", valshipvia = "", valtop = "",
         valakun_penjualan = "", valakun_ongkir = "", valakun_diskon = "", valakun_uang_muka = "", valgolongan = "",
         no_transaksi, keterangan, kirimtextpenjualan = "";
    String val_tipe_bayar;
    public static Date tanggal;
    public static boolean ispersen = true;
    public static boolean istunai = true;
    String id_card = "", id_akun_charge;
    double charge = 0;
    int status_voucher = 0, status_card = 0;
    double totallama;
    public static ArrayList<PosframeController.Entitytabledata> tabeldatalist;

    ArrayList<Entitycombo> pembayaranlist = new ArrayList<>();

    KeyEventDispatcher keydis = new KeyEventDispatcher() {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                if (e.getKeyCode() == KeyEvent.VK_F5) {
                    pane.edbiaya_lain.requestFocus();
                    pane.edbiaya_lain.selectAll();
                } else if (e.getKeyCode() == KeyEvent.VK_F6) {
                    if (ispersen == true) {
                        pane.eddiskon_persen.requestFocus();
                        pane.eddiskon_persen.selectAll();
                    } else {
                        pane.eddiskon_nominal.requestFocus();
                        pane.eddiskon_nominal.selectAll();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_F7) {
                    pane.edbayar.requestFocus();
                    pane.edbayar.selectAll();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    pane.bbatal.doClick();
                } else if (e.getKeyCode() == KeyEvent.VK_F8) {
                    pane.bcari_tipe_bayar.doClick();
                }
            }
            return false;
        }
    };

    public BayarposController(Bayarpos_pane pane) {
        this.pane = pane;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JDialog jdin = (JDialog) pane.getRootPane().getParent();
                jdin.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {

                        pane.bbatal.doClick();

                    }

                });
            }
        });
        loadcontrol();
        ckpoint();
        loadtotalbayar();
        kalkulasi();
        onfocusbykey();
        caricard();
        //loadcomboakun();
        //combopembayarancontrol();
        simpan();
        tutup();
    }

    private void loadcontrol() {
        if (jumlah_piutang > 0) {
            pane.cktambahpiutang.setVisible(true);
            pane.ljumlahpiutang.setVisible(true);
            pane.ljumlahpiutang.setText(nf.format(jumlah_piutang));
        } else {
            pane.cktambahpiutang.setVisible(false);
            pane.ljumlahpiutang.setVisible(false);
        }
        pane.ckgunakan_poin.setSelected(false);
        pane.ljumlah_poin.setVisible(false);
        pane.lttk_jumlah_poin.setVisible(false);
        pane.edjumlah_poin.setVisible(false);
        pane.lnilai_poin.setVisible(false);
        pane.lttk_nilai_poin.setVisible(false);
        pane.ednilai_poin.setVisible(false);
        pane.lmax_poinl.setVisible(false);
        pane.lttk_max_poin.setVisible(false);
        pane.lmax_poin.setVisible(false);

        pane.lno_kartu.setVisible(false);
        pane.lttk_no_kartu.setVisible(false);
        pane.lnama_pemilik.setVisible(false);
        pane.lttk_nama_pemilik.setVisible(false);
        pane.edno_kartu.setVisible(false);
        pane.ednama_pemilik.setVisible(false);
        pane.lttk_tambah_cash.setVisible(false);
        pane.ltambah_cash.setVisible(false);
        pane.edtambah_cash.setVisible(false);
        pane.edtambah_cash.setText("0");

        pane.bcetak_lagi.setVisible(false);
        pane.lperingatan.setVisible(false);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                pane.edbayar.requestFocus();
            }
        });

        id_card = "1";
        pane.edtipe_bayar.setText("TUNAI");

    }

    private void onfocusbykey() {

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keydis);

        pane.edbiaya_lain.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (ispersen == true) {
                        pane.eddiskon_persen.requestFocus();
                        pane.eddiskon_persen.selectAll();
                    } else {
                        pane.eddiskon_nominal.requestFocus();
                        pane.eddiskon_nominal.selectAll();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_F6) {
                    if (ispersen == true) {
                        pane.eddiskon_persen.requestFocus();
                        pane.eddiskon_persen.selectAll();
                    } else {
                        pane.eddiskon_nominal.requestFocus();
                        pane.eddiskon_nominal.selectAll();
                    }
                }
            }

        });

        pane.eddiskon_persen.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    pane.edbayar.requestFocus();
                    pane.edbayar.selectAll();
                } else if (e.getKeyCode() == KeyEvent.VK_F7) {
                    pane.edbayar.requestFocus();
                    pane.edbayar.selectAll();
                }
            }

        });

        pane.eddiskon_nominal.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    pane.edbayar.requestFocus();
                    pane.edbayar.selectAll();
                } else if (e.getKeyCode() == KeyEvent.VK_F7) {
                    pane.edbayar.requestFocus();
                    pane.edbayar.selectAll();
                }
            }

        });

        pane.edbayar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    pane.bcetak_struk.doClick();
                }
            }

        });

        pane.edtambah_cash.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    pane.bcetak_struk.doClick();
                }
            }

        });

    }

    private void loadtotalbayar() {
        pane.ltotal.setText(nf.format(totalbayar));
        pane.ltotal_pajak.setText(nf.format(total_pajak));
        pane.ltotal_service.setText(nf.format(total_service));
        pane.lsubtotal.setText(nf.format(sub_total));

    }

    private void kalkulasi() {
        KeyAdapter keadbiaya = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                char cr = e.getKeyChar();
                if (!Character.isLetter(cr)) {
                    double subtotal = sub_total;
                    double biayalain = FuncHelper.ToDouble(pane.edbiaya_lain.getText());
                    double diskon = FuncHelper.ToDouble(pane.eddiskon_nominal.getText());
                    double pajak = total_pajak;
                    totallama = (subtotal + biayalain - diskon + pajak);
                    if (pane.cktambahpiutang.isSelected()) {
                        totalbayar = (subtotal + biayalain - diskon + pajak) + jumlah_piutang;
                        pane.ltotal.setText(nf.format(totalbayar));
                        pane.lkembali.setText(nf.format(totalbayar));
                    } else {
                        totalbayar = subtotal + biayalain - diskon + pajak;
                        pane.ltotal.setText(nf.format(totalbayar));
                        pane.lkembali.setText(nf.format(totalbayar));
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Hanya memperbolehkan angka");
                    pane.edbiaya_lain.setText("0");
                }
            }

        };

        KeyAdapter keaddiskonpersen = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                char cr = e.getKeyChar();
                if (!Character.isLetter(cr)) {
                    double subtotal = sub_total;
                    double biayalain = FuncHelper.ToDouble(pane.edbiaya_lain.getText());
                    double indiskon_persen = FuncHelper.ToDouble(pane.eddiskon_persen.getText());
                    double indiskon_nominal = (subtotal + biayalain) * (indiskon_persen / 100);
                    double pajak = FuncHelper.ToDouble(pane.ltotal_pajak.getText());
                    totallama = subtotal + biayalain - indiskon_nominal + pajak;
                    if (pane.cktambahpiutang.isSelected()) {
                        totalbayar = (subtotal + biayalain - indiskon_nominal + pajak) + jumlah_piutang;
                        pane.eddiskon_nominal.setText(nf.format(indiskon_nominal));
                        pane.ltotal.setText(nf.format(totalbayar));
                        pane.lkembali.setText(nf.format(totalbayar));
                    } else {
                        totalbayar = subtotal + biayalain - indiskon_nominal + pajak;
                        pane.eddiskon_nominal.setText(nf.format(indiskon_nominal));
                        pane.ltotal.setText(nf.format(totalbayar));
                        pane.lkembali.setText(nf.format(totalbayar));
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Hanya memperbolehkan angka");
                    pane.eddiskon_persen.setText("0");
                }
            }

        };

        KeyAdapter keaddiskonnominal = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                char cr = e.getKeyChar();
                if (!Character.isLetter(cr)) {
                    double subtotal = sub_total;
                    double biayalain = FuncHelper.ToDouble(pane.edbiaya_lain.getText());
                    double pajak = FuncHelper.ToDouble(pane.ltotal_pajak.getText());
                    double indiskon_nominal = FuncHelper.ToDouble(pane.eddiskon_nominal.getText());
                    double indiskon_persen = (indiskon_nominal / (subtotal + biayalain)) * 100;
                    totallama = (subtotal + biayalain - indiskon_nominal + pajak);
                    if (pane.cktambahpiutang.isSelected()) {
                        totalbayar = (subtotal + biayalain - indiskon_nominal + pajak) + jumlah_piutang;
                        pane.eddiskon_persen.setText(FuncHelper.rounding(indiskon_persen));
                        pane.ltotal.setText(nf.format(totalbayar));
                        pane.lkembali.setText(nf.format(totalbayar));
                    } else {
                        totalbayar = subtotal + biayalain - indiskon_nominal + pajak;
                        pane.eddiskon_persen.setText(FuncHelper.rounding(indiskon_persen));
                        pane.ltotal.setText(nf.format(totalbayar));
                        pane.lkembali.setText(nf.format(totalbayar));
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Hanya memperbolehkan angka");
                    pane.eddiskon_nominal.setText("0");
                }
            }

        };

        KeyAdapter kebayar = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (pane.cktambahpiutang.isSelected()) {
                    rawkalkulasi(jumlah_piutang);
                } else {
                    rawkalkulasi(0.0);
                }
            }

        };

        KeyAdapter kebayar2 = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (pane.cktambahpiutang.isSelected()) {
                    rawkalkulasi(jumlah_piutang);
                } else {
                    rawkalkulasi(0.0);
                }
            }

        };
        pane.eddiskon_persen.addKeyListener(keaddiskonpersen);
        pane.edbiaya_lain.addKeyListener(keadbiaya);
        pane.eddiskon_nominal.addKeyListener(keaddiskonnominal);
        pane.edbayar.addKeyListener(kebayar);
        pane.edtambah_cash.addKeyListener(kebayar2);

    }

    private void rawkalkulasi(double jumlah) {
        double jumlah_uang = FuncHelper.ToDouble(pane.edbayar.getText()) + FuncHelper.ToDouble(pane.edtambah_cash.getText());
        double kembalian = 0;
        double setkembalilabel = 0;
        try {
            kembalian = jumlah_uang - (totalbayar + jumlah);
            if (kembalian < 0) {
                if (istunai == true) {
                    pane.lkembalilabel.setText("KURANG");
                } else {
                    pane.lkembalilabel.setText("SISA");
                }
                setkembalilabel = kembalian * -1;
            } else {
                if (istunai == true) {
                    pane.lkembalilabel.setText("KEMBALI");
                    setkembalilabel = kembalian;
                } else {
                    pane.lkembalilabel.setText("SISA");
                    if (kembalian >= 0) {
                        JOptionPane.showMessageDialog(null, "Jumlah uang tidak boleh lebih besar dari total");
                        pane.edbayar.setText("");
                    }
                }

            }
        } catch (Exception es) {
            kembalian = 0;
            setkembalilabel = 0;
        }

        pane.lkembali.setText(nf.format(setkembalilabel));
        pane.ljumlah_bayar.setText(nf.format(jumlah_uang));
    }

    private void ckpoint() {
        pane.ckgunakan_poin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pane.ckgunakan_poin.isSelected()) {
                    pane.ljumlah_poin.setVisible(true);
                    pane.lttk_jumlah_poin.setVisible(true);
                    pane.edjumlah_poin.setVisible(true);
                    pane.lnilai_poin.setVisible(true);
                    pane.lttk_nilai_poin.setVisible(true);
                    pane.ednilai_poin.setVisible(true);
                    pane.lmax_poinl.setVisible(true);
                    pane.lttk_max_poin.setVisible(true);
                    pane.lmax_poin.setVisible(true);
                } else {
                    pane.ljumlah_poin.setVisible(false);
                    pane.lttk_jumlah_poin.setVisible(false);
                    pane.edjumlah_poin.setVisible(false);
                    pane.lnilai_poin.setVisible(false);
                    pane.lttk_nilai_poin.setVisible(false);
                    pane.ednilai_poin.setVisible(false);
                    pane.lmax_poinl.setVisible(false);
                    pane.lttk_max_poin.setVisible(false);
                    pane.lmax_poin.setVisible(false);
                }

            }
        });

        pane.cktambahpiutang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (status_card == 1) {
                    pane.lno_kartu.setVisible(true);
                    pane.lttk_no_kartu.setVisible(true);
                    pane.lnama_pemilik.setVisible(true);
                    pane.lttk_nama_pemilik.setVisible(true);
                    pane.edno_kartu.setVisible(true);
                    pane.ednama_pemilik.setVisible(true);
                    pane.edtambah_cash.setVisible(true);
                    pane.ltambah_cash.setVisible(true);
                    pane.lttk_tambah_cash.setVisible(true);
                    pane.edtambah_cash.setText("0");
                    pane.lno_kartu.setText("No. Kartu");
                    pane.lnama_pemilik.setText("Nama Pemilik");
                    charge_nominal = ((charge / 100) * totalbayar);
                    double total_bayar = charge_nominal + totalbayar;
                    //if()
                    if (istunai) {
                        if (pane.cktambahpiutang.isSelected()) {
                            pane.ltotal.setText(nf.format(total_bayar + jumlah_piutang));
                            pane.edbayar.setText(String.valueOf(total_bayar + jumlah_piutang));
                            pane.ljumlah_bayar.setText(nf.format(FuncHelper.ToDouble(pane.edbayar.getText())));
                            pane.lkembali.setText("0");
                            pane.lkembalilabel.setText("KEMBALI");
                        } else {
                            pane.ltotal.setText(nf.format(total_bayar));
                            pane.edbayar.setText(String.valueOf(total_bayar));
                            pane.ljumlah_bayar.setText(nf.format(FuncHelper.ToDouble(pane.edbayar.getText())));
                            pane.lkembali.setText("0");
                            pane.lkembalilabel.setText("KEMBALI");
                        }
                    } else {
                        if (pane.cktambahpiutang.isSelected()) {
                            pane.ltotal.setText(nf.format(total_bayar + jumlah_piutang));
                            pane.edbayar.setText(String.valueOf(0));
                            pane.ljumlah_bayar.setText(nf.format(FuncHelper.ToDouble(pane.edbayar.getText())));
                            pane.lkembalilabel.setText("SISA");
                            pane.lkembali.setText(nf.format(total_bayar + jumlah_piutang));
                            pane.edbayar.selectAll();
                        } else {
                            pane.ltotal.setText(nf.format(total_bayar));
                            pane.edbayar.setText(String.valueOf(0));
                            pane.ljumlah_bayar.setText(nf.format(FuncHelper.ToDouble(pane.edbayar.getText())));
                            pane.lkembalilabel.setText("SISA");
                            pane.lkembali.setText(nf.format(total_bayar));
                            pane.edbayar.selectAll();
                        }

                    }
                } else if (status_voucher == 1) {
                    pane.lno_kartu.setVisible(true);
                    pane.lttk_no_kartu.setVisible(true);
                    pane.lnama_pemilik.setVisible(true);
                    pane.lttk_nama_pemilik.setVisible(true);
                    pane.edno_kartu.setVisible(true);
                    pane.ednama_pemilik.setVisible(true);
                    pane.edtambah_cash.setVisible(true);
                    pane.ltambah_cash.setVisible(true);
                    pane.edtambah_cash.setText("0");
                    pane.lttk_tambah_cash.setVisible(true);
                    pane.lno_kartu.setText("No Voucher");
                    pane.lnama_pemilik.setText("Nama Voucher");
                    if (pane.cktambahpiutang.isSelected()) {
                        pane.ltotal.setText(nf.format(totalbayar + jumlah_piutang));
                        pane.edbayar.setText(nf.format(totalbayar + jumlah_piutang));
                        pane.ljumlah_bayar.setText(nf.format(FuncHelper.ToDouble(pane.edbayar.getText())));
                    } else {
                        pane.ltotal.setText(nf.format(totalbayar));
                        pane.edbayar.setText("0");
                        pane.ljumlah_bayar.setText(nf.format(FuncHelper.ToDouble(pane.edbayar.getText())));
                    }
                } else {
                    pane.lno_kartu.setVisible(false);
                    pane.lttk_no_kartu.setVisible(false);
                    pane.lnama_pemilik.setVisible(false);
                    pane.lttk_nama_pemilik.setVisible(false);
                    pane.edno_kartu.setVisible(false);
                    pane.ednama_pemilik.setVisible(false);
                    pane.edtambah_cash.setVisible(false);
                    pane.ltambah_cash.setVisible(false);
                    pane.edtambah_cash.setText("0");
                    pane.lttk_tambah_cash.setVisible(false);
                    if (pane.cktambahpiutang.isSelected()) {
                        pane.ltotal.setText(nf.format(totalbayar + jumlah_piutang));
                        pane.edbayar.setText("0");
                        pane.ljumlah_bayar.setText(nf.format(FuncHelper.ToDouble(pane.edbayar.getText())));
                        pane.lkembali.setText(nf.format(totalbayar + jumlah_piutang));
                    } else {
                        pane.ltotal.setText(nf.format(totalbayar));
                        pane.edbayar.setText("0");
                        pane.ljumlah_bayar.setText(nf.format(FuncHelper.ToDouble(pane.edbayar.getText())));
                        pane.lkembali.setText(nf.format(totalbayar));
                    }
                    if (istunai) {
                        pane.lkembalilabel.setText("KURANG");
                    } else {
                        pane.lkembalilabel.setText("SISA");
                    }

                    double subtotal = sub_total;
                    double biayalain = FuncHelper.ToDouble(pane.edbiaya_lain.getText());
                    double indiskon_persen = FuncHelper.ToDouble(pane.eddiskon_persen.getText());
                    double indiskon_nominal = (subtotal + biayalain) * (indiskon_persen / 100);
                    double pajak = FuncHelper.ToDouble(pane.ltotal_pajak.getText());
                    if (pane.cktambahpiutang.isSelected()) {
                        totalbayar = (subtotal + biayalain - indiskon_nominal + pajak) + jumlah_piutang;
                        pane.eddiskon_nominal.setText(nf.format(indiskon_nominal));
                        pane.ltotal.setText(nf.format(totalbayar));
                        pane.lkembali.setText(nf.format(totalbayar));
                    } else {
                        totalbayar = subtotal + biayalain - indiskon_nominal + pajak;
                        pane.eddiskon_nominal.setText(nf.format(indiskon_nominal));
                        pane.ltotal.setText(nf.format(totalbayar));
                        pane.lkembali.setText(nf.format(totalbayar));
                    }

                }

            }
        });
    }

    public void rawsimpan() {
        if (pane.lperingatan.isVisible()) {
            KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keydis);
            Staticvar.isupdate = true;
            JDialog jd = (JDialog) pane.getRootPane().getParent();
            jd.dispose();
        } else {
            String diskon_dalam = "0";
            String tipe_beli = "0";
            String uang_muka = "0";
            if (ispersen) {
                diskon_dalam = "0";
            } else {
                diskon_dalam = "1";
            }

            if (istunai) {
                tipe_beli = "0";
                uang_muka = "0";
            } else {
                tipe_beli = "1";
                uang_muka = pane.edbayar.getText();
            }

            if (!pane.ckgunakan_poin.isSelected()) {
                pane.ednilai_poin.setText("0");
            }

            String data = "genjur="
                 + "id_keltrans='2'::"
                 + "id_dept='" + valdept + "'::"
                 + "tanggal='" + new SimpleDateFormat("yyyy-MM-dd").format(tanggal) + "'::"
                 + "noref='" + FuncHelper.EncodeString(no_transaksi) + "'::"
                 + "keterangan='" + FuncHelper.EncodeString(keterangan) + "'"
                 + "&penjualan="
                 + "id_pelanggan='" + valpelanggan + "'::"
                 + "tipe_pembayaran='" + tipe_beli + "'::"
                 + "id_gudang='" + valgudang + "'::"
                 + "total_penjualan='" + FuncHelper.ToDouble(pane.ltotal.getText()) + "'::"
                 + "total_biaya='" + FuncHelper.ToDouble(pane.edbiaya_lain.getText()) + "'::"
                 + "diskon_persen='" + FuncHelper.ToDouble(pane.eddiskon_persen.getText()) + "'::"
                 + "diskon_nominal='" + FuncHelper.ToDouble(pane.eddiskon_nominal.getText()) + "'::"
                 + "total_uang_muka='" + FuncHelper.ToDouble(uang_muka) + "'::"
                 + "total_pajak='" + total_pajak + "'::"
                 + "id_currency='" + Globalsession.DEFAULT_CURRENCY_ID + "'::"
                 + "nilai_kurs='1'::"
                 + "akun_penjualan='" + valakun_penjualan + "'::"
                 + "akun_biaya='" + valakun_ongkir + "'::"
                 + "akun_diskon='" + valakun_diskon + "'::"
                 + "akun_uang_muka='" + valakun_uang_muka + "'::"
                 + "diskon_dalam='" + diskon_dalam + "'::"
                 + "tanggal_pengantaran='" + new SimpleDateFormat("yyyy-MM-dd").format(tanggal) + "'::"
                 + "id_pengantaran='" + valshipvia + "'::"
                 + "id_bagian_penjualan='" + valsalesman + "'::"
                 + "id_termofpayment='" + valtop + "'::"
                 + "tipe_penjualan='" + "0" + "'::"
                 + "pos_bayar_dengan='" + id_card + "'::"
                 + "pos_jumlah_bayar='" + FuncHelper.ToDouble(pane.edbayar.getText()) + "'::"
                 + "pos_nomor_kartu='" + pane.edno_kartu.getText() + "'::"
                 + "pos_pemilik_kartu='" + pane.ednama_pemilik.getText() + "'::"
                 + "pos_nilai_poin='" + FuncHelper.ToDouble(pane.ednilai_poin.getText()) + "'::"
                 + "pos_charge_nominal='" + charge_nominal + "'::"
                 + "pos_akun_charge='" + id_akun_charge + "'::"
                 + "pos_bayar_cash='" + pane.edtambah_cash.getText() + "'::"
                 + "&" + kirimtextpenjualan;

            ch.insertdata("insertpenjualan", data);
            if (Staticvar.getresult.equals("berhasil")) {
                pane.bcetak_lagi.setVisible(true);
                pane.lperingatan.setVisible(true);
            } else {
                JDialog jd = new JDialog(new Mainmenu());
                Errorpanel ep = new Errorpanel();
                ep.ederror.setText(Staticvar.getresult);
                jd.add(ep);
                jd.pack();
                jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                jd.setLocationRelativeTo(null);
                jd.setVisible(true);
                jd.toFront();
            }
        }

    }

    private void simpan() {
        pane.bcetak_struk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double jumlah_uang = FuncHelper.ToDouble(pane.edbayar.getText());
                double tambah_cash = FuncHelper.ToDouble(pane.edtambah_cash.getText());
                if (istunai) {
                    if (status_card == 1) {
                        if ((jumlah_uang + tambah_cash) == totalbayar) {
                            rawsimpan();
                        } else {
                            JOptionPane.showMessageDialog(null, "Jumlah uang harus pas dengan jumlah bayar");
                        }
                    } else {
                        if (jumlah_uang < totalbayar) {
                            JOptionPane.showMessageDialog(null, "Jumlah uang tidak boleh kurang dari jumlah bayar");
                        } else {
                            rawsimpan();
                        }
                    }

                } else {
                    if (status_card == 1) {
                        if ((jumlah_uang + tambah_cash) > totalbayar) {
                            JOptionPane.showMessageDialog(null, "Jumlah uang tidak boleh lebh besar dari jumlah bayar");
                        } else {
                            rawsimpan();
                        }
                    } else {
                        if (jumlah_uang > totalbayar) {
                            JOptionPane.showMessageDialog(null, "Jumlah uang tidak boleh lebh besar dari jumlah bayar");
                        } else {
                            rawsimpan();
                        }
                    }

                }

            }
        });

        pane.btanpa_struk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rawsimpan();
            }
        });

    }

    private void tutup() {
        pane.bbatal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keydis);
                Staticvar.isupdate = false;
                JDialog jd = (JDialog) pane.getRootPane().getParent();
                jd.dispose();
            }
        });

    }

    private void caricard() {

        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keydis);
                pane.bcari_tipe_bayar.removeActionListener(this);
                Staticvar.sfilter = "";
                Staticvar.preid = id_card;
                Staticvar.prelabel = String.valueOf(pane.edtipe_bayar.getText());
                Staticvar.prevalueextended = String.valueOf(charge);
                Staticvar.prevalueextended2 = String.valueOf(status_voucher);
                Staticvar.prevalueextended3 = String.valueOf(status_card);
                JDialog jd = new JDialog(new Mainmenu());
                jd.add(new Popupcari("tipepembayaran", "dm/daftarposbayardengan", "Daftar Pembayaran"));
                jd.pack();
                jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                jd.setLocationRelativeTo(null);
                jd.setVisible(true);
                jd.toFront();
                id_card = Staticvar.resid;
                pane.edtipe_bayar.setText(Staticvar.reslabel);
                charge = FuncHelper.ToDouble(Staticvar.resvalueextended);
                status_voucher = FuncHelper.ToInt(Staticvar.resvalueextended2);
                status_card = FuncHelper.ToInt(Staticvar.resvalueextended3);
                id_akun_charge = Staticvar.resvalueextended4;

                if (status_card == 1) {
                    pane.lno_kartu.setVisible(true);
                    pane.lttk_no_kartu.setVisible(true);
                    pane.lnama_pemilik.setVisible(true);
                    pane.lttk_nama_pemilik.setVisible(true);
                    pane.edno_kartu.setVisible(true);
                    pane.ednama_pemilik.setVisible(true);
                    pane.edtambah_cash.setVisible(true);
                    pane.ltambah_cash.setVisible(true);
                    pane.lttk_tambah_cash.setVisible(true);
                    pane.edtambah_cash.setText("0");
                    pane.lno_kartu.setText("No. Kartu");
                    pane.lnama_pemilik.setText("Nama Pemilik");
                    charge_nominal = ((charge / 100) * totalbayar);
                    double total_bayar = charge_nominal + totalbayar;
                    if (istunai) {
                        if (pane.cktambahpiutang.isSelected()) {
                            pane.ltotal.setText(nf.format(total_bayar + jumlah_piutang));
                            pane.edbayar.setText(String.valueOf(total_bayar + jumlah_piutang));
                            pane.ljumlah_bayar.setText(nf.format(FuncHelper.ToDouble(pane.edbayar.getText())));
                            pane.lkembali.setText("0");
                            pane.lkembalilabel.setText("KEMBALI");
                        } else {
                            pane.ltotal.setText(nf.format(total_bayar));
                            pane.edbayar.setText(String.valueOf(total_bayar));
                            pane.ljumlah_bayar.setText(nf.format(FuncHelper.ToDouble(pane.edbayar.getText())));
                            pane.lkembali.setText("0");
                            pane.lkembalilabel.setText("KEMBALI");
                        }
                    } else {
                        if (pane.cktambahpiutang.isSelected()) {
                            pane.ltotal.setText(nf.format(total_bayar + jumlah_piutang));
                            pane.edbayar.setText(String.valueOf(0));
                            pane.ljumlah_bayar.setText(nf.format(FuncHelper.ToDouble(pane.edbayar.getText())));
                            pane.lkembalilabel.setText("SISA");
                            pane.lkembali.setText(nf.format(total_bayar + jumlah_piutang));
                            pane.edbayar.selectAll();
                        } else {
                            pane.ltotal.setText(nf.format(total_bayar));
                            pane.edbayar.setText(String.valueOf(0));
                            pane.ljumlah_bayar.setText(nf.format(FuncHelper.ToDouble(pane.edbayar.getText())));
                            pane.lkembalilabel.setText("SISA");
                            pane.lkembali.setText(nf.format(total_bayar));
                            pane.edbayar.selectAll();
                        }

                    }
                } else if (status_voucher == 1) {
                    pane.lno_kartu.setVisible(true);
                    pane.lttk_no_kartu.setVisible(true);
                    pane.lnama_pemilik.setVisible(true);
                    pane.lttk_nama_pemilik.setVisible(true);
                    pane.edno_kartu.setVisible(true);
                    pane.ednama_pemilik.setVisible(true);
                    pane.edtambah_cash.setVisible(true);
                    pane.ltambah_cash.setVisible(true);
                    pane.edtambah_cash.setText("0");
                    pane.lttk_tambah_cash.setVisible(true);
                    pane.lno_kartu.setText("No Voucher");
                    pane.lnama_pemilik.setText("Nama Voucher");
                    if (pane.cktambahpiutang.isSelected()) {
                        pane.ltotal.setText(nf.format(totalbayar + jumlah_piutang));
                        pane.edbayar.setText(nf.format(totalbayar + jumlah_piutang));
                        pane.ljumlah_bayar.setText(nf.format(FuncHelper.ToDouble(pane.edbayar.getText())));
                    } else {
                        pane.ltotal.setText(nf.format(totalbayar));
                        pane.edbayar.setText("0");
                        pane.ljumlah_bayar.setText(nf.format(FuncHelper.ToDouble(pane.edbayar.getText())));
                    }
                } else {
                    pane.lno_kartu.setVisible(false);
                    pane.lttk_no_kartu.setVisible(false);
                    pane.lnama_pemilik.setVisible(false);
                    pane.lttk_nama_pemilik.setVisible(false);
                    pane.edno_kartu.setVisible(false);
                    pane.ednama_pemilik.setVisible(false);
                    pane.edtambah_cash.setVisible(false);
                    pane.ltambah_cash.setVisible(false);
                    pane.edtambah_cash.setText("0");
                    pane.lttk_tambah_cash.setVisible(false);
                    if (pane.cktambahpiutang.isSelected()) {
                        pane.ltotal.setText(nf.format(totalbayar + jumlah_piutang));
                        pane.edbayar.setText("0");
                        pane.ljumlah_bayar.setText(nf.format(FuncHelper.ToDouble(pane.edbayar.getText())));
                        pane.lkembali.setText(nf.format(totalbayar + jumlah_piutang));
                    } else {
                        pane.ltotal.setText(nf.format(totalbayar));
                        pane.edbayar.setText("0");
                        pane.ljumlah_bayar.setText(nf.format(FuncHelper.ToDouble(pane.edbayar.getText())));
                        pane.lkembali.setText(nf.format(totalbayar));
                    }
                    if (istunai) {
                        pane.lkembalilabel.setText("KURANG");
                    } else {
                        pane.lkembalilabel.setText("SISA");
                    }
                }
                KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keydis);
                pane.bcari_tipe_bayar.addActionListener(this);

            }
        };

        pane.bcari_tipe_bayar.addActionListener(al);

    }

    public class Entitycombo {

        String id, nama, charge, status_card, status_voucher;

        public Entitycombo(String id, String nama, String charge, String status_card, String status_voucher) {
            this.id = id;
            this.nama = nama;
            this.charge = charge;
            this.status_card = status_card;
            this.status_voucher = status_voucher;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getCharge() {
            return charge;
        }

        public void setCharge(String charge) {
            this.charge = charge;
        }

        public String getStatus_card() {
            return status_card;
        }

        public void setStatus_card(String status_card) {
            this.status_card = status_card;
        }

        public String getStatus_voucher() {
            return status_voucher;
        }

        public void setStatus_voucher(String status_voucher) {
            this.status_voucher = status_voucher;
        }

    }

}
