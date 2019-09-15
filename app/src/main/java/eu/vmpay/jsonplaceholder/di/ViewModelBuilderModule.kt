package eu.vmpay.jsonplaceholder.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
internal abstract class ViewModelBuilderModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: DaggerAwareViewModelFactory): ViewModelProvider.Factory
}