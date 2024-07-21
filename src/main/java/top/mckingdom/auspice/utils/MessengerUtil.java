package top.mckingdom.auspice.utils;

import org.kingdoms.locale.LanguageEntry;
import org.kingdoms.locale.LanguageManager;
import org.kingdoms.locale.messenger.DefinedMessenger;

import java.util.ArrayList;

public class MessengerUtil {

    /**
     * @param path The Path of Language, such as "new String[]{"permissions", "jail"}" will make a path "permissions/jail"
     */
    public static DefinedMessenger createMessenger(String[] path, String defaultValue) {
        DynamicLanguage dynamicLanguage = new DynamicLanguage(path, defaultValue);
        constants.add(dynamicLanguage);
        LanguageManager.registerMessenger(DynamicLanguage.class, constants.toArray(new DynamicLanguage[0]));
        return dynamicLanguage;
    }

    private static final ArrayList<DynamicLanguage> constants = new ArrayList<>();
    static class DynamicLanguage implements DefinedMessenger {

        private final LanguageEntry languageEntry;
        private final String defaultValue;

        DynamicLanguage(String[] path, String defaultValue) {
            this.languageEntry = new LanguageEntry(path);
            this.defaultValue = defaultValue;
            constants.add(this);
        }

        public ArrayList<DynamicLanguage> getConstants() {
            return constants;
        }

        @Override
        public LanguageEntry getLanguageEntry() {
            return this.languageEntry;
        }

        @Override
        public String name() {
            StringBuilder out = new StringBuilder();
            for (String str : this.languageEntry.getPath()) {
                out.append(str.toUpperCase()).append("_");
            }
            out.deleteCharAt(out.length()-1);
            return out.toString();
        }

        @Override
        public String getDefaultValue() {
            return this.defaultValue;
        }
    }


}
