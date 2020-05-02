package mysql5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.commons.database.IUStH;
import com.aionemu.commons.database.ParamReadStH;
import com.aionemu.gameserver.dao.MySQL5DAOUtils;
import com.aionemu.gameserver.dao.PlayerLoginRewardDAO;
import com.aionemu.gameserver.model.gameobjects.player.LoginReward;

/**
*
* @author Ranastic
*/
public class MySQL5PlayerLoginRewardDAO extends PlayerLoginRewardDAO {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(MySQL5PlayerLoginRewardDAO.class);

	public static final String ADD_QUERY = "INSERT INTO `player_login_reward` (`player_id`, `activated_id`, `login_count`, `next_login_count`) VALUES (?,?,?,?)";
	public static final String SELECT_QUERY = "SELECT `activated_id`, `login_count`, `next_login_count` FROM `player_login_reward` WHERE `player_id`=?";
	public static final String DELETE_QUERY = "DELETE FROM `player_login_reward` WHERE `player_id`=? AND `activated_id`=? AND `login_count`=? AND `next_login_count`=?";

	@Override
	public boolean supports(String databaseName, int majorVersion, int minorVersion) {
		return MySQL5DAOUtils.supports(databaseName, majorVersion, minorVersion);
	}

	@Override
	public LoginReward load(final int playerId) {
		final HashSet<Integer> loginRewardList = new HashSet<Integer>();
		DB.select(SELECT_QUERY, new ParamReadStH() {

			@Override
			public void setParams(PreparedStatement ps) throws SQLException {
				ps.setInt(1, playerId);
			}

			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					loginRewardList.add(rs.getInt("activated_id"));
					loginRewardList.add(rs.getInt("login_count"));
					loginRewardList.add(rs.getInt("next_login_count"));
				}
			}
		});
		return new LoginReward(loginRewardList);
	}
	
	@Override
	public boolean addLoginReward(final int playerId, final int activated_id, final int login_count, final Timestamp next_login_count) {
		return DB.insertUpdate(ADD_QUERY, new IUStH() {

			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
				ps.setInt(1, playerId);
				ps.setInt(2, activated_id);
				ps.setInt(3, login_count);
				ps.setTimestamp(4, next_login_count);
				ps.execute();
			}
		});
	}

	@Override
	public boolean delLoginReward(final int playerId, final int activated_id, final int login_count, final Timestamp next_login_count) {
		return DB.insertUpdate(DELETE_QUERY, new IUStH() {

			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
				ps.setInt(1, playerId);
				ps.setInt(2, activated_id);
				ps.setInt(3, login_count);
				ps.setTimestamp(4, next_login_count);
				ps.execute();
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getLoginCountByObjAndActivatedEventId(final int obj, final int activated_id) {
		Connection con = null;
		int loginCount = 0;
		try {
			con = DatabaseFactory.getConnection();
			PreparedStatement s = con.prepareStatement("SELECT `login_count` FROM `player_login_reward` WHERE `player_id` = ? AND `activated_id` = ?");
			s.setInt(1, obj);
			s.setInt(2, activated_id);
			ResultSet rs = s.executeQuery();
			rs.next();
			loginCount = rs.getInt("login_count");
			rs.close();
			s.close();
		}
		catch (@SuppressWarnings("unused") Exception e) {
			return 0;
		}
		finally {
			DatabaseFactory.close(con);
		}
		return loginCount;
	}

	@Override
	public Timestamp getNextLoginCountbyObjAndActivatedEventId(final int obj, final int activated_id) {
		Connection con = null;
		Timestamp nextLoginCount;
		try {
			con = DatabaseFactory.getConnection();
			PreparedStatement s = con.prepareStatement("SELECT `next_login_count` FROM `player_login_reward` WHERE `player_id` = ? AND `activated_id` = ?");
			s.setInt(1, obj);
			s.setInt(2, activated_id);
			ResultSet rs = s.executeQuery();
			rs.next();
			nextLoginCount = rs.getTimestamp("next_login_count");
			rs.close();
			s.close();
		}
		catch (@SuppressWarnings("unused") Exception e) {
			return null;
		}
		finally {
			DatabaseFactory.close(con);
		}
		return nextLoginCount;
	}

}
