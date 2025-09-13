package com.line.ai.model

import com.line.ai.client.custom.LocalLlmClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.springframework.ai.chat.messages.AssistantMessage
import org.springframework.ai.chat.messages.SystemMessage
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.ai.chat.model.Generation
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.stereotype.Component

@Component
class ExternalChatModel(
    private val localLlmClient: LocalLlmClient
) : ChatModel {
    override fun call(prompt: Prompt): ChatResponse {
        val combinedPrompt = prompt.instructions.joinToString(separator = "\n") { msg ->
            when (msg) {
                is SystemMessage -> "System: ${msg.text}"
                is UserMessage -> "User: ${msg.text}"
                is AssistantMessage -> "Assistant: ${msg.text}"
                else -> throw Exception("Unknown message type: ${msg}")
            }
        }

        val output = runBlocking(Dispatchers.IO) {
            localLlmClient.generateText(combinedPrompt)
        }

        val assistant = AssistantMessage(output.text)
        val generation = Generation(assistant)
        return ChatResponse(listOf(generation))
    }
}