package com.line.ai.client.custom

import org.springframework.ai.embedding.EmbeddingResponse
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Component
class EmbeddingClient(builder: WebClient.Builder) {

    private val embeddingClient: WebClient = builder.baseUrl("http://localhost:8001").build()

    suspend fun getEmbedding(text: String): EmbeddingResponse {
        return embeddingClient.post()
            .uri("/embed")
            .bodyValue(mapOf("text" to text))
            .retrieve()
            .awaitBody<EmbeddingResponse>()
    }
}