package eu.vmpay.jsonplaceholder.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.transition.TransitionInflater
import dagger.android.support.DaggerFragment
import eu.vmpay.jsonplaceholder.R
import eu.vmpay.jsonplaceholder.adapters.CommentAdapter
import eu.vmpay.jsonplaceholder.utils.sendEmailTo
import eu.vmpay.jsonplaceholder.viewmodels.PostDetailsViewModel
import kotlinx.android.synthetic.main.fragment_post_details.view.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class PostDetailsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var adapter: CommentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val transition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_post_details, container, false)

        adapter = CommentAdapter()
        rootView.rvComments.adapter = adapter

        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(PostDetailsViewModel::class.java)

        val postId = arguments?.let { PostDetailsFragmentArgs.fromBundle(it).postId }
        if (postId != null)
            viewModel.setup(postId)

        viewModel.apply {
            error.observe(this@PostDetailsFragment, Observer {
                if (it != "null")
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            })
            post.observe(this@PostDetailsFragment, Observer {
                rootView.tvHeader.text = it.title
                rootView.tvBody.text = it.body
            })
            user.observe(this@PostDetailsFragment, Observer {
                rootView.tvUser.text = it.name
            })
            comments?.observe(this@PostDetailsFragment, Observer {
                adapter?.submitList(it)
            })
        }

        rootView.apply {
            tvUser.setOnClickListener {
                viewModel.user.value?.email?.let {
                    context?.sendEmailTo(it)
                }
            }
            ivBack.setOnClickListener { activity?.onBackPressed() }
        }
        return rootView
    }
}
