package top.mckingdom.auspice.costs;

import org.kingdoms.constants.namespace.NamespacedRegistry;

public class CostRegistry extends NamespacedRegistry<Cost> {

    private CostRegistry() {

    }
    public static CostRegistry INSTANCE;

    static {         //就饿汉单例了...我懒
        INSTANCE = new CostRegistry();
    }

}
