package top.mckingdom.auspice.costs.types;

import org.kingdoms.constants.group.Group;
import org.kingdoms.constants.namespace.Namespace;
import top.mckingdom.auspice.costs.Cost;

public abstract class GroupCost<C> extends Cost<Group, C> {
    public GroupCost(Namespace namespace) {
        super(namespace, Group.class);
    }
}
