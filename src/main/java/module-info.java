module top.mckingdom.Kingdoms.Addon.Auspice.main {
    requires org.bukkit;
    requires KingdomsX;
    requires org.checkerframework.checker.qual;
    exports top.mckingdom.auspice.data.land;
    exports top.mckingdom.auspice.commands.admin.land_category;
    exports top.mckingdom.auspice.commands.admin.relation_attribute_for_duoduojuzi;
    exports top.mckingdom.auspice.land_categories;
    exports top.mckingdom.auspice.managers;
    exports top.mckingdom.auspice.configs;
    exports top.mckingdom.auspice.costs;
    exports top.mckingdom.auspice.costs.types;
    exports top.mckingdom.auspice.utils;
    exports top.mckingdom.auspice.services;
}