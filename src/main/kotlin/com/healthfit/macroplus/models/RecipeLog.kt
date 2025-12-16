package com.healthfit.macroplus.models

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.annotations.UuidGenerator
import org.hibernate.type.SqlTypes
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "recipe_logs")
class RecipeLog(
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	var user: User,

	// nullable so we keep the log if the recipe gets deleted
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipe_id", nullable = true)
	var recipe: Recipe?,

	@Column(name = "recipe_name", nullable = false)
	var recipeName: String,

	@Column(name = "servings_consumed", nullable = false, precision = 8, scale = 2)
	var servingsConsumed: BigDecimal,

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(name = "ingredients_snapshot", columnDefinition = "jsonb", nullable = false)
	var ingredientsSnapshot: String,

	@Column(name = "calories_per_serving", nullable = false)
	var caloriesPerServing: Int,

	@Column(name = "protein_g_per_serving", nullable = true, precision = 8, scale = 2)
	var proteinPerServing: BigDecimal?,

	@Column(name = "carbs_g_per_serving", nullable = true, precision = 8, scale = 2)
	var carbsPerServing: BigDecimal?,

	@Column(name = "fat_g_per_serving", nullable = true, precision = 8, scale = 2)
	var fatPerServing: BigDecimal?,

	@Column(name = "log_date", nullable = false)
	var logDate: LocalDate = LocalDate.now(),

	@Column(name = "logged_at", nullable = false)
	var loggedAt: LocalDateTime = LocalDateTime.now()
) {

	constructor() : this(
		User(), null, "", BigDecimal.ONE, "{}", 0, null, null, null
	)

	@Id
	@GeneratedValue
	@UuidGenerator
	@Column(name = "id", updatable = false, nullable = false)
	var id: UUID? = null

	@CreationTimestamp
	@Column(name = "created_at", updatable = false, nullable = false)
	var createdAt: LocalDateTime? = null

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is RecipeLog) return false
		return id != null && id == other.id
	}

	override fun hashCode(): Int = javaClass.hashCode()

	override fun toString(): String {
		return "RecipeLog(id=$id, name='$recipeName', date=$logDate)"
	}
}