package com.example.anyam.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cart(
        @PrimaryKey(autoGenerate = true)
        val idBarang: Int,
        val namaBarang: String,
        val hargaBarang: Double,
//        val hargaSubTotal: Double,
        val jumlahBarang: Int,
        val gambarBarang: Int
)