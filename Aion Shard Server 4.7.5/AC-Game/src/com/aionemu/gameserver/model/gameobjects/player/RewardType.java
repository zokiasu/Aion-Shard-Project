package com.aionemu.gameserver.model.gameobjects.player;

import com.aionemu.gameserver.configs.main.LegionConfig;
import com.aionemu.gameserver.model.stats.container.StatEnum;

/**
 * @author antness
 */
public enum RewardType {

    AP_PLAYER {
        @Override
        public long calcReward(Player player, long reward) {
            float statRate = player.getGameStats().getStat(StatEnum.AP_BOOST, 100).getCurrent() / 100f;
            return (long) (reward * player.getRates().getApPlayerGainRate() * statRate);
        }
    },
    AP_NPC {
        @Override
        public long calcReward(Player player, long reward) {
            float statRate = player.getGameStats().getStat(StatEnum.AP_BOOST, 100).getCurrent() / 100f;
            return (long) (reward * player.getRates().getApNpcRate() * statRate);
        }
    },
    GP_NPC {
    	@Override
        public long calcReward(Player player, long reward) {
            return (long) (reward * player.getRates().getGpNpcRate());
        }
    },
    HUNTING {
        @Override
        public long calcReward(Player player, long reward) {
            float statRate = player.getGameStats().getStat(StatEnum.BOOST_HUNTING_XP_RATE, 100).getCurrent() / 100f;
            long legionOnlineBonus = 0;
            if (player.isLegionMember() && player.getLegion().getOnlineMembersCount() >= LegionConfig.LEGION_BUFF_REQUIRED_MEMBERS) {
                legionOnlineBonus = (long) (reward * player.getRates().getXpRate() * statRate) / 100 * 10;
            }
            return (long) (reward * player.getRates().getXpRate() * statRate + legionOnlineBonus);
        }
    },
    GROUP_HUNTING {
        @Override
        public long calcReward(Player player, long reward) {
            float statRate = player.getGameStats().getStat(StatEnum.BOOST_GROUP_HUNTING_XP_RATE, 100).getCurrent() / 100f;
            long legionOnlineBonus = 0;
            if (player.isLegionMember() && player.getLegion().getOnlineMembersCount() >= LegionConfig.LEGION_BUFF_REQUIRED_MEMBERS) {
                legionOnlineBonus = (long) (reward * player.getRates().getXpRate() * statRate) / 100 * 10;
            }
            return (long) (reward * player.getRates().getGroupXpRate() * statRate + legionOnlineBonus);
        }
    },
    PVP_KILL {
        @Override
        public long calcReward(Player player, long reward) {
            return (reward);
        }
    },
    QUEST {
        @Override
        public long calcReward(Player player, long reward) {
            float statRate = player.getGameStats().getStat(StatEnum.BOOST_QUEST_XP_RATE, 100).getCurrent() / 100f;
            return (long) (reward * player.getRates().getQuestXpRate() * statRate);
        }
    },
    CRAFTING {
        @Override
        public long calcReward(Player player, long reward) {
            float statRate = player.getGameStats().getStat(StatEnum.BOOST_CRAFTING_XP_RATE, 100).getCurrent() / 100f;
            long legionOnlineBonus = 0;
            if (player.isLegionMember() && player.getLegion().getOnlineMembersCount() >= LegionConfig.LEGION_BUFF_REQUIRED_MEMBERS) {
                legionOnlineBonus = (long) (reward * player.getRates().getXpRate() * statRate) / 100 * 10;
            }
            return (long) (reward * player.getRates().getCraftingXPRate() * statRate + legionOnlineBonus);
        }
    },
    GATHERING {
        @Override
        public long calcReward(Player player, long reward) {
            float statRate = player.getGameStats().getStat(StatEnum.BOOST_GATHERING_XP_RATE, 100).getCurrent() / 100f;
            long legionOnlineBonus = 0;
            if (player.isLegionMember() && player.getLegion().getOnlineMembersCount() >= LegionConfig.LEGION_BUFF_REQUIRED_MEMBERS) {
                legionOnlineBonus = (long) (reward * player.getRates().getXpRate() * statRate) / 100 * 10;
            }
            return (long) (reward * player.getRates().getGatheringXPRate() * statRate + legionOnlineBonus);
        }
    },
    USEITEM {
        @Override
        public long calcReward(Player player, long reward) {
            float statRate = player.getGameStats().getStat(StatEnum.BOOST_QUEST_XP_RATE, 100).getCurrent() / 100f;
            long legionOnlineBonus = 0;
            if (player.isLegionMember() && player.getLegion().getOnlineMembersCount() >= LegionConfig.LEGION_BUFF_REQUIRED_MEMBERS) {
                legionOnlineBonus = (long) (reward * player.getRates().getXpRate() * statRate) / 100 * 10;
            }
            return (long) (reward * player.getRates().getQuestXpRate() * statRate + legionOnlineBonus);
        }
    };

    public abstract long calcReward(Player player, long reward);
}
