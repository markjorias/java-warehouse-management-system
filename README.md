<div align="center">

# Warehouse Management System

**A console-based Java system for inventory, orders, shipments and reports: team project for CS 214A**

![Java](https://img.shields.io/badge/Java-JDK_10%2B-ED8B00?logo=openjdk&logoColor=white)
![Programs](https://img.shields.io/badge/programs-4-blue)
![Period](https://img.shields.io/badge/coursework-Oct%202024-lightgrey)
![Status](https://img.shields.io/badge/status-archived-lightgrey)

</div>

---

## Overview

A warehouse management system written in Java for the Object-Oriented Programming course (CS 214A) by a team that called itself *WMSquad*. A warehouse operator uses a text menu to keep track of stock, take customer orders, record shipments and print reports. Nothing is saved to disk: all data lives in memory while the program runs.

The repository holds four programs:

- **The main program**: a single-file, menu-driven system with items, orders, shipments and three reports.
- **Two simpler programs**: array-based item menus (add, search, update, delete, show) that are dated earlier than the main program.
- **A one-class-per-file version** in its own Java package, which is less complete than the main program.

The project documentation (description, features, scope and limitations, class diagram) is included as a PDF.

**Skills demonstrated**

- Menu-driven console programs with `Scanner` and `switch`
- Classes and objects, one-class-per-file design and Java packages
- Collections (`ArrayList`) next to hand-managed parallel arrays
- Searching, updating and deleting records, including shifting array elements
- Formatted table output with `printf`
- Lambdas and a stream (`removeIf`, `filter`) for working with lists

## Table of Contents

- [Getting Started](#getting-started)
- [Repository Structure](#repository-structure)
- [Programs](#programs)
- [Methodology](#methodology)
- [Sample Results](#sample-results)
- [Known Limitations](#known-limitations)

## Getting Started

**Requirements**

- A JDK. The main program needs **JDK 10 or newer** (it calls `Optional.orElseThrow()` with no argument). The other programs compile with JDK 8.
- A terminal. No display or graphical session is needed.
- No input files: every program reads from the keyboard.

Run everything from the repository root. Compiled classes go to `out/`, which is git-ignored.

**Main program**

```bash
javac -d out main-program/WareHouseSquad.java
java -cp out WareHouseSquad
```

**Simpler array-based programs**

```bash
javac -d out prototypes/Inventory.java prototypes/WarehouseManagementSystem.java
java -cp out Inventory
java -cp out WarehouseManagementSystem
```

**One-class-per-file version**

```bash
javac -d out modular-version/warehouse/*.java
java -cp out warehouse.Main
```

> `Inventory` creates a new `Scanner` for every action, so run it typed at a terminal. Piping input into it makes it fail (see [Known Limitations](#known-limitations)).

## Repository Structure

```text
java-warehouse-management-system/
├── main-program/
│   └── WareHouseSquad.java
├── prototypes/
│   ├── Inventory.java
│   └── WarehouseManagementSystem.java
├── modular-version/
│   └── warehouse/
│       ├── Item.java
│       ├── Order.java
│       ├── Shipment.java
│       ├── Warehouse.java
│       └── Main.java
├── docs/
│   └── project-documentation.pdf
├── sample-data/
│   └── product-list.txt
└── README.md
```

Java source files keep their CamelCase names because Java requires a file to be named after its public class.

## Programs

Dates are the last-modified dates of the files as they were found (`YYYY-MM-DD`). A dash means the original date is not available.

### Main Program

| Program | Description | Concept | Date |
|---|---|---|---|
| [WareHouseSquad](main-program/WareHouseSquad.java) | The complete system in one file: add, update and remove items; create and cancel orders (checking stock); add, cancel and deliver shipments; inventory, order and shipment reports with a sales total | `ArrayList`, menus, lambdas, formatted output | 2024-10-09 ¹ |

### Simpler Array-Based Programs

| Program | Description | Concept | Date |
|---|---|---|---|
| [Inventory](prototypes/Inventory.java) | Item menu over parallel arrays (code, name, price): add, search, update, delete, table | Arrays, one helper class per action | 2024-10-03 |
| [WarehouseManagementSystem](prototypes/WarehouseManagementSystem.java) | The same kind of menu with quantity and a percentage discount per item | Arrays, shifting elements on delete | 2024-10-06 |

### One-Class-Per-File Version

Package `warehouse`. See the [limitations](#known-limitations): orders do not change stock and shipments cannot be created for real order IDs.

| Program | Description | Concept | Date |
|---|---|---|---|
| [Item](modular-version/warehouse/Item.java) | Item with ID, name, quantity and location | Encapsulation | 2024-10-08 ² |
| [Order](modular-version/warehouse/Order.java) | Order for a customer: a list of items and a status (`Pending`, `Processed`, `Cancelled`) | Composition | 2024-10-08 ² |
| [Shipment](modular-version/warehouse/Shipment.java) | Shipment for an order, with a status and a delivery date | Composition | 2024-10-08 ² |
| [Warehouse](modular-version/warehouse/Warehouse.java) | Holds inventory, orders and shipments; prints an inventory report | Collections | — ³ |
| [Main](modular-version/warehouse/Main.java) | Console menu loop that drives the classes above | Menu loop | 2024-10-08 |

### Documentation and Data

| File | Description | Date |
|---|---|---|
| [project-documentation.pdf](docs/project-documentation.pdf) | Three-page project document: description, features, scope and limitations, basic classes and a class diagram | 2024-10-09 |
| [product-list.txt](sample-data/product-list.txt) | A single line, `1-Sharpie-50-99`. No program reads it | 2024-10-08 |

¹ Taken from the copy that was kept with the documentation, which is newer than the other copy of this file (it adds size validation and returns stock when an order is cancelled). The older copy was removed. Cosmetic label fixes were made without changing the date.<br>
² `Item`, `Order` and `Shipment` carry an identical timestamp (to the millisecond), and `Warehouse` was within a millisecond of it, so these dates likely show when the files were created or copied together, not when each was last edited.<br>
³ This file was edited during cleanup to add two missing getters, so it no longer carries its original date.

## Methodology

What each program does, as written in the code.

**Main program (`WareHouseSquad`)**

- State is three static lists (`items`, `orders`, `shipments`) and one shared `Scanner`. An item has an ID, name, quantity, price and size (`S`, `M` or `L`); an order has one item line (item ID and quantity); a shipment links an order ID to a tracking ID and a status.
- **New order.** The first item whose ID matches exactly (case-sensitive) is used. If `item.quantity >= quantity`:
  `totalAmount = item.price × quantity`, and `item.quantity -= quantity`. Otherwise the order is refused with an *Insufficient quantity* message. An unknown ID prints *Item not found.*
- **Cancel order.** The first order with that ID is removed and its quantity is added back to the matching item.
- **Shipments.** A shipment can only be created for an existing order ID and starts as `SHIPPED`; *Mark as Delivered* sets `DELIVERED`. An order's shipment status is the status of the first shipment with its order ID, or `NOT SHIPPED`.
- **Sales total.** `TOTAL SALES = Σ totalAmount` over the orders that currently exist, printed with `%.2f`. A cancelled order is removed, so it no longer counts.
- **Size check.** Adding an item re-asks until the size is exactly `S`, `M` or `L`. Updating an item does not check it.

**Array-based programs**

- `Inventory`: three parallel arrays of 100 slots (code, name, price) and a counter. Lookups use `equalsIgnoreCase`. Delete moves every later entry one slot left, then clears slot 99.
- `WarehouseManagementSystem`: five parallel arrays of 100 slots (ID, name, quantity, price, discount). The discount is typed as a percentage, stored as `typed / 100` and shown as `stored × 100`. It is stored and displayed only and is never used in a calculation. Delete shifts later entries left and lowers the counter only when the ID was found.

**One-class-per-file version**

- `Warehouse` keeps three lists. `processOrder` adds the order to the list and sets its status to `Processed`; a new `Shipment` starts as `In Transit`; the report prints the inventory only. Items are looked up by checking whether the typed text appears anywhere in the item's info string.

**How the sample results were produced.** Each program was compiled and then driven by a script that types one input line at a time into a pseudo-terminal, so the log shows the input echoed as it would be in a real session. Everything ran on two JDKs, OpenJDK 27 and Eclipse Temurin 17.0.20.1, and the output was identical on both. The sources were also compiled with `javac --release` 8 to 27 to find the minimum version (the main program fails on 8 and 9). Compiled output and scratch files were kept outside the repository.

**What this does and does not show.** It shows that the main flows (add, search, update, delete, order, ship, report) work on these inputs and that the limitations listed below really occur. It is a handful of scripted sessions, not a test suite, so it says nothing about inputs that were not tried.

## Sample Results

Real output from scripted sessions (see [Methodology](#methodology)). Text after a prompt is the input that was typed. These are console programs, so no display was involved. Menus that reprint after every action are left out to keep the logs short; everything else is verbatim. Run on OpenJDK 27 and Temurin 17.0.20.1 with identical output.

<details>
<summary><b>WareHouseSquad</b>: add an item, place orders, ship, report, cancel</summary>

```text
Choose an option: 1

Choose an option: 1

Item ID: A100
Item Name: Pen
Quantity: 10
Price: 12.5
Size [S,M,L]: X
Invalid size. Please enter S, M or L: M
Item added successfully.

Choose an option: 0

Choose an option: 2

Choose an option: 1

Items:
--------------------------------------------------------------------
| ITEM ID    | ITEM NAME            | QUANTITY   | PRICE      | Size                 |
--------------------------------------------------------------------
| A100       | Pen                  | 10         | 12.50      | M                    |
--------------------------------------------------------------------

Order ID: O1
Customer Name: Alice
Delivery Address: 12 Main St
Item ID: A100
Quantity: 3
Order placed successfully.

Choose an option: 1

Items:
--------------------------------------------------------------------
| ITEM ID    | ITEM NAME            | QUANTITY   | PRICE      | Size                 |
--------------------------------------------------------------------
| A100       | Pen                  | 7          | 12.50      | M                    |
--------------------------------------------------------------------

Order ID: O2
Customer Name: Bob
Delivery Address: 34 Oak Ave
Item ID: A100
Quantity: 9
Insufficient quantity available for item: A100

Choose an option: 0

Choose an option: 3

Choose an option: 1

Orders:
--------------------------------------------------------------------------------
| ORDER ID   | CUSTOMER NAME        | DELIVERY ADDRESS     | ITEM ID         | SHIPMENT STATUS |
--------------------------------------------------------------------------------
| O1         | Alice                | 12 Main St           | A100            | NOT SHIPPED |
--------------------------------------------------------------------------------

Enter Order ID: O1
Enter Tracking ID: TRK1
New shipment added successfully.

Choose an option: 3

Shipments:
------------------------------------------------------------
| ORDER ID   | TRACKING ID     | STATUS               |
------------------------------------------------------------
| O1         | TRK1            | SHIPPED              |
------------------------------------------------------------

Enter Tracking ID to mark as delivered: TRK1
Shipment marked as delivered successfully.
Invalid choice. Please try again.

Choose an option: 0

Choose an option: 4

Choose an option: 2

Order Report:
--------------------------------------------------------------------
| ORDER ID   | CUSTOMER NAME        | ITEM ID         | QUANTITY   | TOTAL PRICE     | SHIPMENT STATUS |
--------------------------------------------------------------------
| O1         | Alice                | A100            | 3          | 37.50           | DELIVERED       |
--------------------------------------------------------------------
TOTAL SALES: 37.50
--------------------------------------------------------------------

Choose an option: 4

Choose an option: 3

Shipment Report:
---------------------------------------------------
| Order ID   | Tracking ID     | Status     |
---------------------------------------------------
| O1         | TRK1            | DELIVERED  |
---------------------------------------------------

Choose an option: 2

Choose an option: 2

Orders:
--------------------------------------------------------------------------------
| ORDER ID   | CUSTOMER NAME        | DELIVERY ADDRESS     | ITEM ID         | SHIPMENT STATUS |
--------------------------------------------------------------------------------
| O1         | Alice                | 12 Main St           | A100            | DELIVERED  |
--------------------------------------------------------------------------------

Enter ORDER_ID to cancel: O1
Order canceled successfully.

Choose an option: 0

Choose an option: 4

Choose an option: 1

Inventory Report:
--------------------------------------------------------------------
| ITEM ID    | ITEM NAME            | QUANTITY   | PRICE      | Size                 |
--------------------------------------------------------------------
| A100       | Pen                  | 10         | 12.50      | M                    |
--------------------------------------------------------------------

Choose an option: 0
Exiting the program.
```

</details>

<details>
<summary><b>Inventory</b>: add, search, update, show, delete</summary>

```text
Enter your choice: 1
Enter the item code: A100
Enter the item name: Pen
Enter the item price: 12.5
Item added successfully.
Enter your choice: 1
Enter the item code: B200
Enter the item name: Notebook
Enter the item price: 45
Item added successfully.
Enter your choice: 2
Enter the item code to search: a100
Item Code: A100
Item Name: Pen
Item Price: 12.5
Enter your choice: 3
Enter the item code to update: B200
Enter the new item code: B200
Enter the new item name: Notebook
Enter the new item price: 50
Item updated successfully.
Enter your choice: 5
Item Table:
---------------------------------------------------
| Item Code  | Item Name            | Item Price |
---------------------------------------------------
| A100       | Pen                  | 12.50      |
| B200       | Notebook             | 50.00      |
---------------------------------------------------
Enter your choice: 4
Enter the item code to delete: A100
Item deleted successfully.
Item count: 2
Enter your choice: 5
Item Table:
---------------------------------------------------
| Item Code  | Item Name            | Item Price |
---------------------------------------------------
| B200       | Notebook             | 50.00      |
---------------------------------------------------
Enter your choice: 2
Enter the item code to search: A100
Item not found.
Enter your choice: 0
Exiting the Inventory Management System. Goodbye!
```

</details>

<details>
<summary><b>WarehouseManagementSystem</b>: add with discount, search, update, delete</summary>

```text
Enter your choice: 1
ITEM_ID: A100
ITEM_NAME: Pen
ITEM_QUANTITY: 10
ITEM_PRICE: 12.5
ITEM_DISCOUNT: 7
Item added successfully.

Enter your choice: 1
ITEM_ID: B200
ITEM_NAME: Notebook
ITEM_QUANTITY: 5
ITEM_PRICE: 45
ITEM_DISCOUNT: 15
Item added successfully.

Enter your choice: 2
Enter ITEM_ID to search: a100
Item found:
ITEM_ID: A100
ITEM_NAME: Pen
ITEM_QUANTITY: 10
ITEM_PRICE: 12.5
ITEM_DISCOUNT: 7.000000000000001%

Enter your choice: 5
Inventory:
----------------------------------------------------------------------------------
| ITEM_ID    | ITEM_NAME            | ITEM_QUANTITY | ITEM_PRICE | ITEM_DISCOUNT |
----------------------------------------------------------------------------------
| A100       | Pen                  | 10            | 12.50      | 7.00          |
| B200       | Notebook             | 5             | 45.00      | 15.00         |
----------------------------------------------------------------------------------

Enter your choice: 3
Inventory:
----------------------------------------------------------------------------------
| ITEM_ID    | ITEM_NAME            | ITEM_QUANTITY | ITEM_PRICE | ITEM_DISCOUNT |
----------------------------------------------------------------------------------
| A100       | Pen                  | 10            | 12.50      | 7.00          |
| B200       | Notebook             | 5             | 45.00      | 15.00         |
----------------------------------------------------------------------------------
Enter ITEM_ID to update: A100
New ITEM_NAME: Pen XL
New ITEM_QUANTITY: 8
New ITEM_PRICE: 13
New ITEM_DISCOUNT: 10
Item updated successfully.

Enter your choice: 5
Inventory:
----------------------------------------------------------------------------------
| ITEM_ID    | ITEM_NAME            | ITEM_QUANTITY | ITEM_PRICE | ITEM_DISCOUNT |
----------------------------------------------------------------------------------
| A100       | Pen XL               | 8             | 13.00      | 10.00         |
| B200       | Notebook             | 5             | 45.00      | 15.00         |
----------------------------------------------------------------------------------

Enter your choice: 4
Inventory:
----------------------------------------------------------------------------------
| ITEM_ID    | ITEM_NAME            | ITEM_QUANTITY | ITEM_PRICE | ITEM_DISCOUNT |
----------------------------------------------------------------------------------
| A100       | Pen XL               | 8             | 13.00      | 10.00         |
| B200       | Notebook             | 5             | 45.00      | 15.00         |
----------------------------------------------------------------------------------
Enter ITEM_ID to delete: B200
Item deleted successfully.

Enter your choice: 5
Inventory:
----------------------------------------------------------------------------------
| ITEM_ID    | ITEM_NAME            | ITEM_QUANTITY | ITEM_PRICE | ITEM_DISCOUNT |
----------------------------------------------------------------------------------
| A100       | Pen XL               | 8             | 13.00      | 10.00         |
----------------------------------------------------------------------------------

Enter your choice: 2
Enter ITEM_ID to search: B200
Item not found.

Enter your choice: 0
Exiting the Warehouse Management System. Goodbye!
```

</details>

<details>
<summary><b>warehouse.Main</b>: add items, place an order, report</summary>

```text
Choose an option: 1
Enter Item ID: A100
Enter Item Name: Pen
Enter Quantity: 10
Enter Location: Shelf A1
Item added successfully.

Choose an option: 1
Enter Item ID: B200
Enter Item Name: Notebook
Enter Quantity: 5
Enter Location: Shelf B2
Item added successfully.

Choose an option: 3
Enter Order ID: O1
Enter Customer Name: Alice
Enter Item ID to add to order (or 'done' to finish): A100
Item added to order.
Enter Item ID to add to order (or 'done' to finish): done
Order Processed

Choose an option: 4
Enter Shipment ID: S1
Enter Order ID: O1
Order not found.

Choose an option: 5
Inventory Report:
Item ID: A100, Name: Pen, Quantity: 10, Location: Shelf A1
Item ID: B200, Name: Notebook, Quantity: 5, Location: Shelf B2

Choose an option: 6
Exiting... Thank you!
```

</details>

## Known Limitations

Bugs left as they are, because fixing them would change how the programs behave. Each one was reproduced unless it says otherwise.

**Main program**

- Marking a shipment as delivered also prints `Invalid choice. Please try again.` (the `case 3` branch of the shipment menu has no `break`).
- Removing an item that does not exist prints `Item removed successfully.` Cancelling a shipment prints a success message whether or not it existed (read from the code, not run).
- Quantities are not validated. An order of `-4` raised stock from 10 to 14.
- Cancelling an order whose item was removed from the inventory ends the program with `NoSuchElementException`.
- Typing a non-number at a numeric prompt ends the program with `InputMismatchException`.
- Item IDs and order IDs may be duplicated. Cancelling an order does not touch its shipment. Each order holds a single item.
- The table borders are shorter than the table rows in the listings and reports (cosmetic).
- The documentation describes an item with a *location* and a separate `Reports` class. The code uses a *size*, and the reports are static methods.

**Array-based programs**

- `Inventory`: deleting a code that does not exist still lowers the item count, so the last item disappears from the table. The count printed after a delete is the count from before the delete. Every action creates its own `Scanner` on `System.in`, so input piped into it fails with `NoSuchElementException`.
- `WarehouseManagementSystem`: the search output shows floating-point noise, for example `7.000000000000001%` for a 7% discount.
- Both use arrays of 100 slots with no capacity check (read from the code, not run). Both end with `InputMismatchException` when a non-number is typed at the menu.

**One-class-per-file version**

- As first written this version did not compile, because `Main` called `getInventory()` and `getOrders()`, which `Warehouse` did not define. Those two getters were added; nothing else about its behaviour was changed.
- Placing an order does not reduce stock.
- Creating a shipment compares the typed order ID with the order's *status*, so a real order ID gives `Order not found.` and typing `Processed` succeeds.
- Items are matched by substring of their info text, so a short ID can match more than one item.
- `Item.addItem()` and `Item.removeItem()` are empty stubs, and nothing calls `Shipment.setDeliveryDate()` (read from the code).

**General**

- Nothing is saved: all data is lost when the program exits.
- `sample-data/product-list.txt` is not read by any program.
- Only the JDK is required, and nothing in the code is tied to an operating system. Testing was done on Linux only.
