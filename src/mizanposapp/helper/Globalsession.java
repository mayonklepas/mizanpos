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

    public Globalsession() {
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
                        Persediaan_OpnameAkunLain = nilai.split("#")[0];
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
                create_data_month = String.valueOf(Integer.parseInt(String.valueOf(joindata.get("create_data_month"))) - 1);
                create_data_month = String.valueOf(joindata.get("create_data_year"));
                periode_month = String.valueOf(Integer.parseInt(String.valueOf(joindata.get("periode_month"))) - 1);
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

}
