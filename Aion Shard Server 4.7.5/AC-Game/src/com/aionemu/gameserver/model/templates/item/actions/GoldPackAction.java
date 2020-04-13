package com.aionemu.gameserver.model.templates.item.actions;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.IUStH;
import com.aionemu.commons.database.ParamReadStH;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.services.CubeExpandService;
import com.aionemu.gameserver.services.WarehouseService;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GoldPackAction")
public class GoldPackAction extends AbstractItemAction {

    @Override
    public boolean canAct(Player player, Item parentItem, Item targetItem) {
        return true;
    }

    @Override
    public void act(Player player, Item parentItem, Item targetItem) {
        if (!player.getInventory().decreaseByObjectId(parentItem.getObjectId(), 1)) {
            return;
        }
        ItemTemplate itemTemplate = parentItem.getItemTemplate();
        PacketSendUtility.sendMessage(player, "Ah que coucou!");
        ItemService.addItem(player, 164002225, 1);
        ItemService.addItem(player, 169610093, 1);

        try {
            final Timestamp deletionDate = new Timestamp(System.currentTimeMillis());
            final Player player1 = player;
            Calendar cal = Calendar.getInstance();
            cal.setTime(deletionDate);
            cal.add(Calendar.DAY_OF_WEEK, 30);
            deletionDate.setTime(cal.getTime().getTime());

            DB.insertUpdate("UPDATE account_data set membership = ? where name = ?", new IUStH() {
                @Override
                public void handleInsertUpdate(PreparedStatement preparedStatement) throws SQLException {
                    preparedStatement.setInt(1, 1);
                    preparedStatement.setString(2, player1.getAcountName());
                    preparedStatement.execute();
                }
            });

            DB.insertUpdate("UPDATE account_data set expire = ? where name = ?", new IUStH() {
                @Override
                public void handleInsertUpdate(PreparedStatement preparedStatement) throws SQLException {
                    preparedStatement.setTimestamp(1, deletionDate);
                    preparedStatement.setString(2, player1.getAcountName());
                    preparedStatement.execute();
                }
            });

        } catch (Exception e) {
            PacketSendUtility.sendMessage(player, "C'est pas Ok!");
            return;
        }

        PacketSendUtility.broadcastPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), parentItem.getObjectId(), itemTemplate.getTemplateId()), true);
    }
}
