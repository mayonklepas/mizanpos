/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.view.innerpanel.penjualan;

import mizanposapp.controller.innerpanel.penjualan.BayarposController;

/**
 *
 * @author Minami
 */
public class Bayarpos_pane extends javax.swing.JPanel {

    /**
     * Creates new form POS_bayar_panel
     */
    public Bayarpos_pane() {
        initComponents();
        new BayarposController(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        ltotal = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        bcetak_struk = new javax.swing.JButton();
        btanpa_struk = new javax.swing.JButton();
        bbatal = new javax.swing.JButton();
        bcetak_lagi = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        edbayar = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        cmb_pembayaran = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        ljumlah_bayar = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        lkembali = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lmax_poin = new javax.swing.JLabel();
        lttk_nilai_poin = new javax.swing.JLabel();
        ljumlah_poin = new javax.swing.JLabel();
        lttk_max_poin = new javax.swing.JLabel();
        lnilai_poin = new javax.swing.JLabel();
        lmax_poinl = new javax.swing.JLabel();
        ckgunakan_poin = new javax.swing.JCheckBox();
        lttk_jumlah_poin = new javax.swing.JLabel();
        edjumlah_poin = new javax.swing.JTextField();
        ednilai_poin = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        eddiskon_nominal = new javax.swing.JTextField();
        lsubtotal = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        ltotal_service = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        edbiaya_lain = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        eddiskon_persen = new javax.swing.JTextField();
        ltotal_pajak = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        lno_kartu = new javax.swing.JLabel();
        edno_kartu = new javax.swing.JTextField();
        lttk_no_kartu = new javax.swing.JLabel();
        lnama_pemilik = new javax.swing.JLabel();
        lttk_nama_pemilik = new javax.swing.JLabel();
        ednama_pemilik = new javax.swing.JTextField();
        lperingatan = new javax.swing.JLabel();

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jPanel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(80, 80));
        jPanel2.setLayout(new java.awt.BorderLayout());

        ltotal.setBackground(new java.awt.Color(0, 0, 0));
        ltotal.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        ltotal.setForeground(new java.awt.Color(0, 204, 0));
        ltotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ltotal.setText("0");
        ltotal.setToolTipText("");
        jPanel2.add(ltotal, java.awt.BorderLayout.CENTER);

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 204, 0));
        jLabel2.setText("TOTAL");
        jPanel2.add(jLabel2, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));

        bcetak_struk.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        bcetak_struk.setText("[F12] Cetak Struck");

        btanpa_struk.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        btanpa_struk.setText("[F11] Tanpa Struck");

        bbatal.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        bbatal.setText("[ESC] Batal");

        bcetak_lagi.setText("[F4] Cetak Lagi");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bcetak_lagi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bbatal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btanpa_struk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bcetak_struk)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bcetak_struk)
                    .addComponent(btanpa_struk)
                    .addComponent(bbatal)
                    .addComponent(bcetak_lagi))
                .addContainerGap())
        );

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel26.setText("Bayar - [F7]");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel27.setText(":");

        edbayar.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        edbayar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        edbayar.setMinimumSize(new java.awt.Dimension(51, 20));
        edbayar.setPreferredSize(new java.awt.Dimension(51, 20));
        edbayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edbayarActionPerformed(evt);
            }
        });

        jLabel39.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel39.setText("Pembayaran- [F8]");

        jLabel40.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel40.setText(":");

        cmb_pembayaran.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        cmb_pembayaran.setMinimumSize(new java.awt.Dimension(51, 20));

        jPanel5.setBackground(new java.awt.Color(0, 0, 0));
        jPanel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jPanel5.setPreferredSize(new java.awt.Dimension(80, 80));
        jPanel5.setLayout(new java.awt.BorderLayout());

        ljumlah_bayar.setBackground(new java.awt.Color(0, 0, 0));
        ljumlah_bayar.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        ljumlah_bayar.setForeground(new java.awt.Color(51, 0, 255));
        ljumlah_bayar.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ljumlah_bayar.setText("0");
        jPanel5.add(ljumlah_bayar, java.awt.BorderLayout.CENTER);

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 0, 255));
        jLabel4.setText("BAYAR");
        jPanel5.add(jLabel4, java.awt.BorderLayout.PAGE_START);

        jPanel6.setBackground(new java.awt.Color(0, 0, 0));
        jPanel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jPanel6.setPreferredSize(new java.awt.Dimension(80, 80));
        jPanel6.setLayout(new java.awt.BorderLayout());

        lkembali.setBackground(new java.awt.Color(0, 0, 0));
        lkembali.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lkembali.setForeground(new java.awt.Color(255, 153, 0));
        lkembali.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lkembali.setText("0");
        jPanel6.add(lkembali, java.awt.BorderLayout.CENTER);

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 153, 0));
        jLabel6.setText("KEMBALI");
        jPanel6.add(jLabel6, java.awt.BorderLayout.PAGE_START);

        lmax_poin.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        lmax_poin.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lmax_poin.setText("0");

        lttk_nilai_poin.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        lttk_nilai_poin.setText(":");

        ljumlah_poin.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        ljumlah_poin.setText("Jumlah Poin");

        lttk_max_poin.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        lttk_max_poin.setText(":");

        lnilai_poin.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        lnilai_poin.setText("Nilai Poin");

        lmax_poinl.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        lmax_poinl.setText("Max Jumlah Poin");

        ckgunakan_poin.setText("Gunakan Poin");
        ckgunakan_poin.setPreferredSize(new java.awt.Dimension(91, 20));

        lttk_jumlah_poin.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        lttk_jumlah_poin.setText(":");

        edjumlah_poin.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        edjumlah_poin.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        edjumlah_poin.setText("0");
        edjumlah_poin.setPreferredSize(new java.awt.Dimension(51, 20));
        edjumlah_poin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edjumlah_poinActionPerformed(evt);
            }
        });

        ednilai_poin.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        ednilai_poin.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        ednilai_poin.setText("0");
        ednilai_poin.setPreferredSize(new java.awt.Dimension(51, 20));
        ednilai_poin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ednilai_poinActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ckgunakan_poin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(ljumlah_poin)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lttk_jumlah_poin))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lnilai_poin)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lttk_nilai_poin))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lmax_poinl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lttk_max_poin)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ednilai_poin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(edjumlah_poin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lmax_poin, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ckgunakan_poin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lttk_jumlah_poin)
                    .addComponent(ljumlah_poin)
                    .addComponent(edjumlah_poin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lttk_nilai_poin)
                    .addComponent(lnilai_poin)
                    .addComponent(ednilai_poin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lttk_max_poin)
                    .addComponent(lmax_poinl)
                    .addComponent(lmax_poin))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        eddiskon_nominal.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        eddiskon_nominal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        eddiskon_nominal.setText("0");
        eddiskon_nominal.setPreferredSize(new java.awt.Dimension(51, 20));
        eddiskon_nominal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eddiskon_nominalActionPerformed(evt);
            }
        });

        lsubtotal.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        lsubtotal.setText("0");

        jLabel15.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        jLabel15.setText("Sub Total");

        ltotal_service.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        ltotal_service.setText("0");

        jLabel32.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        jLabel32.setText(":");

        jLabel30.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        jLabel30.setText(":");

        jLabel21.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        jLabel21.setText("Total Service");

        jLabel17.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        jLabel17.setText("Biaya Lain [F5]");

        jLabel19.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        jLabel19.setText("Total Pajak");

        jLabel33.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        jLabel33.setText(":");

        edbiaya_lain.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        edbiaya_lain.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        edbiaya_lain.setText("0");
        edbiaya_lain.setPreferredSize(new java.awt.Dimension(51, 20));
        edbiaya_lain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edbiaya_lainActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        jLabel16.setText(":");

        eddiskon_persen.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        eddiskon_persen.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        eddiskon_persen.setText("0");
        eddiskon_persen.setPreferredSize(new java.awt.Dimension(51, 20));
        eddiskon_persen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eddiskon_persenActionPerformed(evt);
            }
        });

        ltotal_pajak.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        ltotal_pajak.setText("0");

        jLabel31.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        jLabel31.setText(":");

        jLabel18.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        jLabel18.setText("Diskon - [F6]");

        jLabel36.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        jLabel36.setText("% = ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel15)
                            .addGap(44, 44, 44)
                            .addComponent(jLabel16)
                            .addGap(1, 1, 1))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel17)
                                    .addGap(18, 18, 18))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel18)
                                    .addGap(33, 33, 33)))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel31)
                                .addComponent(jLabel30)
                                .addComponent(jLabel32)
                                .addComponent(jLabel33))))
                    .addComponent(jLabel21)
                    .addComponent(jLabel19))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lsubtotal))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(eddiskon_persen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel36)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(eddiskon_nominal, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE))
                            .addComponent(edbiaya_lain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ltotal_pajak, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ltotal_service, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel15)
                    .addComponent(lsubtotal))
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(jLabel30)
                            .addComponent(edbiaya_lain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(jLabel31)
                            .addComponent(eddiskon_persen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(eddiskon_nominal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel36)))
                .addGap(2, 2, 2)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ltotal_pajak)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel19)
                        .addComponent(jLabel32)))
                .addGap(3, 3, 3)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jLabel33)
                    .addComponent(ltotal_service))
                .addContainerGap())
        );

        lno_kartu.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lno_kartu.setText("No. Kartu");

        edno_kartu.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        edno_kartu.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        edno_kartu.setMinimumSize(new java.awt.Dimension(51, 20));
        edno_kartu.setPreferredSize(new java.awt.Dimension(51, 20));
        edno_kartu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edno_kartuActionPerformed(evt);
            }
        });

        lttk_no_kartu.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lttk_no_kartu.setText(":");

        lnama_pemilik.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lnama_pemilik.setText("Nama Pemilik");

        lttk_nama_pemilik.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lttk_nama_pemilik.setText(":");

        ednama_pemilik.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        ednama_pemilik.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        ednama_pemilik.setMinimumSize(new java.awt.Dimension(51, 20));
        ednama_pemilik.setPreferredSize(new java.awt.Dimension(51, 20));
        ednama_pemilik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ednama_pemilikActionPerformed(evt);
            }
        });

        lperingatan.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lperingatan.setText("Tekan ENTER Untuk Melanjutkan");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lperingatan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lno_kartu)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lttk_no_kartu))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel27))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel39)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel40))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lnama_pemilik)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lttk_nama_pemilik)))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ednama_pemilik, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                            .addComponent(cmb_pembayaran, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(edbayar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(edno_kartu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel26)
                    .addComponent(edbayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lperingatan))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(jLabel39)
                    .addComponent(cmb_pembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lno_kartu)
                    .addComponent(edno_kartu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lttk_no_kartu))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lnama_pemilik)
                    .addComponent(ednama_pemilik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lttk_nama_pemilik))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void edjumlah_poinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edjumlah_poinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edjumlah_poinActionPerformed

    private void ednilai_poinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ednilai_poinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ednilai_poinActionPerformed

    private void edbiaya_lainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edbiaya_lainActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edbiaya_lainActionPerformed

    private void eddiskon_persenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eddiskon_persenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_eddiskon_persenActionPerformed

    private void eddiskon_nominalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eddiskon_nominalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_eddiskon_nominalActionPerformed

    private void edbayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edbayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edbayarActionPerformed

    private void edno_kartuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edno_kartuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edno_kartuActionPerformed

    private void ednama_pemilikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ednama_pemilikActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ednama_pemilikActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton bbatal;
    public javax.swing.JButton bcetak_lagi;
    public javax.swing.JButton bcetak_struk;
    public javax.swing.JButton btanpa_struk;
    public javax.swing.JCheckBox ckgunakan_poin;
    public javax.swing.JComboBox<String> cmb_pembayaran;
    public javax.swing.JTextField edbayar;
    public javax.swing.JTextField edbiaya_lain;
    public javax.swing.JTextField eddiskon_nominal;
    public javax.swing.JTextField eddiskon_persen;
    public javax.swing.JTextField edjumlah_poin;
    public javax.swing.JTextField ednama_pemilik;
    public javax.swing.JTextField ednilai_poin;
    public javax.swing.JTextField edno_kartu;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    public javax.swing.JLabel ljumlah_bayar;
    public javax.swing.JLabel ljumlah_poin;
    public javax.swing.JLabel lkembali;
    public javax.swing.JLabel lmax_poin;
    public javax.swing.JLabel lmax_poinl;
    public javax.swing.JLabel lnama_pemilik;
    public javax.swing.JLabel lnilai_poin;
    public javax.swing.JLabel lno_kartu;
    public javax.swing.JLabel lperingatan;
    public javax.swing.JLabel lsubtotal;
    public javax.swing.JLabel ltotal;
    public javax.swing.JLabel ltotal_pajak;
    public javax.swing.JLabel ltotal_service;
    public javax.swing.JLabel lttk_jumlah_poin;
    public javax.swing.JLabel lttk_max_poin;
    public javax.swing.JLabel lttk_nama_pemilik;
    public javax.swing.JLabel lttk_nilai_poin;
    public javax.swing.JLabel lttk_no_kartu;
    // End of variables declaration//GEN-END:variables
}