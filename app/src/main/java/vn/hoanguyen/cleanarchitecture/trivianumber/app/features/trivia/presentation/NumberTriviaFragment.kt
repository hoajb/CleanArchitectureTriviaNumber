package vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.presentation

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import vn.hoanguyen.cleanarchitecture.trivianumber.R
import vn.hoanguyen.cleanarchitecture.trivianumber.core.extension.hideKeyboard
import vn.hoanguyen.cleanarchitecture.trivianumber.core.extension.onDone
import vn.hoanguyen.cleanarchitecture.trivianumber.core.extension.safeClick
import vn.hoanguyen.cleanarchitecture.trivianumber.core.extension.viewBinding
import vn.hoanguyen.cleanarchitecture.trivianumber.core.platform.BaseFragment
import vn.hoanguyen.cleanarchitecture.trivianumber.databinding.FragmentNumberTriviaBinding

/**
 * Created by Hoa Nguyen on Aug 14 2021.
 */
@AndroidEntryPoint
class NumberTriviaFragment : BaseFragment(R.layout.fragment_number_trivia) {
    override val viewModel: NumberTriviaViewModel by viewModels()

    private val viewBinding by viewBinding(FragmentNumberTriviaBinding::bind)

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupView()
    }

    private fun setupView() {
        viewBinding?.btSubmit?.isEnabled = false
        viewBinding?.edNumberInput?.doOnTextChanged { text, start, before, count ->
            viewBinding?.btSubmit?.isEnabled = text.isNullOrEmpty().not()
        }

        viewBinding?.btSubmit?.safeClick {
            viewBinding?.edNumberInput?.hideKeyboard()
            val number = viewBinding?.edNumberInput?.text.toString().toInt()
            viewModel.actionGetConcreteNumberTrivia(number)
            viewBinding?.edNumberInput?.text?.clear()
        }

        viewBinding?.edNumberInput?.onDone { viewBinding?.btSubmit?.performClick() }
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenResumed {
            viewModel.isLoading.collectLatest { isLoading ->
                viewBinding?.progressCircular?.visibility =
                    if (isLoading) View.VISIBLE else View.GONE
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.notify.collectLatest { message ->
                if (message.isNotEmpty()) {
                    notify(message)
                    viewBinding?.txtContent?.text = message
                }
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.numberTrivia.collectLatest { data ->
                viewBinding?.txtContent?.text = data.text
            }
        }
    }

}