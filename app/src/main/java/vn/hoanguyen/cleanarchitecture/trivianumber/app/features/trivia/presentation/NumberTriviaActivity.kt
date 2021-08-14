package vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.presentation

import vn.hoanguyen.cleanarchitecture.trivianumber.core.platform.BaseFragment
import vn.hoanguyen.cleanarchitecture.trivianumber.core.platform.BaseSingleFragmentActivity

/**
 * Created by Hoa Nguyen on Aug 14 2021.
 */
class NumberTriviaActivity : BaseSingleFragmentActivity() {
    override fun createFragment(): BaseFragment = NumberTriviaFragment()
}