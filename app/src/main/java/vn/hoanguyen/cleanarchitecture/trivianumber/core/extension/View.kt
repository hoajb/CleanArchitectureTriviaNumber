package vn.hoanguyen.cleanarchitecture.trivianumber.core.extension

import android.view.View

/**
 * Created by Hoa Nguyen on Aug 15 2021.
 */
class DebounceOnClickListener(
    private val interval: Long,
    private val listenerBlock: (View) -> Unit
) : View.OnClickListener {
    private var lastClickTime = 0L

    override fun onClick(v: View) {
        val time = System.currentTimeMillis()
        if (time - lastClickTime >= interval) {
            lastClickTime = time
            listenerBlock(v)
        }
    }
}

fun View.safeClick(debounceInterval: Long = 500, listenerBlock: (View) -> Unit) =
    setOnClickListener(DebounceOnClickListener(debounceInterval, listenerBlock))