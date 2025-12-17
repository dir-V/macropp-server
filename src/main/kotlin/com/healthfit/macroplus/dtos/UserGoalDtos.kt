package com.healthfit.macroplus.dtos

import com.healthfit.macroplus.enums.GoalType
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class CreateUserGoalRequest(
	val userId: UUID,
	val goalType: GoalType,
	val targetCalories: Int,
	val targetProteinGrams: BigDecimal,
	val targetCarbsGrams: BigDecimal,
	val targetFatsGrams: BigDecimal
)

data class UserGoalResponse(
	val id: UUID,
	val userId: UUID,
	val goalType: GoalType,
	val targetCalories: Int,
	val targetProteinGrams: BigDecimal,
	val targetCarbsGrams: BigDecimal,
	val targetFatsGrams: BigDecimal,
	var startDate: LocalDate?,
	var endDate: LocalDate?,
	var isActive: Boolean?,
	var createdAt: LocalDateTime?
	)