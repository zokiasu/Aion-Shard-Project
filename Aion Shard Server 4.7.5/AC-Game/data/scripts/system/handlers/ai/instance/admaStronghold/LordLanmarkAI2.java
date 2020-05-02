package ai.instance.admaStronghold;

import ai.AggressiveNpcAI2;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.ai2.manager.WalkManager;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;

/**
 * Lord Lanmark Boss-Script
 *
 * @author Antraxx
 */
@AIName("lordlanmark")
public class LordLanmarkAI2 extends AggressiveNpcAI2 {

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (!isAlreadyDead()) {
                    getOwner().getSpawn().setWalkerId("lordlanmark");
                    WalkManager.startWalking((NpcAI2) getOwner().getAi2());
                    PacketSendUtility.broadcastPacket(getOwner(), new SM_EMOTION(getOwner(), EmotionType.START_EMOTE2, 0, getOwner().getObjectId()));
                }
            }
        }, 1000);
    }
}
