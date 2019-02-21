package com.sample.test_posts_app.presentation.postDetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import com.sample.test_posts_app.domain.Comment
import com.sample.test_posts_app.domain.Post
import com.sample.test_posts_app.domain.User
import com.sample.test_posts_app.presentation.feed.GetPostsUseCase
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PostDetailViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val testScheduler = TestScheduler()

    private val idleState = State(isIdle = true)
    private val loadingState = State(isLoading = true)
    private val errorState = State(isError = true)
    private val mockGetPostsUseCase = mock<GetPostsUseCase>()
    private val mockGetCommentsUseCase = mock<GetCommentsUseCase>()
    private val mockGetUserUseCase = mock<GetUserUseCase>()
    private val mockObserver = mock<Observer<State>>()

    private val actionLoad = Action.LoadPostDetails(5)
    private val post = Post(1, "foo", "bar", 1234)
    private val user = User(1, "user_foo", "user_bar", "foo@bar.com")
    private val comments = listOf(Comment(2, post.id, "comment name", "comment@bar.com"))
    private val successState = State(PostDetailsModel("foo", "bar", "user_bar", 1))

    private lateinit var sut: PostDetailViewModel

    @Before
    fun setUp() {
        sut = PostDetailViewModel(
            idleState,
            mockGetPostsUseCase,
            mockGetCommentsUseCase,
            mockGetUserUseCase,
            testScheduler,
            testScheduler
        )
    }

    @Test
    fun `When no action is send, no action is emmited`() {
        given(mockGetPostsUseCase.getPost(actionLoad.postId)).willReturn(Single.just(post))
        given(mockGetUserUseCase.getUser(post.authorId)).willReturn(Single.just(user))
        given(mockGetCommentsUseCase.getCommentsFor(post)).willReturn(Single.just(comments))

        sut.observableState.observeForever(mockObserver)
        testScheduler.triggerActions()

        verify(mockObserver, never()).onChanged(any())
    }

    @Test
    fun `Given all usecases completed successfully, when action received, emitting state with post details`() {
        given(mockGetPostsUseCase.getPost(actionLoad.postId)).willReturn(Single.just(post))
        given(mockGetUserUseCase.getUser(post.authorId)).willReturn(Single.just(user))
        given(mockGetCommentsUseCase.getCommentsFor(post)).willReturn(Single.just(comments))

        triggerLoadAction()

        inOrder(mockObserver) {
            verify(mockObserver).onChanged(loadingState)
            verify(mockObserver).onChanged(successState)
        }
    }

    @Test
    fun `Given loading user failed, when action received, emitting error state`() {
        given(mockGetPostsUseCase.getPost(actionLoad.postId)).willReturn(Single.just(post))
        given(mockGetUserUseCase.getUser(post.authorId)).willReturn(Single.error(RuntimeException()))
        given(mockGetCommentsUseCase.getCommentsFor(post)).willReturn(Single.just(comments))

        triggerLoadAction()

        assertLoadingAndErrorReceivedInOrder()
    }

    @Test
    fun `Given loading comment failed, when action received, emitting error state`() {
        given(mockGetPostsUseCase.getPost(actionLoad.postId)).willReturn(Single.just(post))
        given(mockGetUserUseCase.getUser(post.authorId)).willReturn(Single.just(user))
        given(mockGetCommentsUseCase.getCommentsFor(post)).willReturn(Single.error(RuntimeException()))

        triggerLoadAction()

        assertLoadingAndErrorReceivedInOrder()
    }

    @Test
    fun `Given loading post failed, when action received, emitting error state`() {
        given(mockGetPostsUseCase.getPost(actionLoad.postId)).willReturn(Single.error(RuntimeException()))
        given(mockGetUserUseCase.getUser(post.authorId)).willReturn(Single.just(user))
        given(mockGetCommentsUseCase.getCommentsFor(post)).willReturn(Single.just(comments))

        triggerLoadAction()

        assertLoadingAndErrorReceivedInOrder()
    }

    private fun triggerLoadAction() {
        sut.observableState.observeForever(mockObserver)
        sut.dispatch(actionLoad)
        testScheduler.triggerActions()
    }

    private fun assertLoadingAndErrorReceivedInOrder() {
        inOrder(mockObserver) {
            verify(mockObserver).onChanged(loadingState)
            verify(mockObserver).onChanged(errorState)
        }
    }
}