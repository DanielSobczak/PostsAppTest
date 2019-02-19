package com.sample.test_posts_app.presentation.feed

import com.sample.test_posts_app.domain.Post
import com.ww.roxie.BaseAction
import com.ww.roxie.BaseState
import com.ww.roxie.BaseViewModel
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.plusAssign

sealed class Action : BaseAction {
    object LoadFeed : Action()
}

data class State(
    val posts: List<Post> = emptyList(),
    val isIdle: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false
) : BaseState {
    fun forLoading() = copy(isIdle = false, isLoading = true, isError = false, posts = emptyList())
    fun forReceivedData(posts: List<Post>) = copy(isLoading = false, posts = posts)
    fun forError() = copy(isLoading = false, isError = true)
}

sealed class Change {
    object Loading : Change()
    data class Posts(val posts: List<Post>) : Change()
    data class Error(val throwable: Throwable?) : Change()
}

class FeedViewModel(
    initialState: State?,
    private val getPostsUseCase: GetPostsUseCase,
    private val backgroundScheduler: Scheduler,
    private val foregroundScheduler: Scheduler
) : BaseViewModel<Action, State>() {

    override val initialState = initialState ?: State(isIdle = true)

    init {
        bindData()
    }

    private fun bindData() {
        disposables += actions.ofType<Action.LoadFeed>()
            .switchMap {
                getPostsUseCase.getAllPosts()
                    .subscribeOn(backgroundScheduler)
                    .toObservable()
                    .map<Change> { Change.Posts(it) }
                    .defaultIfEmpty(Change.Posts(emptyList()))
                    .startWith(Change.Loading)
                    .onErrorReturn { Change.Error(it) }
            }
            .scan(initialState) { state, change ->
                when (change) {
                    is Change.Loading -> state.forLoading()
                    is Change.Posts -> state.forReceivedData(change.posts)
                    is Change.Error -> state.forError()
                }
            }
            .filter { !it.isIdle }
            .distinctUntilChanged()
            .observeOn(foregroundScheduler)
            .subscribe { state.value = it }
    }

}