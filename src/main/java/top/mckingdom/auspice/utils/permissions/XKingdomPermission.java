package top.mckingdom.auspice.utils.permissions;

import org.bukkit.entity.Player;
import org.kingdoms.config.KingdomsConfig;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.constants.player.KingdomPermission;
import org.kingdoms.locale.messenger.DefinedMessenger;
import org.kingdoms.main.Kingdoms;

public class XKingdomPermission extends KingdomPermission {
    private final DefinedMessenger messenger;
    private final String defaultMessage;
    private final String defaultLore;
    public XKingdomPermission(Namespace namespace, DefinedMessenger messenger, String defaultMessage, String defaultLore) {
        super(namespace);
        this.messenger = messenger;
        this.defaultMessage = defaultMessage;
        this.defaultLore = defaultLore;
    }

    static XKingdomPermission reg(Namespace namespace, DefinedMessenger messenger, String defaultMessage, String defaultLore, int hash) {
        XKingdomPermission perm = new XKingdomPermission(namespace, messenger, defaultMessage, defaultLore);
        perm.setHash(hash);
        Kingdoms.get().getPermissionRegistery().register(perm);
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

    public String getDefaultMessage() {
        return this.defaultMessage;
    }
}
