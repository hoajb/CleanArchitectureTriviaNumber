package vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.data.repositories

import vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.data.datasources.NumberTriviaLocalDataSource
import vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.data.datasources.NumberTriviaRemoteDataSource
import vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.domain.entities.NumberTrivia
import vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.domain.repositories.NumberTriviaRepository
import vn.hoanguyen.cleanarchitecture.trivianumber.core.exception.CachedException
import vn.hoanguyen.cleanarchitecture.trivianumber.core.exception.IFailure
import vn.hoanguyen.cleanarchitecture.trivianumber.core.functional.Either
import vn.hoanguyen.cleanarchitecture.trivianumber.core.platform.NetworkInfo
import java.io.IOException
import javax.inject.Inject

/**
 * Created by Hoa Nguyen on Aug 08 2021.
 */
class NumberTriviaRepositoryImpl @Inject constructor(
    private val remoteDataSource: NumberTriviaRemoteDataSource,
    private val localDataSource: NumberTriviaLocalDataSource,
    private val networkInfo: NetworkInfo
) : NumberTriviaRepository {
    override suspend fun getConcreteNumberTrivia(number: Int): Either<IFailure, NumberTrivia> {
        if (networkInfo.isNetworkAvailable().not()) {
            return try {
                val localResult = localDataSource.getConcreteNumberTrivia(number)
                Either.Right(localResult.toNumberTrivia())
            } catch (e: CachedException) {
                //not found
                Either.Left(IFailure.CacheFailure)
            }
        } else {
            return try {
                val result = remoteDataSource.getConcreteNumberTrivia(number)
                localDataSource.cacheNumberTrivia(result)
                Either.Right(result.toNumberTrivia())
            } catch (e: IOException) {
                Either.Left(IFailure.ServerFailure(e.message.orEmpty()))
            }
        }
    }

    override suspend fun getRandomNumberTrivia(): Either<IFailure, NumberTrivia> {
        //Fixme
        return Either.Left(IFailure.ServerFailure())
    }
}