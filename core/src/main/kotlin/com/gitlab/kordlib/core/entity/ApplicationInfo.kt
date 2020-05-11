package com.gitlab.kordlib.core.entity

import com.gitlab.kordlib.common.entity.Snowflake
import com.gitlab.kordlib.core.Kord
import com.gitlab.kordlib.core.behavior.UserBehavior
import com.gitlab.kordlib.core.behavior.channel.TextChannelBehavior
import com.gitlab.kordlib.core.cache.data.ApplicationInfoData
import java.util.*

class ApplicationInfo(val data: ApplicationInfoData, override val kord: Kord) : Entity {

    override val id: Snowflake
        get() = Snowflake(data.id)

    val name: String get() = data.name

    val description: String? get() = data.description

    val isPublic: Boolean get() = data.botPublic

    val requireCodeGrant: Boolean get() = data.botRequireCodeGrant

    val ownerId: Snowflake get() = Snowflake(data.ownerId)

    val owner: UserBehavior get() = UserBehavior(ownerId, kord)

    suspend fun getOwner(): User = kord.getUser(ownerId)!!

    override fun hashCode(): Int = Objects.hash(id)

    override fun equals(other: Any?): Boolean = when(other) {
        is ApplicationInfo -> other.id == id
        else -> false
    }
}