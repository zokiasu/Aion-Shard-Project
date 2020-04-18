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
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Phantom, ATracer, Source
 */
public class AddShop extends AdminCommand {

    public AddShop() {
        super("addshop");
    }

    @Override
    public void execute(Player player, String... params) {

        if ((params.length < 0) || (params.length < 1)) {
            onFail(player, null);
            return;
        }

        int itemId = 0;
        long itemCount = 1;
        Player receiver;

        try {
            String item = params[0];
            // Some item links have space before Id
            if (item.equals("[item:")) {
                item = params[1];
                Pattern id = Pattern.compile("(\\d{9})");
                Matcher result = id.matcher(item);
                if (result.find()) {
                    itemId = Integer.parseInt(result.group(1));
                }

                if (params.length == 3) {
                    itemCount = Long.parseLong(params[2]);
                }
            } else {
                Pattern id = Pattern.compile("\\[item:(\\d{9})");
                Matcher result = id.matcher(item);

                if (result.find()) {
                    itemId = Integer.parseInt(result.group(1));
                } else {
                    itemId = Integer.parseInt(params[0]);
                }

                if (params.length == 2) {
                    itemCount = Long.parseLong(params[1]);
                }
            }
            receiver = player;
        } catch (NumberFormatException e) {
            receiver = World.getInstance().findPlayer(Util.convertName(params[0]));
            if (receiver == null) {
                PacketSendUtility.sendMessage(player, "Could not find a player by that name.");
                return;
            }

            try {
                String item = params[1];
                // Some item links have space before Id
                if (item.equals("[item:")) {
                    item = params[2];
                    Pattern id = Pattern.compile("(\\d{9})");
                    Matcher result = id.matcher(item);
                    if (result.find()) {
                        itemId = Integer.parseInt(result.group(1));
                    }

                    if (params.length == 4) {
                        itemCount = Long.parseLong(params[3]);
                    }
                } else {
                    Pattern id = Pattern.compile("\\[item:(\\d{9})");
                    Matcher result = id.matcher(item);

                    if (result.find()) {
                        itemId = Integer.parseInt(result.group(1));
                    } else {
                        itemId = Integer.parseInt(params[1]);
                    }

                    if (params.length == 3) {
                        itemCount = Long.parseLong(params[2]);
                    }
                }
            } catch (NumberFormatException ex) {
                PacketSendUtility.sendMessage(player, "You must give number to itemid.");
                return;
            } catch (Exception ex2) {
                PacketSendUtility.sendMessage(player, "Occurs an error.");
                return;
            }
        }

        if (DataManager.ITEM_DATA.getItemTemplate(itemId) == null) {
            PacketSendUtility.sendMessage(player, "Item id is incorrect: " + itemId);
            return;
        }

        if (!AdminService.getInstance().canOperate(player, receiver, itemId, "command //add")) {
            return;
        }

        long count = ItemService.addItem(receiver, itemId, itemCount);

        List<Strings> stringList = RecupXml("AutoShop/client_strings_item2.xml");

        String tmp = "STR_"+ DataManager.ITEM_DATA.getItemTemplate(itemId).getNamedesc();
        for(int i = 0; i < stringList.size(); i++){
            if(stringList.get(i).getName().equals(tmp)){
                PacketSendUtility.sendMessage(player, "Ah que coucou!");
            }
        }

        if (count == 0) {
            if (player != receiver) {
                PacketSendUtility.sendMessage(player, "You successfully gave " + itemCount + " x [item:"+ itemId + "] to " + receiver.getName() + ".");
                PacketSendUtility.sendMessage(receiver, "You successfully received " + itemCount + " x [item:"+ itemId + "] from " + player.getName() + ".");
            } else {
                PacketSendUtility.sendMessage(player, "You successfully received " + itemCount + " x [item:"+ itemId + "]");
            }
        } else {
            PacketSendUtility.sendMessage(player, "Item couldn't be added");
        }

    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "syntax //addShop <item Id>");
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
        System.out.println(root.getNodeName());

        //Get all employees
        NodeList nList = document.getElementsByTagName("string");
        System.out.println("============================");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node node = nList.item(temp);
            Strings abc = new Strings();
            System.out.println("");    //Just a separator
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
}
