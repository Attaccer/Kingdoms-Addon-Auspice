package top.mckingdom.auspice.costs;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.libs.snakeyaml.validation.NodeValidator;
import org.kingdoms.libs.snakeyaml.validation.Validator;

public abstract class RefundableCost<T, C> extends Cost<T, C> {
    public RefundableCost(Namespace namespace, NodeValidator validator) {
        super(namespace, validator);
    }

    public abstract void refund(@NonNull T target, @NonNull C amount);
}
