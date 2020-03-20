package playercommands;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.configs.main.EventSystem;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.utils3d.Point3D;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
import com.aionemu.gameserver.services.instance.InstanceService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.skillengine.effect.AbnormalState;
import com.aionemu.gameserver.skillengine.model.SkillTargetSlot;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.WorldPosition;
import com.aionemu.gameserver.world.knownlist.Visitor;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TRANSFORM;
import com.aionemu.gameserver.utils.PacketSendUtility;

import java.util.HashMap;
import java.util.Map;

public class cmd_skinclear extends PlayerCommand {
    public cmd_skinclear(){
        super("skinclear");
    }

    @Override
    public void execute(Player player, String... params) {
        player.getTransformModel().setModelId(0);
        PacketSendUtility.broadcastPacketAndReceive(player, new SM_TRANSFORM(player, true));
        PacketSendUtility.sendMessage(player, "Skin successfully cancelled.");
    }

    @Override
    public void onFail(Player player, String message) {
        // TODO Auto-generated method stub
    }
}