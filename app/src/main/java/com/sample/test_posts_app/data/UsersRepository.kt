package com.sample.test_posts_app.data

import com.sample.test_posts_app.data.api.FeedApiService
import com.sample.test_posts_app.data.cache.UsersStorage
import com.sample.test_posts_app.domain.User
import io.reactivex.Single

class UsersRepository(
    private val feedApiService: FeedApiService,
    private val usersStorage: UsersStorage
) {

    fun getUser(userId: Int): Single<User> =
        Single.defer {
            val cached = usersStorage.get()
            if (cached != null) {
                Single.just(cached)
            } else {
                feedApiService.getUsers()
                    .doAfterSuccess {usersStorage.save(it)}
            }
        }.map { users -> users.find { user -> user.id == userId } }

}