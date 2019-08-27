package dev.olog.basil.data.local.mapper

import dev.olog.basil.core.Recipe
import dev.olog.basil.data.local.entity.RecipeEntity

internal fun RecipeEntity.toDomain(): Recipe {
    return Recipe(
        this.id,
        this.name,
        this.description,
        this.allergens,
        this.category,
        this.ingredients,
        this.image
    )
}

internal fun Recipe.toEntity(): RecipeEntity {
    return RecipeEntity(
        this.id,
        this.name,
        this.description,
        this.allergens,
        this.category,
        this.ingredients,
        this.image
    )
}