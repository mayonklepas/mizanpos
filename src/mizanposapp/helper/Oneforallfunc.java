/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.helper;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Minami
 */
public class Oneforallfunc {

    public Oneforallfunc() {
    }

    public static int intparsing(String val) {
        int ret = 0;
        String value = String.valueOf(val);
        try {
            if (value.equals("") || value.equals("null")) {
                ret = 0;
            } else {
                ret = Integer.parseInt(value);
            }
        } catch (Exception e) {
            ret = 0;
        }

        return ret;
    }

    public static double doubleparsing(String val) {
        double ret = 0;
        String value = String.valueOf(val);
        try {
            if (value.equals("") || value.equals("null")) {
                ret = 0;
            } else {
                ret = Double.parseDouble(value);
            }
        } catch (Exception e) {
            ret = 0;
        }

        return ret;
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
            Logger.getLogger(Oneforallfunc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public static double ToInt(String str) {
        int result = 0;
        try {
            result = Integer.parseInt(str.replace(",", ""));
        } catch (Exception e) {
            result = 0;
        }
        return result;
    }

    public static double ToInt(Object obj) {
        int result = 0;
        try {
            result = Integer.parseInt(String.valueOf(obj).replace(",", ""));
        } catch (Exception ex) {
            result = 0;
            Logger.getLogger(Oneforallfunc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
