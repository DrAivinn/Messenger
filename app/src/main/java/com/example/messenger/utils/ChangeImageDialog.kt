package com.example.messenger.utils

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import com.example.messenger.databinding.ChangeImageDialogBinding
import java.io.File

object ChangeImageDialog {

    fun showDialog(
        context: Context,
        galleryLauncher: ActivityResultLauncher<String>,
        cameraLauncher: ActivityResultLauncher<Uri>
    ) {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = ChangeImageDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        with(binding) {
            galleryBTN.setOnClickListener {
                galleryLauncher.launch("image/*")
                dialog?.cancel()
            }
            cameraBTN.setOnClickListener {
                getFileUri(context).let {
                    userImageUri = it
                    cameraLauncher.launch(it)
                }
                dialog?.cancel()
            }
        }
        dialog = builder.create()
        dialog.show()
    }

    private fun getFileUri(context: Context): Uri {
        val storageDir = File(context.cacheDir, "images").apply { mkdirs() }
        val file = File.createTempFile("temp_file", ".jpg", storageDir).apply {
            createNewFile()
            deleteOnExit()
        }
        return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
    }
}