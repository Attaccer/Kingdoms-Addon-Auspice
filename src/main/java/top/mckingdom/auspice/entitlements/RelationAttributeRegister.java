package top.mckingdom.auspice.entitlements;

import org.kingdoms.constants.group.Group;
import org.kingdoms.constants.group.model.relationships.RelationAttribute;
import org.kingdoms.constants.group.model.relationships.StandardRelationAttribute;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.main.Kingdoms;

import java.util.HashMap;
import java.util.Map;

public class RelationAttributeRegister extends RelationAttribute {

    public static final RelationAttribute BEACON_EFFECTS = register("AuspiceAddon", "BEACON_EFFECTS");
    public static final RelationAttribute ENDER_PEARL_TELEPORT = register("AuspiceAddon", "ENDER_PEARL_TELEPORT");
    public static final RelationAttribute DIRECTLY_TRANSFER_MEMBER = register("AuspiceAddon", "DIRECTLY_TRANSFER_MEMBER");

    public RelationAttributeRegister(Namespace namespace) {
        super(namespace);
    }

    public static void init() {
    }

    @Override
    public boolean hasAttribute(Group group, Group anotherGroup) {
        return StandardRelationAttribute.hasAttribute(this, group, anotherGroup);
    }

    /**
     * 注册一个外交属性
     * @param namespace 你的插件的标识符,只包含大小写英文字母,建议驼峰命名法,首字母大写,比如"PeaceTreaties"
     * @param keyword 你要注册的外交属性的关键字,只能全部大写英文字母和下划线,比如"ENDER_PEARL_TELEPORT"
     * @return 你所注册的外交属性
     */
    public static RelationAttribute register(String namespace, String keyword) {
        Namespace ns = new Namespace(namespace, keyword);
        RelationAttributeRegister attr = new RelationAttributeRegister(ns);
        Companion.attributes.put(ns, attr);
        attr.setHash(Companion.attributes.size() + 514);
        Kingdoms.get().getRelationAttributeRegistry().register(attr);
        return attr;
    }

    public static XRelationAttribute register(Namespace namespace, String defaultLore) {
        return XRelationAttribute.reg(namespace, defaultLore, Companion.attributes.size() + 31);
    }

    private static class Companion {
        public static final Map<Namespace, RelationAttribute> attributes = new HashMap<>();
    }

}
