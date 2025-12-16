package com.healthfit.macroplus.repositories

import com.healthfit.macroplus.models.RecipeIngredient
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface RecipeIngredientRepository : JpaRepository<RecipeIngredient, UUID> {
}