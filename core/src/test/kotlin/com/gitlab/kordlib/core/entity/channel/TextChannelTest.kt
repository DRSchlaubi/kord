package com.gitlab.kordlib.core.entity.channel

import com.gitlab.kordlib.common.entity.ChannelType
import com.gitlab.kordlib.core.cache.data.ChannelData
import equality.GuildChannelEqualityTest
import io.mockk.mockk

@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
internal class TextChannelTest : GuildChannelEqualityTest<TextChannel> by GuildChannelEqualityTest({ id, guildId ->
    TextChannel(ChannelData(id.longValue, guildId = guildId.longValue, type = ChannelType.GuildNews), mockk())
})