package com.example.messenger.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.messenger.R
import com.example.messenger.databinding.FragmentAuthBinding
import com.example.messenger.utils.auth

class AuthFragment : BaseFragment<FragmentAuthBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentAuthBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (auth.currentUser != null) findNavController().navigate(R.id.action_authFragment_to_startFragment)
        binding.authBTN.setOnClickListener {
            findNavController().navigate(R.id.action_authFragment_to_loginFragment)
        }
    }
}