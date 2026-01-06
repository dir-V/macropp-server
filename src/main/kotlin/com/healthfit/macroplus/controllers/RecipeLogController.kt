package com.healthfit.macroplus.controllers

import com.healthfit.macroplus.dtos.CreateRecipeLogRequest
import com.healthfit.macroplus.dtos.RecipeLogResponse
import com.healthfit.macroplus.models.RecipeLog
import com.healthfit.macroplus.services.RecipeLogService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("api/recipe-logs")
open class RecipeLogController(
	private val recipeLogService: RecipeLogService
) {

	//  POST http://localhost:8080/api/recipe-logs
	@PostMapping
	open fun createRecipeLog(@RequestBody request: CreateRecipeLogRequest): ResponseEntity<RecipeLogResponse> {
		val createdLog = recipeLogService.logRecipe(
			userId = request.userId,
			recipeId = request.recipeId,
			servingsConsumed = request.servingsConsumed,
			logDate = request.logDate,
			loggedAt = request.loggedAt
		)
		return ResponseEntity.status(HttpStatus.CREATED).body(createdLog.toResponse())
	}

	//  GET http://localhost:8080/api/recipe-logs/user/{userId}
	@GetMapping("/user/{userId}")
	open fun getUserLogs(@PathVariable userId: UUID): ResponseEntity<List<RecipeLogResponse>> {
		return try {
			val logs = recipeLogService.getUserRecipeLogs(userId)
			val responseList = logs.map { it.toResponse() }
			ResponseEntity.ok(responseList)
		} catch (e: NoSuchElementException) {
			ResponseEntity.notFound().build()
		}
	}

	//  GET http://localhost:8080/api/recipe-logs/{id}
	@GetMapping("/{id}")
	open fun getLog(@PathVariable id: UUID): ResponseEntity<RecipeLogResponse> {
		return try {
			val log = recipeLogService.getLogById(id)
			ResponseEntity.ok(log.toResponse())
		} catch (e: NoSuchElementException) {
			ResponseEntity.notFound().build()
		}
	}
}

private fun RecipeLog.toResponse(): RecipeLogResponse {
	return RecipeLogResponse(
		id = this.id ?: throw IllegalStateException("Log must have an ID"),
		userId = this.user.id ?: throw IllegalStateException("Log must be linked to a User"),
		recipeId = this.recipe?.id, // Can be null if recipe was deleted
		recipeName = this.recipeName,
		servingsConsumed = this.servingsConsumed,
		caloriesPerServing = this.caloriesPerServing,
		proteinPerServing = this.proteinPerServing,
		carbsPerServing = this.carbsPerServing,
		fatPerServing = this.fatPerServing,
		ingredientsSnapshot = this.ingredientsSnapshot,
		logDate = this.logDate,
		loggedAt = this.loggedAt
	)
}