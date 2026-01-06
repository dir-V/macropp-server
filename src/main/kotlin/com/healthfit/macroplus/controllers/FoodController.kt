package com.healthfit.macroplus.controllers

import com.healthfit.macroplus.dtos.CreateFoodRequest
import com.healthfit.macroplus.dtos.FoodResponse
import com.healthfit.macroplus.models.Food
import com.healthfit.macroplus.services.FoodService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("api/foods")
open class FoodController(
	private val foodService: FoodService
) {

//	POST http://localhost:8080/api/foods
	@PostMapping
	open fun createFood(@RequestBody request: CreateFoodRequest): ResponseEntity<FoodResponse> {
		val createdFood = foodService.addFood(
			userId = request.userId,
			name = request.name,
			caloriesPer100Grams = request.caloriesPer100Grams,
			proteinPer100Grams = request.proteinPer100Grams,
			carbsPer100Grams = request.carbsPer100Grams,
			fatsPer100Grams = request.fatsPer100Grams,
			servingSizeGrams = request.servingSizeGrams,
			barcode = request.barcode
		)
		return ResponseEntity.status(HttpStatus.CREATED).body(createdFood.toResponse())
	}

//	GET http://localhost:8080/api/foods/{foodId}
	@GetMapping("/{foodId}")
	open fun getFood(@PathVariable foodId: UUID): ResponseEntity<FoodResponse> {
		return try {
			val food = foodService.getFoodById(foodId)
			ResponseEntity.ok(food.toResponse())
		} catch (e: NoSuchElementException) {
			ResponseEntity.notFound().build()
		}
	}

//	GET http://localhost:8080/api/foods/user/{userId}
	// get all foods created by a specific user
	@GetMapping("/user/{userId}")
	open fun getUserFoods(@PathVariable userId: UUID): ResponseEntity<List<FoodResponse>> {
		return try {
			val foods = foodService.getUsersFoods(userId)
			// use .map to convert entities to dtos
			val responseList = foods.map { it.toResponse() }
			ResponseEntity.ok(responseList)
		} catch (e: NoSuchElementException) {
			// return 404
			ResponseEntity.notFound().build()
		}
	}

//	GET http://localhost:8080/api/foods/user/{userId}/search?query=chicken
	// search user's foods by name
	@GetMapping("/user/{userId}/search")
	open fun searchUserFoods(
		@PathVariable userId: UUID,
		@RequestParam query: String
	): ResponseEntity<List<FoodResponse>> {
		return try {
			val foods = foodService.searchUserFoods(userId, query)
			val responseList = foods.map { it.toResponse() }
			ResponseEntity.ok(responseList)
		} catch (e: NoSuchElementException) {
			ResponseEntity.notFound().build()
		}
	}
}


private fun Food.toResponse(): FoodResponse {
	return FoodResponse(
		id = this.id ?: throw IllegalStateException("Food must have an ID"),
		userId = this.user.id ?: throw IllegalStateException("Food must be linked to a User ID"),
		name = this.name,
		caloriesPer100Grams = this.caloriesPer100Grams,
		proteinPer100Grams = this.proteinPer100Grams,
		carbsPer100Grams = this.carbsPer100Grams,
		fatsPer100Grams = this.fatsPer100Grams,
		servingSizeGrams = this.servingSizeGrams,
		barcode = this.barcode
	)
}