import java.util.ArrayList;

class Inventory {
	
	public static void checkItem(int x, int y, String item,
           ArrayList<Item> inventory, Room[][] room) {
		
		// Check if item is a valid room item
		boolean validRoomItem = false;
		for (Item roomItems : room[x][y].items ) {
			if (roomItems.getName().equals(item)) {
				validRoomItem = true;
				break;
			}
		}
		
		// Check if item is already in inventory
		boolean inInventory = false;
		for (Item itemInInv: inventory) {
			if (itemInInv.getName().equals(item)) {
				inInventory = true;
					break;
			}
		}
		
		// Text output
		if (!inInventory && validRoomItem) {
			System.out.println("You pick up the " + item + ".");
			inventory.add(new Item(item));
			Rooms.removeItem(room, x, y, item);
		}
		else if (inInventory) {
			System.out.println("You already have the " + item + ".");
		}
		else if (!validRoomItem) {
			System.out.println("You don't see that here.");
		}
		else {
			System.out.println("I don't understand.");
		}
    }
	public static void addItem(ArrayList<Item> inventory, Item item) {
		inventory.add(item);
	}

    public static void print(ArrayList<Item> inventory) {

        System.out.println("Inventory:");
        for (Item item : inventory) {
            System.out.println(item.getName());
        }
    }
}