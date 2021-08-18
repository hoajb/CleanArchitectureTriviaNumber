package vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import vn.hoanguyen.cleanarchitecture.trivianumber.core.platform.BaseViewModel
import javax.inject.Inject

/**
 * Created by Hoa Nguyen on Aug 14 2021.
 */
@HiltViewModel
class NumberTriviaViewModel @Inject constructor() :
    BaseViewModel() {
    init {
        viewModelScope.launch {
            execute {
                delay(2000)
                notify("Loading Done")
            }
        }
    }
}