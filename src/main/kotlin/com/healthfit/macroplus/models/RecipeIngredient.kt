package com.healthfit.macroplus.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.UuidGenerator
import java.math.BigDecimal
import java.util.UUID

@Entity
@Table(name = "recipe_ingredients")
open class RecipeIngredient (
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipe_id", nullable = false)
	var recipe: Recipe,

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "food_id", nullable = false)
	var food: Food,

	@Column(name = "quantity_g")
	var quantity: BigDecimal
) {

	constructor() : this(
		Recipe(),
		Food(),
		BigDecimal.ZERO
	)

	@Id
	@GeneratedValue
	@UuidGenerator
	@Column(name = "id", updatable = false, nullable = false)
	var id: UUID? = null

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is RecipeIngredient) return false
		return id != null && id == other.id
	}

	override fun hashCode(): Int {
		return javaClass.hashCode()
	}

	override fun toString(): String {
		return "Recipe(id=$id, foodName='$food.name' quantity=$quantity)"
	}
}