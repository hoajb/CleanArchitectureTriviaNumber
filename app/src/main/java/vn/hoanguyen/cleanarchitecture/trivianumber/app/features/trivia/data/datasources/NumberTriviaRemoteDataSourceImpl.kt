package vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.data.datasources

import vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.data.models.NumberTriviaDto
import javax.inject.Inject

/**
 * Created by Hoa Nguyen on Aug 11 2021.
 */
class NumberTriviaRemoteDataSourceImpl @Inject constructor(
    private val api: NumberTriviaRestAPI
) : NumberTriviaRemoteDataSource {
    override suspend fun getConcreteNumberTrivia(number: Int): NumberTriviaDto =
        api.getConcreteNumberTrivia(number)

    override suspend fun getRandomNumberTrivia(): NumberTriviaDto =
        api.getRandomNumberTrivia()
}