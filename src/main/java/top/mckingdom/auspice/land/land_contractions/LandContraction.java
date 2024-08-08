package top.mckingdom.auspice.land.land_contractions;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.kingdoms.constants.land.Land;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.constants.namespace.Namespaced;
import org.kingdoms.constants.player.KingdomPlayer;
import org.kingdoms.locale.SupportedLanguage;
import top.mckingdom.auspice.data.land.LandContractions;

public abstract class LandContraction implements Namespaced {

    private final Namespace namespace;

    public LandContraction(Namespace namespace) {
        this.namespace = namespace;
    }

    @Override
    public @NonNull Namespace getNamespace() {
        return namespace;
    }

    public boolean hasContraction(Land land, KingdomPlayer player) {
        return LandContractions.hasLandContraction(player, land, this);
    }

    public abstract String getName(SupportedLanguage lang);

}
