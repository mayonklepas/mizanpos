/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.pengaturan;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Globalsession;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Errorpanel;
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.pengaturan.Pengaturan_inner_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class PengaturaninnerController {

    Pengaturan_inner_panel pane;
    CrudHelper ch = new CrudHelper();

    String kode = "kode";
    String kelompok = "kelompok";
    String suplier = "suplier";
    String order = "order";
    String keterangan = "keterangan";
    String pajak = "pajak";
    String gudang = "gudang";
    String diskon = "diskon";
    String satuan = "satuan";
    String merek = "merek";
    String lokasi = "lokasi";
    String harga_beli = "harga_beli";
    String harga_jual = "harga_jual";
    String hpp = "hpp";
    String service = "service";
    String stok = "stok";

    ArrayList listheaderpembelian = new ArrayList();
    ArrayList listheaderpenjualan = new ArrayList();
    ArrayList listheaderpersediaan = new ArrayList();

    Properties prop = new Properties();
    String valgudang = "", valdept = "", valpelanggan = "", valcurrency = "", validcompany = "", valopnameakunlain = "";

    public PengaturaninnerController(Pengaturan_inner_panel pane) {
        this.pane = pane;
        ButtonGroup bg = new ButtonGroup();
        bg.add(pane.rbgunakanakunhpppersediaan);
        bg.add(pane.rbgunakanakunlainnyapersediaan);
        loadjsonpembelian();
        loadjsonpenjualan();
        loadjsonpersediaan();
        loadsettingfromdb();
        loadconfigprop();
        kusimpankau();
        hidecontrol();
        cariakunstockopname();
        carigudang();
        caripelanggan();
        caridepartment();
        pane.kontrolserial.setVisible(false);
        pane.ptampildaftarpersediaan.setVisible(false);

    }

    private void hidecontrol() {
        pane.ckbarcodedenganserialbarangpos.setVisible(false);
        pane.ckbarisbarupos.setVisible(false);
        pane.ckotomatiscetakstrukpos.setVisible(false);
        pane.rbgunakanakunhpppersediaan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pane.lakunstockopnamepersediaan.setVisible(false);
                pane.ltitik2akunstockopnamepersediaan.setVisible(false);
                pane.edakunstockopnamepersediaan.setVisible(false);
                pane.bcariakunstockopnamepersediaan.setVisible(false);
            }
        });

        pane.rbgunakanakunlainnyapersediaan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pane.lakunstockopnamepersediaan.setVisible(true);
                pane.ltitik2akunstockopnamepersediaan.setVisible(true);
                pane.edakunstockopnamepersediaan.setVisible(true);
                pane.bcariakunstockopnamepersediaan.setVisible(true);
            }
        });

        pane.ckgunakanpembulatanpos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pane.ckgunakanpembulatanpos.isSelected()) {
                    pane.sppembulatanpos.setVisible(true);
                    pane.lpembulatanpos.setVisible(true);
                } else {
                    pane.sppembulatanpos.setVisible(false);
                    pane.lpembulatanpos.setVisible(false);
                }
            }
        });
    }

    private void loadjsonpembelian() {
        try {
            String dataheader = ch.getheaderinputs();
            JSONParser jp = new JSONParser();
            Object isijson = jp.parse(dataheader);
            JSONObject jo = (JSONObject) isijson;

            Object objfieldorderpembelian = jo.get("inputorderpembelian");
            Object objfieldfakturpembelian = jo.get("inputfakturpembelian");
            Object objfieldreturpembelian = jo.get("inputreturpembelian");

            JSONArray jafieldorderpembelian = (JSONArray) objfieldorderpembelian;
            for (int i = 0; i < jafieldorderpembelian.size(); i++) {
                JSONObject jofieldpembelian = (JSONObject) jafieldorderpembelian.get(i);
                JSONArray jajafieldpembelian = (JSONArray) jofieldpembelian.get("key");
                if (jajafieldpembelian.get(0).equals(keterangan)) {

                    if (jajafieldpembelian.get(2).equals("1")) {
                        pane.cktampilorderketeranganpembelian.setSelected(true);
                    } else {
                        pane.cktampilorderketeranganpembelian.setSelected(false);
                    }

                } else if (jajafieldpembelian.get(0).equals(pajak)) {
                    if (jajafieldpembelian.get(2).equals("1")) {
                        pane.cktampilorderpajakpembelian.setSelected(true);
                    } else {
                        pane.cktampilorderpajakpembelian.setSelected(false);
                    }

                } else if (jajafieldpembelian.get(0).equals(harga_jual)) {
                    if (jajafieldpembelian.get(2).equals("1")) {
                        pane.cktampilorderhargajualpembelian.setSelected(true);
                    } else {
                        pane.cktampilorderhargajualpembelian.setSelected(false);
                    }

                } else if (jajafieldpembelian.get(0).equals(gudang)) {
                    if (jajafieldpembelian.get(2).equals("1")) {
                        pane.cktampilordergudangpembelian.setSelected(true);
                    } else {
                        pane.cktampilordergudangpembelian.setSelected(false);
                    }

                }

            }

            JSONArray jafieldfakturpembelian = (JSONArray) objfieldfakturpembelian;
            for (int i = 0; i < jafieldfakturpembelian.size(); i++) {
                JSONObject jofieldpembelian = (JSONObject) jafieldfakturpembelian.get(i);
                JSONArray jajafieldpembelian = (JSONArray) jofieldpembelian.get("key");
                if (jajafieldpembelian.get(0).equals(order)) {

                    if (jajafieldpembelian.get(2).equals("1")) {
                        pane.cktampilfakturorderpembelian.setSelected(true);
                    } else {
                        pane.cktampilfakturorderpembelian.setSelected(false);
                    }

                } else if (jajafieldpembelian.get(0).equals(keterangan)) {

                    if (jajafieldpembelian.get(2).equals("1")) {
                        pane.cktampilfakturketeranganpembelian.setSelected(true);
                    } else {
                        pane.cktampilfakturketeranganpembelian.setSelected(false);
                    }

                } else if (jajafieldpembelian.get(0).equals(pajak)) {
                    if (jajafieldpembelian.get(2).equals("1")) {
                        pane.cktampilfakturpajakpembelian.setSelected(true);
                    } else {
                        pane.cktampilfakturpajakpembelian.setSelected(false);
                    }

                } else if (jajafieldpembelian.get(0).equals(harga_jual)) {
                    if (jajafieldpembelian.get(2).equals("1")) {
                        pane.cktampilfakturhargajualpembelian.setSelected(true);
                    } else {
                        pane.cktampilfakturhargajualpembelian.setSelected(false);
                    }

                } else if (jajafieldpembelian.get(0).equals(gudang)) {
                    if (jajafieldpembelian.get(2).equals("1")) {
                        pane.cktampilfakturgudangpembelian.setSelected(true);
                    } else {
                        pane.cktampilfakturgudangpembelian.setSelected(false);
                    }

                }

            }

            JSONArray jafieldreturpembelian = (JSONArray) objfieldreturpembelian;
            for (int i = 0; i < jafieldreturpembelian.size(); i++) {
                JSONObject jofieldpembelian = (JSONObject) jafieldreturpembelian.get(i);
                JSONArray jajafieldpembelian = (JSONArray) jofieldpembelian.get("key");
                if (jajafieldpembelian.get(0).equals(order)) {

                    if (jajafieldpembelian.get(2).equals("1")) {
                        pane.cktampilreturorderpembelian.setSelected(true);
                    } else {
                        pane.cktampilreturorderpembelian.setSelected(false);
                    }

                } else if (jajafieldpembelian.get(0).equals(keterangan)) {

                    if (jajafieldpembelian.get(2).equals("1")) {
                        pane.cktampilreturketeranganpembelian.setSelected(true);
                    } else {
                        pane.cktampilreturketeranganpembelian.setSelected(false);
                    }

                } else if (jajafieldpembelian.get(0).equals(pajak)) {
                    if (jajafieldpembelian.get(2).equals("1")) {
                        pane.cktampilreturpajakpembelian.setSelected(true);
                    } else {
                        pane.cktampilreturpajakpembelian.setSelected(false);
                    }

                } else if (jajafieldpembelian.get(0).equals(harga_jual)) {
                    if (jajafieldpembelian.get(2).equals("1")) {
                        pane.cktampilreturhargajualpembelian.setSelected(true);
                    } else {
                        pane.cktampilreturhargajualpembelian.setSelected(false);
                    }

                } else if (jajafieldpembelian.get(0).equals(gudang)) {
                    if (jajafieldpembelian.get(2).equals("1")) {
                        pane.cktampilreturgudangpembelian.setSelected(true);
                    } else {
                        pane.cktampilreturgudangpembelian.setSelected(false);
                    }

                }

            }

        } catch (ParseException ex) {
            Logger.getLogger(PengaturaninnerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadjsonpenjualan() {
        try {
            String dataheader = ch.getheaderinputs();
            JSONParser jp = new JSONParser();
            Object isijson = jp.parse(dataheader);
            JSONObject jo = (JSONObject) isijson;

            Object objfieldorderpenjualan = jo.get("inputorderpenjualan");
            Object objfieldfakturpenjualan = jo.get("inputfakturpenjualan");
            Object objfieldreturpenjualan = jo.get("inputreturpenjualan");

            JSONArray jafieldorderpenjualan = (JSONArray) objfieldorderpenjualan;
            for (int i = 0; i < jafieldorderpenjualan.size(); i++) {
                JSONObject jofieldpenjualan = (JSONObject) jafieldorderpenjualan.get(i);
                JSONArray jajafieldpenjualan = (JSONArray) jofieldpenjualan.get("key");
                if (jajafieldpenjualan.get(0).equals(keterangan)) {

                    if (jajafieldpenjualan.get(2).equals("1")) {
                        pane.cktampilorderketeranganpenjualan.setSelected(true);
                    } else {
                        pane.cktampilorderketeranganpenjualan.setSelected(false);
                    }

                } else if (jajafieldpenjualan.get(0).equals(pajak)) {
                    if (jajafieldpenjualan.get(2).equals("1")) {
                        pane.cktampilorderpajakpenjualan.setSelected(true);
                    } else {
                        pane.cktampilorderpajakpenjualan.setSelected(false);
                    }

                } else if (jajafieldpenjualan.get(0).equals(gudang)) {
                    if (jajafieldpenjualan.get(2).equals("1")) {
                        pane.cktampilordergudangpenjualan.setSelected(true);
                    } else {
                        pane.cktampilordergudangpenjualan.setSelected(false);
                    }

                } else if (jajafieldpenjualan.get(0).equals(stok)) {
                    if (jajafieldpenjualan.get(2).equals("1")) {
                        pane.cktampilorderstockpenjualan.setSelected(true);
                    } else {
                        pane.cktampilorderstockpenjualan.setSelected(false);
                    }

                }

            }

            JSONArray jafieldfakturpenjualan = (JSONArray) objfieldfakturpenjualan;
            for (int i = 0; i < jafieldfakturpenjualan.size(); i++) {
                JSONObject jofieldpenjualan = (JSONObject) jafieldfakturpenjualan.get(i);
                JSONArray jajafieldpenjualan = (JSONArray) jofieldpenjualan.get("key");
                if (jajafieldpenjualan.get(0).equals(order)) {

                    if (jajafieldpenjualan.get(2).equals("1")) {
                        pane.cktampilfakturorderpenjualan.setSelected(true);
                    } else {
                        pane.cktampilfakturorderpenjualan.setSelected(false);
                    }

                } else if (jajafieldpenjualan.get(0).equals(keterangan)) {

                    if (jajafieldpenjualan.get(2).equals("1")) {
                        pane.cktampilfakturketeranganpenjualan.setSelected(true);
                    } else {
                        pane.cktampilfakturketeranganpenjualan.setSelected(false);
                    }

                } else if (jajafieldpenjualan.get(0).equals(pajak)) {
                    if (jajafieldpenjualan.get(2).equals("1")) {
                        pane.cktampilfakturpajakpenjualan.setSelected(true);
                    } else {
                        pane.cktampilfakturpajakpenjualan.setSelected(false);
                    }

                } else if (jajafieldpenjualan.get(0).equals(gudang)) {
                    if (jajafieldpenjualan.get(2).equals("1")) {
                        pane.cktampilfakturgudangpenjualan.setSelected(true);
                    } else {
                        pane.cktampilfakturgudangpenjualan.setSelected(false);
                    }

                } else if (jajafieldpenjualan.get(0).equals(stok)) {
                    if (jajafieldpenjualan.get(2).equals("1")) {
                        pane.cktampilfakturstockpenjualan.setSelected(true);
                    } else {
                        pane.cktampilfakturstockpenjualan.setSelected(false);
                    }

                }

            }

            JSONArray jafieldreturpenjualan = (JSONArray) objfieldreturpenjualan;
            for (int i = 0; i < jafieldreturpenjualan.size(); i++) {
                JSONObject jofieldpenjualan = (JSONObject) jafieldreturpenjualan.get(i);
                JSONArray jajafieldpenjualan = (JSONArray) jofieldpenjualan.get("key");
                if (jajafieldpenjualan.get(0).equals(order)) {

                    if (jajafieldpenjualan.get(2).equals("1")) {
                        pane.cktampilreturorderpenjualan.setSelected(true);
                    } else {
                        pane.cktampilreturorderpenjualan.setSelected(false);
                    }

                } else if (jajafieldpenjualan.get(0).equals(keterangan)) {

                    if (jajafieldpenjualan.get(2).equals("1")) {
                        pane.cktampilreturketeranganpenjualan.setSelected(true);
                    } else {
                        pane.cktampilreturketeranganpenjualan.setSelected(false);
                    }

                } else if (jajafieldpenjualan.get(0).equals(pajak)) {
                    if (jajafieldpenjualan.get(2).equals("1")) {
                        pane.cktampilreturpajakpenjualan.setSelected(true);
                    } else {
                        pane.cktampilreturpajakpenjualan.setSelected(false);
                    }

                } else if (jajafieldpenjualan.get(0).equals(gudang)) {
                    if (jajafieldpenjualan.get(2).equals("1")) {
                        pane.cktampilreturgudangpenjualan.setSelected(true);
                    } else {
                        pane.cktampilreturgudangpenjualan.setSelected(false);
                    }

                } else if (jajafieldpenjualan.get(0).equals(stok)) {
                    if (jajafieldpenjualan.get(2).equals("1")) {
                        pane.cktampilreturstockpenjualan.setSelected(true);
                    } else {
                        pane.cktampilreturstockpenjualan.setSelected(false);
                    }

                }

            }

        } catch (ParseException ex) {
            Logger.getLogger(PengaturaninnerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadjsonpersediaan() {

        try {

            String dataheader = ch.getheaderinputs();
            JSONParser jp = new JSONParser();
            Object isijson = jp.parse(dataheader);
            JSONObject jo = (JSONObject) isijson;
            Object objfieldpersediaan = jo.get("persediaan");

            JSONArray jafieldpersediaan = (JSONArray) objfieldpersediaan;
            for (int i = 0; i < jafieldpersediaan.size(); i++) {
                JSONObject jofieldpersediaan = (JSONObject) jafieldpersediaan.get(i);
                JSONArray jajafieldpersediaan = (JSONArray) jofieldpersediaan.get("key");
                if (jajafieldpersediaan.get(0).equals(kode)) {

                    if (jajafieldpersediaan.get(2).equals("1")) {
                        pane.cktampilfieldkodepersediaan.setSelected(true);
                    } else {
                        pane.cktampilfieldkodepersediaan.setSelected(false);
                    }

                } else if (jajafieldpersediaan.get(0).equals(kelompok)) {

                    if (jajafieldpersediaan.get(2).equals("1")) {
                        pane.cktampilfieldkelompokpersediaan.setSelected(true);
                    } else {
                        pane.cktampilfieldkelompokpersediaan.setSelected(false);
                    }

                } else if (jajafieldpersediaan.get(0).equals(satuan)) {
                    if (jajafieldpersediaan.get(2).equals("1")) {
                        pane.cktampilfieldsatuanpersediaan.setSelected(true);
                    } else {
                        pane.cktampilfieldsatuanpersediaan.setSelected(false);
                    }

                } else if (jajafieldpersediaan.get(0).equals(suplier)) {
                    if (jajafieldpersediaan.get(2).equals("1")) {
                        pane.cktampilfieldsuplierpersediaan.setSelected(true);
                    } else {
                        pane.cktampilfieldsuplierpersediaan.setSelected(false);
                    }

                } else if (jajafieldpersediaan.get(0).equals(merek)) {
                    if (jajafieldpersediaan.get(2).equals("1")) {
                        pane.cktampilfieldmerekpersediaan.setSelected(true);
                    } else {
                        pane.cktampilfieldmerekpersediaan.setSelected(false);
                    }

                } else if (jajafieldpersediaan.get(0).equals(lokasi)) {
                    if (jajafieldpersediaan.get(2).equals("1")) {
                        pane.cktampilfieldlokasipersediaan.setSelected(true);
                    } else {
                        pane.cktampilfieldlokasipersediaan.setSelected(false);
                    }

                } else if (jajafieldpersediaan.get(0).equals(harga_beli)) {
                    if (jajafieldpersediaan.get(2).equals("1")) {
                        pane.cktampilfieldhargebeliterakhirpersediaan.setSelected(true);
                    } else {
                        pane.cktampilfieldhargebeliterakhirpersediaan.setSelected(false);
                    }

                } else if (jajafieldpersediaan.get(0).equals(harga_jual)) {
                    if (jajafieldpersediaan.get(2).equals("1")) {
                        pane.cktampilfieldhargajualpersediaan.setSelected(true);
                    } else {
                        pane.cktampilfieldhargajualpersediaan.setSelected(false);
                    }

                } else if (jajafieldpersediaan.get(0).equals(keterangan)) {
                    if (jajafieldpersediaan.get(2).equals("1")) {
                        pane.cktampilfieldketeranganpersediaan.setSelected(true);
                    } else {
                        pane.cktampilfieldketeranganpersediaan.setSelected(false);
                    }

                } else if (jajafieldpersediaan.get(0).equals(hpp)) {
                    if (jajafieldpersediaan.get(2).equals("1")) {
                        pane.cktampilfieldhpppersediaan.setSelected(true);
                    } else {
                        pane.cktampilfieldhpppersediaan.setSelected(false);
                    }

                }
            }

        } catch (ParseException ex) {
            Logger.getLogger(PengaturaninnerController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void loadconfigprop() {
        try {
            InputStream ins = new FileInputStream(new File("config.properties"));
            prop.load(ins);
            //setup umum
            String Setting_GudangDefaultnama = prop.getProperty("Setting_GudangDefaultnama");
            pane.edgudangdef.setText(Setting_GudangDefaultnama);
            String Setting_DeptDefaultnama = prop.getProperty("Setting_DeptDefaultnama");
            pane.eddefdept.setText(Setting_DeptDefaultnama);
            String Setting_GudangDefault = prop.getProperty("Setting_GudangDefault");
            valgudang = Setting_GudangDefault;
            String Setting_DeptDefault = prop.getProperty("Setting_DeptDefault");
            valdept = Setting_DeptDefault;
            String FilterDataPerDept = prop.getProperty("FilterDataPerDept");
            if (FilterDataPerDept.equals("1")) {
                pane.ckfilterdataperdepartment.setSelected(true);
            } else {
                pane.ckfilterdataperdepartment.setSelected(false);
            }
            String CetakFakturPilihFile = prop.getProperty("CetakFakturPilihFile");
            if (CetakFakturPilihFile.equals("1")) {
                pane.ckpilihformfaktur.setSelected(true);
            } else {
                pane.ckpilihformfaktur.setSelected(false);
            }

            //[PEMBELIAN]
            //[PENJUALAN]
            String Penjualan_PelangganUmumnama = prop.getProperty("Penjualan_PelangganUmumnama");
            pane.edpelangganpenjualan.setText(Penjualan_PelangganUmumnama);
            String Penjualan_PelangganUmum = prop.getProperty("Penjualan_PelangganUmum");
            valpelanggan = Penjualan_PelangganUmum;
            String Penjualan_FileFaktur = prop.getProperty("Penjualan_FileFaktur");
            pane.edfilefakturpenjualan.setText(Penjualan_FileFaktur);
            String Penjualan_FileRetur = prop.getProperty("Penjualan_FileRetur");
            pane.edfilereturpenjualan.setText(Penjualan_FileRetur);
            String Penjualan_Printer = prop.getProperty("Penjualan_Printer");

            String Penjualan_DotMatrixPrinter = prop.getProperty("Penjualan_DotMatrixPrinter");
            String Penjualan_HargaJualIncTaxService = prop.getProperty("Penjualan_HargaJualIncTaxService");
            //[POS]
            String POS_OtomatisCetakStruk = prop.getProperty("POS_OtomatisCetakStruk");
            if (POS_OtomatisCetakStruk.equals("1")) {
                pane.ckotomatiscetakstrukpos.setSelected(true);
            } else {
                pane.ckotomatiscetakstrukpos.setSelected(false);
            }
            String POS_FileStruk = prop.getProperty("POS_FileStruk");
            pane.edfiletruckpos.setText(POS_FileStruk);
            String POS_Printer = prop.getProperty("POS_Printer");
            pane.edprinterpos.setText(POS_Printer);
            String POS_DotMatrixPrinter = prop.getProperty("POS_DotMatrixPrinter");
            if (POS_DotMatrixPrinter.equals("1")) {
                pane.ckprinterdotmatrikpos.setSelected(true);
            } else {
                pane.ckprinterdotmatrikpos.setSelected(false);
            }
            String POS_ContinuousForm = prop.getProperty("POS_ContinuousForm");
            if (POS_ContinuousForm.equals("1")) {
                pane.ckprintercontinuouspos.setSelected(true);
            } else {
                pane.ckprintercontinuouspos.setSelected(false);
            }
            String POS_AutoCutter = prop.getProperty("POS_AutoCutter");
            if (POS_AutoCutter.equals("1")) {
                pane.ckprinterautocutterpos.setSelected(true);
            } else {
                pane.ckprinterautocutterpos.setSelected(false);
            }
            String POS_JarakFooter = prop.getProperty("POS_JarakFooter");
            pane.spjarakfooterpos.setValue(Integer.parseInt(POS_JarakFooter));
            String POS_UseCustomerDisplay = prop.getProperty("POS_UseCustomerDisplay");
            String POS_UseCashDrawerUSB = prop.getProperty("POS_UseCashDrawerUSB");
            if (POS_UseCashDrawerUSB.equals("1")) {
                pane.ckcashdrawerusbpos.setSelected(true);
            } else {
                pane.ckcashdrawerusbpos.setSelected(false);
            }
            String POS_ScanBarcodeBuatBaru = prop.getProperty("POS_ScanBarcodeBuatBaru");
            if (POS_ScanBarcodeBuatBaru.equals("1")) {
                pane.ckbarisbaruuntuksemuabarangpos.setSelected(true);
            } else {
                pane.ckbarisbaruuntuksemuabarangpos.setSelected(false);
            }

            //[EXPORTIMPORT]
            String ISAUTOEXPORT = prop.getProperty("ISAUTOEXPORT");
            String ISAUTOIMPORT = prop.getProperty("ISAUTOIMPORT");
            String ISLOCAL = prop.getProperty("ISLOCAL");
            String ISFTP = prop.getProperty("ISFTP");
            String FTPHOST = prop.getProperty("FTPHOST");
            String FTPUSER = prop.getProperty("FTPUSER");
            String FTPPASSWORD = prop.getProperty("FTPPASSWORD");
            String FTPPORT = prop.getProperty("FTPPORT");
            String EXPORTIMPORTSETIAP = prop.getProperty("EXPORTIMPORTSETIAP");
            String POS_Tinggi_Grid_Header = prop.getProperty("POS_Tinggi_Grid_Header");
            pane.sptinggilayoutheaderpos.setValue(Integer.parseInt(POS_Tinggi_Grid_Header));
            String POS_Tinggi_Grid_Item = prop.getProperty("POS_Tinggi_Grid_Item");
            pane.sptinggilayoutitempos.setValue(Integer.parseInt(POS_Tinggi_Grid_Item));
            String POS_Font_Grid_Header = prop.getProperty("POS_Font_Grid_Header");
            pane.spfontilayoutheaderpos.setValue(Integer.parseInt(POS_Font_Grid_Header));
            String POS_Font_Grid_Item = prop.getProperty("POS_Font_Grid_Item");
            pane.spfontilayoutitempos.setValue(Integer.parseInt(POS_Font_Grid_Item));
            String POS_Tinggi_Panel = prop.getProperty("POS_Tinggi_Panel");
            pane.sptinggipanelbawahpos.setValue(Integer.parseInt(POS_Tinggi_Panel));
            String POS_Lebar_Tombol = prop.getProperty("POS_Lebar_Tombol");
            pane.splebartombolbawahpos.setValue(Integer.parseInt(POS_Lebar_Tombol));
            String POS_Tinggi_Tombol = prop.getProperty("POS_Tinggi_Tombol");
            pane.sptinggitombollbawahpos.setValue(Integer.parseInt(POS_Tinggi_Tombol));
            String POS_Font_Tombol = prop.getProperty("POS_Font_Tombol");
            pane.spfonttombolbawahpos.setValue(Integer.parseInt(POS_Font_Tombol));

        } catch (FileNotFoundException ex) {
            Logger.getLogger(PengaturaninnerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PengaturaninnerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadsettingfromdb() {
        try {
            JSONParser jpdata = new JSONParser();
            Object objdata = jpdata.parse(ch.getdatas("datasetupprogram"));
            JSONObject jodata = (JSONObject) objdata;
            Object objsetup = jodata.get("setupprogram");
            JSONArray jasetup = (JSONArray) objsetup;
            for (int i = 0; i < jasetup.size(); i++) {
                JSONObject joindata = (JSONObject) jasetup.get(i);
                String nama = String.valueOf(joindata.get("nama"));
                String nilai = String.valueOf(joindata.get("nilai"));
                switch (nama) {
                    case "POS_BarcodeMenggunakanSerial":
                        if (nilai.equals("1")) {
                            pane.ckbarcodedenganserialbarangpos.setSelected(true);
                        } else {
                            pane.ckbarcodedenganserialbarangpos.setSelected(false);
                        }
                        break;
                    case "POS_BuatBarisBaru":
                        if (nilai.equals("1")) {
                            pane.ckbarisbarupos.setSelected(true);
                        } else {
                            pane.ckbarisbarupos.setSelected(false);
                        }
                        break;
                    case "POS_GunakanPembulatan":
                        if (nilai.equals("1")) {
                            pane.ckgunakanpembulatanpos.setSelected(true);
                        } else {
                            pane.ckgunakanpembulatanpos.setSelected(false);
                        }
                        break;
                    case "POS_HargaJualMinimum":
                        if (nilai.equals("1")) {
                            pane.ckhargajualtidakbolehkecildarimasterpos.setSelected(true);
                        } else {
                            pane.ckhargajualtidakbolehkecildarimasterpos.setSelected(false);
                        }
                        break;
                    case "POS_HarusMenggunakanSalesman":
                        if (nilai.equals("1")) {
                            pane.cktransaksiharussalesmanpos.setSelected(true);
                        } else {
                            pane.cktransaksiharussalesmanpos.setSelected(false);
                        }
                        break;
                    case "POS_PembulatanPer":
                        pane.sppembulatanpos.setValue(Integer.parseInt(nilai));
                        break;
                    case "POS_TampilkanPiutangTotal":
                        if (nilai.equals("1")) {
                            pane.cktampilpiutangpos.setSelected(true);
                        } else {
                            pane.cktampilpiutangpos.setSelected(false);
                        }
                        break;
                    case "POS_FooterStruk":
                        pane.tafooterpos.setText(nilai);
                        break;
                    case "Pembelian_EditFakturTanpaMutasi":
                        if (nilai.equals("1")) {
                            pane.ckfakturtanpapengecekanmutasipembelian.setSelected(true);
                        } else {
                            pane.ckfakturtanpapengecekanmutasipembelian.setSelected(false);
                        }
                        break;
                    case "Pembelian_HitungTotalBuatBarisBaru":
                        if (nilai.equals("1")) {
                            pane.ckhitungtotalbarispembelian.setSelected(true);
                        } else {
                            pane.ckhitungtotalbarispembelian.setSelected(false);
                        }
                        break;
                    case "Pembelian_MasukkanPajakKeHargaPokok":
                        if (nilai.equals("1")) {
                            pane.ckmasukanpajakpembelian.setSelected(true);
                        } else {
                            pane.ckmasukanpajakpembelian.setSelected(false);
                        }
                        break;
                    case "Pembelian_SetOrderSelesai":
                        if (nilai.equals("1")) {
                            pane.cksetselesaiorderpembelian.setSelected(true);
                        } else {
                            pane.cksetselesaiorderpembelian.setSelected(false);
                        }
                        break;

                    case "Penjualan_PembHutangPiutangSimple":
                        if (nilai.equals("1")) {
                            pane.ckpembayaranhutangpiutangformtotalpenjualan.setSelected(true);
                        } else {
                            pane.ckpembayaranhutangpiutangformtotalpenjualan.setSelected(false);
                        }
                        break;

                    case "Persediaan_BatasiPanjangSerial":
                        if (nilai.equals("1")) {
                            pane.ckbatasipenjangserialpersediaan.setSelected(true);
                        } else {
                            pane.ckbatasipenjangserialpersediaan.setSelected(false);
                        }
                        break;
                    case "Persediaan_CekStockSebelumSimpan":
                        if (nilai.equals("1")) {
                            pane.ckcekstocksebelumsimpanpersediaan.setSelected(true);
                        } else {
                            pane.ckcekstocksebelumsimpanpersediaan.setSelected(false);
                        }
                        break;
                    case "Persediaan_EdMenggunakanFIFO":
                        if (nilai.equals("1")) {
                            //pane.ckhpphargabeliterakhirpersediaan.setSelected(true);
                        } else {
                            //pane.ckhpphargabeliterakhirpersediaan.setSelected(false);
                        }
                        break;

                    case "Persediaan_HPPdariHargaBeli":
                        if (nilai.equals("1")) {
                            pane.ckhpphargabeliterakhirpersediaan.setSelected(true);
                        } else {
                            pane.ckhpphargabeliterakhirpersediaan.setSelected(false);
                        }
                        break;
                    case "Persediaan_OpnameGunakanAkunHPP":
                        if (nilai.equals("1")) {
                            pane.rbgunakanakunhpppersediaan.setSelected(true);
                        } else {
                            pane.rbgunakanakunhpppersediaan.setSelected(false);
                        }
                        break;
                    case "Persediaan_OpnameGunakanAkunLain":
                        if (nilai.equals("1")) {
                            pane.rbgunakanakunlainnyapersediaan.setSelected(true);
                        } else {
                            pane.rbgunakanakunlainnyapersediaan.setSelected(false);
                        }
                        break;
                    case "Persediaan_OpnameAkunLain":
                        valopnameakunlain = nilai.split("#")[0];
                        pane.edakunstockopnamepersediaan.setText(nilai.split("#")[0] + "-" + nilai.split("#")[1]);
                        break;
                    case "Persediaan_PanjangSerial":
                        if (nilai.equals("1")) {
                            pane.ckbatasipenjangserialpersediaan.setSelected(true);
                        } else {
                            pane.ckbatasipenjangserialpersediaan.setSelected(false);
                        }
                        break;
                    case "Persediaan_SerialIsDigit":
                        if (nilai.equals("1")) {
                            pane.ckserialhanyadigitpersediaan.setSelected(true);
                        } else {
                            pane.ckserialhanyadigitpersediaan.setSelected(false);
                        }
                        break;
                    case "Persediaan_SerialUnique":
                        if (nilai.equals("1")) {
                            pane.ckserialunikpersediaan.setSelected(true);
                        } else {
                            pane.ckserialunikpersediaan.setSelected(false);
                        }
                        break;
                    case "Penjualan_SetOrderSelesai":
                        if (nilai.equals("1")) {
                            pane.cksetselesaiorderpenjualan.setSelected(true);
                        } else {
                            pane.cksetselesaiorderpenjualan.setSelected(false);
                        }
                        break;
                    case "Penjualan_HargaJualIncTaxService":
                        if (nilai.equals("1")) {
                            pane.ckhargajualtermasukpajakdanservicepenjualan.setSelected(true);
                        } else {
                            pane.ckhargajualtermasukpajakdanservicepenjualan.setSelected(false);
                        }
                        break;
                    case "Global_DesimalDigit":
                        pane.spdesimaldigit.setValue(Integer.parseInt(nilai));
                        break;
                    case "Global_PemisahTanggal":
                        pane.edpemisahtanggal.setText(nilai);
                        break;
                    case "Global_LongDateFormat":
                        pane.edlongdateformat.setText(nilai);
                        break;
                    case "Global_ShortDateFormat":
                        pane.edshortdateformat.setText(nilai);
                        break;
                    default:
                        break;
                }
            }

            Object objprefix = jodata.get("prefix");
            JSONArray japrefix = (JSONArray) objprefix;
            for (int i = 0; i < japrefix.size(); i++) {
                JSONObject joindata = (JSONObject) japrefix.get(i);
                String nama = String.valueOf(joindata.get("nama"));
                String prefix = String.valueOf(joindata.get("prefix"));
                switch (nama) {
                    case "Jurnal Umum":
                        pane.edprejurnalumum.setText(prefix);
                        break;
                    case "Order Penjualan":
                        pane.edpreorderpenjualan.setText(prefix);
                        break;
                    case "Penjualan":
                        pane.edprefakturpenjualan.setText(prefix);
                        break;
                    case "Retur Penjualan":
                        pane.edprereturrpenjualan.setText(prefix);
                        break;
                    case "Pembayaran Piutang":
                        pane.edprepembayaranpiutang.setText(prefix);
                        break;
                    case "Order Pembelian":
                        pane.edpreorderpembelian.setText(prefix);
                        break;
                    case "Pembelian":
                        pane.edprefakturpembelian.setText(prefix);
                        break;
                    case "Retur Pembelian":
                        pane.edprereturpembelian.setText(prefix);
                        break;
                    case "Pembayaran Hutang":
                        pane.edprepembayaranhutang.setText(prefix);
                        break;
                    case "PenyesuaianPersediaan":
                        pane.edprepenyediapersediaan.setText(prefix);
                        break;
                    case "StockOpname":
                        pane.edprestockopname.setText(prefix);
                        break;
                    case "TransferPersediaan":
                        pane.edpretransferbarang.setText(prefix);
                        break;
                    case "PerakitanPersediaan":
                        pane.edpreperakitanbarang.setText(prefix);
                        break;
                    case "Kas Masuk":
                        pane.edprekasmasuk.setText(prefix);
                        break;
                    case "Kas Keluar":
                        pane.edprekaskeluar.setText(prefix);
                        break;
                    case "Data Pelanggan":
                        pane.edprepelanggan.setText(prefix);
                        break;
                    case "Data Karyawan":
                        pane.edprekaryawan.setText(prefix);
                        break;
                    case "Data Rekan":
                        pane.edprerekan.setText(prefix);
                        break;
                    case "Data Supplier":
                        pane.edpresupplier.setText(prefix);
                        break;
                    case "Data Barang":
                        pane.edprepersediaan.setText(prefix);
                        break;
                    case "Data Gudang":
                        pane.edpregudang.setText(prefix);
                        break;
                    case "Data Golongan Pelanggan":
                        pane.edpregolpelanggan.setText(prefix);
                        break;
                    case "Data Golongan Karyawan":
                        pane.edpregolkaryawan.setText(prefix);
                        break;
                    case "Data Kelompok Barang":
                        pane.edprekelompokpersediaan.setText(prefix);
                        break;
                    default:
                        break;
                }
            }

            if (pane.rbgunakanakunhpppersediaan.isSelected()) {
                pane.lakunstockopnamepersediaan.setVisible(false);
                pane.ltitik2akunstockopnamepersediaan.setVisible(false);
                pane.edakunstockopnamepersediaan.setVisible(false);
                pane.bcariakunstockopnamepersediaan.setVisible(false);
            } else {
                pane.lakunstockopnamepersediaan.setVisible(true);
                pane.ltitik2akunstockopnamepersediaan.setVisible(true);
                pane.edakunstockopnamepersediaan.setVisible(true);
                pane.bcariakunstockopnamepersediaan.setVisible(true);
            }

            if (pane.ckgunakanpembulatanpos.isSelected()) {
                pane.sppembulatanpos.setVisible(true);
                pane.lpembulatanpos.setVisible(true);
            } else {
                pane.sppembulatanpos.setVisible(false);
                pane.lpembulatanpos.setVisible(false);
            }

            Object objcompany = jodata.get("company");
            JSONArray jacompany = (JSONArray) objcompany;

            for (int i = 0; i < jacompany.size(); i++) {
                JSONObject joindata = (JSONObject) jacompany.get(i);
                validcompany = String.valueOf(joindata.get("id"));
                pane.ednamaperusahaan.setText(String.valueOf(joindata.get("company_name")));
                pane.edalamatperusahaan.setText(String.valueOf(joindata.get("alamat1")));
                pane.edalamat2perusahaan.setText(String.valueOf(joindata.get("alamat2")));
                pane.ednotelpperusahaan.setText(String.valueOf(joindata.get("telp")));
                pane.spnodeptperusahaan.setValue(Integer.parseInt(String.valueOf(joindata.get("id_dept"))));
                pane.ednamadeptperusahaan.setText(String.valueOf(joindata.get("nama_dept")));
                valcurrency = String.valueOf(joindata.get("id_dept"));
                pane.ednofaxperusahaan.setText(String.valueOf(joindata.get("fax")));
                pane.cmbbulanawalakuntansiperusahaan.setSelectedIndex(Integer.parseInt(String.valueOf(joindata.get("create_data_month"))) - 1);
                pane.sptahunawalakuntansiperusahaan.setValue(Integer.parseInt(String.valueOf(joindata.get("create_data_year"))));
                pane.cmbperiodeakuntansiperusahaan.setSelectedIndex(Integer.parseInt(String.valueOf(joindata.get("periode_month"))) - 1);
                pane.spperiodeakuntansiperusahaan.setValue(Integer.parseInt(String.valueOf(joindata.get("periode_year"))));
                pane.ednpwpfakturpajakperusahaan.setText(String.valueOf(joindata.get("npwp")));
                Date pkp_tanggal = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(joindata.get("pkp_tanggal")));
                pane.dtanggalpkpfakturpajakperusahaan.setDate(pkp_tanggal);
                pane.edalamatpkpfakturpajakperusahaan.setText(String.valueOf(joindata.get("pkp_alamat")));
                pane.edkotafakturpajakperusahaan.setText(String.valueOf(joindata.get("pkp_kota")));
                pane.edttdpajaknama.setText(String.valueOf(joindata.get("ttd_pajak_nama")));
                pane.edjabatanpenandatanganfakturpajakperusahaan.setText(String.valueOf(joindata.get("ttd_pajak_jabatan")));
                pane.edmatauangutama.setText(String.valueOf(joindata.get("nama_currency")));
                valcurrency = String.valueOf(joindata.get("id_currency"));

            }

        } catch (ParseException ex) {
            Logger.getLogger(PengaturaninnerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (java.text.ParseException ex) {
            Logger.getLogger(PengaturaninnerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void simpankedb() {
        String POS_BarcodeMenggunakanSerial,
             POS_BuatBarisBaru, POS_GunakanPembulatan,
             POS_HargaJualMinimum, POS_HarusMenggunakanSalesman,
             POS_TampilkanPiutangTotal, Pembelian_MasukkanPajakKeHargaPokok,
             Pembelian_EditFakturTanpaMutasi, Pembelian_HitungTotalBuatBarisBaru,
             Pembelian_SetOrderSelesai, Penjualan_PembHutangPiutangSimple, Penjualan_SetOrderSelesai,
             Penjualan_HargaJualIncTaxService, Persediaan_BatasiPanjangSerial,
             Persediaan_CekStockSebelumSimpan, Persediaan_EdMenggunakanFIFO, Persediaan_HPPdariHargaBeli,
             Persediaan_OpnameGunakanAkunHPP, Persediaan_OpnameGunakanAkunLain, Persediaan_OpnameAkunLain, Persediaan_PanjangSerial,
             Persediaan_SerialIsDigit, Persediaan_SerialUnique;

        if (pane.ckbarcodedenganserialbarangpos.isSelected()) {
            POS_BarcodeMenggunakanSerial = "1";
        } else {
            POS_BarcodeMenggunakanSerial = "0";
        }

        if (pane.ckbarisbarupos.isSelected()) {
            POS_BuatBarisBaru = "1";
        } else {
            POS_BuatBarisBaru = "0";
        }

        if (pane.ckgunakanpembulatanpos.isSelected()) {
            POS_GunakanPembulatan = "1";
        } else {
            POS_GunakanPembulatan = "0";
        }

        if (pane.ckhargajualtidakbolehkecildarimasterpos.isSelected()) {
            POS_HargaJualMinimum = "1";
        } else {
            POS_HargaJualMinimum = "0";
        }

        if (pane.cktransaksiharussalesmanpos.isSelected()) {
            POS_HarusMenggunakanSalesman = "1";
        } else {
            POS_HarusMenggunakanSalesman = "0";
        }

        if (pane.cktampilpiutangpos.isSelected()) {
            POS_TampilkanPiutangTotal = "1";
        } else {
            POS_TampilkanPiutangTotal = "0";
        }

        if (pane.ckmasukanpajakpembelian.isSelected()) {
            Pembelian_MasukkanPajakKeHargaPokok = "1";
        } else {
            Pembelian_MasukkanPajakKeHargaPokok = "0";
        }

        if (pane.cksetselesaiorderpembelian.isSelected()) {
            Pembelian_SetOrderSelesai = "1";
        } else {
            Pembelian_SetOrderSelesai = "0";
        }

        if (pane.ckhitungtotalbarispembelian.isSelected()) {
            Pembelian_HitungTotalBuatBarisBaru = "1";
        } else {
            Pembelian_HitungTotalBuatBarisBaru = "0";
        }

        if (pane.ckfakturtanpapengecekanmutasipembelian.isSelected()) {
            Pembelian_EditFakturTanpaMutasi = "1";
        } else {
            Pembelian_EditFakturTanpaMutasi = "0";
        }

        if (pane.cksetselesaiorderpenjualan.isSelected()) {
            Penjualan_SetOrderSelesai = "1";
        } else {
            Penjualan_SetOrderSelesai = "0";
        }

        if (pane.ckhargajualtermasukpajakdanservicepenjualan.isSelected()) {
            Penjualan_HargaJualIncTaxService = "1";
        } else {
            Penjualan_HargaJualIncTaxService = "0";
        }

        if (pane.ckpembayaranhutangpiutangformtotalpenjualan.isSelected()) {
            Penjualan_PembHutangPiutangSimple = "1";
        } else {
            Penjualan_PembHutangPiutangSimple = "0";
        }

        if (pane.ckbatasipenjangserialpersediaan.isSelected()) {
            Persediaan_BatasiPanjangSerial = "1";
        } else {
            Persediaan_BatasiPanjangSerial = "0";
        }

        if (pane.ckcekstocksebelumsimpanpersediaan.isSelected()) {
            Persediaan_CekStockSebelumSimpan = "1";
        } else {
            Persediaan_CekStockSebelumSimpan = "0";
        }

        if (pane.ckhpphargabeliterakhirpersediaan.isSelected()) {
            Persediaan_HPPdariHargaBeli = "1";
        } else {
            Persediaan_HPPdariHargaBeli = "0";
        }

        if (pane.rbgunakanakunhpppersediaan.isSelected()) {
            Persediaan_OpnameGunakanAkunHPP = "1";
            Persediaan_OpnameGunakanAkunLain = "0";
        } else {
            Persediaan_OpnameGunakanAkunHPP = "0";
            Persediaan_OpnameGunakanAkunLain = "1";
        }

        if (pane.ckserialhanyadigitpersediaan.isSelected()) {
            Persediaan_SerialIsDigit = "1";
        } else {
            Persediaan_SerialIsDigit = "0";
        }

        if (pane.ckserialunikpersediaan.isSelected()) {
            Persediaan_SerialUnique = "1";
        } else {
            Persediaan_SerialUnique = "0";
        }

        String data = "setupprogram="
             + "POS_BarcodeMenggunakanSerial='" + POS_BarcodeMenggunakanSerial + "'::"
             + "POS_BuatBarisBaru='" + POS_BuatBarisBaru + "'::"
             + "POS_GunakanPembulatan='" + POS_GunakanPembulatan + "'::"
             + "POS_HargaJualMinimum='" + POS_HargaJualMinimum + "'::"
             + "POS_HarusMenggunakanSalesman='" + POS_HarusMenggunakanSalesman + "'::"
             + "POS_TampilkanPiutangTotal='" + POS_TampilkanPiutangTotal + "'::"
             + "POS_PembulatanPer='" + pane.sppembulatanpos.getValue() + "'::"
             + "POS_FooterStruk='" + pane.tafooterpos.getText() + "'::"
             + "Pembelian_MasukkanPajakKeHargaPokok='" + Pembelian_MasukkanPajakKeHargaPokok + "'::"
             + "Pembelian_SetOrderSelesai='" + Pembelian_SetOrderSelesai + "'::"
             + "Pembelian_EditFakturTanpaMutasi='" + Pembelian_EditFakturTanpaMutasi + "'::"
             + "Pembelian_HitungTotalBuatBarisBaru='" + Pembelian_HitungTotalBuatBarisBaru + "'::"
             + "Penjualan_PembHutangPiutangSimple='" + Penjualan_PembHutangPiutangSimple + "'::"
             + "Penjualan_SetOrderSelesai='" + Penjualan_SetOrderSelesai + "'::"
             + "Penjualan_HargaJualIncTaxService='" + Penjualan_HargaJualIncTaxService + "'::"
             + "Persediaan_CekStockSebelumSimpan='" + Persediaan_CekStockSebelumSimpan + "'::"
             + "Persediaan_HPPdariHargaBeli='" + Persediaan_HPPdariHargaBeli + "'::"
             + "Persediaan_OpnameGunakanAkunHPP='" + Persediaan_OpnameGunakanAkunHPP + "'::"
             + "Persediaan_OpnameGunakanAkunLain='" + Persediaan_OpnameGunakanAkunLain + "'::"
             + "Persediaan_OpnameAkunLain='" + valopnameakunlain + "'::"
             + "Persediaan_BatasiPanjangSerial='" + Persediaan_BatasiPanjangSerial + "'::"
             + "Persediaan_SerialIsDigit='" + Persediaan_SerialIsDigit + "'::"
             + "Global_PemisahTanggal='" + pane.edpemisahtanggal.getText() + "'::"
             + "Global_LongDateFormat='" + pane.edlongdateformat.getText() + "'::"
             + "Global_ShortDateFormat='" + pane.edshortdateformat.getText() + "'::"
             + "Global_DesimalDigit='" + pane.spdesimaldigit.getValue() + "'&"
             + "prefix="
             + "Jurnal Umum='" + pane.edprejurnalumum.getText() + "'::"
             + "Penjualan='" + pane.edprefakturpenjualan.getText() + "'::"
             + "Retur Penjualan='" + pane.edprereturrpenjualan.getText() + "'::"
             + "Pembelian='" + pane.edprefakturpembelian.getText() + "'::"
             + "Retur Pembelian='" + pane.edprereturpembelian.getText() + "'::"
             + "Pembayaran Hutang='" + pane.edprepembayaranhutang.getText() + "'::"
             + "Pembayaran Piutang='" + pane.edprepembayaranpiutang.getText() + "'::"
             + "Order Penjualan='" + pane.edpreorderpenjualan.getText() + "'::"
             + "Order Pembelian='" + pane.edpreorderpembelian.getText() + "'::"
             + "PenyesuaianPersediaan='" + pane.edprepenyediapersediaan.getText() + "'::"
             + "StockOpname='" + pane.edprestockopname.getText() + "'::"
             + "TransferPersediaan='" + pane.edpretransferbarang.getText() + "'::"
             + "PerakitanPersediaan='" + pane.edpreperakitanbarang.getText() + "'::"
             + "Kas Masuk='" + pane.edprekasmasuk.getText() + "'::"
             + "Kas Keluar='" + pane.edprekaskeluar.getText() + "'::"
             + "Data Supplier='" + pane.edpresupplier.getText() + "'::"
             + "Data Karyawan='" + pane.edprekaryawan.getText() + "'::"
             + "Data Rekan='" + pane.edprerekan.getText() + "'::"
             + "Data Pelanggan='" + pane.edprepelanggan.getText() + "'::"
             + "Data Barang='" + pane.edprepersediaan.getText() + "'::"
             + "Data Gudang='" + pane.edpregudang.getText() + "'::"
             + "Data Kelompok Barang='" + pane.edprekelompokpersediaan.getText() + "'::"
             + "Data Golongan Karyawan='" + pane.edpregolkaryawan.getText() + "'::"
             + "Data Golongan Pelanggan='" + pane.edpregolpelanggan.getText() + "'&"
             + "company="
             + "company_name='" + pane.ednamaperusahaan.getText() + "'::"
             + "create_data_year='" + pane.sptahunawalakuntansiperusahaan.getValue() + "'::"
             + "create_data_month='" + (pane.cmbbulanawalakuntansiperusahaan.getSelectedIndex() + 1) + "'::"
             + "periode_year='" + pane.spperiodeakuntansiperusahaan.getValue() + "'::"
             + "periode_month='" + (pane.cmbperiodeakuntansiperusahaan.getSelectedIndex() + 1) + "'::"
             + "alamat1='" + pane.edalamatperusahaan.getText() + "'::"
             + "alamat2='" + pane.edalamat2perusahaan.getText() + "'::"
             + "telp='" + pane.ednotelpperusahaan.getText() + "'::"
             + "fax='" + pane.ednofaxperusahaan.getText() + "'::"
             + "kota=''::"
             + "kode_pos=''::"
             + "negara=''::"
             + "website=''::"
             + "npwp='" + pane.ednpwpfakturpajakperusahaan.getText() + "'::"
             + "pkp_tanggal='" + new SimpleDateFormat("yyyy-MM-dd").format(pane.dtanggalpkpfakturpajakperusahaan.getDate()) + "'::"
             + "pkp_alamat='" + pane.edalamatpkpfakturpajakperusahaan.getText() + "'::"
             + "pkp_kota='" + pane.edkotafakturpajakperusahaan.getText() + "'::"
             + "ttd_pajak_nama='" + pane.edttdpajaknama.getText() + "'::"
             + "ttd_pajak_jabatan='" + pane.edjabatanpenandatanganfakturpajakperusahaan.getText() + "'&"
             + "id=" + validcompany;
        ch.insertdata("insertdatasetupprogram", data);
        if (Staticvar.getresult.equals("berhasil")) {
            simpankefile();
            new Globalsession(Staticvar.id_user_aktif);
            JOptionPane.showMessageDialog(null, "Pengaturan Disimpan, Jika Tidak Ada perubahan Restart Aplikasi", "Informasi", JOptionPane.INFORMATION_MESSAGE);

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

    private void simpankeproperties() {

        try {
            OutputStream outs = new FileOutputStream(new File("config.properties"));
            //[SETUPUMUM]
            String Setting_GudangDefaultnama = pane.edgudangdef.getText();
            String Setting_DeptDefaultnama = pane.eddefdept.getText();
            String Setting_GudangDefault = valgudang;
            String Setting_DeptDefault = valdept;
            String FilterDataPerDept = "";
            if (pane.ckfilterdataperdepartment.isSelected()) {
                FilterDataPerDept = "1";
            } else {
                FilterDataPerDept = "0";
            }
            String CetakFakturPilihFile = "";
            if (pane.ckpilihformfaktur.isSelected()) {
                CetakFakturPilihFile = "1";
            } else {
                CetakFakturPilihFile = "0";
            }

            //[PEMBELIAN]
            //[PENJUALAN]
            String Penjualan_PelangganUmumnama = pane.edpelangganpenjualan.getText();
            String Penjualan_PelangganUmum = valpelanggan;
            String Penjualan_FileFaktur = pane.edfilefakturpenjualan.getText();
            String Penjualan_FileRetur = pane.edfilereturpenjualan.getText();
            String Penjualan_Printer = prop.getProperty("Penjualan_Printer");

            String Penjualan_DotMatrixPrinter = "";

            String Penjualan_HargaJualIncTaxService = "";
            //[POS]
            String POS_OtomatisCetakStruk = "";
            if (pane.ckotomatiscetakstrukpos.isSelected()) {
                POS_OtomatisCetakStruk = "1";
            } else {
                POS_OtomatisCetakStruk = "0";
            }
            String POS_FileStruk = pane.edfiletruckpos.getText();
            String POS_Printer = pane.edprinterpos.getText();
            String POS_DotMatrixPrinter = "";
            if (pane.ckprinterdotmatrikpos.isSelected()) {
                POS_DotMatrixPrinter = "1";
            } else {
                POS_DotMatrixPrinter = "0";
            }
            String POS_ContinuousForm = "";
            if (pane.ckprintercontinuouspos.isSelected()) {
                POS_ContinuousForm = "1";
            } else {
                POS_ContinuousForm = "0";
            }
            String POS_AutoCutter = "";
            if (pane.ckprinterautocutterpos.isSelected()) {
                POS_AutoCutter = "1";
            } else {
                POS_AutoCutter = "0";
            }
            String POS_JarakFooter = String.valueOf(pane.spjarakfooterpos.getValue());
            String POS_UseCustomerDisplay = "";
            String POS_UseCashDrawerUSB = "";
            if (pane.ckcashdrawerusbpos.isSelected()) {
                POS_UseCashDrawerUSB = "1";
            } else {
                POS_UseCashDrawerUSB = "0";
            }
            String POS_ScanBarcodeBuatBaru = "";
            if (pane.ckbarisbaruuntuksemuabarangpos.isSelected()) {
                POS_ScanBarcodeBuatBaru = "1";
            } else {
                POS_ScanBarcodeBuatBaru = "0";
            }
            String POS_Tinggi_Grid_Header = String.valueOf(pane.sptinggilayoutheaderpos.getValue());
            String POS_Tinggi_Grid_Item = String.valueOf(pane.sptinggilayoutitempos.getValue());
            String POS_Font_Grid_Header = String.valueOf(pane.spfontilayoutheaderpos.getValue());
            String POS_Font_Grid_Item = String.valueOf(pane.spfontilayoutitempos.getValue());
            String POS_Tinggi_Panel = String.valueOf(pane.sptinggipanelbawahpos.getValue());
            String POS_Lebar_Tombol = String.valueOf(pane.splebartombolbawahpos.getValue());
            String POS_Tinggi_Tombol = String.valueOf(pane.sptinggitombollbawahpos.getValue());
            String POS_Font_Tombol = String.valueOf(pane.spfonttombolbawahpos.getValue());

            prop.setProperty("Setting_GudangDefaultnama", Setting_GudangDefaultnama);
            prop.setProperty("Setting_DeptDefaultnama", Setting_DeptDefaultnama);
            prop.setProperty("Setting_GudangDefault", Setting_GudangDefault);
            prop.setProperty("Setting_DeptDefault", Setting_DeptDefault);
            prop.setProperty("FilterDataPerDept", FilterDataPerDept);
            prop.setProperty("CetakFakturPilihFile", CetakFakturPilihFile);
            prop.setProperty("Penjualan_PelangganUmumnama", Penjualan_PelangganUmumnama);
            prop.setProperty("Penjualan_PelangganUmum", Penjualan_PelangganUmum);
            prop.setProperty("Penjualan_FileFaktur", Penjualan_FileFaktur);
            prop.setProperty("Penjualan_FileRetur", Penjualan_FileRetur);
            prop.setProperty("Penjualan_Printer", Penjualan_Printer);
            prop.setProperty("Penjualan_DotMatrixPrinter", Penjualan_DotMatrixPrinter);
            prop.setProperty("Penjualan_HargaJualIncTaxService", Penjualan_HargaJualIncTaxService);
            prop.setProperty("POS_OtomatisCetakStruk", POS_OtomatisCetakStruk);
            prop.setProperty("POS_FileStruk", POS_FileStruk);

            prop.setProperty("POS_Printer", POS_Printer);
            prop.setProperty("POS_DotMatrixPrinter", POS_DotMatrixPrinter);
            prop.setProperty("POS_ContinuousForm", POS_ContinuousForm);
            prop.setProperty("POS_AutoCutter", POS_AutoCutter);
            prop.setProperty("POS_JarakFooter", POS_JarakFooter);
            prop.setProperty("POS_UseCustomerDisplay", POS_UseCustomerDisplay);
            prop.setProperty("POS_UseCashDrawerUSB", POS_UseCashDrawerUSB);
            prop.setProperty("POS_ScanBarcodeBuatBaru", POS_ScanBarcodeBuatBaru);
            prop.setProperty("POS_Tinggi_Grid_Header", POS_Tinggi_Grid_Header);
            prop.setProperty("POS_Tinggi_Grid_Item", POS_Tinggi_Grid_Item);
            prop.setProperty("POS_Font_Grid_Header", POS_Font_Grid_Header);
            prop.setProperty("POS_Font_Grid_Item", POS_Font_Grid_Item);
            prop.setProperty("POS_Tinggi_Panel", POS_Tinggi_Panel);
            prop.setProperty("POS_Lebar_Tombol", POS_Lebar_Tombol);
            prop.setProperty("POS_Tinggi_Tombol", POS_Tinggi_Tombol);
            prop.setProperty("POS_Font_Tombol", POS_Font_Tombol);

            prop.store(outs, null);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(PengaturaninnerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PengaturaninnerController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private JSONArray setjsonpersediaan() {
        JSONArray jaroot = new JSONArray();
        for (int i = 0; i < 14; i++) {
            JSONArray jainroot = new JSONArray();
            JSONObject joinroot = new JSONObject();
            String val = "";
            String head = "";
            String show = "";
            String size = "";
            switch (i) {
                case 0:
                    val = "id";
                    head = "ID";
                    show = "1";
                    size = "10";
                    break;
                case 1:
                    val = "kode";
                    head = "Kode";
                    if (pane.cktampilfieldkodepersediaan.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 2:
                    val = "nama";
                    head = "Nama";
                    show = "1";
                    size = "10";
                    break;
                case 3:
                    val = "kelompok";
                    head = "Kelompok";
                    if (pane.cktampilfieldkelompokpersediaan.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 4:
                    val = "merek";
                    head = "Merek";
                    if (pane.cktampilfieldmerekpersediaan.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 5:
                    val = "satuan";
                    head = "Satuan";
                    if (pane.cktampilfieldsatuanpersediaan.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 6:
                    val = "harga_jual";
                    head = "Harga Jual";
                    if (pane.cktampilfieldhargajualpersediaan.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 7:
                    val = "harga_beli";
                    head = "Harga Beli";
                    if (pane.cktampilfieldhargebeliterakhirpersediaan.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 8:
                    val = "stok";
                    head = "Stok";
                    show = "0";
                    size = "10";
                    break;
                case 9:
                    val = "lokasi";
                    head = "Lokasi";
                    if (pane.cktampilfieldlokasipersediaan.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 10:
                    val = "hpp";
                    head = "HPP";
                    if (pane.cktampilfieldhpppersediaan.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 11:
                    val = "suplier";
                    head = "Suplier";
                    if (pane.cktampilfieldsuplierpersediaan.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 12:
                    val = "keterangan";
                    head = "Keterangan";
                    if (pane.cktampilfieldketeranganpersediaan.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                default:
                    break;
            }

            jainroot.add(val);
            jainroot.add(head);
            jainroot.add(show);
            jainroot.add(size);
            joinroot.put("key", jainroot);
            jaroot.add(joinroot);

        }

        return jaroot;
    }

    private JSONArray setjsonorderpenjualan() {
        JSONArray jaroot = new JSONArray();
        for (int i = 0; i < 12; i++) {
            JSONArray jainroot = new JSONArray();
            JSONObject joinroot = new JSONObject();
            String val = "";
            String head = "";
            String show = "";
            String size = "";
            switch (i) {
                case 0:
                    val = "kode";
                    head = "Kode";
                    show = "1";
                    size = "10";
                    break;
                case 1:
                    val = "nama";
                    head = "Nama";
                    show = "1";
                    size = "10";
                    break;
                case 2:
                    val = "stok";
                    head = "Stok";
                    if (pane.cktampilorderstockpenjualan.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 3:
                    val = "jumlah";
                    head = "Jumlah";
                    show = "1";
                    size = "10";
                    break;
                case 4:
                    val = "satuan";
                    head = "Satuan";
                    show = "1";
                    size = "10";
                    break;
                case 5:
                    val = "harga_jual";
                    head = "Harga Jual";
                    show = "1";
                    size = "10";
                    break;
                case 6:
                    val = "disc_persen";
                    head = "Diskon %";
                    show = "1";
                    size = "10";
                    break;
                case 7:
                    val = "disc_rp";
                    head = "Diskon Rp";
                    show = "1";
                    size = "10";
                    break;
                case 8:
                    val = "pajak";
                    head = "Pajak";
                    if (pane.cktampilorderpajakpenjualan.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 9:
                    val = "gudang";
                    head = "Gudang";
                    if (pane.cktampilordergudangpenjualan.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 10:
                    val = "keterangan";
                    head = "Keterangan";
                    if (pane.cktampilorderketeranganpenjualan.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 11:
                    val = "total";
                    head = "Total";
                    show = "1";
                    size = "10";
                    break;

                default:
                    break;
            }

            jainroot.add(val);
            jainroot.add(head);
            jainroot.add(show);
            jainroot.add(size);
            joinroot.put("key", jainroot);
            jaroot.add(joinroot);

        }

        return jaroot;
    }

    private JSONArray setjsonfakturpenjualan() {
        JSONArray jaroot = new JSONArray();
        for (int i = 0; i < 13; i++) {
            JSONArray jainroot = new JSONArray();
            JSONObject joinroot = new JSONObject();
            String val = "";
            String head = "";
            String show = "";
            String size = "";
            switch (i) {
                case 0:
                    val = "kode";
                    head = "Kode";
                    show = "1";
                    size = "10";
                    break;
                case 1:
                    val = "nama";
                    head = "Nama";
                    show = "1";
                    size = "10";
                    break;
                case 2:
                    val = "stok";
                    head = "Stok";
                    if (pane.cktampilfakturstockpenjualan.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 3:
                    val = "order";
                    head = "Order";
                    if (pane.cktampilfakturorderpenjualan.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 4:
                    val = "jumlah";
                    head = "Jumlah";
                    show = "1";
                    size = "10";
                    break;
                case 5:
                    val = "satuan";
                    head = "Satuan";
                    show = "1";
                    size = "10";
                    break;
                case 6:
                    val = "harga_jual";
                    head = "Harga Jual";
                    show = "1";
                    size = "10";
                    break;
                case 7:
                    val = "disc_persen";
                    head = "Diskon %";
                    show = "1";
                    size = "10";
                    break;
                case 8:
                    val = "disc_rp";
                    head = "Diskon Rp";
                    show = "1";
                    size = "10";
                    break;
                case 9:
                    val = "pajak";
                    head = "Pajak";
                    if (pane.cktampilfakturpajakpenjualan.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 10:
                    val = "gudang";
                    head = "Gudang";
                    if (pane.cktampilfakturgudangpenjualan.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 11:
                    val = "keterangan";
                    head = "Keterangan";
                    if (pane.cktampilfakturketeranganpenjualan.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 12:
                    val = "total";
                    head = "Total";
                    show = "1";
                    size = "10";
                    break;

                default:
                    break;
            }

            jainroot.add(val);
            jainroot.add(head);
            jainroot.add(show);
            jainroot.add(size);
            joinroot.put("key", jainroot);
            jaroot.add(joinroot);

        }

        return jaroot;
    }

    private JSONArray setjsonreturpenjualan() {
        JSONArray jaroot = new JSONArray();
        for (int i = 0; i < 13; i++) {
            JSONArray jainroot = new JSONArray();
            JSONObject joinroot = new JSONObject();
            String val = "";
            String head = "";
            String show = "";
            String size = "";
            switch (i) {
                case 0:
                    val = "kode";
                    head = "Kode";
                    show = "1";
                    size = "10";
                    break;
                case 1:
                    val = "nama";
                    head = "Nama";
                    show = "1";
                    size = "10";
                    break;
                case 2:
                    val = "stok";
                    head = "Stok";
                    if (pane.cktampilreturstockpenjualan.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 3:
                    val = "order";
                    head = "Order";
                    if (pane.cktampilreturorderpenjualan.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 4:
                    val = "jumlah";
                    head = "Jumlah";
                    show = "1";
                    size = "10";
                    break;
                case 5:
                    val = "satuan";
                    head = "Satuan";
                    show = "1";
                    size = "10";
                    break;
                case 6:
                    val = "harga_jual";
                    head = "Harga Jual";
                    show = "1";
                    size = "10";
                    break;
                case 7:
                    val = "disc_persen";
                    head = "Diskon %";
                    show = "1";
                    size = "10";
                    break;
                case 8:
                    val = "disc_rp";
                    head = "Diskon Rp";
                    show = "1";
                    size = "10";
                    break;
                case 9:
                    val = "pajak";
                    head = "Pajak";
                    if (pane.cktampilreturpajakpenjualan.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 10:
                    val = "gudang";
                    head = "Gudang";
                    show = "1";
                    size = "10";
                    break;
                case 11:
                    val = "keterangan";
                    head = "Keterangan";
                    if (pane.cktampilreturketeranganpenjualan.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 12:
                    val = "total";
                    head = "Total";
                    show = "1";
                    size = "10";
                    break;

                default:
                    break;
            }

            jainroot.add(val);
            jainroot.add(head);
            jainroot.add(show);
            jainroot.add(size);
            joinroot.put("key", jainroot);
            jaroot.add(joinroot);

        }

        return jaroot;
    }

    private JSONArray setjsonorderpembelian() {
        JSONArray jaroot = new JSONArray();
        for (int i = 0; i < 12; i++) {
            JSONArray jainroot = new JSONArray();
            JSONObject joinroot = new JSONObject();
            String val = "";
            String head = "";
            String show = "";
            String size = "";
            switch (i) {
                case 0:
                    val = "kode";
                    head = "Kode";
                    show = "1";
                    size = "10";
                    break;
                case 1:
                    val = "nama";
                    head = "Nama";
                    show = "1";
                    size = "10";
                    break;
                case 2:
                    val = "jumlah";
                    head = "Jumlah";
                    show = "1";
                    size = "10";
                    break;
                case 3:
                    val = "satuan";
                    head = "Satuan";
                    show = "1";
                    size = "10";
                    break;
                case 4:
                    val = "harga_beli";
                    head = "Harga Beli";
                    show = "1";
                    size = "10";
                    break;
                case 5:
                    val = "harga_jual";
                    head = "Harga Jual";
                    if (pane.cktampilorderhargajualpembelian.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 6:
                    val = "disc_persen";
                    head = "Diskon %";
                    show = "1";
                    size = "10";
                    break;
                case 7:
                    val = "disc_rp";
                    head = "Diskon Rp";
                    show = "1";
                    size = "10";
                    break;
                case 8:
                    val = "pajak";
                    head = "Pajak";
                    if (pane.cktampilorderpajakpembelian.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 9:
                    val = "gudang";
                    head = "Gudang";
                    if (pane.cktampilordergudangpembelian.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 10:
                    val = "keterangan";
                    head = "Keterangan";
                    if (pane.cktampilorderketeranganpembelian.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 11:
                    val = "total";
                    head = "Total";
                    show = "1";
                    size = "10";
                    break;

                default:
                    break;
            }

            jainroot.add(val);
            jainroot.add(head);
            jainroot.add(show);
            jainroot.add(size);
            joinroot.put("key", jainroot);
            jaroot.add(joinroot);

        }

        return jaroot;
    }

    private JSONArray setjsonfakturpembelian() {
        JSONArray jaroot = new JSONArray();
        for (int i = 0; i < 13; i++) {
            JSONArray jainroot = new JSONArray();
            JSONObject joinroot = new JSONObject();
            String val = "";
            String head = "";
            String show = "";
            String size = "";
            switch (i) {
                case 0:
                    val = "kode";
                    head = "Kode";
                    show = "1";
                    size = "10";
                    break;
                case 1:
                    val = "nama";
                    head = "Nama";
                    show = "1";
                    size = "10";
                    break;
                case 2:
                    val = "order";
                    head = "Order";
                    if (pane.cktampilfakturorderpembelian.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 3:
                    val = "jumlah";
                    head = "Jumlah";
                    show = "1";
                    size = "10";
                    break;
                case 4:
                    val = "satuan";
                    head = "Satuan";
                    show = "1";
                    size = "10";
                    break;
                case 5:
                    val = "harga_beli";
                    head = "Harga Beli";
                    show = "1";
                    size = "10";
                    break;
                case 6:
                    val = "harga_jual";
                    head = "Harga Jual";
                    if (pane.cktampilorderhargajualpembelian.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 7:
                    val = "disc_persen";
                    head = "Diskon %";
                    show = "1";
                    size = "10";
                    break;
                case 8:
                    val = "disc_rp";
                    head = "Diskon Rp";
                    show = "1";
                    size = "10";
                    break;
                case 9:
                    val = "pajak";
                    head = "Pajak";
                    if (pane.cktampilfakturpajakpembelian.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 10:
                    val = "gudang";
                    head = "Gudang";
                    if (pane.cktampilfakturgudangpembelian.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 11:
                    val = "keterangan";
                    head = "Keterangan";
                    if (pane.cktampilfakturketeranganpembelian.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 12:
                    val = "total";
                    head = "Total";
                    show = "1";
                    size = "10";
                    break;

                default:
                    break;
            }

            jainroot.add(val);
            jainroot.add(head);
            jainroot.add(show);
            jainroot.add(size);
            joinroot.put("key", jainroot);
            jaroot.add(joinroot);

        }

        return jaroot;
    }

    private JSONArray setjsonreturpembelian() {
        JSONArray jaroot = new JSONArray();
        for (int i = 0; i < 13; i++) {
            JSONArray jainroot = new JSONArray();
            JSONObject joinroot = new JSONObject();
            String val = "";
            String head = "";
            String show = "";
            String size = "";
            switch (i) {
                case 0:
                    val = "kode";
                    head = "Kode";
                    show = "1";
                    size = "10";
                    break;
                case 1:
                    val = "nama";
                    head = "Nama";
                    show = "1";
                    size = "10";
                    break;
                case 2:
                    val = "order";
                    head = "Order";
                    if (pane.cktampilreturorderpembelian.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 3:
                    val = "jumlah";
                    head = "Jumlah";
                    show = "1";
                    size = "10";
                    break;
                case 4:
                    val = "satuan";
                    head = "Satuan";
                    show = "1";
                    size = "10";
                    break;
                case 5:
                    val = "harga_beli";
                    head = "Harga Beli";
                    show = "1";
                    size = "10";
                    break;
                case 6:
                    val = "harga_jual";
                    head = "Harga Jual";
                    if (pane.cktampilorderhargajualpembelian.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 7:
                    val = "disc_persen";
                    head = "Diskon %";
                    show = "1";
                    size = "10";
                    break;
                case 8:
                    val = "disc_rp";
                    head = "Diskon Rp";
                    show = "1";
                    size = "10";
                    break;
                case 9:
                    val = "pajak";
                    head = "Pajak";
                    if (pane.cktampilreturpajakpembelian.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 10:
                    val = "gudang";
                    head = "Gudang";
                    show = "1";
                    size = "10";
                    break;
                case 11:
                    val = "keterangan";
                    head = "Keterangan";
                    if (pane.cktampilreturketeranganpembelian.isSelected()) {
                        show = "1";
                    } else {
                        show = "0";
                    }
                    size = "10";
                    break;
                case 12:
                    val = "total";
                    head = "Total";
                    show = "1";
                    size = "10";
                    break;

                default:
                    break;
            }

            jainroot.add(val);
            jainroot.add(head);
            jainroot.add(show);
            jainroot.add(size);
            joinroot.put("key", jainroot);
            jaroot.add(joinroot);

        }

        return jaroot;
    }

    private void simpankefile() {
        try {
            simpankeproperties();
            JSONObject jsobj = new JSONObject();
            //penjualan
            jsobj.put("inputorderpenjualan", setjsonorderpenjualan());
            jsobj.put("inputfakturpenjualan", setjsonfakturpenjualan());
            jsobj.put("inputreturpenjualan", setjsonreturpenjualan());
            //pembelian
            jsobj.put("inputorderpembelian", setjsonorderpembelian());
            jsobj.put("inputfakturpembelian", setjsonfakturpembelian());
            jsobj.put("inputreturpembelian", setjsonreturpembelian());
            //persediaan
            jsobj.put("persediaan", setjsonpersediaan());

            FileWriter fw = new FileWriter("configtableinput.json");
            fw.write(jsobj.toJSONString());
            fw.flush();
        } catch (IOException ex) {
            Logger.getLogger(PengaturaninnerController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void kusimpankau() {
        pane.bsimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simpankedb();
            }
        });
    }

    private void carigudang() {
        pane.bcarigudang.addActionListener((ActionEvent e) -> {
            Staticvar.sfilter = "";
            Staticvar.preid = valgudang;
            Staticvar.prelabel = pane.edgudangdef.getText();
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("gudang", "popupdaftargudang", "Daftar Gudang"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valgudang = Staticvar.resid;
            pane.edgudangdef.setText(Staticvar.reslabel);
        });

    }

    private void caridepartment() {
        pane.bcarideptgudang.addActionListener((ActionEvent e) -> {
            Staticvar.sfilter = "";
            Staticvar.preid = valdept;
            Staticvar.prelabel = pane.eddefdept.getText();
            Staticvar.prevalue = pane.eddefdept.getText();
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("department", "popupdaftardept", "Daftar Department"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valdept = Staticvar.resid;
            pane.eddefdept.setText(Staticvar.resvalue);
        });

    }

    private void caripelanggan() {
        pane.bcaripelangganpenjualan.addActionListener((ActionEvent e) -> {
            Staticvar.sfilter = "";
            Staticvar.preid = valpelanggan;
            Staticvar.prelabel = pane.edpelangganpenjualan.getText();
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("nama", "popupdaftarnama?tipe=0", "Daftar Pelanggan"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valpelanggan = Staticvar.resid;
            pane.edpelangganpenjualan.setText(Staticvar.reslabel);
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

    private void cariakunstockopname() {
        pane.bcariakunstockopnamepersediaan.addActionListener((ActionEvent e) -> {
            rawgetidakun(pane.edakunstockopnamepersediaan.getText(), valopnameakunlain);
            if (!Staticvar.resid.equals(valopnameakunlain)) {
                valopnameakunlain = Staticvar.resid;
                String val = Staticvar.resid + "-" + Staticvar.reslabel;
                pane.edakunstockopnamepersediaan.setText(val);
            }
        });

    }

}
