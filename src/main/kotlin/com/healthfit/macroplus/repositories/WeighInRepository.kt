package com.healthfit.macroplus.repositories

import com.healthfit.macroplus.models.WeighIn
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface WeighInRepository : JpaRepository<WeighIn, UUID> {
	fun findByUserId(userId: UUID): List<WeighIn>
}