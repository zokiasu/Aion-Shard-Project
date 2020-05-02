package com.aionemu.gameserver.model.stats.calc.functions;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.stats.calc.Stat2;
import com.aionemu.gameserver.model.stats.container.StatEnum;
import com.aionemu.gameserver.model.templates.item.ArmorType;

/**
 * @author ATracer (based on Mr.Poke ArmorMasteryModifier)
 */
public class StatArmorMasteryFunction extends StatRateFunction {

    private final ArmorType armorType;

    public StatArmorMasteryFunction(ArmorType armorType, StatEnum name, int value, boolean bonus) {
        super(name, value, bonus);
        this.armorType = armorType;
    }

    @Override
    public void apply(Stat2 stat) {
        Player player = (Player) stat.getOwner();
        if (player.getEquipment().isArmorEquipped(armorType)) {
            super.apply(stat);
        }
    }
}
