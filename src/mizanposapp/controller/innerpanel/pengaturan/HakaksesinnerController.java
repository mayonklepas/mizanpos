/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.pengaturan;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Globalsession;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Errorpanel;
import mizanposapp.view.innerpanel.pengaturan.Hak_akses_inner_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class HakaksesinnerController {

    Hak_akses_inner_panel pane;
    CrudHelper ch = new CrudHelper();
    String ids = "";
    String urlget = "";
    String urlsave = "";

    public HakaksesinnerController(Hak_akses_inner_panel pane) {
        ids = Staticvar.ids;
        Staticvar.ids = "";
        if (Staticvar.labels.equals("")) {
            urlget = "dm/datapenggunalevel";
            urlsave = "dm/updatepenggunalevel";
        } else {
            urlget = "dm/datapengguna";
            urlsave = "dm/updatepenggunahakakses";
        }
        this.pane = pane;
        //disabletab();
        loaddata();
        checkcontrolpenjualan();
        checkcontrolpembelian();
        checkcontrolakuntansi();
        checkcontrolpersediaan();
        simpandata();
        tutup();

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
    }

    private void disabletab() {
        pane.tab_hak_akses.setEnabledAt(0, false);
        pane.tab_hak_akses.setEnabledAt(1, false);
        pane.tab_hak_akses.setEnabledAt(2, false);
        pane.tab_hak_akses.setEnabledAt(3, false);
        pane.tab_hak_akses.setEnabledAt(4, false);
        pane.tab_hak_akses.setEnabledAt(5, false);
        pane.tab_hak_akses.setEnabledAt(6, false);
        pane.tab_hak_akses.setEnabledAt(7, false);

    }

    private void loaddata() {
        if (ids.equals("")) {
            autocheckcontrol();
        } else {
            try {
                JSONParser jpdata = new JSONParser();
                String param = "id=" + ids;
                Object objdata = jpdata.parse(ch.getdatadetails(urlget, param));
                JSONArray jasetup = (JSONArray) objdata;
                for (int i = 0; i < jasetup.size(); i++) {
                    JSONObject jo = (JSONObject) jasetup.get(i);
                    String id = String.valueOf(jo.get("id"));
                    String nama = String.valueOf(jo.get("nama"));
                    pane.ednama_hak_akses.setText(nama);
                    String keterangan = String.valueOf(jo.get("keterangan"));
                    pane.taketerangan_hak_akses.setText(keterangan);
                    String dm_saldo_awal_akun = String.valueOf(jo.get("dm_saldo_awal_akun"));
                    if (dm_saldo_awal_akun.equals("1")) {
                        pane.cksaldo_awal_akun.setSelected(true);
                    } else {
                        pane.cksaldo_awal_akun.setSelected(false);
                    }
                    String dm_saldo_awal_persediaan = String.valueOf(jo.get("dm_saldo_awal_persediaan"));
                    if (dm_saldo_awal_persediaan.equals("1")) {
                        pane.cksaldo_awal_persediaan.setSelected(true);
                    } else {
                        pane.cksaldo_awal_persediaan.setSelected(false);
                    }
                    String dm_saldo_awal_hutang = String.valueOf(jo.get("dm_saldo_awal_hutang"));
                    if (dm_saldo_awal_hutang.equals("1")) {
                        pane.cksaldo_awal_hutang_usaha.setSelected(true);
                    } else {
                        pane.cksaldo_awal_hutang_usaha.setSelected(false);
                    }
                    String dm_saldo_awal_piutang = String.valueOf(jo.get("dm_saldo_awal_piutang"));
                    if (dm_saldo_awal_piutang.equals("1")) {
                        pane.cksaldo_awal_piutang_usaha.setSelected(true);
                    } else {
                        pane.cksaldo_awal_piutang_usaha.setSelected(false);
                    }
                    String dm_baru_edit_hapus = String.valueOf(jo.get("dm_baru_edit_hapus"));
                    if (dm_baru_edit_hapus.equals("1")) {
                        pane.cksaldo_buat_baru_edit_hapus.setSelected(true);
                    } else {
                        pane.cksaldo_buat_baru_edit_hapus.setSelected(false);
                    }
                    String dm_limitasi_gudang = String.valueOf(jo.get("dm_limitasi_gudang"));
                    if (dm_limitasi_gudang.equals("1")) {
                        pane.ckmaster_limitasi_gudang.setSelected(true);
                    } else {
                        pane.ckmaster_limitasi_gudang.setSelected(false);
                    }
                    String dm_limitasi_dept = String.valueOf(jo.get("dm_limitasi_dept"));
                    if (dm_limitasi_dept.equals("1")) {
                        pane.ckmaster_limitasi_departement.setSelected(true);
                    } else {
                        pane.ckmaster_limitasi_departement.setSelected(false);
                    }
                    String sistem_setup_program = String.valueOf(jo.get("sistem_setup_program"));
                    if (sistem_setup_program.equals("1")) {
                        pane.cksistem_setup_program.setSelected(true);
                    } else {
                        pane.cksistem_setup_program.setSelected(false);
                    }
                    String sistem_backup_data = String.valueOf(jo.get("sistem_backup_data"));
                    if (sistem_backup_data.equals("1")) {
                        pane.cksistem_bakcup_data.setSelected(true);
                    } else {
                        pane.cksistem_bakcup_data.setSelected(false);
                    }
                    String sistem_setting_pengguna = String.valueOf(jo.get("sistem_setting_pengguna"));
                    if (sistem_setting_pengguna.equals("1")) {
                        pane.cksistem_setting_pengguna.setSelected(true);
                    } else {
                        pane.cksistem_setting_pengguna.setSelected(false);
                    }
                    String sistem_sql_editor = String.valueOf(jo.get("sistem_sql_editor"));
                    if (sistem_sql_editor.equals("1")) {
                        pane.cksistem_buka_sql_editor.setSelected(true);
                    } else {
                        pane.cksistem_buka_sql_editor.setSelected(false);
                    }
                    String penjualan_order = String.valueOf(jo.get("penjualan_order"));
                    if (penjualan_order.equals("1")) {
                        pane.ckpenjualan_order.setSelected(true);
                        pane.ckpenjualan_input_order.setEnabled(true);
                        pane.ckpenjualan_edit_hapus_order.setEnabled(true);
                    } else {
                        pane.ckpenjualan_order.setSelected(false);
                        pane.ckpenjualan_input_order.setEnabled(false);
                        pane.ckpenjualan_edit_hapus_order.setEnabled(false);
                    }
                    String penjualan_order_input = String.valueOf(jo.get("penjualan_order_input"));
                    if (penjualan_order_input.equals("1")) {
                        pane.ckpenjualan_input_order.setSelected(true);
                    } else {
                        pane.ckpenjualan_input_order.setSelected(false);
                    }
                    String penjualan_order_edit = String.valueOf(jo.get("penjualan_order_edit"));
                    if (penjualan_order_edit.equals("1")) {
                        pane.ckpenjualan_edit_hapus_order.setSelected(true);
                    } else {
                        pane.ckpenjualan_edit_hapus_order.setSelected(false);
                    }
                    String penjualan_faktur = String.valueOf(jo.get("penjualan_faktur"));
                    if (penjualan_faktur.equals("1")) {
                        pane.ckpenjualan_faktur.setSelected(true);
                        pane.ckpenjualan_faktur_secara_kredit.setEnabled(true);
                        pane.ckpenjualan_input_faktur.setEnabled(true);
                        pane.ckpenjualan_edit_hapus_faktur.setEnabled(true);
                        pane.ckpenjualan_tarik_order_dari_faktur.setEnabled(true);
                        pane.ckpenjualan_rubah_harga_jual_faktur.setEnabled(true);
                    } else {
                        pane.ckpenjualan_faktur.setSelected(false);
                        pane.ckpenjualan_faktur_secara_kredit.setEnabled(false);
                        pane.ckpenjualan_input_faktur.setEnabled(false);
                        pane.ckpenjualan_edit_hapus_faktur.setEnabled(false);
                        pane.ckpenjualan_tarik_order_dari_faktur.setEnabled(false);
                        pane.ckpenjualan_rubah_harga_jual_faktur.setEnabled(false);
                    }
                    String penjualan_faktur_input = String.valueOf(jo.get("penjualan_faktur_input"));
                    if (penjualan_faktur_input.equals("1")) {
                        pane.ckpenjualan_input_faktur.setSelected(true);
                    } else {
                        pane.ckpenjualan_input_faktur.setSelected(false);
                    }
                    String penjualan_faktur_edit = String.valueOf(jo.get("penjualan_faktur_edit"));
                    if (penjualan_faktur_edit.equals("1")) {
                        pane.ckpenjualan_edit_hapus_faktur.setSelected(true);
                    } else {
                        pane.ckpenjualan_edit_hapus_faktur.setSelected(false);
                    }
                    String penjualan_faktur_kredit = String.valueOf(jo.get("penjualan_faktur_kredit"));
                    if (penjualan_faktur_kredit.equals("1")) {
                        pane.ckpenjualan_faktur_secara_kredit.setSelected(true);
                    } else {
                        pane.ckpenjualan_faktur_secara_kredit.setSelected(false);
                    }
                    String penjualan_faktur_tarik_order = String.valueOf(jo.get("penjualan_faktur_tarik_order"));
                    if (penjualan_faktur_tarik_order.equals("1")) {
                        pane.ckpenjualan_tarik_order_dari_faktur.setSelected(true);
                    } else {
                        pane.ckpenjualan_tarik_order_dari_faktur.setSelected(false);
                    }
                    String penjualan_faktur_rubah_harga = String.valueOf(jo.get("penjualan_faktur_rubah_harga"));
                    if (penjualan_faktur_rubah_harga.equals("1")) {
                        pane.ckpenjualan_rubah_harga_jual_faktur.setSelected(true);

                    } else {
                        pane.ckpenjualan_rubah_harga_jual_faktur.setSelected(false);

                    }

                    String penjualan_retur = String.valueOf(jo.get("penjualan_retur"));
                    if (penjualan_retur.equals("1")) {
                        pane.ckpenjualan_retur.setSelected(true);
                        pane.ckpenjualan_input_retur.setEnabled(true);
                        pane.ckpenjualan_edit_hapus_retur.setEnabled(true);
                        pane.ckpenjualan_secara_kredit_retur.setEnabled(true);
                    } else {
                        pane.ckpenjualan_retur.setSelected(false);
                        pane.ckpenjualan_input_retur.setEnabled(false);
                        pane.ckpenjualan_edit_hapus_retur.setEnabled(false);
                        pane.ckpenjualan_secara_kredit_retur.setEnabled(false);
                    }

                    String penjualan_retur_input = String.valueOf(jo.get("penjualan_retur_input"));
                    if (penjualan_retur_input.equals("1")) {
                        pane.ckpenjualan_input_retur.setSelected(true);
                    } else {
                        pane.ckpenjualan_input_retur.setSelected(false);
                    }
                    String penjualan_retur_edit = String.valueOf(jo.get("penjualan_retur_edit"));
                    if (penjualan_retur_edit.equals("1")) {
                        pane.ckpenjualan_edit_hapus_retur.setSelected(true);
                    } else {
                        pane.ckpenjualan_edit_hapus_retur.setSelected(false);
                    }
                    String penjualan_retur_kredit = String.valueOf(jo.get("penjualan_retur_kredit"));
                    if (penjualan_retur_kredit.equals("1")) {
                        pane.ckpenjualan_secara_kredit_retur.setSelected(true);
                    } else {
                        pane.ckpenjualan_secara_kredit_retur.setSelected(false);
                    }
                    String penjualan_piutang = String.valueOf(jo.get("penjualan_piutang"));
                    if (penjualan_piutang.equals("1")) {
                        pane.ckpenjualan_piutang_usaha.setSelected(true);
                        pane.ckpenjualan_input_pembayaran_piutang_usaha.setEnabled(true);
                        pane.ckpenjualan_edit_hapus_pembayaran_piutang.setEnabled(true);
                        pane.ckpenjualan_hapus_hutang_tak_tertagih.setEnabled(true);
                    } else {
                        pane.ckpenjualan_piutang_usaha.setSelected(false);
                        pane.ckpenjualan_input_pembayaran_piutang_usaha.setEnabled(false);
                        pane.ckpenjualan_edit_hapus_pembayaran_piutang.setEnabled(false);
                        pane.ckpenjualan_hapus_hutang_tak_tertagih.setEnabled(false);
                    }
                    String penjualan_piutang_input = String.valueOf(jo.get("penjualan_piutang_input"));
                    if (penjualan_piutang_input.equals("1")) {
                        pane.ckpenjualan_input_pembayaran_piutang_usaha.setSelected(true);
                    } else {
                        pane.ckpenjualan_input_pembayaran_piutang_usaha.setSelected(false);
                    }
                    String penjualan_piutang_edit = String.valueOf(jo.get("penjualan_piutang_edit"));
                    if (penjualan_piutang_edit.equals("1")) {
                        pane.ckpenjualan_edit_hapus_pembayaran_piutang.setSelected(true);
                    } else {
                        pane.ckpenjualan_edit_hapus_pembayaran_piutang.setSelected(false);
                    }
                    String penjualan_piutang_writeoff = String.valueOf(jo.get("penjualan_piutang_writeoff"));
                    if (penjualan_piutang_writeoff.equals("1")) {
                        pane.ckpenjualan_hapus_hutang_tak_tertagih.setSelected(true);
                    } else {
                        pane.ckpenjualan_hapus_hutang_tak_tertagih.setSelected(false);
                    }
                    String pembelian_order = String.valueOf(jo.get("pembelian_order"));
                    if (pembelian_order.equals("1")) {
                        pane.ckpembelian_order.setSelected(true);
                        pane.ckpembelian_input_order.setEnabled(true);
                        pane.ckpembelian_edit_hapus_order.setEnabled(true);
                    } else {
                        pane.ckpembelian_order.setSelected(false);
                        pane.ckpembelian_input_order.setEnabled(false);
                        pane.ckpembelian_edit_hapus_order.setEnabled(false);
                    }
                    String pembelian_order_input = String.valueOf(jo.get("pembelian_order_input"));
                    if (pembelian_order_input.equals("1")) {
                        pane.ckpembelian_input_order.setSelected(true);
                    } else {
                        pane.ckpembelian_input_order.setSelected(false);
                    }
                    String pembelian_order_edit = String.valueOf(jo.get("pembelian_order_edit"));
                    if (pembelian_order_edit.equals("1")) {
                        pane.ckpembelian_edit_hapus_order.setSelected(true);
                    } else {
                        pane.ckpembelian_edit_hapus_order.setSelected(false);
                    }
                    String pembelian_faktur = String.valueOf(jo.get("pembelian_faktur"));
                    if (pembelian_faktur.equals("1")) {
                        pane.ckpembelian_faktur.setSelected(true);
                        pane.ckpembelian_input_faktur.setEnabled(true);
                        pane.ckpembelian_edit_hapus_faktur.setEnabled(true);
                        pane.ckpembelian_faktur_secara_kredit.setEnabled(true);
                        pane.ckpembelian_tarik_order_dari_faktur.setEnabled(true);

                    } else {
                        pane.ckpembelian_faktur.setSelected(false);
                        pane.ckpembelian_input_faktur.setEnabled(false);
                        pane.ckpembelian_edit_hapus_faktur.setEnabled(false);
                        pane.ckpembelian_faktur_secara_kredit.setEnabled(false);
                        pane.ckpembelian_tarik_order_dari_faktur.setEnabled(false);
                    }
                    String pembelian_faktur_input = String.valueOf(jo.get("pembelian_faktur_input"));
                    if (pembelian_faktur_input.equals("1")) {
                        pane.ckpembelian_input_faktur.setSelected(true);
                    } else {
                        pane.ckpembelian_input_faktur.setSelected(false);
                    }
                    String pembelian_faktur_edit = String.valueOf(jo.get("pembelian_faktur_edit"));
                    if (pembelian_faktur_edit.equals("1")) {
                        pane.ckpembelian_edit_hapus_faktur.setSelected(true);
                    } else {
                        pane.ckpembelian_edit_hapus_faktur.setSelected(false);
                    }
                    String pembelian_faktur_kredit = String.valueOf(jo.get("pembelian_faktur_kredit"));
                    if (pembelian_faktur_kredit.equals("1")) {
                        pane.ckpembelian_faktur_secara_kredit.setSelected(true);
                    } else {
                        pane.ckpembelian_faktur_secara_kredit.setSelected(false);
                    }
                    String pembelian_faktur_tarik_order = String.valueOf(jo.get("pembelian_faktur_tarik_order"));
                    if (pembelian_faktur_tarik_order.equals("1")) {
                        pane.ckpembelian_tarik_order_dari_faktur.setSelected(true);
                    } else {
                        pane.ckpembelian_tarik_order_dari_faktur.setSelected(false);
                    }
                    String pembelian_retur = String.valueOf(jo.get("pembelian_retur"));
                    if (pembelian_retur.equals("1")) {
                        pane.ckpembelian_retur.setSelected(true);
                        pane.ckpembelian_input_retur.setEnabled(true);
                        pane.ckpembelian_edit_hapus_retur.setEnabled(true);
                        pane.ckpembelian_secara_kredit_retur.setEnabled(true);
                    } else {
                        pane.ckpembelian_retur.setSelected(false);
                        pane.ckpembelian_input_retur.setEnabled(false);
                        pane.ckpembelian_edit_hapus_retur.setEnabled(false);
                        pane.ckpembelian_secara_kredit_retur.setEnabled(false);
                    }
                    String pembelian_retur_input = String.valueOf(jo.get("pembelian_retur_input"));
                    if (pembelian_retur_input.equals("1")) {
                        pane.ckpembelian_input_retur.setSelected(true);
                    } else {
                        pane.ckpembelian_input_retur.setSelected(false);
                    }
                    String pembelian_retur_edit = String.valueOf(jo.get("pembelian_retur_edit"));
                    if (pembelian_retur_edit.equals("1")) {
                        pane.ckpembelian_edit_hapus_retur.setSelected(true);
                    } else {
                        pane.ckpembelian_edit_hapus_retur.setSelected(false);
                    }
                    String pembelian_retur_kredit = String.valueOf(jo.get("pembelian_retur_kredit"));
                    if (pembelian_retur_kredit.equals("1")) {
                        pane.ckpembelian_secara_kredit_retur.setSelected(true);
                    } else {
                        pane.ckpembelian_secara_kredit_retur.setSelected(false);
                    }
                    String pembelian_hutang = String.valueOf(jo.get("pembelian_hutang"));
                    if (pembelian_hutang.equals("1")) {
                        pane.ckpembelian_hutang_usaha.setSelected(true);
                        pane.ckpembelian_input_pembayaran_hutang.setEnabled(true);
                        pane.ckpembelian_edit_hapus_pembayaran_hutang_usaha.setEnabled(true);
                        pane.ckpembelian_hapus_hutang_tidak_tertagih.setEnabled(true);
                    } else {
                        pane.ckpembelian_hutang_usaha.setSelected(false);
                        pane.ckpembelian_input_pembayaran_hutang.setEnabled(false);
                        pane.ckpembelian_edit_hapus_pembayaran_hutang_usaha.setEnabled(false);
                        pane.ckpembelian_hapus_hutang_tidak_tertagih.setEnabled(false);
                    }
                    String pembelian_hutang_input = String.valueOf(jo.get("pembelian_hutang_input"));
                    if (pembelian_hutang_input.equals("1")) {
                        pane.ckpembelian_input_pembayaran_hutang.setSelected(true);
                    } else {
                        pane.ckpembelian_input_pembayaran_hutang.setSelected(false);
                    }
                    String pembelian_hutang_edit = String.valueOf(jo.get("pembelian_hutang_edit"));
                    if (pembelian_hutang_edit.equals("1")) {
                        pane.ckpembelian_edit_hapus_pembayaran_hutang_usaha.setSelected(true);
                    } else {
                        pane.ckpembelian_edit_hapus_pembayaran_hutang_usaha.setSelected(false);
                    }
                    String pembelian_hutang_writeoff = String.valueOf(jo.get("pembelian_hutang_writeoff"));
                    if (pembelian_hutang_writeoff.equals("1")) {
                        pane.ckpembelian_hapus_hutang_tidak_tertagih.setSelected(true);
                    } else {
                        pane.ckpembelian_hapus_hutang_tidak_tertagih.setSelected(false);
                    }

                    String persediaan_tampilkan_modal = String.valueOf(jo.get("persediaan_tampilkan_modal"));
                    if (persediaan_tampilkan_modal.equals("1")) {
                        pane.ckpersediaan_tampilkan_harga_beli_harga_pokok.setSelected(true);
                    } else {
                        pane.ckpersediaan_tampilkan_harga_beli_harga_pokok.setSelected(false);
                    }
                    String persediaan_rubah_harga_jual = String.valueOf(jo.get("persediaan_rubah_harga_jual"));
                    if (persediaan_rubah_harga_jual.equals("1")) {
                        pane.ckpersediaan_merubah_harga_jual.setSelected(true);
                    } else {
                        pane.ckpersediaan_merubah_harga_jual.setSelected(false);
                    }
                    String persediaan_stok_opname = String.valueOf(jo.get("persediaan_stok_opname"));
                    if (persediaan_stok_opname.equals("1")) {
                        pane.ckpersediaan_stock_opname.setSelected(true);
                    } else {
                        pane.ckpersediaan_stock_opname.setSelected(false);
                    }

                    String persediaan_perakitan = String.valueOf(jo.get("persediaan_perakitan"));
                    if (persediaan_perakitan.equals("1")) {
                        pane.ckpersediaan_perakitan.setSelected(true);
                    } else {
                        pane.ckpersediaan_perakitan.setSelected(false);
                    }

                    String persediaan_penyesuaian = String.valueOf(jo.get("persediaan_penyesuaian"));
                    if (persediaan_penyesuaian.equals("1")) {
                        pane.ckpersediaan_penyesuaian.setSelected(true);
                        pane.ckpersediaan_edit_hapus_penyesuaian.setEnabled(true);
                        pane.ckpersediaan_input_penyesuaian.setEnabled(true);
                    } else {
                        pane.ckpersediaan_penyesuaian.setSelected(false);
                        pane.ckpersediaan_edit_hapus_penyesuaian.setEnabled(false);
                        pane.ckpersediaan_input_penyesuaian.setEnabled(false);
                    }
                    String persediaan_penyesuaian_input = String.valueOf(jo.get("persediaan_penyesuaian_input"));
                    if (persediaan_penyesuaian_input.equals("1")) {
                        pane.ckpersediaan_input_penyesuaian.setSelected(true);
                    } else {
                        pane.ckpersediaan_input_penyesuaian.setSelected(false);
                    }
                    String persediaan_penyesuaian_edit = String.valueOf(jo.get("persediaan_penyesuaian_edit"));
                    if (persediaan_penyesuaian_edit.equals("1")) {
                        pane.ckpersediaan_edit_hapus_penyesuaian.setSelected(true);
                    } else {
                        pane.ckpersediaan_edit_hapus_penyesuaian.setSelected(false);
                    }
                    String persediaan_transfer = String.valueOf(jo.get("persediaan_transfer"));
                    if (persediaan_transfer.equals("1")) {
                        pane.ckpersediaan_transfer_barang_antar_gudang.setSelected(true);
                        pane.ckpersediaan_input_transfer_barang.setEnabled(true);
                        pane.ckpersediaan_edit_hapus_transfer_barang.setEnabled(true);
                    } else {
                        pane.ckpersediaan_transfer_barang_antar_gudang.setSelected(false);
                        pane.ckpersediaan_input_transfer_barang.setEnabled(false);
                        pane.ckpersediaan_edit_hapus_transfer_barang.setEnabled(false);
                    }
                    String persediaan_transfer_input = String.valueOf(jo.get("persediaan_transfer_input"));
                    if (persediaan_transfer_input.equals("1")) {
                        pane.ckpersediaan_input_transfer_barang.setSelected(true);
                    } else {
                        pane.ckpersediaan_input_transfer_barang.setSelected(false);
                    }
                    String persediaan_transfer_edit = String.valueOf(jo.get("persediaan_transfer_edit"));
                    if (persediaan_transfer_edit.equals("1")) {
                        pane.ckpersediaan_edit_hapus_transfer_barang.setSelected(true);
                    } else {
                        pane.ckpersediaan_edit_hapus_transfer_barang.setSelected(false);
                    }

                    String keuangan_kas_masuk = String.valueOf(jo.get("keuangan_kas_masuk"));
                    if (keuangan_kas_masuk.equals("1")) {
                        pane.ckakuntansi_kas_masuk.setSelected(true);
                        pane.ckakuntansi_input_kas_masuk.setEnabled(true);
                        pane.ckakuntansi_edit_hapus_kas_masuk.setEnabled(true);
                    } else {
                        pane.ckakuntansi_kas_masuk.setSelected(false);
                        pane.ckakuntansi_input_kas_masuk.setEnabled(false);
                        pane.ckakuntansi_edit_hapus_kas_masuk.setEnabled(false);
                    }
                    String keuangan_kas_masuk_input = String.valueOf(jo.get("keuangan_kas_masuk_input"));
                    if (keuangan_kas_masuk_input.equals("1")) {
                        pane.ckakuntansi_input_kas_masuk.setSelected(true);
                    } else {
                        pane.ckakuntansi_input_kas_masuk.setSelected(false);
                    }
                    String keuangan_kas_masuk_edit = String.valueOf(jo.get("keuangan_kas_masuk_edit"));
                    if (keuangan_kas_masuk_edit.equals("1")) {
                        pane.ckakuntansi_edit_hapus_kas_masuk.setSelected(true);
                    } else {
                        pane.ckakuntansi_edit_hapus_kas_masuk.setSelected(false);
                    }

                    String keuangan_kas_keluar = String.valueOf(jo.get("keuangan_kas_keluar"));
                    if (keuangan_kas_keluar.equals("1")) {
                        pane.ckakuntansi_kas_keluar.setSelected(true);
                        pane.ckakuntansi_input_kas_keluar.setEnabled(true);
                        pane.ckakuntansi_edit_hapus_kas_keluar.setEnabled(true);
                    } else {
                        pane.ckakuntansi_kas_keluar.setSelected(false);
                        pane.ckakuntansi_input_kas_keluar.setEnabled(false);
                        pane.ckakuntansi_edit_hapus_kas_keluar.setEnabled(false);
                    }
                    String keuangan_kas_keluar_input = String.valueOf(jo.get("keuangan_kas_keluar_input"));
                    if (keuangan_kas_keluar_input.equals("1")) {
                        pane.ckakuntansi_input_kas_keluar.setSelected(true);
                    } else {
                        pane.ckakuntansi_input_kas_keluar.setSelected(false);
                    }
                    String keuangan_kas_keluar_edit = String.valueOf(jo.get("keuangan_kas_keluar_edit"));
                    if (keuangan_kas_keluar_edit.equals("1")) {
                        pane.ckakuntansi_edit_hapus_kas_keluar.setSelected(true);
                    } else {
                        pane.ckakuntansi_edit_hapus_kas_keluar.setSelected(false);
                    }
                    String akuntansi_jurnal_umum = String.valueOf(jo.get("akuntansi_jurnal_umum"));
                    if (akuntansi_jurnal_umum.equals("1")) {
                        pane.ckakuntansi_jurnal_umum.setSelected(true);
                        pane.ckakuntansi_input_jurnal_umum.setEnabled(true);
                        pane.ckakuntansi_edit_hapus_jurnal_umum.setEnabled(true);
                    } else {
                        pane.ckakuntansi_jurnal_umum.setSelected(false);
                        pane.ckakuntansi_input_jurnal_umum.setEnabled(false);
                        pane.ckakuntansi_edit_hapus_jurnal_umum.setEnabled(false);
                    }
                    String akuntansi_jurnal_umum_input = String.valueOf(jo.get("akuntansi_jurnal_umum_input"));
                    if (akuntansi_jurnal_umum_input.equals("1")) {
                        pane.ckakuntansi_input_jurnal_umum.setSelected(true);
                    } else {
                        pane.ckakuntansi_input_jurnal_umum.setSelected(false);
                    }
                    String akuntansi_jurnal_umum_edit = String.valueOf(jo.get("akuntansi_jurnal_umum_edit"));
                    if (akuntansi_jurnal_umum_edit.equals("1")) {
                        pane.ckakuntansi_edit_hapus_jurnal_umum.setSelected(true);
                    } else {
                        pane.ckakuntansi_edit_hapus_jurnal_umum.setSelected(false);
                    }
                    String akuntansi_buku_besar = String.valueOf(jo.get("akuntansi_buku_besar"));
                    if (akuntansi_buku_besar.equals("1")) {
                        pane.ckakuntansi_buku_besar.setSelected(true);
                    } else {
                        pane.ckakuntansi_buku_besar.setSelected(false);
                    }
                    String akuntansi_setting_akun_penting = String.valueOf(jo.get("akuntansi_setting_akun_penting"));
                    if (akuntansi_setting_akun_penting.equals("1")) {
                        pane.ckakuntansi_setting_akun_penting.setSelected(true);
                    } else {
                        pane.ckakuntansi_setting_akun_penting.setSelected(false);
                    }
                    String akuntansi_tutup_buku = String.valueOf(jo.get("akuntansi_tutup_buku"));
                    if (akuntansi_tutup_buku.equals("1")) {
                        pane.ckakuntansi_tutup_buku_bulanan_dan_tahun.setSelected(true);
                    } else {
                        pane.ckakuntansi_tutup_buku_bulanan_dan_tahun.setSelected(false);
                    }
                    String akuntansi_hitung_ulang_saldo = String.valueOf(jo.get("akuntansi_hitung_ulang_saldo"));

                    if (akuntansi_hitung_ulang_saldo.equals("1")) {
                        pane.ckakuntansi_hitung_ulang_saldo.setSelected(true);
                    } else {
                        pane.ckakuntansi_hitung_ulang_saldo.setSelected(false);
                    }
                    String akuntansi_reposting = String.valueOf(jo.get("akuntansi_reposting"));
                    if (akuntansi_reposting.equals("1")) {
                        pane.ckakuntansi_reposting_transaksi.setSelected(true);
                    } else {
                        pane.ckakuntansi_reposting_transaksi.setSelected(false);
                    }
                    String akuntansi_ganti_periode = String.valueOf(jo.get("akuntansi_ganti_periode"));
                    if (akuntansi_ganti_periode.equals("1")) {
                        pane.ckakuntansi_periode_akuntansi.setSelected(true);
                    } else {
                        pane.ckakuntansi_periode_akuntansi.setSelected(false);
                    }
                    String laporan_kuangan = String.valueOf(jo.get("laporan_kuangan"));
                    if (laporan_kuangan.equals("1")) {
                        pane.cklaporan_keuangan.setSelected(true);
                    } else {
                        pane.cklaporan_keuangan.setSelected(false);
                    }
                    String laporan_buku_besar = String.valueOf(jo.get("laporan_buku_besar"));
                    if (laporan_buku_besar.equals("1")) {
                        pane.cklaporan_buku_besar.setSelected(true);
                    } else {
                        pane.cklaporan_buku_besar.setSelected(false);
                    }
                    String laporan_pembelian = String.valueOf(jo.get("laporan_pembelian"));
                    if (laporan_pembelian.equals("1")) {
                        pane.cklaporan_pembelian_dan_hutang_usaha.setSelected(true);
                    } else {
                        pane.cklaporan_pembelian_dan_hutang_usaha.setSelected(false);
                    }
                    String laporan_penjualan = String.valueOf(jo.get("laporan_penjualan"));
                    if (laporan_penjualan.equals("1")) {
                        pane.cklaporan_penjualan_dan_piutang_usaha.setSelected(true);
                    } else {
                        pane.cklaporan_penjualan_dan_piutang_usaha.setSelected(false);
                    }
                    String laporan_persediaan = String.valueOf(jo.get("laporan_persediaan"));
                    if (laporan_persediaan.equals("1")) {
                        pane.cklaporan_persediaan.setSelected(true);
                    } else {
                        pane.cklaporan_persediaan.setSelected(false);
                    }

                }
            } catch (ParseException ex) {
                Logger.getLogger(HakaksesinnerController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void autocheckcontrol() {
        if (pane.ckpenjualan_order.isSelected()) {
            pane.ckpenjualan_edit_hapus_order.setEnabled(true);
            pane.ckpenjualan_edit_hapus_order.setSelected(true);
            pane.ckpenjualan_input_order.setEnabled(true);
            pane.ckpenjualan_input_order.setSelected(true);
        } else {
            pane.ckpenjualan_edit_hapus_order.setEnabled(false);
            pane.ckpenjualan_edit_hapus_order.setSelected(false);
            pane.ckpenjualan_input_order.setEnabled(false);
            pane.ckpenjualan_input_order.setSelected(false);
        }
        if (pane.ckpenjualan_retur.isSelected()) {
            pane.ckpenjualan_edit_hapus_retur.setEnabled(true);
            pane.ckpenjualan_edit_hapus_retur.setSelected(true);
            pane.ckpenjualan_input_retur.setEnabled(true);
            pane.ckpenjualan_input_retur.setSelected(true);
            pane.ckpenjualan_secara_kredit_retur.setEnabled(true);
            pane.ckpenjualan_secara_kredit_retur.setSelected(true);
        } else {
            pane.ckpenjualan_edit_hapus_retur.setEnabled(false);
            pane.ckpenjualan_edit_hapus_retur.setSelected(false);
            pane.ckpenjualan_input_retur.setEnabled(false);
            pane.ckpenjualan_input_retur.setSelected(false);
            pane.ckpenjualan_secara_kredit_retur.setEnabled(false);
            pane.ckpenjualan_secara_kredit_retur.setSelected(false);
        }

        if (pane.ckpenjualan_faktur.isSelected()) {
            pane.ckpenjualan_edit_hapus_faktur.setEnabled(true);
            pane.ckpenjualan_edit_hapus_faktur.setSelected(true);
            pane.ckpenjualan_input_faktur.setEnabled(true);
            pane.ckpenjualan_input_faktur.setSelected(true);
            pane.ckpenjualan_faktur_secara_kredit.setEnabled(true);
            pane.ckpenjualan_faktur_secara_kredit.setSelected(true);
            pane.ckpenjualan_tarik_order_dari_faktur.setEnabled(true);
            pane.ckpenjualan_tarik_order_dari_faktur.setSelected(true);
            pane.ckpenjualan_rubah_harga_jual_faktur.setEnabled(true);
            pane.ckpenjualan_rubah_harga_jual_faktur.setSelected(true);
        } else {
            pane.ckpenjualan_edit_hapus_faktur.setEnabled(false);
            pane.ckpenjualan_edit_hapus_faktur.setSelected(false);
            pane.ckpenjualan_input_faktur.setEnabled(false);
            pane.ckpenjualan_input_faktur.setSelected(false);
            pane.ckpenjualan_faktur_secara_kredit.setEnabled(false);
            pane.ckpenjualan_faktur_secara_kredit.setSelected(false);
            pane.ckpenjualan_tarik_order_dari_faktur.setEnabled(false);
            pane.ckpenjualan_tarik_order_dari_faktur.setSelected(false);
            pane.ckpenjualan_rubah_harga_jual_faktur.setEnabled(false);
            pane.ckpenjualan_rubah_harga_jual_faktur.setSelected(false);
        }

        if (pane.ckpembelian_order.isSelected()) {
            pane.ckpembelian_edit_hapus_order.setEnabled(true);
            pane.ckpembelian_edit_hapus_order.setSelected(true);
            pane.ckpembelian_input_order.setEnabled(true);
            pane.ckpembelian_input_order.setSelected(true);
        } else {
            pane.ckpembelian_edit_hapus_order.setEnabled(false);
            pane.ckpembelian_edit_hapus_order.setSelected(false);
            pane.ckpembelian_input_order.setEnabled(false);
            pane.ckpembelian_input_order.setSelected(false);
        }

        if (pane.ckpembelian_faktur.isSelected()) {
            pane.ckpembelian_edit_hapus_faktur.setEnabled(true);
            pane.ckpembelian_edit_hapus_faktur.setSelected(true);
            pane.ckpembelian_input_faktur.setEnabled(true);
            pane.ckpembelian_input_faktur.setSelected(true);
            pane.ckpembelian_faktur_secara_kredit.setEnabled(true);
            pane.ckpembelian_faktur_secara_kredit.setSelected(true);
            pane.ckpembelian_tarik_order_dari_faktur.setEnabled(true);
            pane.ckpembelian_tarik_order_dari_faktur.setSelected(true);
        } else {
            pane.ckpembelian_edit_hapus_faktur.setEnabled(false);
            pane.ckpembelian_edit_hapus_faktur.setSelected(false);
            pane.ckpembelian_input_faktur.setEnabled(false);
            pane.ckpembelian_input_faktur.setSelected(false);
            pane.ckpembelian_faktur_secara_kredit.setEnabled(false);
            pane.ckpembelian_faktur_secara_kredit.setSelected(false);
            pane.ckpembelian_tarik_order_dari_faktur.setEnabled(false);
            pane.ckpembelian_tarik_order_dari_faktur.setSelected(false);
        }

        if (pane.ckpembelian_hutang_usaha.isSelected()) {
            pane.ckpembelian_edit_hapus_pembayaran_hutang_usaha.setEnabled(true);
            pane.ckpembelian_edit_hapus_pembayaran_hutang_usaha.setSelected(true);
            pane.ckpembelian_input_pembayaran_hutang.setEnabled(true);
            pane.ckpembelian_input_pembayaran_hutang.setSelected(true);
            pane.ckpembelian_hapus_hutang_tidak_tertagih.setEnabled(true);
            pane.ckpembelian_hapus_hutang_tidak_tertagih.setSelected(true);
        } else {
            pane.ckpembelian_edit_hapus_pembayaran_hutang_usaha.setEnabled(false);
            pane.ckpembelian_edit_hapus_pembayaran_hutang_usaha.setSelected(false);
            pane.ckpembelian_input_pembayaran_hutang.setEnabled(false);
            pane.ckpembelian_input_pembayaran_hutang.setSelected(false);
            pane.ckpembelian_hapus_hutang_tidak_tertagih.setEnabled(false);
            pane.ckpembelian_hapus_hutang_tidak_tertagih.setSelected(false);
        }

        if (pane.ckpersediaan_penyesuaian.isSelected()) {
            pane.ckpersediaan_edit_hapus_penyesuaian.setEnabled(true);
            pane.ckpersediaan_edit_hapus_penyesuaian.setSelected(true);
            pane.ckpersediaan_input_penyesuaian.setEnabled(true);
            pane.ckpersediaan_input_penyesuaian.setSelected(true);
        } else {
            pane.ckpersediaan_edit_hapus_penyesuaian.setEnabled(false);
            pane.ckpersediaan_edit_hapus_penyesuaian.setSelected(false);
            pane.ckpersediaan_input_penyesuaian.setEnabled(false);
            pane.ckpersediaan_input_penyesuaian.setSelected(false);
        }

        if (pane.ckpersediaan_transfer_barang_antar_gudang.isSelected()) {
            pane.ckpersediaan_edit_hapus_transfer_barang.setEnabled(true);
            pane.ckpersediaan_edit_hapus_transfer_barang.setSelected(true);
            pane.ckpersediaan_input_transfer_barang.setEnabled(true);
            pane.ckpersediaan_input_transfer_barang.setSelected(true);
        } else {
            pane.ckpersediaan_edit_hapus_transfer_barang.setEnabled(false);
            pane.ckpersediaan_edit_hapus_transfer_barang.setSelected(false);
            pane.ckpersediaan_input_transfer_barang.setEnabled(false);
            pane.ckpersediaan_input_transfer_barang.setSelected(false);
        }

        if (pane.ckakuntansi_kas_masuk.isSelected()) {
            pane.ckakuntansi_edit_hapus_kas_masuk.setEnabled(true);
            pane.ckakuntansi_edit_hapus_kas_masuk.setSelected(true);
            pane.ckakuntansi_input_kas_masuk.setEnabled(true);
            pane.ckakuntansi_input_kas_masuk.setSelected(true);
        } else {
            pane.ckakuntansi_edit_hapus_kas_masuk.setEnabled(false);
            pane.ckakuntansi_edit_hapus_kas_masuk.setSelected(false);
            pane.ckakuntansi_input_kas_masuk.setEnabled(false);
            pane.ckakuntansi_input_kas_masuk.setSelected(false);
        }

        if (pane.ckakuntansi_jurnal_umum.isSelected()) {
            pane.ckakuntansi_edit_hapus_jurnal_umum.setEnabled(true);
            pane.ckakuntansi_edit_hapus_jurnal_umum.setSelected(true);
            pane.ckakuntansi_input_jurnal_umum.setEnabled(true);
            pane.ckakuntansi_input_jurnal_umum.setSelected(true);
        } else {
            pane.ckakuntansi_edit_hapus_jurnal_umum.setEnabled(false);
            pane.ckakuntansi_edit_hapus_jurnal_umum.setSelected(false);
            pane.ckakuntansi_input_jurnal_umum.setEnabled(false);
            pane.ckakuntansi_input_jurnal_umum.setSelected(false);
        }

        if (pane.ckakuntansi_kas_keluar.isSelected()) {
            pane.ckakuntansi_edit_hapus_kas_keluar.setEnabled(true);
            pane.ckakuntansi_edit_hapus_kas_keluar.setSelected(true);
            pane.ckakuntansi_input_kas_keluar.setEnabled(true);
            pane.ckakuntansi_input_kas_keluar.setSelected(true);
        } else {
            pane.ckakuntansi_edit_hapus_kas_keluar.setEnabled(false);
            pane.ckakuntansi_edit_hapus_kas_keluar.setSelected(false);
            pane.ckakuntansi_input_kas_keluar.setEnabled(false);
            pane.ckakuntansi_input_kas_keluar.setSelected(false);
        }

    }

    private void checkcontrolpenjualan() {
        pane.ckpenjualan_order.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pane.ckpenjualan_order.isSelected()) {
                    pane.ckpenjualan_edit_hapus_order.setEnabled(true);
                    pane.ckpenjualan_edit_hapus_order.setSelected(true);
                    pane.ckpenjualan_input_order.setEnabled(true);
                    pane.ckpenjualan_input_order.setSelected(true);
                } else {
                    pane.ckpenjualan_edit_hapus_order.setEnabled(false);
                    pane.ckpenjualan_edit_hapus_order.setSelected(false);
                    pane.ckpenjualan_input_order.setEnabled(false);
                    pane.ckpenjualan_input_order.setSelected(false);
                }
            }
        });

        pane.ckpenjualan_retur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pane.ckpenjualan_retur.isSelected()) {
                    pane.ckpenjualan_edit_hapus_retur.setEnabled(true);
                    pane.ckpenjualan_edit_hapus_retur.setSelected(true);
                    pane.ckpenjualan_input_retur.setEnabled(true);
                    pane.ckpenjualan_input_retur.setSelected(true);
                    pane.ckpenjualan_secara_kredit_retur.setEnabled(true);
                    pane.ckpenjualan_secara_kredit_retur.setSelected(true);
                } else {
                    pane.ckpenjualan_edit_hapus_retur.setEnabled(false);
                    pane.ckpenjualan_edit_hapus_retur.setSelected(false);
                    pane.ckpenjualan_input_retur.setEnabled(false);
                    pane.ckpenjualan_input_retur.setSelected(false);
                    pane.ckpenjualan_secara_kredit_retur.setEnabled(false);
                    pane.ckpenjualan_secara_kredit_retur.setSelected(false);
                }
            }
        });

        pane.ckpenjualan_faktur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pane.ckpenjualan_faktur.isSelected()) {
                    pane.ckpenjualan_edit_hapus_faktur.setEnabled(true);
                    pane.ckpenjualan_edit_hapus_faktur.setSelected(true);
                    pane.ckpenjualan_input_faktur.setEnabled(true);
                    pane.ckpenjualan_input_faktur.setSelected(true);
                    pane.ckpenjualan_faktur_secara_kredit.setEnabled(true);
                    pane.ckpenjualan_faktur_secara_kredit.setSelected(true);
                    pane.ckpenjualan_tarik_order_dari_faktur.setEnabled(true);
                    pane.ckpenjualan_tarik_order_dari_faktur.setSelected(true);
                    pane.ckpenjualan_rubah_harga_jual_faktur.setEnabled(true);
                    pane.ckpenjualan_rubah_harga_jual_faktur.setSelected(true);
                } else {
                    pane.ckpenjualan_edit_hapus_faktur.setEnabled(false);
                    pane.ckpenjualan_edit_hapus_faktur.setSelected(false);
                    pane.ckpenjualan_input_faktur.setEnabled(false);
                    pane.ckpenjualan_input_faktur.setSelected(false);
                    pane.ckpenjualan_faktur_secara_kredit.setEnabled(false);
                    pane.ckpenjualan_faktur_secara_kredit.setSelected(false);
                    pane.ckpenjualan_tarik_order_dari_faktur.setEnabled(false);
                    pane.ckpenjualan_tarik_order_dari_faktur.setSelected(false);
                    pane.ckpenjualan_rubah_harga_jual_faktur.setEnabled(false);
                    pane.ckpenjualan_rubah_harga_jual_faktur.setSelected(false);
                }
            }
        });

        pane.ckpenjualan_piutang_usaha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pane.ckpenjualan_piutang_usaha.isSelected()) {
                    pane.ckpenjualan_edit_hapus_pembayaran_piutang.setEnabled(true);
                    pane.ckpenjualan_edit_hapus_pembayaran_piutang.setSelected(true);
                    pane.ckpenjualan_input_pembayaran_piutang_usaha.setEnabled(true);
                    pane.ckpenjualan_input_pembayaran_piutang_usaha.setSelected(true);
                    pane.ckpenjualan_hapus_hutang_tak_tertagih.setEnabled(true);
                    pane.ckpenjualan_hapus_hutang_tak_tertagih.setSelected(true);
                } else {
                    pane.ckpenjualan_edit_hapus_pembayaran_piutang.setEnabled(false);
                    pane.ckpenjualan_edit_hapus_pembayaran_piutang.setSelected(false);
                    pane.ckpenjualan_input_pembayaran_piutang_usaha.setEnabled(false);
                    pane.ckpenjualan_input_pembayaran_piutang_usaha.setSelected(false);
                    pane.ckpenjualan_hapus_hutang_tak_tertagih.setEnabled(false);
                    pane.ckpenjualan_hapus_hutang_tak_tertagih.setSelected(false);
                }
            }
        });

    }

    private void checkcontrolpembelian() {
        pane.ckpembelian_order.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pane.ckpembelian_order.isSelected()) {
                    pane.ckpembelian_edit_hapus_order.setEnabled(true);
                    pane.ckpembelian_edit_hapus_order.setSelected(true);
                    pane.ckpembelian_input_order.setEnabled(true);
                    pane.ckpembelian_input_order.setSelected(true);
                } else {
                    pane.ckpembelian_edit_hapus_order.setEnabled(false);
                    pane.ckpembelian_edit_hapus_order.setSelected(false);
                    pane.ckpembelian_input_order.setEnabled(false);
                    pane.ckpembelian_input_order.setSelected(false);
                }
            }
        });

        pane.ckpembelian_retur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pane.ckpembelian_retur.isSelected()) {
                    pane.ckpembelian_edit_hapus_retur.setEnabled(true);
                    pane.ckpembelian_edit_hapus_retur.setSelected(true);
                    pane.ckpembelian_input_retur.setEnabled(true);
                    pane.ckpembelian_input_retur.setSelected(true);
                    pane.ckpembelian_secara_kredit_retur.setEnabled(true);
                    pane.ckpembelian_secara_kredit_retur.setSelected(true);
                } else {
                    pane.ckpembelian_edit_hapus_retur.setEnabled(false);
                    pane.ckpembelian_edit_hapus_retur.setSelected(false);
                    pane.ckpembelian_input_retur.setEnabled(false);
                    pane.ckpembelian_input_retur.setSelected(false);
                    pane.ckpembelian_secara_kredit_retur.setEnabled(false);
                    pane.ckpembelian_secara_kredit_retur.setSelected(false);
                }
            }
        });

        pane.ckpembelian_faktur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pane.ckpembelian_faktur.isSelected()) {
                    pane.ckpembelian_edit_hapus_faktur.setEnabled(true);
                    pane.ckpembelian_edit_hapus_faktur.setSelected(true);
                    pane.ckpembelian_input_faktur.setEnabled(true);
                    pane.ckpembelian_input_faktur.setSelected(true);
                    pane.ckpembelian_faktur_secara_kredit.setEnabled(true);
                    pane.ckpembelian_faktur_secara_kredit.setSelected(true);
                    pane.ckpembelian_tarik_order_dari_faktur.setEnabled(true);
                    pane.ckpembelian_tarik_order_dari_faktur.setSelected(true);
                } else {
                    pane.ckpembelian_edit_hapus_faktur.setEnabled(false);
                    pane.ckpembelian_edit_hapus_faktur.setSelected(false);
                    pane.ckpembelian_input_faktur.setEnabled(false);
                    pane.ckpembelian_input_faktur.setSelected(false);
                    pane.ckpembelian_faktur_secara_kredit.setEnabled(false);
                    pane.ckpembelian_faktur_secara_kredit.setSelected(false);
                    pane.ckpembelian_tarik_order_dari_faktur.setEnabled(false);
                    pane.ckpembelian_tarik_order_dari_faktur.setSelected(false);
                }
            }
        });

        pane.ckpembelian_hutang_usaha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pane.ckpembelian_hutang_usaha.isSelected()) {
                    pane.ckpembelian_edit_hapus_pembayaran_hutang_usaha.setEnabled(true);
                    pane.ckpembelian_edit_hapus_pembayaran_hutang_usaha.setSelected(true);
                    pane.ckpembelian_input_pembayaran_hutang.setEnabled(true);
                    pane.ckpembelian_input_pembayaran_hutang.setSelected(true);
                    pane.ckpembelian_hapus_hutang_tidak_tertagih.setEnabled(true);
                    pane.ckpembelian_hapus_hutang_tidak_tertagih.setSelected(true);
                } else {
                    pane.ckpembelian_edit_hapus_pembayaran_hutang_usaha.setEnabled(false);
                    pane.ckpembelian_edit_hapus_pembayaran_hutang_usaha.setSelected(false);
                    pane.ckpembelian_input_pembayaran_hutang.setEnabled(false);
                    pane.ckpembelian_input_pembayaran_hutang.setSelected(false);
                    pane.ckpembelian_hapus_hutang_tidak_tertagih.setEnabled(false);
                    pane.ckpembelian_hapus_hutang_tidak_tertagih.setSelected(false);
                }
            }
        });

    }

    private void checkcontrolpersediaan() {
        pane.ckpersediaan_penyesuaian.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pane.ckpersediaan_penyesuaian.isSelected()) {
                    pane.ckpersediaan_edit_hapus_penyesuaian.setEnabled(true);
                    pane.ckpersediaan_edit_hapus_penyesuaian.setSelected(true);
                    pane.ckpersediaan_input_penyesuaian.setEnabled(true);
                    pane.ckpersediaan_input_penyesuaian.setSelected(true);
                } else {
                    pane.ckpersediaan_edit_hapus_penyesuaian.setEnabled(false);
                    pane.ckpersediaan_edit_hapus_penyesuaian.setSelected(false);
                    pane.ckpersediaan_input_penyesuaian.setEnabled(false);
                    pane.ckpersediaan_input_penyesuaian.setSelected(false);
                }
            }
        });

        pane.ckpersediaan_transfer_barang_antar_gudang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pane.ckpersediaan_transfer_barang_antar_gudang.isSelected()) {
                    pane.ckpersediaan_edit_hapus_transfer_barang.setEnabled(true);
                    pane.ckpersediaan_edit_hapus_transfer_barang.setSelected(true);
                    pane.ckpersediaan_input_transfer_barang.setEnabled(true);
                    pane.ckpersediaan_input_transfer_barang.setSelected(true);
                } else {
                    pane.ckpersediaan_edit_hapus_transfer_barang.setEnabled(false);
                    pane.ckpersediaan_edit_hapus_transfer_barang.setSelected(false);
                    pane.ckpersediaan_input_transfer_barang.setEnabled(false);
                    pane.ckpersediaan_input_transfer_barang.setSelected(false);
                }
            }
        });

    }

    private void checkcontrolakuntansi() {
        pane.ckakuntansi_kas_masuk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pane.ckakuntansi_kas_masuk.isSelected()) {
                    pane.ckakuntansi_edit_hapus_kas_masuk.setEnabled(true);
                    pane.ckakuntansi_edit_hapus_kas_masuk.setSelected(true);
                    pane.ckakuntansi_input_kas_masuk.setEnabled(true);
                    pane.ckakuntansi_input_kas_masuk.setSelected(true);
                } else {
                    pane.ckakuntansi_edit_hapus_kas_masuk.setEnabled(false);
                    pane.ckakuntansi_edit_hapus_kas_masuk.setSelected(false);
                    pane.ckakuntansi_input_kas_masuk.setEnabled(false);
                    pane.ckakuntansi_input_kas_masuk.setSelected(false);
                }
            }
        });

        pane.ckakuntansi_jurnal_umum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pane.ckakuntansi_jurnal_umum.isSelected()) {
                    pane.ckakuntansi_edit_hapus_jurnal_umum.setEnabled(true);
                    pane.ckakuntansi_edit_hapus_jurnal_umum.setSelected(true);
                    pane.ckakuntansi_input_jurnal_umum.setEnabled(true);
                    pane.ckakuntansi_input_jurnal_umum.setSelected(true);
                } else {
                    pane.ckakuntansi_edit_hapus_jurnal_umum.setEnabled(false);
                    pane.ckakuntansi_edit_hapus_jurnal_umum.setSelected(false);
                    pane.ckakuntansi_input_jurnal_umum.setEnabled(false);
                    pane.ckakuntansi_input_jurnal_umum.setSelected(false);
                }
            }
        });

        pane.ckakuntansi_kas_keluar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pane.ckakuntansi_kas_keluar.isSelected()) {
                    pane.ckakuntansi_edit_hapus_kas_keluar.setEnabled(true);
                    pane.ckakuntansi_edit_hapus_kas_keluar.setSelected(true);
                    pane.ckakuntansi_input_kas_keluar.setEnabled(true);
                    pane.ckakuntansi_input_kas_keluar.setSelected(true);
                } else {
                    pane.ckakuntansi_edit_hapus_kas_keluar.setEnabled(false);
                    pane.ckakuntansi_edit_hapus_kas_keluar.setSelected(false);
                    pane.ckakuntansi_input_kas_keluar.setEnabled(false);
                    pane.ckakuntansi_input_kas_keluar.setSelected(false);
                }
            }
        });

    }

    private void simpandata() {
        pane.blanjut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int jumlahtab = pane.tab_hak_akses.getTabCount();
                int tabposisi = pane.tab_hak_akses.getSelectedIndex();
                switch (tabposisi) {
                    case 0:
                        pane.tab_hak_akses.setSelectedIndex(1);
                        break;
                    case 1:
                        pane.tab_hak_akses.setSelectedIndex(2);
                        break;
                    case 2:
                        pane.tab_hak_akses.setSelectedIndex(3);
                        break;
                    case 3:
                        pane.tab_hak_akses.setSelectedIndex(4);
                        break;
                    case 4:
                        pane.tab_hak_akses.setSelectedIndex(5);
                        break;
                    case 5:
                        pane.tab_hak_akses.setSelectedIndex(6);
                        break;
                    case 6:
                        pane.tab_hak_akses.setSelectedIndex(7);
                        break;
                    case 7:
                        int opt = JOptionPane.showConfirmDialog(null, "Yakin Ingin Menyimpan Hak Akses?", "Konfirmasi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (opt == 0) {
                            rawsimpan();
                        }

                        break;
                    default:
                        break;
                }
            }
        });

    }

    private void rawsimpan() {
        String nama = pane.ednama_hak_akses.getText();
        String keterangan = pane.taketerangan_hak_akses.getText();
        String dm_saldo_awal_akun = "0";
        if (pane.cksaldo_awal_akun.isSelected()) {
            dm_saldo_awal_akun = "1";
        } else {
            dm_saldo_awal_akun = "0";
        }
        String dm_saldo_awal_persediaan = "0";
        if (pane.cksaldo_awal_persediaan.isSelected()) {
            dm_saldo_awal_persediaan = "1";
        } else {
            dm_saldo_awal_persediaan = "0";
        }
        String dm_saldo_awal_hutang = "0";
        if (pane.cksaldo_awal_hutang_usaha.isSelected()) {
            dm_saldo_awal_hutang = "1";
        } else {
            dm_saldo_awal_hutang = "0";
        }
        String dm_saldo_awal_piutang = "0";
        if (pane.cksaldo_awal_piutang_usaha.isSelected()) {
            dm_saldo_awal_piutang = "1";
        } else {
            dm_saldo_awal_piutang = "0";
        }
        String dm_baru_edit_hapus = "0";
        if (pane.cksaldo_buat_baru_edit_hapus.isSelected()) {
            dm_baru_edit_hapus = "1";

        } else {
            dm_baru_edit_hapus = "0";
        }
        String dm_limitasi_gudang = "0";
        if (pane.ckmaster_limitasi_gudang.isSelected()) {
            dm_limitasi_gudang = "1";

        } else {
            dm_limitasi_gudang = "0";

        }
        String dm_limitasi_dept = "0";
        if (pane.ckmaster_limitasi_departement.isSelected()) {
            dm_limitasi_dept = "1";

        } else {
            dm_limitasi_dept = "0";

        }
        String sistem_setup_program = "0";
        if (pane.cksistem_setup_program.isSelected()) {
            sistem_setup_program = "1";
        } else {
            sistem_setup_program = "0";

        }
        String sistem_backup_data = "0";
        if (pane.cksistem_bakcup_data.isSelected()) {
            sistem_backup_data = "1";
        } else {
            sistem_backup_data = "0";

        }
        String sistem_setting_pengguna = "0";
        if (pane.cksistem_setting_pengguna.isSelected()) {
            sistem_setting_pengguna = "1";
        } else {
            sistem_setting_pengguna = "0";

        }
        String sistem_sql_editor = "0";
        if (pane.cksistem_buka_sql_editor.isSelected()) {
            sistem_sql_editor = "1";

        } else {
            sistem_sql_editor = "0";

        }
        String penjualan_order = "0";
        if (pane.ckpenjualan_order.isSelected()) {
            penjualan_order = "1";

        } else {
            penjualan_order = "0";

        }
        String penjualan_order_input = "0";
        if (pane.ckpenjualan_input_order.isSelected()) {
            penjualan_order_input = "1";

        } else {
            penjualan_order_input = "0";

        }
        String penjualan_order_edit = "0";
        if (pane.ckpenjualan_edit_hapus_order.isSelected()) {
            penjualan_order_edit = "1";

        } else {
            penjualan_order_edit = "0";

        }
        String penjualan_faktur = "0";
        if (pane.ckpenjualan_faktur.isSelected()) {
            penjualan_faktur = "1";

        } else {
            penjualan_faktur = "0";

        }
        String penjualan_faktur_input = "0";
        if (pane.ckpenjualan_input_faktur.isSelected()) {
            penjualan_faktur_input = "1";

        } else {
            penjualan_faktur_input = "0";

        }
        String penjualan_faktur_edit = "0";
        if (pane.ckpenjualan_edit_hapus_faktur.isSelected()) {
            penjualan_faktur_edit = "1";

        } else {
            penjualan_faktur_edit = "0";

        }
        String penjualan_faktur_kredit = "0";
        if (pane.ckpenjualan_faktur_secara_kredit.isSelected()) {
            penjualan_faktur_kredit = "1";

        } else {
            penjualan_faktur_kredit = "0";

        }
        String penjualan_faktur_tarik_order = "0";
        if (pane.ckpenjualan_tarik_order_dari_faktur.isSelected()) {
            penjualan_faktur_tarik_order = "1";

        } else {
            penjualan_faktur_tarik_order = "0";

        }
        String penjualan_faktur_rubah_harga = "0";
        if (pane.ckpenjualan_rubah_harga_jual_faktur.isSelected()) {
            penjualan_faktur_rubah_harga = "1";

        } else {
            penjualan_faktur_rubah_harga = "0";

        }

        String penjualan_retur = "0";
        if (pane.ckpenjualan_retur.isSelected()) {
            penjualan_retur = "1";

        } else {
            penjualan_retur = "0";

        }

        String penjualan_retur_input = "0";
        if (pane.ckpenjualan_input_retur.isSelected()) {
            penjualan_retur_input = "1";

        } else {
            penjualan_retur_input = "0";

        }
        String penjualan_retur_edit = "0";
        if (pane.ckpenjualan_edit_hapus_retur.isSelected()) {
            penjualan_retur_edit = "1";

        } else {
            penjualan_retur_edit = "0";

        }
        String penjualan_retur_kredit = "0";
        if (pane.ckpenjualan_secara_kredit_retur.isSelected()) {
            penjualan_retur_kredit = "1";

        } else {
            penjualan_retur_kredit = "0";

        }
        String penjualan_piutang = "0";
        if (pane.ckpenjualan_piutang_usaha.isSelected()) {
            penjualan_piutang = "1";

        } else {
            penjualan_piutang = "0";

        }
        String penjualan_piutang_input = "0";
        if (pane.ckpenjualan_input_pembayaran_piutang_usaha.isSelected()) {
            penjualan_piutang_input = "1";

        } else {
            penjualan_piutang_input = "0";

        }
        String penjualan_piutang_edit = "0";
        if (pane.ckpenjualan_edit_hapus_pembayaran_piutang.isSelected()) {
            penjualan_piutang_edit = "1";

        } else {
            penjualan_piutang_edit = "0";

        }
        String penjualan_piutang_writeoff = "0";
        if (pane.ckpenjualan_hapus_hutang_tak_tertagih.isSelected()) {
            penjualan_piutang_writeoff = "1";

        } else {
            penjualan_piutang_writeoff = "0";

        }
        String pembelian_order = "0";
        if (pane.ckpembelian_order.isSelected()) {
            pembelian_order = "1";

        } else {
            pembelian_order = "0";

        }
        String pembelian_order_input = "0";
        if (pane.ckpembelian_input_order.isSelected()) {
            pembelian_order_input = "1";

        } else {
            pembelian_order_input = "0";

        }
        String pembelian_order_edit = "0";
        if (pane.ckpembelian_edit_hapus_order.isSelected()) {
            pembelian_order_edit = "1";

        } else {
            pembelian_order_edit = "0";

        }
        String pembelian_faktur = "0";
        if (pane.ckpembelian_faktur.isSelected()) {
            pembelian_faktur = "1";

        } else {
            pembelian_faktur = "0";

        }
        String pembelian_faktur_input = "0";
        if (pane.ckpembelian_input_faktur.isSelected()) {
            pembelian_faktur_input = "1";

        } else {
            pembelian_faktur_input = "0";

        }
        String pembelian_faktur_edit = "0";
        if (pane.ckpembelian_edit_hapus_faktur.isSelected()) {
            pembelian_faktur_edit = "1";

        } else {
            pembelian_faktur_edit = "0";

        }
        String pembelian_faktur_kredit = "0";
        if (pane.ckpembelian_faktur_secara_kredit.isSelected()) {
            pembelian_faktur_kredit = "1";

        } else {
            pembelian_faktur_kredit = "0";

        }
        String pembelian_faktur_tarik_order = "0";
        if (pane.ckpembelian_tarik_order_dari_faktur.isSelected()) {
            pembelian_faktur_tarik_order = "1";

        } else {
            pembelian_faktur_tarik_order = "0";

        }
        String pembelian_retur = "0";
        if (pane.ckpembelian_retur.isSelected()) {
            pembelian_retur = "1";

        } else {
            pembelian_retur = "0";

        }
        String pembelian_retur_input = "0";
        if (pane.ckpembelian_input_retur.isSelected()) {
            pembelian_retur_input = "1";

        } else {
            pembelian_retur_input = "0";

        }
        String pembelian_retur_edit = "0";
        if (pane.ckpembelian_edit_hapus_retur.isSelected()) {
            pembelian_retur_edit = "1";

        } else {
            pembelian_retur_edit = "0";

        }
        String pembelian_retur_kredit = "0";
        if (pane.ckpembelian_secara_kredit_retur.isSelected()) {
            pembelian_retur_kredit = "1";

        } else {
            pembelian_retur_kredit = "0";

        }
        String pembelian_hutang = "0";
        if (pane.ckpembelian_hutang_usaha.isSelected()) {
            pembelian_hutang = "1";

        } else {
            pembelian_hutang = "0";

        }
        String pembelian_hutang_input = "0";
        if (pane.ckpembelian_input_pembayaran_hutang.isSelected()) {
            pembelian_hutang_input = "1";

        } else {
            pembelian_hutang_input = "0";

        }
        String pembelian_hutang_edit = "0";
        if (pane.ckpembelian_edit_hapus_pembayaran_hutang_usaha.isSelected()) {
            pembelian_hutang_edit = "1";

        } else {
            pembelian_hutang_edit = "0";

        }
        String pembelian_hutang_writeoff = "0";
        if (pane.ckpembelian_hapus_hutang_tidak_tertagih.isSelected()) {
            pembelian_hutang_writeoff = "1";

        } else {
            pembelian_hutang_writeoff = "0";

        }
        String persediaan_tampilkan_modal = "0";
        if (pane.ckpersediaan_tampilkan_harga_beli_harga_pokok.isSelected()) {
            persediaan_tampilkan_modal = "1";

        } else {
            persediaan_tampilkan_modal = "0";

        }
        String persediaan_rubah_harga_jual = "0";
        if (pane.ckpersediaan_merubah_harga_jual.isSelected()) {
            persediaan_rubah_harga_jual = "1";

        } else {
            persediaan_rubah_harga_jual = "0";

        }
        String persediaan_stok_opname = "0";
        if (pane.ckpersediaan_stock_opname.isSelected()) {
            persediaan_stok_opname = "1";

        } else {
            persediaan_stok_opname = "0";

        }
        String persediaan_perakitan = "0";
        if (pane.ckpersediaan_perakitan.isSelected()) {
            persediaan_perakitan = "1";

        } else {
            persediaan_perakitan = "0";

        }
        String persediaan_penyesuaian = "0";
        if (pane.ckpersediaan_penyesuaian.isSelected()) {
            persediaan_penyesuaian = "1";

        } else {
            persediaan_penyesuaian = "0";

        }
        String persediaan_penyesuaian_input = "0";
        if (pane.ckpersediaan_input_penyesuaian.isSelected()) {
            persediaan_penyesuaian_input = "1";

        } else {
            persediaan_penyesuaian_input = "0";

        }
        String persediaan_penyesuaian_edit = "0";
        if (pane.ckpersediaan_edit_hapus_penyesuaian.isSelected()) {
            persediaan_penyesuaian_edit = "1";

        } else {
            persediaan_penyesuaian_edit = "0";

        }
        String persediaan_transfer = "0";
        if (pane.ckpersediaan_input_transfer_barang.isSelected()) {
            persediaan_transfer = "1";

        } else {
            persediaan_transfer = "0";

        }
        String persediaan_transfer_input = "0";
        if (pane.ckpersediaan_input_transfer_barang.isSelected()) {
            persediaan_transfer_input = "1";

        } else {
            persediaan_transfer_input = "0";

        }
        String persediaan_transfer_edit = "0";
        if (pane.ckpersediaan_edit_hapus_transfer_barang.isSelected()) {
            persediaan_transfer_edit = "1";

        } else {
            persediaan_transfer_edit = "0";

        }
        String keuangan_kas_masuk = "0";
        if (pane.ckakuntansi_kas_masuk.isSelected()) {
            keuangan_kas_masuk = "1";

        } else {
            keuangan_kas_masuk = "0";

        }
        String keuangan_kas_masuk_input = "0";
        if (pane.ckakuntansi_input_kas_masuk.isSelected()) {
            keuangan_kas_masuk_input = "1";

        } else {
            keuangan_kas_masuk_input = "0";

        }
        String keuangan_kas_masuk_edit = "0";
        if (pane.ckakuntansi_edit_hapus_kas_masuk.isSelected()) {
            keuangan_kas_masuk_edit = "1";

        } else {
            keuangan_kas_masuk_edit = "0";

        }
        String keuangan_kas_keluar = "0";
        if (pane.cksaldo_awal_akun.isSelected()) {
            keuangan_kas_keluar = "1";

        } else {
            keuangan_kas_keluar = "0";

        }
        String keuangan_kas_keluar_input = "0";
        if (pane.ckakuntansi_input_kas_keluar.isSelected()) {
            keuangan_kas_keluar_input = "1";

        } else {
            keuangan_kas_keluar_input = "0";

        }
        String keuangan_kas_keluar_edit = "0";
        if (pane.ckakuntansi_edit_hapus_kas_keluar.isSelected()) {
            keuangan_kas_keluar_edit = "1";

        } else {
            keuangan_kas_keluar_edit = "0";

        }
        String akuntansi_jurnal_umum = "0";
        if (pane.ckakuntansi_jurnal_umum.isSelected()) {
            akuntansi_jurnal_umum = "1";

        } else {
            akuntansi_jurnal_umum = "0";

        }
        String akuntansi_jurnal_umum_input = "0";
        if (pane.ckakuntansi_input_jurnal_umum.isSelected()) {
            akuntansi_jurnal_umum_input = "1";

        } else {
            akuntansi_jurnal_umum_input = "0";

        }
        String akuntansi_jurnal_umum_edit = "0";
        if (pane.ckakuntansi_edit_hapus_jurnal_umum.isSelected()) {
            akuntansi_jurnal_umum_edit = "1";

        } else {
            akuntansi_jurnal_umum_edit = "0";

        }
        String akuntansi_buku_besar = "0";
        if (pane.ckakuntansi_buku_besar.isSelected()) {
            akuntansi_buku_besar = "1";

        } else {
            akuntansi_buku_besar = "0";

        }
        String akuntansi_setting_akun_penting = "0";
        if (pane.ckakuntansi_setting_akun_penting.isSelected()) {
            akuntansi_setting_akun_penting = "1";

        } else {
            akuntansi_setting_akun_penting = "0";

        }
        String akuntansi_tutup_buku = "0";
        if (pane.ckakuntansi_tutup_buku_bulanan_dan_tahun.isSelected()) {
            akuntansi_tutup_buku = "1";

        } else {
            akuntansi_tutup_buku = "0";

        }
        String akuntansi_hitung_ulang_saldo = "0";

        if (pane.ckakuntansi_hitung_ulang_saldo.isSelected()) {
            akuntansi_hitung_ulang_saldo = "1";

        } else {
            akuntansi_hitung_ulang_saldo = "0";

        }
        String akuntansi_reposting = "0";
        if (pane.ckakuntansi_reposting_transaksi.isSelected()) {
            akuntansi_reposting = "1";

        } else {
            akuntansi_reposting = "0";

        }
        String akuntansi_ganti_periode = "0";
        if (pane.ckakuntansi_periode_akuntansi.isSelected()) {
            akuntansi_ganti_periode = "1";

        } else {
            akuntansi_ganti_periode = "0";

        }
        String laporan_kuangan = "0";
        if (pane.cklaporan_keuangan.isSelected()) {
            laporan_kuangan = "1";

        } else {
            laporan_kuangan = "0";

        }
        String laporan_buku_besar = "0";
        if (pane.cklaporan_buku_besar.isSelected()) {
            laporan_buku_besar = "1";

        } else {
            laporan_buku_besar = "0";
        }
        String laporan_pembelian = "0";
        if (pane.cklaporan_pembelian_dan_hutang_usaha.isSelected()) {
            laporan_pembelian = "1";

        } else {
            laporan_pembelian = "0";
        }
        String laporan_penjualan = "0";
        if (pane.cklaporan_penjualan_dan_piutang_usaha.isSelected()) {
            laporan_penjualan = "1";

        } else {
            laporan_penjualan = "0";

        }
        String laporan_persediaan = "0";
        if (pane.cklaporan_persediaan.isSelected()) {
            laporan_persediaan = "1";

        } else {
            laporan_persediaan = "0";

        }

        String data = "data="
             + "nama='" + nama + "'::"
             + "keterangan='" + keterangan + "'::"
             + "dm_saldo_awal_akun='" + dm_saldo_awal_akun + "'::"
             + "dm_saldo_awal_persediaan='" + dm_saldo_awal_persediaan + "'::"
             + "dm_saldo_awal_hutang='" + dm_saldo_awal_hutang + "'::"
             + "dm_saldo_awal_piutang='" + dm_saldo_awal_piutang + "'::"
             + "dm_baru_edit_hapus='" + dm_baru_edit_hapus + "'::"
             + "dm_limitasi_gudang='" + dm_limitasi_gudang + "'::"
             + "dm_limitasi_dept='" + dm_limitasi_dept + "'::"
             + "sistem_setup_program='" + sistem_setup_program + "'::"
             + "sistem_backup_data='" + sistem_backup_data + "'::"
             + "sistem_setting_pengguna='" + sistem_setting_pengguna + "'::"
             + "sistem_sql_editor='" + sistem_sql_editor + "'::"
             + "penjualan_order='" + penjualan_order + "'::"
             + "penjualan_order_input='" + penjualan_order_input + "'::"
             + "penjualan_order_edit='" + penjualan_order_edit + "'::"
             + "penjualan_faktur='" + penjualan_faktur + "'::"
             + "penjualan_faktur_input='" + penjualan_faktur_input + "'::"
             + "penjualan_faktur_edit='" + penjualan_faktur_edit + "'::"
             + "penjualan_faktur_kredit='" + penjualan_faktur_kredit + "'::"
             + "penjualan_faktur_tarik_order='" + penjualan_faktur_tarik_order + "'::"
             + "penjualan_faktur_rubah_harga='" + penjualan_faktur_rubah_harga + "'::"
             + "penjualan_retur='" + penjualan_retur + "'::"
             + "penjualan_retur_input='" + penjualan_retur_input + "'::"
             + "penjualan_retur_edit='" + penjualan_retur_edit + "'::"
             + "penjualan_retur_kredit='" + penjualan_retur_kredit + "'::"
             + "penjualan_piutang='" + penjualan_piutang + "'::"
             + "penjualan_piutang_input='" + penjualan_piutang_input + "'::"
             + "penjualan_piutang_edit='" + penjualan_piutang_edit + "'::"
             + "penjualan_piutang_writeoff='" + penjualan_piutang_writeoff + "'::"
             + "pembelian_order='" + pembelian_order + "'::"
             + "pembelian_order_input='" + pembelian_order_input + "'::"
             + "pembelian_order_edit='" + pembelian_order_edit + "'::"
             + "pembelian_faktur='" + pembelian_faktur + "'::"
             + "pembelian_faktur_input='" + pembelian_faktur_input + "'::"
             + "pembelian_faktur_edit='" + pembelian_faktur_edit + "'::"
             + "pembelian_faktur_kredit='" + pembelian_faktur_kredit + "'::"
             + "pembelian_faktur_tarik_order='" + pembelian_faktur_tarik_order + "'::"
             + "pembelian_retur='" + pembelian_retur + "'::"
             + "pembelian_retur_input='" + pembelian_retur_input + "'::"
             + "pembelian_retur_edit='" + pembelian_retur_edit + "'::"
             + "pembelian_retur_kredit='" + pembelian_retur_kredit + "'::"
             + "pembelian_hutang='" + pembelian_hutang + "'::"
             + "pembelian_hutang_input='" + pembelian_hutang_input + "'::"
             + "pembelian_hutang_edit='" + pembelian_hutang_edit + "'::"
             + "pembelian_hutang_writeoff='" + pembelian_hutang_writeoff + "'::"
             + "persediaan_tampilkan_modal='" + persediaan_tampilkan_modal + "'::"
             + "persediaan_rubah_harga_jual='" + persediaan_rubah_harga_jual + "'::"
             + "persediaan_stok_opname='" + persediaan_stok_opname + "'::"
             + "persediaan_perakitan='" + persediaan_perakitan + "'::"
             + "persediaan_penyesuaian='" + persediaan_penyesuaian + "'::"
             + "persediaan_penyesuaian_input='" + persediaan_penyesuaian_input + "'::"
             + "persediaan_penyesuaian_edit='" + persediaan_penyesuaian_edit + "'::"
             + "persediaan_transfer='" + persediaan_transfer + "'::"
             + "persediaan_transfer_input='" + persediaan_transfer_input + "'::"
             + "persediaan_transfer_edit='" + persediaan_transfer_edit + "'::"
             + "keuangan_kas_masuk='" + keuangan_kas_masuk + "'::"
             + "keuangan_kas_masuk_input='" + keuangan_kas_masuk_input + "'::"
             + "keuangan_kas_masuk_edit='" + keuangan_kas_masuk_edit + "'::"
             + "keuangan_kas_keluar='" + keuangan_kas_keluar + "'::"
             + "keuangan_kas_keluar_input='" + keuangan_kas_keluar_input + "'::"
             + "keuangan_kas_keluar_edit='" + keuangan_kas_keluar_edit + "'::"
             + "akuntansi_jurnal_umum='" + akuntansi_jurnal_umum + "'::"
             + "akuntansi_jurnal_umum_input='" + akuntansi_jurnal_umum_input + "'::"
             + "akuntansi_jurnal_umum_edit='" + akuntansi_jurnal_umum_edit + "'::"
             + "akuntansi_buku_besar='" + akuntansi_buku_besar + "'::"
             + "akuntansi_setting_akun_penting='" + akuntansi_setting_akun_penting + "'::"
             + "akuntansi_tutup_buku='" + akuntansi_tutup_buku + "'::"
             + "akuntansi_hitung_ulang_saldo='" + akuntansi_hitung_ulang_saldo + "'::"
             + "akuntansi_reposting='" + akuntansi_reposting + "'::"
             + "akuntansi_ganti_periode='" + akuntansi_ganti_periode + "'::"
             + "laporan_kuangan='" + laporan_kuangan + "'::"
             + "laporan_buku_besar='" + laporan_buku_besar + "'::"
             + "laporan_pembelian='" + laporan_pembelian + "'::"
             + "laporan_penjualan='" + laporan_penjualan + "'::"
             + "laporan_persediaan='" + laporan_persediaan + "'";
        if (ids.equals("")) {
            ch.insertdata("dm/insertpenggunalevel", data);
        } else {
            ch.updatedata(urlsave, data, ids);
        }

        if (Staticvar.getresult.equals("berhasil")) {
            JOptionPane.showMessageDialog(null, "Hak Akses Berhasil Disimpan");
            new Globalsession();
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
}
