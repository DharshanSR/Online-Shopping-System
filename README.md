# Shopping Management System

This Java project implements a shopping management system with a focus on inheritance and encapsulation principles. The system includes a console-based menu for the manager and a graphical user interface (GUI) for the client.

## Table of Contents

1. [Overview](#overview)
2. [Classes and Implementation](#classes-and-implementation)
   - [Product (Abstract)](#product-abstract)
   - [Electronics](#electronics)
   - [Clothing](#clothing)
   - [User](#user)
   - [ShoppingCart](#shoppingcart)
   - [WestminsterShoppingManager](#westminstershoppingmanager)
3. [Console Menu Implementation (Phase 2)](#console-menu-implementation-phase-2)
4. [Graphical User Interface (GUI) Implementation (Phase 3)](#graphical-user-interface-gui-implementation-phase-3)
5. [File Handling](#file-handling)
6. [How to Run](#how-to-run)

## Overview

The shopping management system allows a manager to add, delete, and view products (Electronics and Clothing) through a console menu. Clients interact with the system through a GUI, where they can view products, add them to a shopping cart, and see the final price with applicable discounts.

## Classes and Implementation

### Product (Abstract)

The `Product` class is an abstract superclass that includes:
- `productID` (String): A mix of characters and numbers
- `productName` (String)
- `availableItems` (int)
- `price` (double)

Includes appropriate getter and setter methods and at least one constructor.

### Electronics

The `Electronics` subclass extends `Product` and includes:
- `brand` (String)
- `warrantyPeriod` (int)

Includes appropriate getter and setter methods and at least one constructor.

### Clothing

The `Clothing` subclass extends `Product` and includes:
- `size` (String)
- `color` (String)

Includes appropriate getter and setter methods and at least one constructor.

### User

The `User` class represents a user account and includes:
- `username` (String)
- `password` (String)

Includes appropriate getter and setter methods and at least one constructor.

### ShoppingCart

The `ShoppingCart` class represents a user's cart and includes:
- A list of `Product` objects

Methods:
- `addProduct(Product product)`
- `removeProduct(Product product)`
- `calculateTotalCost()`

### WestminsterShoppingManager

The `WestminsterShoppingManager` class implements the `ShoppingManager` interface and maintains the list of products. It provides methods for:
- Adding a new product
- Deleting a product by product ID
- Printing the list of products
- Saving and loading products from a file

## Console Menu Implementation (Phase 2)

The console menu allows the manager to:
- Add a new product (Electronics or Clothing)
- Delete a product by ID
- Print the list of products (ordered alphabetically by product ID)
- Save the list of products to a file

## Graphical User Interface (GUI) Implementation (Phase 3)

The GUI allows the client to:
- Select the type of product to view (All, Electronics, Clothing) from a drop-down menu
- View a table of products with all relevant information
  - Items with less than 3 available appear in red
  - Product details appear in a panel below the table when selected
- Add products to a shopping cart
- View the shopping cart with a list of products and the final price

## File Handling

The system saves the list of products to a file and loads it upon starting the application, allowing it to persist data across sessions.

## How to Run

1. Compile all the Java files.
2. Run the `Main` class to start the console menu.
3. Select the option to open the GUI from the console menu.
