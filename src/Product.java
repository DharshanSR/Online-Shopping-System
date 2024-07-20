/**
 * Abstract class representing a general product.
 * This class defines common attributes and behaviors
 * that all specific product types may inherit and override.
 */
public abstract class Product {
    // Instance variables
    private String productId;
    private String productName;
    private int availableItems;
    private double price;

    // Constructor
    public Product(String productId, String productName, int availableItems, double price) {
        this.productId = productId;
        this.productName = productName;
        this.availableItems = availableItems;
        this.price = price;
    }

    // Getter methods
    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getavailableItems() {
        return availableItems;
    }

    public double getPrice() {
        return price;
    }

    // toString method for string representation of the object
    @Override
    public String toString() {
        return "{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", availableItems=" + availableItems +
                ", price=" + price +
                '}';
    }
}
