package com.hidatosdecarbono;

import jdk.nashorn.internal.scripts.JO;

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
    private JugarHidatosCTRL jugaCtrl;

    protected List<HidatoCell> cells = new ArrayList<>();
    private HidatoCell highlighted;

    protected HidatoPane(HidatoRep rep, int preferredWidth, int preferredHeight, JugarHidatosCTRL jugaCtrl, boolean playable){
        this.preferredWidth = preferredWidth;
        this.preferredHeight = preferredHeight;
        this.rep = rep;
        this.jugaCtrl = jugaCtrl;

        if(playable){
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

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    super.mouseClicked(mouseEvent);
                    HidatoCell clickedCell = null;
                    int pos=0;
                    for(HidatoCell cell : cells){
                        if(cell.getArea().contains(mouseEvent.getPoint())){
                            clickedCell = cell;
                            break;
                        }
                        pos++;
                    }
                    if(SwingUtilities.isRightMouseButton(mouseEvent)){
                        boolean move = jugaCtrl.retroceder();
                        if(move){
                            rep.tablero = jugaCtrl.getTablero();
                            updateBoard(false);
                            repaint();
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "NO ES POT RETROCEDIR","ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else if(SwingUtilities.isLeftMouseButton(mouseEvent)){
                        if(clickedCell != null && clickedCell.getTipo() == TipoCelda.VARIABLE && clickedCell.getValue() == 0){
                            int i = pos/rep.nColumnas;
                            int j = pos%rep.nColumnas;
                            boolean move = jugaCtrl.mueve(i, j);

                            if(move){
                                rep.tablero = jugaCtrl.getTablero();
                                clickedCell.setValue(Integer.parseInt(rep.tablero[i][j]));
                            }
                            else{
                                JOptionPane.showMessageDialog(null, "MOVIMENT ERRONI","ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
            });
        }


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

    public void updateBoard(boolean nuevaFija){
        int pos=0;
        for(HidatoCell cell : cells){

            if(cell.getTipo() == TipoCelda.VARIABLE || cell.getTipo() == TipoCelda.FIJA){
                String newValueString = rep.tablero[pos/rep.nColumnas][pos%rep.nColumnas];
                int newValue;
                if(newValueString.equals("?")){
                    newValue = 0;
                }
                else{
                    newValue = Integer.parseInt(newValueString);
                }
                if(cell.getValue() != newValue){
                    cell.setValue(newValue);
                    if(nuevaFija){
                        cell.setTipo(TipoCelda.FIJA);
                    }
                }
            }

            pos++;
        }
        repaint();
    }

}
