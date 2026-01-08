package com.example.macropp.dto

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class QuickAddLogRequest(
	val userId: UUID,

	val name: String,

	val calories: Int,

	// 👇 NEW: Default to ZERO so the app doesn't crash if they are missing
	val proteinGrams: BigDecimal = BigDecimal.ZERO,
	val carbsGrams: BigDecimal = BigDecimal.ZERO,
	val fatsGrams: BigDecimal = BigDecimal.ZERO,

	val loggedAt: LocalDateTime? = null
)