package playercommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.abyss.AbyssPointsService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;
import com.aionemu.gameserver.utils.i18n.CustomMessageId;
import com.aionemu.gameserver.utils.i18n.LanguageHandler;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.knownlist.Visitor;

/**
 * @author Maestross
 */
public class cmd_world_channel extends PlayerCommand {

    public cmd_world_channel() {
        super("world");
    }

    @Override
    public void execute(Player player, String... params) {
        int i = 1;
        int ap = CustomConfig.WORLD_CHANNEL_AP_COSTS;
        boolean check = true;
        String adminTag = "";

        if (params.length < 1) {
            PacketSendUtility.sendMessage(player, "syntax : .world <message>");
            return;
        }

        if (AdminConfig.CUSTOMTAG_ENABLE) {
            if (player.getAccessLevel() == 1) {
                adminTag = LanguageHandler.translate(CustomMessageId.TAG_1);
            } else if (player.getAccessLevel() == 2) {
                adminTag = LanguageHandler.translate(CustomMessageId.TAG_2);
            } else if (player.getAccessLevel() == 3) {
                adminTag = LanguageHandler.translate(CustomMessageId.TAG_3);
            } else if (player.getAccessLevel() == 4) {
                adminTag = LanguageHandler.translate(CustomMessageId.TAG_4);
            } else if (player.getAccessLevel() == 5) {
                adminTag = LanguageHandler.translate(CustomMessageId.TAG_5);
            } else if (player.getAccessLevel() == 6) {
                adminTag = LanguageHandler.translate(CustomMessageId.TAG_6);
            } else if (player.getAccessLevel() == 7) {
                adminTag = LanguageHandler.translate(CustomMessageId.TAG_7);
            } else if (player.getAccessLevel() == 8) {
                adminTag = LanguageHandler.translate(CustomMessageId.TAG_8);
            } else if (player.getAccessLevel() == 9) {
                adminTag = LanguageHandler.translate(CustomMessageId.TAG_9);
            } else if (player.getAccessLevel() == 10) {
                adminTag = LanguageHandler.translate(CustomMessageId.TAG_10);
            }
        }

        adminTag += player.getName() + " : ";

        StringBuilder sbMessage;
        if (player.isGM()) {
            sbMessage = new StringBuilder("[World-Chat]" + " " + adminTag);
        } else {
            sbMessage = new StringBuilder("[World-Chat]" + " " + player.getName() + " : ");
        }

        for (String s : params) {
            if (i++ != 0 && (check)) {
                sbMessage.append(s).append(" ");
            }
        }

        String message = sbMessage.toString().trim();
        int messageLenght = message.length();

        final String sMessage = message.substring(0, CustomConfig.MAX_CHAT_TEXT_LENGHT > messageLenght ? messageLenght : CustomConfig.MAX_CHAT_TEXT_LENGHT);
        if (player.isGM()) {

            World.getInstance().doOnAllPlayers(new Visitor<Player>() {
                @Override
                public void visit(Player player) {
                    PacketSendUtility.sendMessage(player, sMessage);
                }
            });
        } else if (!player.isGM() && !player.isInPrison()) {
            if (player.getAbyssRank().getAp() < ap) {

                PacketSendUtility.sendMessage(player, LanguageHandler.translate(CustomMessageId.NOT_ENOUGH_AP) + player.getAbyssRank().getAp());
            } else {
                AbyssPointsService.addAp(player, -ap);
                World.getInstance().doOnAllPlayers(new Visitor<Player>() {
                    @Override
                    public void visit(Player player) {
                        PacketSendUtility.sendMessage(player, sMessage);
                    }
                });
            }
        } else {
            PacketSendUtility.sendMessage(player, LanguageHandler.translate(CustomMessageId.NOT_ENOUGH_AP) + player.getAbyssRank().getAp());
        }
    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "syntax : .world <message>");
    }
}
