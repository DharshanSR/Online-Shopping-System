import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/* Sure, here's a list format explanation of the methods in the `ProductTable` class:

- `getRowCount()`: Returns the number of rows in the table (number of products).
- `getColumnCount()`: Returns the number of columns in the table.
- `getValueAt(int rowIndex, int columnIndex)`: Retrieves the value at a specific cell in the table based on the row and column index.
- `getTableProductList()`: Returns the list of products currently displayed in the table.
- `setTableProductList(ArrayList<Product> tableProductList)`: Sets the list of products to display in the table.
- `getColumnName(int columnIndex)`: Returns the name of the column at the specified index.

The `ProductTable` class is a custom table model that extends `AbstractTableModel` from Swing. It is used to display a list of products in a table. The `tableProductList` field holds the list of products to be displayed. The `getRowCount()` method returns the number of rows in the table, which is the size of the `tableProductList`. The `getColumnCount()` method returns the number of columns in the table, which is the length of the `columns` array. The `getValueAt(int rowIndex, int columnIndex)` method retrieves the value at a specific cell in the table. It uses the `rowIndex` and `columnIndex` to determine the product and the column to return the value from. The `getTableProductList()` and `setTableProductList(ArrayList<Product> tableProductList)` methods are used to get and set the list of products to display in the table. The `getColumnName(int columnIndex)` method returns the name of the column at the specified index.
 */
public class ProductTable extends AbstractTableModel {
    // Column names for the table
    final String[] columns = { "Product ID", "Name", "Category", "Price(£)", "Info" };

    // List of products to display in the table
    private ArrayList<Product> tableProductList;

    // Constructor to initialize with a list of products
    public ProductTable(ArrayList<Product> tableProductList) {
        this.tableProductList = tableProductList;
    }

    // Get the number of rows in the table (number of products)
    @Override
    public int getRowCount() {
        return tableProductList.size();
    }

    // Get the number of columns in the table
    @Override
    public int getColumnCount() {
        return columns.length;
    }

    // Get the value at a specific row and column in the table
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Product product = tableProductList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return product.getProductId();
            case 1:
                return product.getProductName();
            case 2:
                // Determine the category based on the type of product
                if (product instanceof Electronics) {
                    return "Electronics";
                } else if (product instanceof Clothing) {
                    return "Clothing";
                }
            case 3:
                return product.getPrice();
            case 4:
                // Additional information specific to each product type
                if (product instanceof Electronics) {
                    Electronics electronicsProduct = (Electronics) product;
                    String brandWarranty = electronicsProduct.getBrand() + ", " + electronicsProduct.getWarrantyPeriod()
                            + " weeks warranty";
                    return brandWarranty;
                } else if (product instanceof Clothing) {
                    Clothing clothingProduct = (Clothing) product;
                    String sizeColor = clothingProduct.getSize() + ", " + clothingProduct.getColor();
                    return sizeColor;
                }
            default:
                return null;
        }
    }

    // Get the list of products currently displayed in the table
    public ArrayList<Product> getTableProductList() {
        return tableProductList;
    }

    // Set the list of products to display in the table
    public void setTableProductList(ArrayList<Product> tableProductList) {
        this.tableProductList = tableProductList;
    }

    // Get the column name for a specific column index
    @Override
    public String getColumnName(int columnIndex) {
        return columns[columnIndex];
    }
}
