package instance;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.SkillTemplate;
import com.aionemu.gameserver.world.zone.ZoneInstance;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * @author Eloann
 */
@InstanceID(310010000)
public class KaramatisInstance extends GeneralInstanceHandler {

    @Override
    public void onEnterZone(Player player, ZoneInstance zone) {
        if (zone.getAreaTemplate().getZoneName() == ZoneName.get("AFIRA_OBELISK_310010000")) {
            belpartanBlessing();
        }
    }

    private void belpartanBlessing() {
        for (Player p : instance.getPlayersInside()) {
            SkillTemplate st = DataManager.SKILL_DATA.getSkillTemplate(1910); //Belpartan's Blessing.
            Effect e = new Effect(p, p, st, 1, st.getEffectsDuration(9));
            e.initialize();
            e.applyEffect();
        }
    }
}
