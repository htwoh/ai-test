package com.line.ai.client.custom

import com.line.ai.dto.LLMDTO
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Component
class LocalLlmClient(
    builder: WebClient.Builder
) {
    private val llmClient: WebClient = builder.baseUrl("http://localhost:8000").build()

    suspend fun generateText(prompt: String): LLMDTO.LLMResponse {
        return llmClient.post()
            .uri("/generate")
            .bodyValue(mapOf("prompt" to prompt))
            .retrieve()
            .awaitBody<LLMDTO.LLMResponse>()
    }
}