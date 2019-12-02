package eu.vmpay.jsonplaceholder.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import eu.vmpay.jsonplaceholder.repository.AppRepository
import eu.vmpay.jsonplaceholder.repository.Post
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostsListViewModel @Inject constructor(appRepository: AppRepository) : BaseViewModel() {

    val postsList: LiveData<PagedList<Post>> =
        LivePagedListBuilder(appRepository.getPostsListFactory(), 50).build()
    val error = MutableLiveData<String>()

    init {
        launch {
            flow { emit(appRepository.fetchPosts(10, 0)) }
                .catch {
                    error.value = it.message
                    it.printStackTrace()
                }
                .collect { error.value = "null" }
        }
    }
}