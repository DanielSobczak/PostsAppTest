package com.sample.test_posts_app.presentation.feed

import com.sample.test_posts_app.data.PostsRepository
import com.sample.test_posts_app.domain.Post
import io.reactivex.Single

interface GetPostsUseCase {
    fun getAllPosts(): Single<List<Post>>
    fun getPost(postId: Int): Single<Post>
}

class GetPostUseCaseImpl(
    private val postsRepository: PostsRepository
) : GetPostsUseCase {

    override fun getAllPosts(): Single<List<Post>> = postsRepository.getPosts()

    override fun getPost(postId: Int): Single<Post> = postsRepository.getPost(postId)
}