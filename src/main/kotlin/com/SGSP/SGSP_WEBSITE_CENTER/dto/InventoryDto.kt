package com.SGSP.SGSP_WEBSITE_CENTER.dto

data class InventoryDto(
    val sku: String? = null,
    val stockQuantity: Int? = null,
    val lowStockThreshold: Int? = null
)
