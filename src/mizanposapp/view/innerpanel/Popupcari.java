/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.view.innerpanel;

import mizanposapp.controller.innerpanel.PopupcariController;
import mizanposapp.helper.Tablestyle;

/**
 *
 * @author Minami
 */
public class Popupcari extends javax.swing.JPanel {

    /**
     * Creates new form Popupcari
     */
    public Popupcari(String tipe, String page, String header) {
        initComponents();
        new PopupcariController(this, tipe, page, header);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabledata = new Tablestyle(0);
        bbaru = new javax.swing.JButton();
        btutup = new javax.swing.JButton();
        bedit = new javax.swing.JButton();
        bhapus = new javax.swing.JButton();
        bok = new javax.swing.JButton();
        tcari = new javax.swing.JTextField();
        jPanel30 = new javax.swing.JPanel();
        lheader = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setBackground(new java.awt.Color(255, 255, 255));

        tabledata.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tabledata);

        bbaru.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        bbaru.setText("Baru");

        btutup.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        btutup.setText("Tutup");

        bedit.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        bedit.setText("Edit");

        bhapus.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        bhapus.setText("Hapus");

        bok.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        bok.setText("Ok");

        tcari.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        tcari.setText("Cari Data");

        jPanel30.setBackground(new java.awt.Color(41, 39, 40));
        jPanel30.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        jPanel30.setPreferredSize(new java.awt.Dimension(284, 46));

        lheader.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        lheader.setForeground(new java.awt.Color(255, 255, 255));
        lheader.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mizanposapp/icon/ic_view_module_white_36dp.png"))); // NOI18N
        lheader.setText("Data");

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lheader)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lheader)
                .addContainerGap(11, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(bbaru)
                        .addGap(2, 2, 2)
                        .addComponent(bedit)
                        .addGap(2, 2, 2)
                        .addComponent(bhapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tcari)
                        .addGap(18, 18, 18)
                        .addComponent(btutup)
                        .addGap(2, 2, 2)
                        .addComponent(bok)))
                .addContainerGap())
            .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bbaru)
                    .addComponent(btutup)
                    .addComponent(bedit)
                    .addComponent(bhapus)
                    .addComponent(bok)
                    .addComponent(tcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton bbaru;
    public javax.swing.JButton bedit;
    public javax.swing.JButton bhapus;
    public javax.swing.JButton bok;
    public javax.swing.JButton btutup;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JLabel lheader;
    public javax.swing.JTable tabledata;
    public javax.swing.JTextField tcari;
    // End of variables declaration//GEN-END:variables
}
