package com.SGSP.SGSP_WEBSITE_CENTER.service

import com.SGSP.SGSP_WEBSITE_CENTER.dto.CategoryDto
import org.bson.Document
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class CategoryService(private val reactiveMongoTemplate: ReactiveMongoTemplate) {

    fun getAll(): Flux<CategoryDto> =
        reactiveMongoTemplate.findAll(Document::class.java, "categories")
            .map { mapDocumentToDto(it) }

    private fun mapDocumentToDto(d: Document): CategoryDto {
        fun extractId(idObj: Any?): String? =
            when (idObj) {
                is ObjectId -> idObj.toHexString()
                is Document -> (idObj.get("\$oid") ?: idObj.getString("\$oid"))?.toString()
                null -> null
                else -> idObj.toString()
            }

        return CategoryDto(
            id = extractId(d.get("_id")),
            name = d.getString("name") ?: "",
            slug = d.getString("slug") ?: "",
            parentId = d.getString("parentId"),
            description = d.getString("description"),
            archived = (d.get("archived") as? Boolean) ?: false,
            createdAt = d.getString("createdAt"),
            updatedAt = d.getString("updatedAt")
        )
    }
}
