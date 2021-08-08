package vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.domain.repositories

import vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.domain.entities.NumberTrivia
import vn.hoanguyen.cleanarchitecture.trivianumber.core.exception.IFailure
import vn.hoanguyen.cleanarchitecture.trivianumber.core.functional.Either

/**
 * Created by Hoa Nguyen on Aug 06 2021.
 */
interface NumberTriviaRepository {
    suspend fun getConcreteNumberTrivia(number: Double): Either<IFailure, NumberTrivia>
    suspend fun getRandomNumberTrivia(): Either<IFailure, NumberTrivia>
}