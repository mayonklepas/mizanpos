/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.helper;

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

}
