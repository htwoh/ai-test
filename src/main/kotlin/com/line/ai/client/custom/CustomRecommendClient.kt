package com.line.ai.client.custom

import com.line.ai.model.ExternalChatModel
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.messages.SystemMessage
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.chat.prompt.DefaultChatOptions
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CustomRecommendClient(
    private val externalChatModel: ExternalChatModel
) {
    val systemMessage = """
            You are a professional multilingual marketing translator.
            
            CRITICAL RULES:
            1. ONLY return valid JSON format
            2. NO explanations, NO additional text
            3. Detect input language automatically
            4. Translate/adapt to all 3 target languages
            
            OUTPUT FORMAT (EXACT):
            {"kr":"text","jp":"text","us":"text"}
        """.trimIndent()

    val userMessage =
        """
            Please translate and adapt: {prompt}
            Make it compelling for marketing purposes.
        """.trimIndent()

    val chatOptions = DefaultChatOptions().apply {
        temperature = 0.2
        maxTokens = 600
        topP = 0.4
    }

    @Bean
    fun recommendClient() : ChatClient {
        return ChatClient.builder(externalChatModel)
            .defaultSystem(systemMessage)
            .defaultUser(userMessage)
            .defaultOptions(chatOptions)
            .build()
    }
}