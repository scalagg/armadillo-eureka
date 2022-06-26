package io.liftgate.eureka.commons

import gg.scala.flavor.Flavor
import gg.scala.flavor.FlavorOptions
import gg.scala.flavor.reflections.PackageIndexer

/**
 * @author GrowlyX
 * @since 6/26/2022
 */
object LiftEurekaCommons
{
    lateinit var platform: LiftEurekaPlatform

    val flavor = Flavor
        .create<LiftEurekaCommons>(
            FlavorOptions(platform.logging)
        )

    fun configure()
    {
        flavor.bind<LiftEurekaPlatform>() to platform
        flavor.bind<PackageIndexer>() to flavor.reflections
        flavor.startup()
    }

    fun close()
    {
        flavor.close()
    }
}
