package com.healthfit.macroplus.repositories

import com.healthfit.macroplus.models.RecipeLog
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface RecipeLogRepository : JpaRepository<RecipeLog, UUID> {
	fun findByUserId(userId: UUID): List<RecipeLog>
}