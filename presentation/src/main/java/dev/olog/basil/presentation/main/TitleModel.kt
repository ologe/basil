package dev.olog.basil.presentation.main

import dev.olog.basil.presentation.base.BaseModel

data class TitleModel(
        override val id: Int,
        override val type: Int,
        val title: String
) : BaseModel