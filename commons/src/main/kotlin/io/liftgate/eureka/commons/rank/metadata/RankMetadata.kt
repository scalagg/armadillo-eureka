package io.liftgate.eureka.commons.rank.metadata

/**
 * @author GrowlyX
 * @since 6/26/2022
 */
interface RankMetadata<T>
{
    var value: T

    fun update(input: String)
    fun reset()

    fun name(): String
    fun description(): String
}
