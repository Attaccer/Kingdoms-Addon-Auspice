package top.mckingdom.auspice.costs.types;

import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.utils.compilers.MathCompiler;
import org.kingdoms.utils.config.ConfigSection;
import top.mckingdom.auspice.costs.Cost;

public abstract class DoubleCost<T> extends Cost<T, Double> {

    public DoubleCost(Namespace namespace) {
        super(namespace);
    }

    @Override
    public boolean canExpend(ConfigSection section) {
        return false;
    }

    @Override
    public void expend(ConfigSection section) {
        MathCompiler.Expression exp = MathCompiler.compile(section.getName());  //TODO

        exp.contains(MathCompiler.ConstantExpr.class, (var0) -> var0.getType() == MathCompiler.ConstantExprType.NUMBER);

        section.

    }


}
