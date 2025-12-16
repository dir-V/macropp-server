package com.healthfit.macroplus.models

import com.healthfit.macroplus.models.Food
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UuidGenerator
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "food_logs")
open class FoodLog(
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	var user: User,

	@ManyToOne
	@JoinColumn(name = "food_id", nullable = true)
	var food: Food?,

	@Column(name = "name" , nullable = false)
	var name: String,

	@Column(name = "quantity_g", nullable = true)
	var quantityGrams: BigDecimal?,

	@Column(name = "logged_at", nullable = true)
	var loggedAt: LocalDateTime? = null,

	@Column(name = "calories" , nullable = false)
	var calories: Int,

	@Column(name = "protein_g" , nullable = true)
	var proteinGrams: BigDecimal? = null,

	@Column(name = "carbs_g" , nullable = true)
	var carbsGrams: BigDecimal? = null,

	@Column(name = "fats_g" , nullable = true)
	var fatsGrams: BigDecimal? = null,

) {

	constructor() : this(User(), Food(), "", null,null, 0, null, null, null)

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
		if (other !is FoodLog) return false
		return id != null && id == other.id
	}

	override fun hashCode(): Int {
		return javaClass.hashCode()
	}

	override fun toString(): String {
		return "FoodLog(id=$id, name='$name', calories=$calories, quantity=${quantityGrams}g)"
	}
}