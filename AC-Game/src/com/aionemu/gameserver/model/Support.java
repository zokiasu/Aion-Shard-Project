package com.aionemu.gameserver.model;

import com.aionemu.gameserver.model.gameobjects.player.Player;

/**
 * @author paranaix
 */
public class Support {

    private Player owner;
    private String summary = "";
    private String description = "";

    public Support(Player owner, String summary, String description) {
        this.owner = owner;
        this.summary = summary;
        this.description = description;
    }

    public Support(Player owner) {
        this(owner, "", "");
    }

    public Player getOwner() {
        return owner;
    }

    public String getSummary() {
        return summary;
    }

    public String getDescription() {
        return description;
    }

    public void setOwner(Player owner) {
        if (owner == null) {
            throw new IllegalArgumentException("Owner cant be null");
        }
        this.owner = owner;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
