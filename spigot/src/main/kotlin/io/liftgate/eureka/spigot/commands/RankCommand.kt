package io.liftgate.eureka.spigot.commands

import io.liftgate.lib.spigot.acf.annotation.CommandAlias
import io.liftgate.lib.spigot.acf.annotation.CommandPermission
import io.liftgate.lib.spigot.annotations.commands.AutoRegister
import io.liftgate.lib.spigot.command.LiftCommand

/**
 * @author GrowlyX
 * @since 6/26/2022
 */
@AutoRegister
@CommandAlias("rank|ranks")
@CommandPermission("eureka.command.rank")
object RankCommand : LiftCommand()
{

}
