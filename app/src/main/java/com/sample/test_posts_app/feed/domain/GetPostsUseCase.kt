package com.sample.test_posts_app.feed.domain

import io.reactivex.Single

interface GetPostsUseCase{
    fun getAllPosts(): Single<List<Post>>
}

class DummyGetPostsUseCase : GetPostsUseCase{
    override fun getAllPosts(): Single<List<Post>> = Single.just(listOf())
}