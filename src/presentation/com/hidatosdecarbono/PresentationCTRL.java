package com.hidatosdecarbono;

import javax.swing.*;
import java.awt.*;

public class PresentationCTRL {

    private DomainFactory domini;
    private JFrame frame;
    PresentationCTRL(){
        domini = new DomainFactory();
        frame = new JFrame("HIDATOS DE CARBONO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        try{
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        }
        catch (Exception e){
            //TODO manage aquesta excepcio
        }

    }

    public void runFirstMenu(){
        loadVentana(
                new FirstWindow(this).$$$getRootComponent$$$()
        );
    }

    public void runLogin(){
        loadVentana(
                new LoginLayout(domini.getLogInCTRL(), this).$$$getRootComponent$$$()
        );
    }

    private void loadVentana(Container cont){
        frame.setContentPane(cont);
        frame.pack();
        frame.setVisible(true);
    }

    public void runMainMenu(){
        loadVentana(new MainMenu(this).$$$getRootComponent$$$());
        frame.pack();
        frame.setVisible(true);
    }


}
