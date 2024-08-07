package top.mckingdom.auspice.costs;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.constants.namespace.Namespaced;
import org.kingdoms.libs.snakeyaml.validation.NodeValidator;
import org.kingdoms.libs.snakeyaml.validation.ValidationContext;
import org.kingdoms.utils.config.ConfigSection;
import top.mckingdom.auspice.AuspiceAddon;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class Cost<T, C> implements Namespaced {

    protected Namespace namespace;
    protected Class<T> target;

    public Cost(Namespace namespace, Class<T> target) {
        this.namespace = namespace;
        this.target = target;
        CostRegistry.INSTANCE.register(this);
    }

    abstract public boolean canExpend(T target, C amount);
    abstract public boolean canExpend(T target, ConfigSection section);


    /**
     * 进行消费.
     * @param target 要进行消费的对象, 可以是一个玩家, 可以是一个王国.
     * @param amount 消费的数量.
     */
    abstract public void expend(T target, C amount);
    abstract public void expend(T target, ConfigSection section);




//    public static CostResponse parse(Object target, ConfigSection costsSection) {
//        boolean a = true;
//        Set<Cost<?, ?>> successCosts = new HashSet<>();
//        Set<Cost<?, ?>> failedCost = new HashSet<>();
//
//        {
//            for (ConfigSection section : costsSection.getSections().values()) {
//                Cost<?, ?> cost = CostRegistry.INSTANCE.getRegistered(Namespace.fromString(section.getKey().getValue()));
//                if (cost == null) {
//                    System.out.println("Unknown Cost: " + section.getKey().getValue());
//                    a = false;
//                    continue;
//                }
//
//                try {
//                    cost.target.cast(target);
//                } catch (ClassCastException e) {
//                    a = false;
//                    System.out.println("Can not use this cost: " + cost.namespace + " for Class: " + target.getClass());
//                    continue;
//                }
//
//
//
//                if (!cost.canExpend( (cost.target.cast(target)) , section.getSection())) {
//                    a = false;
//                    failedCost.add(cost.getInstance());
//                    continue;
//                } else {
//                    cost.expend(target, section.getSection());
//                    successCosts.add(cost.getInstance());
//                }
//
//            }
//        }
//
//        if (a) {
//            return CostResponse.SUCCESS;
//        } else {
//            return new CostResponse(successCosts, failedCost);
//        }
//
//    }



    public abstract Cost<?, ?> getInstance();

    @Override
    public final Namespace getNamespace() {
        return this.namespace;
    }

}



