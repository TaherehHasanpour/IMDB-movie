package com.example.myapplication.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.data.database.FavoriteDao
import com.example.myapplication.data.database.IMDBDatabase
import com.example.myapplication.data.database.PersonDao
import com.farzin.imdb.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext c: Context) = Room.databaseBuilder(
        c,
        IMDBDatabase::class.java,
        Constants.DB_NAME
    )
        .fallbackToDestructiveMigration()
        .build()


    @Provides
    fun providePersonDaoModule(database: IMDBDatabase): PersonDao = database.getPersonDao()


    @Provides
    fun provideFavoriteDaoModule(database: IMDBDatabase): FavoriteDao = database.getFavoriteDao()

}