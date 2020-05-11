package com.aionemu.gameserver.network.aion.gmhandler;

import com.aionemu.gameserver.configs.main.GSConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class CmdLevelUpDown extends AbstractGMHandler {

    public enum LevelUpDownState {
        UP,
        DOWN
    }

    ;

    private LevelUpDownState state;

    public CmdLevelUpDown(Player admin, String params, LevelUpDownState state) {
        super(admin, params);
        this.state = state;
        run();
    }

    public void run() {
        Player t = target != null ? target : admin;
        Integer level = Integer.parseInt(params);

        if (state == LevelUpDownState.DOWN) {
            if (t.getCommonData().getLevel() - level >= 1) {
                int newLevel = t.getCommonData().getLevel() - level;
                t.getCommonData().setLevel(newLevel);
            } else {
                PacketSendUtility.sendMessage(admin, "The value of <level> will plus calculated to the current player level!");
            }
        } else if (state == LevelUpDownState.UP) {
            if (t.getCommonData().getLevel() + level <= GSConfig.PLAYER_MAX_LEVEL) {
                int newLevel = t.getCommonData().getLevel() + level;
                t.getCommonData().setLevel(newLevel);
            } else {
                PacketSendUtility.sendMessage(admin, "The value of <level> will plus calculated to the current player level!");
            }
        }
    }

}
