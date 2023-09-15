package org.example;

import org.example.shopSystem.Manager;
import org.example.shopSystem.Shop;
import org.example.gui.UserInterface;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        Shop shop = new Shop();

        //Manager is example of not used Use-Case
        Manager manager = Manager.getManager("super-user"); //You can see information in the console
        manager.setShop(shop);

        SwingUtilities.invokeLater(() -> {
            try {
                new UserInterface(shop);
            } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
                     IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }
}