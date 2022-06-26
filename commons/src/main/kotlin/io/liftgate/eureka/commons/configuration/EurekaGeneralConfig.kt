package io.liftgate.eureka.commons.configuration

/**
 * @author GrowlyX
 * @since 6/26/2022
 */
data class EurekaGeneralConfig(
    val identifier: String,
    val groups: List<String>
)
