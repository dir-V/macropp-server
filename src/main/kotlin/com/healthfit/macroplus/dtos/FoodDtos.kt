package com.healthfit.macroplus.dtos


import java.math.BigDecimal
import java.util.UUID

// input: what the frontend sends to create a food
data class CreateFoodRequest(
	val userId: UUID,
	val name: String,
	val caloriesPer100Grams: Int,
	val proteinPer100Grams: BigDecimal?,
	val carbsPer100Grams: BigDecimal?,
	val fatsPer100Grams: BigDecimal?,
	val servingSizeGrams: BigDecimal?,
	val barcode: Long?
)

// output: what the controller sends back to  frontend
data class FoodResponse(
	val id: UUID,
	val userId: UUID,
	val name: String,
	val caloriesPer100Grams: Int,
	val proteinPer100Grams: BigDecimal?,
	val carbsPer100Grams: BigDecimal?,
	val fatsPer100Grams: BigDecimal?,
	val servingSizeGrams: BigDecimal?,
	val barcode: Long?
)