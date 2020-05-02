package admincommands;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.stats.calc.StatOwner;
import com.aionemu.gameserver.model.stats.calc.functions.IStatFunction;
import com.aionemu.gameserver.model.stats.calc.functions.StatFunctionProxy;
import com.aionemu.gameserver.model.stats.container.StatEnum;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TreeSet;

/**
 * @author MrPoke
 */
public class Stat extends AdminCommand {

    private static final Logger log = LoggerFactory.getLogger(Stat.class);

    /**
     * @param alias
     */
    public Stat() {
        super("stat");
    }

    @Override
    public void execute(Player admin, String... params) {
        if (params.length >= 1) {
            VisibleObject target = admin.getTarget();
            if (target == null) {
                PacketSendUtility.sendMessage(admin, "No target selected");
                return;
            }
            if (target instanceof Creature) {
                Creature creature = (Creature) target;

                TreeSet<IStatFunction> stats = creature.getGameStats().getStatsByStatEnum(StatEnum.valueOf(params[0]));

                if (params.length == 1) {
                    for (IStatFunction stat : stats) {
                        PacketSendUtility.sendMessage(admin, stat.toString());
                    }
                } else if ("details".equals(params[1])) {
                    for (IStatFunction stat : stats) {
                        String details = collectDetails(stat);
                        PacketSendUtility.sendMessage(admin, details);
                        log.info(details);
                    }
                }
            }
        }
    }

    private String collectDetails(IStatFunction stat) {
        StringBuffer sb = new StringBuffer();
        sb.append(stat.toString() + "\n");
        if (stat instanceof StatFunctionProxy) {
            StatFunctionProxy proxy = (StatFunctionProxy) stat;
            sb.append(" -- " + proxy.getProxiedFunction().toString());
        }
        StatOwner owner = stat.getOwner();
        if (owner instanceof Effect) {
            Effect effect = (Effect) owner;
            sb.append("\n -- skillId: " + effect.getSkillId());
            sb.append("\n -- skillName: " + effect.getSkillName());
        }
        return sb.toString();
    }

    @Override
    public void onFail(Player player, String message) {
        // TODO Auto-generated method stub
    }
}
