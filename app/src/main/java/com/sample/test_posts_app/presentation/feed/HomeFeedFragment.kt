package com.sample.test_posts_app.presentation.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.test_posts_app.R
import com.sample.test_posts_app.domain.Post
import kotlinx.android.synthetic.main.feed_item.view.*
import kotlinx.android.synthetic.main.fragment_home_feed.*

class HomeFeedFragment : Fragment() {
    private val feedAdapter = FeedAdapter(this::onPostClicked)
    private lateinit var viewModel: FeedViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        feedList.layoutManager = LinearLayoutManager(context)
        feedList.adapter = feedAdapter

        //TODO implement Dagger to provide factory
        viewModel = ViewModelProviders.of(this, FeedViewModelFactory(null, DummyGetPostsUseCase()))
            .get(FeedViewModel::class.java)

        viewModel.observableState.observe(this, Observer { state -> state?.let { render(state) } })

        viewModel.dispatch(Action.LoadFeed)
    }

    private fun render(state: State) {
        with(state) {
            when {
                isLoading -> renderLoadingState()
                isError -> renderErrorState()
                else -> renderPosts(posts)
            }
        }
    }

    private fun renderLoadingState() {
        feedList.visibility = View.GONE
        feedErrorMessage.visibility = View.GONE
        feedLoadingSpinner.visibility = View.VISIBLE
    }

    private fun renderErrorState() {
        feedLoadingSpinner.visibility = View.GONE
        feedList.visibility = View.GONE
        feedErrorMessage.visibility = View.VISIBLE
        feedErrorMessage.text = getString(R.string.feed_loading_error_message)
    }

    private fun renderPosts(posts: List<Post>) {
        feedLoadingSpinner.visibility = View.GONE
        feedErrorMessage.visibility = View.GONE
        feedList.visibility = View.VISIBLE
        feedAdapter.setPosts(posts)
    }

    private fun onPostClicked(post: Post) {
        val navAction = HomeFeedFragmentDirections.actionHomeFeedFragmentToPostDetailFragment(post.id)
        findNavController().navigate(navAction)
    }
}

typealias PostClickListener = (Post) -> Unit

class FeedAdapter(private val clickListener: PostClickListener) : RecyclerView.Adapter<FeedVH>() {
    private var posts: List<Post> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedVH {
        val feedVH = FeedVH.create(parent)
        feedVH.itemView.setOnClickListener { clickListener.invoke(posts[feedVH.adapterPosition]) }
        return feedVH
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: FeedVH, position: Int) {
        holder.bind(posts[position])
    }

    fun setPosts(posts: List<Post>) {
        this.posts = posts
        notifyDataSetChanged()
    }

}

class FeedVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(post: Post) {
        itemView.apply {
            itemTitle.text = post.title
            itemSubTitle.text = post.body
        }
    }

    companion object {
        fun create(root: ViewGroup): FeedVH {
            val view = LayoutInflater.from(root.context).inflate(R.layout.feed_item, root, false)
            return FeedVH(view)
        }
    }
}


