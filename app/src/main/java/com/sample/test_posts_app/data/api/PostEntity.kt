package com.sample.test_posts_app.data.api

import com.sample.test_posts_app.domain.Post
import com.squareup.moshi.Json

data class PostEntity(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "userId") val userId: Int,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "body") val body: String
) {
    fun asDomain() = Post(id, title, body, userId)
}