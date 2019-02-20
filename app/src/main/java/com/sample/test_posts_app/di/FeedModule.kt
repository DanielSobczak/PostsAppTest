package com.sample.test_posts_app.di

import com.sample.test_posts_app.presentation.feed.DummyGetPostsUseCase
import com.sample.test_posts_app.presentation.feed.FeedViewModelFactory
import com.sample.test_posts_app.presentation.feed.GetPostsUseCase
import com.sample.test_posts_app.presentation.postDetail.*
import dagger.Module
import dagger.Provides

@Module
object FeedModule {

    @Provides
    @JvmStatic
    fun provideUserUseCase(): GetUserUseCase = DummyGetUserUseCase()

    @Provides
    @JvmStatic
    fun providePostUseCase(): GetPostsUseCase = DummyGetPostsUseCase()

    @Provides
    @JvmStatic
    fun provideCommentsUseCase(): GetCommentsUseCase = DummyCommentsUseCaseImpl()

    @Provides
    @JvmStatic
    fun provideFeedVMFactory(): FeedViewModelFactory {
        return FeedViewModelFactory(null, DummyGetPostsUseCase())
    }

    @Provides
    @JvmStatic
    fun providePostDetailsVMFactory(): PostDetailViewModelFactory {
        return PostDetailViewModelFactory(
            null,
            DummyGetPostsUseCase(),
            DummyCommentsUseCaseImpl(),
            DummyGetUserUseCase()
        )
    }
}


