package com.healthfit.macroplus.services

import com.healthfit.macroplus.models.RecipeIngredient
import com.healthfit.macroplus.repositories.FoodRepository
import com.healthfit.macroplus.repositories.RecipeIngredientRepository
import com.healthfit.macroplus.repositories.RecipeRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.NoSuchElementException
import java.util.UUID

@Service
open class RecipeIngredientService(
	private val recipeIngredientRepository: RecipeIngredientRepository,
	private val recipeRepository: RecipeRepository,
	private val foodRepository: FoodRepository
) {

	@Transactional
	open fun addIngredient(
		recipeId: UUID,
		foodId: UUID,
		quantity: BigDecimal
	): RecipeIngredient {

		val foundRecipe = recipeRepository.findById(recipeId)
			.orElseThrow { NoSuchElementException("Recipe not found with ID: $recipeId") }

		val foundFood = foodRepository.findById(foodId)
			.orElseThrow { NoSuchElementException("Food not found with ID: $foodId") }

		val newIngredient = RecipeIngredient(
			recipe = foundRecipe,
			food = foundFood,
			quantity = quantity
		)

		return recipeIngredientRepository.save(newIngredient)
	}

	@Transactional
	open fun getIngredientsByRecipeId(recipeId: UUID): List<RecipeIngredient> {
		// verify recipe exists first
		if (!recipeRepository.existsById(recipeId)) {
			throw NoSuchElementException("Recipe not found with ID: $recipeId")
		}

		return recipeIngredientRepository.findByRecipeId(recipeId)
	}

	@Transactional
	open fun removeIngredient(ingredientId: UUID) {
		if (!recipeIngredientRepository.existsById(ingredientId)) {
			throw NoSuchElementException("Ingredient not found with ID: $ingredientId")
		}
		recipeIngredientRepository.deleteById(ingredientId)
	}
}