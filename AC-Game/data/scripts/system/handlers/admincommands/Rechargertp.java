/**
 * This file is part of Aion-Lightning <aion-lightning.org>.
 *
 *  Aion-Lightning is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Aion-Lightning is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details. *
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Aion-Lightning.
 *  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * Credits goes to all Open Source Core Developer Groups listed below
 * Please do not change here something, ragarding the developer credits, except the "developed by XXXX".
 * Even if you edit a lot of files in this source, you still have no rights to call it as "your Core".
 * Everybody knows that this Emulator Core was developed by Aion Lightning
 * @-Aion-Unique-
 * @-Aion-Lightning
 * @Aion-Engine
 * @Aion-Extreme
 * @Aion-NextGen
 * @Aion-Core Dev.
 */
package admincommands;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.spawns.SpawnTemplate;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;

import java.util.Collection;

/**
 * Created by Kill3r
 */
public class Rechargertp extends AdminCommand {

    public Rechargertp(){
        super("rechargertp");
    }

    private static boolean isOpened = false;

    public void execute(Player player, String...params){
        if (params.length == 0){
            PacketSendUtility.sendMessage(player, "Syntax : .rechargertp on/off");
            return;
        }

        int RechargerID = 730397;
        if(params[0].equals("off")){
            if(isOpened){
                Collection<Npc> recharger = World.getInstance().getNpcs();
                for(Npc n : recharger){
                    if(n.getNpcId() == RechargerID){
                        n.getController().delete();
                    }
                }
                PacketSendUtility.sendMessage(player, "Recharger Closing!");
                isOpened = false;
            }
        } else if(params[0].equals("on")){
            float x = 503.57892f;
            float y = 204.84465f;
            float z = 67.27632f;
            byte heading = 2;
            int worldId = 320150000;
            if(!isOpened){
                SpawnTemplate spawn = SpawnEngine.addNewSpawn(worldId, RechargerID, x, y, z, heading, 0);
                VisibleObject visibleObject = SpawnEngine.spawnObject(spawn, player.getInstanceId());
                PacketSendUtility.sendMessage(player, visibleObject.getName() + " has been Summoned!");
                isOpened = true;
            }else{
                PacketSendUtility.sendMessage(player, "Already Open");
            }
        } else {
            PacketSendUtility.sendMessage(player, "Syntax : .rechargertp on/off");
            return;
        }
    }
}