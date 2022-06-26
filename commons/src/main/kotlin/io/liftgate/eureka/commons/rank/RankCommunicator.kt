package io.liftgate.eureka.commons.rank

import gg.scala.aware.annotation.Subscribe
import gg.scala.aware.message.AwareMessage
import io.liftgate.eureka.commons.communication.Communications

/**
 * @author GrowlyX
 * @since 6/26/2022
 */
@Communications
object RankCommunicator
{
    @Subscribe("rank-update")
    fun onRankUpdate(
        message: AwareMessage
    )
    {
        
    }
}
