package com.healthfit.macroplus.repositories

import com.healthfit.macroplus.models.Food
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface FoodRepository : JpaRepository<Food, UUID> {

	fun findByUserId(userId: UUID): List<Food>

}