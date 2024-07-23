package top.mckingdom.auspice.land_categories;

import org.kingdoms.constants.namespace.Lockable;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.constants.namespace.NamespacedRegistry;
import top.mckingdom.auspice.utils.MessengerUtil;

import java.util.Map;

public class LandCategoryRegistry extends NamespacedRegistry<LandCategory> implements Lockable {
    public static boolean unLocked = true;
    @Override
    public void lock() {
        unLocked = false;
    }


    protected final Map<Namespace, LandCategory> getRawRegistry() {
        return this.registry;
    }

    @Override
    public final void register(LandCategory landCategory) {
        super.register(landCategory);

    }



}