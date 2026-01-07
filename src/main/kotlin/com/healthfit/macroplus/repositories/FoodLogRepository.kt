package com.healthfit.macroplus.repositories

import com.healthfit.macroplus.models.FoodLog
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import java.util.UUID

interface FoodLogRepository : JpaRepository<FoodLog, UUID> {

	fun findByUserId(userId: UUID): List<FoodLog>

	fun findByUserIdOrderByLoggedAtAsc(userId: UUID): List<FoodLog>

	fun findByUserIdAndLoggedAtBetweenOrderByLoggedAtAsc(
		userId: UUID,
		startDateTime: LocalDateTime,
		endDateTime: LocalDateTime
	): List<FoodLog>
}