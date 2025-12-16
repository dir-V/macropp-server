package com.healthfit.macroplus.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.UuidGenerator
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "foods")
class Food(
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	var user: User,

	@Column(name = "name", nullable = false)
	var name: String,

	@Column(name = "calories_per_100g", nullable = false)
	var caloriesPer100Grams: Int,

	@Column(name = "protein_per_100g", nullable = false)
	var proteinPer100Grams: BigDecimal,

	@Column(name = "carbs_per_100g", nullable = false)
	var carbsPer100Grams: BigDecimal,

	@Column(name = "fats_per_100g", nullable = false)
	var fatsPer100Grams: BigDecimal,

	@Column(name = "serving_size_g", nullable = false)
	var servingSizeGrams: BigDecimal,
) {

	@Id
	@GeneratedValue
	@UuidGenerator
	@Column(name = "id", updatable = false, nullable = false)
	var id: UUID? = null

	@CreationTimestamp
	@Column(name = "created_at", updatable = false, nullable = false)
	var createdAt: LocalDateTime? = null

	@UpdateTimestamp
	@Column(name = "updated_at", nullable = false)
	var updatedAt: LocalDateTime? = null

	@Column(name = "barcode", nullable = false)
	var barcode: Long? = null

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is User) return false
		return id != null && id == other.id
	}

	override fun hashCode(): Int {
		return javaClass.hashCode()
	}

	override fun toString(): String {
		return "Food(id=$id, name='$name')"
	}

}