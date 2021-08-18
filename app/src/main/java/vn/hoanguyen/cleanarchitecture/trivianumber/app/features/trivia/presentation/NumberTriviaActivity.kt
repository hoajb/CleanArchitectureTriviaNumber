package vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.presentation

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import vn.hoanguyen.cleanarchitecture.trivianumber.core.platform.BaseFragment
import vn.hoanguyen.cleanarchitecture.trivianumber.core.platform.BaseSingleFragmentActivity

/**
 * Created by Hoa Nguyen on Aug 14 2021.
 */
@AndroidEntryPoint
class NumberTriviaActivity : BaseSingleFragmentActivity() {
    override fun createFragment(): BaseFragment = NumberTriviaFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar("Number Trivia by Input")
    }
}