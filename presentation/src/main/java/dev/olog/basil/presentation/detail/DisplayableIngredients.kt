package dev.olog.basil.presentation.detail

import dev.olog.basil.presentation.model.BaseModel

data class DisplayableIngredients(
    override val id: Int,
    override val type: Int,
    val name: String,
    val quantity: Int,
    val order: Int
) : BaseModel