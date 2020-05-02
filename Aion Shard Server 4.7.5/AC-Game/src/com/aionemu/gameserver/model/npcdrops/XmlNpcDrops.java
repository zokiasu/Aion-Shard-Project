package com.aionemu.gameserver.model.npcdrops;

import java.util.Collections;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author kosyachok
 */
@XmlRootElement(name="npc_drop")
@XmlAccessorType(XmlAccessType.NONE)
public class XmlNpcDrops {

  @XmlElement(name="drop_group")
  protected List<XmlDropGroup> dropGroup;
  @XmlAttribute(name="npc_id", required=true)
  protected int npcId;
  
  public List<XmlDropGroup> getDropGroup() {
    if (this.dropGroup == null) {
      return Collections.emptyList();
    }
    return this.dropGroup;
  }
  
  public int getNpcId() {
    return this.npcId;
  }
}
