package com.healthfit.macroplus.services

import com.healthfit.macroplus.models.WeighIn
import com.healthfit.macroplus.repositories.UserRepository
import com.healthfit.macroplus.repositories.WeighInRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate
import java.util.NoSuchElementException
import java.util.UUID

@Service
open class WeighInService(
	private val weighInRepository: WeighInRepository,
	private val userRepository: UserRepository
) {

	@Transactional
	open fun addWeighIn(
		userId: UUID,
		weightKg: BigDecimal,
		weightDate: LocalDate? = null
	): WeighIn {

		// find user
		val foundUser = userRepository.findById(userId)
			.orElseThrow { NoSuchElementException("User not found with ID: $userId") }

		// default to today if no date provided
		val actualDate = weightDate ?: LocalDate.now()

		val newWeighIn = WeighIn(
			user = foundUser,
			weightKg = weightKg,
			weightDate = actualDate
		)

		return weighInRepository.save(newWeighIn)
	}

	@Transactional
	open fun getWeighInById(weighInId: UUID): WeighIn {
		val foundWeighIn = weighInRepository.findById(weighInId)
			.orElseThrow { NoSuchElementException("WeighIn not found with ID: $weighInId") }

		return foundWeighIn
	}

	@Transactional
	open fun getUserWeighIns(userId: UUID): List<WeighIn> {

		if (!userRepository.existsById(userId)) {
			throw NoSuchElementException("User not found with ID: $userId")
		}

		return weighInRepository.findByUserId(userId)
	}
}