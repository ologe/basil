package dev.olog.basil.core

interface RecipeGateway {

    suspend fun getAll(): List<Recipe>
    suspend fun getById(id: Int): Recipe?

    suspend fun insert(recipe: Recipe)
    suspend fun insertGroup(recipes: List<Recipe>)

}