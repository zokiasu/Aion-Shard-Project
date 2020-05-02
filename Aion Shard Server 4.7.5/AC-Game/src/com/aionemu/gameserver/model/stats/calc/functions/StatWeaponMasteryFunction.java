package com.aionemu.gameserver.model.stats.calc.functions;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.stats.calc.Stat2;
import com.aionemu.gameserver.model.stats.container.StatEnum;
import com.aionemu.gameserver.model.templates.item.WeaponType;

/**
 * @author ATracer (based on Mr.Poke WeaponMasteryModifier)
 */
public class StatWeaponMasteryFunction extends StatRateFunction {

    private final WeaponType weaponType;

    public StatWeaponMasteryFunction(WeaponType weaponType, StatEnum name, int value, boolean bonus) {
        super(name, value, bonus);
        this.weaponType = weaponType;
    }

    @Override
    public void apply(Stat2 stat) {
        Player player = (Player) stat.getOwner();
        switch (this.stat) {
            case MAIN_HAND_POWER:
                if (player.getEquipment().getMainHandWeaponType() == weaponType) {
                    super.apply(stat);
                }
                break;
            case OFF_HAND_POWER:
                if (player.getEquipment().getOffHandWeaponType() == weaponType) {
                    super.apply(stat);
                }
                break;
            default:
                if (player.getEquipment().getMainHandWeaponType() == weaponType) {
                    super.apply(stat);
                }
        }

    }
}
