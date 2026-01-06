package com.healthfit.macroplus.controllers

import com.healthfit.macroplus.dtos.AddRecipeIngredientRequest
import com.healthfit.macroplus.dtos.RecipeIngredientResponse
import com.healthfit.macroplus.models.RecipeIngredient
import com.healthfit.macroplus.services.RecipeIngredientService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("api/recipe-ingredients")
open class RecipeIngredientController(
	private val recipeIngredientService: RecipeIngredientService
) {

	//  POST http://localhost:8080/api/recipe-ingredients
	@PostMapping
	open fun addIngredient(@RequestBody request: AddRecipeIngredientRequest): ResponseEntity<RecipeIngredientResponse> {
		val createdIngredient = recipeIngredientService.addIngredient(
			recipeId = request.recipeId,
			foodId = request.foodId,
			quantity = request.quantity
		)
		return ResponseEntity.status(HttpStatus.CREATED).body(createdIngredient.toResponse())
	}

	//  GET http://localhost:8080/api/recipe-ingredients/recipe/{recipeId}
	@GetMapping("/recipe/{recipeId}")
	open fun getRecipeIngredients(@PathVariable recipeId: UUID): ResponseEntity<List<RecipeIngredientResponse>> {
		return try {
			val ingredients = recipeIngredientService.getIngredientsByRecipeId(recipeId)
			val responseList = ingredients.map { it.toResponse() }
			ResponseEntity.ok(responseList)
		} catch (e: NoSuchElementException) {
			ResponseEntity.notFound().build()
		}
	}

	//  DELETE http://localhost:8080/api/recipe-ingredients/{id}
	@DeleteMapping("/{id}")
	open fun deleteIngredient(@PathVariable id: UUID): ResponseEntity<Void> {
		return try {
			recipeIngredientService.removeIngredient(id)
			ResponseEntity.noContent().build()
		} catch (e: NoSuchElementException) {
			ResponseEntity.notFound().build()
		}
	}
}

private fun RecipeIngredient.toResponse(): RecipeIngredientResponse {
	return RecipeIngredientResponse(
		id = this.id ?: throw IllegalStateException("Ingredient must have an ID"),
		recipeId = this.recipe.id ?: throw IllegalStateException("Ingredient must be linked to a Recipe"),
		foodId = this.food.id ?: throw IllegalStateException("Ingredient must be linked to a Food"),
		foodName = this.food.name, // Accessing name from the Food entity
		quantity = this.quantity
	)
}