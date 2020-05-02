package com.aionemu.gameserver.model.templates.item.actions;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.controllers.observer.ItemUseObserver;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.teleport.ScrollItem;
import com.aionemu.gameserver.model.templates.teleport.ScrollItemLocationList;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.services.teleport.ScrollsTeleporterService;
import com.aionemu.gameserver.utils.PacketSendUtility;


/**
 * @author GiGatR00n
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MultiReturnAction")
public class MultiReturnAction extends AbstractItemAction {

    @XmlAttribute(name = "id")
    private int id;

    public int getId() {
        return id;
    }    
    
    @Override
    public boolean canAct(Player player, Item parentItem, Item targetItem) {
        return true;
    }

    @Override
    public void act(final Player player, final Item parentItem, Item targetItem) {
    }
    
    public void act(final Player player, final Item ScrollItem, final int SelectedMapIndex) {
    	
        PacketSendUtility.sendPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), ScrollItem.getObjectId(), ScrollItem.getItemTemplate().getTemplateId(), 5000, 0, 0));
        player.getController().cancelTask(TaskId.ITEM_USE);

        final ItemUseObserver observer = new ItemUseObserver() {
            @Override
            public void abort() {
                player.getController().cancelTask(TaskId.ITEM_USE);
                PacketSendUtility.sendPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), ScrollItem.getObjectId(), ScrollItem.getItemTemplate().getTemplateId(), 0, 2, 0));
                player.getObserveController().removeObserver(this);
                player.removeItemCoolDown(ScrollItem.getItemTemplate().getUseLimits().getDelayId());
            }
        };
        player.getObserveController().attach(observer);
        player.getController().addTask(TaskId.ITEM_USE, ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                player.getObserveController().removeObserver(observer);
                if (player.getInventory().decreaseByObjectId(ScrollItem.getObjectId(), 1)) {

            		int ScrollItemId = getId();
            		ScrollItem sItem = DataManager.MULTI_RETURN_ITEM_DATA.getScrollItembyId(ScrollItemId);
            		
            		if (sItem != null && sItem.getLocationList() != null) {
            			
            			ScrollItemLocationList LocData = sItem.getLocDatabyId(SelectedMapIndex);
            			if (LocData != null) {
            				
            				int LocCount = sItem.getLocationList().size();        				
           					if (SelectedMapIndex <= (LocCount - 1)) {

           						int worldId = LocData.getWorldId();
           						int LocId = ScrollsTeleporterService.getScrollLocIdbyWorldId(worldId, player.getRace());
           						
           						if (LocId == 0) {
           							LocId = Integer.parseInt(Integer.toString(worldId).substring(0 , 7));          							
           						} 						
           						ScrollsTeleporterService.ScrollTeleprter(player, LocId, worldId);
           					}
            			}
            		}
                }
                PacketSendUtility.sendPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), ScrollItem.getObjectId(), ScrollItem.getItemTemplate().getTemplateId(), 0, 1, 0));
            }
        }, 5000));    	
    }
}
