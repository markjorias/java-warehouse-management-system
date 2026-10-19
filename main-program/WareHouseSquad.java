import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Item {
    String itemID;
    String itemName;
    int quantity;
    double price;
    String size;

    public Item(String itemID, String itemName, int quantity, double price, String size) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
        this.size = size;
    }

    @Override
    public String toString() {
        return itemID + " | " + itemName + " | " + quantity + " | " + price + " | " + size;
    }
}

class Order {
    String orderID;
    String customerName;
    String address;
    String itemID;
    int quantity;
    double totalAmount;

    public Order(String orderID, String customerName, String address, String itemID, int quantity, double totalAmount) {
        this.orderID = orderID;
        this.customerName = customerName;
        this.address = address;
        this.itemID = itemID;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return orderID + " | " + customerName + " | " + address + " | " + itemID + " | " + quantity + " | " + totalAmount;
    }
}

class Shipment {
    String orderID;
    String trackingId;
    String status;

    public Shipment(String orderID, String trackingId, String status) {
        this.orderID = orderID;
        this.trackingId = trackingId;
        this.status = status;
    }

    @Override
    public String toString() {
        return orderID + " | " + " | " + trackingId + " | " + status;
    }
}

public class WareHouseSquad {
    static List<Item> items = new ArrayList<>();
    static List<Order> orders = new ArrayList<>();
    static List<Shipment> shipments = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("0. Exit");
            System.out.println("1. Manage Inventory");
            System.out.println("2. Process Orders");
            System.out.println("3. Manage Shipments");
            System.out.println("4. Reports");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 
            switch (choice) {
                case 0:
                    System.out.println("Exiting the program.");
                    return;
                case 1:
                    manageInventory();
                    break;
                case 2:
                    processOrders();
                    break;
                case 3:
                    manageShipments();
                    break;
                case 4:
                    generateReports();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void manageInventory() {
        while (true) {
            System.out.println("\nInventory Management:");
            System.out.println("1. Add New Items");
            System.out.println("2. Update Item");
            System.out.println("3. Remove Items");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0:
                    return;
                case 1:
                    addNewItem();
                    break;
                case 2:
                    updateItem();
                    break;
                case 3:
                    removeItem();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addNewItem() {
        System.out.print("\nItem ID: ");
        String itemID = scanner.nextLine();
        System.out.print("Item Name: ");
        String itemName = scanner.nextLine();
        System.out.print("Quantity: ");
        int itemQuantity = scanner.nextInt();
        System.out.print("Price: ");
        double itemPrice = scanner.nextDouble();
        scanner.nextLine(); 
        System.out.print("Size [S,M,L]: ");
        String itemsize = scanner.nextLine();
        while (!itemsize.equals("S") && !itemsize.equals("M") && !itemsize.equals("L")) {
            System.out.print("Invalid size. Please enter S, M or L: ");
            itemsize = scanner.nextLine();
        }
        
        items.add(new Item(itemID, itemName, itemQuantity, itemPrice, itemsize));
        System.out.println("Item added successfully.");
    }

    private static void updateItem() {
        listItems();
        if (items.isEmpty()) {
            System.out.println("There are no Items in Inventory");
            return;
        }
        System.out.print("\nEnter ID to update: ");
        String itemID = scanner.nextLine();
        for (Item item : items) {
            if (item.itemID.equals(itemID)) {
                System.out.print("Name: ");
                item.itemName = scanner.nextLine();
                System.out.print("Quantity: ");
                item.quantity = scanner.nextInt();
                System.out.print("Price: ");
                item.price = scanner.nextDouble();
                scanner.nextLine(); 
                System.out.print("Size: ");
                item.size = scanner.nextLine();
                System.out.println("Item updated successfully.");
                return;
            }
        }
        System.out.println("Item not found.");
    }

    private static void removeItem() {
        listItems();
        System.out.print("\nEnter itemID to remove: ");
        String itemID = scanner.nextLine();
        items.removeIf(item -> item.itemID.equals(itemID));
        System.out.println("Item removed successfully.");
    }

    private static void processOrders() {
        while (true) {
            System.out.println("\nOrder Processing:");
            System.out.println("1. New Order");
            System.out.println("2. Cancel Order");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0:
                    return;
                case 1:
                    newOrder();
                    break;
                case 2:
                    cancelOrder();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void newOrder() {
        listItems();
        System.out.print("\nOrder ID: ");
        String orderID = scanner.nextLine();
        System.out.print("Customer Name: ");
        String name = scanner.nextLine();
        System.out.print("Delivery Address: ");
        String address = scanner.nextLine();
        System.out.print("Item ID: ");
        String itemID = scanner.nextLine();
        System.out.print("Quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();
    
        double totalAmount = 0.0;
        Item orderedItem = null;
    
        for (Item item : items) {
            if (item.itemID.equals(itemID)) {
                if (item.quantity >= quantity) {
                    totalAmount = item.price * quantity;
                    orderedItem = item;
                    break;
                } else {
                    System.out.println("Insufficient quantity available for item: " + itemID);
                    return;
                }
            }
        }
    
        if (orderedItem != null) {
            orderedItem.quantity -= quantity;
            orders.add(new Order(orderID, name, address, itemID, quantity, totalAmount));
            System.out.println("Order placed successfully.");
        } else {
            System.out.println("Item not found.");
        }
    }

    private static void cancelOrder() {
        listOrders();
        System.out.print("\nEnter ORDER_ID to cancel: ");
        String orderID = scanner.nextLine();

        for (Order order : orders) {
            if (order.orderID.equals(orderID)) {
                orders.remove(order);
                Item item = items.stream()
                        .filter(i -> i.itemID.equals(order.itemID))
                        .findFirst()
                        .orElseThrow();
                item.quantity += order.quantity;
                System.out.println("Order canceled successfully.");
                return;
            }
        }
        System.out.println("Order not found.");
    }

    private static void manageShipments() {
        while (true) {
            System.out.println("\nShipment Management:");
            System.out.println("1. Add New Shipment");
            System.out.println("2. Cancel Shipment");
            System.out.println("3. Mark Shipment as Delivered");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0:
                    return;
                case 1:
                    addNewShipment();
                    break;
                case 2:
                    cancelShipment();
                    break;
                case 3:
                    markedDelivered();
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addNewShipment() {
        listOrders();
        System.out.print("\nEnter Order ID: ");
        String orderID = scanner.nextLine();
        System.out.print("Enter Tracking ID: ");
        String trackingId = scanner.nextLine();
    
        for (Order order : orders) {
            if (order.orderID.equals(orderID)) {
                shipments.add(new Shipment(orderID, trackingId, "SHIPPED"));
                System.out.println("New shipment added successfully.");
                return;
            }
        }
        System.out.println("Order not found.");
    }

    private static void cancelShipment() {
        listShipments();
        System.out.print("\nEnter Tracking ID to cancel: ");
        String trackingId = scanner.nextLine();
        shipments.removeIf(shipment -> shipment.trackingId.equals(trackingId));
        System.out.println("Shipment canceled successfully.");
    }

    private static void markedDelivered() {
        listShipments();
        System.out.print("\nEnter Tracking ID to mark as delivered: ");
        String trackingId = scanner.nextLine();
        boolean found = false;
        for (Shipment shipment : shipments) {
            if (shipment.trackingId.equals(trackingId)) {
                shipment.status = "DELIVERED";
                System.out.println("Shipment marked as delivered successfully.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Shipment not found.");
        }
    }

    private static void generateReports() {
        System.out.println("\nReports:");
        System.out.println("1. Inventory Report");
        System.out.println("2. Order Report");
        System.out.println("3. Shipment Report");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                printInventoryReport();
                break;
            case 2:
                printOrderReport();
                break;
            case 3:
                printShipmentReport();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void listItems() {
        System.out.println("\nItems:");
        System.out.println("--------------------------------------------------------------------");
        System.out.printf("| %-10s | %-20s | %-10s | %-10s | %-20s |\n",
                "ITEM ID", "ITEM NAME", "QUANTITY", "PRICE", "Size");
        System.out.println("--------------------------------------------------------------------");
        for (Item item : items) {
            System.out.printf("| %-10s | %-20s | %-10d | %-10.2f | %-20s |\n",
                    item.itemID, item.itemName, item.quantity, item.price, item.size);
        }
        System.out.println("--------------------------------------------------------------------");
    }

    private static void listOrders() {
        System.out.println("\nOrders:");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-20s | %-20s | %-15s | %-10s |\n",
                "ORDER ID", "CUSTOMER NAME", "DELIVERY ADDRESS", "ITEM ID", "SHIPMENT STATUS");
        System.out.println("--------------------------------------------------------------------------------");
        for (Order order : orders) {
            System.out.printf("| %-10s | %-20s | %-20s | %-15s | %-10s |\n",
                    order.orderID, order.customerName, order.address, order.itemID, getShipmentStatus(order.orderID));
        }
        System.out.println("--------------------------------------------------------------------------------");
    }

    private static void listShipments() {
        System.out.println("\nShipments:");
        System.out.println("------------------------------------------------------------");
        System.out.printf("| %-10s | %-15s | %-20s |\n",
                "ORDER ID", "TRACKING ID", "STATUS");
        System.out.println("------------------------------------------------------------");
        for (Shipment shipment : shipments) {
            System.out.printf("| %-10s | %-15s | %-20s |\n",
                    shipment.orderID, shipment.trackingId, shipment.status);
        }
        System.out.println("------------------------------------------------------------");
    }

    private static void printInventoryReport() {
        System.out.println("\nInventory Report:");
        System.out.println("--------------------------------------------------------------------");
        System.out.printf("| %-10s | %-20s | %-10s | %-10s | %-20s |\n",
                "ITEM ID", "ITEM NAME", "QUANTITY", "PRICE", "Size");
        System.out.println("--------------------------------------------------------------------");
        for (Item item : items) {
            System.out.printf("| %-10s | %-20s | %-10d | %-10.2f | %-20s |\n",
                    item.itemID, item.itemName, item.quantity, item.price, item.size);
        }
        System.out.println("--------------------------------------------------------------------");
    }

    private static void printOrderReport() {
        System.out.println("\nOrder Report:");
        System.out.println("--------------------------------------------------------------------");
        System.out.printf("| %-10s | %-20s | %-15s | %-10s | %-15s | %-15s |\n",
                "ORDER ID", "CUSTOMER NAME", "ITEM ID", "QUANTITY", "TOTAL PRICE", "SHIPMENT STATUS");
        System.out.println("--------------------------------------------------------------------");
        double totalSales = 0.0;
        for (Order order : orders) {
            System.out.printf("| %-10s | %-20s | %-15s | %-10s | %-15.2f | %-15s |\n",
                    order.orderID, order.customerName, order.itemID, order.quantity, order.totalAmount,
                    getShipmentStatus(order.orderID));
            totalSales += order.totalAmount;
        }
        System.out.println("--------------------------------------------------------------------");
        System.out.println("TOTAL SALES: " + String.format("%.2f", totalSales));
        System.out.println("--------------------------------------------------------------------");
    }

    private static String getShipmentStatus(String orderID) {
        for (Shipment shipment : shipments) {
            if (shipment.orderID.equals(orderID)) {
                return shipment.status;
            }
        }
        return "NOT SHIPPED";
    }

    private static void printShipmentReport() {
        System.out.println("\nShipment Report:");
        System.out.println("---------------------------------------------------");
        System.out.printf("| %-10s | %-15s | %-10s |\n", "Order ID", "Tracking ID", "Status");
        System.out.println("---------------------------------------------------");
        for (Shipment shipment : shipments) {
            System.out.printf("| %-10s | %-15s | %-10s |\n", shipment.orderID, shipment.trackingId, shipment.status);
        }
        System.out.println("---------------------------------------------------");
    }
}
