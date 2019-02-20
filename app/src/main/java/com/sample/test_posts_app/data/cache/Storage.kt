package com.sample.test_posts_app.data.cache

import com.sample.test_posts_app.domain.Comment
import com.sample.test_posts_app.domain.Post
import com.sample.test_posts_app.domain.User

interface Storage<T> {
    fun save(data: T)
    fun get(): T?
}

open class InMemoryStorage<T> : Storage<T> {
    private var item: T? = null

    override fun save(data: T) {
        this.item = data
    }

    override fun get(): T? = item

}

class PostsStorage : InMemoryStorage<List<Post>>()
class UsersStorage : InMemoryStorage<List<User>>()
class CommentsStorage : InMemoryStorage<List<Comment>>()

