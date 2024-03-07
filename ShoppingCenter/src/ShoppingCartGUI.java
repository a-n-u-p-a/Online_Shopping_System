//importing necessary packages

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCartGUI extends JFrame {

    //initializing variables
    private DefaultTableModel cartTableModel;
    private JLabel lblTotal;
    private JLabel lblFirstPurchaseDiscount;
    private JLabel lblCategoryDiscount;
    private JLabel lblFinalTotal;

    public static double firstPurchaseDiscount = 0;

    public ShoppingCartGUI(ShoppingCart shoppingCart) {
        setTitle("Shopping Cart");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setPreferredSize(new Dimension(1107, 742)); // Set the preferred size of the frame
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Cart Table Panel
        JPanel cartPanel = new JPanel(new BorderLayout());
        String[] cartColumns = {"Product", "Quantity", "Price"};
        Object[][] cartData = {{}, {}};
        cartTableModel = new DefaultTableModel(cartData, cartColumns);
        JTable cartTable = new JTable(cartTableModel);
        cartTable.setPreferredScrollableViewportSize(new Dimension(1000, 150));
        JScrollPane cartScrollPane = new JScrollPane(cartTable);
        cartPanel.add(cartScrollPane, BorderLayout.CENTER);

        // Create a wrapper panel with FlowLayout for better centering
        JPanel wrapperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapperPanel.add(cartPanel);

        getContentPane().add(wrapperPanel);


        // Totals Panel
        JPanel totalsPanel = new JPanel(new GridLayout(4, 2, 10, 10)); // 4 rows, 2 columns with padding
        lblTotal = new JLabel("");
        lblFirstPurchaseDiscount = new JLabel("First Purchase Discount (10%): ");
        lblCategoryDiscount = new JLabel("Three Items in same Category Discount (20%): ");
        lblFinalTotal = new JLabel("Final Total: ");

        totalsPanel.add(new JLabel("Total"));
        totalsPanel.add(lblTotal);
        totalsPanel.add(new JLabel("First Purchase Discount (10%)"));
        totalsPanel.add(lblFirstPurchaseDiscount);
        totalsPanel.add(new JLabel("Three Items in same Category Discount (20%)"));
        totalsPanel.add(lblCategoryDiscount);
        totalsPanel.add(new JLabel("Final Total"));
        totalsPanel.add(lblFinalTotal);
        getContentPane().add(totalsPanel);

        addWindowListener(new WindowAdapter() {
                              @Override
                              public void windowClosing(WindowEvent e) {
                                  closeWindow();
                              }
                          });

        pack(); // Adjusts window to fit the preferred size and layouts of its subcomponents
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private void closeWindow() {
        // Hide the current ShoppingCartGUI
        setVisible(false);

    }
    public void updateCartDetails() {
        // Clear the existing table data
        cartTableModel.setRowCount(0);

        // Add the filtered products to the table
        for (Product product : ShoppingCart.products) {
            cartTableModel.addRow(new Object[]{
                    product.getProduct_name(),
                    1,
                    product.getPrice(),
            });
        }
    }


    public void updateCheckoutDetails() {
        // Clear the existing table data
        cartTableModel.setRowCount(0);

        double total = 0;

        // Map to store the quantity of each category
        Map<String, Integer> categoryQuantityMap = new HashMap<>();

        // Add the filtered products to the table and calculate the total
        for (Product product : ShoppingCart.products) {
            double price = product.getPrice();
            cartTableModel.addRow(new Object[]{
                    product.getProduct_name(),
                    1,
                    price
            });

            total += price;

            // Update category quantity map
            String category = product.getType();
            categoryQuantityMap.put(category, categoryQuantityMap.getOrDefault(category, 0) + 1);
        }

        // Calculate discounts
        double categoryDiscount = 0;

        if ((ShoppingCart.products.size()) == 1) {
            firstPurchaseDiscount = 0.1 * total; // 10% first purchase discount
        }


        // Check if there are three items in the same category
        for (int quantity : categoryQuantityMap.values()) {
            if (quantity >= 3) {
                categoryDiscount = 0.2 * total; // 20% category discount
                break;  // Only apply the discount once, even if there are multiple categories with three items
            }
        }

        // Update labels in the totals panel
        lblTotal.setText(String.format("%.2f", total));
        lblFirstPurchaseDiscount.setText(String.format("%.2f (10%%)", firstPurchaseDiscount));
        lblCategoryDiscount.setText(String.format("%.2f (20%%)", categoryDiscount));

        // Calculate final total after applying discounts
        double finalTotal = total - firstPurchaseDiscount - categoryDiscount;
        lblFinalTotal.setText(String.format("%.2f", finalTotal));
    }



}
