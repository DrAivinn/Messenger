package com.example.messenger.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.messenger.R
import com.example.messenger.databinding.FragmentLoginBinding
import com.example.messenger.utils.auth
import com.example.messenger.utils.currentUid
import com.example.messenger.utils.showToast


class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            loginBTN.setOnClickListener {
                login()
            }
            registrationTV.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
            }
            forgotPasswordTV.setOnClickListener { findNavController().navigate(R.id.action_loginFragment_to_passwordRecoveryFragment) }
        }
    }

    private fun FragmentLoginBinding.login() {
        val email = emailTIL.editText?.text.toString()
        val password = passwordTIL.editText?.text.toString()
        if (email.isBlank() || password.isBlank()) {
            showToast("Адрес электронной почты и пароль не могут быть пустыми")
            return
        }
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                currentUid = auth.currentUser?.uid.toString()
                showToast("Успешно вошел в систему")
                findNavController().navigate(R.id.action_loginFragment_to_startFragment)
            } else showToast("Не удалось войти в систему")
        }
    }

}