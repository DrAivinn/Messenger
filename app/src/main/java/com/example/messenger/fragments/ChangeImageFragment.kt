package com.example.messenger.fragments


import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.messenger.databinding.FragmentChangeImageBinding
import com.example.messenger.utils.ChangeImageDialog
import com.example.messenger.utils.USER_IMAGE_URl
import com.example.messenger.utils.currentUid
import com.example.messenger.utils.loadImage
import com.example.messenger.utils.refUsersDataBaseRoot
import com.example.messenger.utils.refUsersStorageRoot
import com.example.messenger.utils.showToast
import com.example.messenger.utils.user
import com.example.messenger.utils.userImageUri
import kotlinx.coroutines.time.delay


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
        binding.avatarImageIV.loadImage(user.imageURL)
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
                binding.avatarImageIV.loadImage(user.imageURL)
            }
            binding.applyImage.setOnClickListener {
                applyImage()
                findNavController().navigateUp()
            }
        }
    }

    private fun applyImage() {
        val path = refUsersStorageRoot.child(currentUid)
        userImageUri?.let {
            path.putFile(it).addOnSuccessListener {
                path.downloadUrl.addOnSuccessListener { uri ->
                    val downloadURL = uri.toString()
                    refUsersDataBaseRoot.child(currentUid).child(USER_IMAGE_URl)
                        .setValue(downloadURL)
                    user.imageURL = downloadURL
                }.addOnFailureListener { exception ->
                    // Обработка ошибки получения URI
                    println("Error occurred: ${exception.message}")
                }
            }.addOnFailureListener { exception ->
                // Обработка ошибки загрузки файла
                println("Upload failed: ${exception.message}")
            }
        } ?: return
        showToast("Данные обновлены")
    }
}

