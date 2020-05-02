package com.aionemu.gameserver.model.templates.itemset;

import com.aionemu.gameserver.model.stats.calc.StatOwner;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author ATracer, modified by Antivirus
 */
@XmlRootElement(name = "itemset")
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemSetTemplate implements StatOwner {

    @XmlElement(required = true)
    protected List<ItemPart> itempart;
    @XmlElement(required = true)
    protected List<PartBonus> partbonus;
    protected FullBonus fullbonus;
    @XmlAttribute
    protected String name;
    @XmlAttribute
    protected int id;

    /**
     * @param u
     * @param parent
     */
    void afterUnmarshal(Unmarshaller u, Object parent) {
        if (fullbonus != null) {
            // Set number of items to apply the full bonus
            fullbonus.setNumberOfItems(itempart.size());
        }
    }

    /**
     * @return the itempart
     */
    public List<ItemPart> getItempart() {
        return itempart;
    }

    /**
     * @return the partbonus
     */
    public List<PartBonus> getPartbonus() {
        return partbonus;
    }

    /**
     * @return the fullbonus
     */
    public FullBonus getFullbonus() {
        return fullbonus;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
}
