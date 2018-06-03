package com.hidatosdecarbono;

import java.awt.*;
import java.awt.geom.Area;

public class HidatoCell {

    TipoCelda tipo;
    Area area;
    double height, width;
    public HidatoCell(TipoCelda tipo, Area area){
        this.tipo = tipo;
        this.area = area;
    }

    public Area getArea(){
        return area;
    }

    public TipoCelda getTipo(){
        return tipo;
    }

    public void setValue(String value, Graphics2D g2d){
        g2d.drawString(value, area.getBounds().getLocation().x+ area.getBounds().width/2, area.getBounds().getLocation().y + area.getBounds().height/2);
    }
}
