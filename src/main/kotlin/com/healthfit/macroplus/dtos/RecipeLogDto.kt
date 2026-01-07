package com.healthfit.macroplus.dtos

import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

// input
data class CreateRecipeLogRequest(
	val userId: UUID,
	val recipeId: UUID,
	val servingsConsumed: BigDecimal, // e.g. 0.5 (half the recipe) or 1.0 (whole recipe)
	val logDate: LocalDate? = null,
	val loggedAt: LocalDateTime? = null
)

// output
data class RecipeLogResponse(
	val id: UUID,
	val userId: UUID,
	val recipeId: UUID?, // Nullable in case recipe was deleted
	val recipeName: String,
	val servingsConsumed: BigDecimal,
	val caloriesPerServing: Int, // Represents the Total Batch Calories in this context
	val proteinPerServing: BigDecimal?,
	val carbsPerServing: BigDecimal?,
	val fatPerServing: BigDecimal?,
	val ingredientsSnapshot: String, // The JSON string
	val logDate: LocalDate,
	val loggedAt: LocalDateTime
)