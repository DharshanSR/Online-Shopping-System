import javax.swing.*; // Import Swing components for building GUI applications
import java.awt.*; // Import AWT components for basic GUI layout and functionality
import java.util.ArrayList; // Import ArrayList to manage collections of objects efficiently


public class GUI extends JFrame {
    // Components
    private JTable table1;
    private ArrayList<Product> myList;
    private JTextArea textArea;
    private ProductTable model;
    private User loggedInUser;

    // Constructor
    public GUI(WestminsterShoppingManager manager) {
        // Initialize components
        myList = manager.getProductList();
        model = new ProductTable(myList);
        table1 = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table1);

        textArea = new JTextArea();
        textArea.setRows(6);
        textArea.setEditable(false);

        JLabel label1 = new JLabel("Select Product Category");
        JLabel label2 = new JLabel("Details");
        String[] selections = {"All", "Electronics", "Clothing"};
        JComboBox<String> comboBox = new JComboBox<>(selections);
        JButton button1 = new JButton("Shopping Cart");
        JButton button2 = new JButton("Add to Shopping Cart");
        JButton button3 = new JButton("Remove from Shopping Cart");

        // Layout setup
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel bottomPanel = new JPanel(new GridLayout(4, 1, 1, 5));
        JPanel addToCartPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 1, 1));

        // Add components to panels
        centerPanel.add(label1);
        centerPanel.add(comboBox);
        topPanel.add(centerPanel, BorderLayout.CENTER);
        rightPanel.add(button1);
        topPanel.add(rightPanel, BorderLayout.EAST);
        addToCartPanel.add(button2);
        addToCartPanel.add(button3);
        bottomPanel.add(label2);
        bottomPanel.add(textArea);
        bottomPanel.add(addToCartPanel);

        // Add panels to frame
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Action listeners for buttons

        // Open Shopping Cart GUI
        button1.addActionListener(e -> {
            ShoppingCartGUI frame = new ShoppingCartGUI(loggedInUser);
            frame.setTitle("Shopping Cart");
            frame.setSize(600, 600);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        });

        // Add product to shopping cart
        button2.addActionListener(e -> {
            int selectedRow = table1.getSelectedRow();
            if (selectedRow >= 0 && selectedRow < model.getRowCount()) {
                Product selectedProduct = model.getTableProductList().get(selectedRow);
                loggedInUser.getShoppingCart().add(selectedProduct);
                JOptionPane.showMessageDialog(GUI.this, "Product added to shopping cart.");
            } else {
                JOptionPane.showMessageDialog(GUI.this, "Please select a product to add to the shopping cart.");
            }
        });

        // Remove product from shopping cart
        button3.addActionListener(e -> {
            int selectedRow = table1.getSelectedRow();
            if (selectedRow >= 0 && selectedRow < model.getRowCount()) {
                Product selectedProduct = model.getTableProductList().get(selectedRow);
                if (loggedInUser.getShoppingCart().getCart().contains(selectedProduct)) {
                    loggedInUser.getShoppingCart().remove(selectedProduct);
                    JOptionPane.showMessageDialog(GUI.this, "Product removed from your shopping cart.");
                } else {
                    JOptionPane.showMessageDialog(GUI.this, "Product not found in the shopping cart.");
                }
            } else {
                JOptionPane.showMessageDialog(GUI.this, "Please select a product to remove from the shopping cart.");
            }
        });

        // Filter products based on selected category
        comboBox.addActionListener(e -> {
            String category = (String) comboBox.getSelectedItem();
            filterProducts(category);
        });

        // Listener for table selection change
        table1.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table1.getSelectedRow();
                updateTextArea(selectedRow);
            }
        });
    }

    // Method to filter products based on selected category
    private void filterProducts(String category) {
        if (category.equals("All")) {
            model.setTableProductList(myList);
        } else {
            ArrayList<Product> filteredList = new ArrayList<>();
            for (Product product : myList) {
                if ((category.equals("Electronics") && product instanceof Electronics) ||
                        (category.equals("Clothing") && product instanceof Clothing)) {
                    filteredList.add(product);
                }
            }
            model.setTableProductList(filteredList);
        }
        model.fireTableDataChanged();
    }

    // Method to update text area with details of selected product
    private void updateTextArea(int selectedRow) {
        if (selectedRow >= 0 && selectedRow < model.getRowCount()) {
            String details = getDetailsForSelectedRow(selectedRow);
            textArea.setText(details);
        }
    }

    // Method to get details of selected product
    private String getDetailsForSelectedRow(int selectedRow) {
        StringBuilder details = new StringBuilder();
        Product product = model.getTableProductList().get(selectedRow);

        details.append("Product Id: ").append(model.getValueAt(selectedRow, 0)).append("\n");
        details.append("Category: ").append(model.getValueAt(selectedRow, 2)).append("\n");
        details.append("Name: ").append(model.getValueAt(selectedRow, 1)).append("\n");

        // Append additional details based on product type
        if (product instanceof Electronics electronicsProduct) {
            details.append("Warranty: ").append(electronicsProduct.getWarrantyPeriod()).append(" weeks\n");
            details.append("Brand: ").append(electronicsProduct.getBrand()).append("\n");
        } else if (product instanceof Clothing clothingProduct) {
            details.append("Size: ").append(clothingProduct.getSize()).append("\n");
            details.append("Color: ").append(clothingProduct.getColor()).append("\n");
        }

        details.append("Number of Items: ").append(product.getavailableItems()).append("\n");
        return details.toString();
    }

    // Method to set the logged-in user
    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }
}
