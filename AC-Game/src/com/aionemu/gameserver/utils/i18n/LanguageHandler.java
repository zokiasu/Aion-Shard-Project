package com.aionemu.gameserver.utils.i18n;

import com.aionemu.commons.scripting.classlistener.AggregatedClassListener;
import com.aionemu.commons.scripting.classlistener.OnClassLoadUnloadListener;
import com.aionemu.commons.scripting.classlistener.ScheduledTaskClassListener;
import com.aionemu.commons.scripting.scriptmanager.ScriptManager;
import com.aionemu.gameserver.GameServerError;
import com.aionemu.gameserver.configs.main.GSConfig;
import javolution.util.FastMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author Fennek
 */
public class LanguageHandler {

    private static final File LANGUAGE_DESCRIPTOR_FILE = new File("./data/scripts/system/languages.xml");
    private static Logger log = LoggerFactory.getLogger(Language.class);
    private Map<String, Language> languages = new FastMap<String, Language>();
    private Language language;
    private static final LanguageHandler instance = new LanguageHandler();
    private ScriptManager sm = new ScriptManager();

    public static final LanguageHandler getInstance() {
        AggregatedClassListener acl = new AggregatedClassListener();
        acl.addClassListener(new OnClassLoadUnloadListener());
        acl.addClassListener(new ScheduledTaskClassListener());
        acl.addClassListener(new LanguagesLoader(instance));
        instance.sm.setGlobalClassListener(acl);

        try {
            instance.sm.load(LANGUAGE_DESCRIPTOR_FILE);
        } catch (Exception e) {
            throw new GameServerError("Cannot load languages", e);
        }

        instance.language = instance.getLanguage(GSConfig.LANG);
        return instance;
    }

    private LanguageHandler() {
    }

    public static String translate(CustomMessageId id, Object... params) {
        return instance.language.translate(id, params);
    }

    public void registerLanguage(Language language) {
        if (language == null) {
            throw new NullPointerException("Cannot register null Language");
        }

        List<String> langs = language.getSupportedLanguages();

        for (String lang : langs) {
            if (languages.containsKey(lang)) {
                log.warn("Overriding language " + lang + " with class " + language.getClass().getName());
            }

            languages.put(lang, language);
        }
    }

    public Language getLanguage(String language) {
        if (!languages.containsKey(language)) {
            return new Language();
        }

        return languages.get(language);
    }

    public void clear() {
        languages.clear();
    }

    public int size() {
        return languages.size();
    }
}
