package com.aionemu.gameserver.model.templates.npc;

import com.aionemu.gameserver.ai2.AiNames;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.TribeClass;
import com.aionemu.gameserver.model.drop.NpcDrop;
import com.aionemu.gameserver.model.items.NpcEquippedGear;
import com.aionemu.gameserver.model.templates.BoundRadius;
import com.aionemu.gameserver.model.templates.VisibleObjectTemplate;
import com.aionemu.gameserver.model.templates.stats.KiskStatsTemplate;
import com.aionemu.gameserver.model.templates.stats.NpcStatsTemplate;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author Luno
 * @author GiGatR00n v4.7.5.x
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "npc_template")
public class NpcTemplate extends VisibleObjectTemplate {

    private int npcId;
    @XmlAttribute(name = "level", required = true)
    private byte level;
    @XmlAttribute(name = "name_id", required = true)
    private int nameId;
    @XmlAttribute(name = "title_id")
    private int titleId;
    @XmlAttribute(name = "name")
    private String name;
    @XmlAttribute(name = "name_desc")
    private String desc;
    @XmlAttribute(name = "height")
    private float height = 1;
    @XmlElement(name = "stats")
    private NpcStatsTemplate statsTemplate;
    @XmlElement(name = "equipment")
    private NpcEquippedGear equipment;
    @XmlElement(name = "kisk_stats")
    private KiskStatsTemplate kiskStatsTemplate;
    @XmlElement(name = "ammo_speed")
    private int ammoSpeed = 0;
    @XmlAttribute(name = "rank")
    private NpcRank rank;
    @XmlAttribute(name = "rating")
    private NpcRating rating;
    @XmlAttribute(name = "srange")
    private int aggrorange;
    @XmlAttribute(name = "arange")
    private int attackRange;
    @XmlAttribute(name = "arate")
    private int attackRate;
    @XmlAttribute(name = "adelay")
    private int attackDelay;
    @XmlAttribute(name = "hpgauge")
    private int hpGauge;
    @XmlAttribute(name = "tribe")
    private TribeClass tribe;
    @XmlAttribute(name = "ai")
    private String ai = AiNames.DUMMY_NPC.getName();
    @XmlAttribute
    private Race race = Race.NONE;
    @XmlAttribute
    private int state;
    @XmlAttribute
    private boolean floatcorpse;

    @XmlAttribute(name = "on_mist")
    private Boolean onMist;

    @XmlElement(name = "bound_radius")
    private BoundRadius boundRadius;
    @XmlAttribute(name = "type")
    private NpcTemplateType npcTemplateType;

    @XmlAttribute(name = "ui_type")
    private NpcUiType npcUiType;

    @XmlAttribute(name = "abyss_type")
    private AbyssNpcType abyssNpcType;
    @XmlElement(name = "talk_info")
    private TalkInfo talkInfo;
    @XmlTransient
    private NpcDrop npcDrop;

    @Override
    public int getTemplateId() {
        return npcId;
    }

    @Override
    public int getNameId() {
        return nameId;
    }

    public int getTitleId() {
        return titleId;
    }

    @Override
    public String getName() {
        return name;
    }

    public float getHeight() {
        return height;
    }

    public NpcEquippedGear getEquipment() {
        return equipment;
    }

    public byte getLevel() {
        return level;
    }

    public NpcStatsTemplate getStatsTemplate() {
        return statsTemplate;
    }

    public void setStatsTemplate(NpcStatsTemplate statsTemplate) {
        this.statsTemplate = statsTemplate;
    }

    public KiskStatsTemplate getKiskStatsTemplate() {
        return kiskStatsTemplate;
    }

    public TribeClass getTribe() {
        return tribe;
    }

    public String getAi() {
        // TODO: npc_template repars
        return (!"noaction".equals(ai) && level > 1 && getAbyssNpcType().equals(AbyssNpcType.TELEPORTER)) ? "siege_teleporter" : ai;
    }

    @Override
    public String toString() {
        return "Npc Template id: " + npcId + " name: " + name;
    }

    @XmlID
    @XmlAttribute(name = "npc_id", required = true)
    private void setXmlUid(String uid) {
        /*
         * This method is used only by JAXB unmarshaller. I couldn't set annotations at field, because ID must be a string.
         */
        npcId = Integer.parseInt(uid);
    }

    public final NpcRank getRank() {
        return rank;
    }

    public final NpcRating getRating() {
        return rating;
    }

    public int getAggroRange() {
        return aggrorange;
    }

    public int getMinimumShoutRange() {
        if (aggrorange < 10) {
            return 10;
        }
        return aggrorange;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(int value) {
        this.attackRange = value;
    }
    
    public void setAggroRange(int value) {
        this.aggrorange = value;
    }

    public int getAttackRate() {
        return attackRate;
    }

    public int getAttackDelay() {
        return attackDelay;
    }

    public int getHpGauge() {
        return hpGauge;
    }

    public Race getRace() {
        return race;
    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public BoundRadius getBoundRadius() {
        // TODO all npcs should have BR in xml
        return boundRadius != null ? boundRadius : super.getBoundRadius();
    }

    public NpcTemplateType getNpcTemplateType() {
        return npcTemplateType != null ? npcTemplateType : NpcTemplateType.NONE;
    }

    public NpcUiType getNpcUiType() {
        return npcUiType != null ? npcUiType : NpcUiType.NONE;
    }

    public AbyssNpcType getAbyssNpcType() {
        return abyssNpcType != null ? abyssNpcType : AbyssNpcType.NONE;
    }

    public final int getTalkDistance() {
        if (talkInfo == null) {
            return 2;
        }
        return talkInfo.getDistance();
    }

    public int getTalkDelay() {
        if (talkInfo == null) {
            return 0;
        }
        return talkInfo.getDelay();
    }

    public List<Integer> getFuncDialogIds() {
        if (talkInfo == null) {
            return null;
        }
        return talkInfo.getFuncDialogIds();
    }

    /**
     * @return the npcDrop
     */
    public NpcDrop getNpcDrop() {
        return npcDrop;
    }

    /**
     * @param npcDrop the npcDrop to set
     */
    public void setNpcDrop(NpcDrop npcDrop) {
        this.npcDrop = npcDrop;
    }

    /**
     * @return if no data is present for the talk
     */
    public boolean canInteract() {
        return talkInfo != null;
    }

    /**
     * @return the hasDialog
     */
    public boolean isDialogNpc() {
        if (talkInfo == null) {
            return false;
        }
        return talkInfo.isDialogNpc();
    }

    /**
     * @return the floatcorpse
     */
    public boolean isFloatCorpse() {
        return floatcorpse;
    }

    public Boolean getMistSpawnCondition() {
        return onMist;
    }

    public String getDesc() {
        return desc;
    }
}
