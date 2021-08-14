package vn.hoanguyen.cleanarchitecture.trivianumber.core.platform

import android.os.Bundle
import vn.hoanguyen.cleanarchitecture.trivianumber.R
import vn.hoanguyen.cleanarchitecture.trivianumber.core.extension.inTransaction

/**
 * Created by Hoa Nguyen on Aug 14 2021.
 */
abstract class BaseSingleFragmentActivity :
    BaseActivity(R.layout.activity_single_fragment) {

    protected abstract fun createFragment(): BaseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addFragment(savedInstanceState)
    }

    private fun addFragment(savedInstanceState: Bundle?) =
        savedInstanceState ?: supportFragmentManager.inTransaction {
            add(
                R.id.content_frame,
                createFragment()
            )
        }
}