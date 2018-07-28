/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.helper;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Minami
 */
@SuppressWarnings("serial")
public class Tablestyle {

    JTable tbl;
    Color fgcolor, bgcolor;

    public Tablestyle(JTable tbl) {
        this.tbl = tbl;
    }

    public void applystyleheader() {
        JTableHeader jthead = tbl.getTableHeader();
        jthead.setOpaque(false);
        jthead.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jthead.setPreferredSize(new Dimension(30, 30));
        jthead.setBackground(Color.decode("#282727"));
        jthead.setForeground(Color.WHITE);
        jthead.setReorderingAllowed(false);
    }

    public void applystylerow(int[] columnright) {
        TableCellRenderer tcr = new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                DefaultTableCellRenderer cellrender = new DefaultTableCellRenderer();
                Component c = cellrender.getTableCellRendererComponent(table,
                        value, isSelected, hasFocus, row, column);

                for (int i = 0; i < columnright.length; i++) {
                    if (column == columnright[i]) {
                        c.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    }
                }
                return c;
            }
        };
        tbl.setDefaultRenderer(Object.class, tcr);

    }

}
