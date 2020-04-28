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
 * Please do not change here something, regarding the developer credits, except the "developed by XXXX".
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

import com.aionemu.gameserver.model.gameobjects.Gatherable;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.StaticObject;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.WorldMapType;
import com.aionemu.gameserver.world.knownlist.Visitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Luno, reworked Bobobear
 */
public class ReloadSpawn extends AdminCommand {

    private static final Logger log = LoggerFactory.getLogger("Reload");

    public ReloadSpawn() {
        super("reload_spawn");
    }

    @Override
    public void execute(Player player, String... params) {
        int worldId;

        worldId = Integer.parseInt(params[0]);
        reloadMap(worldId, player);
    }

    private void reloadMap(int worldId, Player admin) {
        final int IdWorld = worldId;
        final Player adm = admin;

        if (IdWorld != 0) {
            World.getInstance().doOnAllObjects(new Visitor<VisibleObject>() {
                @Override
                public void visit(VisibleObject object) {
                    if (object.getWorldId() == IdWorld) {
                        log.warn("object.getWorldId() : " + object.getWorldId());
                        log.warn("IdWorld : " + IdWorld);
                        if (object instanceof Npc || object instanceof Gatherable || object instanceof StaticObject) {
                            object.getController().onDelete();
                        }
                    }
                }
            });
            SpawnEngine.spawnWorldMap(IdWorld);
            PacketSendUtility.sendMessage(adm, "Spawns for map: " + IdWorld + " reloaded succesfully");
        }
    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "syntax //reload_spawn <location name | all>");
    }
}
