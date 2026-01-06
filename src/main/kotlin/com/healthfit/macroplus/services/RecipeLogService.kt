package com.healthfit.macroplus.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.healthfit.macroplus.models.RecipeLog
import com.healthfit.macroplus.repositories.RecipeIngredientRepository
import com.healthfit.macroplus.repositories.RecipeLogRepository
import com.healthfit.macroplus.repositories.RecipeRepository
import com.healthfit.macroplus.repositories.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.NoSuchElementException
import java.util.UUID

@Service
open class RecipeLogService(
	private val recipeLogRepository: RecipeLogRepository,
	private val recipeRepository: RecipeRepository,
	private val recipeIngredientRepository: RecipeIngredientRepository,
	private val userRepository: UserRepository,
	private val objectMapper: ObjectMapper // Used to create the JSON snapshot
) {

	@Transactional
	open fun logRecipe(
		userId: UUID,
		recipeId: UUID,
		servingsConsumed: BigDecimal,
		logDate: LocalDate? = null,
		loggedAt: LocalDateTime? = null
	): RecipeLog {

		val foundUser = userRepository.findById(userId)
			.orElseThrow { NoSuchElementException("User not found with ID: $userId") }

		val foundRecipe = recipeRepository.findById(recipeId)
			.orElseThrow { NoSuchElementException("Recipe not found with ID: $recipeId") }

		// 1. Fetch all ingredients to calculate totals
		val ingredients = recipeIngredientRepository.findByRecipeId(recipeId)

		if (ingredients.isEmpty()) {
			throw IllegalStateException("Cannot log a recipe with no ingredients!")
		}

		// 2. Calculate Totals (The 'Per Serving' values represent the Whole Batch)
		var totalCalories = 0
		var totalProtein = BigDecimal.ZERO
		var totalCarbs = BigDecimal.ZERO
		var totalFats = BigDecimal.ZERO

		// List to hold data for the JSON snapshot
		val snapshotList = mutableListOf<Map<String, Any>>()

		ingredients.forEach { ing ->
			val food = ing.food
			val ratio = ing.quantity.divide(BigDecimal("100.00"))

			// Math: (Per100g * Quantity) / 100
			val cals = (food.caloriesPer100Grams * ratio.toDouble()).toInt()
			val pro = food.proteinPer100Grams?.multiply(ratio) ?: BigDecimal.ZERO
			val carb = food.carbsPer100Grams?.multiply(ratio) ?: BigDecimal.ZERO
			val fat = food.fatsPer100Grams?.multiply(ratio) ?: BigDecimal.ZERO

			totalCalories += cals
			totalProtein += pro
			totalCarbs += carb
			totalFats += fat

			// Add to snapshot
			snapshotList.add(mapOf(
				"foodName" to food.name,
				"quantityGrams" to ing.quantity
			))
		}

		// 3. Create JSON Snapshot string
		val snapshotJson = objectMapper.writeValueAsString(snapshotList)

		// 4. Save the Log
		val newLog = RecipeLog(
			user = foundUser,
			recipe = foundRecipe,
			recipeName = foundRecipe.name,
			servingsConsumed = servingsConsumed,
			ingredientsSnapshot = snapshotJson,
			caloriesPerServing = totalCalories,
			proteinPerServing = totalProtein,
			carbsPerServing = totalCarbs,
			fatPerServing = totalFats,
			logDate = logDate ?: LocalDate.now(),
			loggedAt = loggedAt ?: LocalDateTime.now()
		)

		return recipeLogRepository.save(newLog)
	}

	@Transactional
	open fun getUserRecipeLogs(userId: UUID): List<RecipeLog> {
		if (!userRepository.existsById(userId)) {
			throw NoSuchElementException("User not found with ID: $userId")
		}
		return recipeLogRepository.findByUserId(userId)
	}

	@Transactional
	open fun getLogById(logId: UUID): RecipeLog {
		return recipeLogRepository.findById(logId)
			.orElseThrow { NoSuchElementException("Recipe Log not found with ID: $logId") }
	}
}