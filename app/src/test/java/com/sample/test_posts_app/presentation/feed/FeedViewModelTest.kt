package com.sample.test_posts_app.presentation.feed

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import com.sample.test_posts_app.domain.Post
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FeedViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val testScheduler = TestScheduler()

    private val idleState = State(isIdle = true)
    private val loadingState = State(isLoading = true)
    private val failedState = State(isError = true)
    private val posts = listOf(Post(1, "foo", "bar", 1234))
    private val mockGetPostsUseCase = mock<GetPostsUseCase>()
    private val mockObserver = mock<Observer<State>>()

    private lateinit var sut: FeedViewModel

    @Before
    fun setUp() {
        sut = FeedViewModel(idleState, mockGetPostsUseCase, testScheduler, testScheduler)
    }

    @Test
    fun `When no action is send, no action is emitted`() {
        given(mockGetPostsUseCase.getAllPosts()).willReturn(Single.just(posts))

        sut.observableState.observeForever(mockObserver)
        testScheduler.triggerActions()

        verify(mockObserver, never()).onChanged(any())
    }

    @Test
    fun `Given posts loaded successfully, when action received, emitting state with posts`() {
        val successState = State(posts)
        given(mockGetPostsUseCase.getAllPosts()).willReturn(Single.just(posts))

        sut.observableState.observeForever(mockObserver)
        sut.dispatch(Action.LoadFeed)
        testScheduler.triggerActions()

        inOrder(mockObserver) {
            verify(mockObserver).onChanged(loadingState)
            verify(mockObserver).onChanged(successState)
        }
    }

    @Test
    fun `Given posts loading failed, when action received, emitting error state`() {
        given(mockGetPostsUseCase.getAllPosts()).willReturn(Single.error(RuntimeException()))

        sut.observableState.observeForever(mockObserver)
        sut.dispatch(Action.LoadFeed)
        testScheduler.triggerActions()

        inOrder(mockObserver) {
            verify(mockObserver).onChanged(loadingState)
            verify(mockObserver).onChanged(failedState)
        }
    }
}