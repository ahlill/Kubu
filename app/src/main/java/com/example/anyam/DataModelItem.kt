package com.example.anyam

data class DataModelItem(
        var nama: String = "",
        var harga: Double = 0.0,
        var deskripsi: String = "",
        var gambar: Int = 0,
        var gambarCarosel: IntArray = intArrayOf(),
        var penjualan: Int = 0
)