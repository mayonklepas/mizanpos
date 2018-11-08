/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.helper;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Minami
 */
public class Headersize {

    JTable tb;
    ArrayList<Integer> lssize = new ArrayList<>();

    public Headersize(JTable tb, ArrayList<Integer> lssize) {
        this.tb = tb;
        this.lssize = lssize;
    }
}
