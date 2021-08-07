package com.example.anyam.API

import com.google.gson.annotations.SerializedName

data class PostModelSubdistrict(

	@field:SerializedName("rajaongkir")
	val rajaongkir: RajaongkirSubdistrict? = null
)

data class RajaongkirSubdistrict(

	@field:SerializedName("query")
	val query: QuerySubdistrict? = null,

	@field:SerializedName("results")
	val results: List<ResultsItemSubdistrict>? = null,

	@field:SerializedName("status")
	val status: StatusSubdistrict? = null
)

data class ResultsItemSubdistrict(

	@field:SerializedName("subdistrict_id")
	val subdistrictId: String? = null,

	@field:SerializedName("province")
	val province: String? = null,

	@field:SerializedName("province_id")
	val provinceId: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("subdistrict_name")
	val subdistrictName: String? = null,

	@field:SerializedName("city_id")
	val cityId: String? = null
)

data class StatusSubdistrict(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("description")
	val description: String? = null
)

data class QuerySubdistrict(

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("key")
	val key: String? = null
)
