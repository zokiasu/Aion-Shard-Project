package com.aionemu.gameserver.model.templates.event;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EventDrops")
public class EventDrops {

    @XmlElement(name = "event_drop")
    protected List<EventDrop> eventDrops;

    public List<EventDrop> getEventDrops() {
        if (eventDrops == null) {
            eventDrops = new ArrayList<EventDrop>();
        }
        return this.eventDrops;
    }
}
