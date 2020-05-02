package com.aionemu.gameserver.services.siegeservice;

/**
 * @author Luzien
 */
public class ArtifactAssault extends Assault<ArtifactSiege> {

    public ArtifactAssault(ArtifactSiege siege) {
        super(siege);
    }

    @Override
    public void scheduleAssault(int delay) {
    }

    @Override
    public void onAssaultFinish(boolean captured) {
    }
}
