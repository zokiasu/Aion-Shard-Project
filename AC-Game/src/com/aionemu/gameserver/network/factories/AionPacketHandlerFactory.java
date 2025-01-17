package com.aionemu.gameserver.network.factories;

import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.AionPacketHandler;
import com.aionemu.gameserver.network.aion.clientpackets.*;

/**
 * This factory is responsible for creating {@link AionPacketHandler} object. It also initializes created handler with a
 * set of packet prototypes.<br>
 * Object of this classes uses <tt>Injector</tt> for injecting dependencies into prototype objects.<br>
 * <br>
 *
 * @author Luno, Ever
 * @author GiGatR00n v4.7.5.x
 */
public class AionPacketHandlerFactory {

    private AionPacketHandler handler;

    public static AionPacketHandlerFactory getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * Creates new instance of <tt>AionPacketHandlerFactory</tt><br>
     */
    public AionPacketHandlerFactory() {

        handler = new AionPacketHandler();
        
        ////////////////////////////4.7.5////////////////////////////
        addPacket(new CM_ABYSS_RANKING_LEGIONS(0x158, State.IN_GAME));//
        addPacket(new CM_ABYSS_RANKING_PLAYERS(0x19E, State.IN_GAME));//
        addPacket(new CM_ATTACK(0xE2, State.IN_GAME));//
        addPacket(new CM_BUY_ITEM(0x115, State.IN_GAME));//
        addPacket(new CM_CASTSPELL(0xE3, State.IN_GAME));//
        addPacket(new CM_CHARACTER_LIST(0x178, State.AUTHED));//
        addPacket(new CM_CHARACTER_PASSKEY(0x1B4, State.AUTHED));//
        addPacket(new CM_CHAT_AUTH(0x170, State.IN_GAME));//
        addPacket(new CM_CHAT_MESSAGE_PUBLIC(0xFD, State.IN_GAME));//
        addPacket(new CM_CHECK_MAIL_SIZE(0x127, State.IN_GAME));//
        addPacket(new CM_CHECK_MAIL_SIZE2(0x1B7, State.IN_GAME));//
        addPacket(new CM_DIALOG_SELECT(0x118, State.IN_GAME));//
        addPacket(new CM_EMOTION(0xCD, State.IN_GAME));//
        addPacket(new CM_ENTER_WORLD(0xAA, State.AUTHED));//
        addPacket(new CM_EQUIP_ITEM(0xC8, State.IN_GAME));//
        addPacket(new CM_CLOSE_DIALOG(0x117, State.IN_GAME));//
        addPacket(new CM_FIND_GROUP(0x2EF, State.IN_GAME));//
        addPacket(new CM_FRIEND_STATUS(0x14C, State.IN_GAME));//
        addPacket(new CM_GM_BOOKMARK(0xCB, State.IN_GAME));//
        addPacket(new CM_INSTANCE_INFO(0x182, State.IN_GAME));//
        addPacket(new CM_L2AUTH_LOGIN_CHECK(0x177, State.CONNECTED));//
        addPacket(new CM_LEGION(0xCF, State.IN_GAME));//
        addPacket(new CM_LEGION_SEND_EMBLEM(0xF1, State.IN_GAME));//
        addPacket(new CM_LEGION_SEND_EMBLEM_INFO(0xD2, State.IN_GAME));//
        addPacket(new CM_LEVEL_READY(0xAB, State.IN_GAME));//
        addPacket(new CM_LOOT_ITEM(0x17D, State.IN_GAME));//
        addPacket(new CM_MAC_ADDRESS(0x19F, State.CONNECTED, State.AUTHED, State.IN_GAME));//
        addPacket(new CM_MAY_LOGIN_INTO_GAME(0x19C, State.AUTHED));//
        addPacket(new CM_MOVE(0xF2, State.IN_GAME));//
        addPacket(new CM_MOVE_ITEM(0x17E, State.IN_GAME));//
        addPacket(new CM_PING(0xCE, State.AUTHED, State.IN_GAME));//
        addPacket(new CM_PRIVATE_STORE(0x159, State.IN_GAME));//
        addPacket(new CM_PRIVATE_STORE_NAME(0x15A, State.IN_GAME));//
        addPacket(new CM_QUESTIONNAIRE(0x153, State.IN_GAME));//
        addPacket(new CM_QUIT(0xA5, State.AUTHED, State.IN_GAME));//
        addPacket(new CM_SEND_MAIL(0x126, State.IN_GAME));//
        addPacket(new CM_SHOW_BLOCKLIST(0x160, State.IN_GAME));//
        addPacket(new CM_SHOW_DIALOG(0x116, State.IN_GAME));//
        addPacket(new CM_SHOW_FRIENDLIST(0x188, State.IN_GAME));//
        addPacket(new CM_SHOW_MAP(0x166, State.IN_GAME));//
        addPacket(new CM_SPLIT_ITEM(0x17F, State.IN_GAME));//
        addPacket(new CM_START_LOOT(0x17C, State.IN_GAME));//
        addPacket(new CM_TARGET_SELECT(0xE1, State.IN_GAME));//
        addPacket(new CM_TELEPORT_DONE(0xD1, State.IN_GAME));//
        addPacket(new CM_TELEPORT_SELECT(0x176, State.IN_GAME));//
        addPacket(new CM_TIME_CHECK(0xF4, State.CONNECTED, State.AUTHED, State.IN_GAME));//
        addPacket(new CM_TOGGLE_SKILL_DEACTIVATE(0xC4, State.IN_GAME));//
        addPacket(new CM_UI_SETTINGS(0xAC, State.IN_GAME));//
        addPacket(new CM_USE_CHARGE_SKILL(0x18C, State.IN_GAME));//
        addPacket(new CM_USE_ITEM(0xC7, State.IN_GAME));//
        addPacket(new CM_VERSION_CHECK(0xC2, State.CONNECTED));//
        addPacket(new CM_HOTSPOT_TELEPORT(0x1D6, State.IN_GAME));//
        addPacket(new CM_SERVER_CHECK(0x199, State.CONNECTED, State.IN_GAME));//
        addPacket(new CM_REQUEST_BEGINNER_SERVER(0x1BA, State.IN_GAME));//
        addPacket(new CM_CLIENT_COMMAND_LOC(0x12F, State.IN_GAME));//
        addPacket(new CM_AUTO_GROUP(0x16A, State.IN_GAME));//?
        addPacket(new CM_BLOCK_ADD(0x148, State.IN_GAME));//
        addPacket(new CM_BLOCK_DEL(0x149, State.IN_GAME));//
        addPacket(new CM_BLOCK_SET_REASON(0x195, State.IN_GAME));//
        addPacket(new CM_BONUS_TITLE(0x18B, State.IN_GAME));//
        addPacket(new CM_BREAK_WEAPONS(0x191, State.IN_GAME));//
        addPacket(new CM_CHANGE_CHANNEL(0x14E, State.IN_GAME));//
        addPacket(new CM_CLIENT_COMMAND_ROLL(0x10D, State.IN_GAME));//
        addPacket(new CM_COMPOSITE_STONES(0x194, State.IN_GAME));//
        addPacket(new CM_CREATE_CHARACTER(0x179, State.AUTHED));//
        addPacket(new CM_DELETE_CHARACTER(0x17A, State.AUTHED));//
        addPacket(new CM_DELETE_QUEST(0x112, State.IN_GAME));//
        addPacket(new CM_FRIEND_ADD(0x131, State.IN_GAME));//
        addPacket(new CM_FRIEND_DEL(0x132, State.IN_GAME));//
        addPacket(new CM_FUSION_WEAPONS(0x190, State.IN_GAME));//
        addPacket(new CM_GATHER(0xF5, State.IN_GAME));//
        addPacket(new CM_GET_HOUSE_BIDS(0x1BC, State.IN_GAME));//
        addPacket(new CM_HOUSE_OPEN_DOOR(0x184, State.IN_GAME));//
        addPacket(new CM_LEGION_WH_KINAH(0x2EE, State.IN_GAME));//
        addPacket(new CM_MACRO_CREATE(0x171, State.IN_GAME));//
        addPacket(new CM_MACRO_DELETE(0x172, State.IN_GAME));//
        addPacket(new CM_MANASTONE(0x2EC, State.IN_GAME));//
        addPacket(new CM_MAY_QUIT(0xA6, State.AUTHED, State.IN_GAME));//
        addPacket(new CM_MOVE_IN_AIR(0xF3, State.IN_GAME));//
        addPacket(new CM_OBJECT_SEARCH(0xAD, State.IN_GAME));//
        addPacket(new CM_OPEN_STATICDOOR(0xF9, State.IN_GAME));//
        addPacket(new CM_PET(0xF8, State.IN_GAME));//
        addPacket(new CM_PING_INGAME(0x31, State.IN_GAME));//
        addPacket(new CM_PLACE_BID(0x1BF, State.IN_GAME));//
        addPacket(new CM_HOUSE_TELEPORT_BACK(0x121, State.IN_GAME));//
        addPacket(new CM_PLAYER_SEARCH(0x161, State.IN_GAME));//
        addPacket(new CM_PLAYER_STATUS_INFO(0x122, State.IN_GAME));//
        addPacket(new CM_QUEST_SHARE(0x146, State.IN_GAME));//
        addPacket(new CM_PLAYER_LISTENER(0xCA, State.IN_GAME));//
        addPacket(new CM_QUESTION_RESPONSE(0x114, State.IN_GAME));//
        addPacket(new CM_RECONNECT_AUTH(0x196, State.AUTHED));//?
        addPacket(new CM_REVIVE(0xA7, State.IN_GAME));//
        addPacket(new CM_TITLE_SET(0x12D, State.IN_GAME));//
        addPacket(new CM_SHOW_BRAND(0x197, State.IN_GAME));//
        addPacket(new CM_GAMEGUARD(0x10A, State.IN_GAME));//
        addPacket(new CM_ITEM_PURIFICATION(0x1D9, State.IN_GAME));//
        addPacket(new CM_FATIGUE_RECOVER(0x135, State.IN_GAME));//
        addPacket(new CM_LOGIN_REWARD(0x1DA, State.IN_GAME));//
        addPacket(new CM_UPGRADE_ARCADE(0x1D8, State.IN_GAME));//
        addPacket(new CM_DIRECT_ENTER_WORLD(0x1B9, State.IN_GAME));//
        addPacket(new CM_CHALLENGE_LIST(0x18A, State.IN_GAME));//
        addPacket(new CM_CUSTOM_SETTINGS(0xAE, State.IN_GAME));//
        addPacket(new CM_HOUSE_EDIT(0x134, State.IN_GAME));//
        addPacket(new CM_HOUSE_KICK(0x2EA, State.IN_GAME));//
        addPacket(new CM_HOUSE_PAY_RENT(0x1A1, State.IN_GAME));//
        addPacket(new CM_HOUSE_SCRIPT(0x111, State.IN_GAME));//
        addPacket(new CM_HOUSE_SETTINGS(0x2EB, State.IN_GAME));//
        addPacket(new CM_HOUSE_TELEPORT(0x1A0, State.IN_GAME));//
		addPacket(new CM_CRAFT(0x12F, State.IN_GAME));
		addPacket(new CM_CHARACTER_EDIT(0xA9, State.AUTHED));
		addPacket(new CM_CHARGE_ITEM(0x110, State.IN_GAME));
		addPacket(new CM_CHAT_GROUP_INFO(0x11F, State.IN_GAME));
		addPacket(new CM_CHAT_MESSAGE_WHISPER(0xFE, State.IN_GAME));
		addPacket(new CM_CHAT_PLAYER_INFO(0xC9, State.IN_GAME));
		addPacket(new CM_CHECK_NICKNAME(0x173, State.AUTHED));
		addPacket(new CM_DELETE_ITEM(0x156, State.IN_GAME));
		addPacket(new CM_DELETE_MAIL(0x12B, State.IN_GAME));
		addPacket(new CM_DISTRIBUTION_SETTINGS(0x19B, State.IN_GAME));
		addPacket(new CM_EXCHANGE_ADD_ITEM(0x102, State.IN_GAME));
		addPacket(new CM_EXCHANGE_ADD_KINAH(0x2E4, State.IN_GAME));
		addPacket(new CM_EXCHANGE_CANCEL(0x2E7, State.IN_GAME));
		addPacket(new CM_EXCHANGE_LOCK(0x2E5, State.IN_GAME));
		addPacket(new CM_EXCHANGE_OK(0x2E6, State.IN_GAME));
		addPacket(new CM_EXCHANGE_REQUEST(0x101, State.IN_GAME));
		addPacket(new CM_DUEL_REQUEST(0x154, State.IN_GAME));
		addPacket(new CM_LEGION_MODIFY_EMBLEM(0x11D, State.IN_GAME));
		addPacket(new CM_LEGION_UPLOAD_EMBLEM(0x163, State.IN_GAME));
		addPacket(new CM_LEGION_UPLOAD_INFO(0x162, State.IN_GAME));
		addPacket(new CM_MEGAPHONE(0x18F, State.IN_GAME));
		addPacket(new CM_VIEW_PLAYER_DETAILS(0x106, State.IN_GAME));
		addPacket(new CM_GM_COMMAND_SEND(0xCC, State.IN_GAME));
		addPacket(new CM_PET_EMOTE(0xF7, State.IN_GAME));
		addPacket(new CM_READ_EXPRESS_MAIL(0x144, State.IN_GAME));
		addPacket(new CM_RECIPE_DELETE(0x13B, State.IN_GAME));
		addPacket(new CM_SUBZONE_CHANGE(0x145, State.IN_GAME));
		addPacket(new CM_STOP_TRAINING(0x136, State.IN_GAME));
		addPacket(new CM_INVITE_TO_GROUP(0x123, State.IN_GAME));
		addPacket(new CM_BUY_TRADE_IN_TRADE(0x13A, State.IN_GAME));
		addPacket(new CM_HOUSE_DECORATE(0x2ED, State.IN_GAME));
		addPacket(new CM_REGISTER_HOUSE(0x1BD, State.IN_GAME));
		addPacket(new CM_RESTORE_CHARACTER(0x17B, State.AUTHED));
		addPacket(new CM_WINDSTREAM(0x2E8, State.IN_GAME));
		addPacket(new CM_PLAY_MOVIE_END(0x113, State.IN_GAME));
		addPacket(new CM_REMOVE_ALTERED_STATE(0xC5, State.IN_GAME));
		addPacket(new CM_GET_MAIL_ATTACHMENT(0x12A, State.IN_GAME));
		addPacket(new CM_GROUP_DISTRIBUTION(0x10E, State.IN_GAME));
		addPacket(new CM_GROUP_DATA_EXCHANGE(0x111, State.IN_GAME));
		addPacket(new CM_GROUP_LOOT(0x19A, State.IN_GAME));
		addPacket(new CM_LEGION_TABS(0x119, State.IN_GAME));
		addPacket(new CM_INSTANCE_LEAVE(0xF0, State.IN_GAME));
		addPacket(new CM_READ_MAIL(0x128, State.IN_GAME));
		addPacket(new CM_SUMMON_ATTACK(0x16D, State.IN_GAME));
		addPacket(new CM_SUMMON_CASTSPELL(0x16F, State.IN_GAME));
		addPacket(new CM_SUMMON_COMMAND(0x15B, State.IN_GAME));
		addPacket(new CM_SUMMON_EMOTION(0x16C, State.IN_GAME));
		addPacket(new CM_SUMMON_MOVE(0x16B, State.IN_GAME));
		addPacket(new CM_TUNE(0x18D, State.IN_GAME));
		addPacket(new CM_SET_NOTE(0x11C, State.IN_GAME));
		addPacket(new CM_MOTION(0x2E9, State.IN_GAME));
		addPacket(new CM_REPORT_PLAYER(0x181, State.IN_GAME));
		addPacket(new CM_CAPTCHA(0xD0, State.IN_GAME));
		addPacket(new CM_USE_PACK_ITEM(0x1B2, State.IN_GAME));
		addPacket(new CM_ITEM_REMODEL(0x13C, State.IN_GAME));
		addPacket(new CM_APPEARANCE(0x167, State.IN_GAME));
        addPacket(new CM_PING_REQUEST(0x109, State.IN_GAME));
        addPacket(new CM_BROKER_LIST(0x15D, State.IN_GAME));
        addPacket(new CM_BROKER_SEARCH(0x15E, State.IN_GAME));
        addPacket(new CM_BROKER_CANCEL_REGISTERED(0x142, State.IN_GAME));
		addPacket(new CM_BROKER_REGISTERED(0x157, State.IN_GAME));
		addPacket(new CM_BROKER_COLLECT_SOLD_ITEMS(0x124, State.IN_GAME));
        addPacket(new CM_BUY_BROKER_ITEM(0x140, State.IN_GAME));
		addPacket(new CM_REGISTER_BROKER_ITEM(0x141, State.IN_GAME));
		addPacket(new CM_BROKER_SETTLE_LIST(0x15F, State.IN_GAME));
        addPacket(new CM_BROKER_SETTLE_ACCOUNT(0x143, State.IN_GAME));
		addPacket(new CM_SELECTITEM_OK(0x18E, State.IN_GAME));
		addPacket(new CM_USE_HOUSE_OBJECT(0x1A2, State.IN_GAME));
		addPacket(new CM_REPLACE_ITEM(0x194, State.IN_GAME));
		addPacket(new CM_MARK_FRIENDLIST(0x130, State.IN_GAME));
		addPacket(new CM_RELEASE_OBJECT(0x1A3, State.IN_GAME));
        //////////////////////////////////////////////////////////////////////
		
		//new find by Raziel
		//addPacket(new CM_REQUEST_RETURN_SERVER(0x1BB, State.IN_GAME));
		//addPacket(new CM_LOGIN_OUT(0xA4, State.IN_GAME));
        
        //addPacket(new CM_SECURITY_TOKEN(0x146, State.IN_GAME));
        //addPacket(new CM_GM_COMMAND_ACTION(0x195, State.IN_GAME)); Check Later
        //addPacket(new CM_ADMIN_PANEL(0x14A, State.IN_GAME));

        ///////////////////////REMOVED PACKETS\\\\\\\\\\\\\\\\\\\\\\\\\
        //addPacket(new CM_PETITION(0xF6, State.IN_GAME)); Packet removed since 4.3
        //addPacket(new CM_IN_GAME_SHOP_INFO(0x184, State.IN_GAME)); removed since 4.7
    }

    public AionPacketHandler getPacketHandler() {
        return handler;
    }

    private void addPacket(AionClientPacket prototype) {
        handler.addPacketPrototype(prototype);
    }

    @SuppressWarnings("synthetic-access")
    private static class SingletonHolder {

        protected static final AionPacketHandlerFactory instance = new AionPacketHandlerFactory();
    }
}
