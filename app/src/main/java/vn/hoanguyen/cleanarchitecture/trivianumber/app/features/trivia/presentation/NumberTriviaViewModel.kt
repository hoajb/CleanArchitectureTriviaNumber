package vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.presentation

import android.app.Application
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import vn.hoanguyen.cleanarchitecture.trivianumber.core.platform.BaseViewModel

/**
 * Created by Hoa Nguyen on Aug 14 2021.
 */
@HiltViewModel
class NumberTriviaViewModel(@ApplicationContext application: Application) : BaseViewModel(application) {
}