package com.example.anyam.API

data class DataModelCost(
        val origin: String,
        val originType: String,
        val destination: String?,
        val destinationType: String,
        val weight: Int,
        val courier: String
)
