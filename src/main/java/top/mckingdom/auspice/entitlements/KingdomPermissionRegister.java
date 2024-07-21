package top.mckingdom.auspice.entitlements;

import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.constants.player.KingdomPermission;
import org.kingdoms.locale.messenger.DefinedMessenger;
import top.mckingdom.auspice.utils.MessengerUtil;

import java.util.HashMap;
import java.util.Map;

public final class KingdomPermissionRegister {

    public static final KingdomPermission PERMISSION_USE_BOATS = register("AuspiceAddon", "USE_BOATS", "You don't have permission to use boats");
    public static final KingdomPermission PERMISSION_TRANSFER_MEMBERS = register("AuspiceAddon", "TRANSFER_MEMBERS");

    /**
     * 注册一个外交属性
     * @param namespace 你的插件的标识符,只包含大小写英文字母,建议驼峰命名法,首字母大写,比如"PeaceTreaties"
     * @param keyword 你要注册的王国权限的关键字,只能全部大写英文字母和下划线,比如"ENDER_PEARL_TELEPORT"
     * @return 你所注册的王国权限的一个对象
     */
    public static KingdomPermission register(String namespace, String keyword) {
        return register(namespace, keyword, "You don't have this kingdom-permission:" + keyword.toLowerCase());
    }

    public static KingdomPermission register(String namespace, String keyword, String defaultMessage) {
        Namespace ns = new Namespace(namespace, keyword);
        DefinedMessenger m = MessengerUtil.createMessenger(new String[]{"permissions", keyword}, defaultMessage);
        return register(ns, m, defaultMessage);
    }

    public static KingdomPermission register(Namespace namespace, DefinedMessenger messenger, String defaultMessage) {
        XKingdomPermission perm = XKingdomPermission.reg(namespace, messenger, Companion.permissions.size() + 90);
        Companion.permissionMessages.put(namespace, defaultMessage);
        return perm;
    }

    public static void init() {

    }


    private static class Companion {
        public static final Map<Namespace, KingdomPermission> permissions = new HashMap<>();
        public static final Map<Namespace, String> permissionMessages = new HashMap<>();
        public static final Map<Namespace, String> permissionLore = new HashMap<>();
    }
}
