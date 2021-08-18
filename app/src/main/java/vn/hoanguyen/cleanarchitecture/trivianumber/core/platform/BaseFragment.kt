package vn.hoanguyen.cleanarchitecture.trivianumber.core.platform

import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import vn.hoanguyen.cleanarchitecture.trivianumber.R

/**
 * Created by Hoa Nguyen on Aug 14 2021.
 */
abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    protected abstract val viewModel: BaseViewModel

    internal fun notify(message: String) {
        Timber.d("[notify]: message")
        view?.let { Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show() }
    }

    internal fun notifyWithAction(
        message: String,
        actionText: String,
        action: () -> Unit
    ) {
        view?.let {
            val snackBar = Snackbar.make(it, message, Snackbar.LENGTH_INDEFINITE)
            snackBar.setAction(actionText) { action.invoke() }
            snackBar.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.teal_700))
            snackBar.show()
        }
    }
}