package com.line.ai.model

import com.line.ai.client.custom.EmbeddingClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.springframework.ai.document.Document
import org.springframework.ai.embedding.Embedding
import org.springframework.ai.embedding.EmbeddingModel
import org.springframework.ai.embedding.EmbeddingRequest
import org.springframework.ai.embedding.EmbeddingResponse
import org.springframework.stereotype.Component

@Component
class ExternalEmbeddingModel(
    private val embeddingClient: EmbeddingClient
) : EmbeddingModel {
    override fun dimensions(): Int {
        return 768
    }

    override fun call(request: EmbeddingRequest): EmbeddingResponse {
        val texts: List<String> = request.instructions

        val embeddings = runBlocking(Dispatchers.IO) {
            texts.mapIndexed { index, text ->
                val vec = embeddingClient.getEmbedding(text)
                Embedding(vec.result.output, index)
            }
        }

        return EmbeddingResponse(embeddings)
    }

    override fun embed(document: Document): FloatArray  = runBlocking {
        embeddingClient.getEmbedding(document.text!!).result.output
    }

}