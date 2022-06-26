package io.liftgate.eureka.commons.rank

import gg.scala.store.storage.storable.IDataStoreObject
import io.liftgate.eureka.commons.rank.metadata.RankMetadataContainer
import io.liftgate.eureka.commons.rank.metadata.defaults.ColorMetadata
import net.kyori.adventure.text.Component
import java.util.*

/**
 * @author GrowlyX
 * @since 6/26/2022
 */
data class Rank(
    override val identifier: UUID
) : IDataStoreObject
{
    val container = RankMetadataContainer()

    init
    {
        if (this.container.metadata.isEmpty())
        {
            RankService.configureDefaultMeta(this)
        }
    }
}
