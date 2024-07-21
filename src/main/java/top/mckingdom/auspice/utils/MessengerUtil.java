package top.mckingdom.auspice.utils;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.config.AdvancedMessage;
import org.kingdoms.config.Comment;
import org.kingdoms.locale.LanguageEntry;
import org.kingdoms.locale.LanguageManager;
import org.kingdoms.locale.SupportedLanguage;
import org.kingdoms.locale.messenger.DefinedMessenger;
import org.kingdoms.locale.provider.MessageProvider;

import java.util.ArrayList;
import java.util.LinkedList;

import static top.mckingdom.auspice.utils.MessengerUtil.Companion.constants;

public class MessengerUtil {

    private static boolean locked = false;

    /**
     * @param path The Path of Language, such as "new String[]{"permissions", "jail"}" will make a path "permissions/jail"
     */
    public static DefinedMessenger createMessenger(String[] path, String defaultValue) {

        LinkedList<String> newPath = new LinkedList<>();
        for (String it : path) {
            newPath.add(it.toLowerCase().replace('_', '-'));
        }

        DynamicLanguage dynamicLanguage = new DynamicLanguage(newPath.toArray(new String[path.length]), defaultValue);
        constants.add(dynamicLanguage);
        LanguageManager.registerMessenger(DynamicLanguage.class, constants.toArray(new DynamicLanguage[constants.size()]));

        return dynamicLanguage;
    }



    public static void lock() {
        if (locked) {
            throw new IllegalAccessError("Registers are already closed");
        } else {
            locked = true;
        }
    }

    public static class Companion {
        public static final ArrayList<DynamicLanguage> constants = new ArrayList<>();


    }

    public static class DynamicLanguage implements DefinedMessenger {

        public final @NotNull LanguageEntry languageEntry;
        public final @NotNull String defaultValue;

        DynamicLanguage(@NonNull String[] path, @NotNull String defaultValue) {
            this.languageEntry = new LanguageEntry(path);
            this.defaultValue = defaultValue;
            constants.add(this);
        }

        public ArrayList<DynamicLanguage> getConstants() {
            return constants;
        }

        @NotNull
        @Override
        public LanguageEntry getLanguageEntry() {
            return this.languageEntry;
        }

        @Override
        public String name() {
            StringBuilder out = new StringBuilder();
            for (String str : this.languageEntry.getPath()) {
                out.append(str.toUpperCase().replace('-', '_')).append("_");
            }
            out.deleteCharAt(out.length()-1);
            return out.toString();
        }

        @NotNull
        @Override
        public String getDefaultValue() {
            return this.defaultValue;
        }

        @Override
        public MessageProvider getProvider(SupportedLanguage supportedLanguage) {
            return DefinedMessenger.super.getProvider(supportedLanguage);
        }

        @Override
        public Comment getComment() {
            return null;
        }

        @Override
        public AdvancedMessage getAdvancedData() {
            return null;
        }



    }


}
