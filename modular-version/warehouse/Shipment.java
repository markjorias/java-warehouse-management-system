package warehouse;

import java.util.*;

public class Shipment {
    private String shipmentID;
    private Order order;
    private String status;
    private Date deliveryDate;

    public Shipment(String shipmentID, Order order) {
        this.shipmentID = shipmentID;
        this.order = order;
        this.status = "In Transit";
    }

    public void trackShipment() {
        System.out.println("Shipment Status: " + status);
    }

    public void updateShipmentStatus(String newStatus) {
        this.status = newStatus;
    }

    public void setDeliveryDate(Date date) {
        this.deliveryDate = date;
    }
    
    // Additional Getters and Setters (if needed)
}
