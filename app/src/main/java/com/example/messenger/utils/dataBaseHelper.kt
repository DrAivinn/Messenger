package com.example.messenger.utils

import android.net.Uri
import android.util.Log
import com.example.messenger.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await


const val NODE_USERS = "users"
const val USER_PROFILE_IMAGE = "userImage"
const val USER_ID = "uid"
const val USER_NICKNAME = "nickName"
const val USER_NAME = "name"
const val USER_SURNAME = "surname"
const val USER_OCCUPATION = "occupation"
const val USER_ADDRESS = "address"
const val USER_PHONE = "phone"
const val USER_EMAIL = "eMail"
const val USER_AGE = "age"
const val USER_IMAGE_URI = "imageUri"
lateinit var userImageUri: Uri
lateinit var currentUid: String
lateinit var auth: FirebaseAuth
lateinit var refUsersDataBaseRoot: DatabaseReference
lateinit var refUsersStorageRoot: StorageReference
lateinit var user: User



fun initFireBase() {
    auth = FirebaseAuth.getInstance()
    currentUid = auth.currentUser?.uid.toString()
    refUsersDataBaseRoot = FirebaseDatabase.getInstance().getReference(NODE_USERS)
    refUsersStorageRoot = FirebaseStorage.getInstance().getReference(USER_PROFILE_IMAGE)
}

suspend fun initUserCoroutine() {
    Log.d("myLOG", "initUser")
    val snapshot = refUsersDataBaseRoot.child(currentUid).get().await()
    if (snapshot.exists()) {
        user = snapshot.getValue(User::class.java) ?: throw Exception("User data is null")
    } else {
        throw Exception("User data not found")
    }
}




