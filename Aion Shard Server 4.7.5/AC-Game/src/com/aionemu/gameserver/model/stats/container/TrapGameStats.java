package com.aionemu.gameserver.model.stats.container;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.stats.calc.Stat2;

/**
 * @author ATracer
 */
public class TrapGameStats extends NpcGameStats {

    public TrapGameStats(Npc owner) {
        super(owner);
    }

    @Override
    public Stat2 getStat(StatEnum statEnum, int base) {
        Stat2 stat = super.getStat(statEnum, base);
        if (owner.getMaster() == null) {
            return stat;
        }
        switch (statEnum) {
            case BOOST_MAGICAL_SKILL:
            case MAGICAL_ACCURACY:
                // bonus is calculated from stat bonus of master (only green value)
                stat.setBonusRate(0.7f); //TODO: retail formula?
                return owner.getMaster().getGameStats().getItemStatBoost(statEnum, stat);
            default:
                break;

        }
        return stat;
    }
}
