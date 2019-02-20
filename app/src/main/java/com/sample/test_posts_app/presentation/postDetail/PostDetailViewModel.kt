package com.sample.test_posts_app.presentation.postDetail

import com.sample.test_posts_app.domain.Comment
import com.sample.test_posts_app.domain.Post
import com.sample.test_posts_app.domain.User
import com.sample.test_posts_app.presentation.feed.GetPostsUseCase
import com.ww.roxie.BaseAction
import com.ww.roxie.BaseState
import com.ww.roxie.BaseViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.plusAssign

sealed class Action : BaseAction {
    data class LoadPostDetails(val postId: Int) : Action()
}

data class State(
    val postDetailsModel: PostDetailsModel? = null,
    val isIdle: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false
) : BaseState {
    fun forLoading() = copy(isIdle = false, isLoading = true, isError = false, postDetailsModel = null)
    fun forReceivedData(postDetailsModel: PostDetailsModel) =
        copy(isLoading = false, postDetailsModel = postDetailsModel)

    fun forError() = copy(isLoading = false, isError = true)
}

sealed class Change {
    object Loading : Change()
    data class PostDetails(val postDetailsModel: PostDetailsModel) : Change()
    data class Error(val throwable: Throwable?) : Change()
}

data class PostDetailsModel(
    val title: String,
    val body: String,
    val userName: String,
    val numberOfComments: Int
)

class PostDetailViewModel(
    initialState: State?,
    private val getPostsUseCase: GetPostsUseCase,
    private val getCommentsUseCase: GetCommentsUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val backgroundScheduler: Scheduler,
    private val foregroundScheduler: Scheduler
) : BaseViewModel<Action, State>() {

    override val initialState = initialState ?: State(isIdle = true)

    init {
        bindData()
    }

    private fun bindData() {
        disposables += actions.ofType<Action.LoadPostDetails>()
            .switchMap { action ->
                getPostsUseCase.getPost(action.postId)
                    .flatMap { post -> obtainPostDetails(post) }
                    .subscribeOn(backgroundScheduler)
                    .toObservable()
                    .map<Change> { Change.PostDetails(it) }
                    .startWith(Change.Loading)
                    .onErrorReturn { Change.Error(it) }

            }
            .scan(initialState) { state, change ->
                when (change) {
                    is Change.Loading -> state.forLoading()
                    is Change.PostDetails -> state.forReceivedData(change.postDetailsModel)
                    is Change.Error -> state.forError()
                }
            }
            .filter { !it.isIdle }
            .distinctUntilChanged()
            .observeOn(foregroundScheduler)
            .subscribe { state.value = it }
    }

    private fun obtainPostDetails(post: Post) =
        Single.zip(
            getUserUseCase.getUser(post.authorId),
            getCommentsUseCase.getCommentsFor(post),
            BiFunction { user: User, comments: List<Comment> ->
                PostDetailsModel(post.title, post.body, user.username, comments.size)
            })
}