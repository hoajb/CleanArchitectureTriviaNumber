package vn.hoanguyen.cleanarchitecture.trivianumber.core.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vn.hoanguyen.cleanarchitecture.trivianumber.BuildConfig
import vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.data.datasources.NumberTriviaRestAPI
import vn.hoanguyen.cleanarchitecture.trivianumber.core.platform.NetworkInfo
import javax.inject.Singleton

/**
 * Created by Hoa Nguyen on Aug 09 2021.
 */

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://numbersapi.com/")
            .client(createClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideNumberTriviaRestAPI(retrofit: Retrofit): NumberTriviaRestAPI {
        return retrofit.create(NumberTriviaRestAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkInfo(@ApplicationContext context: Context): NetworkInfo = NetworkInfo(context)

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("trivia_pref", Context.MODE_PRIVATE)

}