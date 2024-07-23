package top.mckingdom.auspice.land_categories;

import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.constants.namespace.Namespaced;
import org.kingdoms.locale.LanguageManager;
import org.kingdoms.locale.SupportedLanguage;
import org.kingdoms.locale.messenger.DefinedMessenger;

public class LandCategory implements Namespaced {

    private final DefinedMessenger nameMessenger;
    private final DefinedMessenger descriptionMessenger;
    private int hash;
    private final Namespace ns;

    public LandCategory(Namespace ns, DefinedMessenger nameMessenger, DefinedMessenger descriptionMessenger) {
        this.ns = ns;
        this.nameMessenger = nameMessenger;
        this.descriptionMessenger = descriptionMessenger;
    }

    @Override
    public Namespace getNamespace() {
        return this.ns;
    }

    @Override
    public final int hashCode() {
        return this.hash;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LandCategory)) {
            return false;
        } else {
            return this.hash == o.hashCode();
        }
    }

    public String asString() {
        return this.ns.asString();
    }

    public String getName(SupportedLanguage language) {
        return LanguageManager.getRawMessage(this.nameMessenger, language);
    }

    /**
     * 用于语言文件等场景
     * @return 返回这个土地类型的关键字的小写英文名
     */
    public String getConfigName() {
        return this.ns.getKey().toLowerCase().replace('_', '-');
    }

    public DefinedMessenger getNameMessenger() {
        return nameMessenger;
    }

    public DefinedMessenger getDescriptionMessenger() {
        return descriptionMessenger;
    }
}
