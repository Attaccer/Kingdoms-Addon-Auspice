package top.mckingdom.auspice.costs.types;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.kingdoms.constants.group.Kingdom;
import org.kingdoms.constants.group.Nation;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.constants.player.KingdomPlayer;
import org.kingdoms.libs.snakeyaml.validation.StandardValidator;
import org.kingdoms.libs.xseries.XMaterial;
import org.kingdoms.utils.config.CustomConfigValidators;
import top.mckingdom.auspice.AuspiceAddon;
import top.mckingdom.auspice.costs.Cost;
import top.mckingdom.auspice.costs.CostRegistry;
import top.mckingdom.auspice.costs.RefundableCost;
import top.mckingdom.auspice.utils.ItemUtil;

import java.util.List;
import java.util.Map;

public class StandardCostType {

    public static final Cost<KingdomPlayer, Long> COST_KINGDOM_PLAYER_KINGDOM_RP  = new RefundableCost<>(AuspiceAddon.buildNS("KP_KINGDOM_RP")) {
        @Override
        public void refund(@NonNull KingdomPlayer kPlayer, @NonNull Long amount) {
            if (kPlayer.getKingdom() != null) {
                COST_KINGDOM_RP.refund(kPlayer.getKingdom(), amount);
            }
        }

        @Override
        public boolean canExpend(@NonNull KingdomPlayer kPlayer, @NonNull Long amount) {
            if (kPlayer.getKingdom() == null) {
                return false;
            }
            return COST_KINGDOM_RP.canExpend(kPlayer.getKingdom(), amount);
        }

        @Override
        public void expend(@NonNull KingdomPlayer kPlayer, @NonNull Long amount) {
            if (kPlayer.getKingdom() != null) {
                COST_KINGDOM_RP.expend(kPlayer.getKingdom(), amount);
            }
        }
    };



    /**
     * 王国资源点
     */
    public static final RefundableCost<Kingdom, Long> COST_KINGDOM_RP = new RefundableCost<>(
            AuspiceAddon.buildNS("KINGDOM_RP"),
            CustomConfigValidators.getValidators().get(CustomConfigValidators.MATH.getValue())
    ) {

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
    public static final Cost<Kingdom, Double> COST_KINGDOM_PLAYER_KINGDOM_BANK = new Cost<>(AuspiceAddon.buildNS("KINGDOM_BANK")) {

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
     * 王国的银行
     */
    public static final Cost<Kingdom, Double> COST_KINGDOM_BANK = new Cost<>(
            AuspiceAddon.buildNS( "KINGDOM_BANK"),
            StandardValidator.NULL
    ) {

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
    public static final Cost<Kingdom, Integer> COST_KINGDOM_MAX_LANDS_MODIFIER = new Cost<>(
            AuspiceAddon.buildNS( "KINGDOM_MAX_LANDS_MODIFIER")
    ) {

        @Override
        public boolean canExpend(@NonNull Kingdom kingdom, @NonNull Integer amount) {
            return true;
        }
        @Override
        public void expend(@NonNull Kingdom kingdom, @NonNull Integer amount) {
            kingdom.setMaxLandsModifier(kingdom.getMaxLandsModifier() - amount);

        }
        @Override
        public String toString() {
            return "COST_KINGDOM_MAX_LANDS_MODIFIER";
        }

    };



    /**
     * 联邦的资源点
     * resource points of nation
     */
    public static final Cost<Nation, Long> COST_NATION_RP = new Cost<>(
            AuspiceAddon.buildNS( "NATION_RP")
    ) {

        @Override
        public boolean canExpend(@NonNull Nation nation, @NonNull Long amount) {
            return nation.getResourcePoints() >= amount;
        }
        @Override
        public void expend(@NonNull Nation nation, @NonNull Long amount) {
            nation.setResourcePoints(-amount);
        }

        @Override
        public String toString() {
            return "COST_NATION_RP";
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
        @Override
        public String toString() {
            return "COST_NATION_BANK";
        }

    };

    /**
     * 玩家背包中的多个物品堆
     * 日后可能将进行性能优化
     */
    public static final Cost<Player, List<ItemStack>> COST_ROUGH_PLAYER_ITEMS = new Cost<>(
            new Namespace("AuspiceAddon", "ROUGH_PLAYER_ITEMS"),
            CustomConfigValidators.getValidators().get(CustomConfigValidators.ITEM_STACK.getValue())
    ) {
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

        @Override
        public String toString() {
            return "COST_ROUGH_PLAYER_ITEMS";
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
        @Override
        public String toString() {
            return "COST_ROUGH_PLAYER_ITEM";
        }

    };


    public static final Cost<Player, Map<XMaterial, Integer>> COST_PLAYER_MATERIALS = new Cost<>(
            new Namespace("AuspiceAddon", "PLAYER_MATERIALS")
    ) {
        @Override
        public boolean canExpend(@NonNull Player player, @NonNull Map<XMaterial, Integer> materials) {
            return false;
            //TODO
        }

        @Override
        public void expend(@NonNull Player player, @NonNull Map<XMaterial, Integer> materials) {
            //TODO
        }

        @Override
        public String toString() {
            return "COST_PLAYER_MATERIALS";
        }
    };


    static Namespace buildNS(String s) {
        return new Namespace("AuspiceAddon", s);
    }
    
    
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
