package com.healthfit.macroplus.repositories

import com.healthfit.macroplus.models.Recipe
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface RecipeRepository : JpaRepository<Recipe, UUID> {
	fun findByUserId(userId: UUID): List<Recipe>
}