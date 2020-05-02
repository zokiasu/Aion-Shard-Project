package com.aionemu.gameserver.model.broker.filter;

import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import org.apache.commons.lang.ArrayUtils;

/**
 * @author ATracer
 */
public class BrokerContainsExtraFilter extends BrokerFilter {

    private int[] masks;

    /**
     * @param masks
     */
    public BrokerContainsExtraFilter(int... masks) {
        this.masks = masks;
    }

    @Override
    public boolean accept(ItemTemplate template) {
        return ArrayUtils.contains(masks, template.getTemplateId() / 10000);
    }
}
