package dev.olog.basil.domain.entity

data class Ingredient(
        val recipeId: Long,
        val name: String,
        val quantity: Int,
        val order: Int
) {

    init {
        require(quantity > 0)
        require(order > 0)
    }

}
