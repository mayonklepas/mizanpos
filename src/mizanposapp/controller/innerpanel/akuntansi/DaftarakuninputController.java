/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.akuntansi;

import com.sun.org.apache.xerces.internal.impl.dtd.models.CMBinOp;
import mizanposapp.controller.innerpanel.akuntansi.*;
import mizanposapp.controller.innerpanel.penjualan.*;
import mizanposapp.controller.innerpanel.penjualan.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import mizanposapp.helper.FuncHelper;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Globalsession;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Errorpanel;
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.akuntansi.Daftarakun_input_panel;
import mizanposapp.view.innerpanel.akuntansi.Daftarkasbank_input_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Minami
 */
public class DaftarakuninputController {

    CrudHelper ch = new CrudHelper();
    Daftarakun_input_panel pane;
    NumberFormat nf = NumberFormat.getInstance();
    ArrayList<kelompokakun> listkelompok = new ArrayList<>();
    ArrayList<subakun> listsub = new ArrayList<>();
    ArrayList<deptdata> listdept = new ArrayList<>();
    ArrayList<currdata> listcur = new ArrayList<>();

    String id = "";
    String in_id_kelompok = "";
    String in_isparent = "";
    String in_acc_level = "";
    static boolean sudah_jangan_set_lagi_kau_membuat_semua_kacau = false;

    public DaftarakuninputController(Daftarakun_input_panel pane) {
        this.pane = pane;
        id = Staticvar.ids;
        Staticvar.ids = "";
        loaddata();
        showhide();
        simpandata();
        tutup();
        editbank();
    }

