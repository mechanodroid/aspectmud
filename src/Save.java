import java.io.FileWriter;
import java.util.Set;
import java.util.Iterator;
import java.io.IOException;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.JDOMException;

public class Save {
	public void updateRooms(Player player, ArrayList<Item> inv, Room[][] rooms, int WIDTH, int HEIGHT) {
		SAXBuilder builder = new SAXBuilder();
		  File xmlFile = new File("mudState.xml");
		  try {

				Document document = (Document) builder.build(xmlFile);
				Element rootNode = document.getRootElement();
				List list = rootNode.getChildren("roomname");
				for (int i = 0; i < list.size(); i++) {
				   ArrayList<Item> newItems = new ArrayList<Item>();
				   Element node = (Element) list.get(i);
				   List itemList = node.getChildren("item");
				   for (int j = 0; j < itemList.size(); j++) {
					   Element itemNode = (Element) itemList.get(j);
					   newItems.add(new Item(itemNode.getText()));
				   }
				   for(int i2 = 0; i2 < HEIGHT; i2++) {
						for(int j2 = 0; j2 < WIDTH; j2++) {
							System.out.println("in update from state:"+node.getTextTrim()+":"+rooms[i2][j2].getName().trim());
							if(rooms[i2][j2].getName().trim().equals(node.getTextTrim())) {
								rooms[i2][j2].setItems(newItems);
							}
						}
					}
				}
				List listInv = rootNode.getChildren("inventory");
				if(!listInv.isEmpty()) {
					inv.clear();
					Element node = (Element)listInv.get(0);
				    List itemList = node.getChildren("item");
				    for (int j = 0; j < itemList.size(); j++) {
						   Element itemNode = (Element) itemList.get(j);
						   inv.add(new Item(itemNode.getText()));
					}
				}
				List players = rootNode.getChildren("player");
				if(!players.isEmpty()) {
					Element node = (Element)players.get(0);
					List xList = node.getChildren("x");
					List yList = node.getChildren("y");
					if(!xList.isEmpty() && !yList.isEmpty()) {
						Element xElement = (Element)xList.get(0);
						Element yElement = (Element)yList.get(0);
						player.set(Integer.parseInt(xElement.getTextTrim()),
									Integer.parseInt(yElement.getTextTrim()));
					}
				}
				

			  } catch (IOException io) {
				System.out.println(io.getMessage());
			  } catch (JDOMException jdomex) {
		      System.out.println(jdomex.getMessage());
		  }
	}
	public void readRooms(Player player, ArrayList<Item> inv, Room[][] rooms, int WIDTH, int HEIGHT)	{
		  SAXBuilder builder = new SAXBuilder();
		  File xmlFile = new File("mud.xml");
		  try {

				Document document = (Document) builder.build(xmlFile);
				Element rootNode = document.getRootElement();
				List list = rootNode.getChildren("roomname");

				for (int i = 0; i < list.size(); i++) {
				   Element node = (Element) list.get(i);
				   System.out.println("Room Name : " + node.getText());
				   List itemList = node.getChildren("item");
				   int posx = Integer.parseInt(node.getChild("position_x").getText());
				   int posy = Integer.parseInt(node.getChild("position_y").getText());
				   rooms[posx][posy].setName(node.getText());
				   rooms[posx][posy].setDescription(node.getChildText("desc"));
				   rooms[posx][posy].items.clear();
				   for (int j = 0; j < itemList.size(); j++) {
					   Element itemNode = (Element) itemList.get(j);
					   System.out.println("Item Name : " + itemNode.getText());
					   rooms[posx][posy].setItems(new Item(itemNode.getText()));	
				   }   
				}

			  } catch (IOException io) {
				System.out.println(io.getMessage());
			  } catch (JDOMException jdomex) {
			  System.out.println(jdomex.getMessage());
		  }
		  updateRooms(player, inv, rooms, WIDTH, HEIGHT);
	}
	public void updateRoomAndPlayerStates(Player player, Room[][] rooms, int WIDTH, int HEIGHT) {
		try {
			Element mud1 = new Element("mudState");
			Document doc = new Document(mud1);
			Set<Room> itemKeys = ItemSave.items.keySet();
		    Iterator<Room> i = itemKeys.iterator();
			while(i.hasNext())
			{
				Room curRoom = i.next();
	            Element room = new Element("roomname").setText(curRoom.getName());
	            ArrayList<Item> items = ItemSave.items.get(curRoom);
	            for(int j=0; j<items.size(); j++)
	            {
	            	Item curItem = items.get(j);
	            	Element curItemElement = new Element("item").setText(curItem.getName());
	            	room.addContent(curItemElement);
	            }
                doc.getRootElement().addContent(room);
			}  
            Element inv = new Element("inventory").setText("playerOne");
            for(int j = 0; j<InventorySave.inventory.size(); j++) {
            	Item curItem = InventorySave.inventory.get(j);
            	Element curItemElement = new Element("item").setText(curItem.getName());
            	inv.addContent(curItemElement);
            }
            doc.getRootElement().addContent(inv);
            Element pl = new Element("player").setText("playerOne");
           	Element xElement = new Element("x").setText(Integer.toString(player.getX()));
           	Element yElement = new Element("y").setText(Integer.toString(player.getY()));
           	pl.addContent(yElement);
           	pl.addContent(xElement);
           	doc.getRootElement().addContent(pl);
            
			// new XMLOutputter().output(doc, System.out);
			XMLOutputter xmlOutput = new XMLOutputter();
	
			// display nice nice
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter("mudState.xml"));
	
			System.out.println("Mud File Saved!");
		} catch (IOException io) {
			System.out.println(io.getMessage());
		}
	}
	public void writeRooms(Room[][] rooms, int WIDTH, int HEIGHT) {
		try {
			Element mud1 = new Element("mud");
			Document doc = new Document(mud1);
			// Initialize rooms (a 2D array)
	        for (int i = 0; i < WIDTH; i++) {
	            for (int j = 0; j < HEIGHT; j++) {
	                Element room = new Element("roomname").setText(rooms[i][j].getName());
                	Element posxElement = new Element("position_x").setText(Integer.toString(i));
                	Element posyElement = new Element("position_y").setText(Integer.toString(j));
                	Element descElement = new Element("desc").setText(rooms[i][j].getDescription());
                	Element playerposX = new Element("player_pos_x").setText(Integer.toString(mud.playerOne.getX()));
                	Element playerposY = new Element("player_pos_y").setText(Integer.toString(mud.playerOne.getY()));
                	room.addContent(playerposX);
                	room.addContent(playerposY);
                	room.addContent(posxElement);
                	room.addContent(posyElement);
                	room.addContent(descElement);
	                for(Item item:rooms[i][j].getItems()) {
	                	Element itemElement = new Element("item").setText(item.getName());
	                	room.addContent(itemElement);
	                }
	                doc.getRootElement().addContent(room);
	            }
	        }
	
			// new XMLOutputter().output(doc, System.out);
			XMLOutputter xmlOutput = new XMLOutputter();
	
			// display nice nice
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter("mud.xml"));
	
			System.out.println("Mud File Saved!");
		} catch (IOException io) {
			System.out.println(io.getMessage());
		}
	}
	public void testWrite()
	{
		
	}
}

