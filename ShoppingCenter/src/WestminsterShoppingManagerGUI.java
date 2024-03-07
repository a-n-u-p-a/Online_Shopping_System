//importing necessary libraries and componenets

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class WestminsterShoppingManagerGUI extends JFrame {

    //Initializing variables
    private DefaultTableModel productsModel;
    private JComboBox<String> categoryComboBox;
    private List<Product> allProducts; // List containing all products
    private ShoppingCart shoppingCart; // Assuming a ShoppingCart class exists
    private JTable productsTable;
    private JButton shoppingCartButton;

    //Declaring fields
    private JTextField productIdField, categoryNameField, nameField, sizeField, colorField, itemsAvailableField;

    public WestminsterShoppingManagerGUI() {
        setTitle("Westminster Shopping Centre");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setPreferredSize(new Dimension(655, 623)); // Set the preferred size of the frame
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        allProducts = new ArrayList<>();
        shoppingCart = new ShoppingCart();

        getContentPane().add(createShoppingCartButtonPanel(), BorderLayout.NORTH);

        // Create components
        add(createCategoryPanel());
        add(createProductTablePanel());
        add(createProductDetailsPanel());
        add(createAddToCartPanel());

        pack(); //Adjusting the window to fit the preferred size and layouts of its subcomponents
        setLocationRelativeTo(null);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeWindow();
            }
        });

    }

    private void closeWindow() {
        //Hides the current ShoppingCartGUI
        setVisible(false);

    }

    private JPanel createCategoryPanel() {
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        categoryPanel.add(new JLabel("Select Product Category"));

        categoryComboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothing"});
        categoryComboBox.addActionListener(this::updateProductTable);

        categoryPanel.add(categoryComboBox);
        return categoryPanel;
    }

    private void updateProductTable(ActionEvent e) {
        String selectedCategory = (String) categoryComboBox.getSelectedItem();

        //Clear the existing table data
        productsModel.setRowCount(0);

        //Add the filtered products to the table
        for (Product product : WestminsterShoppingManager.product_list) {

            if ("All".equals(selectedCategory)) {
                productsModel.addRow(new Object[]{product.getProduct_ID(), product.getProduct_name(),
                        product.getType(), product.getPrice(),
                        product.getNo_of_items()});
            } else if ("Electronics".equals(selectedCategory) && "Electronics".equals(product.getType())) {
                //Only add Electronics if "Electronics" is selected
                productsModel.addRow(new Object[]{product.getProduct_ID(), product.getProduct_name(),
                        product.getType(), product.getPrice(),
                        product.getNo_of_items()});
            } else if ("Clothing".equals(selectedCategory) && "Clothing".equals(product.getType())) {
                //Only add Clothing if "Clothing" is selected
                productsModel.addRow(new Object[]{product.getProduct_ID(), product.getProduct_name(),
                        product.getType(), product.getPrice(),
                        product.getNo_of_items()});
            }
        }
    }


    private JPanel createProductTablePanel() {
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        String[] columns = {"Product ID", "Name", "Category", "Price(€)", "Available Items"};
        Object[][] data = {}; //Initializing with empty data
        productsModel = new DefaultTableModel(data, columns);
        productsTable = new JTable(productsModel);  // Use the class variable here
        productsTable.setRowSorter(new TableRowSorter<>(productsModel)); // Make the table sortable

        //Set custom renderer for availability column
        productsTable.getColumnModel().getColumn(0).setCellRenderer(new warningAvailability());
        productsTable.getColumnModel().getColumn(1).setCellRenderer(new warningAvailability());
        productsTable.getColumnModel().getColumn(2).setCellRenderer(new warningAvailability());
        productsTable.getColumnModel().getColumn(3).setCellRenderer(new warningAvailability());
        productsTable.getColumnModel().getColumn(4).setCellRenderer(new warningAvailability());

        //Add selection listener to update details panel
        productsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && productsTable.getSelectedRow() != -1) {
                int selectedRow = productsTable.getSelectedRow();
                int modelRow = productsTable.convertRowIndexToModel(selectedRow);
                Product selectedProduct = WestminsterShoppingManager.product_list.get(modelRow);
                updateDetailsPanel(selectedProduct);
            }
        });

        JScrollPane scrollPane = new JScrollPane(productsTable);
        scrollPane.setPreferredSize(new Dimension(600, 150)); //Set the preferred size of the scroll pane
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        return tablePanel;
    }


    class warningAvailability extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            int modelRow = table.convertRowIndexToModel(row);
            Product product = WestminsterShoppingManager.product_list.get(modelRow);
            if (product.getNo_of_items() < 4) {
                c.setForeground(Color.RED);
            } else {
                c.setForeground(Color.BLACK);
            }
            return c;
        }
    }

    private void updateDetailsPanel(Product selectedProduct) {
        int selectedRow = productsTable.getSelectedRow();
        if (selectedRow >= 0) {

            productIdField.setText(selectedProduct.getProduct_ID());
            categoryNameField.setText(selectedProduct.getType());
            nameField.setText(selectedProduct.getProduct_name());
            sizeField.setText(selectedProduct instanceof Clothing ? String.valueOf(((Clothing) selectedProduct).getSize()) : "");
            colorField.setText(selectedProduct instanceof Clothing ? ((Clothing) selectedProduct).getColour() : "");
            itemsAvailableField.setText(String.valueOf(selectedProduct.getNo_of_items()));
        }
    }

    private JPanel createProductDetailsPanel() {
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(6, 2, 10, 5)); // 6 rows, 2 columns, with gaps
        detailsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Product Details"));

        //Creates text fields for each category
        detailsPanel.add(new JLabel("Product Id:"));
        productIdField = new JTextField(15);
        productIdField.setEditable(false);
        detailsPanel.add(productIdField);

        detailsPanel.add(new JLabel("Category:"));
        categoryNameField = new JTextField(15);
        categoryNameField.setEditable(false);
        detailsPanel.add(categoryNameField);

        detailsPanel.add(new JLabel("Name:"));
        nameField = new JTextField(15);
        nameField.setEditable(false);
        detailsPanel.add(nameField);

        detailsPanel.add(new JLabel("Size:"));
        sizeField = new JTextField(15);
        sizeField.setEditable(false);
        detailsPanel.add(sizeField);

        detailsPanel.add(new JLabel("Colour:"));
        colorField = new JTextField(15);
        colorField.setEditable(false);
        detailsPanel.add(colorField);

        detailsPanel.add(new JLabel("Items Available:"));
        itemsAvailableField = new JTextField(15);
        itemsAvailableField.setEditable(false);
        detailsPanel.add(itemsAvailableField);

        //Set font to Arial for all JLabels and JTextFields
        Font font = new Font("Arial", Font.PLAIN, 12);
        for (java.awt.Component component : detailsPanel.getComponents()) {
            if (component instanceof JLabel || component instanceof JTextField) {
                component.setFont(font);
            }
        }

        return detailsPanel;
    }


    private JPanel createShoppingCartButtonPanel() {
        JPanel shoppingCartButtonPanel = new JPanel();
        shoppingCartButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        shoppingCartButton = new JButton("Shopping Cart");
        shoppingCartButton.addActionListener(e -> {
            ShoppingCartGUI shoppingCartGUI = new ShoppingCartGUI(shoppingCart);
            shoppingCartGUI.updateCartDetails();
            shoppingCartGUI.updateCheckoutDetails();
            shoppingCartGUI.setVisible(true);
        });
        shoppingCartButtonPanel.add(shoppingCartButton);

        return shoppingCartButtonPanel;
    }

    private JPanel createAddToCartPanel() {
        JPanel addToCartPanel = new JPanel();
        addToCartPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); //Change to FlowLayout to center
        JButton addToCartButton = new JButton("Add to Shopping Cart");
        addToCartButton.addActionListener(e -> addToCart());
        addToCartPanel.add(addToCartButton);
        return addToCartPanel;
    }


    private void addToCart() {
        int selectedRow = productsTable.getSelectedRow();
        int modelRow = productsTable.convertRowIndexToModel(selectedRow);
        Product selectedProduct = WestminsterShoppingManager.product_list.get(modelRow);
        if (selectedRow >= 0 && (selectedProduct.getNo_of_items() > 0)) {
            shoppingCart.addProduct(selectedProduct);
            selectedProduct.setNo_of_items(-1);

            //updates items table after adding a product
            updateProductTable(null);

            JOptionPane.showMessageDialog(this,  "A " + selectedProduct.getProduct_name()+" has been added to cart: ");
        }else if((selectedProduct.getNo_of_items() == 0)){
            WestminsterShoppingManager.product_list.remove(selectedProduct);
            updateProductTable(null);
            JOptionPane.showMessageDialog(this, "All Stocks for " + selectedProduct.getProduct_name() + " are over, Please stay tuned for updates!!");
        }
    }


}


