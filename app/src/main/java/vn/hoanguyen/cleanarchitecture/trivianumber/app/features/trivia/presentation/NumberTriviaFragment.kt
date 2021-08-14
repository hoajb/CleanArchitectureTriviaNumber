package vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.presentation

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import vn.hoanguyen.cleanarchitecture.trivianumber.R
import vn.hoanguyen.cleanarchitecture.trivianumber.core.platform.BaseFragment
import vn.hoanguyen.cleanarchitecture.trivianumber.core.platform.BaseViewModel

/**
 * Created by Hoa Nguyen on Aug 14 2021.
 */
@AndroidEntryPoint
class NumberTriviaFragment : BaseFragment(R.layout.fragment_number_trivia) {
    override val viewModel: BaseViewModel by viewModels()
}