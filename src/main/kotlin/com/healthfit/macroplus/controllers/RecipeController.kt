package com.healthfit.macroplus.controllers

import com.healthfit.macroplus.dtos.CreateRecipeRequest
import com.healthfit.macroplus.dtos.RecipeResponse
import com.healthfit.macroplus.models.Recipe
import com.healthfit.macroplus.services.RecipeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("api/recipes")
open class RecipeController(
	private val recipeService: RecipeService
) {

	//  POST http://localhost:8080/api/recipes
	@PostMapping
	open fun createRecipe(@RequestBody request: CreateRecipeRequest): ResponseEntity<RecipeResponse> {
		val createdRecipe = recipeService.addRecipe(
			userId = request.userId,
			name = request.name
		)
		return ResponseEntity.status(HttpStatus.CREATED).body(createdRecipe.toResponse())
	}

	//  GET http://localhost:8080/api/recipes/{recipeId}
	@GetMapping("/{recipeId}")
	open fun getRecipe(@PathVariable recipeId: UUID): ResponseEntity<RecipeResponse> {
		return try {
			val recipe = recipeService.getRecipeById(recipeId)
			ResponseEntity.ok(recipe.toResponse())
		} catch (e: NoSuchElementException) {
			ResponseEntity.notFound().build()
		}
	}

	//  GET http://localhost:8080/api/recipes/user/{userId}
	@GetMapping("/user/{userId}")
	open fun getUserRecipes(@PathVariable userId: UUID): ResponseEntity<List<RecipeResponse>> {
		return try {
			val recipes = recipeService.getUserRecipes(userId)
			val responseList = recipes.map { it.toResponse() }
			ResponseEntity.ok(responseList)
		} catch (e: NoSuchElementException) {
			ResponseEntity.notFound().build()
		}
	}
}

private fun Recipe.toResponse(): RecipeResponse {
	return RecipeResponse(
		id = this.id ?: throw IllegalStateException("Recipe must have an ID"),
		userId = this.user.id ?: throw IllegalStateException("Recipe must be linked to a User ID"),
		name = this.name
	)
}