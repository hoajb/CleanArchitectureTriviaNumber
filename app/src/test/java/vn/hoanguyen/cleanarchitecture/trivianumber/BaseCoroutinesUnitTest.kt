package vn.hoanguyen.cleanarchitecture.trivianumber

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import org.junit.Rule

/**
 * Created by Hoa Nguyen on Sep 10 2021.
 */
abstract class BaseCoroutinesUnitTest {
    @get:Rule
    var rule = CoroutineTestRule()

    fun <T> Flow<T>.test(): TestFlow<T> {
        val testFlow = TestFlow(this)
        testFlow.launchIn(rule)
        return testFlow
    }
}