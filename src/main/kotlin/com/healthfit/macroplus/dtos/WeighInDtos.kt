package com.healthfit.macroplus.dtos

import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

// input: what the frontend sends to create a weigh-in
data class CreateWeighInRequest(
	val userId: UUID,
	val weightKg: BigDecimal,
	val weightDate: LocalDate? // Optional, defaults to Today if null
)

// output: what the controller sends back to frontend
data class WeighInResponse(
	val id: UUID,
	val userId: UUID,
	val weightKg: BigDecimal,
	val weightDate: LocalDate
)