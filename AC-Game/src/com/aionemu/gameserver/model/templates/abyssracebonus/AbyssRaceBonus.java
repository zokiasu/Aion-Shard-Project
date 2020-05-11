package com.aionemu.gameserver.model.templates.abyssracebonus;

import com.aionemu.gameserver.model.Race;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Eloann
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbyssRaceBonus", propOrder = {"bonus"})
public class AbyssRaceBonus {

    @XmlElement(name = "bonus")
    protected List<AbyssRacePenalty> bonus;
    @XmlAttribute(name = "id", required = true)
    protected int Id;
    @XmlAttribute(name = "name", required = true)
    private String name;
    @XmlAttribute(name = "race", required = true)
    private Race race;

    public List<AbyssRacePenalty> getPenalty() {
        if (bonus == null) {
            bonus = new ArrayList<AbyssRacePenalty>();
        }
        return bonus;
    }

    public int getId() {
        return Id;
    }

    public void setId(int value) {
        Id = value;
    }

    public String getName() {
        return name;
    }

    public Race getRace() {
        return race;
    }
}
