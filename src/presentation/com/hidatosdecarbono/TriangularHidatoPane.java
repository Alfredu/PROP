package com.hidatosdecarbono;

import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;

public class TriangularHidatoPane extends HidatoPane {

    public TriangularHidatoPane(int nRows, int nCols, int preferredWidth, int getPreferredHeight) {
        super(nRows, nCols, preferredWidth, getPreferredHeight);
    }

    @Override
    protected void putCells() {
        GeneralPath path = new GeneralPath();

        double rowHeight = getHeight()/ nRows;
        double colWidth = getWidth() / nCols;

        double size = Math.min(rowHeight, colWidth) / 2d;

        double centerX = size / 2d;
        double centerY = size * Math.sqrt(3)/ 2d;

        double width = size * 2;
        double height = centerY * 2;

        double x,y;

        x = centerX - (width / 2d);
        y = centerY - (height/ 2d);
        path.moveTo(x,y);
        path.lineTo(x + (width/2),y+(height));
        path.lineTo(x+width, y);
        path.closePath();
        cells.clear();
        double yPos = size / 40d ;
        for (int row = 0; row < nRows; row++) {
            double offset = 0;
            double xPos = offset;
            for (int col = 0; col < nCols; col++) {
                AffineTransform trans = new AffineTransform();
                trans.translate(xPos + size*0.6, yPos);
                if(col % 2 == 0){
                    trans.rotate(Math.PI, centerX, centerY);
                }
                if(row %2 == 1){
                    trans.rotate(Math.PI, centerX, centerY);
                }
                Area area = new Area(path);
                area = area.createTransformedArea(trans);
                TipoCelda tipo = TipoCelda.AGUJERO;
                cells.add(new HidatoCell(tipo, area));
                xPos += width/2;
            }
            yPos += height;
        }
    }
}
