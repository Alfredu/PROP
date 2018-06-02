package com.hidatosdecarbono;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class PresentationCTRL {

    private DomainFactory domini;
    private JFrame frame;
    private CreadorHidatosCTRL creadorHidatos;
    PresentationCTRL(){
        domini = new DomainFactory();
        frame = new JFrame("HIDATOS DE CARBONO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        creadorHidatos = domini.getControladorCreador();
        try{
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        }
        catch (Exception e){
            //ignored
        }

    }

    private void loadVentana(Container cont){
        frame.setContentPane(cont);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void cambiaVentana(String nombre){
        Container cont = null;

        switch (nombre){
            case "FirstMenu":
                cont = new FirstWindow(this).$$$getRootComponent$$$();
                break;
            case "LoginWindow":
                cont = new LoginWindow(domini.getLogInCTRL(), this).$$$getRootComponent$$$();
                break;
            case "RegisterWindow":
                cont = new RegisterWindow(domini.getLogInCTRL(), this).$$$getRootComponent$$$();
                break;
            case "MainMenu":
                cont = new MainMenu(this).$$$getRootComponent$$$();
                break;
            case "JugaWindow":
                cont = new JugaWindow(this).$$$getRootComponent$$$();
                break;
            case "CreaHidatoWindow":
                cont = new CreaHidatoWindow(this, creadorHidatos).$$$getRootComponent$$$();
                break;
            case "InputHidatoWindow":
                cont = new InputHidatoWindow(this,
                        creadorHidatos).$$$getRootComponent$$$();
                break;



        }

        if(cont != null){
            loadVentana(cont);
        }
    }


    public void cierraVentana() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSED));
    }
}
