package com.aionemu.gameserver.model.siege;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.stats.calc.StatOwner;
import com.aionemu.gameserver.model.stats.calc.functions.IStatFunction;
import com.aionemu.gameserver.model.stats.calc.functions.StatAddFunction;
import com.aionemu.gameserver.model.stats.container.StatEnum;
import com.aionemu.gameserver.model.templates.abyssracebonus.AbyssRaceBonus;
import com.aionemu.gameserver.model.templates.abyssracebonus.AbyssRacePenalty;
import com.aionemu.gameserver.skillengine.change.Func;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author Eloann
 */
public class AbyssRaceBuff implements StatOwner {

    private Future<?> task;
    private List<IStatFunction> functions = new ArrayList<IStatFunction>();
    private AbyssRaceBonus abyssRaceBonusAttr;
    private long startTime;

    public AbyssRaceBuff(int id) {
        abyssRaceBonusAttr = DataManager.ABYSS_RACE_BONUS_DATA.getAbyssRaceBonus(id);
    }

    public void applyEffect(Player player) {

        if (hasAbyssRaceBuff() || abyssRaceBonusAttr == null) {
            return;
        }
        startTime = System.currentTimeMillis();
        for (AbyssRacePenalty abyssRacePenaltyAttrs : abyssRaceBonusAttr.getPenalty()) {
            StatEnum stat = abyssRacePenaltyAttrs.getStat();
            int statToModified = player.getGameStats().getStat(stat, 0).getBase();
            int value = abyssRacePenaltyAttrs.getValue();
            int valueModified = abyssRacePenaltyAttrs.getFunc().equals(Func.PERCENT) ? (statToModified * value / 100) : (value);
            functions.add(new StatAddFunction(stat, valueModified, true));
        }
        player.getGameStats().addEffect(this, functions);
    }

    public void endEffect(Player player) {
        functions.clear();
        if (hasAbyssRaceBuff()) {
            task.cancel(true);
        }
        player.getGameStats().endEffect(this);
    }

    public int getRemaningTime() {
        return (int) ((System.currentTimeMillis() - startTime) / 1000);
    }

    public boolean hasAbyssRaceBuff() {
        return task != null && !task.isDone();
    }
}
