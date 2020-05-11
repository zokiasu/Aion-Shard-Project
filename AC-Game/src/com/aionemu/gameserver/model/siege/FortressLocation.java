package com.aionemu.gameserver.model.siege;

import com.aionemu.gameserver.model.DescriptionId;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Kisk;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.siegelocation.SiegeLegionReward;
import com.aionemu.gameserver.model.templates.siegelocation.SiegeLocationTemplate;
import com.aionemu.gameserver.model.templates.siegelocation.SiegeReward;
import com.aionemu.gameserver.model.templates.zone.ZoneType;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.world.zone.ZoneInstance;

import java.util.List;

/**
 * @author Source
 */
public class FortressLocation extends SiegeLocation {

    protected List<SiegeReward> siegeRewards;
    protected List<SiegeLegionReward> siegeLegionRewards, siegeLegionRewardsOnOccupy;
    protected boolean isUnderAssault;

    public FortressLocation() {
    }

    public FortressLocation(SiegeLocationTemplate template) {
        super(template);
        this.siegeRewards = template.getSiegeRewards() != null ? template.getSiegeRewards() : null;
        this.siegeLegionRewards = template.getSiegeLegionRewards() != null ? template.getSiegeLegionRewards() : null;
        this.siegeLegionRewardsOnOccupy = template.getSiegeLegionRewardsOnOccupy() != null ? template.getSiegeLegionRewardsOnOccupy() : null;
    }

    public List<SiegeReward> getReward() {
        return this.siegeRewards;
    }

    public List<SiegeLegionReward> getLegionReward() {
        return this.siegeLegionRewards;
    }

    public List<SiegeLegionReward> getLegionRewardOnOccupy() {
        return this.siegeLegionRewardsOnOccupy;
    }

    /**
     * @return isEnemy
     */
    public boolean isEnemy(Creature creature) {
        return creature.getRace().getRaceId() != getRace().getRaceId();
    }

    /**
     * @return isCanTeleport
     */
    @Override
    public boolean isCanTeleport(Player player) {
        if (player == null) {
            return canTeleport;
        }
        return canTeleport && player.getRace().getRaceId() == getRace().getRaceId();
    }

    /**
     * @return DescriptionId object with fortress name
     */
    public DescriptionId getNameAsDescriptionId() {
        return new DescriptionId(template.getNameId());
    }

    @Override
    public void onEnterZone(Creature creature, ZoneInstance zone) {
        super.onEnterZone(creature, zone);
        if (this.isVulnerable()) {
            creature.setInsideZoneType(ZoneType.SIEGE);
        }
    }

    @Override
    public void onLeaveZone(Creature creature, ZoneInstance zone) {
        super.onLeaveZone(creature, zone);
        if (this.isVulnerable()) {
            creature.unsetInsideZoneType(ZoneType.SIEGE);
        }
    }

    @Override
    public void clearLocation() {
        // TODO: not allow to place Kisk if siege will be soon
        for (Creature creature : getCreatures().values()) {
            if (isEnemy(creature)) {
                if (creature instanceof Kisk) {
                    Kisk kisk = (Kisk) creature;
                    kisk.getController().die();
                }
            }
        }

        for (Player player : getPlayers().values()) {
            if (isEnemy(player)) {
                TeleportService2.moveToBindLocation(player, true);
            }
        }
    }
}
