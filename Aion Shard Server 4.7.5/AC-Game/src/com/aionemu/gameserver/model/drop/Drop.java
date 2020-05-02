package com.aionemu.gameserver.model.drop;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Set;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;

/**
 * @author MrPoke
 */
public class Drop implements DropCalculator {

    private int itemId;
    private int minAmount;
    private int maxAmount;
    private float chance;
    private boolean noReduce = false;
    private boolean eachMember = false;
    private ItemTemplate template;

    public Drop(int itemId, int minAmount, int maxAmount, float chance, boolean noReduce) {
        this.itemId = itemId;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.chance = chance;
        this.noReduce = noReduce;
        template = DataManager.ITEM_DATA.getItemTemplate(itemId);
    }

  	public Drop(int itemId, int minAmount, int maxAmount, float chance, boolean noReduce, boolean eachMember) {
    	this.itemId = itemId;
    	this.minAmount = minAmount;
    	this.maxAmount = maxAmount;
    	this.chance = chance;
    	this.noReduce = noReduce;
    	this.eachMember = eachMember;
  	}
    /**
     *
     */
    public Drop() {
    }

    public ItemTemplate getItemTemplate() {
        return template == null ? DataManager.ITEM_DATA.getItemTemplate(itemId) : template;
    }

    /**
     * Gets the value of the itemId property.
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * Gets the value of the minAmount property.
     */
    public int getMinAmount() {
        return minAmount;
    }

    /**
     * Gets the value of the maxAmount property.
     */
    public int getMaxAmount() {
        return maxAmount;
    }

    /**
     * Gets the value of the chance property.
     */
    public float getChance() {
        return chance;
    }

    public boolean isNoReduction() {
        return noReduce;
    }

    public Boolean isEachMember() {
        return eachMember;
    }

    @Override
    public int dropCalculator(Set<DropItem> result, int index, float dropModifier, Race race, Collection<Player> groupMembers) {
        float percent = chance;
        if (!noReduce) {
            percent *= dropModifier;
        }
        if (Rnd.get() * 100 < percent) {
            if (eachMember && groupMembers != null && !groupMembers.isEmpty()) {
                for (Player player : groupMembers) {
                    DropItem dropitem = new DropItem(this);
                    dropitem.calculateCount();
                    dropitem.setIndex(index++);
                    dropitem.setPlayerObjId(player.getObjectId());
                    dropitem.setWinningPlayer(player);
                    dropitem.isDistributeItem(true);
                    result.add(dropitem);
                }
            } else {
                DropItem dropitem = new DropItem(this);
                dropitem.calculateCount();
                dropitem.setIndex(index++);
                result.add(dropitem);
            }
        }
        return index;
    }

	public static Drop load(ByteBuffer buffer) {
		Drop drop = new Drop();
		drop.itemId = buffer.getInt();
		drop.chance = buffer.getFloat();
		drop.minAmount = buffer.getInt();
		drop.maxAmount = buffer.getInt();
		drop.noReduce = buffer.get() == 1;
		drop.eachMember = buffer.get() == 1;
		return drop;
	}

    @Override
    public String toString() {
        return "Drop [itemId=" + itemId + ", minAmount=" + minAmount + ", maxAmount=" + maxAmount + ", chance=" + chance
                + ", noReduce=" + noReduce + ", eachMember=" + eachMember + "]";
    }
}
