import javax.swing.*; // Import Swing components for building GUI applications
import java.awt.*; // Import AWT components for basic GUI layout and functionality
import java.util.ArrayList; // Import ArrayList to manage collections of objects efficiently

/* This `GUI` class extends `JFrame` to create a graphical user interface for a shopping application. Here's a list of what each method does:
1. **Constructor (`GUI(WestminsterShoppingManager manager)`)**
   - Initializes components like tables, text areas, buttons, and panels.
   - Sets up layout and adds components to the frame.
   - Adds action listeners to buttons for shopping cart, adding/removing products, filtering products, and updating text area.
   
2. **`filterProducts(String category)`**
   - Filters products based on the selected category and updates the product table.
   
3. **`updateTextArea(int selectedRow)`**
   - Updates the text area with details of the selected product when the table row is changed.
   
4. **`getDetailsForSelectedRow(int selectedRow)`**
   - Retrieves details of the selected product to display in the text area.
   
5. **`setLoggedInUser(User user)`**
   - Sets the logged-in user for the GUI. */
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
        String[] selections = { "All", "Electronics", "Clothing" };
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

    /**
     * This method filters the products based on the selected category.
     * The method first checks if the selected category is "All". If it is, it sets
     * the table product list to the original product list.
     * If the selected category is not "All", it creates a new ArrayList to store
     * the filtered products.
     * Then it iterates over each product in the original product list.
     * If the selected category is "Electronics" and the product is an instance of
     * Electronics, or if the selected category is "Clothing" and the product is an
     * instance of Clothing, it adds the product to the filtered list.
     * After iterating over all the products, it sets the table product list to the
     * filtered list and fires a table data changed event.
     *
     * @param category The category selected by the user from the combo box.
     */
    private void filterProducts(String category) {
        if (category.equals("All")) { // Check if the selected category is "All"
            model.setTableProductList(myList); // If it is, set the table product list to the original product list
        } else { // If the selected category is not "All"
            ArrayList<Product> filteredList = new ArrayList<>(); // Create a new ArrayList to store the filtered
                                                                 // products
            for (Product product : myList) { // Iterate over each product in the original product list
                if ((category.equals("Electronics") && product instanceof Electronics) || // Check if the selected
                                                                                          // category is "Electronics"
                                                                                          // and the product is an
                                                                                          // instance of Electronics
                        (category.equals("Clothing") && product instanceof Clothing)) { // Check if the selected
                                                                                        // category is "Clothing" and
                                                                                        // the product is an instance of
                                                                                        // Clothing
                    filteredList.add(product); // If the conditions are met, add the product to the filtered list
                }
            }
            model.setTableProductList(filteredList); // Set the table product list to the filtered list
        }
        model.fireTableDataChanged(); // Fire a table data changed event to update the table
    }

    /**
     * Method to update the text area with details of the selected product in the
     * table.
     * 
     * It first checks if the selected row is valid (i.e. it is not -1 and it is
     * less than the total number of rows in the table).
     * If the selected row is valid, it calls the getDetailsForSelectedRow method to
     * retrieve the details of the selected product.
     * The getDetailsForSelectedRow method returns a string containing the details
     * of the selected product.
     * It then sets the text of the text area to the details of the selected
     * product.
     * This will update the text area so that it displays the details of the
     * selected product.
     * 
     * @param selectedRow The row selected by the user in the table.
     */
    private void updateTextArea(int selectedRow) {
        if (selectedRow >= 0 && selectedRow < model.getRowCount()) {
            String details = getDetailsForSelectedRow(selectedRow);
            textArea.setText(details);
        }
    }

    /**
     * Method to get details of selected product.
     *
     * @param selectedRow The index of the selected row in the table.
     * @return A string containing the details of the selected product.
     */
    private String getDetailsForSelectedRow(int selectedRow) {

        // Create a StringBuilder to store the details of the product
        StringBuilder details = new StringBuilder();

        // Get the product at the selected row from the table model
        Product product = model.getTableProductList().get(selectedRow);

        // Append the product ID, category, and name to the details string
        details.append("Product Id: ").append(model.getValueAt(selectedRow, 0)).append("\n");
        details.append("Category: ").append(model.getValueAt(selectedRow, 2)).append("\n");
        details.append("Name: ").append(model.getValueAt(selectedRow, 1)).append("\n");

        // If the product is an Electronics, append its warranty period and brand to the
        // details string
        if (product instanceof Electronics electronicsProduct) {
            details.append("Warranty: ")
                    .append(electronicsProduct.getWarrantyPeriod())
                    .append(" weeks\n");
            details.append("Brand: ")
                    .append(electronicsProduct.getBrand())
                    .append("\n");
        }

        // If the product is Clothing, append its size and color to the details string
        else if (product instanceof Clothing clothingProduct) {
            details.append("Size: ")
                    .append(clothingProduct.getSize())
                    .append("\n");
            details.append("Color: ")
                    .append(clothingProduct.getColor())
                    .append("\n");
        }

        // Append the number of items available for the product
        details.append("Number of Items: ")
                .append(product.getavailableItems())
                .append("\n");

        // Return the details string
        return details.toString();
    }

    /**
     * This method is used to set the logged-in user for the GUI.
     * It takes a User object as a parameter and assigns it to the
     * loggedInUser field of the GUI class.
     *
     * @param user The User object representing the logged-in user.
     */
    public void setLoggedInUser(User user) {

        // Assign the passed User object to the loggedInUser field
        // of the GUI class. This makes the User object accessible
        // throughout the GUI class and allows the GUI to interact
        // with the user's account.
        this.loggedInUser = user;
    }
}
