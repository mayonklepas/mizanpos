/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp;

import java.util.Locale;
import javax.swing.UIManager;
import mizanposapp.controller.MainmenuController;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Globalsession;

/**
 *
 * @author Minami
 */
public class Mizanposapp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Locale.setDefault(new Locale("en", "en_US"));
        new CrudHelper();
        new Globalsession();
        new MainmenuController();

    }

}
