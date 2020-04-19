package admincommands;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.AdminService;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Phantom, ATracer, Source
 */
public class AddShop extends AdminCommand {

    public AddShop() {
        super("addshop");
    }

    @Override
    public void execute(Player player, String... params) {

        if (params.length < 2) {
            onFail(player, null);
            return;
        }

        int itemId = Integer.parseInt(params[0]);
        int itemPrice = Integer.parseInt(params[1]);
        int itemCount = 1;
        if(params.length == 3) {
            itemCount = Integer.parseInt(params[2]);
        }


        if (DataManager.ITEM_DATA.getItemTemplate(itemId) == null) {
            PacketSendUtility.sendMessage(player, "Item id is incorrect: " + itemId);
            return;
        }

        boolean checkDesc = true;
        List<Strings> stringList = RecupXml("./data/static_data/client_strings_item.xml");

        String tmp = "str_" + DataManager.ITEM_DATA.getItemTemplate(itemId).getNamedesc() + "_desc";

        for(int i = 0; i < stringList.size(); i++){
            if(tmp.equalsIgnoreCase(stringList.get(i).getName())){
                PacketSendUtility.sendMessage(player, Integer.toString(itemId));
                PacketSendUtility.sendMessage(player, DataManager.ITEM_DATA.getItemTemplate(itemId).getName());
                PacketSendUtility.sendMessage(player, stringList.get(i).getbody());
                PacketSendUtility.sendMessage(player, "Price : " + Integer.toString(itemPrice));
                PacketSendUtility.sendMessage(player, "Count : " + Integer.toString(itemCount));
                addShopDb(itemId, DataManager.ITEM_DATA.getItemTemplate(itemId).getName(), stringList.get(i).getbody(), itemCount, itemPrice);
                checkDesc = false;
            }
        }

        if(checkDesc) {
            stringList = RecupXml("./data/static_data/client_strings_item2.xml");

            for (int i = 0; i < stringList.size(); i++) {
                if (tmp.equalsIgnoreCase(stringList.get(i).getName())) {
                    PacketSendUtility.sendMessage(player, Integer.toString(itemId));
                    PacketSendUtility.sendMessage(player, DataManager.ITEM_DATA.getItemTemplate(itemId).getName());
                    PacketSendUtility.sendMessage(player, stringList.get(i).getbody());
                    PacketSendUtility.sendMessage(player, "Price : " + Integer.toString(itemPrice));
                    PacketSendUtility.sendMessage(player, "Count : " + Integer.toString(itemCount));
                    addShopDb(itemId, DataManager.ITEM_DATA.getItemTemplate(itemId).getName(), stringList.get(i).getbody(), itemCount, itemPrice);
                    checkDesc = false;
                }
            }
        }

        if(checkDesc) {
            stringList = RecupXml("./data/static_data/client_strings_item3.xml");

            for (int i = 0; i < stringList.size(); i++) {
                if (tmp.equalsIgnoreCase(stringList.get(i).getName())) {
                    PacketSendUtility.sendMessage(player, Integer.toString(itemId));
                    PacketSendUtility.sendMessage(player, DataManager.ITEM_DATA.getItemTemplate(itemId).getName());
                    PacketSendUtility.sendMessage(player, stringList.get(i).getbody());
                    PacketSendUtility.sendMessage(player, "Price : " + Integer.toString(itemPrice));
                    PacketSendUtility.sendMessage(player, "Count : " + Integer.toString(itemCount));
                    addShopDb(itemId, DataManager.ITEM_DATA.getItemTemplate(itemId).getName(), stringList.get(i).getbody(), itemCount, itemPrice);
                    checkDesc = false;
                }
            }
        }

    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "syntax //addShop <item Id> <item Price> <item Count>");
    }

    public List<Strings> RecupXml(String test) {
        List<Strings> stringList = new ArrayList<Strings>();
        //Get Docuemnt Builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        //Build Document
        Document document = null;
        try {
            document = builder.parse(new File(test));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Normalize the XML Structure; It's just too important !!
        document.getDocumentElement().normalize();

        //Here comes the root node
        Element root = document.getDocumentElement();

        //Get all employees
        NodeList nList = document.getElementsByTagName("string");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node node = nList.item(temp);
            Strings abc = new Strings();
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                //Print each employee's detail
                Element eElement = (Element) node;
                abc.setId(Integer.parseInt(eElement.getElementsByTagName("id").item(0).getTextContent()));
                abc.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
                abc.setbody(eElement.getElementsByTagName("body").item(0).getTextContent());
            }
            stringList.add(abc);
        }

        return stringList;
    }

    public class Strings {
        private int Id;
        private java.lang.String name;
        private java.lang.String body;

        @Override
        public java.lang.String toString() {
            return "Employee [id=" + Id + ", name=" + name + ", body=" + body + "]";
        }

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        public java.lang.String getName() {
            return name;
        }

        public void setName(java.lang.String name) {
            this.name = name;
        }

        public java.lang.String getbody() {
            return body;
        }

        public void setbody(java.lang.String body) {
            this.body = body;
        }
    }

    public void addShopDb(int item_id, String item_name, String item_desc, int item_count, int item_price){
        try {
            final int item_id = item_id;
            final String item_name = item_name;
            final String item_desc = item_desc;
            final int item_count = item_count;
            final int item_price = item_price;

            DB.insertUpdate("INSERT INTO shop (" + "`item_id`,`item_name`, `item_desc`, `item_count`, `price`)" + " VALUES " + "(?, ?, ?, ?, ?)", new IUStH() {
                @Override
                public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, item_id);
                    ps.setString(2, item_name);
                    ps.setString(3, item_desc);
                    ps.setInt(4, item_count);
                    ps.setInt(5, item_price);
                    ps.execute();
                }
            });
            PacketSendUtility.sendMessage(player, "Item successfully added");
        } catch (Exception e) {
            PacketSendUtility.sendMessage(player, "Item could not be added");
        }
    }
}
