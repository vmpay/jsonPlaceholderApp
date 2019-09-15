package eu.vmpay.jsonplaceholder.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import eu.vmpay.jsonplaceholder.R
import eu.vmpay.jsonplaceholder.repository.Post

class PostAdapter(private val clickListener: (Post?) -> Unit) :
    PagedListAdapter<Post, PostAdapter.PostViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false),
            clickListener
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Post, newItem: Post) =
                oldItem == newItem
        }
    }

    class PostViewHolder(
        view: View,
        clickListener: (Post?) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private val header: TextView = view.findViewById(R.id.tvHeader)
        private val body: TextView = view.findViewById(R.id.tvBody)

        private var post: Post? = null

        init {
            view.setOnClickListener {
                clickListener.invoke(post)
            }
        }

        fun bind(post: Post?) {
            this.post = post
            if (post != null) {
                header.text = post.title
                body.text = post.body
            }
        }
    }
}