package io.liftgate.eureka.commons.rank.metadata.defaults

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

/**
 * @author GrowlyX
 * @since 6/26/2022
 */
class NameMetadata : DefaultMetadata<String>("Default")
{
    override fun parse(input: String) = input

    override fun name() = "Name"
    override fun description() =
        "Describes the rank mentioned in a single word."
}

class DisplayNameMetadata : MiniMessageMetadata(
    Component.empty()
        .color(NamedTextColor.GRAY)
        .append(
            Component.text("Default")
        )
)
{
    override fun name() = "Display Name"
    override fun description() =
        "Displayed on the response of a list command, scoreboard, or something else."
}

class PrefixMetadata : MiniMessageMetadata(
    Component.empty().color(NamedTextColor.GRAY)
)
{
    override fun name() = "Prefix"
    override fun description() =
        "Displayed at the beginning of the player's username in a chat message."
}

class SuffixMetadata : MiniMessageMetadata(
    Component.empty()
)
{
    override fun name() = "Suffix"
    override fun description() =
        "Displayed at the end of the player's username in a chat message."
}

class ColorMetadata : MiniMessageMetadata(
    Component.empty().color(NamedTextColor.GRAY)
)
{
    override fun name() = "Color"
    override fun description() =
        "Displayed at the beginning of a name-tag, or something of that sort."
}

class VisibilityMetadata : BooleanMetadata(true)
{
    override fun name() = "Visibility"
    override fun description() =
        "Is this a public rank or no? You decide"
}

class WeightMetadata : IntegerMetadata(0)
{
    override fun name() = "Weight"
    override fun description() =
        "Where does this rank lie in the list of available ranks?"
}


