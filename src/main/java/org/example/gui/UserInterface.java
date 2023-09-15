package org.example.gui;

import org.example.products.Fertilizer;
import org.example.products.part.Part;
import org.example.products.Product;
import org.example.products.ProductFactory;
import org.example.products.seed.*;
import org.example.shopSystem.Address;
import org.example.shopSystem.Customer;
import org.example.shopSystem.Order;
import org.example.shopSystem.Shop;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;



public class UserInterface {

    private final Shop shop;
    private Customer customer;
    private JFrame mainMenuFrame;
    private JFrame registerFrame;
    private JFrame loginFrame;


    public UserInterface(Shop shop) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.shop = shop;
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        UIManager.put("Button.arc", 20);
        UIManager.put("Button.background", Color.WHITE);
        UIManager.put("Button.foreground", Color.BLACK);
        showMainMenuPage();

    }

    private void showMainMenuPage() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        // Main Menu Page
        mainMenuFrame = new JFrame("Main Menu");
        mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenuFrame.setSize(600, 600);
        mainMenuFrame.setLocationRelativeTo(null);
        mainMenuFrame.setResizable(false);

        MyPanel mainMenuPanel = new MyPanel(new GridBagLayout(), 20);
        mainMenuPanel.SetIsDrawLogo(true,"farmer_logo.png");
        mainMenuFrame.getContentPane().add(mainMenuPanel);

        JLabel shopNameLabel = new JLabel("Welcome to " + shop.getName());
        shopNameLabel.setForeground(new Color(0x1e1e20));
        shopNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainMenuPanel.add(shopNameLabel, createGridBagConstraints(0, 0, 2, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(35, 0, 20, 0)));

        JLabel additionalTextLabel = new JLabel("Feel free to explore our shop");
        additionalTextLabel.setForeground(new Color(0x1e1e20));
        additionalTextLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        mainMenuPanel.add(additionalTextLabel, createGridBagConstraints(0, 1, 2, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(-85, 0, 320, 0)));

        JButton loginMenuButton = new RoundButton("Login");
        loginMenuButton.setForeground(new Color(0x1e1e20));
        mainMenuPanel.add(loginMenuButton, createGridBagConstraints(0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(195, 85, -10, 85)));

        JButton registerMenuButton = new RoundButton("Register");
        registerMenuButton.setForeground(new Color(0x1e1e20));
        mainMenuPanel.add(registerMenuButton, createGridBagConstraints(0, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(-75, 85, 30, 85)));

        registerMenuButton.addActionListener(e -> {
            mainMenuFrame.setVisible(false);
            showRegisterPage();
        });

        loginMenuButton.addActionListener(e -> {
            mainMenuFrame.setVisible(false);
            showLoginPage();
        });

        mainMenuFrame.setVisible(true);
    }

    private void showRegisterPage() {

        // Register Page
        registerFrame = new JFrame("Customer Registration");
        registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registerFrame.setSize(600, 600);
        registerFrame.setLocationRelativeTo(null);
        registerFrame.setResizable(false);

        MyPanel registerPanel = new MyPanel(new GridBagLayout(),20);
        registerPanel.setBackground(Color.WHITE);
        registerFrame.getContentPane().add(registerPanel);

        JLabel requiredLabel = new JLabel("* - Required field");
        requiredLabel.setForeground(Color.RED);
        requiredLabel.setFont(new Font("Arial", Font.ITALIC, 15));
        registerPanel.add(requiredLabel, createGridBagConstraints(0, 9, 2, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 20, 10, 20)));

        JLabel nameLabel = new JLabel("Name*:");
        nameLabel.setForeground(new Color(0x1e1e20));
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        registerPanel.add(nameLabel, createGridBagConstraints(0, 0, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(60, 60, 5, 5)));

        RoundTextField nameField = new RoundTextField();
        registerPanel.add(nameField, createGridBagConstraints(1, 0, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(60, -105, 5, 70)));

        JLabel phoneNumberLabel = new JLabel("Phone number*:");
        phoneNumberLabel.setForeground(new Color(0x1e1e20));
        phoneNumberLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        registerPanel.add(phoneNumberLabel, createGridBagConstraints(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(15, 60, 5, 5)));

        RoundTextField phoneNumberField = new RoundTextField();
        registerPanel.add(phoneNumberField, createGridBagConstraints(1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(15, -105, 5, 70)));

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(new Color(0x1e1e20));
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        registerPanel.add(emailLabel, createGridBagConstraints(0, 4, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(15, 60, 5, 5)));

        RoundTextField emailField = new RoundTextField();
        registerPanel.add(emailField, createGridBagConstraints(1, 4, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(15, -105, 5, 70)));

        JLabel locationLabelHome = new JLabel("Home location*:");
        locationLabelHome.setForeground(new Color(0x1e1e20));
        locationLabelHome.setFont(new Font("Arial", Font.PLAIN, 15));
        registerPanel.add(locationLabelHome, createGridBagConstraints(0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(15, 60, 5, 5)));

        RoundTextField locationFieldHome = new RoundTextField();
        registerPanel.add(locationFieldHome, createGridBagConstraints(1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(15, -105, 5, 70)));

        JLabel postalCodeLabelHome = new JLabel("Home postal code*:");
        postalCodeLabelHome.setForeground(new Color(0x1e1e20));
        postalCodeLabelHome.setFont(new Font("Arial", Font.PLAIN, 15));
        registerPanel.add(postalCodeLabelHome, createGridBagConstraints(0, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(15, 60, 5, 5)));

        RoundTextField postalCodeFieldHome = new RoundTextField();
        registerPanel.add(postalCodeFieldHome, createGridBagConstraints(1, 3, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(15, -105, 5, 70)));

        JLabel locationLabelPost = new JLabel("Post location:");
        locationLabelPost.setForeground(new Color(0x1e1e20));
        locationLabelPost.setFont(new Font("Arial", Font.PLAIN, 15));
        registerPanel.add(locationLabelPost, createGridBagConstraints(0, 5, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(15, 60, 5, 5)));

        RoundTextField locationFieldPost = new RoundTextField();
        registerPanel.add(locationFieldPost, createGridBagConstraints(1, 5, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(15, -105, 5, 70)));

        JLabel postalCodeLabelPost = new JLabel("Post postal code:");
        postalCodeLabelPost.setForeground(new Color(0x1e1e20));
        postalCodeLabelPost.setFont(new Font("Arial", Font.PLAIN, 15));
        registerPanel.add(postalCodeLabelPost, createGridBagConstraints(0, 6, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(15, 60, 20, 5)));

        RoundTextField postalCodeFieldPost = new RoundTextField();
        registerPanel.add(postalCodeFieldPost, createGridBagConstraints(1, 6, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(15, -105, 20, 70)));

        JButton registerButton = new RoundButton("Register");
        registerPanel.add(registerButton, createGridBagConstraints(0, 7, 2, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 70, 10, 70)));

        JButton backToMainMenuButton = new RoundButton("Back to Main Menu");
        registerPanel.add(backToMainMenuButton, createGridBagConstraints(0, 8, 2, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 70, 25, 70)));

        nameField.setPlaceholder("First and last name");
        phoneNumberField.setPlaceholder("Phone number without +");
        emailField.setPlaceholder("Valid email address");
        locationFieldHome.setPlaceholder("Home address");
        postalCodeFieldHome.setPlaceholder("Home postal code");
        locationFieldPost.setPlaceholder("Shipping address");
        postalCodeFieldPost.setPlaceholder("Shipping postal code");

        registerButton.addActionListener(e -> {
            String name = nameField.getText();
            String phoneNumber = phoneNumberField.getText();
            String email = emailField.getText();
            String locationHome = locationFieldHome.getText();
            String postalCodeHome = postalCodeFieldHome.getText();
            String locationPost = locationFieldPost.getText();
            String postalCodePost = postalCodeFieldPost.getText();

            Customer customer = new Customer();

            if (name.isEmpty() || phoneNumber.isEmpty() || locationHome.isEmpty() || postalCodeHome.isEmpty()) {
                JOptionPane.showMessageDialog(registerFrame, "Please fill in the required fields.");
                return;
            }

            if (!phoneNumber.matches("^\\d{7,}$")) {
                JOptionPane.showMessageDialog(registerFrame, "Invalid phone number format.");
                return;
            }

            if (!postalCodeHome.matches("^\\d+(-\\d+)*$")) {
                JOptionPane.showMessageDialog(registerFrame, "Invalid postal number format.");
                return;
            }

            customer.addAddress("Home", new Address(locationHome, postalCodeHome));

            if (!email.matches("^[a-zA-Z0-9._\\-]+@[a-zA-Z0-9.\\-]+$") && !email.isEmpty()) {
                JOptionPane.showMessageDialog(registerFrame, "Invalid email format.");
                return;
            }

            if (!postalCodePost.matches("^\\d+(-\\d+)*$") && !locationPost.isEmpty() && !postalCodePost.isEmpty()) {
                JOptionPane.showMessageDialog(registerFrame, "Invalid postal number format.");
                return;
            }

            if (!email.isEmpty()) {
                customer.register(name, phoneNumber, email);
            } else {
                customer.register(name, phoneNumber);
            }

            if (!locationPost.isEmpty() && !postalCodePost.isEmpty()) {
                customer.addAddress("Post", new Address(locationPost, postalCodePost));
            }

            customer.saveCustomer(shop);

            JTextArea messageArea = new JTextArea("Registration Successful!\nYour login key: " + customer.getId());
            messageArea.setEditable(false);
            messageArea.setWrapStyleWord(true);
            messageArea.setLineWrap(true);
            messageArea.setFont(new Font("Arial", Font.PLAIN, 14));

            JScrollPane scrollPane = new JScrollPane(messageArea);
            scrollPane.setPreferredSize(new Dimension(315, 100));

            JOptionPane.showMessageDialog(registerFrame, scrollPane);

            registerFrame.setVisible(false);
            try {
                showMainMenuPage();
            } catch (UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException |
                     ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        backToMainMenuButton.addActionListener(e -> {
            registerFrame.setVisible(false);
            try {
                showMainMenuPage();
            } catch (UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException |
                     ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        registerFrame.setVisible(true);
    }

    private void showLoginPage() {

        // Login Page
        loginFrame = new JFrame("Customer Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(600, 600);
        loginFrame.setResizable(false);
        loginFrame.setLocationRelativeTo(null);

        MyPanel loginPanel = new MyPanel(new GridBagLayout(), 20);
        loginPanel.SetIsDrawLogo(true,"farmer_logo_login.png");
        loginFrame.getContentPane().add(loginPanel);

        JLabel welcomeLabel = new JLabel("Please enter your unique login:");
        welcomeLabel.setForeground(new Color(0x1e1e20));
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        loginPanel.add(welcomeLabel, createGridBagConstraints(0, 0, 2, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(45, 0, 20, 0)));

        RoundTextField loginField = new RoundTextField();
        loginField.setPlaceholder("Enter your login");

        loginPanel.add(loginField, createGridBagConstraints(0, 1, 2, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(170, 85, 5, 85)));

        JButton loginButton = new RoundButton("Login");
        loginPanel.add(loginButton, createGridBagConstraints(0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(255, 85, -10, 85)));

        JButton backToMainMenuButton = new RoundButton("Back to main menu");
        loginPanel.add(backToMainMenuButton, createGridBagConstraints(0, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(-30, 85, 30, 85)));

        loginButton.addActionListener(e -> {
            String login = loginField.getText();

            shop.setCustomers(shop.loadAllCustomerIdsAndStatus());

            if (!shop.getCustomers().containsKey(login)) {
                JOptionPane.showMessageDialog(loginFrame, "Incorrect login, please try again.");
                return;
            }

            customer = Customer.retrieveCustomerFromDatabase(login);
            customer.setOrderList(Order.loadOrdersByCustomerId(customer.getId()));

            loginFrame.setVisible(false);
            showCustomerMainMenuPage();
        });

        backToMainMenuButton.addActionListener(e -> {
            loginFrame.setVisible(false);
            try {
                showMainMenuPage();
            } catch (UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException |
                     ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        loginFrame.setVisible(true);
    }

    private void showCustomerMainMenuPage() {

        //Customer Main Menu Page
        JFrame customerMenu = new JFrame("Customer Main Page");
        customerMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        customerMenu.setSize(600, 600);
        customerMenu.setLocationRelativeTo(null);

        MyPanel customerMenuPanel = new MyPanel(new GridBagLayout(),20);
        customerMenuPanel.SetIsDrawLogo(true,"farmer_logo_customer_menu.png");
        customerMenu.getContentPane().add(customerMenuPanel);

        JLabel titleLabel = new JLabel("Welcome to Customer Main Page");
        titleLabel.setForeground(new Color(0x1e1e20));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        customerMenuPanel.add(titleLabel, createGridBagConstraints(0, 0, 2, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(45, 10, 0, 10)));

        JButton lookOrdersButton = new RoundButton("Look Orders");
        customerMenuPanel.add(lookOrdersButton, createGridBagConstraints(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(150, 85, 0, 5)));

        JButton makeOrderButton = new RoundButton("Make Order");
        customerMenuPanel.add(makeOrderButton, createGridBagConstraints(1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(150, 5, 0, 85)));

        JButton customerInfoButton = new RoundButton("Customer Information");
        customerMenuPanel.add(customerInfoButton, createGridBagConstraints(0, 1, 2, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(245, 85, 0, 85)));

        JButton backButton = new RoundButton("Back to Login");
        customerMenuPanel.add(backButton, createGridBagConstraints(0, 3, 2, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(-65, 85, 10, 85)));

        lookOrdersButton.addActionListener(e -> {
            customerMenu.setVisible(false);
            showOrderListPage();
        });

        makeOrderButton.addActionListener(e -> {
            customerMenu.setVisible(false);
            //Products for order
            showSelectProductsPage();
        });

        customerInfoButton.addActionListener(e -> {
            customerMenu.setVisible(false);
            showCustomerInfoPage();
        });

        backButton.addActionListener(e -> {
            customerMenu.setVisible(false);
            showLoginPage();
        });

        customerMenu.setVisible(true);
    }

    private void showCustomerInfoPage() {

        // Customer Information Page
        JFrame customerInfoFrame = new JFrame("Customer Information");
        customerInfoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        customerInfoFrame.setSize(600, 600);
        customerInfoFrame.setResizable(false);
        customerInfoFrame.setLocationRelativeTo(null);

        MyPanel customerInfoPanel = new MyPanel(new GridBagLayout(),20);
        customerInfoFrame.getContentPane().add(customerInfoPanel);

        JLabel idLabel = new JLabel("Customer ID: " + customer.getId());
        idLabel.setForeground(new Color(0x1e1e20));
        idLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        customerInfoPanel.add(idLabel, createGridBagConstraints(0, 0, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(50, 60, 0, 10)));

        JLabel nameLabel = new JLabel("Name: " + customer.getName());
        nameLabel.setForeground(new Color(0x1e1e20));
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        customerInfoPanel.add(nameLabel, createGridBagConstraints(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(-30, 60, 0, 70)));

        JLabel phoneNumberLabel = new JLabel("Phone Number: " + customer.getPhoneNumber());
        phoneNumberLabel.setForeground(new Color(0x1e1e20));
        phoneNumberLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        customerInfoPanel.add(phoneNumberLabel, createGridBagConstraints(0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(-30, 60, 0, 10)));

        Map<String, Address> addresses = customer.getAddresses();
        int addressRow = 3;
        for (String key : addresses.keySet()) {
            Address address = addresses.get(key);
            JLabel addressLabel = new JLabel(key + ": " + address.getLocation() + " " + address.getPostalCode());
            addressLabel.setForeground(new Color(0x1e1e20));
            addressLabel.setFont(new Font("Arial", Font.PLAIN, 15));
            customerInfoPanel.add(addressLabel, createGridBagConstraints(0, addressRow, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(-30, 60, 0, 10)));
            addressRow++;
        }

        JLabel emailLabel = new JLabel("Email: " + "unspecified");
        emailLabel.setForeground(new Color(0x1e1e20));
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        if (customer.getEmail() != null){
            emailLabel = new JLabel("Email: " + customer.getEmail());
            emailLabel.setForeground(new Color(0x1e1e20));
            emailLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        }
        customerInfoPanel.add(emailLabel, createGridBagConstraints(0, addressRow, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(-30, 60, 0, 10)));

        addressRow++;

        RoundButton backButton = new RoundButton("Back");
        if (addressRow == 5){
            customerInfoPanel.add(backButton, createGridBagConstraints(0, addressRow, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 70, 25, 70)));

        }else {
            customerInfoPanel.add(backButton, createGridBagConstraints(0, addressRow, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(-30, 70, 30, 70)));

        }


        backButton.addActionListener(e -> {
            customerInfoFrame.setVisible(false);
            showCustomerMainMenuPage();
        });

        customerInfoFrame.setVisible(true);
    }

    private void showSelectProductsPage() {

        // Product List Page for making an order
        JFrame productListFrame = new JFrame("Product List");
        productListFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        productListFrame.setSize(850, 600);
        productListFrame.setLocationRelativeTo(null);
        productListFrame.setResizable(false);

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.addColumn("Id");
        model.addColumn("Price");
        model.addColumn("Name");
        model.addColumn("Information");

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setForeground(new Color(0x1e1e20));
        headerRenderer.setFont(new Font("Arial", Font.BOLD, 15));
        headerRenderer.setBackground(new Color(229, 203, 181, 169));

        DefaultTableCellRenderer columnRenderer = new DefaultTableCellRenderer();
        columnRenderer.setForeground(new Color(0x1e1e20));
        columnRenderer.setFont(new Font("Arial", Font.BOLD, 15));
        columnRenderer.setBackground(new Color(243, 236, 226));


        List<Product> products = new ProductFactory().getAllProducts(false, 0);
        for (Product product : products) {
            model.addRow(new Object[]{product.getId(),product.getPrice(),product.getName(),product.getInformation()});
        }

        JTable productTable = new JTable(model);
        productTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        productTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        productTable.getColumnModel().getColumn(1).setPreferredWidth(65);
        productTable.getColumnModel().getColumn(2).setPreferredWidth(250);
        productTable.getColumnModel().getColumn(3).setPreferredWidth(430);

        for (int columnIndex = 0; columnIndex < 4; columnIndex++) {
            productTable.getColumnModel().getColumn(columnIndex).setResizable(false);
            productTable.getColumnModel().getColumn(columnIndex).setCellRenderer(columnRenderer);
        }

        productTable.getTableHeader().setDefaultRenderer(headerRenderer);
        productTable.getTableHeader().setReorderingAllowed(false);


        productTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = productTable.getSelectedRow();
                    if (row >= 0) {
                        String productName = productTable.getValueAt(row, 2).toString();
                        Product selectedProduct = products.get(row);

                        StringBuilder productDetails = new StringBuilder();

                        productDetails.append("Name: ").append(selectedProduct.getName()).append("\n")
                                .append("Price: $").append(selectedProduct.getPrice()).append("\n")
                                .append("Id: ").append(selectedProduct.getId()).append("\n");

                        if (selectedProduct instanceof Fertilizer fertilizer) {
                            productDetails.append("Product type: Fertilizer").append("\n")
                                    .append("Expiration Date: ").append(fertilizer.getExpirationDate()).append("\n")
                                    .append("Type: ").append(fertilizer.getType()).append("\n");
                        } else if (selectedProduct instanceof Seed seed) {
                            productDetails.append("Product type: Seed").append("\n")
                                    .append("Expiration Date: ").append(seed.getExpirationData()).append("\n")
                                    .append("Germination Requirements: ").append(seed.getGerminationRequirements()).append("\n");

                            if (seed instanceof VegetablesFruits vegetablesFruits) {
                                productDetails.append("Product type: Vegetables and Fruits ").append("\n")
                                        .append("Vegetable or Fruit type: ").append(vegetablesFruits.getType()).append("\n");
                            } else if (seed instanceof RawSeed) {
                                productDetails.append("Product type: Raw Seeds").append("\n")
                                        .append("Is diseases resistant").append("\n");
                            } else if (seed instanceof DressedSeeds dressedSeeds) {
                                productDetails.append("Product type: Dressed Seeds").append("\n")
                                        .append("Chemical type: ").append(dressedSeeds.getChemicalType()).append("\n");
                            } else if (seed instanceof CerealSeeds cerealSeeds) {
                                productDetails.append("Product type: Cereal Seeds").append("\n")
                                        .append("Cereal type: ").append(cerealSeeds.getCerealType()).append("\n")
                                        .append("Is fodder: ").append(cerealSeeds.isFodder()).append("\n");

                                if (cerealSeeds instanceof CerealDressedSeeds cerealDressedSeeds) {
                                    productDetails.append("Product type: Cereal Dressed Seeds").append("\n")
                                            .append("Chemical type: ").append(cerealDressedSeeds.getChemicalType()).append("\n")
                                            .append("Processing %: ").append(cerealDressedSeeds.getProcessingPercentage()).append("\n");
                                }
                            }
                        } else if (selectedProduct instanceof Part part) {
                            productDetails.append("Product type: Part").append("\n")
                                    .append("Manufacturer: ").append(part.getManufacturer()).append("\n");

                            if (part.getModel() != null) {
                                productDetails.append("Model: ").append(part.getModel()).append("\n");
                            }

                            if (part.getMachineData() != null) {
                                productDetails.append("MachineData: ").append(part.getMachineData()).append("\n");
                            }
                        }

                        JOptionPane.showMessageDialog(productListFrame, productDetails.toString(), "Product Details - " + productName, JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(productTable);
        productListFrame.add(scrollPane);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(229, 203, 181, 169));
        productListFrame.add(buttonPanel, BorderLayout.SOUTH);

        JButton selectButton = new JButton("Select");
        selectButton.setPreferredSize(new Dimension(150, 25));
        selectButton.setFont(new Font("Arial", Font.PLAIN, 15));
        selectButton.setForeground(new Color(0x1e1e20));
        selectButton.setFocusPainted(false);
        buttonPanel.add(selectButton);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150, 25));
        backButton.setFont(new Font("Arial", Font.PLAIN, 15));
        backButton.setForeground(new Color(0x1e1e20));
        backButton.setFocusPainted(false);
        buttonPanel.add(backButton);

        selectButton.addActionListener(e -> {

            int[] selectedRows = productTable.getSelectedRows();
            if (selectedRows.length > 0) {
                List<Product> selectedProducts = new ArrayList<>();
                for (int row : selectedRows) {
                    int modelRow = productTable.convertRowIndexToModel(row);
                    Product product = products.get(modelRow);
                    selectedProducts.add(product);
                }

                customer.setCart(selectedProducts);

                StringBuilder errorMessage = new StringBuilder("These products are missing in the shop:\n");
                StringBuilder approveMessage = new StringBuilder("Selected products:\n");

                customer.getCart().forEach(x -> {
                    if (!shop.isShopContainsProduct(x.getId())) {
                        errorMessage.append("- ").append(x.getName())
                                .append(" (ID: ").append(x.getId())
                                .append(", Price: $").append(x.getPrice())
                                .append(")\n");
                    } else {
                        approveMessage.append("- ").append(x.getName())
                                .append(" (ID: ").append(x.getId())
                                .append(", Price: $").append(x.getPrice())
                                .append(")\n");
                    }
                });

                if (!errorMessage.toString().equals("These products are missing in the shop:\n")) {
                    errorMessage.append("Try again with alternative products\n");
                    JOptionPane.showMessageDialog(productListFrame, errorMessage.toString());
                    return;
                }

                double totalPrice = 0;
                for (int i = 0; i < customer.getCart().size(); i++) {
                    totalPrice += customer.getCart().get(i).getTotalPrice();
                }

                String formattedTotalPrice = new DecimalFormat("#0.00").format(totalPrice);

                JOptionPane.showMessageDialog(productListFrame, approveMessage + "Total price is: " + formattedTotalPrice);

                productListFrame.setVisible(false);

                Order order = new Order();
                order.setCustomer(customer);
                order.setProductList(customer.getCart());

                // Order status -> Creating
                order.changeStatus("Creating");

                showCreateOrderPage(order);
            }
        });

        backButton.addActionListener(e -> {
            productListFrame.setVisible(false);
            showCustomerMainMenuPage();
        });

        productListFrame.setVisible(true);
    }

    private void showCreateOrderPage(Order order) {

        // Make Order Page
        JFrame createOrderFrame = new JFrame("Create Order");
        createOrderFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createOrderFrame.setSize(600, 600);
        createOrderFrame.setResizable(false);
        createOrderFrame.setLocationRelativeTo(null);

        MyPanel orderPanel = new MyPanel(new GridBagLayout(),20);
        createOrderFrame.getContentPane().add(orderPanel, BorderLayout.CENTER);

        JLabel cardNumberLabel = new JLabel("Card Number:");
        cardNumberLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        cardNumberLabel.setForeground(new Color(0x1e1e20));


        RoundTextField cardNumberField = new RoundTextField();
        cardNumberField.setPlaceholder("1234 / 5678 / 9123 / 4567");

        JLabel cvvLabel = new JLabel("CVV:");
        cvvLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        cvvLabel.setForeground(new Color(0x1e1e20));

        RoundTextField cvvField = new RoundTextField();
        cvvField.setPlaceholder("CVV / CVC");

        JLabel expirationDateLabel = new JLabel("Expiration Date (MM/YY):");
        expirationDateLabel.setForeground(new Color(0x1e1e20));
        expirationDateLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        RoundTextField expirationDateField = new RoundTextField();
        expirationDateField.setPlaceholder("01 / 23");

        JLabel shippingTypeLabel = new JLabel("Shipping Type:");
        shippingTypeLabel.setForeground(new Color(0x1e1e20));
        shippingTypeLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        RoundTextField shippingTypeField = new RoundTextField();

        shippingTypeField.setPlaceholder("InPost / DHL / DPD");


        orderPanel.add(cardNumberLabel, createGridBagConstraints(0, 0, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 70, 0, 70)));
        orderPanel.add(cardNumberField, createGridBagConstraints(1, 0, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, -120, 0, 60)));
        orderPanel.add(cvvLabel, createGridBagConstraints(0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(-50, 70, 0, 70)));
        orderPanel.add(cvvField, createGridBagConstraints(1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(-50, -120, 0, 60)));
        orderPanel.add(expirationDateLabel, createGridBagConstraints(0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(-50, 70, 0, 70)));
        orderPanel.add(expirationDateField, createGridBagConstraints(1, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(-50, -120, 0, 60)));
        orderPanel.add(shippingTypeLabel, createGridBagConstraints(0, 3, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(-50, 70, 0, 70)));
        orderPanel.add(shippingTypeField, createGridBagConstraints(1, 3, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(-50, -120, 0, 60)));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        createOrderFrame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        JButton createButton = new JButton("Select");
        createButton.setPreferredSize(new Dimension(150, 25));
        createButton.setFont(new Font("Arial", Font.PLAIN, 15));
        createButton.setForeground(new Color(0x1e1e20));
        createButton.setFocusPainted(false);
        buttonPanel.add(createButton);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150, 25));
        backButton.setFont(new Font("Arial", Font.PLAIN, 15));
        backButton.setForeground(new Color(0x1e1e20));
        backButton.setFocusPainted(false);
        buttonPanel.add(backButton);

        createButton.addActionListener(e -> {
            String cardNumber = cardNumberField.getText().trim().replaceAll("/", "");
            String cvv = cvvField.getText().trim().replaceAll("/", "");
            String expirationDateText = expirationDateField.getText().trim();
            String shippingType = shippingTypeField.getText().trim().replaceAll("/", "");

            if (cardNumber.isEmpty() || cvv.isEmpty() || expirationDateText.isEmpty() || shippingType.isEmpty()) {
                JOptionPane.showMessageDialog(createOrderFrame, "Please fill all details fields.");
                return;
            }

            if (!shippingType.equals("DHL") && !shippingType.equals("DPD") && !shippingType.equals("InPost")) {
                JOptionPane.showMessageDialog(createOrderFrame, "Incorrect shipping type.");
                return;
            }

            if (!cardNumber.matches("\\d{16}")) {
                JOptionPane.showMessageDialog(createOrderFrame, "Invalid card number format. Please enter a 16-digit number.");
                return;
            }

            if (!cvv.matches("\\d{3}")) {
                JOptionPane.showMessageDialog(createOrderFrame, "Invalid CVV format. Please enter a 3-digit number.");
                return;
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yy");
            dateFormat.setLenient(false);
            Date expirationDate;
            try {
                expirationDate = dateFormat.parse(expirationDateText);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(createOrderFrame, "Invalid expiration date. Please enter in the format MM/YY.");
                return;
            }

            //Order status -> In progress
            order.processOrder(cardNumber,shippingType,Integer.parseInt(cvv),expirationDate.getTime());

            //Customer placeOrder(Order)
            customer.setOrderList(Order.loadOrdersByCustomerId(customer.getId()));

            createOrderFrame.setVisible(false);
            showCustomerMainMenuPage();
        });

        backButton.addActionListener(e -> {
            createOrderFrame.setVisible(false);
            showCustomerMainMenuPage();
        });

        createOrderFrame.setVisible(true);
    }

    private void showOrderListPage() {

        // Order List Page
        JFrame orderListFrame = new JFrame("Order List");
        orderListFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        orderListFrame.setSize(850, 600);
        orderListFrame.setLocationRelativeTo(null);


        DefaultTableModel orderTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        orderTableModel.addColumn("Id");
        orderTableModel.addColumn("Order Date");
        orderTableModel.addColumn("Status");

        List<Order> orders = customer.getOrderList();
        for (Order order : orders) {
            Object[] rowData = {
                    order.getId(),
                    order.getOrderDate(),
                    order.getStatus()
            };
            orderTableModel.addRow(rowData);
        }

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setForeground(new Color(0x1e1e20));
        headerRenderer.setFont(new Font("Arial", Font.BOLD, 15));
        headerRenderer.setBackground(new Color(229, 203, 181, 169));

        DefaultTableCellRenderer columnRenderer = new DefaultTableCellRenderer();
        columnRenderer.setForeground(new Color(0x1e1e20));
        columnRenderer.setFont(new Font("Arial", Font.BOLD, 15));
        columnRenderer.setBackground(new Color(243, 236, 226));


        JTable orderTable = new JTable(orderTableModel);
        orderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        orderTable.setAutoCreateRowSorter(true);

        orderTable.getColumnModel().getColumn(0).setPreferredWidth(250);
        orderTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        orderTable.getColumnModel().getColumn(2).setPreferredWidth(100);

        for (int columnIndex = 0; columnIndex < 3; columnIndex++) {
            orderTable.getColumnModel().getColumn(columnIndex).setResizable(false);
            orderTable.getColumnModel().getColumn(columnIndex).setCellRenderer(columnRenderer);
        }

        orderTable.getTableHeader().setDefaultRenderer(headerRenderer);
        orderTable.getTableHeader().setReorderingAllowed(false);

        orderTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = orderTable.getSelectedRow();
                    if (row >= 0) {
                        Order selectedOrder = orders.get(row);

                        String orderDetails = "Order ID: " + selectedOrder.getId() + "\n" +
                                "Order Date: " + selectedOrder.getOrderDate() + "\n" +
                                "Status: " + selectedOrder.getStatus() + "\n" +
                                "Card Number: " + selectedOrder.getPaymentCardNumber() + "\n" +
                                "CVV: " + selectedOrder.getPaymentCvv() + "\n" +
                                "Card Expiration Date: " + new java.sql.Date (selectedOrder.getPaymentCardExpiration()) + "\n" +
                                "Shipping: " + selectedOrder.getPaymentShippingType() + "\n" +
                                "Service Fee: " + selectedOrder.getPaymentServiceFee() + "\n";

                        JOptionPane.showMessageDialog(orderListFrame, orderDetails, "Order Details:", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(orderTable);
        orderListFrame.getContentPane().add(scrollPane);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(229, 203, 181, 169));
        orderListFrame.add(buttonPanel, BorderLayout.SOUTH);

        JButton viewProductsButton = new JButton("Show Products");
        viewProductsButton.setEnabled(false);
        viewProductsButton.setPreferredSize(new Dimension(150, 25));
        viewProductsButton.setFont(new Font("Arial", Font.PLAIN, 15));
        viewProductsButton.setForeground(new Color(0x1e1e20));
        viewProductsButton.setFocusPainted(false);
        viewProductsButton.setEnabled(false);
        buttonPanel.add(viewProductsButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(150, 25));
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 15));
        cancelButton.setForeground(new Color(0x1e1e20));
        cancelButton.setFocusPainted(false);
        cancelButton.setEnabled(false);
        buttonPanel.add(cancelButton);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150, 25));
        backButton.setFont(new Font("Arial", Font.PLAIN, 15));
        backButton.setForeground(new Color(0x1e1e20));
        backButton.setFocusPainted(false);
        buttonPanel.add(backButton);

        orderTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = orderTable.getSelectedRow();
            if (selectedRow != -1) {
                viewProductsButton.setEnabled(true);
                cancelButton.setEnabled(true);
            } else {
                viewProductsButton.setEnabled(false);
                cancelButton.setEnabled(false);
            }
        });

        viewProductsButton.addActionListener(e -> {
            int selectedRow = orderTable.getSelectedRow();
            if (selectedRow != -1) {
                Order selectedOrder = orders.get(selectedRow);
                orderListFrame.setVisible(false);
                showProductsForOrder(selectedOrder);
            }
        });

        cancelButton.addActionListener(e -> {
            int selectedRow = orderTable.getSelectedRow();
            if (selectedRow != -1) {
                Order selectedOrder = customer.getOrderList().get(selectedRow);
                if (!selectedOrder.getStatus().equals("Completed") && !selectedOrder.getStatus().equals("Cancelled")) {
                    int choice = JOptionPane.showConfirmDialog(orderListFrame,
                            "Are you sure you want to cancel the order?",
                            "Cancel Order", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        selectedOrder.changeStatus("Cancelled");
                        Order.updateOrderStatusInDatabase(selectedOrder);

                        orderTable.setValueAt("Cancelled", selectedRow, 2);

                        customer.setOrderList(Order.loadOrdersByCustomerId(customer.getId()));
                    }
                } else {
                    JOptionPane.showMessageDialog(orderListFrame,
                            "Cannot cancel a completed or cancelled order.",
                            "Cancel Order", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        backButton.addActionListener(e -> {
            orderListFrame.setVisible(false);
            showCustomerMainMenuPage();
        });

        orderListFrame.setVisible(true);
    }

    private void showProductsForOrder(Order order) {

        // Product List Page for making an order
        JFrame productListFrame = new JFrame("Product List");
        productListFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        productListFrame.setSize(850, 600);
        productListFrame.setLocationRelativeTo(null);
        productListFrame.setResizable(false);

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.addColumn("Id");
        model.addColumn("Price");
        model.addColumn("Name");
        model.addColumn("Information");

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setForeground(new Color(0x1e1e20));
        headerRenderer.setFont(new Font("Arial", Font.BOLD, 15));
        headerRenderer.setBackground(new Color(229, 203, 181, 169));

        DefaultTableCellRenderer columnRenderer = new DefaultTableCellRenderer();
        columnRenderer.setForeground(new Color(0x1e1e20));
        columnRenderer.setFont(new Font("Arial", Font.BOLD, 15));
        columnRenderer.setBackground(new Color(243, 236, 226));

        List<Product> products = order.getProductList();
        for (Product product : products) {
            model.addRow(new Object[]{product.getId(),product.getPrice(),product.getName(),product.getInformation()});
        }

        JTable productTable = new JTable(model);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        productTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        productTable.getColumnModel().getColumn(1).setPreferredWidth(65);
        productTable.getColumnModel().getColumn(2).setPreferredWidth(250);
        productTable.getColumnModel().getColumn(3).setPreferredWidth(430);

        for (int columnIndex = 0; columnIndex < 4; columnIndex++) {
            productTable.getColumnModel().getColumn(columnIndex).setResizable(false);
            productTable.getColumnModel().getColumn(columnIndex).setCellRenderer(columnRenderer);
        }

        productTable.getTableHeader().setDefaultRenderer(headerRenderer);
        productTable.getTableHeader().setReorderingAllowed(false);


        productTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = productTable.getSelectedRow();
                    if (row >= 0) {
                        String productName = productTable.getValueAt(row, 2).toString();
                        Product selectedProduct = products.get(row);

                        StringBuilder productDetails = new StringBuilder();

                        productDetails.append("Name: ").append(selectedProduct.getName()).append("\n")
                                .append("Price: $").append(selectedProduct.getPrice()).append("\n")
                                .append("Id: ").append(selectedProduct.getId()).append("\n");

                        if (selectedProduct instanceof Fertilizer fertilizer) {
                            productDetails.append("Product type: Fertilizer").append("\n")
                                    .append("Expiration Date: ").append(fertilizer.getExpirationDate()).append("\n")
                                    .append("Type: ").append(fertilizer.getType()).append("\n");
                        } else if (selectedProduct instanceof Seed seed) {
                            productDetails.append("Product type: Seed").append("\n")
                                    .append("Expiration Date: ").append(seed.getExpirationData()).append("\n")
                                    .append("Germination Requirements: ").append(seed.getGerminationRequirements()).append("\n");
                            if (seed instanceof VegetablesFruits vegetablesFruits) {
                                productDetails.append("Product type: Vegetables and Fruits ").append("\n")
                                        .append("Vegetable or Fruit type: ").append(vegetablesFruits.getType()).append("\n");
                            } else if (seed instanceof RawSeed) {
                                productDetails.append("Product type: Raw Seeds").append("\n")
                                        .append("Is diseases resistant").append("\n");
                            } else if (seed instanceof DressedSeeds dressedSeeds) {
                                productDetails.append("Product type: Dressed Seeds").append("\n")
                                        .append("Chemical type: ").append(dressedSeeds.getChemicalType()).append("\n");
                            } else if (seed instanceof CerealSeeds cerealSeeds) {
                                productDetails.append("Product type: Cereal Seeds").append("\n")
                                        .append("Cereal type: ").append(cerealSeeds.getCerealType()).append("\n")
                                        .append("Is fodder: ").append(cerealSeeds.isFodder()).append("\n");

                                if (cerealSeeds instanceof CerealDressedSeeds cerealDressedSeeds) {
                                    productDetails.append("Product type: Cereal Dressed Seeds").append("\n")
                                            .append("Chemical type: ").append(cerealDressedSeeds.getChemicalType()).append("\n")
                                            .append("Processing %: ").append(cerealDressedSeeds.getProcessingPercentage()).append("\n");
                                }
                            }
                        } else if (selectedProduct instanceof Part part) {
                            productDetails.append("Product type: Part").append("\n")
                                    .append("Manufacturer: ").append(part.getManufacturer()).append("\n");

                            if (part.getModel() != null) {
                                productDetails.append("Model: ").append(part.getModel()).append("\n");
                            }

                            if (part.getMachineData() != null) {
                                productDetails.append("MachineData: ").append(part.getMachineData()).append("\n");
                            }
                        }

                        JOptionPane.showMessageDialog(productListFrame, productDetails.toString(), "Product Details - " + productName, JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(productTable);
        productListFrame.add(scrollPane);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(229, 203, 181, 169));
        productListFrame.add(buttonPanel, BorderLayout.SOUTH);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150, 25));
        backButton.setFont(new Font("Arial", Font.PLAIN, 15));
        backButton.setForeground(new Color(0x1e1e20));
        backButton.setFocusPainted(false);
        buttonPanel.add(backButton);

        backButton.addActionListener(e -> {
            productListFrame.setVisible(false);
            showOrderListPage();
        });

        productListFrame.setVisible(true);
    }

    private GridBagConstraints createGridBagConstraints(int gridx, int gridy, int gridwidth, int anchor, int fill, Insets insets) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = 1;
        gbc.anchor = anchor;
        gbc.fill = fill;
        gbc.insets = insets;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        return gbc;
    }

}
