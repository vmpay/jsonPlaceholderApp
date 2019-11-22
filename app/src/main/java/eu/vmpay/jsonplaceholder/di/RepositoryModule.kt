package eu.vmpay.jsonplaceholder.di

import android.content.Context
import dagger.Module
import dagger.Provides
import eu.vmpay.jsonplaceholder.BuildConfig
import eu.vmpay.jsonplaceholder.repository.AppRepository
import eu.vmpay.jsonplaceholder.repository.local.AppDatabase
import eu.vmpay.jsonplaceholder.repository.remote.JsonPlaceholderService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ContextModule::class])
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(context: Context): AppDatabase = AppDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        }

    @Provides
    @Singleton
    fun provideHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient().newBuilder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofitInterface(httpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

    @Provides
    @Singleton
    fun provideJsonPlaceHolderService(retrofit: Retrofit): JsonPlaceholderService =
        retrofit.create(JsonPlaceholderService::class.java)

    @Provides
    @Singleton
    fun provideAppRepository(
        jsonPlaceholderService: JsonPlaceholderService,
        appDatabase: AppDatabase
    ): AppRepository = AppRepository(
        jsonPlaceholderService,
        appDatabase.postsDao(),
        appDatabase.commentsDao(),
        appDatabase.usersDao()
    )
}