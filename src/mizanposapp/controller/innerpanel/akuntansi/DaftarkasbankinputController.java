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
import mizanposapp.view.innerpanel.akuntansi.Daftarkasbank_input_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Minami
 */
public class DaftarkasbankinputController {

    CrudHelper ch = new CrudHelper();
    Daftarkasbank_input_panel pane;
    NumberFormat nf = NumberFormat.getInstance();
    String id = "";

    public DaftarkasbankinputController(Daftarkasbank_input_panel pane) {
        this.pane = pane;
        id = Staticvar.ids;
        Staticvar.ids = "";
        loaddata();
        simpandata();
        tutup();
    }

    private void loaddata() {

        try {
            JSONParser jpdata = new JSONParser();
            String param = "id=" + id + "";
            Object rawobjdata = jpdata.parse(ch.getdatadetails("dm/databank", param));
            JSONArray ja = (JSONArray) rawobjdata;
            for (int i = 0; i < ja.size(); i++) {
                JSONObject jo = (JSONObject) ja.get(i);
                pane.ednorek.setText(String.valueOf(jo.get("nomor_rekening_bank")));
                pane.ednama.setText(String.valueOf(jo.get("atas_nama_rekening")));
                pane.edalamat.setText(String.valueOf(jo.get("alamat")));
                pane.edkantor_cabang.setText(String.valueOf(jo.get("kantor_cabang")));
                pane.edtelp.setText(String.valueOf(jo.get("telp")));
                pane.edfax.setText(String.valueOf(jo.get("fax")));
                pane.edkontak.setText(String.valueOf(jo.get("kontak")));
                pane.lheader.setText("Masukan Data Bank - " + String.valueOf(jo.get("nama_akun")));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void rawsimpan() {
        String param = "data_bank=akun='" + id + "'::"
                + "nomor_rekening_bank='" + pane.ednorek.getText() + "'::"
                + "atas_nama_rekening='" + pane.ednama.getText() + "'::"
                + "kantor_cabang='" + pane.edkantor_cabang.getText() + "'::"
                + "alamat='" + pane.edalamat.getText() + "'::"
                + "telp='" + pane.edtelp.getText() + "'::"
                + "fax='" + pane.edfax.getText() + "'::"
                + "kontak_person='" + pane.edkontak.getText() + "'";
        ch.updatedata("dm/updatedatabank", param, id);
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

    private void simpandata() {
        pane.bsimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Staticvar.isupdate = true;
                if (pane.ednorek.getText().equals("") || pane.ednama.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "No.Rek dan Nama tidak boleh kosong", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    rawsimpan();
                }
            }
        });
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

}
