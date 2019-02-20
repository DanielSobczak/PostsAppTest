package com.sample.test_posts_app.di

import com.sample.test_posts_app.data.api.FeedApiDefinition
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
object RetrofitModule {

    @JvmStatic
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/") //TODO export to build flavor
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()


    @JvmStatic
    @Provides
    @Singleton
    fun provideFeedService(retrofit: Retrofit): FeedApiDefinition = retrofit.create(FeedApiDefinition::class.java)

}