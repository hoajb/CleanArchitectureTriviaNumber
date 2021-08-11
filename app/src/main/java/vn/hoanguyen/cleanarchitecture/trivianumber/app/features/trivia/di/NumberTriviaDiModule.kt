package vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.di

import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.data.datasources.*
import vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.data.repositories.NumberTriviaRepositoryImpl
import vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.domain.repositories.NumberTriviaRepository
import vn.hoanguyen.cleanarchitecture.trivianumber.core.platform.NetworkInfo
import javax.inject.Singleton

/**
 * Created by Hoa Nguyen on Aug 11 2021.
 */
@Module
@InstallIn(SingletonComponent::class)
class NumberTriviaDiModule {

    @Provides
    @Singleton
    fun provideNumberTriviaLocalDataSource(
        sharedPreferences: SharedPreferences,
        gson: Gson
    ): NumberTriviaLocalDataSource =
        NumberTriviaLocalSharePreferencesDataSourceImpl(sharedPreferences, gson)

    @Provides
    @Singleton
    fun provideNumberTriviaRemoteDataSource(
        api: NumberTriviaRestAPI
    ): NumberTriviaRemoteDataSource = NumberTriviaRemoteDataSourceImpl(api)

    @Provides
    @Singleton
    fun provideUseCase(
        remoteDataSource: NumberTriviaRemoteDataSource,
        localDataSource: NumberTriviaLocalDataSource,
        networkInfo: NetworkInfo
    ): NumberTriviaRepository =
        NumberTriviaRepositoryImpl(remoteDataSource, localDataSource, networkInfo)

}