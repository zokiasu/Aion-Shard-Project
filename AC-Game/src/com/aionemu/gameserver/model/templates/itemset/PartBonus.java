package com.aionemu.gameserver.model.templates.itemset;

import com.aionemu.gameserver.model.stats.calc.functions.StatFunction;
import com.aionemu.gameserver.model.templates.stats.ModifiersTemplate;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author ATracer
 */
@XmlRootElement(name = "PartBonus")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartBonus {

    @XmlAttribute
    protected int count;
    @XmlElement(name = "modifiers", required = false)
    protected ModifiersTemplate modifiers;

    public List<StatFunction> getModifiers() {
        return modifiers != null ? modifiers.getModifiers() : null;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }
}
