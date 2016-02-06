import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.JDOMException;

public class Save {
	public void readRooms(Room[][] rooms, int WIDTH, int HEIGHT)	{
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
					   rooms[posx][posy].items.add(new Item(itemNode.getText()));	
				   }   
				}

			  } catch (IOException io) {
				System.out.println(io.getMessage());
			  } catch (JDOMException jdomex) {
				System.out.println(jdomex.getMessage());
			  }
	}
	public void writeRooms(Room[][] rooms, int WIDTH, int HEIGHT) {
		try {
			Element mud = new Element("mud");
			Document doc = new Document(mud);
			// Initialize rooms (a 2D array)
	        for (int i = 0; i < WIDTH; i++) {
	            for (int j = 0; j < HEIGHT; j++) {
	                Element room = new Element("roomname").setText(rooms[i][j].getName());
                	Element posxElement = new Element("position_x").setText(Integer.toString(i));
                	Element posyElement = new Element("position_y").setText(Integer.toString(j));
                	Element descElement = new Element("desc").setText(rooms[i][j].getDescription());
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
		try {

			Element company = new Element("company");
			Document doc = new Document(company);
			//doc.setRootElement(company);

			Element staff = new Element("staff");
			staff.setAttribute(new Attribute("id", "1"));
			staff.addContent(new Element("firstname").setText("yong"));
			staff.addContent(new Element("lastname").setText("mook kim"));
			staff.addContent(new Element("nickname").setText("mkyong"));
			staff.addContent(new Element("salary").setText("199999"));

			doc.getRootElement().addContent(staff);

			Element staff2 = new Element("staff");
			staff2.setAttribute(new Attribute("id", "2"));
			staff2.addContent(new Element("firstname").setText("low"));
			staff2.addContent(new Element("lastname").setText("yin fong"));
			staff2.addContent(new Element("nickname").setText("fong fong"));
			staff2.addContent(new Element("salary").setText("188888"));

			doc.getRootElement().addContent(staff2);

			// new XMLOutputter().output(doc, System.out);
			XMLOutputter xmlOutput = new XMLOutputter();

			// display nice nice
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter("e:\\file.xml"));

			System.out.println("File Saved!");
		  } catch (IOException io) {
			System.out.println(io.getMessage());
		  }
		}
	}

