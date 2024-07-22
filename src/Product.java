/**
 * Abstract class representing a general product.
 * This class defines common attributes and behaviors
 * that all specific product types may inherit and override.
 */

/*
 * - The class `Product` is an abstract class representing a general product
 * with attributes like `productId`, `productName`, `availableItems`, and
 * `price`.
 * - The constructor initializes these attributes when a `Product` object is
 * created.
 * - `getProductId()` returns the product ID.
 * - `getProductName()` returns the product name.
 * - `getavailableItems()` returns the available items.
 * - `getPrice()` returns the price.
 * - `toString()` method provides a string representation of the `Product`
 * object with all its attributes.
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
