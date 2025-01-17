package com.aionemu.gameserver.services.ecfunctions;

import com.aionemu.gameserver.configs.main.WordFilterConfig;
import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUIT_RESPONSE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Future;

/**
 * @author Dr.Nism
 */
public class WordFilterService {

    private static Logger log = LoggerFactory.getLogger("BADWORD_LOG");
    private FileInputStream wordFilterFile;
    private Scanner scanner;
    static List<String> banWordList = new ArrayList<String>();

    public WordFilterService() {
        try {
            wordFilterFile = new FileInputStream("./config/administration/wordfilter.txt");
            scanner = new Scanner(wordFilterFile, "UTF-8");
            while (scanner.hasNextLine()) {
                banWordList.add(scanner.nextLine());
            }
        } catch (IOException localIOException) {
        } finally {
            scanner.close();
        }
        log.info("Load bad word : " + banWordList.size() + " entry.");
    }

    public static String replaceBanWord(final Player player, String string) {
        String reason = "";
        int duration = WordFilterConfig.WORDFILTER_BANTIME;
        Iterator<String> iterator = banWordList.iterator();
        while (iterator.hasNext()) {
            String str = (String) iterator.next();
            int i = string.indexOf(str);
            if (i != -1) {
                player.increaseWordBanTime();
            }
            string = string.replaceAll(str, "***");
        }
        if (player.getWordBanTime() == WordFilterConfig.WORDFILTER_LIMITTIME) {
            if (WordFilterConfig.WORDFILTER_ENABLED_BAN) {
                player.setGagged(true);
                if (duration != 0) {
                    Future<?> task = player.getController().getTask(TaskId.GAG);
                    if (task != null) {
                        player.getController().cancelTask(TaskId.GAG);
                    }
                    player.getController().addTask(TaskId.GAG, ThreadPoolManager.getInstance().schedule(new Runnable() {
                        @Override
                        public void run() {
                            player.setGagged(false);
                            PacketSendUtility.sendMessage(player, "Your chat ban time finished");
                        }
                    }, duration * 60000L));
                }
                player.banFromWorld(string, reason, duration * 60 * 1000);
                PacketSendUtility.sendMessage(player, "You have been chat banned" + (duration != 0 ? " during " + duration + " minutes" : ""));
                log.info("[BADWORD] : Player " + player.getName() + " chat banned" + (duration != 0 ? " during " + duration + " minutes" : ""));
            }
            if (WordFilterConfig.WORDFILTER_ENABLED_KICK) {
                player.getClientConnection().close(new SM_QUIT_RESPONSE(), true);
                log.info("[BADWORD] : Player " + player.getName() + " has kicked off the server for use some word not allowed.");
            }
        }
        return string;
    }

    public static boolean isValidName(String string) {
        @SuppressWarnings("unused")
        int i = 1;
        Iterator<String> iterator = banWordList.iterator();
        while (iterator.hasNext()) {
            String str = (String) iterator.next();
            int j = string.indexOf(str);
            if (j != -1) {
                i = 0;
            }
            string = string.replaceAll(str, "***");
        }
        return true;
    }

    public static WordFilterService getInstance() {
        return SingletonHolder.wfs;
    }

    private static class SingletonHolder {

        public static WordFilterService wfs = new WordFilterService();
    }
}
