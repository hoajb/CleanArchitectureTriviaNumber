package vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.data.datasources

import android.content.SharedPreferences
import com.google.gson.Gson
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.*
import vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.data.models.NumberTriviaDto
import vn.hoanguyen.cleanarchitecture.trivianumber.core.exception.CachedException
import vn.hoanguyen.cleanarchitecture.trivianumber.core.extension.empty

/**
 * Created by Hoa Nguyen on Aug 09 2021.
 */
@ExperimentalCoroutinesApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // for using [@BeforeAll] non-static in Kotlin
internal class NumberTriviaLocalSharePreferencesDataSourceImplTest {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var dataSource: NumberTriviaLocalSharePreferencesDataSourceImpl
    private lateinit var gson: Gson

    @BeforeAll
    fun setUp() {
        sharedPreferences = mockk(relaxed = true)
        gson = mockk(relaxed = true)
        dataSource = NumberTriviaLocalSharePreferencesDataSourceImpl(sharedPreferences, gson)
    }

    @Nested
    inner class GetConcreteNumberTrivia {
        @Test
        fun `should return local number trivia when it is present in share preferences`() =
            runBlockingTest {
                //Arrange
                val tNumber = 1
                val tNumberTriviaDto = NumberTriviaDto(
                    text = "Number Test",
                    number = tNumber.toDouble(),
                    found = true,
                    type = "trivia"
                )

                val jsonString = tNumberTriviaDto.toString()

                every { sharedPreferences.getString(any(), any()) } returns jsonString
                every {
                    gson.fromJson(
                        jsonString,
                        NumberTriviaDto::class.java
                    )
                } returns tNumberTriviaDto
                //Act
                val model = dataSource.getConcreteNumberTrivia(tNumber)
                //Assert
                verify {
                    sharedPreferences.getString(
                        NumberTriviaLocalSharePreferencesDataSourceImpl.NUMBER_TRIVIA_LOCAL_PREF_KEY,
                        any()
                    )
                }

                verify {
                    gson.fromJson(
                        jsonString,
                        NumberTriviaDto::class.java
                    )
                }

                assertEquals(tNumberTriviaDto, model)
            }

        @Test
        fun `should throws CachedException when it is NOT present in share preferences`() {
            //Arrange
            val tNumber = 1

            every { sharedPreferences.getString(any(), any()) } returns String.empty()
            every {
                gson.fromJson(
                    String.empty(),
                    NumberTriviaDto::class.java
                )
            } returns null
            //Act

            //Assert
            Assertions.assertThrows(CachedException::class.java) {
                runBlockingTest { dataSource.getConcreteNumberTrivia(tNumber) }
            }
        }
    }

    @Nested
    inner class CacheNumberTrivia {
        @Test
        fun `should call share preferences to save number trivia`() =
            runBlockingTest {
                //Arrange
                val tNumber = 1
                val tNumberTriviaDto = NumberTriviaDto(
                    text = "Number Test",
                    number = tNumber.toDouble(),
                    found = true,
                    type = "trivia"
                )

                val jsonString = tNumberTriviaDto.toString()

                every {
                    gson.toJson(tNumberTriviaDto)
                } returns jsonString
                //Act
                val model = dataSource.cacheNumberTrivia(tNumberTriviaDto)
                //Assert
                verify {
                    sharedPreferences.edit().putString(
                        NumberTriviaLocalSharePreferencesDataSourceImpl.NUMBER_TRIVIA_LOCAL_PREF_KEY,
                        jsonString
                    ).apply()
                }
            }
    }
}