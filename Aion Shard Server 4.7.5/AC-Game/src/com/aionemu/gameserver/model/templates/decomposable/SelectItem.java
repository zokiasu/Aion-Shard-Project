package com.aionemu.gameserver.model.templates.decomposable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SelectItem")
public class SelectItem {

    @XmlAttribute
    private int id;

    @XmlAttribute
    private int count = 1;

    public int getSelectItemId() {
        return this.id;
    }

    public int getCount() {
        return this.count;
    }
}
