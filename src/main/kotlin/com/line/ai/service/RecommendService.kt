package com.line.ai.service

import com.line.ai.dto.LLMDTO
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor
import org.springframework.stereotype.Service

@Service
class RecommendService(private val customRecommendClient: ChatClient) {

    suspend fun recommend(prompt: String): LLMDTO.Response {
        return customRecommendClient
            .prompt()
            .user { spec ->
                spec.param("prompt", prompt)
            }
            .advisors(SimpleLoggerAdvisor())
            .call()
            .entity(LLMDTO.Response::class.java) ?: throw Exception("No response")
    }
}