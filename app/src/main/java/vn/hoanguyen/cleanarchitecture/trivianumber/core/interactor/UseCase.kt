package vn.hoanguyen.cleanarchitecture.trivianumber.core.interactor

/**
 * Created by Hoa Nguyen on Aug 07 2021.
 */
import kotlinx.coroutines.*
import vn.hoanguyen.cleanarchitecture.trivianumber.core.exception.IFailure
import vn.hoanguyen.cleanarchitecture.trivianumber.core.functional.Either

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This abstraction represents an execution unit for different use cases (this means that any use
 * case in the application should implement this contract).
 *
 * By convention each [UseCase] implementation will execute its job in a background thread
 * (kotlin coroutine) and will post the result in the UI thread.
 */
abstract class UseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Either<IFailure, Type>

    operator fun invoke(
        params: Params,
        scope: CoroutineScope = GlobalScope,
        onResult: (Either<IFailure, Type>) -> Unit = {}
    ) {
        scope.launch(Dispatchers.Main) {
            val deferred = async(Dispatchers.IO) {
                run(params)
            }
            onResult(deferred.await())
        }
    }

    class None
}