
public class Entity {
    private int x;
    private int y;

    private String description;
    public Entity() {
    	
    }
    public Entity(int x, int y, String description) {
    	this.x = x;
    	this.y = y;
    	this.description = description;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getDescription() {
        return description;
    }
}
