package com.aionemu.gameserver.model.npcdrops;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * @author kosyachok
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="drop")
public class XmlDrop {

  @XmlAttribute(name="item_id", required=true)
  protected int itemId;
  @XmlAttribute(name="min_amount", required=true)
  protected int minAmount;
  @XmlAttribute(name="max_amount", required=true)
  protected int maxAmount;
  @XmlAttribute(required=true)
  protected float chance;
  @XmlAttribute(name="no_reduce")
  protected boolean noReduce = false;
  @XmlAttribute(name="eachmember")
  protected boolean eachMember = false;
  
  public int getItemId() {
    return this.itemId;
  }
  
  public int getMinAmount() {
    return this.minAmount;
  }
  
  public int getMaxAmount() {
    return this.maxAmount;
  }
  
  public float getChance() {
    return this.chance;
  }
  
  public boolean isNoReduction() {
    return this.noReduce;
  }
  
  public boolean isEachMember() {
    return this.eachMember;
  }
}
