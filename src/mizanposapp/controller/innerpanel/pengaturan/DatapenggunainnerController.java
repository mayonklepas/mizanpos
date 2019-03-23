/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.pengaturan;

import com.sun.javafx.sg.prism.NGCanvas;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import mizanposapp.helper.CrudHelper;
import mizanposapp.helper.Globalsession;
import mizanposapp.helper.Staticvar;
import mizanposapp.view.Mainmenu;
import mizanposapp.view.innerpanel.pengaturan.Data_pengguna_inner_panel;
import mizanposapp.view.innerpanel.pengaturan.Data_pengguna_input_panel;
import mizanposapp.view.innerpanel.pengaturan.Gantipassword_panel;
import mizanposapp.view.innerpanel.pengaturan.Hak_akses_inner_panel;
import mizanposapp.view.innerpanel.pengaturan.Login_panel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Minami
 */
public class DatapenggunainnerController {

    Data_pengguna_inner_panel pane;
    CrudHelper ch = new CrudHelper();
    ArrayList<entirytree> lsdatatree = new ArrayList<entirytree>();

    public DatapenggunainnerController(Data_pengguna_inner_panel pane) {
        this.pane = pane;
        databaru();
        loaddata();
        editdata();
        edithakakses();
        btcontrol();
        gantipassword();
    }

