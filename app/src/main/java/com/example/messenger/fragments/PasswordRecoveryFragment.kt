package com.example.messenger.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.messenger.databinding.FragmentPasswordRecoveryBinding
import com.example.messenger.utils.auth
import com.example.messenger.utils.showToast

class PasswordRecoveryFragment : BaseFragment<FragmentPasswordRecoveryBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPasswordRecoveryBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sendBTN.setOnClickListener {
            val email = binding.emailTIL.editText?.text.toString()
            resetPassword(email)
        }
    }

    private fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showToast("Проверьте вашу почту для сброса пароля")
                } else {
                    showToast("Ошибка сброса пароля")
                }
            }
    }
}