package eu.vmpay.jsonplaceholder.di

import dagger.Module
import dagger.Provides
import eu.vmpay.jsonplaceholder.utils.AppSchedulerProvider
import eu.vmpay.jsonplaceholder.utils.SchedulerProvider
import javax.inject.Singleton

@Module
class SchedulerModule {

    @Provides
    @Singleton
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()
}