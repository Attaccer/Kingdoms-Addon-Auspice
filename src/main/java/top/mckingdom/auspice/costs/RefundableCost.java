package top.mckingdom.auspice.costs;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.libs.snakeyaml.validation.NodeValidator;

public interface RefundableCost<T, C> {
    void refund(@NonNull T target, @NonNull C amount);
}
