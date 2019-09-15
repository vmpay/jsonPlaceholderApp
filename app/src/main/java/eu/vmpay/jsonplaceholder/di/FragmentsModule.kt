package eu.vmpay.jsonplaceholder.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import eu.vmpay.jsonplaceholder.MainActivity
import eu.vmpay.jsonplaceholder.fragments.PostDetailsFragment
import eu.vmpay.jsonplaceholder.fragments.PostsListFragment

@Module
internal abstract class FragmentsModule {

    @ContributesAndroidInjector
    internal abstract fun postsListFragment(): PostsListFragment

    @ContributesAndroidInjector
    internal abstract fun postDetailsFragment(): PostDetailsFragment

    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity
}