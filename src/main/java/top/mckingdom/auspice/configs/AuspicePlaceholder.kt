package top.mckingdom.auspice.configs

import org.kingdoms.constants.land.Land
import org.kingdoms.libs.kotlin.jvm.functions.Function1
import org.kingdoms.locale.placeholders.*
import top.mckingdom.auspice.data.land.getCategory
import top.mckingdom.auspice.land.land_categories.LandCategoryRegistry
import top.mckingdom.auspice.land.land_categories.StandardLandCategory
import java.util.*

@Suppress("unused")                                                 //val translator: PlaceholderTranslator   因为kotlin插件不认识KingdomsX的typealias
enum class AuspicePlaceholder(private val default : Any, private val translator : Function1<KingdomsPlaceholderTranslationContext, Any>) :
    KingdomsPlaceholderTranslator {


    LAND_CATEGORY(StandardLandCategory.NONE, { context -> context.getLand().getCategory() }),

    KINGDOM_LANDS_OF_CATEGORY_NONE(0, { context ->
        var amount = 0
        context.kingdom.lands.forEach { land ->
            if (land.getCategory() == StandardLandCategory.NONE) {
                amount++
            }
        }
        amount
    }),

    KINGDOM_LANDS_OF_CATEGORY(0, object : FunctionalPlaceholder() {
        @PlaceholderFunction
        fun of(
            context: KingdomsPlaceholderTranslationContext,
            @PlaceholderParameter(name = "category") category: String
        ): Any {
            val kingdom = context.getKingdom()
            var amount = 0
            val category1 = LandCategoryRegistry.getLandCategoryFromConfigName(category) ?: return 0

            kingdom.lands.forEach { land: Land ->
                if (land.getCategory() == category1) {
                    amount++
                }
            }
            return amount
        }
    }
    ),

    ;


    private var configuredDefaultValue: Any?

    init {
        configuredDefaultValue = default
        KingdomsPlaceholderTranslator.register(this)
    }

    override fun getName(): String {
        return this.name.lowercase(Locale.ENGLISH)
    }


    //override val default
    override fun getDefault(): Any {
        return this.default
    }


    //override var configuredDefaultValue
    override fun getConfiguredDefaultValue(): Any? {
        return configuredDefaultValue
    }

    override fun setConfiguredDefaultValue(value: Any?) {
        configuredDefaultValue = value
    }


    override fun translate(context: KingdomsPlaceholderTranslationContext): Any {
        return this.translator.invoke(context)
    }

    override fun getFunctions(): MutableMap<String, FunctionalPlaceholder.CompiledFunction>? =
        (this.translator as? FunctionalPlaceholder)?.functions


    companion object {
        @JvmStatic
        fun init() {

        }

    }
}