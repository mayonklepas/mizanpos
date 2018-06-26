/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.helper;

import java.awt.Color;
import java.awt.Component;
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
    
    public void applystyle() {
        tbl.setShowGrid(false);
        JTableHeader jthead = tbl.getTableHeader();
        jthead.setOpaque(false);
        jthead.setFont(new Font("century gothic", Font.BOLD, 13));
        jthead.setPreferredSize(new Dimension(30, 30));
        jthead.setBackground(Color.decode("#282727"));
        jthead.setForeground(Color.WHITE);
        jthead.setReorderingAllowed(true);
        
        TableCellRenderer tcr = new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                table.setFillsViewportHeight(true);
                table.setRowHeight(25);
                table.setSelectionForeground(Color.BLACK);
                DefaultTableCellRenderer DEFTBCEllRENDER = new DefaultTableCellRenderer();
                Component c = DEFTBCEllRENDER.getTableCellRendererComponent(table,
                        value, isSelected, hasFocus, row, column);
                if (row % 2 == 0) {
                    c.setBackground(Color.WHITE);
                } else {
                    c.setBackground(Color.decode("#F3F3F3"));
                }
                
                if (isSelected) {
                    c.setBackground(Color.decode("#9933FF"));
                }
                
                return c;
            }
        };
        tbl.setDefaultRenderer(Object.class, tcr);
    }
    
}
