package mysql5;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.commons.database.IUStH;
import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.dao.FriendListDAO;
import com.aionemu.gameserver.dao.MySQL5DAOUtils;
import com.aionemu.gameserver.dao.PlayerDAO;
import com.aionemu.gameserver.model.gameobjects.player.Friend;
import com.aionemu.gameserver.model.gameobjects.player.FriendList;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ben
 */
public class MySQL5FriendListDAO extends FriendListDAO {

    private static final Logger log = LoggerFactory.getLogger(MySQL5FriendListDAO.class);
    public static final String LOAD_QUERY = "SELECT * FROM `friends` WHERE `player`=?";
    public static final String ADD_QUERY = "INSERT INTO `friends` (`player`,`friend`) VALUES (?, ?)";
    public static final String DEL_QUERY = "DELETE FROM friends WHERE player = ? AND friend = ?";

    @Override
    public FriendList load(final Player player) {
        final List<Friend> friends = new ArrayList<Friend>();
        Connection con = null;
        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(LOAD_QUERY);
            stmt.setInt(1, player.getObjectId());
            ResultSet rset = stmt.executeQuery();
            PlayerDAO dao = DAOManager.getDAO(PlayerDAO.class);
            while (rset.next()) {
                int objId = rset.getInt("friend");

                PlayerCommonData pcd = dao.loadPlayerCommonData(objId);
                if (pcd != null) {
                    Friend friend = new Friend(pcd);
                    friends.add(friend);
                }
            }
        } catch (Exception e) {
            log.error("Could not restore QuestStateList data for player: " + player.getObjectId() + " from DB: " + e.getMessage(), e);
        } finally {
            DatabaseFactory.close(con);
        }

        return new FriendList(player, friends);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addFriends(final Player player, final Player friend) {
        return DB.insertUpdate(ADD_QUERY, new IUStH() {
            @Override
            public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
                ps.setInt(1, player.getObjectId());
                ps.setInt(2, friend.getObjectId());
                ps.addBatch();

                ps.setInt(1, friend.getObjectId());
                ps.setInt(2, player.getObjectId());
                ps.addBatch();

                ps.executeBatch();
            }
        });

    }

    @Override
    public boolean delFriends(final int playerOid, final int friendOid) {
        return DB.insertUpdate(DEL_QUERY, new IUStH() {
            @Override
            public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
                ps.setInt(1, playerOid);
                ps.setInt(2, friendOid);
                ps.addBatch();

                ps.setInt(1, friendOid);
                ps.setInt(2, playerOid);
                ps.addBatch();

                ps.executeBatch();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(String s, int i, int i1) {
        return MySQL5DAOUtils.supports(s, i, i1);
    }
}
