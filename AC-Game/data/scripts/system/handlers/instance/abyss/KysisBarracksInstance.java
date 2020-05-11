package instance.abyss;

import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.gameobjects.Npc;

/**
 * @author Everlight
 */
@InstanceID(301280000)
public class KysisBarracksInstance extends GeneralInstanceHandler {

    private boolean rewarded = false;
    private int numberBossDie = 0;

    @Override
    public void onDie(Npc npc) {
        switch (npc.getNpcId()) {
            case 233666:
            case 233667:
            case 233668:
            case 233669:
            case 233644:
            case 233710:
            case 233642:
            case 233687:
            case 233655:
            case 233656:
            case 233674:
                numberBossDie++;
                if(numberBossDie == 4){
                    spawn(233676, 526, 845, 199.7f, (byte) 27);
                    numberBossDie = 0;
                }
                break;
            case 233676: // Kysis Duke lvl.65
            case 233675: // weakened boss lvl.65
                spawnChests(npc);
                break;
            case 215414: // artifact spawns weakened boss
                Npc boss = getNpc(233676); // Kysis Duke
                if (boss != null && !boss.getLifeStats().isAlreadyDead()) {
                    spawn(233675, boss.getX(), boss.getY(), boss.getZ(), boss.getHeading());
                    boss.getController().onDelete();
                }
                break;
        }
    }

    private void spawnChests(Npc npc) {
        if (!rewarded) {
            rewarded = true; //safety mechanism
            if (npc.getAi2().getRemainigTime() != 0) {
                long rtime = (600000 - npc.getAi2().getRemainigTime()) / 30000;
                spawn(702292, 478.7917f, 815.5538f, 199.70894f, (byte) 9);
                if (rtime > 1) {
                    spawn(702292, 471, 853, 199f, (byte) 117);
                }
                if (rtime > 2) {
                    spawn(702292, 477, 873, 199.7f, (byte) 109);
                }
                if (rtime > 3) {
                    spawn(702292, 507, 899, 199.7f, (byte) 96);
                }
                if (rtime > 4) {
                    spawn(702293, 548, 889, 199.7f, (byte) 83);
                }
                if (rtime > 5) {
                    spawn(702293, 565, 889, 199.7f, (byte) 76);
                }
                if (rtime > 6) {
                    spawn(702293, 585, 855, 199.7f, (byte) 65);
                }
                if (rtime > 7) {
                    spawn(702293, 578, 874, 199.7f, (byte) 9);
                }
                if (rtime > 8) {
                    spawn(702294, 528, 903, 199.7f, (byte) 30);
                }
                if (rtime > 9) {
                    spawn(702294, 490, 899, 199.7f, (byte) 44);
                }
                if (rtime > 10) {
                    spawn(702294, 470, 834, 199.7f, (byte) 63);
                }
                if (rtime > 11 && npc.getNpcId() == 233676) {
                    spawn(702295, 577.5694f, 836.9684f, 199.7f, (byte) 120);
                }
            }
        }
    }
}
