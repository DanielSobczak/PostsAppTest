package com.sample.test_posts_app.feed.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.test_posts_app.R
import com.sample.test_posts_app.feed.domain.Post
import kotlinx.android.synthetic.main.feed_item.view.*
import kotlinx.android.synthetic.main.fragment_home_feed.*

class HomeFeedFragment : Fragment() {
    private val feedAdapter = FeedAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        feedList.layoutManager = LinearLayoutManager(context)
        feedList.adapter = feedAdapter
        feedAdapter.setPosts(
            listOf(
                Post(1, "this is title", "my body", 1234),
                Post(1, "another one", "with even longer body", 1234),
                Post(1, "this is title", "Lorem ipsum solor del", 1234),
                Post(1, "this is last title 4", "my body is ready", 1234)
            )
        )
    }
}

class FeedAdapter : RecyclerView.Adapter<FeedVH>() {
    private var posts: List<Post> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedVH {
        return FeedVH.create(parent)
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


