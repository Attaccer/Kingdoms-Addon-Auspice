package top.mckingdom.auspice.costs;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.constants.namespace.NamespaceContainer;
import org.kingdoms.libs.snakeyaml.validation.NodeValidator;
import org.kingdoms.libs.snakeyaml.validation.ValidationContext;

import java.util.Map;
import java.util.Objects;

public abstract class Cost<T, C> implements NamespaceContainer {

    protected Namespace namespace;
    protected NodeValidator validator;

    public Cost(Namespace namespace) {
        this.namespace = namespace;
        this.validator = null;
    }

    //Input the validator for easy calling when validating the Cost node
    public Cost(Namespace namespace, NodeValidator validator) {
        this.namespace = namespace;
        this.validator = validator;
    }



    abstract public boolean canExpend(@NonNull T target, @NonNull C amount);


    /**
     * 进行消费.
     * @param target 要进行消费的对象，可以是一个玩家，可以是一个王国.
     * @param amount 消费的数量.
     */
    abstract public void expend(@NonNull T target, @NonNull C amount);
    public void expend(@NonNull T target, @NonNull Objects amount) {
        this.expend(target, (C)amount);
    }


    public static <T> boolean canExpend(T target, Map<Cost<T, ?>, Objects> costObjects) {
        return true;
    }

    public static <T> void expend(T target, Map<Cost<T, ?>, Objects> costObjects) {
        for (Cost<T, ?> cost : costObjects.keySet()) {
            cost.expend(target, costObjects.get(cost));
        }
    }


    public C compile(ValidationContext context) {
        this.validator.validate(context);
        throw new UnsupportedOperationException();
    }

    public @Nullable NodeValidator getValidator() {
        return validator;
    }

    public void setValidator(NodeValidator validator) {
        this.validator = validator;
    }


    @Override
    public Namespace getNamespace() {
        return this.namespace;
    }

    @Override
    public String toString() {
        return "Cost[" + this.namespace.asString() + ']';
    }
}



