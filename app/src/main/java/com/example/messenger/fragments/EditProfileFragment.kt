package com.example.messenger.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.messenger.R
import com.example.messenger.databinding.FragmentEditProfileBinding
import com.example.messenger.utils.USER_ADDRESS
import com.example.messenger.utils.USER_AGE
import com.example.messenger.utils.USER_NAME
import com.example.messenger.utils.USER_OCCUPATION
import com.example.messenger.utils.USER_SURNAME
import com.example.messenger.utils.currentUid
import com.example.messenger.utils.loadImage
import com.example.messenger.utils.refUsersDataBaseRoot
import com.example.messenger.utils.showToast
import com.example.messenger.utils.user

class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentEditProfileBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveBTN.setOnClickListener { saveInfo() }
        binding.avatarIV.setOnClickListener {
            findNavController().navigate(R.id.action_editProfileFragment_to_changeImageFragment)
        }
    }


    private fun initFields() {
        with(binding) {
            nickNameTV.text = user.nickName
            val email = user.eMail.replaceBefore("@", "*******")
            emailTV.text = email
            editNameTIL.editText?.setText(user.name)
            editSurnameTIL.editText?.setText(user.surname)
            editOccupationTIL.editText?.setText(user.occupation)
            editAddressTIL.editText?.setText(user.address)
            editAgeTIL.editText?.setText(user.age)
            avatarIV.loadImage(user.imageUri)
        }
    }

    private fun saveInfo() {
        with(binding) {
            val name = editNameTIL.editText?.text.toString()
            val surname = editSurnameTIL.editText?.text.toString()
            val occupation = editOccupationTIL.editText?.text.toString()
            val address = editAddressTIL.editText?.text.toString()
            val age = editAgeTIL.editText?.text.toString()
            val userMap = mutableMapOf<String, Any>(
                USER_NAME to name, USER_SURNAME to surname,
                USER_OCCUPATION to occupation, USER_ADDRESS to address, USER_AGE to age
            )
            refUsersDataBaseRoot.child(currentUid).updateChildren(userMap).addOnCompleteListener {
                if (it.isSuccessful) showToast("Данные сохранены")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        initFields()
    }


}




