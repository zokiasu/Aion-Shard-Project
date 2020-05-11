package com.aionemu.gameserver.utils;

import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.GameServer;
import com.aionemu.gameserver.GameServer.StartupHook;
import com.aionemu.gameserver.configs.main.GSConfig;
import com.aionemu.gameserver.dao.PlayerDAO;
import com.aionemu.gameserver.model.Race;

public class ZCXInfo {

    private static final Logger log = LoggerFactory.getLogger(ZCXInfo.class);
    private static int ELYOS_COUNT = 0;
    private static int ASMOS_COUNT = 0;
    private static double ELYOS_RATIO = 0.0;
    private static double ASMOS_RATIO = 0.0;
    private static final ReentrantLock lock = new ReentrantLock();

    /**
     * Adding the ratio limit in this class. on that way the main class dont got trashed with things like this
     */
    public static void checkForRatioLimitation() {
    	if (GSConfig.ENABLE_RATIO_LIMITATION) {
            GameServer.addStartupHook(new StartupHook() {
                @Override
                public void onStartup() {
                    lock.lock();
                    try {
                        ASMOS_COUNT = DAOManager.getDAO(PlayerDAO.class).getCharacterCountForRace(Race.ASMODIANS);
                        ELYOS_COUNT = DAOManager.getDAO(PlayerDAO.class).getCharacterCountForRace(Race.ELYOS);
                        computeRatios();
                    } catch (Exception e) {
                    	log.error("[Error] Something went wrong on checking ratio limitation");
            			e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                    displayRatios(false);
                }
            });
        }
    }
    
	public static void updateRatio(Race race, int i) {
		lock.lock();
		try {
			switch (race) {
				case ASMODIANS:
					ASMOS_COUNT += i;
					break;
				case ELYOS:
					ELYOS_COUNT += i;
					break;
				default:
					break;
			}

			computeRatios();
		} catch (Exception e) {
			log.error("[Error] Cant update ratio limits");
			e.printStackTrace();
		} finally {
			lock.unlock();
		}

		displayRatios(true);
	}

	private static void computeRatios() {
		if ((ASMOS_COUNT <= GSConfig.RATIO_MIN_CHARACTERS_COUNT) && (ELYOS_COUNT <= GSConfig.RATIO_MIN_CHARACTERS_COUNT)) {
			ASMOS_RATIO = ELYOS_RATIO = 50.0;
		} else {
			ASMOS_RATIO = ASMOS_COUNT * 100.0 / (ASMOS_COUNT + ELYOS_COUNT);
			ELYOS_RATIO = ELYOS_COUNT * 100.0 / (ASMOS_COUNT + ELYOS_COUNT);
		}
	}

	private static void displayRatios(boolean updated) {
		log.info("FACTIONS RATIO " + (updated ? "UPDATED " : "") + ": E " + String.format("%.1f", ELYOS_RATIO) + " % / A "
				+ String.format("%.1f", ASMOS_RATIO) + " %");
	}

	public static double getRatiosFor(Race race) {
		switch (race) {
			case ASMODIANS:
				return ASMOS_RATIO;
			case ELYOS:
				return ELYOS_RATIO;
			default:
				return 0.0;
		}
	}

	public static int getCountFor(Race race) {
		switch (race) {
			case ASMODIANS:
				return ASMOS_COUNT;
			case ELYOS:
				return ELYOS_COUNT;
			default:
				return 0;
		}
	}
}