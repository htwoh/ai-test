package com.line.ai.dto

object LLMDTO {

    data class Response(
        val kr: String,
        val jp: String,
        val us: String
    )

    data class LLMResponse(val text: String)
}