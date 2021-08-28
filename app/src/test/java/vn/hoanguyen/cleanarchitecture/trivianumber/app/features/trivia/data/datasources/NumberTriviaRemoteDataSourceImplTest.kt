package vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.data.datasources

import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * Created by Hoa Nguyen on Aug 11 2021.
 */
@ExperimentalCoroutinesApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // for using [@BeforeAll] non-static in Kotlin
internal class NumberTriviaRemoteDataSourceImplTest {
    private lateinit var api: NumberTriviaRestAPI
    private lateinit var dataSource: NumberTriviaRemoteDataSourceImpl

    @BeforeAll
    fun setUp() {
        api = mockk(relaxed = true)
        dataSource = NumberTriviaRemoteDataSourceImpl(api)
    }

    @Test
    fun getConcreteNumberTrivia() = runBlockingTest {
        val tNumber = 19
        dataSource.getConcreteNumberTrivia(tNumber)
        coVerify { api.getConcreteNumberTrivia(tNumber) }
    }

    @Test
    fun getRandomNumberTrivia() = runBlockingTest {
        dataSource.getRandomNumberTrivia()
        coVerify { api.getRandomNumberTrivia() }
    }
}