package ai.worlds.danaria;

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

@AIName("pradethmercenaryasmodians")
public class PradethMercenaryAsmodiansAI2 extends NpcAI2
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
			spawn(273096, 2628.9766f, 2791.371f, 253.86087f, (byte) 61);  //Asmodians Elite Stalwart.
            spawn(273096, 2611.1191f, 2772.6743f, 253.83484f, (byte) 30);  //Asmodians Elite Stalwart.
            spawn(273096, 2689.5513f, 2598.0747f, 253.83484f, (byte) 119);  //Asmodians Elite Stalwart.
            spawn(273096, 2705.4612f, 2614.9175f, 253.83865f, (byte) 90);  //Asmodians Elite Stalwart.
			spawn(273126, 2575.6929f, 2610.3667f, 253.65619f, (byte) 75); //Asmodians Defense Archmagus.
            spawn(273126, 2581.2778f, 2605.1582f, 253.65619f, (byte) 75); //Asmodians Defense Archmagus.
            spawn(273126, 2739.7441f, 2775.4817f, 253.72882f, (byte) 15); //Asmodians Defense Archmagus.
            spawn(273126, 2734.1306f, 2780.7148f, 253.72882f, (byte) 15); //Asmodians Defense Archmagus.
			spawn(273111, 2717.3767f, 2738.2834f, 265.75882f, (byte) 33); //Asmodians Defense Elite Sniper.
            spawn(273111, 2698.919f, 2755.3105f, 265.75632f, (byte) 1); //Asmodians Defense Elite Sniper.
            spawn(273111, 2599.8613f, 2648.6938f, 265.75552f, (byte) 92); //Asmodians Defense Elite Sniper.
            spawn(273111, 2639.0042f, 2612.565f, 265.77414f, (byte) 59); //Asmodians Defense Elite Sniper.
		    announceMercenaries();
		} else if (dialogId == 10001) { //Blood Mark.
			spawn(273111, 2667.2795f, 2592.56f, 268.32513f, (byte) 106); //Asmodians Defense Elite Sniper.
            spawn(273111, 2629.799f, 2770.936f, 265.8121f, (byte) 45); //Asmodians Defense Elite Sniper.
            spawn(273111, 2579.392f, 2672.4946f, 265.81476f, (byte) 75); //Asmodians Defense Elite Sniper.
            spawn(273111, 2621.6184f, 2632.5005f, 265.81476f, (byte) 76); //Asmodians Defense Elite Sniper.
            spawn(273111, 2736.454f, 2669.858f, 265.80414f, (byte) 106); //Asmodians Defense Elite Sniper.
            spawn(273111, 2775.1433f, 2654.893f, 262.82587f, (byte) 106); //Asmodians Defense Elite Sniper.
            spawn(273111, 2684.618f, 2557.8318f, 262.82587f, (byte) 106); //Asmodians Defense Elite Sniper.
            spawn(273111, 2627.3955f, 2578.351f, 262.82587f, (byte) 76); //Asmodians Defense Elite Sniper.
            spawn(273111, 2522.8257f, 2675.4504f, 262.82587f, (byte) 76); //Asmodians Defense Elite Sniper.
            spawn(273111, 2522.1343f, 2711.6804f, 262.82587f, (byte) 47); //Asmodians Defense Elite Sniper.
            spawn(273111, 2541.5078f, 2731.549f, 259.10098f, (byte) 41); //Asmodians Defense Elite Sniper.
            spawn(273111, 2632.497f, 2829.7847f, 262.82587f, (byte) 46); //Asmodians Defense Elite Sniper.
            spawn(273111, 2793.8599f, 2712.1128f, 262.82587f, (byte) 15); //Asmodians Defense Elite Sniper.
		    announceMercenaries();
		} else if (dialogId == 10002) { //Blood Mark.
			spawn(273316, 2596.982f, 2737.708f, 266.19125f, (byte) 46); //Pradeth Empty Etched Bombard.
            spawn(273316, 2611.173f, 2752.241f, 266.19125f, (byte) 45); //Pradeth Empty Etched Bombard.
            spawn(273316, 2705.3672f, 2636.0967f, 266.20126f, (byte) 106); //Pradeth Empty Etched Bombard.
            spawn(273316, 2718.9968f, 2651.4675f, 266.20126f, (byte) 105); //Pradeth Empty Etched Bombard.
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