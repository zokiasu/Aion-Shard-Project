package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.ai.Ai;
import com.aionemu.gameserver.model.templates.ai.AITemplate;
import javolution.util.FastMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author xTz
 */
@XmlRootElement(name = "ai_templates")
@XmlAccessorType(XmlAccessType.FIELD)
public class AIData {

    @XmlElement(name = "ai", type = Ai.class)
    private List<Ai> templates;
    private FastMap<Integer, AITemplate> aiTemplate = new FastMap<Integer, AITemplate>();

    /**
	 * @param u  
     * @param parent 
	 */
    void afterUnmarshal(Unmarshaller u, Object parent) {
        aiTemplate.clear();
        for (Ai template : templates) {
            aiTemplate.put(template.getNpcId(), new AITemplate(template));
        }
    }

    public int size() {
        return aiTemplate.size();
    }

    public FastMap<Integer, AITemplate> getAiTemplate() {
        return aiTemplate;
    }
}
