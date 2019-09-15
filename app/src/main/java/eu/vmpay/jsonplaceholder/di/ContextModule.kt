package eu.vmpay.jsonplaceholder.di

import dagger.Module
import dagger.Provides
import eu.vmpay.jsonplaceholder.JsonPlaceholderApp

@Module
class ContextModule {

    @Provides
    fun provideContext(application: JsonPlaceholderApp) = application.applicationContext
}