/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.helper;

import java.awt.Color;
import java.awt.Font;
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
    public static String url = "http://192.168.1.45:556/";
    //public static String url = "http://172619802169.ip-dynamic.com:556/";
    //public static String url = "http://198.50.174.114:556/";
    public static ArrayList<Integer> stylecolum = new ArrayList<>();
    public static String getresult = "";
    public static HashMap map_var = new HashMap();
    public static String ids = "";
    public static String idsextend_1 = "";
    public static String idsextend_2 = "";
    public static String idsextend_3 = "";
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
    public static int rowfokus = 0;
    public static int rowfokusext = 0;
    public static String frame = "";
    public static Color globaltablecolor = Color.decode("#F2F2F2");
    public static Color globaltablecolorselect = Color.decode("#3399FF");
    public static Color globaltablecolorheader = Color.decode("#F2F2F2");
    public static Color globaltablecolortextheader = Color.decode("#F2F2F2");
    public static String fonttype = "Segoe UI";
    public static int fontstyle = Font.PLAIN;
    public static int fontsize = 12;
    public static String keyholdnumeric[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9",
        "0", "NUMPAD1", "NUMPAD2", "NUMPAD3", "NUMPAD4", "NUMPAD5", "NUMPAD6", "NUMPAD7", "NUMPAD8", "NUMPAD9",
        "NUMPAD0", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
        "q", "r", "s", "t", "u", "p", "w", "x", "y", "z", "A", "B", "C", "D",
        "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
        "S", "T", "U", "P", "W", "X", "Y", "Z", "BACK_SPACE", "SLASH", "BACK_SLASH", "ADD", "MULTIPLY", "EQUALS",
        "COMMA", "ASTERISK", "SUBTRACT", "UNDERSCORE", "SEPARATOR", "SEMICOLON", "COLON", "RIGHT_PARENTHESIS",
        "LEFT_PARENTHESIS", "QUOTEDBL", "QUOTE", "BACK_QUOTE", "OPEN_BRACKET", "CLOSE_BRACKET", "PLUS", "MINUS",
        "PERIOD", "NUMBER_SIGN", "GREATER", "BRACELEFT", "BRACERIGHT"};

}
