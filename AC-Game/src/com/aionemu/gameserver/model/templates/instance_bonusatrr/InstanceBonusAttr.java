package com.aionemu.gameserver.model.templates.instance_bonusatrr;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xTz
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InstanceBonusAttr", propOrder = {
        "penaltyAttr"
})
public class InstanceBonusAttr {

    @XmlElement(name = "penalty_attr")
    protected List<InstancePenaltyAttr> penaltyAttr;
    @XmlAttribute(name = "buff_id", required = true)
    protected int buffId;

    /**
     * Gets the value of the penaltyAttr property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the penaltyAttr property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPenaltyAttr().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link InstancePenaltyAttr }
     */
    public List<InstancePenaltyAttr> getPenaltyAttr() {
        if (penaltyAttr == null) {
            penaltyAttr = new ArrayList<InstancePenaltyAttr>();
        }
        return this.penaltyAttr;
    }

    /**
     * Gets the value of the buffId property.
     */
    public int getBuffId() {
        return buffId;
    }

    /**
     * Sets the value of the buffId property.
     */
    public void setBuffId(int value) {
        this.buffId = value;
    }
}
