import javax.swing.*;
import javax.swing.border.EmptyBorder; // Import EmptyBorder for creating empty borders around Swing components
import java.awt.*;

public class ShoppingCartGUI extends JFrame {
    private JPanel textFieldPanel;
    private JTable table2;
    private User loggedInUser;
    private JScrollPane scrollPane;
    private double finalTotal;

    // Constructor for ShoppingCartGUI
    public ShoppingCartGUI(User loggedInUser) {
        this.loggedInUser = loggedInUser;

        // Initialize cart table with data from loggedInUser's shopping cart
        CartTable cartTableModel = new CartTable(this.loggedInUser.getShoppingCart().getCart());
        table2 = new JTable(cartTableModel);
        scrollPane = new JScrollPane(table2);

        // Labels and text fields for displaying total and discounts
        JLabel label1 = new JLabel("Total");
        JTextField textField1 = new JTextField();
        JLabel label2 = new JLabel("First Purchase Discount (10%)");
        JTextField textField2 = new JTextField();
        JLabel label3 = new JLabel("Three Items in Same Category Discount (20%)");
        JTextField textField3 = new JTextField();
        JLabel label4 = new JLabel("Final Total");
        JTextField textField4 = new JTextField();

        setLayout(new BorderLayout());

        // Panel to organize text fields
        textFieldPanel = new JPanel(new GridLayout(4, 2, 10, 5)); // 4 rows, 2 columns, with spacing
        textFieldPanel.setBorder(new EmptyBorder(10, 50, 20, 50)); // Add margins
        textFieldPanel.add(label1);
        textFieldPanel.add(textField1);
        textFieldPanel.add(label2);
        textFieldPanel.add(textField2);
        textFieldPanel.add(label3);
        textFieldPanel.add(textField3);
        textFieldPanel.add(label4);
        textFieldPanel.add(textField4);

        // Calculate total and apply discounts
        double total = loggedInUser.getShoppingCart().calculate();
        double discount1 = 0.1 * total; // First purchase discount
        double discount2 = 0.2 * total; // Discount for three items in the same category
        finalTotal = total;

        // Display total in textField1
        textField1.setText(String.valueOf(total));

        // Apply first purchase discount if it's the user's first purchase
        if (loggedInUser.getPurchaseNumber() == 0) {
            textField2.setText(String.valueOf(discount1));
            finalTotal = total - discount1;
        } else {
            textField2.setText("0");
        }

        // Count the number of items in the cart and check for category discounts
        int numberOfItems = loggedInUser.getShoppingCart().getCart().size();
        if (numberOfItems >= 3) {
            int countElectronic = 0;
            int countClothing = 0;
            for (Product product : loggedInUser.getShoppingCart().getCart()) {
                if (product instanceof Electronics) {
                    countElectronic++;
                } else if (product instanceof Clothing) {
                    countClothing++;
                }
            }

            // Apply category discount if there are three or more items in any category
            if (countClothing >= 3 || countElectronic >= 3) {
                textField3.setText(String.valueOf(discount2));
                finalTotal = total - discount2;
            } else {
                textField3.setText("0");
            }
        } else {
            textField3.setText("0");
        }

        // Display final total after applying discounts
        textField4.setText(String.valueOf(finalTotal));

        // Add components to the panel and frame
        JPanel panel = new JPanel(new GridLayout(3, 1)); // 3 rows, 1 column
        panel.add(scrollPane); // Add scrollable cart table
        panel.add(textFieldPanel); // Add panel with total and discount fields
        add(panel, BorderLayout.CENTER); // Add panel to center of frame
    }
}
