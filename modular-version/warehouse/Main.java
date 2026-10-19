package warehouse;
import java.util.*;

public class Main {
    private static Warehouse warehouse = new Warehouse();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        
        // Console-based menu loop
        while (running) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline left-over
            
            switch (choice) {
                case 1:
                    addItemToInventory();
                    break;
                case 2:
                    removeItemFromInventory();
                    break;
                case 3:
                    placeOrder();
                    break;
                case 4:
                    processShipment();
                    break;
                case 5:
                    generateReport();
                    break;
                case 6:
                    System.out.println("Exiting... Thank you!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    // Print the main menu
    private static void printMenu() {
        System.out.println("\nWarehouse Management System");
        System.out.println("1. Add Item to Inventory");
        System.out.println("2. Remove Item from Inventory");
        System.out.println("3. Place Order");
        System.out.println("4. Process Shipment");
        System.out.println("5. Generate Report");
        System.out.println("6. Exit");
        System.out.print("Choose an option: ");
    }

    // Option 1: Add Item to Inventory
    private static void addItemToInventory() {
        System.out.print("Enter Item ID: ");
        String itemID = scanner.nextLine();
        System.out.print("Enter Item Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();  // Consume the newline left-over
        System.out.print("Enter Location: ");
        String location = scanner.nextLine();

        Item newItem = new Item(itemID, name, quantity, location);
        warehouse.addInventory(newItem);
        System.out.println("Item added successfully.");
    }

    // Option 2: Remove Item from Inventory
    private static void removeItemFromInventory() {
        System.out.print("Enter Item ID to remove: ");
        String itemID = scanner.nextLine();

        for (Item item : warehouse.getInventory()) {
            if (item.getItemInfo().contains(itemID)) {
                warehouse.removeInventory(item);
                System.out.println("Item removed successfully.");
                return;
            }
        }
        System.out.println("Item not found.");
    }

    // Option 3: Place Order
    private static void placeOrder() {
        System.out.print("Enter Order ID: ");
        String orderID = scanner.nextLine();
        System.out.print("Enter Customer Name: ");
        String customerName = scanner.nextLine();

        Order newOrder = new Order(orderID, customerName);

        boolean addingItems = true;
        while (addingItems) {
            System.out.print("Enter Item ID to add to order (or 'done' to finish): ");
            String itemID = scanner.nextLine();
            if (itemID.equalsIgnoreCase("done")) {
                addingItems = false;
            } else {
                for (Item item : warehouse.getInventory()) {
                    if (item.getItemInfo().contains(itemID)) {
                        newOrder.addItemToOrder(item);
                        System.out.println("Item added to order.");
                    }
                }
            }
        }

        warehouse.processOrder(newOrder);
    }

    // Option 4: Process Shipment
    private static void processShipment() {
        System.out.print("Enter Shipment ID: ");
        String shipmentID = scanner.nextLine();
        System.out.print("Enter Order ID: ");
        String orderID = scanner.nextLine();

        Order foundOrder = null;
        for (Order order : warehouse.getOrders()) {
            if (orderID.equals(order.getOrderStatus())) {
                foundOrder = order;
                break;
            }
        }

        if (foundOrder != null) {
            Shipment shipment = new Shipment(shipmentID, foundOrder);
            System.out.println("Shipment processed.");
            warehouse.manageShipment(shipment);
        } else {
            System.out.println("Order not found.");
        }
    }

    // Option 5: Generate Report
    private static void generateReport() {
        warehouse.generateReport();
    }
}
