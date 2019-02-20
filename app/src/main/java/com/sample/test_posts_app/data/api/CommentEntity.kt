package com.sample.test_posts_app.data.api

import com.sample.test_posts_app.domain.Comment
import com.squareup.moshi.Json

data class CommentEntity(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "postId") val postId: Int,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "body") val body: String
) {
    fun asDomain() = Comment(id, postId, name, body)
}