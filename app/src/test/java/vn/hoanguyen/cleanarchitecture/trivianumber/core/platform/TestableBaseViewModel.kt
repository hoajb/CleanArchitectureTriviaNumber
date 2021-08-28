package vn.hoanguyen.cleanarchitecture.trivianumber.core.platform

import vn.hoanguyen.cleanarchitecture.trivianumber.core.interactor.DispatcherProvider

/**
 * Created by Hoa Nguyen on Aug 28 2021.
 */
class TestableBaseViewModel(private val dispatcherProvider: DispatcherProvider) :
    BaseViewModel(dispatcherProvider) {

    fun testableLoading(isLoading: Boolean) {
        super.loading(isLoading)
    }

    fun testableNotify(message: String) {
        super.notify(message)
    }
}