package com.aionemu.gameserver.model.templates.decomposable;

import com.aionemu.gameserver.model.PlayerClass;

import javax.xml.bind.annotation.*;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SelectItems")
public class SelectItems {


    @XmlAttribute(name = "player_class")
    private PlayerClass playerClass = PlayerClass.ALL;
    @XmlElement(name = "item")
    private List<SelectItem> items;

    public PlayerClass getPlayerClass() {
        return this.playerClass;
    }

    public List<SelectItem> getItems() {
        return this.items;
    }
}
