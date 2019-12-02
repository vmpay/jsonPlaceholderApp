package eu.vmpay.jsonplaceholder.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import eu.vmpay.jsonplaceholder.repository.AppRepository
import eu.vmpay.jsonplaceholder.repository.Comment
import eu.vmpay.jsonplaceholder.repository.Post
import eu.vmpay.jsonplaceholder.repository.User
import eu.vmpay.jsonplaceholder.utils.postDistinct
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostDetailsViewModel @Inject constructor(private val appRepository: AppRepository) :
    BaseViewModel() {

    val post = MutableLiveData<Post>()
    val user = MutableLiveData<User>()
    var comments: LiveData<PagedList<Comment>>? = null
    val error = MutableLiveData<String>()

    fun setup(postId: Int) {
        comments = LivePagedListBuilder(appRepository.getCommentsByPostFactory(postId), 50).build()
        launch {
            flow { emit(appRepository.getPost(postId)) }
                .map { postList ->
                    val post = postList.first()
                    this@PostDetailsViewModel.post.value = post
                    appRepository.fetchUser(post.userId)
                    appRepository.getUser(post.userId.toString())
                }
                .catch {
                    error.value = it.message
                    it.printStackTrace()
                }
                .collect {
                    error.value = "null"
                    user.postDistinct(it.firstOrNull())
                }
            flow { emit(appRepository.fetchCommentsByPost(postId, 10, 0)) }
                .catch {
                    error.value = it.message
                    it.printStackTrace()
                }
                .collect { error.value = "null" }
        }
    }
}