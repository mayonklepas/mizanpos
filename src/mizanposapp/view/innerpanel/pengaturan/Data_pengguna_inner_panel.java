/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.view.innerpanel.pengaturan;

import mizanposapp.controller.innerpanel.pengaturan.DatapenggunainnerController;

/**
 *
 * @author Minami
 */
public class Data_pengguna_inner_panel extends javax.swing.JPanel {

    /**
     * Creates new form Data_pengguna_inner_panel
     */
    public Data_pengguna_inner_panel() {
        initComponents();
        new DatapenggunainnerController(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel28 = new javax.swing.JPanel();
        jLabel91 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        treedata = new javax.swing.JTree();
        bbaru = new javax.swing.JButton();
        bedit = new javax.swing.JButton();
        bhapus = new javax.swing.JButton();
        bhakakses = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel28.setBackground(new java.awt.Color(41, 39, 40));
        jPanel28.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        jPanel28.setPreferredSize(new java.awt.Dimension(284, 46));

        jLabel91.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel91.setForeground(new java.awt.Color(255, 255, 255));
        jLabel91.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mizanposapp/icon/ic_supervisor_account_white_36dp.png"))); // NOI18N
        jLabel91.setText("Data Pengguna");

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel91)
                .addContainerGap(229, Short.MAX_VALUE))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel91)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        treedata.setBackground(new java.awt.Color(255, 255, 255));
        treedata.setForeground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(treedata);

        bbaru.setText("Baru");

        bedit.setText("Edit");

        bhapus.setText("Hapus");

        bhakakses.setText("Hak Akses");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(bhakakses)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bhapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bedit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bbaru)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bbaru)
                    .addComponent(bedit)
                    .addComponent(bhapus)
                    .addComponent(bhakakses))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton bbaru;
    public javax.swing.JButton bedit;
    public javax.swing.JButton bhakakses;
    public javax.swing.JButton bhapus;
    public javax.swing.JLabel jLabel91;
    public javax.swing.JPanel jPanel28;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTree treedata;
    // End of variables declaration//GEN-END:variables
}
