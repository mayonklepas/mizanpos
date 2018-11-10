/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel;

/**
 *
 * @author Minami
 */
public class SatuanEntity {

    public String id, nama, id_pengali, qty_pengali;

    public SatuanEntity(String id, String nama, String id_pengali, String qty_pengali) {
        this.id = id;
        this.nama = nama;
        this.id_pengali = id_pengali;
        this.qty_pengali = qty_pengali;
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

    public String getId_pengali() {
        return id_pengali;
    }

    public void setId_pengali(String id_pengali) {
        this.id_pengali = id_pengali;
    }

    public String getQty_pengali() {
        return qty_pengali;
    }

    public void setQty_pengali(String qty_pengali) {
        this.qty_pengali = qty_pengali;
    }

}
