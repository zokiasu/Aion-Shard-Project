package com.aionemu.gameserver.skillengine.effect;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.model.stats.calc.functions.IStatFunction;
import com.aionemu.gameserver.model.stats.calc.functions.StatWeaponMasteryFunction;
import com.aionemu.gameserver.model.stats.container.StatEnum;
import com.aionemu.gameserver.model.templates.item.WeaponType;
import com.aionemu.gameserver.skillengine.model.Effect;

/**
 * @author ATracer
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WeaponMasteryEffect")
public class WeaponMasteryEffect extends BufEffect {

    @XmlAttribute(name = "weapon")
    private WeaponType weaponType;

    @Override
    public void startEffect(Effect effect) {
        if (change == null) {
            return;
        }

        List<IStatFunction> modifiers = getModifiers(effect);
        List<IStatFunction> masteryModifiers = new ArrayList<IStatFunction>(modifiers.size());
        for (IStatFunction modifier : modifiers) {
            if (weaponType.getRequiredSlots() == 2) {
                masteryModifiers.add(new StatWeaponMasteryFunction(weaponType, modifier.getName(), modifier.getValue(),
                        modifier.isBonus()));
            } else if (modifier.getName() == StatEnum.PHYSICAL_ATTACK) {
                masteryModifiers.add(new StatWeaponMasteryFunction(weaponType, StatEnum.MAIN_HAND_POWER, modifier.getValue(),
                        modifier.isBonus()));
                masteryModifiers.add(new StatWeaponMasteryFunction(weaponType, StatEnum.OFF_HAND_POWER, modifier.getValue(),
                        modifier.isBonus()));
            }
        }
        effect.getEffected().getGameStats().addEffect(effect, masteryModifiers);
    }
}
