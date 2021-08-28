package vn.hoanguyen.cleanarchitecture.trivianumber

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.presentation.NumberTriviaActivity
import vn.hoanguyen.cleanarchitecture.trivianumber.core.extension.safeClick
import vn.hoanguyen.cleanarchitecture.trivianumber.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.buttonByInput.safeClick {
            Intent(this, NumberTriviaActivity::class.java).apply {
                startActivity(this)
            }
        }
    }
}