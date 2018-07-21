/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.helper;

import java.util.ArrayList;
import java.util.HashMap;
import mizanposapp.view.Beranda_panel;
import mizanposapp.view.Laporan_panel;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.Pembelian_panel;
import mizanposapp.view.Penjualan_panel;
import mizanposapp.view.Persedian_panel;

/**
 *
 * @author Minami
 */
public class Staticvar {

    public static Penjualan_panel pp = null;
    public static Persedian_panel psp = null;
    public static Pembelian_panel pmp = null;
    public static Mainmenu mm = null;
    public static Laporan_panel lp = null;
    public static Beranda_panel bp = null;
    //public static String url = "http://192.168.1.45:556/";
    //public static String url = "http://172619802169.ip-dynamic.com:556/";
    public static String url = "http://198.50.174.114:556/";
    public static ArrayList<Integer> stylecolum = new ArrayList<>();
    public static String getresult = "";
    public static HashMap map_var = new HashMap();
    public static String ids = "";
    public static String preid = "";
    public static String prelabel = "";
    public static String prevalue = "";
    public static String prevalueextended = "";
    public static String resid = "";
    public static String reslabel = "";
    public static String resvalue = "";
    public static String resvalueextended = "";
    public static boolean isupdate = false;
    public static String sfilter = "";
    public static int lebarPanelMenu = 344;
    public static String frame = "";
}
