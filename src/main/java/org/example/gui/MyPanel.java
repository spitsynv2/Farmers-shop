package org.example.gui;

import javax.swing.*;
import java.awt.*;

class MyPanel extends JPanel {

    private int arcSize;
    private boolean isDrawLogo = false;
    private String path;

    public MyPanel(LayoutManager layout, int arcSize) {
        super(layout);
        this.arcSize = arcSize;
        setOpaque(false);
    }

    public void SetIsDrawLogo(boolean drawl,String path) {
        this.isDrawLogo = drawl;
        this.path = path;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(229, 203, 181, 169));
        g2.fillRect(0, 0, getWidth(), getHeight());

        int whitePanelWidth = (int) (getWidth() * 0.85);
        int whitePanelHeight = (int) (getHeight() * 0.85);
        int x = (getWidth() - whitePanelWidth) / 2;
        int y = (getHeight() - whitePanelHeight) / 2;
        g2.setColor(new Color(243, 236, 226));
        g2.fillRoundRect(x, y, whitePanelWidth, whitePanelHeight, arcSize, arcSize);


        if (isDrawLogo){
            drawLogo(g2,path);
        }

        g2.dispose();
    }

    private void drawLogo(Graphics2D g2, String path) {
            ImageIcon logoIcon = new ImageIcon(path);

            int maxWidth = (int) (getWidth() * 0.4);
            int maxHeight = (int) (getHeight() * 0.4);

            int logoWidth = logoIcon.getIconWidth();
            int logoHeight = logoIcon.getIconHeight();

            if (logoWidth > maxWidth || logoHeight > maxHeight) {
                double widthScale = (double) maxWidth / logoWidth;
                double heightScale = (double) maxHeight / logoHeight;
                double scale = Math.min(widthScale, heightScale);

                logoWidth = (int) (logoWidth * scale);
                logoHeight = (int) (logoHeight * scale);
            }

            int x = (getWidth() - logoWidth) / 2;
            int y = (getHeight() - logoHeight) / 2;

            if (path.equals("farmer_logo_login.png")){
                g2.drawImage(logoIcon.getImage(), x, y-39, logoWidth, logoHeight, this);
            }else if(path.equals("farmer_logo_customer_menu.png")) {
                g2.drawImage(logoIcon.getImage(), x, y-39, logoWidth, logoHeight, this);
            }else {
                g2.drawImage(logoIcon.getImage(), x, y-15, logoWidth, logoHeight, this);
            }


    }

}