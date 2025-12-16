package com.healthfit.macroplus.models

import com.healthfit.macroplus.enums.GoalType
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UuidGenerator
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "user_goals")
class UserGoal(
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	var user: User,

	@Enumerated(value = EnumType.STRING)
	@Column(name = "goal_type", nullable = false)
	var goalType: GoalType,

	@Column(name = "target_calories", nullable = false)
	var targetCalories: Int,

	@Column(name = "target_protein_g", nullable = false)
	var targetProteinGrams: BigDecimal,

	@Column(name = "target_carbs_g", nullable = false)
	var targetCarbsGrams: BigDecimal,

	@Column(name = "target_fats_g", nullable = false)
	var targetFatsGrams: BigDecimal
) {

	constructor() : this(
		User(),
		GoalType.MAINTENANCE,
		0, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO
	)

	@Id
	@GeneratedValue
	@UuidGenerator
	@Column(name = "id", updatable = false, nullable = false)
	var id: UUID? = null

	@Column(name = "start_date", updatable = false, nullable = false)
	var startDate: LocalDate? = LocalDate.now()

	@Column(name = "end_date", updatable = true, nullable = true)
	var endDate: LocalDate? = null

	@Column(name = "is_active", updatable = true, nullable = false)
	var isActive: Boolean? = true

	@CreationTimestamp
	@Column(name = "created_at", updatable = false, nullable = false)
	var createdAt: LocalDateTime? = null


	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is UserGoal) return false
		return id != null && id == other.id
	}

	override fun hashCode(): Int {
//        returns a constant to avoid hash changing if ID is set later
		return javaClass.hashCode()
	}

	override fun toString(): String {
		return "UserGoal(id=$id, userId=${user.id}, goalType=$goalType, targetCalories=$targetCalories, " +
				"targetProteinGrams=$targetProteinGrams, targetCarbGrams=$targetCarbsGrams), targetFatGrams=$targetFatsGrams, " +
				"startDate=$startDate, endDate=$endDate, isActive=$isActive)"
	}


}