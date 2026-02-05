package com.SGSP.SGSP_WEBSITE_CENTER.dto

data class CategoryDto(
    var id: String? = null,
    var name: String = "",
    var slug: String = "",
    var parentId: String? = null,
    var description: String? = null,
    var archived: Boolean = false,
    var createdAt: String? = null,
    var updatedAt: String? = null
)
