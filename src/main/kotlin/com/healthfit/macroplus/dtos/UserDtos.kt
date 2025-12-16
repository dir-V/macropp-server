package com.healthfit.macroplus.dtos

import java.util.UUID

// what we receive from the app
data class CreateUserRequest(
  val email: String,
  val heightCm: Int
)

// this is what we send to the app
// good practice - we can leave out passwords and other things we don't wanna send
data class UserResponse(
  val id: UUID,
  val email: String,
  val heightCm: Int,
  val joinedDate: String
)