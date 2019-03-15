/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.controller;

import java.awt.BorderLayout;
import mizanposapp.view.Beranda_panel;
import org.panda_lang.pandomium.Pandomium;
import org.panda_lang.pandomium.settings.PandomiumSettings;
import org.panda_lang.pandomium.wrapper.PandomiumBrowser;
import org.panda_lang.pandomium.wrapper.PandomiumClient;

/**
 *
 * @author Minami
 */
public class BerandaController {

    Beranda_panel pane;

    public BerandaController(Beranda_panel pane) {
        this.pane = pane;
        //loadbrowser();
    }

    private void loadbrowser() {
        PandomiumSettings panset = PandomiumSettings.getDefaultSettings();
        Pandomium pan = new Pandomium(panset);
        pan.initialize();
        PandomiumClient panclient = pan.createClient();
        PandomiumBrowser panbro = panclient.loadURL("https://google.com");
        pane.pbrowser.removeAll();
        pane.pbrowser.add(panbro.toAWTComponent(), BorderLayout.CENTER);
        pane.pbrowser.revalidate();
        pane.pbrowser.repaint();
    }

}
