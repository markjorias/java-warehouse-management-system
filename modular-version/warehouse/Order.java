package warehouse;

import java.util.*;

public class Order {
    private String orderID;
    private String customerName;
    private List<Item> items;
    private String status;

    public Order(String orderID, String customerName) {
        this.orderID = orderID;
        this.customerName = customerName;
        this.items = new ArrayList<>();
        this.status = "Pending";
    }

    public void addItemToOrder(Item item) {
        items.add(item);
    }

    public void removeItemFromOrder(Item item) {
        items.remove(item);
    }

    public void processOrder() {
        this.status = "Processed";
        // Additional order processing logic
    }

    public void cancelOrder() {
        this.status = "Cancelled";
    }

    public List<Item> getItems() {
        return items;
    }

    public String getOrderStatus() {
        return status;
    }

    // Additional Getters and Setters (if needed)
}
