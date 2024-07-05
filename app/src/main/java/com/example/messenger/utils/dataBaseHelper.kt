package com.example.messenger.utils

import android.net.Uri
import com.example.messenger.Message
import com.example.messenger.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await

const val OPPONENT = "opponent"
const val TYPE_INCOMING = 2
const val TYPE_OUTGOING = 1

const val NODE_USERS = "users"
const val NODE_MESSAGES = "messages"
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
const val USER_IMAGE_URl = "imageURL"
var listener: ChildEventListener? = null
var userImageUri: Uri? = null
lateinit var currentUid: String
lateinit var auth: FirebaseAuth
lateinit var refUserChatRoot: DatabaseReference
lateinit var refUsersDataBaseRoot: DatabaseReference
lateinit var refUsersStorageRoot: StorageReference
lateinit var refUsersMessagesRoot: DatabaseReference
lateinit var user: User
lateinit var opponent: User


fun initFireBase() {
    auth = FirebaseAuth.getInstance()
    currentUid = auth.currentUser?.uid.toString()
    refUsersDataBaseRoot = FirebaseDatabase.getInstance().getReference(NODE_USERS)
    refUsersStorageRoot = FirebaseStorage.getInstance().getReference(USER_PROFILE_IMAGE)
    refUsersMessagesRoot = FirebaseDatabase.getInstance().getReference(NODE_MESSAGES)

}

suspend fun initUserCoroutine() {
    val snapshot = refUsersDataBaseRoot.child(currentUid).get().await()
    if (snapshot.exists()) {
        user = snapshot.getValue(User::class.java) ?: throw Exception("User data is null")
    } else {
        throw Exception("User data not found")
    }
}

suspend fun initAnotherUserCoroutine(id: String) {
    val snapshot = refUsersDataBaseRoot.child(id).get().await()
    if (snapshot.exists()) {
        opponent = snapshot.getValue(User::class.java) ?: throw Exception("User data is null")
    } else {
        throw Exception("User data not found")
    }
}

fun setupMessageListener(function: (Message) -> Unit) {
    if (listener == null) {
        listener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue(Message::class.java)
                if (message != null) {
                    function(message)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        }
        // Добавляем слушатель к базе данных
        refUserChatRoot = refUsersMessagesRoot.child(user.nickName + user.uid)
            .child(opponent.nickName + opponent.uid)
        refUserChatRoot.addChildEventListener(listener as ChildEventListener)
    }
}

fun sendMessage(text: String) {
    val message = Message(user.nickName, text)
    val messageRoot = refUsersMessagesRoot.push().key.toString()
    refUserChatRoot.child(messageRoot).setValue(message)
    refUsersMessagesRoot
        .child(opponent.nickName + opponent.uid)
        .child(user.nickName + user.uid)
        .child(messageRoot)
        .setValue(message)
}





