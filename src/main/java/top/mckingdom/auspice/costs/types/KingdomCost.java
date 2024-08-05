package top.mckingdom.auspice.costs.types;

import org.kingdoms.constants.group.Kingdom;
import org.kingdoms.constants.namespace.Namespace;
import top.mckingdom.auspice.costs.Cost;

public abstract class KingdomCost<C> extends Cost<Kingdom, C> {
    public KingdomCost(Namespace namespace) {
        super(namespace, Kingdom.class);
    }
}
