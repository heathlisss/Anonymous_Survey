package cg.vsu.survey.app

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import cg.vsu.survey.R
import cg.vsu.survey.view.home.FeedSurveyFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var searchToolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavBar = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        searchToolbar = findViewById(R.id.searchToolbar)

        bottomNavBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    setToolbarVisibility(false)
                    loadFragment(FeedSurveyFragment())
                    true
                }
                R.id.nav_search -> {
                    setToolbarVisibility(true)
                    //loadFragment(FeedSurveyFragment())
                    true
                }
                R.id.nav_add_survey -> {
                    setToolbarVisibility(false)
                    //loadFragment()
                    true
                }
                R.id.nav_profile -> {
                    setToolbarVisibility(false)
                    //loadFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }

        loadFragment(FeedSurveyFragment())
    }


    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun setToolbarVisibility(isVisible: Boolean) {
        searchToolbar.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}

