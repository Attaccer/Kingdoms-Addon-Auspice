@file:JvmName("LandContractions")
package top.mckingdom.auspice.data.land

import org.kingdoms.constants.base.KeyedKingdomsObject
import org.kingdoms.constants.land.Land
import org.kingdoms.constants.land.abstraction.data.DeserializationContext
import org.kingdoms.constants.land.abstraction.data.SerializationContext
import org.kingdoms.constants.metadata.KingdomMetadata
import org.kingdoms.constants.metadata.KingdomMetadataHandler
import org.kingdoms.constants.namespace.Namespace
import org.kingdoms.constants.player.KingdomPlayer
import org.kingdoms.data.database.dataprovider.SectionCreatableDataSetter
import org.kingdoms.data.database.dataprovider.SectionableDataGetter
import top.mckingdom.auspice.AuspiceAddon
import top.mckingdom.auspice.land.land_contractions.LandContraction
import java.util.*

typealias ContractionsData = HashMap<LandContraction, HashSet<UUID>>


fun KingdomPlayer.hasLandContraction(land: Land, contraction: LandContraction) : Boolean {
    return land.getContractions(this).contains(contraction)
}


/**
 * Add a contraction for a kingdom player
 */
@JvmName("addContraction")
fun Land.addContraction(contraction: LandContraction, player: KingdomPlayer) {
    this.getContractions()?.getOrPut(contraction) { hashSetOf() }!!.add(player.getId())
}

/**
 * Remove a contraction for a kingdom player,
 * if this player don't have this contraction, it will remove nothing
 */
@JvmName("removeContraction")
fun Land.removeContraction(contraction: LandContraction, player: KingdomPlayer) {
    this.getContractions()?.get(contraction)?.remove(player.getId())
}

fun Land.getContractions(player: KingdomPlayer) : Collection<LandContraction> {
    val out = HashSet<LandContraction>()
    this.getContractions()?.forEach { entry ->
        if (entry.value.contains(player.getId())) {
            out.add(entry.key)
        }
    }
    return out
}

/**
 * Get all contractions with a land,
 * if this land isn't claimed, it will return null,
 * if this land is claimed, it will return a not null value
 */
@JvmName("getContractions")
fun Land.getContractions(): ContractionsData? {
    if (this.isClaimed()) {
        val data = this.getContractionsData()
        if (data == null) {
            this.resetContractionsData()
            return this.getContractionsData()
        } else {
            return data
        }
    } else {
        return null
    }

}


/**
 * Reset the contractions data of a land
 */
@JvmName("resetContractionsData")
fun Land.resetContractionsData() {
    this.getMetadata().put(
        LandContractionMetaHandler.INSTANCE,
        LandContractionMeta(
            HashMap<LandContraction, HashSet<UUID>>().also {
                AuspiceAddon.get().getLandContractionRegistry().getRegistry().forEach { _, contraction ->
                    it.put(contraction, HashSet())
                }
            }
        )
    )
}

@JvmName("setContractions")
fun Land.setContractions(contractions: ContractionsData) {
    this.getMetadata().put(LandContractionMetaHandler.INSTANCE, LandContractionMeta(contractions))
}

/**
 * Get the data from the metadata,
 * You shouldn't use it except you know how to use this function
 */
@JvmName("getContractionsData")
fun Land.getContractionsData() : ContractionsData? {
    return this.getMetadata().get(LandContractionMetaHandler.INSTANCE)?.getValue() as? ContractionsData
}

@JvmName("clearContractionsData")
fun Land.clearContractionsData() {
    this.getMetadata().remove(LandContractionMetaHandler.INSTANCE)
}


class LandContractionMeta(private var landContractions: ContractionsData) : KingdomMetadata {
    override fun getValue(): Any {
        return this.landContractions
    }

    override fun setValue(o: Any) {
        this.landContractions = o as ContractionsData
    }

    override fun serialize(container: KeyedKingdomsObject<*>, context: SerializationContext<SectionCreatableDataSetter>) {
        context.getDataProvider().setMap(landContractions) { contraction, keyProvider, players ->
            keyProvider.setString(contraction.getNamespace().asDataString())
            keyProvider.getValueProvider().setCollection(players) { playerKeyProvider, player ->
                playerKeyProvider.setUUID(player)
            }
        }
    }

    override fun shouldSave(container: KeyedKingdomsObject<*>): Boolean {
        return container is Land
    }
}


class LandContractionMetaHandler private constructor() : KingdomMetadataHandler(Namespace("AuspiceAddon", "LAND_CONTRACTION")) {
    override fun deserialize(
        container: KeyedKingdomsObject<*>,
        dataGetter: DeserializationContext<SectionableDataGetter>
    ): KingdomMetadata {
        return LandContractionMeta(dataGetter.getDataProvider().asMap(hashMapOf()) { map, key, value ->
            val contraction : LandContraction = AuspiceAddon.get().landContractionRegistry.getRegistered(Namespace.fromConfigString(key.asString()))
            val players : HashSet<UUID> = value.asCollection(HashSet()) { set, dataGetter ->
                set.add(dataGetter.asUUID())
            }
            map.put(contraction, players)
        })
    }

    companion object {
        @JvmStatic
        val INSTANCE: LandContractionMetaHandler = LandContractionMetaHandler()
    }
}

