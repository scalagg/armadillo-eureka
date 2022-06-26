package io.liftgate.eureka.commons.lease

import java.util.concurrent.ForkJoinPool
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

/**
 * @author GrowlyX
 * @since 6/26/2022
 */
class Lease<T>(
    private val compute: () -> T,
    private val strategy: LeaseStrategy,
    val dependencies: List<Any>
) : ReadOnlyProperty<Any?, T?>
{
    private var value: T? = null
    private var valueExpired: T? = null

    private var lock = Any()

    init
    {
        LeaseDependency.leases.add(this)

        if (this.strategy == LeaseStrategy.Eager)
        {
            runCatching {
                this.compute()
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    fun invalidate()
    {
        this.valueExpired = this.value
        this.value = null
    }

    private fun compute() =
        this.compute.invoke()
            .apply {
                synchronized(lock)
                {
                    value = this
                }
            }

    private fun asyncCompute()
    {
        ForkJoinPool.commonPool()
            .execute {
                compute()
            }
    }

    private fun value(): T?
    {
        if (this.value == null)
        {
            return when (strategy)
            {
                LeaseStrategy.Compute, LeaseStrategy.Eager -> this.compute()
                LeaseStrategy.Expired ->
                {
                    this.asyncCompute()
                    this.valueExpired
                }
            }
        }

        return this.value
    }

    override fun getValue(
        thisRef: Any?, property: KProperty<*>
    ) = this.value()
}
