/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.view.innerpanel.persediaan;

import mizanposapp.controller.innerpanel.persediaan.DaftarpenyesuaianinputController;
import mizanposapp.helper.Tablestyle;

/**
 *
 * @author Minami
 */
public class Daftarpenyesuaian_input_panel extends javax.swing.JPanel {

    /**
     * Creates new form Persedian_inner_panel
     */
    public Daftarpenyesuaian_input_panel() {
        initComponents();
        new DaftarpenyesuaianinputController(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel12 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabledata = new Tablestyle(1);
        edno_transaksi = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        eddept = new javax.swing.JTextField();
        edketerangan = new javax.swing.JTextField();
        bcari_dept = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        bsimpan = new javax.swing.JButton();
        bbatal = new javax.swing.JButton();
        bhapus_baris = new javax.swing.JButton();
        ckcetak_faktur = new javax.swing.JCheckBox();
        btambah_baris = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        edakun_penyesuaian = new javax.swing.JTextField();
        bcari_akun_penyesuaian = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        rbtambah_stock = new javax.swing.JRadioButton();
        rbkurangi_stock = new javax.swing.JRadioButton();
        dtanggal = new com.toedter.calendar.JDateChooser();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel12.setBackground(new java.awt.Color(41, 39, 40));
        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Input Penyesuaian");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(602, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        tabledata.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
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
        jScrollPane6.setViewportView(tabledata);

        edno_transaksi.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        edno_transaksi.setPreferredSize(new java.awt.Dimension(51, 20));
        edno_transaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edno_transaksiActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel2.setText("No. Transaksi");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel3.setText("Tanggal");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel4.setText("Dept");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel5.setText("Keterangan");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel6.setText(":");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel7.setText(":");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel8.setText(":");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel9.setText(":");

        eddept.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        eddept.setPreferredSize(new java.awt.Dimension(51, 20));
        eddept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eddeptActionPerformed(evt);
            }
        });

        edketerangan.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        edketerangan.setPreferredSize(new java.awt.Dimension(51, 20));
        edketerangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edketeranganActionPerformed(evt);
            }
        });

        bcari_dept.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        bcari_dept.setText("Cari");
        bcari_dept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcari_deptActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        bsimpan.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        bsimpan.setText("Simpan");

        bbatal.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        bbatal.setText("Batal");

        bhapus_baris.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        bhapus_baris.setText("Hapus Baris");

        ckcetak_faktur.setBackground(new java.awt.Color(204, 204, 204));
        ckcetak_faktur.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        ckcetak_faktur.setText("Cetak Faktur");

        btambah_baris.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        btambah_baris.setText("Tambah Baris");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bhapus_baris)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btambah_baris)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ckcetak_faktur)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bbatal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bsimpan)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bbatal)
                    .addComponent(bsimpan)
                    .addComponent(bhapus_baris)
                    .addComponent(ckcetak_faktur)
                    .addComponent(btambah_baris))
                .addContainerGap())
        );

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel12.setText("Akun Penyesuain");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel13.setText(":");

        edakun_penyesuaian.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        edakun_penyesuaian.setPreferredSize(new java.awt.Dimension(51, 20));
        edakun_penyesuaian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edakun_penyesuaianActionPerformed(evt);
            }
        });

        bcari_akun_penyesuaian.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        bcari_akun_penyesuaian.setText("Cari");
        bcari_akun_penyesuaian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcari_akun_penyesuaianActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel14.setText("Opsi Penyesuaian");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel15.setText(":");

        rbtambah_stock.setBackground(new java.awt.Color(255, 255, 255));
        rbtambah_stock.setText("Tambah Stock");

        rbkurangi_stock.setBackground(new java.awt.Color(255, 255, 255));
        rbkurangi_stock.setText("Kurangi Stok");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(edno_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel8)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(edketerangan, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(eddept, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(2, 2, 2)
                                        .addComponent(bcari_dept))
                                    .addComponent(dtanggal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel14))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(edakun_penyesuaian, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(bcari_akun_penyesuaian))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rbkurangi_stock)
                                    .addComponent(rbtambah_stock))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane6)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(edakun_penyesuaian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12)
                        .addComponent(bcari_akun_penyesuaian))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(edno_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(jLabel3)
                        .addComponent(jLabel14)
                        .addComponent(jLabel15)
                        .addComponent(rbtambah_stock))
                    .addComponent(dtanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(eddept, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(bcari_dept)
                    .addComponent(rbkurangi_stock))
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(edketerangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void edno_transaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edno_transaksiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edno_transaksiActionPerformed

    private void eddeptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eddeptActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_eddeptActionPerformed

    private void bcari_deptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcari_deptActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bcari_deptActionPerformed

    private void edketeranganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edketeranganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edketeranganActionPerformed

    private void edakun_penyesuaianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edakun_penyesuaianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edakun_penyesuaianActionPerformed

    private void bcari_akun_penyesuaianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcari_akun_penyesuaianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bcari_akun_penyesuaianActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton bbatal;
    public javax.swing.JButton bcari_akun_penyesuaian;
    public javax.swing.JButton bcari_dept;
    public javax.swing.JButton bhapus_baris;
    public javax.swing.JButton bsimpan;
    public javax.swing.JButton btambah_baris;
    public javax.swing.JCheckBox ckcetak_faktur;
    public com.toedter.calendar.JDateChooser dtanggal;
    public javax.swing.JTextField edakun_penyesuaian;
    public javax.swing.JTextField eddept;
    public javax.swing.JTextField edketerangan;
    public javax.swing.JTextField edno_transaksi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JScrollPane jScrollPane6;
    public javax.swing.JRadioButton rbkurangi_stock;
    public javax.swing.JRadioButton rbtambah_stock;
    public javax.swing.JTable tabledata;
    // End of variables declaration//GEN-END:variables
}
