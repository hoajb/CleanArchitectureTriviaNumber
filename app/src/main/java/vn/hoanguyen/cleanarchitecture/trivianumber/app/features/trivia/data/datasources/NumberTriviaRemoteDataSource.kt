package vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.data.datasources

import vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.data.models.NumberTriviaDto

/**
 * Created by Hoa Nguyen on Aug 08 2021.
 */
interface NumberTriviaRemoteDataSource {
    /**
     * Call the api http://numbersapi.com/{number}?json
     * Throws a [ServerException] for all error code
     */
    suspend fun getConcreteNumberTrivia(number: Double): NumberTriviaDto

    /**
     * Call the api http://numbersapi.com/random/trivia?json
     * Throws a [ServerException] for all error code
     */
    suspend fun getRandomNumberTrivia(): NumberTriviaDto
}