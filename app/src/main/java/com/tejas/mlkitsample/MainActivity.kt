package com.tejas.mlkitsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.tejas.mlkitsample.application.MLKitApplication
import com.tejas.mlkitsample.databinding.ActivityMainBinding
import com.tejas.mlkitsample.di.MainActivityComponent
import com.tejas.mlkitsample.viewmodel.MainViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainActivityComponent: MainActivityComponent
    lateinit var navController: NavController

    @Inject
    lateinit var mainViewModel: MainViewModel
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MLKitApplication).onCreate()
        mainActivityComponent =
            (applicationContext as MLKitApplication).applicationComponent.mainActivityComponent()
                .create()
        mainActivityComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        navController =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment).navController

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_scanner, R.id.navigation_history
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        mainViewModel.globalErrorObserver.observe(this) { showSnackBar(it) }
    }

    fun showSnackBar(message: String?) {
        val actualMessage = if (message.isNullOrEmpty())
            "Something Went Wrong"
        else
            message
        Snackbar.make(binding.root, actualMessage, Snackbar.LENGTH_LONG).apply {
            setTextColor(ContextCompat.getColor(this@MainActivity, R.color.white))
        }.show()
    }

    override fun onSupportNavigateUp() = navController.navigateUp(appBarConfiguration)

}