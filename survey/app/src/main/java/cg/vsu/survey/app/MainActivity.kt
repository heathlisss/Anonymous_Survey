package cg.vsu.survey.app

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import cg.vsu.survey.R
import cg.vsu.survey.view.CreateSurveyFragment
import cg.vsu.survey.view.FeedSearchFragment
import cg.vsu.survey.view.FeedSurveyFragment
import cg.vsu.survey.view.LoginFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var searchToolbar: MaterialToolbar
    private lateinit var bottomNavBar: BottomNavigationView
    private lateinit var sharedPreferences: SharedPreferences
    private val TOKEN_KEY = "jwt_token"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavBar = findViewById(R.id.bottomNavigationView)
        searchToolbar = findViewById(R.id.searchToolbar)


        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        //sharedPreferences.edit().clear().apply()

        val jwtToken = getJwtToken()
        if (jwtToken == null) {
            setNavbarVisibility(false)
            loadFragment(LoginFragment())
        } else {
            loadFragment(FeedSurveyFragment())
        }

        bottomNavBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    setNavbarVisibility(true)
                    setToolbarVisibility(false)
                    loadFragment(FeedSurveyFragment())
                    true
                }
                R.id.nav_add_survey -> {
                    setNavbarVisibility(true)
                    setToolbarVisibility(false)
                    loadFragment(CreateSurveyFragment())
                    true
                }
                R.id.nav_search -> {
                    setNavbarVisibility(true)
                    setToolbarVisibility(true)
                    loadFragment(FeedSearchFragment())
                    true
                }
                R.id.nav_profile -> {
                    setNavbarVisibility(true)
                    setToolbarVisibility(false)
                    //loadFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun getJwtToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, null)
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun setToolbarVisibility(isVisible: Boolean) {
        searchToolbar.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun setNavbarVisibility(isVisible: Boolean) {
        bottomNavBar.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}

