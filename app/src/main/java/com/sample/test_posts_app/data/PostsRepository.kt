package com.sample.test_posts_app.data

import com.sample.test_posts_app.data.api.FeedApiService
import com.sample.test_posts_app.data.cache.PostsStorage
import com.sample.test_posts_app.domain.Post
import io.reactivex.Single

class PostsRepository(
    private val feedApiService: FeedApiService,
    private val postsStorage: PostsStorage
) {

    fun getPosts(): Single<List<Post>> =
        Single.defer {
            val cached = postsStorage.get()
            if (cached != null) {
                Single.just(cached)
            } else {
                feedApiService.getPosts()
                    .doAfterSuccess { postsStorage.save(it) }
            }
        }

    fun getPost(postId: Int): Single<Post> = getPosts().map { it.find { post -> post.id == postId } }
}