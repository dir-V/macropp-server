package com.healthfit.macroplus.services

import com.healthfit.macroplus.models.Recipe
import com.healthfit.macroplus.repositories.RecipeRepository
import com.healthfit.macroplus.repositories.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.NoSuchElementException
import java.util.UUID

@Service
open class RecipeService(
	private val recipeRepository: RecipeRepository,
	private val userRepository: UserRepository
) {

	@Transactional
	open fun addRecipe(
		userId: UUID,
		name: String
	): Recipe {

		val foundUser = userRepository.findById(userId)
			.orElseThrow { NoSuchElementException("User not found with ID: $userId") }

		val newRecipe = Recipe(
			user = foundUser,
			name = name
		)

		return recipeRepository.save(newRecipe)
	}

	@Transactional
	open fun getRecipeById(recipeId: UUID): Recipe {
		val foundRecipe = recipeRepository.findById(recipeId)
			.orElseThrow { NoSuchElementException("Recipe not found with ID: $recipeId") }

		return foundRecipe
	}

	@Transactional
	open fun getUserRecipes(userId: UUID): List<Recipe> {
		if (!userRepository.existsById(userId)) {
			throw NoSuchElementException("User not found with ID: $userId")
		}

		return recipeRepository.findByUserId(userId)
	}
}