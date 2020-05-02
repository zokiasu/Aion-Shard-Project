package com.aionemu.gameserver.model.templates.mail;

import com.aionemu.gameserver.model.Race;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rolandas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SysMail", propOrder = {"templates"})
public class SysMail {

    @XmlElement(name = "template", required = true)
    private List<MailTemplate> templates;
    @XmlAttribute(name = "name", required = true)
    private String name;
    @XmlTransient
    private Map<String, List<MailTemplate>> mailCaseTemplates = new HashMap<String, List<MailTemplate>>();

    void afterUnmarshal(Unmarshaller u, Object parent) {
        for (MailTemplate template : templates) {
            String caseName = template.getName().toLowerCase();
            List<MailTemplate> sysTemplates = mailCaseTemplates.get(caseName);
            if (sysTemplates == null) {
                sysTemplates = new ArrayList<MailTemplate>();
                mailCaseTemplates.put(caseName, sysTemplates);
            }
            sysTemplates.add(template);
        }
        templates.clear();
        templates = null;
    }

    public MailTemplate getTemplate(String eventName, Race playerRace) {
        List<MailTemplate> sysTemplates = mailCaseTemplates.get(eventName.toLowerCase());
        if (sysTemplates == null) {
            return null;
        }
        for (MailTemplate template : sysTemplates) {
            if (template.getRace() == playerRace || template.getRace() == Race.PC_ALL) {
                return template;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
