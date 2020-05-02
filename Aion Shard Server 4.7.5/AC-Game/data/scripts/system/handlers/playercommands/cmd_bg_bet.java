package playercommands;

import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.items.storage.IStorage;
import com.aionemu.gameserver.model.items.storage.StorageType;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;

/**
 * @author Eloann
 */
public class cmd_bg_bet extends PlayerCommand {

    public cmd_bg_bet() {
        super("bet");
    }

    @Override
    public void execute(Player player, String... params) {
        IStorage inventory = player.getStorage(StorageType.CUBE.getId());
        long PlayerKinah = inventory.getKinah();

        int amount;

        if (params.equals("") || params.equals(" ") || params.length != 2) {
            PacketSendUtility.sendMessage(player, "Use : .bet <e|a> <amount>");
            return;
        }

        try {
            amount = Integer.parseInt(params[1]);
        } catch (NumberFormatException e) {
            PacketSendUtility.sendMessage(player, "Use : .bet <e | a> <amount>");
            return;
        }

        if (player.battlegroundObserve == 1 || player.battlegroundObserve == 2) {
            if (PlayerKinah < amount) {
                PacketSendUtility.sendMessage(player, "You don't have enough kinah !");
            }

            int maxBetAmount = 5000000;
            if (player.battlegroundBetE + player.battlegroundBetA + amount > maxBetAmount) {
                PacketSendUtility.sendMessage(player, "You can't bet more than " + maxBetAmount + " !");
            } else if (player.battlegroundBetE == 0 && player.battlegroundBetA == 0) {
                if (params[0].equals("e")) {
                    player.battlegroundBetE = amount;
                    inventory.decreaseKinah(amount);
                    LoggerFactory.getLogger(PlayerCommand.class).info(String.format("[BET] - Player : " + player.getName() + " | Bet : " + params[1] + " | Faction : e"));
                    PacketSendUtility.sendMessage(player, "You have bet : " + player.battlegroundBetE + " for the elyos");
                } else if (params[0].equals("a")) {
                    player.battlegroundBetA = amount;
                    inventory.decreaseKinah(amount);
                    LoggerFactory.getLogger(PlayerCommand.class).info(String.format("[BET] - Player : " + player.getName() + " | Bet : " + params[1] + " | Faction : a"));
                    PacketSendUtility.sendMessage(player, "You have bet : " + player.battlegroundBetA + " for the asmodians");
                } else {
                    PacketSendUtility.sendMessage(player, "Use : .bet <e | a> <amount>");
                }
            } else if (player.battlegroundBetE > 0) {
                if (params[0].equals("e")) {
                    player.battlegroundBetE += amount;
                    inventory.decreaseKinah(amount);
                    LoggerFactory.getLogger(PlayerCommand.class).info(String.format("[BET] - Player : " + player.getName() + " | Bet : " + params[1] + " | Faction : e"));
                    PacketSendUtility.sendMessage(player, "You have bet : " + player.battlegroundBetE + " for the elyos");
                } else if (params[0].equals("a")) {
                    PacketSendUtility.sendMessage(player, "You have already bet for the elyos");
                } else {
                    PacketSendUtility.sendMessage(player, "Use : .bet <e | a> <amount>");
                }
            } else if (player.battlegroundBetA > 0) {
                if (params[0].equals("a")) {
                    player.battlegroundBetA += amount;
                    inventory.decreaseKinah(amount);
                    LoggerFactory.getLogger(PlayerCommand.class).info(String.format("[BET] - Player : " + player.getName() + " | Bet : " + params[1] + " | Faction : a"));
                    PacketSendUtility.sendMessage(player, "You have bet : " + player.battlegroundBetA + " for the asmodians");
                } else if (params[0].equals("e")) {
                    PacketSendUtility.sendMessage(player, "You have already bet for the asmodians");
                } else {
                    PacketSendUtility.sendMessage(player, "Use : .bet <e | a> <amount>");
                }
            } else {
                PacketSendUtility.sendMessage(player, "Use : .bet <e | a> <amount>");
            }
        } else {
            PacketSendUtility.sendMessage(player, "You can't bet for the moment");
        }
    }
}
