package com.sample.test_posts_app.di

import com.sample.test_posts_app.data.CommentsRepository
import com.sample.test_posts_app.data.PostsRepository
import com.sample.test_posts_app.data.UsersRepository
import com.sample.test_posts_app.data.api.FeedApiDefinition
import com.sample.test_posts_app.data.api.FeedApiService
import com.sample.test_posts_app.data.cache.CommentsStorage
import com.sample.test_posts_app.data.cache.PostsStorage
import com.sample.test_posts_app.data.cache.UsersStorage
import com.sample.test_posts_app.presentation.feed.FeedViewModel
import com.sample.test_posts_app.presentation.feed.GetPostUseCaseImpl
import com.sample.test_posts_app.presentation.feed.GetPostsUseCase
import com.sample.test_posts_app.presentation.postDetail.*
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Provider

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
    fun providePostDetailViewModel(
        getPostsUseCase: GetPostsUseCase,
        getUserUseCase: GetUserUseCase,
        getCommentsUseCase: GetCommentsUseCase
    ): PostDetailViewModel = PostDetailViewModel(
        null,
        getPostsUseCase,
        getCommentsUseCase,
        getUserUseCase,
        Schedulers.io(),
        AndroidSchedulers.mainThread()
    )

    @Provides
    @JvmStatic
    fun provideFeedViewModel(getPostsUseCase: GetPostsUseCase): FeedViewModel =
        FeedViewModel(
            null,
            getPostsUseCase,
            Schedulers.io(),
            AndroidSchedulers.mainThread()
        )

    @Provides
    @JvmStatic
    fun provideFeedVMFactory(vmProvider: Provider<FeedViewModel>): ViewModelFactory<FeedViewModel> =
        ViewModelFactory(vmProvider)

    @Provides
    @JvmStatic
    fun providePostDetailsVMFactory(vmProvider: Provider<PostDetailViewModel>): ViewModelFactory<PostDetailViewModel> =
        ViewModelFactory(vmProvider)

}


