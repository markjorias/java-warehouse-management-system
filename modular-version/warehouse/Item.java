package warehouse;

import java.util.*;

public class Item {
    private String itemID;
    private String name;
    private int quantity;
    private String location;

    public Item(String itemID, String name, int quantity, String location) {
        this.itemID = itemID;
        this.name = name;
        this.quantity = quantity;
        this.location = location;
    }

    public void addItem() {
        // Code to add item
    }

    public void removeItem() {
        // Code to remove item
    }

    public void updateQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }

    public String getItemInfo() {
        return "Item ID: " + itemID + ", Name: " + name + ", Quantity: " + quantity + ", Location: " + location;
    }
    
    // Getters and Setters (if needed)
}
