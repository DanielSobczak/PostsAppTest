package com.sample.test_posts_app.data.api

import com.sample.test_posts_app.domain.User
import com.squareup.moshi.Json

data class UserEntity(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "username") val userName: String,
    @field:Json(name = "email") val email: String
) {
    fun asDomain() = User(id, name, userName, email)
}