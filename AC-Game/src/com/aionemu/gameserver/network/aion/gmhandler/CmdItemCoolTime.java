package com.aionemu.gameserver.network.aion.gmhandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.aionemu.gameserver.model.gameobjects.HouseObject;
import com.aionemu.gameserver.model.gameobjects.UseableItemObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.items.ItemCooldown;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_COOLDOWN;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SKILL_COOLDOWN;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class CmdItemCoolTime extends AbstractGMHandler {

    public CmdItemCoolTime(Player admin) {
        super(admin, "");
        run();
    }

    private void run() {
        Player playerT = target != null ? target : admin;

        List<Integer> delayIds = new ArrayList<Integer>();
        if (playerT.getSkillCoolDowns() != null) {
            long currentTime = System.currentTimeMillis();
            for (Entry<Integer, Long> en : playerT.getSkillCoolDowns().entrySet()) {
                delayIds.add(en.getKey());
            }
            for (Integer delayId : delayIds) {
                playerT.setSkillCoolDown(delayId, currentTime);
            }
            delayIds.clear();
            PacketSendUtility.sendPacket(playerT, new SM_SKILL_COOLDOWN(playerT.getSkillCoolDowns()));
        }

        if (playerT.getItemCoolDowns() != null) {
            for (Entry<Integer, ItemCooldown> en : playerT.getItemCoolDowns().entrySet()) {
                delayIds.add(en.getKey());
            }
            for (Integer delayId : delayIds) {
                playerT.addItemCoolDown(delayId, 0, 0);
            }
            delayIds.clear();
            PacketSendUtility.sendPacket(playerT, new SM_ITEM_COOLDOWN(playerT.getItemCoolDowns()));
        }

        if (playerT.getHouseRegistry() != null && playerT.getHouseObjectCooldownList().getHouseObjectCooldowns().size() > 0) {
            Iterator<HouseObject<?>> iter = playerT.getHouseRegistry().getObjects().iterator();
            while (iter.hasNext()) {
                HouseObject<?> obj = iter.next();
                if (obj instanceof UseableItemObject) {
                    if (!playerT.getHouseObjectCooldownList().isCanUseObject(obj.getObjectId()))
                        playerT.getHouseObjectCooldownList().addHouseObjectCooldown(obj.getObjectId(), 0);
                }
            }
        }

    }

}
