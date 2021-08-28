package vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.data.repositories

import io.mockk.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.*
import vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.data.datasources.NumberTriviaLocalDataSource
import vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.data.datasources.NumberTriviaRemoteDataSource
import vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.data.models.NumberTriviaDto
import vn.hoanguyen.cleanarchitecture.trivianumber.core.exception.CachedException
import vn.hoanguyen.cleanarchitecture.trivianumber.core.exception.IFailure
import vn.hoanguyen.cleanarchitecture.trivianumber.core.functional.Either
import vn.hoanguyen.cleanarchitecture.trivianumber.core.platform.NetworkInfo
import java.io.IOException

/**
 * Created by Hoa Nguyen on Aug 08 2021.
 */
@ExperimentalCoroutinesApi
internal class NumberTriviaRepositoryImplTest {

    private lateinit var numberTriviaRemoteDataSource: NumberTriviaRemoteDataSource
    private lateinit var numberTriviaLocalDataSource: NumberTriviaLocalDataSource
    private lateinit var networkInfo: NetworkInfo
    private lateinit var repository: NumberTriviaRepositoryImpl

    @BeforeEach
    fun setUp() {
        numberTriviaRemoteDataSource = mockk(relaxed = true)
        numberTriviaLocalDataSource = mockk(relaxed = true)
        networkInfo = mockk(relaxed = true)

        repository = NumberTriviaRepositoryImpl(
            numberTriviaRemoteDataSource,
            numberTriviaLocalDataSource,
            networkInfo
        )
    }

    @AfterEach
    fun tearDown() {
    }

    @Nested
    inner class GetConcreteNumberTrivia {
        private val tNumber = 1

        @Test
        fun `should check if the device is online`() = runBlockingTest {
            //Arrange
            every { networkInfo.isNetworkAvailable() } returns true
            //Act
            repository.getConcreteNumberTrivia(tNumber)
            //Assert

            verify { networkInfo.isNetworkAvailable() }
        }
    }

    @Nested
    @DisplayName("Device is Online")
    inner class DeviceOnline {
        @BeforeEach
        fun setUp() {
            every { networkInfo.isNetworkAvailable() } returns true
        }

        private val tNumber = 1
        private val tNumberTriviaDto = NumberTriviaDto(
            text = "Number Test",
            number = tNumber.toDouble(),
            found = true,
            type = "trivia"
        )

        @Test
        fun `should return remote data when call remote data source success`() = runBlockingTest {
            //Arrange
            coEvery { numberTriviaRemoteDataSource.getConcreteNumberTrivia(tNumber) } returns tNumberTriviaDto

            //Act
            val result = repository.getConcreteNumberTrivia(tNumber)
            //Assert
            assertEquals(Either.Right(tNumberTriviaDto.toNumberTrivia()), result)
        }

        @Test
        fun `should cache remote data when call remote data source success`() = runBlockingTest {
            //Arrange
            coEvery { numberTriviaRemoteDataSource.getConcreteNumberTrivia(tNumber) } returns tNumberTriviaDto

            //Act
            val result = repository.getConcreteNumberTrivia(tNumber)
            //Assert
            coVerify { numberTriviaLocalDataSource.cacheNumberTrivia(tNumberTriviaDto) }
        }

        @Test
        fun `should return Exception data when call remote data source error`() = runBlockingTest {
            //Arrange
            val exception = IOException("error server")
            coEvery { numberTriviaRemoteDataSource.getConcreteNumberTrivia(tNumber) } throws exception

            //Act
            val result = repository.getConcreteNumberTrivia(tNumber)
            //Assert
            assertEquals(
                Either.Left(IFailure.ServerFailure(exception.message.orEmpty())),
                result
            )
        }
    }

    @Nested
    @DisplayName("Device is Offline")
    inner class DeviceOffline {
        @BeforeEach
        fun setUp() {
            every { networkInfo.isNetworkAvailable() } returns false
        }

        private val tNumber = 1
        private val tNumberTriviaDto = NumberTriviaDto(
            text = "Number Test",
            number = tNumber.toDouble(),
            found = true,
            type = "trivia"
        )

        @Test
        fun `should return locally cached when data cached is present`() = runBlockingTest {
            //Arrange
            coEvery { numberTriviaLocalDataSource.getConcreteNumberTrivia(tNumber) } returns tNumberTriviaDto
            //Act
            val result = repository.getConcreteNumberTrivia(tNumber)
            //Assert
            coVerify { numberTriviaLocalDataSource.getConcreteNumberTrivia(tNumber) }
            assertEquals(Either.Right(tNumberTriviaDto.toNumberTrivia()), result)
        }

        @Test
        fun `should return failure cached when data cached is NOT present`() = runBlockingTest {
            //Arrange
            coEvery { numberTriviaLocalDataSource.getConcreteNumberTrivia(tNumber) } throws CachedException(
                "not found"
            )
            //Act
            val result = repository.getConcreteNumberTrivia(tNumber)
            //Assert
            coVerify { numberTriviaLocalDataSource.getConcreteNumberTrivia(tNumber) }
            assertEquals(Either.Left(IFailure.CacheFailure), result)
        }

    }


}