package com.hidatosdecarbono;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HidatoListPane extends JPanel {

    private JPanel mainList;

    public HidatoListPane() {
        setLayout(new BorderLayout());

        mainList = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        mainList.add(new JPanel(), gbc);

        add(new JScrollPane(mainList));
        //add(add, BorderLayout.SOUTH);

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }

    public void addThumbnail(HidatoThumbnailPane hidato){
        hidato.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainList.add(hidato, gbc, 0);

        hidato.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                super.mouseEntered(mouseEvent);
                hidato.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5.0f)));
            }
        });
        hidato.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                super.mouseExited(mouseEvent);
                hidato.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
            }
        });


        validate();
        repaint();
    }

    public void emptyList(){
        mainList.removeAll();
        validate();
        repaint();
    }
}
