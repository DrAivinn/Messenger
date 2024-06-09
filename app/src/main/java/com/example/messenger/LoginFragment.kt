package com.example.messenger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.messenger.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        with(binding) {
            loginBTN.setOnClickListener {
                login()
            }
            registrationTV.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
            }
        }
    }

    private fun FragmentLoginBinding.login() {
        val email = emailTIL.editText?.text.toString()
        val password = passwordTIL.editText?.text.toString()
        if (email.isBlank() || password.isBlank()) {
            toastMessage("Адрес электронной почты и пароль не могут быть пустыми")
            return
        }
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                toastMessage("Успешно вошел в систему")
                findNavController().navigate(R.id.action_loginFragment_to_startFragment)
            } else toastMessage("Не удалось войти в систему")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun toastMessage(text: String) {
        Toast.makeText(
            requireContext(),
            text,
            Toast.LENGTH_LONG
        ).show()
    }
}