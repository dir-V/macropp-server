package com.healthfit.macroplus.controllers

import com.healthfit.macroplus.dtos.CreateUserGoalRequest
import com.healthfit.macroplus.dtos.UserGoalResponse
import com.healthfit.macroplus.models.UserGoal // Import your model
import com.healthfit.macroplus.services.UserGoalService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("api/user-goals")
open class UserGoalController(
	private val userGoalService: UserGoalService
) {

	//	POST http://localhost:8080/api/user-goals/
	@PostMapping
	open fun createUserGoal(@RequestBody request: CreateUserGoalRequest): ResponseEntity<UserGoalResponse> {
		val userGoal = userGoalService.addUserGoalToUser(
			userId = request.userId,
			goalType = request.goalType,
			targetCalories = request.targetCalories,
			targetProteinGrams = request.targetProteinGrams,
			targetCarbsGrams = request.targetCarbsGrams,
			targetFatsGrams = request.targetFatsGrams,
		)

		return ResponseEntity.status(HttpStatus.CREATED).body(userGoal.toResponse())
	}


	//	GET http://localhost:8080/api/user-goals/{userId}/active
	@GetMapping("/{userId}/active")
	fun getActiveGoal(@PathVariable userId: UUID): ResponseEntity<UserGoalResponse> {
		return try {
			val goal = userGoalService.getUserGoalByUserId(userId)
			ResponseEntity.ok(goal.toResponse())
		} catch (e: NoSuchElementException) {
			// return 404
			ResponseEntity.notFound().build()
		}
	}

	//	GET http://localhost:8080/api/user-goals/{userId}/has-active
	@GetMapping("/{userId}/has-active")
	fun checkHasActiveGoal(@PathVariable userId: UUID): ResponseEntity<Boolean> {
		val exists = userGoalService.checkUserHasActiveGoal(userId)
		return ResponseEntity.ok(exists)
	}


	private fun UserGoal.toResponse(): UserGoalResponse {
		return UserGoalResponse(
			id = this.id ?: throw IllegalStateException("UserGoal must have an ID"),
			userId = this.user.id ?: throw IllegalStateException("UserGoal must be attached to a User with an ID"),
			goalType = this.goalType,
			targetCalories = this.targetCalories,
			targetProteinGrams = this.targetProteinGrams,
			targetCarbsGrams = this.targetCarbsGrams,
			targetFatsGrams = this.targetFatsGrams,
			startDate = this.startDate,
			endDate = this.endDate,
			isActive = this.isActive,
			createdAt = this.createdAt
		)
	}
}