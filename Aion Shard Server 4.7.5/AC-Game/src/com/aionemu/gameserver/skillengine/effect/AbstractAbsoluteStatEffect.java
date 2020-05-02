package com.aionemu.gameserver.skillengine.effect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.templates.stats.ModifiersTemplate;

/**
 * @author Rolandas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractAbsoluteStatEffect")
public abstract class AbstractAbsoluteStatEffect extends EffectTemplate {

    @XmlAttribute(name = "statsetid")
    private int statSetId;

    /**
     * @return the statSetId
     */
    public ModifiersTemplate getModifiersSet() {
        return DataManager.ABSOLUTE_STATS_DATA.getTemplate(statSetId);
    }
}
