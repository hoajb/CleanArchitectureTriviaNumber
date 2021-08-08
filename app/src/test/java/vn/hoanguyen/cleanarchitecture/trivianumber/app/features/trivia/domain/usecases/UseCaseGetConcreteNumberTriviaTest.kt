package vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.domain.usecases

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.domain.entities.NumberTrivia
import vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.domain.repositories.NumberTriviaRepository
import vn.hoanguyen.cleanarchitecture.trivianumber.core.functional.Either

/**
 * Created by Hoa Nguyen on Aug 06 2021.
 */
@ExperimentalCoroutinesApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // for using [@BeforeAll] non-static in Kotlin
internal class UseCaseGetConcreteNumberTriviaTest {

    private lateinit var repository: NumberTriviaRepository
    private lateinit var useCase: UseCaseGetConcreteNumberTrivia

    @BeforeAll
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = UseCaseGetConcreteNumberTrivia(repository)
    }

    @Test
    fun `should return numberTrivia success from the repository`() =
        runBlockingTest {
            //Arrange
            val number = 1
            val numberTrivia = NumberTrivia(
                text = "SomeText",
                number = number,
                found = true,
                type = "trivia"
            )

            coEvery { repository.getConcreteNumberTrivia(number) } returns Either.Right(
                numberTrivia
            )
            //Act
            val result = useCase.run(UseCaseGetConcreteNumberTrivia.Params(number))
            //Assert
            coVerify { repository.getConcreteNumberTrivia(number) }
            assert(result.isRight)
            assertEquals(Either.Right(numberTrivia), result)

            confirmVerified(repository) // nothing else was called
        }

    @Test
    fun `should return Failure from the repository`() =
        runBlockingTest {
            //Arrange
            val number = 1
            val failure = FailureNumberTriviaNotFound()
            coEvery { repository.getConcreteNumberTrivia(number) } returns Either.Left(
                failure
            )
            //Act
            val result = useCase.run(UseCaseGetConcreteNumberTrivia.Params(number))
            //Assert
            coVerify { repository.getConcreteNumberTrivia(number) }
            assert(result.isLeft)
            assertEquals(Either.Left(failure), result)

            confirmVerified(repository)  // nothing else was called
        }

}