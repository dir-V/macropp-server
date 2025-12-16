package com.healthfit.macroplus.controllers

import com.healthfit.macroplus.dtos.CreateUserRequest
import com.healthfit.macroplus.dtos.UserResponse
import com.healthfit.macroplus.models.User
import com.healthfit.macroplus.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/users")
class UserController(
  private val userService: UserService
) {



//  POST http://localhost:8080/api/users
  @PostMapping
  fun registerUser(@RequestBody request: CreateUserRequest): ResponseEntity<UserResponse> {
	val user = userService.registerUser(
	  email = request.email,
	  heightCm = request.heightCm
	)
	return ResponseEntity.status(HttpStatus.CREATED).body(user.toResponse())
  }



//  GET http://localhost:8080/api/users/{id}
  @GetMapping("/{id}")
  fun getUser(@PathVariable id: UUID): ResponseEntity<UserResponse> {
	return try {
	  val user = userService.getUserById(id)
	  ResponseEntity.ok(user.toResponse())
	} catch (e: NoSuchElementException) {
	  ResponseEntity.notFound().build()
	}
  }

//  PATCH http://localhost:8080/api/users/{id}/height?newHeight=185
  @PatchMapping("/{id}/height")
  fun updateHeight(
	@PathVariable id: UUID,
	@RequestParam newHeight: Int
  ): ResponseEntity<UserResponse> {
	return try {
	  val updatedUser = userService.updateUserHeight(id, newHeight)
	  ResponseEntity.ok(updatedUser.toResponse())
	} catch (e: NoSuchElementException) {
	  ResponseEntity.notFound().build()
	}
  }



//  helper function to convert the user object to dto
  private fun User.toResponse(): UserResponse {
	return UserResponse(
	  id = this.id!!,
	  email = this.email,
	  heightCm = this.heightCm,
	  joinedDate = this.createdAt?.toString() ?: "Unknown"
	)
  }
}