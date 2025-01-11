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
import cg.vsu.survey.model.User
import cg.vsu.survey.model.UserAuth
import cg.vsu.survey.viewmodel.AuthViewModel

class EditProfileFragment : Fragment() {
    private lateinit var passwordEditTextReplay: EditText
    private lateinit var errorTextView: TextView
    private lateinit var signUpButton: Button
    private lateinit var togglePasswordButton: ImageButton
    private lateinit var togglePasswordButtonReplay: ImageButton
    private lateinit var emailEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var saveButton: ImageButton
    private lateinit var closeButton: ImageButton
    private lateinit var deleteButton: ImageButton

    private lateinit var authViewModel: AuthViewModel

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
        togglePasswordButton = view.findViewById(R.id.togglePasswordButton)
        togglePasswordButtonReplay = view.findViewById(R.id.togglePasswordButtonReplay)

        saveButton = view.findViewById(R.id.saveButton)
        closeButton = view.findViewById(R.id.closeButton)
        deleteButton = view.findViewById(R.id.deleteButton)
        signUpButton = view.findViewById(R.id.signUpButton)

        signUpButton.visibility = View.GONE
        closeButton.visibility = View.VISIBLE
        deleteButton.visibility = View.VISIBLE
        saveButton.visibility = View.VISIBLE

        authViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(AuthViewModel::class.java)

        setupObservers()

        val userId = authViewModel.getUserIdFromPrefs() ?: -1
        if (userId != -1) {
            authViewModel.getUserById(userId)
        }

        setupListeners()
        return view
    }

    private fun setupObservers() {
        authViewModel.userDetails.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                usernameEditText.setText(user.username)
                emailEditText.setText(user.email)
            }
        }

        authViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage != null) {
                errorTextView.text = errorMessage
                errorTextView.visibility = View.VISIBLE
            } else {
                errorTextView.visibility = View.GONE
            }
        }

        authViewModel.loginSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                (activity as MainActivity).navigateToProfileFragment()
            }
        }
    }

    private fun setupListeners() {
        togglePasswordButton.setOnClickListener {
            togglePasswordVisibility(passwordEditText, togglePasswordButton).also {
                isPasswordVisible = it
            }
        }

        togglePasswordButtonReplay.setOnClickListener {
            togglePasswordVisibility(passwordEditTextReplay, togglePasswordButtonReplay).also {
                isPasswordReplayVisible = it
            }
        }

        saveButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val passwordReplay = passwordEditTextReplay.text.toString()

            val userId = authViewModel.getUserIdFromPrefs() ?: -1
            val updatedUser = UserAuth(
                username = username,
                email = email,
                password = password
            )
            authViewModel.updateUserById(userId, updatedUser, passwordReplay)
            (activity as MainActivity).navigateToProfileFragment()
        }

        closeButton.setOnClickListener {
            (activity as MainActivity).navigateToProfileFragment()
        }

        deleteButton.setOnClickListener {
            val userId = authViewModel.getUserIdFromPrefs() ?: -1
            authViewModel.deleteUserById(userId)
            (activity as? MainActivity)?.logout()
            (activity as? MainActivity)?.auth()
        }
    }

    private fun togglePasswordVisibility(editText: EditText, button: ImageButton): Boolean {
        val isVisible = editText.transformationMethod == null
        if (isVisible) {
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
            button.setImageResource(R.drawable.ic_visibility_off)
        } else {
            editText.transformationMethod = null
            button.setImageResource(R.drawable.ic_visibility)
        }
        editText.setSelection(editText.text.length)
        return !isVisible
    }
}