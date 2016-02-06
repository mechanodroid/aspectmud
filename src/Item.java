
public class Item extends Entity {
	public Item(int x, int y, String description) {
    	super(x,y,description);
    }
	public Item(String name){
		setName(name);
	}
}
