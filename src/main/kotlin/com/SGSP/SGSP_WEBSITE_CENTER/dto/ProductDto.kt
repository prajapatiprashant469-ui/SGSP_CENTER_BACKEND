package com.SGSP.SGSP_WEBSITE_CENTER.dto

import java.time.Instant

data class ProductDto(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val categoryId: String? = null,
    val categoryName: String? = null,
    val status: String? = null,
    val attributes: AttributesDto? = null,
    val pricing: PricingDto? = null,
    val inventory: InventoryDto? = null,
    val images: List<ImageDto> = emptyList(),
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null
)
