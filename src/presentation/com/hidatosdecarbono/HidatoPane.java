package com.hidatosdecarbono;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;

public abstract class HidatoPane extends JPanel {
    protected int preferredWidth, preferredHeight;
    protected HidatoRep rep;

    protected List<HidatoCell> cells = new ArrayList<>();
    private HidatoCell highlighted;

    protected HidatoPane(HidatoRep rep, int preferredWidth, int preferredHeight){
        this.preferredWidth = preferredWidth;
        this.preferredHeight = preferredHeight;
        this.rep = rep;

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                highlighted = null;
                for (HidatoCell cell : cells) {
                    if (cell.getArea().contains(e.getPoint())) {
                        highlighted = cell;
                        break;
                    }
                }
                repaint();
            }
        });

    }

    @Override
    public void invalidate() {
        super.invalidate();
        putCells();
    }

    protected abstract void putCells();

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        if (highlighted != null && highlighted.getTipo() == TipoCelda.VARIABLE) {
            g2d.setColor(Color.GREEN);
            g2d.fill(highlighted.getArea());
        }
        g2d.setColor(Color.BLACK);
        for (HidatoCell cell : cells) {
            if(cell.getTipo() == TipoCelda.FIJA) {
                g2d.draw(cell.getArea());
                cell.setValue(""+cell.getValue(), g2d);
            }
            else if(cell.getTipo() == TipoCelda.AGUJERO){
                g2d.setColor(Color.black);
                g2d.fill(cell.getArea());
            }
            else if(cell.getTipo() == TipoCelda.VARIABLE){
                g2d.draw(cell.getArea());
                if(cell.getValue()!= 0){
                    cell.setValue(""+cell.getValue(), g2d);
                }
            }
            else{
                //Si es invisible pues ya tal.
            }

        }
        g2d.dispose();
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(preferredWidth, preferredHeight);
    }

    /**
     * Crea una nueva celda para dibujar en la vista a partir de la representacion del Hidato
     * @param i Fila de la celda
     * @param j Columna de la celda
     * @return Una nueva HidatoCell para pintar.
     */
    protected HidatoCell cellFromBoard(int i, int j, Area area){
        String cell = rep.tablero[i][j];
        HidatoCell celda = null;
        if(cell.equals("?")){
            celda = new HidatoCell(TipoCelda.VARIABLE, area, 0);
        }
        else if(cell.equals("*")){
            celda = new HidatoCell(TipoCelda.AGUJERO, area, 0);
        }

        else if(cell.equals("#")){
            celda = new HidatoCell(TipoCelda.INVISIBLE, area, 0);
        }
        else{
            celda = new HidatoCell(TipoCelda.FIJA, area, Integer.parseInt(cell));
        }

        return celda;
    }

}
