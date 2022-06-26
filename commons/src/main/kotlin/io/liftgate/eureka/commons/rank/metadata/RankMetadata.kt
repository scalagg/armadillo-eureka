package io.liftgate.eureka.commons.rank.metadata

import io.liftgate.lib.serialization.impl.AbstractTypeSerializable

/**
 * @author GrowlyX
 * @since 6/26/2022
 */
interface RankMetadata<T> : AbstractTypeSerializable
{
    var value: T

    fun update(input: String)
    fun reset()

    fun name(): String
    fun description(): String
}
