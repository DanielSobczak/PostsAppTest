package com.sample.test_posts_app.domain

data class Post(
    val id: Int,
    val title: String,
    val body: String,
    val authorId: Int
)