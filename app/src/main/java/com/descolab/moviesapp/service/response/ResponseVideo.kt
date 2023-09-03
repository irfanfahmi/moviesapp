package com.descolab.moviesapp.service.response

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class ResponseVideo<T>(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("results")
	val results: ArrayList<Video?>? = null
)

