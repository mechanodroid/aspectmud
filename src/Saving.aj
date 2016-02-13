import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


class EntitySave {
	//this is a generic save which probably won't be used
	public static ArrayList<Entity> entities;
}
class InventorySave {
	public static ArrayList<Item> inventory = new ArrayList<Item>();
}
class ItemSave {
	public static Map<Room, ArrayList<Item>> items = new HashMap<Room, ArrayList<Item>>();
	public static void ItemSaveHelper(Item i, Room room){
    	ArrayList<Item> itemsInRoom = ItemSave.items.get(room);
    	if(itemsInRoom==null){
    		ItemSave.items.put(room, new ArrayList<Item>());
        	itemsInRoom = ItemSave.items.get(room);
    	}
    	itemsInRoom.add(i);		
	}
	public static void ItemDeleteHelper(String item, Room room){
    	ArrayList<Item> itemsInRoom = ItemSave.items.get(room);
    	if(itemsInRoom==null){
    		return;
    	}
    	else {
    		for(int i = 0; i<itemsInRoom.size(); i++)
        	{
        		if(itemsInRoom.get(i).getName().equals(item))
        		{
        			itemsInRoom.remove(i);
        		}
        	}    	
    	}
	}

}
class PlayerSave {
	public static ArrayList<Player> players = new ArrayList<Player>();
	
}
class RoomSave {
	public static ArrayList<Room> rooms;
}

public aspect Saving {
    //for most of these pointcuts we will extract information that must be saved
	//then every once and a while we will call the overall save function for 
	//all the data that's been checkpoint saved
	
	//the following pointcut saves the entire mud, but is no longer needed, keeping it here in case we need something from it later:
    /*pointcut afterSave() : execution(* *.set*(..)); 
    after() returning() : afterSave() { 
        mud.newSave.writeRooms(mud.room, mud.WIDTH, mud.HEIGHT);
    }*/
	

	pointcut afterSetItem(Room room, Item item) : target(room) &&
																 args(item) &&
																 call(void setItems(Item)) &&
																 !within(Saving); 
    
    after() returning() : afterSetItem(Room, Item) { 
    	Item i = (Item)thisJoinPoint.getArgs()[0];
    	Room room = (Room)thisJoinPoint.getTarget();
    	ItemSave.ItemSaveHelper(i, room);
    }
    
    pointcut afterDeleteItem(Room room, String item) : target(room) &&
															     args(item) &&
																 call(void deleteItem(String)) &&
																 !within(Saving); 

    after() returning() : afterDeleteItem(Room, String) { 
		String i = (String)thisJoinPoint.getArgs()[0];
		Room room = (Room)thisJoinPoint.getTarget();
		ItemSave.ItemDeleteHelper(i, room);
    }


    pointcut location_setY(Player p1, int val): target(p1) && 
    											args(val) &&
    											call(void setY(int)) &&
    													!within(Saving);



    after(): location_setY(Player, int) { 	
    	System.out.println("int argument"+(int)thisJoinPoint.getArgs()[0]); 
		System.out.println("player argument"+((Player)thisJoinPoint.getTarget()).getName());
		if( PlayerSave.players.get(0)!=null ) {
			PlayerSave.players.get(0).setY((int)thisJoinPoint.getArgs()[0]);
			//for player, take it and write out to a temp structure
		}
	}
    
    pointcut location_setX(Player p1, int val): target(p1) && 
    											args(val) &&
    											execution(void setX(int)) &&
    													!within(Saving);
    

    after(): location_setX(Player, int) { 
        System.out.println("int argument"+(int)thisJoinPoint.getArgs()[0]); 
        System.out.println("player argument"+((Player)thisJoinPoint.getTarget()).getName());
        if( PlayerSave.players.get(0)!=null ) {
        	PlayerSave.players.get(0).setX((int)thisJoinPoint.getArgs()[0],false);
            //for player, take it and write out to a temp structure
        }
    }
    
    
    
    pointcut mud_(): execution(void mud.main(String[]));
    pointcut inventory_(): execution(void Inventory.checkItem(int, int, String,
            ArrayList<Item>, Room[][]));
    pointcut checkItem(): call(void Rooms.removeItem(Room[][], int, int, String));

    before() : checkItem() && cflow(mud_()) && cflow(inventory_()) && !within(Saving)  {
        System.out.println("getting item!!!");
    	Object fromJoin = thisJoinPoint.getArgs()[3];
        Item itemToSave = new Item(fromJoin.toString());
    	InventorySave.inventory.add(itemToSave);
    }

    pointcut newobject(Object createdObject) :
	    execution(Player.new(..)) 
	        && this(createdObject);
	before(Object createdObject) : newobject(createdObject) 
	{
		PlayerSave.players.add((Player)createdObject);
	}
	
    pointcut readRooms_(): execution(void Save.readRooms(Room[][], int, int));
    pointcut afterAddItem( Item item) : 
    							cflow(readRooms_()) &&
    							args(item) &&
    							call(* add(Item)) &&
    							!within(Saving); 

    before() : afterAddItem( Item) { 
    	Item i = (Item)thisJoinPoint.getArgs()[0];
    	ItemSave.ItemSaveHelper(i, null);

    }
}
