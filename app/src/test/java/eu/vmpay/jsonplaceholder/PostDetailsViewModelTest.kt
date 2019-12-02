package eu.vmpay.jsonplaceholder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.DataSource
import eu.vmpay.jsonplaceholder.repository.AppRepository
import eu.vmpay.jsonplaceholder.repository.Comment
import eu.vmpay.jsonplaceholder.repository.Post
import eu.vmpay.jsonplaceholder.repository.User
import eu.vmpay.jsonplaceholder.viewmodels.PostDetailsViewModel
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import junit.framework.TestCase.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class PostDetailsViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val appRepository = mock(AppRepository::class.java)
    private val dataSourceFactory =
        mock(DataSource.Factory::class.java) as DataSource.Factory<Int, Comment>?
    private val post = mock(Post::class.java)
    private val user = mock(User::class.java)
    private val throwable = mock(Throwable::class.java)
    private val postId = 0
    private val userId = 0
    private val errorMessage = "Error message"

    private val testScheduler = TestScheduler()
    private val schedulerProvider = TestSchedulerProvider(testScheduler)

    private lateinit var viewModel: PostDetailsViewModel

    @Before
    fun setUp() {
        viewModel = PostDetailsViewModel(appRepository, schedulerProvider)
        `when`(appRepository.getCommentsByPostFactory(postId)).thenReturn(dataSourceFactory)
        `when`(throwable.message).thenReturn(errorMessage)
    }

    @Test
    fun setupHappyTest() {
        `when`(
            appRepository.fetchCommentsByPost(
                anyInt(),
                anyInt(),
                anyInt()
            )
        ).thenReturn(Single.just(listOf()))
        `when`(appRepository.getPost(postId)).thenReturn(Flowable.just(listOf(post)))
        `when`(post.userId).thenReturn(userId)
        `when`(appRepository.fetchUser(userId)).thenReturn(Single.just(user))
        `when`(appRepository.getUser(userId.toString())).thenReturn(Flowable.just(listOf(user)))

        viewModel.setup(postId)
        testScheduler.triggerActions()

        assertEquals("null", viewModel.error.value)
        assertEquals(user, viewModel.user.value)
        assertNotNull(viewModel.comments)
    }

    @Test
    fun setupFetchUserErrorTest() {
        `when`(
            appRepository.fetchCommentsByPost(
                anyInt(),
                anyInt(),
                anyInt()
            )
        ).thenReturn(Single.error(throwable))
        `when`(appRepository.getPost(postId)).thenReturn(Flowable.just(listOf(post)))
        `when`(post.userId).thenReturn(userId)
        `when`(appRepository.fetchUser(userId)).thenReturn(Single.error(throwable))
        `when`(appRepository.getUser(userId.toString())).thenReturn(Flowable.error(throwable))

        viewModel.setup(postId)
        testScheduler.triggerActions()

        assertEquals(errorMessage, viewModel.error.value)
        assertNull(viewModel.user.value)
        assertNotNull(viewModel.comments)
    }

    @Test
    fun setupGetPostErrorTest() {
        `when`(
            appRepository.fetchCommentsByPost(
                anyInt(),
                anyInt(),
                anyInt()
            )
        ).thenReturn(Single.error(throwable))
        `when`(appRepository.getPost(postId)).thenReturn(Flowable.just(listOf()))

        viewModel.setup(postId)
        testScheduler.triggerActions()

        assertEquals(errorMessage, viewModel.error.value)
        assertNull(viewModel.user.value)
        assertNotNull(viewModel.comments)
    }
}