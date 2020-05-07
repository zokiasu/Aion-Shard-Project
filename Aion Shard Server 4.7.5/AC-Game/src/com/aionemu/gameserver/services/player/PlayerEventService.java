package com.aionemu.gameserver.services.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.configs.main.EventsConfig;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.knownlist.Visitor;

/**
 * @author Source
 */
public class PlayerEventService {

    private static final Logger log = LoggerFactory.getLogger(PlayerEventService.class);

    private PlayerEventService() {

        final EventCollector visitor = new EventCollector();
        ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                World.getInstance().doOnAllPlayers(visitor);
            }
        }, EventsConfig.EVENT_PERIOD * 60000, EventsConfig.EVENT_PERIOD * 60000);
    }

    private static final class EventCollector implements Visitor<Player> {

        @Override
        public void visit(Player player) {
            int membership = player.getClientConnection().getAccount().getMembership();
            int rate = EventsConfig.EVENT_REWARD_MEMBERSHIP_RATE ? membership + 1 : 1;
            if (membership >= EventsConfig.EVENT_REWARD_MEMBERSHIP) {
                try {
                    if (player.getInventory().isFull()) {
                        log.warn("[EventReward] player " + player.getName() + " tried to receive item with full inventory.");
                    } else {
                        switch (player.getCommonData().getPlayerClass()) {
                            case GLADIATOR: {
                                ItemService.addItem(player, (player.getCommonData().getRace() == Race.ELYOS ? 160010289 : 160010290), 1); //Vit Atk Phys
                            }
                            case TEMPLAR: {
                                ItemService.addItem(player, (player.getCommonData().getRace() == Race.ELYOS ? 160010289 : 160010290), 1); //Vit Atk Phys
                            }
                            case RANGER: {
                                ItemService.addItem(player, (player.getCommonData().getRace() == Race.ELYOS ? 160010289 : 160010290), 1); //Vit Atk Phys
                            }
                            case ASSASSIN: {
                                ItemService.addItem(player, (player.getCommonData().getRace() == Race.ELYOS ? 160010289 : 160010290), 1); //Vit Atk Phys
                            }
                            case SORCERER: {
                                ItemService.addItem(player, (player.getCommonData().getRace() == Race.ELYOS ? 160010291 : 160010292), 1); //Vit Inc Magic
                            }
                            case SPIRIT_MASTER: {
                                ItemService.addItem(player, (player.getCommonData().getRace() == Race.ELYOS ? 160010291 : 160010292), 1); //Vit Inc Magic
                            }
                            case CLERIC: {
                                ItemService.addItem(player, (player.getCommonData().getRace() == Race.ELYOS ? 160010291 : 160010292), 1); //Vit Inc Magic
                            }
                            case CHANTER: {
                                ItemService.addItem(player, (player.getCommonData().getRace() == Race.ELYOS ? 160010289 : 160010290), 1); //Vit Atk Phys
                            }
                            case GUNNER: {
                                ItemService.addItem(player, (player.getCommonData().getRace() == Race.ELYOS ? 160010293 : 160010294), 1); //Vit Atk Magic
                            }
                            case BARD: {
                                ItemService.addItem(player, (player.getCommonData().getRace() == Race.ELYOS ? 160010293 : 160010294), 1); //Vit Atk Magic
                            }
                            case RIDER: {
                                ItemService.addItem(player, (player.getCommonData().getRace() == Race.ELYOS ? 160010293 : 160010294), 1); //Vit Atk Magic
                            }
                            default: {
                                ItemService.addItem(player, (player.getCommonData().getRace() == Race.ELYOS ? 188052726 : 188052726), 1); //Sac de bonbon par défaut
                            }
                        }
                        //ItemService.addItem(player, (player.getRace() == Race.ELYOS ? EventsConfig.EVENT_ITEM_ELYOS : EventsConfig.EVENT_ITEM_ASMO), EventsConfig.EVENT_ITEM_COUNT * rate);
                    }
                } catch (Exception ex) {
                    log.error("Exception during event rewarding of player " + player.getName(), ex);
                }
            }
        }
    }

    ;

    public static PlayerEventService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {

        protected static final PlayerEventService instance = new PlayerEventService();
    }
}
