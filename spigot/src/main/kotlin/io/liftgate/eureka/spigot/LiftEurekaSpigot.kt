package io.liftgate.eureka.spigot

import io.liftgate.eureka.commons.LiftEurekaCommons
import io.liftgate.eureka.commons.LiftEurekaPlatform
import io.liftgate.eureka.commons.PlatformType
import io.liftgate.eureka.commons.configuration.EurekaGeneralConfig
import io.liftgate.lib.spigot.InjectedPlugin
import io.liftgate.lib.spigot.annotations.container.ContainerDisable
import io.liftgate.lib.spigot.annotations.container.ContainerEnable
import io.liftgate.lib.spigot.config.annotations.ContainerConfig
import me.lucko.helper.plugin.ap.Plugin
import me.lucko.helper.plugin.ap.PluginDependency
import net.kyori.adventure.platform.bukkit.BukkitAudiences

/**
 * @author GrowlyX
 * @since 6/26/2022
 */
@ContainerConfig(
    value = "settings",
    model = EurekaGeneralConfig::class
)
@Plugin(
    name = "eureka",
    description = "Next-generation cross-platform network base.",
    depends = [
        PluginDependency("LiftLib"),
        PluginDependency("store-spigot"),
    ]
)
class LiftEurekaSpigot : InjectedPlugin(), LiftEurekaPlatform
{
    override val audience = lazy {
        BukkitAudiences.create(this)
    }

    override val generalConfig = lazy {
        this.config<EurekaGeneralConfig>()
    }

    override val logging = this.logger!!
    override val platformType = PlatformType.Spigot

    @ContainerEnable
    fun containerEnable()
    {
        LiftEurekaCommons.platform = this
        LiftEurekaCommons.configure()
    }

    @ContainerDisable
    fun containerDisable()
    {
        LiftEurekaCommons.close()
    }
}
