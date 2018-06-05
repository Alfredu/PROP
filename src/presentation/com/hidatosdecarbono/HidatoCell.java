package com.hidatosdecarbono;

import java.awt.*;
import java.awt.geom.Area;

public class HidatoCell {

    private TipoCelda tipo;
    private Area area;
    private int value;

    public void setTipo(TipoCelda tipo) {
        this.tipo = tipo;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    private double height, width;
    public HidatoCell(TipoCelda tipo, Area area, int value){
        this.tipo = tipo;
        this.area = area;
        this.value = value;
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
