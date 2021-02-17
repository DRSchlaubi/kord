package dev.kord.gateway

import dev.kord.common.ratelimit.BucketRateLimiter
import dev.kord.common.ratelimit.RateLimiter
import dev.kord.gateway.retry.LinearRetry
import dev.kord.gateway.retry.Retry
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.util.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlin.time.seconds

class DefaultGatewayBuilder {
    var url = "wss://gateway.discord.gg/?v=8&encoding=json&compress=zlib-stream"
    var client: HttpClient? = null
    var reconnectRetry: Retry? = null
    var sendRateLimiter: RateLimiter? = null
    var identifyRateLimiter: RateLimiter? = null
    var dispatcher: CoroutineDispatcher = Dispatchers.Default
    var eventFlow: MutableSharedFlow<Event> = MutableSharedFlow(extraBufferCapacity = Int.MAX_VALUE)

    @OptIn(KtorExperimentalAPI::class, ObsoleteCoroutinesApi::class)
    fun build(): DefaultGateway {
        val client = client ?: HttpClient(CIO) {
            install(WebSockets)
            install(JsonFeature)
        }

        val retry = reconnectRetry ?: LinearRetry(2.seconds, 20.seconds, 10)
        val sendRateLimiter = sendRateLimiter ?: BucketRateLimiter(120, 60.seconds)
        val identifyRateLimiter = identifyRateLimiter ?: BucketRateLimiter(1, 5.seconds)

        val data = DefaultGatewayData(
            url,
            client,
            retry,
            sendRateLimiter,
            identifyRateLimiter,
            dispatcher,
            eventFlow
        )

        return DefaultGateway(data)
    }

}
