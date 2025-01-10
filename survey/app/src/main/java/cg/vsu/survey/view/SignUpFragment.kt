package cg.vsu.survey.view

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
import cg.vsu.survey.app.MainActivity
import cg.vsu.survey.model.UserAuth
import cg.vsu.survey.viewmodel.AuthViewModel

class SignUpFragment : Fragment() {

    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var passwordEditTextReplay: EditText
    private lateinit var errorTextView: TextView
    private lateinit var signUpButton: Button
    private lateinit var togglePasswordButton: ImageButton
    private lateinit var togglePasswordButtonReplay: ImageButton

    private lateinit var viewModel: AuthViewModel

    private var isPasswordVisible = false
    private var isPasswordReplayVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        usernameEditText = view.findViewById(R.id.usernameEditText)
        emailEditText = view.findViewById(R.id.emailEditText)
        passwordEditText = view.findViewById(R.id.passwordEditText)
        passwordEditTextReplay = view.findViewById(R.id.passwordEditTextReplay)
        errorTextView = view.findViewById(R.id.errorTextView)
        signUpButton = view.findViewById(R.id.signUpButton)
        togglePasswordButton = view.findViewById(R.id.togglePasswordButton)
        togglePasswordButtonReplay = view.findViewById(R.id.togglePasswordButtonReplay)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(AuthViewModel::class.java)

        setupObservers()

        arguments?.let {
            val username = it.getString("username") ?: ""
            val password = it.getString("password") ?: ""
            usernameEditText.setText(username)
            passwordEditText.setText(password)
        }

        signUpButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val passwordReplay = passwordEditTextReplay.text.toString()
            viewModel.registerUser(UserAuth(username = username, email = email, password = password), passwordReplay)
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

        togglePasswordButtonReplay.setOnClickListener {
            if (isPasswordReplayVisible) {
                passwordEditTextReplay.transformationMethod = PasswordTransformationMethod.getInstance()
                togglePasswordButtonReplay.setImageResource(R.drawable.ic_visibility_off)
            } else {
                passwordEditTextReplay.transformationMethod = null
                togglePasswordButtonReplay.setImageResource(R.drawable.ic_visibility)
            }
            isPasswordReplayVisible = !isPasswordReplayVisible
            passwordEditTextReplay.setSelection(passwordEditTextReplay.text.length)
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

        viewModel.signUpSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                (activity as MainActivity).navigateToHomeFragment()
            }
        }
    }
}
