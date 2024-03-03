package com.example.githubusernavigationdanapi.data.remote.response

import com.google.gson.annotations.SerializedName

data class ListFollowerResponse(
	@field:SerializedName("ListFollowerResponse")
	val listFollowerResponse: List<ListFollowerResponseItem?>? = null
)

data class ListFollowerResponseItem(
	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,
)
