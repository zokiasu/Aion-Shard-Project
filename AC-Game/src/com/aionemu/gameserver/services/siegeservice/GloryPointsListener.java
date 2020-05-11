package com.aionemu.gameserver.services.siegeservice;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.siege.SiegeLocation;
import com.aionemu.gameserver.services.abyss.AbyssPointsService;

/**
 * @author Ever
 */
public class GloryPointsListener extends AbyssPointsService.AddGPGlobalCallback {

    private final Siege<?> siege;

    public GloryPointsListener(Siege<?> siege) {
        this.siege = siege;
    }

    @Override
    public void onGloryPointsAdded(Player player, int gloryPoints) {
        SiegeLocation fortress = siege.getSiegeLocation();

        // Make sure that only GP earned near this fortress will be added
        // Abyss points can be added only while in the siege zones
        if (fortress.isInsideLocation(player)) {
            siege.addGloryPoints(player, gloryPoints);
        }
    }
}
