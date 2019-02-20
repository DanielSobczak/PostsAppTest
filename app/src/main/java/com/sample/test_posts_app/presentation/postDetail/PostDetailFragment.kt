package com.sample.test_posts_app.presentation.postDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sample.test_posts_app.R
import com.sample.test_posts_app.presentation.feed.DummyGetPostsUseCase
import kotlinx.android.synthetic.main.fragment_post_detail.*

class PostDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_post_detail, container, false)
    }

    private lateinit var viewModel: PostDetailViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(
            this,
            PostDetailViewModelFactory(null, DummyGetPostsUseCase(), DummyCommentsUseCaseImpl(), DummyGetUserUseCase())
        ).get(PostDetailViewModel::class.java)

        viewModel.observableState.observe(this, Observer { state -> state?.let { render(state) } })

        val postDetailFragmentArgs = PostDetailFragmentArgs.fromBundle(arguments!!)
        viewModel.dispatch(Action.LoadPostDetails(postDetailFragmentArgs.postId))
    }

    private fun render(state: State) {
        with(state) {
            when {
                isLoading -> renderLoadingState()
                isError -> renderErrorState()
                else -> renderPosts(postDetailsModel)
            }
        }
    }

    private fun renderLoadingState() {
        postDetailsContentGroup.visibility = View.GONE
        postDetailsErrorMessage.visibility = View.GONE
        postDetailsSpinner.visibility = View.VISIBLE
    }

    private fun renderErrorState() {
        postDetailsContentGroup.visibility = View.GONE
        postDetailsSpinner.visibility = View.GONE
        postDetailsErrorMessage.text = getString(R.string.post_details_loading_error_message)
    }

    private fun renderPosts(posts: PostDetailsModel?) {
        postDetailsContentGroup.visibility = View.VISIBLE
        postDetailsSpinner.visibility = View.GONE
        posts?.let {
            postTitle.text = posts.title
            postBody.text = posts.body
            postCommentsIndicator.text = posts.numberOfComments.toString()
        }
    }

}