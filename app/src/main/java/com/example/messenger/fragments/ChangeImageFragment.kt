package com.example.messenger.fragments


import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.messenger.databinding.FragmentChangeImageBinding
import com.example.messenger.utils.ChangeImageDialog
import com.example.messenger.utils.USER_IMAGE_URI
import com.example.messenger.utils.currentUid
import com.example.messenger.utils.loadImage
import com.example.messenger.utils.refUsersDataBaseRoot
import com.example.messenger.utils.refUsersStorageRoot
import com.example.messenger.utils.showToast
import com.example.messenger.utils.user
import com.example.messenger.utils.userImageUri


class ChangeImageFragment : BaseFragment<FragmentChangeImageBinding>() {
    private val CAMERA_PERMISSION_CODE = 100


    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        it?.let {
            userImageUri = it
            binding.avatarImageIV.setImageURI(userImageUri)
        }
    }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                userImageUri.let {
                    binding.avatarImageIV.setImageURI(it)
                }
            }
        }

    override fun getFragmentBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentChangeImageBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.selectImageBTN.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(), android.Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(android.Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_CODE
                )
            } else {
                ChangeImageDialog.showDialog(
                    requireContext(), galleryLauncher, cameraLauncher
                )
                binding.avatarImageIV.loadImage(user.imageUri)
            }
            binding.applyImage.setOnClickListener {
                applyImage()
            }
        }
    }

    private fun applyImage() {
        val path = refUsersStorageRoot.child(currentUid)
        path.putFile(userImageUri).addOnCompleteListener { task1 ->
            if (task1.isSuccessful) {
                refUsersDataBaseRoot.child(currentUid).child(USER_IMAGE_URI)
                    .setValue(userImageUri.toString())
                user.imageUri = userImageUri.toString()
            }
        }
        showToast("Данные обновлены")
    }
}

