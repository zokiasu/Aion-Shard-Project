package playercommands;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.IUStH;
import com.aionemu.commons.database.ParamReadStH;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Mathew
 * @edit Ever'
 */
public class cmd_onlineshop extends PlayerCommand {

    private int nbitem = 0;

    public cmd_onlineshop() {
        super("onlineshop");
    }

    @Override
    public void execute(final Player player, String... params) {
        try {
            DB.select("SELECT object_id, item_id, item_count FROM player_shop WHERE player_id = ?", new ParamReadStH() {
                @Override
                public void setParams(PreparedStatement stmt) throws SQLException {
                    stmt.setInt(1, player.getObjectId());
                }

                @Override
                public void handleRead(ResultSet rset) throws SQLException {
                    while (rset.next()) {
                        final int id = rset.getInt("object_id");
                        int itemId = rset.getInt("item_id");
                        int item_count = rset.getInt("item_count");

                        if (ItemService.addItem(player, itemId, item_count) == 0) {
                            DB.insertUpdate("DELETE FROM player_shop WHERE object_id = ?", new IUStH() {
                                @Override
                                public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
                                    ps.setInt(1, id);
                                    ps.execute();
                                }
                            });
                        } else {
                            PacketSendUtility.sendMessage(player, "Your inventory is full. Make free slot and repeat the command.");
                            return;
                        }
                        nbitem++;
                    }
                }
            });
            if (nbitem == 0) {
                PacketSendUtility.sendMessage(player, "No item in queue");
            }
        } catch (Exception ex) {
            PacketSendUtility.sendMessage(player, "Only numbers are allowed");
        }
    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "syntax: .onlineshop");
    }
}
