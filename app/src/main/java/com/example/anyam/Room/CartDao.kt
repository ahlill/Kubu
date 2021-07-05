package com.example.anyam.Room

import androidx.room.*

@Dao
interface CartDao {

    @Insert
    suspend fun addCart(cart: Cart)

    @Delete
    suspend fun deleteCart(cart: Cart)

    @Query("SELECT * FROM cart order by idBarang DESC")
    suspend fun getCart(): List<Cart>
}