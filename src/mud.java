import java.util.ArrayList;

public class mud {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        // Build rooms
        final int WIDTH = 2;
        final int HEIGHT = 2;
        Room[][] room = new Room[WIDTH][HEIGHT];
        Rooms.build(room, WIDTH, HEIGHT);
        int x = 0;
        int y = 0;
        Save newSave = new Save();
        // Load inventory
        ArrayList<Item> inventory = new ArrayList<>();
        newSave.readRooms(room, WIDTH, HEIGHT);
        Rooms.print(room, x, y);
        
        // Start game
        boolean playing = true;
        while (playing) {

            String input = InputProcessor.getInput();

            // Movement commands
            if (input.equals("n")) {
                if (y > 0) {
                    y--;
                    Rooms.print(room, x, y);
                } else {
                    System.out.println("You can't go that way.");
                }
            } else if (input.equals("s")) {
                if (y < HEIGHT - 1) {
                    y++;
                    Rooms.print(room, x, y);
                } else {
                    System.out.println("You can't go that way.");
                }
            } else if (input.equals("e")) {
                if (x > 0) {
                    x--;
                    Rooms.print(room, x, y);
                } else {
                    System.out.println("You can't go that way.");
                }
            } else if (input.equals("w")) {
                if (x < WIDTH - 1) {
                    x++;
                    Rooms.print(room, x, y);
                } else {
                    System.out.println("You can't go that way.");
                }
            }

            // Look commands
            else if (input.equals("look")) {
                Rooms.print(room, x, y);
            }

            // Get commands
            else if (input.length() > 4  && input.substring(0, 4).equals("get ")) {
            	if (input.substring(0, input.indexOf(' ')).equals("get")) {
            		if (input.substring(input.indexOf(' ')).length() > 1) {
            			String item = input.substring(input.indexOf(' ') + 1);
                    	Inventory.checkItem(x, y, item, inventory, room);
            		}	
            	}
            }

            // Inventory commands
            else if (input.equals("i") || input.equals("inv")
                    || input.equals("inventory")) {
                Inventory.print(inventory);
            }
            else if (input.equals("save")) {
                newSave.writeRooms(room, WIDTH, HEIGHT);
            }            
            // Quit commands
            else if (input.equals("quit")) {
                System.out.println("Goodbye!");
                playing = false;

            // Catch-all for invalid input
            } else {
                System.out.println("You can't do that.");
            }
        }
        System.exit(0);
	}

}
