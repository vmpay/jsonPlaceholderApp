package eu.vmpay.jsonplaceholder.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import eu.vmpay.jsonplaceholder.viewmodels.PostDetailsViewModel
import eu.vmpay.jsonplaceholder.viewmodels.PostsListViewModel

@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(PostsListViewModel::class)
    abstract fun bindPostsListViewModel(viewModel: PostsListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PostDetailsViewModel::class)
    abstract fun bindPostDetailsViewModel(viewModel: PostDetailsViewModel): ViewModel
}