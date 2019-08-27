package dev.olog.basil.data.local

import dev.olog.basil.core.Recipe
import dev.olog.basil.core.RecipeGateway
import dev.olog.basil.data.local.db.AppDatabase
import dev.olog.basil.data.local.mapper.toDomain
import dev.olog.basil.data.local.mapper.toEntity
import javax.inject.Inject

internal class RecipeRepositoryLocal @Inject constructor(
    appDatabase: AppDatabase
) : RecipeGateway {

    private val recipeDao = appDatabase.recipeDao()

    override suspend fun getAll(): List<Recipe> {
        return recipeDao.getAll().map { it.toDomain() }
    }

    override suspend fun getById(id: Int): Recipe? {
        return recipeDao.getById(id)?.toDomain()
    }

    override suspend fun insert(recipe: Recipe) {
        recipeDao.insert(recipe.toEntity())
    }

    override suspend fun insertGroup(recipes: List<Recipe>) {
        recipeDao.insertGroup(recipes.map { it.toEntity() })
    }
}
