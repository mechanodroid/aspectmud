
public class Rooms {
	public static void build(Room[][] room, final int WIDTH, final int HEIGHT) {

    	// Initialize rooms (a 2D array)
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                room[i][j] = new Room(i, "", "", null);
            }
        }
        
        return;
        
        //no longer needed:
        /*

        room[0][0].setNumber(1);
        room[0][0].setName("Living ");
        room[0][0].setDescription("You are in your living room.");
        room[0][0].setItems(new Item("wallet"));
        room[0][0].setItems(new Item("remote"));

        room[0][1].setNumber(2);
        room[0][1].setName("Bedroom");
        room[0][1].setDescription("You are in your bedroom. Your closet is slightly ajar.");
        room[0][1].setItems(new Item("keys"));
        room[0][1].setItems(new Item("flashlight"));

        room[1][0].setNumber(3);
        room[1][0].setName("Kitchen");
        room[1][0].setDescription("You are in your kitchen.");
        room[1][0].setItems(new Item("pop tarts"));
        room[1][0].setItems(new Item("soda"));

        room[1][1].setNumber(4);
        room[1][1].setName("Bathroom");
        room[1][1].setDescription("You are in your bathroom.");
        room[1][1].setItems(new Item("toilet paper"));
        room[1][1].setItems(new Item("magazine"));
        */
    }
	public static void print(Room[][] room, int x, int y) {

        System.out.println(room[x][y].getDescription());
        System.out.println("You see: " + Rooms.printItems(room, x, y));
        System.out.println();
    }
    public static void print(Room[][] room, Player player) {

        Rooms.print(room, player.getX(), player.getY());
    }
    public static String printItems(Room[][] room, int x, int y) {
    	String buildString = "";
    	for(Item i:room[x][y].getItems())
    	{
    		buildString+=i.getName();
    		buildString+=" ";
    	}
    	return buildString;
    }

    // Remove item from room when added to inventory
    public static void removeItem(Room[][] room, int x, int y, String item) {
    	
    	room[x][y].deleteItem(item);
    }
}
