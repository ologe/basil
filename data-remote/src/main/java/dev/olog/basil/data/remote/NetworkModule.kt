package dev.olog.basil.data.remote

import android.content.Context
import dagger.Module
import dagger.Provides
import dev.olog.basil.data.remote.service.SpoonacularRecipeService
import dev.olog.basil.shared.dagger.qualifier.ApplicationContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
internal object NetworkModule {

    @Provides
    @JvmStatic
    @Singleton
    internal fun provideOkHttp(@ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(logInterceptor())
            .addInterceptor(headerInterceptor(context))
            .build()
    }

    @JvmStatic
    private fun logInterceptor(): Interceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        } else {
            // disable retrofit log on release
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return loggingInterceptor
    }

    @JvmStatic
    private fun headerInterceptor(context: Context): Interceptor {
        return Interceptor {
            val original = it.request()
            val request = original.newBuilder()
                .header("User-Agent", context.packageName)
                .addHeader("Content-Type", "application/json")
                .method(original.method, original.body)
                .build()
            it.proceed(request)
        }
    }

    @Provides
    @JvmStatic
    @Singleton
    internal fun provideLastFmRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @JvmStatic
    @Singleton
    internal fun provideLastFmRest(retrofit: Retrofit): SpoonacularRecipeService {
        return retrofit.create(SpoonacularRecipeService::class.java)
    }

}