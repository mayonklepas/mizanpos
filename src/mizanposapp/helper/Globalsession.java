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
            NAMA_PERUSAHAAN = String.valueOf(jodata.get("NAMA_PERUSAHAAN"));
            TAHUN_AWAL_DATA = String.valueOf(jodata.get("TAHUN_AWAL_DATA"));
            BULAN_AWAL_DATA = String.valueOf(jodata.get("BULAN_AWAL_DATA"));
            PERIODE_TAHUN = String.valueOf(jodata.get("PERIODE_TAHUN"));
            PERIODE_BULAN = String.valueOf(jodata.get("PERIODE_BULAN"));
            PERIODE_AKHIR_BULAN = String.valueOf(jodata.get("PERIODE_AKHIR_BULAN"));
            DEFAULT_CURRENCY_ID = String.valueOf(jodata.get("DEFAULT_CURRENCY_ID"));
            DEFAULT_DEPT_ID = String.valueOf(jodata.get("DEFAULT_DEPT_ID"));
            DEFAULT_DEPT_NAME = String.valueOf(jodata.get("DEFAULT_DEPT_NAME"));
            DEFAULT_JOB_ID = String.valueOf(jodata.get("DEFAULT_JOB_ID"));
            DEFAULT_JOB_KODE = String.valueOf(jodata.get("DEFAULT_JOB_KODE"));
            CURRENCYCODE = String.valueOf(jodata.get("CURRENCYCODE"));
            CURRENCYNAME = String.valueOf(jodata.get("CURRENCYNAME"));
            CURRENCYSYMBOL = String.valueOf(jodata.get("CURRENCYSYMBOL"));
            AKUNLRPERIODE = String.valueOf(jodata.get("AKUNLRPERIODE"));
            AKUNLRTAHUNBERJALAN = String.valueOf(jodata.get("AKUNLRTAHUNBERJALAN"));
            AKUNLRDITAHAN = String.valueOf(jodata.get("AKUNLRDITAHAN"));
            AKUNPENYEIMBANGNERACA = String.valueOf(jodata.get("AKUNPENYEIMBANGNERACA"));
            AKUNHUTANGUSAHA = String.valueOf(jodata.get("AKUNHUTANGUSAHA"));
            AKUNHUTANGGIRO = String.valueOf(jodata.get("AKUNHUTANGGIRO"));
            AKUNPIUTANGUSAHA = String.valueOf(jodata.get("AKUNPIUTANGUSAHA"));
            AKUNPIUTANGGIRO = String.valueOf(jodata.get("AKUNPIUTANGGIRO"));
            AKUNPEMBELIANTUNAI = String.valueOf(jodata.get("AKUNPEMBELIANTUNAI"));
            AKUNPENJUALANTUNAI = String.valueOf(jodata.get("AKUNPENJUALANTUNAI"));
            AKUNKAS = String.valueOf(jodata.get("AKUNKAS"));
            AKUNDISKONPEMBELIAN = String.valueOf(jodata.get("AKUNDISKONPEMBELIAN"));
            AKUNDISKONPENJUALAN = String.valueOf(jodata.get("AKUNDISKONPENJUALAN"));
            AKUNUANGMUKAPEMBELIAN = String.valueOf(jodata.get("AKUNUANGMUKAPEMBELIAN"));
            AKUNUANGMUKAPENJUALAN = String.valueOf(jodata.get("AKUNUANGMUKAPENJUALAN"));
            AKUNONGKOSKIRIMPEMBELIAN = String.valueOf(jodata.get("AKUNONGKOSKIRIMPEMBELIAN"));
            AKUNONGKOSKIRIMPENJUALAN = String.valueOf(jodata.get("AKUNONGKOSKIRIMPENJUALAN"));
            AKUNAPOTEKTUSLAH = String.valueOf(jodata.get("AKUNAPOTEKTUSLAH"));
            AKUNAPOTEKEMBALASE = String.valueOf(jodata.get("AKUNAPOTEKEMBALASE"));
        } catch (MalformedURLException ex) {
            Logger.getLogger(Globalsession.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Globalsession.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Globalsession.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
