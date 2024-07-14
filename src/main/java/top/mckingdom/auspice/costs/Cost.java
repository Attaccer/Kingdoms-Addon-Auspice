package top.mckingdom.auspice.costs;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.constants.namespace.NamespaceContainer;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public abstract class Cost<T, C> implements NamespaceContainer {

    protected Namespace namespace;
    public Cost(Namespace namespace) {
        this.namespace = namespace;
    }

    @Override
    public Namespace getNamespace() {
        return this.namespace;
    }

    abstract public boolean canExpend(@NonNull T target, @NonNull C amount);


    /**
     * 进行消费.
     * @param target 要进行消费的对象，可以是一个玩家，可以是一个王国.
     * @param amount 消费的数量.
     */
    abstract public void expend(@NonNull T target, @NonNull C amount);



    /**
     * @deprecated 有问题的代码,容易报一大堆错误,别用
     * @param target 消费的对象
     * @param amount
     * @param costProjects
     * @return
     */
    @Deprecated()
    public static boolean batchExpend(Objects target, Objects amount, Cost<?, ?>... costProjects) {
        boolean b = true;

        for (Iterator<Cost<?, ?>> it = Arrays.stream(costProjects).iterator(); it.hasNext(); ) {
            @SuppressWarnings("rawtypes") Cost c = it.next();
            //noinspection unchecked
            b = c.canExpend(target, amount) && b;
        }
        if (b) {
            for (Iterator<Cost<?, ?>> it = Arrays.stream(costProjects).iterator(); it.hasNext(); ) {
                @SuppressWarnings("rawtypes") Cost c = it.next();
                //noinspection unchecked
                c.expend(target, amount);
            }
        }

        return b;  //返回是否批量消费成功

    }


    public static boolean batchExpend(Objects target, Map<Cost<?, ?>, Objects> costObjects) {
        return true;
    }

    public void compile(String str) {
        throw new UnsupportedOperationException();
    }



}



