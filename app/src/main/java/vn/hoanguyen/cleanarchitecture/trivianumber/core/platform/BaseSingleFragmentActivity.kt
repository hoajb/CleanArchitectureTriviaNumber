package vn.hoanguyen.cleanarchitecture.trivianumber.core.platform

import android.os.Bundle
import android.view.MenuItem
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

    protected fun setupToolbar(title: String) {
        supportActionBar?.apply {
            setTitle(title.ifEmpty { getString(R.string.app_name) })
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}