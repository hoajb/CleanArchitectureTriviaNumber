package vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.data.datasources

import vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.data.models.NumberTriviaDto

/**
 * Created by Hoa Nguyen on Aug 08 2021.
 */
interface NumberTriviaLocalDataSource {
    /**
     * Gets the cached [NumberTriviaDto] which was gotten the lasttime the user has an internet connection
     * Throws a [CachedException] if no cached data is present
     */
    suspend fun getConcreteNumberTrivia(number: Double): NumberTriviaDto

    /**
     * Call the api http://numbersapi.com/random/trivia?json
     * Throws a [CachedException] if no cached data is present
     */
    suspend fun cacheNumberTrivia(itemToCache: NumberTriviaDto)
}