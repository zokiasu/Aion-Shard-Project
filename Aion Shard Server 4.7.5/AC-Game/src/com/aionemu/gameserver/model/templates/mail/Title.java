package com.aionemu.gameserver.model.templates.mail;

import javax.xml.bind.annotation.*;

/**
 * @author Rolandas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Title")
@XmlSeeAlso({com.aionemu.gameserver.model.templates.mail.MailPart.class})
public class Title extends MailPart {

    @XmlAttribute(name = "type")
    protected MailPartType type;

    @Override
    public MailPartType getType() {
        if (type == null) {
            return MailPartType.TITLE;
        }
        return type;
    }

    @Override
    public String getParamValue(String name) {
        return "";
    }
}
