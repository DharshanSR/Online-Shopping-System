import java.util.ArrayList;

public class ShoppingCart {
    private ArrayList<Product> cart = new ArrayList<>(); // ArrayList to store products in the cart
    private double totalCost; // Total cost of all products in the cart

    public ShoppingCart() {
        // Constructor initializes an empty shopping cart
    }

    // Add a product to the shopping cart
    public void add(Product product) {
        cart.add(product);
    }

    // Remove a product from the shopping cart
    public void remove(Product product) {
        cart.remove(product);
    }

    // Calculate the total cost of all products in the shopping cart
    public double calculate() {
        totalCost = 0;
        for (Product product : cart) {
            totalCost += product.getPrice(); // Summing up prices of all products in the cart
        }
        return totalCost;
    }

    // Get the list of products currently in the shopping cart
    public ArrayList<Product> getCart() {
        return cart;
    }
}
