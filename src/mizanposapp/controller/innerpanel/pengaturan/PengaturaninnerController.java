/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller.innerpanel.pengaturan;

import mizanposapp.helper.CrudHelper;
import mizanposapp.view.innerpanel.pengaturan.Pengaturan_inner_panel;

/**
 *
 * @author Minami
 */
public class PengaturaninnerController {

    Pengaturan_inner_panel pane;
    CrudHelper ch = new CrudHelper();

    public PengaturaninnerController(Pengaturan_inner_panel pane) {
        this.pane = pane;
    }

}
