package com.healthfit.macroplus.dtos

import java.util.UUID

// input: what the frontend sends to create a recipe
data class CreateRecipeRequest(
	val userId: UUID,
	val name: String
)

// output: what the controller sends back
data class RecipeResponse(
	val id: UUID,
	val userId: UUID,
	val name: String
)