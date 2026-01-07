package com.healthfit.macroplus.dtos

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

// create food log with food
data class CreateFoodLogRequest(
	val userId: UUID,
	val foodId: UUID,
	val quantityGrams: BigDecimal,
	val loggedAt: LocalDateTime?
)

// create quick add food log
data class QuickAddLogRequest(
	val userId: UUID,
	val calories: Int,
	val quickName: String,
	val loggedAt: LocalDateTime?
)

// response dto to the frontend
data class FoodLogResponse(
	val id: UUID,
	val userId: UUID,
	val foodId: UUID?,
	val name: String,
	val quantityGrams: BigDecimal?,
	val loggedAt: LocalDateTime?,
	val calories: Int,
	val proteinGrams: BigDecimal?,
	val carbsGrams: BigDecimal?,
	val fatsGrams: BigDecimal?
)

// update food log timestamp
data class UpdateFoodLogTimestampRequest(
	val loggedAt: LocalDateTime
)