package com.example.githubusernavigationdanapi.data.remote.response

import com.google.gson.annotations.SerializedName

data class ListFollowingResponse(
	@field:SerializedName("ListFollowingResponse")
	val listFollowingResponse: List<ListFollowingResponseItem?>? = null
)

data class ListFollowingResponseItem(
	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,
)
