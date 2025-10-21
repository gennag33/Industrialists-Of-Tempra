package com.example.model.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class LangManagerTest {

    @BeforeEach
    void resetLangManagerState() throws Exception {
        // reset private static fields to their initial state
        Field translationsField = LangManager.class.getDeclaredField("translations");
        translationsField.setAccessible(true);
        translationsField.set(null, new HashMap<String, String>());

        Field currentLanguageField = LangManager.class.getDeclaredField("currentLanguage");
        currentLanguageField.setAccessible(true);
        currentLanguageField.set(null, null);
    }

    @AfterEach
    void cleanup() throws Exception {
        // ensure state is cleared after tests
        Field translationsField = LangManager.class.getDeclaredField("translations");
        translationsField.setAccessible(true);
        translationsField.set(null, new HashMap<String, String>());

        Field currentLanguageField = LangManager.class.getDeclaredField("currentLanguage");
        currentLanguageField.setAccessible(true);
        currentLanguageField.set(null, null);
    }

    @Test
    void setLanguage_missingFile_throwsRuntimeException() {
        assertThrows(RuntimeException.class, () -> LangManager.setLanguage("nonexistent_lang_code"));
    }

    @Test
    void setLanguage_null_throwsRuntimeException() {
        assertThrows(RuntimeException.class, () -> LangManager.setLanguage(null));
    }

    @Test
    void get_beforeLoad_throwsIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> LangManager.get("any"));
    }

    @Test
    void get_returnsTranslation_whenLoadedAnd_missingSuffixForUnknownKey() throws Exception {
        // prepare a fake translations map via reflection to simulate a loaded language
        Field translationsField = LangManager.class.getDeclaredField("translations");
        translationsField.setAccessible(true);
        Map<String, String> fake = new HashMap<>();
        fake.put("hello", "Hello World");
        translationsField.set(null, fake);

        Field currentLanguageField = LangManager.class.getDeclaredField("currentLanguage");
        currentLanguageField.setAccessible(true);
        currentLanguageField.set(null, "en");

        // existing key
        assertEquals("Hello World", LangManager.get("hello"));

        // missing key -> should return key + _MISSING
        assertEquals("goodbye_MISSING", LangManager.get("goodbye"));

        // current language should reflect what we set
        assertEquals("en", LangManager.getCurrentLanguage());
    }

    @Test
    void getCurrentLanguage_initiallyNull() {
        assertNull(LangManager.getCurrentLanguage());
    }
}
