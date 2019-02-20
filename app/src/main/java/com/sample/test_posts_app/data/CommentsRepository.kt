package com.sample.test_posts_app.data

import com.sample.test_posts_app.data.api.FeedApiService
import com.sample.test_posts_app.domain.Comment
import com.sample.test_posts_app.domain.Post
import io.reactivex.Single

class CommentsRepository(
    private val feedApiService: FeedApiService
) {

    fun getComments(post: Post): Single<List<Comment>> =
        feedApiService.getComments()
            .map { it.filter { comment -> comment.postId == post.id } }
}