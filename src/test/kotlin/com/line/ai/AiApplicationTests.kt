package com.line.ai

import com.line.ai.client.custom.LocalLlmClient
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.collections.toFloatArray

@SpringBootTest
class AiApplicationTests {

    @Autowired
    lateinit var llmClient: LocalLlmClient

    @Test
    fun contextLoads(): Unit = runBlocking {
        println(llmClient.getEmbedding("Hello, world!").map { it.toFloat() }.toFloatArray())
    }

}
