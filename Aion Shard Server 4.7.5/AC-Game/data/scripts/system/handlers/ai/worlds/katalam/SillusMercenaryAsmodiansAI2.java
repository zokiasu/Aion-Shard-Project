package ai.worlds.katalam;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.knownlist.Visitor;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/**
 * 
 * @author Ranastic
 *
 */

@AIName("sillusmercenaryasmodians")
public class SillusMercenaryAsmodiansAI2 extends NpcAI2
{
    @Override
    protected void handleDialogStart(Player player) {
        if (player.getInventory().getItemCountByItemId(186000236) > 0) { //Blood Mark.
            super.handleDialogStart(player);
        } else {
            PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 27));
        }
    }
	
	@Override
    public boolean onDialogSelect(Player player, int dialogId, int questId, int extendedRewardIndex) {
		if (dialogId == 10000) { //Blood Mark.
			spawn(272150, 2160.2812f, 1874.4827f, 311.00818f, (byte) 0); //Asmodians Offense Elite Sniper.
		    spawn(272150, 2161.8381f, 1879.8955f, 311.00815f, (byte) 0); //Asmodians Offense Elite Sniper.
			spawn(272150, 2161.9878f, 1876.8461f, 311.00815f, (byte) 0); //Asmodians Offense Elite Sniper.
			spawn(272150, 2117.2002f, 1874.6698f, 332.05746f, (byte) 105); //Asmodians Offense Elite Sniper.
			spawn(272150, 2103.5344f, 1859.9352f, 332.03583f, (byte) 105); //Asmodians Offense Elite Sniper.
			spawn(272150, 1999.8871f, 1724.2051f, 318.26813f, (byte) 105); //Asmodians Offense Elite Sniper.
			announceMercenaries();
		} else if (dialogId == 10001) { //Blood Mark.
			spawn(272160, 2310.5618f, 1891.5841f, 297.4462f, (byte) 0); //Asmodians Offense Archpriest.
			spawn(272160, 2248.4426f, 1856.122f, 279.89194f, (byte) 15); //Asmodians Offense Archpriest.
			spawn(272160, 2199.7979f, 1874.4004f, 290.16302f, (byte) 105); //Asmodians Offense Archpriest.
			spawn(272160, 2009.3325f, 1787.3176f, 316.03876f, (byte) 90); //Asmodians Offense Archpriest.
			spawn(272160, 2024.449f, 1726.4705f, 308.93158f, (byte) 90); //Asmodians Offense Archpriest.
			spawn(272160, 2012.5654f, 1689.2875f, 299.40112f, (byte) 63); //Asmodians Offense Archpriest.
			announceMercenaries();
		} else if (dialogId == 10002) { //Blood Mark.
			spawn(272116, 1998.9874f, 1790.6902f, 317.125f, (byte) 95); //Asmodians Defense Elite Cannoneer.
            spawn(272116, 2011.273f, 1793.6339f, 317.29504f, (byte) 95); //Asmodians Defense Elite Cannoneer.
            spawn(272116, 2105.16f, 1863.1708f, 317.5952f, (byte) 107); //Asmodians Defense Elite Cannoneer.
            spawn(272116, 2113.8284f, 1872.8129f, 317.79007f, (byte) 107); //Asmodians Defense Elite Cannoneer.
			announceMercenaries();
		}
        return true;
    }
	
	private void announceMercenaries() {
		World.getInstance().doOnAllPlayers(new Visitor<Player>() {
			@Override
			public void visit(Player player) {
				PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1401837));
			}
		});
	}
}