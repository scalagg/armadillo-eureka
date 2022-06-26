package io.liftgate.eureka.commons

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import java.time.temporal.ChronoUnit
import java.util.*

/**
 * @author GrowlyX
 * @since 6/25/2022
 */
val forever = ChronoUnit.FOREVER.duration

val platform: LiftEurekaPlatform
    get() = LiftEurekaCommons.platform

fun String.text() =
    Component.text(this)

fun String.legacy() = LegacyComponentSerializer
    .legacyAmpersand().deserialize(this)

fun UUID.audience() = platform.audience
