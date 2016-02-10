
public class Entity {
    private int x;
    private int y;

    private String name;
    private String description;
    public Entity() {
    	x=0;
    	y=0;
    }
    public Entity(int x, int y, String description) {
    	this.x = x;
    	this.y = y;
    	this.description = description;
    }
    public int getX() {
        return x;
    }
    public void setX(int x){
    	this.x = x;
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
