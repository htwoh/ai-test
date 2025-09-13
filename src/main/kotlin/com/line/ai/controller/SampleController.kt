package com.line.ai.controller

import com.line.ai.dto.LLMDTO
import com.line.ai.service.RecommendService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
class SampleController(private val recommendService: RecommendService) {

    @GetMapping("/api/v1/custom/recommend")
    suspend fun qa(@RequestParam query: String): ResponseEntity<LLMDTO.Response> {
        return ResponseEntity.ok(recommendService.recommend(query))
    }

}