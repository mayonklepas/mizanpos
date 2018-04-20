/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.persediaan;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
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

    ArrayList<multisatuan> multisatuanlist = new ArrayList<>();
    ArrayList<multiharga> multihargalist = new ArrayList<>();
    ArrayList<multilokasi> multilokasilist = new ArrayList<>();
    String id = "";
    CrudHelper ch = new CrudHelper();
    Daftarpersediaan_input_panel pane;
    String valkelompok, valsupplier, valmerek, valsatuan, valgudang, vallokasi, valdepartment,
            valpajakpenjualan, valpajakpembelian, valservice, metodehpp;
    DefaultTableModel dtmmultisatuan = new DefaultTableModel();
    DefaultTableModel dtmmultihargajual = new DefaultTableModel();
    DefaultTableModel dtmmultilokasi = new DefaultTableModel();
    int col = 0;
    int row = 0;
    Object[] rowmultisatuan = new Object[4];
    Object[] rowmultiharga = new Object[6];
    Object[] rowmultilokasi = new Object[2];

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
        multisatuanedit();

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

                dtmmultisatuan.addColumn("Satuan");
                dtmmultisatuan.addColumn("Kode Barcode");
                dtmmultisatuan.addColumn("Isi Persatuan");
                dtmmultisatuan.addColumn("Satuan Pengali");
                multisatuanlist.add(new multisatuan("", "", "", "", "", "", "", ""));
                dtmmultisatuan.addRow(rowmultisatuan);
                pane.tablemulti_satuan.setModel(dtmmultisatuan);

                dtmmultihargajual.addColumn("Golongan");
                dtmmultihargajual.addColumn("Dari");
                dtmmultihargajual.addColumn("Hingga");
                dtmmultihargajual.addColumn("Satuan");
                dtmmultihargajual.addColumn("Harga Jual");
                dtmmultihargajual.addColumn("Harga Jual Persen");

                dtmmultihargajual.addRow(rowmultiharga);
                pane.tablemulti_harga_jual.setModel(dtmmultihargajual);

                dtmmultilokasi.addColumn("Nama Lokasi");
                dtmmultilokasi.addColumn("Nama Gudang");
                dtmmultilokasi.addRow(rowmultilokasi);
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
                dtmmultisatuan.addColumn("Satuan");
                dtmmultisatuan.addColumn("Kode Barcode");
                dtmmultisatuan.addColumn("Isi Persatuan");
                dtmmultisatuan.addColumn("Satuan Pengali");
                Object objmultisatuan = jsonobjdata.get("datamultisatuan");
                System.out.println(objmultisatuan);
                JSONArray jamultisatuan = (JSONArray) objmultisatuan;
                for (int i = 0; i < jamultisatuan.size(); i++) {
                    JSONObject joinmultisatuan = (JSONObject) jamultisatuan.get(i);
                    String id = String.valueOf(joinmultisatuan.get("id"));
                    String id_inv = String.valueOf(joinmultisatuan.get("id_inv"));
                    String id_satuan = String.valueOf(joinmultisatuan.get("id_satuan"));
                    String kode_satuan = String.valueOf(joinmultisatuan.get("kode_satuan"));
                    String barcode = String.valueOf(joinmultisatuan.get("barcode"));
                    String id_satuan_pengali = String.valueOf(joinmultisatuan.get("id_satuan_pengali"));
                    String kode_satuan_pengali = String.valueOf(joinmultisatuan.get("kode_satuan_pengali"));
                    String qty_satuan_pengali = String.valueOf(joinmultisatuan.get("qty_satuan_pengali"));
                    multisatuanlist.add(new multisatuan(id, id_inv, id_satuan, kode_satuan, barcode, id_satuan_pengali, kode_satuan_pengali, qty_satuan_pengali));
                }
                for (int i = 0; i < multisatuanlist.size(); i++) {
                    rowmultisatuan[0] = multisatuanlist.get(i).getKode_satuan();
                    rowmultisatuan[1] = multisatuanlist.get(i).getBarcode();
                    rowmultisatuan[2] = multisatuanlist.get(i).getQty_satuan_pengali();
                    rowmultisatuan[3] = multisatuanlist.get(i).getKode_satuan_pengali();
                    dtmmultisatuan.addRow(rowmultisatuan);
                }
                pane.tablemulti_satuan.setModel(dtmmultisatuan);

                //multiharga
                dtmmultihargajual.addColumn("Golongan");
                dtmmultihargajual.addColumn("Dari");
                dtmmultihargajual.addColumn("Hingga");
                dtmmultihargajual.addColumn("Satuan");
                dtmmultihargajual.addColumn("Harga Jual");
                dtmmultihargajual.addColumn("Harga Jual Persen");
                Object[] rowmultiharga = new Object[6];
                Object objmultiharga = jsonobjdata.get("datamultiharga");
                System.out.println(objmultisatuan);
                JSONArray jamultiharga = (JSONArray) objmultiharga;
                for (int i = 0; i < jamultiharga.size(); i++) {
                    JSONObject joinmultiharga = (JSONObject) jamultisatuan.get(i);

                    String id = String.valueOf(joinmultiharga.get("id"));
                    String id_inv = String.valueOf(joinmultiharga.get("id_inv"));
                    String id_golongan = String.valueOf(joinmultiharga.get("id_golongan"));
                    String kode_golongan = String.valueOf(joinmultiharga.get("kode_golongan"));
                    String dari = String.valueOf(joinmultiharga.get("dari"));
                    String hingga = String.valueOf(joinmultiharga.get("hingga"));
                    String id_satuan = String.valueOf(joinmultiharga.get("id_satuan"));
                    String kode_satuan = String.valueOf(joinmultiharga.get("kode_satuan"));
                    String harga_jual = String.valueOf(joinmultiharga.get("harga_jual"));
                    String harga_jual_persen = String.valueOf(joinmultiharga.get("harga_jual_persen"));
                    multihargalist.add(new multiharga(id, id_inv, id_golongan, kode_golongan, dari, hingga, id_satuan, kode_satuan, harga_jual, harga_jual_persen));

                }

                for (int i = 0; i < multihargalist.size(); i++) {
                    rowmultiharga[0] = multihargalist.get(i).getKode_golongan();
                    rowmultiharga[1] = multihargalist.get(i).getDari();
                    rowmultiharga[2] = multihargalist.get(i).getHingga();
                    rowmultiharga[3] = multihargalist.get(i).getKode_satuan();
                    rowmultiharga[4] = multihargalist.get(i).getHarga_jual();
                    rowmultiharga[5] = multihargalist.get(i).getHarga_jual_persen();
                    dtmmultihargajual.addRow(rowmultiharga);
                }
                pane.tablemulti_harga_jual.setModel(dtmmultihargajual);

                //multilokasi
                dtmmultilokasi.addColumn("Nama Lokasi");
                dtmmultilokasi.addColumn("Nama Gudang");
                Object[] rowmultilokasi = new Object[2];
                Object objmultilokasi = jsonobjdata.get("datamultilokasi");
                JSONArray jamultilokasi = (JSONArray) objmultilokasi;
                for (int i = 0; i < jamultilokasi.size(); i++) {
                    JSONObject joinmultilokasi = (JSONObject) jamultisatuan.get(i);
                    String id = String.valueOf(joinmultilokasi.get("id"));
                    String id_inv = String.valueOf(joinmultilokasi.get("id_inv"));
                    String id_lokasi = String.valueOf(joinmultilokasi.get("id_lokasi"));
                    String nama_lokasi = String.valueOf(joinmultilokasi.get("nama_lokasi"));
                    String id_gudang = String.valueOf(joinmultilokasi.get("id_gudang"));
                    String nama_gudang = String.valueOf(joinmultilokasi.get("nama_gudang"));
                    multilokasilist.add(new multilokasi(id, id_inv, id_lokasi, nama_lokasi, id_gudang, nama_gudang));
                }

                for (int i = 0; i < multilokasilist.size(); i++) {
                    rowmultilokasi[0] = multilokasilist.get(i).getNama_gudang();
                    rowmultilokasi[1] = multilokasilist.get(i).getNama_lokaksi();
                }

                pane.table_multilokasi.setModel(dtmmultilokasi);

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

        //pane.tablemulti_satuan.setDefaultEditor(Object.class, new Editortablemultisatuan());
        pane.tablemulti_satuan.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                col = e.getColumn();
            }

        });

        KeyAdapter keya = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int row = pane.tablemulti_satuan.getSelectedRow();
                if (e.getKeyCode() == KeyEvent.VK_F3) {
                    multisatuanlist.add(new multisatuan("", "", "", "", "", "", "", ""));
                    dtmmultisatuan.addRow(rowmultisatuan);
                } else if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    multisatuanlist.remove(row);
                    dtmmultisatuan.removeRow(row);
                    dtmmultisatuan.fireTableDataChanged();
                } else if (e.getKeyCode() == KeyEvent.VK_F4) {
                    for (int i = 0; i < multisatuanlist.size(); i++) {
                        System.out.println(multisatuanlist.get(i).getId_satuan());
                        System.out.println(multisatuanlist.get(i).getBarcode());
                        System.out.println(multisatuanlist.get(i).getQty_satuan_pengali());
                        System.out.println(multisatuanlist.get(i).getId_satuan_pengali());
                        System.out.println("----------------------------------------");
                    }
                } else {
                    if (col == 0) {
                        JDialog jd = new JDialog(new Mainmenu());
                        jd.add(new Popupcari("satuan", "popupdaftarsatuan", "Daftar Satuan"));
                        jd.pack();
                        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        jd.setLocationRelativeTo(null);
                        jd.setVisible(true);
                        jd.toFront();
                        multisatuanlist.get(row).setId_satuan(Staticvar.resid);
                        pane.tablemulti_satuan.setValueAt(Staticvar.reslabel, row, 0);
                        multisatuanlist.get(row).setId_satuan_pengali(valsatuan);
                        pane.tablemulti_satuan.setValueAt(pane.edsatuan_persediaan.getText(), row, 3);
                        dtmmultisatuan.fireTableCellUpdated(row, 0);
                        dtmmultisatuan.fireTableCellUpdated(row, 3);
                    } else if (col == 1) {
                        multisatuanlist.get(row).setBarcode(String.valueOf(pane.tablemulti_satuan.getValueAt(row, 1)));
                    } else if (col == 2) {
                        multisatuanlist.get(row).setQty_satuan_pengali(String.valueOf(pane.tablemulti_satuan.getValueAt(row, 2)));
                    } else if (col == 3) {
                        JDialog jd = new JDialog(new Mainmenu());
                        jd.add(new Popupcari("satuan", "popupdaftarsatuan", "Daftar Satuan"));
                        jd.pack();
                        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        jd.setLocationRelativeTo(null);
                        jd.setVisible(true);
                        jd.toFront();
                        multisatuanlist.get(row).setId_satuan_pengali(Staticvar.resid);
                        pane.tablemulti_satuan.setValueAt(Staticvar.reslabel, row, 3);
                        dtmmultisatuan.fireTableCellUpdated(row, 3);
                    }

                    String id_satuan_check = multisatuanlist.get(row).getId_satuan();
                    String barcode_check = multisatuanlist.get(row).getBarcode();
                    String qty_check = multisatuanlist.get(row).getQty_satuan_pengali();
                    int jumlah_row = pane.tablemulti_satuan.getRowCount() - 1;
                    String last_id_satuan_check = multisatuanlist.get(jumlah_row).getId_satuan();
                    String last_barcode_check = multisatuanlist.get(jumlah_row).getBarcode();
                    String last_qty_check = multisatuanlist.get(jumlah_row).getQty_satuan_pengali();
                    if (!id_satuan_check.equals("") && !barcode_check.equals("") && !qty_check.equals("")) {
                        if (!last_id_satuan_check.equals("") && !last_barcode_check.equals("") && !last_qty_check.equals("")) {
                            multisatuanlist.add(new multisatuan("", "", "", "", "", "", "", ""));
                            dtmmultisatuan.addRow(rowmultisatuan);
                        }
                    }
                }
            }

        };
        pane.tablemulti_satuan.addKeyListener(keya);

    }

    public class multisatuan {

        String id, id_inv, id_satuan, kode_satuan, barcode, id_satuan_pengali, kode_satuan_pengali, qty_satuan_pengali;

        public multisatuan() {

        }

        public multisatuan(String id, String id_inv, String id_satuan, String kode_satuan, String barcode, String id_satuan_pengali, String kode_satuan_pengali, String qty_satuan_pengali) {
            this.id = id;
            this.id_inv = id_inv;
            this.id_satuan = id_satuan;
            this.kode_satuan = kode_satuan;
            this.barcode = barcode;
            this.id_satuan_pengali = id_satuan_pengali;
            this.kode_satuan_pengali = kode_satuan_pengali;
            this.qty_satuan_pengali = qty_satuan_pengali;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId_inv() {
            return id_inv;
        }

        public void setId_inv(String id_inv) {
            this.id_inv = id_inv;
        }

        public String getId_satuan() {
            return id_satuan;
        }

        public void setId_satuan(String id_satuan) {
            this.id_satuan = id_satuan;
        }

        public String getKode_satuan() {
            return kode_satuan;
        }

        public void setKode_satuan(String kode_satuan) {
            this.kode_satuan = kode_satuan;
        }

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }

        public String getId_satuan_pengali() {
            return id_satuan_pengali;
        }

        public void setId_satuan_pengali(String id_satuan_pengali) {
            this.id_satuan_pengali = id_satuan_pengali;
        }

        public String getKode_satuan_pengali() {
            return kode_satuan_pengali;
        }

        public void setKode_satuan_pengali(String kode_satuan_pengali) {
            this.kode_satuan_pengali = kode_satuan_pengali;
        }

        public String getQty_satuan_pengali() {
            return qty_satuan_pengali;
        }

        public void setQty_satuan_pengali(String qty_satuan_pengali) {
            this.qty_satuan_pengali = qty_satuan_pengali;
        }

    }

    public class multiharga {

        String id, id_inv, id_golongan, kode_golongan, dari, hingga, id_satuan, kode_satuan, harga_jual, harga_jual_persen;

        public multiharga(String id, String id_inv, String id_golongan, String kode_golongan, String dari, String hingga, String id_satuan, String kode_satuan, String harga_jual, String harga_jual_persen) {
            this.id = id;
            this.id_inv = id_inv;
            this.id_golongan = id_golongan;
            this.kode_golongan = kode_golongan;
            this.dari = dari;
            this.hingga = hingga;
            this.id_satuan = id_satuan;
            this.kode_satuan = kode_satuan;
            this.harga_jual = harga_jual;
            this.harga_jual_persen = harga_jual_persen;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId_inv() {
            return id_inv;
        }

        public void setId_inv(String id_inv) {
            this.id_inv = id_inv;
        }

        public String getId_golongan() {
            return id_golongan;
        }

        public void setId_golongan(String id_golongan) {
            this.id_golongan = id_golongan;
        }

        public String getKode_golongan() {
            return kode_golongan;
        }

        public void setKode_golongan(String kode_golongan) {
            this.kode_golongan = kode_golongan;
        }

        public String getDari() {
            return dari;
        }

        public void setDari(String dari) {
            this.dari = dari;
        }

        public String getHingga() {
            return hingga;
        }

        public void setHingga(String hingga) {
            this.hingga = hingga;
        }

        public String getId_satuan() {
            return id_satuan;
        }

        public void setId_satuan(String id_satuan) {
            this.id_satuan = id_satuan;
        }

        public String getKode_satuan() {
            return kode_satuan;
        }

        public void setKode_satuan(String kode_satuan) {
            this.kode_satuan = kode_satuan;
        }

        public String getHarga_jual() {
            return harga_jual;
        }

        public void setHarga_jual(String harga_jual) {
            this.harga_jual = harga_jual;
        }

        public String getHarga_jual_persen() {
            return harga_jual_persen;
        }

        public void setHarga_jual_persen(String harga_jual_persen) {
            this.harga_jual_persen = harga_jual_persen;
        }

    }

    public class multilokasi {

        String id, id_inv, id_lokasi, nama_lokaksi, id_gudang, nama_gudang;

        public multilokasi(String id, String id_inv, String id_lokasi, String nama_lokaksi, String id_gudang, String nama_gudang) {
            this.id = id;
            this.id_inv = id_inv;
            this.id_lokasi = id_lokasi;
            this.nama_lokaksi = nama_lokaksi;
            this.id_gudang = id_gudang;
            this.nama_gudang = nama_gudang;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId_inv() {
            return id_inv;
        }

        public void setId_inv(String id_inv) {
            this.id_inv = id_inv;
        }

        public String getId_lokasi() {
            return id_lokasi;
        }

        public void setId_lokasi(String id_lokasi) {
            this.id_lokasi = id_lokasi;
        }

        public String getNama_lokaksi() {
            return nama_lokaksi;
        }

        public void setNama_lokaksi(String nama_lokaksi) {
            this.nama_lokaksi = nama_lokaksi;
        }

        public String getId_gudang() {
            return id_gudang;
        }

        public void setId_gudang(String id_gudang) {
            this.id_gudang = id_gudang;
        }

        public String getNama_gudang() {
            return nama_gudang;
        }

        public void setNama_gudang(String nama_gudang) {
            this.nama_gudang = nama_gudang;
        }

    }

}
