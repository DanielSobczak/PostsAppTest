package com.sample.test_posts_app.di

import com.sample.test_posts_app.presentation.feed.FeedViewModelFactory
import com.sample.test_posts_app.presentation.postDetail.PostDetailViewModelFactory
import dagger.Component

@FragmentScope
@Component(dependencies = [AppComponent::class], modules = [FeedModule::class])
interface FeedComponent {
    fun provideFeedViewModelFactory(): FeedViewModelFactory
    fun providePostDetailViewModelFactory(): PostDetailViewModelFactory
}

