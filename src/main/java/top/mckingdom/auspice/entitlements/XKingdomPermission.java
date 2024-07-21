package top.mckingdom.auspice.entitlements;

import org.bukkit.entity.Player;
import org.kingdoms.config.KingdomsConfig;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.constants.player.KingdomPermission;
import org.kingdoms.locale.messenger.DefinedMessenger;

public class XKingdomPermission extends KingdomPermission {
    private final DefinedMessenger messenger;
    public XKingdomPermission(Namespace namespace, DefinedMessenger messenger) {
        super(namespace);
        this.messenger = messenger;
    }

    static XKingdomPermission reg(Namespace namespace, DefinedMessenger messenger, int hash) {
        XKingdomPermission perm = new XKingdomPermission(namespace, messenger);
        perm.setHash(hash);
        return perm;
    }

    public final void sendDeniedMessage(Player player) {
        this.messenger.sendMessage(player);
        KingdomsConfig.errorSound(player);
    }

    public final DefinedMessenger getDeniedMessage() {
        return this.messenger;
    }

    public DefinedMessenger getMessenger() {
        return messenger;
    }
}
