package com.sample.test_posts_app.di

import com.sample.test_posts_app.data.api.FeedApiDefinition
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class])
interface AppComponent {
    fun feedApiDefinition(): FeedApiDefinition
}