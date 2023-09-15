package org.example.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

class RoundTextField extends JTextField {
    private static final int ARC_SIZE = 20;
    private String placeholder;

    public RoundTextField() {
        super(0);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        setMinimumSize(new Dimension(35, 35));
        setForeground(new Color(0x1e1e20));
        setBackground(new Color(0xEACD85));
        setFont(new Font("Arial", Font.PLAIN, 16));
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (isEnabled()) {
            g2.setColor(getBackground());
        } else {
            g2.setColor(getBackground().darker());
        }

        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, ARC_SIZE, ARC_SIZE));

        if (getText().isEmpty() && placeholder != null) {

            g2.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
            g2.setColor(new Color(0x735B5A5A, true));

            int x = getInsets().left + 2;
            int y = (getHeight() - g2.getFontMetrics().getHeight()) / 2 + g2.getFontMetrics().getAscent();
            g2.drawString(placeholder, x, y);

        }

        super.paintComponent(g2);
        g2.dispose();
    }
}
