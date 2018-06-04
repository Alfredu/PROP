package com.hidatosdecarbono;

import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;

public class SquareHidatoPane extends HidatoPane{

    public SquareHidatoPane(int nRows, int nCols,
                               int preferredWidth, int getPreferredHeight){
        super(nRows, nCols, preferredWidth, getPreferredHeight);
    }

    @Override
    protected void putCells() {
        GeneralPath path = new GeneralPath();

        double rowHeight = getHeight() * 1.14f / nRows;
        double colWidth = getWidth() / nCols;

        double size = Math.min(rowHeight, colWidth) / 2d;

        double centerX = size / 2d;
        double centerY = size / 2d;

        double width = 2 * size;
        double height = size * 2;

        double x,y;

        x = centerX - (size / 2d);
        y = centerY - (size / 2d);
        path.moveTo(x,y);
        path.lineTo(x,y+(width));
        path.lineTo(x+width, y+height);
        path.lineTo(x+width, y);
        path.closePath();
        cells.clear();
        double yPos = size / 40d ;
        for (int row = 0; row < nRows; row++) {
            double offset = 0;
            double xPos = offset;
            for (int col = 0; col < nCols; col++) {
                AffineTransform at = AffineTransform.getTranslateInstance(xPos, yPos);
                Area area = new Area(path);
                area = area.createTransformedArea(at);
                TipoCelda tipo = TipoCelda.FIJA;
                cells.add(new HidatoCell(tipo, area));
                xPos += width;
            }
            yPos += height;
        }
    }


}
