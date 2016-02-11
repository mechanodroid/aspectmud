
public class Creature {
	private String name;
	private String description;
	private int xPosition;
	private int yPosition;
	public Creature(int x, int y, String desc)
	{
		xPosition = x;
		yPosition = y;
		description = desc;
	}
	public void printMonsterLocation()
	{
		System.out.println("Monster: "+name);
		System.out.println(description);
	}
	public void setName(String incomingName)
	{
		name = incomingName;
	}
	public void setDescription(String incomingDesc)
	{
		description = incomingDesc;
	}
	public void setX(int incomingX)
	{
		xPosition = incomingX;
	}
	public void setY(int incomingY)
	{
		yPosition = incomingY;
	}
	public int getX()
	{
		return xPosition;
	}
	public int getY()
	{
		return yPosition;
	}
	public String getDesc()
	{
		return description;
	}
	public String getName()
	{
		return name;
	}
}
