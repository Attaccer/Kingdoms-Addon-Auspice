package top.mckingdom.auspice.costs;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.kingdoms.constants.group.Kingdom;
import org.kingdoms.constants.group.Nation;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.libs.xseries.XMaterial;
import top.mckingdom.auspice.utils.ItemUtil;

import java.util.List;
import java.util.Map;

public class StandardCostType {
    /**
     * 王国资源点
     */
    public static final Cost<Kingdom, Long> COST_KINGDOM_RP = new RefundableCost<>(new Namespace("AuspiceAddon", "KINGDOM_RP")) {

        @Override
        public boolean canExpend(@NonNull Kingdom kingdom, @NonNull Long amount) {
            return kingdom.getResourcePoints() >= amount;
        }

        @Override
        public void expend(@NonNull Kingdom kingdom, @NonNull Long amount) {
            kingdom.addResourcePoints(-amount);
        }

        @Override
        public void refund(@NonNull Kingdom kingdom, @NonNull Long amount) {
            kingdom.addResourcePoints(amount);
        }
    };


    /**
     * 王国的银行
     */
    public static final Cost<Kingdom, Double> COST_KINGDOM_BANK = new Cost<>(new Namespace("AuspiceAddon", "KINGDOM_BANK")) {

        @Override
        public boolean canExpend(@NonNull Kingdom kingdom, @NonNull Double amount) {
            return kingdom.getBank() >= amount;
        }

        @Override
        public void expend(@NonNull Kingdom kingdom, @NonNull Double amount) {
            kingdom.setBank(kingdom.getBank() - amount);
        }

    };

    /**
     * 王国的最大土地数量调节器
     * maxLandsModifier
     */
    public static final Cost<Kingdom, Integer> COST_KINGDOM_MAX_LANDS_MODIFIER = new Cost<>(new Namespace("AuspiceAddon", "KINGDOM_MAX_LANDS_MODIFIER")) {

        @Override
        public boolean canExpend(@NonNull Kingdom kingdom, @NonNull Integer amount) {
            return true;
        }
        @Override
        public void expend(@NonNull Kingdom kingdom, @NonNull Integer amount) {
            kingdom.setMaxLandsModifier(kingdom.getMaxLandsModifier() - amount);

        }

    };



    /**
     * 联邦的资源点
     * resource points of nation
     */
    public static final Cost<Nation, Long> COST_NATION_RP = new Cost<>(new Namespace("AuspiceAddon", "NATION_RP")) {

        @Override
        public boolean canExpend(@NonNull Nation nation, @NonNull Long amount) {
            return nation.getResourcePoints() >= amount;
        }
        @Override
        public void expend(@NonNull Nation nation, @NonNull Long amount) {
            nation.setResourcePoints(-amount);
        }

    };


    /**
     * 联邦的银行
     */
    public static final Cost<Nation, Double> COST_NATION_BANK = new Cost<>(new Namespace("AuspiceAddon", "NATION_BANK")) {

        @Override
        public boolean canExpend(@NonNull Nation nation, @NonNull Double amount) {
            return nation.getBank() >= amount;
        }

        @Override
        public void expend(@NonNull Nation nation, @NonNull Double amount) {
            nation.setBank(nation.getBank() - amount);
        }

    };

    /**
     * 玩家背包中的多个物品堆
     * 日后可能将进行性能优化
     */
    public static final Cost<Player, List<ItemStack>> COST_ROUGH_PLAYER_ITEMS = new Cost<>(new Namespace("AuspiceAddon", "ROUGH_PLAYER_ITEMS")) {
        @Override
        public boolean canExpend(@NonNull Player player, @NonNull List<ItemStack> items) {
            boolean out = true;
            for (ItemStack itemStack : items) {
                if (!COST_ROUGH_PLAYER_ITEM.canExpend(player, itemStack)) {
                    out = false;
                }
            }
            return out;
        }

        @Override
        public void expend(@NonNull Player player, @NonNull List<ItemStack> items) {
            for (ItemStack itemStack : items) {
                COST_ROUGH_PLAYER_ITEM.expend(player, itemStack);
            }
        }
    };      


    /**
     * 玩家背包中的物品
     */
    public static final Cost<Player, ItemStack> COST_ROUGH_PLAYER_ITEM = new Cost<>(new Namespace("AuspiceAddon", "ROUGH_PLAYER_ITEM")) {
        @Override
        public boolean canExpend(@NonNull Player player, @NonNull ItemStack item) {
            return ItemUtil.hasItem(player.getInventory(), item.getType(), item.getAmount());
        }

        @Override
        public void expend(@NonNull Player player, @NonNull ItemStack item) {
            ItemUtil.removeItem(player.getInventory(), item.getType(), item.getAmount());
        }
    };


    public static final Cost<Player, Map<XMaterial, Integer>> COST_PLAYER_MATERIALS = new Cost<>(new Namespace("AuspiceAddon", "PLAYER_MATERIALS")) {
        @Override
        public boolean canExpend(@NonNull Player player, @NonNull Map<XMaterial, Integer> materials) {
            return false;
            //TODO
        }

        @Override
        public void expend(@NonNull Player player, @NonNull Map<XMaterial, Integer> materials) {
            //TODO
        }
    };
    
    
    public static void init() {
        CostRegistry.INSTANCE.register(COST_KINGDOM_RP);
        CostRegistry.INSTANCE.register(COST_KINGDOM_BANK);
        CostRegistry.INSTANCE.register(COST_KINGDOM_MAX_LANDS_MODIFIER);
        CostRegistry.INSTANCE.register(COST_NATION_RP);
        CostRegistry.INSTANCE.register(COST_NATION_BANK);
        CostRegistry.INSTANCE.register(COST_ROUGH_PLAYER_ITEM);
        CostRegistry.INSTANCE.register(COST_ROUGH_PLAYER_ITEMS);
        CostRegistry.INSTANCE.register(COST_PLAYER_MATERIALS);
    }
    

}
