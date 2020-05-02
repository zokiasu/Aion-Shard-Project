package com.aionemu.gameserver.model.templates.tradelist;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author orz
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "tradelist_template")
public class TradeListTemplate {

    /**
     * Npc Id.
     */
    @XmlAttribute(name = "npc_id", required = true)
    private int npcId;
    @XmlAttribute(name = "npc_type")
    private TradeNpcType tradeNpcType = TradeNpcType.NORMAL;
    @XmlAttribute(name = "sell_price_rate")
    private int sellPriceRate = 100;
    @XmlAttribute(name = "buy_price_rate")
    private int buyPriceRate;
    @XmlElement(name = "tradelist")
    protected List<TradeTab> tradeTablist;

    /**
     * @return List<TradeTab>
     */
    public List<TradeTab> getTradeTablist() {
        if (tradeTablist == null) {
            tradeTablist = new ArrayList<TradeTab>();
        }
        return this.tradeTablist;
    }

    public int getNpcId() {
        return npcId;
    }

    public int getCount() {
        return tradeTablist.size();
    }

    /**
     * @return the Npc Type
     */
    public TradeNpcType getTradeNpcType() {
        return tradeNpcType;
    }

    /**
     * @return the sellPriceRate
     */
    public int getSellPriceRate() {
        return sellPriceRate;
    }

    /**
     * @return the buyPriceRate
     */
    public int getBuyPriceRate() {
        return buyPriceRate;
    }

    /**
     * <p/>
     * Java class for anonymous complex type.
     * <p/>
     * The following schema fragment specifies the expected content contained
     * within this class.
     * <p/>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}int" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "Tradelist")
    public static class TradeTab {

        @XmlAttribute
        protected int id;

        /**
         * Gets the value of the id property.
         */
        public int getId() {
            return id;
        }
    }
}
