package io.liftgate.eureka.commons

import net.kyori.adventure.platform.AudienceProvider
import java.util.logging.Logger

/**
 * @author GrowlyX
 * @since 6/26/2022
 */
interface LiftEurekaPlatform
{
    val audience: Lazy<AudienceProvider>
    val logging: Logger
}
