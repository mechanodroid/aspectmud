//cross cutting concern: saving state

import java.util.ArrayList;

public class Room extends Entity {


    public Room(int x, int y, String description) {
    	super(x,y,description);
    }
    private int number;
    private String name;
    private String description;
    public ArrayList<Item> items = new ArrayList<>();

    public Room(int number, String name, String description,
            ArrayList<Item> items) {
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setItems(Item item) {
        this.items.add(item);
    }

    public void deleteItem(String item) {
    	for(int i = 0; i<items.size(); i++)
    	{
    		if(items.get(i).getName()==item)
    		{
    			this.items.remove(i);
    		}
    	}
    }

    public ArrayList<Item> getItems() {
        return this.items;
    }


}
