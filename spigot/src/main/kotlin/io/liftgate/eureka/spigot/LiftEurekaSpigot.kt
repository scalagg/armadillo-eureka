package io.liftgate.eureka.spigot

import io.liftgate.eureka.commons.LiftEurekaCommons
import io.liftgate.eureka.commons.LiftEurekaPlatform
import io.liftgate.lib.spigot.InjectedPlugin
import io.liftgate.lib.spigot.annotations.container.ContainerDisable
import io.liftgate.lib.spigot.annotations.container.ContainerEnable
import me.lucko.helper.plugin.ap.Plugin
import me.lucko.helper.plugin.ap.PluginDependency
import net.kyori.adventure.platform.bukkit.BukkitAudiences

/**
 * @author GrowlyX
 * @since 6/26/2022
 */
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

    override val logging = this.logger!!

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
