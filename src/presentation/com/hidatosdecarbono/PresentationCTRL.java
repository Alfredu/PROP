package com.hidatosdecarbono;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class PresentationCTRL {

    private DomainFactory domini;
    private JFrame frame;
    private CreadorHidatosCTRL creadorHidatos;
    private LogInCTRL logInCTRL;
    private JugarHidatosCTRL jugarHidatosPausaCtrl;
    PresentationCTRL(){
        domini = new DomainFactory();
        frame = new JFrame("HIDATOS DE CARBONO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        logInCTRL = domini.getLogInCTRL();
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
                logInCTRL = domini.getLogInCTRL();
                break;
            case "LoginWindow":
                cont = new LoginWindow(logInCTRL, this).$$$getRootComponent$$$();
                break;
            case "RegisterWindow":
                cont = new RegisterWindow(logInCTRL, this).$$$getRootComponent$$$();
                break;
            case "MainMenu":
                creadorHidatos = domini.getControladorCreador();
                cont = new MainMenu(this).$$$getRootComponent$$$();
                break;
            case "JugaWindow":
                cont = new JugaWindow(this).$$$getRootComponent$$$();
                break;
            case "CreaHidatoWindow":
                cont = new CreaHidatoWindow(this, creadorHidatos).$$$getRootComponent$$$();
                break;
            case "InputRandomHidatoWindow":
                cont = new InputRandomHidatoWindow(this, creadorHidatos).$$$getRootComponent$$$();
                break;
            case "InputHidatoWindow":
                cont = new InputHidatoWindow(this,
                        creadorHidatos).$$$getRootComponent$$$();
                break;

            case "InputHidatoByDifficultyWindow":
                cont = new InputHidatoByDifficultyWindow(this, creadorHidatos).$$$getRootComponent$$$();
                break;
            case "JugaPartidaWindow":
                    cont = new PlayHidatoWindow(creaHidatoPane(creadorHidatos.getRepresentacionHidato().forma,
                            true), creadorHidatos.getControladorPartida(), this).$$$getRootComponent$$$();
                break;

            case "ReanudarHidatoWindow":
                    cont = new PlayHidatoWindow(creaHidatoPane(TipoHidato.CUADRADO, true), jugarHidatosPausaCtrl, this).$$$getRootComponent$$$();
                    break;
            case "MostraSolucioWindow":

                cont = new ShowSolutionWindow(creaHidatoPane(creadorHidatos.getRepresentacionSolucion().forma,
                        false), creadorHidatos, this).$$$getRootComponent$$$();

                break;




        }

        if(cont != null){
            loadVentana(cont);
        }
    }


    public void cierraVentana() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSED));
    }

    private HidatoPane creaHidatoPane(TipoHidato forma, boolean playable){
        HidatoPane pane;
        int width = 600;
        int height = 500;
        HidatoRep rep;
        if(playable) rep = creadorHidatos.getRepresentacionHidato();
        else rep = creadorHidatos.getRepresentacionSolucion();
        if(forma == TipoHidato.CUADRADO){
            pane = new SquareHidatoPane(rep,
                    width, height, creadorHidatos.getControladorPartida(), playable);
        }

        else if(forma == TipoHidato.TRIANGULAR){
            pane = new TriangularHidatoPane(rep,
                    width, height, creadorHidatos.getControladorPartida(), playable);
        }
        else{
            pane = new HexagonalHidatoPane(rep,
                    width, height, creadorHidatos.getControladorPartida(), playable);
        }

        return pane;
    }

    public boolean tryLoadSavedGame(){
        try{
            this.jugarHidatosPausaCtrl = domini.getControladorJugarHidatoPausado();
            return true;
        }
        catch(IndexOutOfBoundsException e){
            return false;
        }
    }
}
