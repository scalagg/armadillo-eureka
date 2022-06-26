package io.liftgate.eureka.commons.reference.impl

import io.liftgate.eureka.commons.rank.Rank
import io.liftgate.eureka.commons.rank.RankService
import io.liftgate.eureka.commons.reference.Reference
import java.lang.reflect.Type
import java.util.UUID

/**
 * @author GrowlyX
 * @since 6/26/2022
 */
class RankReference(
    override val original: UUID
) : Reference<UUID, Rank>
{
    override fun getAbstractType() = Reference::class.java

    override fun parsed() =
        RankService.findRank(original)
}
