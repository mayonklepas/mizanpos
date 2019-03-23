/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.helper;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import mizanposapp.controller.innerpanel.penjualan.PosframeController;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.frameform.Informasi;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class FuncHelper {

    CrudHelper ch = new CrudHelper();

    public FuncHelper() {
    }

    public static double ToDouble(String str) {
        double result = 0;
        try {
            result = Double.parseDouble(str.replace(",", ""));
        } catch (Exception e) {
            result = 0;
        }
        return result;
    }

    public static double ToDouble(Object obj) {
        double result = 0;
        try {
            result = Double.parseDouble(String.valueOf(obj).replace(",", ""));
        } catch (Exception ex) {
            result = 0;
            Logger.getLogger(FuncHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public static int ToInt(String str) {
        int result = 0;
        double d = 0;
        try {
            d = Double.parseDouble(str.replace(",", ""));
            result = (int) d;

            if ((d - result) > 0.5f) {
                result = result + 1;
            }
        } catch (Exception e) {
            result = 0;
        }
        return result;
    }

    public static int ToInt(Object obj) {
        int result = 0;
        double d = 0;
        try {
            d = Double.parseDouble(String.valueOf(obj).replace(",", ""));
            result = (int) d;

            if ((d - result) > 0.5f) {
                result = result + 1;
            }
        } catch (Exception ex) {
            result = 0;
            Logger.getLogger(FuncHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public static String EncodeString(String str) {
        String hasil = "";
        if (!str.isEmpty() || !str.equals("null") || str != null) {
            String a = str.replace("%", "%25");
            String b = a.replace("&", "%26");
            String c = b.replace("'", "%27%27");
            String d = c.replace("\"", "%27%27");
            hasil = d.replace("+", "%2B");
        }
        return hasil;
    }

    public static String ret(String data) {
        String hasil = data.replaceAll("||", "\n");
        return hasil;
    }

    public static String rounding(double value) {
        double scale = Math.pow(10, 2);
        double hasil = Math.round(value * scale) / scale;
        return String.valueOf(hasil);
    }

    public HashMap getkodetransaksi(String keltrans, Date tanggal, String iddept) {
        HashMap<String, String> hashmap = new HashMap<>();
        try {
            JSONParser jpdata = new JSONParser();
            String param = String.format("id_keltrans=%s&tahun=%s&bulan=%s&id_dept=%s",
                 keltrans,
                 new SimpleDateFormat("yyyy").format(tanggal),
                 new SimpleDateFormat("MM").format(tanggal),
                 iddept);
            Object ob = jpdata.parse(ch.getdatadetails("getnomortransaksi", param));
            JSONArray ja = (JSONArray) ob;
            for (int i = 0; i < ja.size(); i++) {
                JSONObject jo = (JSONObject) ja.get(i);
                hashmap.put("no_transaksi", String.valueOf(jo.get("no_transaksi")));
                hashmap.put("no_urut", String.valueOf(jo.get("no_urut")));
            }

        } catch (ParseException ex) {
            Logger.getLogger(PosframeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hashmap;
    }

    public void insertnogagal(String keltrans, Date tanggal, String iddept, String no_urut) {
        String data = String.format("id_keltrans=%s&tahun=%s&bulan=%s&id_dept=%s&no_urut=%s",
             keltrans,
             new SimpleDateFormat("yyyy").format(tanggal),
             new SimpleDateFormat("MM").format(tanggal),
             iddept,
             no_urut);
        ch.insertdata("insertnomorgagal", data);
    }

    public static double getpembulatan(double totalPenjualan) {
        double pembulatan = 0.0;
        if (Globalsession.POS_GunakanPembulatan.equals("1")) {
            pembulatan = Double.parseDouble(Globalsession.POS_PembulatanPer);
        }

        double sisa = 0;
        double result = 0;
        sisa = (totalPenjualan % pembulatan);
        if (totalPenjualan > 0) {
            if ((pembulatan - sisa) < pembulatan) {
                result = (pembulatan - sisa);
            }
        } else {
            if ((pembulatan + sisa) < pembulatan) {
                result = (-1 * (pembulatan + sisa));
            }
        }
        return result;
    }

    public static void showmessage(String header, String sub) {
        JDialog jd = new JDialog(new Mainmenu());
        jd.add(new Informasi(header, sub));
        jd.pack();
        jd.setLocationRelativeTo(null);
        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        jd.setTitle("Informasi");
        jd.setVisible(true);

    }

}
