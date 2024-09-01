# Online Store CLI

## 📜 Description

This is a Command-Line Interface (CLI) application simulating an online store. It allows users to browse products, add them to the cart, configure products, place orders, and view order history. It also features an administrator mode for managing products.

## ✨ Features

### User Mode

1. **Browse Products**: Display a list of available products.
2. **Add Product to Cart**: Add selected products to the shopping cart.
3. **Configure Product**: Customize products such as computers and smartphones.
4. **View Cart**: Show the products currently in the shopping cart.
5. **Place Order**: Finalize the order with customer details.
6. **View Order History**: View a history of past orders.
7. **Check Discounts**: Display available discounts based on purchase amount.
8. **Exit**: Quit the application.

### Administrator Mode

1. **Add Product**: Add a new product to the inventory.
2. **Remove Product**: Remove an existing product from the inventory.
3. **Update Product**: Update details of an existing product.

## 🚀 Getting Started

### Prerequisites

- **Java Development Kit (JDK)** 11 or later
- A terminal or command prompt

### Installation

To run this project, clone the repository and compile the Java files. Then, run the `Main` class.

## 📂 Method Descriptions

### ProductManager Class

- **`addProduct(Product product)`**: Adds a new product to the inventory and displays a confirmation message.
- **`removeProduct(int productId)`**: Removes a product by its ID. If the product does not exist, a message is displayed.
- **`updateProduct(int productId, Product updatedProduct)`**: Updates the details of an existing product identified by its ID.
- **`viewProducts()`**: Displays the list of all available products in the store.
- **`findProductById(int productId)`**: Searches for a product by its ID and returns it if found.
- **`saveProductsToCSV(String fileName)`**: Saves the list of products to a CSV file for persistence.
- **`loadProductsFromCSV(String filePath)`**: Loads products from a CSV file to populate the inventory.

### Cart Class

- **`addProductToCart(Product product, int quantity)`**: Adds a specified quantity of a product to the user's cart.
- **`removeProductFromCart(int productId)`**: Removes a product from the cart by its ID.
- **`viewProductsInCart()`**: Displays all products currently in the cart along with their details.
- **`calculateTotalAmount()`**: Calculates the total price of products in the cart and applies discounts based on total purchase amount.
- **`placeOrder(Customer customer)`**: Places an order for the products in the cart, generates an invoice, and clears the cart.
- **`viewOrders()`**: Displays a list of all orders placed by the user.

### OrdersProcessor Class

- **`generateInvoice(Order order)`**: Generates a text file invoice for a given order, detailing customer information and purchased products.
- **`run()`**: Implements the `Runnable` interface, which triggers the invoice generation asynchronously.

### Order Class

- **`getTotalAmount()`**: Calculates and returns the total amount for an order, including any discounts.
- **`displayOrderDetails()`**: Prints detailed information about the order, such as customer details, products, and total amount.

### Product Class

- **`displayDetails()`**: Displays detailed information about the product, such as its name, price, and quantity.
- **`setAvailableQuantity(int quantity)`**: Sets the available quantity for the product in the inventory.
- **`getPrice()`**: Returns the price of the product.

### Exception Handling

The application handles various exceptions such as:

- **`NumberFormatException`**: When parsing integers or BigDecimal values.
- **`InputMismatchException`**: For invalid user inputs.
- **`NoSuchElementException`**: When an input is expected but not provided.
- **`ArrayIndexOutOfBoundsException`**: When accessing invalid array indices.

## 📘 Usage

1. **User Mode**:  
   - Allows customers to browse products, add them to the cart, configure them, place orders, and check for available discounts.

2. **Admin Mode**:  
   - Enables administrators to manage the inventory by adding, updating, and removing products.


---

