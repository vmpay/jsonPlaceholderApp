package eu.vmpay.jsonplaceholder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.DataSource
import eu.vmpay.jsonplaceholder.repository.AppRepository
import eu.vmpay.jsonplaceholder.repository.Comment
import eu.vmpay.jsonplaceholder.repository.Post
import eu.vmpay.jsonplaceholder.repository.User
import eu.vmpay.jsonplaceholder.viewmodels.PostDetailsViewModel
import junit.framework.TestCase.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

class PostDetailsViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val appRepository = mock(AppRepository::class.java)
    private val dataSourceFactory =
        mock(DataSource.Factory::class.java) as DataSource.Factory<Int, Comment>?
    private val post = mock(Post::class.java)
    private val user = mock(User::class.java)
    private val throwable = mock(Throwable::class.java)
    private val postId = 0
    private val userId = 0
    private val errorMessage = "Error message"

    private lateinit var viewModel: PostDetailsViewModel

    @Before
    fun setUp() = testCoroutineRule.runBlockingTest {
        viewModel = PostDetailsViewModel(appRepository)
        `when`(appRepository.getCommentsByPostFactory(postId)).thenReturn(dataSourceFactory)
        `when`(throwable.message).thenReturn(errorMessage)
        `when`(post.userId).thenReturn(userId)
        `when`(appRepository.getPost(postId)).thenReturn(listOf(post))
    }

    @Test
    fun setupHappyTest() = testCoroutineRule.runBlockingTest {
        `when`(
            appRepository.fetchCommentsByPost(
                ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyInt()
            )
        ).thenReturn(listOf())
        `when`(post.userId).thenReturn(userId)
        `when`(appRepository.fetchUser(userId)).thenReturn(user)
        `when`(appRepository.getUser(userId.toString())).thenReturn(listOf(user))

        viewModel.setup(postId)

        assertEquals("null", viewModel.error.value)
        assertEquals(user, viewModel.user.value)
        assertNotNull(viewModel.comments)
    }

    @Test
    fun setupFetchUserErrorTest() = testCoroutineRule.runBlockingTest {
        `when`(appRepository.fetchCommentsByPost(anyInt(), anyInt(), anyInt()))
            .thenAnswer { throw throwable }
        `when`(appRepository.fetchUser(userId)).thenAnswer { throw throwable }
        `when`(appRepository.getUser(userId.toString())).thenAnswer { throw throwable }

        viewModel.setup(postId)

        verify(throwable, times(2)).printStackTrace()
        assertEquals(errorMessage, viewModel.error.value)
        assertNull(viewModel.user.value)
        assertNotNull(viewModel.comments)
    }

    @Test
    fun setupGetPostErrorTest() = testCoroutineRule.runBlockingTest {
        `when`(appRepository.fetchCommentsByPost(anyInt(), anyInt(), anyInt()))
            .thenAnswer { throw throwable }
        `when`(appRepository.getPost(postId)).thenAnswer { throw throwable }

        viewModel.setup(postId)

        verify(throwable, times(2)).printStackTrace()
        assertEquals(errorMessage, viewModel.error.value)
        assertNull(viewModel.user.value)
        assertNotNull(viewModel.comments)
    }
}