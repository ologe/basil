package dev.olog.basil.data.remote.service

import dev.olog.basil.data.remote.BuildConfig
import dev.olog.basil.data.remote.model.SpoonacularRecipes
import retrofit2.Response
import retrofit2.http.GET

private const val BASE_URL = "?apiKey=${BuildConfig.SPOONACULAR_KEY}"

interface SpoonacularRecipeService {

    @GET("recipes/random$BASE_URL&number=10")
    suspend fun getRandomRecipes() : Response<SpoonacularRecipes>

}