package com.aionemu.gameserver.model.templates;

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.instance.InstanceCoolTimeType;
import com.aionemu.gameserver.model.instance.InstanceType;

import javax.xml.bind.annotation.*;

/**
 * @author VladimirZ
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InstanceCooltime")
public class InstanceCooltime {

    @XmlElement(name = "type")
    protected InstanceCoolTimeType coolTimeType;
    @XmlElement(name = "typevalue")
    protected String typevalue;
    @XmlElement(name = "ent_cool_time")
    protected Integer entCoolTime;
    @XmlElement(name = "indun_type")
    protected InstanceType indun_type;
    @XmlElement(name = "max_member_light")
    protected Integer maxMemberLight;
    @XmlElement(name = "max_member_dark")
    protected Integer maxMemberDark;
    @XmlElement(name = "enter_min_level_light")
    protected Integer enterMinLevelLight;
    @XmlElement(name = "enter_max_level_light")
    protected Integer enterMaxLevelLight;
    @XmlElement(name = "enter_min_level_dark")
    protected Integer enterMinLevelDark;
    @XmlElement(name = "enter_max_level_dark")
    protected Integer enterMaxLevelDark;
    @XmlElement(name = "alarm_unit_score")
    protected Integer alarmUnitScore;
    @XmlElement(name = "can_enter_mentor")
    protected boolean can_enter_mentor;
    @XmlElement(name = "enter_guild")
    protected boolean enter_guild;
    @XmlElement(name = "maxcount")
    protected Integer max_count;
    @XmlAttribute(required = true)
    protected int id;
    @XmlAttribute(required = true)
    protected int worldId;
    @XmlAttribute(required = true)
    protected Race race;

    public InstanceCoolTimeType getCoolTimeType() {
        return coolTimeType;
    }

    public String getTypeValue() {
        return typevalue;
    }

    public InstanceType getTypeInstance() {
        return indun_type;
    }

    /**
     * Gets the value of the entCoolTime property.
     *
     * @return possible object is {@link Integer }
     */
    public Integer getEntCoolTime() {
        return entCoolTime;
    }

    /**
     * Gets the value of the maxMemberLight property.
     *
     * @return possible object is {@link Integer }
     */
    public Integer getMaxMemberLight() {
        return maxMemberLight;
    }

    /**
     * Gets the value of the maxMemberDark property.
     *
     * @return possible object is {@link Integer }
     */
    public Integer getMaxMemberDark() {
        return maxMemberDark;
    }

    /**
     * Gets the value of the enterMinLevelLight property.
     *
     * @return possible object is {@link Integer }
     */
    public Integer getEnterMinLevelLight() {
        return enterMinLevelLight;
    }

    /**
     * Gets the value of the enterMaxLevelLight property.
     *
     * @return possible object is {@link Integer }
     */
    public Integer getEnterMaxLevelLight() {
        return enterMaxLevelLight;
    }

    /**
     * Gets the value of the enterMinLevelDark property.
     *
     * @return possible object is {@link Integer }
     */
    public Integer getEnterMinLevelDark() {
        return enterMinLevelDark;
    }

    /**
     * Gets the value of the enterMaxLevelDark property.
     *
     * @return possible object is {@link Integer }
     */
    public Integer getEnterMaxLevelDark() {
        return enterMaxLevelDark;
    }

    /**
     * Gets the value of the alarmUnitScore property.
     *
     * @return possible object is {@link Integer }
     */
    public Integer getAlarmUnitScore() {
        return alarmUnitScore;
    }

    /**
     * Gets the value of the can_enter_mentor property.
     *
     * @return possible object is {@link boolean }
     */
    public boolean getCanEnterMentor() {
        return can_enter_mentor;
    }

    /**
     * Gets the value of the enter_guild property.
     *
     * @return possible object is {@link boolean }
     */
    public boolean CanEnterLegion() {
        return enter_guild;
    }

    /**
     * Gets the value of the max_count property.
     *
     * @return possible object is {@link Integer }
     */
    public Integer getMaxEntriesCount() {
        return max_count;
    }

    /**
     * Gets the value of the id property.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the value of the worldId property.
     */
    public int getWorldId() {
        return worldId;
    }

    /**
     * Gets the value of the race property.
     *
     * @return possible object is {@link Race }
     */
    public Race getRace() {
        return race;
    }
}
