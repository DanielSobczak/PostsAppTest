package com.sample.test_posts_app.presentation.postDetail

import com.sample.test_posts_app.domain.User
import io.reactivex.Single

interface GetUserUseCase {
    fun getUser(userId: Int): Single<User>
}

class DummyGetUserUseCase : GetUserUseCase {
    override fun getUser(userId: Int): Single<User> {
        return Single.just(User(1, "userName1", "userName 2", " foo@baar.com"))
    }
}