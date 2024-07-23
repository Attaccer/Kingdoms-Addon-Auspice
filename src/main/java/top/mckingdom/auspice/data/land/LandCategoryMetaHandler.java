package top.mckingdom.auspice.data.land;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.kingdoms.constants.KingdomsObject;
import org.kingdoms.constants.land.abstraction.data.DeserializationContext;
import org.kingdoms.constants.metadata.KingdomMetadata;
import org.kingdoms.constants.metadata.KingdomMetadataHandler;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.data.database.dataprovider.SectionableDataGetter;
import top.mckingdom.auspice.AuspiceAddon;
import top.mckingdom.auspice.land_categories.LandCategory;

public class LandCategoryMetaHandler extends KingdomMetadataHandler {
    private LandCategoryMetaHandler() {
        super(new Namespace("KingdomChunks", "CHUNK_TYPE"));
    }

    @Override
    public @NonNull KingdomMetadata deserialize(@NonNull KingdomsObject<?> container, @NonNull DeserializationContext<SectionableDataGetter> dataGetter) {
        Namespace chunkTypeNS = Namespace.fromString(dataGetter.getDataProvider().asString());     //鬼知道会不会爆空指针,先这样留着罢,等爆空指针了再来看(
        LandCategory landCategory = AuspiceAddon.get().getLandCategoryRegistry().getRegistered(chunkTypeNS);//通过Namespace从已注册的ChunkType里面寻找对应的区块类型
        return new LandCategoryMeta(landCategory);
    }

    public static final LandCategoryMetaHandler INSTANCE;

    static {
        INSTANCE = new LandCategoryMetaHandler();
        System.out.println("区块类型MetadataHandler的单例是" + INSTANCE);
    }

}