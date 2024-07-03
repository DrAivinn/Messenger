package com.example.messenger.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.messenger.R
import com.example.messenger.databinding.FragmentRegistrationBinding
import com.example.messenger.utils.USER_EMAIL
import com.example.messenger.utils.USER_ID
import com.example.messenger.utils.USER_NICKNAME
import com.example.messenger.utils.auth
import com.example.messenger.utils.refUsersDataBaseRoot
import com.example.messenger.utils.showToast

class RegistrationFragment : BaseFragment<FragmentRegistrationBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentRegistrationBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            registrationBTN.setOnClickListener {
                signUpUser()
            }
            haveAnAccountBTN.setOnClickListener {
                findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
            }
        }
    }

    private fun FragmentRegistrationBinding.signUpUser() {
        val nickName = nickNameTIL.editText?.text.toString()
        val email = emailTIL.editText?.text.toString()
        val password = passwordTIL.editText?.text.toString()
        val confirmPassword = confirmPasswordTIL.editText?.text.toString()
        if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            showToast("Поля не могут быть пустыми")
            return
        }
        if (password.length < 6) {
            showToast("Пароль не может быть меньше 6 символов")
            return
        }
        if (password != confirmPassword) {
            showToast("Пароли не совпадают")
            return
        }
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                showToast("Успешно зарегистрирован")
                val userId = auth.currentUser?.uid.toString()
                val userEmail = auth.currentUser?.email.toString()
                val userMap = mutableMapOf<String, Any>(
                    USER_ID to userId,
                    USER_EMAIL to userEmail,
                    USER_NICKNAME to nickName
                )
                refUsersDataBaseRoot.child(userId).updateChildren(userMap)
                findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
            } else {
                if (auth.currentUser != null) {
                    showToast("Неверно указана почта, либо пользователь уже существует")
                }
                showToast("Регистрация не прошла")
            }
        }
    }
}