package io.liftgate.eureka.commons

import gg.scala.flavor.Flavor
import gg.scala.flavor.FlavorOptions
import gg.scala.flavor.reflections.PackageIndexer
import io.liftgate.eureka.commons.rank.metadata.RankMetadata
import io.liftgate.eureka.commons.reference.Reference
import io.liftgate.lib.serialization.Serializers
import io.liftgate.lib.serialization.impl.AbstractTypeSerializer

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
        Serializers.useGsonBuilderThenRebuild {
            it.registerTypeHierarchyAdapter(
                Reference::class.java,
                AbstractTypeSerializer<Reference<*, *>>()
            )

            it.registerTypeHierarchyAdapter(
                RankMetadata::class.java,
                AbstractTypeSerializer<RankMetadata<*>>()
            )
        }

        flavor.bind<LiftEurekaPlatform>() to platform
        flavor.bind<PackageIndexer>() to flavor.reflections
        flavor.startup()
    }

    fun close()
    {
        flavor.close()
    }
}
