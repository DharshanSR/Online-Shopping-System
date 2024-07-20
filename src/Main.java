import javax.swing.*; // Import Swing components for building graphical user interfaces (GUIs)
import java.util.Scanner; // Import Scanner for reading input from standard input (keyboard)

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        User user = new User();

        // Loading data from files
        manager.loadFromFile();     //loading the manager data from the file
        user.loadFromFile();    // loading the user data from file

        while (true) {
            try {
                //main menu
                System.out.println("\nWelcome To Shopping Center");
                System.out.println("-----------------------------------");
                System.out.println("Select Your Role");
                System.out.println("1. User");
                System.out.println("2. Manager");
                System.out.println("3. Exit");
                System.out.println("-----------------------------------");
                System.out.print("Enter your Option : ");

                int role = input.nextInt(); // read user role choice

                if (role == 1) {    //user menu
                    System.out.println("\n1. New User");
                    System.out.println("2. Existing User");
                    System.out.print("Enter your Option : ");
                    int userAction = input.nextInt();

                    if (userAction == 1) {
                        // new user registration
                        System.out.print("\nUsername: ");
                        String username = input.next();
                        System.out.print("Password: ");
                        String password = input.next();

                        if (User.registerUser(username, password)) {
                            System.out.println("Registration successful.");
                        } else {
                            System.out.println("Username already taken. Please choose a different username.");
                        }
                    } else if (userAction == 2) {
                        // existing user login
                        System.out.print("Username: ");
                        String username = input.next();
                        System.out.print("Password: ");
                        String password = input.next();

                        // Authentication process

                        User authenticatedUser = User.authenticateUser(username, password);
                        if (authenticatedUser != null) {
                            System.out.println("Login successful.");

                            // Open GUI
                            GUI frame = new GUI(manager);
                            frame.setTitle("Westminster Shopping Centre");
                            frame.setSize(600, 800);
                            frame.setLoggedInUser(authenticatedUser);
                            frame.setVisible(true);
                            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        } else {
                            System.out.println("Invalid username or password. Please try again.");
                        }
                    } else {
                        System.out.println("Invalid Input.");
                    }

                } else if (role == 2) {
                    manager.console();      //westminster manager menu
                } else if (role == 3) {
                    System.out.println("Exiting the program...");
                    break; // Exit the loop and terminate the program
                } else {
                    System.out.println("\nInvalid Input.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("\nInvalid Input.");
                input.next();   //clear invalid input
            } catch (Exception e) {
                System.out.println("\nInvalid Input.");
            }
        }

        // Save to the file
        user.saveToFile();
    }
}
