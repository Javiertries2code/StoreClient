package com.example.storeclient.ui.login

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.Toast
import com.example.storeclient.ui.main.MainActivity
import com.example.storeclient.databinding.FragmentLoginBinding

import com.example.storeclient.R
import com.example.storeclient.data.LoginDataSource
import com.example.storeclient.data.LoginRepository
import com.example.storeclient.utils.goToUsers
import com.example.storeclient.ui.login.LoginViewModelFactory


class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appContext = requireContext().applicationContext
        val loginRepository = LoginRepository(LoginDataSource(appContext))
        loginViewModel = ViewModelProvider(
            this,
            LoginViewModelFactory(loginRepository)
        ).get(LoginViewModel::class.java)

        // Unlock login in case it was locked from a previous session
        loginViewModel.getRepository().unlock()

        view.findViewById<Button>(R.id.go_to_users).setOnClickListener {
            goToUsers()
        }


        super.onViewCreated(view, savedInstanceState)

        // Clear the fileds, for some reason, everytime i was loging out, then it was loggin in itself... perhaps this way will send to loging
        binding.username.text.clear()
        binding.password.text.clear()



        loginViewModel = ViewModelProvider(
            this,
            LoginViewModelFactory(loginRepository)
        )[LoginViewModel::class.java]

        val usernameEditText = binding.username
        val passwordEditText = binding.password
        val loginButton = binding.login
        val loadingProgressBar = binding.loading

        loginViewModel.loginFormState.observe(viewLifecycleOwner,
            Observer { loginFormState ->
                if (loginFormState == null) {
                    return@Observer
                }
                loginButton.isEnabled = loginFormState.isDataValid
                loginFormState.usernameError?.let {
                    usernameEditText.error = getString(it)
                }
                loginFormState.passwordError?.let {
                    passwordEditText.error = getString(it)
                }
            })

        loginViewModel.loginResult.observe(viewLifecycleOwner,
            Observer { loginResult ->
                loginResult ?: return@Observer
                loadingProgressBar.visibility = View.GONE
                loginResult.error?.let {
                    showLoginFailed(it)
                }
                loginResult.success?.let {
                    updateUiWithUser(it)
                    //another try to stop the continuos login
                    //as it was in a loop not triggered by any botton
                    loginViewModel.clearLoginResult()
                }
            })

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                loginViewModel.loginDataChanged(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
        }
        usernameEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
            false
        }

        loginButton.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            loginViewModel.login(
                usernameEditText.text.toString(),
                passwordEditText.text.toString()
            )
        }
    }

    /**
     * in here, after successfull loggin, will save it, callong the delayed 10 min logout, then navigate to the saved target fragment
     */
    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome) + model.displayName
        // TODO : initiate successful logged in experience
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()

        binding.username.text.clear()
        binding.password.text.clear()

        val activity = requireActivity() as? MainActivity
        Log.d("login", "login Fragment -->succesfull")
        //So if everything goes fine, it will navigate later on to the targetfragment  saved before calling login
        if (activity != null) {
            activity.logged_limit()
            activity.navigate(activity.targetFragment)
        }
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clear the fileds, for some reason, everytime i was loging out, then it was loggin in itself... perhaps this way will send to loging
        binding.username.text.clear()
        binding.password.text.clear()
        _binding = null
    }
}
