package com.SGSP.SGSP_WEBSITE_CENTER.service

import com.SGSP.SGSP_WEBSITE_CENTER.dto.*
import org.bson.Document
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.time.Instant
import java.time.format.DateTimeParseException

@Service
class ProductService(private val reactiveMongoTemplate: ReactiveMongoTemplate) {

    fun getAllProducts(): Flux<ProductDto> =
        reactiveMongoTemplate.findAll(Document::class.java, "products")
            .map { mapDocumentToDto(it) }

    // new delegating method so callers can use getAll()
    fun getAll(): Flux<ProductDto> = getAllProducts()

    private fun mapDocumentToDto(d: Document): ProductDto {
        fun extractId(idObj: Any?): String? =
            when (idObj) {
                is ObjectId -> idObj.toHexString()
                is Document -> (idObj.get("\$oid") ?: idObj.getString("\$oid"))?.toString()
                null -> null
                else -> idObj.toString()
            }

        val id = extractId(d.get("_id"))
        val name = d.getString("name")
        val description = d.getString("description")
        val categoryId = d.getString("categoryId")
        val categoryName = d.getString("categoryName")
        val status = d.getString("status")

        val attributes = (d.get("attributes") as? Document)?.let { attr ->
            AttributesDto(
                materialUsed = attr.getString("materialUsed"),
                machineUsed = attr.getString("machineUsed"),
                zariUsed = attr.getString("zariUsed"),
                design = attr.getString("design"),
                color = attr.getString("color"),
                length = attr.getString("length"),
                fabricType = attr.getString("fabricType"),
                washCare = attr.getString("washCare"),
                workerAssigned = attr.getString("workerAssigned")
            )
        }

        val pricing = (d.get("pricing") as? Document)?.let { p ->
            PricingDto(
                price = (p.get("price") as? Number)?.toDouble(),
                currency = p.getString("currency"),
                discountType = p.getString("discountType"),
                discountValue = (p.get("discountValue") as? Number)?.toDouble()
            )
        }

        val inventory = (d.get("inventory") as? Document)?.let { i ->
            InventoryDto(
                sku = i.getString("sku"),
                stockQuantity = (i.get("stockQuantity") as? Number)?.toInt(),
                lowStockThreshold = (i.get("lowStockThreshold") as? Number)?.toInt()
            )
        }

        val images = (d.get("images") as? List<*>)?.mapNotNull { o ->
            (o as? Document)?.let { img ->
                ImageDto(
                    id = extractId(img.get("_id")),
                    url = img.getString("url"),
                    sortOrder = (img.get("sortOrder") as? Number)?.toInt()
                )
            }
        } ?: emptyList()

        var createdAt: Instant? = null
        var updatedAt: Instant? = null
        try {
            (d.get("createdAt") as? String)?.let { createdAt = Instant.parse(it) }
            (d.get("updatedAt") as? String)?.let { updatedAt = Instant.parse(it) }
        } catch (e: DateTimeParseException) {
            // ignore parse errors
        }

        return ProductDto(
            id = id,
            name = name,
            description = description,
            categoryId = categoryId,
            categoryName = categoryName,
            status = status,
            attributes = attributes,
            pricing = pricing,
            inventory = inventory,
            images = images,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}
