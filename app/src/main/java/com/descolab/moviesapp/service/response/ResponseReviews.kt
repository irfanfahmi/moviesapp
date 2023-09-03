package com.descolab.moviesapp.service.response

import com.google.gson.annotations.SerializedName

data class ResponseReviews<T>(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null,

	@field:SerializedName("results")
	val results: ArrayList<Reviews?>? = null,

	@field:SerializedName("total_results")
	val totalResults: Int? = null
)

