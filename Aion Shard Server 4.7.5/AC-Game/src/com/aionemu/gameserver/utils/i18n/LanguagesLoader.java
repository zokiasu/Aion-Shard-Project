package com.aionemu.gameserver.utils.i18n;

import com.aionemu.commons.scripting.classlistener.ClassListener;
import com.aionemu.commons.utils.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;

/**
 * @author blakawk
 */
public class LanguagesLoader implements ClassListener {

    private static final Logger log = LoggerFactory.getLogger(Language.class);
    private final LanguageHandler handler;

    public LanguagesLoader(LanguageHandler handler) {
        this.handler = handler;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void postLoad(Class<?>[] classes) {
        for (Class<?> clazz : classes) {
            if (log.isDebugEnabled()) {
                log.debug("Loading class " + clazz.getName());
            }

            if (!isValidClass(clazz)) {
                continue;
            }

            if (ClassUtils.isSubclass(clazz, Language.class)) {
                Class<? extends Language> language = (Class<? extends Language>) clazz;
                if (language != null) {
                    try {
                        handler.registerLanguage(language.newInstance());
                    } catch (Exception e) {
                        log.error("Registering " + language.getName(), e);
                    }
                }
            }
        }

        log.info("Loaded " + handler.size() + " custom message handlers.");
    }

    @Override
    public void preUnload(Class<?>[] classes) {
        if (log.isDebugEnabled()) {
            for (Class<?> clazz : classes) {
                log.debug("Unload language " + clazz.getName());
            }
        }
        handler.clear();
    }

    public boolean isValidClass(Class<?> clazz) {
        final int modifiers = clazz.getModifiers();

        if (Modifier.isAbstract(modifiers) || Modifier.isInterface(modifiers)) {
            return false;
        }

        if (!Modifier.isPublic(modifiers)) {
            return false;
        }

        return true;
    }
}
