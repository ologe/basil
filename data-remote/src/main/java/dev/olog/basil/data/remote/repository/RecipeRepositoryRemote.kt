package dev.olog.basil.data.remote.repository

import dev.olog.basil.core.Recipe
import dev.olog.basil.core.RecipeGateway
import dev.olog.basil.data.remote.mapper.toDomain
import dev.olog.basil.data.remote.service.SpoonacularRecipeService
import dev.olog.basil.data.remote.utils.networkCall
import javax.inject.Inject

class RecipeRepositoryRemote @Inject constructor(
    private val service: SpoonacularRecipeService
) : RecipeGateway {

    override suspend fun getAll(): List<Recipe> {
        val recipes = networkCall { service.getRandomRecipes() } ?: return emptyList()
        return recipes.recipes.map { it.toDomain() }

    }

    override suspend fun getById(id: Int): Recipe? {
        return null
    }

    override suspend fun insert(recipe: Recipe) {

    }

    override suspend fun insertGroup(recipes: List<Recipe>) {

    }
}