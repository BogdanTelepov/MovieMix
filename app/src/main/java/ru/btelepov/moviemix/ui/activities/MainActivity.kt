package ru.btelepov.moviemix.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.NavController

import ru.btelepov.moviemix.utils.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.btelepov.moviemix.R
import ru.btelepov.moviemix.databinding.ActivityMainBinding
import ru.btelepov.moviemix.utils.Constants.Companion.APP_ACTIVITY

import ru.btelepov.moviemix.viewmodels.MainActivityViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding




    private val mainViewModel: MainActivityViewModel by viewModels()
    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
        APP_ACTIVITY = this





    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }


    private fun setupBottomNavigationBar() {
        val bottomNavView = binding.bottomNavigationView
        val navGraphs = listOf(
            R.navigation.graph_movies,
            R.navigation.graph_serials,
            R.navigation.graph_favorites
        )
        val controller = bottomNavView.setupWithNavController(
            navGraphs,
            supportFragmentManager,
            R.id.fragmentContainerView,
            intent
        )

        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean =
        currentNavController?.value?.navigateUp() ?: false


    override fun onBackPressed() {
        if (currentNavController?.value?.popBackStack() != true)
            super.onBackPressed()
    }


}