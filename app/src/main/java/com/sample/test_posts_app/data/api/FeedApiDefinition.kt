package com.sample.test_posts_app.data.api

import io.reactivex.Single
import retrofit2.http.GET

interface FeedApiDefinition {
    @GET("posts")
    fun posts(): Single<List<PostEntity>>

    @GET("users")
    fun users(): Single<List<UserEntity>>

    @GET("comments")
    fun comments(): Single<List<CommentEntity>>
}