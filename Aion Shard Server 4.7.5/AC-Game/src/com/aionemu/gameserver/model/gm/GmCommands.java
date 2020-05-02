package com.aionemu.gameserver.model.gm;

/**
 * @author xTz
 */
public enum GmCommands {

    GM_MAIL_LIST,
    INVENTORY,
    SKILL,
    TELEPORTTO,
    STATUS,
    SEARCH,
    QUEST,
    GM_GUILDHISTORY,
    GM_BUDDY_LIST,
    RECALL,
    GM_COMMENT_LSIT,
    GM_COMMENT_ADD,
    CHECK_BOT1,
    CHECK_BOT99,
    BOOKMARK_ADD,
    GUILD;

    public static GmCommands getValue(String command) {
        for (GmCommands value : values()) {
            if (value.name().equals(command.toUpperCase())) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid GmCommands id: " + command);
    }
}
