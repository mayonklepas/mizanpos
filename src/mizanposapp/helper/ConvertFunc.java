/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.helper;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Minami
 */
public class ConvertFunc {

    public ConvertFunc() {
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
            Logger.getLogger(ConvertFunc.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ConvertFunc.class.getName()).log(Level.SEVERE, null, ex);
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
}
