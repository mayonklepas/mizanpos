/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.penjualan;

import java.awt.Dialog;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.innerpanel.Popupcari;
import mizanposapp.view.innerpanel.penjualan.Insertpos_pane;

/**
 *
 * @author Minami
 */
public class InsertposController {
    
    NumberFormat nf = NumberFormat.getInstance();
    CrudHelper ch = new CrudHelper();
    Insertpos_pane pane;
    public static boolean status_diskon_persen = true;
    public static String golongan, id_barang, jumlah, id_satuan, satuan, id_satuan_pengali, qty_satuan_pengali, diskon_persen, diskon_nominal, harga_persatuan, keterangan;
    
    public InsertposController(Insertpos_pane pane) {
        this.pane = pane;
        loaddata();
        kontrol();
    }
    
    private void loaddata() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                pane.edjumlah.requestFocus();
                pane.edjumlah.selectAll();
            }
        });
        pane.edjumlah.setText(jumlah);
        pane.edsatuan.setText(satuan);
        pane.eddiskon.setText(diskon_persen);
        pane.eddiskon2.setText(diskon_nominal);
        pane.edharga_persatuan.setText(harga_persatuan);
        pane.edketerangan.setText(keterangan);
    }
    
    private void kontrol() {
        
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    if (e.getKeyCode() == KeyEvent.VK_F2) {
                        Staticvar.sfilter = "";
                        Staticvar.preid = id_satuan;
                        Staticvar.prelabel = satuan;
                        Staticvar.prevalueextended = qty_satuan_pengali;
                        JDialog jd = new JDialog();
                        jd.add(new Popupcari("satuanperbarang",
                             String.format("popupdaftarsatuanperbarang?id_inv=%s",
                                  id_barang),
                             "Daftar Satuan Perbarang"));
                        jd.pack();
                        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        jd.setLocationRelativeTo(null);
                        jd.setVisible(true);
                        jd.toFront();
                        if (!id_satuan.equals(Staticvar.resid)) {
                            id_satuan = Staticvar.resid;
                            satuan = Staticvar.reslabel;
                            qty_satuan_pengali = Staticvar.resvalueextended;
                            pane.edsatuan.setText(satuan);
                            double callhargajual = gethargajual(
                                 id_barang,
                                 id_satuan,
                                 jumlah);
                            pane.edharga_persatuan.setText(nf.format(callhargajual));
                        }
                    } else if (e.getKeyCode() == KeyEvent.VK_F3) {
                        pane.eddiskon.requestFocus();
                    } else if (e.getKeyCode() == KeyEvent.VK_F4) {
                        pane.eddiskon2.requestFocus();
                    } else if (e.getKeyCode() == KeyEvent.VK_F12) {
                        pane.bok.doClick();
                    } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        pane.bbatal.doClick();
                    } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        if (pane.edjumlah.isFocusOwner()) {
                            if (status_diskon_persen == true) {
                                pane.eddiskon.requestFocus();
                                pane.eddiskon.selectAll();
                            } else {
                                pane.eddiskon2.requestFocus();
                                pane.eddiskon2.selectAll();
                            }
                        } else if (pane.eddiskon.isFocusOwner()) {
                            pane.edharga_persatuan.requestFocus();
                            pane.edharga_persatuan.selectAll();
                        } else if (pane.eddiskon2.isFocusOwner()) {
                            pane.edharga_persatuan.requestFocus();
                            pane.edharga_persatuan.selectAll();
                        } else if (pane.edharga_persatuan.isFocusOwner()) {
                            pane.edketerangan.requestFocus();
                            pane.edketerangan.selectAll();
                        } else if (pane.edketerangan.isFocusOwner()) {
                            pane.bok.doClick();
                        }
                    }
                }
                return false;
            }
        });
        
        pane.bok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jumlah = pane.edjumlah.getText();
                satuan = pane.edsatuan.getText();
                diskon_persen = pane.eddiskon.getText();
                diskon_nominal = pane.eddiskon2.getText();
                harga_persatuan = pane.edharga_persatuan.getText();
                keterangan = pane.edketerangan.getText();
                JDialog jd = (JDialog) pane.getRootPane().getParent();
                jd.dispose();
            }
        });
        
        pane.bbatal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog jd = (JDialog) pane.getRootPane().getParent();
                jd.dispose();
            }
        });
    }
    
    private double gethargajual(String id_inv, String id_satuan, String qty) {
        String param = String.format("id_golongan=%s&id_inv=%s&id_satuan=%s&qty=%s", golongan, id_inv, id_satuan, qty);
        String hargajual = ch.getdatadetails("gethargajual", param);
        return Double.parseDouble(hargajual);
    }
    
}
