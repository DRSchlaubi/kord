package dev.kord.core.cache.data

import dev.kord.common.entity.DiscordWelcomeScreen
import dev.kord.common.entity.DiscordWelcomeScreenChannel
import dev.kord.common.entity.Snowflake
import kotlinx.serialization.Serializable

@Serializable
data class WelcomeScreenData(
        val description: String?,
        val welcomeChannels: List<WelcomeScreenChannelData>,
) {
    companion object {
        fun from(data: DiscordWelcomeScreen): WelcomeScreenData {
            with(data) {
                return WelcomeScreenData(description, welcomeChannels.map { WelcomeScreenChannelData.from(it) })
            }
        }
    }
}

@Serializable
data class WelcomeScreenChannelData(
        val channelId: Snowflake,
        val description: String,
        val emojiId: Snowflake?,
        val emojiName: String?,
) {
    companion object {
        fun from(data: DiscordWelcomeScreenChannel): WelcomeScreenChannelData {
            with(data) {
                return WelcomeScreenChannelData(channelId, description, emojiId, emojiName)
            }
        }
    }
}