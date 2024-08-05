package top.mckingdom.auspice.costs.std;

import org.kingdoms.constants.group.Kingdom;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.utils.config.ConfigSection;
import top.mckingdom.auspice.AuspiceAddon;
import top.mckingdom.auspice.costs.Cost;

public class KingdomResourcePointsCost extends Cost<Kingdom, Long> {

    public static final KingdomResourcePointsCost INSTANCE = new KingdomResourcePointsCost();

    private KingdomResourcePointsCost() {
        super(AuspiceAddon.buildNS("COST_KINGDOM_RP"), Kingdom.class);
    }

    @Override
    public boolean canExpend(Kingdom kingdom, Long amount) {
        return kingdom.hasResourcePoints(amount);
    }

    @Override
    public boolean canExpend(Kingdom kingdom, ConfigSection section) {
        return this.canExpend(kingdom, section.getLong());
    }


    @Override
    public void expend(Kingdom target, Long amount) {

    }

    @Override
    public void expend(Kingdom kingdom, ConfigSection section) {

    }

    @Override
    public Cost<?, ?> getInstance() {
        return INSTANCE;
    }


}
