package top.mckingdom.auspice.costs;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.constants.namespace.NamespaceContainer;
import org.kingdoms.constants.namespace.NamespaceRegistry;

public class CostRegistry extends NamespaceRegistry<Cost> {

    private CostRegistry() {

    }
    public static CostRegistry INSTANCE;

    static {         //就饿汉单例了...我懒
        INSTANCE = new CostRegistry();
    }

}
