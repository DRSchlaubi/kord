package com.gitlab.kordlib.core.entity

import com.gitlab.kordlib.core.KordObject

interface Entity : KordObject {
    val snowflake: Snowflake
}
