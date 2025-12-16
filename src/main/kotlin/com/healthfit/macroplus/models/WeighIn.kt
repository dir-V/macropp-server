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
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "weigh_ins")
open class WeighIn(
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	var user: User,

	@Column(name = "weight_kg", nullable = false)
	var weightKg: BigDecimal,

	@Column(name = "weight_date", nullable = false)
	var weightDate: LocalDate = LocalDate.now()
) {

	constructor() : this(User(), BigDecimal.ZERO, LocalDate.now())

	@Id
	@GeneratedValue
	@UuidGenerator // replaces GenericGenerator
	@Column(name = "id", updatable = false, nullable = false)
	var id: UUID? = null

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	var createdAt: LocalDateTime? = null

	@UpdateTimestamp
	@Column(name = "updated_at", nullable = false)
	var updatedAt: LocalDateTime? = null

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is WeighIn) return false
		return id != null && id == other.id
	}

	override fun hashCode(): Int {
		return javaClass.hashCode()
	}

	override fun toString(): String {
		return "WeighIn(id=$id, Weight=${weightKg}kg, Date=${weightDate})"
	}

}