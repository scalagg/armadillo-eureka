package io.liftgate.eureka.commons.rank.metadata

import io.liftgate.eureka.commons.rank.metadata.defaults.ColorMetadata
import io.liftgate.eureka.commons.rank.metadata.defaults.DisplayNameMetadata
import io.liftgate.eureka.commons.rank.metadata.defaults.NameMetadata
import io.liftgate.eureka.commons.rank.metadata.defaults.PrefixMetadata
import io.liftgate.eureka.commons.rank.metadata.defaults.SuffixMetadata
import io.liftgate.eureka.commons.rank.metadata.defaults.VisibilityMetadata
import io.liftgate.eureka.commons.rank.metadata.defaults.WeightMetadata
import kotlin.reflect.KClass

/**
 * @author GrowlyX
 * @since 6/26/2022
 */
class RankMetadataContainer
{
    val metadata =
        mutableMapOf<KClass<out RankMetadata<*>>, RankMetadata<*>>()

    inline fun <reified T : RankMetadata<*>> metadata() =
        this.metadata[T::class]!! as T

    inline fun <reified T : RankMetadata<*>> value() =
        this.metadata<T>().value

    init
    {
        if (this.metadata.isEmpty())
        {
            this.registerDefaults(
                NameMetadata::class,
                DisplayNameMetadata::class,
                PrefixMetadata::class,
                SuffixMetadata::class,
                ColorMetadata::class,
                VisibilityMetadata::class,
                WeightMetadata::class
            )
        }
    }

    fun registerDefaults(
        vararg kClasses: KClass<out RankMetadata<*>>
    )
    {
        for (kClass in kClasses)
        {
            this.metadata[kClass] = kClass.java.newInstance()
        }
    }
}
