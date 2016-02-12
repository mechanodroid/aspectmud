
public class Entity {
    private int x;
    private int y;

    private String name;
    private String description;
    public Entity() {
    	x=0;
    	y=0;
    }
    public Entity(int incomingx, int y, String description) {
    	x = incomingx;
    	this.y = y;
    	this.description = description;
    }
    public int getX() {
        return x;
    }
    public void setX(int x){
    	this.x = x;
    }
    public void setX(int x, boolean test) {
    	this.x = x;
    }
    public void setY(int y, boolean test) {
    	this.y = y;
    }
    public void setY(int y){
    	this.y = y;
    }

    public int getY() {
        return y;
    }

    public String getName() {
    	return name;
    }
    public void setName(String name){
    	this.name = name;
    }
    public String getDescription() {
        return description;
    }
}
