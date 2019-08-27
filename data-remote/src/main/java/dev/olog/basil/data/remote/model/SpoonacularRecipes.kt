package dev.olog.basil.data.remote.model

data class SpoonacularRecipes(
    val recipes: List<SpoonacularRecipe>
)

data class SpoonacularRecipe(
    val id: Int,
    val title: String,
    val image: String,
    val imageType: String,

    val instructions: String,
    val analyzedInstructions: List<AnalyzedInstruction>,

    val extendedIngredients: List<ExtendedIngredient>,
    val readyInMinutes: Int,
    val servings: Int,

    val cuisines: List<String>,

    val dairyFree: Boolean,
    val glutenFree: Boolean,
    val ketogenic: Boolean,
    val vegan: Boolean,
    val vegetarian: Boolean,

    val diets: List<String>
)

data class AnalyzedInstruction(
    val name: String,
    val steps: List<Step>
)

data class Step(
    val equipment: List<Any>,
    val ingredients: List<Any>,
    val length: Length,
    val number: Int,
    val step: String
)

data class Length(
    val number: Int,
    val unit: String
)

data class ExtendedIngredient(
    val aisle: String,
    val amount: Double,
    val consitency: String,
    val id: Int,
    val image: String,
    val measures: Measures,
    val meta: List<String>,
    val metaInformation: List<String>,
    val name: String,
    val original: String,
    val originalName: String,
    val originalString: String,
    val unit: String
)

data class Measures(
    val metric: Metric,
    val us: Us
)

data class Us(
    val amount: Double,
    val unitLong: String,
    val unitShort: String
)

data class Metric(
    val amount: Double,
    val unitLong: String,
    val unitShort: String
)