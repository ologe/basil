package dev.olog.basil.data

import dev.olog.basil.core.Recipe
import dev.olog.basil.core.RecipeGateway
import dev.olog.basil.shared.dagger.qualifier.Local
import dev.olog.basil.shared.dagger.qualifier.Remote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class RecipeRepository @Inject constructor(
    @Local private val local: RecipeGateway,
    @Remote private val service: RecipeGateway
) : RecipeGateway {

    override suspend fun getAll(): List<Recipe> = withContext(Dispatchers.IO) {
        var cached = local.getAll()
        if (cached.isEmpty()) {
            cached = service.getAll()
            local.insertGroup(cached)
        }
        return@withContext cached
    }

    override suspend fun getById(id: Int): Recipe? = withContext(Dispatchers.IO) {
        var cached = local.getById(id)
        if (cached == null) {
            cached = service.getById(id)
            if (cached != null) {
                local.insert(cached)
            }
        }
        return@withContext cached
    }

    override suspend fun insert(recipe: Recipe) = withContext(Dispatchers.IO) {
        service.insert(recipe)
        local.insert(recipe)
    }

    override suspend fun insertGroup(recipes: List<Recipe>) = withContext(Dispatchers.IO) {
        service.insertGroup(recipes)
        local.insertGroup(recipes)
    }
}