package vn.hoanguyen.cleanarchitecture.trivianumber.core.platform

import app.cash.turbine.test
import io.kotest.matchers.shouldBe
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import vn.hoanguyen.cleanarchitecture.trivianumber.CoroutineTestRule
import kotlin.time.ExperimentalTime

/**
 * Created by Hoa Nguyen on Aug 28 2021.
 */
@ExperimentalCoroutinesApi
@ExperimentalTime
internal class BaseViewModelTest {
    private lateinit var viewModel: TestableBaseViewModel

    @get:Rule
    var rule = CoroutineTestRule()

    @BeforeEach
    fun setup() {
        viewModel = TestableBaseViewModel(rule.testDispatcherProvider)
    }

    @Nested
    inner class Loading {

        @Test
        fun `should init false for loading flow`() = runBlockingTest {
            viewModel.isLoading.test {
                awaitItem() shouldBe false
            }
        }

        @Test
        fun `should send true-false for loading true-false flow`() = runBlockingTest {
            viewModel.testableLoading(true)
            viewModel.isLoading.test {
                awaitItem() shouldBe true
            }
            viewModel.testableLoading(false)
            viewModel.isLoading.test {
                awaitItem() shouldBe false
            }
        }
    }

    @Nested
    inner class Notify {

        @Test
        fun `should init empty for notify flow`() = runBlockingTest {
            viewModel.notify.test {
                awaitItem() shouldBe ""
            }
        }

        @Test
        fun `should send value for notify flow`() = runBlockingTest {
            viewModel.testableNotify("my_mess")
            viewModel.notify.test {
                awaitItem() shouldBe "my_mess"
            }
            viewModel.testableNotify("another_mess")
            viewModel.notify.test {
                awaitItem() shouldBe "another_mess"
            }
        }
    }

    @Nested
    inner class Execute {

        @Test
        fun `should call block and handle loading`() = runBlockingTest {
            //Arrange
            val mock = spyk(viewModel)
            val isLoadingValue = true
            val handleErrorCall: (Throwable) -> Unit = mockk(relaxed = true)
            val blockCall: suspend () -> Unit = mockk(relaxed = true)
            //Act and Assert
            mock.isLoading.test {
                awaitItem() shouldBe false // init
                mock.execute(
                    isLoading = isLoadingValue,
                    handleError = handleErrorCall,
                    block = blockCall
                )

                awaitItem() shouldBe true //show
                coVerify { blockCall.invoke() }
                awaitItem() shouldBe false // hide
            }
        }

        @Test
        fun `should call block and handle loading and handle default error`() = runBlockingTest {
            //Arrange
            val mock = spyk(viewModel)
            val isLoadingValue = true
            val blockCall: suspend () -> Unit = mockk(relaxed = true)
            val errorMessage = "nothing"
            val error = Throwable(errorMessage)
            coEvery { blockCall.invoke() } throws error
            //Act and Assert
            mock.isLoading.test {
                awaitItem() shouldBe false // init
                mock.execute(
                    isLoading = isLoadingValue,
                    block = blockCall
                )

                awaitItem() shouldBe true //show
                coVerify { blockCall.invoke() }
                mock.notify.test {
                    awaitItem() shouldBe errorMessage
                }
                awaitItem() shouldBe false // hide
            }
        }

        @Test
        fun `should call block and handle loading and handle error`() = runBlockingTest {
            //Arrange
            val mock = spyk(viewModel)
            val isLoadingValue = true
            val handleErrorCall: (Throwable) -> Unit = mockk(relaxed = true)
            val blockCall: suspend () -> Unit = mockk(relaxed = true)
            val error = Throwable()
            coEvery { blockCall.invoke() } throws error
            //Act and Assert
            mock.isLoading.test {
                awaitItem() shouldBe false // init
                mock.execute(
                    isLoading = isLoadingValue,
                    handleError = handleErrorCall,
                    block = blockCall
                )

                awaitItem() shouldBe true //show
                coVerify { blockCall.invoke() }
                verify { handleErrorCall.invoke(error) }
                awaitItem() shouldBe false // hide
            }
        }
    }

    @Nested
    inner class HandleErrorDefault {
        @Test
        fun `should call notify with error message`() = runBlockingTest {
            //Arrange
            val mock = spyk(viewModel)
            val errorMessage = "nothing"
            val error = Throwable(errorMessage)
            //Act and Assert
            mock.notify.test {
                awaitItem() shouldBe ""
                mock.handleErrorDefault(error)
                awaitItem() shouldBe errorMessage
            }
        }

        @Test
        fun `should call notify with default message when Throwable in empty message`() = runBlockingTest {
            //Arrange
            val mock = spyk(viewModel)
            val errorMessage = ""
            val error = Throwable(errorMessage)
            //Act and Assert
            mock.notify.test {
                awaitItem() shouldBe ""
                mock.handleErrorDefault(error)
                awaitItem() shouldBe "Unknown"
            }
        }
    }

//    @Test
//    fun iFailureHandler() {
//    }
}