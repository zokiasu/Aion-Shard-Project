package com.aionemu.gameserver.eventEngine.battleground.model.templates;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Maestross
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BattleGroundType")
public enum BattleGroundType {

    ASSAULT,
    CTF,
    CTP
}
