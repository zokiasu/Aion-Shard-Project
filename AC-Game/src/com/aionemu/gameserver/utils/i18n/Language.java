package com.aionemu.gameserver.utils.i18n;

import javolution.util.FastList;
import javolution.util.FastMap;

import java.util.List;
import java.util.Map;

/**
 * @author blakawk
 */
public class Language {

    private final List<String> supportedLanguages = new FastList<String>();
    private final Map<CustomMessageId, String> translatedMessages = new FastMap<CustomMessageId, String>();

    public Language() {
    }

    protected Language(String language) {
        supportedLanguages.add(language);
    }

    protected void addSupportedLanguage(String language) {
        supportedLanguages.add(language);
    }

    public List<String> getSupportedLanguages() {
        return supportedLanguages;
    }

    public String translate(CustomMessageId id, Object... params) {
        if (translatedMessages.containsKey(id)) {
            return String.format(translatedMessages.get(id), params);
        }

        return String.format(id.getFallbackMessage(), params);
    }

    /*
     * public String translateRU(CustomMessageIdRU id, Object... params) { if (translatedMessages.containsKey(id)) {
     * return String.format(translatedMessages.get(id), params); } return String.format(id.getFallbackMessage(), params);
     * }
     */
    protected void addTranslatedMessage(CustomMessageId id, String message) {
        translatedMessages.put(id, message);
    }
}
