package com.sample.test_posts_app.data

import com.sample.test_posts_app.data.api.FeedApiService
import com.sample.test_posts_app.data.cache.CommentsStorage
import com.sample.test_posts_app.domain.Comment
import com.sample.test_posts_app.domain.Post
import io.reactivex.Single

class CommentsRepository(
    private val feedApiService: FeedApiService,
    private val commentsStorage: CommentsStorage
) {

    fun getComments(post: Post): Single<List<Comment>> =
        Single.defer {
            val cached = commentsStorage.get()
            if (cached != null) {
                Single.just(cached)
            } else {
                feedApiService.getComments()
                    .doAfterSuccess {commentsStorage.save(it)}
            }
        }.map { it.filter { comment -> comment.postId == post.id } }
}