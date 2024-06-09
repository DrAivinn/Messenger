package com.example.messenger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.messenger.databinding.FragmentRegistrationBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegistrationFragment : Fragment() {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        with(binding) {
            registrationBTN.setOnClickListener {
                signUpUser()
            }
            haveAnAccountBTN.setOnClickListener {
                findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
            }
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

    private fun FragmentRegistrationBinding.signUpUser() {
        val email = emailTIL.editText?.text.toString()
        val password = passwordTIL.editText?.text.toString()
        val confirmPassword = confirmPasswordTIL.editText?.text.toString()
        if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            toastMessage("Адрес электронной почты и пароль не могут быть пустыми")
            return
        }
        if (password.length < 6) {
            toastMessage("Пароль не может быть 6 символов")
            return
        }
        if (password != confirmPassword) {
            toastMessage("Пароли не совпадают")
            return
        }
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                toastMessage("Успешно зарегистрирован")
                findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
            } else {
                if (auth.currentUser != null) {
                    toastMessage("Неверно указана почта, либо пользователь уже существует")
                }
                toastMessage("Регистрация не прошла")
            }
        }
    }
}