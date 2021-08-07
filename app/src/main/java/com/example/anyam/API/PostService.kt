package com.example.anyam.daerah


import com.example.anyam.API.*
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Query

const val key = "fabe1a38c95522bf66ef8605b6cc8a99"

interface PostProvince {
    @GET("province?key=$key")
    fun getPosts(): Call<PostModelProvince>
}

interface PostCity {
    @GET("city?key=$key")
    fun getPosts(@Query("province") province: Int): Call<PostModelCity>
}

interface PostSubdistrict {
    @GET("subdistrict?key=$key")
    fun getPosts(@Query("city") city: Int): Call<PostModelSubdistrict>
}

interface PostCost {
    @Headers("key: $key")
    @POST("cost")
    fun getPosts(@Body postCostQuery: DataModelCost): Call<PostModelCost>
}
