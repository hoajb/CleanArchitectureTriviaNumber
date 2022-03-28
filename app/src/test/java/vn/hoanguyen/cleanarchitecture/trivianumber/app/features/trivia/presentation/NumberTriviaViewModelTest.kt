package vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.presentation

import app.cash.turbine.test
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import vn.hoanguyen.cleanarchitecture.trivianumber.BaseCoroutinesUnitTest
import vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.domain.entities.NumberTrivia
import vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.domain.usecases.FailureNumberTriviaNotFound
import vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.domain.usecases.UseCaseGetConcreteNumberTrivia
import vn.hoanguyen.cleanarchitecture.trivianumber.core.exception.IFailure
import vn.hoanguyen.cleanarchitecture.trivianumber.core.functional.Either

/**
 * Created by Hoa Nguyen on Sep 10 2021.
 */
internal class NumberTriviaViewModelTest : BaseCoroutinesUnitTest() {
    private lateinit var viewModel: NumberTriviaViewModel
    private val useCaseGetConcreteNumberTrivia: UseCaseGetConcreteNumberTrivia =
        mockk(relaxed = true)

    @BeforeEach
    fun setUp() {
        viewModel = NumberTriviaViewModel(
            useCaseGetConcreteNumberTrivia = useCaseGetConcreteNumberTrivia,
            dispatcherProvider = rule.testDispatcherProvider
        )
    }

    @AfterEach
    fun tearDown() {
    }

    @Nested
    inner class ActionGetConcreteNumberTrivia {
        @Test
        fun `should call notify not found error for FailureNumberTriviaNotFound failure`() =
            runBlockingTest {
                //Arrange
                val number = 1
                val param = UseCaseGetConcreteNumberTrivia.Params(number)
                coEvery { useCaseGetConcreteNumberTrivia.run(param) } returns Either.Left(
                    FailureNumberTriviaNotFound()
                )
                //Act
                viewModel.actionGetConcreteNumberTrivia(number)
                //Assert
                coVerify { useCaseGetConcreteNumberTrivia.run(param) }

                viewModel.notify.test {
                    awaitItem() shouldBe "[Server]: Number Trivia not found"
                }
            }

        @Test
        fun `should call notify not found error for IFailure - CacheFailure`() = runBlockingTest {
            //Arrange
            val number = 1
            val param = UseCaseGetConcreteNumberTrivia.Params(number)
            coEvery { useCaseGetConcreteNumberTrivia.run(param) } returns Either.Left(
                IFailure.CacheFailure
            )
            //Act
            viewModel.actionGetConcreteNumberTrivia(number)
            //Assert
            coVerify { useCaseGetConcreteNumberTrivia.run(param) }

            viewModel.notify.test {
                awaitItem() shouldBe "[No Internet Connection] : No cached found"
            }
        }

        @Test
        fun `should call notify not found error for other IFailure`() = runBlockingTest {
            //Arrange
            val number = 1
            val param = UseCaseGetConcreteNumberTrivia.Params(number)
            coEvery { useCaseGetConcreteNumberTrivia.run(param) } returns Either.Left(
                IFailure.TestIFailure()
            )
            //Act
            viewModel.actionGetConcreteNumberTrivia(number)
            //Assert
            coVerify { useCaseGetConcreteNumberTrivia.run(param) }

            viewModel.notify.test {
                awaitItem() shouldBe "[Unknown Error]"
            }
        }

        @Test
        fun `should emit data number trivia for success`() = runBlockingTest {
            //Arrange

            val number = 1
            val numberTrivia = NumberTrivia(
                text = "SomeText",
                number = number.toDouble(),
                found = true,
                type = "trivia"
            )
            val param = UseCaseGetConcreteNumberTrivia.Params(number)
            coEvery { useCaseGetConcreteNumberTrivia.run(param) } returns Either.Right(
                numberTrivia
            )
            //Act
            viewModel.actionGetConcreteNumberTrivia(number)
            //Assert
            coVerify { useCaseGetConcreteNumberTrivia.run(param) }

            viewModel.numberTrivia.test {
                awaitItem() shouldBe numberTrivia
            }
        }
    }
}