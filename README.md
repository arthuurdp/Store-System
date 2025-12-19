# Store System - Portfolio Project

A Java console application designed to manage products and purchases for a retail store. This project serves as a comprehensive demonstration of **Object-Oriented Programming (OOP)**, **Layered Architecture**, and manual **Persistence** using **JDBC** and **MySQL**.

## Educational Purpose & Concepts

This project was developed with a strong focus on core software engineering principles and the internal workings of data persistence. Instead of relying on high-level frameworks (like Spring Data or Hibernate).

### 1. Manual ORM & Data Grouping
One of the most challenging aspects of this project was mapping relational data (SQL) to objects (Java) when dealing with **1:N (One-to-Many)** relationships. 
*   **The Challenge**: SQL `JOIN`s return "flattened" rows, duplicating purchase data for every item.
*   **The Solution**: I implemented a **Map-based grouping logic** in the DAO layer. Using a `HashMap<Integer, Purchase>`, the system ensures that multiple result set rows are aggregated into a single `Purchase` object with a list of products, preventing object duplication and ensuring data integrity.

### 2. Transaction Management
To guarantee consistency (especially when a purchase affects both the `purchases` and `products` tables), I implemented manual **Transaction Control**:
*   Disabling `autoCommit`.
*   Using `commit()` for successful operations.
*   Implementing `rollback()` in `catch` blocks to prevent partial data updates (Atomicity).

### 3. Layered Architecture & Design Patterns
*   **DAO (Data Access Object) Pattern**: Completely decouples the business logic from the SQL details.
*   **Service Layer**: Acts as a bridge, orchestrating complex business rules like stock updates and tax calculations.
*   **Dependency Injection**: The `StoreSystem` (UI) receives its services via constructor, making it easier to test and maintain.
*   **Factory Pattern**: Used `DaoFactory` to hide implementation details of the DAOs.

### 4. Error Handling
Custom `DbException` (unchecked) implementation to wrap `SQLException`, keeping the method signatures clean while still providing meaningful error propagation.

---

## Features

### Product Management
- **Inventory Control:** Add, update, and remove products with real-time stock tracking.
- **Validation:** Prevents negative stock and handles returning deleted products to inventory.

### Purchase Management
- **Multi-item Purchases:** Create complex transactions with multiple products.
- **Stock Integration:** Automatically deducts from stock during purchase and **restores stock** if a purchase is deleted.
- **Dynamic Updates:** Change items in an existing purchase with automatic stock reconciliation.

### Financials
- **Dynamic Calculations:** Subtotals and total calculations including ICMS tax (17%).

---

## Tech Stack

- **Java (JDK 8+)**: Core language and OOP logic.
- **JDBC (Java Database Connectivity)**: Direct database interaction.
- **MySQL**: Relational database for persistent storage.
- **MySQL Connector/J**: Driver for JDBC connection.

## Setup

### 1. Database Setup
1. Create a MySQL database named `storejdbc`.
2. Run the provided `script.sql` file:
   ```sql
   SOURCE script.sql;
   ```

### 2. Configure Connection
1. Update `db.properties` in the project root:
   ```properties
   user=your_username
   password=your_password
   dburl=jdbc:mysql://localhost:3306/storejdbc
   useSSL=false
   ```

### 3. Run the Application
Compile and run `src/application/Main.java`.

---

## Author
**Arthur Dall Agnol Pinheiro**
* Estudante de Análise e Desenvolvimento de Sistemas,  UPF - Passo Fundo.
* [LinkedIn](https://www.linkedin.com/in/arthur-dall-agnol-pinheiro-b04285357/)