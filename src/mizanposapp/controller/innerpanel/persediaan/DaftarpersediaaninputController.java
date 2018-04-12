/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.persediaan;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.table.DefaultTableModel;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Globalsession;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Errorpanel;
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.persediaan.Daftarpersediaan_input_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class DaftarpersediaaninputController {

    String id = "";
    CrudHelper ch = new CrudHelper();
    Daftarpersediaan_input_panel pane;
    String valkelompok, valsupplier, valmerek, valsatuan, valgudang, vallokasi, valdepartment,
            valpajakpenjualan, valpajakpembelian, valservice, metodehpp;

    public DaftarpersediaaninputController(Daftarpersediaan_input_panel pane) {
        this.pane = pane;
        loaddata();
        carikelompokpersediaan();
        carisupplier();
        carimerek();
        carisatuan();
        carilokasi();
        caridepartment();
        caripajakpembelian();
        caripajakpenjualan();
        carigudang();
        cariservice();
        simpandata();
        tutup();

    }

    private void carikelompokpersediaan() {
        pane.bcari_kelompok.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("kelompokbarang", "popupdaftarkelompokpersediaan", "Daftar Kelompok Persediaan"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valkelompok = Staticvar.resid;
            pane.edkelompok_persediaan.setText(Staticvar.reslabel);

            try {
                JSONParser jpdata = new JSONParser();
                String param = String.format("id=%s", valkelompok);
                Object objdata = jpdata.parse(ch.getdatadetails("dm/datakelompokbarang", param));
                JSONArray jadata = (JSONArray) objdata;
                for (int i = 0; i < jadata.size(); i++) {
                    JSONObject joindata = (JSONObject) jadata.get(i);
                    int mhppcek = Integer.parseInt(String.valueOf(joindata.get("metodehpp")));
                    if (mhppcek == 1) {
                        pane.rbfifo.setSelected(true);
                    } else if (mhppcek == 2) {
                        pane.rblifo.setSelected(true);
                    } else {
                        pane.rbaverage.setSelected(true);
                    }
                    pane.edsatuan_persediaan.setText(String.valueOf(joindata.get("nama_satuan")));
                    valsatuan = String.valueOf(joindata.get("id_satuan"));
                    pane.edlokasi_persediaan.setText(String.valueOf(joindata.get("nama_lokasi")));
                    vallokasi = String.valueOf(joindata.get("id_lokasi"));
                    pane.eddepartment_persediaan.setText(String.valueOf(joindata.get("nama_dept")));
                    valdepartment = String.valueOf(joindata.get("id_dept"));
                    pane.edgudang_persediaan.setText(String.valueOf(joindata.get("nama_gudang")));
                    valgudang = String.valueOf(joindata.get("id_gudang"));
                }
            } catch (ParseException ex) {
                Logger.getLogger(DaftarpersediaaninputController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

    }

    private void carisupplier() {
        pane.bcari_supplier.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("nama", "popupdaftarnama?tipe=1", "Daftar Supplier"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valsupplier = Staticvar.resid;
            pane.edsupplier_persediaan.setText(Staticvar.reslabel);

        });

    }

    private void carimerek() {
        pane.bcari_merek.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("merek", "popupdaftarmerekpersediaan", "Daftar Merek"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valmerek = Staticvar.resid;
            pane.edmerek_persedian.setText(Staticvar.reslabel);

        });

    }

    private void carisatuan() {
        pane.bcari_satuan.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("satuan", "popupdaftarsatuan", "Daftar Satuan"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valsatuan = Staticvar.resid;
            pane.edsatuan_persediaan.setText(Staticvar.reslabel);

        });

    }

    private void carilokasi() {
        pane.bcari_lokasi.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("lokasi", "popupdaftarlokasipersediaan", "Daftar Lokasi Persediaan"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            vallokasi = Staticvar.resid;
            pane.edlokasi_persediaan.setText(Staticvar.reslabel);

        });

    }

    private void caridepartment() {
        pane.bcari_department.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("department", "popupdaftardept", "Daftar Department"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valdepartment = Staticvar.resid;
            pane.eddepartment_persediaan.setText(Staticvar.reslabel);

        });

    }

    private void caripajakpenjualan() {
        pane.bcari_pajak_penjualan.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("pajak", "popupdaftarpajak", "Daftar Pajak Penjualan"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valpajakpenjualan = Staticvar.resid;
            pane.edpajak_penjualan.setText(Staticvar.reslabel);

        });

    }

    private void caripajakpembelian() {
        pane.bcari_pajak_pembelian.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("pajak", "popupdaftarpajak", "Daftar Pajak Pembelian"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valpajakpembelian = Staticvar.resid;
            pane.edpajak_pembelian.setText(Staticvar.reslabel);

        });

    }

    private void cariservice() {
        pane.bcari_service.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("service", "popupdaftarservice", "Daftar Service"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valservice = Staticvar.resid;
            pane.edservice.setText(Staticvar.reslabel);

        });

    }

    private void carigudang() {
        pane.bcari_gudang.addActionListener((ActionEvent e) -> {
            JDialog jd = new JDialog(new Mainmenu());
            jd.add(new Popupcari("gudang", "popupdaftargudang", "Daftar Gudang"));
            jd.pack();
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            jd.toFront();
            valgudang = Staticvar.resid;
            pane.edgudang_persediaan.setText(Staticvar.reslabel);

        });

    }

    private void loaddata() {
        try {
            id = Staticvar.ids;
            if (id.equals("")) {
                pane.ckaktif.setSelected(true);
                pane.edkode_persediaan.setText("");
                pane.ednama_persediaan.setText("");
                pane.edkelompok_persediaan.setText("");
                valkelompok = "";
                pane.edsupplier_persediaan.setText("");
                valsupplier = "";
                pane.edketerangan_persediaan.setText("");
                pane.edmerek_persedian.setText("");
                pane.edsatuan_persediaan.setText("");
                pane.edlokasi_persediaan.setText("");
                pane.eddepartment_persediaan.setText("");
                pane.edgudang_persediaan.setText("");
                pane.rbaverage.setSelected(true);
                pane.edpajak_penjualan.setText("");
                pane.edpajak_pembelian.setText("");
                pane.edservice.setText("");
                pane.edstock_minimal.setText("");
                pane.edharga_beli_akhir.setText("");
                pane.edharga_jual.setText("");
                pane.edharga_master.setText("");
                pane.edupharga_beli.setText("0");
                pane.ckharga_jual_persen.setSelected(true);

                DefaultTableModel dtmmultisatuan = new DefaultTableModel();
                dtmmultisatuan.addColumn("Satuan");
                dtmmultisatuan.addColumn("Kode Barcode");
                dtmmultisatuan.addColumn("Isi Persatuan");
                dtmmultisatuan.addColumn("Satuan Pengali");
                Object[] rowmultisatuan = new Object[4];
                dtmmultisatuan.addRow(rowmultisatuan);
                pane.tablemulti_satuan.setModel(dtmmultisatuan);

                DefaultTableModel dtmmultihargajual = new DefaultTableModel();
                dtmmultihargajual.addColumn("Golongan");
                dtmmultihargajual.addColumn("Dari");
                dtmmultihargajual.addColumn("Hingga");
                dtmmultihargajual.addColumn("Satuan");
                if (pane.ckharga_jual_persen.isSelected() == true) {
                    dtmmultihargajual.addColumn("Harga Jual %");
                } else {
                    dtmmultihargajual.addColumn("Harga Jual");
                }
                Object[] rowmultiharga = new Object[5];
                dtmmultihargajual.addRow(rowmultiharga);
                pane.tablemulti_harga_jual.setModel(dtmmultihargajual);

                DefaultTableModel dtmmultilokasi = new DefaultTableModel();
                pane.table_multilokasi.setModel(dtmmultilokasi);

            } else {
                JSONParser jpdata = new JSONParser();
                String param = String.format("id=%s", id);
                Object rawobjdata = jpdata.parse(ch.getdatadetails("dm/datapersediaan", param));
                JSONObject jsonobjdata = (JSONObject) rawobjdata;
                Object objdata = jsonobjdata.get("data");
                JSONArray jadata = (JSONArray) objdata;
                for (int i = 0; i < jadata.size(); i++) {
                    JSONObject joindata = (JSONObject) jadata.get(i);
                    metodehpp = String.valueOf(joindata.get("metodehpp"));
                    if (metodehpp.equals("1")) {
                        pane.rbfifo.setSelected(true);
                    } else if (metodehpp.equals("2")) {
                        pane.rblifo.setSelected(true);
                    } else {
                        pane.rbaverage.setSelected(true);
                    }
                    pane.ckaktif.setSelected(true);
                    pane.edkode_persediaan.setText(String.valueOf(joindata.get("kode")));
                    pane.ednama_persediaan.setText(String.valueOf(joindata.get("nama")));
                    pane.edkelompok_persediaan.setText(String.valueOf(joindata.get("nama_kelompok")));
                    valkelompok = String.valueOf(joindata.get("id_kelompok"));
                    pane.edsupplier_persediaan.setText(String.valueOf(joindata.get("nama_supplier")));
                    valsupplier = String.valueOf(joindata.get("id_supplier"));
                    pane.edketerangan_persediaan.setText(String.valueOf(joindata.get("keterangan")));
                    pane.edmerek_persedian.setText(String.valueOf(joindata.get("nama_merek")));
                    valmerek = String.valueOf(joindata.get("id_merek"));
                    pane.edsatuan_persediaan.setText(String.valueOf(joindata.get("nama_satuan")));
                    valsatuan = String.valueOf(joindata.get("id_satuan"));
                    pane.edlokasi_persediaan.setText(String.valueOf(joindata.get("nama_lokasi")));
                    vallokasi = String.valueOf(joindata.get("id_lokasi"));
                    pane.eddepartment_persediaan.setText(String.valueOf(joindata.get("nama_dept")));
                    valdepartment = String.valueOf(joindata.get("id_dept"));
                    pane.edgudang_persediaan.setText(String.valueOf(joindata.get("nama_gudang")));
                    valgudang = String.valueOf(joindata.get("id_gudang"));
                    pane.edpajak_penjualan.setText(String.valueOf(joindata.get("nama_pajak_jual")));
                    pane.edpajak_pembelian.setText(String.valueOf(joindata.get("nama_pajak_beli")));
                    pane.edservice.setText(String.valueOf(joindata.get("nama_service")));
                    pane.edstock_minimal.setText(String.valueOf(joindata.get("stok_minimum")));
                    pane.edharga_beli_akhir.setText(String.valueOf(joindata.get("harga_beli")));
                    pane.edharga_jual.setText(String.valueOf(joindata.get("harga_jual")));
                    pane.edharga_master.setText(String.valueOf(joindata.get("harga_master")));
                    pane.edupharga_beli.setText("0");
                    int iscek = Integer.parseInt(String.valueOf(joindata.get("isaktif")));
                    if (iscek == 1) {
                        pane.ckaktif.setSelected(true);
                    } else {
                        pane.ckaktif.setSelected(false);
                    }
                    int iscekhppsamadenganjual = Integer.parseInt(String.valueOf(joindata.get("ishppsamadenganhargajual")));
                    if (iscekhppsamadenganjual == 1) {
                        pane.ckhpp.setSelected(true);
                    } else {
                        pane.ckhpp.setSelected(false);
                    }
                }

                //multisatuan
                DefaultTableModel dtmmultisatuan = new DefaultTableModel();
                dtmmultisatuan.addColumn("ID");
                dtmmultisatuan.addColumn("ID INV");
                dtmmultisatuan.addColumn("ID Satuan");
                dtmmultisatuan.addColumn("ID Satuan Pengali");
                dtmmultisatuan.addColumn("Qty Satuan Pengali");
                dtmmultisatuan.addColumn("Satuan");
                dtmmultisatuan.addColumn("Kode Barcode");
                dtmmultisatuan.addColumn("Isi Persatuan");
                dtmmultisatuan.addColumn("Satuan Pengali");
                Object[] rowmultisatuan = new Object[9];
                Object objmultisatuan = jsonobjdata.get("datamultisatuan");
                System.out.println(objmultisatuan);
                JSONArray jamultisatuan = (JSONArray) objmultisatuan;
                for (int i = 0; i < jamultisatuan.size(); i++) {
                    JSONObject joinmultisatuan = (JSONObject) jamultisatuan.get(i);
                    rowmultisatuan[0] = String.valueOf(joinmultisatuan.get("id"));
                    rowmultisatuan[1] = String.valueOf(joinmultisatuan.get("id_inv"));
                    rowmultisatuan[2] = String.valueOf(joinmultisatuan.get("id_satuan"));
                    rowmultisatuan[3] = String.valueOf(joinmultisatuan.get("id_satuan_pengali"));
                    rowmultisatuan[4] = String.valueOf(joinmultisatuan.get("qty_satuan_pengali"));
                    rowmultisatuan[5] = String.valueOf(joinmultisatuan.get("kode_satuan"));
                    rowmultisatuan[6] = String.valueOf(joinmultisatuan.get("barcode"));
                    rowmultisatuan[7] = String.valueOf(joinmultisatuan.get("qty_satuan_pengali"));
                    rowmultisatuan[8] = String.valueOf(joinmultisatuan.get("kode_satuan_pengali"));

                    dtmmultisatuan.addRow(rowmultisatuan);
                }
                pane.tablemulti_satuan.setModel(dtmmultisatuan);

                //multiharga
                DefaultTableModel dtmmultihargajual = new DefaultTableModel();
                dtmmultihargajual.addColumn("Golongan");
                dtmmultihargajual.addColumn("Dari");
                dtmmultihargajual.addColumn("Hingga");
                dtmmultihargajual.addColumn("Satuan");
                if (pane.ckharga_jual_persen.isSelected() == true) {
                    dtmmultihargajual.addColumn("Harga Jual %");
                } else {
                    dtmmultihargajual.addColumn("Harga Jual");
                }
                Object[] rowmultiharga = new Object[5];
                Object objmultiharga = jsonobjdata.get("datamultiharga");
                System.out.println(objmultisatuan);
                JSONArray jamultiharga = (JSONArray) objmultiharga;
                for (int i = 0; i < jamultiharga.size(); i++) {
                    JSONObject joinmultiharga = (JSONObject) jamultisatuan.get(i);
                    rowmultiharga[0] = String.valueOf(joinmultiharga.get("id"));
                    rowmultiharga[1] = String.valueOf(joinmultiharga.get("id_inv"));
                    rowmultiharga[2] = String.valueOf(joinmultiharga.get("id_harga"));
                    rowmultiharga[3] = String.valueOf(joinmultiharga.get("id_harga_pengali"));
                    rowmultiharga[4] = String.valueOf(joinmultiharga.get("qty_harga_pengali"));
                    rowmultiharga[5] = String.valueOf(joinmultiharga.get("kode_harga"));
                    rowmultiharga[6] = String.valueOf(joinmultiharga.get("barcode"));
                    rowmultiharga[7] = String.valueOf(joinmultiharga.get("qty_harga_pengali"));
                    rowmultiharga[8] = String.valueOf(joinmultiharga.get("kode_harga_pengali"));

                    dtmmultihargajual.addRow(rowmultiharga);
                }
                pane.tablemulti_harga_jual.setModel(dtmmultihargajual);

                //multilokasi
                DefaultTableModel dtmmultilokasi = new DefaultTableModel();
                dtmmultilokasi.addColumn("Golongan");
                dtmmultilokasi.addColumn("Dari");
                dtmmultilokasi.addColumn("Hingga");
                dtmmultilokasi.addColumn("Satuan");
                Object[] rowmultilokasi = new Object[5];
                Object objmultilokasi = jsonobjdata.get("datamultilokasi");
                JSONArray jamultilokasi = (JSONArray) objmultilokasi;
                for (int i = 0; i < jamultilokasi.size(); i++) {
                    JSONObject joinmultilokasi = (JSONObject) jamultisatuan.get(i);
                    rowmultilokasi[0] = String.valueOf(joinmultilokasi.get("id"));
                    rowmultilokasi[1] = String.valueOf(joinmultilokasi.get("id_inv"));
                    rowmultilokasi[2] = String.valueOf(joinmultilokasi.get("id_lokasi"));
                    rowmultilokasi[3] = String.valueOf(joinmultilokasi.get("id_lokasi_pengali"));
                    rowmultilokasi[4] = String.valueOf(joinmultilokasi.get("qty_lokasi_pengali"));
                    rowmultilokasi[5] = String.valueOf(joinmultilokasi.get("kode_lokasi"));
                    rowmultilokasi[6] = String.valueOf(joinmultilokasi.get("barcode"));
                    rowmultilokasi[7] = String.valueOf(joinmultilokasi.get("qty_lokasi_pengali"));
                    rowmultilokasi[8] = String.valueOf(joinmultilokasi.get("kode_lokasi_pengali"));

                    dtmmultihargajual.addRow(rowmultilokasi);
                }
                pane.tablemulti_harga_jual.setModel(dtmmultilokasi);

            }
        } catch (ParseException ex) {
            Logger.getLogger(DaftarserviceinnerinputController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void simpandata() {
        pane.bsimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Staticvar.isupdate = true;
                String ckval = "";
                if (pane.ckaktif.isSelected() == true) {
                    ckval = "1";
                } else {
                    ckval = "0";
                }
                if (pane.rbfifo.isSelected() == true) {
                    metodehpp = "1";
                } else if (pane.rblifo.isSelected() == true) {
                    metodehpp = "2";
                } else {
                    metodehpp = "3";
                }
                if (id.equals("")) {
                    String data = String.format("data=kode='%s'::"
                            + "nama='%s'::"
                            + "id_kelompok='%s'::"
                            + "id_satuan='%s'::"
                            + "id_gudang='%s'::"
                            + "id_dept='%s'::"
                            + "metode_hpp='%s'::"
                            + "stok_minimum='%s'::"
                            + "harga_beli='%s'::"
                            + "harga_jual='%s'::"
                            + "harga_master='%s'::"
                            + "id_pajak_beli='%s'::"
                            + "id_pajak_jual='%s'::"
                            + "id_currency='%s'::"
                            + "gambar='%s'::"
                            + "isaktif='%s'::"
                            + "id_lokasi='%s'::"
                            + "id_merek='%s'::"
                            + "id_supplier='%s'::"
                            + "keterangan='%s'::"
                            + "ishargajualdarigol='%s'::"
                            + "ishargajualpersen='%s'::"
                            + "ishppsamadenganhargajual='%s'::"
                            + "id_service='%s'::"
                            + "hargajual_berdasar='%s'",
                            pane.edkode_persediaan.getText(),
                            pane.ednama_persediaan.getText(),
                            valkelompok,
                            valsatuan,
                            valgudang,
                            valdepartment,
                            metodehpp,
                            pane.edstock_minimal.getText(),
                            pane.edharga_beli_akhir.getText(),
                            pane.edharga_jual.getText(),
                            pane.edharga_master.getText(),
                            valpajakpembelian,
                            valpajakpenjualan,
                            "1-1",
                            "",
                            ckval,
                            vallokasi,
                            valmerek,
                            valsupplier,
                            pane.edketerangan_persediaan.getText(),
                            "0",
                            "0",
                            "0",
                            valservice,
                            "1");
                    System.out.println(data);
                    ch.insertdata("dm/insertpersediaan", data);
                    if (!Staticvar.getresult.equals("berhasil")) {
                        JDialog jd = new JDialog(new Mainmenu());
                        Errorpanel ep = new Errorpanel();
                        ep.ederror.setText(Staticvar.getresult);
                        jd.add(ep);
                        jd.pack();
                        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        jd.setLocationRelativeTo(null);
                        jd.setVisible(true);
                        jd.toFront();
                    } else {
                        JDialog jd = (JDialog) pane.getRootPane().getParent();
                        jd.dispose();
                    }
                } else {
                    String data = String.format("data=kode='%s'::"
                            + "nama='%s'::"
                            + "id_kelompok::"
                            + "id_satuan='%s'::"
                            + "id_gudang='%s'::"
                            + "id_dept::"
                            + "metode_hpp='%s'::"
                            + "stok_minimum='%s'::"
                            + "harga_beli::"
                            + "harga_jual='%s'::"
                            + "harga_master='%s'::"
                            + "id_pajak_beli='%s'::"
                            + "id_pajak_jual='%s'::"
                            + "id_currency='%s'::"
                            + "gambar='%s'::"
                            + "isaktif='%s'::"
                            + "id_lokasi='%s'::"
                            + "id_merek='%s'::"
                            + "id_supplier='%s'::"
                            + "keterangan='%s'::"
                            + "ishargajualdarigol='%s'::"
                            + "ishargajualpersen='%s'::"
                            + "ishppsamadenganhargajual='%s'::"
                            + "id_service='%s'::"
                            + "hargajual_berdasar='%s'",
                            pane.edkode_persediaan.getText(),
                            pane.ednama_persediaan.getText(),
                            valkelompok,
                            valsatuan,
                            valgudang,
                            valdepartment,
                            metodehpp,
                            pane.edstock_minimal.getText(),
                            pane.edharga_beli_akhir.getText(),
                            pane.edharga_jual.getText(),
                            pane.edharga_master.getText(),
                            valpajakpembelian,
                            valpajakpenjualan,
                            Globalsession.DEFAULT_CURRENCY_ID,
                            "",
                            ckval,
                            vallokasi,
                            valmerek,
                            valsupplier,
                            pane.edketerangan_persediaan,
                            "0", "0", "0",
                            valservice,
                            "1");
                    System.out.println(data);
                    ch.updatedata("dm/updatepersediaan", data, id);
                    if (!Staticvar.getresult.equals("berhasil")) {
                        JDialog jd = new JDialog(new Mainmenu());
                        Errorpanel ep = new Errorpanel();
                        ep.ederror.setText(Staticvar.getresult);
                        jd.add(ep);
                        jd.pack();
                        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        jd.setLocationRelativeTo(null);
                        jd.setVisible(true);
                        jd.toFront();
                    } else {
                        JDialog jd = (JDialog) pane.getRootPane().getParent();
                        jd.dispose();
                    }
                }
            }
        });
    }

    private void tutup() {
        pane.bbatal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Staticvar.isupdate = false;
                JDialog jd = (JDialog) pane.getRootPane().getParent();
                jd.dispose();
            }
        });
    }

    private void multisatuanedit() {

    }
}
