//package top.mckingdom.auspice.configs;
//
//import org.kingdoms.constants.namespace.Namespace;
//import org.kingdoms.libs.snakeyaml.nodes.ScalarNode;
//import org.kingdoms.libs.snakeyaml.nodes.Tag;
//import org.kingdoms.libs.snakeyaml.validation.NodeValidator;
//import org.kingdoms.libs.snakeyaml.validation.ValidationContext;
//import org.kingdoms.libs.snakeyaml.validation.ValidationFailure;
//import top.mckingdom.auspice.costs.Cost;
//import top.mckingdom.auspice.costs.CostRegistry;
//
//public class CustomConfigValidators {
//
//    private static final Tag COST = org.kingdoms.utils.config.CustomConfigValidators.register("Cost", new c());
//
//    public static void init() {
//    }
//
//    private static final class c implements NodeValidator {
//
//        @Override
//        public ValidationFailure validate(ValidationContext context) {
//            Tag tag = context.getNode().getTag();
//            if (tag == COST) {
//                return null;
//            } else if (tag != Tag.STR){
//                return context.err("Expected a cost name, instead got " + tag);
//            } else {
//                ScalarNode scalarNode = (ScalarNode) context.getNode();
//                Cost<?, ?> cost = CostRegistry.INSTANCE.getRegistered(Namespace.fromString(scalarNode.getValue()));
//                if (cost == null) {
//                    return context.err("Unknown Cost type '" + scalarNode.getValue() + '\'');
//                } else {
//                    scalarNode.setTag(COST);
//                    scalarNode.cacheConstructed(cost);
//
//                    cost.compile(context);
//
//                    return null;
//                }
//            }
//
//
//
//
//
//        }
//    }
//
//
//}
