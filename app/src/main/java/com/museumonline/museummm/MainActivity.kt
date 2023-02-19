package com.museumonline.museummm

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.museumonline.museummm.databinding.ActivityMainBinding
import com.museumonline.museummm.model.Direction
import com.museumonline.museummm.R
import com.museumonline.museummm.ui.Utils
import com.museumonline.museummm.ui.langugae.RuntimeLocaleChanger
import com.museumonline.museummm.viewModel.MyViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.michaelrocks.paranoid.Obfuscate

@Obfuscate
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val myViewModel by viewModels<MyViewModel>()
    private var directions: List<Direction>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RuntimeLocaleChanger.setLocale(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_history, R.id.nav_contact
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        initViews()
    }

    private fun initViews() {
        handleFilterButtonVisibility()
        handleLanguageChange()
        setupViewModel()
    }

    private fun handleLanguageChange() {
        binding.appBarMain.apply {
            val currentLang = when (RuntimeLocaleChanger.getLocale(this@MainActivity)) {
                "en" -> 0
                "uz" -> 1
                "ru" -> 2
                else -> 0
            }
            btnChangeLang.setOnClickListener { view ->
                Utils.showLanguageChangeMenu(view) {
                    if (currentLang != it) {
                        when (it) {
                            0 -> RuntimeLocaleChanger.setNewLocale(this@MainActivity, "en")
                            1 -> RuntimeLocaleChanger.setNewLocale(this@MainActivity, "uz")
                            2 -> RuntimeLocaleChanger.setNewLocale(this@MainActivity, "ru")
                        }
                        mRecreate()
                    }

                }
            }
        }
    }

    private fun handleFilterButtonVisibility() {
        supportFragmentManager.registerFragmentLifecycleCallbacks(object :
            FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
                super.onFragmentResumed(fm, f)

                if (supportActionBar!!.title.toString() == getString(R.string.home)) {
                    binding.appBarMain.btnFilter.visibility = VISIBLE
                } else {
                    binding.appBarMain.btnFilter.visibility = GONE
                }
            }
        }, true)

        binding.appBarMain.btnFilter.setOnClickListener { view ->
            if (directions.isNullOrEmpty()) {
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.try_once_again),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Utils.showFilterMenu(view, directions!!) {
                    myViewModel.setArtDescription(it)
                    myViewModel.getArts(it.id.toString(), RuntimeLocaleChanger.getLocale(this))
                }
            }

        }
    }

    private fun setupViewModel() {
        myViewModel.getDirections(RuntimeLocaleChanger.getLocale(this))

        myViewModel.directions.observe(this) { it ->
            directions = it.directions?.toList()

            directions?.get(0)?.let {
                myViewModel.setArtDescription(it)
                myViewModel.getArts(it.id.toString(), RuntimeLocaleChanger.getLocale(this))
            }

        }
    }


    override fun onStart() {
        super.onStart()
        updateLanguageText()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun mRecreate() {
        val starterIntent = this.intent
        this.finish()
        startActivity(starterIntent)
        this.overridePendingTransition(0, 0);
    }

    private fun updateLanguageText() {
        binding.appBarMain.txtLanguage.apply {
            when (RuntimeLocaleChanger.getLocale(this@MainActivity)) {
                "en" -> text = "EN"
                "uz" -> text = "O'Z"
                "ru" -> text = "РУ"
            }
        }

    }
}