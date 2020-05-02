package com.aionemu.gameserver.network.aion.clientpackets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Summon;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;

/**
 * @author ATracer, KID
 */
public class CM_SUMMON_CASTSPELL extends AionClientPacket {

    private static final Logger log = LoggerFactory.getLogger(CM_SUMMON_CASTSPELL.class);
    private int summonObjId;
    private int targetObjId;
    private int skillId;
    @SuppressWarnings("unused")
    private int skillLvl;
    @SuppressWarnings("unused")
    private float unk;

    public CM_SUMMON_CASTSPELL(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        summonObjId = readD();
        skillId = readH();
        skillLvl = readC();
        targetObjId = readD();
        unk = readF();
    }

    @Override
    protected void runImpl() {
        Player player = getConnection().getActivePlayer();

        long currentTime = System.currentTimeMillis();
        if (player.getNextSummonSkillUse() > currentTime) {
            return;
        }

        Summon summon = player.getSummon();
        if (summon == null) {
            log.warn("summon castspell without active summon on " + player.getName() + ".");
            return;
        }
        if (summon.getObjectId() != summonObjId) {
            log.warn("summon castspell from a different summon instance on " + player.getName() + ".");
            return;
        }

        Creature target = null;
        if (targetObjId != summon.getObjectId()) {
            VisibleObject obj = summon.getKnownList().getObject(targetObjId);
            if (obj instanceof Creature) {
                target = (Creature) obj;
            }
        } else {
            target = summon;
        }

        if (target != null) {
            player.setNextSummonSkillUse(currentTime + 1100);
            summon.getController().useSkill(skillId, target);
        } else {
            log.warn("summon castspell on a wrong target on " + player.getName());
        }
    }
}
