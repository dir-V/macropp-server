package com.healthfit.macroplus.controllers

import com.healthfit.macroplus.dtos.CreateFoodLogRequest
import com.healthfit.macroplus.dtos.FoodLogResponse
import com.healthfit.macroplus.dtos.QuickAddLogRequest
import com.healthfit.macroplus.models.FoodLog
import com.healthfit.macroplus.services.FoodLogService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/food-logs")
class FoodLogController(
	private val foodLogService: FoodLogService
) {

//	POST http://localhost:8080/api/food-logs
//	log food with food id
	@PostMapping
	fun createFoodLog(@RequestBody request: CreateFoodLogRequest): ResponseEntity<FoodLogResponse> {
		val createdLog = foodLogService.addFoodLogWithFoodId(
			userId = request.userId,
			foodId = request.foodId,
			name = request.name,
			quantityGrams = request.quantityGrams,
			loggedAt = request.loggedAt
		)
		return ResponseEntity.status(HttpStatus.CREATED).body(createdLog.toResponse())
	}

//	POST http://localhost:8080/api/food-logs/quick-add
//	quick add cals
	@PostMapping("/quick-add")
	fun quickAddLog(@RequestBody request: QuickAddLogRequest): ResponseEntity<FoodLogResponse> {
		val createdLog = foodLogService.addFoodLogQuickAdd(
			userId = request.userId,
			calories = request.calories,
			loggedAt = request.loggedAt
		)
		return ResponseEntity.status(HttpStatus.CREATED).body(createdLog.toResponse())
	}

//	GET http://localhost:8080/api/food-logs/user/{userId}
	// food log history for a user
	@GetMapping("/user/{userId}")
	fun getUserLogs(@PathVariable userId: UUID): ResponseEntity<List<FoodLogResponse>> {
		return try {
			val logs = foodLogService.getFoodLogsForUser(userId)
			val responseList = logs.map { it.toResponse() }
			ResponseEntity.ok(responseList)
		} catch (e: NoSuchElementException) {
			ResponseEntity.notFound().build()
		}
	}

}



private fun FoodLog.toResponse(): FoodLogResponse {
	return FoodLogResponse(
		id = this.id ?: throw IllegalStateException("FoodLog must have an ID"),
		userId = this.user.id ?: throw IllegalStateException("FoodLog must have a User ID"),
		foodId = this.food?.id, // Can be null (Quick Add)
		name = this.name,
		quantityGrams = this.quantityGrams,
		loggedAt = this.loggedAt,
		calories = this.calories,
		proteinGrams = this.proteinGrams,
		carbsGrams = this.carbsGrams,
		fatsGrams = this.fatsGrams
	)
}