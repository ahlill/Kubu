package com.example.anyam

import java.text.NumberFormat
import java.util.*

class Companion {
    companion object{

        var localID: Locale = Locale("in", "ID")
        var rupiah: NumberFormat = NumberFormat.getCurrencyInstance(localID)

    }
}