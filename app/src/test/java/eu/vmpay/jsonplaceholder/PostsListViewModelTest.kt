package eu.vmpay.jsonplaceholder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.DataSource
import eu.vmpay.jsonplaceholder.repository.AppRepository
import eu.vmpay.jsonplaceholder.repository.Post
import eu.vmpay.jsonplaceholder.viewmodels.PostsListViewModel
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.*

class PostsListViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val appRepository = mock(AppRepository::class.java)
    private val dataSourceFactory =
        mock(DataSource.Factory::class.java) as DataSource.Factory<Int, Post>?
    private val throwable = mock(Throwable::class.java)
    private val errorMessage = "Error Message"

    private val testScheduler = TestScheduler()
    private val schedulerProvider = TestSchedulerProvider(testScheduler)

    @Before
    fun setUp() {
        `when`(appRepository.getPostsListFactory()).thenReturn(dataSourceFactory)
    }

    @Test
    fun initHappyTest() {
        `when`(appRepository.fetchPosts(anyInt(), anyInt())).thenReturn(Single.just(listOf()))
        val viewModel = PostsListViewModel(appRepository, schedulerProvider)
        testScheduler.triggerActions()

        verify(appRepository).fetchPosts(anyInt(), anyInt())
        assertEquals("null", viewModel.error.value)
        assertNotNull(viewModel.postsList)
    }

    @Test
    fun initFailTest() {
        `when`(appRepository.fetchPosts(anyInt(), anyInt())).thenReturn(Single.error(throwable))
        `when`(throwable.message).thenReturn(errorMessage)
        val viewModel = PostsListViewModel(appRepository, schedulerProvider)
        testScheduler.triggerActions()

        verify(appRepository).fetchPosts(anyInt(), anyInt())
        assertEquals(errorMessage, viewModel.error.value)
        assertNotNull(viewModel.postsList)
    }
}