import java.util.ArrayList;

/**
 * Interface representing a shopping manager.
 * This interface defines methods that a shopping manager
 */
public interface ShoppingManager {

    // Method to add a product to the manager
    void addProduct(Product product);

    // Method to remove a product from the manager by its ID
    void removeProduct(String productId);

    // Method to display all products managed by the manager
    void displayProducts();

    // Method to save the list of products to a file
    boolean saveToFile();

    // Method to load a list of products from a file
    ArrayList<Product> loadFromFile();

    // Method to retrieve the list of products managed by the manager
    ArrayList<Product> getProductList();
}
