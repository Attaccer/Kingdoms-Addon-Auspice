@file:JvmName("LandCategories")
package top.mckingdom.auspice.data.land

import org.bukkit.Chunk
import org.bukkit.Location
import org.kingdoms.constants.base.KeyedKingdomsObject
import org.kingdoms.constants.land.Land
import org.kingdoms.constants.land.abstraction.data.DeserializationContext
import org.kingdoms.constants.land.abstraction.data.SerializationContext
import org.kingdoms.constants.land.location.SimpleChunkLocation
import org.kingdoms.constants.metadata.KingdomMetadata
import org.kingdoms.constants.metadata.KingdomMetadataHandler
import org.kingdoms.constants.namespace.Namespace
import org.kingdoms.data.database.dataprovider.SectionCreatableDataSetter
import org.kingdoms.data.database.dataprovider.SectionableDataGetter
import top.mckingdom.auspice.AuspiceAddon
import top.mckingdom.auspice.land.land_categories.LandCategory
import top.mckingdom.auspice.land.land_categories.StandardLandCategory

@JvmName("setCategory")
fun Chunk.setCategory(landCategory: LandCategory) {
    Land.getLand(this)?.setCategory(landCategory)
}

@JvmName("setCategory")
fun SimpleChunkLocation.setCategory(landCategory: LandCategory) {
    this.land?.setCategory(landCategory)
}

@JvmName("setCategory")
fun Land.setCategory(landCategory: LandCategory) {
    this.getMetadata().put(LandCategoryMetaHandler.INSTANCE, LandCategoryMeta(landCategory))
}

@JvmName("getCategory")
fun Location.getCategory(): LandCategory? {
    return (SimpleChunkLocation.of(this)).getCategory()
}

@JvmName("getCategory")
fun Chunk.getCategory(): LandCategory? {
    return SimpleChunkLocation.of(this).land?.getCategory()
}

@JvmName("getCategory")
fun SimpleChunkLocation.getCategory(): LandCategory? {
    return this.land?.getCategory()
}


/**
 * Get the category of a land
 * If this land is not claimed, it will also return null
 * If this land is claimed, it will return a not null value
 */
@JvmName("getCategory")
fun Land.getCategory(): LandCategory? {
    if (this.isClaimed()) {
        return this.getCategoryData() ?: return StandardLandCategory.NONE
    } else {
        return null
    }

}


/**
 * Get the data from the metadata,
 * You shouldn't use it except you know how to use this function
 */
@JvmName("getCategoryData")
fun Land.getCategoryData() : LandCategory? {
    return this.getMetadata().get(LandCategoryMetaHandler.INSTANCE)?.getValue() as? LandCategory
}


@JvmName("clearCategoryData")
fun Land.clearCategoryData() {
    this.metadata.remove(LandCategoryMetaHandler.INSTANCE)
}





class LandCategoryMeta(private var landCategory: LandCategory) : KingdomMetadata {
    override fun getValue(): Any {
        return this.landCategory
    }

    override fun setValue(o: Any) {
        this.landCategory = o as LandCategory
    }

    override fun serialize(container: KeyedKingdomsObject<*>, context: SerializationContext<SectionCreatableDataSetter>) {
        context.dataProvider.setString(landCategory.getNamespace().asString())
    }

    override fun shouldSave(container: KeyedKingdomsObject<*>): Boolean {
        return container is Land;
    }
}


class LandCategoryMetaHandler private constructor() : KingdomMetadataHandler(Namespace("AuspiceAddon", "LAND_CATEGORY")) {
    override fun deserialize(
        container: KeyedKingdomsObject<*>,
        dataGetter: DeserializationContext<SectionableDataGetter>
    ): KingdomMetadata {
        val chunkTypeNS = Namespace.fromString(dataGetter.dataProvider.asString())
        val landCategory = AuspiceAddon.get().landCategoryRegistry.getRegistered(chunkTypeNS)
        return LandCategoryMeta(landCategory)
    }

    companion object {
        @JvmField
        val INSTANCE = LandCategoryMetaHandler()
    }
}





