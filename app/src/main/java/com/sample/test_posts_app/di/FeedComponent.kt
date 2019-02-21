package com.sample.test_posts_app.di

import com.sample.test_posts_app.presentation.feed.FeedViewModel
import com.sample.test_posts_app.presentation.postDetail.PostDetailViewModel
import dagger.Component

@FragmentScope
@Component(dependencies = [AppComponent::class], modules = [FeedModule::class])
interface FeedComponent {
    fun provideFeedViewModelFactory(): ViewModelFactory<FeedViewModel>
    fun providePostDetailViewModelFactory(): ViewModelFactory<PostDetailViewModel>
}

