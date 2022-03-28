package vn.hoanguyen.cleanarchitecture.trivianumber

import kotlinx.coroutines.flow.AbstractFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect

/**
 * Created by Hoa Nguyen on Sep 14 2021.
 */
class TestFlow<T>(
    private val source: Flow<T>
) : AbstractFlow<T>() {

    private val _values: MutableList<T> = mutableListOf()
    val values: List<T> = _values

    var exception: Throwable? = null
        private set

    var completed: Boolean = false
        private set

    override suspend fun collectSafely(collector: FlowCollector<T>) {
        try {
            source.collect {
                _values.add(it)
                collector.emit(it)
            }
        } catch (throwable: Throwable) {
            exception = throwable
        } finally {
            completed = true
        }
    }

    fun clear() {
        _values.clear()
        exception = null
        completed = false
    }
}