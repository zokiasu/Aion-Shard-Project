package com.aionemu.gameserver.eventEngine.battleground.model.templates;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Maestross
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BattleGroundRules")
public class BattleGroundRules {

    @XmlAttribute(name = "kill_player", required = true)
    private int killPlayer;
    @XmlAttribute(name = "die", required = true)
    private int die;
    @XmlAttribute(name = "flag_cap", required = false)
    private int flagCap;
    @XmlAttribute(name = "flag_base", required = false)
    private int flagBase;
    @XmlAttribute(name = "flag_ground", required = false)
    private int flagGround;
    @XmlAttribute(name = "CTF_reward", required = false)
    private int CTFReward;

    public int getKillPlayer() {
        return killPlayer;
    }

    public int getDie() {
        return die;
    }

    public int getFlagCap() {
        return flagCap;
    }

    public int getFlagBase() {
        return flagBase;
    }

    public int getFlagGround() {
        return flagGround;
    }

    public int getCTFReward() {
        return CTFReward;
    }
}
