package warehouse;

import java.util.*;

public class Warehouse {
    private List<Item> inventory;
    private List<Order> orders;
    private List<Shipment> shipments;

    public Warehouse() {
        inventory = new ArrayList<>();
        orders = new ArrayList<>();
        shipments = new ArrayList<>();
    }

    public void addInventory(Item item) {
        inventory.add(item);
    }

    public void removeInventory(Item item) {
        inventory.remove(item);
    }

    public void processOrder(Order order) {
        orders.add(order);
        order.processOrder();
        System.out.println("Order " + order.getOrderStatus());
    }

    public void manageShipment(Shipment shipment) {
        shipments.add(shipment);
        shipment.trackShipment();
    }

    public void generateReport() {
        System.out.println("Inventory Report:");
        for (Item item : inventory) {
            System.out.println(item.getItemInfo());
        }
        // Additional reporting logic
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public List<Order> getOrders() {
        return orders;
    }

    // Additional Getters and Setters (if needed)
}
