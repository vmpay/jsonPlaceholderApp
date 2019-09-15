package eu.vmpay.jsonplaceholder

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import eu.vmpay.jsonplaceholder.di.DaggerAppComponent

class JsonPlaceholderApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder().create(this)

    override fun onCreate() {
        super.onCreate()
        // TODO init firebase here
    }
}