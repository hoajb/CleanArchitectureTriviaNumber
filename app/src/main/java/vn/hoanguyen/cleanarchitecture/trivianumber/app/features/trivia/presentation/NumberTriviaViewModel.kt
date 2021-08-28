package vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.domain.entities.NumberTrivia
import vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.domain.usecases.UseCaseGetConcreteNumberTrivia
import vn.hoanguyen.cleanarchitecture.trivianumber.core.exception.IFailure
import vn.hoanguyen.cleanarchitecture.trivianumber.core.interactor.DispatcherProvider
import vn.hoanguyen.cleanarchitecture.trivianumber.core.platform.BaseViewModel
import javax.inject.Inject

/**
 * Created by Hoa Nguyen on Aug 14 2021.
 */
@HiltViewModel
class NumberTriviaViewModel @Inject constructor(
    private val useCaseGetConcreteNumberTrivia: UseCaseGetConcreteNumberTrivia,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {
    private val _numberTrivia = MutableSharedFlow<NumberTrivia>()
    val numberTrivia = _numberTrivia.asSharedFlow()

    fun actionGetConcreteNumberTrivia(number: Int) = viewModelScope.launch {
        execute {
            val param = UseCaseGetConcreteNumberTrivia.Params(number)// only Decimal
            useCaseGetConcreteNumberTrivia.run(param)
                .fold(
                    { iFailure ->
                        val handled = iFailureHandler(iFailure)
                        if (handled.not()) {
                            if (iFailure == IFailure.CacheFailure) {
                                notify("[No Internet Connection] : No cached found")
                            } else {
                                notify("[Unknown Error]")
                            }
                        }
                    }, { data ->
                        viewModelScope.launch {
                            _numberTrivia.emit(data)
                        }
                    }
                )
        }
    }
}