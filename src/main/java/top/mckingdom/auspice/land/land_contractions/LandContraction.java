package top.mckingdom.auspice.land.land_contractions;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.constants.namespace.Namespaced;

public class LandContraction implements Namespaced {

    private final Namespace namespace;

    public LandContraction(Namespace namespace) {
        this.namespace = namespace;
    }

    @Override
    public @NonNull Namespace getNamespace() {
        return namespace;
    }
}
