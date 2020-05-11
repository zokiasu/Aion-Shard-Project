package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.commons.objects.filter.ObjectFilter;
import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.configs.main.LoggingConfig;
import com.aionemu.gameserver.model.ChatType;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MESSAGE;
import com.aionemu.gameserver.restrictions.RestrictionsManager;
import com.aionemu.gameserver.services.NameRestrictionService;
import com.aionemu.gameserver.services.player.PlayerChatService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.audit.AuditLogger;
import com.aionemu.gameserver.utils.chathandlers.ChatProcessor;
import com.aionemu.gameserver.utils.stats.AbyssRankEnum;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.world.knownlist.Visitor;

/**
 * Packet that reads normal chat messages.<br>
 *
 * @author SoulKeeper
 */
public class CM_CHAT_MESSAGE_PUBLIC extends AionClientPacket {

    /**
     * Chat type
     */
    private ChatType type;
    /**
     * Chat message
     */
    private String message;

    public CM_CHAT_MESSAGE_PUBLIC(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        type = ChatType.getChatTypeByInt(readC());

        message = readS();
    }

    @Override
    protected void runImpl() {

        final Player player = getConnection().getActivePlayer();

        if (ChatProcessor.getInstance().handleChatCommand(player, message)) {
            return;
        }

        message = NameRestrictionService.filterMessage(message);

        if (LoggingConfig.LOG_CHAT) {
            PlayerChatService.chatLogging(player, type, message);
        }

        if (RestrictionsManager.canChat(player) && !PlayerChatService.isFlooding(player)) {
            switch (this.type) {
                case GROUP:
                    if (!player.isInTeam()) {
                        return;
                    }
                    broadcastToGroupMembers(player);
                    break;
                case ALLIANCE:
                    if (!player.isInAlliance2()) {
                        return;
                    }
                    broadcastToAllianceMembers(player);
                    break;
                case GROUP_LEADER:
                    if (!player.isInTeam()) {
                        return;
                    }
                    // Alert must go to entire group or alliance.
                    if (player.isInGroup2()) {
                        broadcastToGroupMembers(player);
                    } else {
                        broadcastToAllianceMembers(player);
                    }
                    break;
                case LEGION:
                    broadcastToLegionMembers(player);
                    break;
                case LEAGUE:
                case LEAGUE_ALERT:
                    if (!player.isInLeague()) {
                        return;
                    }
                    broadcastToLeagueMembers(player);
                    break;
                case NORMAL:
                case SHOUT:
                    if (player.isGM()) {
                        broadcastFromGm(player);
                    } else {
                        if (CustomConfig.SPEAKING_BETWEEN_FACTIONS) {
                            broadcastToNonBlockedPlayers(player);
                        } else {
                            broadcastToNonBlockedRacePlayers(player);
                        }
                    }
                    break;
                case COMMAND:
                    if (player.getAbyssRank().getRank() == AbyssRankEnum.COMMANDER || player.getAbyssRank().getRank() == AbyssRankEnum.SUPREME_COMMANDER) {
                        broadcastFromCommander(player);
                    }
                    break;
                default:
                    if (player.isGM()) {
                        broadcastFromGm(player);
                    } else {
                        AuditLogger.info(player, String.format("Send message type %s. Message: %s", type, message));
                    }
                    break;
            }
        }
    }

    private void broadcastFromCommander(final Player _actor) {    
		if (_actor.getRace() == Race.ELYOS) {            
		World.getInstance().doOnAllPlayers(new Visitor<Player>() {                
		@Override               
		public void visit(final Player _actore) {               
		if (_actore.getRace() == Race.ELYOS || _actore.isGM()) {  
		if (_actore.getWorldId() == _actor.getWorldId() && _actore.getRace() == _actor.getRace()) { 
		Player _sendere =  (Player) _actor;  
		PacketSendUtility.broadcastPacket(_actore, new SM_MESSAGE(_sendere, message, type), true);            
		}      
		}                    
		}            
		});        
		}    
		if (_actor.getRace() == Race.ASMODIANS) {            
		World.getInstance().doOnAllPlayers(new Visitor<Player>() {                
		@Override                
		public void visit(final Player _actora) {                
		if (_actora.getRace() == Race.ASMODIANS || _actora.isGM()) {           
		if (_actora.getWorldId() == _actor.getWorldId() && _actora.getRace() == _actor.getRace()) {      
		Player _sendera = (Player) _actor;
		PacketSendUtility.broadcastPacket(_actora, new SM_MESSAGE(_sendera, message, type), true);        
		}            
		}                
		}            
		});        
		}    
		}

    /**
     * Sends message to all players from admin
     *
     * @param player
     */
    private void broadcastFromGm(final Player player) {
        PacketSendUtility.broadcastPacket(player, new SM_MESSAGE(player, message, type), true);
    }

    /**
     * Sends message to all players that are not in blocklist
     *
     * @param player
     */
    private void broadcastToNonBlockedPlayers(final Player player) {
        PacketSendUtility.broadcastPacket(player, new SM_MESSAGE(player, message, type), true, new ObjectFilter<Player>() {
            @Override
            public boolean acceptObject(Player object) {
                return !object.getBlockList().contains(player.getObjectId());
            }
        });
    }

    /**
     * Sends message to races members (other race will receive an unknown
     * message)
     *
     * @param player
     */
    private void broadcastToNonBlockedRacePlayers(final Player player) {
        final int senderRace = player.getRace().getRaceId();
        PacketSendUtility.broadcastPacket(player, new SM_MESSAGE(player, message, type), true, new ObjectFilter<Player>() {
            @Override
            public boolean acceptObject(Player object) {
                return ((senderRace == object.getRace().getRaceId() && !object.getBlockList().contains(player.getObjectId())) || object.isGM());
            }
        });
        PacketSendUtility.broadcastPacket(player, new SM_MESSAGE(player, "Unknow Message", type), false, new ObjectFilter<Player>() {
            @Override
            public boolean acceptObject(Player object) {
                return senderRace != object.getRace().getRaceId() && !object.getBlockList().contains(player.getObjectId()) && !object.isGM();
            }
        });
    }

    /**
     * Sends message to all group members (regular player group, or alliance
     * sub-group Error 105, random value for players to report. Should never
     * happen.
     *
     * @param player
     */
    private void broadcastToGroupMembers(final Player player) {
        if (player.isInTeam()) {
            player.getCurrentGroup().sendPacket(new SM_MESSAGE(player, message, type));
        } else {
            PacketSendUtility.sendMessage(player, "You are not in an alliance or group. (Error 105)");
        }
    }

    /**
     * Sends message to all alliance members
     *
     * @param player
     */
    private void broadcastToAllianceMembers(final Player player) {
        player.getPlayerAlliance2().sendPacket(new SM_MESSAGE(player, message, type));
    }

    /**
     * Sends message to all league members
     *
     * @param player
     */
    private void broadcastToLeagueMembers(final Player player) {
        player.getPlayerAlliance2().getLeague().sendPacket(new SM_MESSAGE(player, message, type));
    }

    /**
     * Sends message to all legion members
     *
     * @param player
     */
    private void broadcastToLegionMembers(final Player player) {
        if (player.isLegionMember()) {
            PacketSendUtility.broadcastPacketToLegion(player.getLegion(), new SM_MESSAGE(player, message, type));
        }
    }
}
