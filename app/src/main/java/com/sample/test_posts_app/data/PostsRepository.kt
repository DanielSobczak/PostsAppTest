package com.sample.test_posts_app.data

import com.sample.test_posts_app.data.api.FeedApiService
import com.sample.test_posts_app.domain.Post
import io.reactivex.Single

class PostsRepository(
    private val feedApiService: FeedApiService
) {

    fun getPosts(): Single<List<Post>> = feedApiService.getPosts()
}