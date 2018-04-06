/*
 * To change this license header; choose License Headers in Project Properties.
 * To change this template file; choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

    public static String NAMA_PERUSAHAAN = "";
    public static String TAHUN_AWAL_DATA = "";
    public static String BULAN_AWAL_DATA = "";
    public static String PERIODE_TAHUN = "";
    public static String PERIODE_BULAN = "";
    public static String PERIODE_AKHIR_BULAN = "";
    public static String DEFAULT_CURRENCY_ID = "";
    public static String DEFAULT_DEPT_ID = "";
    public static String DEFAULT_DEPT_NAME = "";
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
    public static String AKUNHUTANGGIRO = "";
    public static String AKUNPIUTANGUSAHA = "";
    public static String AKUNPIUTANGGIRO = "";
    public static String AKUNPEMBELIANTUNAI = "";
    public static String AKUNPENJUALANTUNAI = "";
    public static String AKUNKAS = "";
    public static String AKUNDISKONPEMBELIAN = "";
    public static String AKUNDISKONPENJUALAN = "";
    public static String AKUNUANGMUKAPEMBELIAN = "";
    public static String AKUNUANGMUKAPENJUALAN = "";
    public static String AKUNONGKOSKIRIMPEMBELIAN = "";
    public static String AKUNONGKOSKIRIMPENJUALAN = "";
    public static String AKUNAPOTEKTUSLAH = "";
    public static String AKUNAPOTEKEMBALASE = "";
    public static String DEFAULT_ID_SATUAN = "";
    public static String DEFAULT_NAMA_SATUAN = "";
    public static String DEFAULT_ID_LOKASI = "";
    public static String DEFAULT_NAMA_LOKASI = "";
    public static String DEFAULT_ID_GUDANG = "";
    public static String DEFAULT_NAMA_GUDANG = "";

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
            Object objdata = jpdata.parse(sb.toString());
            JSONArray jadata = (JSONArray) objdata;
            JSONObject jodata = (JSONObject) jadata.get(0);
            NAMA_PERUSAHAAN = String.valueOf(jodata.get("nama_perusahaan"));
            TAHUN_AWAL_DATA = String.valueOf(jodata.get("tahun_awal_data"));
            BULAN_AWAL_DATA = String.valueOf(jodata.get("bulan_awal_data"));
            PERIODE_TAHUN = String.valueOf(jodata.get("periode_tahun"));
            PERIODE_BULAN = String.valueOf(jodata.get("periode_bulan"));
            PERIODE_AKHIR_BULAN = String.valueOf(jodata.get("periode_akhir_bulan"));
            DEFAULT_CURRENCY_ID = String.valueOf(jodata.get("default_currecy_id"));
            DEFAULT_DEPT_ID = String.valueOf(jodata.get("default_dept_id"));
            DEFAULT_DEPT_NAME = String.valueOf(jodata.get("default_dept_name"));
            DEFAULT_JOB_ID = String.valueOf(jodata.get("default_job_id"));
            DEFAULT_JOB_KODE = String.valueOf(jodata.get("default_job_kode"));
            CURRENCYCODE = String.valueOf(jodata.get("currency_code"));
            CURRENCYNAME = String.valueOf(jodata.get("currency_name"));
            CURRENCYSYMBOL = String.valueOf(jodata.get("currency_symbol"));
            AKUNLRPERIODE = String.valueOf(jodata.get("akun_lr_periode"));
            AKUNLRTAHUNBERJALAN = String.valueOf(jodata.get("akun_lr_tahun_berjalan"));
            AKUNLRDITAHAN = String.valueOf(jodata.get("akun_lr_ditahan"));
            AKUNPENYEIMBANGNERACA = String.valueOf(jodata.get("akun_penyeimbang_neraca"));
            AKUNHUTANGUSAHA = String.valueOf(jodata.get("akun_hutang_usaha"));
            AKUNHUTANGGIRO = String.valueOf(jodata.get("akun_hutang_biro"));
            AKUNPIUTANGUSAHA = String.valueOf(jodata.get("akun_piutang_usaha"));
            AKUNPIUTANGGIRO = String.valueOf(jodata.get("akun_piutang_giro"));
            AKUNPEMBELIANTUNAI = String.valueOf(jodata.get("akun_pembelian_tunai"));
            AKUNPENJUALANTUNAI = String.valueOf(jodata.get("akun_penjualan_tunai"));
            AKUNKAS = String.valueOf(jodata.get("akun_kas"));
            AKUNDISKONPEMBELIAN = String.valueOf(jodata.get("akun_diskon_pembelian"));
            AKUNDISKONPENJUALAN = String.valueOf(jodata.get("akun_diskon_penjualan"));
            AKUNUANGMUKAPEMBELIAN = String.valueOf(jodata.get("akun_uang_muka_pembelian"));
            AKUNUANGMUKAPENJUALAN = String.valueOf(jodata.get("akun_uang_muka_penjualan"));
            AKUNONGKOSKIRIMPEMBELIAN = String.valueOf(jodata.get("akun_ongkos_kirim_pembelian"));
            AKUNONGKOSKIRIMPENJUALAN = String.valueOf(jodata.get("akun_ongkos_kirim_penjualan"));
            AKUNAPOTEKTUSLAH = String.valueOf(jodata.get("akun_apotek_tuslah"));
            AKUNAPOTEKEMBALASE = String.valueOf(jodata.get("akun_apotek_embalase"));
            DEFAULT_ID_SATUAN = String.valueOf(jodata.get("default_id_satuan"));
            DEFAULT_NAMA_SATUAN = String.valueOf(jodata.get("default_nama_satuan"));
            DEFAULT_ID_LOKASI = String.valueOf(jodata.get("default_id_lokasi"));
            DEFAULT_NAMA_LOKASI = String.valueOf(jodata.get("default_nama_lokasi"));
            DEFAULT_ID_GUDANG = String.valueOf(jodata.get("default_id_gudang"));
            DEFAULT_NAMA_GUDANG = String.valueOf(jodata.get("default_nama_gudang"));

        } catch (MalformedURLException ex) {
            Logger.getLogger(Globalsession.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Globalsession.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Globalsession.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
