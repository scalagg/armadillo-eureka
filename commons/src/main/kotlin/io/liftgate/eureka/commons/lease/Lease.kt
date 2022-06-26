package io.liftgate.eureka.commons.lease

import java.util.concurrent.ForkJoinPool
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author GrowlyX
 * @since 6/26/2022
 */
class Lease<T>(
    private val compute: () -> T
) : ReadWriteProperty<Any?, T?>
{
    private var value: T? = null
    private var valueExpired: T? = null

    fun invalidate()
    {
        this.valueExpired = this.value
        this.value = null
    }

    private fun asyncCompute()
    {
        ForkJoinPool.commonPool().execute {
            this.compute.invoke().let { this.value = it }
        }
    }

    private fun value(): T?
    {
        if (this.value == null)
        {
            this.asyncCompute()
            return this.valueExpired
        }

        return this.value
    }

    override fun getValue(
        thisRef: Any?, property: KProperty<*>
    ) = this.value()

    override fun setValue(
        thisRef: Any?, property: KProperty<*>, value: T?
    )
    {
        throw IllegalStateException("You cannot set.")
    }
}
