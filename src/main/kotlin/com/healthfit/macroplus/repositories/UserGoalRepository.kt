package com.healthfit.macroplus.repositories

import com.healthfit.macroplus.models.UserGoal
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserGoalsRepository : JpaRepository<UserGoal, UUID> {
}