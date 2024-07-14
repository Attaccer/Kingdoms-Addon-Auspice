package top.mckingdom.auspice.permissions;

import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.constants.player.KingdomPermission;
import org.kingdoms.main.Kingdoms;

import java.util.HashMap;
import java.util.Map;

public final class KingdomPermissionAutoRegister extends KingdomPermission {

    public static final KingdomPermission PERMISSION_USE_BOATS = register("AuspiceAddon", "USE_BOATS");
    public static final KingdomPermission PERMISSION_TRANSFER_MEMBERS = register("AuspiceAddon", "TRANSFER_MEMBERS");


    public KingdomPermissionAutoRegister(Namespace namespace) {
        super(namespace);
    }

    /**
     * 注册一个外交属性
     * @param namespace 你的插件的标识符,只包含大小写英文字母,建议驼峰命名法,首字母大写,比如"PeaceTreaties"
     * @param keyword 你要注册的王国权限的关键字,只能全部大写英文字母和下划线,比如"ENDER_PEARL_TELEPORT"
     * @return 你所注册的王国权限的一个对象
     */
    public static KingdomPermission register(String namespace, String keyword) {
        Namespace ns = new Namespace(namespace, keyword);;
        KingdomPermissionAutoRegister perm = new KingdomPermissionAutoRegister(ns);
        KingdomPermissionAutoRegister.Companion.permissonMap.put(ns, perm);
        perm.setHash(KingdomPermissionAutoRegister.Companion.permissonMap.size() + 514);
        Kingdoms.get().getPermissionRegistery().register(perm);
        return perm;
    }

    public static KingdomPermission register(String namespace, String keyword, String defaultLore) {
        KingdomPermission perm = register(namespace, keyword);
        Companion.permissionLoreMap.put(perm, defaultLore);
        return perm;
    }

    public static void init() {

    }

    private static class Companion {
        public static final Map<Namespace, KingdomPermission> permissonMap = new HashMap<>();

        public static final Map<KingdomPermission, String> permissionLoreMap = new HashMap<>();
    }
}
