package com.aionemu.gameserver.model.templates.housing;

import javax.xml.bind.annotation.*;

/**
 * @author Rolandas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "sale")
public class Sale {

    @XmlAttribute(name = "point_price", required = true)
    protected int pointPrice;
    @XmlAttribute(name = "gold_price", required = true)
    protected long goldPrice;
    @XmlAttribute(required = true)
    protected int level;

    public int getPointPrice() {
        return pointPrice;
    }

    public long getGoldPrice() {
        return goldPrice;
    }

    public int getMinLevel() {
        return level;
    }
}
