package vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.domain.entities.NumberTrivia
import vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.domain.usecases.FailureNumberTriviaNotFound
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
    private val dispatcherProvider: DispatcherProvider,
) : BaseViewModel(dispatcherProvider) {
    private val _numberTrivia = MutableStateFlow<NumberTrivia>(NumberTrivia.empty())
    val numberTrivia = _numberTrivia.asStateFlow()

    fun actionGetConcreteNumberTrivia(number: Int) =
        viewModelScope.launch(dispatcherProvider.io()) {
            execute {
                val param = UseCaseGetConcreteNumberTrivia.Params(number)// only Decimal
                useCaseGetConcreteNumberTrivia.run(param)
                    .fold(
                        { iFailure ->
                            when (iFailure) {
                                is IFailure.CacheFailure -> {
                                    notify("[No Internet Connection] : No cached found")
                                }
                                is FailureNumberTriviaNotFound -> {
                                    notify("[Server]: Number Trivia not found")
                                }
                                else -> {
                                    iFailureHandler(iFailure)
                                }
                            }
                        }, { data ->
                            launch(dispatcherProvider.main()) {
                                _numberTrivia.emit(data)
                            }
                        }
                    )
            }
        }
}