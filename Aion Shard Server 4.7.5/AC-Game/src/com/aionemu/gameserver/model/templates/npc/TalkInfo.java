package com.aionemu.gameserver.model.templates.npc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * @author Rolandas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TalkInfo")
public class TalkInfo {

    @XmlAttribute(name = "distance")
    private int talkDistance = 2;
    @XmlAttribute(name = "delay")
    private int talkDelay;
    @XmlAttribute(name = "is_dialog")
    private boolean hasDialog;
    @XmlAttribute(name = "func_dialogs")
    private List<Integer> funcDialogIds;

    /**
     * @return the talkDistance
     */
    public int getDistance() {
        return talkDistance;
    }

    /**
     * @return the talk_delay
     */
    public int getDelay() {
        return talkDelay;
    }

    /**
     * @return the hasDialog
     */
    public boolean isDialogNpc() {
        return hasDialog;
    }

    public List<Integer> getFuncDialogIds() {
        return funcDialogIds;
    }
}
