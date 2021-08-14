package vn.hoanguyen.cleanarchitecture.trivianumber.core.platform

import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import vn.hoanguyen.cleanarchitecture.trivianumber.R

/**
 * Created by Hoa Nguyen on Aug 14 2021.
 */
abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    protected abstract val viewModel: BaseViewModel

    internal fun notify(@StringRes message: Int) {
        view?.let { Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show() }
    }

    internal fun notifyWithAction(
        @StringRes message: Int,
        @StringRes actionText: Int,
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