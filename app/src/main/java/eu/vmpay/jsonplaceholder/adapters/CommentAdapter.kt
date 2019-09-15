package eu.vmpay.jsonplaceholder.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import eu.vmpay.jsonplaceholder.R
import eu.vmpay.jsonplaceholder.repository.Comment

class CommentAdapter : PagedListAdapter<Comment, CommentAdapter.CommentViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Comment>() {
            override fun areItemsTheSame(oldItem: Comment, newItem: Comment) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Comment, newItem: Comment) =
                oldItem == newItem
        }
    }

    class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val commentText: TextView = view.findViewById(R.id.tvComment)
        private val user: TextView = view.findViewById(R.id.tvAuthor)

        private var comment: Comment? = null

        fun bind(comment: Comment?) {
            this.comment = comment
            if (comment != null) {
                commentText.text = comment.body
                user.text = comment.name
            }
        }
    }
}