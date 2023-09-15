package org.example.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

class RoundButton extends JButton {
    private static final int ARC_SIZE = 20;

    public RoundButton(String text) {
        super(text);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setForeground(new Color(0x1e1e20));
        setBackground(new Color(0xEACD85));
        setFocusPainted(false);
        setFont(new Font("Arial", Font.PLAIN, 16));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isArmed()) {
            g2.setColor(getBackground().darker());
        } else if (getModel().isRollover()) {
            g2.setColor(getBackground().brighter());
        } else {
            g2.setColor(getBackground());
        }

        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, ARC_SIZE, ARC_SIZE));
        super.paintComponent(g2);
        g2.dispose();
    }


}