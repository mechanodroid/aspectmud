import java.util.ArrayList;


class EntitySave {
	//this is a generic save which probably won't be used
	public static ArrayList<Entity> entities;
}
class InventorySave {
	public static ArrayList<Inventory> inventories;
}
class ItemSave {
	public static ArrayList<Item> items;
}
class PlayerSave {
	public static ArrayList<Player> players;
	
}
class RoomSave {
	public static ArrayList<Room> rooms;
}

public aspect Saving {
    //for most of these pointcuts we will extract information that must be saved
	//then every once and a while we will call the overall save function for 
	//all the data that's been checkpoint saved
    /*pointcut afterSave() : execution(* *.set*(..)); 
    
    after() returning() : afterSave() { 
        System.out.println("save now!"); 
        mud.newSave.writeRooms(mud.room, mud.WIDTH, mud.HEIGHT);
    }*/

    
    pointcut location_setX(Player p1, int val): target(p1) && 
    											args(val) &&
    											call(void setX(int));
    
    

    after(): location_setX(Player, int) { 
        System.out.println("int argument"+(int)thisJoinPoint.getArgs()[0]); 
        System.out.println("player argument"+((Player)thisJoinPoint.getTarget()).getName()); 
        //for player, take it and write out to a temp structure, which
        //will be eventually saved out. This is in place of:
        // mud.newSave.writeRooms(mud.room, mud.WIDTH, mud.HEIGHT);
    }
    
    pointcut mud_(): execution(void mud.main(String[]));
    pointcut inventory_(): execution(void Inventory.checkItem(int, int, String,
            ArrayList<Item>, Room[][]));
    pointcut checkItem(): call(void Rooms.removeItem(Room[][], int, int, String));

    before() : checkItem() && cflow(mud_()) && cflow(inventory_()) && !within(Saving)  {
        System.out.println("getting item!!!");
    	Object fromJoin = thisJoinPoint.getArgs()[3];
        Item itemToSave = new Item(fromJoin.toString());
    	ItemSave.items.add(itemToSave);
    }

    pointcut newobject(Object createdObject) :
	    execution(Player.new(..)) 
	        && this(createdObject);
	before(Object createdObject) : newobject(createdObject) 
	{
		PlayerSave.players.add((Player)createdObject);
	}
}
