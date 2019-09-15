package eu.vmpay.jsonplaceholder.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import eu.vmpay.jsonplaceholder.repository.AppRepository
import eu.vmpay.jsonplaceholder.repository.Post
import eu.vmpay.jsonplaceholder.utils.SchedulerProvider
import javax.inject.Inject

class PostsListViewModel @Inject constructor(
    appRepository: AppRepository,
    schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    val postsList: LiveData<PagedList<Post>> =
        LivePagedListBuilder(appRepository.getPostsListFactory(), 50).build()
    val error = MutableLiveData<String>()

    init {
        compositeDisposable.add(
            appRepository.fetchPosts()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.main())
                .subscribe({
                    error.value = "null"
                }, {
                    error.value = it.message
                })
        )
    }
}