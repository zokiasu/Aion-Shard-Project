package com.aionemu.gameserver.model.templates.stats;

import com.aionemu.gameserver.model.stats.calc.functions.*;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author xavier
 * @modified Rolandas
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "modifiers")
public class ModifiersTemplate {

    @XmlElements({
            @XmlElement(name = "sub", type = StatSubFunction.class),
            @XmlElement(name = "add", type = StatAddFunction.class),
            @XmlElement(name = "rate", type = StatRateFunction.class),
            @XmlElement(name = "set", type = StatSetFunction.class)
    })
    private List<StatFunction> modifiers;
    @XmlAttribute
    private float chance = 100f;

    public List<StatFunction> getModifiers() {
        return modifiers;
    }

    /**
     * @return the chance
     */
    public float getChance() {
        return chance;
    }
}
