package com.sample.test_posts_app.di

import com.sample.test_posts_app.data.api.FeedApiDefinition
import com.sample.test_posts_app.data.cache.CommentsStorage
import com.sample.test_posts_app.data.cache.PostsStorage
import com.sample.test_posts_app.data.cache.UsersStorage
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class, StorageModule::class])
interface AppComponent {
    fun feedApiDefinition(): FeedApiDefinition
    fun userStorage(): UsersStorage
    fun postStorage(): PostsStorage
    fun commentsStorage(): CommentsStorage
}