    private void showhide() {
        pane.cmbkelompok_akun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ind = pane.cmbkelompok_akun.getSelectedIndex();
                in_id_kelompok = listkelompok.get(ind).getId_kelompok();
                if (pane.cmbjenis_akun.getSelectedItem().equals("Akun Transaksi")) {
                    in_isparent = "0";
                } else {
                    in_isparent = "1";
                }
                in_acc_level = String.valueOf(pane.cmbakun_level.getSelectedItem());
                if (FuncHelper.ToInt(in_id_kelompok) == 1 && FuncHelper.ToInt(in_isparent) == 0) {
                    pane.cktipe_akun.setVisible(true);
                    pane.bdata_bank.setVisible(true);
                } else {
                    pane.cktipe_akun.setVisible(false);
                    pane.bdata_bank.setVisible(false);
                }

                if (FuncHelper.ToInt(in_isparent) == 0) {
                    sudah_jangan_set_lagi_kau_membuat_semua_kacau = false;
                    loadsubakun(in_id_kelompok, in_isparent, in_acc_level);
                    pane.cmbsub_akun_dari.setVisible(true);
                    pane.lsubakun.setVisible(true);
                    pane.ltitikduasubakun.setVisible(true);

                } else if (FuncHelper.ToInt(in_isparent) == 1 && !in_acc_level.equals("2")) {
                    loadsubakun(in_id_kelompok, in_isparent, in_acc_level);
                    sudah_jangan_set_lagi_kau_membuat_semua_kacau = false;
                    pane.cmbsub_akun_dari.setVisible(true);
                    pane.lsubakun.setVisible(true);
                    pane.ltitikduasubakun.setVisible(true);
                } else {
                    pane.cmbsub_akun_dari.setVisible(false);
                    pane.lsubakun.setVisible(false);
                    pane.ltitikduasubakun.setVisible(false);
                }

                if (!listsub.isEmpty()) {
                    String subakun = listsub.get(pane.cmbsub_akun_dari.getSelectedIndex()).getId_subakun();
                    String valdept = listdept.get(pane.cmbdept.getSelectedIndex()).getId_dept();
                    String kode_trans = getkode(in_id_kelompok, in_isparent, subakun, in_acc_level, valdept);
                    pane.edkode_akun.setText(kode_trans);
                } else {
                    String valdept = listdept.get(pane.cmbdept.getSelectedIndex()).getId_dept();
                    String kode_trans = getkode(in_id_kelompok, in_isparent, "", in_acc_level, valdept);
                    pane.edkode_akun.setText(kode_trans);
                }

            }

        });

        pane.cmbjenis_akun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ind = pane.cmbkelompok_akun.getSelectedIndex();
                in_id_kelompok = listkelompok.get(ind).getId_kelompok();
                if (pane.cmbjenis_akun.getSelectedItem().equals("Akun Transaksi")) {
                    in_isparent = "0";
                } else {
                    in_isparent = "1";
                }
                in_acc_level = String.valueOf(pane.cmbakun_level.getSelectedItem());
                if (FuncHelper.ToInt(in_id_kelompok) == 1 && FuncHelper.ToInt(in_isparent) == 0) {
                    pane.cktipe_akun.setVisible(true);
                    pane.bdata_bank.setVisible(true);
                } else {
                    pane.cktipe_akun.setVisible(false);
                    pane.bdata_bank.setVisible(false);
                }

                if (FuncHelper.ToInt(in_isparent) == 0) {
                    sudah_jangan_set_lagi_kau_membuat_semua_kacau = false;
                    loadsubakun(in_id_kelompok, in_isparent, in_acc_level);
                    pane.cmbsub_akun_dari.setVisible(true);
                    pane.lsubakun.setVisible(true);
                    pane.ltitikduasubakun.setVisible(true);
                } else if (FuncHelper.ToInt(in_isparent) == 1 && !in_acc_level.equals("2")) {
                    sudah_jangan_set_lagi_kau_membuat_semua_kacau = false;
                    loadsubakun(in_id_kelompok, in_isparent, in_acc_level);
                    pane.cmbsub_akun_dari.setVisible(true);
                    pane.lsubakun.setVisible(true);
                    pane.ltitikduasubakun.setVisible(true);
                } else {
                    pane.cmbsub_akun_dari.setVisible(false);
                    pane.lsubakun.setVisible(false);
                    pane.ltitikduasubakun.setVisible(false);
                }

                if (!listsub.isEmpty()) {
                    String subakun = listsub.get(pane.cmbsub_akun_dari.getSelectedIndex()).getId_subakun();
                    String valdept = listdept.get(pane.cmbdept.getSelectedIndex()).getId_dept();
                    String kode_trans = getkode(in_id_kelompok, in_isparent, subakun, in_acc_level, valdept);
                    pane.edkode_akun.setText(kode_trans);
                } else {
                    String valdept = listdept.get(pane.cmbdept.getSelectedIndex()).getId_dept();
                    String kode_trans = getkode(in_id_kelompok, in_isparent, "", in_acc_level, valdept);
                    pane.edkode_akun.setText(kode_trans);
                }

            }
        });

        pane.cmbakun_level.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ind = pane.cmbkelompok_akun.getSelectedIndex();
                in_id_kelompok = listkelompok.get(ind).getId_kelompok();
                if (pane.cmbjenis_akun.getSelectedItem().equals("Akun Transaksi")) {
                    in_isparent = "0";
                } else {
                    in_isparent = "1";
                }
                in_acc_level = String.valueOf(pane.cmbakun_level.getSelectedItem());
                if (FuncHelper.ToInt(in_id_kelompok) == 1 && FuncHelper.ToInt(in_isparent) == 0) {
                    pane.cktipe_akun.setVisible(true);
                    pane.bdata_bank.setVisible(true);
                } else {
                    pane.cktipe_akun.setVisible(false);
                    pane.bdata_bank.setVisible(false);
                }

                if (FuncHelper.ToInt(in_isparent) == 0) {
                    sudah_jangan_set_lagi_kau_membuat_semua_kacau = false;
                    loadsubakun(in_id_kelompok, in_isparent, in_acc_level);
                    pane.cmbsub_akun_dari.setVisible(true);
                    pane.lsubakun.setVisible(true);
                    pane.ltitikduasubakun.setVisible(true);

                } else if (FuncHelper.ToInt(in_isparent) == 1 && !in_acc_level.equals("2")) {
                    sudah_jangan_set_lagi_kau_membuat_semua_kacau = false;
                    loadsubakun(in_id_kelompok, in_isparent, in_acc_level);
                    pane.cmbsub_akun_dari.setVisible(true);
                    pane.lsubakun.setVisible(true);
                    pane.ltitikduasubakun.setVisible(true);
                } else {
                    pane.cmbsub_akun_dari.setVisible(false);
                    pane.lsubakun.setVisible(false);
                    pane.ltitikduasubakun.setVisible(false);
                }

                if (!listsub.isEmpty()) {
                    String subakun = listsub.get(pane.cmbsub_akun_dari.getSelectedIndex()).getId_subakun();
                    String valdept = listdept.get(pane.cmbdept.getSelectedIndex()).getId_dept();
                    String kode_trans = getkode(in_id_kelompok, in_isparent, subakun, in_acc_level, valdept);
                    pane.edkode_akun.setText(kode_trans);
                } else {
                    String valdept = listdept.get(pane.cmbdept.getSelectedIndex()).getId_dept();
                    String kode_trans = getkode(in_id_kelompok, in_isparent, "", in_acc_level, valdept);
                    pane.edkode_akun.setText(kode_trans);
                }
            }
        });

        pane.cmbsub_akun_dari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sudah_jangan_set_lagi_kau_membuat_semua_kacau == true) {
                    if (!listsub.isEmpty()) {
                        String subakun = listsub.get(pane.cmbsub_akun_dari.getSelectedIndex()).getId_subakun();
                        String valdept = listdept.get(pane.cmbdept.getSelectedIndex()).getId_dept();
                        String kode_trans = getkode(in_id_kelompok, in_isparent, subakun, in_acc_level, valdept);
                        pane.edkode_akun.setText(kode_trans);
                    } else {
                        String valdept = listdept.get(pane.cmbdept.getSelectedIndex()).getId_dept();
                        String kode_trans = getkode(in_id_kelompok, in_isparent, "", in_acc_level, valdept);
                        pane.edkode_akun.setText(kode_trans);
                    }
                }
                sudah_jangan_set_lagi_kau_membuat_semua_kacau = true;

            }
        });
    }

    private void loaddata() {
        if (id.equals("")) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    pane.ednama_akun.requestFocus();
                }
            });
            pane.ckstatus.setSelected(true);
            loaddept();
            loadkelompok();
            loadmatauang();
            in_id_kelompok = listkelompok.get(0).getId_kelompok();
            in_isparent = String.valueOf(1);
            in_acc_level = pane.cmbakun_level.getItemAt(0);
            String iddept = listdept.get(0).getId_dept();
            pane.edkode_akun.setText(getkode(in_id_kelompok, in_isparent, "", in_acc_level, iddept));
            pane.cmbjenis_akun.setSelectedIndex(1);
            pane.cmbakun_level.setSelectedIndex(0);
            pane.cmbsub_akun_dari.setVisible(false);
            pane.lsubakun.setVisible(false);
            pane.ltitikduasubakun.setVisible(false);
            pane.cktipe_akun.setVisible(false);
            pane.bdata_bank.setVisible(false);
            pane.bgenerate.setVisible(false);
        } else {
            try {
                JSONParser jpdata = new JSONParser();
                String param = "id=" + id + "";
                Object rawobjdata = jpdata.parse(ch.getdatadetails("dm/dataakun", param));
                JSONArray ja = (JSONArray) rawobjdata;
                for (int i = 0; i < ja.size(); i++) {
                    JSONObject jo = (JSONObject) ja.get(i);
                    in_id_kelompok = String.valueOf(jo.get("id_kelompok"));
                    in_acc_level = String.valueOf(jo.get("acc_level"));
                    in_isparent = String.valueOf(jo.get("isparent"));
                    pane.edkode_akun.setText(String.valueOf(jo.get("id")));
                    pane.ednama_akun.setText(String.valueOf(jo.get("nama_akun")));
                    loadkelompokwithval(String.valueOf(jo.get("id_kelompok")), String.valueOf(jo.get("nama_kelompok")));
                    loaddeptwithval(String.valueOf(jo.get("id_dept")), String.valueOf(jo.get("nama_dept")));
                    loadmatauangwithval(String.valueOf(jo.get("id_currency")), String.valueOf(jo.get("kode_currency")));
                    loadsubakunwithval(String.valueOf(jo.get("id_kelompok")),
                            String.valueOf(jo.get("isparent")),
                            String.valueOf(jo.get("acc_level")),
                            String.valueOf(jo.get("akun_parent")),
                            String.valueOf(jo.get("nama_akun_parent")));
                    if (String.valueOf(jo.get("isparent")).equals("0")) {
                        pane.cmbjenis_akun.setSelectedIndex(0);
                    } else {
                        pane.cmbjenis_akun.setSelectedIndex(1);
                    }

                    if (String.valueOf(jo.get("acc_level")).equals("2")) {
                        pane.cmbakun_level.setSelectedIndex(0);
                    } else if (String.valueOf(jo.get("acc_level")).equals("3")) {
                        pane.cmbakun_level.setSelectedIndex(1);
                    }

                    int statusaktif = FuncHelper.ToInt(jo.get("isaktif"));
                    int statuskas = FuncHelper.ToInt(jo.get("iskasbank"));
                    if (statusaktif == 1) {
                        pane.ckstatus.setSelected(true);
                    } else {
                        pane.ckstatus.setSelected(false);
                    }

                    if (statuskas == 1) {
                        pane.cktipe_akun.setSelected(true);
                    } else {
                        pane.cktipe_akun.setSelected(false);
                    }

                    if (FuncHelper.ToInt(jo.get("id_kelompok")) == 1 && FuncHelper.ToInt(jo.get("isparent")) == 0) {
                        pane.cktipe_akun.setVisible(true);
                        pane.bdata_bank.setVisible(true);
                    } else {
                        pane.cktipe_akun.setVisible(false);
                        pane.bdata_bank.setVisible(false);
                    }

                    if (FuncHelper.ToInt(jo.get("isparent")) == 0) {
                        sudah_jangan_set_lagi_kau_membuat_semua_kacau = false;
                        pane.cmbsub_akun_dari.setVisible(true);
                        pane.lsubakun.setVisible(true);
                        pane.ltitikduasubakun.setVisible(true);

                    } else if (FuncHelper.ToInt(jo.get("isparent")) == 1 && !String.valueOf(jo.get("acc_level")).equals("2")) {
                        sudah_jangan_set_lagi_kau_membuat_semua_kacau = false;
                        pane.cmbsub_akun_dari.setVisible(true);
                        pane.lsubakun.setVisible(true);
                        pane.ltitikduasubakun.setVisible(true);
                    } else {
                        pane.cmbsub_akun_dari.setVisible(false);
                        pane.lsubakun.setVisible(false);
                        pane.ltitikduasubakun.setVisible(false);
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void rawsimpan() {
        int statusaktif = 0;
        int statusbank = 0;
        if (pane.ckstatus.isSelected()) {
            statusaktif = 1;
        } else {
            statusaktif = 0;

        }
        if (pane.cktipe_akun.isSelected()) {
            statusbank = 1;
        } else {
            statusbank = 0;
        }
        String subid = "";
        if (!listsub.isEmpty()) {
            subid = listsub.get(pane.cmbsub_akun_dari.getSelectedIndex()).getId_subakun();
        }
        if (id.equals("")) {
            String param = "acc=id='" + pane.edkode_akun.getText() + "'::"
                    + "nama='" + pane.ednama_akun.getText() + "'::"
                    + "id_acc_class='" + in_id_kelompok + "'::"
                    + "isparent='" + in_isparent + "'::"
                    + "akun_parent='" + subid + "'::"
                    + "isaktif='" + statusaktif + "'::"
                    + "acc_level='" + in_acc_level + "'::"
                    + "id_currency='" + listcur.get(pane.cmbmata_uang.getSelectedIndex()).getId_currency() + "'::"
                    + "id_dept='" + listdept.get(pane.cmbdept.getSelectedIndex()).getId_dept() + "'::"
                    + "iskasbank='" + statusbank + "'";
            ch.insertdata("dm/insertakun", param);
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
            String param = "acc=id='" + pane.edkode_akun.getText() + "'::"
                    + "nama='" + pane.ednama_akun.getText() + "'::"
                    + "id_acc_class='" + in_id_kelompok + "'::"
                    + "isparent='" + in_isparent + "'::"
                    + "akun_parent='" + subid + "'::"
                    + "isaktif='" + statusaktif + "'::"
                    + "acc_level='" + in_acc_level + "'::"
                    + "id_currency='" + listcur.get(pane.cmbmata_uang.getSelectedIndex()).getId_currency() + "'::"
                    + "id_dept='" + listdept.get(pane.cmbdept.getSelectedIndex()).getId_dept() + "'::"
                    + "iskasbank='" + statusbank + "'";
            ch.updatedata("dm/updateakun", param, id);
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

    private void simpandata() {
        pane.bsimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Staticvar.isupdate = true;
                if (pane.ednama_akun.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Dept tidak boleh kosong", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    rawsimpan();
                }
            }
        });
    }

    private void editbank() {
        pane.bdata_bank.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Staticvar.ids = pane.edkode_akun.getText();
                JDialog jd = new JDialog(new Mainmenu());
                jd.add(new Daftarkasbank_input_panel());
                jd.pack();
                jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                jd.setLocationRelativeTo(null);
                jd.setVisible(true);
                jd.toFront();
            }
        });
    }

    private void loadkelompok() {
        pane.cmbkelompok_akun.removeAllItems();
        listkelompok.clear();
        try {
            JSONParser jpdata = new JSONParser();
            Object rawobjdata = jpdata.parse(ch.getdatas("dm/daftarkelompokakun"));
            JSONArray ja = (JSONArray) rawobjdata;
            for (int i = 0; i < ja.size(); i++) {
                JSONObject jo = (JSONObject) ja.get(i);
                listkelompok.add(new kelompokakun(String.valueOf(jo.get("id_kelompok")),
                        String.valueOf(jo.get("nama_kelompok"))));

            }

            for (int i = 0; i < listkelompok.size(); i++) {
                pane.cmbkelompok_akun.addItem(listkelompok.get(i).getNama_kelompok());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadkelompokwithval(String id, String nama) {
        pane.cmbkelompok_akun.removeAllItems();
        listkelompok.clear();
        listkelompok.add(new kelompokakun(id, nama));
        try {
            JSONParser jpdata = new JSONParser();
            Object rawobjdata = jpdata.parse(ch.getdatas("dm/daftarkelompokakun"));
            JSONArray ja = (JSONArray) rawobjdata;
            for (int i = 0; i < ja.size(); i++) {
                JSONObject jo = (JSONObject) ja.get(i);
                if (!id.equals(jo.get("id_kelompok")) && !nama.equals(jo.get("nama_kelompok"))) {
                    listkelompok.add(new kelompokakun(String.valueOf(jo.get("id_kelompok")),
                            String.valueOf(jo.get("nama_kelompok"))));
                }

            }
            for (int i = 0; i < listkelompok.size(); i++) {
                pane.cmbkelompok_akun.addItem(listkelompok.get(i).getNama_kelompok());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadsubakun(String id_kelompok, String isparent, String acc_level) {
        pane.cmbsub_akun_dari.removeAllItems();
        listsub.clear();
        try {
            JSONParser jpdata = new JSONParser();
            String param = "id_kelompok=" + id_kelompok + "&isparent=" + isparent + "&acc_level=" + acc_level + "";
            Object rawobjdata = jpdata.parse(ch.getdatadetails("dm/getsubakun", param));
            JSONArray ja = (JSONArray) rawobjdata;
            for (int i = 0; i < ja.size(); i++) {
                JSONObject jo = (JSONObject) ja.get(i);
                listsub.add(new subakun(String.valueOf(jo.get("id_subakun")), String.valueOf(jo.get("nama_subakun"))));
            }

            for (int i = 0; i < listsub.size(); i++) {
                pane.cmbsub_akun_dari.addItem(listsub.get(i).getNama_subakun());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadsubakunwithval(String id_kelompok, String isparent, String acc_level, String id, String nama) {
        pane.cmbsub_akun_dari.removeAllItems();
        listsub.clear();
        listsub.add(new subakun(id, nama));
        try {
            JSONParser jpdata = new JSONParser();
            String param = "id_kelompok=" + id_kelompok + "&isparent=" + isparent + "&acc_level=" + acc_level + "";
            Object rawobjdata = jpdata.parse(ch.getdatadetails("dm/getsubakun", param));
            JSONArray ja = (JSONArray) rawobjdata;
            for (int i = 0; i < ja.size(); i++) {
                JSONObject jo = (JSONObject) ja.get(i);
                if (!id.equals(jo.get("id_subakun")) && !nama.equals(jo.get("nama_subakun"))) {
                    listsub.add(new subakun(String.valueOf(jo.get("id_subakun")),
                            String.valueOf(jo.get("nama_subakun"))));
                }
            }

            for (int i = 0; i < listsub.size(); i++) {
                pane.cmbsub_akun_dari.addItem(listsub.get(i).getNama_subakun());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loaddept() {
        pane.cmbdept.removeAllItems();
        try {
            JSONParser jpdata = new JSONParser();
            Object rawobjdata = jpdata.parse(ch.getdatas("dm/daftardeptakun"));
            JSONArray ja = (JSONArray) rawobjdata;
            for (int i = 0; i < ja.size(); i++) {
                JSONObject jo = (JSONObject) ja.get(i);
                listdept.add(new deptdata(String.valueOf(jo.get("id_dept")), String.valueOf(jo.get("nama_dept"))));
            }

            for (int i = 0; i < listdept.size(); i++) {
                pane.cmbdept.addItem(listdept.get(i).getNama_dept());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loaddeptwithval(String id, String nama) {
        pane.cmbdept.removeAllItems();
        listdept.clear();
        listdept.add(new deptdata(id, nama));
        try {
            JSONParser jpdata = new JSONParser();
            Object rawobjdata = jpdata.parse(ch.getdatas("dm/daftardeptakun"));
            JSONArray ja = (JSONArray) rawobjdata;
            for (int i = 0; i < ja.size(); i++) {
                JSONObject jo = (JSONObject) ja.get(i);
                if (!id.equals(jo.get("id_dept")) && !nama.equals(jo.get("nama_dept"))) {
                    listdept.add(new deptdata(String.valueOf(jo.get("id_dept")), String.valueOf(jo.get("nama_dept"))));
                }
            }

            for (int i = 0; i < listdept.size(); i++) {
                pane.cmbdept.addItem(listdept.get(i).getNama_dept());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadmatauang() {
        pane.cmbmata_uang.removeAllItems();
        listcur.clear();
        try {
            JSONParser jpdata = new JSONParser();
            Object rawobjdata = jpdata.parse(ch.getdatas("dm/daftarcurrencyakun"));
            JSONArray ja = (JSONArray) rawobjdata;
            for (int i = 0; i < ja.size(); i++) {
                JSONObject jo = (JSONObject) ja.get(i);
                listcur.add(new currdata(String.valueOf(jo.get("id_currency")),
                        String.valueOf(jo.get("nama_currency"))));
            }

            for (int i = 0; i < listcur.size(); i++) {
                pane.cmbmata_uang.addItem(listcur.get(i).getNama_currency());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadmatauangwithval(String id, String nama) {
        pane.cmbmata_uang.removeAllItems();
        listcur.clear();
        listcur.add(new currdata(id, nama));
        try {
            JSONParser jpdata = new JSONParser();
            Object rawobjdata = jpdata.parse(ch.getdatas("dm/daftarcurrencyakun"));
            JSONArray ja = (JSONArray) rawobjdata;
            for (int i = 0; i < ja.size(); i++) {
                JSONObject jo = (JSONObject) ja.get(i);
                if (!id.equals(jo.get("id_currency")) && !nama.equals(jo.get("nama_currency"))) {
                    listcur.add(new currdata(String.valueOf(jo.get("id_currency")),
                            String.valueOf(jo.get("nama_currency"))));
                }
            }

            for (int i = 0; i < listcur.size(); i++) {
                pane.cmbmata_uang.addItem(listcur.get(i).getNama_currency());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getkode(String id_kelompok, String isparent, String id_subakun, String acc_level, String id_dept) {
        String reskode = "";
        try {
            JSONParser jpdata = new JSONParser();
            String param = "id_kelompok=" + id_kelompok + ""
                    + "&isparent=" + isparent + ""
                    + "&id_subakun=" + id_subakun + ""
                    + "&acc_level=" + acc_level + ""
                    + "&id_dept=" + id_dept + "";
            reskode = ch.getdatadetails("dm/getkodeakun", param);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return reskode;
    }

    private void tutup() {
        pane.bbatal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog jd = (JDialog) pane.getRootPane().getParent();
                jd.dispose();
            }
        });

    }

    public class kelompokakun {

        String id_kelompok, nama_kelompok;

        public kelompokakun(String id_kelompok, String nama_kelompok) {
            this.id_kelompok = id_kelompok;
            this.nama_kelompok = nama_kelompok;
        }

        public String getId_kelompok() {
            return id_kelompok;
        }

        public void setId_kelompok(String id_kelompok) {
            this.id_kelompok = id_kelompok;
        }

        public String getNama_kelompok() {
            return nama_kelompok;
        }

        public void setNama_kelompok(String nama_kelompok) {
            this.nama_kelompok = nama_kelompok;
        }

    }

    public class subakun {

        String id_subakun, nama_subakun;

        public subakun(String id_subakun, String nama_subakun) {
            this.id_subakun = id_subakun;
            this.nama_subakun = nama_subakun;
        }

        public String getId_subakun() {
            return id_subakun;
        }

        public void setId_subakun(String id_subakun) {
            this.id_subakun = id_subakun;
        }

        public String getNama_subakun() {
            return nama_subakun;
        }

        public void setNama_subakun(String nama_subakun) {
            this.nama_subakun = nama_subakun;
        }

    }

    public class deptdata {

        String id_dept, nama_dept;

        public deptdata(String id_dept, String nama_dept) {
            this.id_dept = id_dept;
            this.nama_dept = nama_dept;
        }

        public String getId_dept() {
            return id_dept;
        }

        public void setId_dept(String id_dept) {
            this.id_dept = id_dept;
        }

        public String getNama_dept() {
            return nama_dept;
        }

        public void setNama_dept(String nama_dept) {
            this.nama_dept = nama_dept;
        }

    }

    public class currdata {

        String id_currency, nama_currency;

        public currdata(String id_currency, String nama_currency) {
            this.id_currency = id_currency;
            this.nama_currency = nama_currency;
        }

        public String getId_currency() {
            return id_currency;
        }

        public void setId_currency(String id_currency) {
            this.id_currency = id_currency;
        }

        public String getNama_currency() {
            return nama_currency;
        }

        public void setNama_currency(String nama_currency) {
            this.nama_currency = nama_currency;
        }

    }

}
