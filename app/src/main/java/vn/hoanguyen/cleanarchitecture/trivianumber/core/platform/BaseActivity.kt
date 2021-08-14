package vn.hoanguyen.cleanarchitecture.trivianumber.core.platform

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by Hoa Nguyen on Aug 14 2021.
 */
abstract class BaseActivity(@LayoutRes contentLayoutId: Int) : AppCompatActivity(contentLayoutId) {
}