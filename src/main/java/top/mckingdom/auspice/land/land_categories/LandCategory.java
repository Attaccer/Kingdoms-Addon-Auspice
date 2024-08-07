package top.mckingdom.auspice.land.land_categories;

import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.constants.namespace.Namespaced;

public class LandCategory implements Namespaced {


    private int hash;
    private final Namespace ns;

    public LandCategory(Namespace ns) {
        this.ns = ns;

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



    /**
     * 用于语言文件等场景
     * @return 返回这个土地类型的关键字的小写英文名
     */
    public String getConfigName() {
        return this.ns.getKey().toLowerCase().replace('_', '-');
    }

    @Override
    public String toString() {
        return "LandCategory:{" + this.getConfigName() + '}';
    }
}
