package cg.vsu.survey.view

import android.content.Context
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cg.vsu.survey.R
import cg.vsu.survey.model.UserRegistration
import cg.vsu.survey.viewmodel.LoginViewModel


class LoginFragment : Fragment() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var errorTextView: TextView
    private lateinit var loginButton: Button
    private lateinit var signUpButton: Button
    private lateinit var togglePasswordButton: ImageButton

    private lateinit var viewModel: LoginViewModel

    private var isPasswordVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        usernameEditText = view.findViewById(R.id.usernameEditText)
        passwordEditText = view.findViewById(R.id.passwordEditText)
        errorTextView = view.findViewById(R.id.errorTextView)
        loginButton = view.findViewById(R.id.loginButton)
        signUpButton = view.findViewById(R.id.signUpButton)
        togglePasswordButton = view.findViewById(R.id.togglePasswordButton)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application))
            .get(LoginViewModel::class.java)

        setupObservers()

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            viewModel.loginUser(UserRegistration(username = username, password = password))
        }

        signUpButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            navigateToSignUpFragment(username, password)
        }

        togglePasswordButton.setOnClickListener {
            if (isPasswordVisible) {
                passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
                togglePasswordButton.setImageResource(R.drawable.ic_visibility_off)
            } else {
                passwordEditText.transformationMethod = null
                togglePasswordButton.setImageResource(R.drawable.ic_visibility)
            }
            isPasswordVisible = !isPasswordVisible
            passwordEditText.setSelection(passwordEditText.text.length)
        }

        return view
    }

    private fun setupObservers() {
        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage != null) {
                errorTextView.text = errorMessage
                errorTextView.visibility = View.VISIBLE
            } else {
                errorTextView.visibility = View.GONE
            }
        }

        viewModel.loginSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                navigateToHomeFragment()
            }
        }
    }

    private fun navigateToHomeFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, FeedSurveyFragment())
            .commit()
    }

    private fun navigateToSignUpFragment(username: String, password: String) {
        val fragment = SignUpFragment().apply {
            arguments = Bundle().apply {
                putString("username", username)
                putString("password", password)
            }
        }
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}
