package com.aionemu.gameserver.eventEngine.battleground.model.templates;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Maestross
 */
@XmlRootElement(name = "battleground")
@XmlAccessorType(XmlAccessType.FIELD)
public class BattleGroundTemplate {

    @XmlAttribute(name = "tpl_id", required = true)
    private int tplId;
    @XmlAttribute(name = "world_id", required = true)
    private int worldId;
    @XmlAttribute(name = "type", required = true)
    private BattleGroundType type;
    @XmlAttribute(name = "name", required = true)
    private String name;
    @XmlAttribute(name = "target_score", required = true)
    private int targetScore;
    @XmlAttribute(name = "price", required = true)
    private int price;
    @XmlAttribute(name = "nb_players", required = true)
    private int nbPlayers;
    @XmlAttribute(name = "wait_time", required = true)
    private int waitTime;
    @XmlAttribute(name = "bg_time", required = true)
    private int bgTime;
    @XmlElement(name = "join_conditions", required = true)
    private BattleGroundJoinConditions joinConditions;
    @XmlElement(name = "insert_point", required = true)
    private ObjectLocation insertPoint;
    @XmlElement(name = "healer_location", required = true)
    private ObjectLocation healerLocation;
    @XmlElement(name = "flag_location", required = true)
    private ObjectLocation flagLocation;
    @XmlElement(name = "rules", required = true)
    private BattleGroundRules rules;

    public int getTplId() {
        return tplId;
    }

    public int getWorldId() {
        return worldId;
    }

    public String getName() {
        return name;
    }

    public int getTargetScore() {
        return targetScore;
    }

    public int getPrice() {
        return price;
    }

    public int getNbPlayers() {
        return nbPlayers;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public int getBgTime() {
        return bgTime;
    }

    public BattleGroundJoinConditions getJoinConditions() {
        return joinConditions;
    }

    public ObjectLocation getInsertPoint() {
        return insertPoint;
    }

    public ObjectLocation getHealerLocation() {
        return healerLocation;
    }

    public BattleGroundRules getRules() {
        return rules;
    }

    public BattleGroundType getType() {
        return type;
    }

    public ObjectLocation getFlagLocation() {
        return flagLocation;
    }
}
