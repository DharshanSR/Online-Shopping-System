import java.io.File;       // Import File class for file and directory manipulation
import java.io.FileWriter; // Import FileWriter class for writing to files
import java.io.IOException; // Import IOException class for handling input/output errors
import java.util.ArrayList; // Import ArrayList class for managing collections of objects efficiently
import java.util.Scanner;   // Import Scanner class for reading input from standard input and files

public class WestminsterShoppingManager implements ShoppingManager {
    private static ArrayList<Product> ProductList;

    public WestminsterShoppingManager() {
        this.ProductList = new ArrayList<>();
    }

    public void console(){
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        manager.loadFromFile();
        while(true) {
            try {
                // Console menu
                System.out.println("\n*******************************************");
                System.out.println("*      Westminster Shopping Menu          *");
                System.out.println("*-----------------------------------------*");
                System.out.println("* 1. Add a new product to the system      *");
                System.out.println("* 2. Remove a product from the system     *");
                System.out.println("* 3. Display all products                 *");
                System.out.println("* 4. Save to file                         *");
                System.out.println("* 5. Exit                                 *");
                System.out.println("*******************************************");
                System.out.print("Enter your choice: ");

                Scanner input = new Scanner(System.in);
                int option = input.nextInt();

                switch (option) {
                    case 1:
                        System.out.println("\n--------------------");
                        System.out.println("Select product type");
                        System.out.println("1. Electronics");
                        System.out.println("2. Clothing");
                        System.out.println("\n--------------------");
                        System.out.print("Enter your choice: ");

                        int type = input.nextInt();
                        System.out.print("Product ID (4 characters): ");
                        String productId = input.next();

                        boolean exitloop = false;

                        // Checks for duplicate product id
                        for (Product product : ProductList) {
                            if (product.getProductId().equals(productId)) {
                                System.out.println("ID Already Exists.\n");
                                exitloop = true;
                                break ;
                            }
                        }
                        if (exitloop){
                            break;
                        }
                        if (productId.length() == 4) {
                            System.out.println("Product Name: ");
                            String productName = input.next();


                            System.out.print("Number Of Items: ");
                            int availableItems = 0;
                            boolean validInput = false;

                            while (!validInput) {
                                if (input.hasNextInt()) {
                                    availableItems = input.nextInt();
                                    validInput = true; // Break the loop as we have valid input
                                } else {
                                    System.out.println("Invalid input for number of items. Please enter a valid integer.");
                                    input.next(); // Consume the invalid input
                                    System.out.print("Number Of Items: "); // Prompt again
                                }
                            }


                            System.out.print("Product Price: ");
                            double price = 0;
                            if (input.hasNextDouble()) {
                                price = input.nextDouble();
                            } else {
                                System.out.println("Invalid input for product price.");
                                break;
                            }

                            // Validate price
                            if (price <= 0) {
                                System.out.println("Price should be greater than zero.");
                                break;
                            }

                            Product product;
                            if (type == 1) {
                                System.out.print("Product Brand: ");
                                String brand = input.next();

                                System.out.print("Product Warranty Period (in months, 60 months maximum): ");
                                int warrantyPeriod = 0;
                                if (input.hasNextInt()) {
                                    warrantyPeriod = input.nextInt();
                                } else {
                                    System.out.println("Invalid input for warranty period.");
                                    break;
                                }

                                // Validate warranty period
                                if (warrantyPeriod <= 0 || warrantyPeriod > 60) {
                                    System.out.println("Warranty period should be between 1 and 60 months.");
                                    break;
                                }

                                product = new Electronics(productId, productName, availableItems, price, brand, warrantyPeriod);
                            } else if (type == 2) {
                                System.out.print("Size (S, M, XL, XXL): ");
                                String size = input.next().toUpperCase();

                                // Validate size
                                if (!size.equals("S") && !size.equals("M") && !size.equals("XL") && !size.equals("XXL")) {
                                    System.out.println("Invalid size. Size must be S, M, XL, or XXL.");
                                    break;
                                }

                                System.out.print("Color: ");
                                String color = input.next();

                                product = new Clothing(productId, productName, availableItems, price, size, color);
                            } else {
                                break;
                            }
                            manager.addProduct(product);
                        } else {
                            System.out.println("\nInvalid number of characters for product ID (must be 4 characters).\n");
                        }

                        break;
                    case 2:
                        System.out.println("Enter Product ID to Delete: ");
                        String productIdRemove = input.next();
                        manager.removeProduct(productIdRemove);
                        break;
                    case 3:
                        manager.displayProducts();
                        break;
                    case 4:
                        manager.saveToFile();
                        System.out.println("Saving Completed");
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("\nInvalid Option. \nPlease Enter:");
                }
            } catch (Exception e) {
                System.out.println("\nInvalid Input. Try Again\n");
            }
        }
    }

    @Override
    public void addProduct(Product product) {
        // Checks the number pf products in the arraylist
        if (ProductList.size()<=50) {
            ProductList.add(product);
            ProductList.sort((p1, p2) -> p1.getProductId().compareTo(p2.getProductId()));
            System.out.println("Product added successfully.");
        }
    }

