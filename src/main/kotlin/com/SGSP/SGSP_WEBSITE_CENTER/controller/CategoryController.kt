package com.SGSP.SGSP_WEBSITE_CENTER.controller

import com.SGSP.SGSP_WEBSITE_CENTER.dto.CategoryDto
import com.SGSP.SGSP_WEBSITE_CENTER.service.CategoryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/api/admin/v1/categories")
class CategoryController(private val categoryService: CategoryService) {

    @GetMapping
    fun getAll(): Flux<CategoryDto> = categoryService.getAll()
}
