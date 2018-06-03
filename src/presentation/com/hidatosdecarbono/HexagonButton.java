package com.hidatosdecarbono;

import javax.swing.*;
import java.awt.*;

public class HexagonButton extends JButton {
    private Shape hexagon;

    public HexagonButton(float centerX, float centerY, float size){
        Polygon p = new Polygon();

        for(int i=0; i<6; i++){
            float angleDegrees = (60 * i) - 30;
            float angleRad = (float)Math.PI / (180 * angleDegrees);

            float x = centerX + size * (float)Math.cos(angleRad);
            float y = centerY + size * (float)Math.sin(angleRad);

            p.addPoint(Math.round(x), Math.round(y));
        }
    }
}
