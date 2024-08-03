package top.mckingdom.auspice.costs;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.libs.snakeyaml.validation.NodeValidator;

public abstract class RefundableCost<T, C> extends Cost<T, C> {

    public RefundableCost(Namespace namespace) {
        super(namespace);
    }

    public abstract void refund(@NonNull T target, @NonNull C amount);
}
