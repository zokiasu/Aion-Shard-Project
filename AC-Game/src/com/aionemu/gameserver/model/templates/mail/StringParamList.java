package com.aionemu.gameserver.model.templates.mail;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rolandas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StringParamList", propOrder = {"param"})
@XmlSeeAlso({MailPart.class})
public class StringParamList {

    protected List<StringParamList.Param> param;

    public List<StringParamList.Param> getParam() {
        if (param == null) {
            param = new ArrayList<StringParamList.Param>();
        }
        return this.param;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Param {

        @XmlAttribute(name = "id", required = true)
        protected String id;

        public String getId() {
            return id;
        }
    }
}
