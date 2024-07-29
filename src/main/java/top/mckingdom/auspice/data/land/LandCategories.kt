@file:JvmName("LandCategories")
package top.mckingdom.auspice.data.land

import org.bukkit.Chunk
import org.bukkit.Location
import org.jetbrains.annotations.NotNull
import org.kingdoms.constants.KingdomsObject
import org.kingdoms.constants.land.Land
import org.kingdoms.constants.land.abstraction.data.DeserializationContext
import org.kingdoms.constants.land.abstraction.data.SerializationContext
import org.kingdoms.constants.land.location.SimpleChunkLocation
import org.kingdoms.constants.metadata.KingdomMetadata
import org.kingdoms.constants.metadata.KingdomMetadataHandler
import org.kingdoms.constants.namespace.Namespace
import org.kingdoms.data.database.dataprovider.SectionCreatableDataSetter
import org.kingdoms.data.database.dataprovider.SectionableDataGetter
import org.kingdoms.libs.kotlin.jvm.functions.Function1
import org.kingdoms.locale.compiler.container.MessageContainer
import org.kingdoms.locale.placeholders.*
import top.mckingdom.auspice.AuspiceAddon
import top.mckingdom.auspice.land_categories.LandCategory
import top.mckingdom.auspice.land_categories.LandCategoryRegistry
import top.mckingdom.auspice.land_categories.StandardLandCategory
import java.util.*

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
public fun SimpleChunkLocation.getCategory(): LandCategory? {
    return this.land?.getCategory()
}


/**
 * Get the category of a land
 * 不会返回null值
 */
@JvmName("getCategory")
public fun Land.getCategory(): LandCategory {
    val data = this.getCategoryData()
    if (data == null) {
        return StandardLandCategory.NONE
    } else {
        return data
    }
}


/**
 * 获取存储的数据, 可能会因为根本没存数据而返回null
 */
@JvmName("getCategoryData")
fun Land.getCategoryData() : LandCategory? {
    val meta = this.getMetadata().get(LandCategoryMetaHandler.INSTANCE)
    if (meta  == null) {
        return null
    } else {
        return meta.getValue() as LandCategory
    }

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

    override fun serialize(container: KingdomsObject<*>, context: SerializationContext<SectionCreatableDataSetter>) {
        context.dataProvider.setString(landCategory.namespace.asString())
    }

    override fun shouldSave(container: KingdomsObject<*>): Boolean {
//        return container instanceof Land;
        return true
    } //应该是用于限定ChunkTypeMeta存储的对象
}


class LandCategoryMetaHandler private constructor() : KingdomMetadataHandler(Namespace("AuspiceAddon", "LAND_CATEGORY")) {
    override fun deserialize(
        container: KingdomsObject<*>,
        dataGetter: DeserializationContext<SectionableDataGetter>
    ): KingdomMetadata {
        val chunkTypeNS = Namespace.fromString(dataGetter.dataProvider.asString())
        val landCategory = AuspiceAddon.get().landCategoryRegistry.getRegistered(chunkTypeNS)
        return LandCategoryMeta(landCategory)
    }

    companion object {
        val INSTANCE: LandCategoryMetaHandler = LandCategoryMetaHandler()
    }
}
@Suppress("unused")
enum class LandCategoryPlaceholder(private val default : Any, private val translator : Function1<KingdomsPlaceholderTranslationContext, Any>) : KingdomsPlaceholderTranslator {


    LAND_LAND_CATEGORY(StandardLandCategory.NONE, { context -> context.land.getCategory() }),

    KINGDOM_LAND_AMOUNT_CATEGORY_NULL(0, { context ->
        var amount = 0
        context.kingdom.lands.forEach { land -> if (land.getCategory() == StandardLandCategory.NONE) { amount++ } }
        amount
    }),

    KINGDOM_LAND_AMOUNT_CATEGORY(0, object : FunctionalPlaceholder() {
        @PlaceholderFunction
        fun of(context: KingdomsPlaceholderTranslationContext, @PlaceholderParameter(name = "category") category: String): Any {
            val kingdom = context.kingdom
            var amount = 0
            val standardCategory = LandCategoryRegistry.getLandCategoryFromConfigName(category)

            if (standardCategory === null) {
                return 0
            }

            kingdom.lands.forEach { land: Land ->
                if (land.getCategory() == standardCategory) {
                    amount ++
                }
            }
            return amount
        }
    }
    ),

    ;

    private var GLOBAL_MACROS : Map<String, MessageContainer>

    @JvmField
    val IDENTIFIER: String = "auspices"

    @NotNull
    @JvmField
    val OTHER_IDENTIFIER: String = "other_"

    private var configuredDefaultValue : Any?

    init {
        configuredDefaultValue = default
        KingdomsPlaceholderTranslator.register(this)
        GLOBAL_MACROS = HashMap()
    }

    override fun getName(): String {
        return this.name.lowercase(Locale.ENGLISH)
    }

    override fun getDefault(): Any {
        return this.default
    }

    override fun getConfiguredDefaultValue(): Any? {
        return configuredDefaultValue
    }

    override fun setConfiguredDefaultValue(value: Any?) {
        configuredDefaultValue = value
    }

    override fun translate(context: KingdomsPlaceholderTranslationContext): Any {
        return this.translator.invoke(context)
    }

    override fun getFunctions(): MutableMap<String, FunctionalPlaceholder.CompiledFunction>? {
        var function1: Function1<*, *>?
        val var10000 =
            if (translator.also { function1 = it } is FunctionalPlaceholder) function1 as FunctionalPlaceholder? else null
        return var10000?.functions
    }





    companion object {
        @JvmStatic
        fun init() {

        }

    }

}



