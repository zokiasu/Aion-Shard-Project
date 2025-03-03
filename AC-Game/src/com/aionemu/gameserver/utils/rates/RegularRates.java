package com.aionemu.gameserver.utils.rates;

import com.aionemu.gameserver.configs.main.CraftConfig;
import com.aionemu.gameserver.configs.main.RateConfig;

/**
 * @author ATracer
 * @author GiGatR00n v4.7.5.x
 */
public class RegularRates extends Rates {

    int holidayRate = HolidayRates.getHolidayRates(0);

    @Override
    public float getGroupXpRate() {
        return RateConfig.GROUPXP_RATE + holidayRate;
    }

    @Override
    public float getDropRate() {
        return RateConfig.DROP_RATE + holidayRate;
    }

    @Override
    public float getApNpcRate() {
        return RateConfig.AP_NPC_RATE + holidayRate;
    }

    @Override
    public float getApPlayerGainRate() {
        return RateConfig.AP_PLAYER_GAIN_RATE + holidayRate;
    }

    @Override
    public float getXpPlayerGainRate() {
        return RateConfig.XP_PLAYER_GAIN_RATE + holidayRate;
    }

    @Override
    public float getApPlayerLossRate() {
        return RateConfig.AP_PLAYER_LOSS_RATE + holidayRate;
    }

    @Override
    public float getQuestKinahRate() {
        return RateConfig.QUEST_KINAH_RATE + holidayRate;
    }

    @Override
    public float getQuestXpRate() {
        return RateConfig.QUEST_XP_RATE + holidayRate;
    }

    @Override
    public float getQuestApRate() {
        return RateConfig.QUEST_AP_RATE + holidayRate;
    }
	
	@Override
    public float getQuestGpRate() {
        return RateConfig.QUEST_GP_RATE + holidayRate;
    }
	
    @Override
    public float getXpRate() {
        return RateConfig.XP_RATE + holidayRate;
    }

    /*
     * (non-Javadoc) @see
     * com.aionemu.gameserver.utils.rates.Rates#getCraftingXPRate()
     */
    @Override
    public float getCraftingXPRate() {
        return RateConfig.CRAFTING_XP_RATE + holidayRate;
    }

    /*
     * (non-Javadoc) @see
     * com.aionemu.gameserver.utils.rates.Rates#getGatheringXPRate()
     */
    @Override
    public float getGatheringXPRate() {
        return RateConfig.GATHERING_XP_RATE + holidayRate;
    }

    @Override
    public int getGatheringCountRate() {
        return RateConfig.GATHERING_COUNT_RATE + holidayRate;
    }

    @Override
    public float getDpNpcRate() {
        return RateConfig.DP_NPC_RATE + holidayRate;
    }

    @Override
    public float getDpPlayerRate() {
        return RateConfig.DP_PLAYER_RATE + holidayRate;
    }

    @Override
    public int getCraftCritRate() {
        return CraftConfig.CRAFT_CRIT_RATE + holidayRate;
    }

    @Override
    public int getComboCritRate() {
        return CraftConfig.CRAFT_COMBO_RATE + holidayRate;
    }

    @Override
    public float getDisciplineRewardRate() {
        return RateConfig.PVP_ARENA_DISCIPLINE_REWARD_RATE + holidayRate;
    }

    @Override
    public float getChaosRewardRate() {
        return RateConfig.PVP_ARENA_CHAOS_REWARD_RATE + holidayRate;
    }

    @Override
    public float getHarmonyRewardRate() {
        return RateConfig.PVP_ARENA_HARMONY_REWARD_RATE + holidayRate;
    }

    @Override
    public float getGloryRewardRate() {
        return RateConfig.PVP_ARENA_GLORY_REWARD_RATE + holidayRate;
    }

    @Override
    public float getSellLimitRate() {
        return RateConfig.SELL_LIMIT_RATE + holidayRate;
    }


    @Override
    public float getKamarRewardRate() {
        return RateConfig.KAMAR_REWARD_RATE + holidayRate;
    }
    
    @Override
    public float getIdgelDomeBoxRewardRate() {
        return RateConfig.IDGEL_DOME_BOX_REWARD_RATE + holidayRate;
    }
    
	@Override
	public float getGpNpcRate() {
		return RateConfig.GP_NPC_RATE + holidayRate;
	}
}
