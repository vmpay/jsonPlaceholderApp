package eu.vmpay.jsonplaceholder.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import eu.vmpay.jsonplaceholder.repository.AppRepository
import eu.vmpay.jsonplaceholder.repository.Comment
import eu.vmpay.jsonplaceholder.repository.Post
import eu.vmpay.jsonplaceholder.repository.User
import eu.vmpay.jsonplaceholder.utils.SchedulerProvider
import eu.vmpay.jsonplaceholder.utils.postDistinct
import io.reactivex.Flowable
import javax.inject.Inject

class PostDetailsViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    val post = MutableLiveData<Post>()
    val user = MutableLiveData<User>()
    var comments: LiveData<PagedList<Comment>>? = null
    val error = MutableLiveData<String>()

    fun setup(postId: Int) {
        comments = LivePagedListBuilder(appRepository.getCommentsByPostFactory(postId), 50).build()
        compositeDisposable.add(
            appRepository.getPost(postId)
                .subscribeOn(schedulerProvider.io())
                .flatMap {
                    val currentPost = it.firstOrNull()
                    if (currentPost != null) {
                        post.postDistinct(currentPost)
                        compositeDisposable.add(
                            appRepository.fetchUser(currentPost.userId)
                                .subscribeOn(schedulerProvider.io())
                                .observeOn(schedulerProvider.main())
                                .subscribe({
                                    error.value = "null"
                                }, {
                                    error.value = it.message
                                })
                        )
                        appRepository.getUser("${currentPost.userId}")
                    } else {
                        Flowable.error(Throwable("Post not found"))
                    }
                }
                .observeOn(schedulerProvider.main())
                .subscribe({
                    error.value = "null"
                    user.postDistinct(it.firstOrNull())
                }, {
                    error.value = it.message
                })
        )
        compositeDisposable.add(
            appRepository.fetchCommentsByPost(postId, 10, 0)
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