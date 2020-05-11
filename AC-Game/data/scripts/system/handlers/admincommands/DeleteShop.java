package admincommands;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.commons.database.IUStH;
import com.aionemu.commons.database.ParamReadStH;
import com.aionemu.gameserver.dataholders.DataManager;
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
public class DeleteShop extends AdminCommand {

    public DeleteShop() {
        super("deleteshop");
    }

    @Override
    public void execute(Player player, String... params) {

        if (params.length < 1) {
            onFail(player, null);
            return;
        }

        int itemId = Integer.parseInt(params[0]);

        if (DataManager.ITEM_DATA.getItemTemplate(itemId) == null) {
            PacketSendUtility.sendMessage(player, "Item id is incorrect: " + itemId);
            return;
        }

        deleteShopDb(itemId, player);

    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "syntax //deleteshop <item Id>");
    }

    public void deleteShopDb(final int item_id, Player player){
        try {
            DB.insertUpdate("DELETE FROM shop WHERE item_id = ?", new IUStH() {
                @Override
                public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, item_id);
                    ps.execute();
                }
            });
            PacketSendUtility.sendMessage(player, "Item successfully delete");
        } catch (Exception e) {
            PacketSendUtility.sendMessage(player, "Item could not be added");
        }
    }
}
