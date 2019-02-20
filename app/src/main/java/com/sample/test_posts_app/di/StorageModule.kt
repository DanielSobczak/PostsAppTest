package com.sample.test_posts_app.di

import com.sample.test_posts_app.data.cache.CommentsStorage
import com.sample.test_posts_app.data.cache.PostsStorage
import com.sample.test_posts_app.data.cache.UsersStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object StorageModule {

    @Provides
    @JvmStatic
    @Singleton
    fun providePostsStorage(): PostsStorage {
        return PostsStorage()
    }

    @Provides
    @JvmStatic
    @Singleton
    fun provideUsersStorage(): UsersStorage {
        return UsersStorage()
    }

    @Provides
    @JvmStatic
    @Singleton
    fun provideCommentsStorage(): CommentsStorage {
        return CommentsStorage()
    }
}