    private void btcontrol() {
        pane.treedata.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                pane.bhakakses.setEnabled(true);
                pane.bhapus.setEnabled(true);
                pane.bedit.setEnabled(true);
            }
        });
    }

    private void databaru() {
        pane.bbaru.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Staticvar.ids = "";
                JDialog jd = new JDialog(new Mainmenu());
                jd.add(new Data_pengguna_input_panel());
                jd.pack();
                jd.setLocationRelativeTo(null);
                jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                jd.setTitle("Input Data Pengguna");
                jd.setVisible(true);
                if (Staticvar.isupdate == true) {
                    loaddata();
                    Staticvar.isupdate = false;
                }
            }
        });
    }

    private void editdata() {
        pane.bedit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String namatree = "";
                String idtree = "";
                TreePath[] tpath = pane.treedata.getSelectionPaths();
                for (TreePath treePath : tpath) {
                    namatree = String.valueOf(treePath.getLastPathComponent());
                }
                for (int i = 0; i < lsdatatree.size(); i++) {
                    if (lsdatatree.get(i).getNama().equals(namatree)) {
                        idtree = lsdatatree.get(i).getId();
                    }
                }
                //JOptionPane.showMessageDialog(null, idtree);
                Staticvar.ids = idtree;
                JDialog jd = new JDialog(new Mainmenu());
                jd.add(new Data_pengguna_input_panel());
                jd.pack();
                jd.setLocationRelativeTo(null);
                jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                jd.setTitle("Edit Data Pengguna");
                jd.setVisible(true);
                if (Staticvar.isupdate == true) {
                    loaddata();
                    Staticvar.isupdate = false;
                }

            }
        });
    }

    private void edithakakses() {
        pane.bhakakses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String namatree = "";
                String namaparent = "";
                String idtree = "";
                TreePath[] tpath = pane.treedata.getSelectionPaths();
                for (TreePath treePath : tpath) {
                    namatree = String.valueOf(treePath.getLastPathComponent());
                    String rawnamaparent = String.valueOf(treePath.getParentPath().getLastPathComponent());
                    if (rawnamaparent.equals("Daftar Pengguna")) {
                        namaparent = String.valueOf(treePath.getLastPathComponent());
                    } else {
                        namaparent = String.valueOf(treePath.getParentPath().getLastPathComponent());
                    }
                }
                for (int i = 0; i < lsdatatree.size(); i++) {
                    if (lsdatatree.get(i).getNama().equals(namatree)) {
                        idtree = lsdatatree.get(i).getId();
                    }
                }

                Staticvar.ids = idtree;
                Staticvar.labels = namatree;
                LoginController.username = namaparent;
                JDialog jd = new JDialog(new Mainmenu());
                jd.add(new Login_panel());
                jd.pack();
                jd.setLocationRelativeTo(null);
                jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                jd.setTitle("Login");
                jd.setVisible(true);
                if (Staticvar.isupdate == true) {
                    Staticvar.isupdate = false;
                    JDialog jdin = new JDialog(new Mainmenu());
                    jdin.add(new Hak_akses_inner_panel());
                    jdin.pack();
                    jdin.setLocationRelativeTo(null);
                    jdin.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                    jdin.setTitle("Pengaturan Hak Akses");
                    jdin.setVisible(true);
                    if (Staticvar.isupdate == true) {
                        loaddata();
                        Staticvar.isupdate = false;
                    }
                }

            }
        });
    }

    private void gantipassword() {
        pane.bgantipass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginController.username = Globalsession.nama_user;
                JDialog jd = new JDialog(new Mainmenu());
                jd.add(new Login_panel());
                jd.pack();
                jd.setLocationRelativeTo(null);
                jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                jd.setTitle("Login");
                jd.setVisible(true);
                if (Staticvar.isupdate == true) {
                    Staticvar.isupdate = false;
                    JDialog jdin = new JDialog(new Mainmenu());
                    jdin.add(new Gantipassword_panel());
                    jdin.pack();
                    jdin.setLocationRelativeTo(null);
                    jdin.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                    jdin.setTitle("Ganti Password");
                    jdin.setVisible(true);
                    if (Staticvar.isupdate == true) {
                        loaddata();
                        Staticvar.isupdate = false;
                    }
                }

            }
        });
    }

    private void loaddata() {
        pane.treedata.removeAll();
        lsdatatree.clear();
        pane.bhakakses.setEnabled(false);
        pane.bhapus.setEnabled(false);
        pane.bedit.setEnabled(false);
        try {
            JSONParser jpdata = new JSONParser();
            Object objdata = jpdata.parse(ch.getdatas("dm/daftarpengguna"));
            System.out.println(objdata);
            JSONArray japengguna = (JSONArray) objdata;
            ArrayList<entirytree> lsroot = new ArrayList();
            ArrayList<entirytree> lschild = new ArrayList();
            for (int i = 0; i < japengguna.size(); i++) {
                JSONObject jo = (JSONObject) japengguna.get(i);
                String id = String.valueOf(jo.get("id"));
                String nama = String.valueOf(jo.get("nama"));
                String id_parent = String.valueOf(jo.get("id_parent"));
                if (id_parent.equals("")) {
                    lsroot.add(new entirytree(id, nama, id_parent, 1));
                } else {
                    lschild.add(new entirytree(id, nama, id_parent, 2));
                }

            }

            for (int i = 0; i < lsroot.size(); i++) {
                lsdatatree.add(new entirytree(lsroot.get(i).getId(), lsroot.get(i).getNama(), lsroot.get(i).getId_parent(), 1));
                for (int j = 0; j < lschild.size(); j++) {
                    if (lschild.get(j).getId_parent().equals(lsroot.get(i).getId())) {
                        lsdatatree.add(new entirytree(lschild.get(j).getId(), lschild.get(j).getNama(), lschild.get(j).getId_parent(), 2));
                        for (int k = 0; k < lschild.size(); k++) {
                            if (lschild.get(k).getId_parent().equals(lschild.get(j).getId())) {
                                lsdatatree.add(new entirytree(lschild.get(k).getId(), lschild.get(k).getNama(), lschild.get(k).getId_parent(), 3));
                                for (int l = 0; l < lschild.size(); l++) {
                                    if (lschild.get(l).getId_parent().equals(lschild.get(k).getId())) {
                                        lsdatatree.add(new entirytree(lschild.get(l).getId(), lschild.get(l).getNama(), lschild.get(l).getId_parent(), 4));
                                        for (int m = 0; m < lschild.size(); m++) {
                                            if (lschild.get(m).getId_parent().equals(lschild.get(l).getId())) {
                                                lsdatatree.add(new entirytree(lschild.get(m).getId(), lschild.get(m).getNama(), lschild.get(m).getId_parent(), 5));
                                                for (int n = 0; n < lschild.size(); n++) {
                                                    if (lschild.get(n).getId_parent().equals(lschild.get(m).getId())) {
                                                        lsdatatree.add(new entirytree(lschild.get(n).getId(), lschild.get(n).getNama(), lschild.get(n).getId_parent(), 6));
                                                        for (int o = 0; o < lschild.size(); o++) {
                                                            if (lschild.get(o).getId_parent().equals(lschild.get(m).getId())) {
                                                                lsdatatree.add(new entirytree(lschild.get(o).getId(), lschild.get(o).getNama(), lschild.get(o).getId_parent(), 7));
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            for (int i = 0; i < lsdatatree.size(); i++) {
                System.out.println(lsdatatree.get(i).getNama());
            }

            DefaultMutableTreeNode troot = new DefaultMutableTreeNode("Daftar Pengguna");

            DefaultMutableTreeNode lvl1 = new DefaultMutableTreeNode("");
            DefaultMutableTreeNode lvl2 = new DefaultMutableTreeNode("");
            DefaultMutableTreeNode lvl3 = new DefaultMutableTreeNode("");
            DefaultMutableTreeNode lvl4 = new DefaultMutableTreeNode("");
            DefaultMutableTreeNode lvl5 = new DefaultMutableTreeNode("");
            DefaultMutableTreeNode lvl6 = new DefaultMutableTreeNode("");
            DefaultMutableTreeNode lvl7 = new DefaultMutableTreeNode("");

            ArrayList<String> lastchild = new ArrayList();

            for (int i = 0; i < lsdatatree.size(); i++) {
                lastchild.clear();
                if (lsdatatree.get(i).getLevel() == 1) {
                    lvl1 = new DefaultMutableTreeNode(lsdatatree.get(i).getNama());
                    troot.add(lvl1);
                }
                for (int j = 0; j < lsdatatree.size(); j++) {
                    if (lsdatatree.get(j).getLevel() == 2) {
                        if (lsdatatree.get(j).getId_parent().equals(lsdatatree.get(i).getId())) {
                            lvl2 = new DefaultMutableTreeNode(lsdatatree.get(j).getNama());
                            lvl1.add(lvl2);
                            for (int k = 0; k < lsdatatree.size(); k++) {
                                if (lsdatatree.get(k).getLevel() == 3) {
                                    if (lsdatatree.get(k).getId_parent().equals(lsdatatree.get(j).getId())) {
                                        lvl3 = new DefaultMutableTreeNode(lsdatatree.get(k).getNama());
                                        lvl2.add(lvl3);
                                        for (int l = 0; l < lsdatatree.size(); l++) {
                                            if (lsdatatree.get(l).getLevel() == 4) {
                                                if (lsdatatree.get(l).getId_parent().equals(lsdatatree.get(k).getId())) {
                                                    lvl4 = new DefaultMutableTreeNode(lsdatatree.get(l).getNama());
                                                    lvl3.add(lvl4);
                                                    for (int m = 0; m < lsdatatree.size(); m++) {
                                                        if (lsdatatree.get(m).getLevel() == 5) {
                                                            if (lsdatatree.get(m).getId_parent().equals(lsdatatree.get(l).getId())) {
                                                                lvl5 = new DefaultMutableTreeNode(lsdatatree.get(m).getNama());
                                                                lvl4.add(lvl5);
                                                                for (int n = 0; n < lsdatatree.size(); n++) {
                                                                    if (lsdatatree.get(n).getLevel() == 6) {
                                                                        if (lsdatatree.get(n).getId_parent().equals(lsdatatree.get(m).getId())) {
                                                                            lvl6 = new DefaultMutableTreeNode(lsdatatree.get(n).getNama());
                                                                            lvl5.add(lvl6);
                                                                            for (int o = 0; o < lsdatatree.size(); o++) {
                                                                                if (lsdatatree.get(o).getLevel() == 7) {
                                                                                    if (lsdatatree.get(o).getId_parent().equals(lsdatatree.get(n).getId())) {
                                                                                        lvl7 = new DefaultMutableTreeNode(lsdatatree.get(o).getNama());
                                                                                        lvl6.add(lvl7);
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            DefaultTreeModel tmodel = new DefaultTreeModel(troot);
            pane.treedata.setModel(tmodel);
        } catch (ParseException ex) {
            Logger.getLogger(DatapenggunainnerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public class entirytree {

        String id, nama, id_parent;
        int level;

        public entirytree(String id, String nama, String id_parent, int level) {
            this.id = id;
            this.nama = nama;
            this.id_parent = id_parent;
            this.level = level;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getId_parent() {
            return id_parent;
        }

        public void setId_parent(String id_parent) {
            this.id_parent = id_parent;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

    }
}
