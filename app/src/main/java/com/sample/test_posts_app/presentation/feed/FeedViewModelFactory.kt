package com.sample.test_posts_app.presentation.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FeedViewModelFactory(
    private val initialState: State?,
    private val getPostsUseCase: GetPostsUseCase
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FeedViewModel(initialState, getPostsUseCase, Schedulers.io(), AndroidSchedulers.mainThread()) as T
    }
}