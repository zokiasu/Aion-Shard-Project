package com.aionemu.gameserver.model.siege;

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;

/**
 * @author Ever
 */
public class PlayerGP implements Comparable<PlayerGP> {

    private Player player;
    private Race race;
    private int gp;

    public PlayerGP(Player player) {
        this.player = player;
        this.race = player.getRace();
        this.gp = 0;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Race getRace() {
        return this.race;
    }

    public int getGP() {
        return this.gp;
    }

    public void increaseGP(int gp) {
        this.gp += gp;
    }

    @Override
    public int compareTo(PlayerGP pl) {
        return this.gp - pl.gp;
    }
}
