package com.example.messenger.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.messenger.models.User
import com.example.messenger.databinding.FragmentOpponentUserProfileBinding
import com.example.messenger.utils.OPPONENT
import com.example.messenger.utils.loadImage
import com.example.messenger.utils.showToast


class OpponentUserProfileFragment : BaseFragment<FragmentOpponentUserProfileBinding>() {
    private lateinit var opponentInfo: User
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentOpponentUserProfileBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFields()
        binding.callBTN.setOnClickListener {
            if (opponentInfo.phone.isBlank()) showToast("Номер телефона не указан") else {
                call(opponentInfo.phone)
            }
        }
        binding.sendSMSBTN.setOnClickListener {
            if (opponentInfo.phone.isBlank()) showToast("Номер телефона не указан") else {
                sendSMS(opponentInfo.phone)
            }
        }

    }

    private fun sendSMS(phoneNumber: String) {
        phoneNumber.isBlank()
        val smsIntent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("sms:$phoneNumber")
        }
        startActivity(smsIntent)
    }

    private fun call(phoneNumber: String) {
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:$phoneNumber")
        startActivity(callIntent)
    }

    @Suppress("DEPRECATION")
    private fun initFields() {
        opponentInfo = arguments?.getParcelable<User>(OPPONENT) ?: User()
        with(binding) {
            val email = opponentInfo.eMail.replaceBefore("@", "*******")
            userAvatarIV.loadImage(opponentInfo.imageURL)
            userNickNameTV.text = opponentInfo.nickName
            userEmailTV.text = email
            userIdTV.append(":\n${opponentInfo.uid}")
            userNameTV.append(": ${opponentInfo.name}")
            userSurname.append(": ${opponentInfo.surname}")
            userOccupationTV.append(": ${opponentInfo.occupation}")
            userPhoneTV.append(": ${opponentInfo.phone}")
            userAgeTV.append(": ${opponentInfo.age}")
            userAddressTV.append(": ${opponentInfo.address}")
            userLoginMethodTV.append(": $email")
        }
    }
}