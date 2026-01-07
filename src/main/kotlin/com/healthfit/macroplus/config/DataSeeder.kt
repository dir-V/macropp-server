package com.healthfit.macroplus.config

import com.healthfit.macroplus.enums.GoalType
import com.healthfit.macroplus.models.*
import com.healthfit.macroplus.repositories.*
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

@Component
open class DataSeeder(
	private val userRepository: UserRepository,
	private val userGoalRepository: UserGoalRepository,
	private val foodRepository: FoodRepository,
	private val foodLogRepository: FoodLogRepository,
	// 👇 Newly added repositories
	private val weighInRepository: WeighInRepository,
	private val recipeRepository: RecipeRepository,
	private val recipeIngredientRepository: RecipeIngredientRepository,
	private val recipeLogRepository: RecipeLogRepository
) : CommandLineRunner {

	override fun run(vararg args: String?) {
		println("SEEDING: Starting database population...")

		// 1. CLEANUP (Delete children first to avoid Foreign Key errors)
		recipeLogRepository.deleteAll()
		recipeIngredientRepository.deleteAll()
		recipeRepository.deleteAll()
		weighInRepository.deleteAll()
		foodLogRepository.deleteAll()
		foodRepository.deleteAll()
		userGoalRepository.deleteAll()
		userRepository.deleteAll()

		// 2. CREATE USER
		val user = User(
			email = "tom@example.com",
			heightCm = 175
		)
		val savedUser = userRepository.save(user)

		// 3. CREATE GOAL
		val goal = UserGoal(
			user = savedUser,
			goalType = GoalType.DEFICIT,
			targetCalories = 1800,
			targetProteinGrams = BigDecimal("130.00"),
			targetCarbsGrams = BigDecimal("185.00"),
			targetFatsGrams = BigDecimal("60.00")
		)
		userGoalRepository.save(goal)

		// 4. CREATE WEIGH-INS
		val weighIn1 = WeighIn(
			user = savedUser,
			weightKg = BigDecimal("80.50"),
			weightDate = LocalDate.now().minusDays(7)
		)
		val weighIn2 = WeighIn(
			user = savedUser,
			weightKg = BigDecimal("79.80"), // Progress!
			weightDate = LocalDate.now()
		)
		weighInRepository.saveAll(listOf(weighIn1, weighIn2))

		// 5. CREATE FOODS
		val chicken = Food(
			user = savedUser,
			name = "Chicken Breast",
			caloriesPer100Grams = 165,
			proteinPer100Grams = BigDecimal("31.00"),
			carbsPer100Grams = BigDecimal("0.00"),
			fatsPer100Grams = BigDecimal("3.60"),
			servingSizeGrams = BigDecimal("100.00"),
			barcode = 123456L
		)

		val rice = Food(
			user = savedUser,
			name = "White Rice",
			caloriesPer100Grams = 130,
			proteinPer100Grams = BigDecimal("2.70"),
			carbsPer100Grams = BigDecimal("28.00"),
			fatsPer100Grams = BigDecimal("0.30"),
			servingSizeGrams = BigDecimal("100.00"),
			barcode = null
		)

		val savedChicken = foodRepository.save(chicken)
		val savedRice = foodRepository.save(rice)

		// 6. CREATE RECIPE (Chicken Rice Bowl)
		val recipe = Recipe(
			user = savedUser,
			name = "Chicken Rice Bowl"
		)
		val savedRecipe = recipeRepository.save(recipe)

		// 7. ADD INGREDIENTS TO RECIPE
		// 200g Chicken
		val ing1 = RecipeIngredient(
			recipe = savedRecipe,
			food = savedChicken,
			quantity = BigDecimal("200.00")
		)
		// 150g Rice
		val ing2 = RecipeIngredient(
			recipe = savedRecipe,
			food = savedRice,
			quantity = BigDecimal("150.00")
		)
		recipeIngredientRepository.saveAll(listOf(ing1, ing2))

		// 8. LOG THE RECIPE (Eating the whole bowl)
		// Manually calculating totals here since Seeder bypasses Service logic
		// Chicken (200g): 330 Cals, 62g Pro, 0g Carb, 7.2g Fat
		// Rice (150g):    195 Cals, 4g Pro, 42g Carb, 0.45g Fat
		// TOTAL:          525 Cals, 66g Pro, 42g Carb, 7.65g Fat

		val recipeLog = RecipeLog(
			user = savedUser,
			recipe = savedRecipe,
			recipeName = savedRecipe.name,
			servingsConsumed = BigDecimal("1.00"), // Ate 100% of it
			// Simple JSON string for the snapshot
			ingredientsSnapshot = """[{"name": "Chicken Breast", "qty": 200}, {"name": "White Rice", "qty": 150}]""",
			caloriesPerServing = 525,
			proteinPerServing = BigDecimal("66.00"),
			carbsPerServing = BigDecimal("42.00"),
			fatPerServing = BigDecimal("7.65"),
			logDate = LocalDate.now(),
			loggedAt = LocalDateTime.now()
		)
		recipeLogRepository.save(recipeLog)

		// 9. NORMAL FOOD LOGS
		val log1 = FoodLog(
			user = savedUser,
			food = savedChicken,
			name = "Lunch Chicken (Leftovers)",
			quantityGrams = BigDecimal("100.00"),
			loggedAt = LocalDateTime.now().minusHours(4),
			calories = 165,
			proteinGrams = BigDecimal("31.00"),
			carbsGrams = BigDecimal("0.00"),
			fatsGrams = BigDecimal("3.60")
		)

		val log2 = FoodLog(
			user = savedUser,
			food = null,
			name = "Random Cookie",
			quantityGrams = null,
			loggedAt = LocalDateTime.now().minusHours(1),
			calories = 250,
			proteinGrams = BigDecimal.ZERO,
			carbsGrams = BigDecimal.ZERO,
			fatsGrams = BigDecimal.ZERO
		)

		foodLogRepository.saveAll(listOf(log1, log2))

		println("SEEDING: Finished seeding")
	}
}