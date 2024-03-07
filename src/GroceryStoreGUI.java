import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class GroceryStoreGUI extends JFrame {
    private JList<String> itemList;
    private DefaultListModel<String> listModel;
    private JTextField quantityField;
    private JTextField totalPriceField;
    private JLabel statusLabel;
    private HashMap<String, Integer> itemQuantities;
    private HashMap<String, Double> itemPrices;
    private HashMap<String, Integer> shoppingCart;

    public GroceryStoreGUI() {
        setTitle("Grocery Store");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        listModel = new DefaultListModel<>();
        itemList = new JList<>(listModel);
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        itemQuantities = new HashMap<>();
        itemQuantities.put("Apple", 10);
        itemQuantities.put("Banana", 15);
        itemQuantities.put("Orange", 5);
        itemQuantities.put("Milk", 20);
        itemQuantities.put("Bread", 30);
        itemQuantities.put("Eggs", 25);
        itemQuantities.put("Cheese", 12);
        itemQuantities.put("Chicken", 8);
        itemQuantities.put("Beef", 10);
        itemQuantities.put("Pasta", 18);
        itemQuantities.put("Rice", 22);
        itemQuantities.put("Tomato", 35);
        itemQuantities.put("Potato", 40);
        itemQuantities.put("Carrot", 30);
        itemQuantities.put("Lettuce", 20);
        itemQuantities.put("Onion", 25);
        itemQuantities.put("Garlic", 15);
        itemQuantities.put("Watermelon", 5);
        itemQuantities.put("Grapes", 8);

        itemPrices = new HashMap<>();
        itemPrices.put("Apple", 1.0);
        itemPrices.put("Banana", 0.75);
        itemPrices.put("Orange", 0.5);
        itemPrices.put("Milk", 2.5);
        itemPrices.put("Bread", 1.8);
        itemPrices.put("Eggs", 1.5);
        itemPrices.put("Cheese", 3.0);
        itemPrices.put("Chicken", 5.0);
        itemPrices.put("Beef", 7.0);
        itemPrices.put("Pasta", 2.0);
        itemPrices.put("Rice", 1.5);
        itemPrices.put("Tomato", 0.8);
        itemPrices.put("Potato", 0.5);
        itemPrices.put("Carrot", 0.4);
        itemPrices.put("Lettuce", 1.0);
        itemPrices.put("Onion", 0.6);
        itemPrices.put("Garlic", 0.3);
        itemPrices.put("Watermelon", 4.0);
        itemPrices.put("Grapes", 2.0);

        for (String item : itemQuantities.keySet()) {
            listModel.addElement(item + " (Quantity: " + itemQuantities.get(item) + ", Price: $" + itemPrices.get(item) + ")");
        }

        itemList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                String selectedItem = itemList.getSelectedValue();
                if (selectedItem != null) {
                    String[] parts = selectedItem.split(" ");
                    String itemName = parts[0];
                    int quantity = itemQuantities.getOrDefault(itemName, 0);
                    statusLabel.setText("Quantity: " + quantity);
                }
            }
        });

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Enter Quantity: "));
        quantityField = new JTextField();
        inputPanel.add(quantityField);
        JButton addToCartButton = new JButton("Add to Cart");
        inputPanel.add(addToCartButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(itemList), BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        JPanel cartPanel = new JPanel(new GridLayout(2, 2));
        cartPanel.add(new JLabel("Total Cost: "));
        totalPriceField = new JTextField();
        totalPriceField.setEditable(false);
        cartPanel.add(totalPriceField);

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double totalPrice = calculateTotalPrice();
                JOptionPane.showMessageDialog(null, "Total Cost: $" + totalPrice, "Checkout", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        cartPanel.add(checkoutButton);

        JPanel statusPanel = new JPanel(new BorderLayout());
        statusLabel = new JLabel("Select an item to see quantity", SwingConstants.CENTER);
        statusPanel.add(statusLabel, BorderLayout.NORTH);
        statusPanel.add(cartPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
        setVisible(true);

        shoppingCart = new HashMap<>();

        addToCartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedItem = itemList.getSelectedValue();
                String[] parts = selectedItem.split(" ");
                String itemName = parts[0];
                int quantity = Integer.parseInt(quantityField.getText());
                if (itemQuantities.containsKey(itemName) && itemQuantities.get(itemName) >= quantity) {
                    if (shoppingCart.containsKey(itemName)) {
                        shoppingCart.put(itemName, shoppingCart.get(itemName) + quantity);
                    } else {
                        shoppingCart.put(itemName, quantity);
                    }
                    itemQuantities.put(itemName, itemQuantities.get(itemName) - quantity);
                    listModel.setElementAt(itemName + " (Quantity: " + itemQuantities.get(itemName) + ", Price: $" + itemPrices.get(itemName) + ")", itemList.getSelectedIndex());
                    statusLabel.setText("Quantity: " + itemQuantities.get(itemName));
                    quantityField.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Not enough quantity available.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private double calculateTotalPrice() {
        double total = 0.0;
        for (String item : shoppingCart.keySet()) {
            total += shoppingCart.get(item) * itemPrices.get(item);
        }
        return total;
    }
}