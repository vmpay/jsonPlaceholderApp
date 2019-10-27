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
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import dagger.android.support.DaggerFragment
import eu.vmpay.jsonplaceholder.R
import eu.vmpay.jsonplaceholder.adapters.PostAdapter
import eu.vmpay.jsonplaceholder.repository.Post
import eu.vmpay.jsonplaceholder.viewmodels.PostsListViewModel
import kotlinx.android.synthetic.main.fragment_posts_list.view.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class PostsListFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var adapter: PostAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_posts_list, container, false)

        rootView.apply {
            adapter = PostAdapter { it: Post?, view: View ->
                it?.id?.let {
                    val action =
                        PostsListFragmentDirections.actionPostsListFragmentToPostDetailsFragment(it)
                    val extras = FragmentNavigatorExtras(view to "transitionTitle")
                    findNavController().navigate(action, extras)
                }
            }
            rvPostList.adapter = adapter
        }

        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(PostsListViewModel::class.java)
        viewModel.apply {
            postsList.observe(this@PostsListFragment, Observer {
                adapter?.submitList(it)
            })
            error.observe(this@PostsListFragment, Observer {
                if (it != "null")
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            })
        }
        return rootView
    }
}
