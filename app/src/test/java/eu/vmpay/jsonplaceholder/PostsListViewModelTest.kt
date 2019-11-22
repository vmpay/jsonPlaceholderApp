package eu.vmpay.jsonplaceholder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.DataSource
import eu.vmpay.jsonplaceholder.repository.AppRepository
import eu.vmpay.jsonplaceholder.repository.Post
import eu.vmpay.jsonplaceholder.viewmodels.PostsListViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.*

class PostsListViewModelTest {
    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val appRepository = mock(AppRepository::class.java)
    private val dataSourceFactory =
        mock(DataSource.Factory::class.java) as DataSource.Factory<Int, Post>?

    @Before
    fun setUp() {
        `when`(appRepository.getPostsListFactory()).thenReturn(dataSourceFactory)
    }

    @Test
    fun initHappyTest() = testCoroutineRule.runBlockingTest {
        val post = mock(Post::class.java)
        `when`(appRepository.fetchPosts()).thenReturn(listOf(post))
        val viewModel = PostsListViewModel(appRepository)

        verify(appRepository).fetchPosts()
        assertEquals("null", viewModel.error.value)
        assertNotNull(viewModel.postsList)

    }

    @Test
    fun initFailTest() = testCoroutineRule.runBlockingTest {
        val errorMessage = "Error Message"
        val exception = mock(Exception::class.java)
        `when`(exception.message).thenReturn(errorMessage)
        `when`(appRepository.fetchPosts()).thenAnswer { throw exception }
        val viewModel = PostsListViewModel(appRepository)

        verify(appRepository).fetchPosts()
        verify(exception).printStackTrace()
        assertEquals(errorMessage, viewModel.error.value)
        assertNotNull(viewModel.postsList)
    }
}