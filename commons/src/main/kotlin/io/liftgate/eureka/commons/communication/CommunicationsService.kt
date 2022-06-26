package io.liftgate.eureka.commons.communication

import gg.scala.aware.AwareBuilder
import gg.scala.aware.codec.codecs.interpretation.AwareMessageCodec
import gg.scala.aware.message.AwareMessage
import gg.scala.flavor.inject.Inject
import gg.scala.flavor.reflections.PackageIndexer
import gg.scala.flavor.service.Close
import gg.scala.flavor.service.Configure
import gg.scala.flavor.service.Service
import io.liftgate.eureka.commons.LiftEurekaPlatform

/**
 * @author GrowlyX
 * @since 6/26/2022
 */
@Service
object CommunicationsService
{
    @Inject
    lateinit var platform: LiftEurekaPlatform

    @Inject
    lateinit var indexer: PackageIndexer

    val aware by lazy {
        AwareBuilder.of<AwareMessage>("eureka")
            .logger(platform.logging)
            .codec(AwareMessageCodec)
            .build()
    }

    @Configure
    fun configure()
    {
        indexer.getTypesAnnotatedWith<Communications>()
            .forEach {
                aware.listen(it.kotlin.objectInstance!!)
            }

        aware.connect()
            .toCompletableFuture()
            .join()
    }

    @Close
    fun close()
    {
        aware.shutdown()
    }
}
