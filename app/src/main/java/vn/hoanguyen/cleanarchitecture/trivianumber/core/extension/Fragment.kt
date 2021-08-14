package vn.hoanguyen.cleanarchitecture.trivianumber.core.extension

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import vn.hoanguyen.cleanarchitecture.trivianumber.core.platform.BaseFragment

/**
 * Created by Hoa Nguyen on Aug 14 2021.
 */

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
    beginTransaction().func().commit()

fun BaseFragment.close() = parentFragmentManager.popBackStack()