package com.example.anyam.API

import com.google.gson.annotations.SerializedName


data class PostModelProvince(
		@field:SerializedName("rajaongkir")
		val rajaongkir: RajaongkirProvince? = null
)

data class RajaongkirProvince(

	@field:SerializedName("query")
	val query: QueryProvince? = null,

	@field:SerializedName("results")
	val results: List<ResultsItemProvince>? = null,

	@field:SerializedName("status")
	val status: StatusProvince? = null
)

data class QueryProvince(

	@field:SerializedName("key")
	val key: String? = null
)

data class ResultsItemProvince(

	@field:SerializedName("province")
	val province: String? = null,

	@field:SerializedName("province_id")
	val provinceId: String? = null
)

data class StatusProvince(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("description")
	val description: String? = null
)
