import javax.swing.table.AbstractTableModel; // Import AbstractTableModel for creating custom table models in Swing
import java.util.ArrayList; // Import ArrayList to manage collections of objects efficiently


public class CartTable extends AbstractTableModel {
    // Column headers for the table
    final String[] cartColumns = {"Products", "Quantity", "Price ($)"};
    private ArrayList<Product> cartProductList;

    // Number of the specific product
    int quantity;

    // Constructor to initialize with the cart product list
    public CartTable(ArrayList<Product> cartProductList) {
        this.cartProductList = cartProductList;
    }

    @Override
    public int getRowCount() {
        // Returns the number of rows, which is the size of the cart product list
        return cartProductList.size();
    }

    @Override
    public int getColumnCount() {
        // Returns the number of columns, which is the length of the column headers array
        return cartColumns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // Flag to check if the product is a duplicate in the list
        boolean condition = true;
        // Get the product at the specified row index
        Product product = cartProductList.get(rowIndex);

        // Check for duplicate products in the cart
        for (int i = 0; i < rowIndex; i++) {
            Product otherProduct = cartProductList.get(i);
            if (product.getProductId().equals(otherProduct.getProductId())) {
                condition = false;
                break;
            }
        }

        if (condition) {
            // Return appropriate value based on the column index
            switch (columnIndex) {
                case 0:
                    // For the "Products" column, return a detailed string based on product type
                    if (product instanceof Electronics) {
                        Electronics electronicsProduct = (Electronics) product;
                        return product.getProductId() + "-\n " + product.getProductName() +
                                "-\n " + electronicsProduct.getBrand() + "-\n" +
                                electronicsProduct.getWarrantyPeriod();
                    } else if (product instanceof Clothing) {
                        Clothing clothingProduct = (Clothing) product;
                        return product.getProductId() + "-\n" + product.getProductName() + "-\n" +
                                clothingProduct.getSize() + "-\n" + clothingProduct.getColor();
                    }
                    break;
                case 1:
                    // For the "Quantity" column, count the number of the same product in the cart
                    quantity = 0;
                    for (Product product2 : cartProductList) {
                        if (product2.getProductId().equals(product.getProductId())) {
                            quantity++;
                        }
                    }
                    return quantity;
                case 2:
                    // For the "Price ($)" column, return the total price based on quantity
                    return product.getPrice() * quantity;
                default:
                    return null;
            }
        }
        return null;
    }

    // Overrides the column names with the given names in cartColumns
    @Override
    public String getColumnName(int columnIndex) {
        return cartColumns[columnIndex];
    }
}
