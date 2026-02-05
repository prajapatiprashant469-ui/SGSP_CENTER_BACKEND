package com.SGSP.SGSP_WEBSITE_CENTER.controller

import com.SGSP.SGSP_WEBSITE_CENTER.dto.ProductDto
import com.SGSP.SGSP_WEBSITE_CENTER.service.ProductService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/api/admin/v1/products")
class ProductController(private val productService: ProductService) {

    @GetMapping
    fun getAll(): Flux<ProductDto> = productService.getAll()
}
