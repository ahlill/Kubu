package com.example.anyam.API

import com.google.gson.annotations.SerializedName

data class PostModelCity(

	@field:SerializedName("rajaongkir")
	val rajaongkir: RajaongkirCity? = null
)

data class ResultsItemCity(

	@field:SerializedName("city_name")
	val cityName: String? = null,

	@field:SerializedName("province")
	val province: String? = null,

	@field:SerializedName("province_id")
	val provinceId: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("postal_code")
	val postalCode: String? = null,

	@field:SerializedName("city_id")
	val cityId: String? = null
)

data class RajaongkirCity(

	@field:SerializedName("query")
	val query: QueryCity? = null,

	@field:SerializedName("results")
	val results: List<ResultsItemCity>? = null,

	@field:SerializedName("status")
	val status: StatusCity? = null
)

data class StatusCity(

		@field:SerializedName("code")
		val code: Int? = null,

		@field:SerializedName("description")
		val description: String? = null
)

data class QueryCity(

	@field:SerializedName("key")
	val key: String? = null
)
