package vn.hoanguyen.cleanarchitecture.trivianumber.core.extension

import android.view.inputmethod.EditorInfo
import android.widget.EditText

/**
 * Created by Hoa Nguyen on Aug 28 2021.
 */
fun EditText.onDone(callback: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            callback.invoke()
            return@setOnEditorActionListener true
        }
        false
    }
}