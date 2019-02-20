package com.sample.test_posts_app.presentation.postDetail

import com.sample.test_posts_app.data.CommentsRepository
import com.sample.test_posts_app.domain.Comment
import com.sample.test_posts_app.domain.Post
import io.reactivex.Single

interface GetCommentsUseCase {
    fun getCommentsFor(post: Post): Single<List<Comment>>
}

class GetCommentsUseCaseImpl(
    private val commentsRepository: CommentsRepository
) : GetCommentsUseCase {
    override fun getCommentsFor(post: Post): Single<List<Comment>> {
        return commentsRepository.getComments(post)
    }

}

class DummyCommentsUseCaseImpl : GetCommentsUseCase {
    override fun getCommentsFor(post: Post): Single<List<Comment>> {
        return Single.just(listOf(Comment(1, 1, "a", "foo@bar.com")))
    }
}

