package cg.vsu.survey.app

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import cg.vsu.survey.R
import cg.vsu.survey.view.home.FeedSurveyFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavBar = findViewById<BottomNavigationView>(R.id.bottomNavigationView)


        bottomNavBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {

                    loadFragment(FeedSurveyFragment())
                    true
                }
                R.id.nav_search -> {

                    loadFragment(FeedSurveyFragment())
                    true
                }
                R.id.nav_add_survey -> {

                    //loadFragment()
                    true
                }
                R.id.nav_profile -> {

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
}

