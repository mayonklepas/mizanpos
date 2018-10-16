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
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Minami
 */
@SuppressWarnings("serial")
public class Tablestyle extends JTable {

    JTable tbl;
    Color fgcolor, bgcolor;
    int tipe;
    int[] rightcolumindex;
    int[] centercolumindex;

    public Tablestyle(int tipe, int[] rightcolumindex, int[] centercolumindex) {
        this.tipe = tipe;
        this.rightcolumindex = rightcolumindex;
        this.centercolumindex = centercolumindex;
        this.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        this.setRowHeight(25);
        JTableHeader jthead = this.getTableHeader();
        jthead.setOpaque(false);
        jthead.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jthead.setPreferredSize(new Dimension(30, 30));
        jthead.setBackground(Color.decode("#282727"));
        jthead.setForeground(Color.WHITE);
        jthead.setReorderingAllowed(false);

    }

    public Tablestyle(int tipe, int[] rightcolumindex) {
        this.tipe = tipe;
        this.rightcolumindex = rightcolumindex;
        this.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        this.setRowHeight(25);
        JTableHeader jthead = this.getTableHeader();
        jthead.setOpaque(false);
        jthead.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jthead.setPreferredSize(new Dimension(30, 30));
        jthead.setBackground(Color.decode("#282727"));
        jthead.setForeground(Color.WHITE);
        jthead.setReorderingAllowed(false);

    }

    public Tablestyle(int tipe) {
        this.tipe = tipe;
        this.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        this.setRowHeight(25);
        JTableHeader jthead = this.getTableHeader();
        jthead.setOpaque(false);
        jthead.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jthead.setPreferredSize(new Dimension(30, 30));
        jthead.setBackground(Color.decode("#282727"));
        jthead.setForeground(Color.WHITE);
        jthead.setReorderingAllowed(false);
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component c = super.prepareRenderer(renderer, row, column);
        if (rightcolumindex != null) {
            for (int i = 0; i < rightcolumindex.length; i++) {
                DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
                rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
                this.getColumnModel().getColumn(rightcolumindex[i]).setCellRenderer(rightRenderer);
            }
        }

        if (centercolumindex != null) {
            for (int i = 0; i < centercolumindex.length; i++) {
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                this.getColumnModel().getColumn(centercolumindex[i]).setCellRenderer(centerRenderer);
            }
        }

        if (tipe == 0) {
            if (!isRowSelected(row)) {
                c.setBackground(row % 2 == 0 ? getBackground() : Staticvar.globaltablecolor);
            } else {
                c.setBackground(Staticvar.globaltablecolorselect);
            }
        } else if (tipe == 1) {
            c.setBackground(row % 2 == 0 ? getBackground() : Staticvar.globaltablecolor);

            if (!isColumnSelected(column)) {
                c.setBackground(row % 2 == 0 ? getBackground() : Staticvar.globaltablecolor);
            } else {
                if (isRowSelected(row)) {
                    c.setBackground(Staticvar.globaltablecolorselect);
                }
            }
        }

        return c;
    }

}
