package cg.vsu.survey.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cg.vsu.survey.R
import cg.vsu.survey.adapters.SurveyAdapter
import cg.vsu.survey.app.MainActivity
import cg.vsu.survey.utils.DEFAULT_SURVEYS_WHEN_TO_LOAD
import cg.vsu.survey.viewmodel.AuthViewModel
import cg.vsu.survey.viewmodel.SurveyListViewModel
import com.google.android.material.appbar.MaterialToolbar

class ProfileFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SurveyAdapter
    private lateinit var surveyViewModel: SurveyListViewModel
    private lateinit var authViewModel: AuthViewModel
    private lateinit var logoutButton: ImageButton
    private lateinit var editButton: ImageButton
    private lateinit var usernameTextView: TextView
    private lateinit var profileToolbar: MaterialToolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_feed, container, false)


        recyclerView = view.findViewById(R.id.RV)
        profileToolbar = view.findViewById(R.id.profileToolbar)
        logoutButton = view.findViewById(R.id.logoutButton)
        editButton = view.findViewById(R.id.editButton)
        usernameTextView = view.findViewById(R.id.usernameTextView)
        setProfileToolbarVisibility(true)

        recyclerView.layoutManager = LinearLayoutManager(context)

        surveyViewModel = ViewModelProvider(this).get(SurveyListViewModel::class.java)
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        usernameTextView.text = authViewModel.getUsernameFromPrefs() ?: "Unknown User"

        adapter = SurveyAdapter(surveyViewModel, viewLifecycleOwner)
        recyclerView.adapter = adapter

        logoutButton.setOnClickListener {
            (activity as? MainActivity)?.logout()
            (activity as? MainActivity)?.auth()
        }

        editButton.setOnClickListener {
            val fragment = EditProfileFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit()
        }

        authViewModel.getUserIdFromPrefs()?.let { userId ->
            Log.d("Перешли вк пользователю", " номер: $userId")
            surveyViewModel.loadUserSurveys(userId)
        } ?: run {
            Toast.makeText(requireContext(), "User ID not found", Toast.LENGTH_SHORT).show()
        }

        surveyViewModel.surveys.observe(viewLifecycleOwner) { surveys ->
            adapter.submitList(surveys.toList())
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount - 1 && firstVisibleItemPosition >= 0) {
                    //surveyViewModel.loadData() // Вызываем метод загрузки данных
                }
            }
        })

        return view
    }


    private fun setProfileToolbarVisibility(isVisible: Boolean) {
        profileToolbar.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}

