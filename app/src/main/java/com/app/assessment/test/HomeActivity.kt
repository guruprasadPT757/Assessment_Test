package com.app.assessment.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.assessment.test.movie.list.MovieListFragment
import com.app.assessment.test.movie.list.showSnackBar
import com.app.assessment.test.movie.list.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * This is only activity which handles all fragments in FragmentContainerView using navigation
 * components.
 */
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        val navHostFragment = supportFragmentManager.primaryNavigationFragment
        val fragmentManager = navHostFragment?.childFragmentManager
        when (val movieListFragment = fragmentManager?.primaryNavigationFragment) {
            is MovieListFragment -> {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed()
                }
                this.doubleBackToExitPressedOnce = true;
                movieListFragment.showSnackBar(getString(R.string.please_press_back_again))
                CoroutineScope(Dispatchers.Main).launch {
                    delay(2000)
                    doubleBackToExitPressedOnce = false
                }
            }
            is SplashFragment -> {

            }
            else -> {
                super.onBackPressed()
            }
        }
    }
}