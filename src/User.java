import java.io.File; // Import File class for file manipulation operations
import java.io.FileWriter; // Import FileWriter class for writing to files
import java.io.IOException; // Import IOException class for handling input/output exceptions
import java.util.ArrayList; // Import ArrayList class for managing collections of objects efficiently
import java.util.Scanner; // Import Scanner class for reading input from standard input (keyboard) and files

/* - **Class Definition**: 
  - Represents a user with username, password, purchase number, and shopping cart information.
  - Includes methods for managing user data, such as registration, authentication, saving to file, and loading from a file.

- **Method Explanation**:
  1. `getShoppingCart()`: Returns the user's shopping cart.
  2. `getPurchaseNumber()`: Returns the user's purchase number.
  3. `setPurchaseNumber(int purchaseNumber)`: Sets the user's purchase number.
  4. `getUserName()`: Returns the user's username.
  5. `getPassword()`: Returns the user's password.
  6. `isUsernameTaken(String username)`: Checks if a username is already taken.
  7. `saveToFile()`: Saves user details to a file.
  8. `loadFromFile()`: Loads user details from a file.
  9. `registerUser(String username, String password)`: Registers a new user.
  10. `authenticateUser(String username, String password)`: Authenticates user credentials. */
public class User {
    // Instance variables
    private String userName;
    private String password;
    private int purchaseNumber;
    private ShoppingCart shoppingCart;

    // Static variables
    private static ArrayList<User> userList = new ArrayList<>();
    private ArrayList<ShoppingCart> shoppingCartList = new ArrayList<>(); // Not currently used

    // Default constructor
    public User() {
        ShoppingCart shoppingCart1 = new ShoppingCart();
        shoppingCartList.add(shoppingCart1); // Initialize shopping cart for each user
    }

    // Constructor with parameters
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.shoppingCart = new ShoppingCart(); // Initialize shopping cart for the user
    }

    // Getter method for shopping cart
    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    // Getter method for purchase number
    public int getPurchaseNumber() {
        return purchaseNumber;
    }

    // Setter method for purchase number
    public void setPurchaseNumber(int purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    // Getter method for username
    public String getUserName() {
        return userName;
    }

    // Getter method for password
    public String getPassword() {
        return password;
    }

    // Check if username is taken
    private static boolean isUsernameTaken(String username) {
        for (User user : userList) {
            if (user.getUserName().equals(username)) {
                return true; // Username already exists
            }
        }
        return false; // Username is available
    }

    // Save user details to a file
    public boolean saveToFile() {
        try {
            FileWriter fileWriter = new FileWriter("UserDetails.txt");
            for (User user : userList) {
                fileWriter.write("Username = " + user.userName + "\n");
                fileWriter.write("Password = " + user.password + "\n");
                fileWriter.write("PurchaseNumber = " + user.purchaseNumber + "\n\n");
            }
            fileWriter.close();
            return true; // Successfully saved to file
        } catch (IOException e) {
            return false; // Failed to save to file
        }
    }

    // Load user details from a file
    public static ArrayList<User> loadFromFile() {
        ArrayList<User> loadedUsers = new ArrayList<>();
        try {
            File file = new File("UserDetails.txt");
            Scanner fileRead = new Scanner(file);
            while (fileRead.hasNextLine()) {
                String username = fileRead.nextLine();
                String password = fileRead.nextLine();
                int purchaseNumber = Integer.parseInt(fileRead.nextLine());
                User user = new User(username, password);
                user.setPurchaseNumber(purchaseNumber);
                loadedUsers.add(user);
            }
            fileRead.close();
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
        userList.addAll(loadedUsers); // Add loaded users to static userList
        return loadedUsers; // Return loaded users
    }

    // Register a new user
    public static boolean registerUser(String username, String password) {
        if (!isUsernameTaken(username)) {
            User newUser = new User(username, password);
            userList.add(newUser); // Add new user to userList
            return true; // Registration successful
        }
        return false; // Username already taken
    }

    // Authenticate user credentials
    public static User authenticateUser(String username, String password) {
        for (User user : userList) {
            if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
                return user; // Authentication successful
            }
        }
        return null; // Authentication failed
    }
}
