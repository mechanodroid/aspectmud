import java.util.ArrayList;

public class mud {

	 public static final int WIDTH = 2;
     public static final int HEIGHT = 2;
     public static Room[][] room;
     public static Save newSave;
     public static Player playerOne;
	/**
	 * @param args
	 */
     public static void init() {
         // Build rooms
    	 room = new Room[WIDTH][HEIGHT];
    	 newSave = new Save();
         Rooms.build(room, WIDTH, HEIGHT);
    	 playerOne = new Player();
    	 playerOne.setName("Bob");
         playerOne.set(0, 0);    	 
     }
	public static void main(String[] args) {

		
		
		Creature theMonster = new Creature(0,0,"A gross swampy monster.");
        // Load inventory
        ArrayList<Item> inventory = new ArrayList<>();
        init();
        newSave.readRooms(playerOne, inventory, room, WIDTH, HEIGHT);
        Rooms.print(room, playerOne);
        
        // Start game
        boolean playing = true;
        while (playing) {

            String input = InputProcessor.getInput();

            if(playerOne.getX()==theMonster.getX() &&
            		playerOne.getY()==theMonster.getY())
            {
            	theMonster.printMonsterLocation();
            }
            // Movement commands
            if (input.equals("n")) {
                playerOne.GoNorth(room);
            } else if (input.equals("s")) {
                playerOne.GoSouth(room);
            } else if (input.equals("e")) {
                playerOne.GoEast(room);
            } else if (input.equals("w")) {
                playerOne.GoWest(room);
            }

            // Look commands
            else if (input.equals("look")) {
                Rooms.print(room, playerOne.getX(), playerOne.getY());
            }

            // Get commands
            else if (input.length() > 4  && input.substring(0, 4).equals("get ")) {
            	if (input.substring(0, input.indexOf(' ')).equals("get")) {
            		if (input.substring(input.indexOf(' ')).length() > 1) {
            			String item = input.substring(input.indexOf(' ') + 1);
                    	Inventory.checkItem(playerOne.getX(), playerOne.getY(), item, inventory, room);
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
            newSave.updateRoomAndPlayerStates( playerOne, room, WIDTH, HEIGHT);
            //^^ in a full featured game, this wouldn't be called every frame, 
            //but for our game, the frames are controlled by player input so this is appropriate
        }
        System.exit(0);
	}

}
