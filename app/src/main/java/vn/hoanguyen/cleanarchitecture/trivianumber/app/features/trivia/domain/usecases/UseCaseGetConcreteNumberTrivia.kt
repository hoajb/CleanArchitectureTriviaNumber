package vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.domain.usecases

import vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.domain.entities.NumberTrivia
import vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.domain.repositories.NumberTriviaRepository
import vn.hoanguyen.cleanarchitecture.trivianumber.core.exception.IFailure
import vn.hoanguyen.cleanarchitecture.trivianumber.core.functional.Either
import vn.hoanguyen.cleanarchitecture.trivianumber.core.interactor.UseCase

/**
 * Created by Hoa Nguyen on Aug 06 2021.
 */
class UseCaseGetConcreteNumberTrivia (
    private val numberTriviaRepository: NumberTriviaRepository,
) : UseCase<NumberTrivia, UseCaseGetConcreteNumberTrivia.Params>() {
    data class Params(val number: Double)

    override suspend fun run(params: Params): Either<IFailure, NumberTrivia> {
        return numberTriviaRepository.getConcreteNumberTrivia(params.number)
    }
}