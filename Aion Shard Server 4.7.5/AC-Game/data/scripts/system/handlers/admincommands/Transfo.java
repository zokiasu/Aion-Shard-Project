package admincommands;

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;

/**
 * @author Alex
 */
public class Transfo extends AdminCommand {

    public Transfo() {
        super("transfo");
    }

    enum RecipientType {

        ELYOS,
        ASMO,
        ALL,
        PLAYER;

        public boolean isAllowed(Race race) {
            switch (this) {
                case ELYOS:
                    return race == Race.ELYOS;
                case ASMO:
                    return race == Race.ASMODIANS;
                case ALL:
                    return race == Race.ELYOS || race == Race.ASMODIANS;
            }
            return false;
        }
    }

    @Override
    public void execute(Player admin, String... params) {
        if (params.length < 4) {
            PacketSendUtility.sendMessage(admin, "Syntax: //transfo <add | dispel> <idSkill> <level> <locID> <ely | asmo | all>");
            return;
        }

        int id = 0;
        int idSkill = 0;
        int level = 0;

        RecipientType recipientType = null;

        if ("all".startsWith(params[4])) {
            recipientType = RecipientType.ALL;
        } else if ("ely".startsWith(params[4])) {
            recipientType = RecipientType.ELYOS;
        } else if ("asmo".startsWith(params[4])) {
            recipientType = RecipientType.ASMO;
        } else {
            PacketSendUtility.sendMessage(admin, "Syntax: //transfo <add | dispel> <idSkill> <level> <locID> <ely | asmo | all>");
            return;
        }

        try {
            idSkill = Integer.parseInt(params[1]);
            level = Integer.parseInt(params[2]);
            id = Integer.parseInt(params[3]);
        } catch (Exception e) {
            PacketSendUtility.sendMessage(admin, "Syntax: //transfo <add | dispel> <idSkill> <level> <locID> <ely | asmo | all>");
        }

        if (params[0].equals("add")) {
            for (Player pp : World.getInstance().getAllPlayers()) {
                if (recipientType.isAllowed(pp.getRace()) && pp.getWorldId() == id) {
                    pp.getController().useSkill(idSkill, level);
                }
            }
        } else if (params[0].equals("dispel")) {
            for (Player pp : World.getInstance().getAllPlayers()) {
                if (recipientType.isAllowed(pp.getRace()) && pp.getWorldId() == id) {
                    pp.getEffectController().removeEffect(idSkill);
                }
            }
        }
    }
}
