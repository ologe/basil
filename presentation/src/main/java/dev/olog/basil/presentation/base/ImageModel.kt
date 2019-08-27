package dev.olog.basil.presentation.base

data class ImageModel(
    override val id: Int,
    override val type: Int,
    val image: Int
) : BaseModel