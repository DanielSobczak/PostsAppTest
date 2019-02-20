package com.sample.test_posts_app.presentation.postDetail

import com.sample.test_posts_app.domain.User
import io.reactivex.Single

interface GetUserUseCase {
    fun getUser(userId: Int): Single<User>
}