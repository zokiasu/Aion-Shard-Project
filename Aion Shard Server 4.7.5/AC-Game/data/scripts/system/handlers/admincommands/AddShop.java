package admincommands;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.commons.database.IUStH;
import com.aionemu.commons.database.ParamReadStH;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.AdminService;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.ChatUtil;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.WorldMapType;

import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

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
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;


/**
 * @author Phantom, ATracer, Source
 */
public class AddShop extends AdminCommand {

    public AddShop() {
        super("addshop");
    }

    @Override
    public void execute(Player player, String... params) {

        String imagePath = "";
        Connection con = null;
        boolean checkImage = true;
        List<ClientItem> imageList;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM shop WHERE item_image_path = '' ");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String tmp = "str_" + DataManager.ITEM_DATA.getItemTemplate(rs.getInt("item_id")).getNamedesc() + "_desc";

                if(checkImage) {
                    imageList = RecupXmlIcon("./data/static_data/client_info/client_items_misc.xml");
                    for (int i = 0; i < imageList.size(); i++) {
                        if (DataManager.ITEM_DATA.getItemTemplate(rs.getInt("item_id")).getNamedesc().equalsIgnoreCase(imageList.get(i).getName())) {
                            imagePath = imageList.get(i).getIcon_name();
                            checkImage = false;
                        }
                    }
                }

                if(checkImage) {
                    imageList = RecupXmlIcon("./data/static_data/client_info/client_items_etc.xml");
                    for (int i = 0; i < imageList.size(); i++) {
                        if (DataManager.ITEM_DATA.getItemTemplate(rs.getInt("item_id")).getNamedesc().equalsIgnoreCase(imageList.get(i).getName())) {
                            imagePath = imageList.get(i).getIcon_name();
                            checkImage = false;
                        }
                    }
                }

                if(checkImage) {
                    imageList = RecupXmlIcon("./data/static_data/client_info/client_items_armor.xml");
                    for (int i = 0; i < imageList.size(); i++) {
                        if (DataManager.ITEM_DATA.getItemTemplate(rs.getInt("item_id")).getNamedesc().equalsIgnoreCase(imageList.get(i).getName())) {
                            imagePath = imageList.get(i).getIcon_name();
                            checkImage = false;
                        }
                    }
                }

                if(!checkImage) {
                    try {
                        DB.insertUpdate("UPDATE shop SET item_image_path=? WHERE item_id=?", new IUStH() {
                            @Override
                            public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
                                preparedStatement.setString(1, imagePath);
                                preparedStatement.setInt(2, rs.getInt("item_id"));
                                preparedStatement.execute();
                            }
                        });
                        PacketSendUtility.sendMessage(player, "Item successfully added");
                    } catch (Exception e) {
                        PacketSendUtility.sendMessage(player, "Item could not be added");
                    }
                }
            }
            stmt.close();
        } catch (Exception e) {
            log.error("Error getting pets for " + player.getObjectId(), e);
        } finally {
            DatabaseFactory.close(con);
        }
        /*else if (params.length < 3) {
            onFail(player, null);
            return;
        } else {
            int itemId = Integer.parseInt(params[0]);
            int itemPrice = Integer.parseInt(params[1]);
            int itemCategory = Integer.parseInt(params[2]);
            String itemName = "";
            String itemCategoryName = "";
            String itemDesc = "";
            String imagePath = "";

            switch (itemCategory){
                case 0 :
                    itemCategoryName = "Premium";
                    if(itemId == 188710074) {
                        itemDesc = "- Administrator's Boon 30 d\n " +
                                "- Title Top Gun 30 d\n" +
                                "- XP Obtained by quests and hunting (x3)\n" +
                                "- XP Obtained for jobs (x5)\n" +
                                "- Object obtained by extraction (x5)\n" +
                                "- +30% AP, Kinah, Drop, Reward Ordalie";
                    }
                    break;
                case 1 :
                    itemCategoryName = "Miscellaneous";
                    break;
                case 2 :
                    itemCategoryName = "Ticket";
                    break;
                case 3 :
                    itemCategoryName = "Candy";

                    if(itemId == 160010344 || itemId == 160010342){
                        itemDesc = "Take the form of an Ailu for 60m and increases Physical Attack% by 3, Accuracy% by 60, Atk Speed% by 4%, Casting Speed% by 4%, and Speed% by 4%.";
                    } else if (itemId == 160010345 || itemId == 160010343) {
                        itemDesc = "Take the form of an Ailu for 60m and increases Magic Boost% by 15, Magical Acc% by 60, Atk Speed% by 4%, Casting Speed% by 4%, and Speed% by 4%.";
                    } else if (itemId == 160010177 || itemId == 160010179 || itemId == 160010181
                            || itemId == 160010183 || itemId == 160010185 || itemId == 160010176
                            || itemId == 160010178 || itemId == 160010180 || itemId == 160010182 || itemId == 160010184) {
                        itemDesc = "You transform into an Inquin that can use skills for 60 minutes. Meanwhile, your Physical Attack increases by 3 points, Maximum HP increases by 220 points, Atk Speed increases by 3%, and Speed increases by 3%.";
                    } else if (itemId == 160010187 || itemId == 160010189 || itemId == 160010191
                            || itemId == 160010193 || itemId == 160010195 || itemId == 160010186
                            || itemId == 160010188 || itemId == 160010190 || itemId == 160010192 || itemId == 160010194) {
                        itemDesc = "You transform into an Inquin that can use skills for 60 minutes. Meanwhile, your Magic Boost increases by 15 points, Maximum HP increases by 220 points, Casting Speed increases by 3%, and Speed increases by 3%.";
                    }
                    break;
                case 4 :
                    itemCategoryName = "Emotion Card/Motion Card";
                    break;
                case 5 :
                    itemCategoryName = "Pet";
                    break;
                case 6 :
                    itemCategoryName = "Mount";
                    itemDesc = "The mounts are personalized and have all the same.\n Move Speed 13 |  Fly Speed 16 | Sprint Speed 15,2\n Cost Flight Point 6";
                    break;
                case 7 :
                    itemCategoryName = "Skin";
                    break;
                case 8 :
                    itemCategoryName = "Weapon Skin";
                    break;
                case 9 :
                    itemCategoryName = "Hat Skin";
                    break;
                case 10 :
                    itemCategoryName = "Skill Skin";
                    break;
                case 11 :
                    itemCategoryName = "Wings Skin";
                    break;
                case 12 :
                    itemCategoryName = "Housing";
                    break;
                default:
                    onFail(player, null);
                    return;
            }
            int itemCount = 1;
            if(params.length == 4) {
                itemCount = Integer.parseInt(params[3]);
            }


            if (DataManager.ITEM_DATA.getItemTemplate(itemId) == null) {
                PacketSendUtility.sendMessage(player, "Item id is incorrect: " + itemId);
                return;
            }

            boolean checkDesc = true;
            boolean checkImage = true;
            List<Strings> stringList;
            List<ClientItem> imageList;

            String tmp = "str_" + DataManager.ITEM_DATA.getItemTemplate(itemId).getNamedesc() + "_desc";

            if(checkDesc && itemDesc.equals("")) {
                stringList = RecupXmlDesc("./data/static_data/client_info/client_strings_item.xml");
                for (int i = 0; i < stringList.size(); i++) {
                    if (tmp.equalsIgnoreCase(stringList.get(i).getName())) {
                        itemDesc = stringList.get(i).getbody();
                        checkDesc = false;
                    }
                }
            }

            if(checkDesc && itemDesc.equals("")) {
                stringList = RecupXmlDesc("./data/static_data/client_info/client_strings_item2.xml");

                for (int i = 0; i < stringList.size(); i++) {
                    if (tmp.equalsIgnoreCase(stringList.get(i).getName())) {
                        itemDesc = stringList.get(i).getbody();
                        checkDesc = false;
                    }
                }
            }

            if(checkDesc && itemDesc.equals("")) {
                stringList = RecupXmlDesc("./data/static_data/client_info/client_strings_item3.xml");

                for (int i = 0; i < stringList.size(); i++) {
                    if (tmp.equalsIgnoreCase(stringList.get(i).getName())) {
                        itemDesc = stringList.get(i).getbody();
                        checkDesc = false;
                    }
                }
            }

            if(checkImage) {
                imageList = RecupXmlIcon("./data/static_data/client_info/client_items_misc.xml");
                for (int i = 0; i < imageList.size(); i++) {
                    if (DataManager.ITEM_DATA.getItemTemplate(itemId).getNamedesc().equalsIgnoreCase(imageList.get(i).getName())) {
                        imagePath = imageList.get(i).getIcon_name();
                        checkImage = false;
                    }
                }
            }

            if(checkImage) {
                imageList = RecupXmlIcon("./data/static_data/client_info/client_items_etc.xml");
                for (int i = 0; i < imageList.size(); i++) {
                    if (DataManager.ITEM_DATA.getItemTemplate(itemId).getNamedesc().equalsIgnoreCase(imageList.get(i).getName())) {
                        imagePath = imageList.get(i).getIcon_name();
                        checkImage = false;
                    }
                }
            }

            if(checkImage) {
                imageList = RecupXmlIcon("./data/static_data/client_info/client_items_armor.xml");
                for (int i = 0; i < imageList.size(); i++) {
                    if (DataManager.ITEM_DATA.getItemTemplate(itemId).getNamedesc().equalsIgnoreCase(imageList.get(i).getName())) {
                        imagePath = imageList.get(i).getIcon_name();
                        checkImage = false;
                    }
                }
            }

            if(DataManager.ITEM_DATA.getItemTemplate(itemId).getRace() == Race.ASMODIANS) {
                itemName = "[Asmo] " + DataManager.ITEM_DATA.getItemTemplate(itemId).getName();
            } else if(DataManager.ITEM_DATA.getItemTemplate(itemId).getRace() == Race.ELYOS) {
                itemName = "[Elyos] " + DataManager.ITEM_DATA.getItemTemplate(itemId).getName();
            } else {
                itemName = DataManager.ITEM_DATA.getItemTemplate(itemId).getName();
            }

            if(itemCount > 1) {
                itemName = itemName + " (x" + itemCount + ")";
            }

            PacketSendUtility.sendMessage(player, " ");
            PacketSendUtility.sendMessage(player, "You have added the following item to the shop :");
            PacketSendUtility.sendMessage(player, "ID : " + Integer.toString(itemId));
            PacketSendUtility.sendMessage(player, "Name : " + itemName);
            PacketSendUtility.sendMessage(player, "Description : " + itemDesc);
            PacketSendUtility.sendMessage(player, "Category : " + itemCategoryName);
            PacketSendUtility.sendMessage(player, "Price : " + Integer.toString(itemPrice));
            PacketSendUtility.sendMessage(player, "Count : " + Integer.toString(itemCount));

            addShopDb(itemId, itemName, itemDesc, itemCategoryName, itemCount, itemPrice, imagePath, player);
        }*/
    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "ID for Category :");
        PacketSendUtility.sendMessage(player, "0 : Premium / 1 : Miscellaneous / 2 : Ticket");
        PacketSendUtility.sendMessage(player, "3 : Candy / 4 : Emotion Card-Motion Card / 5 : Pet");
        PacketSendUtility.sendMessage(player, "6 : Mount / 7 : Skin / 8 : Weapon Skin / 9: Hat Skin");
        PacketSendUtility.sendMessage(player, "10: Skill Skin / 11 : Wings Skin / 12 : Housing");
        PacketSendUtility.sendMessage(player, "syntax //addshop <item Id> <item Price> <item Category> <item Count>");
    }

    public List<Strings> RecupXmlDesc(String test) {
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

    public List<ClientItem> RecupXmlIcon(String test) {
        List<ClientItem> stringList = new ArrayList<ClientItem>();
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
        NodeList nList = document.getElementsByTagName("client_item");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node node = nList.item(temp);
            ClientItem abc = new ClientItem();
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                //Print each employee's detail
                Element eElement = (Element) node;
                abc.setId(Integer.parseInt(eElement.getElementsByTagName("id").item(0).getTextContent()));
                abc.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
                try {
                    abc.setIcon_name(eElement.getElementsByTagName("icon_name").item(0).getTextContent());
                } catch (Exception e) {

                }
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

    public class ClientItem {
        private int id;
        private java.lang.String name;
        private java.lang.String icon_name;

        @Override
        public java.lang.String toString() {
            return "clientItem [id=" + id + ", name=" + name + ", icon_name=" + icon_name + "]";
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public java.lang.String getName() {
            return name;
        }

        public void setName(java.lang.String name) {
            this.name = name;
        }

        public java.lang.String getIcon_name() {
            return icon_name;
        }

        public void setIcon_name(java.lang.String icon_name) {
            this.icon_name = icon_name;
        }
    }

    public void addShopDb(final int item_id, final String item_name, final String item_desc, final String item_category,
                          final int item_count, final int item_price, final String item_image_path, Player player){
        try {

            DB.insertUpdate("INSERT INTO shop (" + "`item_id`,`item_name`, `item_desc`, `item_category`, " +
                    "`item_count`, `price`, `item_image_path`)" + " VALUES " + "(?, ?, ?, ?, ?, ?, ?)", new IUStH() {
                @Override
                public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, item_id);
                    ps.setString(2, item_name);
                    ps.setString(3, item_desc);
                    ps.setString(4, item_category);
                    ps.setInt(5, item_count);
                    ps.setInt(6, item_price);
                    ps.setString(7, item_image_path);
                    ps.execute();
                }
            });
            PacketSendUtility.sendMessage(player, "Item successfully added");
        } catch (Exception e) {
            PacketSendUtility.sendMessage(player, "Item could not be added");
        }
    }
}
