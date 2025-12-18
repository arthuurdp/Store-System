# Store System

A simple Java console application designed to manage products and purchases for a retail store. This project was developed as part of a Java course to practice Object-Oriented Programming (OOP) concepts, list manipulation, and basic system logic.

##  Features

The system offers a variety of functionalities to manage inventory and sales:

### Product Management
- **Add Product:** Register new products with code, name, quantity, and price.
- **Exclude Product:** Remove products from the system's inventory.
- **Change Product:** Update existing product information (Name, Quantity, or Price).
- **List Products:** View all registered products in the stock.

### Purchase Management
- **Add Purchase:** Create a new purchase, adding multiple products from the stock.
- **Exclude Purchase:** Cancel and remove a purchase from the system.
- **Change Purchase:** Modify an existing purchase by adding or removing products.
- **List Purchases:** Display all recorded purchases, including details like total price and total with ICMS tax.

### Financial Calculations
- **Total Calculation:** Automatically calculates the total value of each purchase.
- **ICMS Tax:** Applies a standard ICMS tax (17%) to the total purchase value.

##  Project Structure

The project is organized into the following packages:

- `application`: Contains the `Main` class, the entry point of the application and the menu logic.
- `entities`: Contains the domain models:
    - `Product`: Represents a store item.
    - `Purchase`: Represents a customer's purchase, containing a list of products.
- `services`: Contains the `StoreSystem` class, which handles the business logic and manages the data in memory.

##  Usage Example

Upon running the program, you will see a menu like this:

```text
-=-=-=-=-=-=-=-=- MENU -=-=-=-=-=-=-=-=-=-=
1 - Add Product
2 - Exclude Product
3 - Add Purchase
4 - Exclude Purchase
5 - Change Product
6 - Change Purchase
7 - List Products
8 - List Purchases
9 - Exit Program
-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
Choose an option:
```

Simply type the number corresponding to the action you want to perform and follow the on-screen instructions.

---
## Author
Arthur Dall Agnol Pinheiro.
