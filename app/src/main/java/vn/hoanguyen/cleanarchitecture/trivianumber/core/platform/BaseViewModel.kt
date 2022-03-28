package vn.hoanguyen.cleanarchitecture.trivianumber.core.platform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import vn.hoanguyen.cleanarchitecture.trivianumber.core.exception.IFailure
import vn.hoanguyen.cleanarchitecture.trivianumber.core.interactor.DispatcherProvider

/**
 * Created by Hoa Nguyen on Aug 14 2021.
 */
abstract class BaseViewModel(private val dispatcherProvider: DispatcherProvider) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _notify = MutableStateFlow("")
    val notify: StateFlow<String> = _notify

    protected fun scopeLaunch(body: suspend () -> Unit) {
        viewModelScope.launch(dispatcherProvider.io()) {
            body.invoke()
        }
    }

    protected fun loading(isLoading: Boolean) {
        Timber.i("loading $isLoading")
        scopeLaunch {
            _isLoading.emit(isLoading)
        }
    }

    protected fun notify(message: String) {
        scopeLaunch {
            _notify.emit(message)
        }
    }

    suspend fun execute(
        isLoading: Boolean = true,
        handleError: ((t: Throwable) -> Unit) = ::handleErrorDefault,
        block: suspend () -> Unit
    ) {
        if (isLoading)
            loading(true)
        try {
            block()
        } catch (t: Throwable) {
            handleError.invoke(t)
        }
        if (isLoading)
            loading(false)
    }

    open fun handleErrorDefault(t: Throwable) {
        Timber.i("Error: ${t.message}")
        notify(t.message.orEmpty().ifEmpty { "Unknown" })
    }

    protected fun iFailureHandler(iFailure: IFailure) {
        return when (iFailure) {
            is IFailure.NetworkConnectionFailure -> {
                notify("No Internet Connection!!!")
            }

            is IFailure.ServerFailure -> {
                notify("[Server Error] : ${iFailure.messageError}")
            }
            else -> {
                notify("[Unknown Error]")
            }
        }
    }
}