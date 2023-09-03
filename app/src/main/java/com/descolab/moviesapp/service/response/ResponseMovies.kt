package com.descolab.moviesapp.service.response

import com.google.gson.annotations.SerializedName

data class ResponseMovies<T>(

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null,

	@field:SerializedName("results")
	val results: ArrayList<Movie?>? = null,

	@field:SerializedName("total_results")
	val totalResults: Int? = null
)


