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
import mizanposapp.helper.ConvertFunc;
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
    public static double totalbayar = 0, total_pajak = 0, total_service = 0, sub_total = 0;
    public static String valpelanggan = "", valgudang = "", valdept = "", valsalesman = "", valshipvia = "", valtop = "",
         valakun_penjualan = "", valakun_ongkir = "", valakun_diskon = "", valakun_uang_muka = "", valgolongan = "",
         no_transaksi, keterangan, kirimtextpenjualan = "";
    String val_tipe_bayar;
    public static Date tanggal;
    public static boolean ispersen = true;
    public static boolean istunai = true;
    String id_card = "";
    double charge = 0;
    int status_voucher = 0, status_card = 0;
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
                    double biayalain = ConvertFunc.ToDouble(pane.edbiaya_lain.getText());
                    double diskon = ConvertFunc.ToDouble(pane.eddiskon_nominal.getText());
                    double pajak = total_pajak;
                    totalbayar = subtotal + biayalain - diskon + pajak;
                    pane.ltotal.setText(nf.format(totalbayar));
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
                    double biayalain = ConvertFunc.ToDouble(pane.edbiaya_lain.getText());
                    double indiskon_persen = ConvertFunc.ToDouble(pane.eddiskon_persen.getText());
                    double indiskon_nominal = (subtotal + biayalain) * (indiskon_persen / 100);
                    double pajak = ConvertFunc.ToDouble(pane.ltotal_pajak.getText());
                    totalbayar = subtotal + biayalain - indiskon_nominal + pajak;

                    pane.eddiskon_nominal.setText(nf.format(indiskon_nominal));
                    pane.ltotal.setText(nf.format(totalbayar));
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
                    double biayalain = ConvertFunc.ToDouble(pane.edbiaya_lain.getText());
                    double pajak = ConvertFunc.ToDouble(pane.ltotal_pajak.getText());
                    double indiskon_nominal = ConvertFunc.ToDouble(pane.eddiskon_nominal.getText());
                    double indiskon_persen = (indiskon_nominal / (subtotal + biayalain)) * 100;
                    totalbayar = subtotal + biayalain - indiskon_nominal + pajak;
                    pane.eddiskon_persen.setText(ConvertFunc.rounding(indiskon_persen));
                    pane.ltotal.setText(nf.format(totalbayar));
                } else {
                    JOptionPane.showMessageDialog(null, "Hanya memperbolehkan angka");
                    pane.eddiskon_nominal.setText("0");
                }
            }

        };

        KeyAdapter kebayar = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                double jumlah_uang = ConvertFunc.ToDouble(pane.edbayar.getText());
                double kembalian = 0;
                try {
                    kembalian = jumlah_uang - ConvertFunc.ToDouble(pane.ltotal.getText());
                    if (kembalian < 0) {
                        kembalian = 0;
                    }
                } catch (Exception es) {
                    kembalian = 0;
                }

                pane.lkembali.setText(nf.format(kembalian));
                pane.ljumlah_bayar.setText(nf.format(jumlah_uang));
            }

        };

        pane.eddiskon_persen.addKeyListener(keaddiskonpersen);
        pane.edbiaya_lain.addKeyListener(keadbiaya);
        pane.eddiskon_nominal.addKeyListener(keaddiskonnominal);
        pane.edbayar.addKeyListener(kebayar);

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
    }

    /*private void loadcomboakun() {
        pane.cmb_pembayaran.removeAllItems();
        pembayaranlist.clear();
        try {
            JSONParser jpdata = new JSONParser();
            Object rawobjdata = jpdata.parse(ch.getdatas("dm/daftarposbayardengan"));
            JSONArray ja = (JSONArray) rawobjdata;
            for (int i = 0; i < ja.size(); i++) {
                JSONObject jo = (JSONObject) ja.get(i);
                pembayaranlist.add(new Entitycombo(String.valueOf(jo.get("id")),
                     String.valueOf(jo.get("nama")),
                     String.valueOf(jo.get("charge")),
                     String.valueOf(jo.get("iscard")),
                     String.valueOf(jo.get("isvoucher"))));
            }

            for (int i = 0; i < pembayaranlist.size(); i++) {
                pane.cmb_pembayaran.addItem(pembayaranlist.get(i).getNama());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

 /*private void combopembayarancontrol() {
        pane.cmb_pembayaran.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    int index = pane.cmb_pembayaran.getSelectedIndex();
                    id_card = pembayaranlist.get(index).getId();
                    charge = ConvertFunc.ToDouble(pembayaranlist.get(index).getCharge());
                    status_card = ConvertFunc.ToInt(pembayaranlist.get(index).getStatus_card());
                    status_voucher = ConvertFunc.ToInt(pembayaranlist.get(index).getStatus_voucher());
                    if (status_card == 1) {
                        pane.lno_kartu.setVisible(true);
                        pane.lttk_no_kartu.setVisible(true);
                        pane.lnama_pemilik.setVisible(true);
                        pane.lttk_nama_pemilik.setVisible(true);
                        pane.edno_kartu.setVisible(true);
                        pane.ednama_pemilik.setVisible(true);
                        pane.lno_kartu.setText("No. Kartu");
                        pane.lnama_pemilik.setText("Nama Pemilik");
                        double sebenarnya_total = ((charge / 100) * totalbayar) + totalbayar;
                        pane.ltotal.setText(nf.format(sebenarnya_total));
                        pane.edbayar.setText(String.valueOf(sebenarnya_total));
                        pane.ljumlah_bayar.setText(nf.format(sebenarnya_total));
                    } else if (status_voucher == 1) {
                        pane.lno_kartu.setVisible(true);
                        pane.lttk_no_kartu.setVisible(true);
                        pane.lnama_pemilik.setVisible(true);
                        pane.lttk_nama_pemilik.setVisible(true);
                        pane.edno_kartu.setVisible(true);
                        pane.ednama_pemilik.setVisible(true);

                        pane.lno_kartu.setText("No Voucher");
                        pane.lnama_pemilik.setText("Nama Voucher");
                    } else {
                        pane.lno_kartu.setVisible(false);
                        pane.lttk_no_kartu.setVisible(false);
                        pane.lnama_pemilik.setVisible(false);
                        pane.lttk_nama_pemilik.setVisible(false);
                        pane.edno_kartu.setVisible(false);
                        pane.ednama_pemilik.setVisible(false);
                        pane.ltotal.setText(nf.format(totalbayar));
                        pane.edbayar.setText(String.valueOf("0"));
                        pane.ljumlah_bayar.setText(String.valueOf("0"));
                    }
                }
            }
        });
    }*/
    public void rawsimpan() {
        if (pane.lperingatan.isVisible()) {
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
                 + "noref='" + ConvertFunc.EncodeString(no_transaksi) + "'::"
                 + "keterangan='" + ConvertFunc.EncodeString(keterangan) + "'"
                 + "&penjualan="
                 + "id_pelanggan='" + valpelanggan + "'::"
                 + "tipe_pembayaran='" + tipe_beli + "'::"
                 + "id_gudang='" + valgudang + "'::"
                 + "total_penjualan='" + totalbayar + "'::"
                 + "total_biaya='" + ConvertFunc.ToDouble(pane.edbiaya_lain.getText()) + "'::"
                 + "diskon_persen='" + ConvertFunc.ToDouble(pane.eddiskon_persen.getText()) + "'::"
                 + "diskon_nominal='" + ConvertFunc.ToDouble(pane.eddiskon_nominal.getText()) + "'::"
                 + "total_uang_muka='" + ConvertFunc.ToDouble(uang_muka) + "'::"
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
                 + "pos_jumlah_bayar='" + ConvertFunc.ToDouble(pane.edbayar.getText()) + "'::"
                 + "pos_nomor_kartu='" + pane.edno_kartu.getText() + "'::"
                 + "pos_pemilik_kartu='" + pane.ednama_pemilik.getText() + "'::"
                 + "pos_nilai_poin='" + ConvertFunc.ToDouble(pane.ednilai_poin.getText()) + "'::"
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
                double jumlah_uang = ConvertFunc.ToDouble(pane.edbayar.getText());
                if (istunai) {
                    if (jumlah_uang < totalbayar) {
                        JOptionPane.showMessageDialog(null, "Jumlah Uang tidak boleh lebh kecil dari jumlah bayar");
                    } else {
                        rawsimpan();
                    }
                } else {
                    if (jumlah_uang > totalbayar) {
                        JOptionPane.showMessageDialog(null, "Jumlah Uang tidak boleh lebh besar dari jumlah bayar");
                    } else {
                        rawsimpan();
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
                pane.bcari_tipe_bayar.removeActionListener(this);
                Staticvar.sfilter = "";
                Staticvar.preid = val_tipe_bayar;
                Staticvar.resvalueextended = valgolongan;
                Staticvar.prelabel = String.valueOf(pane.edtipe_bayar.getText());
                JDialog jd = new JDialog(new Mainmenu());
                jd.add(new Popupcari("tipepembayaran", "dm/daftarposbayardengan", "Daftar Pembayaran"));
                jd.pack();
                jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                jd.setLocationRelativeTo(null);
                jd.setVisible(true);
                jd.toFront();
                id_card = Staticvar.resid;
                pane.edtipe_bayar.setText(Staticvar.reslabel);
                charge = ConvertFunc.ToDouble(Staticvar.resvalueextended);
                status_voucher = ConvertFunc.ToInt(Staticvar.resvalueextended2);
                status_card = ConvertFunc.ToInt(Staticvar.resvalueextended3);
                if (status_card == 1) {
                    pane.lno_kartu.setVisible(true);
                    pane.lttk_no_kartu.setVisible(true);
                    pane.lnama_pemilik.setVisible(true);
                    pane.lttk_nama_pemilik.setVisible(true);
                    pane.edno_kartu.setVisible(true);
                    pane.ednama_pemilik.setVisible(true);
                    pane.lno_kartu.setText("No. Kartu");
                    pane.lnama_pemilik.setText("Nama Pemilik");
                    double total_bayar = ((charge / 100) * totalbayar) + totalbayar;
                    pane.ltotal.setText(nf.format(total_bayar));
                    pane.edbayar.setText(String.valueOf(total_bayar));
                    pane.ljumlah_bayar.setText(nf.format(total_bayar));
                } else if (status_voucher == 1) {
                    pane.lno_kartu.setVisible(true);
                    pane.lttk_no_kartu.setVisible(true);
                    pane.lnama_pemilik.setVisible(true);
                    pane.lttk_nama_pemilik.setVisible(true);
                    pane.edno_kartu.setVisible(true);
                    pane.ednama_pemilik.setVisible(true);
                    pane.lno_kartu.setText("No Voucher");
                    pane.lnama_pemilik.setText("Nama Voucher");
                } else {
                    pane.lno_kartu.setVisible(false);
                    pane.lttk_no_kartu.setVisible(false);
                    pane.lnama_pemilik.setVisible(false);
                    pane.lttk_nama_pemilik.setVisible(false);
                    pane.edno_kartu.setVisible(false);
                    pane.ednama_pemilik.setVisible(false);
                    pane.ltotal.setText(nf.format(totalbayar));
                    pane.edbayar.setText(String.valueOf("0"));
                    pane.ljumlah_bayar.setText(String.valueOf("0"));
                }

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
