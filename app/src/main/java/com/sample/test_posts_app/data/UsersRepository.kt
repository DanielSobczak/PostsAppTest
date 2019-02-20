package com.sample.test_posts_app.data

import com.sample.test_posts_app.data.api.FeedApiService
import com.sample.test_posts_app.domain.User
import io.reactivex.Single

class UsersRepository(
    private val feedApiService: FeedApiService
) {

    fun getUsers(): Single<List<User>> = feedApiService.getUsers()

    fun getUser(userId: Int): Single<User> {
        return getUsers()
            .map { users -> users.find { user -> user.id == userId } }
    }
}