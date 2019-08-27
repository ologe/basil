package dev.olog.basil.data.local

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dev.olog.basil.core.RecipeGateway
import dev.olog.basil.data.local.db.AppDatabase
import dev.olog.basil.shared.dagger.qualifier.ApplicationContext
import dev.olog.basil.shared.dagger.qualifier.Local
import javax.inject.Singleton

@Module
abstract class  RepositoryLocalModule {

    @Binds
    @Local
    @Singleton
    internal abstract fun provideRemoteRepository(impl: RecipeRepositoryLocal): RecipeGateway

    @Module
    companion object {

        @Provides
        @Singleton
        @JvmStatic
        internal fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "db")
                .build()
        }

    }

}