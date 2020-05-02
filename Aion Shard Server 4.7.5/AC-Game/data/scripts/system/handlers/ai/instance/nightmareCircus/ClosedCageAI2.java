package ai.instance.nightmareCircus;

import ai.NoActionAI2;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;

@AIName("closed_cage")
public class ClosedCageAI2 extends NoActionAI2 {

    public void playerSkillUse(Player player, int skillId) {
        int skill;
        Npc npc = getPosition().getWorldMapInstance().getNpc(831573);
        if (npc == null) {
            return;
        }
        switch (skillId) {
            case 21327:
                skill = 21365;
                break;
            case 21328:
                skill = 21364;
                break;
            default:
                return;
        }
        if (isInRange(player, 10)) {
            AI2Actions.targetCreature(((NpcAI2) npc.getAi2()), npc);
            AI2Actions.useSkill(((NpcAI2) npc.getAi2()), skill);
            //((NpcAI2) npc.getAi2()).playerSkillUse(player, skillId);
        }
    }
}
