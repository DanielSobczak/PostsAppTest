package com.sample.test_posts_app.presentation.postDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sample.test_posts_app.presentation.feed.GetPostsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PostDetailViewModelFactory(
    private val initialState: State?,
    private val getPostsUseCase: GetPostsUseCase,
    private val getCommentsUseCase: GetCommentsUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostDetailViewModel(
            initialState,
            getPostsUseCase,
            getCommentsUseCase,
            getUserUseCase,
            Schedulers.io(),
            AndroidSchedulers.mainThread()
        ) as T
    }
}
