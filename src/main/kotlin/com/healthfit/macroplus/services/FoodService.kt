package com.healthfit.macroplus.services

import com.healthfit.macroplus.models.Food
import com.healthfit.macroplus.repositories.FoodRepository
import com.healthfit.macroplus.repositories.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.NoSuchElementException
import java.util.UUID

@Service
open class FoodService(
	private val foodRepository: FoodRepository,
	private val userRepository: UserRepository
) {

	@Transactional
	open fun addFood(
		userId: UUID,
		name: String,
		caloriesPer100Grams: Int,
		proteinPer100Grams: BigDecimal? = null,
		carbsPer100Grams: BigDecimal? = null,
		fatsPer100Grams: BigDecimal? = null,
		servingSizeGrams: BigDecimal? = null,
		barcode: Long? = null
	) : Food {

//		find user
		val foundUser = userRepository.findById(userId)
			.orElseThrow { NoSuchElementException("User not found with ID: $userId") }

		val newFood = Food(
			user = foundUser,
			name = name,
			caloriesPer100Grams = caloriesPer100Grams,
			proteinPer100Grams = proteinPer100Grams,
			carbsPer100Grams = carbsPer100Grams,
			fatsPer100Grams = fatsPer100Grams,
			servingSizeGrams = servingSizeGrams,
			barcode = barcode
		)

//		maybe return a dto instead later
		return foodRepository.save(newFood)
	}


	@Transactional
	open fun getFoodById(foodId: UUID): Food {
		val foundFood = foodRepository.findById(foodId)
			.orElseThrow { NoSuchElementException("Food not found with ID: $foodId") }

//		maybe dto later
		return foundFood
	}


	@Transactional
	open fun getUsersFoods(userId: UUID): List<Food> {

		if (!userRepository.existsById(userId)) {
			throw kotlin.NoSuchElementException("User not found with ID: $userId")
		}

		return foodRepository.findByUserId(userId)
	}

	@Transactional
	open fun searchUserFoods(userId: UUID, query: String): List<Food> {

		if (!userRepository.existsById(userId)) {
			throw kotlin.NoSuchElementException("User not found with ID: $userId")
		}

		return foodRepository.findByUserIdAndNameContainingIgnoreCase(userId, query)
	}

}