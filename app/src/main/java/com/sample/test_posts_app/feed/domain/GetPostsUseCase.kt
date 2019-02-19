package com.sample.test_posts_app.feed.domain

import io.reactivex.Single
import java.util.concurrent.TimeUnit

interface GetPostsUseCase {
    fun getAllPosts(): Single<List<Post>>
}

class DummyGetPostsUseCase : GetPostsUseCase {
    override fun getAllPosts(): Single<List<Post>> = Single.just(
        listOf(
            Post(1, "this is title", "my body", 1234),
            Post(1, "another one", "with even longer body", 1234),
            Post(1, "this is title", "Lorem ipsum solor del", 1234),
            Post(1, "this is last title 4", "my body is ready", 1234)
        )
    ).delay(2, TimeUnit.SECONDS)
}