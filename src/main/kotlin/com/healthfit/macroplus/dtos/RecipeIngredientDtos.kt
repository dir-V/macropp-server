package com.healthfit.macroplus.dtos

import java.math.BigDecimal
import java.util.UUID

// input: adding a specific food to a recipe
data class AddRecipeIngredientRequest(
	val recipeId: UUID,
	val foodId: UUID,
	val quantity: BigDecimal
)

// output: returning the ingredient details
data class RecipeIngredientResponse(
	val id: UUID,
	val recipeId: UUID,
	val foodId: UUID,
	val foodName: String, // Useful to return the name directly so the UI doesn't have to fetch Food again
	val quantity: BigDecimal
)