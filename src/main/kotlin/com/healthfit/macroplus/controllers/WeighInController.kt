package com.healthfit.macroplus.controllers

import com.healthfit.macroplus.dtos.CreateWeighInRequest
import com.healthfit.macroplus.dtos.WeighInResponse
import com.healthfit.macroplus.models.WeighIn
import com.healthfit.macroplus.services.WeighInService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("api/weigh-ins")
open class WeighInController(
	private val weighInService: WeighInService
) {

	//  POST http://localhost:8080/api/weigh-ins
	@PostMapping
	open fun createWeighIn(@RequestBody request: CreateWeighInRequest): ResponseEntity<WeighInResponse> {
		val createdWeighIn = weighInService.addWeighIn(
			userId = request.userId,
			weightKg = request.weightKg,
			weightDate = request.weightDate
		)
		return ResponseEntity.status(HttpStatus.CREATED).body(createdWeighIn.toResponse())
	}

	//  GET http://localhost:8080/api/weigh-ins/{weighInId}
	@GetMapping("/{weighInId}")
	open fun getWeighIn(@PathVariable weighInId: UUID): ResponseEntity<WeighInResponse> {
		return try {
			val weighIn = weighInService.getWeighInById(weighInId)
			ResponseEntity.ok(weighIn.toResponse())
		} catch (e: NoSuchElementException) {
			ResponseEntity.notFound().build()
		}
	}

	//  GET http://localhost:8080/api/weigh-ins/user/{userId}
	// get all weigh-ins created by a specific user
	@GetMapping("/user/{userId}")
	open fun getUserWeighIns(@PathVariable userId: UUID): ResponseEntity<List<WeighInResponse>> {
		return try {
			val weighIns = weighInService.getUserWeighIns(userId)
			// use .map to convert entities to dtos
			val responseList = weighIns.map { it.toResponse() }
			ResponseEntity.ok(responseList)
		} catch (e: NoSuchElementException) {
			// return 404
			ResponseEntity.notFound().build()
		}
	}
}

// Extension function
private fun WeighIn.toResponse(): WeighInResponse {
	return WeighInResponse(
		id = this.id ?: throw IllegalStateException("WeighIn must have an ID"),
		userId = this.user.id ?: throw IllegalStateException("WeighIn must be linked to a User ID"),
		weightKg = this.weightKg,
		weightDate = this.weightDate
	)
}