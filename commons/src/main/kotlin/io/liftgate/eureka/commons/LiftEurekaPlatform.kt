package io.liftgate.eureka.commons

import net.kyori.adventure.platform.AudienceProvider

/**
 * @author GrowlyX
 * @since 6/26/2022
 */
interface LiftEurekaPlatform
{
    val audience: AudienceProvider

    fun configure()
    {
        // TODO: configure abstracttypeserializable for RankMetadata
    }
}
