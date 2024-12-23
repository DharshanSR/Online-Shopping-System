/*- **Class Clothing extends Product**: Clothing class inherits from the Product class.
- **Constructor Clothing()**: Initializes a Clothing object with specific attributes like size and color.
- **getSize()**: Getter method that retrieves the size of the clothing.
- **getColor()**: Getter method that retrieves the color of the clothing.
- **toString()**: Overrides the toString() method to provide a string representation of the Clothing object including size and color. */

public class Clothing extends Product {
    private String size;
    private String color;

    // constructor for initializing a clothing object
    public Clothing(String productId, String productName, int availableItems, double price, String size, String color) {
        // call the constructor from the super class
        super(productId, productName, availableItems, price);

        // initializing thr specific attributes for clothing
        this.size = size;
        this.color = color;
    }

    // Getter method for retrieving the size of clothing
    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    // Override toString method to provide a string representation of the Clothing
    // object
    @Override
    public String toString() {
        return super.toString() + ", Size: " + size + ", Color: " + color;
    }
}
