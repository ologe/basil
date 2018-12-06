package dev.olog.basil.presentation.model

import dev.olog.basil.domain.entity.Ingredient
import dev.olog.basil.domain.entity.Tag

data class DisplayableRecipe(
        val id: Long,
        val name: String,
        val description: String,
        val people: String,
        val calories: String,
        val ingredients: MutableList<Ingredient>,
        val images: MutableList<String>,
        val tags: MutableList<Tag>
)
