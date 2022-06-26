package io.liftgate.eureka.commons.rank

import gg.scala.flavor.FlavorOptions
import gg.scala.flavor.reflections.PackageIndexer
import gg.scala.flavor.service.Close
import gg.scala.flavor.service.Configure
import gg.scala.flavor.service.Service
import gg.scala.store.controller.DataStoreObjectControllerCache
import gg.scala.store.storage.type.DataStoreStorageType
import io.liftgate.eureka.commons.lease
import io.liftgate.eureka.commons.lease.LeaseStrategy
import io.liftgate.eureka.commons.rank.metadata.RankMetadata
import io.liftgate.eureka.commons.rank.metadata.annotate.MetadataDefault
import io.liftgate.eureka.commons.rank.metadata.defaults.DisplayNameMetadata
import io.liftgate.eureka.commons.rank.metadata.defaults.NameMetadata
import io.liftgate.eureka.commons.rank.metadata.defaults.VisibilityMetadata
import io.liftgate.eureka.commons.rank.metadata.defaults.WeightMetadata
import io.liftgate.eureka.commons.text
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * @author GrowlyX
 * @since 6/26/2022
 */
@Service
object RankService
{
    private val ranks =
        mutableMapOf<UUID, Rank>()

    private val metadataIndexer = PackageIndexer(
        RankMetadata::class,
        FlavorOptions(
            `package` = "io.liftgate.eureka.commons.rank.metadata.defaults"
        )
    )

    @Suppress("UNCHECKED_CAST")
    private val defaults = metadataIndexer
        .getTypesAnnotatedWith<MetadataDefault>()
        .map { it as Class<RankMetadata<*>> }

    fun available() = ranks.size

    @Configure
    fun configure()
    {
        DataStoreObjectControllerCache
            .create<Rank>()

        invalidate()
        reload()
    }

    @Close
    fun close() = invalidate()

    fun configureDefaultMeta(rank: Rank)
    {
        rank.container.registerDefaults(
            *defaults.map { it.kotlin }.toTypedArray()
        )
    }

    // TODO: invalidate lease cache on new rank creation/update
    val sortedRankFormat by lease(
        strategy = LeaseStrategy.Compute,
        dependencies = listOf(Rank::class)
    ) {
        val component = Component.empty()
        val ranks = ranks.values

        val comma = Component.text()
            .color(NamedTextColor.WHITE)
            .append(", ".text())

        ranks
            .filter {
                it.container
                    .metadata<VisibilityMetadata>()
                    .value
            }
            .sortedBy {
                -it.container
                    .metadata<WeightMetadata>()
                    .value
            }
            .forEachIndexed { index, rank ->
                val container = rank.container
                    .metadata<DisplayNameMetadata>()
                    .value

                component.append(container)

                if (index != ranks.size - 1)
                {
                    component.append(comma)
                }
            }

        component
    }

    fun findRank(uuid: UUID): Rank?
    {
        return ranks.values
            .firstOrNull {
                it.identifier == uuid
            }
    }

    fun findRank(name: String): Rank?
    {
        return ranks.values
            .firstOrNull {
                it.container
                    .metadata<NameMetadata>()
                    .value.equals(name, true)
            }
    }

    fun getDefaultRank(): Rank
    {
        return findRank("Default") ?: createDefaultRank()
    }

    fun invalidate()
    {
        this.ranks.clear()
    }

    fun reload(): CompletableFuture<Void>
    {
        return DataStoreObjectControllerCache
            .findNotNull<Rank>()
            .loadAll(DataStoreStorageType.MONGO)
            .thenAccept { entries ->
                this.ranks.putAll(entries)

                this.ranks.ifEmpty {
                    createDefaultRank()
                }
            }
    }

    private fun createDefaultRank(): Rank
    {
        val rank = Rank(
            UUID.randomUUID()
        )

        this.ranks[rank.identifier] = rank
        return rank
    }
}
