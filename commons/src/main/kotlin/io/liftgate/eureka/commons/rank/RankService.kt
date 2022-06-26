package io.liftgate.eureka.commons.rank

import gg.scala.flavor.service.Configure
import gg.scala.flavor.service.Service
import gg.scala.store.controller.DataStoreObjectControllerCache
import gg.scala.store.storage.type.DataStoreStorageType
import io.liftgate.eureka.commons.rank.metadata.defaults.NameMetadata
import java.util.UUID
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

    @Configure
    fun configure()
    {
        DataStoreObjectControllerCache
            .create<Rank>()

        invalidate()
        reload()
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
