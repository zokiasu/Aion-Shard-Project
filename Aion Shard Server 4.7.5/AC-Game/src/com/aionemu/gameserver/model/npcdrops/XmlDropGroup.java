package com.aionemu.gameserver.model.npcdrops;

import com.aionemu.gameserver.model.Race;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author kosyachok
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlDropGroup {

  @XmlElement(name="drop")
  protected List<XmlDrop> drop;
  @XmlAttribute
  protected Race race = Race.PC_ALL;
  @XmlAttribute(name="use_category")
  protected Boolean useCategory = Boolean.valueOf(true);
  @XmlAttribute(name="name")
  protected String group_name;
  
  public List<XmlDrop> getDrop() {
    return this.drop;
  }
  
  public Race getRace() {
    return this.race;
  }
  
  public Boolean isUseCategory() {
    return this.useCategory;
  }
  
  public String getGroupName() {
    if (this.group_name == null) {
      return "";
    }
    return this.group_name;
  }
}
