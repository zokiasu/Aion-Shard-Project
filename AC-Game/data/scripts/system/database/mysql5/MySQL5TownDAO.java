package mysql5;

import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.gameserver.dao.MySQL5DAOUtils;
import com.aionemu.gameserver.dao.TownDAO;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.town.Town;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ViAl
 */
public class MySQL5TownDAO extends TownDAO {

    private static final Logger log = LoggerFactory.getLogger(MySQL5TownDAO.class);
    private static final String SELECT_QUERY = "SELECT * FROM `towns` WHERE `race` = ?";
    private static final String INSERT_QUERY = "INSERT INTO `towns`(`id`,`level`,`points`, `race`) VALUES (?,?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE `towns` SET `level` = ?, `points` = ?, `level_up_date` = ? WHERE `id` = ?";

    @Override
    public Map<Integer, Town> load(Race race) {
        Map<Integer, Town> towns = new HashMap<Integer, Town>();
        Connection conn = null;
        try {
            conn = DatabaseFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SELECT_QUERY);
            stmt.setString(1, race.toString());
            ResultSet rset = stmt.executeQuery();
            while (rset.next()) {
                int id = rset.getInt("id");
                int level = rset.getInt("level");
                int points = rset.getInt("points");
                Timestamp levelUpDate = rset.getTimestamp("level_up_date");
                Town town = new Town(id, level, points, race, levelUpDate);
                towns.put(town.getId(), town);
            }
            rset.close();
            stmt.close();
        } catch (SQLException e) {
            log.error("Can't load towns info. " + e);
        } finally {
            DatabaseFactory.close(conn);
        }
        return towns;
    }

    @Override
    public void store(Town town) {
        switch (town.getPersistentState()) {
            case NEW:
                insertTown(town);
                break;
            case UPDATE_REQUIRED:
                updateTown(town);
                break;
        }
    }

    private void insertTown(Town town) {
        Connection conn = null;
        try {
            conn = DatabaseFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(INSERT_QUERY);
            stmt.setInt(1, town.getId());
            stmt.setInt(2, town.getLevel());
            stmt.setInt(3, town.getPoints());
            stmt.setString(4, town.getRace().toString());
            stmt.executeUpdate();
            stmt.close();
            town.setPersistentState(PersistentState.UPDATED);
        } catch (SQLException e) {
            log.error("Can insert new town into database! Town id:" + town.getId() + ". " + e);
        } finally {
            DatabaseFactory.close(conn);
        }
    }

    private void updateTown(Town town) {
        Connection conn = null;
        try {
            conn = DatabaseFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(UPDATE_QUERY);
            stmt.setInt(1, town.getLevel());
            stmt.setInt(2, town.getPoints());
            stmt.setTimestamp(3, town.getLevelUpDate());
            stmt.setInt(4, town.getId());
            stmt.executeUpdate();
            stmt.close();
            town.setPersistentState(PersistentState.UPDATED);
        } catch (SQLException e) {
            log.error("Can insert new town into database! Town id:" + town.getId() + ". " + e);
        } finally {
            DatabaseFactory.close(conn);
        }
    }

    @Override
    public boolean supports(String databaseName, int majorVersion, int minorVersion) {
        return MySQL5DAOUtils.supports(databaseName, majorVersion, minorVersion);
    }
}
