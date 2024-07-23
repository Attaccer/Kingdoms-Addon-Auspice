package top.mckingdom.auspice.data.land

import org.bukkit.Chunk
import org.bukkit.Location
import org.kingdoms.constants.land.Land
import org.kingdoms.constants.land.location.SimpleChunkLocation
import org.kingdoms.constants.metadata.KingdomMetadata
import top.mckingdom.auspice.land_categories.LandCategory
import top.mckingdom.auspice.land_categories.StandardLandCategory
import java.util.*

class LandCategoryData {


    fun Chunk.setCategory(landCategory: LandCategory) {
        Land.getLand(this)?.setCategory(landCategory)
    }
    fun SimpleChunkLocation.setCategory(landCategory: LandCategory) {
        this.land?.setCategory(landCategory)
    }

    fun Land.setCategory(landCategory: LandCategory) {
        this.metadata[LandCategoryMetaHandler.INSTANCE]!!.value = landCategory
    }

    fun Location.getCategory(): LandCategory? {
        return (SimpleChunkLocation.of(this)).getCategory()
    }

    fun Chunk.getCategory(): LandCategory? {
        return SimpleChunkLocation.of(this).land?.getCategory()
    }


    fun SimpleChunkLocation.getCategory(): LandCategory? {
        return this.land?.getCategory()
    }


    /**
     * 如果Land未被占领且未分配Category，返回null
     * 如果Land已被占领，返回非null
     */
    fun Land.getCategory(): LandCategory? {
        var meta: KingdomMetadata? = this.metadata[LandCategoryMetaHandler.INSTANCE]
        if (!this.isClaimed && meta === null) {
            return null
        } else {
            if (meta === null) {
                meta = LandCategoryMeta(StandardLandCategory.NULL)
            }
            return meta.value as LandCategory
        }

    }

    fun Land.clearCategory(): Unit {
        this.metadata.remove(LandCategoryMetaHandler.INSTANCE)
    }


}