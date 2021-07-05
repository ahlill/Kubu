package com.example.anyam.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Cart::class], version = 1)

abstract class CartDB : RoomDatabase(){

    abstract fun cartDao() : CartDao

    companion object{
        @Volatile
        private var instance: CartDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also{
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            CartDB::class.java,
            "shoppingcart.db"
        ).build()
    }
}