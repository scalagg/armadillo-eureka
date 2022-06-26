package io.liftgate.eureka.commons.lease

/**
 * @author GrowlyX
 * @since 6/26/2022
 */
object LeaseDependency
{
    val leases = mutableListOf<Lease<*>>()

    fun invalidateDependant(
        dependency: Any
    )
    {
        this.leases
            .filter {
                it.dependencies
                    .contains(dependency)
            }
            .forEach {
                it.invalidate()
            }
    }
}
