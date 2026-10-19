import java.util.Scanner;

public class Inventory {
    private static String[] itemCodes = new String[100]; // Array to store item codes
    private static String[] itemNames = new String[100]; // Array to store item names
    private static double[] itemPrices = new double[100]; // Array to store item prices
    private static int itemCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
	
	AddItem Addobj = new AddItem();
	
	
        do {
            System.out.println("Inventory Management System");
            System.out.println("1. Add Item");
            System.out.println("2. Search Item");
            System.out.println("3. Update Item");
            System.out.println("4. Delete Item");
            System.out.println("5. Show Item Table");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    Addobj.addItem(itemCodes, itemNames, itemPrices, itemCount);
                    itemCount++;
                    break;
                case 2:
                    SearchItem.searchItem(itemCodes, itemNames, itemPrices, itemCount);
                    break;
                case 3:
                    UpdateItem.updateItem(itemCodes, itemNames, itemPrices, itemCount);
                    break;
                case 4:
                    DeleteItem.deleteItem(itemCodes, itemNames, itemPrices, itemCount);
                    itemCount--;
                    break;
                case 5:
                    ShowItemTable.showItemTable(itemCodes, itemNames, itemPrices, itemCount);
                    break;
                case 0:
                    System.out.println("Exiting the Inventory Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 0);

        scanner.close();
    }
}

class AddItem {
    public static void addItem(String[] itemCodes, String[] itemNames, double[] itemPrices, int itemCount) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the item code: ");
        String itemCode = scanner.nextLine();
        System.out.print("Enter the item name: ");
        String itemName = scanner.nextLine();
        System.out.print("Enter the item price: ");
        double itemPrice = scanner.nextDouble();
        
        itemCodes[itemCount] = itemCode;
        itemNames[itemCount] = itemName;
        itemPrices[itemCount] = itemPrice;
        System.out.println("Item added successfully.");
    }
}

class SearchItem {
    public static void searchItem(String[] itemCodes, String[] itemNames, double[] itemPrices, int itemCount) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the item code to search: ");
        String query = scanner.nextLine();

        for (int i = 0; i < itemCount; i++) {
            if (itemCodes[i].equalsIgnoreCase(query)) {
                System.out.println("Item Code: " + itemCodes[i]);
                System.out.println("Item Name: " + itemNames[i]);
                System.out.println("Item Price: " + itemPrices[i]);
                return;
            }
        }

        System.out.println("Item not found.");
    }
}

class UpdateItem {
    public static void updateItem(String[] itemCodes, String[] itemNames, double[] itemPrices, int itemCount) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the item code to update: ");
        String query = scanner.nextLine();

        for (int i = 0; i < itemCount; i++) {
            if (itemCodes[i].equalsIgnoreCase(query)) {
                System.out.print("Enter the new item code: ");
                String newItemCode = scanner.nextLine();
                System.out.print("Enter the new item name: ");
                String newItemName = scanner.nextLine();
                System.out.print("Enter the new item price: ");
                double newItemPrice = scanner.nextDouble();
                
                itemCodes[i] = newItemCode;
                itemNames[i] = newItemName;
                itemPrices[i] = newItemPrice;
                System.out.println("Item updated successfully.");
                return;
            }
        }

        System.out.println("Item not found. Update failed.");
    }
}

class DeleteItem {
    public static void deleteItem(String[] itemCodes, String[] itemNames, double[] itemPrices, int itemCount) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the item code to delete: ");
        String query = scanner.nextLine();

        for (int i = 0; i < itemCount; i++) {
            if (itemCodes[i].equalsIgnoreCase(query)) {

                for (int j = i; j < itemCount; j++) {
                    itemCodes[j] = itemCodes[j + 1];
                    itemNames[j] = itemNames[j + 1];
                    itemPrices[j] = itemPrices[j + 1];
					
                }
				itemCodes[99] = null;
                itemNames[99] = null;
                itemPrices[99] = 0.0;
			
                System.out.println("Item deleted successfully.");
				System.out.println("Item count: " + itemCount);
                return;
            }
        }

        System.out.println("Item not found. Deletion failed.");
    }
}

class ShowItemTable {
    public static void showItemTable(String[] itemCodes, String[] itemNames, double[] itemPrices, int itemCount) {
 
            System.out.println("Item Table:");
            System.out.println("---------------------------------------------------");
            System.out.printf("| %-10s | %-20s | %-10s |\n", "Item Code", "Item Name", "Item Price");
            System.out.println("---------------------------------------------------");
            
            for (int i = 0; i < itemCount; i++) {
                System.out.printf("| %-10s | %-20s | %-10.2f |\n", itemCodes[i], itemNames[i], itemPrices[i]);
            }
            
            System.out.println("---------------------------------------------------");
        
    }
}