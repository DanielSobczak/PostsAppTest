package com.sample.test_posts_app.feed.domain

data class Comment(
    val id: Int,
    val postId: Int,
    val name: String,
    val email: String
)