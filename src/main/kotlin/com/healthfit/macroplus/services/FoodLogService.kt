package com.healthfit.macroplus.services

import com.healthfit.macroplus.models.Food
import com.healthfit.macroplus.models.FoodLog
import com.healthfit.macroplus.repositories.FoodLogRepository
import com.healthfit.macroplus.repositories.FoodRepository
import com.healthfit.macroplus.repositories.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.NoSuchElementException
import java.util.UUID

@Service
open class FoodLogService(
	private val foodLogRepository: FoodLogRepository,
	private val foodRepository: FoodRepository,
	private val userRepository : UserRepository
	) {

	@Transactional
	 open fun addFoodLogWithFoodId(
		userId: UUID,
		foodId: UUID,
		quantityGrams: BigDecimal,
		loggedAt: LocalDateTime? = LocalDateTime.now(),
		): FoodLog {

		val foundUser = userRepository.findById(userId)
			.orElseThrow { NoSuchElementException("User not found with ID: $userId") }

		val foundFood = foodRepository.findById(foodId)
			.orElseThrow { NoSuchElementException("Food not found with ID: $foodId") }

		val ratio = quantityGrams.divide(BigDecimal("100"))
		val calories = (foundFood.caloriesPer100Grams * ratio.toDouble()).toInt()

		val proteinGrams = foundFood.proteinPer100Grams?.multiply(ratio)
		val carbsGrams = foundFood.carbsPer100Grams?.multiply(ratio)
		val fatsGrams = foundFood.fatsPer100Grams?.multiply(ratio)

		val newFoodLog = FoodLog(
			user = foundUser,
			food = foundFood,
			name = foundFood.name,
			quantityGrams = quantityGrams,
			loggedAt = loggedAt,
			calories = calories,
			proteinGrams = proteinGrams,
			carbsGrams = carbsGrams,
			fatsGrams = fatsGrams
		)

		return foodLogRepository.save(newFoodLog)
	}


	@Transactional
	open fun addFoodLogQuickAdd(
		userId: UUID,
		calories: Int,
		quickName: String,

		protein: BigDecimal,
		carbs: BigDecimal,
		fats: BigDecimal,

		loggedAt: LocalDateTime? = LocalDateTime.now(),
	): FoodLog {

		val foundUser = userRepository.findById(userId)
			.orElseThrow { NoSuchElementException("User not found with ID: $userId") }

		val newFoodLog = FoodLog(
			user = foundUser,
			food = null,
			name = "Quick Add: $quickName",
			quantityGrams = null,
			loggedAt = loggedAt ?: LocalDateTime.now(),
			calories = calories,

			proteinGrams = protein,
			carbsGrams = carbs,
			fatsGrams = fats
		)

		return foodLogRepository.save(newFoodLog)
	}


	@Transactional
	open fun getFoodLogsForUser(userId: UUID): List<FoodLog> {

		if (!userRepository.existsById(userId)) {
			throw kotlin.NoSuchElementException("User not found with ID: $userId")
		}

		return foodLogRepository.findByUserIdOrderByLoggedAtAsc(userId)
	}

	@Transactional
	open fun updateFoodLogTimestamp(foodLogId: UUID, newLoggedAt: LocalDateTime): FoodLog {
		val foodLog = foodLogRepository.findById(foodLogId)
			.orElseThrow { NoSuchElementException("Food log not found with ID: $foodLogId") }

		foodLog.loggedAt = newLoggedAt
		return foodLogRepository.save(foodLog)
	}

	@Transactional
	open fun deleteFoodLog(foodLogId: UUID) {
		if (!foodLogRepository.existsById(foodLogId)) {
			throw NoSuchElementException("Food log not found with ID: $foodLogId")
		}
		foodLogRepository.deleteById(foodLogId)
	}
}