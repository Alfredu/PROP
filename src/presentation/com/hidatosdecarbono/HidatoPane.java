package com.hidatosdecarbono;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class HidatoPane extends JPanel {
    protected int nCols, nRows,preferredWidth, getPreferredHeight;

    protected List<HidatoCell> cells = new ArrayList<>();
    private HidatoCell highlighted;

    public HidatoPane(int nRows, int nCols,
                      int preferredWidth, int getPreferredHeight){
        this.preferredWidth = preferredWidth;
        this.getPreferredHeight = getPreferredHeight;
        this.nCols = nCols;
        this.nRows = nRows;

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
        int i=0;
        for (HidatoCell cell : cells) {
            if(cell.getTipo() == TipoCelda.FIJA) {
                g2d.draw(cell.getArea());
                cell.setValue(""+i, g2d);
            }
            else if(cell.getTipo() == TipoCelda.AGUJERO){
                g2d.setColor(Color.black);
                g2d.fill(cell.getArea());
            }
            i++;

        }
        g2d.dispose();
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 400);
    }

}
