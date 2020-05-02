package com.aionemu.gameserver.model.gameobjects;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.house.House;
import com.aionemu.gameserver.model.templates.housing.HousingStorage;
import com.aionemu.gameserver.network.aion.serverpackets.SM_OBJECT_USE_UPDATE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_WAREHOUSE_INFO;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Rolandas
 */
public final class StorageObject extends HouseObject<HousingStorage> {

    public StorageObject(House owner, int objId, int templateId) {
        super(owner, objId, templateId);
    }

    @Override
    public void onUse(Player player) {
        if (player.getObjectId() != this.getOwnerHouse().getOwnerId()) {
            PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_HOUSING_OBJECT_IS_ONLY_FOR_OWNER_VALID);
            return;
        }
        PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_HOUSING_OBJECT_USE(getObjectTemplate().getNameId()));
        PacketSendUtility.sendPacket(player, new SM_OBJECT_USE_UPDATE(player.getObjectId(), 0, 0, this));

        for (HouseObject<?> ho : getOwnerHouse().getRegistry().getSpawnedObjects()) {
            if (ho instanceof StorageObject) {
                int warehouseId = ((HousingStorage) ho.getObjectTemplate()).getWarehouseId() + 59;
                PacketSendUtility.sendPacket(player, new SM_WAREHOUSE_INFO(player.getStorage(warehouseId).getItemsWithKinah(),
                        warehouseId, 0, true, player));
                PacketSendUtility.sendPacket(player, new SM_WAREHOUSE_INFO(null, warehouseId, 0, false, player));
            }
        }
    }

    @Override
    public boolean canExpireNow() {
        // FIXME: if player is using mailbox, should not expire immediately
        return false;
    }
}
