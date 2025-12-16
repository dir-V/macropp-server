package com.healthfit.macroplus.models


import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.Id
import java.time.LocalDateTime
import java.util.UUID


@Entity
@Table(name = "users" )
class User(
    @Column(name = "email")
    var email:String
){

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id:UUID? = null

    @CreationTimestamp
    @Column(name = "createdAt", nullable = true)
    var createdAt: LocalDateTime? = null

    @UpdateTimestamp
    @Column(name = "updatedAt", nullable = true)
    var updatedAt: LocalDateTime? = null


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false
        return id != null && id == other.id
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}