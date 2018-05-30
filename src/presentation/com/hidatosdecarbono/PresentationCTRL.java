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

    }

    public void runLogin(){
        loadVentana(new LoginLayout(domini.getLogInCTRL(), this).$$$getRootComponent$$$());
    }

    private void loadVentana(Container cont){
        frame.setContentPane(cont);
        frame.pack();
        frame.setVisible(true);
    }

    public void runMainMenu(){
        loadVentana(new MainMenu().$$$getRootComponent$$$());
        frame.pack();
        frame.setVisible(true);
    }


}
