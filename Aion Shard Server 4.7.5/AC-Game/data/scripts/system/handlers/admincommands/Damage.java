package admincommands;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Source
 */
public class Damage extends AdminCommand {

    public Damage() {
        super("damage");
    }

    @Override
    public void execute(Player admin, String... params) {
        if (params.length > 1) {
            onFail(admin, null);
        }

        VisibleObject target = admin.getTarget();
        if (target == null) {
            PacketSendUtility.sendMessage(admin, "No target selected");
        } else if (target instanceof Creature) {
            Creature creature = (Creature) target;
            int dmg;
            try {
                String percent = params[0];
                Pattern damage = Pattern.compile("([^%]+)%");
                Matcher result = damage.matcher(percent);

                if (result.find()) {
                    dmg = Integer.parseInt(result.group(1));

                    if (dmg < 100) {
                        creature.getController().onAttack(admin, (int) (dmg / 100f * creature.getLifeStats().getMaxHp()), true);
                    } else {
                        creature.getController().onAttack(admin, creature.getLifeStats().getMaxHp() + 1, true);
                    }
                } else {
                    creature.getController().onAttack(admin, Integer.parseInt(params[0]), true);
                }
            } catch (Exception ex) {
                onFail(admin, null);
            }
        }
    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "syntax //damage <dmg | dmg%>"
                + "\n<dmg> must be a number.");
    }
}
