package com.healthfit.macroplus.models

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "users")
class User(
    @Column(name = "email", nullable = false, unique = true)
    var email: String,

    @Column(name = "height_cm", nullable = false)
    var heightCm: Int
) {

//    protected no-arg constructor with default arguments
//    secondary constructors in Kotlin must call the primary constructor
//    dummy values are just passed to keep the compiler happy, they will be overridden
    constructor() : this("", 0)

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
        if (other !is User) return false
//        only check id as the other things can change
        return id != null && id == other.id
    }

    override fun hashCode(): Int {
//        returns a constant to avoid hash changing if ID is set later
        return javaClass.hashCode()
    }

    override fun toString(): String {
        return "User(id=$id, email='$email')"
    }
}