    @Override
    public void removeProduct(String productId) {
        Product productToRemove = null;
        for (Product product : ProductList) {
            if (product.getProductId().equals(productId)) {
                productToRemove = product;
                System.out.println("Product removed successfully.");
                break;
            }
        }
        if (productToRemove != null) {
            ProductList.remove(productToRemove);

            // Determine the type of the removed product
            if (productToRemove instanceof Electronics) {
                System.out.println("Removed Electronics product with ID " + productId);
            } else if (productToRemove instanceof Clothing) {
                System.out.println("Removed Clothing product with ID " + productId);
            } else {
                System.out.println("Removed product with ID " + productId);
            }
        }else {
            System.out.println("Product not found.");
        }
        ProductList.sort((p1,p2) -> p1.getProductId().compareTo(p2.getProductId()));
    }

    @Override
    public void displayProducts() {
        ProductList.sort((p1,p2) -> p1.getProductId().compareTo(p2.getProductId()));
        if(ProductList.isEmpty()){
            System.out.println("\nProducts not Available\n");
        }else {
            for (Product product : ProductList) {
                if(product instanceof Electronics){
                    System.out.print("Electronics ");
                } else if (product instanceof Clothing) {
                    System.out.print("Clothing ");
                }
                System.out.println(product);
            }
        }
    }

    @Override
    public boolean saveToFile() {
        try {
            FileWriter fileWriter = new FileWriter("Products.txt");
            for (Product product : ProductList) {
                if (product instanceof Electronics) {
                    fileWriter.write("Electronic Product\n");
                } else if (product instanceof Clothing) {
                    fileWriter.write("Clothing Product\n");
                }
                fileWriter.write("Product ID: " + product.getProductId() + "\n");
                fileWriter.write("Product Name: " + product.getProductName() + "\n");
                fileWriter.write("Available Items: " + product.getavailableItems() + "\n");
                fileWriter.write("Price: " + product.getPrice() + "\n");
                if (product instanceof Electronics) {
                    fileWriter.write("Brand: " + ((Electronics) product).getBrand() + "\n");
                    fileWriter.write("Warranty Period: " + ((Electronics) product).getWarrantyPeriod() + " months\n");
                } else if (product instanceof Clothing) {
                    fileWriter.write("Size: " + ((Clothing) product).getSize() + "\n");
                    fileWriter.write("Color: " + ((Clothing) product).getColor() + "\n");
                }
                fileWriter.write("\n"); // Separate each product entry with a blank line
            }
            fileWriter.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public ArrayList<Product> loadFromFile() {
        try {
            File file = new File("Products.txt");
            Scanner fileRead = new Scanner(file);
            Product product;
            String productId = null;
            String productName = null;
            int availableItems = 0;
            double price = 0;
            String brand = null;
            int warrantyPeriod = 0;
            String size = null;
            String color = null;
            String line;
            int count = -1;
            int cond = 0;

            while (fileRead.hasNextLine()) {
                line = fileRead.nextLine();

                if (line.equals("Electronic Product")) {
                    count = 1;
                    cond = 0;
                    continue;
                } else if (line.equals("Clothing Product")) {
                    count = 0;
                    cond = 0;
                    continue;
                }

                if (count == 1) { // Electronic product
                    switch (cond) {
                        case 0:
                            productId = line.replace("Product ID: ", "");
                            cond++;
                            break;
                        case 1:
                            productName = line.replace("Product Name: ", "");
                            cond++;
                            break;
                        case 2:
                            availableItems = Integer.parseInt(line.replace("Available Items: ", ""));
                            cond++;
                            break;
                        case 3:
                            price = Double.parseDouble(line.replace("Price: ", ""));
                            cond++;
                            break;
                        case 4:
                            brand = line.replace("Brand: ", "");
                            cond++;
                            break;
                        case 5:
                            warrantyPeriod = Integer.parseInt(line.replace("Warranty Period: ", "").replace(" months", ""));
                            cond++;
                            break;
                    }
                    if (cond == 6) {
                        product = new Electronics(productId, productName, availableItems, price, brand, warrantyPeriod);
                        ProductList.add(product);
                        cond = 0; // Reset condition for the next product
                    }
                } else if (count == 0) { // Clothing product
                    switch (cond) {
                        case 0:
                            productId = line.replace("Product ID: ", "");
                            cond++;
                            break;
                        case 1:
                            productName = line.replace("Product Name: ", "");
                            cond++;
                            break;
                        case 2:
                            availableItems = Integer.parseInt(line.replace("Available Items: ", ""));
                            cond++;
                            break;
                        case 3:
                            price = Double.parseDouble(line.replace("Price: ", ""));
                            cond++;
                            break;
                        case 4:
                            size = line.replace("Size: ", "");
                            cond++;
                            break;
                        case 5:
                            color = line.replace("Color: ", "");
                            cond++;
                            break;
                    }
                    if (cond == 6) {
                        product = new Clothing(productId, productName, availableItems, price, size, color);
                        ProductList.add(product);
                        cond = 0; // Reset condition for the next product
                    }
                }
            }
            fileRead.close();
            ProductList.sort((p1, p2) -> p1.getProductId().compareTo(p2.getProductId()));
        } catch (IOException e) {
            System.out.println("File not Found. \n");
        }
        return ProductList;
    }
    @Override
    public ArrayList<Product> getProductList(){
        return ProductList;
    }
}