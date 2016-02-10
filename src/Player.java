
public class Player extends Entity {
	public Player(int x, int y, String description) {
    	super(x,y,description);
    }

	public Player() {
//		super.setX(0); //cannot due these due to aspectj, TODO: write about this
//		super.setY(0);
		super();
	}
	public void decrementY(){
		this.setY(super.getY()-1);
	}
	
	public void decrementX(){
		this.setY(super.getX()-1);
	}
	
	public void incrementY(){
		this.setY(super.getY()+1);
	}
	
	public void incrementX(){
		this.setY(super.getX()+1);
	}
	
	public void GoNorth(Room[][] room){
		if (getY() > 0) {
			decrementY();
			Rooms.print(room, this);
        } else {
            System.out.println("You can't go that way.");
        }
	}
	
	public void GoEast(Room[][] room){
		if (getX() > 0) {
			decrementX();
			Rooms.print(room, this);
		} else {
			System.out.println("You can't go that way.");
		}
	}
	

	public void GoWest(Room[][] room){
		if (getX() < mud.WIDTH - 1) {
			incrementX();
			Rooms.print(room, getX(), getY());
        } else {
            System.out.println("You can't go that way.");
        }
	}
	
	public void GoSouth(Room[][] room) {
		if (getY() < mud.HEIGHT - 1) {
			incrementY();
			Rooms.print(room, this);
		} else {
			System.out.println("You can't go that way.");
		}
	}

	public void set(int x, int y) {
		super.setX(x);
		super.setY(y);
	}

}
