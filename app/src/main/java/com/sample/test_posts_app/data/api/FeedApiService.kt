package com.sample.test_posts_app.data.api

import com.sample.test_posts_app.domain.Comment
import com.sample.test_posts_app.domain.Post
import com.sample.test_posts_app.domain.User
import io.reactivex.Single

class FeedApiService(
    private val feedApiDefinition: FeedApiDefinition
) {

    fun getComments(): Single<List<Comment>> {
        return feedApiDefinition.comments().map { comments -> comments.map { it.asDomain() } }
    }

    fun getUsers(): Single<List<User>> {
        return feedApiDefinition.users().map { users -> users.map { it.asDomain() } }
    }

    fun getPosts(): Single<List<Post>> {
        return feedApiDefinition.posts().map { posts -> posts.map { it.asDomain() } }
    }
}