package eu.vmpay.jsonplaceholder.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import eu.vmpay.jsonplaceholder.JsonPlaceholderApp
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        FragmentsModule::class,
        ViewModelBuilderModule::class,
        ViewModelModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent : AndroidInjector<JsonPlaceholderApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<JsonPlaceholderApp>()
}