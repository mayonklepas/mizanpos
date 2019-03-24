/*
 * To change this license header; choose License Headers in Project Properties.
 * To change this template file; choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class Globalsession {

    public static String DEFAULT_JOB_ID = "";
    public static String DEFAULT_JOB_KODE = "";
    public static String CURRENCYCODE = "";
    public static String CURRENCYNAME = "";
    public static String CURRENCYSYMBOL = "";
    public static String AKUNLRPERIODE = "";
    public static String AKUNLRTAHUNBERJALAN = "";
    public static String AKUNLRDITAHAN = "";
    public static String AKUNPENYEIMBANGNERACA = "";
    public static String AKUNHUTANGUSAHA = "";
    public static String NAMAAKUNHUTANGUSAHA = "";
    public static String NAMAAKUNPIUTANGUSAHA = "";
    public static String AKUNHUTANGGIRO = "";
    public static String NAMAAKUNHUTANGGIRO = "";
    public static String AKUNPIUTANGUSAHA = "";
    public static String AKUNPIUTANGGIRO = "";
    public static String NAMAAKUNPIUTANGGIRO = "";
    public static String AKUNPEMBELIANTUNAI = "";
    public static String NAMAAKUNPEMBELIANTUNAI = "";
    public static String AKUNPENJUALANTUNAI = "";
    public static String NAMAAKUNPENJUALANTUNAI = "";
    public static String AKUNKAS = "";
    public static String NAMAAKUNKAS = "";
    public static String AKUNDISKONPEMBELIAN = "";
    public static String NAMAAKUNDISKONPEMBELIAN = "";
    public static String AKUNDISKONPENJUALAN = "";
    public static String NAMAAKUNDISKONPENJUALAN = "";
    public static String AKUNUANGMUKAPEMBELIAN = "";
    public static String NAMAAKUNUANGMUKAPEMBELIAN = "";
    public static String AKUNUANGMUKAPENJUALAN = "";
    public static String NAMAAKUNUANGMUKAPENJUALAN = "";
    public static String AKUNONGKOSKIRIMPEMBELIAN = "";
    public static String NAMAAKUNONGKOSKIRIMPEMBELIAN = "";
    public static String AKUNONGKOSKIRIMPENJUALAN = "";
    public static String NAMAAKUNONGKOSKIRIMPENJUALAN = "";
    public static String AKUNAPOTEKTUSLAH = "";
    public static String AKUNAPOTEKEMBALASE = "";
    public static String DEFAULT_ID_SATUAN = "";
    public static String DEFAULT_NAMA_SATUAN = "";
    public static String DEFAULT_ID_LOKASI = "";
    public static String DEFAULT_NAMA_LOKASI = "";
    public static String DEFAULT_ID_GUDANG = "";
    public static String DEFAULT_NAMA_GUDANG = "";
    public static String DEFAULT_DISKON_DALAM = "";
    public static String DEFAULT_ID_PAJAK = "";
    public static String DEFAULT_KODE_PAJAK = "";
    public static String DEFAULT_ID_SERVICE = "";
    public static String DEFAULT_KODE_SERVICE = "";
    public static String DEFAULT_ID_KELOMPOK = "";
    public static String DEFAULT_NAMA_KELOMPOK = "";
    public static String DEFAULT_ID_PENGGUNA = "";
    public static String DEFAULT_NAMA_PENGGUNA = "";
    public static String POS_BarcodeMenggunakanSerial = "";
    public static String POS_BuatBarisBaru = "";
    public static String POS_GunakanPembulatan = "";
    public static String POS_HargaJualMinimum = "";
    public static String POS_HarusMenggunakanSalesman = "";
    public static String POS_PembulatanPer = "";
    public static String POS_TampilkanPiutangTotal = "";
    public static String POS_FooterStruk = "";
    public static String Pembelian_EditFakturTanpaMutasi = "";
    public static String Pembelian_HitungTotalBuatBarisBaru = "";
    public static String Pembelian_MasukkanPajakKeHargaPokok = "";
    public static String Pembelian_SetOrderSelesai = "";
    public static String Penjualan_PembHutangPiutangSimple = "";
    public static String Persediaan_BatasiPanjangSerial = "";
    public static String Persediaan_CekStockSebelumSimpan = "";
    public static String Persediaan_EdMenggunakanFIFO = "";
    public static String Persediaan_HPPdariHargaBeli = "";
    public static String Persediaan_OpnameGunakanAkunHPP = "";
    public static String Persediaan_OpnameGunakanAkunLain = "";
    public static String Persediaan_OpnameAkunLain = "";
    public static String Persediaan_PanjangSerial = "";
    public static String Persediaan_SerialIsDigit = "";
    public static String Persediaan_SerialUnique = "";
    public static String Penjualan_SetOrderSelesai = "";
    public static String Penjualan_HargaJualIncTaxService = "";
    public static String Global_DesimalDigit = "";
    public static String Global_PemisahTanggal = "";
    public static String Global_LongDateFormat = "";
    public static String Global_ShortDateFormat = "";

    public static String Jurnal_Umum = "";
    public static String Order_Penjualan = "";
    public static String Penjualan = "";
    public static String Retur_Penjualan = "";
    public static String Order_Pembelian = "";
    public static String Pembelian = "";
    public static String Retur_Pembelian = "";
    public static String Pembayaran_Piutang = "";
    public static String Pembayaran_Hutang = "";
    public static String PenyesuaianPersediaan = "";
    public static String StockOpname = "";
    public static String TransferPersediaan = "";
    public static String PerakitanPersediaan = "";
    public static String Kas_Masuk = "";
    public static String Kas_Keluar = "";
    public static String Data_Pelanggan = "";
    public static String Data_Karyawan = "";
    public static String Data_Rekan = "";
    public static String Data_Supplier = "";
    public static String Data_Barang = "";
    public static String Data_Gudang = "";
    public static String Data_Golongan_Pelanggan = "";
    public static String Data_Golongan_Karyawan = "";
    public static String Data_Kelompok_Barang = "";

    public static String id_company = "";
    public static String company_name = "";
    public static String alamat1 = "";
    public static String alamat2 = "";
    public static String telp = "";
    public static String id_dept_company = "";
    public static String nama_dept_company = "";
    public static String id_currency = "";
    public static String fax = "";
    public static String create_data_month = "";
    public static String create_data_year = "";
    public static String periode_month = "";
    public static String periode_year = "";
    public static String npwp = "";
    public static String pkp_tanggal = "";
    public static String pkp_alamat = "";
    public static String pkp_kota = "";
    public static String ttd_pajak_nama = "";
    public static String ttd_pajak_jabatan = "";
    public static String nama_currency = "";
    public static String id_currency_company = "";
    Properties prop = new Properties();
    public static String Setting_GudangDefaultnama = "";
    public static String Setting_DeptDefaultnama = "";
    public static String Setting_GudangDefault = "";
    public static String Setting_DeptDefault = "";
    public static String FilterDataPerDept = "";
    public static String Penjualan_PelangganUmumnama = "";
    public static String Penjualan_PelangganUmum = "";
    public static String Penjualan_FileFaktur = "";
    public static String Penjualan_FileRetur = "";
    public static String Penjualan_Printer = "";
    public static String Penjualan_DotMatrixPrinter = "";
    //public static String Penjualan_HargaJualIncTaxService = "";
    public static String POS_OtomatisCetakStruk = "";
    public static String POS_FileStruk = "";
    public static String POS_Printer = "";
    public static String POS_DotMatrixPrinter = "";
    public static String POS_ContinuousForm = "";
    public static String POS_AutoCutter = "";
    public static String POS_JarakFooter = "";
    public static String POS_UseCustomerDisplay = "";
    public static String POS_UseCashDrawerUSB = "";
    public static String POS_ScanBarcodeBuatBaru = "";
    public static String CetakFakturPilihFile = "";
    public static String POS_Tinggi_Grid_Header = "";
    public static String POS_Tinggi_Grid_Item = "";
    public static String POS_Font_Grid_Header = "";
    public static String POS_Font_Grid_Item = "";
    public static String POS_Tinggi_Panel = "";
    public static String POS_Lebar_Tombol = "";
    public static String POS_Tinggi_Tombol = "";
    public static String POS_Font_Tombol = "";

    //user
    public static String id_user = "";
    public static String nama_user = "";
    public static String keterangan_user = "";
    public static String dm_saldo_awal_akun = "";
    public static String dm_saldo_awal_persediaan = "";
    public static String dm_saldo_awal_hutang = "";
    public static String dm_saldo_awal_piutang = "";
    public static String dm_baru_edit_hapus = "";
    public static String dm_limitasi_gudang = "";
    public static String dm_limitasi_dept = "";
    public static String sistem_setup_program = "";
    public static String sistem_backup_data = "";
    public static String sistem_setting_pengguna = "";
    public static String sistem_sql_editor = "";
    public static String penjualan_order = "";
    public static String penjualan_order_input = "";
    public static String penjualan_order_edit = "";
    public static String penjualan_faktur = "";
    public static String penjualan_faktur_input = "";
    public static String penjualan_faktur_edit = "";
    public static String penjualan_faktur_kredit = "";
    public static String penjualan_faktur_tarik_order = "";
    public static String penjualan_faktur_rubah_harga = "";
    public static String penjualan_retur = "";
    public static String penjualan_retur_input = "";
    public static String penjualan_retur_edit = "";
    public static String penjualan_retur_kredit = "";
    public static String penjualan_piutang = "";
    public static String penjualan_piutang_input = "";
    public static String penjualan_piutang_edit = "";
    public static String penjualan_piutang_writeoff = "";
    public static String pembelian_order = "";
    public static String pembelian_order_input = "";
    public static String pembelian_order_edit = "";
    public static String pembelian_faktur = "";
    public static String pembelian_faktur_input = "";
    public static String pembelian_faktur_edit = "";
    public static String pembelian_faktur_kredit = "";
    public static String pembelian_faktur_tarik_order = "";
    public static String pembelian_retur = "";
    public static String pembelian_retur_input = "";
    public static String pembelian_retur_edit = "";
    public static String pembelian_retur_kredit = "";
    public static String pembelian_hutang = "";
    public static String pembelian_hutang_input = "";
    public static String pembelian_hutang_edit = "";
    public static String pembelian_hutang_writeoff = "";
    public static String persediaan_tampilkan_modal = "";
    public static String persediaan_rubah_harga_jual = "";
    public static String persediaan_stok_opname = "";
    public static String persediaan_perakitan = "";
    public static String persediaan_penyesuaian = "";
    public static String persediaan_penyesuaian_input = "";
    public static String persediaan_penyesuaian_edit = "";
    public static String persediaan_transfer = "";
    public static String persediaan_transfer_input = "";
    public static String persediaan_transfer_edit = "";
    public static String keuangan_kas_masuk = "";
    public static String keuangan_kas_masuk_input = "";
    public static String keuangan_kas_masuk_edit = "";
    public static String keuangan_kas_keluar = "";
    public static String keuangan_kas_keluar_input = "";
    public static String keuangan_kas_keluar_edit = "";
    public static String keuangan_transfer_kas = "";
    public static String keuangan_transfer_kas_input = "";
    public static String keuangan_transfer_kas_edit = "";
    public static String akuntansi_jurnal_umum = "";
    public static String akuntansi_jurnal_umum_input = "";
    public static String akuntansi_jurnal_umum_edit = "";
    public static String akuntansi_buku_besar = "";
    public static String akuntansi_setting_akun_penting = "";
    public static String akuntansi_tutup_buku = "";
    public static String akuntansi_hitung_ulang_saldo = "";
    public static String akuntansi_reposting = "";
    public static String akuntansi_ganti_periode = "";
    public static String laporan_kuangan = "";
    public static String laporan_buku_besar = "";
    public static String laporan_pembelian = "";
    public static String laporan_penjualan = "";
    public static String laporan_persediaan = "";

    public Globalsession(String id_user_aktif) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(Staticvar.url + "getglobalvarrest");
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod("GET");
            huc.connect();
            BufferedReader brdata = new BufferedReader(new InputStreamReader(huc.getInputStream()));
            String linedata = "";
            while ((linedata = brdata.readLine()) != null) {
                sb.append(linedata);
            }
            brdata.close();
            JSONParser jpdata = new JSONParser();
            Object objroot = jpdata.parse(sb.toString());
            JSONObject joroot = (JSONObject) objroot;

            Object objglobalvar = joroot.get("globalvarrest");
            JSONArray jaglobalvar = (JSONArray) objglobalvar;
            JSONObject joglobalvar = (JSONObject) jaglobalvar.get(0);
            DEFAULT_ID_PAJAK = String.valueOf(joglobalvar.get("default_id_pajak"));
            DEFAULT_KODE_PAJAK = String.valueOf(joglobalvar.get("default_kode_pajak"));
            DEFAULT_ID_SERVICE = String.valueOf(joglobalvar.get("default_id_service"));
            DEFAULT_KODE_SERVICE = String.valueOf(joglobalvar.get("default_kode_service"));
            DEFAULT_ID_KELOMPOK = String.valueOf(joglobalvar.get("default_id_kelompok"));
            DEFAULT_NAMA_KELOMPOK = String.valueOf(joglobalvar.get("default_nama_kelompok"));
            DEFAULT_JOB_ID = String.valueOf(joglobalvar.get("default_job_id"));
            DEFAULT_JOB_KODE = String.valueOf(joglobalvar.get("default_job_kode"));
            CURRENCYCODE = String.valueOf(joglobalvar.get("currency_kode"));
            CURRENCYNAME = String.valueOf(joglobalvar.get("currency_nama"));
            CURRENCYSYMBOL = String.valueOf(joglobalvar.get("currency_symbol"));
            AKUNLRPERIODE = String.valueOf(joglobalvar.get("akun_lr_periode"));
            AKUNLRTAHUNBERJALAN = String.valueOf(joglobalvar.get("akun_lr_tahun_berjalan"));
            AKUNLRDITAHAN = String.valueOf(joglobalvar.get("akun_lr_ditahan"));
            AKUNPENYEIMBANGNERACA = String.valueOf(joglobalvar.get("akun_penyeimbang_neraca"));
            AKUNHUTANGUSAHA = String.valueOf(joglobalvar.get("akun_hutang_usaha"));
            NAMAAKUNHUTANGUSAHA = String.valueOf(joglobalvar.get("nama_akun_hutang_usaha"));
            NAMAAKUNPIUTANGUSAHA = String.valueOf(joglobalvar.get("nama_akun_piutang_usaha"));
            AKUNHUTANGGIRO = String.valueOf(joglobalvar.get("akun_hutang_giro"));
            NAMAAKUNHUTANGGIRO = String.valueOf(joglobalvar.get("nama_akun_hutang_giro"));
            AKUNPIUTANGUSAHA = String.valueOf(joglobalvar.get("akun_piutang_usaha"));
            AKUNPIUTANGGIRO = String.valueOf(joglobalvar.get("akun_piutang_giro"));
            NAMAAKUNPIUTANGGIRO = String.valueOf(joglobalvar.get("nama_akun_piutang_giro"));
            AKUNPEMBELIANTUNAI = String.valueOf(joglobalvar.get("akun_pembelian_tunai"));
            NAMAAKUNPEMBELIANTUNAI = String.valueOf(joglobalvar.get("nama_akun_pembelian_tunai"));
            AKUNPENJUALANTUNAI = String.valueOf(joglobalvar.get("akun_penjualan_tunai"));
            NAMAAKUNPENJUALANTUNAI = String.valueOf(joglobalvar.get("nama_akun_penjualan_tunai"));
            AKUNKAS = String.valueOf(joglobalvar.get("akun_kas"));
            NAMAAKUNKAS = String.valueOf(joglobalvar.get("nama_akun_kas"));
            AKUNDISKONPEMBELIAN = String.valueOf(joglobalvar.get("akun_diskon_pembelian"));
            NAMAAKUNDISKONPEMBELIAN = String.valueOf(joglobalvar.get("nama_akun_diskon_pembelian"));
            AKUNDISKONPENJUALAN = String.valueOf(joglobalvar.get("akun_diskon_penjualan"));
            NAMAAKUNDISKONPENJUALAN = String.valueOf(joglobalvar.get("nama_akun_diskon_penjualan"));
            AKUNUANGMUKAPEMBELIAN = String.valueOf(joglobalvar.get("akun_uang_muka_pembelian"));
            NAMAAKUNUANGMUKAPEMBELIAN = String.valueOf(joglobalvar.get("nama_akun_uang_muka_pembelian"));
            AKUNUANGMUKAPENJUALAN = String.valueOf(joglobalvar.get("akun_uang_muka_penjualan"));
            NAMAAKUNUANGMUKAPENJUALAN = String.valueOf(joglobalvar.get("nama_akun_uang_muka_penjualan"));
            AKUNONGKOSKIRIMPEMBELIAN = String.valueOf(joglobalvar.get("akun_ongkos_kirim_pembelian"));
            NAMAAKUNONGKOSKIRIMPEMBELIAN = String.valueOf(joglobalvar.get("nama_akun_ongkos_kirim_pembelian"));
            AKUNONGKOSKIRIMPENJUALAN = String.valueOf(joglobalvar.get("akun_ongkos_kirim_penjualan"));
            NAMAAKUNONGKOSKIRIMPENJUALAN = String.valueOf(joglobalvar.get("nama_akun_ongkos_kirim_penjualan"));
            AKUNAPOTEKTUSLAH = String.valueOf(joglobalvar.get("akun_apotek_tuslah"));
            AKUNAPOTEKEMBALASE = String.valueOf(joglobalvar.get("akun_apotek_embalase"));
            DEFAULT_ID_SATUAN = String.valueOf(joglobalvar.get("default_id_satuan"));
            DEFAULT_NAMA_SATUAN = String.valueOf(joglobalvar.get("default_nama_satuan"));
            DEFAULT_ID_LOKASI = String.valueOf(joglobalvar.get("default_id_lokasi"));
            DEFAULT_NAMA_LOKASI = String.valueOf(joglobalvar.get("default_nama_lokasi"));
            DEFAULT_ID_GUDANG = String.valueOf(joglobalvar.get("default_id_gudang"));
            DEFAULT_NAMA_GUDANG = String.valueOf(joglobalvar.get("default_nama_gudang"));
            DEFAULT_DISKON_DALAM = String.valueOf(joglobalvar.get("default_diskon_dalam"));
            DEFAULT_ID_PENGGUNA = String.valueOf(joglobalvar.get("default_id_pengguna"));
            DEFAULT_NAMA_PENGGUNA = String.valueOf(joglobalvar.get("default_nama_pengguna"));

            //loadsetup dari db
            Object objsetup = joroot.get("setupprogram");
            JSONArray jasetup = (JSONArray) objsetup;
            for (int i = 0;
                 i < jasetup.size();
                 i++) {
                JSONObject joindata = (JSONObject) jasetup.get(i);
                String nama = String.valueOf(joindata.get("nama"));
                String nilai = String.valueOf(joindata.get("nilai"));
                switch (nama) {
                    case "POS_BarcodeMenggunakanSerial":
                        POS_BarcodeMenggunakanSerial = nilai;
                        break;
                    case "POS_BuatBarisBaru":
                        POS_BuatBarisBaru = nilai;
                        break;
                    case "POS_GunakanPembulatan":
                        POS_GunakanPembulatan = nilai;
                        break;
                    case "POS_HargaJualMinimum":
                        POS_HargaJualMinimum = nilai;
                        break;
                    case "POS_HarusMenggunakanSalesman":
                        POS_HarusMenggunakanSalesman = nilai;
                        break;
                    case "POS_PembulatanPer":
                        POS_PembulatanPer = nilai;
                        break;
                    case "POS_TampilkanPiutangTotal":
                        POS_TampilkanPiutangTotal = nilai;
                        break;
                    case "POS_FooterStruk":
                        POS_FooterStruk = nilai;
                        break;
                    case "Pembelian_EditFakturTanpaMutasi":
                        Pembelian_EditFakturTanpaMutasi = nilai;
                        break;
                    case "Pembelian_HitungTotalBuatBarisBaru":
                        Pembelian_HitungTotalBuatBarisBaru = nilai;
                        break;
                    case "Pembelian_MasukkanPajakKeHargaPokok":
                        Pembelian_MasukkanPajakKeHargaPokok = nilai;
                        break;
                    case "Pembelian_SetOrderSelesai":
                        Pembelian_SetOrderSelesai = nilai;
                        break;
                    case "Penjualan_PembHutangPiutangSimple":
                        Penjualan_PembHutangPiutangSimple = nilai;
                        break;

                    case "Persediaan_BatasiPanjangSerial":
                        Persediaan_BatasiPanjangSerial = nilai;
                        break;
                    case "Persediaan_CekStockSebelumSimpan":
                        Persediaan_CekStockSebelumSimpan = nilai;
                        break;
                    case "Persediaan_EdMenggunakanFIFO":
                        Persediaan_EdMenggunakanFIFO = nilai;
                        break;

                    case "Persediaan_HPPdariHargaBeli":
                        Persediaan_HPPdariHargaBeli = nilai;
                        break;
                    case "Persediaan_OpnameGunakanAkunHPP":
                        Persediaan_OpnameGunakanAkunHPP = nilai;
                        break;
                    case "Persediaan_OpnameGunakanAkunLain":
                        Persediaan_OpnameGunakanAkunLain = nilai;
                        break;
                    case "Persediaan_OpnameAkunLain":
                        Persediaan_OpnameAkunLain = nilai;
                        break;
                    case "Persediaan_PanjangSerial":
                        Persediaan_PanjangSerial = nilai;
                        break;
                    case "Persediaan_SerialIsDigit":
                        Persediaan_SerialIsDigit = nilai;
                        break;
                    case "Persediaan_SerialUnique":
                        Persediaan_SerialUnique = nilai;
                        break;
                    case "Penjualan_SetOrderSelesai":
                        Penjualan_SetOrderSelesai = nilai;
                        break;
                    case "Penjualan_HargaJualIncTaxService":
                        Penjualan_HargaJualIncTaxService = "";
                        break;
                    case "Global_DesimalDigit":
                        Global_DesimalDigit = nilai;
                        break;
                    case "Global_PemisahTanggal":
                        Global_PemisahTanggal = nilai;
                        break;
                    case "Global_LongDateFormat":
                        Global_LongDateFormat = nilai;
                        break;
                    case "Global_ShortDateFormat":
                        Global_ShortDateFormat = nilai;
                        break;
                    default:
                        break;
                }
            }

            Object objprefix = joroot.get("prefix");
            JSONArray japrefix = (JSONArray) objprefix;
            for (int i = 0;
                 i < japrefix.size();
                 i++) {
                JSONObject joindata = (JSONObject) japrefix.get(i);
                String nama = String.valueOf(joindata.get("nama"));
                String prefix = String.valueOf(joindata.get("prefix"));
                switch (nama) {
                    case "Jurnal Umum":
                        Jurnal_Umum = prefix;
                        break;
                    case "Order Penjualan":
                        Order_Penjualan = prefix;
                        break;
                    case "Penjualan":
                        Penjualan = prefix;
                        break;
                    case "Retur Penjualan":
                        Retur_Penjualan = prefix;
                        break;
                    case "Pembayaran Piutang":
                        Pembayaran_Piutang = prefix;
                        break;
                    case "Order Pembelian":
                        Order_Pembelian = prefix;
                        break;
                    case "Pembelian":
                        Pembelian = prefix;
                        break;
                    case "Retur Pembelian":
                        Retur_Pembelian = prefix;
                        break;
                    case "Pembayaran Hutang":
                        Pembayaran_Hutang = prefix;
                        break;
                    case "PenyesuaianPersediaan":
                        PenyesuaianPersediaan = prefix;
                        break;
                    case "StockOpname":
                        StockOpname = prefix;
                        break;
                    case "TransferPersediaan":
                        TransferPersediaan = prefix;
                        break;
                    case "PerakitanPersediaan":
                        PerakitanPersediaan = prefix;
                        break;
                    case "Kas Masuk":
                        Kas_Masuk = prefix;
                        break;
                    case "Kas Keluar":
                        Kas_Keluar = prefix;
                        break;
                    case "Data Pelanggan":
                        Data_Pelanggan = prefix;
                        break;
                    case "Data Karyawan":
                        Data_Karyawan = prefix;
                        break;
                    case "Data Rekan":
                        Data_Rekan = prefix;
                        break;
                    case "Data Supplier":
                        Data_Supplier = prefix;
                        break;
                    case "Data Barang":
                        Data_Barang = prefix;
                        break;
                    case "Data Gudang":
                        Data_Gudang = prefix;
                        break;
                    case "Data Golongan Pelanggan":
                        Data_Golongan_Pelanggan = prefix;
                        break;
                    case "Data Golongan Karyawan":
                        Data_Golongan_Karyawan = prefix;
                        break;
                    case "Data Kelompok Barang":
                        Data_Kelompok_Barang = prefix;
                        break;
                    default:
                        break;
                }
            }

            //company
            Object objcompany = joroot.get("company");
            JSONArray jacompany = (JSONArray) objcompany;

            for (int i = 0; i < jacompany.size(); i++) {
                JSONObject joindata = (JSONObject) jacompany.get(i);
                id_company = String.valueOf(joindata.get("id"));
                company_name = String.valueOf(joindata.get("company_name"));
                alamat1 = String.valueOf(joindata.get("alamat1"));
                alamat2 = String.valueOf(joindata.get("alamat2"));
                telp = String.valueOf(joindata.get("telp"));
                id_dept_company = String.valueOf(joindata.get("id_dept"));
                nama_dept_company = String.valueOf(joindata.get("nama_dept"));
                id_currency = String.valueOf(joindata.get("id_currency"));
                fax = String.valueOf(joindata.get("fax"));
                create_data_month = String.valueOf(Integer.parseInt(String.valueOf(joindata.get("create_data_month"))));
                create_data_month = String.valueOf(joindata.get("create_data_year"));
                periode_month = String.valueOf(String.valueOf(joindata.get("periode_month")));
                periode_year = String.valueOf(joindata.get("periode_year"));
                npwp = String.valueOf(joindata.get("npwp"));
                pkp_tanggal = String.valueOf(joindata.get("pkp_tanggal"));
                pkp_alamat = String.valueOf(joindata.get("pkp_alamat"));
                pkp_kota = String.valueOf(joindata.get("pkp_kota"));
                ttd_pajak_nama = String.valueOf(joindata.get("ttd_pajak_nama"));
                ttd_pajak_jabatan = String.valueOf(joindata.get("ttd_pajak_jabatan"));
                nama_currency = String.valueOf(joindata.get("nama_currency"));
                id_currency_company = String.valueOf(joindata.get("id_currency"));

            }

            loadconfigprop();
            loaddatahakakses(id_user_aktif);

        } catch (MalformedURLException ex) {
            Logger.getLogger(Globalsession.class
                 .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(Globalsession.class
                 .getName()).log(Level.SEVERE, null, ex);

        } catch (ParseException ex) {
            Logger.getLogger(Globalsession.class
                 .getName()).log(Level.SEVERE, null, ex);
        }

        //load data from file
    }

    private void loadconfigprop() {
        try {
            InputStream ins = new FileInputStream(new File("config.properties"));
            prop.load(ins);
            //setup umum
            Setting_GudangDefaultnama = prop.getProperty("Setting_GudangDefaultnama");
            Setting_DeptDefaultnama = prop.getProperty("Setting_DeptDefaultnama");
            Setting_GudangDefault = prop.getProperty("Setting_GudangDefault");
            Setting_DeptDefault = prop.getProperty("Setting_DeptDefault");
            FilterDataPerDept = prop.getProperty("FilterDataPerDept");
            CetakFakturPilihFile = prop.getProperty("FilterDataPerDept");

            //[PEMBELIAN]
            //[PENJUALAN]
            Penjualan_PelangganUmumnama = prop.getProperty("Penjualan_PelangganUmumnama");
            Penjualan_PelangganUmum = prop.getProperty("Penjualan_PelangganUmum");
            Penjualan_FileFaktur = prop.getProperty("Penjualan_FileFaktur");
            Penjualan_FileRetur = prop.getProperty("Penjualan_FileRetur");
            Penjualan_Printer = prop.getProperty("Penjualan_Printer");
            Penjualan_DotMatrixPrinter = prop.getProperty("Penjualan_DotMatrixPrinter");
            Penjualan_HargaJualIncTaxService = prop.getProperty("Penjualan_HargaJualIncTaxService");
            //[POS]
            POS_OtomatisCetakStruk = prop.getProperty("POS_OtomatisCetakStruk");
            POS_FileStruk = prop.getProperty("POS_FileStruk");
            POS_Printer = prop.getProperty("POS_Printer");
            POS_DotMatrixPrinter = prop.getProperty("POS_DotMatrixPrinter");
            POS_ContinuousForm = prop.getProperty("POS_ContinuousForm");
            POS_AutoCutter = prop.getProperty("POS_AutoCutter");
            POS_JarakFooter = prop.getProperty("POS_JarakFooter");
            POS_UseCustomerDisplay = prop.getProperty("POS_UseCustomerDisplay");
            POS_UseCashDrawerUSB = prop.getProperty("POS_UseCashDrawerUSB");
            POS_ScanBarcodeBuatBaru = prop.getProperty("POS_ScanBarcodeBuatBaru");

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
            POS_Tinggi_Grid_Header = prop.getProperty("POS_Tinggi_Grid_Header");
            POS_Tinggi_Grid_Item = prop.getProperty("POS_Tinggi_Grid_Item");
            POS_Font_Grid_Header = prop.getProperty("POS_Font_Grid_Header");
            POS_Font_Grid_Item = prop.getProperty("POS_Font_Grid_Item");
            POS_Tinggi_Panel = prop.getProperty("POS_Tinggi_Panel");
            POS_Lebar_Tombol = prop.getProperty("POS_Lebar_Tombol");
            POS_Tinggi_Tombol = prop.getProperty("POS_Tinggi_Tombol");
            POS_Font_Tombol = prop.getProperty("POS_Font_Tombol");

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Globalsession.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Globalsession.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loaddatahakakses(String id_user_aktif) {
        StringBuilder sb = new StringBuilder();
        String param = "id=" + id_user_aktif;
        try {
            URL url = new URL(Staticvar.url + "dm/datapengguna");
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod("POST");
            huc.setDoInput(true);
            huc.setDoOutput(true);
            huc.connect();
            OutputStream os = huc.getOutputStream();
            os.write(param.getBytes());
            BufferedReader brdata = new BufferedReader(new InputStreamReader(huc.getInputStream()));
            String linedata = "";
            while ((linedata = brdata.readLine()) != null) {
                sb.append(linedata);
            }
            brdata.close();
            JSONParser jpdata = new JSONParser();
            Object objdata = jpdata.parse(sb.toString());
            JSONArray jasetup = (JSONArray) objdata;
            for (int i = 0; i < jasetup.size(); i++) {
                JSONObject jo = (JSONObject) jasetup.get(i);
                id_user = String.valueOf(jo.get("id"));
                nama_user = String.valueOf(jo.get("nama"));
                keterangan_user = String.valueOf(jo.get("keterangan"));
                dm_saldo_awal_akun = String.valueOf(jo.get("dm_saldo_awal_akun"));
                dm_saldo_awal_persediaan = String.valueOf(jo.get("dm_saldo_awal_persediaan"));
                dm_saldo_awal_hutang = String.valueOf(jo.get("dm_saldo_awal_hutang"));
                dm_saldo_awal_piutang = String.valueOf(jo.get("dm_saldo_awal_piutang"));
                dm_baru_edit_hapus = String.valueOf(jo.get("dm_baru_edit_hapus"));
                dm_limitasi_gudang = String.valueOf(jo.get("dm_limitasi_gudang"));
                dm_limitasi_dept = String.valueOf(jo.get("dm_limitasi_dept"));
                sistem_setup_program = String.valueOf(jo.get("sistem_setup_program"));
                sistem_backup_data = String.valueOf(jo.get("sistem_backup_data"));
                sistem_setting_pengguna = String.valueOf(jo.get("sistem_setting_pengguna"));
                sistem_sql_editor = String.valueOf(jo.get("sistem_sql_editor"));
                penjualan_order = String.valueOf(jo.get("penjualan_order"));
                penjualan_order_input = String.valueOf(jo.get("penjualan_order_input"));
                penjualan_order_edit = String.valueOf(jo.get("penjualan_order_edit"));
                penjualan_faktur = String.valueOf(jo.get("penjualan_faktur"));
                penjualan_faktur_input = String.valueOf(jo.get("penjualan_faktur_input"));
                penjualan_faktur_edit = String.valueOf(jo.get("penjualan_faktur_edit"));
                penjualan_faktur_kredit = String.valueOf(jo.get("penjualan_faktur_kredit"));
                penjualan_faktur_tarik_order = String.valueOf(jo.get("penjualan_faktur_tarik_order"));
                penjualan_faktur_rubah_harga = String.valueOf(jo.get("penjualan_faktur_rubah_harga"));
                penjualan_retur = String.valueOf(jo.get("penjualan_retur"));
                penjualan_retur_input = String.valueOf(jo.get("penjualan_retur_input"));
                penjualan_retur_edit = String.valueOf(jo.get("penjualan_retur_edit"));
                penjualan_retur_kredit = String.valueOf(jo.get("penjualan_retur_kredit"));
                penjualan_piutang = String.valueOf(jo.get("penjualan_piutang"));
                penjualan_piutang_input = String.valueOf(jo.get("penjualan_piutang_input"));
                penjualan_piutang_edit = String.valueOf(jo.get("penjualan_piutang_edit"));
                penjualan_piutang_writeoff = String.valueOf(jo.get("penjualan_piutang_writeoff"));
                pembelian_order = String.valueOf(jo.get("pembelian_order"));
                pembelian_order_input = String.valueOf(jo.get("pembelian_order_input"));
                pembelian_order_edit = String.valueOf(jo.get("pembelian_order_edit"));
                pembelian_faktur = String.valueOf(jo.get("pembelian_faktur"));
                pembelian_faktur_input = String.valueOf(jo.get("pembelian_faktur_input"));
                pembelian_faktur_edit = String.valueOf(jo.get("pembelian_faktur_edit"));
                pembelian_faktur_kredit = String.valueOf(jo.get("pembelian_faktur_kredit"));
                pembelian_faktur_tarik_order = String.valueOf(jo.get("pembelian_faktur_tarik_order"));
                pembelian_retur = String.valueOf(jo.get("pembelian_retur"));
                pembelian_retur_input = String.valueOf(jo.get("pembelian_retur_input"));
                pembelian_retur_edit = String.valueOf(jo.get("pembelian_retur_edit"));
                pembelian_retur_kredit = String.valueOf(jo.get("pembelian_retur_kredit"));
                pembelian_hutang = String.valueOf(jo.get("pembelian_hutang"));
                pembelian_hutang_input = String.valueOf(jo.get("pembelian_hutang_input"));
                pembelian_hutang_edit = String.valueOf(jo.get("pembelian_hutang_edit"));
                pembelian_hutang_writeoff = String.valueOf(jo.get("pembelian_hutang_writeoff"));
                persediaan_tampilkan_modal = String.valueOf(jo.get("persediaan_tampilkan_modal"));
                persediaan_rubah_harga_jual = String.valueOf(jo.get("persediaan_rubah_harga_jual"));
                persediaan_stok_opname = String.valueOf(jo.get("persediaan_stok_opname"));
                persediaan_perakitan = String.valueOf(jo.get("persediaan_perakitan"));
                persediaan_penyesuaian = String.valueOf(jo.get("persediaan_penyesuaian"));
                persediaan_penyesuaian_input = String.valueOf(jo.get("persediaan_penyesuaian_input"));
                persediaan_penyesuaian_edit = String.valueOf(jo.get("persediaan_penyesuaian_edit"));
                persediaan_transfer = String.valueOf(jo.get("persediaan_transfer"));
                persediaan_transfer_input = String.valueOf(jo.get("persediaan_transfer_input"));
                persediaan_transfer_edit = String.valueOf(jo.get("persediaan_transfer_edit"));
                keuangan_kas_masuk = String.valueOf(jo.get("keuangan_kas_masuk"));
                keuangan_kas_masuk_input = String.valueOf(jo.get("keuangan_kas_masuk_input"));
                keuangan_kas_masuk_edit = String.valueOf(jo.get("keuangan_kas_masuk_edit"));
                keuangan_kas_keluar = String.valueOf(jo.get("keuangan_kas_keluar"));
                keuangan_kas_keluar_input = String.valueOf(jo.get("keuangan_kas_keluar_input"));
                keuangan_kas_keluar_edit = String.valueOf(jo.get("keuangan_kas_keluar_edit"));
                keuangan_transfer_kas = String.valueOf(jo.get("keuangan_transfer_kas"));
                keuangan_transfer_kas_input = String.valueOf(jo.get("keuangan_transfer_kas_input"));
                keuangan_transfer_kas_edit = String.valueOf(jo.get("keuangan_transfer_kas_edit"));
                akuntansi_jurnal_umum = String.valueOf(jo.get("akuntansi_jurnal_umum"));
                akuntansi_jurnal_umum_input = String.valueOf(jo.get("akuntansi_jurnal_umum_input"));
                akuntansi_jurnal_umum_edit = String.valueOf(jo.get("akuntansi_jurnal_umum_edit"));
                akuntansi_buku_besar = String.valueOf(jo.get("akuntansi_buku_besar"));
                akuntansi_setting_akun_penting = String.valueOf(jo.get("akuntansi_setting_akun_penting"));
                akuntansi_tutup_buku = String.valueOf(jo.get("akuntansi_tutup_buku"));
                akuntansi_hitung_ulang_saldo = String.valueOf(jo.get("akuntansi_hitung_ulang_saldo"));
                akuntansi_reposting = String.valueOf(jo.get("akuntansi_reposting"));
                akuntansi_ganti_periode = String.valueOf(jo.get("akuntansi_ganti_periode"));
                laporan_kuangan = String.valueOf(jo.get("laporan_kuangan"));
                laporan_buku_besar = String.valueOf(jo.get("laporan_buku_besar"));
                laporan_pembelian = String.valueOf(jo.get("laporan_pembelian"));
                laporan_penjualan = String.valueOf(jo.get("laporan_penjualan"));
                laporan_persediaan = String.valueOf(jo.get("laporan_persediaan"));

            }
        } catch (ParseException ex) {
            Logger.getLogger(Globalsession.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Globalsession.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Globalsession.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
