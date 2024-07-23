package top.mckingdom.auspice.data.land;


import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.KingdomsObject;
import org.kingdoms.constants.land.abstraction.data.SerializationContext;
import org.kingdoms.constants.metadata.KingdomMetadata;
import org.kingdoms.data.database.dataprovider.SectionCreatableDataSetter;
import top.mckingdom.auspice.land_categories.LandCategory;

public class LandCategoryMeta implements KingdomMetadata {

    public LandCategoryMeta(LandCategory landCategory) {
        this.landCategory = landCategory;
    }
    private LandCategory landCategory;
    @NotNull
    @Override
    public Object getValue() {
        return this.landCategory;
    }
    @Override
    public void setValue(@NotNull Object o) {
        this.landCategory = (LandCategory) o ;
    }

    @Override
    public void serialize(@NotNull KingdomsObject<?> container, @NotNull SerializationContext<SectionCreatableDataSetter> context) {
        context.getDataProvider().setString(this.landCategory.getNamespace().asString());
    }

    @Override
    public boolean shouldSave(@NotNull KingdomsObject<?> container) {
//        return container instanceof Land;
        return true;
    }   //应该是用于限定ChunkTypeMeta存储的对象


}
