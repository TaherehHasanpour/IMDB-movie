package com.example.myapplication.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.models.database.FavoriteDBModel
import com.example.myapplication.models.database.PersonDBModel

@Database(entities = [PersonDBModel::class, FavoriteDBModel::class], version = 2, exportSchema = false)
abstract class IMDBDatabase : RoomDatabase() {

    abstract fun getPersonDao() : PersonDao

    abstract fun getFavoriteDao(): FavoriteDao

}