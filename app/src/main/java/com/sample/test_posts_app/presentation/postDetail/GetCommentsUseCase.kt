package com.sample.test_posts_app.presentation.postDetail

import com.sample.test_posts_app.domain.Comment
import com.sample.test_posts_app.domain.Post
import io.reactivex.Single

interface GetCommentsUseCase {
    fun getCommentsFor(post: Post): Single<List<Comment>>
}