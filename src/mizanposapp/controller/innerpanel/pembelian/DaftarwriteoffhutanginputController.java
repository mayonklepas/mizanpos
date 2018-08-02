/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.pembelian;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import mizanposapp.helper.ConvertFunc;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Globalsession;
import mizanposapp.helper.Staticvar;
import mizanposapp.helper.numtoword;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Errorpanel;
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.pembelian.Daftarhutang_inner_panel;
import mizanposapp.view.innerpanel.pembelian.Daftarhutangrincian_inner_panel;
import mizanposapp.view.innerpanel.pembelian.Daftarwriteoffhutang_input_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class DaftarwriteoffhutanginputController {

    String id;
    String valsupplier = "", valakun_pengeluaran = "", valdept = "", valakun_transaksi = "", valid_transaksi = "";
    int no_urut = 0, valgiro = 0;
    CrudHelper ch = new CrudHelper();
    Daftarwriteoffhutang_input_panel pane;
    NumberFormat nf = NumberFormat.getInstance();

    public DaftarwriteoffhutanginputController(Daftarwriteoffhutang_input_panel pane) {
        this.pane = pane;
        String id = Staticvar.ids;
        String tipe = Staticvar.frame;
        Staticvar.ids = "";
        Staticvar.frame = "";
        skinning();
        loaddata(tipe, id);
        simpandata(tipe, id);
        cariakunpengeluaran();
        caridepartment();
        batal(tipe);
    }

    private void skinning() {
        pane.dtanggal.setDateFormatString("dd MMMM yyyy");
        pane.dtanggal.setDate(new Date());

    }

    private void getkodetransaksi() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONParser jpdata = new JSONParser();
                    String param = String.format("id_keltrans=%s", "45");
                    Object ob = jpdata.parse(ch.getdatadetails("getnomortransaksi", param));
                    JSONArray ja = (JSONArray) ob;
                    for (int i = 0; i < ja.size(); i++) {
                        JSONObject jo = (JSONObject) ja.get(i);
                        pane.ednoref.setText(String.valueOf(jo.get("no_transaksi")));
                        no_urut = ConvertFunc.ToInt(String.valueOf(jo.get("no_urut")));
                    }

                } catch (ParseException ex) {
                    Logger.getLogger(DaftarorderpembelianinputController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    private void loaddata(String tipe, String id) {
        try {
            if (tipe.equals("add")) {
                getkodetransaksi();
                JSONParser jpdata = new JSONParser();
                String param = String.format("id=%s", id);
                Object rawobjdata = jpdata.parse(ch.getdatadetails("datapembayaranhutang", param));
                JSONObject jsonobjdata = (JSONObject) rawobjdata;

                Object jogenjur = jsonobjdata.get("genjur");
                JSONArray jagenjur = (JSONArray) jogenjur;

                pane.edsupplier.setText(String.valueOf(Staticvar.map_var.get("nama_supplier")));
                valsupplier = String.valueOf(Staticvar.map_var.get("id_supplier"));
                for (int i = 0; i < jagenjur.size(); i++) {
                    JSONObject joingenjur = (JSONObject) jagenjur.get(i);
                    pane.ednoref.setText(String.valueOf(joingenjur.get("id_no_genjur")));
                    pane.edketerangan_transaksi.setText(String.valueOf(joingenjur.get("keterangan")));
                    pane.eddept.setText(String.valueOf(joingenjur.get("nama_dept")));
                    valdept = String.valueOf(joingenjur.get("id_dept"));
                    pane.dtanggal.setDate(new Date());

                }

                JSONParser jpdatadetail = new JSONParser();
                String paramdetail = String.format("id=%s", id);
                Object rawobjdatadetail = jpdatadetail.parse(ch.getdatadetails("datahutangpersupplier", paramdetail));
                JSONArray jadetail = (JSONArray) rawobjdatadetail;
                for (int i = 0; i < jadetail.size(); i++) {
                    JSONObject jointabledata = (JSONObject) jadetail.get(i);
                    valid_transaksi = String.valueOf(jointabledata.get("id"));
                    valakun_transaksi = String.valueOf(jointabledata.get("akun"));
                    pane.ednotransaksi.setText(String.valueOf(jointabledata.get("noref")));
                    pane.edtanggaltransaksi.setText(String.valueOf(jointabledata.get("tanggal")));
                    pane.edtotal.setText(String.valueOf(jointabledata.get("total")));
                    double sebenarnyasisa = ConvertFunc.ToDouble(jointabledata.get("sisa"));
                    pane.edsisa_hutang.setText(String.valueOf(sebenarnyasisa));
                    pane.edjumlah_hapus.setText(String.valueOf(jointabledata.get("sisa")));
                }
            } else {
                JSONParser jpdata = new JSONParser();
                String param = String.format("id=%s", id);
                Object rawobjdata = jpdata.parse(ch.getdatadetails("datapembayaranhutang", param));
                JSONObject jsonobjdata = (JSONObject) rawobjdata;

                Object jogenjur = jsonobjdata.get("genjur");
                JSONArray jagenjur = (JSONArray) jogenjur;
                for (int i = 0; i < jagenjur.size(); i++) {
                    JSONObject joingenjur = (JSONObject) jagenjur.get(i);
                    pane.ednoref.setText(String.valueOf(joingenjur.get("noref")));
                    pane.edketerangan.setText(String.valueOf(joingenjur.get("keterangan")));
                    pane.eddept.setText(String.valueOf(joingenjur.get("nama_dept")));
                    valdept = String.valueOf(joingenjur.get("id_dept"));
                    try {
                        pane.dtanggal.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(joingenjur.get("tanggal"))));
                    } catch (java.text.ParseException ex) {
                        Logger.getLogger(DaftarorderpembelianinputController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

                Object jokaskeluar = jsonobjdata.get("kaskeluar");
                JSONArray jakaskeluar = (JSONArray) jokaskeluar;
                for (int i = 0; i < jakaskeluar.size(); i++) {
                    JSONObject joinkaskeluar = (JSONObject) jakaskeluar.get(i);
                    pane.edsupplier.setText(String.valueOf(joinkaskeluar.get("nama_cards")));
                    valsupplier = String.valueOf(joinkaskeluar.get("id_cards"));
                    valakun_pengeluaran = String.valueOf(joinkaskeluar.get("akun_keluar_dari"));
                    pane.edakun_pengeluaran.setText(valakun_pengeluaran + "-" + String.valueOf(joinkaskeluar.get("nama_akun_keluar_dari")));
                }

                Object jokaskeluardetail = jsonobjdata.get("kaskeluar_detail");
                JSONArray jakaskeluardetail = (JSONArray) jokaskeluardetail;
                for (int i = 0; i < jakaskeluardetail.size(); i++) {
                    JSONObject joinkaskeluar_detail = (JSONObject) jakaskeluardetail.get(i);
                    valid_transaksi = String.valueOf(joinkaskeluar_detail.get("id"));
                    valakun_transaksi = String.valueOf(joinkaskeluar_detail.get("akun"));
                    pane.edketerangan_transaksi.setText(String.valueOf(joinkaskeluar_detail.get("keterangan_transaksi")));
                    pane.ednotransaksi.setText(String.valueOf(joinkaskeluar_detail.get("noref")));
                    pane.edtanggaltransaksi.setText(String.valueOf(joinkaskeluar_detail.get("tanggal")));
                    pane.edtotal.setText(String.valueOf(joinkaskeluar_detail.get("total")));
                    double sebenarnyasisa = ConvertFunc.ToDouble(joinkaskeluar_detail.get("sisa")) + ConvertFunc.ToDouble(joinkaskeluar_detail.get("jumlah"));
                    pane.edsisa_hutang.setText(String.valueOf(sebenarnyasisa));
                    pane.edjumlah_hapus.setText(String.valueOf(joinkaskeluar_detail.get("jumlah")));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void rawsimpan(String tipe, String id) {
        if (tipe.equals("add")) {
            String data = "genjur="
                    + "id_keltrans='45'::"
                    + "id_dept='" + valdept + "'::"
                    + "tanggal='" + new SimpleDateFormat("yyyy-MM-dd").format(pane.dtanggal.getDate()) + "'::"
                    + "noref='" + ConvertFunc.EncodeString(pane.ednoref.getText()) + "'::"
                    + "keterangan='" + ConvertFunc.EncodeString(pane.edketerangan.getText()) + "'"
                    + "&kaskeluar="
                    + "id_cards='" + valsupplier + "'::"
                    + "akun_keluar_dari='" + valakun_pengeluaran + "'::"
                    + "jumlah='" + ConvertFunc.ToDouble(pane.edtotal.getText()) + "'"
                    + "&kaskeluar_detail="
                    + "id_no_genjur='" + valid_transaksi + "'::"
                    + "akun='" + valakun_transaksi + "'::"
                    + "jumlah='" + pane.edjumlah_hapus.getText() + "'";

            ch.insertdata("insertpembayaranhutang", data);
            if (Staticvar.getresult.equals("berhasil")) {
                Staticvar.isupdate = true;
                JDialog jd = (JDialog) pane.getRootPane().getParent();
                jd.dispose();
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
        } else {
            String data = "genjur="
                    + "id_keltrans='45'::"
                    + "id_dept='" + valdept + "'::"
                    + "tanggal='" + new SimpleDateFormat("yyyy-MM-dd").format(pane.dtanggal.getDate()) + "'::"
                    + "noref='" + ConvertFunc.EncodeString(pane.ednoref.getText()) + "'::"
                    + "keterangan='" + ConvertFunc.EncodeString(pane.edketerangan.getText()) + "'"
                    + "&kaskeluar="
                    + "id_cards='" + valsupplier + "'::"
                    + "akun_keluar_dari='" + valakun_pengeluaran + "'::"
                    + "jumlah='" + ConvertFunc.ToDouble(pane.edtotal.getText()) + "'"
                    + "&kaskeluar_detail="
                    + "id_no_genjur='" + valid_transaksi + "'::"
                    + "akun='" + valakun_transaksi + "'::"
                    + "jumlah='" + pane.edjumlah_hapus.getText() + "'";

            ch.updatedata("updatepembayaranhutang", data, id);
            if (Staticvar.getresult.equals("berhasil")) {
                Staticvar.isupdate = true;
                JDialog jd = (JDialog) pane.getRootPane().getParent();
                jd.dispose();
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

    private void simpandata(String tipe, String id) {
        pane.bsimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Staticvar.isupdate = true;
                double jumlahbayar = ConvertFunc.ToDouble(pane.edjumlah_hapus.getText());
                double sisa = ConvertFunc.ToDouble(pane.edsisa_hutang.getText());
                if (pane.edakun_pengeluaran.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Akun tidak boleh kosong", "Informasi", JOptionPane.INFORMATION_MESSAGE);

                } else if (pane.edketerangan.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Keterangan tidak boleh kosong", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else if (jumlahbayar <= 0 || jumlahbayar > sisa || pane.edjumlah_hapus.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Jumlah bayar tidak boleh 0 atau lebih besar dari sisa", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    int tahunbulan = Integer.parseInt(new SimpleDateFormat("yyyyMM").format(pane.dtanggal.getDate()));
                    int periodetahunnulan = Integer.parseInt(Globalsession.PERIODE_TAHUN + Globalsession.PERIODE_BULAN);
                    if (tahunbulan > periodetahunnulan) {
                        int dialog = JOptionPane.showConfirmDialog(null, "Tanggal transaksi setelah periode akuntansi.\n"
                                + "Apakah anda ingin melanjutkan transaksi ?", "Konfirmasi", JOptionPane.YES_NO_OPTION, 1);
                        if (dialog == 0) {

                            rawsimpan(tipe, id);

                        }
                    } else if (tahunbulan < periodetahunnulan) {
                        JDialog jd = new JDialog(new Mainmenu());
                        Errorpanel ep = new Errorpanel();
                        ep.ederror.setText("Tanggal transaksi sebelum periode akuntansi. \n"
                                + "Anda tidak dapat memasukan, mengedit,menghapus transaksi sebelum periode. \n"
                                + "Untuk dapat memasukan atau mengedit transaksi, silahkan merubah periode akuntansi");
                        jd.add(ep);
                        jd.pack();
                        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        jd.setLocationRelativeTo(null);
                        jd.setVisible(true);
                        jd.toFront();
                    } else {

                        rawsimpan(tipe, id);

                    }
                }
            }
        });
    }

    private void caridepartment() {
        pane.bcari_dept.addActionListener((ActionEvent e) -> {
            Staticvar.sfilter = "";
            Staticvar.preid = valdept;
            Staticvar.prelabel = pane.eddept.getText();
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("department", "popupdaftardept", "Daftar Department"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valdept = Staticvar.resid;
            pane.eddept.setText(Staticvar.reslabel);
        });

    }

    private void rawgetidakun(String prevlabel, String previd) {
        Staticvar.sfilter = "";
        Staticvar.preid = previd;
        Staticvar.prelabel = prevlabel;
        JDialog jd = new JDialog(new Mainmenu());
        jd.add(new Popupcari("akun", "popupdaftarakun", "Daftar Akun"));
        jd.pack();
        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        jd.setLocationRelativeTo(null);
        jd.setVisible(true);
        jd.toFront();
    }

    private void cariakunpengeluaran() {
        pane.bcariakun_pengeluaran.addActionListener((ActionEvent e) -> {
            rawgetidakun(pane.edakun_pengeluaran.getText(), valakun_pengeluaran);
            if (!Staticvar.resid.equals(valakun_pengeluaran)) {
                valakun_pengeluaran = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.edakun_pengeluaran.setText(val);
            }
        });

    }

    private void batal(String tipe) {
        pane.bbatal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        Staticvar.isupdate = false;
                        if (tipe.equals("add")) {
                            String data = String.format("id_keltrans=%s&no_urut=%s", "45", String.valueOf(no_urut));
                            ch.insertdata("insertnomorgagal", data);
                        }
                        JDialog jd = (JDialog) pane.getRootPane().getParent();
                        jd.dispose();
                    }
                });
            }
        });
    }

}
