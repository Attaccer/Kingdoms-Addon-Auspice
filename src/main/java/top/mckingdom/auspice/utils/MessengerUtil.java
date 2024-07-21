package top.mckingdom.auspice.utils;

import org.kingdoms.locale.LanguageEntry;
import org.kingdoms.locale.LanguageManager;
import org.kingdoms.locale.messenger.DefinedMessenger;

import java.util.ArrayList;

public class MessengerUtil {

    /**
     *
     * @param path The Path of Language, such as "new String[]{"permissions", "jail"}" will make a path "permissions/jail"
     */
    public static DefinedMessenger createMessenger(String[] path, String defaultValue) {
        M m = new M(path, defaultValue);
        constants.add(m);
        LanguageManager.registerMessenger(M.class, constants.toArray(new M[0]));
        return m;
    }

    protected static ArrayList<M> constants = new ArrayList<>();
    static class M implements DefinedMessenger {

        private final LanguageEntry languageEntry;
        private final String defaultValue;

        M(String[] path, String defaultValue) {
            this.languageEntry = new LanguageEntry(path);
            this.defaultValue = defaultValue;
            constants.add(this);
        }

        public ArrayList<M> getConstants() {
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
