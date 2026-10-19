import java.util.Scanner;

public class WarehouseManagementSystem {
    private static String[] itemIds = new String[100];
    private static String[] itemNames = new String[100];
    private static int[] itemQuantities = new int[100];
    private static double[] itemPrices = new double[100];
    private static double[] itemDiscounts = new double[100];
    private static int itemCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nWarehouse Management System");
            System.out.println("1. Add Item");
            System.out.println("2. Search Item");
            System.out.println("3. Update Item");
            System.out.println("4. Delete Item");
            System.out.println("5. Show Inventory");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addItem(scanner);
                    break;
                case 2:
                    searchItem(scanner);
                    break;
                case 3:
                    updateItem(scanner);
                    break;
                case 4:
                    deleteItem(scanner);
                    break;
                case 5:
                    showInventory();
                    break;
                case 0:
                    System.out.println("Exiting the Warehouse Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 0);
    }

    private static void addItem(Scanner scanner) {
        System.out.print("ITEM_ID: ");
        String itemId = scanner.nextLine();
        System.out.print("ITEM_NAME: ");
        String itemName = scanner.nextLine();
        System.out.print("ITEM_QUANTITY: ");
        int itemQuantity = scanner.nextInt();
        System.out.print("ITEM_PRICE: ");
        double itemPrice = scanner.nextDouble();
        System.out.print("ITEM_DISCOUNT: ");
        double itemDiscount = scanner.nextDouble() / 100;

        itemIds[itemCount] = itemId;
        itemNames[itemCount] = itemName;
        itemQuantities[itemCount] = itemQuantity;
        itemPrices[itemCount] = itemPrice;
        itemDiscounts[itemCount] = itemDiscount;

        System.out.println("Item added successfully.");
        itemCount++;
    }

    private static void searchItem(Scanner scanner) {
        System.out.print("Enter ITEM_ID to search: ");
        String query = scanner.nextLine();

        for (int i = 0; i < itemCount; i++) {
            if (itemIds[i].equalsIgnoreCase(query)) {
                System.out.println("Item found:");
                System.out.println("ITEM_ID: " + itemIds[i]);
                System.out.println("ITEM_NAME: " + itemNames[i]);
                System.out.println("ITEM_QUANTITY: " + itemQuantities[i]);
                System.out.println("ITEM_PRICE: " + itemPrices[i]);
                System.out.println("ITEM_DISCOUNT: " + (itemDiscounts[i] * 100) + "%");
                return;
            }
        }

        System.out.println("Item not found.");
    }

    private static void updateItem(Scanner scanner) {
        showInventory();
        
        System.out.print("Enter ITEM_ID to update: ");
        String query = scanner.nextLine();

        for (int i = 0; i < itemCount; i++) {
            if (itemIds[i].equalsIgnoreCase(query)) {
                System.out.print("New ITEM_NAME: ");
                itemNames[i] = scanner.nextLine();
                System.out.print("New ITEM_QUANTITY: ");
                itemQuantities[i] = scanner.nextInt();
                System.out.print("New ITEM_PRICE: ");
                itemPrices[i] = scanner.nextDouble();
                System.out.print("New ITEM_DISCOUNT: ");
                itemDiscounts[i] = scanner.nextDouble() / 100;

                System.out.println("Item updated successfully.");
                return;
            }
        }

        System.out.println("Item not found. Update failed.");
    }



    private static void deleteItem(Scanner scanner) {
        showInventory();

        System.out.print("Enter ITEM_ID to delete: ");
        String query = scanner.nextLine();

        for (int i = 0; i < itemCount; i++) {
            if (itemIds[i].equalsIgnoreCase(query)) {
                for (int j = i; j < itemCount - 1; j++) {
                    itemIds[j] = itemIds[j + 1];
                    itemNames[j] = itemNames[j + 1];
                    itemQuantities[j] = itemQuantities[j + 1];
                    itemPrices[j] = itemPrices[j + 1];
                    itemDiscounts[j] = itemDiscounts[j + 1];
                }
                itemCount--;
                System.out.println("Item deleted successfully.");
                return;
            }
        }

        System.out.println("Item not found. Deletion failed.");
    }

    private static void showInventory() {
        System.out.println("Inventory:");
        System.out.println("----------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-20s | %-10s | %-10s | %-10s |\n", "ITEM_ID", "ITEM_NAME", "ITEM_QUANTITY", "ITEM_PRICE", "ITEM_DISCOUNT");
        System.out.println("----------------------------------------------------------------------------------");
        
        for (int i = 0; i < itemCount; i++) {
            System.out.printf("| %-10s | %-20s | %-13d | %-10.2f | %-13.2f |\n",
                              itemIds[i], itemNames[i], itemQuantities[i], itemPrices[i], itemDiscounts[i] * 100);
        }
        
        System.out.println("----------------------------------------------------------------------------------");
    }
}