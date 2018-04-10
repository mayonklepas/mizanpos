/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp;

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
        /*try {
            // TODO code application logic here
            UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Mizanposapp.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        new CrudHelper();
        new MainmenuController();
        new Globalsession();

    }

}
