package com.hidatosdecarbono;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;

public class PresentationCTRL {

    private DomainFactory domini;
    private JFrame frame;
    private CreadorHidatosCTRL creadorHidatos;
    private LogInCTRL logInCTRL;
    private JugarHidatosCTRL jugarHidatosPausaCtrl;
    private ConsultarRankingCTRL consultarRankingCTRL;
    private SeleccionarHidatosCTRL seleccionarHidatosCTRL;
    private Dificultad hidatoJugadoDificultad;
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
                    cont = new PlayHidatoWindow(creaHidatoPane(creadorHidatos.getRepresentacionHidato(),
                            true,creadorHidatos.getControladorPartida()), creadorHidatos.getControladorPartida(), this).$$$getRootComponent$$$();
                break;

            case "ReanudarHidatoWindow":
                    cont = new PlayHidatoWindow(creaHidatoPane(jugarHidatosPausaCtrl.getRepresentacionHidato(), true, jugarHidatosPausaCtrl), jugarHidatosPausaCtrl, this).$$$getRootComponent$$$();
                    break;
            case "MostraSolucioWindow":

                cont = new ShowSolutionWindow(creaHidatoPane(creadorHidatos.getRepresentacionSolucion(),
                        false,creadorHidatos.getControladorPartida()), creadorHidatos, this).$$$getRootComponent$$$();

                break;
            case "ShowRankingWindow":
                cont = new ShowRankingWindow(this, null).$$$getRootComponent$$$();
                break;

            case "ChoseHidatoWindow":
                this.seleccionarHidatosCTRL = domini.getSeleccionarHidatosCTRL();
                cont = new ChoseHidatoWindow(this, this.seleccionarHidatosCTRL).$$$getRootComponent$$$();
                break;

            case "ShowCreatedHidatoWindow":
                cont = new ShowSolutionWindow(creaHidatoPane(creadorHidatos.getRepresentacionHidato(),
                        false, creadorHidatos.getControladorPartida()), creadorHidatos, this).$$$getRootComponent$$$();
                break;

            case "ShowRankingWindowAfter":
                cont = new ShowRankingWindow(this, this.hidatoJugadoDificultad).$$$getRootComponent$$$();
                break;

        }

        if(cont != null){
            loadVentana(cont);
        }
    }


    public void cierraVentana() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSED));
    }

    private HidatoPane creaHidatoPane(HidatoRep rep, boolean playable, JugarHidatosCTRL controladorPartida){
        HidatoPane pane;
        int width = 600;
        int height = 500;
        if(rep.forma == TipoHidato.CUADRADO){
            pane = new SquareHidatoPane(rep,
                    width, height, controladorPartida, playable);
        }

        else if(rep.forma == TipoHidato.TRIANGULAR){
            pane = new TriangularHidatoPane(rep,
                    width, height, controladorPartida, playable);
        }
        else{
            pane = new HexagonalHidatoPane(rep,
                    width, height, controladorPartida, playable);
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

    public ArrayList getEntradasRanking(Dificultad dificultat) throws NoSuchFileException {
        return domini.getRankingCTRL(dificultat).getEntradasRanking();
    }

    public void seleccionaHidato(int id){
        JugarHidatosCTRL jugarHidatosCTRL = this.seleccionarHidatosCTRL.getControladorPartida(id);
        Container cont = new PlayHidatoWindow(creaHidatoPane(jugarHidatosCTRL.getRepresentacionHidato(), true,
                jugarHidatosCTRL), jugarHidatosCTRL, this).$$$getRootComponent$$$();

        loadVentana(cont);
    }

    public void setHidatoJugadoDificultad(Dificultad hidatoJugadoDificultad) {
        this.hidatoJugadoDificultad = hidatoJugadoDificultad;
    }
}
