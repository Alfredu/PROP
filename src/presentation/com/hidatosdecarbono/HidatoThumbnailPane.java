package com.hidatosdecarbono;

import javax.swing.*;
import java.awt.*;

public class HidatoThumbnailPane extends JPanel {

    HidatoPane hidato;
    String forma;
    String nFilas;
    String nColumnas;
    HidatoRep rep;

    public HidatoThumbnailPane(HidatoRep rep){

        this.forma = rep.forma.toString();
        this.nFilas = rep.nFilas+"";
        this.nColumnas = rep.nColumnas+"";
        this.rep = rep;
        HidatoPane thumbnail;
        if(rep.forma == TipoHidato.CUADRADO){
            thumbnail = new SquareHidatoPane(rep, 300, 165, null, false);
        }
        else if(rep.forma == TipoHidato.TRIANGULAR){
            thumbnail = new TriangularHidatoPane(rep, 300, 165, null, false);
        }
        else{
            thumbnail = new HexagonalHidatoPane(rep, 300, 165, null, false);
        }

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;

        this.add(thumbnail, c);
        this.add(new JLabel("<html>"+forma+"&nbsp<br>"+rep.adyacencia.toString()));
        this.add(new JLabel(nFilas+" x "+nColumnas+"  "));
    }
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(300, 200);

    }
}
