package playercommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;

public class cmd_job extends PlayerCommand {

    public cmd_job() {
        super("job");
    }

    @Override
    public void execute(Player player, String... params) {
        player.getSkillList().addSkill(player, 30002, 499); // Vita
        player.getSkillList().addSkill(player, 30003, 499); // Ether
        player.getSkillList().addSkill(player, 40001, 550); // Cuisine
        player.getSkillList().addSkill(player, 40002, 550); // Armes
        player.getSkillList().addSkill(player, 40003, 550); // Armure
        player.getSkillList().addSkill(player, 40004, 550); // Couture
        player.getSkillList().addSkill(player, 40007, 550); // Alchimie
        player.getSkillList().addSkill(player, 40008, 550); // Artisanat
        player.getSkillList().addSkill(player, 40010, 550); // Artisanat
    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "Syntax: .job ");
    }
}
