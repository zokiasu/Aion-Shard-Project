package com.aionemu.gameserver.model.templates.item.bonuses;

import com.aionemu.gameserver.model.templates.stats.ModifiersTemplate;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RandomBonus", propOrder = {"modifiers"})
public class RandomBonus {

    @XmlElement(required = true)
    protected List<ModifiersTemplate> modifiers;
    @XmlAttribute(required = true)
    protected int id;
    @XmlAttribute(name = "type", required = true)
    private StatBonusType bonusType;

    public List<ModifiersTemplate> getModifiers() {
        if (modifiers == null) {
            modifiers = new ArrayList<ModifiersTemplate>();
        }
        return this.modifiers;
    }

    /**
     * Gets the value of the id property.
     */
    public int getId() {
        return id;
    }

    public StatBonusType getBonusType() {
        return bonusType;
    }
}
