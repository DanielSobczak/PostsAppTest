package com.sample.test_posts_app.di

import com.sample.test_posts_app.data.CommentsRepository
import com.sample.test_posts_app.data.PostsRepository
import com.sample.test_posts_app.data.UsersRepository
import com.sample.test_posts_app.data.api.FeedApiDefinition
import com.sample.test_posts_app.data.api.FeedApiService
import com.sample.test_posts_app.data.cache.CommentsStorage
import com.sample.test_posts_app.data.cache.PostsStorage
import com.sample.test_posts_app.data.cache.UsersStorage
import com.sample.test_posts_app.presentation.feed.FeedViewModelFactory
import com.sample.test_posts_app.presentation.feed.GetPostUseCaseImpl
import com.sample.test_posts_app.presentation.feed.GetPostsUseCase
import com.sample.test_posts_app.presentation.postDetail.*
import dagger.Module
import dagger.Provides

@Module
object FeedModule {

    @Provides
    @JvmStatic
    fun provideFeedAPIService(feedApiDefinition: FeedApiDefinition) = FeedApiService(feedApiDefinition)

    @Provides
    @JvmStatic
    fun provideUserUseCase(feedApiService: FeedApiService, usersStorage: UsersStorage): GetUserUseCase =
        GetUserUseCaseImpl(UsersRepository(feedApiService, usersStorage))

    @Provides
    @JvmStatic
    fun providePostUseCase(feedApiService: FeedApiService, postsStorage: PostsStorage): GetPostsUseCase =
        GetPostUseCaseImpl(PostsRepository(feedApiService, postsStorage))

    @Provides
    @JvmStatic
    fun provideCommentsUseCase(feedApiService: FeedApiService, commentsStorage: CommentsStorage): GetCommentsUseCase =
        GetCommentsUseCaseImpl(CommentsRepository(feedApiService, commentsStorage))

    @Provides
    @JvmStatic
    fun provideFeedVMFactory(getPostsUseCase: GetPostsUseCase): FeedViewModelFactory {
        return FeedViewModelFactory(null, getPostsUseCase)
    }

    @Provides
    @JvmStatic
    fun providePostDetailsVMFactory(
        getPostsUseCase: GetPostsUseCase,
        getUserUseCase: GetUserUseCase,
        getCommentsUseCase: GetCommentsUseCase
    ): PostDetailViewModelFactory {
        return PostDetailViewModelFactory(
            null,
            getPostsUseCase,
            getCommentsUseCase,
            getUserUseCase
        )
    }
}


