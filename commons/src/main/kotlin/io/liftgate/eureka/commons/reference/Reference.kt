package io.liftgate.eureka.commons.reference

import io.liftgate.lib.serialization.impl.AbstractTypeSerializable

/**
 * @author GrowlyX
 * @since 6/26/2022
 */
interface Reference<T, U> : AbstractTypeSerializable
{
    val original: T

    fun parsed(): U?
}
